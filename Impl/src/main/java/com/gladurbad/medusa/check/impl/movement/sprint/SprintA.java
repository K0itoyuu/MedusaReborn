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
        if (data.getActionProcessor().isSprinting() && data.getActionProcessor().getSprintingTicks() > 10) {
            if (data.getPlayer().getFoodLevel() <= 6) {
                buffer += 0.1;
                data.getPlayer().setSprinting(false);
            }

            if (buffer >= 3.0) {
                fail("FoodLevel: " + data.getPlayer().getFoodLevel());
                buffer = 0;
            }
        }
    }
}
