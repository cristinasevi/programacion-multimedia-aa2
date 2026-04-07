package programacion.multimedia.aa2.domain;

import programacion.multimedia.aa2.manager.ResourceManager;

import static programacion.multimedia.aa2.util.Constants.*;

public class TiburonEnemy extends Enemy {

    private PlayerFish jugador;

    public TiburonEnemy(float x, float y, PlayerFish jugador) {
        super(x, y, 130, 75, TIBURON_SPEED, TIBURON_VALUE);
        this.jugador = jugador;
        frameActual = ResourceManager.getRegion(TIBURON);
    }

    @Override
    public void actualizar(float dt) {
        tiempoEstado += dt;

        // Avanza hacia la izquierda
        posicion.x -= velocidad * dt;

        // Persigue al jugador en Y
        if (jugador.getY() > posicion.y) {
            posicion.y += velocidad * dt;
        } else if (jugador.getY() < posicion.y) {
            posicion.y -= velocidad * dt;
        }

        limites.setPosition(posicion.x, posicion.y);
    }
}
