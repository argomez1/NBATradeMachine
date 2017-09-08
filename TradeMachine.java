import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;

public class TradeMachine{
	
	//private int TAX_LINE = 119266000;
	private Team[] teams;
	private ArrayList<TradedPlayer> trades;
	private int teamCount;
	
//	public TradeMachine(Team[] teams){
//		this.teams = teams;
//		this.outgoingSalary = new int[teams.length];
//		this.incomingSalary = new int[teams.length];
//		this.payrolls = new int[teams.length];
//		for (int i=0;i<teams.length;i++){
//			this.payrolls[i] = teams[i].getTaxLevel();
//			this.outgoingSalary[i] = 0;
//			this.incomingSalary[i] = 0;
//			this.mapTeamToIndex.put(teams[i].getName(), i);
//		}
//		this.tradeArray = new ArrayList<TradedPlayer>();
//	}
	
	public TradeMachine(){
		this.teams = new Team[4];
		this.trades = new ArrayList<TradedPlayer>();
		this.teamCount = 0;
	}
	
	public Team grabTeamFromDatabase(String team){
		Team teamToAdd = new Team();
		try{
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/nba", "root", "password");
			Statement selectTeam = con.createStatement();
			ResultSet rs = selectTeam.executeQuery("select * from teams where name = \"" + team + "\"");
			if (rs.next()){
				teamToAdd.setName(rs.getString(1));
				teamToAdd.setTaxLevel(rs.getInt(2));
				Statement selectRoster = con.createStatement();
				rs = selectRoster.executeQuery("select * from players where team = \"" + team + "\"");
				while (rs.next()){
					Player playerToAdd = new Player(rs.getString(1)+" "+rs.getString(2), rs.getInt(3), rs.getString(4), rs.getInt(5));
					teamToAdd.addPlayer(playerToAdd);
				}
			}
		} catch (Exception e){
			System.out.println("SQL Connectivity Error" + e);
		}
		return teamToAdd;
	}
	
	public void addTeamToTrade(String team){
		if (teamCount < 4){
			Team toAdd = grabTeamFromDatabase(team);
			this.teams[this.teamCount] = toAdd;
			this.teamCount++;
		}
		else{
			System.out.println("Too many teams.");
		}
	}
	
	public void removeTeamFromTrade(String team){
		if (this.teamCount != 0){
			for (int i=0; i<this.teamCount; i++){
				if (this.teams[i].getName().equals(team)){
					for (int j=i; j<this.teamCount-1; j++){
						this.teams[j] = this.teams[j+1];
					}
					this.teams[this.teamCount] = null;
					this.teamCount--;
				}
			}
			ArrayList<TradedPlayer> tradesCopy = new ArrayList<TradedPlayer>(trades);
			for (TradedPlayer tp : tradesCopy){
				if (tp.getTeam().equals(team) || tp.getNewTeam().equals(team)){
					deletePlayerFromTrade(tp);
				}
			}
		}
		else{
			System.out.println("Cannot remove team.");
		}
	}
	
	
	public void addPlayerToTrade(Team team, Player player, Team newTeam){
		if (teamCount < 2){
			System.out.println("You must have at least 2 teams to trade a player.");
			return;
		}
		TradedPlayer tp = new TradedPlayer(player,newTeam.getName());
		this.trades.add(tp);
		newTeam.setTaxLevel(newTeam.getTaxLevel() + player.getSalary());
		team.setTaxLevel(team.getTaxLevel() - player.getSalary());
		
		newTeam.setIncomingSalary(newTeam.getIncomingSalary() + player.getSalary());
		team.setOutgoingSalary(team.getOutgoingSalary() + player.getSalary());
		team.removePlayer(player);
	}
	
	public void deletePlayerFromTrade(TradedPlayer player){
		this.trades.remove(player);
		for (int i=0; i<teamCount; i++){
			if (teams[i].getName().equals(player.getTeam())){
				Team origTeam = teams[i];
				origTeam.setTaxLevel(origTeam.getTaxLevel() + player.getSalary());
				origTeam.setOutgoingSalary(origTeam.getOutgoingSalary() - player.getSalary());
				origTeam.addPlayer(new Player(player.getName(),player.getNumber(),player.getTeam(), player.getSalary()));
			}
			if (teams[i].getName().equals(player.getNewTeam())){
				Team newTeam = teams[i];
				newTeam.setTaxLevel(newTeam.getTaxLevel() - player.getSalary());
				newTeam.setIncomingSalary(newTeam.getIncomingSalary() - player.getSalary());
			}
		}
	}
	
	public boolean trade() throws UnderTaxLineLowException, UnderTaxLineMidException, UnderTaxLineHighException, OverTaxLineException{
		for (int i=0; i<teamCount; i++){
			if (teams[i].getTaxLevel() >= 1){
				if (teams[i].getIncomingSalary() > teams[i].getOutgoingSalary()*1.25 + 100000){
					throw new OverTaxLineException();
				}
			}
			else{
				if (teams[i].getOutgoingSalary() <= 6533333){
					if (teams[i].getIncomingSalary() > teams[i].getOutgoingSalary()*1.75 + 100000){
						throw new UnderTaxLineLowException();
					}
				}
				else if (teams[i].getOutgoingSalary() <= 19600000){
					if (teams[i].getIncomingSalary() > teams[i].getOutgoingSalary() + 5000000){
						throw new UnderTaxLineMidException();
					}
				}
				else{
					if (teams[i].getIncomingSalary() > teams[i].getOutgoingSalary()*1.25 + 100000){
						throw new UnderTaxLineHighException();
					}
				}
			}
		}
		return true;
	}
	
	public ArrayList<TradedPlayer> getTrades(){
		return this.trades;
	}
	
	public Team[] getTeams(){
		return this.teams;
	}
	
	public int getTeamCount(){
		return this.teamCount;
	}
	
}