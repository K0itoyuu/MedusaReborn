package com.gladurbad.medusa.check.impl.combat.autoblock;

import com.gladurbad.api.check.CheckInfo;
import com.gladurbad.medusa.check.Check;
import com.gladurbad.medusa.data.PlayerData;
import com.gladurbad.medusa.packet.Packet;
import com.gladurbad.medusa.util.PlayerUtil;

@CheckInfo(name = "AutoBlock (A)", description = "Attacking and Blocking",experimental = true)
public class AutoBlockA extends Check {
    public AutoBlockA(PlayerData data) {
        super(data);
    }

    @Override
    public void handle(Packet packet) {
        if (packet.isUseEntity() && data.getTransactionProcessor().getTransactionPing() < 120) {
            if (data.getCombatProcessor().getHitTicks() == 0 && data.getPlayer().isBlocking()) buffer++;
            else buffer = Math.max(buffer-1,0);
            if (buffer>5) {
                fail();
                PlayerUtil.setBackOnGround(data);
                buffer = 0;
            }
        }
    }
}
