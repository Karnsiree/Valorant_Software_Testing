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
@DisplayName("Viper")
class ViperTest implements skillsBoundaryTestNormal{

    private Agent viper;
    private Agent reyna;
    private Agent breach;

    @BeforeEach
    void getSubAgentInstance(Map<String,Agent> agentPool) {
        //note that agentPool is created, and returned from 'Valorant.SubAgentParameterResolver'

        //we simply get the value stored in agentPool
        this.viper = agentPool.get("Viper");
        this.reyna = agentPool.get("Reyna");
        this.breach = agentPool.get("Breach");
    }
    @Override
    public Agent getAgent() {
        return viper;
    }

    @Override
    public Agent getEnemyAgent() {
        return reyna;
    }

    @Test
    @DisplayName("is Enenmy agent affected by vulnerable effect")
    void vulnerableeffect() {

        viper.skills(breach); //enemy breach take twice damage

    }

    @Test
    @DisplayName("is enemy taking damge form her normal attack")
    void normalattackonenemy() {

        viper.shoot(reyna);
        assertThat(reyna.getHp()).isEqualTo(70);
    }
}