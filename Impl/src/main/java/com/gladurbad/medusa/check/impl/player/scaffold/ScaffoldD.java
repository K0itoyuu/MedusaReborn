package com.gladurbad.medusa.check.impl.player.scaffold;

import com.gladurbad.api.check.CheckInfo;
import com.gladurbad.medusa.check.Check;
import com.gladurbad.medusa.data.PlayerData;
import com.gladurbad.medusa.packet.Packet;
import io.github.retrooper.packetevents.packetwrappers.play.in.blockplace.WrappedPacketInBlockPlace;

@CheckInfo(name = "Scaffold (D)",description = "Test")
public class ScaffoldD extends Check {

    public ScaffoldD(PlayerData data) {
        super(data);
    }

    @Override
    public void handle(Packet packet) {
        if (packet.isBlockPlace()) {
            WrappedPacketInBlockPlace wrapper = new WrappedPacketInBlockPlace(packet.getRawPacket());
            if (wrapper.getItemStack().getType().isBlock()) {
                float x,y,z;
                x = wrapper.getCursorPosition().x;
                y = wrapper.getCursorPosition().y;
                z = wrapper.getCursorPosition().z;
                debug(String.format("x:%.5f, y:%.5f, z:%.5f", x, y, z));
            }
        }
    }
}
