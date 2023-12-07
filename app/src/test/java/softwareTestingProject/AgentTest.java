package softwareTestingProject;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(SubAgentParameterResolver.class)
@DisplayName("Every agent")
class AgentTest {
    private ArrayList<Agent> listOfAllAgent;
    private ArrayList<Agent>teamA;
    private ArrayList<Agent>teamB;
    private Agent breach;
    private Agent fade;
    private Agent jett;
    private Agent neon;
    private Agent reyna;
    private Agent sage;
    private Agent skye;
    private Agent viper;

    //for testing system.out.print
    private final ByteArrayOutputStream output = new ByteArrayOutputStream();



    @BeforeEach
    void getAgentInstance(Map<String,Agent> agentPool){
        teamA = new ArrayList<>();
        teamB = new ArrayList<>();
        this.breach=agentPool.get("Breach");
        this.fade = agentPool.get("Fade");
        this.jett = agentPool.get("Jett");
        this.neon = agentPool.get("Neon");
        this.reyna = agentPool.get("Reyna");
        this.sage = agentPool.get("Sage");
        this.skye=agentPool.get("Skye");
        this.viper=agentPool.get("Viper");
        listOfAllAgent = addToTeam(breach,fade,jett,neon,reyna,sage,skye,viper);


    }

    ArrayList<Agent> addToTeam(Agent... agents){
        //use .stream to put all the vararg(agents) into an arraylist
        return (ArrayList<Agent>) Arrays.stream(agents).collect(Collectors.toList());
    }

    @Nested
    @DisplayName("normal behavior & value")
    class defaultValue{
        @Test
        @DisplayName("should have a default damage of 30")
        void defaultDamageIs30() {
            listOfAllAgent.forEach(agent->assertThat(agent.getDamage()).isEqualTo(30));

        }

        @Test
        @DisplayName("should have a default HP of 100")
        void defaultHPis100() {
            listOfAllAgent.forEach(agent -> assertThat(agent.getHp()).isEqualTo(100));
        }

        @Test
        @DisplayName("can change its HP when getting attacked or healed")
        void ableToModifyHP() {

            jett.shoot(neon); //Neon's hp should be set to 70 because of Jett's attack (jett deal 30 damage)
            sage.skills(neon); //Neon's hp should be set to 120 because of Sage's heal (sage heal 50 damage)
            assertThat(neon.getHp()).isEqualTo(120);

        }

    }


    @Nested
    @DisplayName("isAlive()")
    class isAlive{
        @Test
        @DisplayName("should return false if HP is below or equal to 0")
        void isAliveShouldReturnFalseIfHPBelow0() {

            jett.setHp(-999);
            assertFalse(jett.isAlive());
        }

        @Test
        @DisplayName("should return true if HP is greater than 0")
        void isAliveShouldReturnTrueIfHPGreater0(){
            jett.setHp(100);
            assertTrue(jett.isAlive());
        }

    }

    @Nested
    @DisplayName("shoot 1 target")
    class shootOne{

        @Test
        @DisplayName("cannot shoot dead agent")
        void cannotShootDeadAgent() {

            //we create a mock of agent Sage because we don't care how she dies, we just want her to die (mockito method)
            Agent sageMock = Mockito.mock(Sage.class);
            Mockito.when(sageMock.isAlive()).thenReturn(false);//sageMock is considered DEAD
            Mockito.when(sageMock.getAgentName()).thenReturn("Sage");
            System.setOut(new PrintStream(output));//records the system.out.print
            jett.shoot(sageMock);
            //check if the following message is printed when trying to shoot a dead enemy agent
            assertEquals("The Enemy Sage is already dead..!\n",output.toString());
        }

        @Test
        @DisplayName("deal 0 damage when shooting at a dashing jett once")
        void cannotShootDashingJett() {

            jett.dashing=true;//we don't care how she got dashing status, we just mock her to have it (manual method) *manual method means without using mockito*
            System.setOut(new PrintStream(output));//records the system.out.println invoked by reyna.shoot(jett)
            reyna.shoot(jett);
            assertAll(
                    ()->assertThat(jett.getHp()).isEqualTo(100),
                    ()->assertFalse(jett.dashing), //make sure her dashing status is set to false (so next time she will take damage)
                    ()->assertEquals("Cannot attack Jett while she's using her signature ability\n",output.toString())
            );

        }


        @Test
        @DisplayName("deal x2 damage once when an enemy has vulnerable effect")
        void dealX2DamageToVulnerableEnemy(){
            breach.vulnerable=true; //we just mock breach to have vulnerable effect (manual method)
            reyna.shoot(breach);
            assertAll(
                    ()->assertThat(breach.getHp()).isEqualTo(40), //reyna has normal damage: 30, so x2 is 60 --> Breach HP: 100-60 = 40
                    ()->assertFalse(breach.vulnerable)
            );
        }

