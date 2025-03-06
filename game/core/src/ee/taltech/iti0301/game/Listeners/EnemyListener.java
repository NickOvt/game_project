package ee.taltech.iti0301.game.Listeners;

import com.badlogic.gdx.Gdx;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

import ee.taltech.iti0301.game.Character;
import ee.taltech.iti0301.game.Messages.EnemyMovedMessage;
import ee.taltech.iti0301.game.Messages.EnemySpawnedMessage;

public class EnemyListener extends Listener {
    private Character enemy;

    public EnemyListener(Character enemy) {
        this.enemy = enemy;
    }

    @Override
    public void received(Connection connection, Object object) {
        if (object instanceof EnemySpawnedMessage) {
            final EnemySpawnedMessage enemySpawnedMessage = (EnemySpawnedMessage) object;

            Gdx.app.postRunnable(new Runnable() {
                public void run() {
                        enemy.moveToNewPosition(enemySpawnedMessage.getX(), enemySpawnedMessage.getY());
                        System.out.println("ENEMY SPAWNED!");
                }
            });
        }
        else if (object instanceof EnemyMovedMessage) {
            final EnemyMovedMessage enemyMovedMessage = (EnemyMovedMessage) object;

            // System.out.println("GOT ENEMY MOVED!");

            Gdx.app.postRunnable(new Runnable() {
                public void run() {
                        if (enemyMovedMessage.getX() != enemy.getBounds().x && enemyMovedMessage.getY() != enemy.getBounds().y) {
                            enemy.moveToNewPosition(enemyMovedMessage.getX(), enemyMovedMessage.getY());
                        }
                }
            });
        }
    }
}
