package cn.rpgmc.bean.skill;

import java.util.ArrayList;

import org.bukkit.configuration.ConfigurationSection;

public abstract class Skill_Package extends Skill {
	public Skill_Package(ConfigurationSection cfg) {
		super(cfg);
		// TODO �Զ����ɵĹ��캯�����
	}

	private ArrayList<Skill> skills = new ArrayList<Skill>();

}
