#ifdef GL_ES
#define LOWP lowp
precision mediump float;
#else
#define LOWP
#endif

uniform vec2 u_resolution;
uniform float u_radius;
uniform vec4 u_shadowColor;
uniform float u_shadowRadius;
uniform vec2 u_shadowOffset;

void main() {
    vec2 st = gl_FragCoord.xy / u_resolution.xy;

    // Calculate distance from the center of the rounded rectangle
    float distance = length(st - vec2(0.5, 0.5));

    // Apply shadow effect based on distance and radius
    float shadow = smoothstep(u_radius - u_shadowRadius, u_radius + u_shadowRadius, distance);

    // Apply shadow offset
    vec2 shadowOffset = u_shadowOffset * (1.0 - shadow);
    vec2 shadowCoord = st - shadowOffset;

    // Mix the original color and shadow color
    vec4 originalColor = gl_FragColor;
    vec4 shadowColor = u_shadowColor * originalColor.a * shadow;
    gl_FragColor = mix(originalColor, shadowColor, shadow);
}
