package Server;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class SConnect extends Thread {
	private Socket withClient;
	private InputStream reMsg;
	private OutputStream sendMsg;
	private String id;
	private ServerCenter sc;

	SConnect(Socket c) {
		this.withClient = c;
	}

	public void admin(ServerCenter sc) {
		this.sc = sc;
	}

	@Override
	public void run() {
//		streamSet();
		receive();
	}

	public void receive() {
		new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					System.out.println("receive start~~");

					while (true) {
						reMsg = withClient.getInputStream();
						byte[] reBuffer = new byte[100];

						reMsg.read(reBuffer);
						String msg = new String(reBuffer);
						msg = msg.trim();

						System.out.println(msg);
						sc.receive(withClient, msg);
					}
				} catch (Exception e) {
				}
			}
		}).start();
	}

//	private void streamSet() {
//		try {
//			reMsg = withClient.getInputStream();
//			byte[] reBuffer = new byte[100];
//			reMsg.read(reBuffer);
//			id = new String(reBuffer);
//			id = id.trim();
//
//			InetAddress c_ip = withClient.getInetAddress();
//			String ip = c_ip.getHostAddress();
//
//			System.out.println(id + "님 로그인");
//
//			String reMsg = "정상접속되었습니다.";
//			sendMsg = withClient.getOutputStream();
//			sendMsg.write(reMsg.getBytes());
//
//		} catch (Exception e) {
//		}
//	}

	public void send(Socket wc, String msg) {
//		System.out.println("SConnect왔니?");
		try {
			if (this.withClient.equals(wc)) {
				sendMsg = withClient.getOutputStream();
				sendMsg.write(msg.getBytes());
			}

		} catch (Exception e) {
		}
	}

	public void streamSet(Socket wc, String id) {
		System.out.println(id + "님 로그인");
		try {
			if (this.withClient.equals(wc)) {
				sendMsg = withClient.getOutputStream();
				sendMsg.write(id.getBytes());
			}

		} catch (Exception e) {
		}

	}
}
