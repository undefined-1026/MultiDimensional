package mDimension.content;

import arc.Core;
import arc.graphics.Color;
import arc.graphics.Texture;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.TextureRegion;
import arc.graphics.gl.Shader;
import arc.math.Mathf;
import arc.math.geom.Geometry;
import arc.math.geom.Point2;
import arc.util.Time;
import mDimension.tool.AutotileSlice;
import mDimension.type.FloorPro;
import mindustry.Vars;
import mindustry.content.Blocks;
import mindustry.content.Items;
import mindustry.content.Liquids;
import mindustry.content.StatusEffects;
import mindustry.graphics.CacheLayer;
import mindustry.graphics.Shaders;
import mindustry.ui.dialogs.SettingsMenuDialog;
import mindustry.world.Block;
import mindustry.world.Tile;
import mindustry.world.blocks.TileBitmask;
import mindustry.world.blocks.environment.Floor;
import mindustry.world.blocks.environment.OreBlock;
import mindustry.world.blocks.environment.StaticWall;
import mindustry.world.blocks.environment.TiledFloor;
import mindustry.world.meta.Attribute;

import static mindustry.Vars.renderer;

public class md_environment {
    public static Block
            crystallization_oil_floor,magnetic_shale_wall,magnetic_shale_stone,brownSandFloor,brownSandWall,
    light_shale_wall,pure_light_shale_wall,darkCrystallineStoneWall,darkRedStoneWall,
            inlayMetalTiles4,darkCrystalFloor,darkCrystallineStone,darkDenseRedStone,darkRedStone,
    //ore
    oreAluminium,WallOreTitanium;
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

        brownSandFloor = new Floor("brown-sand-floor"){{
            itemDrop = Items.sand;
            attributes.set(Attribute.oil,1.2f);
        }};

        brownSandWall = new StaticWall("brown-sand-wall"){{
            brownSandFloor.asFloor().wall = this;
            attributes.set(Attribute.sand, 2f);
        }};

        magnetic_shale_wall = new StaticWall("magnetic-shale-wall"){{
            attributes.set(Attribute.sand, 0.7f);
        }};

        magnetic_shale_stone = new Floor("magnetic-shale-stone"){{
            attributes.set(Attribute.water, -0.2f);
        }};

        oreAluminium = new OreBlock("ore-aluminium",md_items.aluminium){{
            oreDefault = true;
            oreThreshold = 0.846f;
            oreScale = 24.428572f;
        }};

        WallOreTitanium = new OreBlock("ore-wall-titanium",Items.titanium){{
            wallOre = true;
        }};

        light_shale_wall = new StaticWall("light-stone-wall"){{
        }};
        pure_light_shale_wall = new StaticWall("pure-light-stone-wall"){{
        }};

        inlayMetalTiles4 = new FloorPro("inlay-metal-tiles-1"){{
            autotile = true;
            drawEdgeOut = false;
            drawEdgeIn = false;
            mapColor = Color.valueOf("FFDB82");
        }};

        darkCrystalFloor = new Floor("dark-crystal-floor"){{
            variants = 4;
        }};

        darkCrystallineStone = new Floor("dark-crystalline-stone"){{
            variants = 5;
        }};

        darkCrystallineStoneWall = new StaticWall("dark-crystalline-stone-wall"){{
            variants = 4;
            darkCrystallineStone.asFloor().wall = darkCrystalFloor.asFloor().wall = this;
        }};

        darkRedStone = new Floor("dark-red-stone"){{
            variants=4;
        }};
        darkDenseRedStone = new Floor("dark-dense-red-stone"){{
            variants=4;
        }};
        darkRedStoneWall = new StaticWall("dark-red-stone-wall"){{
            darkDenseRedStone.asFloor().wall = darkRedStone.asFloor().wall = this;
        }};


    }

    public static void loadCacheLayer(){
        threeWave = new CacheLayer.ShaderLayer(new Shaders.SurfaceShader("crystallization-oil"));
        CacheLayer.add(threeWave);
    }
}