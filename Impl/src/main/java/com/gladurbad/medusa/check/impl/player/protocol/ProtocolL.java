package com.gladurbad.medusa.check.impl.player.protocol;

import com.gladurbad.api.check.CheckInfo;
import com.gladurbad.medusa.check.Check;
import com.gladurbad.medusa.data.PlayerData;
import com.gladurbad.medusa.packet.Packet;
import io.github.retrooper.packetevents.PacketEvents;
import io.github.retrooper.packetevents.packetwrappers.play.in.blockdig.WrappedPacketInBlockDig;
import io.github.retrooper.packetevents.utils.player.ClientVersion;
import io.github.retrooper.packetevents.utils.player.Direction;
import io.github.retrooper.packetevents.utils.version.PEVersion;

@CheckInfo(name = "ProtocolL",description = "Invalid block dig packet")
public class ProtocolL extends Check {

    public ProtocolL(PlayerData data) {
        super(data);
    }

    @Override
    public void handle(Packet packet) {
        if (packet.isBlockDig() && data.getClientVersion().isNewerThanOrEquals(ClientVersion.v_1_8)) {
            WrappedPacketInBlockDig blockDig = new WrappedPacketInBlockDig(packet.getRawPacket());
            if (blockDig.getDigType().equals(WrappedPacketInBlockDig.PlayerDigType.RELEASE_USE_ITEM)) {
                if (!blockDig.getDirection().equals(Direction.DOWN) || blockDig.getBlockPosition().getX() != 0 || blockDig.getBlockPosition().getY() != 0 || blockDig.getBlockPosition().getZ() != 0) {
                    packet.setCancelled(true);
                    fail();
                }
            }
        }
    }
}
