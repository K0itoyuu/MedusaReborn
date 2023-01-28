package com.gladurbad.medusa.check.impl.combat.autoblock;

import com.gladurbad.api.check.CheckInfo;
import com.gladurbad.medusa.check.Check;
import com.gladurbad.medusa.data.PlayerData;
import com.gladurbad.medusa.packet.Packet;

@CheckInfo(name = "AutoBlock (B)",description = "Always Blocking")
public class AutoBlockB extends Check {

    public AutoBlockB(PlayerData data) {
        super(data);
    }

    @Override
    public void handle(Packet packet) {
        if (packet.isUseEntity() && data.getCombatProcessor().getHitTicks() == 0) {
            if (data.getActionProcessor().isBlocking() && data.getPositionProcessor().getFastBlockTicks() > 15) {
                fail("BlockTicks: " + data.getPositionProcessor().getFastBlockTicks());
            }
        }
    }
}
