package cn.rpgmc.mobs.command.example;

import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;

import cn.rpgmc.mobs.bean.mob.DropItemStack;
import cn.rpgmc.mobs.bean.mob.Eqpt;
import cn.rpgmc.mobs.bean.mob.MobModel;
import cn.rpgmc.mobs.bean.skill.Skill;
import cn.rpgmc.mobs.command.PluginCommand;
import cn.rpgmc.mobs.run.Main;
import cn.rpgmc.mobs.utils.Send;
import cn.rpgmc.mobs.utils.rangeint.Damage;
import cn.rpgmc.mobs.utils.rangeint.EXP;
import cn.rpgmc.mobs.utils.rangeint.HP;

public class MobModify_CMD implements PluginCommand {

	@Override
	public String[] getBranch() {
		// TODO �Զ����ɵķ������
		return new String[] { "mobs", "mob", "modify" };
	}

	@Override
	public boolean run(Player p, String[] args, String auto) throws Exception {
		if (args.length == 0)
			return false;

		if (Main.getsMobModel() == null) {
			Send.sendPluginMessage(p, "����ѡ��һ�������ڽ����޸Ĳ���.");
			return true;
		}
		MobModel mm = Main.getsMobModel();
		if (args[0].equalsIgnoreCase("del")) {
			if (!mm.remove())
				Send.sendPluginMessage(p, "�ù��ﲻ����.");
			Main.setsMobModel(null);
			Send.sendPluginMessage(p, "�����ɹ�.");
		} else if (args[0].equalsIgnoreCase("drop")) {
			if (args.length < 2) {
				return false;
			}
			if (args[1].equalsIgnoreCase("add")) {

				if (p.getItemInHand() == null
						|| p.getItemInHand().getType().name()
								.equalsIgnoreCase(Material.AIR.name())) {
					Send.sendPluginMessage(p, "���ֳ�һ��װ��.");
					return true;
				}

				if (args.length != 3) {
					return false;
				}
				

					
					mm.addDrop(p.getItemInHand(), Integer.parseInt(args[2]) );

				

			} else if (args[1].equalsIgnoreCase("list")) {

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

				Send.sendPluginMessage(p, s);
				return true;

			} else if (args[1].equalsIgnoreCase("del")) {
				if (args.length != 3) {
					return false;
				}


					
				mm.addDrop(p.getItemInHand(), Integer.parseInt(args[2]));
				
	
				
			}

		} else if (args[0].equalsIgnoreCase("sl")) {
			if (args.length != 3)
				return false;
			boolean t = false;
			if (args[2].equalsIgnoreCase("true"))
				t = true;
			else if (args[2].equalsIgnoreCase("false"))
				t = false;
			else
				return false;

			if (args[1].equalsIgnoreCase("Night")) {
				mm.getSurvivalLimit().isNight(t);
			} else if (args[1].equalsIgnoreCase("Rain")) {
				mm.getSurvivalLimit().isRain(t);
			} else if (args[1].equalsIgnoreCase("Thunder")) {
				mm.getSurvivalLimit().isThundering(t);
			} else if (args[1].equalsIgnoreCase("Day")) {
				mm.getSurvivalLimit().isDay(t);
			} else if (args[1].equalsIgnoreCase("Sun")) {
				mm.getSurvivalLimit().isSun(t);
			} else {
				return false;
			}

		} else if (args[0].equalsIgnoreCase("droptype")) {
			if (args.length != 2)
				return false;

			if (args[1].equalsIgnoreCase("All")) {
				mm.setDropType(1);
			} else if (args[1].equalsIgnoreCase("Invalid")) {
				mm.setDropType(0);
			} else if (args[1].equalsIgnoreCase("Random")) {
				mm.setDropType(2);
			} else
				return false;

		} else if (args[0].equalsIgnoreCase("effect")) {
			if (args.length < 2)
				return false;
			if (args[1].equalsIgnoreCase("set")) {
				if (args.length < 4)
					return false;

				if (PotionEffectType.getByName(args[2]) == null) {
					Send.sendPluginMessage(p, "��ҩˮ���Ͳ�����.");
					return true;
				} else {
					Main.getsMobModel().addPotionEffect(args[2],
							Integer.parseInt(args[3]));

				}

			} else if (args[1].equalsIgnoreCase("del")) {
				if (args.length < 3)
					return false;
				if (Main.getsMobModel().delPotionEffect(args[2]) == false) {
					Send.sendPluginMessage(p, "��ҩˮ״̬������.");
					return true;
				}

			} else if (args[1].equalsIgnoreCase("list")) {
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
				Send.sendPluginMessage(p, str);
				return true;

			} else
				return false;

		} else if (args[0].equalsIgnoreCase("rider")) {
			if (args.length != 2)
				return false;

			if (MobModel.isMobModel(args[1]) == -1) {
				Send.sendPluginMessage(p, "�ù��ﲻ����.");
				return true;
			}
			if (Main.getsMobModel().getsName()
					.equalsIgnoreCase(MobModel.getMobModel(args[1]).getsName())) {
				Send.sendPluginMessage(p, "�㲻�������Լ�.");
				return true;
			}

			if (MobModel.getMobModel(args[1]).getrider()
					.equalsIgnoreCase(Main.getsMobModel().getsName())) {
				Send.sendPluginMessage(p, "�����������Ѿ������˹�ϵ.");
				return true;
			}

			mm.setRider(args[1]);
		} else if (args[0].equalsIgnoreCase("name")) {
			if (args.length != 2)
				return false;
			mm.setDisplayName(args[1]);
		} else if (args[0].equalsIgnoreCase("damage")) {
			if (args.length == 2) {
				mm.setDmg(new Damage(Integer.parseInt(args[1])));
			} else if (args.length == 3) {
				mm.setDmg(new Damage(Integer.parseInt(args[1]), Integer
						.parseInt(args[2])));
			} else {
				return false;
			}

		} else if (args[0].equalsIgnoreCase("hp")) {
			if (args.length == 2) {
				mm.setHp(new HP(Integer.parseInt(args[1])));
			} else if (args.length == 3) {
				mm.setHp(new HP(Integer.parseInt(args[1]), Integer
						.parseInt(args[2])));
			} else {
				return false;
			}
		} else if (args[0].equalsIgnoreCase("attrcover")) {
			if (args.length != 2)
				return false;
			if (args[1].equalsIgnoreCase("true"))
				mm.setAttrCover(true);
			else if (args[1].equalsIgnoreCase("false"))
				mm.setAttrCover(false);
			else
				return false;

		} else if (args[0].equalsIgnoreCase("noRepel")) {
			if (args.length != 2)
				return false;
			if (args[1].equalsIgnoreCase("true"))
				mm.setNoRepel(true);
			else if (args[1].equalsIgnoreCase("false"))
				mm.setNoRepel(false);
			else
				return false;

		} else if (args[0].equalsIgnoreCase("exp")) {
			if (args.length == 2) {
				mm.setExp(new EXP(Integer.parseInt(args[1])));
			} else if (args.length == 3) {
				mm.setExp(new EXP(Integer.parseInt(args[1]), Integer
						.parseInt(args[2])));
			} else {
				return false;
			}
		} else if (args[0].equalsIgnoreCase("bossname")) {
			if (args.length < 2)
				return false;
			if (args[1].equalsIgnoreCase("enable")) {
				if (args.length != 3)
					return false;
				if (args[2].equalsIgnoreCase("true")) {
					mm.getBossName().setEnable(true);
				} else if (args[2].equalsIgnoreCase("false")) {
					mm.getBossName().setEnable(false);
				} else
					return false;

			} else if (args[1].equalsIgnoreCase("show")) {
				if (args.length != 3)
					return false;
				String str = "";
				for (int i = 0; i < args.length - 2; i++) {
					if (i != 0)
						str += " ";
					str += args[i + 2];
				}
				mm.getBossName().setValue(str);
			} else if (args[1].equalsIgnoreCase("nearby")) {
				if (args.length != 3)
					return false;
				mm.getBossName().setNearby(Integer.parseInt(args[2]));
			}

		} else if (args[0].equalsIgnoreCase("type")) {
			if (args.length != 2)
				return false;
			if (EntityType.fromName(args[1]) != null)
			mm.setType(EntityType.fromName(args[1]));
			else {
				Send.sendPluginMessage(p, "��������ȷ����������");
				return true;
			}
		} else if (args[0].equalsIgnoreCase("eqpt")) {
			mm.setEqpt(new Eqpt(p.getEquipment().getHelmet(), p.getEquipment()
					.getChestplate(), p.getEquipment().getLeggings(), p
					.getEquipment().getBoots(), p.getEquipment()
					.getItemInHand()));
			// װ��
		} else if (args[0].equalsIgnoreCase("skill")) {

			if (args.length < 2)
				return false;

			if (args[1].equalsIgnoreCase("add")) {

				if (args.length != 3)
					return false;
				Skill skill = Skill.getSkill(args[2]);
				if (skill == null) {

					Send.sendPluginMessage(p, "�ü��ܲ�����.");
					return true;

				} else
					mm.addSkill(skill);

			} else if (args[1].equalsIgnoreCase("del")) {
				if (args.length != 3)
					return false;
				if (!mm.delSkills(Integer.parseInt(args[2]))) {
					Send.sendPluginMessage(p, "�ü��ܲ�����.");
					return false;
				}

			} else if (args[1].equalsIgnoreCase("list")) {
				if (args.length != 2)
					return false;

				String str = "���＼���б�:";
				for (int i = 0; i < mm.getSkills().size(); i++) {
					if (i != 0)
						str += ",";
					str += i + ":" + mm.getSkills().get(i).getsName();
				}
				Send.sendPluginMessage(p, str);
				return true;

			} else
				return false;

		} else {
			return false;
		}


			saveMobModel(mm, p);
			Send.sendPluginMessage(p, "�����ɹ�.");
		return true;
	}

	private static void saveMobModel(MobModel mm, Player p) throws Exception {
		mm.save();
			Main.getCfg().save(Main.getF());
	}
}