package server;

import impl.*;

import java.util.*;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class GameServer extends UnicastRemoteObject implements IGameServer {

	HashMap<Player, IGameClient> players;

	public GameServer() throws RemoteException {
		players = new HashMap<Player, IGameClient>();
	}

	private void notifyRemovePlayer(Player update) {

	}

	private void notifyPlayers(Player update) {
		Iterator it = players.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<Player, IGameClient> pair = (Map.Entry<Player, IGameClient>) it
					.next();
			try {
				pair.getValue().receiveFlyHunted(update.getName(),
						update.getRemoveMark() ? -1 : update.getScore());
			} catch (RemoteException ex) {

			}
		}
	}

	private Map.Entry<Player, IGameClient> findByName(String playerName) {
		Iterator it = players.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<Player, IGameClient> pair = (Map.Entry<Player, IGameClient>) it
					.next();
			if (pair.getKey().getName() == playerName)
				return pair;
		}

		return null;
	}

	@Override
	public void login(String playerName, IGameClient client)
			throws RemoteException {
		System.out.println("Login request: " + playerName);

		if (findByName(playerName) == null) {
			Player newPlayer = new Player(playerName);
			players.put(newPlayer, client);
			notifyPlayers(newPlayer);
		} else
			throw new RemoteException("Doublicate player name!");
	}

	@Override
	public void logout(String playerName) throws RemoteException {
		System.out.println("Logout request: " + playerName);
		
		Map.Entry<Player, IGameClient> item = findByName(playerName);

		if (item == null)
			throw new RemoteException("No such player exists!");
		else {
			Player player = item.getKey();

			players.remove(player);
		}
	}

	@Override
	public void huntFly(String playerName) throws RemoteException {
		System.out.println("HuntFly request: " + playerName);
		
		Map.Entry<Player, IGameClient> item = findByName(playerName);

		if (item == null)
			throw new RemoteException("No such player exists!");
		else {
			Player player = item.getKey();
			player.increaseScore();
			notifyPlayers(player);
		}
	}

}
