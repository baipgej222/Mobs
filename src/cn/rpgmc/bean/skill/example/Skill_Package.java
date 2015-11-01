package cn.rpgmc.bean.skill.example;

import java.util.ArrayList;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import cn.rpgmc.bean.mob.Mob;
import cn.rpgmc.bean.skill.Skill;
import cn.rpgmc.utils.Send;

public class Skill_Package extends Skill {
	private ArrayList<String> skills;

	public Skill_Package() {
	}

	public Skill_Package(ConfigurationSection cfg) {
		super(cfg);
	}

	public Skill_Package(String sName2, ConfigurationSection cfg2) {
		super(sName2, cfg2);
	}

	@Override
	public String getType() {
		return "Package";
	}

	@Override
	public String help() {
		return "��������:���ܰ�\n" + "���ܽ���:ͬʱִ�ж������,�����Ӽ�����ȴ�ͼ���.\n" + "ָ��:\n"
				+ "  /mobs skill modify skill add [������]  \n"
				+ "  /mobs skill modify skill del [�б����] \n"
				+ "  /mobs skill modify skill list \n";
	}

	@Override
	protected void skillNext(ConfigurationSection cfg) {
		this.skills = (ArrayList<String>) cfg.getList("skills");
	}

	@Override
	protected void newSkillNext() {
		this.skills = new ArrayList<String>();
	}

	@Override
	protected void saveNext() {
		getCfg().set("skills", this.skills);

	}

	@Override
	public String seeNext() {

		String str = "  �����б�:\n";
		if (skills != null)
			for (int i = 0; i < skills.size(); i++) {
				str += "  " + skills.get(i) + "\n";
			}

		return str;
	}

	@Override
	public boolean isAuto() {
		return true;
	}

	@Override
	protected boolean cmdElse(String[] args, Player p) {

		if (args.length >= 2)
			if (args[0].equalsIgnoreCase("skill")) {
				if (args[1].equalsIgnoreCase("del")) {

					if (args.length == 3) {
						skills.remove(Integer.parseInt(args[2]));
						return true;
					}
				} else if (args[1].equalsIgnoreCase("add")) {

					if (args.length == 3) {
						if (Skill.isSkill(args[2]) != -1)
							skills.add(args[2]);
						else
							Send.sendPluginMessage(p, "�ü��ܲ�����.");
						return true;
					}

				} else if (args[1].equalsIgnoreCase("list")) {
					if (args.length == 2) {
						String str = "�����б�:";
						if (skills != null)
							for (int i = 0; i < skills.size(); i++) {
								if (i != 0)
									str += ",";

								str += i + ":" + skills.get(i);
							}
						p.sendMessage(str);
						return true;
					}

				}
			}

		return false;
	}

	@Override
	public void run(Mob mob, Entity entity) {
		for (int i = 0; i < skills.size(); i++) {
			if (Skill.isSkill(skills.get(i)) == -1)
				continue;

			Skill sk = Skill.getSkill(skills.get(i));
			sk.run(mob, entity);
		}

	}

}
