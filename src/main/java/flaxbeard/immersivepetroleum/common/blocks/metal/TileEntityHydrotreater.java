package flaxbeard.immersivepetroleum.common.blocks.metal;

import blusunrize.immersiveengineering.api.crafting.IMultiblockRecipe;
import blusunrize.immersiveengineering.common.Config.IEConfig;
import blusunrize.immersiveengineering.common.IEContent;
import blusunrize.immersiveengineering.common.blocks.IEBlockInterfaces;
import blusunrize.immersiveengineering.common.blocks.IEBlockInterfaces.IAdvancedCollisionBounds;
import blusunrize.immersiveengineering.common.blocks.IEBlockInterfaces.IAdvancedSelectionBounds;
import blusunrize.immersiveengineering.common.blocks.IEBlockInterfaces.IGuiTile;
import blusunrize.immersiveengineering.common.blocks.metal.BlockTypes_MetalDevice1;
import blusunrize.immersiveengineering.common.blocks.metal.TileEntityMultiblockMetal;
import blusunrize.immersiveengineering.common.blocks.metal.TileEntitySqueezer;
import blusunrize.immersiveengineering.common.util.Utils;
import com.google.common.collect.Lists;
import flaxbeard.immersivepetroleum.api.crafting.PumpjackHandler;
import flaxbeard.immersivepetroleum.common.Config.IPConfig;
import flaxbeard.immersivepetroleum.common.blocks.multiblocks.MultiblockHydrotreater;
import flaxbeard.immersivepetroleum.common.blocks.multiblocks.MultiblockPumpjack;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraftforge.fluids.*;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.List;

public class TileEntityHydrotreater extends TileEntityMultiblockMetal<TileEntityHydrotreater, IMultiblockRecipe> implements IAdvancedSelectionBounds,IAdvancedCollisionBounds, IGuiTile, IEBlockInterfaces.IPlayerInteraction
{

	public static class TileEntityHydrotreaterParent extends TileEntityHydrotreater
	{
		/*@SideOnly(Side.CLIENT)
		@Override
		public AxisAlignedBB getRenderBoundingBox()
		{
			BlockPos nullPos = this.getPos();
			return new AxisAlignedBB(nullPos.offset(facing, -2).offset(mirrored?facing.rotateYCCW():facing.rotateY(), -1).down(1), nullPos.offset(facing, 5).offset(mirrored?facing.rotateYCCW():facing.rotateY(), 2).up(3));
		}*/

		@Override
		public boolean isDummy()
		{
			return false;
		}

		@Override
		@SideOnly(Side.CLIENT)
		public double getMaxRenderDistanceSquared()
		{
			return super.getMaxRenderDistanceSquared()* IEConfig.increasedTileRenderdistance;
		}
	}

	public TileEntityHydrotreater()
	{
		super(MultiblockHydrotreater.instance, new int[]{4, 4, 3}, 16000, true);
	}

	public FluidTank[] tanks = new FluidTank[] { new FluidTank(24000), new FluidTank(24000) };

	@Override
	public void readCustomNBT(NBTTagCompound nbt, boolean descPacket)
	{
		super.readCustomNBT(nbt, descPacket);
		tanks[0].readFromNBT(nbt.getCompoundTag("tank0"));
		tanks[1].readFromNBT(nbt.getCompoundTag("tank1"));
	}

	@Override
	public void writeCustomNBT(NBTTagCompound nbt, boolean descPacket)
	{
		super.writeCustomNBT(nbt, descPacket);
		nbt.setTag("tank0", tanks[0].writeToNBT(new NBTTagCompound()));
		nbt.setTag("tank1", tanks[1].writeToNBT(new NBTTagCompound()));
	}
	
	@Override
	public void update()
	{
	}

	@Override
	public float[] getBlockBounds()
	{
		return new float[]{0,0,0, 0,0,0};
	}

	@Override
	public List<AxisAlignedBB> getAdvancedSelectionBounds()
	{
		List list = new ArrayList<AxisAlignedBB>();
		list.add(new AxisAlignedBB(0, 0, 0, 1, 1, 1).offset(getPos().getX(),getPos().getY(),getPos().getZ()));
		return list;
	}

	@Override
	public boolean isOverrideBox(AxisAlignedBB box, EntityPlayer player, RayTraceResult mop, ArrayList<AxisAlignedBB> list)
	{
		return false;
	}

