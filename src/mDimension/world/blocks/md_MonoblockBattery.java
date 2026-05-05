package mDimension.world.blocks;

import arc.Core;
import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.TextureRegion;
import arc.math.Mathf;
import arc.util.Time;
import mindustry.gen.Building;
import mindustry.graphics.Layer;
import mindustry.world.blocks.power.Battery;

import java.util.Arrays;

public class md_MonoblockBattery extends Battery {
    private static int[] dpos = new int[]{
            1,1,  -1,1,  -1,-1,  1,-1
    };
    public static boolean[] isSide= new boolean[]{true,false,true,false,false,true,true};
    public TextureRegion[] sideRegions = new TextureRegion[]{null,null,null,null,null,null,null,null};
    public TextureRegion contentCentreRegions,
        contentSideRegions,
        contentIndRehions,
        contentCornerRehions;
    public md_MonoblockBattery(String name){
        super(name);
        enableDrawStatus = false;
        update = true;
    }
    public void init(){
        super.init();
        for(int i =0;i<8;i++){
            sideRegions[i] = Core.atlas.find(name+"-shard"+(i+1));

        }
        contentSideRegions = Core.atlas.find(name+"-content-shard");
        contentCentreRegions = Core.atlas.find(name+"-content");
        contentIndRehions = Core.atlas.find(name+"-content-ind");
        contentCornerRehions = Core.atlas.find(name+"-content-corner");

    }
    public class md_MonoblockBatteryBuild extends BatteryBuild{
        public boolean[] sidesIsLink = new boolean[]{
                      false,
                false,      false,
                      false
        };
        public boolean[] drawCorner = new boolean[]{
                true,       true,

                true,       true
        };
        public boolean[] drawCornerContent = new boolean[]{
                true,       true,

                true,       true
        };


        public float rand = Mathf.randomSeed((this.id* 325L),50f);
        public boolean isReal = false;

        //0 to 1
        public float powerStored = 0;

        public void updateNearby(){
//            Items.sand.description =
//                    ("\ngetLastPowerStored()      :"+this.power.graph.getLastPowerStored())+
//                    ("\ngetBatteryCapacity()      :"+this.power.graph.getBatteryCapacity())+
//                    ("\ngetBatteryStored()        :"+this.power.graph.getBatteryStored())+
//                    ("\ngetTotalBatteryCapacity() :"+this.power.graph.getTotalBatteryCapacity())+
//                    ("\ngetLastCapacity()         :"+this.power.graph.getLastCapacity())+
//                    ("\ngetLastPowerNeeded()      :"+this.power.graph.getLastPowerNeeded())+
//                    ("\ngetPowerBalance()         :"+this.power.graph.getPowerBalance())+
//                    ("\ngetPowerProduced()        :"+this.power.graph.getPowerProduced())+
//                    ("\ngetSatisfaction()         :"+this.power.graph.getSatisfaction())+
//                    ("\ngetLastScaledPowerOut()   :"+this.power.graph.getLastScaledPowerOut())+
//                    ("\ngetLastScaledPowerIn()    :"+this.power.graph.getLastScaledPowerIn())+
//                    ("\nstatus()                  :"+this.power.status);

            super.updateTile();
            float tmp = this.power.graph.getLastPowerStored()/this.power.graph.getTotalBatteryCapacity();
            if((tmp != 0 || isReal)&&powerStored != tmp){
                powerStored = tmp;
                isReal = false;
            }else isReal = true;
            Arrays.fill(drawCorner,true);
            Arrays.fill(drawCornerContent,false);
            for(int i = 0;i<4;i++){
                Building nearby = this.nearby(i);
                if(nearby!=null){
                    if(nearby instanceof BatteryBuild &&nearby.block == this.block&&nearby.team == this.team){
                        sidesIsLink[i] = true;
                        drawCorner[i] = false;
                        drawCorner[(i+3)%4] = false;
                    }else{
                        sidesIsLink[i] = false;
                    }
                }else{
                    sidesIsLink[i] = false;
                }
                Building corner = this.nearby(dpos[i*2],dpos[i*2+1]);
                if(corner!=null){
                    if(corner instanceof BatteryBuild &&corner.block == this.block&&corner.team == this.team){
                        drawCornerContent[i] = true;
                    }
                }
            }
        }
        @Override
        public void draw(){
            updateNearby();
            Draw.z(Layer.block);
            Draw.rect(region,x,y);

            Color fullColor = Color.valueOf("995A46").lerp(Color.valueOf("FFC37A"),powerStored);

            Draw.z(Layer.block-1f);
            Draw.color(fullColor);
            Draw.rect(contentCentreRegions,x,y);
            Draw.reset();
            for(int i = 0;i<4;i++){
                if(!sidesIsLink[i]){
                    Draw.z(Layer.block+0.01f);
                    Draw.rect(sideRegions[i],x,y);
                }else{
                    Draw.color(fullColor);
                    Draw.z(Layer.block-1f);
                    Draw.rect(contentSideRegions,x,y,i*90f);
                    Draw.reset();
                }
                if(drawCorner[i]){
                    Draw.z(Layer.block+0.02f);
                    Draw.rect(sideRegions[i+4],x,y);
                }else if(sidesIsLink[i] && sidesIsLink[(i+1)%4] && drawCornerContent[i]){
                    Draw.color(fullColor);
                    Draw.z(Layer.block+0.01f);
                    Draw.rect(contentCornerRehions,x,y,i*90f);
                    Draw.reset();
                }
                float contentScl = 0.5f*0.25f;
                if(sidesIsLink[0]&&sidesIsLink[1]&&sidesIsLink[2]&&sidesIsLink[3]){
                    contentScl = 0.75f*0.25f;
                    if(drawCornerContent[0]&&drawCornerContent[1]&&drawCornerContent[2]&&drawCornerContent[3]){
                        contentScl = 0.25f;
                    }
                }
                float scl = (Mathf.sin(Time.time/8+rand,3.5f,0.12f)+1f);
                contentScl = contentScl*scl;
                if(this.power.graph.getBatteryStored()>10f){
                    Draw.z(Layer.block+0.03f);
                    Draw.alpha(powerStored*0.5f/scl);
                    Draw.rect(contentIndRehions,x,y,
                            contentIndRehions.width*contentScl*this.power.status,
                            contentIndRehions.height*contentScl*this.power.status
                    );
                    Draw.reset();
                }

            }
            Draw.reset();
        }
    }
}
