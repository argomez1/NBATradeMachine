import java.util.*;

public class TradeMachine{
	
	//private int TAX_LINE = 119266000;
	private Team[] teams;
	private int[] payrolls;
	private int[] outgoingSalary;
	private int[] incomingSalary;
	private ArrayList<TradedPlayer> tradeArray;
	private HashMap<String,Integer> mapTeamToIndex = new HashMap<String,Integer>();
	
	public TradeMachine(Team[] teams){
		this.teams = teams;
		this.outgoingSalary = new int[teams.length];
		this.incomingSalary = new int[teams.length];
		this.payrolls = new int[teams.length];
		for (int i=0;i<teams.length;i++){
			this.payrolls[i] = teams[i].getTaxLevel();
			this.outgoingSalary[i] = 0;
			this.incomingSalary[i] = 0;
			this.mapTeamToIndex.put(teams[i].getName(), i);
		}
		this.tradeArray = new ArrayList<TradedPlayer>();
	}
	
	
	public void addPlayerToTrade(Player player, String newTeam){
		TradedPlayer tradedPlayer = new TradedPlayer(player,newTeam);
		tradeArray.add(tradedPlayer);
		teams[mapTeamToIndex.get(tradedPlayer.getTeam())].removePlayer(player);
		
		outgoingSalary[mapTeamToIndex.get(tradedPlayer.getTeam())] += tradedPlayer.getSalary();
		payrolls[mapTeamToIndex.get(tradedPlayer.getTeam())] -= tradedPlayer.getSalary();
		
		incomingSalary[mapTeamToIndex.get(tradedPlayer.getNewTeam())] += tradedPlayer.getSalary();
		payrolls[mapTeamToIndex.get(tradedPlayer.getNewTeam())] += tradedPlayer.getSalary();
	}
	
	public void deletePlayerFromTrade(TradedPlayer player){
		outgoingSalary[mapTeamToIndex.get(player.getTeam())] -= player.getSalary();
		payrolls[mapTeamToIndex.get(player.getTeam())] += player.getSalary();
		
		incomingSalary[mapTeamToIndex.get(player.getNewTeam())] -= player.getSalary();
		payrolls[mapTeamToIndex.get(player.getNewTeam())] -= player.getSalary();
		
		Player p = new Player(player.getName(), player.getNumber(), player.getTeam(), player.getSalary());
		teams[mapTeamToIndex.get(player.getTeam())].addPlayer(p);
		tradeArray.remove(player);
	}
	
	public boolean trade() throws UnderTaxLineLowException, UnderTaxLineMidException, UnderTaxLineHighException, OverTaxLineException{
		for (int i=0; i<teams.length; i++){
			if (payrolls[i] >= 1){
				if (incomingSalary[i] > outgoingSalary[i]*1.25 + 100000){
					throw new OverTaxLineException();
				}
			}
			else{
				if (outgoingSalary[i] <= 6533333){
					if (incomingSalary[i] > outgoingSalary[i]*1.75 + 100000){
						throw new UnderTaxLineLowException();
					}
				}
				else if (outgoingSalary[i] <= 19600000){
					if (incomingSalary[i] > outgoingSalary[i] + 5000000){
						throw new UnderTaxLineMidException();
					}
				}
				else{
					if (incomingSalary[i] > outgoingSalary[i]*1.25 + 100000){
						throw new UnderTaxLineHighException();
					}
				}
			}
		}
		return true;
	}
	
	public Team[] getTeams(){
		return this.teams;
	}
	
	public ArrayList<TradedPlayer> getTrades(){
		return tradeArray;
	}
	
}