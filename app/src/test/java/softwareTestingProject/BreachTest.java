package softwareTestingProject;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SubAgentParameterResolver.class)
@DisplayName("Breach")
class BreachTest implements skillsBoundaryTestNormal{

    private Agent breach;
    private Agent jett;
    private Agent fade;

    @BeforeEach
    void getSubAgentInstance(Map<String,Agent> agentPool) {
        this.breach = agentPool.get("Breach");
        this.jett = agentPool.get("Jett");
        this.fade = agentPool.get("Fade");
    }

    @Override
    public Agent getAgent() {
        return breach;
    }
    @Override
    public Agent getEnemyAgent() {
        return fade;
    }

    //remember to make an interface with 'default' test to increase level of abstraction,
    // and also we won't have to write "can use ability once" for all the test class
    @Test
    @DisplayName("can only perform his signature ability once")
    void abilitycanbeuseonce()  {
        breach.skills(jett);
        assertThat(breach.abilitiesDepleted).isEqualTo(true);
    }

    @Test
    @DisplayName("decrease concussed enemy's damage to 10")
    void concussedEnemyDeal10Damage() {

        breach.skills(jett);
        assertThat(jett.getDamage()).isEqualTo(10);
    }
    @Test
    @DisplayName("is he dealing damage")
    void isdamagedealth(){

        breach.shoot(jett);
        assertThat(jett.getHp()).isEqualTo(70);

    }

    @Test
    @DisplayName("is enemy being effected by concussed")
    void isconcussedactive(){


        breach.skills(fade);
        assertThat(fade.getDamage()).isEqualTo(10);
        fade.shoot(breach);
        assertThat(breach.getHp()).isEqualTo(90);
    }

}