package Client;

import java.net.Socket;

public class CMain {

	public static void main(String[] args) throws Exception {
		Socket withServer = null;
		Socket withServer_1 = null;

		withServer = new Socket("10.0.0.77", 9988);
		withServer_1 = new Socket("10.0.0.77", 9977);

		new CConnect(withServer, withServer_1);
	}

}
