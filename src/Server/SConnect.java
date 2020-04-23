package Server;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import JDBC.RDTO;

public class SConnect extends Thread {
	private Socket withClient = null;
	private Socket withClient_1 = null;
	private InputStream reMsg = null;
	private OutputStream sendMsg = null;
	private InputStream reMsg_1 = null;
	private OutputStream sendMsg_1 = null;
	private String id = null;
	private ServerCenter sc = null;
	private ServerSocket serverS_1 = null;

	SConnect(Socket c, ServerSocket serverS_1, int S_1Port, ServerCenter sc) {
		this.withClient = c;
		this.serverS_1 = serverS_1;
		sendPort(String.valueOf(S_1Port));
		this.sc = sc;
	}

	public void sendPort(String S_1Port) {
		System.out.println("S_1Port: " + S_1Port);
		
		try {
			sendMsg = withClient.getOutputStream();
			sendMsg.write(S_1Port.getBytes());
			withClient_1 = serverS_1.accept();
			System.out.println("withClient_1 Á¢¼ÓµÊ");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		receive();
	}

	public void receive() {
		new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					while (true) {

						reMsg = withClient.getInputStream();
						byte[] reBuffer = new byte[100];

						reMsg.read(reBuffer);
						String msg = new String(reBuffer);
						msg = msg.trim();

						System.out.println(msg);
						sc.receive(withClient, msg, id);
					}

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}).start();
	}

	public void send(Socket wc, String msg) {
		System.out.println("SConnect send msg: " + msg);
		try {
			if (this.withClient.equals(wc)) {
				sendMsg = withClient.getOutputStream();
				sendMsg.write(msg.getBytes());
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void sendObject(Socket wc, Object o) {
		try {
			if (this.withClient.equals(wc)) {
				ByteArrayOutputStream bos = new ByteArrayOutputStream();
				ObjectOutputStream os = new ObjectOutputStream(bos);

				os.writeObject(o);

				byte[] reBuffer = bos.toByteArray();

				sendMsg_1 = withClient_1.getOutputStream();
				sendMsg_1.write(reBuffer);

				System.out.println("sendMsg: " + o);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void streamSet(Socket wc, String id) {
		this.id = id;
		System.out.println(id + "´Ô ·Î±×ÀÎ");

		try {
			if (this.withClient.equals(wc)) {
				sendMsg = withClient.getOutputStream();
				sendMsg.write(id.getBytes());
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void logout(Socket wc, String msg) {
		this.id = null;
		System.out.println(id + "´Ô ·Î±×¾Æ¿ô");

		try {
			if (this.withClient.equals(wc)) {
				sendMsg = withClient.getOutputStream();
				sendMsg.write(msg.getBytes());
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
