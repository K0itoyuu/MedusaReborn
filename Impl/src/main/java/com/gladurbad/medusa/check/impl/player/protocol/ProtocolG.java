package com.gladurbad.medusa.check.impl.player.protocol;

import com.gladurbad.medusa.check.Check;
import com.gladurbad.api.check.CheckInfo;
import com.gladurbad.medusa.data.PlayerData;
import com.gladurbad.medusa.packet.Packet;

@CheckInfo(name = "Protocol (G)", description = "Post")
public final class ProtocolG extends Check {

    private long lastBlockPlace,lastAction;
    private boolean placedBlock,actioned;

    public ProtocolG(final PlayerData data) {
        super(data);
    }

    @Override
    public void handle(final Packet packet) {
        if (packet.isBlockPlace()) {
            lastBlockPlace = now();
            placedBlock = true;
        } else if (packet.isEntityAction()) {
            lastAction = now();
            actioned = true;
        } else if (packet.isFlying()) {
            if (placedBlock) {
                final long delay = now() - lastBlockPlace;
                final boolean invalid = !data.getActionProcessor().isLagging() && delay > 25;

                if (invalid) {
                    if (++buffer > 5) {
                        fail("delay=" + delay + " buffer=" + buffer);
                    }
                } else {
                    buffer = Math.max(buffer - 2, 0);
                }
            }
            placedBlock = false;
            if (actioned) {
                final long delay = now() - lastAction;
                final boolean invalid = !data.getActionProcessor().isLagging() && delay > 25;

                if (invalid) {
                    if (++buffer > 5) {
                        fail("delay=" + delay + " buffer=" + buffer);
                    }
                } else {
                    buffer = Math.max(buffer - 2, 0);
                }
            }
            actioned = false;
        }
    }
}
