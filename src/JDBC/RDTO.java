package JDBC;

import java.io.Serializable;

public class RDTO implements Serializable {
	private String room;
	private int people;
	private int price;

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

	public String[] getArray() {
		String[] data = new String[3];
		data[0] = this.room;
		data[1] = String.valueOf(this.people);
		data[2] = String.valueOf(this.price);

		return data;
	}

}
