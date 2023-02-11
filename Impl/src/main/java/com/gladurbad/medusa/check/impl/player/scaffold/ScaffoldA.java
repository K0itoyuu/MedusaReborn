package com.gladurbad.medusa.check.impl.player.scaffold;

import com.gladurbad.api.check.CheckInfo;
import com.gladurbad.medusa.check.Check;
import com.gladurbad.medusa.data.PlayerData;
import com.gladurbad.medusa.packet.Packet;
import io.github.retrooper.packetevents.packetwrappers.play.in.blockplace.WrappedPacketInBlockPlace;
import io.github.retrooper.packetevents.utils.player.Direction;

@CheckInfo(name = "Scaffold (A)",description = "Invalid Direction")
public class ScaffoldA extends Check {

    public ScaffoldA(PlayerData data) {
        super(data);
    }

    @Override
    public void handle(Packet packet) {
        if (packet.isBlockPlace()) {
            WrappedPacketInBlockPlace blockPlace = new WrappedPacketInBlockPlace(packet.getRawPacket());
            if (blockPlace.getDirection().equals(Direction.INVALID)) {
                fail();
                packet.setCancelled(true);
            }
        }
    }
}
