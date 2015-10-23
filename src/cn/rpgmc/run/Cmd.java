package cn.rpgmc.run;

import java.io.IOException;
import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;

import cn.rpgmc.bean.mob.DropItemStack;
import cn.rpgmc.bean.mob.Eqpt;
import cn.rpgmc.bean.mob.Mob;
import cn.rpgmc.bean.mob.MobModel;
import cn.rpgmc.bean.skill.Skill;
import cn.rpgmc.bean.spawn.PointSpawn;
import cn.rpgmc.bean.spawn.Spawn;
import cn.rpgmc.bean.spawn.WorldSpawn;
import cn.rpgmc.bean.utils.Damage;
import cn.rpgmc.bean.utils.EXP;
import cn.rpgmc.bean.utils.HP;

public class Cmd {

	static boolean mobSpawn(Player p, String[] args) throws Exception {

		if (args.length > 0) {
			if (args[0].equalsIgnoreCase("spawn")) {
				if (args.length == 1) {
					spawnHelp(p);
					if (Main.getsSpawn() == null)
						return true;
					else if (Main.getsSpawn().getCreateType()
							.equalsIgnoreCase(Spawn.POINTMOBCREATE))
						pointSpawnHelp(p);
					else if (Main.getsSpawn().getCreateType()
							.equalsIgnoreCase(Spawn.WORLDMOBCREATE))
						worldSpawnHelp(p);

					return true;
				}
				return spawn(p, args);
			} else if (args[0].equalsIgnoreCase("mob")) {
				if (args.length == 1) {
					mobHelp(p);
					return true;
				}
				return mob(p, args);
			} else if (args[0].equalsIgnoreCase("set")) {

				if (args.length == 1) {
					if (p.getItemInHand() == null
							| p.getItemInHand().getType().getId() == Material.AIR
									.getId()) {
						p.sendMessage("��c[Mobs]��f����һ����Ʒ��������֮����ִ�и�����.");
						return true;

					}

					Main.setClickItem(p.getItemInHand().getTypeId());
					p.sendMessage("��c[Mobs]��f���Ѿ���"
							+ p.getItemInHand().getType().name() + "��Ϊ��ѡ����.");
					return true;
				} else {
					return false;
				}

			} else if (args[0].equalsIgnoreCase("setban")) {

				if (args.length != 3) {
					return false;
				} else {
					if (args[1].equalsIgnoreCase("Animal")) {
						if (args[2].equalsIgnoreCase("true")) {
							for (int i = 0; i < Main
									.getAnimalSpawnBannedWorld().size(); i++) {
								if (Main.getAnimalSpawnBannedWorld()
										.get(i)
										.equalsIgnoreCase(
												p.getWorld().getName())) {
									Main.getAnimalSpawnBannedWorld().remove(
											p.getWorld().getName());
								}

							}

							Main.getAnimalSpawnBannedWorld().add(
									p.getWorld().getName());
						} else if (args[2].equalsIgnoreCase("false")) {
							Main.getAnimalSpawnBannedWorld().remove(
									p.getWorld().getName());
						} else {
							return false;
						}

					} else if (args[1].equalsIgnoreCase("Monster")) {
						if (args[2].equalsIgnoreCase("true")) {
							for (int i = 0; i < Main
									.getMonsterSpawnBannedWorld().size(); i++) {
								if (Main.getMonsterSpawnBannedWorld()
										.get(i)
										.equalsIgnoreCase(
												p.getWorld().getName())) {
									Main.getMonsterSpawnBannedWorld().remove(
											p.getWorld().getName());
								}

							}

							Main.getMonsterSpawnBannedWorld().add(
									p.getWorld().getName());

						} else if (args[2].equalsIgnoreCase("false")) {
							Main.getMonsterSpawnBannedWorld().remove(
									p.getWorld().getName());
						} else {
							return false;
						}

					} else {
						return false;
					}

					try {
						Main.saveYml();
						p.sendMessage("��c[Mobs]��f���óɹ�.");
						return true;
					} catch (IOException e) {
						p.sendMessage("��c[Mobs]��f���ñ���ʧ��.");
						return true;
					}

				}

			} else if (args[0].equalsIgnoreCase("listban")) {
				String s1 = "��ֹ��������:";
				String s2 = "��ֹ��������:";
				ArrayList<String> al = Main.getAnimalSpawnBannedWorld();
				ArrayList<String> ml = Main.getMonsterSpawnBannedWorld();
				for (int i = 0; i < al.size(); i++) {
					s1 += al.get(i);
					if (i != al.size() - 1) {
						s1 += ",";
					}
				}

				for (int i = 0; i < ml.size(); i++) {
					s2 += ml.get(i);
					if (i != ml.size() - 1) {
						s2 += ",";
					}
				}
				p.sendMessage(s1);
				p.sendMessage(s2);
				return true;
			} else if (args[0].equalsIgnoreCase("listPotionEffectType")) {
				String str = "֧�ֵ�ҩˮ����:";
				for (int i = 0; i < PotionEffectType.values().length; i++) {
					if (i != 0)
						str += ",";

					str += PotionEffectType.values()[i].getName();
				}
				p.sendMessage(str);

				return true;
			} else if (args[0].equalsIgnoreCase("listEntityType")) {
				String str = "֧�ֵĹ�������:";
				for (int i = 0; i < EntityType.values().length; i++) {
					if (i != 0)
						str += ",";

					str += EntityType.values()[i].name();
					;
				}
				p.sendMessage(str);

				return true;
			} else if (args[0].equalsIgnoreCase("reload")) {
				if (args.length == 1) {
					try {
						Main.getCfg().load(Main.getF());
						Main.getCfg().set("Version", Main.getV());
						Main.loadYml();
						Mob.killAll();
						p.sendMessage("��c[Mobs]��f�������سɹ�.");
					} catch (Exception e) {
						e.printStackTrace();
						p.sendMessage("��c[Mobs]��f��������ʧ��,�����Գ���ɾ�����������������������������ļ�.");
					}
				} else {
					return false;
				}
				return true;
			} else if (args[0].equalsIgnoreCase("help")) {
				if (args.length == 1) {
					p.sendMessage("��c[Mobs]��f/Mobs help [main/mob/skill/spawn] (spawn)<Point/World> �鿴[������/��������/��������/ˢ�µ�����]�İ����ı�");
					return true;
				}
				if (args.length != 2) {
					if (args.length > 2 & args[1].equalsIgnoreCase("spawn"))
						;
					else
						return false;
				}
				if (args[1].equalsIgnoreCase("main")) {
					mainHelp(p);
				} else if (args[1].equalsIgnoreCase("mob")) {
					mobHelp(p);
				} else if (args[1].equalsIgnoreCase("skill")) {
					skillHelp(p);
				} else if (args[1].equalsIgnoreCase("spawn")) {

					if (args.length == 2) {
						p.sendMessage("��c[Mobs]��f/Mobs help spawn <Point/World> �鿴[ˢ�µ�����]�İ����ı�");
						return true;
					}
					if (!args[2].equalsIgnoreCase("point")
							& !args[2].equalsIgnoreCase("world")) {
						p.sendMessage("��c[Mobs]��f/Mobs help spawn <Point/World> �鿴[ˢ�µ�����]�İ����ı�");
						return true;
					}
					// ����ˢ�µ�

					spawnHelp(p);
					if (args[2].equalsIgnoreCase("point")) {
						pointSpawnHelp(p);
					}
					if (args[2].equalsIgnoreCase("world")) {
						worldSpawnHelp(p);
					}

				} else {
					return false;
				}
				return true;

			} else if (args[0].equalsIgnoreCase("skill")) {
				return skill(args, p);
			} else {

				return false;
			}
		}

		return false;

	}

