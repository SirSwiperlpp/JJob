package de.sirswiperlpp.jjob.Listener;

import de.sirswiperlpp.jjob.API.EcoAPI;
import de.sirswiperlpp.jjob.Lang.Language;
import de.sirswiperlpp.jjob.Main.Main;
import de.sirswiperlpp.jjob.Provider.JobPROV;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.sql.SQLException;
import java.util.Objects;

public class MinerListener implements Listener
{

    static Language language = new Language(new File(Main.getInstance().getDataFolder(), "lang.ini"));

    @EventHandler
    public void onMiner(final BlockBreakEvent event) throws SQLException {
        final Block block = event.getBlock();
        final Material material = block.getType();
        final Player player = event.getPlayer();

        ItemStack tool = player.getInventory().getItemInMainHand();

        if (!Objects.equals(JobPROV.getCJob(player), "miner"))
            return;

        if (tool.hasItemMeta() && tool.getItemMeta().hasEnchants()) {
            if (tool.getItemMeta().hasEnchant(Enchantment.SILK_TOUCH)) {
                return;
            }
        }


        switch (material)
        {
            case COAL_ORE:
                //1$
                EcoAPI.addBalance(player, 1);
                String coal_msg = "§a+1$, +1xp";
                player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(coal_msg));
                //1xp
                String coal_pJob = JobPROV.getCJob(player);
                int coal_c_xp = JobPROV.getCXP(player, coal_pJob);
                int coal_xp = coal_c_xp + 1;
                JobPROV.updateCXP(player, coal_xp, coal_pJob);
                break;

            case DEEPSLATE_COAL_ORE:
                //1$
                EcoAPI.addBalance(player, 1);
                String d_coal_msg = "§a+1$, +1xp";
                player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(d_coal_msg));
                //1xp
                String d_coal_pJob = JobPROV.getCJob(player);
                int d_coal_c_xp = JobPROV.getCXP(player, d_coal_pJob);
                int d_coal_xp = d_coal_c_xp + 1;
                JobPROV.updateCXP(player, d_coal_xp, d_coal_pJob);
                break;

            case IRON_ORE:
                //1$
                EcoAPI.addBalance(player, 1);
                String iron_msg = "§a+1$, +2xp";
                player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(iron_msg));
                //2xp
                String iron_pJob = JobPROV.getCJob(player);
                int iron_c_xp = JobPROV.getCXP(player, iron_pJob);
                int iron_xp = iron_c_xp + 2;
                JobPROV.updateCXP(player, iron_xp, iron_pJob);
                break;

            case DEEPSLATE_IRON_ORE:
                //1$
                EcoAPI.addBalance(player, 1);
                String d_iron_msg = "§a+1$, +2xp";
                player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(d_iron_msg));
                //2xp
                String d_iron_pJob = JobPROV.getCJob(player);
                int d_iron_c_xp = JobPROV.getCXP(player, d_iron_pJob);
                int d_iron_xp = d_iron_c_xp + 2;
                JobPROV.updateCXP(player, d_iron_xp, d_iron_pJob);
                break;

            case COPPER_ORE:
                //1$
                EcoAPI.addBalance(player, 1);
                String cop_msg = "§a+1$, +1xp";
                player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(cop_msg));
                //1xp
                String cop_pJob = JobPROV.getCJob(player);
                int cop_c_xp = JobPROV.getCXP(player, cop_pJob);
                int cop_xp = cop_c_xp + 1;
                JobPROV.updateCXP(player,cop_xp,cop_pJob);
                break;

            case DEEPSLATE_COPPER_ORE:
                EcoAPI.addBalance(player, 1);
                String d_cop_msg = "§a+1$, +1xp";
                player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(d_cop_msg));
                //1xp
                String d_cop_pJob = JobPROV.getCJob(player);
                int d_cop_c_xp = JobPROV.getCXP(player, d_cop_pJob);
                int d_cop_xp = d_cop_c_xp + 1;
                JobPROV.updateCXP(player,d_cop_xp,d_cop_pJob);
                break;

            case GOLD_ORE:
                //2$
                EcoAPI.addBalance(player, 2);
                String gold_msg = "§a+2$, +3xp";
                player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(gold_msg));
                //3xp
                String gold_pJob = JobPROV.getCJob(player);
                int gold_c_xp = JobPROV.getCXP(player, gold_pJob);
                int gold_xp = gold_c_xp + 2;
                JobPROV.updateCXP(player,gold_xp,gold_pJob);
                break;

            case DEEPSLATE_GOLD_ORE:
                //2$
                EcoAPI.addBalance(player, 2);
                String d_gold_msg = "§a+2$, +3xp";
                player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(d_gold_msg));
                //3xp
                String d_gold_pJob = JobPROV.getCJob(player);
                int d_gold_c_xp = JobPROV.getCXP(player, d_gold_pJob);
                int d_gold_xp = d_gold_c_xp + 2;
                JobPROV.updateCXP(player,d_gold_xp,d_gold_pJob);
                break;

            case REDSTONE_ORE:
                //1$
                //3xp
                break;

            case DEEPSLATE_REDSTONE_ORE:
                //1$
                //3xp
                break;

            case EMERALD_ORE:
                //6$
                //15xp
                break;

            case DEEPSLATE_EMERALD_ORE:
                //6$
                //15xp
                break;

            case LAPIS_ORE:
                //4$
                //7xp
                break;

            case DEEPSLATE_LAPIS_ORE:
                //4$
                //7xp
                break;

            case DIAMOND_ORE:
                //10$
                //25xp
                break;

            case DEEPSLATE_DIAMOND_ORE:
                //10$
                //25xp
                break;

            case ANCIENT_DEBRIS:
                //20$
                //50xp
                break;
        }
    }

}
