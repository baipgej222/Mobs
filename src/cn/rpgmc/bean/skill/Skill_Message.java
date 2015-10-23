package cn.rpgmc.bean.skill;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import cn.rpgmc.bean.mob.Mob;

public class Skill_Message extends Skill {

	private String msg;

	public Skill_Message() {
		super();
	}

	public Skill_Message(ConfigurationSection cfg) {
		super(cfg);
	}

	public Skill_Message(String sName2, ConfigurationSection cfg2) {
		super(sName2, cfg2);
	}

	@Override
	public String getType() {
		return "Message";
	}

	@Override
	public String help() {
		return "��������:�Ի�����\n"
				+ "���ܽ���:��������ʱ���жԻ�.\n"
				+ "ָ��:\n"
				+ "  /mobs skill modify msg [�Ի�����] \n(���жԻ����ݵ�%a%�����ͷż��ܶ���,%b%�����Ի�����Ҷ���)";
	}

	@Override
	protected void skillNext(ConfigurationSection cfg) {
		this.msg = cfg.getString("msg");
	}

	@Override
	protected void newSkillNext() {
		this.msg = "";
	}

	@Override
	protected void saveNext() {
		getCfg().set("msg", this.msg);

	}

	@Override
	public String seeNext() {
		return "  �Ի�����:" + this.msg + "\n";
	}

	@Override
	public boolean isAuto() {
		return true;
	}

	@Override
	protected boolean cmdElse(String[] args, Player p) {
		if (args[0].equalsIgnoreCase("msg")) {
			if (args.length != 2)
				return false;
			msg = args[1];
			return true;

		}

		return false;
	}

	@Override
	protected void run(Mob mob, Entity entity) {
		if (entity instanceof Player)
			((Player) entity).sendMessage(msg
					.replaceAll("%a%",
							((LivingEntity) mob.getE()).getCustomName())
					.replaceAll("%b%", ((Player) entity).getDisplayName())
					.replaceAll("&", "��"));
	}

}
