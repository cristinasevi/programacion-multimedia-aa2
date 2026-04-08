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

    private float modificadorDificultad;
    private float tasaSpawnBase;

    public LogicManager() {
        enemigos = new ArrayList<>();
        powerups = new ArrayList<>();
        random = new Random();
        nivelActual = 1;
        timerEnemigo = 0;
        timerPowerup = 0;
        partidaTerminada = false;

        // Leer dificultad guardada y aplicarla
        int dificultad = ConfigurationManager.getDifficulty();
        switch (dificultad) {
            case 1:  // Fácil
                modificadorDificultad = 0.7f;
                tasaSpawnBase = ENEMY_SPAWN_RATE * 1.5f;
                break;
            case 3:  // Difícil
                modificadorDificultad = 1.4f;
                tasaSpawnBase = ENEMY_SPAWN_RATE * 0.7f;
                break;
            default: // Medio
                modificadorDificultad = 1.0f;
                tasaSpawnBase = ENEMY_SPAWN_RATE;
                break;
        }
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
        // En nivel 2 spawn más rápido, modificado por dificultad
        float tasaSpawn = nivelActual == 2 ? tasaSpawnBase * 0.6f : tasaSpawnBase;

        if (timerEnemigo >= tasaSpawn) {
            timerEnemigo = 0;
            float y = random.nextFloat() * (SCREEN_HEIGHT - 100) + 50;
            int tipo = random.nextInt(nivelActual == 2 ? 3 : 2);

            switch (tipo) {
                case 0:
                    enemigos.add(new SubmarinoEnemy(SCREEN_WIDTH + 50, y, modificadorDificultad));
                    break;
                case 1:
                    enemigos.add(new TiburonEnemy(SCREEN_WIDTH + 50, y, jugador, modificadorDificultad));
                    break;
                case 2:
                    enemigos.add(new PezGloboEnemy(SCREEN_WIDTH + 50, y, modificadorDificultad));
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
                continue;
            }

            // Tinta del pez globo vs jugador
            if (enemigo instanceof PezGloboEnemy) {
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
