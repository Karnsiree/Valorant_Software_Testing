package softwareTestingProject;

public class Sage extends Agent{


    public Sage(String agentName) {
        super(agentName);
    }

    public void skills(Agent teammate){

        if(super.abilitiesDepleted==false){
            if(teammate.isAlive()){
            System.out.println("Healing my teammates..!!");
            int newHp = teammate.getHp()+50;
            teammate.setHp(newHp);
            super.abilitiesDepleted=true;
            } else{
                System.out.println("Teammate is already dead, cannot heal a dead person, skipping turn");
            }
        } else{
            System.out.println("Oh no... I'm too tired to use my signature abilities again");
        }
    }
}
