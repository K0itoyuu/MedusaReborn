package com.gladurbad.medusa.data.processor;

import com.gladurbad.medusa.data.PlayerData;
import lombok.Getter;

@Getter
public class BukkitProcessor {
    private int bukkitBlockingTick;

    private final PlayerData data;

    public BukkitProcessor(PlayerData playerData) {
        this.data = playerData;
    }

    public void handleFlying() {
        bukkitBlockingTick = data.getPlayer().isBlocking() ? bukkitBlockingTick + 1 : 0;
    }
}
