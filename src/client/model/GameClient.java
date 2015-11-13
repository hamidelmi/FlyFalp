package client.model;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import impl.*;

public class GameClient implements Serializable, IGameClient {

	@Override
	public void receiveFlyHunted(String playerName, int newPoints)
			throws RemoteException {
	}

	@Override
	public void receiveFlyPosition(int x, int y) throws RemoteException {

	}

	public void login(String username) throws Exception {
		String ipAddress = "127.0.0.1";
		int port = 8118;
		Registry registry = LocateRegistry.getRegistry(ipAddress, port);
		IGameServer gameServer = (IGameServer) registry
				.lookup(IGameServer.bindName);

		gameServer.login(username, this);
	}
}
