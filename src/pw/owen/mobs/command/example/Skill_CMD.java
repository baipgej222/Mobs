package pw.owen.mobs.command.example;

import org.bukkit.entity.Player;

import pw.owen.mobs.bean.skill.Skill;
import pw.owen.mobs.command.PluginCommand;
import pw.owen.mobs.run.Main;
import pw.owen.mobs.utils.Send;

public class Skill_CMD implements PluginCommand {

	@Override
	public String[] getBranch() {
		// TODO �Զ����ɵķ������
		return new String[] { "mobs", "skill" };
	}

	@Override
	public boolean run(Player p, String[] args, String auto) throws Exception {
		if (args.length == 0)
			return false;
		if (args[0].equalsIgnoreCase("new")) {
			if (args.length != 3)
				return false;

			if (Skill.isSkill(args[1]) != -1) {
				Send.sendPluginMessage(p, "�����Ѵ���.");
				return true;
			}

			Skill skill = Skill.newSkill(args[2], args[1]);
			if (skill == null) {
				Send.sendPluginMessage(p, "�ü������Ͳ�����,������ڲ�����.");
				return true;
			}
			Send.sendPluginMessage(p, "�����ɹ�.");
			Main.setsSkill(skill);
				Main.saveYml();
			return true;

		} else if (args[0].equalsIgnoreCase("select")) {
			if (args.length != 2)
				return false;

			if (Skill.isSkill(args[1]) == -1) {
				Send.sendPluginMessage(p, "�ü��ܲ�����.");
				return true;
			}

			Main.setsSkill(Skill.getSkills().get(Skill.isSkill(args[1])));
			Send.sendPluginMessage(p, "ѡ��ɹ�.");
			return true;

		} else if (args[0].equalsIgnoreCase("type")) {
			p.sendMessage(Skill.getTypes());
			return true;
		} else if (args[0].equalsIgnoreCase("list")) {
			if (args.length == 1)
				p.sendMessage(Skill.getList());
			else if (args.length == 2)
				p.sendMessage(Skill.getList(args[1]));
			else
				return false;

			return true;
		} else if (args[0].equalsIgnoreCase("help")) {
			if (args.length != 2)
				return false;
			p.sendMessage(Skill.help(args[1]));
			return true;

		}

		else if (Main.getsSkill() == null) {
			Send.sendPluginMessage(p, "����ѡ��һ������.");
			return true;
		} else if (args[0].equalsIgnoreCase("see")) {
			p.sendMessage(Main.getsSkill().see());
			return true;
		} else if (args[0].equalsIgnoreCase("modify")) {
			return Main.getsSkill().cmdManager(args, p);

		}

		return false;

	}

	
}
