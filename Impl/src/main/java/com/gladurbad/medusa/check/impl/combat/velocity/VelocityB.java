package com.gladurbad.medusa.check.impl.combat.velocity;

import com.gladurbad.api.check.CheckInfo;
import com.gladurbad.medusa.check.Check;
import com.gladurbad.medusa.data.PlayerData;
import com.gladurbad.medusa.exempt.type.ExemptType;
import com.gladurbad.medusa.packet.Packet;

@CheckInfo(name = "Velocity (B)",description = "Checks if the player horizontal velocity")
public class VelocityB extends Check {
    public VelocityB(PlayerData data) {
        super(data);
    }

    @Override
    public void handle(Packet packet) {
        if (packet.isFlying()) {
            if (data.getVelocityProcessor().getVelocityX() > 0 && data.getVelocityProcessor().getVelocityZ() > 0) {
                if (data.getVelocityProcessor().getBypassTicks() > 20) {
                    double dxz = data.getPositionProcessor().getDeltaXZ();
                    debug("dxz:" + dxz);
                    boolean invalid = dxz <= 0.00006 && !isExempt(ExemptType.FLYING);
                    if (invalid) {
                        buffer += 1.0;
                    } else {
                        buffer = Math.max(0, buffer - 0.25);
                    }

                    if (buffer >= 3.0) {
                        fail("dxz:" + dxz);
                        buffer = 0;
                    }
                }
            }
            if (!data.getPlayer().isOnGround()) buffer = Math.max(0,buffer - 0.5);
        }
    }
}
