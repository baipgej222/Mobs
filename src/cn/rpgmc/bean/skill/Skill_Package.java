package cn.rpgmc.bean.skill;

import java.util.ArrayList;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;

public class Skill_Package extends Skill{
private ArrayList<Skill> skills=new ArrayList<Skill>();
	public Skill_Package(ConfigurationSection cfg) {
		super(cfg);
		// TODO �Զ����ɵĹ��캯�����
		
	}
	@Override
	public void save() {
		// TODO �Զ����ɵķ������
		super.save();
	}
	

	@Override
	protected void run(Entity entity) {
		// TODO �Զ����ɵķ������
		
	}

}
