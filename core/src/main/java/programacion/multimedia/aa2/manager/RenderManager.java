package programacion.multimedia.aa2.manager;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Disposable;
import programacion.multimedia.aa2.domain.Enemy;
import programacion.multimedia.aa2.powerup.Powerup;

import static programacion.multimedia.aa2.util.Constants.*;

public class RenderManager implements Disposable {

    private SpriteBatch batch;
    private BitmapFont fuente;
    private LogicManager logicManager;
    private Texture fondoNivel1;
    private Texture fondoNivel2;

    public RenderManager(LogicManager logicManager) {
        this.logicManager = logicManager;
        batch = new SpriteBatch();
        fuente = new BitmapFont();
        fuente.setColor(Color.WHITE);

        fondoNivel1 = ResourceManager.getTexture(FONDO1);
        fondoNivel2 = ResourceManager.getTexture(FONDO2);
    }

    public void dibujar() {
        batch.begin();

        dibujarFondo();
        dibujarEnemigos();
        dibujarPowerups();
        dibujarJugador();
        dibujarHUD();

        batch.end();
    }

    private void dibujarFondo() {
        Texture fondo = logicManager.getNivelActual() == 1 ? fondoNivel1 : fondoNivel2;
        batch.draw(fondo, 0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
    }

    private void dibujarEnemigos() {
        for (Enemy e : logicManager.enemigos) {
            e.dibujar(batch);
        }
    }

    private void dibujarPowerups() {
        for (Powerup p : logicManager.powerups) {
            p.dibujar(batch);
        }
    }

    private void dibujarJugador() {
        logicManager.jugador.dibujar(batch);

        // Dibujar balas del jugador
        for (var bala : logicManager.jugador.getBalas()) {
            bala.dibujar(batch);
        }
    }

    private void dibujarHUD() {
        // Puntuación
        fuente.draw(batch, "PUNTOS: " + logicManager.jugador.getPuntuacion(), 10, SCREEN_HEIGHT - 10);
        // Vidas
        fuente.draw(batch, "VIDAS: " + logicManager.jugador.getVidas(), 10, SCREEN_HEIGHT - 30);
        // Nivel
        fuente.draw(batch, "NIVEL: " + logicManager.getNivelActual(), 10, SCREEN_HEIGHT - 50);

        // Escudo activo
        if (logicManager.jugador.tieneEscudo()) {
            fuente.draw(batch, "ESCUDO ACTIVO", SCREEN_WIDTH / 2f - 50, SCREEN_HEIGHT - 10);
        }
    }

    public SpriteBatch getBatch() { return batch; }

    @Override
    public void dispose() {
        batch.dispose();
        fuente.dispose();
    }
}
