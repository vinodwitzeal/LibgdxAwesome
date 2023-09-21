package libgdx.awesome.confetti;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Group;

//
// Created by Vinod(vinod.kumar@witzeal.com)on 19/01/23.
//
// Copyright (c) 2023 Witzeal Technologies Private Limited. All rights reserved.
//
public class ConfettiParticle extends Group {
    public final Vector2 velocity=new Vector2();
    public float mass;
    public final Vector2 gravity=new Vector2();
    public float distance;
    public float scaleFactor=2.0f;
    protected final Vector2 tmp=new Vector2();
    protected final Vector2 orientation=new Vector2();
    protected boolean increaseDistance=true;
    protected float fadeDistance;
    protected float alpha=0.0f;
    protected float spawnDuration=0.0f;


    @Override
    public void act(float delta) {
        super.act(delta);
        float x=getX();
        float y=getY();
        float deltaX=velocity.x*delta+0.5f*gravity.x*delta*delta;
        float deltaY=velocity.y*delta+0.5f*gravity.y*delta*delta;
        setX(x+deltaX);
        setY(y+deltaY);
        velocity.x+=gravity.x*delta;
        velocity.y+=gravity.y*delta;

        tmp.set(x,y);
        if (increaseDistance) {
            distance += tmp.dst(getX(), getY());
        }else {
            distance-=tmp.dst(getX(),getY());
        }
        orientation.set(getX()-x,getY()-y);

        spawnDuration+=delta;
        if (spawnDuration>=0.1f){
            spawnDuration=0.1f;
        }
        alpha=spawnDuration/0.1f;
        if (getY()<0){
            fadeDistance=-getY();
            alpha=(getHeight()*scaleFactor-fadeDistance)/(getHeight()*scaleFactor);
            alpha=MathUtils.clamp(alpha,0,1.0f);
            if (alpha==0.0f){
                Gdx.app.postRunnable(new Runnable() {
                    @Override
                    public void run() {
                        remove();
                    }
                });
            }
        }
        updateScaling(delta);
    }


    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, alpha*parentAlpha);
    }

    protected void updateScaling(float delta){
        setRotation(MathUtils.atan2(orientation.y,orientation.x)*MathUtils.radiansToDegrees-90.0f);
        if (distance>=getHeight()*scaleFactor){
            distance=getHeight()*scaleFactor;
            increaseDistance=false;
        }else if (distance<=-getHeight()*scaleFactor){
            distance=-getHeight()*scaleFactor;
            increaseDistance=true;
        }
        float scaleY=MathUtils.clamp(distance/(scaleFactor*getHeight()),-1.0f,1.0f);
        setScaleY(scaleY);
    }
}
