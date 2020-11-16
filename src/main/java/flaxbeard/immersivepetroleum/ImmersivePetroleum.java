package flaxbeard.immersivepetroleum;

import java.util.HashMap;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeModContainer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

import blusunrize.immersiveengineering.common.Config;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartedEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import flaxbeard.immersivepetroleum.api.crafting.DistillationRecipe;
import flaxbeard.immersivepetroleum.api.crafting.PumpjackHandler;
import flaxbeard.immersivepetroleum.api.crafting.PumpjackHandler.ReservoirType;
import flaxbeard.immersivepetroleum.api.energy.FuelHandler;
import flaxbeard.immersivepetroleum.common.CommonProxy;
import flaxbeard.immersivepetroleum.common.Config.IPConfig;
import flaxbeard.immersivepetroleum.common.EventHandler;
import flaxbeard.immersivepetroleum.common.IPContent;
import flaxbeard.immersivepetroleum.common.IPSaveData;
import flaxbeard.immersivepetroleum.common.network.IPPacketHandler;
import flaxbeard.immersivepetroleum.common.util.CommandHandler;

@Mod(modid = ImmersivePetroleum.MODID, version = ImmersivePetroleum.VERSION, dependencies = "required-after:immersiveengineering;")
public class ImmersivePetroleum
{
	public static final String MODID = "immersivepetroleum";
	public static final String VERSION = "@VERSION@";
	
	@SidedProxy(clientSide="flaxbeard.immersivepetroleum.client.ClientProxy", serverSide="flaxbeard.immersivepetroleum.common.CommonProxy")
	public static CommonProxy proxy;
	
	@Instance(MODID)
	public static ImmersivePetroleum INSTANCE;
	
	static
	{
		FluidRegistry.enableUniversalBucket();
	}
		
	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		IPContent.preInit();
		proxy.preInit();
		proxy.preInitEnd();
		
