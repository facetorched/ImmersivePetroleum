package flaxbeard.immersivepetroleum.api.event;

import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import cpw.mods.fml.common.eventhandler.Cancelable;
import cpw.mods.fml.common.eventhandler.Event;
import blusunrize.immersiveengineering.api.MultiblockHandler.IMultiblock;

@Cancelable
public class SchematicPlaceBlockEvent extends Event
{
	private IBlockState state;
	private World world;
	private int index;
	private IMultiblock multiblock;
	private int rotate;
	private int l;
	private int h;
	private int w; 
	
	public SchematicPlaceBlockEvent(IMultiblock multiblock, int index, IBlockState state, World world, int rotate, int l, int h, int w)
	{
		super();
		this.state = state;
		this.world = world;
		this.multiblock = multiblock;
		this.index = index;
		this.rotate = rotate;
		this.l = l;
		this.h = h;
		this.w = w;
	}
	
	public World getWorld()
	{
		return world;
	}
	
	public IBlockState getBlockState()
	{
		return state;
	}
	
	public int getIndex()
	{
		return index;
	}
	
	public IMultiblock getMultiblock()
	{
		return multiblock;
	}
	
	public void setBlockState(IBlockState state)
	{
		this.state = state;
	}
	
	public EnumFacing getRotate()
	{
		switch (rotate)
		{
			case 0:
				return EnumFacing.EAST;
			case 1:
				return EnumFacing.NORTH;
			case 2:
				return EnumFacing.WEST;
			default:
				return EnumFacing.SOUTH;
		}
	}
	
	
	public int getL()
	{
		return l;
	}
	
	public int getH()
	{
		return h;
	}
	
	public int getW()
	{
		return w;
	}
}
