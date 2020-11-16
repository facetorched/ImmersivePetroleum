package flaxbeard.immersivepetroleum.client;


import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import flaxbeard.immersivepetroleum.ImmersivePetroleum;
import flaxbeard.immersivepetroleum.common.IPContent;
import net.minecraftforge.client.event.TextureStitchEvent;

public class ClientEventHandler{
	@SideOnly(Side.CLIENT)
	@SubscribeEvent()
	public void textureStich(TextureStitchEvent.Pre event)
	{
		IPContent.fluidCrudeOil.setIcons(event.map.registerIcon(ImmersivePetroleum.MODID + ":blocks/fluid/oil_still"), event.map.registerIcon(ImmersivePetroleum.MODID + ":blocks/fluid/oil_flow"));
		IPContent.fluidDiesel.setIcons(event.map.registerIcon(ImmersivePetroleum.MODID + ":blocks/fluid/diesel_still"), event.map.registerIcon(":blocks/fluid/diesel_flow"));
		IPContent.fluidGasoline.setIcons(event.map.registerIcon(ImmersivePetroleum.MODID + ":blocks/fluid/gasoline_still"), event.map.registerIcon(ImmersivePetroleum.MODID + ":blocks/fluid/gasoline_flow"));
		IPContent.fluidLubricant.setIcons(event.map.registerIcon(ImmersivePetroleum.MODID + ":blocks/fluid/lubricant_still"), event.map.registerIcon(ImmersivePetroleum.MODID + ":blocks/fluid/lubricant_flow"));
	}
}
