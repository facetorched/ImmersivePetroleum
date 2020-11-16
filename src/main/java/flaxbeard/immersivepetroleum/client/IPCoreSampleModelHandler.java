package flaxbeard.immersivepetroleum.client;


import blusunrize.immersiveengineering.common.IEContent;
import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import flaxbeard.immersivepetroleum.client.model.ModelCoresampleExtended;
import net.minecraft.util.ResourceLocation;

public class IPCoreSampleModelHandler
{
	public static IPCoreSampleModelHandler instance = new IPCoreSampleModelHandler();

	@SubscribeEvent(priority=EventPriority.LOWEST)
	public void onModelBakeEvent(ModelBakeEvent event)
	{
		
		ModelResourceLocation mLoc = new ModelResourceLocation(new ResourceLocation("immersiveengineering", IEContent..itemName), "inventory");
		event.getModelRegistry().putObject(mLoc, new ModelCoresampleExtended());
		
	}
}
