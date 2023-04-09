package com.gladurbad.medusa.check.impl.movement.fly;

import com.gladurbad.api.check.CheckInfo;
import com.gladurbad.medusa.check.Check;
import com.gladurbad.medusa.data.PlayerData;
import com.gladurbad.medusa.packet.Packet;

@CheckInfo(name = "Fly (D)",description = "FakeGround")
public class FlyB extends Check {

    public FlyB(PlayerData data) {
        super(data);
    }

    @Override
    public void handle(Packet packet) {
        if (data.getPositionProcessor().isInAir() && packet.isPosition()) {
            if (data.getPositionProcessor().getAirTicks() > 13 && data.getPositionProcessor().isMathematicallyOnGround()) {
                buffer += 1.0;
            }

            if (buffer >= 5) {
                fail("at: " + data.getPositionProcessor().getAirTicks());
                buffer = 0;
            }
        }
    }
}
