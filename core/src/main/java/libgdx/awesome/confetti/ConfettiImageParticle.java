package libgdx.awesome.confetti;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;

//
// Created by Vinod(vinod.kumar@witzeal.com)on 19/01/23.
//
// Copyright (c) 2023 Witzeal Technologies Private Limited. All rights reserved.
//
public class ConfettiImageParticle extends ConfettiParticle{
    private ConfettiWidget confettiWidget;
    private Image particleImage;
    private Confetti.ConfettiConfig config;
    public ConfettiImageParticle(ConfettiWidget confettiWidget, Confetti.ConfettiConfig config, Drawable drawable, boolean keepAspectRatio){
        this.confettiWidget=confettiWidget;
        this.config=config;
        float particleWidth=MathUtils.random(config.particleMinWidth,config.particleMaxWidth);
        float particleHeight;
        if (keepAspectRatio){
            particleHeight=particleWidth*drawable.getMinHeight()/drawable.getMinWidth();
        }else{
            particleHeight=MathUtils.random(config.particleMinHeight,config.particleMaxHeight);
        }
        particleWidth=particleWidth*confettiWidget.ppm;
        particleHeight=particleHeight*confettiWidget.ppm;
        particleImage=new Image(drawable);
        particleImage.setColor(new Color(MathUtils.random(0.0f,1.0f),MathUtils.random(0.0f,1.0f),MathUtils.random(0.0f,1.0f),1.0f));
        particleImage.setSize(particleWidth,particleHeight);
        particleImage.setOrigin(particleWidth/2f,particleHeight/2f);
        particleImage.setRotation(MathUtils.random(0.0f,360.0f));
        addActor(particleImage);
        setSize(particleWidth,particleHeight);
        setOrigin(particleWidth/2f,particleHeight/2f);
        setProperties();
    }

    private void setProperties(){
        setX(confettiWidget.getWidth()*MathUtils.random(config.minStart.x,config.maxStart.x));
        setY(confettiWidget.getHeight()*MathUtils.random(config.minStart.y,config.maxStart.y));
        float particleAngle=config.angle+MathUtils.random(-config.cone/2f,config.cone/2f);
        velocity.set(MathUtils.cosDeg(particleAngle),MathUtils.sinDeg(particleAngle)).scl(MathUtils.random(config.particleMinVelocity,config.particleMaxVelocity)*confettiWidget.ppm);
        gravity.set(0.0f,-9.8f).scl(confettiWidget.ppm);
        scaleFactor=MathUtils.random(config.particleMinScaleFactor,config.particleMaxScaleFactor);
        distance=getHeight();
    }
}
