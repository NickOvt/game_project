package iti0301.backend.Handlers;

import java.util.LinkedList;
import java.util.Optional;

import com.esotericsoftware.kryonet.Connection;

import iti0301.backend.Models.Enemy;
import iti0301.backend.Models.ServersidePlayer;

public class PlayerHandler {
    public static final PlayerHandler INSTANCE = new PlayerHandler();
    private final LinkedList<ServersidePlayer> players;

    private Enemy enemy;

    private PlayerHandler() {
        this.players = new LinkedList<>();
    }

    public Optional<ServersidePlayer> getPlayerByConnection(final Connection connection) {
        for (final ServersidePlayer serversidePlayer : players) {
            if (serversidePlayer.getConnection() == connection) {
                return Optional.of(serversidePlayer);
            }
        }
        return Optional.empty();
    }

    public Optional<ServersidePlayer> getPlayerByUsername(final String username) {
        for (final ServersidePlayer serversidePlayer : players) {
            if (serversidePlayer.getUsername().equals(username)) {
                return Optional.of(serversidePlayer);
            }
        }
        return Optional.empty();
    }

    public void addPlayer(final ServersidePlayer serversidePlayer) {
        players.add(serversidePlayer);
    }

    public void removePlayer(final ServersidePlayer serversidePlayer) {
        players.remove(serversidePlayer);
    }

    public LinkedList<ServersidePlayer> getPlayers() {
        return this.players;
    }

    public void setEnemy(float x, float y) {
        this.enemy = new Enemy(x, y, 0.25f);
    }

    public void setEnemy(float x, float y, float speed) {
        this.enemy = new Enemy(x, y, speed);
    }

    public Enemy getEnemy() {
        return enemy;
    }
}
