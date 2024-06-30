package de.sirswiperlpp.jjob.Listener;

import de.sirswiperlpp.jjob.API.EcoAPI;
import de.sirswiperlpp.jjob.Main.Main;
import de.sirswiperlpp.jjob.Manager.BlockDataManager;
import de.sirswiperlpp.jjob.Manager.BossBarManager;
import de.sirswiperlpp.jjob.Provider.JobPROV;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.data.Ageable;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import java.sql.SQLException;
import java.util.Objects;
import java.util.UUID;

public class FarmerListener  implements Listener
{

    private final Main plugin;

    private final BlockDataManager blockDataManager;

    public FarmerListener(Main plugin, BlockDataManager blockDataManager) {
        this.plugin = plugin;
        this.blockDataManager = blockDataManager;
    }

    public String getBlockAge(Block block)
    {
        if (block.getBlockData() instanceof Ageable) {
            Ageable ageable = (Ageable) block.getBlockData();

            if (ageable.getAge() == ageable.getMaximumAge())
            {
                return "GOOD";
            }
        }
        return "NG";
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) throws SQLException {

        final Player player = event.getPlayer();
        final Material material = event.getBlock().getType();
        ItemStack tool = player.getInventory().getItemInMainHand();
        Block block = event.getBlock();

        if (!Objects.equals(JobPROV.getCJob(player), "farmer"))
            return;

        if (tool.hasItemMeta() && tool.getItemMeta().hasEnchants()) {
            if (tool.getItemMeta().hasEnchant(Enchantment.SILK_TOUCH)) {
                return;
            }
        }

        String job = JobPROV.getCJob(player);
        int xpIncrement = 0;

        switch (material)
        {
            case WHEAT:
            case SWEET_BERRIES:
            case SWEET_BERRY_BUSH:

                if (Objects.equals(getBlockAge(block), "NG"))
                    return;

                BossBarManager.removeBossBar(player);
                EcoAPI.addBalance(player, 1);
                player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText("§a+1$, +1xp"));
                xpIncrement = 1;
                break;

                //MAYBE CHANGE TO POTATO
            case POTATOES:
                if (Objects.equals(getBlockAge(block), "NG"))
                    return;

                BossBarManager.removeBossBar(player);
                EcoAPI.addBalance(player, 5);
                player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText("§a+5$, +15xp"));
                xpIncrement = 15;
                break;

                //MAYBE CHANGE TO CARROT
            case CARROTS:
                if (Objects.equals(getBlockAge(block), "NG"))
                    return;

                BossBarManager.removeBossBar(player);
                EcoAPI.addBalance(player, 3);
                player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText("§a+3$, +5xp"));
                xpIncrement = 5;
                break;

            case BEETROOTS:
                if (Objects.equals(getBlockAge(block), "NG"))
                    return;

                BossBarManager.removeBossBar(player);
                EcoAPI.addBalance(player, 5);
                player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText("§a+5$, +10xp"));
                xpIncrement = 10;
                break;

            case MELON:

                if (blockDataManager.isBlockPlacedByPlayer(event.getBlock())) {
                    UUID playerUUID = blockDataManager.getPlayerWhoPlacedBlock(event.getBlock());
                    Bukkit.getServer().getLogger().info("BLOCK IS PLACED BY PLAYER");
                    return;
                }

                BossBarManager.removeBossBar(player);
                EcoAPI.addBalance(player, 3);
                player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText("§a+3$, +3xp"));
                xpIncrement = 3;
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
