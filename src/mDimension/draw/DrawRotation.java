package mDimension.draw;

import arc.Core;
import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.TextureRegion;
import arc.util.Eachable;
import mindustry.entities.units.BuildPlan;
import mindustry.gen.Building;
import mindustry.world.Block;
import mindustry.world.draw.DrawBlock;

public class DrawRotation extends DrawBlock {
    public String suffix = "-arrow";
    public boolean buildingRotate = false;
    public boolean drawPlan = true;

    public int textureAmount = 1;
    public float layerOffset = 0;
    //把 textureAmount 强制设为4 buildingRotate 设为true,且只需要两张贴图
    public boolean flip = false;
    public Color color;
    public TextureRegion[] regions;

    public DrawRotation(){
    }
    public DrawRotation(String suffix){
        this.suffix = suffix;
    }
    public DrawRotation(String suffix,int textureAmount){
        this.suffix = suffix;
        this.textureAmount = textureAmount;
    }
    public DrawRotation(String suffix,boolean flip){
        this.suffix = suffix;
        this.flip = flip;
    }



    @Override
    public void draw(Building build) {
        float z = Draw.z();
        if(layerOffset != 0) Draw.z(z+layerOffset);
        if(color != null) Draw.color(color);

        Draw.rect(regions[index(build.rotation)],build.x,build.y,buildingRotate?build.rotdeg():0);
        Draw.z(z);
    }

    @Override
    public void drawPlan(Block block, BuildPlan plan, Eachable<BuildPlan> list) {
        if(!drawPlan) return;
        float z = Draw.z();
        if(layerOffset != 0) Draw.z(z+layerOffset);
        Draw.rect(regions[index(plan.rotation)],plan.drawx(),plan.drawy(),buildingRotate?plan.rotation *90:0);
        Draw.z(z);
    }

    @Override
    public TextureRegion[] icons(Block block){
        return new TextureRegion[]{regions[0]};
    }

    @Override
    public void load(Block b){
        if(flip){
            textureAmount =4;
            buildingRotate = true;
            regions = new TextureRegion[textureAmount];
            var t1 = Core.atlas.find(b.name+suffix +1);
            var t2 = new TextureRegion(t1);
            t2.flip(false,true);
            var t3 = Core.atlas.find(b.name+suffix +2);
            var t4 = new TextureRegion(t3);
            t4.flip(false,true);
            regions[0] = t1;
            regions[1] = t2;
            regions[2] = t3;
            regions[3] = t4;
        }else{
            regions = new TextureRegion[textureAmount];
            for(int i = 0; i< textureAmount; i++){
                regions[i] = Core.atlas.find(b.name + suffix+(i+1));
            }
        }

    }
    int index(int i){
        switch (textureAmount){
            case(1)->{
                return 0;
            }
            case(2)->{
                return i<2?0:1;
            }
            case(4)->{
                return i;
            }
        }
        return 0;
    }
}