	public static void worldSpawnHelp(Player p) {
		p.sendMessage("��a  /Mobs spawn modify world [add/list/del] �޸�ˢ�µ�����");
		p.sendMessage("��a  /Mobs spawn modify chance [����] �޸�ˢ�µļ���");
		p.sendMessage("��a  /Mobs spawn modify playerNearby [����] �޸�����ҵ�ˢ��������");

	}

	public static void pointSpawnHelp(Player p) {
		p.sendMessage("��a  /Mobs spawn modify point ����ˢ�µ�λ��");
		p.sendMessage("��a  /Mobs spawn modify single [Single] ����ÿ��ˢ������");
		p.sendMessage("��a  /Mobs spawn modify range [Range] ���û�뾶(�����ᱻ����ԭ��)");
		p.sendMessage("��a  /Mobs spawn modify center ���û�뾶��Բ�ĵ�");

	}

	public static void spawnHelp(Player p) {
		p.sendMessage("��a  /Mobs spawn new [Point/World] [ˢ�µ���] ����ĳ��ˢ�µ�(�Զ�select)");
		p.sendMessage("��a  /Mobs spawn select [Point/World] [ˢ�µ���] ����ĳ��ˢ�µ������");
		p.sendMessage("��a  /Mobs spawn list [Point/World] �鿴ˢ�µ��б�");
		p.sendMessage("��a  /Mobs spawn killall ɾ��һ��ˢ�µ��ˢ������");
		p.sendMessage("��a  /Mobs spawn see [ˢ�µ�] �鿴һ������ˢ�µ����ϸ��Ϣ");
		p.sendMessage("��a  /Mobs spawn modify del [ˢ�µ�] ɾ��һ������ˢ�µ�");
		p.sendMessage("��a  /Mobs spawn modify mob [sName] ���ù���ģ��");
		p.sendMessage("��a  /Mobs spawn modify lag [Lag] ����ˢ�¼��(tick:20tick=1s)");
		p.sendMessage("��a  /Mobs spawn modify max [Max] ���ù����������(����ˢ��Ϊ�����������)");

	}

