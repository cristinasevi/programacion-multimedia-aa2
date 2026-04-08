package programacion.multimedia.aa2.domain;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import programacion.multimedia.aa2.manager.ResourceManager;

import java.util.ArrayList;
import java.util.List;

import static programacion.multimedia.aa2.util.Constants.*;

public class PlayerFish extends Character {

    private Animation<TextureRegion> animNadar;
    private Animation<TextureRegion> animGirar;
    private Animation<TextureRegion> animSubir;
    private Animation<TextureRegion> animBajar;
    private Animation<TextureRegion> animGolpe;

    private int puntuacion;
    private float timerDisparo;
    private float timerEscudo;
    private float timerVelocidad;
    private float timerDobleDisparo;
    private float timerGolpe;
    private boolean miraDerecha;

    private List<Burbuja> balas;

    public PlayerFish() {
        super(100, SCREEN_HEIGHT / 2f, 60, 40);
        vidas = PLAYER_LIVES;
        puntuacion = 0;
        miraDerecha = true;
        timerGolpe = 0;
        balas = new ArrayList<>();

        animNadar = crearAnimacion(PLAYER_SWIM);
        animGirar = crearAnimacion(PLAYER_TURN);
        animSubir = crearAnimacion(PLAYER_UP);
        animBajar = crearAnimacion(PLAYER_DOWN);
        animGolpe = crearAnimacion(PLAYER_OUCH);

        frameActual = animNadar.getKeyFrame(0);
    }

    private Animation<TextureRegion> crearAnimacion(String prefijo) {
        Array<TextureRegion> frames = new Array<>();
        for (int i = 1; i <= 4; i++) {
            frames.add(ResourceManager.getRegion(prefijo + "-" + i));
        }
        return new Animation<>(0.15f, frames, Animation.PlayMode.LOOP);
    }

    @Override
    public void actualizar(float dt) {
        tiempoEstado += dt;
        timerDisparo += dt;
        if (timerEscudo > 0) timerEscudo -= dt;
        if (timerVelocidad > 0) timerVelocidad -= dt;
        if (timerDobleDisparo > 0) timerDobleDisparo -= dt;

        // Si está en animación de golpe, esperar a que termine
        if (timerGolpe > 0) {
            timerGolpe -= dt;
            frameActual = animGolpe.getKeyFrame(tiempoEstado, true);
        } else {
            manejarInput(dt);
        }

        // Actualizar balas
        for (int i = balas.size() - 1; i >= 0; i--) {
            balas.get(i).actualizar(dt);
            if (balas.get(i).fueraDePantalla()) balas.remove(i);
        }

        limites.setPosition(posicion.x, posicion.y);
    }

    private void manejarInput(float dt) {
        float vel = PLAYER_SPEED * (timerVelocidad > 0 ? SPEED_MULTIPLIER : 1f);

        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            posicion.x += vel * dt;
            frameActual = animNadar.getKeyFrame(tiempoEstado, true);
            miraDerecha = true;
        } else if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            posicion.x -= vel * dt;
            frameActual = animGirar.getKeyFrame(tiempoEstado, true);
            miraDerecha = false;
        } else if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            posicion.y += vel * dt;
            frameActual = animSubir.getKeyFrame(tiempoEstado, true);
        } else if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            posicion.y -= vel * dt;
            frameActual = animBajar.getKeyFrame(tiempoEstado, true);
        } else {
            frameActual = animNadar.getKeyFrame(tiempoEstado, true);
        }

        // Límites de pantalla
        posicion.x = Math.max(0, Math.min(posicion.x, SCREEN_WIDTH - limites.width));
        posicion.y = Math.max(0, Math.min(posicion.y, SCREEN_HEIGHT - limites.height));

        // Disparo
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE) && timerDisparo >= BULLET_RATE) {
            timerDisparo = 0;
            float bx = miraDerecha ? posicion.x + limites.width : posicion.x - 10;
            float by = posicion.y + limites.height / 2f;
            balas.add(new Burbuja(bx, by, miraDerecha));
            if (timerDobleDisparo > 0)
                balas.add(new Burbuja(bx, by + 10, miraDerecha));
        }
    }

    public void recibirGolpe() {
        if (timerEscudo <= 0) {
            vidas--;
            timerGolpe = 0.6f;
        }
    }

    public void activarEscudo(float duracion)       { timerEscudo = duracion; }
    public void activarVelocidad(float duracion)    { timerVelocidad = duracion; }
    public void activarDobleDisparo(float duracion) { timerDobleDisparo = duracion; }

    public boolean tieneEscudo()        { return timerEscudo > 0; }
    public float getTimerEscudo()       { return timerEscudo; }
    public float getTimerVelocidad()    { return timerVelocidad; }
    public float getTimerDobleDisparo() { return timerDobleDisparo; }
    public int getPuntuacion()          { return puntuacion; }
    public void sumarPuntos(int p)      { puntuacion += p; }
    public List<Burbuja> getBalas()     { return balas; }
}
