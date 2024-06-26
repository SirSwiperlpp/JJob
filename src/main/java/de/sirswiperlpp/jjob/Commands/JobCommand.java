package de.sirswiperlpp.jjob.Commands;

import de.sirswiperlpp.jjob.Lang.Language;
import de.sirswiperlpp.jjob.Main.Main;
import de.sirswiperlpp.jjob.Provider.JobPROV;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.File;
import java.sql.SQLException;
import java.util.Objects;

public class JobCommand implements CommandExecutor {

    static Language language = new Language(new File(Main.getInstance().getDataFolder(), "lang.ini"));

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        if (!(commandSender instanceof Player))
        {
            commandSender.sendMessage("You must be a player to use this command!");
            return true;
        }

        Player player = (Player) commandSender;

        //COMMAND = /job <join | leave | info> <job>

        if (strings.length == 0)
        {
            player.sendMessage(language.get("prefix") + language.get("no.args"));
            return true;
        }

        switch (strings[0].toLowerCase())
        {
            case "join":
                if (strings.length != 2)
                {
                    player.sendMessage(language.get("prefix") + language.get("no.args"));
                    return true;
                }

                switch (strings[1].toLowerCase())
                {
                    case "miner":
                        JobPROV.updatePlayer(player, "miner");

                        if (Objects.equals(JobPROV.get_c_lvl(player), -1))
                        {
                            Bukkit.getServer().getLogger().warning(player.getName() + " wasnt found in XPEntry.. Creating him..");
                            JobPROV.createXPEntry(player, "miner");
                            Bukkit.getServer().getLogger().warning("Entry for player " + player.getName() + " (Miner job) created!");
                        }

                        player.sendMessage(language.get("prefix") + language.translateString("job.joined", "Miner"));
                        break;

                    case "farmer":
                        JobPROV.updatePlayer(player, "farmer");
                        player.sendMessage(language.get("prefix") + language.translateString("job.joined", "Farmer"));
                        break;

                    case "lumberjack":
                        JobPROV.updatePlayer(player, "lumberjack");
                        player.sendMessage(language.get("prefix") + language.translateString("job.joined", "Lumberjack"));
                        break;

                    case "headhunter":
                        JobPROV.updatePlayer(player, "headhunter");
                        player.sendMessage(language.get("prefix") + language.translateString("job.joined", "§4Head-Hunter"));
                        break;

                    default:
                        player.sendMessage(language.get("prefix") + "use: §c/job join <miner | farmer | lumberjack | headhunter>");
                        break;
                }
                break;

            case "leave":
                break;

            case "info":
                break;

            default:
                player.sendMessage(language.get("prefix") + language.get("no.args"));
                break;
        }

        return true;
    }
}
