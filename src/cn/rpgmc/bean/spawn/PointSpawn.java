package cn.rpgmc.bean.spawn;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;

import cn.rpgmc.bean.mob.Mob;

public class PointSpawn extends Spawn{
	private static ArrayList<PointSpawn> Pmobcreates=new ArrayList<PointSpawn>();
	private Loc p;
	private Loc o;
	private int range=-1;
	private int one=1;

	public static ArrayList<PointSpawn> getPmobcreates() {
		return Pmobcreates;
	}
	public Location getO() {
		return o.getLocation();
	}
	public Location getP() {
		return p.getLocation();
	}
	public int getRange() {
		return range;
	}
	public void setO(Location o) {
		this.o = new Loc(o);
	}
	public void setP(Location p) {
		this.p = new Loc(p);
	}
	public void setRange(int range) {
		this.range = range;
	}
	public PointSpawn(String sName,ConfigurationSection cfg,Location p) {
		super(sName,cfg);
		range=-1;
		this.p=new Loc(p);
		this.o=new Loc(p);
		this.one=2;	

		Pmobcreates.add(this);
	}
	public PointSpawn(ConfigurationSection cfg) {
		super(cfg);
		range=cfg.getInt("range");
		p=new Loc(cfg.getConfigurationSection("point").getString("WORLD"),cfg.getConfigurationSection("point").getInt("X"),cfg.getConfigurationSection("point").getInt("Y"),cfg.getConfigurationSection("point").getInt("Z"));
		o=new Loc(cfg.getConfigurationSection("center").getString("WORLD"),cfg.getConfigurationSection("center").getInt("X"),cfg.getConfigurationSection("center").getInt("Y"),cfg.getConfigurationSection("center").getInt("Z"));
		one=cfg.getInt("one");
		Pmobcreates.add(this);
	}

	public void save() {
		getCfg().set("one", one);
		getCfg().createSection("center");
		getCfg().createSection("point");
		getCfg().getConfigurationSection("center").set("X",o.getX() );
		getCfg().getConfigurationSection("center").set("Y",o.getY() );
		getCfg().getConfigurationSection("center").set("Z",o.getZ() );
		getCfg().getConfigurationSection("center").set("WORLD",o.getWorld() );
		getCfg().getConfigurationSection("point").set("X",p.getX() );
		getCfg().getConfigurationSection("point").set("Y",p.getY() );
		getCfg().getConfigurationSection("point").set("Z",p.getZ() );
		getCfg().getConfigurationSection("point").set("WORLD",p.getWorld() );
getCfg().set("range", range);
super.save();
	}
	public int getOne() {
		return one;
	}
	public void setOne(int one) {
		this.one = one;
	}
	@Override
	public
	String getCreateType() {
		// TODO �Զ����ɵķ������
		return this.POINTMOBCREATE;
	}
	public void test() {
		if(getRange()==-1)
			return;
		
	ArrayList<Mob> m = getMobs();
	for(int i=0;i<m.size();i++){
		if(Math.abs(m.get(i).getE().getLocation().getBlockX()-getO().getBlockX())>getRange()){
			m.get(i).getE().teleport(getP());
		}
		
		if(Math.abs(m.get(i).getE().getLocation().getBlockY()-getO().getBlockY())>getRange()){
			m.get(i).getE().teleport(getP());
		}
	}
	}
	@Override
	public String getSee() {
		
		String s1 = "ˢ�µ�λ��:"+p.getX()+","+p.getY()+","+p.getZ()+"|"+p.getWorld();
		String s2 = "������Χ������:"+o.getX()+","+o.getY()+","+o.getZ()+"|"+o.getWorld();
		String s3 = "������Χ:"+range;
		String s4 = "һ��ˢ������:"+one;
		return getMainSee()+"\n"+s1+"\n"+s2+"\n"+s3+"\n"+s4;
	}


}
