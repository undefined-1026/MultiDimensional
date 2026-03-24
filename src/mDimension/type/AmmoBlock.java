package mDimension.type;


import mindustry.entities.TargetPriority;
import mindustry.entities.bullet.LightningBulletType;
import mindustry.gen.Building;
import mindustry.world.Block;
import mindustry.world.meta.BlockGroup;
import mindustry.world.meta.BuildVisibility;
import mindustry.world.meta.Env;

public class AmmoBlock extends Block {

    public AmmoBlock(String name){
        super(name);
        placeablePlayer = false;
        // Set buildVisibility to shown so constructors can see and build this bloc
        buildVisibility = BuildVisibility.shown;
        solid = true;
        destructible = true;
        group = BlockGroup.walls;
        buildCostMultiplier = 6f;
        canOverdrive = false;
        drawDisabled = false;
        crushDamageMultiplier = 5f;
        priority = TargetPriority.wall;

        //it's a wall of course it's supported everywhere
        envEnabled = Env.any;

    }


}
