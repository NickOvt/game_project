package ee.taltech.iti0301.game;

import java.util.HashMap;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.BatchTiledMapRenderer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.esotericsoftware.kryonet.Client;

import box2dLight.RayHandler;
import ee.taltech.iti0301.game.Character.MoveDirection;
import ee.taltech.iti0301.game.Handlers.MovementHandler;
import ee.taltech.iti0301.game.Listeners.EnemyListener;
import ee.taltech.iti0301.game.Listeners.JoinListener;
import ee.taltech.iti0301.game.Listeners.MovementUpdateListener;
import ee.taltech.iti0301.game.Listeners.OtherPlayerLeaveListerner;
import ee.taltech.iti0301.game.Listeners.TimerListener;
import ee.taltech.iti0301.game.Messages.ConnectRequest;
import ee.taltech.iti0301.game.Messages.EnemyMovedMessage;
import ee.taltech.iti0301.game.Messages.MoveUpdateEvent;

public class GameScreen implements Screen {
    final Spooky game;
    OrthographicCamera camera;
    private final SpriteBatch batch;
    // initial
    Character character = new Character(new Vector2(1280 / 2, 720 / 2), "blondeGirlDesign");
    Character enemy = new Character(new Vector2(10000, 10000), new Texture(Gdx.files.internal("enemy/Monster4.png")));
    private final Client client;
    // HashMap<String, List<Integer>> otherPlayersWithData;
    HashMap<String, Character> otherPlayers;

    TiledMap tiledMap;
    BatchTiledMapRenderer tiledMapRenderer;

    MapLayer collisionObjectLayer;
    MapObjects collisionObjects;
    private final Hud hud;
    private Stage stage;
    private Stage taskStage;
    private Stage completeTaskStage;
    private Stage correctAnswerStage;
    private Stage incorrectAnswerStage;
    private Dialog taskDialog;
    private Dialog completeTaskDialog;
    private Dialog correctAnswer;
    private Dialog incorrectAnswer;
    private Skin skin;
    private TextButton taskTextButton;
    private TextButton answerButton1;
    private TextButton answerButton2;

    static World world = new World(new Vector2(0, 0), false);
    static RayHandler rayHandler = new RayHandler(world);

    float elapsed = 0;

    /**
     * Main game screen of the game
     * 
     * @param game
     */
    public GameScreen(final Spooky game) {
        this.game = game;
        this.otherPlayers = game.getPlayers();

        camera = new OrthographicCamera();

        camera.setToOrtho(false, 1280, 720);
        camera.update();
        tiledMap = new TmxMapLoader().load("./maps/classroom3.tmx");

        collisionObjectLayer = tiledMap.getLayers().get(2);
        collisionObjects = collisionObjectLayer.getObjects();

        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);

        batch = new SpriteBatch();
        this.client = game.getClient();
        hud = new Hud(batch);
        FitViewport viewport = new FitViewport(1280, 720, new OrthographicCamera());
        stage = new Stage(viewport, batch);

        client.addListener(new JoinListener(otherPlayers));
        client.addListener(new MovementUpdateListener(character, otherPlayers, camera));
        client.addListener(new OtherPlayerLeaveListerner(otherPlayers));
        client.addListener(new TimerListener(hud));
        client.addListener(new EnemyListener(enemy));

        ConnectRequest msg = new ConnectRequest(String.valueOf(new Random().nextInt(1000000)), character.bounds.x,
                character.bounds.y);
        client.sendTCP(msg);

        rayHandler.setCombinedMatrix(camera);
        rayHandler.setAmbientLight(0.0f);
        RayHandler.useDiffuseLight(false);
        RayHandler.setGammaCorrection(true);
        RayHandler.useDiffuseLight(true);

        rayHandler.setCulling(true);
        rayHandler.setBlur(true);
        rayHandler.setBlurNum(1);
        rayHandler.setShadows(true);
        skin = new Skin(Gdx.files.internal("./freezing/skin/freezing-ui.json"));

        taskStage = new Stage();
        completeTaskStage = new Stage();
        correctAnswerStage = new Stage();
        incorrectAnswerStage = new Stage();

    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        elapsed += delta;
        camera.update();
        tiledMapRenderer.setView(camera);
        tiledMapRenderer.render();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        for (RectangleMapObject rectangleObject : collisionObjects.getByType(RectangleMapObject.class)) {
            Rectangle rectangle = rectangleObject.getRectangle();
            if (Intersector.overlaps(rectangle, character.getBounds())) {
                System.out.println("COLLIDED!" + "x:" + rectangle.x + "y:" + rectangle.y);
                handleCollision();
                if (rectangle.x == 675.0 && rectangle.y == 720.0) {
                    task();
                }
                if (rectangle.x == 227.0 && rectangle.y == 706.0) {
                    completeTask();
                }
            }
        }

        detectInput();
        for (Character currPlayer : otherPlayers.values()) {
            if (currPlayer.animation != null) {
                currPlayer.render(batch, currPlayer.animation.getKeyFrame(elapsed, true));
            } else {
                currPlayer.render(batch);
            }
            currPlayer.getLight().setPosition(currPlayer.getBounds().x + (13.5f + currPlayer.getBounds().width / 2),
                    currPlayer.getBounds().y + (currPlayer.getBounds().height / 2));
        }

        if (character.animation != null) {
            character.render(batch, character.animation.getKeyFrame(elapsed, true));
        } else {
            character.render(batch);
        }

