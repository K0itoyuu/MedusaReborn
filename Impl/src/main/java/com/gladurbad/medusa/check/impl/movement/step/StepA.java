package com.gladurbad.medusa.check.impl.movement.step;

import com.gladurbad.api.check.CheckInfo;
import com.gladurbad.medusa.check.Check;
import com.gladurbad.medusa.data.PlayerData;
import com.gladurbad.medusa.packet.Packet;

@CheckInfo(name = "Step (A)",description = "Simple")
public class StepA extends Check {
    public StepA(PlayerData data) {
        super(data);
    }

    @Override
    public void handle(Packet packet) {
        if (packet.isPosition() && data.getJoinTime() > 1000) {
            if (data.getPositionProcessor().getDeltaY() >= 1) {
                if (data.getPositionProcessor().getAirTicks() <= 1) {
                    fail("DeltaY: " + data.getPositionProcessor().getDeltaY() + ", at:" + data.getPositionProcessor().getAirTicks());
                }
            }
        }
    }
}
