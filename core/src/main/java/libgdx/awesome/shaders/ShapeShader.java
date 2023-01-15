package libgdx.awesome.shaders;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Vector2;

public class ShapeShader extends ShaderProgram {
    private final String DIMENSION = "u_dimension";
    private final String RADIUS = "u_radius";
    private final String OUTLINE = "u_outline";
    private final String OUTLINE_COLOR = "u_outlineColor";
    private final String FILL_TYPE="u_fillType";
    private final String START_COLOR = "u_startColor";
    private final String END_COLOR = "u_endColor";
    private final String ANGLE = "u_angle";
    private final String RADIAL_POSITION="u_radialPosition";
    private final String GRADIENT_RADIUS="u_gradientRadius";

    public ShapeShader() {
        super(Gdx.files.internal("shaders/shapes.vert"), Gdx.files.internal("shaders/shapes.frag"));
    }

    public void setDimension(Vector2 dimension) {
        setUniformf(DIMENSION, dimension);
    }

    public void setRadius(float topLeft, float topRight, float bottomRight, float bottomLeft) {
        setUniformf(RADIUS, bottomRight,topRight,bottomLeft,topLeft);
    }

    public void setOutline(float outline) {
        setUniformf(OUTLINE, outline);
    }

    public void setOutlineColor(Color outlineColor) {
        setUniformf(OUTLINE_COLOR, outlineColor);
    }

    public void setFillType(int fillType){
        setUniformi(FILL_TYPE,fillType);
    }

    public void setGradient(Color start, Color end) {
        setUniformf(START_COLOR, start);
        setUniformf(END_COLOR, end);
    }

    public void setGradientAngle(float angle) {
        setUniformf(ANGLE, angle);
    }

    public void setRadialPosition(Vector2 position){
        setUniformf(RADIAL_POSITION,position);
    }

    public void setGradientRadius(float gradientRadius){
        setUniformf(GRADIENT_RADIUS,gradientRadius);
    }
}
