package pw.owen.mobs.utils.mobtype.example;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Zombie;

import pw.owen.mobs.utils.mobtype.MobType;

public class MobType_Zombie_Villager_Little extends MobType {
	public MobType_Zombie_Villager_Little() {
		super("С����ʬ", EntityType.ZOMBIE);
	}

	@Override
	public LivingEntity modify(LivingEntity e) {
		Zombie z = (Zombie) e;
		z.setBaby(true);
		z.setVillager(true);
		return e;
	}

}
