package ee.taltech.iti0301.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.FrameworkMessage.KeepAlive;

import ee.taltech.iti0301.game.Character;
import ee.taltech.iti0301.game.Spooky;
import ee.taltech.iti0301.game.Character.MoveDirection;
import ee.taltech.iti0301.game.Messages.ConnectRequest;
import ee.taltech.iti0301.game.Messages.ConnectResponse;
import ee.taltech.iti0301.game.Messages.DisconnectMessage;
import ee.taltech.iti0301.game.Messages.EnemyMovedMessage;
import ee.taltech.iti0301.game.Messages.EnemySpawnedMessage;
import ee.taltech.iti0301.game.Messages.GetAllOtherPlayersMessage;
import ee.taltech.iti0301.game.Messages.MoveUpdateEvent;
import ee.taltech.iti0301.game.Messages.OtherPlayerConnectedMessage;
import ee.taltech.iti0301.game.Messages.OtherPlayerMovedMessage;
import ee.taltech.iti0301.game.Messages.TimerUpdateMessage;
import ee.taltech.iti0301.game.Messages.ConnectResponse.Type;
import ee.taltech.iti0301.game.Models.Player;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class DesktopLauncher {
	public static void main(String[] arg) throws IOException, InterruptedException {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Mortician";
		config.width = 1280;
		config.height = 720;

		Client client = new Client();
		client.getKryo().register(ConnectRequest.class);
		client.getKryo().register(ConnectResponse.class);
		client.getKryo().register(MoveUpdateEvent.class);
		client.getKryo().register(DisconnectMessage.class);
		client.getKryo().register(GetAllOtherPlayersMessage.class);
		client.getKryo().register(OtherPlayerConnectedMessage.class);
		client.getKryo().register(OtherPlayerMovedMessage.class);
		client.getKryo().register(EnemySpawnedMessage.class);
		client.getKryo().register(TimerUpdateMessage.class);
		client.getKryo().register(EnemyMovedMessage.class);
		client.getKryo().register(MoveDirection.class);
		client.getKryo().register(ArrayList.class);
		client.getKryo().register(KeepAlive.class);
		client.getKryo().register(Player.class);
		client.getKryo().register(Type.class);

		client.start();

		// client.connect(6000, "193.40.156.105", 8085, 8086);
		client.connect(6000, "localhost", 8085, 8086);

		HashMap<String, Character> players = new HashMap<>();

		new LwjglApplication(new Spooky(client, players), config);
	}
}
