package softwareTestingProject;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public interface skillsBoundaryTestNormal {

    Agent getAgent();
    Agent getEnemyAgent();
    @Test
    @DisplayName("can only perform his/her signature ability once")
    default void canUseAbilityOnce(){
        getAgent().skills(getEnemyAgent());
        assertTrue(getAgent().abilitiesDepleted);
    }
}
