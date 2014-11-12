package com.eddie.executiveexperience;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.eddie.executiveexperience.UI.HomeScreen;
import com.eddie.executiveexperience.UI.UI;

public class Game extends com.badlogic.gdx.Game
{
    public static Game instance;

    public GameScreen gameScreen;
    public HomeScreen homeScreen;
    protected MusicManager musicManager;
    protected Assets assets;

    protected UI ui;
    protected ShapeRenderer shapeRenderer;
    protected SpriteBatch spriteBatch;
    public static OrthographicCamera camera;

    protected InputMultiplexer inputMultiplexer;
    protected InputManager inputManager;

    protected boolean isFullscreen;
    protected boolean cheatMode;

    public Game()
    {
        instance = this;
    }

    @Override
    public void create()
    {
        assets = new Assets("assets/config/assets.json");
        assets.loadGroup("base");
//        assets.loadFolder("entities", "assets/entities/", EntityData.class, ".json", true);
//        assets.loadFolder("maps", "assets/levels/", TiledMap.class, ".tmx", false);
        assets.finishLoading();

        inputManager = new InputManager();

        inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(inputManager);

        Gdx.input.setInputProcessor(inputMultiplexer);

        musicManager = new MusicManager();

        homeScreen = new HomeScreen();

        camera = new OrthographicCamera(Env.virtualWidth, Env.virtualHeight);
        camera.setToOrtho(false);

        Gdx.graphics.setDisplayMode((int) Env.virtualWidth, (int) Env.virtualHeight, false);
        isFullscreen = false;

        shapeRenderer = new ShapeRenderer();
        shapeRenderer.setProjectionMatrix(camera.combined);

        spriteBatch = new SpriteBatch();
        spriteBatch.setProjectionMatrix(camera.combined);

        ui = new UI();

        setScreen(homeScreen);

        if(Env.debug)
        {
            Game.instance.getUI().writeText("Debug mode enabled");
            Game.instance.getUI().writeText("Press F1 to turn on aimbot.");
        }

        cheatMode = false;
    }

    @Override
    public void render()
    {
        processInput();

        GL20 gl = Gdx.gl20;
        gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        super.render();

        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        ui.renderShadowBox();
        shapeRenderer.end();
        Gdx.gl.glDisable(GL20.GL_BLEND);

        spriteBatch.begin();
        ui.render(spriteBatch);
        spriteBatch.end();
    }

    private void processInput()
    {
        if(inputManager.isKeyTyped(Input.Keys.F1))
        {
            Game.instance.getUI().writeText("WALL HACK TURNED ON");
            Game.instance.getUI().writeText("HOLD ON TO YOUR SOCKS");

            cheatMode = true;
        }

        ((Screen) getScreen()).processInput(inputManager);
    }

    public GameScreen getGameScreen()
    {
        return gameScreen;
    }

    public Assets getAssetManager()
    {
        return assets;
    }

    public MusicManager getMusicManager()
    {
        return musicManager;
    }

    public ShapeRenderer getShapeRenderer()
    {
        return shapeRenderer;
    }

    public InputMultiplexer getInputMultiplexer()
    {
        return inputMultiplexer;
    }

    public UI getUI()
    {
        return ui;
    }

    public SpriteBatch getSpriteBatch()
    {
        return spriteBatch;
    }

    public void toggleFullscreen()
    {
        isFullscreen = !isFullscreen;

        Gdx.graphics.setDisplayMode((int) Env.virtualWidth, (int) Env.virtualHeight, isFullscreen);
    }

    public boolean isCheating()
    {
        return cheatMode;
    }
}

/*
 * Credits:
 * Thomas Snyder, Jonathan Bees, Isaac Merritt - Bug Testing
 * Blue Strike Warrior - Graphics
 */