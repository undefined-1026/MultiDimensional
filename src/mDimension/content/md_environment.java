package mDimension.content;

import arc.Core;
import arc.graphics.Color;
import arc.graphics.Texture;
import arc.graphics.gl.Shader;
import arc.util.Time;
import mindustry.Vars;
import mindustry.content.Liquids;
import mindustry.content.StatusEffects;
import mindustry.graphics.CacheLayer;
import mindustry.graphics.Shaders;
import mindustry.ui.dialogs.SettingsMenuDialog;
import mindustry.world.Block;
import mindustry.world.Tile;
import mindustry.world.blocks.environment.Floor;
import mindustry.world.blocks.environment.StaticWall;
import mindustry.world.meta.Attribute;

import static mindustry.Vars.renderer;

public class md_environment {
    public static Block
            crystallization_oil_floor,magnetic_shale_wall,magnetic_shale_stone;
    public static CacheLayer
            threeWave
    ;
    public static void load(){
        loadCacheLayer();
        crystallization_oil_floor = new Floor("crystallization-oil-floor"){{
            liquidDrop = md_liquids.crystallization_oil;
            cacheLayer = threeWave;
            supportsOverlay = true;
            overlayAlpha = 0.6f;
            drownTime = 80f;
            status = StatusEffects.tarred;
            statusDuration = 150f;
            speedMultiplier = 0.9f;
            variants = 0;
            liquidMultiplier = 1.5f;
            isLiquid = true;

            emitLight = true;
            lightRadius = 18f;
            lightColor = Color.valueOf("FFE299").a(0.05f);
            obstructsLight = true;
            forceDrawLight = false;
        }};

        magnetic_shale_wall = new StaticWall("magnetic-shale-wall"){{
            attributes.set(Attribute.sand, 0.7f);
        }};

        magnetic_shale_stone = new Floor("magnetic-shale-stone"){{
            attributes.set(Attribute.water, -0.5f);
        }};
    }

    public static void loadCacheLayer(){
        threeWave = new CacheLayer.ShaderLayer(new Shaders.SurfaceShader("crystallization-oil"));
        CacheLayer.add(threeWave);
    }
}
