package libgdx.awesome.widget;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ImmediateModeRenderer20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.BSpline;
import com.badlogic.gdx.math.Bezier;
import com.badlogic.gdx.math.CatmullRomSpline;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Path;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Widget;
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SideShowWidget extends WidgetGroup {
    ShapeRenderer shapeRenderer;
    private List<Path<Vector2>> paths;
    private float current=0;
    private int steps;
    private float stepWidth;
    private float velocity;
    private final Vector2 pointA=new Vector2();
    private final Vector2 pointB=new Vector2();
    private Color backgroundColor,stepsColor;
    private TextureRegion arrowRegion;
    private float arrowWidth,arrowHeight;
    private float arrowSpawnDuration;
    private float arrowMoveDuration;
    private float currentArrowSpawn=0.0f;
    private SideShowWidget(SideShowWidgetBuilder builder){
        setTransform(true);
        float width=2.0f*builder.radius+builder.stepsWidth;
        float height=builder.from.dst(builder.to)+width;
        float rotation= builder.to.cpy().sub(builder.from).angleDeg();
        Gdx.app.error("Rotation",rotation+"");
        setSize(width,height);
        setOrigin(width/2.0f,height/2.0f);
        setRotation(rotation-90.0f);
        Vector2 centerPoint=new Vector2(builder.to).sub(builder.from).scl(0.5f).add(builder.from);
        setPosition(centerPoint.x-width/2.0f,centerPoint.y-height/2.0f);
        this.shapeRenderer=builder.shapeRenderer;
        this.backgroundColor=builder.backgroundColor;
        this.stepsColor=builder.stepColor;
        this.steps=builder.steps;
        this.velocity=builder.velocity;
        this.stepWidth=builder.stepsWidth;
        this.arrowSpawnDuration=builder.arrowSpawnDuration;
        this.arrowMoveDuration=builder.arrowMoveDuration;
        this.arrowRegion=builder.arrowRegion;
        this.paths=new ArrayList<>();
    }


    @Override
    public void layout() {
        super.layout();
        float width=getWidth();
        float height=getHeight();
        this.paths.clear();
        float radius=width*0.5f;

        this.arrowWidth=width*0.25f;
        this.arrowHeight=this.arrowWidth*arrowRegion.getRegionHeight()/arrowRegion.getRegionWidth();
        paths.add(new ArcPath(new Vector2(radius,height-radius),radius,0,180));
        paths.add(new LinePath(new Vector2(0,height-radius),new Vector2(0,radius)));
        paths.add(new ArcPath(new Vector2(radius,radius),radius,180,180));
        paths.add(new LinePath(new Vector2(width,radius),new Vector2(width,height-radius)));
    }


    @Override
    public void act(float delta) {
        super.act(delta);
        current+=delta*velocity;
        if(current >= 1.0f) {
            current -= 1.0f;
        }

        if (currentArrowSpawn==0.0f){
            addArrow();
        }
        currentArrowSpawn+=delta;
        if (currentArrowSpawn>=arrowSpawnDuration){
            currentArrowSpawn=0.0f;
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        validate();
        batch.end();
        applyTransform(batch,computeTransform());
        shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
        shapeRenderer.setTransformMatrix(batch.getTransformMatrix());
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        drawBackground();
        drawSteps();
        shapeRenderer.end();
        batch.begin();
        drawChildren(batch,parentAlpha);
        resetTransform(batch);
    }


    protected void drawBackground(){
        super.layout();
        float width=getWidth();
        float height=getHeight();
        float radius=width*0.5f;
        shapeRenderer.setColor(backgroundColor);
        shapeRenderer.arc(radius,height-radius,radius,0,180,20);
        shapeRenderer.rect(0,radius,width,height-2*radius);
        shapeRenderer.arc(radius,radius,radius,180,180,20);
    }

    protected void drawSteps(){
        shapeRenderer.setColor(stepsColor);
        for (Path<Vector2> path:paths){
            float stepValue=1.0f/steps;
            float pathCurrentValue=current;
            for (int i=0;i<steps;i++){
                pathCurrentValue+=stepValue;
                if (pathCurrentValue>1.0f){
                    pathCurrentValue-=1.0f;
                }
                if(i%2==0){
                    float pointAValue=pathCurrentValue;
                    float pointBValue=pointAValue+stepValue;
                    if (pointBValue>1.0f){
                        pointBValue=1.0f;
                    }
                    path.valueAt(pointA,pointAValue);
                    path.valueAt(pointB,pointBValue);
                    shapeRenderer.rectLine(pointA,pointB,stepWidth);
                }
            }
        }
    }
    
    private void addArrow(){
        Image arrowImage=new Image(arrowRegion);
        arrowImage.setSize(arrowWidth,arrowHeight);
        arrowImage.setPosition((getWidth()-arrowWidth)/2.0f,0);
        arrowImage.setColor(1,1,1,0.0f);
        float fadeDuration=0.5f;
        arrowImage.addAction(Actions.sequence(
                Actions.parallel(
                        Actions.fadeIn(fadeDuration),
                        Actions.moveBy(0,getHeight()-arrowHeight,arrowMoveDuration, Interpolation.linear),
                        Actions.delay(arrowMoveDuration-fadeDuration,Actions.fadeOut(fadeDuration))
                ),
                Actions.removeActor()
        ));
        addActor(arrowImage);
    }

    public static class SideShowWidgetBuilder{

        private ShapeRenderer shapeRenderer;
        private float radius;
        private Vector2 from;
        private Vector2 to;
        private int steps=10;
        private float stepsWidth=5.0f;
        private float velocity=0.1f;
        private Color backgroundColor=Color.valueOf("#C7C7C7");
        private Color stepColor= Color.valueOf("#FFFFFF88");

        private TextureRegion arrowRegion;
        
        private float arrowSpawnDuration=0.2f;
        private float arrowMoveDuration=1.0f;
        public SideShowWidgetBuilder(ShapeRenderer shapeRenderer){
            if (shapeRenderer==null){
                throw new IllegalArgumentException("ShapeRenderer is required. Cannot be null.");
            }
            this.shapeRenderer=shapeRenderer;
        }

        public SideShowWidgetBuilder radius(float radius){
            this.radius=radius;
            return this;
        }

        public SideShowWidgetBuilder from(Vector2 from){
            this.from=from;
            return this;
        }

        public SideShowWidgetBuilder from(float x,float y){
           return from(new Vector2(x,y));
        }

        public SideShowWidgetBuilder to(Vector2 to){
            this.to=to;
            return this;
        }

        public SideShowWidgetBuilder steps(int steps){
            this.steps=steps;
            return this;
        }

        public SideShowWidgetBuilder stepsWidth(float stepsWidth){
            this.stepsWidth=stepsWidth;
            return this;
        }

        public SideShowWidgetBuilder to(float x,float y){
            return to(new Vector2(x,y));
        }

        public SideShowWidgetBuilder backgroundColor(Color backgroundColor){
            this.backgroundColor=backgroundColor;
            return this;
        }

        public SideShowWidgetBuilder stepColor(Color stepColor){
            this.stepColor=stepColor;
            return this;
        }

        public SideShowWidgetBuilder arrowRegion(TextureRegion arrowRegion){
            this.arrowRegion=arrowRegion;
            return this;
        }

        public SideShowWidgetBuilder arrowRegion(Texture arrowTexture){
            return arrowRegion(new TextureRegion(arrowTexture));
        }

        public SideShowWidgetBuilder velocity(float velocity){
            this.velocity=velocity;
            return this;
        }

        public SideShowWidgetBuilder arrowSpawnDuration(float arrowSpawnDuration){
            this.arrowSpawnDuration=arrowSpawnDuration;
            return this;
        }

        public SideShowWidgetBuilder arrowMoveDuration(float arrowMoveDuration){
            this.arrowMoveDuration=arrowMoveDuration;
            return this;
        }


        public SideShowWidget build(){
            if (from==null){
                throw new RuntimeException("Starting position cannot be null.");
            }

            if (to==null){
                throw new RuntimeException("Ending position cannot be null.");
            }

            if (radius<=0.0f){
                throw  new RuntimeException("Radius cannot be less than or equal to zero. ");
            }

            if (arrowRegion==null){
                throw new RuntimeException("Arrow Region cannot be null.");
            }

            return new SideShowWidget(this);
        }


    }
}
