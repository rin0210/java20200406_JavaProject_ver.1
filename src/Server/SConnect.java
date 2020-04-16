package Server;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
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

	SConnect(Socket c, Socket c_1) {
		this.withClient = c;
		this.withClient_1 = c_1;
	}

	public void admin(ServerCenter sc) {
		this.sc = sc;
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
						sc.receive(withClient, msg);
					}

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}).start();

	}

	public void send(Socket wc, String msg) {
		try {
			if (this.withClient.equals(wc)) {
				sendMsg = withClient.getOutputStream();
				sendMsg.write(msg.getBytes());
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void sendObject(Socket wc, Object olist) {
		try {
			if (this.withClient.equals(wc)) {
				ByteArrayOutputStream bos = new ByteArrayOutputStream();
				ObjectOutputStream os = new ObjectOutputStream(bos);

				os.writeObject(olist);

				byte[] reBuffer = bos.toByteArray();

				sendMsg_1 = withClient_1.getOutputStream();
				sendMsg_1.write(reBuffer);

				System.out.println("sendMsg: " + olist);
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

}
