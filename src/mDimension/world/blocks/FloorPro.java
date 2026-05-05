package mDimension.world.blocks;

import mDimension.tool.AutotileSlice;
import mindustry.world.blocks.environment.Floor;

public class FloorPro extends Floor {

    public FloorPro(String name){
        super(name);
    }

    @Override
    public void load() {
        super.load();
        if(autotile)autotileRegions = AutotileSlice.sliceAndPack(this);
    }

};
