package ee.taltech.iti0301.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;

public class MainMenuScreen  implements Screen {

    final Spooky game;
    Texture menuScreen;
    OrthographicCamera camera;

    public MainMenuScreen(final Spooky game) {
        this.game = game;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 1280, 820);
        menuScreen = new Texture(Gdx.files.internal("spooky.jpg"));
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        game.batch.draw(menuScreen, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        game.batch.end();

        if (Gdx.input.isTouched()) {
            game.setScreen(new GameScreen(game));
            dispose();
        }
    }

    @Override
    public void resize(int width, int height) {

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
        menuScreen.dispose();

    }
}
