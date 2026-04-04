package mDimension.type;

import arc.Core;
import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.TextureRegion;
import arc.scene.ui.layout.Table;
import arc.util.Eachable;
import arc.util.Time;
import arc.util.io.Reads;
import arc.util.io.Writes;
import mindustry.entities.units.BuildPlan;
import mindustry.gen.Building;
import mindustry.type.Liquid;
import mindustry.world.Block;
import mindustry.world.blocks.ItemSelection;
import mindustry.world.meta.BlockGroup;

import static mindustry.Vars.content;

public class LiquidUnloader extends Block {
    public TextureRegion centerRegion, topRegion,sideRegion;

    public float speed = 1;
    public boolean allowCoreUnload = true;
    public LiquidUnloader(String name){
        super(name);
        update = true;
        solid = false;
        hasLiquids = true;
        liquidCapacity = 100;
        configurable = true;
        outputsLiquid = true;
        saveConfig = true;
        noUpdateDisabled = true;
        displayFlow = false;
        group = BlockGroup.liquids;
        clearOnDoubleTap = true;
        rotate = true;

        config(Liquid.class, (LiquidUnloader.LiquidUnloaderBuild tile, Liquid l) -> tile.sortLiquid = l);
        configClear((LiquidUnloader.LiquidUnloaderBuild tile) -> tile.sortLiquid = null);
    }

    @Override
    public void init() {
        super.init();
        centerRegion = Core.atlas.find(name+"-center");
        topRegion = Core.atlas.find(name+"-top");
        sideRegion = Core.atlas.find(name+"-side");
    }

    @Override
    public void drawPlanConfig(BuildPlan plan, Eachable<BuildPlan> list){
        drawPlanConfigCenter(plan, plan.config, name+"-center", false);
    }

    @Override
    public void drawPlan(BuildPlan plan, Eachable<BuildPlan> list, boolean valid) {
        super.drawPlan(plan, list, valid);
        float  z= Draw.z();
        Draw.z(z+0.01f);
        Draw.color(Color.white);
        Draw.rect(topRegion,plan.x,plan.y,plan.rotation*90f);
        Draw.z(z);
        Draw.reset();

    }

    public class LiquidUnloaderBuild extends Building{
        public Liquid sortLiquid;

        @Override
        public void updateTile() {

            float amount = amount();

            Building back = back();
            if(sortLiquid != null&&back != null
            &&back.block.hasLiquids && back.liquids.get(sortLiquid)>0.0001f && back.canUnload()
            && this.liquids.get(sortLiquid)<block.liquidCapacity-0.01f){
                float minAmount = Math.min(block.liquidCapacity-this.liquids.get(sortLiquid),amount);
                back.liquids.remove(sortLiquid,minAmount);
                this.handleLiquid(back,sortLiquid,minAmount);
            }

            unloaderLiquid();
        }
        public float amount(){
            return Time.delta  *  this.timeScale*this.efficiency * speed;
        }

        public void unloaderLiquid(){
            if(this.front() == null)return;
            if(sortLiquid!=null){
                if(this.liquids.get(sortLiquid)>0.01f)transferLiquid(this.front(),Math.min(this.liquids.get(sortLiquid),amount()),sortLiquid);
            }else if(liquids.current() !=null){
                if(this.liquids.get(liquids.current())>0.01f)transferLiquid(this.front(),Math.min(this.liquids.get(liquids.current()),amount()),liquids.current());
            }
        }

        @Override
        public boolean acceptLiquid(Building source, Liquid liquid){
            return false;
        }


        @Override
        public void draw() {
            super.draw();
            float z = Draw.z();
            Draw.rect(sideRegion,x,y,(rotation%2)*90);
            if(sortLiquid != null){
                Draw.color(sortLiquid.color);
                Draw.rect(centerRegion,x,y,rotdeg());
            }
            Draw.z(z+0.01f);
            Draw.color(Color.white);
            Draw.rect(topRegion,x,y,rotdeg());
            Draw.reset();
            Draw.z(z);
        }

        @Override
        public void buildConfiguration(Table table){
            ItemSelection.buildTable(LiquidUnloader.this, table, content.liquids(), () -> sortLiquid, this::configure, selectionRows, selectionColumns);
        }

        @Override
        public Liquid config(){
            return sortLiquid;
        }

        @Override
        public void write(Writes write){
            super.write(write);
            write.s(sortLiquid == null ? -1 : sortLiquid.id);
        }

        @Override
        public void read(Reads read, byte revision){
            super.read(read, revision);
            int id = revision == 1 ? read.s() : read.b();
            sortLiquid = id == -1 ? null : content.liquid(id);
        }
    }
}
