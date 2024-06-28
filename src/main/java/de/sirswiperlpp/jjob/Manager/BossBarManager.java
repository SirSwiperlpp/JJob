package de.sirswiperlpp.jjob.Manager;

import de.sirswiperlpp.jjob.Main.Main;
import de.sirswiperlpp.jjob.Provider.JobPROV;
import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;

public class BossBarManager {

    private static final Map<Player, BossBar> playerBossBars = new HashMap<>();

    public static void showBossBar(Player player, String job, Main plugin) {
        int currentXP = JobPROV.getCXP(player, job);
        int maxXP = JobPROV.getMXP(player, job);
        int lvl = JobPROV.get_c_lvl(player);

        if (currentXP == -1 || maxXP == -1) {
            player.sendMessage("Error retrieving XP values.");
            return;
        }

        double progress = (double) currentXP / maxXP;
        BossBar bossBar = Bukkit.createBossBar("§d" + job + " Level: " + lvl, BarColor.PURPLE, BarStyle.SOLID);
        bossBar.setProgress(progress);
        bossBar.addPlayer(player);
        playerBossBars.put(player, bossBar);

        // Schedule the BossBar to be removed after 5 seconds (100 ticks)
        new BukkitRunnable() {
            @Override
            public void run() {
                removeBossBar(player);
            }
        }.runTaskLater(plugin, 100L); // 100 ticks = 5 seconds
    }

    public static void removeBossBar(Player player) {
        BossBar bossBar = playerBossBars.remove(player);
        if (bossBar != null) {
            bossBar.removeAll();
        }
    }
}
