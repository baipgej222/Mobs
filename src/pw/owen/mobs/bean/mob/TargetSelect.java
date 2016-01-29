package pw.owen.mobs.bean.mob;

import org.bukkit.event.entity.EntityTargetEvent;

public enum TargetSelect {
	�������(EntityTargetEvent.TargetReason.CLOSEST_PLAYER), 
	��ײ(EntityTargetEvent.TargetReason.COLLISION), 
	�Զ���(EntityTargetEvent.TargetReason.CUSTOM),
	������ׯ(EntityTargetEvent.TargetReason.DEFEND_VILLAGE), 
	����Ŀ��(EntityTargetEvent.TargetReason.FORGOT_TARGET), 
	�����߹���Ŀ��(EntityTargetEvent.TargetReason.OWNER_ATTACKED_TARGET),
	����ѡ��Ŀ��(EntityTargetEvent.TargetReason.PIG_ZOMBIE_TARGET),
	���Ŀ��(EntityTargetEvent.TargetReason.RANDOM_TARGET), 
	Ŀ����Ԯ(EntityTargetEvent.TargetReason.REINFORCEMENT_TARGET),
	Ŀ�깥��ʵ��(EntityTargetEvent.TargetReason.TARGET_ATTACKED_ENTITY),
	Ŀ�깥������ʵ��(EntityTargetEvent.TargetReason.TARGET_ATTACKED_NEARBY_ENTITY),
	Ŀ�깥��������(EntityTargetEvent.TargetReason.TARGET_ATTACKED_OWNER);
	private EntityTargetEvent.TargetReason reason;

	TargetSelect(EntityTargetEvent.TargetReason reason) {
		this.reason = reason;
	}
	public EntityTargetEvent.TargetReason getReason() {
		return reason;
	}
	public static TargetSelect valuesOfReason(EntityTargetEvent.TargetReason reason) {
		for(int i=0;i<values().length;i++)
			if(values()[i].getReason()==reason)
				return values()[i];
		return null;
	}
}
