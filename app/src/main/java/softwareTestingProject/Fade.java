package softwareTestingProject;

public class Fade extends Agent{

    public Fade(String agentName) {
        super(agentName);
    }

    public void skills(){

        if(abilitiesDepleted==false){
            System.out.println("Trails Found..!! I can attack 2 of the enemy team at the same time");
            hunting=true;
            abilitiesDepleted=true;
        }else{
            System.out.println("Out of charges..");
        }

    }
}
