package com.gladurbad.medusa.check.impl.movement.fly;

import com.gladurbad.api.check.CheckInfo;
import com.gladurbad.medusa.check.Check;
import com.gladurbad.medusa.data.PlayerData;
import com.gladurbad.medusa.packet.Packet;

@CheckInfo(name = "Fly (A)",description = "Impossible MotionY")
public class FlyA extends Check {
    public FlyA(PlayerData data) {
        super(data);
    }

    @Override
    public void handle(Packet packet) {
        if (data.getTransactionProcessor().getServerTransactionPing() < 300 && packet.isPosition()) {
            if (data.getPositionProcessor().isInAir() && !data.getPositionProcessor().isFlying() && data.getPositionProcessor().getAirTicks() >= 11) {
                boolean invalid = Math.abs(data.getPositionProcessor().getDeltaY()) < 0.01;
                if (invalid) {
                    fail("DeltaY: " + data.getPositionProcessor().getDeltaY());
                }
            }
        }
    }
}
