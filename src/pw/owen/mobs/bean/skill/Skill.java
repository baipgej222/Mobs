package pw.owen.mobs.bean.skill;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import pw.owen.mobs.bean.mob.Mob;
import pw.owen.mobs.bean.mob.MobModel;
import pw.owen.mobs.run.Main;
import pw.owen.mobs.utils.Send;

/**
 * 
 * @author owen
 * @see 继承该类需要继承所有构造方法,否则报错:
 *      <P/>
 *      super(String,ConfigurationSection)
 *      <P/>
 *      super(ConfigurationSection)
 *      <P/>
 *      super()
 */

public abstract class Skill {
	private Map<Mob, Long> skillRun = new HashMap<Mob, Long>();
	private String sName = null;
	private static ArrayList<Class<? extends Skill>> types = new ArrayList<Class<? extends Skill>>();
	private static ArrayList<Skill> skills = new ArrayList<Skill>();
	private ConfigurationSection cfg = null;
	private double chance = 0;
	private int cooling = 0;
	public static final String TRIGGER_CYCLE = "TRIGGER_CYCLE";// 周期
	public static final String TRIGGER_ATTACK = "TRIGGER_ATTACK";// 攻击
	public static final String TRIGGER_HURT = "TRIGGER_HURT";// 受到伤害
	public static final String TRIGGER_DYING = "TRIGGER_DYING";// 濒死
	public static final String TRIGGER_TARGET = "TRIGGER_TARGET";// 瞄准
	public static final String TRIGGER_BETARGET = "TRIGGER_BETARGET";// 被瞄准
	public static final String TRIGGER_BESPAWN = "TRIGGER_BESPAWN";// 被产生
	private String trigger = null;
	public static final String RANGE_WORLD = "RANGE_WORLD";
	public static final String RANGE_TARGET = "RANGE_TARGET";
	public static final String RANGE_CHUNK = "RANGE_CHUNK";
	public static final String RANGE_NEARBY = "RANGE_NEARBY";
	public static final String RANGE_PLAYER = "RANGE_PLAYER";
	private String range = null;
	private ArrayList<String> enemys = new ArrayList<String>();

	public abstract boolean canTriggerToCycle();

	public abstract boolean canTriggerToAttack();

	public abstract boolean canTriggerToHurt();

	public abstract boolean canTriggerToDying();

	public abstract boolean canTriggerToTarget();

	public abstract boolean canTriggerToBeTarget();

	public abstract boolean canTriggerToBeSpawn();

	public abstract boolean canRangeToWorld();

	public abstract boolean canRangeToTarget();

	public abstract boolean canRangeToChunk();

	public abstract boolean canRangeToPlayer();

	public abstract boolean canRangeToNearby();
	public static void setSkills(ArrayList<Skill> skills) {
		Skill.skills = skills;
	}

	public String[] getCanExecuteVersion() {
		return new String[] { "1.7", "1.8" };
	}

	public static void registerSkill(Class<? extends Skill> c) {
		types.add(c);
	}

	public static Skill getStatic() {
		if (Main.getCLoader() == null)
			return null;
		String name = new SecurityManager() {
			public String getClassName() {
				return getClassContext()[1].getName();
			}
		}.getClassName();
		try {
			if (Main.getCLoader().loadClass(name) == null)
				return null;
		} catch (Exception e1) {
			// TODO 自动生成的 catch 块
			e1.printStackTrace();
			return null;
		}

		try {
			return (Skill) Main.getCLoader().loadClass(name).newInstance();
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
			return null;
		}
	}

	public abstract String getType();

	public String getsName() {
		return sName;
	}

	public static ArrayList<Skill> getSkills() {
		return skills;
	}

	public static void addSkills(Skill skill) {
		for (int i = 0; i < skills.size(); i++) {
			if (skills.get(i).getsName().equalsIgnoreCase(skill.getsName())) {
				skills.set(i, skill);
				return;
			}

		}
		skills.add(skill);
	}

