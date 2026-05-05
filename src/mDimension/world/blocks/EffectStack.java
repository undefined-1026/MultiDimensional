package mDimension.world.blocks;

import arc.graphics.Color;
import arc.math.geom.Position;
import mindustry.entities.Effect;

public class EffectStack extends Effect {
    public Effect[] effects;

    public EffectStack(Effect... effects){
        this.effects = effects;
        for(Effect e:effects){
            lifetime = Math.max(lifetime,e.lifetime);
        }
    }
    @Override
    public void at(Position pos){
        at(pos.getX(), pos.getY(), 0, Color.white, null);
    }

    public void at(Position pos, boolean parentize){
        at(pos.getX(), pos.getY(), 0, Color.white, parentize ? pos : null);
    }

    public void at(Position pos, float rotation){
        at(pos.getX(), pos.getY(), rotation, Color.white, null);
    }
    @Override
    public void at(float x,float y){at(x,y,0,Color.white,null);}
    public void at(float x,float y,float rotation){at(x,y,rotation,Color.white,null);}
    public void at(float x,float y,Color color){at(x,y,0,color,null);}
    public void at(float x,float y,float rotation,Color color){at(x,y,rotation,color,null);}





    @Override
    public void at(float x, float y, float rotation, Color color, Object data){
        for(Effect e:effects) {
            e.at(x, y, rotation, color, data);
        }
    }

}
