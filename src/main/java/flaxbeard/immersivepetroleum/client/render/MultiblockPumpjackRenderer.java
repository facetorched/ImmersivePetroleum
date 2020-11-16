package flaxbeard.immersivepetroleum.client.render;

import org.lwjgl.opengl.GL11;

import blusunrize.immersiveengineering.client.ClientUtils;
import flaxbeard.immersivepetroleum.client.model.ModelPumpjack;
import flaxbeard.immersivepetroleum.common.blocks.metal.TileEntityPumpjack;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.util.EnumFacing;

public class MultiblockPumpjackRenderer extends TileEntitySpecialRenderer<TileEntityPumpjack.TileEntityPumpjackParent>
{
	private static ModelPumpjack model = new ModelPumpjack(false);
	private static ModelPumpjack modelM = new ModelPumpjack(true);

	private static String texture = "immersivepetroleum:textures/models/pumpjack.png";

	@Override
	public void renderTileEntityAt(TileEntityPumpjack.TileEntityPumpjackParent te, double x, double y, double z, float partialTicks, int destroyStage)
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
			GL11.glTranslated(-1, 0, -1);

			if (te.mirrored)
			{
			}
			
			ClientUtils.bindTexture(texture);
			
			float ticks = te.activeTicks + (te.wasActive ? partialTicks : 0);;
			model.ticks = modelM.ticks = 1.5F * ticks;

			if (te.mirrored)
			{
				modelM.render(null, 0, 0, 0, 0, 0, 0.0625F);
			}
			else
			{
				model.render(null, 0, 0, 0, 0, 0, 0.0625F);
			}
			GL11.glPopMatrix();
			
		}
	}

}
