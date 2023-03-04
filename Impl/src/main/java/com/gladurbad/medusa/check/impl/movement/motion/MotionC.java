package com.gladurbad.medusa.check.impl.movement.motion;

import com.gladurbad.api.check.CheckInfo;
import com.gladurbad.medusa.check.Check;
import com.gladurbad.medusa.data.PlayerData;
import com.gladurbad.medusa.exempt.type.ExemptType;
import com.gladurbad.medusa.packet.Packet;
import com.gladurbad.medusa.util.PlayerUtil;

@CheckInfo(name = "Motion (C)",description = "Mini Jump")
public class MotionC extends Check {

    public MotionC(PlayerData data) {
        super(data);
    }

    @Override
    public void handle(Packet packet) {
        if (packet.isPosition()) {
            if (isExempt(ExemptType.FLYING,ExemptType.VELOCITY) || data.getPositionProcessor().isInLiquid()) return;
            double motionY = data.getPositionProcessor().getLastDeltaY();
            double maxMotionY = PlayerUtil.maxMotionY(data.getPlayer());
            double diff = maxMotionY - motionY;
            if (motionY > 0 && maxMotionY >= motionY) {
                debug("my:" + motionY);
                if (diff > 1E-2) {
                    buffer ++;
                } else {
                    buffer = 0;
                }
            }
            if (buffer >= 12) {
                setBack();
                buffer = 0;
                fail("diff:" + diff);
            }
        }
    }
}
