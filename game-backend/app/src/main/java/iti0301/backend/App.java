package iti0301.backend;

import iti0301.backend.Server.ServerConnection;

public class App {
    public static void main(String[] args) {
        ServerConnection server = new ServerConnection(8085, 8086);
    }
}
