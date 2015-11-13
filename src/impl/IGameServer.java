package impl;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * @author hamidelmi
 *
 *         Server interface
 */
public interface IGameServer extends Remote {
	/**
	 * Constant name for registration and lookup
	 */
	public static String bindName = "FlyFlapServer";

	void login(String playerName, IGameClient client) throws RemoteException;

	void logout(String playerName) throws RemoteException;

	void huntFly(String playerName) throws RemoteException;
}
