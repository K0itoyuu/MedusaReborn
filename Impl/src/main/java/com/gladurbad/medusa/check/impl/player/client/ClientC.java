package com.gladurbad.medusa.check.impl.player.client;

import com.gladurbad.api.check.CheckInfo;
import com.gladurbad.medusa.check.Check;
import com.gladurbad.medusa.data.PlayerData;
import com.gladurbad.medusa.exempt.type.ExemptType;
import com.gladurbad.medusa.packet.Packet;
import com.gladurbad.medusa.util.MathUtil;
import com.gladurbad.medusa.util.type.EvictingList;

@CheckInfo(name = "Client (C)",description = "Client Modified GameSpeed")
public class ClientC extends Check {

    private final EvictingList<Long> samples = new EvictingList<>(50);
    private long lastFlyingTime;

    public ClientC(PlayerData data) {
        super(data);
    }

    @Override
    public void handle(Packet packet) {
        if (packet.isFlying() && !isExempt(ExemptType.TPS)) {
            final long now = now();
            final long delta = now - lastFlyingTime;

            if (delta > 1) {
                samples.add(delta);
            }

            if (samples.isFull()) {
                final double average = MathUtil.getAverage(samples);
                final double speed = 50 / average;

                if (speed >= 1.05) {
                    if (++buffer > 15) {
                        fail(String.format("speed=%.4f, delta=%o, buffer=%.2f", speed, delta, buffer));
                        buffer = 0;
                    }
                } else {
                    buffer = Math.max(0, buffer - 0.25);
                }
            }

            lastFlyingTime = now;
        } else if (packet.isOutPosition()) {
            samples.add(135L);
        }
    }
}
