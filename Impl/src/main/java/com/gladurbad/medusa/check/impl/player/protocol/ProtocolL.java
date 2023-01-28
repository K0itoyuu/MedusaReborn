package com.gladurbad.medusa.check.impl.player.protocol;

import com.gladurbad.api.check.CheckInfo;
import com.gladurbad.medusa.check.Check;
import com.gladurbad.medusa.data.PlayerData;
import com.gladurbad.medusa.packet.Packet;

@CheckInfo(name = "ProtocolL",description = "invalid block dig packet")
public class ProtocolL extends Check {

    public ProtocolL(PlayerData data) {
        super(data);
    }

    @Override
    public void handle(Packet packet) {
        boolean isSwordInHand = data.getPlayer().getItemInHand().getType().toString().toLowerCase().contains("sword");
        if (!isSwordInHand) {
            if (packet.isBlockDig()) {
                fail();
            }
        }
    }
}
