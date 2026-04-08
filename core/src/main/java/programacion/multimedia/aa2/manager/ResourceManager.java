package programacion.multimedia.aa2.manager;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

public class ResourceManager {

    private static AssetManager assetManager = new AssetManager();

    public static void loadAllResources() {
        assetManager.load("juegopeces.atlas", TextureAtlas.class);
        assetManager.load("backgrounds/fondo1.png", Texture.class);
        assetManager.load("backgrounds/fondo2.png", Texture.class);

        loadSounds();
        loadMusics();
    }

    public static boolean update() {
        return assetManager.update();
    }

    private static void loadSounds() {
        assetManager.load("sounds/bubble-pop.mp3", Sound.class);
        assetManager.load("sounds/explosion.mp3", Sound.class);
        assetManager.load("sounds/powerup.mp3", Sound.class);
    }

    private static void loadMusics() {
        assetManager.load("sounds/ocean-waves.mp3", Music.class);
    }

    public static Sound getSound(String name) {
        return assetManager.get("sounds/" + name, Sound.class);
    }

    public static Music getMusic(String name) {
        return assetManager.get("sounds/" + name, Music.class);
    }

    public static Texture getTexture(String path) {
        return assetManager.get(path, Texture.class);
    }

    public static TextureRegion getRegion(String name) {
        return assetManager.get("juegopeces.atlas", TextureAtlas.class).findRegion(name);
    }

    public static Array<TextureAtlas.AtlasRegion> getRegions(String name) {
        return assetManager.get("juegopeces.atlas", TextureAtlas.class).findRegions(name);
    }

    public static void dispose() {
        assetManager.dispose();
    }
}
