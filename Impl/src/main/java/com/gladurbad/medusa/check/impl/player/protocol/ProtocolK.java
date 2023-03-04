package com.gladurbad.medusa.check.impl.player.protocol;

import com.gladurbad.api.check.CheckInfo;
import com.gladurbad.medusa.check.Check;
import com.gladurbad.medusa.data.PlayerData;
import com.gladurbad.medusa.packet.Packet;
import io.github.retrooper.packetevents.packetwrappers.play.in.tabcomplete.WrappedPacketInTabComplete;

@CheckInfo(name = "Protocol (K)",description = "Hacked Client Tab Complete")
public class ProtocolK extends Check {

    public ProtocolK(PlayerData data) {
        super(data);
    }

    @Override
    public void handle(Packet packet) {
        if (packet.isTabComplete()) {
            WrappedPacketInTabComplete tabComplete = new WrappedPacketInTabComplete(packet.getRawPacket());
            if (tabComplete.getText().startsWith(".") && tabComplete.getText().contains(" ")) {
                fail(tabComplete.getText());
            }
        }
    }
}