		IPPacketHandler.preInit();
	}
	
	@Mod.EventHandler
	public void init(FMLInitializationEvent event)
	{
		DistillationRecipe.energyModifier = IPConfig.Refining.distillationTower_energyModifier;
		DistillationRecipe.timeModifier = IPConfig.Refining.distillationTower_timeModifier;
		
		PumpjackHandler.oilChance = IPConfig.Extraction.reservoir_chance;
		
		Config.manual_int.put("distillationTower_operationCost", (int) (2048 * IPConfig.Refining.distillationTower_energyModifier));
		Config.manual_int.put("pumpjack_consumption", IPConfig.Extraction.pumpjack_consumption);
		Config.manual_int.put("pumpjack_speed", IPConfig.Extraction.pumpjack_speed);

		int oil_min = 1000000;
		int oil_max = 5000000;
		for (ReservoirType type : PumpjackHandler.reservoirList.keySet())
		{
			if (type.name.equals("oil"))
			{
				oil_min = type.minSize;
				oil_max = type.maxSize;
				break;
			}
		}
		Config.manual_int.put("pumpjack_days", (((oil_max + oil_min) / 2) + oil_min) / (IPConfig.Extraction.pumpjack_speed * 24000));
		Config.manual_double.put("autoLubricant_speedup", 1.25);

		IPContent.init();
		
		
		HashMap<String, Integer> map = FuelHandler.getFuelFluxesPerTick();
		if (map.size() > 0 && map.containsKey("gasoline"))
		{
			Config.manual_int.put("portableGenerator_flux", map.get("gasoline"));

		}
		else
		{
			Config.manual_int.put("portableGenerator_flux", -1);
		}
		
		NetworkRegistry.INSTANCE.registerGuiHandler(INSTANCE, proxy);
		proxy.init();
		
		MinecraftForge.EVENT_BUS.register(new EventHandler());
	}
	
	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent event)
	{
		proxy.postInit();
		PumpjackHandler.recalculateChances(true);
	}
	
	

	public static <T extends IForgeRegistryEntry<?>> T register(T object, String name)
	{
		return registerByFullName(object, MODID+":"+name);
	}
	public static <T extends IForgeRegistryEntry<?>> T registerByFullName(T object, String name)
	{
		object.setRegistryName(new ResourceLocation(name));
		return GameRegistry.register(object);
	}
	public static Block registerBlockByFullName(Block block, ItemBlock itemBlock, String name)
	{
		block = registerByFullName(block, name);
		registerByFullName(itemBlock, name);
		return block;
	}
	public static Block registerBlockByFullName(Block block, Class<? extends ItemBlock> itemBlock, String name)
	{
		try{
			return registerBlockByFullName(block, itemBlock.getConstructor(Block.class).newInstance(block), name);
		}catch(Exception e){e.printStackTrace();}
		return null;
	}
	public static Block registerBlock(Block block, Class<? extends ItemBlock> itemBlock, String name)
	{
		try{
			return registerBlockByFullName(block, itemBlock.getConstructor(Block.class).newInstance(block), MODID+":"+name);
		}catch(Exception e){e.printStackTrace();}
		return null;
	}
	
	public static CreativeTabs creativeTab = new CreativeTabs(MODID)
	{
		@Override
		public Item getTabIconItem()
		{
			return null;
		}
		
		@Override
		public ItemStack getIconItemStack()
		{
			UniversalBucket bucket = ForgeModContainer.getInstance().universalBucket;
			ItemStack stack = new ItemStack(bucket);
            FluidStack fs = new FluidStack(IPContent.fluidCrudeOil, bucket.getCapacity());
			if (bucket.fill(stack, fs, true) == fs.amount)
			{
				return stack;
			}
			
			return new ItemStack(IPContent.blockFluidDiesel,1,0);
		}
		
		@Override
		@SideOnly(Side.CLIENT)
		public void displayAllRelevantItems(List<ItemStack> list)
		{
			UniversalBucket bucket = ForgeModContainer.getInstance().universalBucket;
			ItemStack stack = new ItemStack(bucket);
            FluidStack fs = new FluidStack(IPContent.fluidCrudeOil, bucket.getCapacity());
			if (bucket.fill(stack, fs, true) == fs.amount)
			{
				list.add(stack);
			}
			
			stack = new ItemStack(bucket);
            fs = new FluidStack(IPContent.fluidDiesel, bucket.getCapacity());
			if (bucket.fill(stack, fs, true) == fs.amount)
			{
				list.add(stack);
			}
			
			stack = new ItemStack(bucket);
            fs = new FluidStack(IPContent.fluidLubricant, bucket.getCapacity());
			if (bucket.fill(stack, fs, true) == fs.amount)
			{
				list.add(stack);
			}
			
			stack = new ItemStack(bucket);
            fs = new FluidStack(IPContent.fluidGasoline, bucket.getCapacity());
			if (bucket.fill(stack, fs, true) == fs.amount)
			{
				list.add(stack);
			}
			
			super.displayAllRelevantItems(list);
		}
	};
	
	@Mod.EventHandler
	public void serverStarted(FMLServerStartedEvent event)
	{
		if(FMLCommonHandler.instance().getEffectiveSide() == Side.SERVER)
		{
			World world = FMLCommonHandler.instance().getMinecraftServerInstance().getEntityWorld();
			if(!world.isRemote)
			{
				IPSaveData worldData = (IPSaveData) world.loadItemData(IPSaveData.class, IPSaveData.dataName);
				if(worldData == null)
				{
					worldData = new IPSaveData(IPSaveData.dataName);
					world.setItemData(IPSaveData.dataName, worldData);
				}
				IPSaveData.setInstance(world.provider.getDimension(), worldData);
			}
		}
	}
	
	@Mod.EventHandler
	public void serverStarting(FMLServerStartingEvent event)
	{
		event.registerServerCommand(new CommandHandler());
	}

}
