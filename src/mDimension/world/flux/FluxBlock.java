package mDimension.world.flux;

import arc.util.io.Reads;
import arc.util.io.Writes;
import mDimension.consumers.ConsumeFlux;
import mDimension.consumers.modules.FluxModule;
import mindustry.content.Items;
import mindustry.gen.Building;
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

    public class FluxBlockBuild extends Building {
        @Override
        public void write(Writes write) {
            super.write(write);
            FluxModule flux = ConsumeFlux.flux(this);
            flux.write(write);
        }

        @Override
        public void read(Reads read, byte revision) {
            super.read(read,revision);
            FluxModule flux = ConsumeFlux.flux(this);
            flux.read(read);
        }
    }
}
