package com.gladurbad.medusa.check.impl.movement.motion;

import com.gladurbad.api.check.CheckInfo;
import com.gladurbad.medusa.check.Check;
import com.gladurbad.medusa.data.PlayerData;
import com.gladurbad.medusa.packet.Packet;

@CheckInfo(name = "Motion (B)",description = "Wrong MotionY")
public class MotionB extends Check {

    public MotionB(PlayerData data) {
        super(data);
    }

    @Override
    public void handle(Packet packet) {
        double motionY = data.getPositionProcessor().getLastDeltaY();
        if (motionY < 0.4) return;
        double diff = motionY - 0.42;
        if (Math.abs(diff) < 1.7E-14) {
            fail("diff:" + diff);
        }
    }
}
