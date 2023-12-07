package softwareTestingProject;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SubAgentParameterResolver.class)
@DisplayName("Neon")
class NeonTest {

    private Agent neon;
    private Agent breach;
    private Agent reyna;
    private Agent jett;


    @BeforeEach
    void getSubAgentInstance(Map<String,Agent> agentPool){

        this.neon= agentPool.get("Neon");
        this.breach= agentPool.get("Breach");
        this.reyna = agentPool.get("Reyna");
        this.jett = agentPool.get("Jett");
    }

    @Test
    @DisplayName("increase her damage to 70 when using her signature ability")
    void increaseDamageTo70WhenUseSkills() {
        neon.skills();
        assertThat(neon.getDamage()).isEqualTo(70);
    }

    @Test
    @DisplayName("enemy agent actually takes 70 damage when Neon uses her ult")
    void enemyDeal70DamageWithHerUlt(){
        neon.skills();
        neon.shoot(breach);

        assertThat(breach.getHp()).isEqualTo(30);
    }

    @Test
    @DisplayName("set damage back to 30 after shooting")
    void setDamageBackTo30(){
        neon.skills();//increase damage to 70
        neon.shoot(breach); //deal 70 damage to enemy breach
        neon.shoot(reyna);

        assertThat(breach.getHp()).isEqualTo(30);
        assertThat(reyna.getHp()).isEqualTo(70);
    }

    @Test
    @DisplayName("her 'hyperboost' status is set to false after finished using signature ability")
    void setHyperBoostToFalseAfterSkills(){
        neon.skills();//hyperboost will be set to True when skills() is called
        neon.shoot(reyna);//hyperboost should be set to false after shooting

        assertFalse(neon.hyperboost);
    }
  
    @Test
    @DisplayName("can only perform her signature ability once")
    void canUseAbilityOnce()  {
        neon.skills();
        neon.shoot(jett);
        assertTrue(neon.abilitiesDepleted);
    }

  
  
}