package com.gladurbad.medusa.check.impl.movement.fly;

import com.gladurbad.api.check.CheckInfo;
import com.gladurbad.medusa.check.Check;
import com.gladurbad.medusa.data.PlayerData;
import com.gladurbad.medusa.exempt.type.ExemptType;
import com.gladurbad.medusa.packet.Packet;

@CheckInfo(name = "Fly (G)",description = "AirJump")
public class FlyG extends Check {

    public FlyG(PlayerData data) {
        super(data);
    }

    @Override
    public void handle(Packet packet) {
        if (packet.isPosition()) {
            boolean isExempt = isExempt(ExemptType.FLYING,ExemptType.VELOCITY);
            double motionY = data.getPositionProcessor().getLastDeltaY();
            debug("my:" + motionY + ",fd:" + data.getPositionProcessor().getFallDistance());
            if (motionY >= 0.1 && data.getPositionProcessor().getFallDistance() > 0.001 && !isExempt) {
                fail();
            }
        }
    }
}
