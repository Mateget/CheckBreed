package showbreed;

import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.Lists;
import com.pixelmonmod.pixelmon.Pixelmon;
import com.pixelmonmod.pixelmon.api.pokemon.SpecFlag;
import com.pixelmonmod.pixelmon.storage.PlayerPartyStorage;

import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.server.SPacketPlayerPosLook.EnumFlags;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;

public class CheckBreedCommand implements ICommand {
	
	String cmdname = "checkbreed";
	@Override
	public int compareTo(ICommand arg0) {
		return 0;
	}

	@Override
	public String getName() {
		return cmdname;
	}

	@Override
	public String getUsage(ICommandSender sender) {
		return "/" + cmdname + " <slot>";
	}

	@Override
	public List<String> getAliases() {
		List<String> aliases = Lists.<String>newArrayList();
		aliases.add(cmdname);
		aliases.add("cb");
		return aliases;
	}
	
	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
		EntityPlayer player = server.getPlayerList().getPlayerByUsername(sender.getName());
		if(args.length==0) {
			sender.sendMessage(format(net.minecraft.util.text.TextFormatting.GRAY, "/checkbreed <slot>"));
		} else {
			if(!isInteger(args[0])) {
				sender.sendMessage(format(net.minecraft.util.text.TextFormatting.RED, "Invalid slot given!"));
			} else {
				int slot = Integer.parseInt(args[0]); 
				if( slot < 1 || slot > 6 ) {
					sender.sendMessage(format(net.minecraft.util.text.TextFormatting.GRAY, "Slot number must be between 1 and 6."));
				} else {
					
					PlayerPartyStorage party = Pixelmon.storageManager.getParty(player.getUniqueID());	
					if(party.get(slot-1) == null) {
						sender.sendMessage(format(net.minecraft.util.text.TextFormatting.GRAY,"Nothing exists in that slot."));
					} else {
						if(party.get(slot-1).hasSpecFlag("unbreedable")) {
							sender.sendMessage(format(net.minecraft.util.text.TextFormatting.RED,party.get(slot-1).getDisplayName().toString() + " is unbreedable"));
						} else {
							sender.sendMessage(format(net.minecraft.util.text.TextFormatting.GREEN,party.get(slot-1).getDisplayName().toString() + " is breedable"));
						}				
					}				
				}
			}
		}
		
			
	}
	
	public static boolean isInteger(String s, int radix) {
	    if(s.isEmpty()) return false;
	    for(int i = 0; i < s.length(); i++) {
	        if(i == 0 && s.charAt(i) == '-') {
	            if(s.length() == 1) return false;
	            else continue;
	        }
	        if(Character.digit(s.charAt(i),radix) < 0) return false;
	    }
	    return true;
	}

	@Override
    public boolean checkPermission(MinecraftServer server, ICommandSender sender) {
        return PermissionUtils.canUse(Main.MODID + ".checkbreed", sender);
    }

	@Override
	public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, BlockPos targetPos) {
		List<String> list = new ArrayList<String>();
		list.add("1");
		list.add("2");
		list.add("3");
		list.add("4");
		list.add("5");
		list.add("6");
		return list;
	}

	@Override
	public boolean isUsernameIndex(String[] args, int index) {
		if (args.length > 0) {
			return true;
		}
		return false;
	}
	
	private TextComponentTranslation format(TextFormatting color, String str, Object... args)
    {
        TextComponentTranslation ret = new TextComponentTranslation(str, args);
        ret.getStyle().setColor(color);
        return ret;
    }
	
	public static boolean isInteger(String str) {
	    if (str == null) {
	        return false;
	    }
	    int length = str.length();
	    if (length == 0) {
	        return false;
	    }
	    int i = 0;
	    if (str.charAt(0) == '-') {
	        if (length == 1) {
	            return false;
	        }
	        i = 1;
	    }
	    for (; i < length; i++) {
	        char c = str.charAt(i);
	        if (c < '0' || c > '9') {
	            return false;
	        }
	    }
	    return true;
	}
	
}