package mDimension.plante;

import arc.graphics.Color;
import arc.math.geom.Vec3;
import arc.struct.*;
import arc.util.noise.Simplex;
import mDimension.content.md_environment;
import mindustry.content.Blocks;
import mindustry.maps.generators.BaseGenerator;
import mindustry.maps.generators.PlanetGenerator;
import mindustry.world.Block;
import mindustry.world.TileGen;

public class DepicilonPlanetGenerator extends PlanetGenerator {
    //alternate, less direct generation
    public static boolean indirectPaths = false;
    //random water patches
    public static boolean genLakes = false;
    public Color EmissiveColor = Color.valueOf("fffaa0");

    {
        seed = 1145;
    }

    public Block[] blockArr = new Block[]{
            md_environment.crystallization_oil_floor,
            md_environment.brownSandFloor,
            md_environment.magnetic_shale_stone
    };

    ObjectMap<Block, Block> dec = ObjectMap.of(
            Blocks.sporeMoss, Blocks.sporeCluster,
            Blocks.moss, Blocks.sporeCluster,
            Blocks.taintedWater, Blocks.water,
            Blocks.darksandTaintedWater, Blocks.darksandWater
    );

    BaseGenerator basegen = new BaseGenerator();
    float heightYOffset = 42.7f;
    float scl = 5f;
    float waterOffset = 0.04f;
    float heightScl = 1.01f;
    static float[] count = {0,0};

    @Override
    protected void genTile(Vec3 position, TileGen tile) {
        float height = rawHeight(position);
        if(height<0.48f){
            tile.block = Blocks.air;
        }else{
            tile.block = md_environment.magnetic_shale_wall;
        }

    }

    @Override
    protected void generate(){
    }
    public boolean isEmissive(){
        return true;
    }

    @Override
    public float getHeight(Vec3 position){
        float height = rawHeight(position);
        if (height>0.72f){
            height += 0.3f;
        }else if (height > 0.68f) {
            height += 0.15f;
        }else if (height > 0.58f) {
            height -= 0.1f;
        }else if (height <0.42f){
            height -=0.2f;
        }
        return height * 0.5f;
    }

    @Override
    public void getColor(Vec3 position, Color out){
//            Block block = getBlock(position);
//            //replace salt with sand color
//            if(block == Blocks.salt) block = Blocks.sand;
//            out.set(block.mapColor).a(1f - block.albedo);
        float height = rawHeight(position);
        float latit = getAbsLatitude(position);
        latit = (float)Math.pow(latit,0.7f);
        Color toC = Color.valueOf("FFFFE8");
        Color c = Color.valueOf("524727");
        if(height>0.72f){
            c.lerp(toC,0.8f*latit);
        }else if(height>0.68f){
            c.lerp(toC,0.55f*latit);
        }else if(height>0.58f){
            c.lerp(toC,0.35f*latit);
        }else if(height<0.42f){
            c.lerp(toC,0.2f*latit);
        }
        out.set(c);
    }

    @Override
    public void getEmissiveColor(Vec3 position, Color out) {
        float height = rawHeight(position);
        if(height<0.4f){
            out.set(EmissiveColor);
            out.lerp(Color.clear,Math.max(0,(height-0.25f)*2.66F));
        }

    }

    // -1~~0~~1  ->  -90~~0~~90
    float getLatitude(Vec3 v){
        return (float) (Math.asin(v.y)/(Math.PI/2));
    }
    float getAbsLatitude(Vec3 v){
        return (float) (Math.abs(Math.asin(v.y)/(Math.PI/2)));
    }

    float rawHeight(Vec3 position) {
        float height = Simplex.noise3d(114, 7, 0.5f, 1f / 3f, position.x * scl, position.y * scl + heightYOffset, position.z * scl) * heightScl;
        return height;
    }

}