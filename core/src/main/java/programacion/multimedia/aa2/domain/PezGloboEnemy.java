package programacion.multimedia.aa2.domain;

import com.badlogic.gdx.graphics.g2d.Batch;
import programacion.multimedia.aa2.manager.ResourceManager;

import java.util.ArrayList;
import java.util.List;

import static programacion.multimedia.aa2.util.Constants.*;

public class PezGloboEnemy extends Enemy {

    private float timerDisparo;
    private List<Burbuja> tinta;

    public PezGloboEnemy(float x, float y) {
        super(x, y, 100, 100, PEZ_GLOBO_SPEED, PEZ_GLOBO_VALUE);
        timerDisparo = 0;
        tinta = new ArrayList<>();
        frameActual = ResourceManager.getRegion(PEZ_GLOBO);
    }

    @Override
    public void actualizar(float dt) {
        tiempoEstado += dt;
        timerDisparo += dt;

        // Avanza hacia la izquierda
        posicion.x -= velocidad * dt;

        // Dispara cada cierto tiempo hacia la izquierda
        if (timerDisparo >= PEZ_GLOBO_SHOOT_RATE) {
            timerDisparo = 0;
            tinta.add(new Burbuja(posicion.x, posicion.y + 50, false));
        }

        // Actualizar tinta
        for (int i = tinta.size() - 1; i >= 0; i--) {
            tinta.get(i).actualizar(dt);
            if (tinta.get(i).fueraDePantalla()) tinta.remove(i);
        }

        limites.setPosition(posicion.x, posicion.y);
    }

    @Override
    public void dibujar(Batch batch) {
        super.dibujar(batch);
        for (Burbuja b : tinta) {
            b.dibujar(batch);
        }
    }

    public List<Burbuja> getTinta() { return tinta; }
}
