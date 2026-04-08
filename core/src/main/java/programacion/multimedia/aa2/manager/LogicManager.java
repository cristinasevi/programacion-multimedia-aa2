package programacion.multimedia.aa2.manager;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.utils.Disposable;
import programacion.multimedia.aa2.domain.*;
import programacion.multimedia.aa2.powerup.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static programacion.multimedia.aa2.util.Constants.*;

public class LogicManager implements Disposable {

    public PlayerFish jugador;
    public List<Enemy> enemigos;
    public List<Powerup> powerups;

    private Music musica;
    private float timerEnemigo;
    private float timerPowerup;
    private Random random;
    private int nivelActual;
    private boolean partidaTerminada;

    public LogicManager() {
        enemigos = new ArrayList<>();
        powerups = new ArrayList<>();
        random = new Random();
        nivelActual = 1;
        timerEnemigo = 0;
        timerPowerup = 0;
        partidaTerminada = false;
    }

    public void cargar() {
        jugador = new PlayerFish();

        musica = ResourceManager.getMusic(MUSIC_GAME);
        if (ConfigurationManager.isMusicEnabled()) {
            musica.setLooping(true);
            musica.play();
        }
    }

    public void actualizar(float dt) {
        if (partidaTerminada) return;

        jugador.actualizar(dt);

        // Comprobar si pasamos al nivel 2
        if (nivelActual == 1 && jugador.getPuntuacion() >= LEVEL_2_SCORE) {
            nivelActual = 2;
        }

        spawnearEnemigos(dt);
        spawnearPowerups(dt);
        actualizarEnemigos(dt);
        actualizarPowerups(dt);
        comprobarColisiones();

        if (jugador.estaMuerto()) {
            partidaTerminada = true;
            musica.stop();
        }
    }

    private void spawnearEnemigos(float dt) {
        timerEnemigo += dt;
        float tasaSpawn = nivelActual == 2 ? ENEMY_SPAWN_RATE * 0.6f : ENEMY_SPAWN_RATE;

        if (timerEnemigo >= tasaSpawn) {
            timerEnemigo = 0;
            float y = random.nextFloat() * (SCREEN_HEIGHT - 100) + 50;
            int tipo = random.nextInt(nivelActual == 2 ? 3 : 2);

            switch (tipo) {
                case 0:
                    enemigos.add(new SubmarinoEnemy(SCREEN_WIDTH + 50, y));
                    break;
                case 1:
                    enemigos.add(new TiburonEnemy(SCREEN_WIDTH + 50, y, jugador));
                    break;
                case 2:
                    enemigos.add(new PezGloboEnemy(SCREEN_WIDTH + 50, y));
                    break;
            }
        }
    }

    private void spawnearPowerups(float dt) {
        timerPowerup += dt;
        if (timerPowerup >= POWERUP_SPAWN_RATE) {
            timerPowerup = 0;
            float y = random.nextFloat() * (SCREEN_HEIGHT - 100) + 50;
            int tipo = random.nextInt(3);

            switch (tipo) {
                case 0: powerups.add(new EscudoPowerup(SCREEN_WIDTH + 50, y)); break;
                case 1: powerups.add(new VelocidadPowerup(SCREEN_WIDTH + 50, y)); break;
                case 2: powerups.add(new DisparoDoublePowerup(SCREEN_WIDTH + 50, y)); break;
            }
        }
    }

    private void actualizarEnemigos(float dt) {
        for (int i = enemigos.size() - 1; i >= 0; i--) {
            Enemy e = enemigos.get(i);
            e.actualizar(dt);
            if (e.getX() < -200) enemigos.remove(i);
        }
    }

    private void actualizarPowerups(float dt) {
        for (int i = powerups.size() - 1; i >= 0; i--) {
            Powerup p = powerups.get(i);
            p.actualizar(dt);
            if (p.fueraDePantalla()) powerups.remove(i);
        }
    }

    private void comprobarColisiones() {
        // Balas del jugador vs enemigos
        for (int i = enemigos.size() - 1; i >= 0; i--) {
            Enemy enemigo = enemigos.get(i);
            for (int j = jugador.getBalas().size() - 1; j >= 0; j--) {
                if (enemigo.getLimites().overlaps(jugador.getBalas().get(j).getLimites())) {
                    jugador.getBalas().remove(j);
                    enemigo.setVidas(enemigo.getVidas() - 1);
                    if (enemigo.estaMuerto()) {
                        jugador.sumarPuntos(enemigo.getPuntos());
                        enemigos.remove(i);
                        if (ConfigurationManager.isSoundEnabled())
                            ResourceManager.getSound(SOUND_HIT).play();
                    }
                    break;
                }
            }
        }

        // Enemigos vs jugador
        for (int i = enemigos.size() - 1; i >= 0; i--) {
            Enemy enemigo = enemigos.get(i);
            if (jugador.getLimites().overlaps(enemigo.getLimites())) {
                jugador.recibirGolpe();
                enemigos.remove(i);
                if (ConfigurationManager.isSoundEnabled())
                    ResourceManager.getSound(SOUND_HIT).play();
            }

            // Tinta del pez globo vs jugador
            if (i < enemigos.size() && enemigo instanceof PezGloboEnemy) {
                List<Burbuja> tinta = ((PezGloboEnemy) enemigo).getTinta();
                for (int j = tinta.size() - 1; j >= 0; j--) {
                    if (jugador.getLimites().overlaps(tinta.get(j).getLimites())) {
                        jugador.recibirGolpe();
                        tinta.remove(j);
                        if (ConfigurationManager.isSoundEnabled())
                            ResourceManager.getSound(SOUND_HIT).play();
                    }
                }
            }
        }

        // Powerups vs jugador
        for (int i = powerups.size() - 1; i >= 0; i--) {
            if (jugador.getLimites().overlaps(powerups.get(i).getLimites())) {
                powerups.get(i).aplicar(jugador);
                powerups.remove(i);
                if (ConfigurationManager.isSoundEnabled())
                    ResourceManager.getSound(SOUND_POWERUP).play();
            }
        }
    }

    public int getNivelActual() { return nivelActual; }
    public boolean isPartidaTerminada() { return partidaTerminada; }

    @Override
    public void dispose() {
        if (musica != null) musica.stop();
    }
}
