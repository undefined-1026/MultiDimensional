package mDimension.consumers.modules;

import arc.struct.IntSeq;
import arc.struct.Seq;
import arc.util.io.Reads;
import arc.util.io.Writes;
import mDimension.consumers.ConsumeFlux;
import mDimension.world.flux.FluxGraph;
import mindustry.gen.Building;
import mindustry.world.modules.BlockModule;

/**复制 PowerModule*/
public class FluxModule extends BlockModule {
    public FluxModule(){}

    /**
     * In case of unbuffered consumers, this is the percentage (1.0f = 100%) of the demanded power which can be supplied.
     * Blocks will work at a reduced efficiency if this is not equal to 1.0f.
     * In case of buffered consumers, this is the percentage of power stored in relation to the maximum capacity.
     */
    //public float status = 0.0f;
    // public float effectivity = 0f;
    public float fluxAmount = 0f;
    public boolean fusing = false;
    public FluxGraph graph = new FluxGraph();
    public IntSeq links = new IntSeq();

    @Override
    public void write(Writes write){
        write.s(links.size);
        for(int i = 0; i < links.size; i++){
            write.i(links.get(i));
        }
        write.f(fluxAmount);
    }

    @Override
    public void read(Reads read){
        links.clear();
        short amount = read.s();
        for(int i = 0; i < amount; i++){
            links.add(read.i());
        }
        fluxAmount = read.f();
        graph = new FluxGraph();
        if(Float.isNaN(fluxAmount) || Float.isInfinite(fluxAmount)) fluxAmount = 0f;
    }



}
