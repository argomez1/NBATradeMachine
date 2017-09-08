public class Team{
	
	private String name;
	private Player[] roster;
	private int rosterSize;
	private int taxLevel;
	private int incomingSalary;
	private int outgoingSalary;

	
	public Team(){
		this.name = null;
		this.taxLevel = 0;
		this.roster = new Player[16];
		this.rosterSize = 0;
		this.setOutgoingSalary(0);
		this.setIncomingSalary(0);
	}
	
	public Team(String name, int taxLevel){
		this.setName(name);
		this.setTaxLevel(taxLevel);
		this.roster = new Player[16];

	}
	
	public void addPlayer(Player player){
		
		if (rosterSize >= 16){
			System.out.println("Roster full");
		}
		else if (rosterSize == 0){
			roster[0] = player;
			rosterSize++;
		}
		else{
			for (int i=0; i<rosterSize; i++){
				//if(player.getSalary() == roster[i].getSalary()){
					
					//return;
				//}
				if (player.getSalary() >= roster[i].getSalary()){
					for (int j=rosterSize; j>i; j--){
						roster[j] = roster[j-1];
					}
					roster[i] = player;
					rosterSize++;
					return;
				}
			}
			roster[rosterSize] = player;
			rosterSize++;
		}	
	}
	
	public void removePlayer(Player player){
		if (rosterSize < 1){
			System.out.println("Roster is empty.");
		}
		else if (rosterSize == 1){
			roster[0] = null;
			rosterSize--;
		}
		else{
			for (int i=0; i<rosterSize; i++){
				if (roster[i].getName() == player.getName() && roster[i].getNumber() == player.getNumber()){
					if (i == rosterSize-1){
						roster[i] = null;
						break;
					}
					for (int j=i; j<rosterSize-1; j++){
						roster[j] = roster[j+1];
					}
					roster[rosterSize-1] = null;
					rosterSize--;
					break;
				}
			}
		}
	}
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public Player[] getRoster(){
		return this.roster;
	}
	
	public int getRosterSize(){
		return this.rosterSize;
	}

	public int getTaxLevel() {
		return taxLevel;
	}

	public void setTaxLevel(int taxLevel) {
		this.taxLevel = taxLevel;
	}

	public int getIncomingSalary() {
		return incomingSalary;
	}

	public void setIncomingSalary(int incomingSalary) {
		this.incomingSalary = incomingSalary;
	}

	public int getOutgoingSalary() {
		return outgoingSalary;
	}

	public void setOutgoingSalary(int outgoingSalary) {
		this.outgoingSalary = outgoingSalary;
	}
	
}