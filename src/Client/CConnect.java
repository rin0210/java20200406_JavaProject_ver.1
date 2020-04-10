package Client;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import Swing.HMain;

public class CConnect {
	private Socket withServer = null;
	private InputStream reMsg = null;
	private OutputStream sendMsg = null;
	private String id = null;
	private HMain hm = null;

	public CConnect(Socket s) {
		this.withServer = s;
		hm = HMain.getInstance();
		hm.admin(this);
		receive();
	}

	public void receive() {
		new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					System.out.println("receive start~~");

					while (true) {
						reMsg = withServer.getInputStream();
						byte[] reBuffer = new byte[100];
						reMsg.read(reBuffer);
						String msg = new String(reBuffer);
						msg = msg.trim();
						System.out.println(msg);

						hm.setMsg(msg);
					}
				} catch (Exception e) {
				}

			}
		}).start();
	}

	public void send(String m) {
		try {
			sendMsg = withServer.getOutputStream();
			sendMsg.write(m.getBytes());

		} catch (Exception e) {
		}
	}

//	public void streamSet(String id) {
//		try {
//			reMsg = withServer.getInputStream();
//			byte[] reBuffer = new byte[100];
//			reMsg.read(reBuffer);
//			id = new String(reBuffer);
//			id = id.trim();
//			this.id = id;
//			System.out.println(id + "님 정상접속되었습니다.");
//
//		} catch (Exception e) {
//		}
//	}

}
