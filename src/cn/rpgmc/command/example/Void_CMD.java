package cn.rpgmc.command.example;

import org.bukkit.entity.Player;

import cn.rpgmc.command.PluginCommand;
import cn.rpgmc.run.Main;
import cn.rpgmc.utils.Send;

public class Void_CMD implements PluginCommand {

	@Override
	public String[] getBranch() {
		// TODO �Զ����ɵķ������
		return new String[] { "mobs" };
	}

	@Override
	public boolean run(Player p, String[] args, String auto) {
		if (args.length == 0) {
			Send.sendPluginMessage(p, "��7��lVersion:" + Main.getV());
			Send.sendPluginMessage(p, "��7��l������������ /Mobs help");

			return true;
		}

		return false;
	}


}
