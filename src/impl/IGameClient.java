package impl;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * @author hamidelmi
 * Callback interface 
 */
public interface IGameClient extends Remote {
	/**
	 * Will be called when someone hunt the fly
	 * @param playerName
	 * @param newPoints
	 * @throws RemoteException
	 */
	void receiveFlyHunted(String playerName, int newPoints)
			throws RemoteException;

	/**
	 * Will be called whenever the position of fly changes
	 * @param x
	 * @param y
	 * @throws RemoteException
	 */
	void receiveFlyPosition(int x, int y) throws RemoteException;

}
