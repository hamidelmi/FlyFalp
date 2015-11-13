package server;

import impl.*;

import java.util.*;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * Coordinate central logic of the game and communicate with all of the players
 * 
 * @author hamidelmi
 *
 */
@SuppressWarnings("serial")
public class GameServer extends UnicastRemoteObject implements IGameServer {
	private int x, y;
	private HashMap<Player, IGameClient> players;
	private static Random rand = new Random();

	/**
	 * Constructor
	 * 
	 * @throws RemoteException
	 */
	public GameServer() throws RemoteException {
		players = new HashMap<Player, IGameClient>();
		setNewPoint();
	}

	private static int randInt(int min, int max) {
		return rand.nextInt((max - min) + 1) + min;
	}

	private void setNewPoint() {
		x = randInt(0, 100);
		y = randInt(0, 100);

		notifyPlayers(x, y);
	}

	private void notifyRemovePlayer(String playerName) {
		Iterator<Map.Entry<Player, IGameClient>> it = players.entrySet()
				.iterator();
		while (it.hasNext()) {
			Map.Entry<Player, IGameClient> pair = it.next();
			try {
				pair.getValue().receiveFlyHunted(playerName, -1);
			} catch (RemoteException ex) {

			}
		}
	}

	private void notifyPlayers(Player update) {
		Iterator<Map.Entry<Player, IGameClient>> it = players.entrySet()
				.iterator();
		while (it.hasNext()) {
			Map.Entry<Player, IGameClient> pair = it.next();
			try {
				pair.getValue().receiveFlyHunted(update.getName(),
						update.getScore());
			} catch (RemoteException ex) {

			}
		}
	}

	private void notifyPlayers(int x, int y) {
		Iterator<Map.Entry<Player, IGameClient>> it = players.entrySet()
				.iterator();
		while (it.hasNext()) {
			Map.Entry<Player, IGameClient> pair = it.next();
			try {
				pair.getValue().receiveFlyPosition(x, y);
			} catch (RemoteException ex) {

			}
		}
	}

	private Map.Entry<Player, IGameClient> findByName(String playerName) {
		Iterator<Map.Entry<Player, IGameClient>> it = players.entrySet()
				.iterator();
		while (it.hasNext()) {
			Map.Entry<Player, IGameClient> pair = it.next();
			if (pair.getKey().getName().equals(playerName))
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

			// Let the new player knows about the current
			// position of the fly
			client.receiveFlyPosition(x, y);
			Iterator<Map.Entry<Player, IGameClient>> it = players.entrySet()
					.iterator();
			while (it.hasNext()) {
				Player p = it.next().getKey();
				client.receiveFlyHunted(p.getName(), p.getScore());
			}
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
			notifyRemovePlayer(player.getName());
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

			setNewPoint();
		}
	}
}
