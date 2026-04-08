package programacion.multimedia.aa2.powerup;

import com.badlogic.gdx.math.Rectangle;
import programacion.multimedia.aa2.domain.PlayerFish;
import programacion.multimedia.aa2.manager.ResourceManager;

import static programacion.multimedia.aa2.util.Constants.*;

public class DisparoDoublePowerup extends Powerup {

    public DisparoDoublePowerup(float x, float y) {
        super(x, y);
        textura = ResourceManager.getRegion(POWERUP_DOBLE);
        limites = new Rectangle(x, y, textura.getRegionWidth(), textura.getRegionHeight());
    }

    @Override
    public void aplicar(PlayerFish jugador) {
        jugador.activarDobleDisparo(DOUBLE_SHOT_DURATION);
    }
}
