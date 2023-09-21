package libgdx.awesome;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Widget;
import com.badlogic.gdx.utils.Array;

//
// Created by Vinod(vinod.kumar@witzeal.com)on 31/01/23.
//
// Copyright (c) 2023 Witzeal Technologies Private Limited. All rights reserved.
//
public class EmojiWidget extends Widget {
    private Animation<TextureRegion> animation;
    private TextureRegion region;
    private float currentTime;
    public EmojiWidget(Array<TextureAtlas.AtlasRegion> regions,float frameDuration){
        animation=new Animation<TextureRegion>(frameDuration,regions, Animation.PlayMode.LOOP);
        currentTime=0.0f;
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        region=animation.getKeyFrame(currentTime);
        if (animation.isAnimationFinished(currentTime)){
            currentTime=0.0f;
        }
        currentTime+=delta;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        validate();
        if (region==null)return;
        batch.draw(region,getX(),getY(),getOriginX(),getOriginY(),getWidth(),getHeight(),getScaleX(),getScaleY(),getRotation());
    }
}
