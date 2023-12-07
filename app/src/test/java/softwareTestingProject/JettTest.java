package softwareTestingProject;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SubAgentParameterResolver.class)
@DisplayName("Jett")
public class JettTest implements skillsBoundaryTestForSkillsWithOutParameter {

    private Agent Jett;
    private Agent Neon;

    @BeforeEach
    void getAgentInstance(Map<String, Agent> agentPool) {
        this.Jett = agentPool.get("Jett");
        this.Neon = agentPool.get("Neon");
    }

    @Test
    @DisplayName("Dodge shooting damage when using her signature ability.")
    void dodgeShooting() {
//        Jett jett = new Jett("Jett");
//        Neon neon = new Neon("Neon");
        Jett.skills(); //uses her signature ability to dodge damage
        Neon.shoot(Jett); //Jett shot by Neon while using her signature ability, no damage should be taken.
        assertThat(Jett.getHp()).isEqualTo(100); // check if Jett's HP decreased
    }

    @Override
    public Agent getAgent() {
        return Jett;
    }

//    @Test
//    @DisplayName("can only perform her signature ability once")
//    void canUseAbilityOnce()  {
//        Jett jett = new Jett("Jett");
//        jett.skills();
//        AssertionsForClassTypes.assertThat(jett.abilitiesDepleted).isEqualTo(true);
//    }

    @Test
    @DisplayName("Can dodge the shooting only once,should take damage after second shot.")
    void dodgeShootingOnlyOnce() {
//        Jett jett = new Jett("Jett");
//        Neon neon = new Neon("Neon");
        Jett.skills(); //uses her signature ability to dodge damage for the first time
        Neon.shoot(Jett); //Jett shot by Neon while using her signature ability for the first time, no damage should be taken.
        assertThat(Jett.getHp()).isEqualTo(100); // check if Jett's HP decreased
        Jett.skills(); //uses her signature ability to dodge damage for second time - should have no effect
        Neon.shoot(Jett); //Jett shot by Neon while using her signature ability for second time, damage should be taken.
        assertThat(Jett.getHp()).isEqualTo(70); // check if Jett's HP decreased
    }

//    @Override
//    public Agent getAgent() {
//        return Jett;
//    }

//    @Test
//    @DisplayName("can only perform her signature ability once")
//    void canUseAbilityOnce()  {
//        Jett jett = new Jett("Jett");
//        jett.skills();
//        AssertionsForClassTypes.assertThat(jett.abilitiesDepleted).isEqualTo(true);
//    }

    @Test
    @DisplayName("set status dashing = true when using her signature ability")
    void checkSignatureAbilitySetStatus() {
//        Jett jett = new Jett("Jett");
        Jett.skills();
        assertTrue(Jett.dashing);
    }

//    private Agent Jett;
//    private Agent Neon;


}
