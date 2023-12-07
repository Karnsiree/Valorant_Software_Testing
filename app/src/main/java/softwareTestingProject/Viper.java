package softwareTestingProject;

public class Viper extends Agent {

    public Viper(String agentName) {
        super(agentName);
    }

    public void skills(Agent target)  {
        //signatures abilities can only be used once

            if(super.abilitiesDepleted==false){
                if(target.isAlive()){
                    System.out.println("Eat my poison..!!");
                    System.out.println("Enemy "+target.getAgentName()+" will take damage x2 once");
                    target.vulnerable=true;
                    super.abilitiesDepleted=true;
                }else{
                    System.out.println("The target is dead, no need to poison, skipping turn");
                }
            }

            else{
                System.out.println("Oh no... I'm too tired to use my signature abilities again");
            }


    }
}
