package com.gladurbad.medusa.check.impl.movement.speed;

import com.gladurbad.api.check.CheckInfo;
import com.gladurbad.medusa.check.Check;
import com.gladurbad.medusa.data.PlayerData;
import com.gladurbad.medusa.exempt.type.ExemptType;
import com.gladurbad.medusa.packet.Packet;
import com.gladurbad.medusa.util.PlayerUtil;

@CheckInfo(name = "Speed (C)",description = "Impossible Speed")
public class SpeedC extends Check {

    public SpeedC(PlayerData data) {
        super(data);
    }

    @Override
    public void handle(Packet packet) {
        if (packet.isPosition()) {
            if (data.getJoinTime() < 4500L) return;
            double dxz = data.getPositionProcessor().getLastDeltaXZ();
            boolean isExempt = isExempt(ExemptType.FLYING,ExemptType.VELOCITY,ExemptType.TELEPORT);
            if (!isExempt) {
                boolean invalid = dxz > PlayerUtil.maxSpeed(data.getPlayer());
                if (dxz > 0)
                    debug("s:" + dxz);
                if (invalid) {
                    setBack();
                    buffer += 1;
                }
            }

            if (buffer >= 3) {
                fail("s:" + dxz + ", ms:" + PlayerUtil.maxSpeed(data.getPlayer()));
                buffer = 0;
            }
        }
    }
}
