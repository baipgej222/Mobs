package cn.rpgmc.mobs.command.example;

import org.bukkit.entity.Player;

import cn.rpgmc.mobs.command.PluginCommand;
import cn.rpgmc.mobs.run.Main;
import cn.rpgmc.mobs.utils.Send;

public class Void_CMD implements PluginCommand {

	@Override
	public String[] getBranch() {
		// TODO �Զ����ɵķ������
		return new String[] { "mobs" };
	}

	@Override
	public boolean run(Player p, String[] args, String auto) throws Exception {
		if (args.length == 0) {
			Send.sendPluginMessage(p, "��7��lVersion:" + Main.getV());
			Send.sendPluginMessage(p, "��7��l������������ /Mobs help");

			return true;
		}

		return false;
	}


}
