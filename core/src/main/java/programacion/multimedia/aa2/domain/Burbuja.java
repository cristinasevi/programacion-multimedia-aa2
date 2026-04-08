package programacion.multimedia.aa2.domain;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import programacion.multimedia.aa2.manager.ResourceManager;

import static programacion.multimedia.aa2.util.Constants.*;

public class Burbuja {

    private static final float ANCHO = 20f;
    private static final float ALTO  = 20f;

    private Vector2 posicion;
    private Rectangle limites;
    private TextureRegion textura;
    private boolean vaDerecha;

    public Burbuja(float x, float y, boolean vaDerecha) {
        this.vaDerecha = vaDerecha;
        textura = ResourceManager.getRegion(BURBUJA_PROYECTIL);
        posicion = new Vector2(x, y);
        limites = new Rectangle(x, y, ANCHO, ALTO);
    }

    public void actualizar(float dt) {
        posicion.x += BULLET_SPEED * (vaDerecha ? 1 : -1) * dt;
        limites.setPosition(posicion.x, posicion.y);
    }

    public void dibujar(Batch batch) {
        batch.draw(textura, posicion.x, posicion.y, ANCHO, ALTO);
    }

    public boolean fueraDePantalla() {
        return posicion.x > SCREEN_WIDTH || posicion.x < 0;
    }

    public Rectangle getLimites() { return limites; }
    public TextureRegion getTextura() { return textura; }
}
