package Server;

import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

public class SMain {

	public static void main(String[] args) throws Exception {
		ServerSocket serverS = null;
		Socket withClient = null;

		serverS = new ServerSocket();
		serverS.bind(new InetSocketAddress("10.0.0.77", 8888));

		ServerSocket serverS_1 = null;

//		Random r = new Random();
//		int S_1Port = r.nextInt(900) + 9000;
//		System.out.println(S_1Port);
//
//		serverS_1 = new ServerSocket();
//		serverS_1.bind(new InetSocketAddress("10.0.0.77", S_1Port));

		ServerCenter sc = new ServerCenter();

		while (true) {
			System.out.println("서버 대기중");
			withClient = serverS.accept();
			
			Random r = new Random();
			int S_1Port = r.nextInt(900) + 9000;
			System.out.println(S_1Port);

			serverS_1 = new ServerSocket();
			serverS_1.bind(new InetSocketAddress("10.0.0.77", S_1Port));

			System.out.println("withClient 접속 됨");
			SConnect s = new SConnect(withClient, serverS_1, S_1Port, sc);
			sc.addList(s);
			s.start();

		}
	}

}
