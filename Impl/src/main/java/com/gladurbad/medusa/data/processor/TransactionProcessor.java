package com.gladurbad.medusa.data.processor;

import com.gladurbad.medusa.data.PlayerData;
import com.gladurbad.medusa.util.PacketUtil;
import com.gladurbad.medusa.util.PlayerUtil;
import com.gladurbad.medusa.util.anticheat.AlertUtil;
import io.github.retrooper.packetevents.packetwrappers.play.in.transaction.WrappedPacketInTransaction;
import io.github.retrooper.packetevents.packetwrappers.play.out.transaction.WrappedPacketOutTransaction;
import lombok.Getter;
import org.bukkit.Bukkit;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

@Getter
public class TransactionProcessor {

    private final PlayerData data;

    private final Map<Short,Long> map = new HashMap<>();

    private int ping,lastPing;

    private long transactionPing;

    public TransactionProcessor(final PlayerData data) {
        this.data = data;
    }

    public void handleTransactionReceiving(WrappedPacketInTransaction transaction) {
        if (map.containsKey(transaction.getActionNumber())) {
            transactionPing = System.currentTimeMillis() - map.get(transaction.getActionNumber());
            map.remove(transaction.getActionNumber());
            lastPing = ping;
            ping = PlayerUtil.getPing(data.getPlayer());
        }
    }

    public void handleTransactionSend(WrappedPacketOutTransaction transaction) {
        map.put(transaction.getActionNumber(),System.currentTimeMillis());
        PacketUtil.sendPacket(data.getPlayer(),transaction);
    }

    public long getServerTransactionPing() {
        return transactionPing;
    }
}
