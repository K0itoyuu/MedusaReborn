package com.gladurbad.medusa.check.impl.player.scaffold;

import com.gladurbad.api.check.CheckInfo;
import com.gladurbad.medusa.check.Check;
import com.gladurbad.medusa.data.PlayerData;
import com.gladurbad.medusa.packet.Packet;
import io.github.retrooper.packetevents.packetwrappers.play.in.blockplace.WrappedPacketInBlockPlace;

@CheckInfo(name = "Scaffold (B)",description = "Checks for tower cheat")
public class ScaffoldB extends Check {

    private int lastBlockX,lastBlockY,lastBlockZ;

    public ScaffoldB(PlayerData data) {
        super(data);
    }

    @Override
    public void handle(Packet packet) {
        if (packet.isBlockPlace()) {
            WrappedPacketInBlockPlace blockPlace = new WrappedPacketInBlockPlace(packet.getRawPacket());
        }
    }
}
