package cn.rpgmc.mobs.utils.mobtype.example;

import org.bukkit.entity.Horse;

public class MobType_Horse_Undead_Little extends MobType_Horse {

	public MobType_Horse_Undead_Little() {
		super("С��ʬ��", true);
		// TODO �Զ����ɵĹ��캯�����
	}

	@Override
	protected Horse modifyH(Horse h) {
		h.setVariant(Horse.Variant.UNDEAD_HORSE);
		return h;
	}


}
