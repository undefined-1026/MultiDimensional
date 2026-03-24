package mDimension.type;

import arc.func.Cons;
import arc.math.geom.Vec2;
import mindustry.entities.units.BuildPlan;
import mindustry.gen.Building;
import mindustry.world.Block;
import mindustry.world.blocks.production.GenericCrafter;

public class md_BeamDeflector extends GenericCrafter {
    public boolean canDeflectorParticle = false;
    public Vec2 afterRotation = new Vec2(1,0);
    public boolean diagonalFilp  = false;

    public md_BeamDeflector(String name){
        super(name);
        rotate = true;
        rotateDraw = false;
    }

    @Override
    public void flipRotation(BuildPlan plan, boolean x) {
        if(!diagonalFilp) {
            super.flipRotation(plan,x);
        }else if(!x){
            switch (plan.rotation){
                case(0)-> plan.rotation = planRotation(3);
                case(3)-> plan.rotation = planRotation(1);
                case(1)-> plan.rotation = planRotation(2);
                case(2)-> plan.rotation = planRotation(1);
            }
        }else{
            switch (plan.rotation){
                case(0)-> plan.rotation = planRotation(1);
                case(1)-> plan.rotation = planRotation(0);
                case(2)-> plan.rotation = planRotation(3);
                case(3)-> plan.rotation = planRotation(2);
            }
        }
    }

    public class md_BeamDeflectorBuild extends GenericCrafterBuild {

    }
}
