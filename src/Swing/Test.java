package Swing;

import java.util.Random;

public class Test {
	Random r = new Random();

	public Test() {
		
		int a = r.nextInt(900)+9000;
		System.out.println(a);

	}

	public static void main(String[] args) {
		new Test();
	}

}
