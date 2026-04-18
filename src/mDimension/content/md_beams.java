package mDimension.content;

import arc.graphics.Color;
import arc.graphics.g2d.Fill;
import arc.graphics.g2d.Lines;
import arc.math.Mathf;
import mDimension.tool.Drawff;
import mDimension.type.Beam;

public class md_beams {
    public static Beam
            near_infrared_ligth,ultraviolet_ligth,nihility_light;
    public static void load() {
        near_infrared_ligth = new Beam("near-infrared-laser", Color.valueOf("F9BDA3")) {{
            energyLevel = 3;
            lenght = 18;
            laserDrawer = l -> {
                Beam.basicDraw(l, Color.valueOf("ff9863"), 2.5f, 10f, 31f,4f);
                Beam.basicDraw(l, Color.valueOf("F9F2EF"), 0.6f, 10f, 101,4f);
                Beam.DrawPacket(l,Color.valueOf("F9F2EF"), 1.2f,1f,101.1f,80f);
            };
        }};
        ultraviolet_ligth = new Beam("ultraviolet-light", Color.valueOf("EA61F9")) {{
            energyLevel = 5;
            lenght = 12;
            laserDrawer = l -> {
                Beam.basicDraw(l, Color.valueOf("EA61F9"), 2.2f, 10f, 31f,4f);
                Beam.basicDraw(l, Color.valueOf("F8F1F9"), 0.5f, 10f, 101,4f);
                Beam.DrawPacket(l,Color.valueOf("F8F1F9"), 1f,1f,101.1f,60f);
            };
        }};

        nihility_light = new Beam("nihility_light",Color.valueOf("fff080")){{
            energyLevel = 9;
            lenght = 6;
            laserDrawer = l->{
                Beam.basicDraw(l, Color.valueOf("fff080"), 3f, 10f, 31f,3f,(x,y,rad)->{
                    Drawff.jellyCircle((long) (l.id+x),x,y,rad*0.92f,60,24);
                });
                Beam.basicDraw(l, Color.valueOf("101000"), 1.8f, 10f, 31.1f,4.2f,(x,y,rad)->{
                    Drawff.jellyCircle((long) (l.id+x),x,y,rad,60,18);
                });
                Beam.particleFlowDraw(l,Color.valueOf("fff0c0"),4,1.8f,0.7f,5f,101f);
                Beam.basicDraw(l,Color.valueOf("fff0c0"), 0.5f,1f,101.1f,5.5f,(x,y,r,head)->{
                    float scl = head?1f:0.5f;
                    Drawff.jellyCircle((long) (l.id+x),x,y,r*2.24f*scl,60f,12);
                });
            };
        }};
    }

}
