package softwareTestingProject;

import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SubAgentParameterResolver.class)
@DisplayName("Reyna")
public class ReynaTest implements skillsBoundaryTestNormal{

    //signature abilities, skills() --> steal enemy's HP by 30 and give it to herself (plus 30 HP for herself)
    //signature abilities message --> “Consuming your health..”

    private Agent reyna;
    private Agent jett;
    private Agent neon;
    private Agent viper;

    private final ByteArrayOutputStream output = new ByteArrayOutputStream();

    @BeforeEach
    void getSubAgentInstance(Map<String,Agent> agentPool){
        this.reyna = agentPool.get("Reyna");
        this.jett = agentPool.get("Jett");
        this.neon= agentPool.get("Neon");
        this.viper= agentPool.get("Viper");
    }

    @Override
    public Agent getAgent() {
        return reyna;
    }

    @Override
    public Agent getEnemyAgent() {
        return jett;
    }

    @Test
    @DisplayName("Reyna gain 30 HP after stealing enemy HP by using her signature ability")
    void stealEnemyHpAfterSkills() {
        reyna.skills(jett); //by using her signature skill
        assertThat(jett.getHp()).isEqualTo(70); //her enemy should lose 30 HP
        assertThat(reyna.getHp()).isEqualTo(130); //and her HP should increase by 30
    }

    @Test
    @DisplayName("Reyna can not use her signature ability when the target enemy is already dead")
    void cannotUseSkillToDeadEnemy() {
        jett.setHp(-999); //jett dead
        System.setOut(new PrintStream(output));
        reyna.skills(jett); //if the enemy is already dead, then skill should be unable to use
        assertEquals("The target is already dead, skipping turn\n",output.toString());
        //it should print: "The target is already dead, skipping turn"
    }

    @Test
    @DisplayName("Reyna's HP not increase when shooting")
    void setHPBackToNoIncreaseWhenShooting(){
        reyna.skills(jett); //increase 30 HP
        reyna.shoot(neon); //no HP gain
        reyna.shoot(viper); //no HP gain
        AssertionsForClassTypes.assertThat(reyna.getHp()).isEqualTo(130);
    }
}