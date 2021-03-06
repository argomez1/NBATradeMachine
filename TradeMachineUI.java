import java.util.Scanner;
import java.util.ArrayList;


public class TradeMachineUI{
	
	private static Scanner scanner = new Scanner(System.in);
	
	public static void printMainMenu(){
		System.out.println("Welcome to the NBA Trade Machine!");
	}
	
	public static void printTeams(Team[] teams, int teamCount){
		System.out.println("TEAMS:");
		int maxTeamSize = 0;
		if (teamCount == 0){
			return;
		}
		else if (teamCount > 1){	
			for (int i=0; i<teamCount; i++){
				if (teams[i].getRoster().length > maxTeamSize){
					maxTeamSize = teams[i].getRoster().length;
				}
			}
		}
		else{
			maxTeamSize = teams[0].getRoster().length;
		}
		for (int i=0; i<teamCount; i++){
			System.out.format("%-48s ", teams[i].getName());
		}
		System.out.println();
		for (int i=0; i<teamCount; i++){
			System.out.print("---------------------------------               ");
		}
		System.out.println();
		for (int i=0; i<maxTeamSize; i++){
			for (int j=0; j < teamCount; j++){
				if (i < teams[j].getRosterSize()){
					System.out.format("%3s %-27s $%-8s      ", 
							(i+1) + ")", teams[j].getRoster()[i].getName(), 
								teams[j].getRoster()[i].getSalary());
				}
				else{
					System.out.format("%47s", " ");
				}
			}
			System.out.println();
		}
		System.out.println();
	}
	
	public static void printTrades(ArrayList<TradedPlayer> trades){
		System.out.println("\nTRADES:");
		System.out.format("%-27s %-11s %-24s %-24s\n", "Traded Player", "Salary", "From", "To");
		System.out.println("--------------------------- ----------- ------------------------ ------------------------");
		for (TradedPlayer player : trades){
			System.out.format("%-27s $%-10d %-24s %-24s\n", player.getName(), player.getSalary(), player.getTeam(), player.getNewTeam());
		}
		System.out.println();
	}
	
	public static int getMenuAction(ArrayList<String> teams){
		int action;
		System.out.print("Team(s) in Trade: ");
		for (int i=0; i<teams.size(); i++){
			System.out.print(teams.get(i));
			if (i < teams.size()-1){
				System.out.print(", ");
			}
		}
		System.out.println("\nChoose an action:\n1) Add Team to Trade\n2) Remove Team from Trade\n3) Begin Trade");
		System.out.print("\nEnter action (1-3): ");
		action = scanner.nextInt();
		
		return action;
	}
	
	public static int getTradeAction(){
		int action;
		System.out.println("Choose an action:\n1) Add Team to Trade\n2) Remove Team From Trade\n3) Add Player to Trade\n4) Remove Player from Trade\n5) Attempt Trade\n"
				+ "6) Clear All Players From Trade\n7) Restart\n8) Quit");
		System.out.print("\nEnter action (1-8): ");
		action = scanner.nextInt();
		
		return action;
	}
	
	public static int addTeamToTrade(String[] teams){
		int action;
		System.out.println("Choose a team for trading:\n");
		for (int i=0; i < teams.length; i++){
			System.out.format("%4s%s\n", (i+1)+") ", teams[i]);
		}
		System.out.print("\nEnter number: ");
		action = scanner.nextInt();
		
		return action;
	}
	
	public static int getTeamForTrade(Team[] teams, int teamCount){
		int action;
		System.out.println("Choose a team to trade from:\n");
		for (int i=0; i < teamCount; i++){
			System.out.println((i+1)+") " + teams[i].getName());
		}
		System.out.print("\nEnter number: ");
		action = scanner.nextInt();
		
		return action;
	}
	
	public static int getTeamForTradeTo(Team[] teams, int teamCount){
		int action;
		System.out.println("Choose a team to trade to:\n");
		for (int i=0; i < teams.length; i++){
			System.out.println((i+1)+") " + teams[i].getName());
		}
		System.out.print("\nEnter number: ");
		action = scanner.nextInt();
		
		return action;
	}
	
	
	public static int getPlayer(Team team){
		int action;
		System.out.println("Choose a player to add:\n");
		for (int i=0; i < team.getRosterSize(); i++){
			System.out.println((i+1)+") " + team.getRoster()[i].getName());
		}
		System.out.print("\nEnter number: ");
		action = scanner.nextInt();
		
		return action;
	}
	
	public static int removePlayer(ArrayList<TradedPlayer> trades){
		int action;
		System.out.println("Choose a player to remove:\n");
		for (int i=0; i < trades.size(); i++){
			System.out.println((i+1)+") " + trades.get(i).getName());
		}
		System.out.print("\nEnter number: ");
		action = scanner.nextInt();
		
		return action;
	}
	
	public static int removeTeam(Team[] teams, int teamCount){
		int action;
		System.out.println("Choose a team to remove:\n");
		for (int i=0; i < teamCount; i++){
			System.out.println((i+1)+") " + teams[i].getName());
		}
		System.out.print("\nEnter number: ");
		action = scanner.nextInt();
		
		return action;
	}
}