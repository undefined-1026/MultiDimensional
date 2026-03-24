package mDimension.tool;

import arc.math.Mathf;
import arc.math.Rand;
import arc.util.Time;
import arc.util.Tmp;
import mindustry.graphics.Drawf;

public class Drawff {
    public static Rand rand = new Rand();

    public static void prominence(long seed,float x,float y,int amount,float timeScl,float len,float width,float radius,float h){
        rand.setSeed(seed);
        float rad = (float) Math.sqrt(radius*radius-(width*width*0.25f));
        for(int i = 0;i<amount;i++){
            float length = Math.max(0,
                    Mathf.absin(Time.time+rand.range(1145f),timeScl,h)-h+1);
            length*=len * (Mathf.absin(Time.time+rand.range(325),timeScl*Mathf.PI*Mathf.E/4,0.8f)+0.6f);
            float angle = i*(360f/amount);
            Tmp.v1.set(1,0).setAngle(angle).setLength(rad).add(x,y);
            float vx = Tmp.v1.x,vy = Tmp.v1.y;
            Drawf.tri(vx,vy,width,length,angle);
        }
    }
}
