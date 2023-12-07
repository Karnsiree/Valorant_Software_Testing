package softwareTestingProject;

import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.ArrayList;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SubAgentParameterResolver.class)
public class SageTest implements skillsBoundaryTestNormal{

    private Agent Sage;
    private Agent Jett;
    private Agent Fade;

    @Override
    public Agent getAgent() {
        return Sage;
    }

    @Override
    public Agent getEnemyAgent() {
        return Jett;
    }

    @BeforeEach
    void getAgentInstance(Map<String,Agent> agentPool){
        this.Jett=agentPool.get("Jett");
        this.Sage=agentPool.get("Sage");
        this.Fade=agentPool.get("Fade");
    }


//    @Test
//    @DisplayName("can only perform her signature ability once")
//    void canUseAbilityOnce()  {
//        ArrayList<Agent> teamA = new ArrayList<>();
//        Jett jett = new Jett("Jett");
//        Sage sage = new Sage("Sage");
//        teamA.add(sage);
//        teamA.add(jett);
//        sage.skills(jett);
//        AssertionsForClassTypes.assertThat(sage.abilitiesDepleted).isEqualTo(true);
//    }

    @Test
    @DisplayName("selected teammate hp + 50 after using her signature ability")
    void TeammatesHealed()  {
        ArrayList<Agent> teamA = new ArrayList<>();
        teamA.add(Sage);
        teamA.add(Jett);
        teamA.add(Fade);
        Sage.skills(Jett); //select jett to heal
        assertThat(Sage.getHp()).isEqualTo(100);
        assertThat(Jett.getHp()).isEqualTo(150); //jett hp + 50
        assertThat(Fade.getHp()).isEqualTo(100);
    }

    @Test
    @DisplayName("can heal herself by 50 hp after using her signature ability")
    void canHealHerself()  {
        ArrayList<Agent> teamA = new ArrayList<>();
        teamA.add(Sage);
        teamA.add(Jett);
        teamA.add(Fade);
        Sage.skills(Sage); //select herself to heal
        assertThat(Sage.getHp()).isEqualTo(150); //Sage hp + 50
        assertThat(Jett.getHp()).isEqualTo(100);
        assertThat(Fade.getHp()).isEqualTo(100);
    }

    @Test
    @DisplayName("cannot heal dead teammates")
    void cannotHealDeadTeammates()  {
        ArrayList<Agent> teamA = new ArrayList<>();
        teamA.add(Sage);
        teamA.add(Jett);
        teamA.add(Fade);
        Jett.setHp(0); //Jett is dead
        Sage.skills(Jett);
        assertThat(Sage.getHp()).isEqualTo(100);
        assertThat(Jett.getHp()).isEqualTo(0); //Cannot heal dead Jett
        assertThat(Fade.getHp()).isEqualTo(100);
        AssertionsForClassTypes.assertThat(Sage.abilitiesDepleted).isEqualTo(false); //ability not depleted
    }

}
