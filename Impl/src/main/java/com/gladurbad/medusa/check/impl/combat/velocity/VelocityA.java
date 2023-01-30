package com.gladurbad.medusa.check.impl.combat.velocity;

import com.gladurbad.api.check.CheckInfo;
import com.gladurbad.medusa.check.Check;
import com.gladurbad.medusa.data.PlayerData;
import com.gladurbad.medusa.exempt.type.ExemptType;
import com.gladurbad.medusa.packet.Packet;

@CheckInfo(name = "Velocity (A)",description = "Checks if the player vertical velocity")
public class VelocityA extends Check {
    public VelocityA(PlayerData data) {
        super(data);
    }

    @Override
    public void handle(Packet packet) {
        if (packet.isFlying()) {
            if (data.getVelocityProcessor().getVelocityY() > 0) {
                if (data.getVelocityProcessor().getBypassTicks() > 20) {
                    double dy = Math.abs(data.getPositionProcessor().getDeltaY());
                    debug("dy:" + dy);
                    boolean invalid = dy <= 0.0105 && !isExempt(ExemptType.FLYING);
                    if (invalid) {
                        buffer += 1.0;
                    } else {
                        buffer = Math.max(0, buffer - 0.25);
                    }

                    if (buffer >= 3.0) {
                        fail("dy:" + dy);
                        buffer = 0;
                    }
                }
            }
            if (!data.getPlayer().isOnGround()) buffer = Math.max(0,buffer - 0.5);
        }
    }
}
