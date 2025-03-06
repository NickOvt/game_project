package ee.taltech.iti0301.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;

public class GameScreen implements Screen {
    final Spooky game;
    Texture backgroundImage;
    OrthographicCamera camera;

    public GameScreen(final Spooky game) {
        this.game = game;
        backgroundImage = new Texture(Gdx.files.internal("spookyBackground.jpg"));

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 1380, 820);


    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        game.batch.draw(backgroundImage, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        game.batch.end();



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
        backgroundImage.dispose();
    }

}
