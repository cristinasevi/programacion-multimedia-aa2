package programacion.multimedia.aa2;

import com.badlogic.gdx.Game;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class JuegoPeces extends Game {
    @Override
    public void create() {
        setScreen(new FirstScreen());
    }
}
