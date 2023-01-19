#ifdef GL_ES
#define LOWP lowp
precision mediump float;
#else
#define LOWP
#endif

const int defaultFunction=0;
const int fontFunction=1;
const int drawableFunction=2;

const int fillNone=0;
const int fillSolid=1;
const int fillLinearGradient=2;
const int fillRadialGradient=3;

varying LOWP vec4 v_color;
varying vec2 v_texCoords;
uniform sampler2D u_texture;

uniform int u_functionType;

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

//Fonts
uniform float smoothing;

uniform int shadowEnable;
uniform vec2 shadowOffset;
uniform vec4 shadowColor;
uniform float shadowSmoothing;

uniform int outlineEnable;
uniform float outlineDistance;
uniform vec4 outlineColor;

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

void renderDefault(){
    gl_FragColor = v_color * texture2D(u_texture, v_texCoords);
}

void renderFont(){
    float distance = texture2D(u_texture, v_texCoords).a;
    float alpha = smoothstep(0.5 - smoothing, 0.5 + smoothing, distance);
    vec4 text = vec4(v_color.rgb, v_color.a * alpha);
    vec4 resultText=text;


    if(outlineEnable>0){
        float outlineFactor = smoothstep(0.5 - smoothing, 0.5 + smoothing, distance);
        vec4 color = mix(outlineColor, v_color, outlineFactor);
        float alpha = smoothstep(outlineDistance - smoothing, outlineDistance + smoothing, distance);
        resultText= vec4(color.rgb, color.a * alpha*v_color.a);
    }

    if(shadowEnable>0){
        float shadowDistance = texture2D(u_texture, v_texCoords).a;
        float shadowAlpha = smoothstep(0.5 - shadowSmoothing, 0.5 + shadowSmoothing, shadowDistance);
        vec4 shadow = vec4(shadowColor.rgb, shadowColor.a*shadowAlpha*v_color.a);
        resultText = mix(shadow,resultText,resultText.a);
    }

    gl_FragColor=resultText;
}

void renderDrawable(){
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
        if(u_fillType==fillNone){
            fillColor=vec4(0.0);
        }else if(u_fillType==fillLinearGradient){
            vec2 uv =v_texCoords;
            vec2 origin = vec2(0.5, 0.5);
            uv -= origin;
            float angle = radians(90.0) - radians(u_angle) + atan(uv.y, uv.x);
            float len = length(uv);
            uv = vec2(cos(angle) * len, sin(angle) * len) + origin;
            fillColor = mix(u_endColor, u_startColor, smoothstep(0.0, 1.0, uv.x));
        }else if(u_fillType==fillRadialGradient){
            float distance =dist(u_radialPosition,v_texCoords*u_dimension)/u_gradientRadius;
            if(distance>1.0){
                distance=1.0;
            }
            fillColor = mix(u_endColor, u_startColor, smoothstep(0.0, 1.0, distance));
        }else{
            fillColor=u_fillColor;
        }

        if(fillDist>0.0){
            col=vec4(u_outlineColor.rgb,outlineColor.a*smoothstep(0.0, 0.005, abs(outlineDist)));
        }else{
            if(u_outline>0.0){
                col=mix(u_outlineColor,fillColor,smoothstep(0.0, 0.01, abs(fillDist)));
            }else{
                col=vec4(fillColor.rgb,fillColor.a*smoothstep(0.0, 0.01, abs(fillDist)));
            }
        }
    }
    gl_FragColor = col*v_color;
}


void main()
{
    if(u_functionType==fontFunction){
        renderFont();
    }else if(u_functionType==drawableFunction){
        renderDrawable();
    }else{
        renderDefault();
    }


}

