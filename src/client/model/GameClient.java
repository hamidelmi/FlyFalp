package client.model;

import java.io.Serializable;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import impl.*;

public class GameClient extends UnicastRemoteObject implements IGameClient {
	private IGameServer gameServer;
	private IGameClient handler;

	public GameClient(IGameClient handler) throws Exception {
		this.handler = handler;
		String ipAddress = "127.0.0.1";
		int port = 8118;
		Registry registry = LocateRegistry.getRegistry(ipAddress, port);
		gameServer = (IGameServer) registry.lookup(IGameServer.bindName);
	}

	@Override
	public void receiveFlyHunted(String playerName, int newPoints)
			throws RemoteException {
		System.out.println("receiveFlyHunted: (" + playerName + "," + newPoints
				+ ")");
		if (handler != null)
			handler.receiveFlyHunted(playerName, newPoints);
	}

	@Override
	public void receiveFlyPosition(int x, int y) throws RemoteException {
		System.out.println("receiveFlyPosition: (" + x + "," + y + ")");

		if (handler != null)
			handler.receiveFlyPosition(x, y);
	}

	/**
	 * Call method on the server to let it know that a new player logged in
	 * 
	 * @param username
	 * @throws RemoteException
	 */
	public void login(String username) throws RemoteException {
		gameServer.login(username, this);
	}

	/**
	 * Call method on the server to let it know that this player has just hunted
	 * a fly
	 * 
	 * @param playerName
	 * @throws RemoteException
	 */
	public void huntFly(String playerName) throws RemoteException {
		gameServer.huntFly(playerName);
	}

	/**
	 * Call method on the server to let it know that this player has just logged
	 * out
	 * 
	 * @param playerName
	 * @throws RemoteException
	 */
	public void logout(String playerName) throws RemoteException {
		gameServer.logout(playerName);
	}

}
