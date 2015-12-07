package cn.rpgmc.mobs.bean.skill.example;

import java.util.ArrayList;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import cn.rpgmc.mobs.bean.mob.Mob;
import cn.rpgmc.mobs.bean.skill.Skill;
import cn.rpgmc.mobs.utils.Send;

public class Skill_Package extends Skill {
	private ArrayList<String> skills;
	private boolean isUnit;

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
		return "��������:���ܰ�\n" + "���ܽ���:ͬʱִ�ж������(�Ա����ܵĴ�����ʽ�ʹ�����Χ),�����Ӽ�����ȴ�ͼ���.\n"
				+ "ָ��:\n"
				+ "  /mobs skill modify skill add [������]  \n"
				+ "  /mobs skill modify skill addtag [�ӳ�]  \n"
				+ "  /mobs skill modify skill del [�б����] \n"
				+ "  /mobs skill modify skill list \n"
				+ "  /mobs skill modify unit [true/false] ���ü����Ƿ�ÿ�ε�����ȡ�ͷŷ�Χ \n";
	}

	@Override
	protected void skillNext(ConfigurationSection cfg) {
		this.skills = (ArrayList<String>) cfg.getList("skills");
		this.isUnit = cfg.getBoolean("isUnit");
	}

	@Override
	protected void newSkillNext() {
		this.skills = new ArrayList<String>();
		this.isUnit = false;
	}

	@Override
	protected void saveNext() {
		getCfg().set("skills", this.skills);
		getCfg().set("isUnit", this.isUnit);

	}

	@Override
	public String seeNext() {

		String str = "  �����б�:\n";
		if (skills != null)
			for (int i = 0; i < skills.size(); i++) {
				str += "  " + skills.get(i) + "\n";
			}

		return str + "  �Ƿ񵥶���ȡ�ͷŷ�Χ:" + isUnit;
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

				} else if (args[1].equalsIgnoreCase("addtag")) {

					if (args.length == 3) {
						try {
							skills.add("sleep:" + Integer.parseInt(args[2]));
						} catch (NumberFormatException eee) {
							Send.sendPluginMessage(p, "������һ��������Ϊ�ӳ����.");
						}
						return true;
					}

				} else if (args[1].equalsIgnoreCase("list")) {
					if (args.length == 2) {
						String str = "����˳���б�:";
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
			} else if (args[0].equalsIgnoreCase("unit")) {
				if (args.length == 2) {
					isUnit = Boolean.parseBoolean(args[1]);
					return true;
				} else
					return false;

			}

		return false;
	}

	@Override
	public void run(Mob mob, Entity entity, Event event) {
		if (!isUnit)
		for (int i = 0; i < skills.size(); i++) {
			if (Skill.isSkill(skills.get(i)) == -1)
				if (skills.get(i).startsWith("sleep:"))
					try {
						Thread.sleep(Integer.parseInt(skills.get(i).replaceAll(
								"sleep:", "")));
						continue;
					} catch (Exception e) {
						// TODO �Զ����ɵ� catch ��
						e.printStackTrace();
					}
				else
				continue;


			Skill sk = Skill.getSkill(skills.get(i));
			sk.run(mob, entity, event);
			}
		else
			for (int i = 0; i < skills.size(); i++) {
				long rrr = System.currentTimeMillis();
				if (Skill.isSkill(skills.get(i)) == -1)
					if (skills.get(i).startsWith("sleep:"))
						try {
							rrr += Integer.parseInt(skills.get(i).replaceAll(
									"sleep:", ""));
							continue;
						} catch (Exception e) {
							// TODO �Զ����ɵ� catch ��
							e.printStackTrace();
						}
					else
						continue;

				Skill sk = Skill.getSkill(skills.get(i));
				sk.runSkillLater(mob, rrr);
			}

	}

	@Override
	public boolean canTriggerToCycle() {
		// TODO �Զ����ɵķ������
		return true;
	}

	@Override
	public boolean canTriggerToAttack() {
		// TODO �Զ����ɵķ������
		return true;
	}

	@Override
	public boolean canTriggerToHurt() {
		// TODO �Զ����ɵķ������
		return true;
	}

	@Override
	public boolean canTriggerToDying() {
		// TODO �Զ����ɵķ������
		return true;
	}

	@Override
	public boolean canTriggerToTarget() {
		// TODO �Զ����ɵķ������
		return true;
	}

	@Override
	public boolean canTriggerToBeTarget() {
		// TODO �Զ����ɵķ������
		return true;
	}

	@Override
	public boolean canTriggerToBeSpawn() {
		// TODO �Զ����ɵķ������
		return true;
	}

	@Override
	public boolean canRangeToWorld() {
		// TODO �Զ����ɵķ������
		return true;
	}

	@Override
	public boolean canRangeToTarget() {
		// TODO �Զ����ɵķ������
		return true;
	}

	@Override
	public boolean canRangeToChunk() {
		// TODO �Զ����ɵķ������
		return true;
	}

	@Override
	public boolean canRangeToNearby() {
		// TODO �Զ����ɵķ������
		return true;
	}

	@Override
	public boolean canRangeToPlayer() {
		// TODO �Զ����ɵķ������
		return true;
	}

}
