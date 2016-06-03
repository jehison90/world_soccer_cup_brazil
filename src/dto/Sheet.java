package dto;

public class Sheet {

	private int id;
	
	private String color;
	
	public Sheet() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Sheet(int id, String color) {
		super();
		this.id = id;
		this.color = color;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}
	
	
	
}
