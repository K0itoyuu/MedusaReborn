package com.gladurbad.medusa.check.impl.player.client;

import com.gladurbad.api.check.CheckInfo;
import com.gladurbad.medusa.check.Check;
import com.gladurbad.medusa.data.PlayerData;
import com.gladurbad.medusa.exempt.type.ExemptType;
import com.gladurbad.medusa.packet.Packet;

import java.util.LinkedList;

@CheckInfo(name = "Client (F)",description = "Client Modified Speed")
public class ClientF extends Check {

    public ClientF(PlayerData data) {
        super(data);
    }

    private final LinkedList<Long> packets = new LinkedList<>();
    private boolean flag = false;

    @Override
    public void handle(Packet packet) {
        if (isExempt(ExemptType.TPS,ExemptType.TELEPORT)) return;

        if (packets.size() > 6 && !flag) {
            fail("overflow:" + (packets.size() - 6));
            flag = true;
        }

        if (packet.isFlying()) {
            packets.add(now());
        } else if (packet.isIncomingTransaction()) {
           if (!packets.isEmpty() && packets.size() > 1) {
               debug("row:" + packets.size());
               packets.clear();
               flag = false;
           }
        }
    }
}
