package JDBC;

public class RDTO {
	private String id;
	private String room;
	private int people;
	private int price;
	private String chkIn;
	private String chkOut;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRoom() {
		return room;
	}

	public void setRoom(String room) {
		this.room = room;
	}

	public int getPeople() {
		return people;
	}

	public void setPeople(int people) {
		this.people = people;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public String getChkIn() {
		return chkIn;
	}

	public void setChkIn(String chkIn) {
		this.chkIn = chkIn;
	}

	public String getChkOut() {
		return chkOut;
	}

	public void setChkOut(String chkOut) {
		this.chkOut = chkOut;
	}

}
