package mDimension.world.flux;

import mDimension.consumers.ConsumeFlux;
import mindustry.world.blocks.defense.turrets.ItemTurret;

public class FluxTurret extends ItemTurret {
    public float consumeAmount = 10f;
    public FluxTurret(String name){
        super(name);
    }

    @Override
    public void init() {
        super.init();
        if(ConsumeFlux.hasConsume(this)){
            consume(new ConsumeFlux());
        }
    }

    public class FluxTurretBuild extends ItemTurretBuild{

    }
}
