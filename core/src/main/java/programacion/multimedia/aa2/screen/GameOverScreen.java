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

import java.util.List;

public class GameOverScreen implements Screen {

    private JuegoPeces juego;
    private int puntuacion;
    private Stage stage;

    public GameOverScreen(JuegoPeces juego, int puntuacion) {
        this.juego = juego;
        this.puntuacion = puntuacion;
    }

    @Override
    public void show() {
        if (!VisUI.isLoaded()) VisUI.load();

        stage = new Stage();

        VisTable tabla = new VisTable(true);
        tabla.setFillParent(true);
        stage.addActor(tabla);

        // Título
        VisLabel lblGameOver = new VisLabel("GAME OVER");
        VisLabel lblPuntuacion = new VisLabel("Puntuación: " + puntuacion);

        // Campo nombre jugador
        VisLabel lblNombre = new VisLabel("Tu nombre:");
        VisTextField txtNombre = new VisTextField("");

        VisTextButton btnGuardar = new VisTextButton("Guardar puntuación");
        btnGuardar.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                String nombre = txtNombre.getText().trim();
                if (!nombre.isEmpty()) {
                    ConfigurationManager.saveScore(nombre, puntuacion);
                    mostrarTop10(tabla);
                }
            }
        });

        VisTextButton btnJugarOtraVez = new VisTextButton("Jugar otra vez");
        btnJugarOtraVez.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                juego.setScreen(new GameScreen(juego));
            }
        });

        VisTextButton btnMenuPrincipal = new VisTextButton("Menú principal");
        btnMenuPrincipal.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                juego.setScreen(new MainMenuScreen(juego));
            }
        });

        tabla.add(lblGameOver).center().pad(10);
        tabla.row();
        tabla.add(lblPuntuacion).center().pad(5);
        tabla.row();
        tabla.add(lblNombre).center().pad(5);
        tabla.row();
        tabla.add(txtNombre).center().width(300).height(50).pad(5);
        tabla.row();
        tabla.add(btnGuardar).center().width(300).height(60).pad(5);
        tabla.row();
        tabla.add(btnJugarOtraVez).center().width(300).height(60).pad(5);
        tabla.row();
        tabla.add(btnMenuPrincipal).center().width(300).height(60).pad(5);

        Gdx.input.setInputProcessor(stage);
    }

    private void mostrarTop10(VisTable tabla) {
        tabla.clear();
        VisLabel lblTitulo = new VisLabel("TOP 10");
        tabla.add(lblTitulo).center().pad(10);
        tabla.row();

        List<String> scores = ConfigurationManager.getTopScoresFormatted();
        for (String score : scores) {
            tabla.add(new VisLabel(score)).center().pad(3);
            tabla.row();
        }

        VisTextButton btnVolver = new VisTextButton("Menú principal");
        btnVolver.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                juego.setScreen(new MainMenuScreen(juego));
            }
        });
        tabla.add(btnVolver).center().width(300).height(60).pad(10);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0.1f, 0.2f, 1);
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
