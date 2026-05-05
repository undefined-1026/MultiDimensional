package mDimension.world.flux;

import mindustry.world.Block;
import mindustry.world.blocks.power.PowerBlock;
import mindustry.world.meta.BlockGroup;

public class FluxBlock extends Block {
    public FluxBlock(String name){
        super(name);
        update = true;
        solid = true;
        hasPower = true;
        group = BlockGroup.power;
    }
}
