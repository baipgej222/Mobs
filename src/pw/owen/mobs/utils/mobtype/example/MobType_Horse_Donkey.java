package pw.owen.mobs.utils.mobtype.example;

import org.bukkit.entity.Horse;

public class MobType_Horse_Donkey extends MobType_Horse {

	public MobType_Horse_Donkey() {
		super("¿", false);
		// TODO �Զ����ɵĹ��캯�����
	}

	@Override
	protected Horse modifyH(Horse h) {
		h.setVariant(Horse.Variant.DONKEY);
		return h;
	}


}