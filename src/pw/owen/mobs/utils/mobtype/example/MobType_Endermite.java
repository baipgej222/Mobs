package pw.owen.mobs.utils.mobtype.example;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;

import pw.owen.mobs.utils.mobtype.MobType;

public class MobType_Endermite extends MobType {
	public MobType_Endermite() {
		super("ĩӰ��", EntityType.ENDERMITE);
	}

	public static double getStartWith() {
		return 1.8;
	}

	@Override
	public LivingEntity modify(LivingEntity e) {
		return e;
	}

}
