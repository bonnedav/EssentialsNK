package cn.yescallop.essentialsnk.command.defaults;

import cn.nukkit.Player;
import cn.nukkit.command.CommandSender;
import cn.nukkit.utils.TextFormat;
import cn.yescallop.essentialsnk.EssentialsAPI;
import cn.yescallop.essentialsnk.command.CommandBase;

public class VanishCommand extends CommandBase {

    public VanishCommand(EssentialsAPI api) {
        super("vanish", api);
        this.setAliases(new String[]{"v"});
    }

    public boolean execute(CommandSender sender, String label, String[] args) {
        if (!this.testPermission(sender)) {
            return false;
        }
        if (args.length > 1) {
            this.sendUsage(sender);
            return false;
        }
        Player player;
        if (args.length == 0) {
            if (!this.testIngame(sender)) {
                return false;
            }
            player = (Player) sender;
        } else {
            if (!sender.hasPermission("essentialsnk.vanish.other")) {
                this.sendPermissionMessage(sender);
                return false;
            }
            player = api.getServer().getPlayer(args[0]);
            if (player == null) {
                sender.sendMessage(TextFormat.RED + lang.translateString("commands.generic.player.notfound", args[0]));
                return false;
            }
        }
        String enabled = lang.translateString(api.switchVanish(player) ? "commands.generic.enabled" : "commands.generic.disabled");
        player.sendMessage(lang.translateString("commands.vanish.success", enabled));
        if (sender != player) {
            sender.sendMessage(lang.translateString("commands.vanish.success.other", player.getDisplayName(), enabled));
        }
        return true;
    }
}
