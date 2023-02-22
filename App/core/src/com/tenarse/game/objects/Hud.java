package com.tenarse.game.objects;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.tenarse.game.utils.Settings;


public class Hud extends Actor {
    public Stage stage;
    private Viewport viewport;

    private Integer score;

    Label scoreLabel;

    public Hud(Batch batch){
        score = 0;

        viewport = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), new OrthographicCamera());
        stage = new Stage(viewport, batch);

        Table table = new Table();
        table.top();
        table.setFillParent(true);

        scoreLabel = new com.badlogic.gdx.scenes.scene2d.ui.Label(Integer.toString(score), new com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle(new BitmapFont(), Color.BLACK));
        if (Gdx.app.getType() == Application.ApplicationType.Android) {
            scoreLabel.setFontScale(5f);
        } else {
            scoreLabel.setFontScale(2f);
        }


        table.add(scoreLabel).align(Align.right).expandX().padTop(15).padRight(25);

        stage.addActor(table);

        table.setZIndex(100);
    }

    public Integer getScore() {
        return score;
    }

    public Label getScoreLabel() {
        return scoreLabel;
    }
}
