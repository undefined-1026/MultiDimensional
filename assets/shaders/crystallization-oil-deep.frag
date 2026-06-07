//precision mediump float;

uniform sampler2D u_texture;
uniform sampler2D u_noise;
uniform vec2 u_campos;
uniform vec2 u_resolution;
uniform highp float u_time;

in vec2 v_texCoords;

const float scl = 15.0;
const float noiseScl = 450.0;

float sinf(float d) {
    return (sin((u_time / 40.0) * 0.2 + d)
    + sin((u_time / 20.0) * 0.2 + d) * 0.5
    + sin((u_time / 90.0) * 0.2 + d) * 2.0
    + sin((u_time / 180.0) * 0.2 + d) * 3.0) * 10.0;
}

vec2 center(vec2 p, float w, float dx) {
    vec2 q = vec2(p.x + p.y + dx, p.x - p.y + dx);
    float cx = floor(q.x / w) * w + 0.5 * w;
    float cy = floor(q.y / w) * w + 0.5 * w;
    return vec2((cx + cy) * 0.5, (cx - cy) * 0.5);
}

float f(float x) {
    return 2.0 * abs(x - 0.5);
}

void main() {
    vec2 c = v_texCoords * u_resolution + u_campos;
    vec2 v = vec2(1.0 / u_resolution.x, 1.0 / u_resolution.y);
    vec2 coords = vec2(c.x / v.x + u_campos.x, c.y / v.y + u_campos.y);

    float btime = u_time / 4000.0;
    float noise_1 = (texture(u_noise, c / noiseScl + vec2(btime) * vec2(-0.9, 0.8)).r
    + texture(u_noise, c / noiseScl + vec2(btime * 1.1) * vec2(-0.8, -1.0)).r) / 2.0;
    float noise_2 = (texture(u_noise, c / (noiseScl * 5.5) + vec2(btime) * vec2(-0.9, 0.8)).r
    + texture(u_noise, c / (noiseScl * 5.5) + vec2(btime * 1.1) * vec2(-0.8, -1.0)).r) / 2.0;

    float width = 7.0;
    float gridX = (mod(c.x + c.y + u_time / 50.0, width) / width);
    float gridY = (mod(c.x - c.y + u_time / 50.0, width) / width);
    float textureScl = gridX * gridY + 0.5;
    vec2 o = center(c, width, u_time / 50.0);
    float noiseO = (texture(u_noise, o / (noiseScl * 0.02) + vec2(btime) * vec2(-0.9, 0.8)).r
    + texture(u_noise, o / noiseScl / 2.0 + vec2(btime * 1.1) * vec2(-0.8, -1.0)).r) / 2.0;
    noiseO = pow(noiseO, 2.0);

    float alpha = f(gridX) * f(gridY) * ((noiseO + 0.5) * 0.5 + 0.2) + noiseO * 2.3 + 0.3;

    vec2 vc = v_texCoords.xy + (vec2(
    texture(u_noise, c / (noiseScl * 2.0) + vec2(btime) * vec2(-0.9, 0.8)).r,
    texture(u_noise, c / (noiseScl * 2.0) + vec2(btime * 1.1) * vec2(0.8, -1.0)).r
    ) - vec2(0.5)) * 10.0 * textureScl / u_resolution;

    vc.xy = clamp(vc.xy, 0.0, 1.0);
    vec4 color = texture(u_texture, vc);

    float timeFactor = u_time / 1.5;
    float height = noise_1 * 0.84 + noise_2 * 0.16;

    if (height > 0.553 && height < 0.567) {
        color.rgb *= vec3(1.8, 1.8, 1.5);
    } else if (height > 0.54 && height < 0.58) {
        color.rgb *= vec3(1.4, 1.4, 1.2);
    }

    if (color.r < 0.01) {
        color.rgb = vec3(51.0, 43.0, 27.0) / 255.0;
    }

    color.rgb *= alpha;
    gl_FragColor = color;
}