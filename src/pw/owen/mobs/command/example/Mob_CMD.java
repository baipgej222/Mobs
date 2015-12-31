package pw.owen.mobs.command.example;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import pw.owen.mobs.bean.mob.MobModel;
import pw.owen.mobs.command.PluginCommand;
import pw.owen.mobs.run.Main;
import pw.owen.mobs.utils.Send;

public class Mob_CMD implements PluginCommand {

	@Override
	public String[] getBranch() {
		// TODO �Զ����ɵķ������
		return new String[] { "mobs", "mob" };
	}

	@Override
	public boolean run(Player p, String[] args, String auto) throws Exception {
		if (args.length == 0)
			return false;
		if (args[0].equalsIgnoreCase("select")) {
			if (args.length == 1) {
				Send.sendPluginMessage(p,
						"��a  /Mobs mob select  [������] ����ĳ�����������");
				return true;
			}
			if (args.length != 2) {
				return false;
			}

			ConfigurationSection section = Main.getCfg()
					.getConfigurationSection("MobModel")
					.getConfigurationSection(args[1]);
			if (section == null) {
				Send.sendPluginMessage(p, "�ù��ﲻ����.");
				return true;
			}
			Main.setsMobModel(new MobModel(section));
			Send.sendPluginMessage(p, "�Ѿ�ѡ�����:" + args[1] + ".");
			return true;

		} else if (args[0].equalsIgnoreCase("new")) {
			if (args.length != 2)
				return false;

			if (MobModel.isMobModel(args[1]) != -1) {

				Send.sendPluginMessage(p, "�����Ѵ���.");

			} else {

					Main.setsMobModel(new MobModel(args[1], Main.getCfg()
							.getConfigurationSection("MobModel")));
					Main.saveYml();
					Send.sendPluginMessage(p, "�����ɹ�.");

			}
			return true;

		} else if (args[0].equalsIgnoreCase("list")) {
			String s = "�����б�:";
			for (int i = 0; i < MobModel.getMobModels().size(); i++) {
				s += MobModel.getMobModels().get(i).getsName();
				if (i != MobModel.getMobModels().size() - 1) {
					s += ",";
				}
			}
			Send.sendPluginMessage(p, s);
			return true;
		} else if (Main.getsMobModel() == null) {
			Send.sendPluginMessage(p, "����ʹ��/Mobs mob select [������] ѡ��ĳ����������޸�.");
			return true;
		}
		MobModel mm = Main.getsMobModel();

		if (args[0].equalsIgnoreCase("see")) {
			p.sendMessage(mm.getSee());
			return true;
		} else if (args[0].equalsIgnoreCase("spawn")) {
			if (args.length != 1)
				return false;

			mm.spawnMob(this, p.getEyeLocation());
			Send.sendPluginMessage(p, "�����ɹ�.");
			return true;

		} else if (args[0].equalsIgnoreCase("killall")) {
			if (args.length != 1)
				return false;
			mm.killAll();
			Send.sendPluginMessage(p, "�����ɹ�.");
			return true;

		}

		return false;

	}

}
