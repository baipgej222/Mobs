package cn.rpgmc.mobs.bean.skill;

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

import cn.rpgmc.mobs.bean.mob.Mob;
import cn.rpgmc.mobs.run.Main;
import cn.rpgmc.mobs.utils.Send;

/**
 * 
 * @author owen
 * @see �̳и�����Ҫ�̳����й��췽��,���򱨴�:
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
	public static final String TRIGGER_CYCLE = "TRIGGER_CYCLE";// ����
	public static final String TRIGGER_ATTACK = "TRIGGER_ATTACK";// ����
	public static final String TRIGGER_HURT = "TRIGGER_HURT";// �ܵ��˺�
	public static final String TRIGGER_DYING = "TRIGGER_DYING";// ����
	public static final String TRIGGER_TARGET = "TRIGGER_TARGET";// ��׼
	public static final String TRIGGER_BETARGET = "TRIGGER_BETARGET";// ����׼
	public static final String TRIGGER_BESPAWN = "TRIGGER_BESPAWN";// ������
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
			// TODO �Զ����ɵ� catch ��
			e1.printStackTrace();
			return null;
		}

		try {
			return (Skill) Main.getCLoader().loadClass(name).newInstance();
		} catch (Exception e) {
			// TODO �Զ����ɵ� catch ��
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
					// TODO �Զ����ɵ� catch ��
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
				Send.sendPluginMessage(p, "�������Ͳ�����.");
				return true;
			}

			if (!canRun(args[2], null)) {
				Send.sendPluginMessage(p, "�ü��ܲ���ʹ�����ִ�������.");
				return true;
			}

			setTrigger(args[2].toUpperCase());

		} else if (args[1].equalsIgnoreCase("range")) {
			if (args.length != 3)
				return false;
			if (!isRange(args[2])) {
				Send.sendPluginMessage(p, "�������Ͳ�����.");
				return true;

			}

			if (!canRun(null, args[2])) {
				Send.sendPluginMessage(p, "�ü��ܲ���ʹ��������������.");
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

				p.sendMessage("�����б�:" + str);
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
				Send.sendPluginMessage(p, "���óɹ�.");

			try {
				Main.saveYml();
			} catch (IOException e) {
				Send.sendPluginMessage(p, "����ʧ��.");
			}

			return b;
		}

		save();
		Send.sendPluginMessage(p, "���óɹ�.");
		try {
			Main.saveYml();
		} catch (IOException e) {
			Send.sendPluginMessage(p, "����ʧ��.");
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
	 * ����ʵ�����е�����
	 * 
	 */
	protected abstract boolean cmdElse(String[] args, Player p);

	/**
	 * ִ�м���ʵ��
	 * 
	 */
	public abstract void run(Mob mob, Entity[] es, Event event);

	/**
	 * ���ؼ���ʵ������
	 * 
	 */
	public String see() {
		String str = "������ϸ��Ϣ:\n";
		str += "  ����:" + getsName() + "\n";
		str += "  ����:" + getChance() + "\n";
		str += "  ��������:" + getTrigger() + "\n";
		str += "  ���÷�Χ:" + getRange() + "\n";
		str += "  ��ȴʱ��:" + getCooling() + "\n";
		String s = "";
		for (int i = 0; i < getEnemys().size(); i++) {
			s += "    " + getEnemys().get(i) + "\n";

		}
		if (s.equalsIgnoreCase(""))
			s = "��\n";

		str += "  ���������б�:" + s;
		return str + seeNext();

	}

	public abstract String seeNext();

	/**
	 * ���ؼ��������б�
	 * 
	 */
	public static String getList() {
		// TODO �Զ����ɵķ������
		return getAllList();
	}

	private static String getAllList() {
		String s = "";
		for (int i = 0; i < getSkills().size(); i++) {
			if (i != 0)
				s += ",";

			s += getSkills().get(i).getsName();

		}
		s = "�������ͼ����б�:" + s;
		return s;
	}

	public static String getList(String str) {
		if (isType(str) == null)
			return "û�������������";

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
		s = str + "���ͼ����б�:" + s;
		return s;
	}

	/**
	 * �������¼������Ͱ����ı�
	 * 
	 */
	public static String help(String str) {
		Class<? extends Skill> skill = isType(str);
		if (skill == null)
			return "�ü������Ͳ�����.";
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
		return "  /mobs skill modify chance [��������] ���øü��ܱ������ļ���.\n"
				+ "  /mobs skill modify cooling [��ȴʱ��(tick)] ʹ��һ�κ����ȴʱ��(20tickԼ����1��)\n"
				+ "  /mobs skill modify trigger [������ʽ] �������ܵķ�ʽ,��ѡ:\n"
				+ "    TRIGGER_CYCLE ����\n"
				+ "    TRIGGER_ATTACK ����\n"
				+ "    TRIGGER_HURT �ܵ��˺�\n"
				+ "    TRIGGER_DYING ����\n"
				+ "    TRIGGER_TARGET ��׼\n"
				+ "    TRIGGER_BETARGET ����׼\n"
				+ "    TRIGGER_BESPAWN ������(�������)\n"
				+ "  /mobs skill modify range [���÷�Χ] ��������ָ��Ķ���,��ѡ:\n"
				+ "    RANGE_WORLD ��������\n"
				+ "    RANGE_CHUNK ��������\n"
				+ "    RANGE_NEARBY_X ����,X��Ҫ��Ϊ�����ķ�Χ,������������. "
				+ "    RANGE_TARGET ������(���������÷�ʽ����Ϊ����.)\n"
				+ "    RANGE_PLAYER �����������\n"
				+ "  /mobs skill modify enemys [add/del/list] ������ʵ���б�(������Ϊ���˹�������ȫ������),����:"
				+ "    /mobs skill modify enemys add PLAYER (�ü��ܿ����������������)"
				+ "    /mobs skill modify enemys add ZOMBIE (�ü��ܿ������������н�ʬ)"
				+ "    /mobs skill modify enemys add ALL (�ü��ܿ�����������������)"
				+ "    /mobs skill modify enemys add ME (�ü��ܿ����������Լ�)"
				+ "  /mobs skill modify del ɾ����ǰ����";

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
				Bukkit.getLogger().info("��ȡ����ʧ��!");
			}
		}
		Bukkit.getLogger().info(types.size() + "������");
		str = "���������б�:" + str;
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
	 * �½�ʱ���еĲ���/super()
	 */

	protected abstract void newSkillNext();

	/**
	 * �����Զ��䵽cfg�ļ�/super()
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
			// TODO �Զ����ɵ� catch ��
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
			Throwable st = e.getTargetException();// ��ȡĿ���쳣
			st.printStackTrace();
			return null;
		} catch (Exception e) {

			e.printStackTrace();
			return null;

		}

	}

}
