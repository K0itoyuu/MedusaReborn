package com.gladurbad.medusa.data.processor;

import com.gladurbad.medusa.data.PlayerData;
import com.gladurbad.medusa.util.PacketUtil;
import com.gladurbad.medusa.util.PlayerUtil;
import com.gladurbad.medusa.util.anticheat.AlertUtil;
import io.github.retrooper.packetevents.packetwrappers.play.in.transaction.WrappedPacketInTransaction;
import io.github.retrooper.packetevents.packetwrappers.play.out.transaction.WrappedPacketOutTransaction;
import lombok.Getter;
import org.bukkit.Bukkit;

import java.util.LinkedList;

@Getter
public class TransactionProcessor {

    private final PlayerData data;

    private short transactionID,lastTransactionID,serverTransactionID;

    private long transactionReplyClient,transactionReplyServer,transactionPing;

    private int ping,lastPing;

    public TransactionProcessor(final PlayerData data) {
        this.data = data;
    }

    public void handleTransactionReceiving(WrappedPacketInTransaction transaction) {
        if (transaction.getActionNumber() == serverTransactionID) {
            this.lastTransactionID = this.transactionID;
            this.transactionID = transaction.getActionNumber();
            transactionReplyClient = System.currentTimeMillis();
            transactionPing = Math.abs(transactionReplyClient - transactionReplyServer);
            lastPing = ping;
            ping = PlayerUtil.getPing(data.getPlayer());
        }
    }

    public void handleTransactionSend(WrappedPacketOutTransaction transaction) {
        serverTransactionID = transaction.getActionNumber();
        transactionReplyServer = System.currentTimeMillis();
        PacketUtil.sendPacket(data.getPlayer(),transaction);
    }

    public long getServerTransactionPing() {
        return System.currentTimeMillis() - transactionReplyClient;
    }
}
