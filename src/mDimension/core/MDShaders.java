package mDimension.core;

import arc.graphics.Color;
import arc.graphics.g2d.TextureRegion;
import arc.graphics.gl.Shader;
import mindustry.graphics.Shaders;

import static mindustry.graphics.Shaders.getShaderFi;

public class MDShaders {
    public static FisheyeShader fisheye;
    public static Shader wave;
    public static Shader well;


    public static void init(){
        fisheye = new FisheyeShader("fisheye");
        wave = new Shaders.SurfaceShader("crystallization-oil");
        well = new Shaders.SurfaceShader("well");
    }
    public static class FisheyeShader extends Shader{
        public FisheyeShader(String frag){
            super(getShaderFi("default.vert"), getShaderFi(frag + ".frag"));
        }

        public float progress;
        //Alpha changes the opacity of *everything*, while the provided batch color only changes the outline
        public float alpha = 1f;
        public TextureRegion region;
        public float time;
        public Color color = new Color(1,1,1,1);

        @Override
        public void apply(){
            setUniformf("u_progress", progress);
            setUniformf("u_time", time);
            setUniformf("u_alpha", alpha);
            setUniformf("u_color", color);

            if(region.texture == null){
                setUniformf("u_uv", 0f, 0f);
                setUniformf("u_uv2", 1f, 1f);
                setUniformf("u_texsize", 1, 1);
            }else{
                setUniformf("u_uv", region.u, region.v);
                setUniformf("u_uv2", region.u2, region.v2);
                setUniformf("u_texsize", region.texture.width, region.texture.height);
            }
        }


    }
}
