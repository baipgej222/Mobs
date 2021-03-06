package pw.owen.mobs.command.example;

import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import pw.owen.mobs.bean.mob.MobModel;
import pw.owen.mobs.bean.spawn.PointSpawn;
import pw.owen.mobs.bean.spawn.Spawn;
import pw.owen.mobs.bean.spawn.WorldSpawn;
import pw.owen.mobs.command.PluginCommand;
import pw.owen.mobs.run.Main;
import pw.owen.mobs.utils.Send;

public class SpawnModify_CMD implements PluginCommand {

	@Override
	public String[] getBranch() {
		// TODO 自动生成的方法存根
		return new String[] { "mobs", "spawn", "modify" };
	}

	@Override
	public boolean run(Player p, String[] args, String auto) throws Exception {
		if (args.length == 0)
			return false;
		if (Main.getsSpawn() == null) {
			Send.sendPluginMessage(p, "请先选择一个刷新点在进行修改操作.");
			return true;
		}

		if (Main.getsSpawn() instanceof PointSpawn) {
			boolean b = true;

			PointSpawn pSpawn = (PointSpawn) Main.getsSpawn();
			if (args[0].equalsIgnoreCase("point")) {
				if (Main.getO() == null) {

					Send.sendPluginMessage(p, "请先使用选择器选择一个点.");
					return true;
					// /
				}

				pSpawn.setP(Main.getO());
				Send.sendPluginMessage(p, "您已经设置了新的刷新点.");
				saveSpawn(pSpawn, p);
				return true;
			} else if (args[0].equalsIgnoreCase("single")) {
				if (args.length != 2) {
					return false;
				}
				pSpawn.setOne(Integer.parseInt(args[1]));


			} else if (args[0].equalsIgnoreCase("copyto")) {
				if (args.length != 2)
					return false;

				if (PointSpawn.getPointSpawn(args[1]) != null) {
					Send.sendPluginMessage(p, "将要复制的刷新点名已存在");
					return true;
				}
				if (Main.getO() == null) {
					Send.sendPluginMessage(p, "请先用选择器选择新刷新点的位置");
					return true;
				}




				new PointSpawn(
						Main.copySection(pSpawn.getCfg(), Main.getCfg()
								.getConfigurationSection(Spawn.POINTMOBCREATE),
								args[1]))
						.setP(Main
						.getO());


			} else if (args[0].equalsIgnoreCase("max")) {
				if (args.length != 2) {
					return false;
				}
				pSpawn.setAll(Integer.parseInt(args[1]));
			} else if (args[0].equalsIgnoreCase("range")) {
				if (args.length != 2) {
					return false;
				}
				pSpawn.setRange(Integer.parseInt(args[1]));

			} else if (args[0].equalsIgnoreCase("onPoint")) {
				if (args.length != 2) {
					return false;
				}
				if (args[1].equalsIgnoreCase("true"))
					pSpawn.setOnPoint(true);
				else if (args[1].equalsIgnoreCase("false"))
					pSpawn.setOnPoint(false);
				else
					return false;

			} else if (args[0].equalsIgnoreCase("onMove")) {
				if (args.length != 2) {
					return false;
				}
				if (args[1].equalsIgnoreCase("true"))
					pSpawn.setOnMove(true);
				else if (args[1].equalsIgnoreCase("false"))
					pSpawn.setOnMove(false);
				else
					return false;

			} else {
				b = false;
			}

			if (b) {
				saveSpawn(pSpawn, p);
				Send.sendPluginMessage(p, "操作成功.");
				return true;
			}

		} else if (Main.getsSpawn() instanceof WorldSpawn) {
			boolean b = true;
			WorldSpawn wSpawn = (WorldSpawn) Main.getsSpawn();
			if (args[0].equalsIgnoreCase("world")) {
				if (args.length != 3) {
					return false;
				}
				if (args[1].equalsIgnoreCase("add")) {
					for (int i = 0; i < wSpawn.getWorld().size(); i++) {
						if (wSpawn.getWorld().get(i).getName()
								.equalsIgnoreCase(args[2])) {
							Send.sendPluginMessage(p, "该世界已存在.");
							return true;
						}
					}
					if (Bukkit.getWorld(args[2]) != null) {
						wSpawn.getWorld().add(Bukkit.getWorld(args[2]));
					} else {
						Send.sendPluginMessage(p, "该世界不存在.");
						return true;
					}
				} else if (args[1].equalsIgnoreCase("del")) {
					for (int i = 0; i < wSpawn.getWorld().size(); i++) {
						if (wSpawn.getWorld().get(i).getName()
								.equalsIgnoreCase(args[2])) {
							wSpawn.getWorld().remove(i);

						}
					}

					Send.sendPluginMessage(p, "该世界不存在.");
					return true;
				} else if (args[1].equalsIgnoreCase("list")) {
					String s = "刷新世界列表:\n";
					for (int i = 0; i < wSpawn.getWorld().size(); i++) {
						s += wSpawn.getWorld().get(i).getName();
						if (i != wSpawn.getWorld().size() - 1) {
							s += ",";
						}
					}
					Send.sendPluginMessage(p, s);
					return true;
				}


			} else if (args[0].equalsIgnoreCase("chance")) {
				if (args.length != 2) {
					return false;
				}
				wSpawn.setChance(Double.parseDouble(args[1]));
			} else if (args[0].equalsIgnoreCase("maxInChunk")) {
				if (args.length != 2) {
					return false;
				}
				wSpawn.setMaxInChunk(Integer.parseInt(args[1]));
			} else if (args[0].equalsIgnoreCase("maxInWorld")) {
				if (args.length != 2) {
					return false;
				}
				wSpawn.setMaxInWorld(Integer.parseInt(args[1]));
			} else if (args[0].equalsIgnoreCase("maxInServer")) {
				if (args.length != 2) {
					return false;
				}
				wSpawn.setMaxInServer(Integer.parseInt(args[1]));
			} else if (args[0].equalsIgnoreCase("playerNearby")) {
				if (args.length != 2) {
					return false;
				}

				wSpawn.setPlayerNearby(Integer.parseInt(args[1]));

			} else if (args[0].equalsIgnoreCase("onPlayerNum")) {
				if (args.length != 2) {
					return false;
				}

				if (args[1].equalsIgnoreCase("true"))
					wSpawn.setOnPlayerNum(true);
				else if (args[1].equalsIgnoreCase("false"))
					wSpawn.setOnPlayerNum(false);
				else
					return false;

			} else {
				b = false;
			}

			if (b) {
			saveSpawn(wSpawn, p);
			Send.sendPluginMessage(p, "操作成功.");
				return true;
			}

		}

		// /以下为共有命令
		Spawn spawn = Main.getsSpawn();
		if (args[0].equalsIgnoreCase("del")) {

			spawn.remove();
			Main.setsSpawn(null);
		} else if (args[0].equalsIgnoreCase("lag")) {
			if (args.length != 2) {
				return false;
			}
			spawn.setTime(Integer.parseInt(args[1]));
		} else if (args[0].equalsIgnoreCase("copy")) {
			if (args.length != 2)
				return false;

			if (spawn instanceof PointSpawn) {
				if(PointSpawn.getPointSpawn(args[1])!=null)
				{Send.sendPluginMessage(p, "将要复制的刷新点名已存在");
				return true;}

				new PointSpawn(
						Main.copySection(spawn.getCfg(), Main.getCfg()
								.getConfigurationSection(Spawn.POINTMOBCREATE),
								args[1]));

			} else if (spawn instanceof WorldSpawn) {
				if (WorldSpawn.getWorldSpawn(args[1]) != null) {
					Send.sendPluginMessage(p, "将要复制的刷新点名已存在");
					return true;
				}
				new WorldSpawn(
						Main.copySection(spawn.getCfg(), Main.getCfg()
								.getConfigurationSection(Spawn.WORLDMOBCREATE),
								args[1]));
			} else
				throw new NullPointerException();

		} else if (args[0].equalsIgnoreCase("Mob")) {

			if (args.length != 2) {
				return false;
			}
			if (MobModel.isMobModel(args[1]) == -1) {
				Send.sendPluginMessage(p, "怪物不存在.");
				return true;
			}
			Main.getsSpawn().setMobModel(
					MobModel.getMobModels().get(MobModel.isMobModel(args[1])));
		} else
			return false;

		saveSpawn(spawn, p);
		Send.sendPluginMessage(p, "操作成功.");
		return true;
	}

	private static void saveSpawn(Spawn spawn, Player p) throws IOException {
		spawn.save();
			Main.getCfg().save(Main.getF());

	}

}
