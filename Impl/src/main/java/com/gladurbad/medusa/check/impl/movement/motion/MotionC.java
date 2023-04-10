package com.gladurbad.medusa.check.impl.movement.motion;

import com.gladurbad.api.check.CheckInfo;
import com.gladurbad.medusa.check.Check;
import com.gladurbad.medusa.data.PlayerData;
import com.gladurbad.medusa.packet.Packet;

@CheckInfo(name = "Motion (C)",description = "Mini Jump")
public class MotionC extends Check {

    public MotionC(PlayerData data) {
        super(data);
    }

    @Override
    public void handle(Packet packet) {
        if (packet.isFlying()) {
            if (Math.abs(data.getPositionProcessor().getLastDeltaY()) > 0 && Math.abs(data.getPositionProcessor().getLastDeltaY()) < 3E-2) {
                buffer ++;
            }
            if (buffer >= 3) {
                setBack();
                fail("motionY: " + Math.abs(data.getPositionProcessor().getLastDeltaY()));
                buffer = 0;
            }
        }
    }
}
