package mDimension.core;

import arc.graphics.Color;
import arc.graphics.g2d.TextureRegion;
import arc.graphics.gl.Shader;
import mindustry.graphics.Shaders;

import static mindustry.graphics.Shaders.getShaderFi;

public class MDShaders {
    public static Shader wave;
    public static Shader well;


    public static void init(){
        wave = new Shaders.SurfaceShader("crystallization-oil");
        well = new Shaders.SurfaceShader("well");
    }
}
