package cn.rpgmc.utils;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import cn.rpgmc.run.Main;

public class Send {


public static void sendConsole(String s){
		Bukkit.getConsoleSender().sendMessage(
				"��e[" + Main.getMain().getName() + "] ��b" + s);
}
public static void sendPluginMessage(Player p,String s){
		p.sendMessage("��c��l[Mobs]��f" + s);
}

}