	public static void mobHelp(Player p) {
		p.sendMessage("��a  /Mobs mob new [������(������ʾ����,��Ϊ�Ǻ�)] ����ĳ������(�Զ�select)");
		p.sendMessage("��a  /Mobs mob select [������] ����ĳ�����������");
		p.sendMessage("��a  /Mobs mob spawn �����ߴ�����һ���ù���");
		p.sendMessage("��a  /Mobs mob see �鿴һ���������ϸ��Ϣ");
		p.sendMessage("��a  /Mobs mob list  �鿴�����б�");
		p.sendMessage("��a  /Mobs mob modify del ɾ��һ������");
		p.sendMessage("��a  /Mobs mob modify drop [add/list/del] ���ӵ�����͵��伸��");
		p.sendMessage("��a  /Mobs mob modify droptype [��ʽ(All,Invalid,Random)] ���õ��䷽ʽ");
		p.sendMessage("��a  /Mobs mob modify name [Name] ��������");
		p.sendMessage("��a  /Mobs mob modify hp [HighHP] <LowHP>����Ѫ��");
		p.sendMessage("��a  /Mobs mob modify attrcover [boolean] �����Ƿ񸲸�����");
		p.sendMessage("��a  /Mobs mob modify damage [HighDamage] <LowDamage> �����˺�");
		p.sendMessage("��a  /Mobs mob modify exp [HighEXP] <LowEXP> ������������ľ���");
		p.sendMessage("��a  /Mobs mob modify type [Type] ���ù�������");
		p.sendMessage("��a  /Mobs mob modify effect [set:([ҩˮ����] [ҩˮ�ȼ�])/del/list] ���ӹ��������ҩˮ״̬");
		p.sendMessage("��a  /Mobs mob modify rider [sName] ��������︽��һ����ʻ��(�����Ĺ���ģ��)");
		p.sendMessage("��a  /Mobs mob modify eqpt ����װ��Ϊ��ǰ������װ�������õ�����");
		p.sendMessage("��a  /Mobs mob modify skill [add/list/del] ��ɾ�鼼���б�");
		p.sendMessage("��a  /Mobs mob modify sl [Day/Night/Sun/Rain/Thunder] [true/false] ���ù���ˢ�¶Ի���������");

	}

	public static void skillHelp(Player p) {
		p.sendMessage("��a  /Mobs skill new [������(������ʾ����,��Ϊ�Ǻ�)] [��������] ����ĳ������(�Զ�select)");
		p.sendMessage("��a  /Mobs skill select [������] ����ĳ�����ܵ�����");
		p.sendMessage("��a  /Mobs skill type  �鿴���������б�");
		p.sendMessage("��a  /Mobs skill list <��������> �鿴�����б�");
		p.sendMessage("��a  /Mobs skill see �鿴һ�����ܵ���ϸ��Ϣ");
		p.sendMessage("��a  /Mobs skill help [��������] �鿴�����ͼ��ܰ�������ָ��");
		p.sendMessage("��a  /Mobs skill modify  �޸ļ�������");
		Skill.mainHelp();
	}

	public static void mainHelp(Player p) {
		p.sendMessage("��a  /Mobs set ����ѡ��������ƷIDΪ���ϵ���Ʒ");
		p.sendMessage("��a  /Mobs spawn ����ˢ�µ�ĸ�������<��Ҫ����>");
		p.sendMessage("��a  /Mobs mob ���ù���ĸ�������<��Ҫ����>");
		p.sendMessage("��a  /Mobs skill ���ü��ܵĸ�������<��Ҫ����>");
		p.sendMessage("��a  /Mobs setBan [Animal/Monster] [true/false] ���������ڵ������Ƿ����Ĭ�ϲ����Ķ���/����");
		p.sendMessage("��a  /Mobs listBan �鿴�����ڵ������Ƿ����Ĭ�ϲ����Ķ���/����");
		p.sendMessage("��a  /Mobs listPotionEffectType �鿴����֧�ֵ�ҩˮ����");
		p.sendMessage("��a  /Mobs listEntityType �鿴����֧����Ϊ���������");
		p.sendMessage("��a  /Mobs reload ���ز��");
		p.sendMessage("��a  /Mobs help ����");

	}

