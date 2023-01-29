package com.gladurbad.medusa.manager;

import com.gladurbad.medusa.check.Check;
import com.gladurbad.medusa.check.impl.combat.autoblock.AutoBlockA;
import com.gladurbad.medusa.check.impl.combat.autoblock.AutoBlockB;
import com.gladurbad.medusa.check.impl.combat.killaura.KillAuraF;
import com.gladurbad.medusa.check.impl.combat.killaura.*;
import com.gladurbad.medusa.check.impl.combat.reach.ReachA;
import com.gladurbad.medusa.check.impl.movement.fly.*;
import com.gladurbad.medusa.check.impl.movement.noslow.*;
import com.gladurbad.medusa.check.impl.movement.sprint.*;
import com.gladurbad.medusa.check.impl.movement.speed.*;
import com.gladurbad.medusa.check.impl.movement.step.StepA;
import com.gladurbad.medusa.check.impl.player.client.ClientA;
import com.gladurbad.medusa.check.impl.player.client.ClientB;
import com.gladurbad.medusa.check.impl.player.client.ClientC;
import com.gladurbad.medusa.check.impl.player.inventory.*;
import com.gladurbad.medusa.check.impl.player.protocol.*;
import com.gladurbad.medusa.config.Config;
import com.gladurbad.medusa.data.PlayerData;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

public final class CheckManager {

    public static final Class<?>[] CHECKS = new Class[] {
            NoSlowA.class,
            NoSlowB.class,
            FlyA.class,
            FlyB.class,
            FlyC.class,
            FlyD.class,
            SpeedA.class,
            StepA.class,
            SprintA.class,
            SprintB.class,
            ReachA.class,
            AutoBlockA.class,
            AutoBlockB.class,
            KillAuraA.class,
            KillAuraB.class,
            KillAuraC.class,
            KillAuraD.class,
            KillAuraE.class,
            KillAuraF.class,
            KillAuraG.class,
            KillAuraH.class,
            InventoryA.class,
            InventoryB.class,
            InventoryC.class,
            ProtocolA.class,
            ProtocolB.class,
            ProtocolC.class,
            ProtocolD.class,
            ProtocolE.class,
            ProtocolF.class,
            ProtocolG.class,
            ProtocolH.class,
            ProtocolI.class,
            ProtocolJ.class,
            ProtocolK.class,
            ClientA.class,
            ClientB.class,
            ClientC.class,
    };

    private static final List<Constructor<?>> CONSTRUCTORS = new ArrayList<>();

    public static List<Check> loadChecks(final PlayerData data) {
        final List<Check> checkList = new ArrayList<>();
        for (Constructor<?> constructor : CONSTRUCTORS) {
            try {
                checkList.add((Check) constructor.newInstance(data));
            } catch (Exception exception) {
                System.err.println("Failed to load checks for " + data.getPlayer().getName());
                exception.printStackTrace();
            }
        }
        return checkList;
    }

    public static void setup() {
        for (Class<?> clazz : CHECKS) {
            if (Config.ENABLED_CHECKS.contains(clazz.getSimpleName())) {
                try {
                    CONSTRUCTORS.add(clazz.getConstructor(PlayerData.class));
                } catch (NoSuchMethodException exception) {
                    exception.printStackTrace();
                }
            }
        }
    }
}

