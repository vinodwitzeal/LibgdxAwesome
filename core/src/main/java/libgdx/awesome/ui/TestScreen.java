package libgdx.awesome.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.TextureArray;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

import libgdx.awesome.EmojiWidget;
import libgdx.awesome.confetti.ConfettiWidget;
import libgdx.awesome.drawable.BoxDrawable;
import libgdx.awesome.widget.SideShowWidget;

public class TestScreen extends UIScreen {
    public TestScreen() {
        super();
    }

    @Override
    protected void buildUI() {
//        Texture sdfTexture=new Texture("sdf_rect.png");
//        sdfTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        Table mainTable = new Table();
        mainTable.setFillParent(true);
////
////
////
//        BoxDrawable solidDrawable=new BoxDrawable(new TextureRegion(sdfTexture))
////                .linearGradient(Color.valueOf("#FF1188"), Color.valueOf("#88BBFF"),0)
////                .radialGradient(Color.valueOf("#FF1188"), Color.valueOf("#88BBFF"),new Vector2(150,150),150)
//                .fillSolid(Color.valueOf("FFbb66"))
////                .fillNone()
//                .outline(4.0f,Color.valueOf("#FFFFFF"))
//                .radius(20,40,20,20);

//        mainTable.add(new Image(solidDrawable)).width(400).height(800).padBottom(20).row();
        SideShowWidget.SideShowWidgetBuilder sideShowWidgetBuilder = new SideShowWidget.SideShowWidgetBuilder(new ShapeRenderer());
        sideShowWidgetBuilder.radius(200)
                .from(width * 0.2f, height * 0.4f)
                .to(-width *0.2f, height * 0.4f+width *0.4f)
                .steps(20)
                .velocity(0.5f)
                .backgroundColor(Color.valueOf("#FF00AA"))
                .stepsWidth(10)
                .arrowRegion(new Texture("img_star.png"))
                .arrowSpawnDuration(0.5f)
                .arrowMoveDuration(2.0f);
        stage.addActor(sideShowWidgetBuilder.build());
//
//        BoxDrawable linearDrawable=new BoxDrawable(new TextureRegion(sdfTexture))
//                .linearGradient(Color.valueOf("#FF1188"), Color.valueOf("#88BBFF"),90)
////                .radialGradient(Color.valueOf("#FF1188"), Color.valueOf("#88BBFF"),new Vector2(150,150),150)
////                .fillSolid(Color.valueOf("FFbb66"))
////                .fillNone()
//                .outline(20.0f,Color.valueOf("#FFFFFF"))
//                .radius(200);

//        mainTable.add(new Image(linearDrawable)).width(800).height(400).padBottom(20).row();

//        ConfettiWidget confettiWidget=new ConfettiWidget();
//        confettiWidget.debugAll();
//
//        mainTable.add(confettiWidget).expandX().fillX().pad(20).height(400);
//
//        mainTable.debugAll();

//        TextureAtlas atlas=new TextureAtlas("fold_maaro.atlas");
//        EmojiWidget emojiWidget=new EmojiWidget(atlas.findRegions("fold_maaro"),0.02f);
//        mainTable.add(emojiWidget).width(width*0.5f).height(width*0.5f);
//        mainTable.row();
//
//        EmojiWidget emojiWidget2=new EmojiWidget(atlas.findRegions("fold_maaro"),0.02f);
//        mainTable.add(emojiWidget2).width(width*0.25f).height(width*0.25f);

        stage.addActor(mainTable);
    }
}
