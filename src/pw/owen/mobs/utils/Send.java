package pw.owen.mobs.utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import pw.owen.mobs.run.Main;

public class Send {
	public static ChatColor COLOR = ChatColor.AQUA;

public static void sendConsole(String s){
		Bukkit.getConsoleSender().sendMessage(
				"��e[" + Main.getMain().getName() + "] ��b"
						+ s.replaceAll("&", "��"));
}
public static void sendPluginMessage(Player p,String s){
		p.sendMessage("��c��l[Mobs]��3" + s.replaceAll("&", "��"));
}

}
