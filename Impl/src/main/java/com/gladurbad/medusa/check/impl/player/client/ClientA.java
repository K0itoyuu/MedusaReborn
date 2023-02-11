package com.gladurbad.medusa.check.impl.player.client;

import com.gladurbad.api.check.CheckInfo;
import com.gladurbad.medusa.check.Check;
import com.gladurbad.medusa.data.PlayerData;
import com.gladurbad.medusa.exempt.type.ExemptType;
import com.gladurbad.medusa.packet.Packet;
import com.gladurbad.medusa.util.PacketUtil;
import io.github.retrooper.packetevents.packetwrappers.play.in.custompayload.WrappedPacketInCustomPayload;
import io.github.retrooper.packetevents.packetwrappers.play.out.transaction.WrappedPacketOutTransaction;

@CheckInfo(name = "Client (A)",description = "Cancel Transaction")
public class ClientA extends Check {

    public ClientA(PlayerData data) {
        super(data);
    }

    @Override
    public void handle(Packet packet) {
        if (data.getJoinTime() < 6000) return;
        if (packet.isFlying()) {
            buffer+=1;
            if (buffer >= 20) {
                //防延迟
                WrappedPacketOutTransaction transaction = new WrappedPacketOutTransaction(0,data.getTransactionProcessor().getServerTransactionID(),true);
                data.getTransactionProcessor().handleTransactionSend(transaction);
            }
            if (buffer >= 40) {
                WrappedPacketOutTransaction transaction = new WrappedPacketOutTransaction(0,data.getTransactionProcessor().getServerTransactionID(),true);
                data.getTransactionProcessor().handleTransactionSend(transaction);
                fail();
                buffer = 0;
            }
        }
        if (packet.isIncomingTransaction()) {
            buffer = 0;
        }
    }
}
