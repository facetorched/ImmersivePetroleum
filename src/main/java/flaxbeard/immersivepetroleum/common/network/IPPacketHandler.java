package flaxbeard.immersivepetroleum.common.network;

import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;
import flaxbeard.immersivepetroleum.ImmersivePetroleum;

public class IPPacketHandler
{
	public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(ImmersivePetroleum.MODID);
	
	public static void preInit()
	{	
		INSTANCE.registerMessage(CloseBookPacket.Handler.class, CloseBookPacket.class, 0, Side.SERVER);
		INSTANCE.registerMessage(RotateSchematicPacket.Handler.class, RotateSchematicPacket.class, 1, Side.SERVER);
		INSTANCE.registerMessage(MessageReservoirListSync.Handler.class, MessageReservoirListSync.class, 2, Side.CLIENT);
		INSTANCE.registerMessage(ConsumeBoatFuelPacket.Handler.class, ConsumeBoatFuelPacket.class, 3, Side.SERVER);
	}
}
