package softwareTestingProject;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;

public class Gameplay {
    public static void main(String[] args)  {



        Scanner sc = new Scanner(System.in);
        System.out.println("Welcome..!! Choose 3 agents");
        System.out.println("Game will end when all of your agents died");
        System.out.println("1.Jett  2.Sage  3.Reyna 4.Skye 5.Viper 6.Breach 7.Neon 8.Fade");


        ArrayList<Agent>teamA = new ArrayList<>();
        ArrayList<Agent>teamB = new ArrayList<>();


        System.out.println("Player A, choose your 3 agents: ");
        for(int i =0;i<3;i++){
            System.out.print("Agent "+(i+1)+": ");
            int A_chosenAgent = sc.nextInt();
            switch (A_chosenAgent){
                case 1->{Jett jettA = new Jett("Jett");teamA.add(jettA);}
                case 2->{Sage sageA = new Sage("Sage");teamA.add(sageA);}
                case 3->{Reyna reynaA = new Reyna("Reyna");teamA.add(reynaA);}
                case 4->{Skye skyeA = new Skye("Skye");teamA.add(skyeA);}
                case 5->{Viper viperA = new Viper("Viper");teamA.add(viperA);}
                case 6->{Breach breachA = new Breach("Breach");teamA.add(breachA);}
                case 7->{Neon neonA = new Neon("Neon");teamA.add(neonA);}
                case 8->{Fade fadeA = new Fade("Fade");teamA.add(fadeA);}
            }
        }

        System.out.println("Player B, choose your 3 agents: ");
        for(int j =0;j<3;j++){
            System.out.print("Agent "+(j+1)+": ");
            int B_chosenAgent = sc.nextInt();
            switch (B_chosenAgent){
                case 1->{Jett jettB = new Jett("Jett");teamB.add(jettB);}
                case 2->{Sage sageB = new Sage("Sage");teamB.add(sageB);}
                case 3->{Reyna reynaB = new Reyna("Reyna");teamB.add(reynaB);}
                case 4->{Skye skyeB = new Skye("Skye");teamB.add(skyeB);}
                case 5->{Viper viperB = new Viper("Viper");teamB.add(viperB);}
                case 6->{Breach breachB = new Breach("Breach");teamB.add(breachB);}
                case 7->{Neon neonB = new Neon("Neon");teamB.add(neonB);}
                case 8->{Fade fadeB = new Fade("Fade");teamB.add(fadeB);}

            }
        }


        int roundCount =1;

        while(true){
            System.out.println("Round: "+roundCount);

            printGame(teamA,teamB);
            System.out.println("Player A, what are your three moves?");
                for(int i=0;i<3;i++){
                    if(allDead(teamB)){ //put it in here because we want to end the game as soon as one team has no agents left
                        System.out.println("PlayerA won..!");
                        System.exit(0);
                    }
                    Agent agent = teamA.get(i);
                    if(agent.isAlive()){
                        System.out.print("Move for "+agent.getAgentName()+": ");
                        char move = sc.next().charAt(0);
                        if(move=='S'){//if use signature abilities
                            if(agent.getAgentName().equals("Jett")||agent.getAgentName().equals("Neon")||agent.getAgentName().equals("Fade")){//Jett's signature skill only applies to herself
                                agent.skills();
                            }else if(agent.getAgentName().equals("Sage")){//Sage's signature skill only appllies to her teammates
                                System.out.println("Select your teammates to heal (0-2)");
                                int teammate = sc.nextInt();
                                agent.skills(teamA.get(teammate));
                            } else{//other than those two, their signature skills apply to enemy agent
                                System.out.println("Which enemy agent would you like to target? (0-2)");
                                int target = sc.nextInt();
                                agent.skills(teamB.get(target));
                            }
                        }else{//if normal attack

                            if(agent.getAgentName().equals("Fade")&& agent.hunting){//if Fade tries to shoot when after using her ult
                                System.out.println("Choose 2 enemies that you want to shoot at (0-2)");
                                System.out.print("First enemy: ");int target1 = sc.nextInt();
                                System.out.print("Second enemy: ");int target2 = sc.nextInt();
                                agent.shoot(teamB.get(target1),teamB.get(target2));
                            }else {
                                System.out.println("which enemy agent would you shoot at? (0-2)");
                                int target = sc.nextInt();
                                agent.shoot(teamB.get(target));
                            }

                        }
                        System.out.println();
                    }else{
                        System.out.println(agent.getAgentName()+" is dead, moving to next agent");
                    }

            }

            printGame(teamA,teamB);
            System.out.println("Player B, what are your three moves?");
            for(int i=0;i<3;i++){
                if(allDead(teamA)){
                    System.out.println("PlayerB won..!");
                    System.exit(0);
                }
                Agent agent=teamB.get(i);
                if(agent.isAlive()){
                    System.out.print("Move for "+agent.getAgentName()+": ");
                    char move = sc.next().charAt(0);
                    if(move=='S'){//if use signature abilities
                        if(agent.getAgentName().equals("Jett")||agent.getAgentName().equals("Neon")||agent.getAgentName().equals("Fade")){//Jett's signature skill only applies to herself
                            agent.skills();
                        }else if(agent.getAgentName().equals("Sage")){//Sage's signature skill only appllies to her teammates
                            System.out.println("Select your teammates to heal (0-2)");
                            int teammate = sc.nextInt();
                            agent.skills(teamB.get(teammate));
                        } else{//other than those two, their signature skills apply to enemy agent
                            System.out.println("Which enemy agent would you like to target? (0-2)");
                            int target = sc.nextInt();
                            agent.skills(teamA.get(target));
                        }
                    }else{//if normal attack
                        if(agent.getAgentName().equals("Fade")&& agent.hunting){//if Fade tries to shoot when after using her ult
                            System.out.println("My signature abilities allows me to target multiple enemies");
                            System.out.println("Choose 2 enemies that you want to shoot at (0-2)");
                            System.out.print("First enemy: ");int target1 = sc.nextInt();
                            System.out.print("Second enemy: ");int target2 = sc.nextInt();
                            agent.shoot(teamA.get(target1),teamA.get(target2));
                        }else {
                            System.out.println("which enemy agent would you shoot at? (0-2)");
                            int target = sc.nextInt();
                            agent.shoot(teamA.get(target));
                        }
                    }
                    System.out.println();

                }else{
                    System.out.println(agent.getAgentName()+" is dead, moving to next agent");
                }

            }
            for(Agent agentA:teamA){
                int updatemoney = agentA.getMoney()+2000;
                agentA.setMoney(updatemoney);
            }
            for(Agent agentB:teamB){
                int updatemoney = agentB.getMoney()+2000;
                agentB.setMoney(updatemoney);
            }
            roundCount++;
        }


    }




