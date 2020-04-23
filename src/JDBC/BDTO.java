package JDBC;

import java.io.Serializable;

public class BDTO implements Serializable {
	private String reNum;
	private String id;
	private String name;
	private String room;
	private int people;
	private String chkIn;
	private String chkOut;
	private int night;
	private int price;

	public String getReNum() {
		return reNum;
	}

	public void setReNum(String reNum) {
		this.reNum = reNum;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getNight() {
		return night;
	}

	public void setNight(int night) {
		this.night = night;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

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
