package ee.taltech.iti0301.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

public class Hud implements Disposable {

    public Stage stage;
    private Viewport viewport;
    private Integer worldTimer;
    private boolean timeUp;
    private static Integer score;
    private Label countDownLabel;
    private static Label scoreLabel;
    private Label timeLabel;
    Sound sound = Gdx.audio.newSound(Gdx.files.internal("Horror-creepy-clock-ticking-sound-effect.mp3"));

    public void addTimer() {

    }

    public void setCurrentTimerValue(int currentTimerValue) {
        this.worldTimer = currentTimerValue;
    }

    public Hud(SpriteBatch spriteBatch) {
        score = 0;

        viewport = new FitViewport(1280, 720, new OrthographicCamera());
        stage = new Stage(viewport, spriteBatch);

        Table table = new Table();

        countDownLabel = new Label(String.format("%03d", worldTimer),
                new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        scoreLabel = new Label(String.format("%6d", score), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        timeLabel = new Label("TIME", new Label.LabelStyle(new BitmapFont(), Color.WHITE));

        table.add(timeLabel).padTop(10).padRight(100);
        table.row();
        table.add(countDownLabel).padRight(100);

        table.top().right();
        table.setFillParent(true);
        stage.addActor(table);

    }

    private boolean isAudioPlaying = false;

    public void update(float dt) {
        if (worldTimer != null) {
            if (worldTimer != 0 && worldTimer < 20 && !isAudioPlaying) {
                sound.play();
                isAudioPlaying = true;
            }
            if (worldTimer == 0) {
                sound.stop();
                timeUp = true;
            }
            countDownLabel.setText(String.format("%03d", worldTimer));
        }
    }

    public static void addScore(int value) {
        score += value;
        scoreLabel.setText(String.format("%06d", score));
    }

    @Override
    public void dispose() {
        stage.dispose();
        sound.dispose();

    }

    public boolean isTimeUp() {
        return timeUp;
    }
}
