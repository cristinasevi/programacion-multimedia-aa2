package programacion.multimedia.aa2.screen;

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
import programacion.multimedia.aa2.manager.ConfigurationManager;

public class InGameMenuScreen implements Screen {

    private JuegoPeces juego;
    private GameScreen gameScreen;
    private Stage stage;

    public InGameMenuScreen(JuegoPeces juego, GameScreen gameScreen) {
        this.juego = juego;
        this.gameScreen = gameScreen;
    }

    @Override
    public void show() {
        if (!VisUI.isLoaded()) VisUI.load();

        stage = new Stage();

        VisTable tabla = new VisTable(true);
        tabla.setFillParent(true);
        stage.addActor(tabla);

        VisTextButton btnContinuar = new VisTextButton("Continuar");
        btnContinuar.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                juego.setScreen(gameScreen);
            }
        });

        VisTextButton btnSonido = new VisTextButton(
            ConfigurationManager.isSoundEnabled() ? "Sonido: ON" : "Sonido: OFF");
        btnSonido.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                boolean nuevoEstado = !ConfigurationManager.isSoundEnabled();
                ConfigurationManager.setSoundEnabled(nuevoEstado);
                btnSonido.setText(nuevoEstado ? "Sonido: ON" : "Sonido: OFF");
            }
        });

        VisTextButton btnMenuPrincipal = new VisTextButton("Menú principal");
        btnMenuPrincipal.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                juego.setScreen(new MainMenuScreen(juego));
            }
        });

        VisTextButton btnSalir = new VisTextButton("Salir del juego");
        btnSalir.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });

        tabla.add(btnContinuar).center().width(300).height(80).pad(10);
        tabla.row();
        tabla.add(btnSonido).center().width(300).height(80).pad(10);
        tabla.row();
        tabla.add(btnMenuPrincipal).center().width(300).height(80).pad(10);
        tabla.row();
        tabla.add(btnSalir).center().width(300).height(80).pad(10);

        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0.1f, 0.3f, 1);
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
