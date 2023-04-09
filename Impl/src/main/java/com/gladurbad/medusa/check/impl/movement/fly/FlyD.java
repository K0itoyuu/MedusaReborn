package com.gladurbad.medusa.check.impl.movement.fly;

import com.gladurbad.api.check.CheckInfo;
import com.gladurbad.medusa.check.Check;
import com.gladurbad.medusa.data.PlayerData;
import com.gladurbad.medusa.packet.Packet;

@CheckInfo(name = "Fly (D)",description = "Air Jump")
public class FlyD extends Check {

    public FlyD(PlayerData data) {
        super(data);
    }

    @Override
    public void handle(Packet packet) {
        if (packet.isFlying()) {
            if (data.getPositionProcessor().getFallDistance() >= 0.42) {
                if (data.getPositionProcessor().getLastDeltaY() > 0.114514) {
                    buffer++;
                }
            }
        }

        if (buffer >= 3) {
            setBack();
            buffer -= 1.5;
            fail("motionY:" + data.getPositionProcessor().getLastDeltaY());
        }
    }
}
