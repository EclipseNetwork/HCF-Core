package raton.meme.hcf.listener.combatloggers;

import org.bukkit.craftbukkit.v1_7_R4.entity.CraftPlayer;
import raton.meme.hcf.HCF;

import java.util.UUID;

public interface LoggerEntity {
    void postSpawn(HCF paramHCF);

    CraftPlayer getBukkitEntity();

    UUID getUniqueID();

    void destroy();
}
