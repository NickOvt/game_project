package iti0301.backend.Listeners;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

import iti0301.backend.Handlers.PlayerHandler;
import iti0301.backend.Helpers.ConnectionHelpers;
import iti0301.backend.Messages.EnemyMovedMessage;
import iti0301.backend.Models.Enemy;
import iti0301.backend.Models.ServersidePlayer;

public class EnemyMovementListener extends Listener {
    @Override
    public void received(Connection connection, Object object) {
        if (object instanceof EnemyMovedMessage) {
            /**
             * Get the message from the client, that means we need to update the enemy
             * pos. Find the nearest player and move enemy to that location by specified
             * enemy speed (on the backend) and then broadcast to all clients
             */

            Enemy enemy = PlayerHandler.INSTANCE.getEnemy();
            // System.out.println("enemy > " + enemy);
            if (enemy != null) {
                ServersidePlayer serversidePlayer = enemy.findClosestPlayer();
                // System.out.println("Closest player > " + serversidePlayer);
                if (serversidePlayer != null) {
                    // get closest player x and y
                    // construct a vector to that pos from the current enemy pos
                    // normalize the vector
                    // move by speed

                    float playerX = serversidePlayer.getX();
                    float playerY = serversidePlayer.getY();

                    float[] vector = { playerX - enemy.getX(), playerY - enemy.getY() };
                    float vectorLength = (float) Math.sqrt((vector[0] * vector[0]) + (vector[1] * vector[1]));

                    // System.out.println("VectorLength > " + vectorLength);

                    if (vectorLength != 0.0) {
                        float[] normalizedVector = { vector[0] / vectorLength, vector[1] / vectorLength };

                        enemy.setX(enemy.getX() + normalizedVector[0] * enemy.getSpeed());
                        enemy.setY(enemy.getY() + normalizedVector[1] * enemy.getSpeed());

                        ConnectionHelpers.sendMessageToAllPlayers(new EnemyMovedMessage(enemy.getX(), enemy.getY()));
                    }
                }
            }
        }
    }
}
