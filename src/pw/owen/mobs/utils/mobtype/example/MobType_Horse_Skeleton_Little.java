package pw.owen.mobs.utils.mobtype.example;

import org.bukkit.entity.Horse;

public class MobType_Horse_Skeleton_Little extends MobType_Horse {

	public MobType_Horse_Skeleton_Little() {
		super("С������", true);
		// TODO �Զ����ɵĹ��캯�����
	}

	@Override
	protected Horse modifyH(Horse h) {
		h.setVariant(Horse.Variant.SKELETON_HORSE);
		return h;
	}


}