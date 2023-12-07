package softwareTestingProject;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SubAgentParameterResolver.class)
@DisplayName("Fade")
class FadeTest implements skillsBoundaryTestForSkillsWithOutParameter{
    private Agent fade;
    private Agent jett;
    private Agent sage;

    @BeforeEach
    void getSubAgentInstance(Map<String,Agent> agentPool){
        //note that agentPool is created, and returned from 'Valorant.SubAgentParameterResolver'

        //we simply get the value stored in agentPool
        this.fade= agentPool.get("Fade");
        this.jett= agentPool.get("Jett");
        this.sage = agentPool.get("Sage");
        /*
        Note, agentPool is a map

        Map stores data as key-value --> we access the value by giving the corresponding key

        just like database --> we use private key to retrieve data

        the format is Map<Key,Value> --> so Map<String,Value> means the key is type String, and the value is type Agent

        in our case, key is the name of the agent in String, and the value is the object of that agent

        for example, if we specify our key to be "Jett", then we would get an object of Jett
         */

    }


    @Override
    public Agent getAgent() {
        return fade;
    }


    @Test
    @DisplayName("can shoot at multiple targets when using her signature ability")
    void canShootMultipleTargetWithSignatureAbilities() {
        fade.skills();
        fade.shoot(jett,sage);

        //we shot both jett and sage once, and deal 30 damage each
        assertThat(jett.getHp()).isEqualTo(70);
        assertThat(sage.getHp()).isEqualTo(70);

    }

    @Test
    @DisplayName("set 'hunting' status to false after shooting at multiple target")
    void setHuntingToFalseAfterShooting(){
        fade.skills();
        assertTrue(fade.hunting);//hunting should be true when do .skills()
        fade.shoot(jett,sage);

        assertFalse(fade.hunting);//hunting should be false after finished shooting multiple targets
    }

    @Test
    @DisplayName("does not invoke other agent's hunting status")
    void doesNotInvokeOtherAgentsHuntingStatus(){
        fade.skills();
        assertTrue(fade.hunting);
        fade.shoot(jett,sage);
        assertFalse(jett.hunting);
        assertFalse(sage.hunting);

    }




}