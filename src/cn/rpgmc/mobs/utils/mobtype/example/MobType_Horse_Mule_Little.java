package cn.rpgmc.mobs.utils.mobtype.example;

import org.bukkit.entity.Horse;

public class MobType_Horse_Mule_Little extends MobType_Horse {

	public MobType_Horse_Mule_Little() {
		super("С����", true);
		// TODO �Զ����ɵĹ��캯�����
	}

	@Override
	protected Horse modifyH(Horse h) {
		h.setVariant(Horse.Variant.MULE);
		return h;
	}


}
