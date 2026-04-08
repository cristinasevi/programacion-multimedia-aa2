package programacion.multimedia.aa2.util;

public class Constants {

    // Pantalla
    public static final int SCREEN_WIDTH = 800;
    public static final int SCREEN_HEIGHT = 600;
    public static final String GAME_NAME = "juegopeces";

    // Jugador
    public static final float PLAYER_SPEED = 250f;
    public static final float BULLET_SPEED = 400f;
    public static final float BULLET_RATE = 0.3f;
    public static final int PLAYER_LIVES = 3;

    // Enemigos — velocidades
    public static final float SUBMARINO_SPEED = 80f;
    public static final float TIBURON_SPEED = 150f;
    public static final float PEZ_GLOBO_SPEED = 100f;
    public static final float PEZ_GLOBO_SHOOT_RATE = 2f;

    // Enemigos — puntos al eliminarlos
    public static final int SUBMARINO_VALUE = 5;
    public static final int TIBURON_VALUE = 15;
    public static final int PEZ_GLOBO_VALUE = 10;

    // Spawn - Tiempo en aparecer
    public static final float ENEMY_SPAWN_RATE = 2f;
    public static final float POWERUP_SPAWN_RATE = 8f;

    // Powerups — duración en segundos
    public static final float SHIELD_DURATION = 5f;
    public static final float SPEED_DURATION = 5f;
    public static final float DOUBLE_SHOT_DURATION = 5f;
    public static final float SPEED_MULTIPLIER = 1.5f;

    // Niveles
    public static final int LEVEL_2_SCORE = 200;

    // Jugador
    public static final String PLAYER_SWIM = "swim-normal";
    public static final String PLAYER_TURN = "swim-turning";
    public static final String PLAYER_UP = "swim-diag-up";
    public static final String PLAYER_DOWN = "swim-diag-down";
    public static final String PLAYER_OUCH = "fish-ouch";

    // Enemigos
    public static final String TIBURON = "tiburon";
    public static final String PEZ_GLOBO = "pez_globo";
    public static final String SUBMARINO = "submarino";

    // Proyectiles
    public static final String BURBUJA = "burbuja";
    public static final String BURBUJA_PROYECTIL = "burbuja_proyectil";

    // Powerups
    public static final String POWERUP_ESCUDO = "estrella_mar";
    public static final String POWERUP_VELOCIDAD = "caballito_mar";
    public static final String POWERUP_DOBLE = "concha";

    // Fondos (se cargan como Texture, no desde el atlas)
    public static final String FONDO1 = "backgrounds/fondo1.png";
    public static final String FONDO2 = "backgrounds/fondo2.png";

    // Audio
    public static final String SOUND_SHOOT = "bubble-pop.mp3";
    public static final String SOUND_HIT = "explosion.mp3";
    public static final String MUSIC_GAME = "ocean-waves.mp3";
    public static final String SOUND_POWERUP = "powerup.mp3";
}
