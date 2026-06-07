package mDimension.core;

import arc.graphics.Color;
import arc.graphics.g2d.TextureRegion;
import arc.graphics.gl.Shader;
import mindustry.graphics.Shaders;

import static mindustry.graphics.Shaders.getShaderFi;

public class MDShaders {
    public static Shader well;


    public static void init(){
        well = new Shaders.SurfaceShader("well");
    }
}