	public static Skill newSkill(String tt, String sName) {
		Class t = null;
		try {
			for (int i = 0; i < types.size(); i++) {

				String str = types.get(i).newInstance().getType();
				if (str.equalsIgnoreCase(tt)) {

					t = types.get(i);

				}

			}
		} catch (Exception e) {

			e.printStackTrace();
			return null;
		}
		if (t == null)
			return null;

		try {
			return (Skill) t.getDeclaredConstructor(String.class,
					ConfigurationSection.class).newInstance(sName,
					Main.getCfg().getConfigurationSection("Skill"));
		} catch (Exception e) {
			if (e instanceof InvocationTargetException) {
				try {
					throw ((InvocationTargetException) e).getTargetException();
				} catch (Throwable e1) {
					// TODO 自动生成的 catch 块
					e1.printStackTrace();
				}
			}
			e.printStackTrace();
			return null;

		}

	}

	public static boolean isSkills(Skill skill) {
		for (int i = 0; i < skills.size(); i++) {
			if (skills.get(i) == skill) {

				return true;
			}

		}
		return false;
	}

	public int getCooling() {
		return cooling;
	}

	public void setCooling(int cooling) {
		this.cooling = cooling;
	}

	public static boolean isTrigger(String trigger) {
		if (!trigger.equalsIgnoreCase(TRIGGER_CYCLE))
			if (!trigger.equalsIgnoreCase(TRIGGER_ATTACK))
				if (!trigger.equalsIgnoreCase(TRIGGER_HURT))
					if (!trigger.equalsIgnoreCase(TRIGGER_DYING))
						if (!trigger.equalsIgnoreCase(TRIGGER_TARGET))
							if (!trigger.equalsIgnoreCase(TRIGGER_BETARGET))
								if (!trigger.equalsIgnoreCase(TRIGGER_BESPAWN))
									return false;
		return true;
	}

	public static boolean isRange(String range) {
		if (!range.equalsIgnoreCase(RANGE_WORLD))
			if (!range.equalsIgnoreCase(RANGE_TARGET))
				if (!range.equalsIgnoreCase(RANGE_CHUNK))
					if (!range.equalsIgnoreCase(RANGE_PLAYER))
						if (!range.toUpperCase().startsWith(
								RANGE_NEARBY.toUpperCase()))
					return false;
		return true;
	}

	public boolean cmdManager(String[] args, Player p) {
		if (args.length < 2)
			return false;

		if (args[1].equalsIgnoreCase("chance")) {
			if (args.length != 3)
				return false;
			setChance(Double.parseDouble(args[2]));

		} else if (args[1].equalsIgnoreCase("copy")) {
			if (args.length != 3)
				return false;

			if (MobModel.getMobModel(args[2]) != null) {
				Send.sendPluginMessage(p, "将要复制的技能名已存在");
				return true;
			}


			Skill.newSkill(Main.copySection(getCfg(), Main.getCfg()
					.getConfigurationSection("Skill"), args[2]));


		} else if (args[1].equalsIgnoreCase("del")) {
			if (args.length != 2)
				return false;
			this.remove();
			Main.setsSkill(null);

		} else if (args[1].equalsIgnoreCase("cooling")) {
			if (args.length != 3)
				return false;
			setCooling(Integer.parseInt(args[2]));

		} else if (args[1].equalsIgnoreCase("trigger")) {
			if (args.length != 3)
				return false;
			if (!isTrigger(args[2])) {
				Send.sendPluginMessage(p, "触发类型不存在.");
				return true;
			}

			if (!canRun(args[2], null)) {
				Send.sendPluginMessage(p, "该技能不能使用这种触发类型.");
				return true;
			}

			setTrigger(args[2].toUpperCase());

		} else if (args[1].equalsIgnoreCase("range")) {
			if (args.length != 3)
				return false;
			if (!isRange(args[2])) {
				Send.sendPluginMessage(p, "作用类型不存在.");
				return true;

			}

			if (!canRun(null, args[2])) {
				Send.sendPluginMessage(p, "该技能不能使用这种作用类型.");
				return true;
			}

			setRange(args[2].toUpperCase());

		} else if (args[1].equalsIgnoreCase("enemys")) {
			if (args.length < 3)
				return false;

			if (args[2].equalsIgnoreCase("add")) {
				if (args.length != 4)
					return false;
				enemys.add(args[3].toUpperCase());

			} else if (args[2].equalsIgnoreCase("del")) {
				if (args.length != 4)
					return false;
				enemys.remove(Integer.parseInt(args[3]));
			} else if (args[2].equalsIgnoreCase("list")) {
				String str = "";
				for (int i = 0; i < enemys.size(); i++) {
					if (i != 0)
						str += ",";

					str += i + ":" + enemys.get(i);
				}

				p.sendMessage("敌人列表:" + str);
				return true;

			} else
				return false;

		} else {

			List<String> al = new ArrayList<String>();
			for (int i = 0; i < args.length; i++) {
				if (i != 0)
					al.add(args[i]);
			}

			boolean b = cmdElse(al.toArray(new String[al.size()]), p);
			save();
			if (b)
				Send.sendPluginMessage(p, "设置成功.");

			try {
				Main.saveYml();
			} catch (IOException e) {
				Send.sendPluginMessage(p, "保存失败.");
			}

			return b;
		}

		save();
		Send.sendPluginMessage(p, "设置成功.");
		try {
			Main.saveYml();
		} catch (IOException e) {
			Send.sendPluginMessage(p, "保存失败.");
		}

		return true;

	}

