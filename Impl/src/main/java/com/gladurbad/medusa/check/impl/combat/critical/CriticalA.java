package com.gladurbad.medusa.check.impl.combat.critical;

import com.gladurbad.api.check.CheckInfo;
import com.gladurbad.medusa.check.Check;
import com.gladurbad.medusa.data.PlayerData;
import com.gladurbad.medusa.packet.Packet;
import com.gladurbad.medusa.util.MSTimer;
import io.github.retrooper.packetevents.packetwrappers.play.in.useentity.WrappedPacketInUseEntity;

@CheckInfo(name = "Critical (A)",description = "Critical")
public class CriticalA extends Check {

    public CriticalA(PlayerData data) {
        super(data);
    }

    private final MSTimer timer = new MSTimer();

    @Override
    public void handle(Packet packet) {
        if (timer.hasTimePassed(5000)) {
            buffer = 0;
            timer.reset();
        }

        if (packet.isUseEntity()) {
            WrappedPacketInUseEntity useEntity = new WrappedPacketInUseEntity(packet.getRawPacket());
            if (useEntity.getAction().equals(WrappedPacketInUseEntity.EntityUseAction.ATTACK)) {
                double motionY = Math.abs(data.getPositionProcessor().getLastDeltaY());
                debug("motionY:" + motionY);
                if (motionY > 0 && motionY < 3E-2) {
                    buffer ++;
                }

                if (buffer >= 3) {
                    fail("motionY:" + motionY);
                    buffer = 0;
                }
            }
        }
    }
}
