package iti0301.backend.Models;

import iti0301.backend.Handlers.PlayerHandler;

public class Enemy {
    private float x;
    private float y;
    private float speed = 0.25f;

    public Enemy() {
    }

    public Enemy(float x, float y, float speed) {
        this.x = x;
        this.y = y;
        this.speed = speed;
    }

    public float getSpeed() {
        return this.speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public float getX() {
        return this.x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return this.y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public ServersidePlayer findClosestPlayer() {
        double minDistance = 100000;
        ServersidePlayer serversidePlayer = null;

        for (ServersidePlayer player : PlayerHandler.INSTANCE.getPlayers()) {
            double distance = Math.sqrt((player.getX() - this.getX()) * (player.getX() - this.getX())
                    + (player.getY() - this.getY()) * (player.getY() - this.getY()));

            if (distance < minDistance) {
                minDistance = distance;
                serversidePlayer = player;
            }
        }

        return serversidePlayer;
    }

}