	public boolean remove() {
		
		if (!getSkills().remove(this))
			return false;
		ConfigurationSection m = Main.getCfg().getConfigurationSection("Skill");
		if (m == null)
			return true;
		m.set(this.getsName(), null);
		return true;
	}

	/**
	 * 技能实例独有的命令
	 * 
	 */
	protected abstract boolean cmdElse(String[] args, Player p);

	/**
	 * 执行技能实例
	 * 
	 */
	public abstract void run(Mob mob, Entity[] es, Event event);

	/**
	 * 返回技能实例属性
	 * 
	 */
	public String see() {
		String str = "技能详细信息:\n";
		str += "  名字:" + getsName() + "\n";
		str += "  几率:" + getChance() + "\n";
		str += "  触发类型:" + getTrigger() + "\n";
		str += "  作用范围:" + getRange() + "\n";
		str += "  冷却时间:" + getCooling() + "\n";
		String s = "";
		for (int i = 0; i < getEnemys().size(); i++) {
			s += "    " + getEnemys().get(i) + "\n";

		}
		if (s.equalsIgnoreCase(""))
			s = "无\n";

		str += "  触发对象列表:" + s;
		return str + seeNext();

	}

	public abstract String seeNext();

	/**
	 * 返回技能类型列表
	 * 
	 */
	public static String getList() {
		// TODO 自动生成的方法存根
		return getAllList();
	}

	private static String getAllList() {
		String s = "";
		for (int i = 0; i < getSkills().size(); i++) {
			if (i != 0)
				s += ",";

			s += getSkills().get(i).getsName();

		}
		s = "所有类型技能列表:" + s;
		return s;
	}

	public static String getList(String str) {
		if (isType(str) == null)
			return "没有这个技能类型";

		String s = "";
		for (int i = 0; i < getSkills().size(); i++) {
			try {
				if (getSkills().get(i).getClass().newInstance().getType()
						.equalsIgnoreCase(str)) {
					if (i != 0)
						s += ",";

					s += getSkills().get(i).getsName();

				}
			} catch (Exception e) {

			}

		}
		s = str + "类型技能列表:" + s;
		return s;
	}

	/**
	 * 返回向下技能类型帮助文本
	 * 
	 */
	public static String help(String str) {
		Class<? extends Skill> skill = isType(str);
		if (skill == null)
			return "该技能类型不存在.";
		try {
			String s = skill.newInstance().help();
			return mainHelp() + "\n" + s;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}

	}

	public abstract String help();

