package cn.rpgmc.mobs.command.example;

import org.bukkit.entity.Player;

import cn.rpgmc.mobs.bean.skill.Skill;
import cn.rpgmc.mobs.command.PluginCommand;
import cn.rpgmc.mobs.utils.Send;

public class Help_CMD implements PluginCommand {

	@Override
	public String[] getBranch() {
		// TODO �Զ����ɵķ������
		return new String[] { "mobs", "help" };
	}

	@Override
	public boolean run(Player p, String[] args, String auto) throws Exception {

		if (args.length == 0) {
			Send.sendPluginMessage(
					p,
					"/Mobs help [main/mob/skill/spawn] (spawn)<Point/World> �鿴[������/��������/��������/ˢ�µ�����]�İ����ı�");
					return true;
				}

		if (args.length != 1)
			if (!(args.length > 1 & args[0].equalsIgnoreCase("spawn")))
						return false;


		if (args[0].equalsIgnoreCase("main")) {
					mainHelp(p);
		} else if (args[0].equalsIgnoreCase("mob")) {
					mobHelp(p);
		} else if (args[0].equalsIgnoreCase("skill")) {
					skillHelp(p);
		} else if (args[0].equalsIgnoreCase("spawn")) {

			if (args.length == 1) {
				Send.sendPluginMessage(p,
						"/Mobs help spawn <Point/World> �鿴[ˢ�µ�����]�İ����ı�");
						return true;
					}
			if (!args[1].equalsIgnoreCase("point")
					& !args[1].equalsIgnoreCase("world")) {
				Send.sendPluginMessage(p,
						"/Mobs help spawn <Point/World> �鿴[ˢ�µ�����]�İ����ı�");
						return true;
					}
					// ����ˢ�µ�

					spawnHelp(p);
			if (args[1].equalsIgnoreCase("point")) {
						pointSpawnHelp(p);
					}
			if (args[1].equalsIgnoreCase("world")) {
						worldSpawnHelp(p);
					}

				} else {
					return false;
		}
		return true;

	}


	public static void worldSpawnHelp(Player p) {
		p.sendMessage("��a  /Mobs spawn modify world [add/list/del] �޸�ˢ�µ�����");
		p.sendMessage("��a  /Mobs spawn modify chance [����] �޸�ˢ�µļ���");
		p.sendMessage("��a  /Mobs spawn modify playerNearby [����] �޸�����ҵ�ˢ��������");

	}

	public static void pointSpawnHelp(Player p) {
		p.sendMessage("��a  /Mobs spawn modify point ����ˢ�µ�����λ��");
		p.sendMessage("��a  /Mobs spawn modify single [Single] ����ÿ��ˢ������");
		p.sendMessage("��a  /Mobs spawn modify range [Range] ����ˢ�°뾶(���onMoveΪTrue�����ᱻ����ԭ��)");
		p.sendMessage("��a  /Mobs spawn modify onPoint �����Ƿ�ֻ��ˢ�µ���ˢ��");
		p.sendMessage("��a  /Mobs spawn modify onMove �����Ƿ�ֻ��ˢ�·�Χ�ڻ");

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
		p.sendMessage("��a  /Mobs mob killall �Ƴ��ù������͵�����ʵ��");
		p.sendMessage("��a  /Mobs mob modify del ɾ��һ������");
		p.sendMessage("��a  /Mobs mob modify drop [add/list/del] ���ӵ�����͵��伸��");
		p.sendMessage("��a  /Mobs mob modify droptype [��ʽ(All,Invalid,Random)] ���õ��䷽ʽ");
		p.sendMessage("��a  /Mobs mob modify name [Name] ��������");
		p.sendMessage("��a  /Mobs mob modify hp [HighHP] <LowHP>����Ѫ��");
		p.sendMessage("��a  /Mobs mob modify attrcover [boolean] �����Ƿ񸲸�����");
		p.sendMessage("��a  /Mobs mob modify noRepel [boolean] �����Ƿ񲻱�����");
		p.sendMessage("��a  /Mobs mob modify damage [HighDamage] <LowDamage> �����˺�");
		p.sendMessage("��a  /Mobs mob modify exp [HighEXP] <LowEXP> ������������ľ���");
		p.sendMessage("��a  /Mobs mob modify type [Type] ���ù�������");
		p.sendMessage("��a  /Mobs mob modify effect [set:([ҩˮ����] [ҩˮ�ȼ�])/del/list] ���ӹ��������ҩˮ״̬");
		p.sendMessage("��a  /Mobs mob modify rider [sName] ��������︽��һ����ʻ��(�����Ĺ���ģ��)");
		p.sendMessage("��a  /Mobs mob modify eqpt ����װ��Ϊ��ǰ������װ�������õ�����");
		p.sendMessage("��a  /Mobs mob modify skill [add/list/del] ��ɾ�鼼���б�");
		p.sendMessage("��a  /Mobs mob modify sl [Day/Night/Sun/Rain/Thunder] [true/false] ���ù���ˢ�¶Ի���������");
		p.sendMessage("��a  /Mobs mob modify bossname [enable/show/nearby] �Ƿ���boss��ʽ��ʾ����");

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
		p.sendMessage("��a  /Mobs killall �Ƴ��������������ʵ��");
		p.sendMessage("��a  /Mobs setBan [Animal/Monster] [true/false] ���������ڵ������Ƿ����Ĭ�ϲ����Ķ���/����");
		p.sendMessage("��a  /Mobs listBan �鿴�����ڵ������Ƿ����Ĭ�ϲ����Ķ���/����");
		p.sendMessage("��a  /Mobs listPotionEffectType �鿴����֧�ֵ�ҩˮ����");
		p.sendMessage("��a  /Mobs listEntityType �鿴����֧����Ϊ���������");
		p.sendMessage("��a  /Mobs update ���������ɰ�����");
		p.sendMessage("��a  /Mobs reload ���ز��");
		p.sendMessage("��a  /Mobs help ����");

	}

}
