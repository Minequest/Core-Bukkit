package com.theminequest.MineQuest.Frontend.Command;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.logging.Level;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.theminequest.MineQuest.I18NMessage;
import com.theminequest.MineQuest.MineQuest;
import com.theminequest.MineQuest.API.Managers;

public abstract class CommandFrontend implements CommandExecutor {
	
	private String cmdname;
	
	public CommandFrontend(String name){
		cmdname = name;
		Managers.log("[CommandFrontend] Starting Command Frontend for \""+cmdname+"\"...");
	}

	@Override
	public boolean onCommand(CommandSender arg0, Command arg1, String arg2,
			String[] arg3) {
		
		Player player = null;
		if (arg0 instanceof Player)
			player = (Player)arg0;
		if ((player==null) && !allowConsole()){
			Managers.log(Level.WARNING,"[CommandFrontend] No console use for \""+cmdname+"\"...");
			return false;
		}
		
		if (arg3.length==0)
			return help(player,arg3);

		String cmd = arg3[0].toLowerCase();
		
		if (player!=null){
			if (!hasPermission(cmdname+"."+cmd,player)){
				player.sendMessage(ChatColor.RED + I18NMessage.Cmd_NOPERMISSION.getDescription());
				return false;
			}
		}

		String[] arguments = shrinkArray(arg3);

		Method m;
		try {
			m = this.getClass().getMethod(cmd, Player.class, String[].class);
			return (Boolean)m.invoke(this, player, arguments);
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			//e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
			arg0.sendMessage(ChatColor.RED + "A severe error occured executing the command.");
			arg0.sendMessage(ChatColor.RED + "We've recovered as best as we can; please alert system admins.");
			return true;
		}
		arg0.sendMessage(I18NMessage.Cmd_INVALIDARGS.getDescription());
		return false;
	}
	
	private boolean hasPermission(String node, Player player){
		if (player.isOp())
			return true;
		node = "minequest." + node;
		String[] parts = node.split(".");
		for (int i=0; i<parts.length; i++){
			String newnode = "";
			for (int j=0; j<i+1; i++){
				newnode+=parts[j] + ".";
			}
			if (i!=parts.length-1)
				newnode+="*";
			else
				newnode = newnode.substring(0, newnode.length()-1);
			if (player.hasPermission(node))
				return true;
			if (MineQuest.permission.has(player, node))
				return true;
		}
		
		return false;
	}
	
	private String[] shrinkArray(String[] array){
		if (array.length<=1)
			return new String[0];
		String[] toreturn = new String[array.length-1];
		for (int i=1; i<array.length; i++)
			toreturn[i-1] = array[i];
		return toreturn;
	}
	
	public abstract Boolean help(Player p, String[] args);
	public abstract boolean allowConsole();

}
