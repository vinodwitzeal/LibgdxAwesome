package libgdx.awesome.confetti;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Group;

import java.util.ArrayList;
import java.util.List;

//
// Created by Vinod(vinod.kumar@witzeal.com)on 19/01/23.
//
// Copyright (c) 2023 Witzeal Technologies Private Limited. All rights reserved.
//
public class Confetti extends Group {
    private ConfettiWidget confettiWidget;
    private ConfettiConfig config;
    private final List<ConfettiParticle> particles=new ArrayList<>();
    private float currentTime=0.0f;
    private int currentCount;
    public Confetti(ConfettiWidget confettiWidget,ConfettiConfig config){
        this.confettiWidget=confettiWidget;
        this.config=config;
        setSize(confettiWidget.getWidth(),confettiWidget.getHeight());
    }

    private void addBoxes(){
        int particleCount= MathUtils.random(config.minBoxes,config.maxBoxes);
        if (config.continuous){
            particleCount=particleCount/2;
        }
        for (int i=0;i<particleCount;i++){
            ConfettiParticle confettiParticle=new ConfettiImageParticle(confettiWidget,config,confettiWidget.boxDrawable,false);
            addActor(confettiParticle);
            particles.add(confettiParticle);
        }
    }

    private void addCircles(){
        int particleCount= MathUtils.random(config.minCircles,config.maxCircles);
        if (config.continuous){
            particleCount=particleCount/2;
        }
        for (int i=0;i<particleCount;i++){
            ConfettiParticle confettiParticle=new ConfettiImageParticle(confettiWidget,config,confettiWidget.circleDrawable,true);
            addActor(confettiParticle);
            particles.add(confettiParticle);
        }
    }

    private void addStars(){
        int particleCount= MathUtils.random(config.minStars,config.maxStars);
        if (config.continuous){
            particleCount=particleCount/2;
        }
        for (int i=0;i<particleCount;i++){
            ConfettiParticle confettiParticle=new ConfettiImageParticle(confettiWidget,config,confettiWidget.starDrawable,true);
            addActor(confettiParticle);
            particles.add(confettiParticle);
        }
    }

    private void addParticles(){
        addBoxes();
        addCircles();
        addStars();
    }


    @Override
    public void act(float delta) {
        super.act(delta);
        if (currentTime==0.0f){
            if (config.continuous) {
                currentCount++;
                addParticles();
            }else {
                if (currentCount<config.emitCount){
                    currentCount++;
                    addParticles();
                }
            }
        }
        currentTime+=delta;
        if (currentTime>=config.emitDuration){
            currentTime=0.0f;
        }
    }

    public static class ConfettiConfig{
        public final Vector2 minStart=new Vector2();
        public final Vector2 maxStart=new Vector2();
        public float particleMinWidth,particleMaxWidth;
        public float particleMinHeight,particleMaxHeight;
        public float particleMinVelocity=1.0f;
        public float particleMaxVelocity=1.0f;
        public float particleMinScaleFactor,particleMaxScaleFactor;
        public float angle;
        public float cone;
        public int minBoxes,maxBoxes;
        public int minCircles,maxCircles;
        public int minStars,maxStars;
        public boolean continuous;
        public float emitDuration;
        public float emitCount;
    }

    public static class DefaultConfettiConfig extends ConfettiConfig{
        public DefaultConfettiConfig(){
            minStart.set(0,1.0f);
            maxStart.set(1.0f,1.0f);
            angle=90.0f;
            cone=20.0f;
            particleMinWidth=0.25f;
            particleMaxWidth=0.35f;
            particleMinHeight=0.15f;
            particleMaxHeight=0.65f;
            particleMinVelocity=2.0f;
            particleMaxVelocity=3.0f;
//            particleMinVelocity=7.0f;
//            particleMaxVelocity=10.0f;
            particleMinScaleFactor=3.0f;
            particleMaxScaleFactor=10.0f;
            minBoxes=10;
            maxBoxes=20;
            minCircles=10;
            maxCircles=20;
            minStars=10;
            maxStars=20;
            continuous=true;
            emitDuration=0.25f;
            emitCount=2;
        }
    }
}
