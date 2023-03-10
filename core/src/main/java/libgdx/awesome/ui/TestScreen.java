package libgdx.awesome.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

import libgdx.awesome.drawable.BoxDrawable;

public class TestScreen extends UIScreen{
    public TestScreen() {
        super();
    }

    @Override
    protected void buildUI() {
        Texture sdfTexture=new Texture("sdf_rect.png");
        sdfTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        Table mainTable=new Table();
        mainTable.setFillParent(true);



        BoxDrawable solidDrawable=new BoxDrawable(new TextureRegion(sdfTexture))
//                .linearGradient(Color.valueOf("#FF1188"), Color.valueOf("#88BBFF"),0)
//                .radialGradient(Color.valueOf("#FF1188"), Color.valueOf("#88BBFF"),new Vector2(150,150),150)
                .fillSolid(Color.valueOf("FFbb66"))
//                .fillNone()
                .outline(4.0f,Color.valueOf("#FFFFFF"))
                .radius(20,40,20,20);

        mainTable.add(new Image(solidDrawable)).width(100).height(100).padBottom(20).row();

        BoxDrawable linearDrawable=new BoxDrawable(new TextureRegion(sdfTexture))
                .linearGradient(Color.valueOf("#FF1188"), Color.valueOf("#88BBFF"),90)
//                .radialGradient(Color.valueOf("#FF1188"), Color.valueOf("#88BBFF"),new Vector2(150,150),150)
//                .fillSolid(Color.valueOf("FFbb66"))
//                .fillNone()
                .outline(20.0f,Color.valueOf("#FFFFFF"))
                .radius(200);

        mainTable.add(new Image(linearDrawable)).width(800).height(400).padBottom(20).row();

        BoxDrawable radialDrawable=new BoxDrawable(new TextureRegion(sdfTexture))
//                .linearGradient(Color.valueOf("#FF1188"), Color.valueOf("#88BBFF"),0)
                .radialGradient(Color.valueOf("#FF1188"), Color.valueOf("#88BBFF"),new Vector2(200,200),600)
//                .fillSolid(Color.valueOf("FFbb66"))
//                .fillNone()
//                .outline(0.5f)
//                .outlineColor(Color.valueOf("#FFFFFFBB"))
                .radius(100,20,100,20);

        mainTable.add(new Image(radialDrawable)).width(400).height(800).padBottom(20);

        stage.addActor(mainTable);
    }
}
