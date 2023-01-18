package libgdx.awesome.drawable;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.utils.BaseDrawable;

import libgdx.awesome.shaders.ShapeShader;

public class BoxDrawable extends BaseDrawable {
    private final String FUNCTION_TYPE="u_functionType";
    private final String DIMENSION = "u_dimension";
    private final String RADIUS = "u_radius";
    private final String FILL_COLOR="u_fillColor";
    private final String OUTLINE = "u_outline";
    private final String OUTLINE_COLOR = "u_outlineColor";
    private final String FILL_TYPE="u_fillType";
    private final String START_COLOR = "u_startColor";
    private final String END_COLOR = "u_endColor";
    private final String ANGLE = "u_angle";
    private final String RADIAL_POSITION="u_radialPosition";
    private final String GRADIENT_RADIUS="u_gradientRadius";

    public static final int FILL_NONE=0,FILL_SOLID=1,FILL_LINEAR_GRADIENT=2,FILL_RADIAL_GRADIENT=3;
    private float x,y,width,height;
    private TextureRegion region;
    private float radius[]=new float[]{0,0,0,0};
    private float outline=0.0f;
    private Color outlineColor=Color.valueOf("#00000000");
    private int fillType=FILL_SOLID;
    private Color fillColor=Color.valueOf("#FFFFFF");
    private Color startColor=Color.valueOf("#00000000"),endColor=Color.valueOf("#00000000");
    private final Vector2 radialPosition=new Vector2();
    private float gradientRadius=0.0f;
    private float angle;

    private final Vector2 uDimension=new Vector2();
    private float uRadius[]=new float[]{0,0,0,0};
    private float uOutline=0.0f;
    private final Vector2 uRadialPosition=new Vector2();
    private float uGradientRadius=0.0f;
    public BoxDrawable(TextureRegion region){
        this.region=region;
    }

    public BoxDrawable radius(float radius){
        return this.radius(radius,radius,radius,radius);
    }

    public BoxDrawable radius(float topLeft,float topRight,float bottomRight,float bottomLeft){
        radius[0]=bottomRight;
        radius[1]=topRight;
        radius[2]=bottomLeft;
        radius[3]=topLeft;
        return this;
    }



    public BoxDrawable outline(float outline){
        if (outline<1.0f)return this;
        this.outline=outline+3.0f;
        return this;
    }

    public BoxDrawable outlineColor(Color outlineColor){
        this.outlineColor=outlineColor;
        return this;
    }

    public BoxDrawable fillNone(){
        this.fillType=FILL_NONE;
        return this;
    }

    public BoxDrawable fillSolid(Color color){
        this.fillType=FILL_SOLID;
        this.fillColor=color;
        return this;
    }

    public BoxDrawable linearGradient(Color startColor,Color endColor,float angle){
        this.fillType=FILL_LINEAR_GRADIENT;
        this.startColor=startColor;
        this.endColor=endColor;
        this.angle=angle;
        return this;
    }


    public BoxDrawable radialGradient(Color startColor,Color endColor,Vector2 position,float radius){
        this.fillType=FILL_RADIAL_GRADIENT;
        this.startColor=startColor;
        this.endColor=endColor;
        this.radialPosition.set(position);
        this.gradientRadius=radius;
        return this;
    }

    private void validate(float x,float y,float width,float height){
        if (this.x==x && this.y==y && this.width==width && this.height==height)return;
        this.x=x;
        this.y=y;
        this.width=width;
        this.height=height;

        float maxDimension=Math.max(width,height);
        uDimension.set(width/maxDimension,height/maxDimension);
        uOutline=outline/maxDimension;
        for (int i=0;i<4;i++){
            uRadius[i]=radius[i]/maxDimension;
        }
        uRadialPosition.set(radialPosition.x,height-radialPosition.y).scl(1.0f/maxDimension);
        uGradientRadius=gradientRadius/maxDimension;
    }


    @Override
    public void draw(Batch batch, float x, float y, float width, float height) {
        validate(x,y,width,height);
        applyShader(batch);
        batch.draw(region,x,y,width,height);
        removeShader(batch);
    }

    private void applyShader(Batch batch){
        batch.flush();
        ShaderProgram shader=batch.getShader();
        shader.setUniformi(FUNCTION_TYPE,2);
        shader.setUniformf(DIMENSION,uDimension);
        shader.setUniformf(RADIUS,uRadius[0],uRadius[1],uRadius[2],uRadius[3]);
        shader.setUniformi(FILL_TYPE,fillType);
        shader.setUniformf(FILL_COLOR,fillColor);
        shader.setUniformf(OUTLINE,uOutline);
        shader.setUniformf(OUTLINE_COLOR,outlineColor);
        shader.setUniformf(START_COLOR,startColor);
        shader.setUniformf(END_COLOR,endColor);
        shader.setUniformf(RADIAL_POSITION,uRadialPosition);
        shader.setUniformf(GRADIENT_RADIUS,uGradientRadius);
    }

    private void removeShader(Batch batch){
        batch.flush();
        batch.getShader().setUniformi(FUNCTION_TYPE,0);
    }
}
