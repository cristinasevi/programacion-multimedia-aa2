package programacion.multimedia.aa2.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.kotcrab.vis.ui.VisUI;
import com.kotcrab.vis.ui.widget.VisTable;
import com.kotcrab.vis.ui.widget.VisTextButton;
import programacion.multimedia.aa2.JuegoPeces;

public class MainMenuScreen implements Screen {

    private JuegoPeces juego;
    private Stage stage;

    public MainMenuScreen(JuegoPeces juego) {
        this.juego = juego;
    }

    @Override
    public void show() {
        if (!VisUI.isLoaded()) VisUI.load();

        stage = new Stage();

        VisTable tabla = new VisTable(true);
        tabla.setFillParent(true);
        stage.addActor(tabla);

        VisTextButton btnJugar = new VisTextButton("Jugar");
        btnJugar.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                juego.setScreen(new GameScreen(juego));
            }
        });

        VisTextButton btnInstrucciones = new VisTextButton("Instrucciones");
        btnInstrucciones.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                juego.setScreen(new InstructionsScreen(juego));
            }
        });

        VisTextButton btnConfiguracion = new VisTextButton("Configuración");
        btnConfiguracion.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                juego.setScreen(new ConfigurationScreen(juego));
            }
        });

        VisTextButton btnSalir = new VisTextButton("Salir");
        btnSalir.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });

        tabla.add(btnJugar).center().width(300).height(80).pad(10);
        tabla.row();
        tabla.add(btnInstrucciones).center().width(300).height(80).pad(10);
        tabla.row();
        tabla.add(btnConfiguracion).center().width(300).height(80).pad(10);
        tabla.row();
        tabla.add(btnSalir).center().width(300).height(80).pad(10);

        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0.2f, 0.4f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        if (width <= 0 || height <= 0) return;
        stage.getViewport().update(width, height, true);
    }

    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
    @Override public void dispose() { stage.dispose(); }
}
