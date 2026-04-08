package programacion.multimedia.aa2.domain;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public abstract class Enemy extends Character {

    protected float velocidad;
    protected int puntos;

    public Enemy(float x, float y, int ancho, int alto, float velocidad, int puntos) {
        super(x, y, ancho, alto);
        this.velocidad = velocidad;
        this.puntos = puntos;
        this.vidas = 1;
    }

    public int getPuntos() { return puntos; }
}
