package pw.owen.mobs.run;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.jar.JarFile;
import java.util.zip.GZIPInputStream;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Animals;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import pw.owen.mobs.bean.mob.Mob;
import pw.owen.mobs.bean.mob.MobModel;
import pw.owen.mobs.bean.mob.MobSave;
import pw.owen.mobs.bean.skill.Skill;
import pw.owen.mobs.bean.spawn.PointSpawn;
import pw.owen.mobs.bean.spawn.Spawn;
import pw.owen.mobs.bean.spawn.WorldSpawn;
import pw.owen.mobs.command.CommandManager;
import pw.owen.mobs.thread.Manager;
import pw.owen.mobs.thread.Spawner;
import pw.owen.mobs.thread.TitleShows;
import pw.owen.mobs.utils.ErrorReport;
import pw.owen.mobs.utils.JarLoad;
import pw.owen.mobs.utils.LoggerListener;
import pw.owen.mobs.utils.Send;
import pw.owen.mobs.utils.StringEncrypt;
import pw.owen.mobs.utils.mobtype.MobType;
import sun.misc.JarFilter;

public class Main extends JavaPlugin {
	public static final String KEY = "7AD8B9A9C0E9F85EECB9A0DEA996CF9D88EE275EFAAE6E9A";
	private static Main main = null;
	private static int clickItem = 0;
	private static MobModel sMobModel = null;
	private static Spawn sSpawn = null;
	private static Skill sSkill = null;
	private static Location o = null;
	private static String V = "";
	private static FileConfiguration cfg;
	private static File f;
	private static AutoListener ls = null;
	private static File file;
	private static ArrayList<Integer> tid = new ArrayList<Integer>();
	private static ArrayList<String> MonsterSpawnBannedWorld = new ArrayList<String>();
	private static ArrayList<String> AnimalSpawnBannedWorld = new ArrayList<String>();
	private static ClassLoader classLoader;
	private static YamlConfiguration mobYml = new YamlConfiguration();
	private static File mobSaveFile = null;
	private static boolean autoErrorReporting = true;
	public static double bukkitVer;
	static{
		try {
			bukkitVer = Double.parseDouble(Bukkit.getBukkitVersion().substring(
					0, 3));
		} catch (NumberFormatException e) {
			bukkitVer = 1.7;
		}
	}
	public static YamlConfiguration getMobYml() {
		return mobYml;
	}


	public static void setMobYml(YamlConfiguration mobYml) {
		Main.mobYml = mobYml;
	}
	public static void setsMobModel(MobModel sMobModel) {
		Main.sMobModel = sMobModel;
	}

	public static void setsSpawn(Spawn sSpawn) {
		Main.sSpawn = sSpawn;
	}

	public static Skill getsSkill() {
		return sSkill;
	}

	public static void setsSkill(Skill sSkill) {
		Main.sSkill = sSkill;
	}

	public static Spawn getsSpawn() {
		return sSpawn;
	}

	public static MobModel getsMobModel() {
		return sMobModel;
	}

	public static ArrayList<String> getMonsterSpawnBannedWorld() {
		return MonsterSpawnBannedWorld;
	}

	public static ArrayList<String> getAnimalSpawnBannedWorld() {
		return AnimalSpawnBannedWorld;
	}

	public static int getClickItem() {
		return clickItem;
	}

	public static void setClickItem(int c) {
		clickItem = c;
	}

	public static Location getO() {
		return o;
	}


	public static void setO(Location location) {
		o = location;
	}

	public static FileConfiguration getCfg() {
		return cfg;
	}

	public static File getF() {
		return f;
	}

	public static Main getMain() {
		return main;
	}

	public static void setAutoErrorReporting(boolean auto) {
		autoErrorReporting = auto;
	}

	public static boolean isAutoErrorReporting() {
		return autoErrorReporting;
	}

	public static ClassLoader getCLoader() {
		return classLoader;
	}


