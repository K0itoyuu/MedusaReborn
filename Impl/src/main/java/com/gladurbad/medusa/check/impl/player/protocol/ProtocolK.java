package com.gladurbad.medusa.check.impl.player.protocol;

import com.gladurbad.api.check.CheckInfo;
import com.gladurbad.medusa.check.Check;
import com.gladurbad.medusa.data.PlayerData;
import com.gladurbad.medusa.packet.Packet;

@CheckInfo(name = "Protocol (K)",description = "PingSpoof")
public class ProtocolK extends Check {

    public ProtocolK(PlayerData data) {
        super(data);
    }

    /**
     * @param packet
     * 已弃用
     */
    @Override
    public void handle(Packet packet) {
        if (packet.isIncomingTransaction()) {
            int clientPing = data.getTransactionProcessor().getPing();
            long transactionPing = data.getTransactionProcessor().getServerTransactionPing();
            double diff = Math.abs(transactionPing - clientPing);

            if (clientPing == data.getTransactionProcessor().getLastPing()) return;

            if (data.getJoinTime() < 6000) return;

            if (transactionPing < 1) return;

            if (diff > 100) {
                buffer += 1.0;
            }
            if (buffer >= 4.0) {
                fail("ClientPing: " + clientPing + ", TransactionPing: " + transactionPing + ", Diff: " + diff);
                buffer = 0;
            }
        }
    }
}
