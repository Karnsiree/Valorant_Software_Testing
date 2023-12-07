package softwareTestingProject;

public class Jett extends Agent {


    public Jett(String agentName) {
        super(agentName);
    }



    public void skills() {

        //signatures abilities can only be used once
        if(super.abilitiesDepleted==false){
            System.out.println("swoosshhhhhhh");
            System.out.println("Jett will take no damage for 1 time");
            super.dashing=true;
            super.abilitiesDepleted=true;
        }

        else{
            System.out.println("Oh no... I'm too tired to use my signature abilities again");
        }

    }

}
