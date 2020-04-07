package Server;

import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class SMain {

	public static void main(String[] args) throws Exception {
		ServerSocket serverS = null;
		Socket withClient = null;

		serverS = new ServerSocket();
		serverS.bind(new InetSocketAddress("10.0.0.77", 9999));
		
		ServerCenter sc = new ServerCenter();

		while (true) {
			System.out.println("서버 대기중");
			withClient = serverS.accept();

			System.out.println("클라이언트 접속 됨");
			SConnect s = new SConnect(withClient);
			s.admin(sc);
			sc.addList(s);
			s.start();

		}
	}

}
