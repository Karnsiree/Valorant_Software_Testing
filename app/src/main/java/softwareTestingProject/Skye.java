package softwareTestingProject;

public class Skye extends Agent {


    public Skye(String agentName) {
        super(agentName);
    }


    public void skills(Agent target) {

            if(super.abilitiesDepleted==false){
                if(target.isAlive()){
                    System.out.println("Hawk out..!!");
                    System.out.println("Blinded Enemy "+target.getAgentName()+" !!!");
                    target.blinded=true;
                    super.abilitiesDepleted=true;
                } else{
                    System.out.println("Target is already dead, no point blinding dead person, skipping turn");
                }
            }
            else{
                System.out.println("Oh no... I'm too tired to use my signature abilities again");
            }



    }
}
