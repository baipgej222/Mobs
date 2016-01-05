package pw.owen.mobs.command.example;

import org.bukkit.entity.Player;

import pw.owen.mobs.bean.skill.Skill;
import pw.owen.mobs.command.PluginCommand;
import pw.owen.mobs.utils.Send;

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
		Send.sendPluginMessage(p,
				"&3  /Mobs spawn modify world [add/list/del] �޸�ˢ�µ�����");
		Send.sendPluginMessage(p, "&3  /Mobs spawn modify chance [����] �޸�ˢ�µļ���");
		Send.sendPluginMessage(p,
				"&3  /Mobs spawn modify playerNearby [����] �޸�����ҵ�ˢ��������");
		Send.sendPluginMessage(p,
				"&3  /Mobs spawn modify maxInChunk [����] �޸��ڵ�ǰ������������");
		Send.sendPluginMessage(p,
				"&3  /Mobs spawn modify maxInWorld [����] �޸��ڵ�ǰ������������");
		Send.sendPluginMessage(p,
				"&3  /Mobs spawn modify maxInServer [����] �޸��ڵ�ǰ���������������");
		Send.sendPluginMessage(p,
				"&3  /Mobs spawn modify onPlayerNum [true/false] ˢ�¼����Ƿ����������");

	}

	public static void pointSpawnHelp(Player p) {
		Send.sendPluginMessage(p, "&3  /Mobs spawn tp ���͵����ˢ�ֵ㴦");
		Send.sendPluginMessage(p, "&3  /Mobs spawn modify point ����ˢ�µ�����λ��");
		Send.sendPluginMessage(p,
				"&3  /Mobs spawn modify single [Single] ����ÿ��ˢ������");
		Send.sendPluginMessage(p,
 "&3  /Mobs spawn modify max [Max] ���ù����������");
		Send.sendPluginMessage(p,
				"&3  /Mobs spawn modify range [Range] ����ˢ�°뾶(���onMoveΪTrue�����ᱻ����ԭ��)");
		Send.sendPluginMessage(p,
				"&3  /Mobs spawn modify onPoint [true/false] �����Ƿ�ֻ��ˢ�µ���ˢ��");
		Send.sendPluginMessage(p,
				"&3  /Mobs spawn modify onMove [true/false] �����Ƿ�ֻ��ˢ�·�Χ�ڻ");
		Send.sendPluginMessage(p,
				"&3  /Mobs spawn modify copyTo [��ˢ�µ���] ���Ƶ�ǰˢ�µ㵽�µ����ֺ�λ��");

	}

	public static void spawnHelp(Player p) {
		Send.sendPluginMessage(p,
				"&3  /Mobs spawn new [Point/World] [ˢ�µ���] ����ĳ��ˢ�µ�(�Զ�select)");
		Send.sendPluginMessage(p,
				"&3  /Mobs spawn select [Point/World] [ˢ�µ���] ����ĳ��ˢ�µ������");
		Send.sendPluginMessage(p, "&3  /Mobs spawn list [Point/World] �鿴ˢ�µ��б�");
		Send.sendPluginMessage(p, "&3  /Mobs spawn killall ɾ��һ��ˢ�µ��ˢ������");
		Send.sendPluginMessage(p, "&3  /Mobs spawn see [ˢ�µ�] �鿴һ������ˢ�µ����ϸ��Ϣ");
		Send.sendPluginMessage(p, "&3  /Mobs spawn modify del [ˢ�µ�] ɾ��һ������ˢ�µ�");
		Send.sendPluginMessage(p, "&3  /Mobs spawn modify mob [sName] ���ù���ģ��");
		Send.sendPluginMessage(p,
				"&3  /Mobs spawn modify lag [Lag] ����ˢ�¼��(tick:20tick=1s)");
		Send.sendPluginMessage(p,
				"&3  /Mobs spawn modify copy [��ˢ�µ���] ���Ƶ�ǰˢ�µ㵽�µ�����");

	}

	public static void mobHelp(Player p) {
		Send.sendPluginMessage(p,
				"&3  /Mobs mob new [������(������ʾ����,��Ϊ�Ǻ�)] ����ĳ������(�Զ�select)");
		Send.sendPluginMessage(p, "&3  /Mobs mob select [������] ����ĳ�����������");
		Send.sendPluginMessage(p, "&3  /Mobs mob spawn �����ߴ�����һ���ù���");
		Send.sendPluginMessage(p, "&3  /Mobs mob see �鿴һ���������ϸ��Ϣ");
		Send.sendPluginMessage(p, "&3  /Mobs mob list  �鿴�����б�");
		Send.sendPluginMessage(p, "&3  /Mobs mob killall �Ƴ��ù������͵�����ʵ��");
		Send.sendPluginMessage(p, "&3  /Mobs mob modify del ɾ��һ������");
		Send.sendPluginMessage(p,
				"&3  /Mobs mob modify drop [add [����] <�������> <��С����>(��������Ĭ��Ϊ�ֳֵĶѵ���)/list/del [��������]] ���ӵ�����͵��伸��");
		Send.sendPluginMessage(p,
				"&3  /Mobs mob modify droptype [��ʽ(All,Invalid,Random)] ���õ��䷽ʽ");
		Send.sendPluginMessage(p, "&3  /Mobs mob modify name [Name] ��������");
		Send.sendPluginMessage(p,
				"&3  /Mobs mob modify hp [HighHP] <LowHP>����Ѫ��");
		Send.sendPluginMessage(p,
				"&3  /Mobs mob modify copy [�¹���ģ����] ���Ƶ�ǰ����ģ�嵽�µ�����");
		Send.sendPluginMessage(p,
				"&3  /Mobs mob modify attrcover [boolean] �����Ƿ񸲸�����");
		Send.sendPluginMessage(p,
				"&3  /Mobs mob modify autoSave [boolean] ��������֮���Ƿ񱣴�ù���");
		Send.sendPluginMessage(p,
				"&3  /Mobs mob modify noRepel [boolean] �����Ƿ񲻱�����");
		Send.sendPluginMessage(p,
				"&3  /Mobs mob modify noNatureDamage [boolean] �����Ƿ�ȡ����Ȼ�˺�(����,��Ϣ��)");
		Send.sendPluginMessage(p,
				"&3  /Mobs mob modify damage [HighDamage] <LowDamage> �����˺�");
		Send.sendPluginMessage(p,
				"&3  /Mobs mob modify exp [HighEXP] <LowEXP> ������������ľ���");
		Send.sendPluginMessage(p, "&3  /Mobs mob modify type [Type] ���ù�������");
		Send.sendPluginMessage(p,
				"&3  /Mobs mob modify potion [set:([ҩˮ����] [ҩˮ�ȼ�])/del [ҩˮ����]/list] ���ӹ��������ҩˮ״̬");
		Send.sendPluginMessage(p,
				"&3  /Mobs mob modify rider [sName] ��������︽��һ����ʻ��(�����Ĺ���ģ��)");
		Send.sendPluginMessage(p,
				"&3  /Mobs mob modify eqpt ����װ��Ϊ��ǰ������װ�������õ�����");
		Send.sendPluginMessage(p,
				"&3  /Mobs mob modify skill [add/list/del [���ܱ��]] ��ɾ�鼼���б�");
		Send.sendPluginMessage(
				p,
				"&3  /Mobs mob modify sl [Day/Night/Sun/Rain/Thunder] [true/false] ���ù���ˢ�¶Ի���������");
		Send.sendPluginMessage(
				p,
				"&3  /Mobs mob modify bossname [enable/show/nearby] �Ƿ���boss��ʽ��ʾ����(nearby������,0����boss��������ȫ�����,-1����������� )");

	}

	public static void skillHelp(Player p) {
		Send.sendPluginMessage(p,
				"&3  /Mobs skill new [������(������ʾ����,��Ϊ�Ǻ�)] [��������] ����ĳ������(�Զ�select)");
		Send.sendPluginMessage(p, "&3  /Mobs skill select [������] ����ĳ�����ܵ�����");
		Send.sendPluginMessage(p, "&3  /Mobs skill type  �鿴���������б�");
		Send.sendPluginMessage(p, "&3  /Mobs skill list <��������> �鿴�����б�");
		Send.sendPluginMessage(p, "&3  /Mobs skill see �鿴һ�����ܵ���ϸ��Ϣ");
		Send.sendPluginMessage(p, "&3  /Mobs skill help [��������] �鿴�����ͼ��ܰ�������ָ��");
		Send.sendPluginMessage(p, "&3  /Mobs skill modify  �޸ļ�������");
		Skill.mainHelp();
	}

	public static void mainHelp(Player p) {
		Send.sendPluginMessage(p, "&3  /Mobs set ����ѡ��������ƷIDΪ���ϵ���Ʒ");
		Send.sendPluginMessage(p, "&3  /Mobs spawn ����ˢ�µ�ĸ�������<��Ҫ����>");
		Send.sendPluginMessage(p, "&3  /Mobs mob ���ù���ĸ�������<��Ҫ����>");
		Send.sendPluginMessage(p, "&3  /Mobs skill ���ü��ܵĸ�������<��Ҫ����>");
		Send.sendPluginMessage(p, "&3  /Mobs killall �Ƴ��������������ʵ��");
		Send.sendPluginMessage(p,
				"&3  /Mobs setBan [Animal/Monster] [true/false] ���������ڵ������Ƿ����Ĭ�ϲ����Ķ���/����");
		Send.sendPluginMessage(p, "&3  /Mobs listBan �鿴�����ڵ������Ƿ����Ĭ�ϲ����Ķ���/����");
		Send.sendPluginMessage(p, "&3  /Mobs PotionType �鿴����֧�ֵ�ҩˮ����");
		Send.sendPluginMessage(p, "&3  /Mobs MobType �鿴����֧����Ϊ���������");
		Send.sendPluginMessage(p, "&3  /Mobs update ���������ɰ�����");
		Send.sendPluginMessage(p, "&3  /Mobs reload ���ز��");
		Send.sendPluginMessage(p, "&3  /Mobs help ����");

	}

}
