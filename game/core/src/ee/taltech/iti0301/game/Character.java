package ee.taltech.iti0301.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import box2dLight.PointLight;

public class Character {

    protected float movementSpeed;
    protected int health;
    private boolean isColliding = false;

    protected Rectangle bounds = new Rectangle();
    private Texture characterTexture1;

    public float oldX;
    public float oldY;

    public float lastMoveX = 0;
    public float lastMoveY = 0;

    public MoveDirection moveDirection = MoveDirection.UP;

    public enum MoveDirection {
        UP, DOWN, LEFT, RIGHT, IDLE
    }

    private PointLight pointLight = new PointLight(GameScreen.rayHandler, 100, Color.valueOf("#fdf3c6"), 500.0F,
            0,
            0);

    Animation<Texture> animation;

    Texture[] animationFramesFront = new Texture[4];
    Texture[] animationFramesBack = new Texture[4];
    Texture[] animationFramesLeft = new Texture[4];
    Texture[] animationFramesRight = new Texture[4];

    /**
     * Load textures from specified folder with given path into given texture array
     * with the given iterations
     * 
     * @param path
     * @param arr
     * @param iterations
     */
    private void loadTexturesFromFolder(String path, Texture[] arr, int iterations) {
        FileHandle[] texs = Gdx.files.internal(path).list();

        for (int i = 0; i < iterations; i++) {
            Texture tex = new Texture(texs[i]);
            arr[i] = tex;
        }

        arr[3] = new Texture(texs[1]);
    }

    /**
     * Character constructor with specified position as a Vector2
     * @param position
     */
    public Character(Vector2 position) {
        this.bounds.x = position.x;
        this.bounds.y = position.y;
        oldX = position.x;
        oldY = position.y;

        loadTexturesFromFolder("./blondeGirlDesign/frontAnim/", animationFramesFront, 3);
        loadTexturesFromFolder("./blondeGirlDesign/backAnim/", animationFramesBack, 3);
        loadTexturesFromFolder("./blondeGirlDesign/leftAnim/", animationFramesLeft, 3);
        loadTexturesFromFolder("./blondeGirlDesign/rightAnim/", animationFramesRight, 3);

        this.characterTexture1 = animationFramesBack[0];

        this.bounds.height = 96;
        this.bounds.width = 76;

        pointLight = new PointLight(GameScreen.rayHandler, 100, Color.valueOf("#fdf3c6"), 500.0F,
                this.bounds.x + this.bounds.width / 2,
                this.bounds.y + this.bounds.height / 2);
    }

    /**
     * Character constructor with specified position as Vector2 and a characterName
     * @param position
     * @param characterName
     */
    public Character(Vector2 position, String characterName) {
        this.bounds.x = position.x;
        this.bounds.y = position.y;
        oldX = position.x;
        oldY = position.y;

        loadTexturesFromFolder("" + characterName + "/frontAnim/", animationFramesFront, 3);
        loadTexturesFromFolder("" + characterName + "/backAnim/", animationFramesBack, 3);
        loadTexturesFromFolder("" + characterName + "/leftAnim/", animationFramesLeft, 3);
        loadTexturesFromFolder("" + characterName + "/rightAnim/", animationFramesRight, 3);

        this.characterTexture1 = animationFramesBack[0];

        this.bounds.height = 96;
        this.bounds.width = 76;
    }

    /**
     * Create static character with specified texture
     * @param position initial position
     * @param texture texture
     */
    public Character(Vector2 position, Texture texture) {
        this.bounds.x = position.x;
        this.bounds.y = position.y;
        oldX = position.x;
        oldY = position.y;

        for (int i = 0; i < 4; i++) {
            animationFramesBack[i] = texture;
            animationFramesFront[i] = texture;
            animationFramesLeft[i] = texture;
            animationFramesRight[i] = texture;
        }

        this.characterTexture1 = animationFramesBack[0];

        this.bounds.height = 96;
        this.bounds.width = 76;
    }

    public PointLight getLight() {
        return pointLight;
    }

    public float getMovementSpeed() {
        return this.movementSpeed;
    }

    public void setTexture(Texture texture) {
        this.characterTexture1 = texture;
    }

    /**
     * Set texture from the animation based on current movement direction
     */
    public void setTextureBasedOnMoveDirection() {
        switch (moveDirection) {
            case UP:
                animation = new Animation<>(1f / 8f, animationFramesFront);
                break;
            case DOWN:
                animation = new Animation<>(1f / 8f, animationFramesBack);
                break;
            case LEFT:
                animation = new Animation<>(1f / 8f, animationFramesLeft);
                break;
            case RIGHT:
                animation = new Animation<>(1f / 8f, animationFramesRight);
                break;
            case IDLE:
                animation = null;
                break;
            default:
                break;
        }
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getHealth() {
        return health;
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public void setPlayerColliding(boolean isColliding) {
        this.isColliding = isColliding;
    }

    public boolean getIsColliding() {
        return isColliding;
    }

    /**
     * Move the player physically to the new location
     * @param xPosition
     * @param yPosition
     */
    public void moveToNewPosition(float xPosition, float yPosition) {
        this.bounds.set(xPosition, yPosition, bounds.getWidth(), bounds.getHeight());
    }

    /**
     * Render player using default texture
     * @param batch
     */
    public void render(Batch batch) {
        batch.draw(characterTexture1, this.bounds.x, this.bounds.y);
    }

    /**
     * Render player given a specified texture
     * @param batch
     * @param texture
     */
    public void render(Batch batch, Texture texture) {
        batch.draw(texture, this.bounds.x, this.bounds.y);
    }
}