	private static boolean skill(String[] args, Player p) {

		if (args[1].equalsIgnoreCase("new")) {
			if (args.length != 4)
				return false;

			if (Skill.isSkill(args[2]) != -1) {
				p.sendMessage("��c[Mobs]��f�����Ѵ���.");
				return true;
			}

			Skill skill = Skill.newSkill(args[3], args[2]);
			if (skill == null) {
				p.sendMessage("��c[Mobs]��f�ü������Ͳ�����,������ڲ�����.");
				return true;
			}
			p.sendMessage("��c[Mobs]��f�����ɹ�.");
			Main.setsSkill(skill);
			try {
				Main.saveYml();
			} catch (IOException e) {
				// TODO �Զ����ɵ� catch ��
				e.printStackTrace();
			}
			return true;

		} else if (args[1].equalsIgnoreCase("select")) {
			if (args.length != 3)
				return false;

			if (Skill.isSkill(args[2]) == -1) {
				p.sendMessage("��c[Mobs]��f�ü��ܲ�����.");
				return true;
			}

			Main.setsSkill(Skill.getSkills().get(Skill.isSkill(args[2])));
			p.sendMessage("��c[Mobs]��fѡ��ɹ�.");
			return true;

		} else if (args[1].equalsIgnoreCase("type")) {
			p.sendMessage(Skill.getTypes());
			return true;
		} else if (args[1].equalsIgnoreCase("list")) {
			if (args.length == 2)
				p.sendMessage(Skill.getList());
			else if (args.length == 3)
				p.sendMessage(Skill.getList(args[2]));
			else
				return false;

			return true;
		} else if (args[1].equalsIgnoreCase("help")) {
			if (args.length != 3)
				return false;
			p.sendMessage(Skill.help(args[2]));
			return true;

		}

		else if (Main.getsSkill() == null) {
			p.sendMessage("��c[Mobs]��f����ѡ��һ������.");
			return true;
		} else if (args[1].equalsIgnoreCase("see")) {
			p.sendMessage(Main.getsSkill().see());
			return true;
		} else if (args[1].equalsIgnoreCase("modify")) {
			return Main.getsSkill().cmdManager(args, p);

		}

		return false;
	}

	private static boolean mob(Player p, String[] args) {
		if (args[1].equalsIgnoreCase("select")) {
			if (args.length == 2) {
				p.sendMessage("��a  /Mobs mob select  [������] ����ĳ�����������");
				return true;
			}
			if (args.length != 3) {
				return false;
			}

			ConfigurationSection section = Main.getCfg()
					.getConfigurationSection("MobModel")
					.getConfigurationSection(args[2]);
			if (section == null) {
				p.sendMessage("��c[Mobs]��f�ù��ﲻ����.");
				return true;
			}
			Main.setsMobModel(new MobModel(section));
			p.sendMessage("��c[Mobs]��f�Ѿ�ѡ�����:" + args[2] + ".");
			return true;

		} else if (args[1].equalsIgnoreCase("new")) {
			if (args.length != 3)
				return false;

			if (MobModel.isMobModel(args[2]) != -1) {

				p.sendMessage("��c[Mobs]��f�����Ѵ���.");

			} else {
				try {
					Main.setsMobModel(new MobModel(args[2], Main.getCfg()
							.getConfigurationSection("MobModel")));
					Main.saveYml();
					p.sendMessage("��c[Mobs]��f�����ɹ�.");
				} catch (IOException e) {
					p.sendMessage("��c[Mobs]��f����ʧ��.");
				}
			}
			return true;

		} else if (args[1].equalsIgnoreCase("list")) {
			String s = "�����б�:";
			for (int i = 0; i < MobModel.getMobModels().size(); i++) {
				s += MobModel.getMobModels().get(i).getsName();
				if (i != MobModel.getMobModels().size() - 1) {
					s += ",";
				}
			}
			p.sendMessage(s);
			return true;
		} else if (Main.getsMobModel() == null) {
			p.sendMessage("��c[Mobs]��f����ʹ��/Mobs mob select [������] ѡ��ĳ����������޸�.");
			return true;
		}
		MobModel mm = Main.getsMobModel();

		if (args[1].equalsIgnoreCase("see")) {
			p.sendMessage(mm.getSee());
			return true;
		} else if (args[1].equalsIgnoreCase("spawn")) {
			if (args.length != 2)
				return false;

			mm.spawnMob(p.getEyeLocation());
			p.sendMessage("��c[Mobs]��f�����ɹ�.");
			return true;

		} else if (args[1].equalsIgnoreCase("modify")) {
			return mobModify(p, args);
		}

		return false;
	}

