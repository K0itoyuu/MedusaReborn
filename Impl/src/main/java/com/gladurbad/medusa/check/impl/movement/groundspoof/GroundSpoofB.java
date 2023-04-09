package com.gladurbad.medusa.check.impl.movement.groundspoof;

import com.gladurbad.api.check.CheckInfo;
import com.gladurbad.medusa.check.Check;
import com.gladurbad.medusa.data.PlayerData;
import com.gladurbad.medusa.exempt.type.ExemptType;
import com.gladurbad.medusa.packet.Packet;
import io.github.retrooper.packetevents.packetwrappers.play.in.flying.WrappedPacketInFlying;

@CheckInfo(name = "GroundSpoof (B)",description = "Advanced")
public class GroundSpoofB extends Check {

    public GroundSpoofB(PlayerData data) {
        super(data);
    }

    @Override
    public void handle(Packet packet) {
        if (packet.isPosition()) {
            if (data.getPositionProcessor().getFallDistance() > 3.0) {
                WrappedPacketInFlying wrapper = new WrappedPacketInFlying(packet.getRawPacket());
                boolean invalid = wrapper.isOnGround() && !isExempt(ExemptType.TELEPORT) && data.getJoinTime() > 5000 && data.getPositionProcessor().getDeltaY() < 0;
                if (invalid) {
                    buffer += 1;
                }
            }

            if (buffer >= 2) {
                fail("motionY:" + data.getPositionProcessor().getLastDeltaY());
                buffer = 0;
            }
        }
    }
}
