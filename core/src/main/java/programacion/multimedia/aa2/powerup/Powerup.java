package programacion.multimedia.aa2.powerup;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import programacion.multimedia.aa2.domain.PlayerFish;

public abstract class Powerup {

    protected Vector2 posicion;
    protected Rectangle limites;
    protected TextureRegion textura;
    protected float velocidad = 80f;

    public Powerup(float x, float y) {
        posicion = new Vector2(x, y);
    }

    public void actualizar(float dt) {
        posicion.x -= velocidad * dt;
        limites.setPosition(posicion.x, posicion.y);
    }

    public void dibujar(Batch batch) {
        batch.draw(textura, posicion.x, posicion.y);
    }

    public abstract void aplicar(PlayerFish jugador);

    public boolean fueraDePantalla() {
        return posicion.x < -limites.width;
    }

    public Rectangle getLimites() { return limites; }
}
