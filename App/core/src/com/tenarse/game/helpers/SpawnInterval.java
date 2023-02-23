package com.tenarse.game.helpers;

import com.badlogic.gdx.utils.TimeUtils;

public class SpawnInterval {
    public long time;
    public long interval;

    public SpawnInterval(long interval) {
        this.interval = interval;
        this.time = TimeUtils.nanoTime();
    }
}
