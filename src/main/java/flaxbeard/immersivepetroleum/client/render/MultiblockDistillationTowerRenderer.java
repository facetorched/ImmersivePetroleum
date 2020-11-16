package flaxbeard.immersivepetroleum.client.render;

import net.minecraft.client.Minecraft;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntityBeacon;
import net.minecraft.util.EnumFacing;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL14;
import org.lwjgl.opengl.GL44;

import blusunrize.immersiveengineering.client.ClientUtils;
import flaxbeard.immersivepetroleum.client.model.ModelDistillationTower;
import flaxbeard.immersivepetroleum.common.blocks.metal.TileEntityDistillationTower;

public class MultiblockDistillationTowerRenderer extends TileEntitySpecialRenderer<TileEntityDistillationTower.TileEntityDistillationTowerParent>
{
	private static ModelDistillationTower model = new ModelDistillationTower(false);
	private static ModelDistillationTower modelM = new ModelDistillationTower(true);

	private static String texture = "immersivepetroleum:textures/models/distillation_tower.png";
	private static String textureOn = "immersivepetroleum:textures/models/furnace_hot.png";
	private static String textureM = "immersivepetroleum:textures/models/distillation_tower_m.png";
	private static String textureOnM = "immersivepetroleum:textures/models/furnace_hot_m.png";

	@Override
	public boolean isGlobalRenderer(TileEntityDistillationTower.TileEntityDistillationTowerParent te)
    {
        return true;
    }
	
	@Override
	public void renderTileEntityAt(TileEntityDistillationTower.TileEntityDistillationTowerParent te, double x, double y, double z, float partialTicks, int destroyStage)
	{
		if (te != null)
		{
			GL11.glPushMatrix();
			GL11.glTranslated(x, y - 1, z);


			
			EnumFacing rotation = te.facing;
			if (rotation == EnumFacing.NORTH)
			{
				GL11.glRotatef(90F, 0, 1, 0);
				GL11.glTranslated(-1, 0, 0);
			}
			else if (rotation == EnumFacing.WEST)
			{
				GL11.glRotatef(180F, 0, 1, 0);
				GL11.glTranslated(-1, 0, -1);
			}
			else if (rotation == EnumFacing.SOUTH)
			{
				GL11.glRotatef(270F, 0, 1, 0);
				GL11.glTranslated(0, 0, -1);
			}
			GL11.glTranslated(-3, 0, 0);
			if (te.mirrored)
			{
				GL11.glTranslated(0, 0, -3);
			}
			
			float ticks = Minecraft.getMinecraft().thePlayer.ticksExisted + partialTicks;

			if (te.mirrored)
			{
				ClientUtils.bindTexture(textureM);
				modelM.render(null, 0, 0, 0, 0, 0, 0.0625F);
				ClientUtils.bindTexture(te.shouldRenderAsActive() ? textureOnM : textureM);
				modelM.renderFurnace(null, 0, 0, 0, 0, 0, 0.0625F);
			}
			else
			{
				ClientUtils.bindTexture(texture);
				model.render(null, 0, 0, 0, 0, 0, 0.0625F);
				ClientUtils.bindTexture(te.shouldRenderAsActive() ? textureOn : texture);
				model.renderFurnace(null, 0, 0, 0, 0, 0, 0.0625F);
			}
			
			GL11.glPopMatrix();
			
		}
	}

}
