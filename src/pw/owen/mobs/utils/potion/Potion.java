package pw.owen.mobs.utils.potion;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.potion.PotionEffectType;

public class Potion {
	private static Map<String, String> key = new HashMap<String, String>();
	static {

		key.put("�ٶ�", PotionEffectType.SPEED.getName());
		key.put("�����˺�", PotionEffectType.ABSORPTION.getName());
		key.put("ʧ��", PotionEffectType.BLINDNESS.getName());
		key.put("��������", PotionEffectType.DAMAGE_RESISTANCE.getName());
		key.put("�����ھ�", PotionEffectType.FAST_DIGGING.getName());
		key.put("���濹��", PotionEffectType.FIRE_RESISTANCE.getName());
		key.put("˲���˺�", PotionEffectType.HARM.getName());
		key.put("˲������", PotionEffectType.HEAL.getName());
		key.put("����", PotionEffectType.HUNGER.getName());
		key.put("�˺�����", PotionEffectType.INCREASE_DAMAGE.getName());
		key.put("����", PotionEffectType.INVISIBILITY.getName());
		key.put("��Ծ����", PotionEffectType.JUMP.getName());
		key.put("ҹ��", PotionEffectType.NIGHT_VISION.getName());
		key.put("�ж�", PotionEffectType.POISON.getName());
		key.put("����", PotionEffectType.REGENERATION.getName());
		key.put("����", PotionEffectType.SATURATION.getName());
		key.put("����", PotionEffectType.SLOW.getName());
		key.put("�ھ���", PotionEffectType.SLOW_DIGGING.getName());
		key.put("ˮ�º���", PotionEffectType.WATER_BREATHING.getName());
		key.put("����", PotionEffectType.WEAKNESS.getName());
		key.put("����", PotionEffectType.WITHER.getName());
		key.put("��θ", PotionEffectType.CONFUSION.getName());
		key.put("��������", PotionEffectType.HEALTH_BOOST.getName());

	}

	public static String fromCh(String ch) {

		return key.get(ch);

	}

	public static String fromEn(String en) {
		Object[] keys = key.keySet().toArray();
		for (int i = 0; i < keys.length; i++)
			if (key.get(keys[i]).equals(en))
				return (String) keys[i];
		return null;

	}
}
