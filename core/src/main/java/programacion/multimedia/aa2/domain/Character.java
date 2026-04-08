package programacion.multimedia.aa2.domain;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public abstract class Character {

    protected TextureRegion frameActual;
    protected Vector2 posicion;
    protected Rectangle limites;
    protected float tiempoEstado;
    protected int vidas;

    public Character(float x, float y, int ancho, int alto) {
        posicion = new Vector2(x, y);
        limites = new Rectangle(x, y, ancho, alto);
        tiempoEstado = 0;
    }

    public abstract void actualizar(float dt);

    public void dibujar(Batch batch) {
        batch.draw(frameActual, posicion.x, posicion.y);
    }

    public boolean colisionaCon(Character otro) {
        return limites.overlaps(otro.limites);
    }

    public boolean estaMuerto() { return vidas <= 0; }

    public float getX() { return posicion.x; }
    public float getY() { return posicion.y; }
    public Rectangle getLimites() { return limites; }
    public int getVidas() { return vidas; }
    public void setVidas(int vidas) { this.vidas = vidas; }
    public TextureRegion getFrameActual() { return frameActual; }
}
