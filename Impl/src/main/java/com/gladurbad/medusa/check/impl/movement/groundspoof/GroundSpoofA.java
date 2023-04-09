package com.gladurbad.medusa.check.impl.movement.groundspoof;

import com.gladurbad.api.check.CheckInfo;
import com.gladurbad.medusa.check.Check;
import com.gladurbad.medusa.data.PlayerData;
import com.gladurbad.medusa.exempt.type.ExemptType;
import com.gladurbad.medusa.packet.Packet;
import com.gladurbad.medusa.util.PlayerUtil;
import io.github.retrooper.packetevents.packetwrappers.play.in.flying.WrappedPacketInFlying;

@CheckInfo(name = "GroundSpoof (A)",description = "Simple")
public class GroundSpoofA extends Check {

    public GroundSpoofA(PlayerData data) {
        super(data);
    }

    @Override
    public void handle(Packet packet) {
        if (data.getJoinTime() < 6000) return;

        if (packet.isPosLook()) {
            WrappedPacketInFlying wrapper = new WrappedPacketInFlying(packet.getRawPacket());
            boolean positionOnGround = PlayerUtil.isOnGround(data);
            boolean invalid = (wrapper.isOnGround() != positionOnGround) && !isExempt(ExemptType.TELEPORT);
            if (invalid) {
                buffer += 1;
            }
            if (buffer >= 2) {
                fail("position:" + positionOnGround + ", packet:" + wrapper.isOnGround());
                buffer = 0;
            }
        }
    }
}
