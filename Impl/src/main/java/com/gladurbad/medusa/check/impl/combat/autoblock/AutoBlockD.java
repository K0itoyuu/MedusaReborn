package com.gladurbad.medusa.check.impl.combat.autoblock;

import com.gladurbad.api.check.CheckInfo;
import com.gladurbad.medusa.check.Check;
import com.gladurbad.medusa.data.PlayerData;
import com.gladurbad.medusa.packet.Packet;

@CheckInfo(name = "AutoBlock (D)",description = "123",experimental = true)
public class AutoBlockD extends Check {

    public AutoBlockD(PlayerData data) {
        super(data);
    }

    @Override
    public void handle(Packet packet) {
        if (packet.isFlying()) {
            debug("isBlocking: " + data.getActionProcessor().isBlocking());
        }
    }
}
