package libgdx.awesome.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import de.eskalon.commons.screen.ManagedScreen;

public abstract class UIScreen extends ManagedScreen {
    protected Stage stage;
    protected float screenWidth,screenHeight;
    protected float width,height;
    public UIScreen() {
        super();
    }

    @Override
    protected void create() {
        screenWidth=Gdx.graphics.getWidth();
        screenHeight=Gdx.graphics.getHeight();
        width= 200.0f;
        height=width*screenHeight/screenWidth;
        FitViewport fitViewport=new FitViewport(width,height);
        stage=new Stage(fitViewport);
        buildUI();
    }

    protected abstract void buildUI();

    @Override
    public void hide() {

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0.5f,0.5f,0.5f,0.5f);
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void dispose() {

    }
}
