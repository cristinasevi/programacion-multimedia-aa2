package programacion.multimedia.aa2.manager;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Disposable;
import programacion.multimedia.aa2.domain.Burbuja;
import programacion.multimedia.aa2.domain.Enemy;
import programacion.multimedia.aa2.domain.PezGloboEnemy;
import programacion.multimedia.aa2.domain.SubmarinoEnemy;
import programacion.multimedia.aa2.domain.TiburonEnemy;
import programacion.multimedia.aa2.powerup.Powerup;

import static programacion.multimedia.aa2.util.Constants.*;

public class RenderManager implements Disposable {

    private SpriteBatch batch;
    private BitmapFont fuente;
    private ShapeRenderer shapeRenderer;
    private LogicManager logicManager;
    private Texture fondoNivel1;
    private Texture fondoNivel2;
    private float scrollX;

    private static final float HUD_H = 28f;
    private static final float TIBURON_W = 130f;
    private static final float TIBURON_H = 75f;
    private static final float SUBMARINO_W = 100f;
    private static final float SUBMARINO_H = 50f;
    private static final float PEZGLOBO_W = 80f;
    private static final float PEZGLOBO_H = 80f;
    private static final float BURBUJA_W = 20f;
    private static final float BURBUJA_H = 20f;
    private static final float POWERUP_W = 50f;
    private static final float POWERUP_H = 50f;
    private static final float PLAYER_W = 60f;
    private static final float PLAYER_H = 40f;

    public RenderManager(LogicManager logicManager) {
        this.logicManager = logicManager;
        batch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();
        fuente = new BitmapFont();
        fuente.getData().setScale(1f);
        fuente.setColor(Color.WHITE);
        scrollX = 0;

        fondoNivel1 = ResourceManager.getTexture(FONDO1);
        fondoNivel2 = ResourceManager.getTexture(FONDO2);
    }

    public void dibujar(float delta) {
        dibujarBarraHUD();

        batch.begin();
        dibujarFondo(delta);
        dibujarEnemigos();
        dibujarPowerups();
        dibujarJugador();
        dibujarTextoHUD();
        batch.end();
    }

    // Fondo con scroll
    private void dibujarFondo(float delta) {
        scrollX -= 80 * delta;
        if (scrollX <= -SCREEN_WIDTH) scrollX = 0;

        Texture fondo = logicManager.getNivelActual() == 1 ? fondoNivel1 : fondoNivel2;
        // El área de juego empieza en y=HUD_H para dejar espacio a la barra
        batch.draw(fondo, scrollX, HUD_H, SCREEN_WIDTH, SCREEN_HEIGHT - HUD_H);
        batch.draw(fondo, scrollX + SCREEN_WIDTH, HUD_H, SCREEN_WIDTH, SCREEN_HEIGHT - HUD_H);
    }

    // Barra HUD
    private void dibujarBarraHUD() {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(0.05f, 0.1f, 0.35f, 1f);
        shapeRenderer.rect(0, 0, SCREEN_WIDTH, HUD_H);
        shapeRenderer.end();
    }

    // Texto del HUD
    private void dibujarTextoHUD() {
        float y = HUD_H - 6f;

        fuente.draw(batch, "PUNTOS: " + logicManager.jugador.getPuntuacion(), 8, y);
        fuente.draw(batch, "VIDAS: " + logicManager.jugador.getVidas(), 170, y);
        fuente.draw(batch, "NIVEL: " + logicManager.getNivelActual(), 290, y);

        // Powerup activo
        String powerupActivo = getPowerupActivo();
        if (!powerupActivo.isEmpty()) {
            fuente.draw(batch, powerupActivo, 400, y);
        }
    }

    private String getPowerupActivo() {
        float escudo = logicManager.jugador.getTimerEscudo();
        float vel    = logicManager.jugador.getTimerVelocidad();
        float doble  = logicManager.jugador.getTimerDobleDisparo();

        if (escudo > 0)  return "ESCUDO " + (int) escudo + "s";
        if (vel > 0)     return "VELOCIDAD " + (int) vel + "s";
        if (doble > 0)   return "DOBLE DISPARO " + (int) doble + "s";
        return "";
    }

    // Enemigos, powerups y jugador
    private void dibujarEnemigos() {
        for (Enemy e : logicManager.enemigos) {
            if (e instanceof TiburonEnemy) {
                batch.draw(e.getFrameActual(), e.getX(), e.getY(), TIBURON_W, TIBURON_H);
            } else if (e instanceof SubmarinoEnemy) {
                batch.draw(e.getFrameActual(), e.getX(), e.getY(), SUBMARINO_W, SUBMARINO_H);
            } else if (e instanceof PezGloboEnemy) {
                batch.draw(e.getFrameActual(), e.getX(), e.getY(), PEZGLOBO_W, PEZGLOBO_H);
                for (Burbuja b : ((PezGloboEnemy) e).getTinta()) {
                    batch.draw(b.getTextura(), b.getLimites().x, b.getLimites().y, BURBUJA_W, BURBUJA_H);
                }
            }
        }
    }

    private void dibujarPowerups() {
        for (Powerup p : logicManager.powerups) {
            batch.draw(p.getTextura(), p.getLimites().x, p.getLimites().y, POWERUP_W, POWERUP_H);
        }
    }

    private void dibujarJugador() {
        batch.draw(logicManager.jugador.getFrameActual(),
            logicManager.jugador.getX(), logicManager.jugador.getY(), PLAYER_W, PLAYER_H);
        for (Burbuja bala : logicManager.jugador.getBalas()) {
            batch.draw(bala.getTextura(), bala.getLimites().x, bala.getLimites().y, BURBUJA_W, BURBUJA_H);
        }
    }

    @Override
    public void dispose() {
        batch.dispose();
        fuente.dispose();
        shapeRenderer.dispose();
    }
}