	private static boolean mobModify(Player p, String[] args) {
		MobModel mm = Main.getsMobModel();
		if (args[2].equalsIgnoreCase("del")) {
			if (!mm.remove())
				p.sendMessage("��c[Mobs]��f�ù��ﲻ����.");
			Main.setsMobModel(null);
			p.sendMessage("��c[Mobs]��f�����ɹ�.");
		} else if (args[2].equalsIgnoreCase("drop")) {
			if (args.length < 4) {
				return false;
			}
			if (args[3].equalsIgnoreCase("add")) {

				if (p.getItemInHand() == null
						|| p.getItemInHand().getType().name()
								.equalsIgnoreCase(Material.AIR.name())) {
					p.sendMessage("��c[Mobs]��f���ֳ�һ��װ��.");
					return true;
				}

				if (args.length != 5) {
					return false;
				}

				mm.addDrop(p.getItemInHand(), Integer.parseInt(args[4]));

			} else if (args[3].equalsIgnoreCase("list")) {

				String s = "�������б�:";
				ArrayList<DropItemStack> a = mm.getDrop();
				for (int i = 0; i < a.size(); i++) {
					s += i + ":[����-" + a.get(i).getItem().getType().name()
							+ "|" + a.get(i).getI() + "%|"
							+ a.get(i).getItem().getAmount() + "��]";
					if (i != a.size() - 1) {
						s += ",";
					}
				}

				p.sendMessage(s);
				return true;

			} else if (args[3].equalsIgnoreCase("del")) {
				if (args.length != 5) {
					return false;
				}

				if (!mm.delDrop(Integer.parseInt(args[4]))) {
					p.sendMessage("��c[Mobs]��f�õ����ﲻ����.");
					return true;
				}
			}

		} else if (args[2].equalsIgnoreCase("sl")) {
			if (args.length != 5)
				return false;
			boolean t = false;
			if (args[4].equalsIgnoreCase("true"))
				t = true;
			else if (args[4].equalsIgnoreCase("false"))
				t = false;
			else
				return false;

			if (args[3].equalsIgnoreCase("Night")) {
				mm.getSurvivalLimit().isNight(t);
			} else if (args[3].equalsIgnoreCase("Rain")) {
				mm.getSurvivalLimit().isRain(t);
			} else if (args[3].equalsIgnoreCase("Thunder")) {
				mm.getSurvivalLimit().isThundering(t);
			} else if (args[3].equalsIgnoreCase("Day")) {
				mm.getSurvivalLimit().isDay(t);
			} else if (args[3].equalsIgnoreCase("Sun")) {
				mm.getSurvivalLimit().isSun(t);
			} else {
				return false;
			}

		} else if (args[2].equalsIgnoreCase("droptype")) {
			if (args.length != 4)
				return false;

			if (args[3].equalsIgnoreCase("All")) {
				mm.setDropType(1);
			} else if (args[3].equalsIgnoreCase("Invalid")) {
				mm.setDropType(0);
			} else if (args[3].equalsIgnoreCase("Random")) {
				mm.setDropType(2);
			} else
				return false;

		} else if (args[2].equalsIgnoreCase("effect")) {
			if (args.length < 4)
				return false;
			if (args[3].equalsIgnoreCase("set")) {
				if (args.length < 6)
					return false;

				if (PotionEffectType.getByName(args[4]) == null) {
					p.sendMessage("��c[Mobs]��f��ҩˮ���Ͳ�����.");
					return true;
				} else {
					Main.getsMobModel().addPotionEffect(args[4],
							Integer.parseInt(args[5]));

				}

			} else if (args[3].equalsIgnoreCase("del")) {
				if (args.length < 5)
					return false;
				if (Main.getsMobModel().delPotionEffect(args[4]) == false) {
					p.sendMessage("��c[Mobs]��f��ҩˮ״̬������.");
					return true;
				}

			} else if (args[3].equalsIgnoreCase("list")) {
				String str = "ҩˮЧ���б�:";
				for (int i = 0; i < Main.getsMobModel().getPotionEffectList()
						.size(); i++) {
					if (i != 0)
						str += ",";

					str += (String) Main.getsMobModel().getPotionEffectList()
							.toArray()[i]
							+ ":"
							+ Main.getsMobModel()
									.getPotionEffectLv(
											(String) Main.getsMobModel()
													.getPotionEffectList()
													.toArray()[i]) + "��";
				}
				p.sendMessage(str);
				return true;

			} else
				return false;

		} else if (args[2].equalsIgnoreCase("rider")) {
			if (args.length != 4)
				return false;

			if (MobModel.isMobModel(args[3]) == -1) {
				p.sendMessage("��c[Mobs]��f�ù��ﲻ����.");
				return true;
			}
			if (Main.getsMobModel().getsName()
					.equalsIgnoreCase(MobModel.getMobModel(args[3]).getsName())) {
				p.sendMessage("��c[Mobs]��f�㲻�������Լ�.");
				return true;
			}

			mm.setRider(args[3]);
		} else if (args[2].equalsIgnoreCase("name")) {
			if (args.length != 4)
				return false;
			mm.setDisplayName(args[3]);
		} else if (args[2].equalsIgnoreCase("damage")) {
			if (args.length == 4) {
				mm.setDmg(new Damage(Integer.parseInt(args[3])));
			} else if (args.length == 5) {
				mm.setDmg(new Damage(Integer.parseInt(args[3]), Integer
						.parseInt(args[4])));
			} else {
				return false;
			}

		} else if (args[2].equalsIgnoreCase("hp")) {
			if (args.length == 4) {
				mm.setHp(new HP(Integer.parseInt(args[3])));
			} else if (args.length == 5) {
				mm.setHp(new HP(Integer.parseInt(args[3]), Integer
						.parseInt(args[4])));
			} else {
				return false;
			}
		} else if (args[2].equalsIgnoreCase("attrcover")) {
			if (args.length != 4)
				return false;
			if (args[3].equalsIgnoreCase("true"))
				mm.setAttrCover(true);
			else if (args[3].equalsIgnoreCase("false"))
				mm.setAttrCover(false);
			else
				return false;

		} else if (args[2].equalsIgnoreCase("exp")) {
			if (args.length == 4) {
				mm.setExp(new EXP(Integer.parseInt(args[3])));
			} else if (args.length == 5) {
				mm.setExp(new EXP(Integer.parseInt(args[3]), Integer
						.parseInt(args[4])));
			} else {
				return false;
			}
		} else if (args[2].equalsIgnoreCase("type")) {
			if (args.length != 4)
				return false;
			mm.setType(EntityType.fromName(args[3]));
		} else if (args[2].equalsIgnoreCase("eqpt")) {
			mm.setEqpt(new Eqpt(p.getEquipment().getHelmet(), p.getEquipment()
					.getChestplate(), p.getEquipment().getLeggings(), p
					.getEquipment().getBoots(), p.getEquipment()
					.getItemInHand()));
			// װ��
		} else if (args[2].equalsIgnoreCase("skill")) {

			if (args.length < 4)
				return false;

			if (args[3].equalsIgnoreCase("add")) {

				if (args.length != 5)
					return false;
				Skill skill = Skill.getSkill(args[4]);
				if (skill == null) {

					p.sendMessage("��c[Mobs]��f�ü��ܲ�����.");
					return true;

				} else
					mm.addSkill(skill);

			} else if (args[3].equalsIgnoreCase("del")) {
				if (args.length != 5)
					return false;
				if (!mm.delSkills(Integer.parseInt(args[4]))) {
					p.sendMessage("��c[Mobs]��f�ü��ܲ�����.");
					return false;
				}

			} else if (args[3].equalsIgnoreCase("list")) {
				if (args.length != 4)
					return false;

				String str = "���＼���б�:";
				for (int i = 0; i < mm.getSkills().size(); i++) {
					if (i != 0)
						str += ",";
					str += i + ":" + mm.getSkills().get(i).getsName();
				}
				p.sendMessage("str");
				return true;

			} else
				return false;

		} else {
			return false;
		}

		try {
			saveMobModel(mm, p);
			p.sendMessage("��c[Mobs]��f�����ɹ�.");
		} catch (IOException e) {
			p.sendMessage("��c[Mobs]��f����ʧ��.");
		}
		return true;
	}