        enemy.render(batch);
        if (enemy.getBounds().x < 10000.0f && enemy.getBounds().y < 10000.0f) {
            client.sendTCP(new EnemyMovedMessage(enemy.getBounds().x, enemy.getBounds().y));
            if (Intersector.overlaps(character.getBounds(), enemy.getBounds())) {
                client.close();
                System.exit(1);
            }
        }

        batch.end();
        game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
        hud.stage.draw();
        hud.update(delta);
        taskStage.act();
        taskStage.draw();
        completeTaskStage.act();
        completeTaskStage.draw();
        correctAnswerStage.act();
        correctAnswerStage.draw();
        incorrectAnswerStage.act();
        incorrectAnswerStage.draw();
        character.getLight().setPosition(character.getBounds().x + (character.getBounds().width / 2),
                character.getBounds().y + (character.getBounds().height / 2));
        rayHandler.setCombinedMatrix(camera);
        rayHandler.updateAndRender();
    }

    private void task() {
        Gdx.input.setInputProcessor(taskStage);
        taskDialog = new Dialog("Riddle me this", skin);
        taskTextButton = new TextButton("exit", skin);
        taskDialog.button(taskTextButton);
        taskDialog.text("What month of the year has 28 days?");
        taskDialog.show(taskStage);
        taskTextButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                taskStage.clear();
            }
        });
    }

    private void completeTask() {
        Gdx.input.setInputProcessor(completeTaskStage);
        completeTaskDialog = new Dialog("Answer to determine your faith", skin);
        answerButton1 = new TextButton("February", skin);
        answerButton2 = new TextButton("All of them", skin);
        completeTaskDialog.button(answerButton1);
        completeTaskDialog.button(answerButton2);
        completeTaskDialog.show(completeTaskStage);

        answerButton1.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                inCorrectAnswer();
                completeTaskStage.clear();
            }
        });
        answerButton2.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                correctAnswer();
                completeTaskStage.clear();
            }
        });
    }

    public void correctAnswer() {
        Gdx.input.setInputProcessor(correctAnswerStage);
        correctAnswer = new Dialog(" ", skin);
        correctAnswer.text("You are safe for now...");
        taskTextButton = new TextButton("exit", skin);
        correctAnswer.button(taskTextButton);
        correctAnswer.show(correctAnswerStage);
        taskTextButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                correctAnswerStage.clear();
            }
        });
    }

    public void inCorrectAnswer() {
        Gdx.input.setInputProcessor(incorrectAnswerStage);
        incorrectAnswer = new Dialog(" ", skin);
        incorrectAnswer.text("so, you chose death...");
        taskTextButton = new TextButton("exit", skin);
        incorrectAnswer.button(taskTextButton);
        incorrectAnswer.show(incorrectAnswerStage);
        taskTextButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                incorrectAnswerStage.clear();
            }
        });

    }

    @Override
    public void resize(int width, int height) {
        camera.viewportWidth = width / 1.8f;
        camera.viewportHeight = height / 1.8f;
        camera.update();
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        world.dispose();
        rayHandler.dispose();
        tiledMap.dispose();
        tiledMapRenderer.dispose();
        // pointLight.dispose();
        rayHandler.dispose();
        batch.dispose();
        hud.dispose();
        stage.dispose();
        taskStage.dispose();
        completeTaskStage.dispose();
        correctAnswerStage.dispose();
        incorrectAnswerStage.dispose();
    }

    /**
     * Handle collision
     */
    private void handleCollision() {
        MoveUpdateEvent moveUpdateEvent = new MoveUpdateEvent();
        moveUpdateEvent.setNewX(-character.lastMoveX);
        moveUpdateEvent.setNewY(-character.lastMoveY);
        moveUpdateEvent.setMoveDirection(character.moveDirection);
        client.sendTCP(moveUpdateEvent);
        camera.translate(-character.lastMoveX, -character.lastMoveY);
    }

    /**
     * Detect keyboard input and move character if detected
     */
    private void detectInput() {

        if (Gdx.input.isKeyPressed(Input.Keys.UP) || Gdx.input.isKeyPressed(Input.Keys.W)) {
            float moveX = 0;
            float moveY = 2.5f;
            MoveDirection moveDirection = MoveDirection.UP;

            MovementHandler.moveCharacter(client, moveX, moveY, moveDirection);
        } else if (Gdx.input.isKeyPressed(Input.Keys.DOWN) || Gdx.input.isKeyPressed(Input.Keys.S)) {
            float moveX = 0;
            float moveY = -2.5f;
            MoveDirection moveDirection = MoveDirection.DOWN;

            MovementHandler.moveCharacter(client, moveX, moveY, moveDirection);
        } else if (Gdx.input.isKeyPressed(Input.Keys.LEFT) || (Gdx.input.isKeyPressed(Input.Keys.A))) {

            float moveX = -2.5f;
            float moveY = 0;
            MoveDirection moveDirection = MoveDirection.LEFT;

            MovementHandler.moveCharacter(client, moveX, moveY, moveDirection);
        } else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) || (Gdx.input.isKeyPressed(Input.Keys.D))) {

            float moveX = 2.5f;
            float moveY = 0;
            MoveDirection moveDirection = MoveDirection.RIGHT;

            MovementHandler.moveCharacter(client, moveX, moveY, moveDirection);
        } else {
            float moveX = 0;
            float moveY = 0;
            MoveDirection moveDirection = MoveDirection.IDLE;

            MovementHandler.moveCharacter(client, moveX, moveY, moveDirection);
        }
    }

}