package pw.owen.mobs.bean.mob;


public enum TargetSelect{

	�������("CLOSEST_PLAYER"), 
	��ײ("COLLISION"), 
	�Զ���("CUSTOM"),
	������ׯ("DEFEND_VILLAGE"), 
	����Ŀ��("FORGOT_TARGET"), 
	�����߹���Ŀ��("OWNER_ATTACKED_TARGET"),
	����ѡ��Ŀ��("PIG_ZOMBIE_TARGET"),
	���Ŀ��("RANDOM_TARGET"), 
	Ŀ����Ԯ("REINFORCEMENT_TARGET"),
	Ŀ�깥��ʵ��("TARGET_ATTACKED_ENTITY"),
	Ŀ�깥������ʵ��("TARGET_ATTACKED_NEARBY_ENTITY"),
	Ŀ�깥��������("TARGET_ATTACKED_OWNER");

	private String reason;

	TargetSelect(String reason) {
		this.reason = reason;
	}

public String getReason(){
		return reason;
	}
	public static TargetSelect valuesOfReason(String string) {
		for(int i=0;i<values().length;i++)
			if(values()[i].getReason().equals(string))
				return values()[i];
		return null;
	}
}
