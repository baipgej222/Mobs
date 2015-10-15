package cn.rpgmc.bean.skill;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import cn.rpgmc.bean.mob.Mob;
import cn.rpgmc.run.Main;

/**
 * 
 * @author owen
 * @see 
 *�̳и�����Ҫ��д�ķ���:<P/>
 *static String getType() <P/>
 *static String help() <P/>
 */


public abstract class Skill {
	private String  sName = null;
	private static ArrayList<Class>  types= new ArrayList<Class> ();
	private static ArrayList<Skill>  skills = new ArrayList<Skill> ();
	private ConfigurationSection cfg = null;
	private double chance=0;
	private int cooling = 0;
	private static long BECOOLING = 0;
	public static final String TRIGGER_CYCLE="TRIGGER_CYCLE";//����
	public static final String TRIGGER_ATTACK="TRIGGER_ATTACK";//����
	public static final String TRIGGER_HURT="TRIGGER_HURT";//�ܵ��˺�
	public static final String TRIGGER_DYING="TRIGGER_DYING";//����
	public static final String TRIGGER_TARGET="TRIGGER_TARGET";//��׼
	public static final String TRIGGER_BETARGET="TRIGGER_BETARGET";//����׼
	private String trigger = TRIGGER_CYCLE;
	public static final String RANGE_WORLD="RANGE_WORLD";
	public static final String RANGE_TARGET="RANGE_TARGET";
	public static final String RANGE_CHUNK="RANGE_CHUNK";
	private String  range = RANGE_CHUNK;
	private ArrayList<String> enemys=  new ArrayList<String>();
	static {
/**
 * �ڴ�������м̳�Skill����
 */
		types.add(Skill_Message.class);

		
	}
	public static void setSkills(ArrayList<Skill> skills) {
		Skill.skills = skills;
	}
	public String getsName() {
		return sName;
	}
	public static ArrayList<Skill> getSkills() {
		return skills;
	}
	public static void addSkills(Skill skill) {
		for(int i=0;i<skills.size();i++){
			if(skills.get(i).getsName().equalsIgnoreCase(skill.getsName())){
				skills.set(i, skill);
				return;
			}
			
		}
		skills.add(skill);
	}
	public static Skill newSkill(String tt,String sName){
		Class t = null;
		try {
			for(int i=0;i<types.size();i++){
				
			Method method	= types.get(i).getMethod("getType");
				String str=(String) method.invoke(null);
			if(str.equalsIgnoreCase(tt)){
				
				t=types.get(i);
				
				
				
			}
			
			
			
			
			
			}
		} catch (Exception e) {

			e.printStackTrace();
			return null;
		}
	if(t==null)
		return null;
	
	try {
		return (Skill) t.getDeclaredConstructor(String.class,ConfigurationSection.class).newInstance(sName,Main.getCfg().getConfigurationSection("Skill"));
	} catch (Exception e) {
		 if(e instanceof InvocationTargetException){  
             try {
				throw ((InvocationTargetException) e).getTargetException();
			} catch (Throwable e1) {
				// TODO �Զ����ɵ� catch ��
				e1.printStackTrace();
			}  
         }
		e.printStackTrace();
		return null;

	}
		
		

		
		
	}
	public static boolean isSkills(Skill skill) {
		for(int i=0;i<skills.size();i++){
			if(skills.get(i)==skill){
				
				return true;
			}
			
		}
		return false;
	}


public int getCooling() {
	return cooling;
}
public void setCooling(int cooling) {
	this.cooling = cooling;
}

public static boolean isTrigger(String trigger) {
	if(!trigger.equalsIgnoreCase(TRIGGER_CYCLE))
		if(!trigger.equalsIgnoreCase(TRIGGER_ATTACK))
			if(!trigger.equalsIgnoreCase(TRIGGER_HURT))
				if(!trigger.equalsIgnoreCase(TRIGGER_DYING))
					if(!trigger.equalsIgnoreCase(TRIGGER_TARGET))
						if(!trigger.equalsIgnoreCase(TRIGGER_BETARGET))
			return false;
	return true;
}
public static boolean isRange(String range) {
	if(!range.equalsIgnoreCase(RANGE_WORLD))
		if(!range.equalsIgnoreCase(RANGE_TARGET))
			if(!range.equalsIgnoreCase(RANGE_CHUNK))
				return false;
return true;
}
public boolean cmdManager(String[] args,Player p) {
	
		
		if(args[2].equalsIgnoreCase("chance")){
			if(args.length!=4)
				return false;
			setChance(Double.parseDouble(args[3]));
			
		}else if(args[2].equalsIgnoreCase("cooling")){
			if(args.length!=4)
				return false;
			setCooling(Integer.parseInt(args[3]));
			
		}else if(args[2].equalsIgnoreCase("trigger")){
			if(args.length!=4)
				return false;
			if(!isTrigger(args[3]))
				{p.sendMessage("��c[����������]��f�������Ͳ�����.");
				return false;
				}
				setTrigger(args[3]);
			
			
		}else if(args[2].equalsIgnoreCase("range")){
			if(args.length!=4)
				return false;
			if(!isRange(args[3]))
				{p.sendMessage("��c[����������]��f�������Ͳ�����.");
			return false;
			
				}
			
				setRange(args[3]);
			
			
		}else if(args[2].equalsIgnoreCase("enemys")){
			if(args.length<4)
				return false;
			
			if(args[3].equalsIgnoreCase("add")){
				if(args.length!=5)
					return false;
				enemys.add(args[4]);
				
			}
			else if(args[3].equalsIgnoreCase("del")){
				if(args.length!=5)
					return false;
				enemys.remove(Integer.parseInt(args[4]));
			}
			else if(args[3].equalsIgnoreCase("list")){
				String str="";
				for(int i=0;i<enemys.size();i++){
					if(i!=0)
						str+=",";
					
					str+=i+":"+enemys.get(i);
				}
				
				p.sendMessage("�����б�:"+str);
				return true;
				
				
				
			}else return false;
			
		
			p.sendMessage("��c[����������]��f�����ɹ�.");
			
			save();
			try {
				Main.saveYml();
			} catch (IOException e) {
				p.sendMessage("��c[����������]��f����ʧ��.");
			}
			return true;

		}else
			{
			
			boolean b= cmdElse(args, p);
			save();
			try {
				Main.saveYml();
			} catch (IOException e) {
				p.sendMessage("��c[����������]��f����ʧ��.");
			}
			
			return b;
		
			}
		
		return false;

}





/**
 * ����ʵ�����е�����
 * 
 */
protected abstract boolean cmdElse(String[] args,Player p) ;



/**
 * ִ�м���ʵ��
 * 
 */
protected abstract void run(Mob mob,Entity entity);


/**
 * ���ؼ���ʵ������
 * 
 */
public String see(){
	String str="������ϸ��Ϣ:\n";
	str+="  ����:"+getsName()+"\n";
	str+="  ����:"+getChance()+"\n";
	str+="  ��������:"+getTrigger()+"\n";
	str+="  ������Χ:"+getRange()+"\n";
	str+="  ��ȴʱ��:"+getCooling()+"\n";
	String s="";
	for(int i =0;i<getEnemys().size();i++)
	{
		s+="    "+getEnemys().get(i)+"\n";
		
	}
	if(s.equalsIgnoreCase(""))
		s="��\n";
	
	str+="  ���������б�:"+s;
	return str+seeNext();
	
}

public abstract String seeNext();




/**
 * ���ؼ��������б�
 * 
 */
public static String getList() {
	// TODO �Զ����ɵķ������
	return getAllList();
}

private static String getAllList() {
	String s="";
	for(int i=0;i<getSkills().size();i++)
	{
				if(i!=0)
			s+=",";
			
			
			s+=getSkills().get(i).getsName();
				
				

		
	}
	s="�������ͼ����б�:"+s;
	return s;
}
public static String getList(String str) {
	if(isType(str)==null)
		return "û�������������";
	
	
	String s="";
	for(int i=0;i<getSkills().size();i++)
	{
		if(getSkills().get(i).getType().equalsIgnoreCase(str));
		{		if(i!=0)
			s+=",";
			
			
			s+=getSkills().get(i).getsName();
				
				

		}
	}
	s=str+"���ͼ����б�:"+s;
	return s;
}




/**
 * �������¼������Ͱ����ı�
 * 
 */
public static String help(String str) {
	Class skill = isType(str);
if(skill==null)
	return "�ü������Ͳ�����.";
	try {
		String s = (String) skill.getMethod("help").invoke(null);
		return help()+"\n"+s;
	} catch (IllegalAccessException | IllegalArgumentException
			| InvocationTargetException | NoSuchMethodException
			| SecurityException e) {
		e.printStackTrace();
		return "";
	}

	
	
}

public static String help() {
	return "  /mobs skill modify chance [��������] ���øü��ܱ������ļ���.\n"+
				"  /mobs skill modify cooling [��ȴʱ��(tick)] ʹ��һ�κ����ȴʱ��(20tickԼ����1��)\n"+
				"  /mobs skill modify trigger [������ʽ] �������ܵķ�ʽ,��ѡ:\n"+
				"    TRIGGER_CYCLE ����\n"+
				"    TRIGGER_ATTACK ����\n"+
				"    TRIGGER_HURT �ܵ��˺�\n"+
				"    TRIGGER_DYING ����\n"+
				"    TRIGGER_TARGET ��׼\n"+
				"    TRIGGER_BETARGET ����׼\n"+
				"  /mobs skill modify range [������Χ] ��������ָ��Ķ���,��ѡ:\n"+
				"    RANGE_WORLD ��������\n"+
				"    RANGE_CHUNK ��������\n"+
				"    RANGE_TARGET ������(�����ʹ�����ʽ����Ϊ����.)\n"+
				"  /mobs skill modify enemys [add/del/list] �ɴ���ʵ���б�,����:"+
				"    /mobs skill modify enemys add PLAYER (�ü��ܿ��Ա�������Ҵ���)"+
				"    /mobs skill modify enemys add LIVINGENTITY (�ü��ܿ��Ա��������ﴥ��)"+
				"    /mobs skill modify enemys add ZOMBIE (�ü��ܿ��Ա����н�ʬ����)";

	
}

public static Class isType(String str){
	for(int i=0;i<types.size();i++){
		try {
			if(((String)types.get(i).getMethod("getType").invoke(types.get(i))).equalsIgnoreCase(str)){
				return types.get(i);
				
			}
		} catch (IllegalAccessException | IllegalArgumentException
				| InvocationTargetException | NoSuchMethodException
				| SecurityException e) {
			
			return null;
		}
	}
	
	return null;
}


public static String getTypes() {
	String str="";
	for(int i=0;i<types.size();i++){
		try {
			str+=types.get(i).getMethod("getType").invoke(types.get(i));
			
			if(i!=types.size()-1)
				str+=",";
			
			
		} catch (Exception e) {

		} 
	}
	
	str="���������б�:"+str;
	return str;
}
public static String getType() {
	return "Skill";
}
public static Skill getSkill(String skill) {
	
	for(int i=0;i<skills.size();i++){
		if(skills.get(i).getsName().equalsIgnoreCase(skill))
			return skills.get(i);
	}
	return null;
}

