package mDimension.type;

import arc.graphics.g2d.Draw;
import arc.graphics.g2d.TextureRegion;
import arc.math.Mathf;
import arc.math.geom.Geometry;
import arc.math.geom.Point2;
import mDimension.tool.AutotileSlice;
import mindustry.world.Tile;
import mindustry.world.blocks.TileBitmask;
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
