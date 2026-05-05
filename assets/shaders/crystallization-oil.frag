//#define HIGHP
uniform sampler2D u_texture;
uniform sampler2D u_noise;
//相机
uniform vec2 u_campos;
//分辨率
uniform vec2 u_resolution;

uniform float u_time;
//纹理坐标
varying vec2 v_texCoords;

const float scl = 15;
const float noiseScl = 450;

float sinf(float d) {
    return (sin((u_time / 40)*0.2 + d) +sin((u_time / 20) * 0.2 + d)*0.5+sin((u_time / 90)*0.2 + d)*2+sin((u_time / 180)*0.2+ d)*3)*10;
}
vec2 center(vec2 p,float w,float dx){
    vec2 q = vec2(p.x+p.y +dx,p.x-p.y +dx);
    float cx = floor(q.x/w)*w+0.5*w;
    float cy = floor(q.y/w)*w+0.5*w;

    return vec2((cx+cy)*0.5,(cx-cy)*0.5);
};
float f(float x){
    return 2*abs(x-0.5);
}
void main(){
    vec2 c = v_texCoords*u_resolution + u_campos;
    vec2 v = vec2(1.0/u_resolution.x, 1.0/u_resolution.y);
    vec2 coords = vec2(c.x / v.x + u_campos.x, c.y / v.y + u_campos.y);

    float btime = u_time / 4000.0;
    float width =13;
    float gridX = (mod(c.x+c.y + u_time/50,width)/width);
    float gridY = (mod(c.x-c.y + u_time/50,width)/width);
    float textureScl = gridX*gridY+0.5;
    vec2 o = center(c,width,u_time/50);
    float noiseO = (texture2D(u_noise, o / (noiseScl*0.02) + vec2(btime) * vec2(-0.9, 0.8)).r + texture2D(u_noise, o / noiseScl/2 + vec2(btime * 1.1) * vec2(-0.8, -1.0)).r) / 2.0;
    noiseO = pow(noiseO,2);
    //vec2 face = c-o;

    float alpha = f(gridX)*f(gridY)*((noiseO+0.5)*0.5+0.2)+noiseO*2.3+0.3;
    //float alpha = (2-length(normalize(face)-normalize(u_campos.xy-o.xy)))*0.25 +1;

    vec2 vc = v_texCoords.xy + (vec2(
    texture2D(u_noise, (c) / (noiseScl*2) + vec2(btime) * vec2(-0.9, 0.8)).r,
    texture2D(u_noise, (c) / (noiseScl*2) + vec2(btime * 1.1) * vec2(0.8, -1.0)).r
    ) - vec2(0.5)) * 13.0 *textureScl/ u_resolution;
    vc.xy = clamp(vc.xy,0,1);
    vec4 color = texture2D(u_texture,vc);

    color.rgb*=alpha;

    gl_FragColor = color;
}
