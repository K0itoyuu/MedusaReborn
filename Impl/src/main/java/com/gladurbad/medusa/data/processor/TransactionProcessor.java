package com.gladurbad.medusa.data.processor;

import com.gladurbad.medusa.data.PlayerData;
import com.gladurbad.medusa.util.PacketUtil;
import com.gladurbad.medusa.util.PlayerUtil;
import io.github.retrooper.packetevents.packetwrappers.play.in.transaction.WrappedPacketInTransaction;
import io.github.retrooper.packetevents.packetwrappers.play.out.transaction.WrappedPacketOutTransaction;
import lombok.Getter;
import org.bukkit.Bukkit;

import java.util.LinkedList;

@Getter
public class TransactionProcessor {

    private final PlayerData data;

    private short transactionID,lastTransactionID,serverTransactionID,invalidTransactionValue;

    private long transactionReplyClient,transactionReplyServer,transactionPing;

    private int ping,lastPing;

    private boolean invalidTransaction = false;

    private boolean canSend = true;

    public TransactionProcessor(final PlayerData data) {
        this.data = data;
    }

    public void handleTransactionReceiving(WrappedPacketInTransaction transaction) {
        if (transaction.getActionNumber() == serverTransactionID) {
            invalidTransaction = false;
            this.lastTransactionID = this.transactionID;
            this.transactionID = transaction.getActionNumber();
            transactionReplyClient = System.currentTimeMillis();
            transactionPing = Math.abs(transactionReplyClient - transactionReplyServer);
            lastPing = ping;
            ping = PlayerUtil.getPing(data.getPlayer());
            canSend = true;
        } else if (transaction.getActionNumber() != serverTransactionID || transaction.getActionNumber() != 1) {
            invalidTransactionValue = transaction.getActionNumber();
            transactionReplyClient = System.currentTimeMillis();
            transactionPing = Math.abs(transactionReplyClient - transactionReplyServer);
            invalidTransaction = true;
        }
    }

    public void handleTransactionSend(WrappedPacketOutTransaction transaction) {
        canSend = false;
        serverTransactionID = transaction.getActionNumber();
        transactionReplyServer = System.currentTimeMillis();
        PacketUtil.sendPacket(data.getPlayer(),transaction);
    }

    public long getServerTransactionPing() {
        return System.currentTimeMillis() - transactionReplyClient;
    }
}
