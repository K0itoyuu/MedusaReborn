package com.gladurbad.medusa.util.anticheat;

import com.gladurbad.medusa.Medusa;
import com.gladurbad.medusa.check.Check;
import com.gladurbad.medusa.config.Config;
import com.gladurbad.medusa.data.PlayerData;
import com.gladurbad.medusa.util.ColorUtil;
import com.gladurbad.medusa.util.RandomUtils;
import lombok.experimental.UtilityClass;
import org.bukkit.Bukkit;

@UtilityClass
public final class PunishUtil {

    public void punish(final Check check, final PlayerData data) {
        if (!check.getPunishCommand().isEmpty()) {
            Bukkit.getScheduler().runTask(Medusa.INSTANCE.getPlugin(), () ->
                    Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), check.getPunishCommand()
                            .replaceAll("%player%", data.getPlayer().getName())
                            .replaceAll("%checkName%", check.getJustTheName())
                            .replaceAll("%checkType", String.valueOf(check.getType()))
                            .replaceAll("%random_ac%",getRandomACName())));

            data.setPunished(true);
        }

    }

    private String getRandomACName() {
        String[] strings = Config.getStringListFromConfig("appearance.random_anticheat_names").toArray(new String[0]);
        return ColorUtil.translate(strings[RandomUtils.nextInt(0,strings.length-1)]);
    }
}
