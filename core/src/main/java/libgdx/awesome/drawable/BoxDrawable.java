package libgdx.awesome.drawable;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.utils.BaseDrawable;

import libgdx.awesome.shaders.ShapeShader;

public class BoxDrawable extends BaseDrawable {
    public static final int FILL_NONE=0,FILL_SOLID=1,FILL_LINEAR_GRADIENT=2,FILL_RADIAL_GRADIENT=3;
    private float x,y,width,height;
    private TextureRegion region;
    private ShapeShader shapeShader;
    private ShaderProgram defaultShader;
    private float radius[]=new float[]{0,0,0,0};
    private float outline=0.0f;
    private Color outlineColor=Color.valueOf("#00000000");
    private int fillType=FILL_SOLID;
    private Color fillColor=Color.valueOf("#FFFFFF");
    private Color startColor=Color.valueOf("#00000000"),endColor=Color.valueOf("#00000000");
    private final Vector2 radialPosition=new Vector2();
    private float gradientRadius=0.0f;
    private float angle;

    private float uRadius[]=new float[]{0,0,0,0};
    private float uOutline;
    private float uGradientRadius;
    private Vector2 uRadialPosition=new Vector2();
    public BoxDrawable(TextureRegion region){
        this.region=region;
        ShaderProgram.pedantic=false;
        this.shapeShader=new ShapeShader();
        if (!this.shapeShader.isCompiled()){
            Gdx.app.error("Shader Error",this.shapeShader.getLog());
        }
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
        this.outline=outline;
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
        for (int i=0;i<4;i++){
            uRadius[i]=radius[i]/maxDimension;
        }
        uRadialPosition.set(radialPosition.x/maxDimension,(radialPosition.y)/maxDimension);
        uOutline=outline/maxDimension;
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
        defaultShader=batch.getShader();
        batch.setShader(shapeShader);
        shapeShader.setRadius(uRadius);
        shapeShader.setFillColor(fillColor);
        shapeShader.setOutline(uOutline);
        shapeShader.setOutlineColor(outlineColor);
        shapeShader.setFillType(fillType);
        shapeShader.setGradient(startColor,endColor);
        shapeShader.setGradientAngle(angle);
        shapeShader.setRadialPosition(uRadialPosition);
        shapeShader.setGradientRadius(uGradientRadius);
    }

    private void removeShader(Batch batch){
        batch.flush();
        batch.setShader(defaultShader);
    }
}
