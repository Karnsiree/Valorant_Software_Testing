package softwareTestingProject;

public class Neon extends Agent{
    public Neon(String agentName) {
        super(agentName);
    }

    public void skills(){
        //she cannot perform her abilities and shooting at the same turn.
        //so 1 turns she spends increasing her dmg, and the other turn she then can attack with 70 damage

        if(abilitiesDepleted==false){
            System.out.println("HYPER BOOST!!!!");
            System.out.println("I just boosted my damage to 70");
            hyperboost=true;
            abilitiesDepleted=true;
            this.setDamage(getDamage()+40);
        }else{
            System.out.println("That previous boost took all of my power.. cannot use it anymore");
        }

    }
}
