package model.other;

public class PlayerSave implements Comparable<PlayerSave>{
	
	private String name;
	private int point;
	
	public PlayerSave(String name, int score) {
		this.name = name;
		this.point = score;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPoint() {
		return point;
	}

	public void setPoint(int score) {
		this.point = score;
	}

	@Override
	public int compareTo(PlayerSave p) {
		if (p.getPoint() > this.point) {
			return 1;
		} else if (p.getPoint() < this.point) {
			return -1;
		} else
			return 0;
	}

	public String infoofData() {
		return this.name+";"+this.point;
	}
	
	
	

}