	@Override
	public List<AxisAlignedBB> getAdvancedColisionBounds()
	{
		List list = new ArrayList<AxisAlignedBB>();
		return getAdvancedSelectionBounds();
	}

	@Override
	public int[] getEnergyPos()
	{
		return new int[0];
	}

	@Override
	public int[] getRedstonePos()
	{
		return new int[0];
	}

	@Override
	public boolean isInWorldProcessingMachine()
	{
		return false;
	}
	
	@Override
	public void doProcessOutput(ItemStack output)
	{
	}
	
	@Override
	public void doProcessFluidOutput(FluidStack output)
	{
	}
	
	@Override
	public void onProcessFinish(MultiblockProcess<IMultiblockRecipe> process)
	{
	}
	
	@Override
	public int getMaxProcessPerTick()
	{
		return 1;
	}
	@Override
	public int getProcessQueueMaxLength()
	{
		return 1;
	}
	
	@Override
	public float getMinProcessDistance(MultiblockProcess<IMultiblockRecipe> process)
	{
		return 0;
	}

	@Override
	public boolean isStackValid(int slot, ItemStack stack)
	{
		return true;
	}

	@Override
	public int getSlotLimit(int slot)
	{
		return 64;
	}

	@Override
	public int[] getOutputSlots()
	{
		return null ;
	}

	@Override
	public int[] getOutputTanks()
	{
		return new int[] { 1 };
	}


	@Override
	public void doGraphicalUpdates(int slot)
	{
		this.markDirty();
		this.markContainingBlockForUpdate(null);
	}

	@Override
	public IMultiblockRecipe findRecipeForInsertion(ItemStack inserting)
	{
		return null;
	}

	@Override
	protected IMultiblockRecipe readRecipeFromNBT(NBTTagCompound tag)
	{
		return null;
	}

	@Override
	public boolean canOpenGui()
	{
		return true;
	}

	@Override
	public int getGuiID()
	{
		return 0;
	}

	@Override
	public TileEntity getGuiMaster()
	{
		return master();
	}

	@Override
	public NonNullList<ItemStack> getInventory()
	{
		return null;
	}
	
	@Override
	public IFluidTank[] getInternalTanks()
	{
		return tanks;
	}

	@Override
	public boolean additionalCanProcessCheck(MultiblockProcess<IMultiblockRecipe> process)
	{
		return false;
	}

	@Override
	protected IFluidTank[] getAccessibleFluidTanks(EnumFacing side)
	{
		TileEntityHydrotreater master = master();
		if (master != null && pos == 2 && (side == null || side == (mirrored ? facing.rotateYCCW() : facing.rotateY())))
		{
			return new FluidTank[] { master.tanks[0] };
		}
		else if (master != null && pos == 6 && (side == null || side == (mirrored ? facing.rotateY() : facing.rotateYCCW())))
		{
			return new FluidTank[] { master.tanks[0] };
		}
		return new FluidTank[0];
	}

	@Override
	protected boolean canFillTankFrom(int iTank, EnumFacing side, FluidStack resource)
	{
		return iTank == 0;
	}

	@Override
	protected boolean canDrainTankFrom(int iTank, EnumFacing side)
	{
		return iTank == 1;
	}
	
	@Override
	public boolean isDummy()
	{
		return true;
	}
	
	@Override
	public TileEntityHydrotreater master()
	{
		if (offset[0] == 0 && offset[1] == 0 && offset[2] == 0)
		{
			return this;
		}
		TileEntity te = world.getTileEntity(getPos().add(-offset[0], -offset[1], -offset[2]));
		return this.getClass().isInstance(te) ? (TileEntityHydrotreater) te : null;
	}
	
	@Override
	public TileEntityHydrotreater getTileForPos(int targetPos)
	{
		BlockPos target = getBlockPosForPos(targetPos);
		TileEntity tile = world.getTileEntity(target);
		return tile instanceof TileEntityHydrotreater ? (TileEntityHydrotreater) tile : null;
	}

	@Override
	public boolean interact(EnumFacing side, EntityPlayer player, EnumHand hand, ItemStack heldItem, float hitX, float hitY, float hitZ)
	{
		System.out.println(pos);
		return false;
	}
}