package com.gladurbad.medusa.check.impl.movement.sprint;

import com.gladurbad.api.check.CheckInfo;
import com.gladurbad.medusa.check.Check;
import com.gladurbad.medusa.data.PlayerData;
import com.gladurbad.medusa.packet.Packet;

@CheckInfo(name = "Sprint (A)",description = "Client Ignored FoodLevel")
public class SprintA extends Check {

    public SprintA(PlayerData data) {
        super(data);
    }

    @Override
    public void handle(Packet packet) {
        if (data.getActionProcessor().isSprinting()) {
            if (data.getPlayer().getFoodLevel() <= 6) {
                fail("FoodLevel: " + data.getPlayer().getFoodLevel());
                data.getPlayer().setSprinting(false);
            }
        }
    }
}
