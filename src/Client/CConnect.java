package Client;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import Swing.HMain;

public class CConnect {
	private Socket withServer = null;
	private InputStream reMsg = null;
	private OutputStream sendMsg = null;
	private String id = null;
	private String pwd = null;
	private HMain hm = null;

	public CConnect(Socket s) {
		this.withServer = s;
		hm = new HMain(this);
		receive();
	}

	public void receive() {
		System.out.println("CConnect¿Ô´Ï?");
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
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				try {
					sendMsg = withServer.getOutputStream();
					sendMsg.write(m.getBytes());
					
					reMsg = withServer.getInputStream();
					byte[] reBuffer = new byte[100];
					reMsg.read(reBuffer);
					String msg = new String(reBuffer);
					msg = msg.trim();
					
					System.out.println(msg);
					
				} catch (Exception e) {
				}
				
			}
		}).start();
	}

}