    /*
    Team A                             Team B

     Name    HP   Gun       Money       Name     HP    Gun       Money
    1.Jett   100  Classic    500        1.Skye   100   Operator   800
    2.Reyna  100  Operator  2700        2.Viper  100   Vandal     800
    3.Breach 100  Vandal    1000        3.Sage   100   Classic    800

     */


    public static boolean allDead(ArrayList<Agent>team){
        int killCount =0;
        for(Agent agent : team){
            if(!agent.isAlive()){
                killCount++;
            }
        }
        return killCount == 3;
    }

    public static void printGame(ArrayList<Agent>teamA, ArrayList<Agent>teamB){
        System.out.println("------------------------------------------------------------------------------");
        System.out.println("TeamA \t\t\t\t\t\t\t\t\t\t\tTeamB");
        System.out.printf("%-6s\t%4s\t%4s\t%10s\t\t\t%-6s\t%4s\t%4s\t%10s\n",
                "Name","HP","Dmg","Used Ability?","Name","HP","Dmg","Used Ability?");

        for(int i=0;i<3;i++){
            System.out.printf("%d.%-6s\t%4d\t%4d\t%10s\t\t\t%d.%-6s\t%4d\t%4d\t%10s\n",
                    (i),
                    teamA.get(i).getAgentName(),
                    teamA.get(i).getHp(),
                    teamA.get(i).getDamage(),
                    teamA.get(i).usedAbility(),
                    (i),
                    teamB.get(i).getAgentName(),
                    teamB.get(i).getHp(),
                    teamB.get(i).getDamage(),
                    teamB.get(i).usedAbility());
        }
    }
}