	public String getRange() {
		return range;
	}
	public ArrayList<String> getEnemys() {
		return enemys;
	
	}
	private Skill(){
		
	}
	public Skill(String sName,ConfigurationSection cfg) {
		cfg.createSection(sName);
		this.cfg=cfg.getConfigurationSection(sName);
		this.sName=sName;
			trigger=TRIGGER_ATTACK;
			chance=25;
			range=RANGE_TARGET;
			cooling=500;
			 newSkillNext();
		addSkills(this);
		save();
	}

public Skill(ConfigurationSection cfg) {
	this.cfg=cfg;
	trigger=cfg.getString("trigger");
	chance=cfg.getInt("chance");
	range=cfg.getString("range");
	cooling=cfg.getInt("cooling");
	enemys=(ArrayList<String>) cfg.getList("enemys");
	sName=cfg.getName();
	skillNext(cfg);
	addSkills(this);
}

protected  abstract void skillNext(ConfigurationSection cfg);
/**
 * �½�ʱ���еĲ���/super()
 */

protected abstract void newSkillNext();
/**
 * �����Զ��䵽cfg�ļ�/super()
 */
public void save(){
	saveNext();
	cfg.set("trigger",trigger);
	cfg.set("chance",chance);
	cfg.set("range",range);
	cfg.set("enemys",enemys);
	cfg.set("cooling",cooling);
	try {
		cfg.set("type", (String)this.getClass().getMethod("getType").invoke(null));
	} catch (IllegalAccessException | IllegalArgumentException
			| InvocationTargetException | NoSuchMethodException
			| SecurityException e) {
		// TODO �Զ����ɵ� catch ��
		e.printStackTrace();
	}
	
}


	
	protected abstract void saveNext() ;
	public boolean isEnemy(String en) {
		if(EntityType.fromName(en)==null&!en.equalsIgnoreCase("PLAYER"))
			return false;
			
			
			
			for(int i=0;i<enemys.size();i++){
				if(enemys.get(i).equalsIgnoreCase(en))
				return true;
			}
			

if(enemys.size()==0){
	return true;
}

return false;
	
}
	
