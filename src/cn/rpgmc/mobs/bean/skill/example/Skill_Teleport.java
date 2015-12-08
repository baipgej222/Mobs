package cn.rpgmc.mobs.bean.skill.example;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import cn.rpgmc.mobs.bean.mob.Mob;
import cn.rpgmc.mobs.bean.skill.Skill;
import cn.rpgmc.mobs.utils.Calc;

public class Skill_Teleport extends Skill {

	private String world;
	private String x;
	private String y;
	private String z;

	public Skill_Teleport() {
	}

	public Skill_Teleport(ConfigurationSection cfg) {
		super(cfg);
	}

	public Skill_Teleport(String sName2, ConfigurationSection cfg2) {
		super(sName2, cfg2);
	}

	@Override
	public String getType() {
		return "Teleport";
	}

	@Override
	public String help() {
		return "��������:���ͼ���\n"
				+ "���ܽ���:��Ŀ����д���.\n"
				+ "ָ��:\n"
				+ "  /mobs skill modify loc [x] [y] [z] [world] \n(����x��%x1%����ʹ�ü����ߵ�x����,%x2%����ʩ���ߵ�x����(֧�ֹ�ʽ,����:%x1%+(%x1%-%x2%)/3.),�Դ�����.world�е�%w%������������)";
	}

	@Override
	protected void skillNext(ConfigurationSection cfg) {
		if (cfg.getConfigurationSection("loc") == null)
			cfg.createSection("loc");

		ConfigurationSection locm = cfg.getConfigurationSection("loc");
		if (locm.getString("X") != null)
			if (locm.getString("Y") != null)
				if (locm.getString("Z") != null)
					if (locm.getString("World") != null) {
						this.world = locm.getString("World");
						this.x = locm.getString("X");
						this.y = locm.getString("Y");
						this.z = locm.getString("Z");
					}
	}

	@Override
	protected void newSkillNext() {
		this.world = "";
		this.x = "";
		this.y = "";
		this.z = "";
	}

	@Override
	protected void saveNext() {
		if (getCfg().getConfigurationSection("loc") == null)
			getCfg().createSection("loc");
		getCfg().getConfigurationSection("loc").set("World", world);
		getCfg().getConfigurationSection("loc").set("X", x);
		getCfg().getConfigurationSection("loc").set("Y", y);
		getCfg().getConfigurationSection("loc").set("Z", z);
	}

	@Override
	public String seeNext() {
		return "  ����λ��:\n" + "  World:" + world + "\n" + "  X:" + x + "\n"
				+ "  Y:" + y + "\n" + "  Z:" + z + "\n";
	}

	@Override
	public boolean isAuto() {
		return true;
	}

	@Override
	protected boolean cmdElse(String[] args, Player p) {

		if (args[0].equalsIgnoreCase("loc")) {
			if (args.length != 5)
				return false;

			this.world = args[4];
			this.x = args[1];
			this.y = args[2];
			this.z = args[3];
			return true;
		}

		return false;
	}

	public void run(Mob mob, Entity[] es, Event event) {
		for (int i = 0; i < es.length; i++)
			this.run(mob, es[i], event);
	}

	public void run(Mob mob, Entity entity, Event event) {
		World w = Bukkit.getWorld(world.replaceAll("%w%", entity.getWorld()
				.getName()));
		Double X = Calc.calc(x.replaceAll("%x1%",
				String.valueOf(mob.getE().getLocation().getX())).replaceAll(
				"%x2%", String.valueOf(entity.getLocation().getX())));
		Double Y = Calc.calc(y.replaceAll("%y1%",
				String.valueOf(mob.getE().getLocation().getY())).replaceAll(
				"%y2%", String.valueOf(entity.getLocation().getY())));
		Double Z = Calc.calc(z.replaceAll("%z1%",
				String.valueOf(mob.getE().getLocation().getZ())).replaceAll(
				"%z2%", String.valueOf(entity.getLocation().getZ())));
		if (w != null)
			if (X != null)
				if (Y != null)
					if (Z != null)
						entity.teleport(new Location(w, X, Y, Z));
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
