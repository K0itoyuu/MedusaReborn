package com.gladurbad.medusa.check.impl.movement.fly;

import com.gladurbad.api.check.CheckInfo;
import com.gladurbad.medusa.check.Check;
import com.gladurbad.medusa.data.PlayerData;
import com.gladurbad.medusa.packet.Packet;
import com.gladurbad.medusa.util.PlayerUtil;
import io.github.retrooper.packetevents.packetwrappers.play.in.flying.WrappedPacketInFlying;

@CheckInfo(name = "Fly (B)",description = "GroundSpoof")
public class FlyB extends Check {

    public FlyB(PlayerData data) {
        super(data);
    }

    @Override
    public void handle(Packet packet) {
        if (packet.isPosition() || packet.isPosLook() && data.getTransactionProcessor().getServerTransactionPing() < 200 && data.getJoinTime() > 5000 && !data.getPositionProcessor().isTeleporting() && !data.getActionProcessor().isRespawning() && data.getPositionProcessor().getAirTicks() > 5) {
            if (data.getJoinTime() < 10000) return;
            WrappedPacketInFlying wrapped = new WrappedPacketInFlying(packet.getRawPacket());
            boolean packetOnGround = wrapped.isOnGround();
            boolean positionOnGround = PlayerUtil.isOnGround(data);
            boolean invalid = (packetOnGround != positionOnGround);
            if (invalid) {
                buffer += 1;
            }
            if (buffer >= 2) {
                fail("PacketOnGround: " + packetOnGround + ", PositionOnGround: " + positionOnGround);
                buffer = 0;
            }
        }
    }
}
