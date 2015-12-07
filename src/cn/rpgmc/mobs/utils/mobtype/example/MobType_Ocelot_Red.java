package cn.rpgmc.mobs.utils.mobtype.example;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Ocelot;

import cn.rpgmc.mobs.utils.mobtype.MobType;

public class MobType_Ocelot_Red extends MobType {
	public MobType_Ocelot_Red() {
		super("��ɫ��è", EntityType.OCELOT);
	}

	@Override
	public LivingEntity modify(LivingEntity e) {
		Ocelot o = (Ocelot) e;
	o.setCatType(Ocelot.Type.RED_CAT);
		return e;
	}

}
