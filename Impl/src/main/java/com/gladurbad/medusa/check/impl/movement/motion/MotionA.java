package com.gladurbad.medusa.check.impl.movement.motion;

import com.gladurbad.api.check.CheckInfo;
import com.gladurbad.medusa.check.Check;
import com.gladurbad.medusa.data.PlayerData;
import com.gladurbad.medusa.packet.Packet;
import com.gladurbad.medusa.util.PlayerUtil;

@CheckInfo(name = "Motion (A)",description = "High Jump")
public class MotionA extends Check {

    public MotionA(PlayerData data) {
        super(data);
    }

    @Override
    public void handle(Packet packet) {

        if (data.getJoinTime() < 6000L) return;

        if (packet.isPosition()) {
            double motionY = data.getPositionProcessor().getLastDeltaY();
            double limitMotionY = PlayerUtil.maxMotionY(data.getPlayer());

            if (motionY == 0) return;

            if (motionY > limitMotionY) {
                fail("motionY:" + motionY + ", limit:" + limitMotionY);
            }
        }
    }
}