	public static String mainHelp() {
		return "  /mobs skill modify chance [触发几率] 设置该技能被触发的几率.\n"
				+ "  /mobs skill modify cooling [冷却时间(tick)] 使用一次后的冷却时间(20tick约等于1秒)\n"
				+ "  /mobs skill modify trigger [触发方式] 触发技能的方式,可选:\n"
				+ "    TRIGGER_CYCLE 周期\n"
				+ "    TRIGGER_ATTACK 攻击\n"
				+ "    TRIGGER_HURT 受到伤害\n"
				+ "    TRIGGER_DYING 濒死\n"
				+ "    TRIGGER_TARGET 瞄准\n"
				+ "    TRIGGER_BETARGET 被瞄准\n"
				+ "    TRIGGER_BESPAWN 被产生(怪物出生)\n"
				+ "  /mobs skill modify range [作用范围] 触发后技能指向的对象,可选:\n"
				+ "    RANGE_WORLD 所在世界\n"
				+ "    RANGE_CHUNK 所在区块\n"
				+ "    RANGE_NEARBY_X 附近,X需要换为附近的范围,必须是正整数. "
				+ "    RANGE_TARGET 触发者(该类型作用方式不可为周期.)\n"
				+ "    RANGE_PLAYER 所有在线玩家\n"
				+ "  /mobs skill modify enemys [add/del/list] 可作用实体列表(不填视为除了怪物自身全部作用),例子:"
				+ "    /mobs skill modify enemys add PLAYER (该技能可以作用于所有玩家)"
				+ "    /mobs skill modify enemys add ZOMBIE (该技能可以作用于所有僵尸)"
				+ "    /mobs skill modify enemys add ALL (该技能可以作用于所有生物)"
				+ "    /mobs skill modify enemys add ME (该技能可以作用于自己)"
				+ "  /mobs skill modify del 删除当前技能"
				+ "  /mobs skill modify copy [新技能名] 复制当前技能到新的名字";


	}

	public static Class<? extends Skill> isType(String str) {
		for (int i = 0; i < types.size(); i++) {
			try {
				if (types.get(i).newInstance().getType().equalsIgnoreCase(str)) {
					return types.get(i);

				}
			} catch (Exception e) {

				return null;
			}
		}

		return null;
	}

	public boolean isAuto() {
		return false;
	}

	public static String getTypes() {
		String str = "";
		for (int i = 0; i < types.size(); i++) {
			try {
				str += types.get(i).newInstance().getType();

				if (i != types.size() - 1)
					str += ",";

			} catch (Exception e) {
				e.printStackTrace();
				Bukkit.getLogger().info("获取类型失败!");
			}
		}
		Bukkit.getLogger().info(types.size() + "个技能");
		str = "技能类型列表:" + str;
		return str;
	}

	public static Skill getSkill(String skill) {

		for (int i = 0; i < skills.size(); i++) {
			if (skills.get(i).getsName().equalsIgnoreCase(skill))
				return skills.get(i);
		}
		return null;
	}

	public String getRange() {
		return range;
	}

	public ArrayList<String> getEnemys() {
		return enemys;

	}

	protected Skill() {

	}

	protected Skill(String sName, ConfigurationSection cfg) {
		cfg.createSection(sName);
		this.cfg = cfg.getConfigurationSection(sName);
		this.sName = sName;
		this.trigger = TRIGGER_ATTACK;
		this.chance = 25;
		this.range = RANGE_TARGET;
		this.cooling = 500;
		addSkills(this);
		newSkillNext();
		save();
	}

	protected Skill(ConfigurationSection cfg) {
		this.cfg = cfg;
		this.trigger = cfg.getString("trigger");
		this.chance = cfg.getInt("chance");
		this.range = cfg.getString("range");
		this.cooling = cfg.getInt("cooling");
		this.enemys = (ArrayList<String>) cfg.getList("enemys");
		this.sName = cfg.getName();
		addSkills(this);
		skillNext(cfg);

	}

	protected abstract void skillNext(ConfigurationSection cfg);

	/**
	 * 新建时进行的操作/super()
	 */

	protected abstract void newSkillNext();

	/**
	 * 将属性对其到cfg文件/super()
	 */
	public void save() {
		saveNext();
		cfg.set("trigger", trigger);
		cfg.set("chance", chance);
		cfg.set("range", range);
		cfg.set("enemys", enemys);
		cfg.set("cooling", cooling);
		try {
			cfg.set("type", this.getClass().newInstance().getType());
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}

	}

	protected abstract void saveNext();

	public boolean isEnemy(String en) {
		if (EntityType.fromName(en) == null & !en.equalsIgnoreCase("PLAYER"))
			return false;

		for (int i = 0; i < enemys.size(); i++) {
			if (enemys.get(i).equalsIgnoreCase(en))
				return true;
		}

		if (enemys.size() == 0) {
			return true;
		}

		return false;

	}

	public ConfigurationSection getCfg() {
		return cfg;
	}

	public boolean addEnemys(String enemy) {
		if (enemys == null)
			enemys = new ArrayList<String>();

		try {
			if (Class.forName(enemy) != null)
				if (Class.forName(enemy).isInstance(Entity.class))
					enemys.add(enemy);
				else
					return false;
		} catch (ClassNotFoundException e) {
			return false;
		}
		return true;
	}

