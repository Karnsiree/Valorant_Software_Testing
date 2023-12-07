package softwareTestingProject;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

//this interface forces all the implemented class to perform a default test
// that makes sure every agent can perform his/her signature ability once
public interface skillsBoundaryTestForSkillsWithOutParameter {

    //get which agent we want to check for 'abilitiesDepleted'
    //if we're working on FadeTest, that means we're testing agent Fade, so just return Fade
    Agent getAgent();

    @Test
    @DisplayName("can only perform his/her signature ability once")
    default void canUseAbilityOnce(){
        getAgent().skills();
        assertTrue(getAgent().abilitiesDepleted);
    }
}
