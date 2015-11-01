package cn.rpgmc.command.example;

import java.io.IOException;

import org.bukkit.entity.Player;

import cn.rpgmc.bean.skill.Skill;
import cn.rpgmc.command.PluginCommand;
import cn.rpgmc.run.Main;
import cn.rpgmc.utils.Send;

public class Skill_CMD implements PluginCommand {

	@Override
	public String[] getBranch() {
		// TODO �Զ����ɵķ������
		return new String[] { "mobs", "skill" };
	}

	@Override
	public boolean run(Player p, String[] args, String auto) {
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
			try {
				Main.saveYml();
			} catch (IOException e) {
				// TODO �Զ����ɵ� catch ��
				e.printStackTrace();
			}
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
