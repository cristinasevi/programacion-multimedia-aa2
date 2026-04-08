package programacion.multimedia.aa2.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.kotcrab.vis.ui.VisUI;
import com.kotcrab.vis.ui.widget.*;
import programacion.multimedia.aa2.JuegoPeces;
import programacion.multimedia.aa2.manager.ConfigurationManager;

public class ConfigurationScreen implements Screen {

    private JuegoPeces juego;
    private Stage stage;

    public ConfigurationScreen(JuegoPeces juego) {
        this.juego = juego;
    }

    @Override
    public void show() {
        if (!VisUI.isLoaded()) VisUI.load();

        stage = new Stage();

        VisTable tabla = new VisTable(true);
        tabla.setFillParent(true);
        stage.addActor(tabla);

        // Sonido
        VisCheckBox chkSonido = new VisCheckBox("Efectos de sonido");
        chkSonido.setChecked(ConfigurationManager.isSoundEnabled());
        chkSonido.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ConfigurationManager.setSoundEnabled(chkSonido.isChecked());
            }
        });

        // Música
        VisCheckBox chkMusica = new VisCheckBox("Música de fondo");
        chkMusica.setChecked(ConfigurationManager.isMusicEnabled());
        chkMusica.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ConfigurationManager.setMusicEnabled(chkMusica.isChecked());
            }
        });

        // Dificultad
        VisList<String> listaDificultad = new VisList<>();
        listaDificultad.getItems().add("Fácil");
        listaDificultad.getItems().add("Medio");
        listaDificultad.getItems().add("Difícil");
        listaDificultad.setSelectedIndex(ConfigurationManager.getDifficulty() - 1);
        listaDificultad.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ConfigurationManager.setDifficulty(listaDificultad.getSelectedIndex() + 1);
            }
        });

        // Volver
        VisTextButton btnVolver = new VisTextButton("Volver");
        btnVolver.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                juego.setScreen(new MainMenuScreen(juego));
            }
        });

        tabla.add(chkSonido).center().width(300).height(50).pad(5);
        tabla.row();
        tabla.add(chkMusica).center().width(300).height(50).pad(5);
        tabla.row();
        tabla.add(listaDificultad).center().width(300).height(80).pad(10);
        tabla.row();
        tabla.add(btnVolver).center().width(300).height(80).pad(10);

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