	private static boolean spawn(Player p, String[] args) {

		if (args[1].equalsIgnoreCase("select")) {
			if (args.length != 4) {
				return false;
			}
			ConfigurationSection section = null;
			if (args[2].equalsIgnoreCase("Point")) {
				section = Main.getCfg().getConfigurationSection("PointSpawn")
						.getConfigurationSection(args[3]);
				if (section == null) {
					p.sendMessage("��c[Mobs]��f�õ㲻����.");
					return true;
				}
				Main.setsSpawn(new PointSpawn(section));
				p.sendMessage("��c[Mobs]��f�Ѿ�ѡ���:" + args[3] + ".");
				return true;
			} else if (args[2].equalsIgnoreCase("World")) {
				section = Main.getCfg().getConfigurationSection("WorldSpawn")
						.getConfigurationSection(args[3]);
				if (section == null) {
					p.sendMessage("��c[Mobs]��f�õ㲻����.");
					return true;
				}
				Main.setsSpawn(new WorldSpawn(section));
				p.sendMessage("��c[Mobs]��f�Ѿ�ѡ���:" + args[3] + ".");
				return true;
			}
		} else if (args[1].equalsIgnoreCase("new")) {

			if (args.length != 4)
				return false;
			if (args[2].equalsIgnoreCase("Point")) {
				if (Main.getO() == null) {
					p.sendMessage("��c[Mobs]��f����ѡ��һ����.");
					return true;
				}

				if (PointSpawn.isPSpawn(args[3]) != -1) {
					p.sendMessage("��c[Mobs]��f�����Ѵ���.");
					return true;
				}
				Main.setsSpawn(new PointSpawn(args[3], Main.getCfg()
						.getConfigurationSection("PointSpawn"), Main.getO()));
				Main.getsSpawn().save();
				p.sendMessage("��c[Mobs]��f�����ɹ�.");
				try {
					Main.saveYml();
				} catch (IOException e) {
					p.sendMessage("��c[Mobs]��f����ʧ��.");
				}
				return true;

			} else if (args[2].equalsIgnoreCase("World")) {
				if (WorldSpawn.isWSpawn(args[3]) != -1) {
					p.sendMessage("��c[Mobs]��f�����Ѵ���.");
					return true;
				}
				ArrayList<World> w = new ArrayList<World>();
				w.add(p.getWorld());
				Main.setsSpawn(new WorldSpawn(args[3], Main.getCfg()
						.getConfigurationSection("WorldSpawn"), w));
				Main.getsSpawn().save();
				p.sendMessage("��c[Mobs]��f�����ɹ�.");
				try {
					Main.saveYml();
				} catch (IOException e) {
					p.sendMessage("��c[Mobs]��f����ʧ��.");
				}
				return true;
			}

		} else if (args[1].equalsIgnoreCase("list")) {
			if (args.length != 3)
				return false;

			String s = "";
			if (args[2].equalsIgnoreCase("world")) {
				s = "����ˢ�µ��б�:";

				for (int i = 0; i < WorldSpawn.getWmobcreates().size(); i++) {
					s += WorldSpawn.getWmobcreates().get(i).getcName();
					if (i != WorldSpawn.getWmobcreates().size() - 1) {
						s += ",";
					}
				}

			} else if (args[2].equalsIgnoreCase("point")) {
				s = "����ˢ�µ��б�:";

				for (int i = 0; i < PointSpawn.getPmobcreates().size(); i++) {
					s += PointSpawn.getPmobcreates().get(i).getcName();
					if (i != PointSpawn.getPmobcreates().size() - 1) {
						s += ",";
					}
				}

			}
			p.sendMessage(s);
			return true;

		} else if (Main.getsSpawn() == null) {
			p.sendMessage("��c[Mobs]��f����ʹ��/Mobs spawn select [Point/World] [ˢ�µ���] ѡ��ĳ��ˢ�µ�����޸�.");
			return true;
		}

		Spawn spawn = Main.getsSpawn();

		if (args[1].equalsIgnoreCase("modify")) {
			return spawnModify(p, args);
		} else if (args[1].equalsIgnoreCase("killall")) {

			Main.getsSpawn().killAll();
			p.sendMessage("��c[Mobs]��fִ�гɹ�.");
			return true;

		} else if (args[1].equalsIgnoreCase("see")) {
			p.sendMessage(spawn.getSee());
			return true;
		} else {
			return false;
		}

	}

