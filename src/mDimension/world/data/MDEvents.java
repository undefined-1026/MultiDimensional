package mDimension.world.data;

import arc.Events;
import arc.struct.Seq;
import arc.util.Interval;
import mDimension.consumers.ConsumeBeam;
import mDimension.consumers.ConsumeFlux;
import mDimension.consumers.modules.ExtraModule;
import mDimension.consumers.modules.FluxModule;
import mindustry.content.Blocks;
import mindustry.content.Items;
import mindustry.game.EventType;
import mindustry.gen.Building;
import mindustry.world.blocks.defense.turrets.ItemTurret;

import static mindustry.Vars.world;

public class MDEvents {
    public static Interval timer;
    public static Seq<Building> out = new Seq<>();
    public static  void load(){
        timer = new Interval(3);
        Events.run(EventType.Trigger.update,()->{
            if(timer.get(0,5*60)){
                ConsumeBeam.free();
                for(ExtraModule<?> mods :ExtraModule.allModule){
                    mods.freeAllIf(b->b.dead);
                }
            }
        });
        Events.on(EventType.BlockDestroyEvent.class,e->{

            Building b = e.tile.build;
            if(b!=null){
                ConsumeFlux cons = ConsumeFlux.getConsume(b);
                if(cons!=null){
                    FluxModule flux = cons.getModule(b);

                    if(flux.fusing){
                        ItemTurret t = (ItemTurret)(Blocks.scathe);
                        t.ammoTypes.get(Items.carbide).spawnUnit.weapons.get(0).bullet.create(b,b.x,b.y,0);
                    }
                }
            }
        });

    }
}
