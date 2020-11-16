package flaxbeard.immersivepetroleum.client.render;

import org.lwjgl.opengl.GL11;

import blusunrize.immersiveengineering.client.ClientUtils;
import flaxbeard.immersivepetroleum.api.crafting.LubricatedHandler;
import flaxbeard.immersivepetroleum.api.crafting.LubricatedHandler.ILubricationHandler;
import flaxbeard.immersivepetroleum.client.model.ModelLubricantPipes;
import flaxbeard.immersivepetroleum.client.model.ModelLubricantPipes.Base;
import flaxbeard.immersivepetroleum.common.blocks.metal.TileEntityAutoLubricator;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fluids.FluidStack;

public class TileAutoLubricatorRenderer extends TileEntitySpecialRenderer<TileEntityAutoLubricator>
{

	private static Base base = new ModelLubricantPipes.Base();

	@Override
	public boolean isGlobalRenderer(TileEntityAutoLubricator te)
    {
        return true;
    }
	
	@Override
	public void renderTileEntityAt(TileEntityAutoLubricator te, double x, double y, double z, float partialTicks, int destroyStage)
	{
		if (te == null)
		{
			
			GL11.glPushMatrix();
			GL11.glTranslated(x, y, z);
			//GL11.glEnable(GL11.GL_BLEND);
			//GL11.enableAlpha();
			
			
			GL11.glScalef(0.65F, 0.65F, 0.65F);
			GL11.glTranslatef(0.25F, -0.1F, 0.25F);
			
			ClientUtils.bindTexture("immersivepetroleum:textures/models/lubricator.png");
			base.render(null, 0, 0, 0, 0, 0, 0.0625F);
			
			//GL11.glEnable(GL11.GL_BLEND);
			//GL11.enableAlpha();
			base.renderTank(null, 0, 0, 0, 0, 0, 0.0625F);
			
			//base.renderPlunger(null, 0, 0, 0, 0, 0, 0.0625F);
			GL11.glPopMatrix();
			return;
		}
		
		if (te.isDummy() || !te.getWorld().isBlockLoaded(te.getPos(), false))
			return;
	
		int pass = net.minecraftforge.client.MinecraftForgeClient.getRenderPass();
		
		ClientUtils.bindAtlas(1);
		GL11.glPushMatrix();
		GL11.glTranslated(x+.5, y+.5, z+.5);

		
		float height = 16;
		FluidStack fs = te.tank.getFluid();
		float level = 0;
		if (fs != null)
		{
			level = fs.amount / (float)te.tank.getCapacity();
		}
		float yOffset = (1 - level) * height * -1F/16F;
		float scale = .0625f;
		//GL11.glEnable(GL11.GL_BLEND);
		//GL11.enableAlpha();
		if (pass == 1 && level > 0)
		{
			
			RenderHelper.disableStandardItemLighting();

			GL11.glPushMatrix();
			//ShaderUtil.alpha_static(0.25f, 1);
			GL11.glTranslated(-4F/16F, 6/16F, -4F/16F);
			GL11.glScalef(scale, scale, scale);
			float h = level * height;
			ClientUtils.drawRepeatedFluidIcon(fs.getFluid(), 0, 0, 8, h);
			GL11.glRotatef(90,0,1,0);
			GL11.glTranslated(-7.98, 0, 0);
			ClientUtils.drawRepeatedFluidIcon(fs.getFluid(), 0, 0, 8, h);
			GL11.glRotatef(90,0,1,0);
			GL11.glTranslated(-7.98,0,0);
			ClientUtils.drawRepeatedFluidIcon(fs.getFluid(), 0, 0, 8, h);
			GL11.glRotatef(90,0,1,0);
			GL11.glTranslated(-7.98,0,0);
			ClientUtils.drawRepeatedFluidIcon(fs.getFluid(), 0,0, 8,h);

			GL11.glRotatef(90,1,0,0);
			GL11.glTranslated(0,0,-h);
			ClientUtils.drawRepeatedFluidIcon(fs.getFluid(), 0,0, 8,8);
			GL11.glRotatef(180, 1, 0, 0);
			GL11.glTranslated(0, -9, -h);
			ClientUtils.drawRepeatedFluidIcon(fs.getFluid(), 0,0, 8,8);

			GL11.glScalef(1/scale, 1/scale, 1/scale);
			GL11.glTranslated(0,-1,-1);
			GL11.glPopMatrix();
				//ShaderUtil.releaseShader();
			
			RenderHelper.enableStandardItemLighting();

		}

		GL11.glPopMatrix();
		
		if (pass == 1)
		{
			
			GL11.glPushMatrix();
			GL11.glTranslated(x, y, z);
			GL11.glEnable(GL11.GL_BLEND);
			GL11.glEnable(GL11.GL_ALPHA);
			
			ClientUtils.bindTexture("immersivepetroleum:textures/models/lubricator.png");
			base.renderTank(null, 0, 0, 0, 0, 0, 0.0625F);
			GL11.glPopMatrix();
		}
		
		

		if (pass == 0)
		{
			GL11.glEnable(GL11.GL_BLEND);
			GL11.glEnable(GL11.GL_ALPHA);
			GL11.glPushMatrix();
			GL11.glTranslated(x, y, z);
			
			BlockPos target = te.getPos().offset(te.getFacing());
			TileEntity test = te.getWorld().getTileEntity(target);

			ILubricationHandler handler = LubricatedHandler.getHandlerForTile(test);
			if (handler != null)
			{
				TileEntity master = handler.isPlacedCorrectly(te.getWorld(), te, te.getFacing());
				if (master != null)
				{
					handler.renderPipes(te.getWorld(), te, te.getFacing(), master);
				}
			}
			GL11.glPopMatrix();

			
			
			GL11.glPushMatrix();
			GL11.glTranslated(x + .5F, y + .5F, z + .5F);
			GL11.glEnable(GL11.GL_ALPHA);
			
			int rotate = 0;
			if (te.getFacing() == EnumFacing.NORTH) rotate = 1;
			if (te.getFacing() == EnumFacing.SOUTH) rotate = 3;
			if (te.getFacing() == EnumFacing.WEST) rotate = 2;
			GL11.glRotatef(rotate * 90, 0, 1, 0);
			GL11.glTranslated(-.5F, -.5F, -.5F);
			ClientUtils.bindTexture("immersivepetroleum:textures/models/lubricator.png");
			base.render(null, 0, 0, 0, 0, 0, 0.0625F);
			GL11.glTranslated(0, yOffset, 0);
			//base.renderPlunger(null, 0, 0, 0, 0, 0, 0.0625F);
			GL11.glPopMatrix();
		}
		GL11.glDisable(GL11.GL_BLEND);


	}
}