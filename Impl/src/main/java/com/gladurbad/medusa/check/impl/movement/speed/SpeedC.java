package com.gladurbad.medusa.check.impl.movement.speed;

import com.gladurbad.api.check.CheckInfo;
import com.gladurbad.medusa.check.Check;
import com.gladurbad.medusa.data.PlayerData;
import com.gladurbad.medusa.packet.Packet;
import com.gladurbad.medusa.util.PlayerUtil;

@CheckInfo(name = "Speed (C)",description = "High Jump")
public class SpeedC extends Check {

    public SpeedC(PlayerData data) {
        super(data);
    }

    @Override
    public void handle(Packet packet) {

        if (data.getJoinTime() < 6000L) return;

        if (packet.isPosition()) {
            double motionY = data.getPositionProcessor().getLastDeltaY();
            double limitMotionY = PlayerUtil.maxMotionY(data.getPlayer());

            if (motionY > limitMotionY) {
                buffer += motionY - limitMotionY;
            } else {
                buffer = Math.max(0,buffer - 0.025);
            }

            if (buffer >= 3.0) {
                fail("my:" + motionY);
                buffer = 0;
            }
        }
    }
}
