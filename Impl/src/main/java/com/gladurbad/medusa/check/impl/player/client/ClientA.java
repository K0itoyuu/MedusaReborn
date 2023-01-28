package com.gladurbad.medusa.check.impl.player.client;

import com.gladurbad.api.check.CheckInfo;
import com.gladurbad.medusa.check.Check;
import com.gladurbad.medusa.data.PlayerData;
import com.gladurbad.medusa.packet.Packet;
import io.github.retrooper.packetevents.packetwrappers.play.in.custompayload.WrappedPacketInCustomPayload;

@CheckInfo(name = "Client (A)",description = "Cancel Transaction")
public class ClientA extends Check {

    public ClientA(PlayerData data) {
        super(data);
    }

    @Override
    public void handle(Packet packet) {
        if (packet.isPosLook()) {
            buffer+=1;
            if (buffer >= 100) {
                fail();
            }
        }
        if (packet.isIncomingTransaction()) {
            buffer = 0;
        }
    }
}
