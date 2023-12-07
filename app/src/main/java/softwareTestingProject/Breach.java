package softwareTestingProject;

public class Breach extends Agent{

    public Breach(String agentName) {
        super(agentName);
    }

    public void skills(Agent target)  {
        //signatures abilities can only be used once

            if(super.abilitiesDepleted==false){
                if(target.isAlive()){
                    System.out.println("STUNNING..!!");
                    System.out.println("Enemy "+target.getAgentName()+" is now concussed!!");
                    target.concussed=true;
                    target.setDamage(10);
                    super.abilitiesDepleted=true;
                }else{
                    System.out.println("The target is already dead, no need to concuss anymore, skipping turn");
                }
            }else{
                System.out.println("Oh no... I'm too tired to use my signature abilities again");
            }


    }
}
