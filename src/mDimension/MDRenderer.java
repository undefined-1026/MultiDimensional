package mDimension;

import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import mindustry.core.Renderer;
import mindustry.graphics.Layer;
import mindustry.graphics.Shaders;

import static mDimension.MDShaders.wave;

public class MDRenderer extends Renderer {
    public MDRenderer(){
        super();
    }
    public void init(){super.init();}

    public void draw(){
        Draw.drawRange(Layer.buildBeam, 1f, () -> effectBuffer.begin(Color.clear), () -> {
            effectBuffer.end();
            effectBuffer.blit(wave);
        });
    }
}
