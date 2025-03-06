package iti0301.backend.Server;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

import iti0301.backend.Messages.ConnectRequest;
import iti0301.backend.Messages.ConnectResponse;
import iti0301.backend.Messages.DisconnectMessage;
import iti0301.backend.Messages.EnemyMovedMessage;
import iti0301.backend.Messages.EnemySpawnedMessage;
import iti0301.backend.Messages.GetAllOtherPlayersMessage;
import iti0301.backend.Messages.MoveUpdateEvent;
import iti0301.backend.Messages.OtherPlayerConnectedMessage;
import iti0301.backend.Messages.OtherPlayerMovedMessage;
import iti0301.backend.Messages.TimerUpdateMessage;
import iti0301.backend.Messages.ConnectResponse.Type;
import iti0301.backend.Models.Player;
import iti0301.backend.Models.ServersidePlayer.MoveDirection;
import iti0301.backend.Listeners.EnemyMovementListener;
import iti0301.backend.Listeners.JoinListener;
import iti0301.backend.Listeners.LeaveListener;
import iti0301.backend.Listeners.MoveListener;

public class ServerConnection extends Server {
    public ServerConnection(int tcpPort, int udpPort) {
        try {
            this.getKryo().register(ConnectRequest.class);
            this.getKryo().register(ConnectResponse.class);
            this.getKryo().register(MoveUpdateEvent.class);
            this.getKryo().register(DisconnectMessage.class);
            this.getKryo().register(GetAllOtherPlayersMessage.class);
            this.getKryo().register(OtherPlayerConnectedMessage.class);
            this.getKryo().register(OtherPlayerMovedMessage.class);
            this.getKryo().register(EnemySpawnedMessage.class);
            this.getKryo().register(TimerUpdateMessage.class);
            this.getKryo().register(EnemyMovedMessage.class);
            this.getKryo().register(MoveDirection.class);
            this.getKryo().register(ArrayList.class);
            this.getKryo().register(Player.class);
            this.getKryo().register(Type.class);

            this.addListener(new JoinListener());
            this.addListener(new LeaveListener());
            this.addListener(new MoveListener());
            this.addListener(new EnemyMovementListener());

            this.addListener(new Listener() {
                @Override
                public void connected(Connection connection) {
                    System.out.println(
                            "New connection! Connected ID: " + connection.getID() + " date: " + LocalDateTime.now());
                }
            });

            this.start();

            this.bind(tcpPort, udpPort);
            System.out.print("Server started on tcp port: " + tcpPort + " and udp port: " + udpPort
                    + " | server start date: " + LocalDateTime.now());
        } catch (IOException e) {
            System.out.println("Could not start the server!\n err: " + e);
            System.exit(0);
        }
    }
}
