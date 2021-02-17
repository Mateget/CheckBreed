package showbreed;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;


@Mod(modid = Main.MODID, name = Main.NAME, version = Main.VERSION ,acceptableRemoteVersions = "*" )
public class Main
{
    public static final String MODID = "checkbreed";
    public static final String NAME = "Check Breed";
    public static final String VERSION = "1.0.0";

    @Mod.EventHandler
    public void serverLoad(FMLServerStartingEvent e) {
    	CheckBreedCommand checkBreed = new CheckBreedCommand();
    	e.registerServerCommand(checkBreed); 	
    	
    }
    
    
}
