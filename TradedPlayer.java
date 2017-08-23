public class TradedPlayer extends Player{
	
	private String newTeam;
	
	public TradedPlayer(Player player){
		super(player.getName(),player.getNumber(),player.getTeam(),player.getSalary());
	}
	
	public TradedPlayer(Player player, String newTeam){
		super(player.getName(),player.getNumber(),player.getTeam(),player.getSalary());
		this.setNewTeam(newTeam);
	}

	public String getNewTeam() {
		return newTeam;
	}

	public void setNewTeam(String newTeam) {
		this.newTeam = newTeam;
	}
	
	
}