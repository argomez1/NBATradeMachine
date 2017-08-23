public class Player{
	
	private String name;
	private int number;
	private int salary;
	private String team;
	
	public Player(String name, int num, String team, int salary){
		this.name = name;
		this.setNumber(num);
		this.team = team;
		this.salary = salary;
	}
	
	public String getName(){
		return name;
	}

	public int getNumber() {
		return number;
	}
	
	public String getTeam(){
		return team;
	}
	
	public int getSalary(){
		return salary;
	}

	public void setName(String name){
		this.name = name;
	}
	
	public void setNumber(int number) {
		this.number = number;
	}
	
	public void setTeam(String team){
		this.team = team;
	}
	
	public void setSalary(int salary){
		this.salary = salary;
	}
}