        @Test
        @DisplayName("cannot deal any damage once if was blinded by Skye's skill")
        void cannotShootWhileBlinded(){
            breach.blinded=true;//we just mock breach to be blinded (manual method)
            System.setOut(new PrintStream(output));//records the system.out.println invoked by breach.shoot(jett)
            breach.shoot(jett);
            assertAll(
                    ()->assertThat(jett.getHp()).isEqualTo(100), //cannot shoot while blinded
                    ()->assertFalse(breach.blinded), //set so that next time breach should be able to shoot normally
                    ()->assertEquals("Cannot shoot Enemy Jett while blinded\n",output.toString())
            );

        }

        @Test
        @DisplayName("deal only 10 damage to enemy when concussed")
        void deal10DamageWhileConcussed(){
            skye.concussed=true; //mock skye to be concussed (manual method)
            System.setOut(new PrintStream(output));//records the system.out.println of skye.shoot(jett)
            skye.shoot(jett);
            assertAll(
                    ()->assertThat(jett.getHp()).isEqualTo(90),//concussed agent can only deal 10 damage
                    ()->assertFalse(skye.concussed),
                    ()->assertEquals("Skye is currently concussed, so will only deal 10 damage to Enemy Jett\n",output.toString())
            );

        }

        @Test
        @DisplayName("deal 70 damage when have hyperboost effect")
        void deal70DamageWhileHyperBoost(){
            neon.skills();
            System.setOut(new PrintStream(output));//records the system.out.println of neon.shoot(fade)
            neon.shoot(fade);
            assertAll(
                    ()->assertThat(fade.getHp()).isEqualTo(30),
                    ()->assertFalse(neon.hyperboost),
                    ()->assertEquals("Shooting at Enemy Fade with my boosted damage\n",output.toString())
            );
        }

        @Test
        @DisplayName("a shooting message is printed and deals 30 damage when shooting at 1 enemy")
        void deal30DamageInNormalShooting(){
            System.setOut(new PrintStream(output));//records the system.out.print
            jett.shoot(breach);
            assertAll(
                    ()->assertThat(breach.getHp()).isEqualTo(70),
                    ()->assertEquals("Shooting at Enemy Breach\n",output.toString())
            );
        }

        @Test
        @DisplayName("a kill message should be printed when perform normal shooting and killed enemy")
        void killMessagePrintedAfterkillingWithNormalShooting(){
            System.setOut(new PrintStream(output));//records the system.out.print
            jett.setHp(10);
            skye.shoot(jett);//jett should be dead because her hp will be -20 from skye's attack
            assertEquals("""
                    Shooting at Enemy Jett
                    I just killed Enemy Jett
                    """,output.toString());
        }

        @Test
        @DisplayName("a kill message should be printed when perform shooting with hyperboost effect and killed enemy")
        void killMessagePrintedAfterKillingWithHyperBoost(){
            neon.skills();
            jett.setHp(20);
            System.setOut(new PrintStream(output));//records the system.out.print at neon.shoot(jett)
            neon.shoot(jett);
            assertEquals("""
                    Shooting at Enemy Jett with my boosted damage
                    I just killed Enemy Jett
                    """, output.toString());
        }

        @Test
        @DisplayName("a kill message should be printed when perform shooting and killed a vulnerable enemy")
        void killMessagePrintedAfterKillingAVulnerableEnemy(){
            jett.vulnerable=true;
            jett.setHp(20);
            System.setOut(new PrintStream(output));//records the system.out.print
            reyna.shoot(jett);
            assertEquals("""
                    Jett will take x2 damage because of the vulnerable effect
                    I just killed Enemy Jett
                    """,output.toString());
        }







    }
    @Nested
    @DisplayName("shoot 2 targets")
    class shootTwo{
        //TODO: Add more test cases for shoot() with 2 targets

        @Test
        @DisplayName("a shooting message is printed and deals 30 damage to 2 enemies when given 2 agents as arguments ")
        void shootWithTwoArgumentsDealDamageTo2Enemies(){
            System.setOut(new PrintStream(output));//records the system.out.print
            fade.shoot(jett,breach);
            assertAll(
                    ()->assertThat(jett.getHp()).isEqualTo(70),
                    ()->assertThat(breach.getHp()).isEqualTo(70),
                    ()->assertEquals(
                            "I just shot Enemy Jett and Enemy Breach at the same time HAHA\n",
                            output.toString()
                    )
            );
        }

        @Test
        @DisplayName("only 1 kill message should be printed when shooting at 2 enemies but only 1 died")
        void shootTwoKillOne(){
            jett.setHp(20); //mock hp
            System.setOut(new PrintStream(output));//records the system.out.print
            fade.shoot(jett,breach);
            assertEquals("""
                            I just shot Enemy Jett and Enemy Breach at the same time HAHA
                            I just killed Enemy Jett
                            """,
                    output.toString());
        }

