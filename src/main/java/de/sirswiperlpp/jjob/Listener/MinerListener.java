package de.sirswiperlpp.jjob.Listener;

import de.sirswiperlpp.jjob.API.EcoAPI;
import de.sirswiperlpp.jjob.Lang.Language;
import de.sirswiperlpp.jjob.Main.Main;
import de.sirswiperlpp.jjob.Manager.BlockDataManager;
import de.sirswiperlpp.jjob.Manager.BossBarManager;
import de.sirswiperlpp.jjob.Provider.JobPROV;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.io.File;
import java.sql.SQLException;
import java.util.Objects;
import java.util.UUID;

public class MinerListener implements Listener
{

    static Language language = new Language(new File(Main.getInstance().getDataFolder(), "lang.ini"));

    private final Main plugin;

    private final BlockDataManager blockDataManager;

    public MinerListener(Main plugin, BlockDataManager blockDataManager) {
        this.plugin = plugin;
        this.blockDataManager = blockDataManager;
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        BlockDataManager.setBlockPlacedByPlayer(event.getBlock(), player.getUniqueId());
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        if (blockDataManager.isBlockPlacedByPlayer(event.getBlock())) {
            UUID playerUUID = blockDataManager.getPlayerWhoPlacedBlock(event.getBlock());
            Bukkit.getServer().getLogger().info("BLOCK IS PLACED BY PLAYER");
        }
    }

    @EventHandler
    public void onMiner(final BlockBreakEvent event) throws SQLException {
        final Player player = event.getPlayer();
        final Material material = event.getBlock().getType();
        ItemStack tool = player.getInventory().getItemInMainHand();

        if (!Objects.equals(JobPROV.getCJob(player), "miner"))
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

        switch (material) {
            case COAL_ORE:
            case DEEPSLATE_COAL_ORE:
                BossBarManager.removeBossBar(player);
                EcoAPI.addBalance(player, 1);
                player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText("§a+1$, +1xp"));
                xpIncrement = 1;
                break;

            case IRON_ORE:
            case DEEPSLATE_IRON_ORE:
                BossBarManager.removeBossBar(player);
                EcoAPI.addBalance(player, 1);
                player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText("§a+1$, +2xp"));
                xpIncrement = 2;
                break;

            case COPPER_ORE:
            case DEEPSLATE_COPPER_ORE:
                BossBarManager.removeBossBar(player);
                EcoAPI.addBalance(player, 1);
                player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText("§a+1$, +1xp"));
                xpIncrement = 1;
                break;

            case GOLD_ORE:
            case DEEPSLATE_GOLD_ORE:
                BossBarManager.removeBossBar(player);
                EcoAPI.addBalance(player, 2);
                player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText("§a+2$, +3xp"));
                xpIncrement = 3;
                break;

            case REDSTONE_ORE:
            case DEEPSLATE_REDSTONE_ORE:
                BossBarManager.removeBossBar(player);
                EcoAPI.addBalance(player, 3);
                player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText("§a+3$, +2xp"));
                xpIncrement = 2;
                break;

            case EMERALD_ORE:
            case DEEPSLATE_EMERALD_ORE:
                BossBarManager.removeBossBar(player);
                EcoAPI.addBalance(player, 6);
                player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText("§a+6$, +15xp"));
                xpIncrement = 15;
                break;

            case LAPIS_ORE:
            case DEEPSLATE_LAPIS_ORE:
                BossBarManager.removeBossBar(player);
                EcoAPI.addBalance(player, 4);
                player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText("§a+4$, +7xp"));
                xpIncrement = 7;
                break;

            case DIAMOND_ORE:
            case DEEPSLATE_DIAMOND_ORE:
                BossBarManager.removeBossBar(player);
                EcoAPI.addBalance(player, 10);
                player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText("§a+10$, +25xp"));
                xpIncrement = 25;

                break;

            case ANCIENT_DEBRIS:
                BossBarManager.removeBossBar(player);
                EcoAPI.addBalance(player, 20);
                player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText("§c§l+20$, +50xp"));
                xpIncrement = 50;
                break;
        }

        if (xpIncrement > 0) {
            int currentXP = JobPROV.getCXP(player, job);
            int maxXP = JobPROV.getMXP(player, job);

            if (currentXP >= maxXP)
            {
                int newMax = maxXP + 5000;
                int newLevel = JobPROV.get_c_lvl(player) + 1;
                int newCXP = 2;
                JobPROV.updateMXP(player, newMax, job);
                JobPROV.updateCXP(player, newCXP, job);
                JobPROV.updateLVL(player, newLevel, job);
                player.sendTitle("§6§k!!§r§6LEVEL UP§k!!", "§c" + JobPROV.get_c_lvl(player), 15, 15, 15);
                player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1);
                BossBarManager.showBossBar(player, job, plugin);
                return;
            }

            JobPROV.updateCXP(player, currentXP + xpIncrement, job);
            BossBarManager.showBossBar(player, job, plugin);
        }
    }

}
