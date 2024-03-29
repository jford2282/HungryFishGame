package com.hungryfishgame.manager;

import java.io.IOException;

import org.andengine.audio.music.Music;
import org.andengine.audio.music.MusicFactory;
import org.andengine.audio.sound.Sound;
import org.andengine.audio.sound.SoundFactory;
import org.andengine.engine.Engine;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;
import org.andengine.opengl.texture.ITexture;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.atlas.bitmap.BuildableBitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.source.IBitmapTextureAtlasSource;
import org.andengine.opengl.texture.atlas.buildable.builder.BlackPawnTextureAtlasBuilder;
import org.andengine.opengl.texture.atlas.buildable.builder.ITextureAtlasBuilder.TextureAtlasBuilderException;
import org.andengine.opengl.texture.bitmap.BitmapTextureFormat;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.debug.Debug;
 
import com.hungryfishgame.GameActivity;
import com.hungryfishgame.FollowCamera;
import android.graphics.Color;


public class ResourceManager {
	
	private static final ResourceManager INSTANCE = new ResourceManager();
    public Engine engine;
    public GameActivity activity;
    public FollowCamera camera;
    public VertexBufferObjectManager vbom;
    public Font font;
   
    public Sound jumpSound;
    public Sound dieSound;
    public Sound scoreSound;
    public Music gameMusic;
   
    public static ResourceManager getInstance() {
            return INSTANCE;
    }
   
    public ITextureRegion splash_region;
    public ITextureRegion playbutton_region;
    public ITextureRegion parallaxBackLayerRegion;
    public ITextureRegion parallaxMidLayerRegion;
    public ITextureRegion parallaxFrontLayerRegion;
    public ITiledTextureRegion playerRegion;
    public TextureRegion pillarRegion;
    public TextureRegion pillarRegionUp;
    public TextureRegion foodRegion;
    
    public ITextureRegion menuBackgroundRegion;
   
    private BitmapTextureAtlas splashTextureAtlas;
    private BuildableBitmapTextureAtlas menuTextureAtlas;
    private BuildableBitmapTextureAtlas gameTextureAtlas;
    private BitmapTextureAtlas repeatingGroundAtlas;


   
    public static void prepareManager(Engine engine, GameActivity activity, FollowCamera camera, VertexBufferObjectManager vbom) {
            getInstance().engine = engine;
            getInstance().activity = activity;
            getInstance().camera = camera;
            getInstance().vbom = vbom;
    }
   
    public void loadMenuResources() {
            loadMenuGraphic();
            loadMenuSound();
            loadMenuFonts();
           
    }
   
    private void loadMenuFonts() {
            FontFactory.setAssetBasePath("font/");
            final ITexture mainFontTexture = new BitmapTextureAtlas(activity.getTextureManager(), 256, 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
            font = FontFactory.createStrokeFromAsset(activity.getFontManager(), mainFontTexture, activity.getAssets(), "font.ttf", 50, true, Color.WHITE, 2, Color.BLACK);
            font.load();
           
    }

    private void loadMenuSound() {
            // TODO Auto-generated method stub
           
    }
   
    private void loadGameSound() {
            try {
                    jumpSound = SoundFactory.createSoundFromAsset(activity.getSoundManager(), activity, "sfx/Bloop.wav");
                    dieSound = SoundFactory.createSoundFromAsset(activity.getSoundManager(), activity, "sfx/dieSound.mp3");
                    scoreSound = SoundFactory.createSoundFromAsset(activity.getSoundManager(), activity, "sfx/crunchSound.mp3");
                    gameMusic = MusicFactory.createMusicFromAsset(activity.getMusicManager(), activity, "sfx/bubbles.mp3");
            } catch (IOException e) {
                    e.printStackTrace();
            }
    }

    private void loadMenuGraphic() {
            BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
            menuTextureAtlas = new BuildableBitmapTextureAtlas(activity.getTextureManager(), 1024, 1024, TextureOptions.BILINEAR);
            playbutton_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(menuTextureAtlas, activity, "playbutton.png");
            menuBackgroundRegion =  BitmapTextureAtlasTextureRegionFactory.createFromAsset(menuTextureAtlas, activity, "menubackground.png");
    try
    {
                    this.menuTextureAtlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 1, 0));
                    this.menuTextureAtlas.load();
            }
    catch (final TextureAtlasBuilderException e)
    {
                    Debug.e(e);
            }
   
    //loadGameSound();
    }

    public void loadGameResources() {
            BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
           
            repeatingGroundAtlas = new BitmapTextureAtlas(activity.getTextureManager(), 200, 200, TextureOptions.REPEATING_BILINEAR_PREMULTIPLYALPHA);
            parallaxFrontLayerRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(repeatingGroundAtlas, activity, "ground.png", 0, 0);
            repeatingGroundAtlas.load();
           
            gameTextureAtlas = new BuildableBitmapTextureAtlas(activity.getTextureManager(), 2048, 2048, BitmapTextureFormat.RGBA_8888, TextureOptions.BILINEAR);
            parallaxBackLayerRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTextureAtlas, activity, "background.png");
            parallaxMidLayerRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTextureAtlas, activity, "parallax_background_layer_mid.png");
            //parallaxFrontLayerRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTextureAtlas, activity, "parallax_background_layer_front.png");
            playerRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(gameTextureAtlas, activity, "player.png", 2, 1);
            pillarRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTextureAtlas, activity, "pillar.png");
            pillarRegionUp = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTextureAtlas, activity, "pillarUp.png");
            foodRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTextureAtlas, activity, "fishfood.png");
            
           
            try
    {
                    this.gameTextureAtlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 1, 0));
                    this.gameTextureAtlas.load();
            }
    catch (final TextureAtlasBuilderException e)
    {
                    Debug.e(e);
            }
           
            loadGameSound();
           
    }
   
    public void loadSplashResources() {
            BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
            splashTextureAtlas = new BitmapTextureAtlas(activity.getTextureManager(), 300, 300, TextureOptions.BILINEAR);
            splash_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(splashTextureAtlas, activity, "splash.png",0,0);
            splashTextureAtlas.load();
    }

    public void unloadSplashScreen() {
            splashTextureAtlas.unload();
            splash_region = null;          
    }
   
    public void unloadMenuTexture() {
            menuTextureAtlas.unload();     
    }

    public void loadMenuTextures() {
            menuTextureAtlas.load();
           
    }

    public void unloadGameTextures() {
            gameTextureAtlas.unload();
           
    }
   
   

}
	