        @Test
        @DisplayName("2 kill messages should be printed when shooting at 2 enemies and kill them both")
        void shootTwoKillTwo(){
            jett.setHp(20);//mock hp
            breach.setHp(20);//mock hp
            System.setOut(new PrintStream(output));//records the system.out.print
            fade.shoot(jett,breach);
            assertEquals("""
                            I just shot Enemy Jett and Enemy Breach at the same time HAHA
                            I just killed Enemy Jett
                            I just killed Enemy Breach
                            """,
                    output.toString());
        }

        @Test
        @DisplayName("shooting at 2 enemies should set the hunting status to be false")
        void sethuntingToFalseAfterShootingAtMultipleEnemies(){
            fade.hunting=true; //mock hunting to be true
            fade.shoot(jett,breach); //hunting should be set to false here
            assertFalse(fade.hunting);
        }

        @Test
        @DisplayName("shooting at 2 enemies should only deal damage to 1 enemy if one if them is a dashing Jett")
        void shoot2ArgCannotShootDashingJett(){
            jett.dashing=true;
            System.setOut(new PrintStream(output));//records the system.out.print
            fade.shoot(jett,skye);
            assertAll(
                    ()->assertThat(jett.getHp()).isEqualTo(100),
                    ()->assertThat(skye.getHp()).isEqualTo(70),
                    ()->assertEquals("""
                            I just shot Enemy Jett and Enemy Skye at the same time HAHA
                            Cannot attack Jett while she's using her signature ability
                            """,
                            output.toString())
            );
        }

        @Test
        @DisplayName("shooting at 2 enemies should deal 30 damage to normal enemy and x2 damage to vulnerable enemy")
        void shoot2DealNormalOneDealX2one(){
            neon.vulnerable=true;
            System.setOut(new PrintStream(output));//records the system.out.print
            fade.shoot(reyna,neon);
            assertAll(
                    ()->assertThat(reyna.getHp()).isEqualTo(70), //reyna should take normal 30 damage since she doesn't have any effects
                    ()->assertThat(neon.getHp()).isEqualTo(40), //neon should take 60 damage because of vulnerable effect
                    ()->assertEquals("""
                            I just shot Enemy Reyna and Enemy Neon at the same time HAHA
                            Neon will take x2 damage because of the vulnerable effect
                            """,output.toString())
            );
        }


        @Test
        @DisplayName("shooting at 2 enemies while blinded should be able to deal damage to 1 enemy")
        void shoot2ShouldHitOneWhenBlinded(){
            fade.blinded=true;
            System.setOut(new PrintStream(output));//records the system.out.print
            fade.shoot(reyna,neon);
            assertAll(
                    ()->assertThat(reyna.getHp()).isEqualTo(100),//fade cannot deal damage to reyna because she's blinded
                    ()->assertThat(neon.getHp()).isEqualTo(70),//now fade can attack neon because 'blinded' effects only last for one attack
                    ()->assertEquals("""
                            I just shot Enemy Reyna and Enemy Neon at the same time HAHA
                            Cannot shoot Enemy Reyna while blinded
                            """,output.toString())
            );
        }

        @Test
        @DisplayName("shooting at 2 enemies while concussed should deal 10 damage to one enemy, and 30 damage to the other enemy")
        void shoot2Deal10OneDeal30OneWheenConcussed(){
            fade.concussed=true;
            System.setOut(new PrintStream(output));//records the system.out.print
            fade.shoot(jett,sage);
            assertAll(
                    ()->assertThat(jett.getHp()).isEqualTo(90),
                    ()->assertThat(sage.getHp()).isEqualTo(70),
                    ()->assertEquals("""
                            I just shot Enemy Jett and Enemy Sage at the same time HAHA
                            Fade is currently concussed, so will only deal 10 damage to Enemy Jett
                            """,output.toString())
            );

        }

        @Test
        @DisplayName("shooting at 2 enemies should only deal damage to one alive enemy and do nothing on the dead enemy")
        void shoot2Alive1ShouldDeal1(){
            neon.setHp(-999); //mock
            System.setOut(new PrintStream(output));//records the system.out.print
            fade.shoot(jett,neon);
            assertAll(
                    ()->assertThat(jett.getHp()).isEqualTo(70),
                    ()->assertThat(neon.getHp()).isEqualTo(-999), //shouldnt deal any more damage since she's already dead
                    ()->assertEquals("""
                            I just shot Enemy Jett and Enemy Neon at the same time HAHA
                            The Enemy Neon is already dead..!
                            """,output.toString())
            );
        }

        @Test
        @DisplayName("shooting at 2 dead enemies should deal 0 damage to both of them")
        void shoot2DeadShouldDoNothing(){
            neon.setHp(-999);
            jett.setHp(-999);
            System.setOut(new PrintStream(output));//records the system.out.print
            fade.shoot(neon,jett);
            assertAll(
                    ()->assertThat(neon.getHp()).isEqualTo(-999),
                    ()->assertThat(jett.getHp()).isEqualTo(-999),
                    ()->assertEquals("""
                            I just shot Enemy Neon and Enemy Jett at the same time HAHA
                            The Enemy Neon is already dead..!
                            The Enemy Jett is already dead..!
                            """,output.toString())
            );
        }


    }



}