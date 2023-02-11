package com.gladurbad.medusa.check.impl.player.client;

import com.gladurbad.api.check.CheckInfo;
import com.gladurbad.medusa.check.Check;
import com.gladurbad.medusa.data.PlayerData;
import com.gladurbad.medusa.packet.Packet;

@CheckInfo(name = "Client (F)",description = "Force player send C0F")
public class ClientF extends Check {

    public ClientF(PlayerData data) {
        super(data);
    }

    @Override
    public void handle(Packet packet) {
        if (data.getTransactionProcessor().isInvalidTransaction() && !packet.isIncomingTransaction()) {
            debug("Fucking player");
            if (packet.isFlying() || packet.isPosition() || packet.isPosLook() || packet.isRotation() || packet.isBlockDig() || packet.isBlockPlace() || packet.isUseEntity()) {
                packet.setCancelled(true);
            }
        }
    }
}
