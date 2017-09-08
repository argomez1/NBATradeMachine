public class Run{
	
	private String[] teams = {"Atlanta Hawks", "Boston Celtics", "Brooklyn Nets", "Charlotte Hornets",
			"Chicago Bulls", "Cleveland Cavaliers", "Dallas Mavericks", "Denver Nuggets", "Detroit Pistons",
			"Golden State Warriors", "Houston Rockets", "Indiana Pacers", "Los Angeles Clippers", 
			"Los Angeles Lakers", "Memphis Grizzlies", "Miami Heat", "Milwaukee Bucks", "Minnesota Timberwolves",
			"New Orleans Pelicans", "New York Knicks", "Oklahoma City Thunder", "Orlando Magic", "Philadelphia 76ers",
			"Phoenix Suns", "Portland Trail Blazers", "Sacramento Kings", "San Antonio Spurs", "Toronto Raptors",
			"Utah Jazz", "Washington Wizards"};
	
	private Team[] tradeTeams;
	
	//runs menu prompt
	public String tradeMachine(){
		TradeMachineUI.printMainMenu();
		TradeMachine trade = new TradeMachine();
		TradeMachineUI.printTrades(trade.getTrades());
		TradeMachineUI.printTeams(trade.getTeams(), trade.getTeamCount());
		while (true){
			int action = TradeMachineUI.getTradeAction();
			//adding team to trade
			if (action == 1){
				trade.addTeamToTrade(teams[TradeMachineUI.addTeamToTrade(teams)-1]);
			}
			//removing team from trade
			else if (action == 2){
				trade.removeTeamFromTrade(trade.getTeams()[TradeMachineUI.removeTeam(trade.getTeams(), trade.getTeamCount())-1].getName());
			}
			//adding player to trade
			else if (action == 3){
				Team tradeFrom = trade.getTeams()[TradeMachineUI.getTeamForTrade(trade.getTeams(), trade.getTeamCount())-1];
				if (tradeFrom.getRosterSize() > 0){
					Player playerToTrade = tradeFrom.getRoster()[TradeMachineUI.getPlayer(tradeFrom)-1];
					if (trade.getTeamCount() > 2){
						Team[] teamsRemaining = new Team[trade.getTeamCount()-1];
						int index = 0;
						for (int i=0; i<trade.getTeamCount(); i++){
							if (!trade.getTeams()[i].getName().equals(tradeFrom.getName())){
								teamsRemaining[index] = trade.getTeams()[i];
								index++;
							}
						}
						Team tradeTo = teamsRemaining[TradeMachineUI.getTeamForTradeTo(teamsRemaining, trade.getTeamCount())-1];
						trade.addPlayerToTrade(tradeFrom, playerToTrade, tradeTo);
					}
					else{
						if (tradeFrom.getName().equals(trade.getTeams()[0].getName())){
							trade.addPlayerToTrade(trade.getTeams()[0], playerToTrade, trade.getTeams()[1]);
						}
						else{
							trade.addPlayerToTrade(trade.getTeams()[1], playerToTrade, trade.getTeams()[0]);
						}
					}
				}
				else{
					System.out.println("There are no remaining players to trade on this team.");
				}
			}
			//removing player from trade
			else if (action == 4){
				if (trade.getTrades().size() == 0){
					System.out.println("There are no players to remove from trade.");
				}
				else{
					TradedPlayer tp = trade.getTrades().get(TradeMachineUI.removePlayer(trade.getTrades())-1);
					trade.deletePlayerFromTrade(tp);
				}
			}
			//attempt to trade
			else if (action == 5){
				try{
					if (trade.trade()){
						System.out.println("This Trade is Successful!");
					}
				}catch (Exception e){
					System.out.println("This Trade Failed.");
				}
			}
			//clear all players from trade
			else if (action == 6){
				int tradeNum = trade.getTrades().size();
				for (int i=0; i<tradeNum; i++){
					trade.deletePlayerFromTrade(trade.getTrades().get(0));
				}
			}
			//restart
			else if (action == 7){
				return "Restart";
			}
			//quit
			else if (action == 8){
				return "Quit";
			}
			else{
				System.out.println("Invalid entry.");
			}
			TradeMachineUI.printTrades(trade.getTrades());
			TradeMachineUI.printTeams(trade.getTeams(), trade.getTeamCount());
		}
	}
	
	public Team[] getTeams(){
		return this.tradeTeams;
	}
	
	public void runTradeMachine(){
		if (tradeMachine().equals("Restart")){
			runTradeMachine();
		}
	}
	
}