package softwareTestingProject;

public class Reyna extends Agent{

    public Reyna(String agentName) {
        super(agentName);
    }

    public void skills(Agent target){
        //signatures abilities can only be used once

            if(super.abilitiesDepleted==false){
                if(target.isAlive()){
                    System.out.println("Consuming your health..!!");
                    int targetNewHp = target.getHp()-30;
                    target.setHp(targetNewHp);
                    int reynaNewHp = super.getHp()+30;
                    super.setHp(reynaNewHp);
                    super.abilitiesDepleted=true;
                }else{
                    System.out.println("The target is already dead, skipping turn");
                }
            } else{
                System.out.println("Oh no... I'm too tired to use my signature abilities again");
            }


    }
}
