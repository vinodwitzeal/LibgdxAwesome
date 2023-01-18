#ifdef GL_ES
#define LOWP lowp
precision mediump float;
#else
#define LOWP
#endif

varying LOWP vec4 v_color;
varying vec2 v_texCoords;
uniform sampler2D u_texture;

uniform vec2 u_dimension;
uniform vec4 u_radius;
uniform vec4 u_fillColor;
uniform float u_outline;
uniform vec4 u_outlineColor;
uniform int u_fillType;
uniform vec4 u_startColor;
uniform vec4 u_endColor;
uniform float u_angle;
uniform vec2 u_radialPosition;
uniform float u_gradientRadius;

float sdRoundBox(in vec2 p, in vec2 b, in vec4 r)
{
    r.xy = (p.x>0.0)?r.xy : r.zw;
    r.x  = (p.y>0.0)?r.x  : r.y;
    vec2 q = abs(p)-b+r.x;
    return min(max(q.x, q.y), 0.0) + length(max(q, 0.0)) - r.x;
}

float sdCircle( vec2 p, float r )
{
    return length(p) - r;
}

float dist(vec2 p0, vec2 pf)
{
    return sqrt((pf.x - p0.x) * (pf.x - p0.x) + (pf.y - p0.y) * (pf.y - p0.y));
}


void main()
{
    vec2 point=2.0*v_texCoords*u_dimension-u_dimension;
    float outlineDist=sdRoundBox(point,u_dimension,2.0*u_radius);
    vec4 col;
    if(outlineDist>0.0){
        col=vec4(0.0);
    }else{
        vec2 fillDim=u_dimension-2.0*vec2(u_outline);
        vec4 fillRadius=u_radius-u_outline;
        float fillDist=sdRoundBox(point,fillDim,2.0*fillRadius);

        vec4 fillColor;
        if(u_fillType==0){
            fillColor=vec4(0.0);
        }else if(u_fillType==2){
            vec2 uv =v_texCoords;
            vec2 origin = vec2(0.5, 0.5);
            uv -= origin;
            float angle = radians(90.0) - radians(u_angle) + atan(uv.y, uv.x);
            float len = length(uv);
            uv = vec2(cos(angle) * len, sin(angle) * len) + origin;
            fillColor = mix(u_endColor, u_startColor, smoothstep(0.0, 1.0, uv.x));
        }else if(u_fillType==3){
            float distance =dist(u_radialPosition,v_texCoords*u_dimension)/u_gradientRadius;
            if(distance>1.0){
                distance=1.0;
            }
            fillColor = mix(u_endColor, u_startColor, smoothstep(0.0, 1.0, distance));
        }else{
            fillColor=u_fillColor;
        }

        if(fillDist>0.0){
            col=mix(u_outlineColor,vec4(0.0), 1.0-smoothstep(0.0, 0.01, abs(outlineDist)));
        }else{
            col=mix(fillColor,u_outlineColor, 1.0-smoothstep(0.0, 0.01, abs(fillDist)));
        }
    }
    gl_FragColor = col*v_color;
}