	private static boolean spawnModify(Player p, String[] args) {
		if (args.length < 3) {
			return false;
		}
		if (Main.getsSpawn() instanceof PointSpawn) {
			PointSpawn pSpawn = (PointSpawn) Main.getsSpawn();
			if (args[2].equalsIgnoreCase("point")) {
				if (Main.getO() == null) {

					p.sendMessage("��c[Mobs]��f����ʹ��ѡ����ѡ��һ����.");
					return true;
					// /
				}

				pSpawn.setP(Main.getO());
				p.sendMessage("��c[Mobs]��f���Ѿ��������µ�ˢ�µ�.");
				saveSpawn(pSpawn, p);
				return true;
			} else if (args[2].equalsIgnoreCase("single")) {
				if (args.length != 4) {
					return false;
				}
				pSpawn.setOne(Integer.parseInt(args[3]));
				p.sendMessage("��c[Mobs]��f���óɹ�.");
				return true;

			} else if (args[2].equalsIgnoreCase("range")) {
				if (args.length != 4) {
					return false;
				}
				pSpawn.setRange(Integer.parseInt(args[3]));
				p.sendMessage("��c[Mobs]��f���Ѿ��������µĻ�뾶.");
				saveSpawn(pSpawn, p);
				return true;
			} else if (args[2].equalsIgnoreCase("center")) {
				if (Main.getO() == null) {

					p.sendMessage("��c[Mobs]��f����ʹ��ѡ����ѡ��һ����.");
					return true;
					// /
				}
				pSpawn.setO(Main.getO());
				p.sendMessage("��c[Mobs]��f���Ѿ��������µ�Բ�ĵ�.");
				saveSpawn(pSpawn, p);
				return true;
			}

		} else if (Main.getsSpawn() instanceof WorldSpawn) {
			WorldSpawn wSpawn = (WorldSpawn) Main.getsSpawn();
			if (args[2].equalsIgnoreCase("world")) {
				if (args.length != 5) {
					return false;
				}
				if (args[3].equalsIgnoreCase("add")) {
					for (int i = 0; i < wSpawn.getWorld().size(); i++) {
						if (wSpawn.getWorld().get(i).getName()
								.equalsIgnoreCase(args[4])) {
							p.sendMessage("��c[Mobs]��f�������Ѵ���.");
							return true;
						}
					}
					if (Bukkit.getWorld(args[4]) != null) {
						wSpawn.getWorld().add(Bukkit.getWorld(args[4]));
						p.sendMessage("��c[Mobs]��f�����ɹ�.");
					} else {
						p.sendMessage("��c[Mobs]��f�����粻����.");
					}
				} else if (args[3].equalsIgnoreCase("del")) {
					for (int i = 0; i < wSpawn.getWorld().size(); i++) {
						if (wSpawn.getWorld().get(i).getName()
								.equalsIgnoreCase(args[4])) {
							wSpawn.getWorld().remove(i);
							p.sendMessage("��c[Mobs]��f�����ɹ�.");
							return true;
						}
					}

					p.sendMessage("��c[Mobs]��f�����粻����.");
					return true;
				} else if (args[3].equalsIgnoreCase("list")) {
					String s = "ˢ�������б�:\n";
					for (int i = 0; i < wSpawn.getWorld().size(); i++) {
						s += wSpawn.getWorld().get(i).getName();
						if (i != wSpawn.getWorld().size() - 1) {
							s += ",";
						}
					}
					p.sendMessage(s);
				}
				p.sendMessage("��a  /Mobs spawn modify world [add/list/del] �޸�ˢ�µ�����");

				p.sendMessage("��c[Mobs]��f.");
				saveSpawn(wSpawn, p);
				return true;
			} else if (args[2].equalsIgnoreCase("chance")) {
				if (args.length != 4) {
					return false;
				}
				wSpawn.setChance(Double.parseDouble(args[3]));
				p.sendMessage("��c[Mobs]��f���Ѿ��������µ�ˢ�¼���.");
				saveSpawn(wSpawn, p);
				return true;
			} else if (args[2].equalsIgnoreCase("playerNearby")) {
				if (args.length != 4) {
					return false;
				}

				wSpawn.setPlayerNearby(Integer.parseInt(args[3]));
				p.sendMessage("��c[Mobs]��f���óɹ�.");
				saveSpawn(wSpawn, p);
				return true;

			}

		}

		// /����Ϊ��������
		Spawn spawn = Main.getsSpawn();
		if (args[2].equalsIgnoreCase("del")) {
			spawn.remove();
			Main.setsSpawn(null);
			p.sendMessage("��c[Mobs]��f���Ѿ�ɾ���˸õ�.");
		} else if (args[2].equalsIgnoreCase("lag")) {
			if (args.length != 4) {
				return false;
			}
			spawn.setTime(Integer.parseInt(args[3]));
			p.sendMessage("��c[Mobs]��f���óɹ�.");
		} else if (args[2].equalsIgnoreCase("max")) {
			if (args.length != 4) {
				return false;
			}
			spawn.setAll(Integer.parseInt(args[3]));
			p.sendMessage("��c[Mobs]��f���óɹ�.");
		} else if (args[2].equalsIgnoreCase("Mob")) {

			if (args.length != 4) {
				return false;
			}
			if (MobModel.isMobModel(args[3]) == -1) {
				p.sendMessage("��c[Mobs]��f���ﲻ����.");
				return true;
			}
			Main.getsSpawn().setMobModel(
					MobModel.getMobModels().get(MobModel.isMobModel(args[3])));
			p.sendMessage("��c[Mobs]��f���óɹ�.");
		} else
			return false;

		saveSpawn(spawn, p);
		return true;
	}

	private static void saveSpawn(Spawn pSpawn, Player p) {
		pSpawn.save();
		try {
			Main.getCfg().save(Main.getF());
		} catch (IOException e) {
			p.sendMessage("��c[Mobs]��f���ñ���ʧ��.");
		}

	}

	private static void saveMobModel(MobModel mm, Player p) throws IOException {
		mm.save();
		try {
			Main.getCfg().save(Main.getF());
		} catch (IOException e) {
			p.sendMessage("��c[Mobs]��f���ñ���ʧ��.");
		}

	}
}
