#ifdef GL_ES
#define LOWP lowp
precision mediump float;
#else
#define LOWP
#endif


varying LOWP vec4 v_color;
varying vec2 v_texCoords;
uniform sampler2D u_texture;

uniform vec2 u_size;
uniform float u_radiusTL;  // Top-left radius
uniform float u_radiusTR;  // Top-right radius
uniform float u_radiusBR;  // Bottom-right radius
uniform float u_radiusBL;  // Bottom-left radius

void main() {
    // Calculate the distance from each corner
    vec4 distances = vec4(
    distance(v_texCoords, vec2(u_radiusTL, u_radiusTL)),
    distance(v_texCoords, vec2(u_size.x - u_radiusTR, u_radiusTR)),
    distance(v_texCoords, vec2(u_radiusBL, u_size.y - u_radiusBL)),
    distance(v_texCoords, vec2(u_size.x - u_radiusBR, u_size.y - u_radiusBR))
    );

    // Apply rounded corners
    float roundedCorner = 1.0 - step(0.0, min(min(distances.x, distances.y), min(distances.z, distances.w)));

    // Calculate the interpolation factor for the gradient
//    float gradientFactor = clamp((st.y - u_gradientStart.y) / (u_gradientEnd.y - u_gradientStart.y), 0.0, 1.0);
//
//    // Interpolate the color based on the gradient factor
//    vec4 gradientColor = mix(u_color, vec4(0.0), gradientFactor);

    gl_FragColor = v_color * texture2D(u_texture, v_texCoords) * roundedCorner;
}
