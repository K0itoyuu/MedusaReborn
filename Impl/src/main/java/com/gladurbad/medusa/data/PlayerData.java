package com.gladurbad.medusa.data;

import com.gladurbad.medusa.check.Check;
import com.gladurbad.medusa.data.processor.*;
import com.gladurbad.medusa.util.PlayerUtil;
import com.gladurbad.medusa.util.type.EvictingList;
import com.gladurbad.medusa.util.type.Pair;
import io.github.retrooper.packetevents.utils.player.ClientVersion;
import lombok.Getter;
import lombok.Setter;
import com.gladurbad.medusa.exempt.ExemptProcessor;
import com.gladurbad.medusa.manager.CheckManager;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import java.util.List;

@Getter
@Setter
public final class PlayerData {

    private boolean isPunished;
    private final Player player;
    private String clientBrand;
    private int totalViolations, combatViolations, movementViolations, playerViolations;
    private final long joinTime = System.currentTimeMillis();
    private List<Check> checks = CheckManager.loadChecks(this);
    private final EvictingList<Pair<Location, Integer>> targetLocations = new EvictingList<>(40);

    private final ExemptProcessor exemptProcessor = new ExemptProcessor(this);
    private final CombatProcessor combatProcessor = new CombatProcessor(this);
    private final ActionProcessor actionProcessor = new ActionProcessor(this);
    private final ClickProcessor clickProcessor = new ClickProcessor(this);
    private final PositionProcessor positionProcessor = new PositionProcessor(this);
    private final RotationProcessor rotationProcessor = new RotationProcessor(this);
    private final VelocityProcessor velocityProcessor = new VelocityProcessor(this);
    private final TransactionProcessor transactionProcessor = new TransactionProcessor(this);
    private final BukkitProcessor bukkitProcessor = new BukkitProcessor(this);

    public PlayerData(final Player player) {
        this.player = player;
    }

    public ClientVersion getClientVersion() {
        return PlayerUtil.getClientVersion(player);
    }

    public long getJoinTime() {
        return System.currentTimeMillis() - joinTime;
    }
}
