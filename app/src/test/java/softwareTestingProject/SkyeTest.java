package softwareTestingProject;

import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Map;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SubAgentParameterResolver.class)
@DisplayName("Skye")
public class SkyeTest implements skillsBoundaryTestNormal{

    /*signature abilities, skills() --> throw a highly effective flash grenade called “Hawk”
    gives the blinded effect to the chosen enemy. The blinded enemy will not be able to shoot for 1 time */
    //signature abilities message --> “Hawk out..!!!” and “Blinded <EnemyName>”

    private Agent skye;
    private Agent jett;

    private final ByteArrayOutputStream output = new ByteArrayOutputStream();

    @BeforeEach
    void getSubAgentInstance(Map<String,Agent> agentPool){
        this.skye = agentPool.get("Skye");
        this.jett = agentPool.get("Jett");
    }
    @Override
    public Agent getAgent() {
        return skye;
    }

    @Override
    public Agent getEnemyAgent() {
        return jett;
    }

    @Test
    @DisplayName("Skye's enemy should be blinded after Skye using her signature ability")
    void blindEnemyAfterSkills() {
        skye.skills(jett); //by using her signature skill
        assertTrue(jett.blinded); //her enemy should be blinded
    }

    @Test
    @DisplayName("Skye can not use her signature ability when the target enemy is already dead")
    void cannotUseSkillToDeadEnemy() {
        jett.setHp(-999);
        System.setOut(new PrintStream(output));
        skye.skills(jett); //if the enemy is already dead, then skill should be unable to use
        assertEquals("Target is already dead, no point blinding dead person, skipping turn\n",output.toString());
        //it should print: "Target is already dead, no point blinding dead person, skipping turn"
    }
}