	public void setRange(String range) {
		this.range = range;
	}

	public String getTrigger() {
		return trigger;
	}

	public double getChance() {
		return chance;
	}

	public void setChance(double chance) {
		this.chance = chance;
	}

	public void setTrigger(String trigger) {
		this.trigger = trigger;
	}

	public void runSkill(Mob mob, Object t, Event event) {
		if (t == null)
			t = new ArrayList<Entity>();
		List<Entity> tt = new ArrayList<Entity>();

		if (t instanceof List) {
			if (((List) t).size() != 0)
				if (((List) t).get(0) instanceof Entity) {
					tt = (List<Entity>) t;
				}

		} else if (t instanceof Entity) {
			ArrayList<Entity> as = new ArrayList<Entity>();
			as.add((Entity) t);
			tt = as;
		}


		runSkill(mob, tt, event);

	}

	public void runSkillLater(Mob m, long time) {
		skillRun.put(m, (System.currentTimeMillis() + time));
	}


	public void skillRun() {
		Object[] key = skillRun.keySet().toArray();
for(int i=0;i<key.length;i++)
			if (skillRun.get(key[i]) != null)
	if(skillRun.get(key[i])<=System.currentTimeMillis())
 {


					((Mob) key[i]).runSkillAuto(this);
				skillRun.remove(key[i]);
			}

	}

	public static void skillRunAll() {
		for (int i = 0; i < skills.size(); i++)
			if (skills.get(i) != null)
				skills.get(i).skillRun();
	}

	private List<Entity> getNearby(Entity e, String str) {
		int i = 20;
		if (str.startsWith(RANGE_NEARBY + "_")) {
			String[] strs = str.split("_");
			if (strs.length >= 3)
				if (Integer.parseInt(strs[2]) > 0)
					i = Integer.parseInt(strs[2]);

		}
		return e.getNearbyEntities(i, i, i);
	}

	public void runSkill(Mob mob, List<Entity> e, Event event) {

		if (!canRun())
			return;

		if (getRange().equalsIgnoreCase(RANGE_CHUNK))
			e = Arrays
					.asList(mob.getE().getLocation().getChunk().getEntities());
		else if (getRange().equalsIgnoreCase(RANGE_WORLD))
			e = mob.getE().getWorld().getEntities();
		else if (getRange().equalsIgnoreCase(RANGE_TARGET)) {

		} else if (getRange().equalsIgnoreCase(RANGE_PLAYER)) {
			e = new ArrayList<Entity>(Bukkit.getOnlinePlayers());
		} else if (getRange().startsWith(RANGE_NEARBY))
			e = getNearby(mob.getE(), getRange());
		else
			return;

		if (e == null)
			return;

		if (chance > (Math.random() * 100)) {
			long time = System.currentTimeMillis();
			if (time - mob.getBeCooling(this) > cooling * 50) {
				mob.setBeCooling(this, System.currentTimeMillis());
				List<Entity> ls = new ArrayList<Entity>();
				for (int i = 0; i < e.size(); i++) {
					if (!isEnemy("ALL")) {
						if (e.get(i) != null)
						if (isEnemy(e.get(i).getType().name()))
								ls.add(e.get(i));
					} else
						ls.add(e.get(i));

				}
				if (isEnemy("ME"))
					ls.add(mob.getE());

				this.run(mob, (Entity[]) ls.toArray(new Entity[ls.size()]),
						event);

			}

		}

	}

