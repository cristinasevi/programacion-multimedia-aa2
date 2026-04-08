package programacion.multimedia.aa2;

import com.badlogic.gdx.Game;
import programacion.multimedia.aa2.manager.ResourceManager;
import programacion.multimedia.aa2.screen.MainMenuScreen;

public class JuegoPeces extends Game {

    @Override
    public void create() {
        ResourceManager.loadAllResources();
        while (!ResourceManager.update()) {
            // Esperar a que carguen los recursos
        }
        setScreen(new MainMenuScreen(this));
    }

    @Override
    public void dispose() {
        super.dispose();
        ResourceManager.dispose();
    }
}
