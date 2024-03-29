package com.gladurbad.medusa.data.processor;

import com.gladurbad.medusa.util.MSTimer;
import lombok.Getter;
import com.gladurbad.medusa.data.PlayerData;

import java.util.ArrayList;

public final class ClickProcessor {

    @Getter
    private final PlayerData data;
    @Getter
    private long lastSwing = -1;
    @Getter
    private long delay;

    public ClickProcessor(final PlayerData data) {
        this.data = data;
    }

    public void handleArmAnimation() {
        if (!data.getActionProcessor().isDigging() && !data.getActionProcessor().isPlacing()) {
            if (lastSwing > 0) {
                delay = System.currentTimeMillis() - lastSwing;
            }
            lastSwing = System.currentTimeMillis();
        }
    }
}
