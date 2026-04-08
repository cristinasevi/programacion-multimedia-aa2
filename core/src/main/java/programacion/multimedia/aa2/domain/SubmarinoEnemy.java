package programacion.multimedia.aa2.domain;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import programacion.multimedia.aa2.manager.ResourceManager;

import static programacion.multimedia.aa2.util.Constants.*;

public class SubmarinoEnemy extends Enemy {

    private float tiempo;
    private float yOrigen;

    public SubmarinoEnemy(float x, float y, float modificador) {
        super(x, y, 100, 50, SUBMARINO_SPEED * modificador, SUBMARINO_VALUE);
        yOrigen = y;
        tiempo = 0;
        frameActual = ResourceManager.getRegion(SUBMARINO);
    }

    @Override
    public void actualizar(float dt) {
        tiempoEstado += dt;
        tiempo += dt;

        // Movimiento horizontal hacia la izquierda
        posicion.x -= velocidad * dt;

        // Zigzag en Y
        posicion.y = yOrigen + (float) Math.sin(tiempo * 2) * 40;

        limites.setPosition(posicion.x, posicion.y);
    }
}