	@Override
	public void onEnable() {
		try {

			main = this;
			Bukkit.getLogger().setFilter(new LoggerListener());
		classLoader = this.getClassLoader();
		Server server = getServer();
		PluginManager manager = server.getPluginManager();
		V = this.getDescription().getVersion();
		ls = new AutoListener();
		getServer().getPluginManager().registerEvents(ls, this);
		cfg = Bukkit.getPluginManager().getPlugin(this.getName()).getConfig();
		f = new File(Bukkit.getPluginManager().getPlugin(this.getName())
				.getDataFolder().getAbsolutePath()
				+ File.separator + "config.yml");
		ErrorReport.setCfg(f);
		file = new File(Bukkit.getPluginManager().getPlugin(this.getName())
				.getDataFolder().getAbsolutePath());
		file.mkdirs();
		if (!f.exists()) {
			try {
				f.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
			cfg.set("Version", V);
			cfg.set("Tools", 50);
			cfg.set("AutoErrorReporting", true);
			cfg.set("MonsterSpawnBannedWorld", MonsterSpawnBannedWorld);
			cfg.set("AnimalSpawnBannedWorld", AnimalSpawnBannedWorld);
			cfg.createSection("MobModel");
			cfg.createSection("PointSpawn");
			cfg.createSection("WorldSpawn");
			cfg.createSection("Skill");

				saveYml();

		} else {

				cfg.load(f);

		}
		loadSkills();
		MobType.values();
		loadYml();

			new BukkitRunnable() {

				@Override
				public void run() {
					Bukkit.getLogger().setFilter(new LoggerListener());

				}
			}.runTask(this);
			new BukkitRunnable() {

						@Override
						public void run() {
							try {
								loadAllMobs();
							} catch (Exception e) {
						ErrorReport.report(LoggerListener.toString(e));
								e.printStackTrace();
							}

						}
			}.runTask(this);

		for (int i = 0; i < Spawn.getTHREADS(); i++) {
			tid.add(Bukkit
					.getServer()
					.getScheduler()
					.scheduleSyncRepeatingTask(this, new Spawner(),
							Spawner.RUNS, Spawner.RUNS));
		}
		Bukkit.getServer()
				.getScheduler()
				.scheduleSyncRepeatingTask(this, new Manager(), Manager.RUNS,
						Manager.RUNS);

		Bukkit.getServer()
				.getScheduler()
				.scheduleSyncRepeatingTask(this, new TitleShows(),
						TitleShows.RUNS, TitleShows.RUNS);


		} catch (Exception maine) {
			ErrorReport.report(LoggerListener.toString(maine));
			try {
				ErrorReport.update();
			} catch (IOException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
			maine.printStackTrace();
		}

		if (!Main.getCfg().getString("Version").equalsIgnoreCase(Main.getV()))
			new File(Bukkit.getPluginManager().getPlugin(this.getName())
					.getDataFolder().getAbsolutePath()
					+ File.separator + "save.dat").delete();
		Send.sendConsole("┏一一一一一一一一一一一┓");
		Send.sendConsole(" --->>>>>>>>>>>>>>>>>>---");
		Send.sendConsole(" --->>>>>加载成功>>>>>---");
		Send.sendConsole(" --->>>>>>>>>>>>>>>>>>---");
		Send.sendConsole("┗一一一一一一一一一一一┛");
	}



	private void loadAllMobs() throws FileNotFoundException, IOException,
			InvalidConfigurationException {

		mobSaveFile = new File(Bukkit.getPluginManager()
				.getPlugin(this.getName()).getDataFolder().getAbsolutePath()
				+ File.separator + "save.dat");

		if (!mobSaveFile.exists())
				mobSaveFile.createNewFile();



		try {
			mobYml.load(new GZIPInputStream(new FileInputStream(mobSaveFile)));

		} catch (EOFException e) {
			return;
		}
			if (mobYml.getConfigurationSection("Mobs") == null)
				return;
			ConfigurationSection s = mobYml.getConfigurationSection("Mobs");

			Set<String> list = s.getKeys(false);

			if (list == null)
				return;

			for (int i = 0; i < list.size(); i++) {
				String key = ((String) list.toArray()[i]);
				String key2 = StringEncrypt.getFromBase64(key);
				if (key2.trim().equalsIgnoreCase(""))
					continue;
				MobSave save = MobSave.fromJson(key2);
				save.BDrop((List<ItemStack>) s.getConfigurationSection(key)
						.getList("Drop"));
				save.BEQPT((List<ItemStack>) s.getConfigurationSection(key)
						.getList("Eqpt"));

			Mob mm = save.toMob();
			}
			Mob.checkRider();
			Send.sendConsole(Send.COLOR.LIGHT_PURPLE + "成功恢复了" + list.size()
					+ "个怪物.");




		



	}



	public File getMainFile() {
		return this.getFile();
	}

	private void loadSkills() {
		File skillFile = new File(Bukkit.getPluginManager()
				.getPlugin(this.getName()).getDataFolder().getAbsolutePath()
				+ File.separator + "Skills" + File.separator);
		if (!skillFile.exists())
			skillFile.mkdirs();
		File[] skillArray = skillFile.listFiles(new JarFilter());
		List<File> skillList = new ArrayList<File>();
		skillList.add(this.getFile());
		for (int i = 0; i < skillArray.length; i++) {
			skillList.add(skillArray[i]);
		}
		int s = 0;
		int ss = 0;
		for (int i = 0; i < skillList.size(); i++) {
			List<Class<?>> l = null;
			try {
				l = JarLoad.getJarClass(skillList.get(i).getAbsolutePath(),
						Skill.class, getClassLoader());
			} catch (Exception e) {
				Send.sendConsole(
						skillList.get(i).getAbsoluteFile().getName()
								+ "技能包加载失败,原因:" + e.getClass().getName() + ".");
			}

			if (l == null)
				continue;

			for (int r = 0; r < l.size(); r++) {
				String skillName = "null";
				try {
					Class<? extends Skill> sklr = (Class<? extends Skill>) l
							.get(r);
					skillName = sklr.newInstance().getType();
					String[] vers = sklr.newInstance().getCanExecuteVersion();
					boolean canExe = false;
					for (int z = 0; z < vers.length; z++)
						if (vers[z].equals(bukkitVer + ""))
							canExe = true;

					if (!canExe) {
						Send.sendConsole("[技能:" + skillName + "]"
								+ skillList.get(i).getAbsoluteFile().getName()
								+ "<" + "§c" + l.get(r).getName() + ".class"
								+ "§b" + ">装载失败(不支持的服务端版本号:" + bukkitVer + ")");
						continue;
					}

					Skill.registerSkill(sklr);
				} catch (Exception e) {
					Send.sendConsole("[技能:" + skillName + "]"
							+ skillList.get(i).getAbsoluteFile().getName()
							+ "<" + "§c" + l.get(r).getName() + ".class" + "§b"
							+ ">装载失败");
					continue;
				}
				Send.sendConsole("[技能:" + skillName + "]"
								+ skillList.get(i).getAbsoluteFile().getName()
 + "<"
						+ "§c" + l.get(r).getName() + ".class" + "§b" + ">装载成功");
				ss++;
			}

			Send.sendConsole(
					"[技能包装载完成]" + skillList.get(i).getAbsoluteFile().getName());
			s++;

			String info = "[" + skillList.get(i).getAbsoluteFile().getName()
					+ "]技能包信息:";
			String infoNext = "无";
			try {
				JarFile j = new JarFile(skillList.get(i).getAbsolutePath());
				if (j.getJarEntry("main.info") != null) {

					InputStream is = j.getInputStream(j
							.getJarEntry("main.info"));

					if (is != null) {

						byte[] ccc = new byte[is.available()];
						is.read(ccc);
						is.close();
						infoNext = new String(ccc);

					}

				}
				j.close();
			} catch (IOException e) {
				Send.sendConsole("[技能包头部信息加载失败]"
						+ skillList.get(i).getAbsoluteFile().getName());
			}
			String[] infoNexts = infoNext.split("\n");
			Send.sendConsole(info);
			for (int aa = 0; aa < infoNexts.length; aa++) {
				Send.sendConsole(
						"[" + skillList.get(i).getAbsoluteFile().getName()
								+ "]" + ">>>" + infoNexts[aa]);
			}
		}

		Send.sendConsole(
				"检测到的技能包共" + skillList.size() + "个!" + s + "成功,"
						+ (skillList.size() - s) + "失败.");

		Send.sendConsole("共加载了" + ss + "个技能包技能!");

	}




	public static void loadYml() {
		if (cfg.get("AutoErrorReporting") != null)
		setAutoErrorReporting(cfg.getBoolean("AutoErrorReporting"));
		if (autoErrorReporting)
			Send.sendConsole("插件已开启误报上交,如果想关闭本功能请在配置文件中修改AutoErrorReporting=false.");
		else
			Send.sendConsole("插件已关闭误报上交,如果想开启本功能请在配置文件中修改AutoErrorReporting=true.");
		setClickItem(cfg.getInt("Tools"));
		Skill.setSkills(new ArrayList<Skill>());
		Set<String> all = cfg.getConfigurationSection("Skill").getKeys(false);
		Object[] ary = all.toArray();
		for (int i = 0; i < ary.length; i++) {
			Skill.newSkill(cfg.getConfigurationSection("Skill")
					.getConfigurationSection((String) ary[i]));
		}

		MobModel.setMobModel(new ArrayList<MobModel>());
		all = cfg.getConfigurationSection("MobModel").getKeys(false);
		ary = all.toArray();
		for (int i = 0; i < ary.length; i++) {

			new MobModel(

			cfg.getConfigurationSection("MobModel").getConfigurationSection(
					(String) ary[i]));

		}

		Spawn.setSpawns(new ArrayList<Spawn>());
		all = cfg.getConfigurationSection("PointSpawn").getKeys(false);
		ary = all.toArray();
		Spawn.getSpawns().clear();
		for (int i = 0; i < ary.length; i++) {
			new PointSpawn(cfg.getConfigurationSection("PointSpawn")
					.getConfigurationSection((String) ary[i]));

		}

		all = cfg.getConfigurationSection("WorldSpawn").getKeys(false);
		ary = all.toArray();
		for (int i = 0; i < ary.length; i++) {
			new WorldSpawn(cfg.getConfigurationSection("WorldSpawn")
					.getConfigurationSection((String) ary[i]));

		}

		MonsterSpawnBannedWorld = (ArrayList<String>) cfg
				.getList("MonsterSpawnBannedWorld");
		AnimalSpawnBannedWorld = (ArrayList<String>) cfg
				.getList("AnimalSpawnBannedWorld");
	}

	public static void saveYml() throws IOException {

		cfg.set("Tools", clickItem);
		cfg.set("MonsterSpawnBannedWorld", MonsterSpawnBannedWorld);
		cfg.set("AnimalSpawnBannedWorld", AnimalSpawnBannedWorld);

		for (int i = 0; i < MobModel.getMobModels().size(); i++) {
			MobModel.getMobModels().get(i).save();
		}

		for (int i = 0; i < Spawn.getSpawns().size(); i++) {
			Spawn.getSpawns().get(i).save();
		}

		for (int i = 0; i < Skill.getSkills().size(); i++) {
			Skill.getSkills().get(i).save();
		}

		try {
			cfg.save(f);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static String getV() {
		return V;
	}

	@Override
	public void onDisable() {
		try {
			Mob.saveAll();
		} catch (IOException e) {
			Send.sendConsole(Color.RED + "生物保存失败,现有生物有可能会被刷新.");
			ErrorReport.report(LoggerListener.toString(e));
		}
		Mob.killAll();

		try {
			ErrorReport.update();
		} catch (IOException e1) {
			e1.printStackTrace();
		}


		Send.sendConsole("┏一一一一一一一一一一一┓");
		Send.sendConsole(" ---<<<<<<<<<<<<<<<<<<---");
		Send.sendConsole(" ---<<<<<卸载成功<<<<<---");
		Send.sendConsole(" ---<<<<<<<<<<<<<<<<<<---");
		Send.sendConsole("┗一一一一一一一一一一一┛");
	}

	public static File getMobSaveFile() {
		return mobSaveFile;
	}

	public static void setMobSaveFile(File mobSaveFile) {
		Main.mobSaveFile = mobSaveFile;
	}
	@Override
	public boolean onCommand(CommandSender sender, Command cmd,
			String commandLabel, String[] args) {
		if (sender instanceof Player)
			if (cmd.getName().equalsIgnoreCase("Mobs")) {
				Player p = (Player) sender;
				try {
					String[] argss = new String[args.length + 1];
					argss[0] = cmd.getName();
					for (int i = 0; i < args.length; i++)
						argss[i + 1] = args[i];
					return CommandManager.run(p, argss, null);
				} catch (Exception e) {
					StringWriter sw = new StringWriter();
					PrintWriter pw = new PrintWriter(sw);
					e.printStackTrace(pw);
					String str = "";

					for (int i = 0; i < args.length; i++) {
						if (i != 0)
							str += " ";

						str += args[i];
					}

					ErrorReport.report("指令异常:" + cmd.getName() + " " + str
							+ "\r\n"
							+ sw.toString());
					Send.sendPluginMessage(p, "§d插件异常:§c§l"
							+ e.getClass().getName());

					if (e instanceof IllegalArgumentException)
						Send.sendPluginMessage(p,
								"§d异常原因:§c§l参数异常(参数无法被转换为整数形)");
					else if (e instanceof NullPointerException)
						Send.sendPluginMessage(p,
								"§d异常原因:§c§l空指针异常(目标参数不存在,有可能是因为手动配置有误或者配置版本有误)");
					else
						Send.sendPluginMessage(p, "§d异常原因:§c§l未知");

					Send.sendPluginMessage(p, "§d详情查看控制台的错误信息.");
					e.printStackTrace();
					return true;
				}

			}

		Send.sendConsole("本插件不支持控制台操作!");
		return true;
	}


	public static ConfigurationSection copySection(ConfigurationSection section,
			ConfigurationSection config, String str) {
		return pushSection(section, config.createSection(str));
	}

	private static ConfigurationSection pushSection(
			ConfigurationSection section, ConfigurationSection newSection) {
		Object[] key = section.getKeys(false).toArray();
		for (int i = 0; i < key.length; i++)
			if (section.getConfigurationSection((String) key[i]) == null)
				newSection.set((String) key[i], section.get((String) key[i]));
			else
				pushSection(section.getConfigurationSection((String) key[i]),
						newSection.createSection((String) key[i]));

		try {
			Main.saveYml();
		} catch (IOException e) {
			Send.sendConsole("配置储存失败.");
			return newSection;
		}
		return newSection;
	}


	public static boolean canSpawnEntity(LivingEntity e, World w) {
	
		if(e instanceof Animals){
			if(AnimalSpawnBannedWorld==null)
				return true;
			else for(int i=0;i<AnimalSpawnBannedWorld.size();i++)
				if(AnimalSpawnBannedWorld.get(i).equals(w.getName()))
					return false;
		}
		else if(e instanceof Monster){
			if(MonsterSpawnBannedWorld==null)
				return true;
			else for(int i=0;i<MonsterSpawnBannedWorld.size();i++)
				if(MonsterSpawnBannedWorld.get(i).equals(w.getName()))
					return false;
		}
		
		
		return true;
	}

}
