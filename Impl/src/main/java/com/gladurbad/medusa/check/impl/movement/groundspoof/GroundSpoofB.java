package com.gladurbad.medusa.check.impl.movement.groundspoof;

import com.gladurbad.api.check.CheckInfo;
import com.gladurbad.medusa.check.Check;
import com.gladurbad.medusa.data.PlayerData;
import com.gladurbad.medusa.packet.Packet;
import io.github.retrooper.packetevents.packetwrappers.play.in.flying.WrappedPacketInFlying;

@CheckInfo(name = "GroundSpoof (B)",description = "Advanced")
public class GroundSpoofB extends Check {

    public GroundSpoofB(PlayerData data) {
        super(data);
    }

    @Override
    public void handle(Packet packet) {
        if (packet.isFlying()) {
            WrappedPacketInFlying wrapper = new WrappedPacketInFlying(packet.getRawPacket());

            if (data.getPositionProcessor().getFallDistance() > 3.0) {
                if (wrapper.isOnGround()) {
                    debug("dy:" + data.getPositionProcessor().getDeltaY());
                    if (data.getPositionProcessor().getDeltaY() < 0) {
                        buffer++;
                    } else {
                        buffer = Math.max(0,buffer - 0.05);
                    }
                }
            }

            if (buffer >= 3) {
                fail("delayY: " + data.getPositionProcessor().getDeltaY());
                buffer = 0;
            }
        }
    }
}
