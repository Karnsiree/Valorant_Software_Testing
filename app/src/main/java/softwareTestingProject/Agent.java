package softwareTestingProject;

import java.util.Arrays;
import java.util.stream.Collectors;

public class Agent {
    private final String agentName;
    private int hp = 100;
    private int money =800;
    private int damage= 30;


    //status effects
    boolean abilitiesDepleted = false;
    boolean concussed =false;//damage reduced to 10
    boolean vulnerable = false;//damage received x2
    boolean blinded = false;//cannot attack
    boolean dashing = false;//receive no damage (only apply for jett)
    boolean hyperboost = false; //set neon's damage to 70 for 1 round
    boolean hunting = false; //attack 2 enemies at the same time

    public Agent(String agentName){
        this.agentName=agentName;
    }

    public String getAgentName() {
        return agentName;
    }

    public void setDamage(int newDamage){
        this.damage=newDamage;
    }

    public int getDamage(){
        return damage;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public boolean isAlive(){
        return getHp() > 0;
    }

    public String usedAbility(){
        return String.valueOf(abilitiesDepleted);
    }


    public  void shoot(Agent enemyPlayer){
        damageCalculation(enemyPlayer);
    }

    //special case for Fade
    public  void shoot(Agent enemyPlayer1, Agent enemyPlayer2){
        System.out.println("I just shot Enemy "+enemyPlayer1.getAgentName()+" and Enemy "+enemyPlayer2.getAgentName()+" at the same time HAHA");
        damageCalculation(enemyPlayer1,enemyPlayer2);
        hunting=false;

    }

    public void damageCalculation(Agent... enemyPlayers){
        Arrays.stream(enemyPlayers).forEach(enemy->{
            if(enemy.isAlive()){
                if(enemy.getAgentName().equals("Jett") && enemy.dashing){//if jett is targeted while dashing
                    System.out.println("Cannot attack Jett while she's using her signature ability");
                    enemy.dashing=false;
                }else if(enemy.vulnerable){//if the targeted enemy is poisoned by Viper --> damage x2
                    int remainingHealth = enemy.getHp()- (this.getDamage()*2);
                    enemy.setHp(remainingHealth);
                    System.out.println(enemy.getAgentName()+" will take x2 damage because of the vulnerable effect");
                    enemy.vulnerable=false;
                } else if(blinded){//if the shooter is currently blinded --> cannot attack
                    System.out.println("Cannot shoot Enemy "+enemy.getAgentName()+" while blinded");
                    blinded=false;
                }else if(concussed){//if the shooter is currently concussed --> damage will be set to 10
                    this.setDamage(10);
                    int remainingHealth = enemy.getHp()- this.getDamage();
                    System.out.println(getAgentName()+" is currently concussed, so will only deal 10 damage to Enemy "+enemy.getAgentName());
                    enemy.setHp(remainingHealth);
                    concussed=false;
                } else if(hyperboost){//if neon uses her signature abilities
                    //attack with her boosted damage once
                    int remainingHealth = enemy.getHp() - getDamage();
                    //then set it back to normal
                    this.setDamage(30);
                    enemy.setHp(remainingHealth);
                    hyperboost=false;
                    System.out.println("Shooting at Enemy "+ enemy.getAgentName()+" with my boosted damage");
                }else{//normal case
                    //enemy health = current enemy health - damage of the gun (e.g. phantom does 39 damange per bullet)
                    this.setDamage(30);
                    int remainingHealth = enemy.getHp()- this.getDamage();
                    enemy.setHp(remainingHealth);
                    if(enemyPlayers.length==1) { //if we dont do this, Fade.shoot(target1,target2) will print this line as well as her special line!
                        System.out.println("Shooting at Enemy " + enemy.getAgentName());
                    }
                }

                if(enemy.getHp()<=0){
                    System.out.println("I just killed Enemy "+enemy.getAgentName());
                }
            }else{
                System.out.println("The Enemy "+enemy.getAgentName()+" is already dead..!");
            }


        });
    }


    public void skills()  {
        System.out.println("Using my signature abilities on myself..!");
    }

    public void skills(Agent target)  {
        System.out.println("Using my signature abilities on someone else..!");
    }

    //LOLOLOL this is just to practice squashing commits
    @Override
    public String toString() {
        return "Agent{" +
                "agentName='" + agentName + '\'' + '}';
    }
}
