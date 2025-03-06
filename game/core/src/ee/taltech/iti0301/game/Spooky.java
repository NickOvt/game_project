package ee.taltech.iti0301.game;

import java.util.HashMap;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.esotericsoftware.kryonet.Client;

public class Spooky extends Game {

	public SpriteBatch batch;
	public BitmapFont font;
	public Music music;
	private Client client;
	HashMap<String, Character> players;

	/**
	 * Main game class
	 */
	public void create() {

		music = Gdx.audio.newMusic(Gdx.files.internal("2020-06-25_-_Dark_Shadows_-_www.FesliyanStudios.com_David_Fesliyan.mp3"));
		Sound sound = Gdx.audio.newSound(Gdx.files.internal("Horror-creepy-clock-ticking-sound-effect.mp3"));

		batch = new SpriteBatch();
		font = new BitmapFont();

		this.setScreen(new MainMenuScreen(this));
		music.setLooping(true);
		music.play();

	}

	public Spooky(Client client, HashMap<String, Character> players) {
		this.client = client;
		this.players = players;
	}

	public Client getClient() {
		return client;
	}

	public void render() {
		super.render();

	}

	public HashMap<String, Character> getPlayers() {
		return players;
	}

	public void dispose() {
		batch.dispose();
		font.dispose();
		music.dispose();
	}
}
