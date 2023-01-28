package com.gladurbad.medusa.check.impl.movement.speed;

import com.gladurbad.api.check.CheckInfo;
import com.gladurbad.medusa.check.Check;
import com.gladurbad.medusa.data.PlayerData;
import com.gladurbad.medusa.packet.Packet;
import com.gladurbad.medusa.util.PlayerUtil;

@CheckInfo(name = "Speed (A)",description = "limit move speed")
public class SpeedA extends Check {

    public SpeedA(PlayerData data) {
        super(data);
    }

    @Override
    public void handle(Packet packet) {
        if (packet.isPosition() || packet.isPosLook()) {
            boolean invalid = data.getPositionProcessor().getDeltaXZ() > PlayerUtil.getBaseSpeed(data.getPlayer());
            if (invalid) {
                buffer += 4.0;
            } else {
                buffer -= 1.0;
            }

            if (buffer >= 16.0) {
                fail("dxz:" + data.getPositionProcessor().getDeltaXZ() + ", limit: " + PlayerUtil.getBaseSpeed(data.getPlayer()));
                buffer = 0;
            }
        }
    }
}
