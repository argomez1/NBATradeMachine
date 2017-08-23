import java.sql.*;
import java.util.ArrayList;
public class Run{
	
	private String[] teams = {"Atlanta Hawks", "Boston Celtics", "Brooklyn Nets", "Charlotte Hornets",
			"Chicago Bulls", "Cleveland Cavaliers", "Dallas Mavericks", "Denver Nuggets", "Detroit Pistons",
			"Golden State Warriors", "Houston Rockets", "Indiana Pacers", "Los Angeles Clippers", 
			"Los Angeles Lakers", "Memphis Grizzlies", "Miami Heat", "Milwaukee Bucks", "Minnesota Timberwolves",
			"New Orleans Pelicans", "New York Knicks", "Oklahoma City Thunder", "Orlando Magic", "Philadelphia 76ers",
			"Phoenix Suns", "Portland Trail Blazers", "Sacramento Kings", "San Antonio Spurs", "Toronto Raptors",
			"Utah Jazz", "Washington Wizards"};
	
	private Team[] tradeTeams;
	
	public ArrayList<String> teamsToTradeSetup(){
		ArrayList<String> teamNames = new ArrayList<String>();
		TradeMachineUI.printMainMenu();
		System.out.println();
		while (true){
			int menuAction = TradeMachineUI.getMenuAction(teamNames);
			if (menuAction == 1){
				if (teamNames.size() >=4 ){
					System.out.println("You can only add up to 4 teams.");
				}
				else{
					String teamToAdd = teams[TradeMachineUI.addTeamToTrade(teams)-1];
					if (teamNames.contains(teamToAdd)){
						System.out.println("This team has already been added.");
					}
					else{
						teamNames.add(teamToAdd);
					}
				}
			}
			else if (menuAction == 2){
				teamNames.remove(TradeMachineUI.removeTeam(teamNames)-1);
			}
			else if (menuAction == 3){
				if (teamNames.size() < 2){
					System.out.println("You must add at least 2 teams to trade.");
				}
				else{
					return teamNames;
				}
			}
			else{
				System.out.println("Invalid entry.");
			}
		}
	}
	
	public void grabTeamsFromDatabase(ArrayList<String> teams){
		this.tradeTeams = new Team[teams.size()];
		try{
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/nba", "root", "password");
			for (int i=0; i<teams.size(); i++){
				Statement selectTeam = con.createStatement();
				ResultSet rs = selectTeam.executeQuery("select * from teams where name = \"" + teams.get(i) + "\"");
				if (rs.next()){
					Team teamToAdd = new Team(rs.getString(1), rs.getInt(2));
				
				
					Statement selectRoster = con.createStatement();
					rs = selectRoster.executeQuery("select * from players where team = \"" + teams.get(i) + "\"");
					while (rs.next()){
						Player playerToAdd = new Player(rs.getString(1)+" "+rs.getString(2), rs.getInt(3), rs.getString(4), rs.getInt(5));
						teamToAdd.addPlayer(playerToAdd);
					}
					this.tradeTeams[i] = teamToAdd;
				}
			}
		} catch (Exception e){
			System.out.println("SQL Connectivity Error" + e);
		}
	}
	
	public String tradeMachine(){
		TradeMachine trade = new TradeMachine(this.tradeTeams);
		TradeMachineUI.printTrades(trade.getTrades());
		TradeMachineUI.printTeams(trade.getTeams());
		while (true){
			int action = TradeMachineUI.getTradeAction();
			//adding player to trade
			
			if (action == 1){
				Team tradeFrom = trade.getTeams()[TradeMachineUI.getTeamForTrade(trade.getTeams())-1];
				if (tradeFrom.getRosterSize() > 0){
					Player playerToTrade = tradeFrom.getRoster()[TradeMachineUI.getPlayer(tradeFrom)-1];
					if (trade.getTeams().length > 2){
						Team[] teamsRemaining = new Team[trade.getTeams().length-1];
						int i = 0;
						for (Team team : trade.getTeams()){
							if (!team.getName().equals(tradeFrom.getName())){
								teamsRemaining[i] = team;
								i++;
							}
						}
						Team tradeTo = teamsRemaining[TradeMachineUI.getTeamForTradeTo(teamsRemaining)-1];
						trade.addPlayerToTrade(playerToTrade, tradeTo.getName());
					}
					else{
						if (tradeFrom.getName().equals(trade.getTeams()[0].getName())){
							trade.addPlayerToTrade(playerToTrade, trade.getTeams()[1].getName());
						}
						else{
							trade.addPlayerToTrade(playerToTrade, trade.getTeams()[0].getName());
						}
					}
				}
				else{
					System.out.println("There are no remaining players to trade on this team.");
				}
			}
			else if (action == 2){
				if (trade.getTrades().size() == 0){
					System.out.println("There are no players to remove from trade.");
				}
				else{
					trade.deletePlayerFromTrade(trade.getTrades().get(TradeMachineUI.removePlayer(trade.getTrades())-1));
				}
			}
			else if (action == 3){
				try{
					if (trade.trade()){
						System.out.println("This Trade is Successful!");
					}
				}catch (Exception e){
					System.out.println("This Trade Failed.");
				}
			}
			else if (action == 4){
				int tradeNum = trade.getTrades().size();
				for (int i=0; i<tradeNum; i++){
					trade.deletePlayerFromTrade(trade.getTrades().get(0));
				}
			}
			else if (action == 5){
				return "Restart";
			}
			else if (action == 6){
				return "Quit";
			}
			else{
				System.out.println("Invalid entry.");
			}
			TradeMachineUI.printTrades(trade.getTrades());
			TradeMachineUI.printTeams(trade.getTeams());
		}
	}
	
	public Team[] getTeams(){
		return this.tradeTeams;
	}
	
	public void runTradeMachine(){
		ArrayList<String> teams = teamsToTradeSetup();
		grabTeamsFromDatabase(teams);
		if (tradeMachine().equals("Restart")){
			runTradeMachine();
		}
	}
	
}