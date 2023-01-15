package libgdx.awesome;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import de.eskalon.commons.core.ManagedGame;
import de.eskalon.commons.screen.ManagedScreen;
import de.eskalon.commons.screen.transition.ScreenTransition;
import de.eskalon.commons.screen.transition.impl.BlendingTransition;
import libgdx.awesome.ui.TestScreen;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class MainController extends ManagedGame<ManagedScreen, ScreenTransition> {

    @Override
    public void create() {
        super.create();
        SpriteBatch spriteBatch=new SpriteBatch();
        screenManager.addScreen("test",new TestScreen());
        screenManager.addScreenTransition("blending",new BlendingTransition(spriteBatch,0.2f));
        screenManager.pushScreen("test","blending");
    }
}