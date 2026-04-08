package programacion.multimedia.aa2.manager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

import java.util.ArrayList;
import java.util.List;

import static programacion.multimedia.aa2.util.Constants.GAME_NAME;

public class ConfigurationManager {

    private static final Preferences prefs = Gdx.app.getPreferences(GAME_NAME);

    // Sonido y música
    public static boolean isSoundEnabled() {
        return prefs.getBoolean("sound_effects", true);
    }

    public static void setSoundEnabled(boolean enabled) {
        prefs.putBoolean("sound_effects", enabled);
        prefs.flush();
    }

    public static boolean isMusicEnabled() {
        return prefs.getBoolean("music", true);
    }

    public static void setMusicEnabled(boolean enabled) {
        prefs.putBoolean("music", enabled);
        prefs.flush();
    }

    // Dificultad: 1 = Fácil, 2 = Medio, 3 = Difícil
    public static int getDifficulty() {
        return prefs.getInteger("difficulty", 2);
    }

    public static void setDifficulty(int difficulty) {
        prefs.putInteger("difficulty", difficulty);
        prefs.flush();
    }

    // Top 10 puntuaciones
    public static void saveScore(String playerName, int score) {
        List<int[]> scores = getRawScores();
        List<String> names = getRawNames();

        scores.add(new int[]{score});
        names.add(playerName);

        // Ordenar de mayor a menor (burbuja simple)
        for (int i = 0; i < scores.size() - 1; i++) {
            for (int j = 0; j < scores.size() - i - 1; j++) {
                if (scores.get(j)[0] < scores.get(j + 1)[0]) {
                    int[] tmpScore = scores.get(j);
                    scores.set(j, scores.get(j + 1));
                    scores.set(j + 1, tmpScore);

                    String tmpName = names.get(j);
                    names.set(j, names.get(j + 1));
                    names.set(j + 1, tmpName);
                }
            }
        }

        // Guardar solo top 10
        int limit = Math.min(scores.size(), 10);
        for (int i = 0; i < limit; i++) {
            prefs.putInteger("score_" + i + "_value", scores.get(i)[0]);
            prefs.putString("score_" + i + "_name", names.get(i));
        }
        prefs.putInteger("score_count", limit);
        prefs.flush();
    }

    public static List<String> getTopScoresFormatted() {
        List<String> result = new ArrayList<>();
        int count = prefs.getInteger("score_count", 0);
        for (int i = 0; i < count; i++) {
            String name = prefs.getString("score_" + i + "_name", "---");
            int value = prefs.getInteger("score_" + i + "_value", 0);
            result.add((i + 1) + ". " + name + "  —  " + value);
        }
        return result;
    }

    // Helpers privados
    private static List<int[]> getRawScores() {
        List<int[]> list = new ArrayList<>();
        int count = prefs.getInteger("score_count", 0);
        for (int i = 0; i < count; i++) {
            list.add(new int[]{prefs.getInteger("score_" + i + "_value", 0)});
        }
        return list;
    }

    private static List<String> getRawNames() {
        List<String> list = new ArrayList<>();
        int count = prefs.getInteger("score_count", 0);
        for (int i = 0; i < count; i++) {
            list.add(prefs.getString("score_" + i + "_name", "---"));
        }
        return list;
    }
}
