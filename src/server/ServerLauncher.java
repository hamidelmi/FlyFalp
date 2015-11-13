package server;

import impl.IGameServer;

import java.net.InetAddress;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class ServerLauncher {

	public ServerLauncher() {

	}

	public static void main(String[] args) {
		String ipAddress;
		int port;
		Registry registry;
		GameServer game;

		if (System.getSecurityManager() == null) {
			// System.setSecurityManager(new SecurityManager());
			try {

				game = new GameServer();
				ipAddress = (InetAddress.getLocalHost()).toString();
				port = 8118;

				System.out.println("Server starting... (" + ipAddress + ":"
						+ port + ")");

				registry = LocateRegistry.createRegistry(port);
				registry.rebind(IGameServer.bindName, game);

				System.out.println("JavaRMIServer ready.");
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}
}
