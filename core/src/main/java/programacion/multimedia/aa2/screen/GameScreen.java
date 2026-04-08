package programacion.multimedia.aa2.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import programacion.multimedia.aa2.JuegoPeces;
import programacion.multimedia.aa2.manager.LogicManager;
import programacion.multimedia.aa2.manager.RenderManager;

public class GameScreen implements Screen {

    private JuegoPeces juego;
    private LogicManager logicManager;
    private RenderManager renderManager;

    public GameScreen(JuegoPeces juego) {
        this.juego = juego;
        logicManager = new LogicManager();
        renderManager = new RenderManager(logicManager);
    }

    @Override
    public void show() {
        logicManager.cargar();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        logicManager.actualizar(delta);
        renderManager.dibujar();

        // Pausa con ESC
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            juego.setScreen(new InGameMenuScreen(juego, this));
        }

        // Si partida terminada ir a game over
        if (logicManager.isPartidaTerminada()) {
            juego.setScreen(new GameOverScreen(juego, logicManager.jugador.getPuntuacion()));
        }
    }

    @Override
    public void resize(int width, int height) {}

    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}

    @Override
    public void dispose() {
        logicManager.dispose();
        renderManager.dispose();
    }
}
