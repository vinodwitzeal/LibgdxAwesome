package libgdx.awesome.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

import libgdx.awesome.drawable.BoxDrawable;

public class TestScreen extends UIScreen{
    public TestScreen() {
        super();
    }

    @Override
    protected void buildUI() {
        BoxDrawable shapeDrawable=new BoxDrawable(new TextureRegion(new Texture("sdf_rect.png")))
//                .linearGradient(Color.valueOf("#FF1188"), Color.valueOf("#88BBFF"),0)
//                .radialGradient(Color.valueOf("#FF1188"), Color.valueOf("#88BBFF"),new Vector2(150,150),150)
                .fillSolid(Color.valueOf("FFbb66"))
//                .fillNone()
                .outline(4.0f)
                .outlineColor(Color.valueOf("#FFFFFF"))
                .radius(150,40,20,150);
        Image image=new Image(shapeDrawable);
        image.setSize(360,300);
        image.setPosition((width-image.getWidth())/2f,(height-image.getHeight())/2f);
        stage.addActor(image);
    }
}
