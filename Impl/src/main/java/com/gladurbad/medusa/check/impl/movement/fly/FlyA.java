package com.gladurbad.medusa.check.impl.movement.fly;

import com.gladurbad.api.check.CheckInfo;
import com.gladurbad.medusa.check.Check;
import com.gladurbad.medusa.data.PlayerData;
import com.gladurbad.medusa.exempt.type.ExemptType;
import com.gladurbad.medusa.packet.Packet;

@CheckInfo(name = "Fly (A)",description = "Checks for constant vertical movement.")
public class FlyA extends Check {

    public FlyA(PlayerData data) {
        super(data);
    }

    @Override
    public void handle(Packet packet) {
        if (packet.isPosition()) {
            boolean inAir = data.getPositionProcessor().isInAir();
            double deltaY = data.getPositionProcessor().getDeltaY();
            double lastDeltaY = data.getPositionProcessor().getLastDeltaY();
            double acceleration = Math.abs(deltaY - lastDeltaY);
            boolean exempt = isExempt(ExemptType.LIQUID,ExemptType.JOINED, ExemptType.TRAPDOOR, ExemptType.VELOCITY, ExemptType.FLYING, ExemptType.WEB, ExemptType.TELEPORT, ExemptType.SLIME, ExemptType.CLIMBABLE, ExemptType.UNDER_BLOCK, ExemptType.SLAB, ExemptType.STAIRS);
            if (acceleration == 0.0 && inAir && !exempt) {
                buffer += 4.0;
                if (buffer > 20.0) {
                    fail("deltaY:" + data.getPositionProcessor().getDeltaY());
                    buffer = 0;
                }
            } else {
                buffer = Math.max(Math.floor(buffer / 2), 0.0);
            }
        }
    }
}