	public ConfigurationSection getCfg() {
		return cfg;
	}

	public boolean addEnemys(String enemy) {
		if(enemys ==null)
			enemys=new ArrayList<String>();

		try {
			if(Class.forName(enemy)!=null)
				if(Class.forName(enemy).isInstance(Entity.class))
				enemys.add(enemy);
				else
					return false;
		} catch (ClassNotFoundException e) {
			return false;
		}
		return true;
	}
	
	public void setRange(String range) {
		this.range = range;
	}
	public String getTrigger() {
		return trigger;
	}
	public double getChance() {
		return chance;
	}
	public void setChance(double chance) {
		this.chance = chance;
	}
	
	public void setTrigger(String trigger) {
		this.trigger = trigger;
	}
	public void runSkill(Mob mob,List<Entity> e) {
		if(chance>(Math.random()*100)){
			long time=System.currentTimeMillis();
			if(time-BECOOLING>cooling*50)
			{BECOOLING=System.currentTimeMillis();
				
				for(int i =0;i<e.size();i++)
			{
				if(isEnemy(e.get(i).getType().name()))
				this.run(mob,e.get(i));
				}
				
				
			
			}

		
		}
		
	}
	public void runSkill(Mob mob, Entity e) {
		ArrayList<Entity> a = new ArrayList<Entity>();
		a.add(e);
	runSkill(mob,e);
		
	}
	
	

	public static int isSkill(String string) {

		for(int i=0;i<skills.size();i++){
			if(skills.get(i).getsName().equalsIgnoreCase(string)){
				return i;
			}
		}
		return -1;
	}
	public static Skill newSkill(ConfigurationSection config) {

		
		Class t = null;
		String tt=config.getString("type");
		try {
			for(int i=0;i<types.size();i++){
			if(((String)types.get(i).getMethod("getType").invoke(types.get(i))).equalsIgnoreCase(tt)){
				t=types.get(i);
			}
			}
		} catch (Exception e) {

			e.printStackTrace();
			return null;
		}
	if(t==null)
		return null;
	
	try {
		return (Skill) t.getDeclaredConstructor(ConfigurationSection.class).newInstance(config);
	} catch (Exception e) {

		e.printStackTrace();
		return null;

	}
		
		
		
	}



}
