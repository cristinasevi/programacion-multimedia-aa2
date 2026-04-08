package programacion.multimedia.aa2.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.kotcrab.vis.ui.VisUI;
import com.kotcrab.vis.ui.widget.VisLabel;
import com.kotcrab.vis.ui.widget.VisTable;
import com.kotcrab.vis.ui.widget.VisTextButton;
import programacion.multimedia.aa2.JuegoPeces;

public class InstructionsScreen implements Screen {

    private JuegoPeces juego;
    private Stage stage;

    public InstructionsScreen(JuegoPeces juego) {
        this.juego = juego;
    }

    @Override
    public void show() {
        if (!VisUI.isLoaded()) VisUI.load();

        stage = new Stage();

        VisTable tabla = new VisTable(true);
        tabla.setFillParent(true);
        stage.addActor(tabla);

        tabla.add(new VisLabel("INSTRUCCIONES")).center().pad(10);
        tabla.row();
        tabla.add(new VisLabel("Flechas / WASD → Mover el pez")).center().pad(5);
        tabla.row();
        tabla.add(new VisLabel("ESPACIO → Disparar burbujas")).center().pad(5);
        tabla.row();
        tabla.add(new VisLabel("ESC → Pausar el juego")).center().pad(5);
        tabla.row();
        tabla.add(new VisLabel("")).pad(5);
        tabla.row();
        tabla.add(new VisLabel("ENEMIGOS")).center().pad(5);
        tabla.row();
        tabla.add(new VisLabel("Submarino → Se mueve en zigzag (5 pts)")).center().pad(3);
        tabla.row();
        tabla.add(new VisLabel("Tiburón → Te persigue (15 pts)")).center().pad(3);
        tabla.row();
        tabla.add(new VisLabel("Pez globo → Dispara tinta (10 pts)")).center().pad(3);
        tabla.row();
        tabla.add(new VisLabel("")).pad(5);
        tabla.row();
        tabla.add(new VisLabel("POWERUPS")).center().pad(5);
        tabla.row();
        tabla.add(new VisLabel("Estrella de mar → Escudo 5s")).center().pad(3);
        tabla.row();
        tabla.add(new VisLabel("Caballito de mar → Velocidad 5s")).center().pad(3);
        tabla.row();
        tabla.add(new VisLabel("Concha → Disparo doble 5s")).center().pad(3);
        tabla.row();

        VisTextButton btnJugar = new VisTextButton("¡Jugar!");
        btnJugar.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                juego.setScreen(new GameScreen(juego));
            }
        });

        VisTextButton btnVolver = new VisTextButton("Volver al menú");
        btnVolver.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                juego.setScreen(new MainMenuScreen(juego));
            }
        });

        tabla.add(btnJugar).center().width(300).height(70).pad(8);
        tabla.row();
        tabla.add(btnVolver).center().width(300).height(70).pad(8);

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
