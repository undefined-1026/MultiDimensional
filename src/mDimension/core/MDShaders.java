package mDimension.core;



import arc.graphics.gl.Shader;
import mindustry.graphics.Shaders;



public class MDShaders {
    public static Shader well;


    public static void init(){
        well = new Shaders.SurfaceShader("well");
    }
}
