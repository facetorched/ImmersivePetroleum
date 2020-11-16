package flaxbeard.immersivepetroleum.common.items;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import flaxbeard.immersivepetroleum.common.util.BlockPos;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import blusunrize.immersiveengineering.common.items.IEItemInterfaces.IColouredItem;
import flaxbeard.immersivepetroleum.ImmersivePetroleum;
import flaxbeard.immersivepetroleum.common.IPContent;
import flaxbeard.immersivepetroleum.common.blocks.multiblocks.MultiblockPumpjack;

public class ItemIPBase extends Item implements IColouredItem
{
	public String itemName;
	protected String[] subNames;
	boolean[] isMetaHidden;
	public boolean registerSubModels=true;

	public ItemIPBase(String name, int stackSize, String... subNames)
	{
		this.setUnlocalizedName(ImmersivePetroleum.MODID+"."+name);
		this.setHasSubtypes(subNames!=null&&subNames.length>0);
		this.setCreativeTab(ImmersivePetroleum.creativeTab);
		this.setMaxStackSize(stackSize);
		this.itemName = name;
		this.subNames = subNames!=null&&subNames.length>0?subNames:null;
		this.isMetaHidden = new boolean[this.subNames!=null?this.subNames.length:1];
		ImmersivePetroleum.register(this, name);
		IPContent.registeredIPItems.add(this);
	}

	public String[] getSubNames()
	{
		return subNames;
	}
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item item, CreativeTabs tab, List list)
	{
		if(getSubNames()!=null)
		{
			for(int i=0;i<getSubNames().length;i++)
				if(!isMetaHidden(i))
					list.add(new ItemStack(this,1,i));
		}
		else
			list.add(new ItemStack(this));

	}
	@Override
	public String getUnlocalizedName(ItemStack stack)
	{
		if(getSubNames()!=null)
		{
			String subName = stack.getItemDamage()<getSubNames().length?getSubNames()[stack.getItemDamage()]:"";
			return this.getUnlocalizedName()+"."+subName;
		}
		return this.getUnlocalizedName();
	}

	public ItemIPBase setMetaHidden(int... meta)
	{
		for(int i : meta)
			if(i>=0 && i<this.isMetaHidden.length)
				this.isMetaHidden[i] = true;
		return this;
	}
	public ItemIPBase setMetaUnhidden(int... meta)
	{
		for(int i : meta)
			if(i>=0 && i<this.isMetaHidden.length)
				this.isMetaHidden[i] = false;
		return this;
	}
	public boolean isMetaHidden(int meta)
	{
		return this.isMetaHidden[Math.max(0, Math.min(meta, this.isMetaHidden.length-1))];
	}
	
	public ItemIPBase setRegisterSubModels(boolean register)
	{
		this.registerSubModels = register;
		return this;
	}

}