	public boolean canRun(String targer, String range) {
		if (range != null)
		if (!canRangeToChunk())
			if (range.equalsIgnoreCase(RANGE_CHUNK))
				return false;

		if (range != null)
		if (!canRangeToTarget())
			if (range.equalsIgnoreCase(RANGE_TARGET))
				return false;

		if (range != null)
		if (!canRangeToWorld())
			if (range.equalsIgnoreCase(RANGE_WORLD))
				return false;

		if (range != null)
			if (!canRangeToPlayer())
				if (range.equalsIgnoreCase(RANGE_PLAYER))
					return false;

		if (range != null)
		if (!canRangeToNearby())
			if (range.toUpperCase().startsWith(RANGE_NEARBY.toUpperCase()))
				return false;

		if (targer != null)
		if (!canTriggerToAttack())
			if (targer.equalsIgnoreCase(TRIGGER_ATTACK))
				return false;

		if (targer != null)
		if (!canTriggerToBeSpawn())
			if (targer.equalsIgnoreCase(TRIGGER_BESPAWN))
				return false;

		if (targer != null)
		if (!canTriggerToBeTarget())
			if (targer.equalsIgnoreCase(TRIGGER_BETARGET))
				return false;

		if (targer != null)
		if (!canTriggerToCycle())
			if (targer.equalsIgnoreCase(TRIGGER_CYCLE))
				return false;

		if (targer != null)
		if (!canTriggerToDying())
			if (targer.equalsIgnoreCase(TRIGGER_DYING))
				return false;

		if (targer != null)
		if (!canTriggerToHurt())
			if (targer.equalsIgnoreCase(TRIGGER_HURT))
				return false;

		if (targer != null)
		if (!canTriggerToTarget())
			if (targer.equalsIgnoreCase(TRIGGER_TARGET))
				return false;
		return true;
	}

	public boolean canRun() {

		if (getRange() != null)
		if (!canRangeToChunk())
			if (getRange().equalsIgnoreCase(RANGE_CHUNK))
				return false;

		if (getRange() != null)
		if (!canRangeToTarget())
			if (getRange().equalsIgnoreCase(RANGE_TARGET))
				return false;

		if (getRange() != null)
		if (!canRangeToWorld())
			if (getRange().equalsIgnoreCase(RANGE_WORLD))
				return false;

		if (getRange() != null)
			if (!canRangeToPlayer())
				if (getRange().equalsIgnoreCase(RANGE_PLAYER))
					return false;

		if (getRange() != null)
		if (!canRangeToNearby())
			if (getRange().toUpperCase().startsWith(RANGE_NEARBY.toUpperCase()))
				return false;

		if (getTrigger() != null)
		if (!canTriggerToAttack())
			if (getTrigger().equalsIgnoreCase(TRIGGER_ATTACK))
				return false;

		if (getTrigger() != null)
		if (!canTriggerToBeSpawn())
			if (getTrigger().equalsIgnoreCase(TRIGGER_BESPAWN))
				return false;

		if (getTrigger() != null)
		if (!canTriggerToBeTarget())
			if (getTrigger().equalsIgnoreCase(TRIGGER_BETARGET))
				return false;

		if (getTrigger() != null)
		if (!canTriggerToCycle())
			if (getTrigger().equalsIgnoreCase(TRIGGER_CYCLE))
				return false;

		if (getTrigger() != null)
		if (!canTriggerToDying())
			if (getTrigger().equalsIgnoreCase(TRIGGER_DYING))
				return false;

		if (getTrigger() != null)
		if (!canTriggerToHurt())
			if (getTrigger().equalsIgnoreCase(TRIGGER_HURT))
				return false;

		if (getTrigger() != null)
		if (!canTriggerToTarget())
			if (getTrigger().equalsIgnoreCase(TRIGGER_TARGET))
				return false;
		return true;
	}

	public void runSkill(Mob mob, Entity target, Event event) {
		ArrayList<Entity> a = new ArrayList<Entity>();
		a.add(target);
		runSkill(mob, a, event);

	}

	public static int isSkill(String string) {

		for (int i = 0; i < skills.size(); i++) {
			if (skills.get(i).getsName().equalsIgnoreCase(string)) {
				return i;
			}
		}
		return -1;
	}

	public static Skill newSkill(ConfigurationSection config) {

		Class<? extends Skill> t = null;
		String tt = config.getString("type");
		try {
			for (int i = 0; i < types.size(); i++) {
				if (types.get(i).newInstance().getType().equalsIgnoreCase(tt)) {
					t = types.get(i);
				}
			}
		} catch (Exception e) {

			e.printStackTrace();
			return null;
		}
		if (t == null)
			return null;

		try {
			return t.getDeclaredConstructor(ConfigurationSection.class)
					.newInstance(config);

		} catch (InvocationTargetException e) {
			Throwable st = e.getTargetException();// 获取目标异常
			st.printStackTrace();
			return null;
		} catch (Exception e) {

			e.printStackTrace();
			return null;

		}

	}

}
