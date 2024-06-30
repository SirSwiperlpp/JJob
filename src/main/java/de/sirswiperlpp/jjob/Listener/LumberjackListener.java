package de.sirswiperlpp.jjob.Listener;

import de.sirswiperlpp.jjob.API.EcoAPI;
import de.sirswiperlpp.jjob.Main.Main;
import de.sirswiperlpp.jjob.Manager.BossBarManager;
import de.sirswiperlpp.jjob.Provider.JobPROV;
import de.sirswiperlpp.jjob.Manager.BlockDataManager;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import java.sql.SQLException;
import java.util.Objects;
import java.util.UUID;

public class LumberjackListener implements Listener
{

    private final Main plugin;

    private final BlockDataManager blockDataManager;

    public LumberjackListener(Main plugin, BlockDataManager blockDataManager) {
        this.plugin = plugin;
        this.blockDataManager = blockDataManager;
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) throws SQLException {
        final Player player = event.getPlayer();
        final Material material = event.getBlock().getType();
        ItemStack tool = player.getInventory().getItemInMainHand();

        if (!Objects.equals(JobPROV.getCJob(player), "lumberjack"))
            return;

        if (blockDataManager.isBlockPlacedByPlayer(event.getBlock())) {
            UUID playerUUID = blockDataManager.getPlayerWhoPlacedBlock(event.getBlock());
            Bukkit.getServer().getLogger().info("BLOCK IS PLACED BY PLAYER");
            return;
        }

        if (tool.hasItemMeta() && tool.getItemMeta().hasEnchants()) {
            if (tool.getItemMeta().hasEnchant(Enchantment.SILK_TOUCH)) {
                return;
            }
        }

        String job = JobPROV.getCJob(player);
        int xpIncrement = 0;

        switch (material)
        {
            case OAK_LOG:
            case STRIPPED_OAK_LOG:
                BossBarManager.removeBossBar(player);
                EcoAPI.addBalance(player, 1);
                player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText("§a+1$, +1xp"));
                xpIncrement = 1;
                break;

            case SPRUCE_LOG:
            case STRIPPED_SPRUCE_LOG:
                BossBarManager.removeBossBar(player);
                EcoAPI.addBalance(player, 1);
                player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText("§a+1$, +2xp"));
                xpIncrement = 2;
                break;

            case BIRCH_LOG:
            case STRIPPED_BIRCH_LOG:
                BossBarManager.removeBossBar(player);
                EcoAPI.addBalance(player, 1);
                player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText("§a+1$, +2xp"));
                xpIncrement = 2;
                break;

            case JUNGLE_LOG:
            case STRIPPED_JUNGLE_LOG:
                BossBarManager.removeBossBar(player);
                EcoAPI.addBalance(player, 1);
                player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText("§a+1$, +1xp"));
                xpIncrement = 1;
                break;

            case ACACIA_LOG:
            case STRIPPED_ACACIA_LOG:
                BossBarManager.removeBossBar(player);
                EcoAPI.addBalance(player, 4);
                player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText("§a+4$, +5xp"));
                xpIncrement = 5;
                break;

            case DARK_OAK_LOG:
            case STRIPPED_DARK_OAK_LOG:
                BossBarManager.removeBossBar(player);
                EcoAPI.addBalance(player, 1);
                player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText("§a+1$, +1xp"));
                xpIncrement = 1;
                break;

            case MANGROVE_LOG:
            case STRIPPED_MANGROVE_LOG:
                BossBarManager.removeBossBar(player);
                EcoAPI.addBalance(player, 6);
                player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText("§a+6$, +15xp"));
                xpIncrement = 15;
                break;

            case CHERRY_LOG:
            case STRIPPED_CHERRY_LOG:
                BossBarManager.removeBossBar(player);
                EcoAPI.addBalance(player, 2);
                player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText("§a+2$, +5xp"));
                xpIncrement = 5;
                break;
        }

        if (xpIncrement > 0) {
            int currentXP = JobPROV.getCXP(player, job);
            int maxXP = JobPROV.getMXP(player, job);

            if (currentXP >= maxXP)
            {
                int newMax = maxXP + 5000;
                int newLevel = JobPROV.get_c_lvl(player, job) + 1;
                int newCXP = 2;
                JobPROV.updateMXP(player, newMax, job);
                JobPROV.updateCXP(player, newCXP, job);
                JobPROV.updateLVL(player, newLevel, job);
                player.sendTitle("§6§k!!§r§aLEVEL UP§6§k!!", "§a" + JobPROV.get_c_lvl(player, job), 15, 15, 15);
                player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1);
                BossBarManager.showBossBar(player, job, plugin);
                return;
            }

            JobPROV.updateCXP(player, currentXP + xpIncrement, job);
            BossBarManager.showBossBar(player, job, plugin);
        }

    }


}
