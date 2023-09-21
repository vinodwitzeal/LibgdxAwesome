package libgdx.awesome.confetti;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import java.util.ArrayList;
import java.util.List;

//
// Created by Vinod(vinod.kumar@witzeal.com)on 19/01/23.
//
// Copyright (c) 2023 Witzeal Technologies Private Limited. All rights reserved.
//
public class ConfettiWidget extends WidgetGroup {
    private boolean confettiAdded;
    private List<Confetti.ConfettiConfig> configList;
    public float ppm;
    public Drawable boxDrawable,circleDrawable,starDrawable;
    public ConfettiWidget(){
        ppm= Gdx.graphics.getWidth()/10.0f;
        List<Confetti.ConfettiConfig> configList=new ArrayList<Confetti.ConfettiConfig>();
        configList.add(new Confetti.DefaultConfettiConfig());
        setConfigList(configList);
        createBoxDrawable();
        createCircleDrawable();
        createStarDrawable();
    }

    private void createBoxDrawable(){
        boxDrawable=new NinePatchDrawable(new NinePatch(new Texture("img_box.png"),1,1,1,1));
    }

    private void createCircleDrawable(){
        circleDrawable=new TextureRegionDrawable(new Texture("img_circle.png"));
    }

    private void createStarDrawable(){
        starDrawable=new TextureRegionDrawable(new Texture("img_star.png"));
    }

    public ConfettiWidget(List<Confetti.ConfettiConfig> configList){
        setConfigList(configList);
    }

    private void setConfigList(List<Confetti.ConfettiConfig> configList){
        this.configList=configList;
        confettiAdded=false;
    }

    @Override
    public void layout() {
        super.layout();
        float width=getWidth();
        if (!confettiAdded){
            confettiAdded=true;
            for (Confetti.ConfettiConfig config:configList){
                Confetti confetti=new Confetti(this,config);
                addActor(confetti);
            }
        }
    }
}
