package flaxbeard.immersivepetroleum.client.render;

import org.lwjgl.opengl.GL11;

import blusunrize.immersiveengineering.client.ClientUtils;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import flaxbeard.immersivepetroleum.client.model.ModelSpeedboat;
import flaxbeard.immersivepetroleum.common.entity.EntitySpeedboat;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

@SideOnly(Side.CLIENT)
public class RenderSpeedboat extends Render<EntitySpeedboat>
{
	private static String texture = "immersivepetroleum:textures/models/boat_motor.png";
	private static String textureArmor = "immersivepetroleum:textures/models/boat_motor_armor.png";
	/** instance of ModelBoat for rendering */
	protected ModelSpeedboat modelBoat = new ModelSpeedboat();

	public RenderSpeedboat(RenderManager renderManagerIn)
	{
		super(renderManagerIn);
		this.shadowSize = 0.5F;
	}

	/**
	 * Renders the desired {@code T} type Entity.
	 */
	public void doRender(EntitySpeedboat entity, double x, double y, double z, float entityYaw, float partialTicks)
	{
		
		GL11.glPushMatrix();
		this.setupTranslation(x, y, z);
		

		this.setupRotation(entity, entityYaw, partialTicks);
		ClientUtils.bindTexture(entity.isFireproof ? textureArmor : texture);
		if (entity.inLava)
		{
			GL11.glTranslated(0, -3.9F/16F, 0);
		}

		if (this.renderOutlines)
		{
			//GL11.enableColorMaterial();
			//GL11.enableOutlineMode(this.getTeamColor(entity));
		}

	

		this.modelBoat.render(entity, partialTicks, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);
	

		if (entity.hasIcebreaker)
		{
			ClientUtils.bindTexture(textureArmor);
			this.modelBoat.renderIcebreaker(entity, partialTicks, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);
		}
		
		if (entity.hasRudders)
		{
			ClientUtils.bindTexture(textureArmor);
			this.modelBoat.renderRudders(entity, partialTicks, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);
		}
		
		if (entity.hasTank)
		{
			ClientUtils.bindTexture(textureArmor);
			this.modelBoat.renderTank(entity, partialTicks, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);
		}
		
		if (entity.hasPaddles)
		{
			ClientUtils.bindTexture(texture);
			this.modelBoat.renderPaddles(entity, partialTicks, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);
		}
		
		
		if (this.renderOutlines)
		{
			//GL11.disableOutlineMode();
			//GL11.disableColorMaterial();
		}
			
		
		GL11.glPopMatrix();
		
		super.doRender(entity, x, y, z, entityYaw, partialTicks);
	}

	public void setupRotation(EntitySpeedboat boat, float p_188311_2_, float p_188311_3_)
	{
		GL11.glRotatef(180.0F - p_188311_2_, 0.0F, 1.0F, 0.0F);
		float f = (float)boat.getTimeSinceHit() - p_188311_3_;
		float f1 = boat.getDamageTaken() - p_188311_3_;

		if (f1 < 0.0F)
		{
			f1 = 0.0F;
		}

		if (f > 0.0F)
		{
			GL11.glRotatef(MathHelper.sin(f) * f * f1 / 10.0F * (float)boat.getForwardDirection(), 1.0F, 0.0F, 0.0F);
		}
		
		if (boat.isBoosting)
		{
			GL11.glRotatef(3, 1, 0, 0);
		}

		GL11.glScalef(-1.0F, -1.0F, 1.0F);
	}

	public void setupTranslation(double p_188309_1_, double p_188309_3_, double p_188309_5_)
	{
		GL11.glTranslated((float)p_188309_1_, (float)p_188309_3_ + 0.375F, (float)p_188309_5_);
	}

	/**
	 * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
	 */

	public boolean isMultipass()
	{
		return true;
	}

	public void renderMultipass(EntitySpeedboat p_188300_1_, double p_188300_2_, double p_188300_4_, double p_188300_6_, float p_188300_8_, float p_188300_9_)
	{
		GL11.glPushMatrix();
		this.setupTranslation(p_188300_2_, p_188300_4_, p_188300_6_);
		this.setupRotation(p_188300_1_, p_188300_8_, p_188300_9_);

		ClientUtils.bindTexture(texture);

		modelBoat.renderMultipass(p_188300_1_, p_188300_9_, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);
		GL11.glPopMatrix();
	}

	@Override
	protected ResourceLocation getEntityTexture(EntitySpeedboat entity)
	{
		return new ResourceLocation(texture);
	}
}