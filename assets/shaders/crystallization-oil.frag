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
const float noiseScl = 180;

float sinf(float d) {
    return (sin((u_time / 40)*0.2 + d) +sin((u_time / 20) * 0.2 + d)*0.5+sin((u_time / 90)*0.2 + d)*2+sin((u_time / 180)*0.2+ d)*3)*10;
}
void main(){
    vec2 c = v_texCoords*u_resolution + u_campos;
    vec2 v = vec2(1.0/u_resolution.x, 1.0/u_resolution.y);
    vec2 coords = vec2(c.x / v.x + u_campos.x, c.y / v.y + u_campos.y);

    float btime = u_time / 8000.0;
    float noise_1 = (texture2D(u_noise, (c) / noiseScl + vec2(btime) * vec2(-0.9, 0.8)).r + texture2D(u_noise, (c) / noiseScl + vec2(btime * 1.1) * vec2(-0.8, -1.0)).r) / 2.0;
    float noise_2 = (texture2D(u_noise, (c) / (noiseScl*5.5) + vec2(btime) * vec2(-0.9, 0.8)).r + texture2D(u_noise, (c) / (noiseScl*5.5) + vec2(btime * 1.1) * vec2(-0.8, -1.0)).r) / 2.0;
    vec4 color = texture2D(u_texture,v_texCoords);

    float timeFactor = u_time / 1.5f;

    float stripe1 = pow((sin((c.y + 1.732*c.x +sinf(114.0))/scl)+1.0)*0.5,3.0);
    float stripe2 = pow((sin((c.y - 1.732*c.x + sinf(1219.0))/scl)+1.0)*0.5,3.0);
    float stripe3 = pow((sin((c.y*2.0 + sinf(325.0))/scl)+1.0)*0.5,3.0);

    float wave = sin((c.y+ c.x + timeFactor)/(scl*8.0));

    float height = pow((stripe1+stripe2+stripe3)*0.45,0.7)*0.20 + wave *0.02+ noise_1 *0.78 + noise_2 * 0.16;



    if(height>0.68){
        color.rgb *= vec3(1.8,1.8,1.7);
    }else if(height >0.6){
        color.rgb *= (vec3(0.82,0.7,0.7) * ((noise_2*noise_2)/3 + 0.7));
    }else if(height < 0.42){
        color.rgb *= vec3(0.75,0.65,0.58);
    }

    gl_FragColor = color;


}
