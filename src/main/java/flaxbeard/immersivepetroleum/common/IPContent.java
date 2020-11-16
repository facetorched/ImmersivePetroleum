package flaxbeard.immersivepetroleum.common;

import java.util.ArrayList;

import blusunrize.immersiveengineering.api.MultiblockHandler;
import blusunrize.immersiveengineering.api.tool.ChemthrowerHandler;
import blusunrize.immersiveengineering.api.tool.ChemthrowerHandler.ChemthrowerEffect_Potion;
import blusunrize.immersiveengineering.common.IEContent;
import blusunrize.immersiveengineering.common.IERecipes;
import blusunrize.immersiveengineering.common.blocks.metal.BlockMetalDecoration;
import blusunrize.immersiveengineering.common.blocks.metal.BlockMetalDevices;
import blusunrize.immersiveengineering.common.blocks.metal.BlockMetalDevices2;
import blusunrize.immersiveengineering.common.util.IEPotions;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import flaxbeard.immersivepetroleum.ImmersivePetroleum;
import flaxbeard.immersivepetroleum.api.crafting.ItemOilCan;
import flaxbeard.immersivepetroleum.api.crafting.LubricantHandler;
import flaxbeard.immersivepetroleum.api.crafting.LubricatedHandler.LubricantEffect;
import flaxbeard.immersivepetroleum.common.Config.IPConfig;
import flaxbeard.immersivepetroleum.common.blocks.BlockIPBase;
import flaxbeard.immersivepetroleum.common.blocks.BlockIPFluid;
import flaxbeard.immersivepetroleum.common.blocks.BlockIPMetalDevice;
import flaxbeard.immersivepetroleum.common.blocks.BlockIPMetalMultiblocks;
import flaxbeard.immersivepetroleum.common.blocks.ItemBlockIPBase;
import flaxbeard.immersivepetroleum.common.blocks.metal.BlockTypes_Dummy;
import flaxbeard.immersivepetroleum.common.blocks.metal.BlockTypes_MetalDecoration0;
import flaxbeard.immersivepetroleum.common.blocks.metal.TileEntityAutoLubricator;
import flaxbeard.immersivepetroleum.common.blocks.metal.TileEntityDistillationTower;
import flaxbeard.immersivepetroleum.common.blocks.metal.TileEntityGasGenerator;
import flaxbeard.immersivepetroleum.common.blocks.metal.TileEntityPumpjack;
import flaxbeard.immersivepetroleum.common.blocks.multiblocks.MultiblockDistillationTower;
import flaxbeard.immersivepetroleum.common.blocks.multiblocks.MultiblockPumpjack;
import flaxbeard.immersivepetroleum.common.blocks.stone.BlockTypes_IPStoneDecoration;
import flaxbeard.immersivepetroleum.common.entity.EntitySpeedboat;
import flaxbeard.immersivepetroleum.common.items.ItemIPBase;
import flaxbeard.immersivepetroleum.common.items.ItemIPUpgrade;
import flaxbeard.immersivepetroleum.common.items.ItemProjector;
import flaxbeard.immersivepetroleum.common.items.ItemSpeedboat;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

public class IPContent
{
	public static ArrayList<Block> registeredIPBlocks = new ArrayList<Block>();

	public static BlockIPFluid blockFluidCrudeOil;
	public static BlockIPFluid blockFluidDiesel;
	public static BlockIPFluid blockFluidLubricant;
	public static BlockIPFluid blockFluidGasoline;

	public static BlockIPBase blockMetalMultiblock;
	
	public static BlockIPBase blockStoneDecoration;
	
	public static BlockIPBase blockMetalDevice;

	public static BlockIPBase blockDummy;

	
	public static ArrayList<Item> registeredIPItems = new ArrayList<Item>();

	public static Item itemMaterial;
	public static Item itemProjector;
	public static Item itemSpeedboat;
	public static Item itemUpgrades;
	public static Item itemOilCan;

	public static Fluid fluidCrudeOil;
	public static Fluid fluidDiesel;
	public static Fluid fluidLubricant;
	public static Fluid fluidGasoline;

	public static void preInit()
	{
		EntityRegistry.registerModEntity(EntitySpeedboat.class, "speedboat", 0, ImmersivePetroleum.INSTANCE, 80, 3, true);
		//EntityRegistry.registerModEntity(EntityBoatDrill.class, "boat_drill", 1, ImmersivePetroleum.INSTANCE, 80, 3, true);

		fluidCrudeOil = new Fluid("oil").setDensity(1000).setViscosity(2250);
		if(!FluidRegistry.registerFluid(fluidCrudeOil))
			fluidCrudeOil = FluidRegistry.getFluid("oil");
		//FluidRegistry.addBucketForFluid(fluidCrudeOil);

		fluidDiesel = new Fluid("diesel").setDensity(789).setViscosity(1750);
		if(!FluidRegistry.registerFluid(fluidDiesel))
			fluidDiesel = FluidRegistry.getFluid("diesel");
		//FluidRegistry.addBucketForFluid(fluidDiesel);
		
		fluidLubricant = new Fluid("lubricant").setDensity(925).setViscosity(1000);
		if(!FluidRegistry.registerFluid(fluidLubricant))
			fluidLubricant = FluidRegistry.getFluid("lubricant");
		//FluidRegistry.addBucketForFluid(fluidLubricant);
		
		fluidGasoline = new Fluid("gasoline").setDensity(789).setViscosity(1200);
		if(!FluidRegistry.registerFluid(fluidGasoline))
			fluidGasoline = FluidRegistry.getFluid("gasoline");
		//FluidRegistry.addBucketForFluid(fluidGasoline);
		
		blockFluidCrudeOil = new BlockIPFluid("fluid_crude_oil", fluidCrudeOil, Material.water).setFlammability(60, 200);
		blockFluidDiesel = new BlockIPFluid("fluid_diesel", fluidDiesel, Material.water).setFlammability(60, 200);
		blockFluidLubricant = new BlockIPFluid("fluid_lubricant", fluidLubricant, Material.water);
		blockFluidGasoline = new BlockIPFluid("fluid_gasoline", fluidGasoline, Material.water).setFlammability(60, 200);

		blockMetalMultiblock = new BlockIPMetalMultiblocks();
		
		blockMetalDevice = new BlockIPMetalDevice();

		blockStoneDecoration = (BlockIPBase)new BlockIPBase("stone_decoration", Material.rock, PropertyEnum.create("type", BlockTypes_IPStoneDecoration.class), ItemBlockIPBase.class).setHardness(2.0F).setResistance(10.0F);
		blockDummy = (BlockIPBase)new BlockIPBase("dummy", Material.rock, PropertyEnum.create("type", BlockTypes_Dummy.class), ItemBlockIPBase.class);

		itemMaterial = new ItemIPBase("material", 64,
				"bitumen");
		
		itemProjector = new ItemProjector("schematic");
		itemSpeedboat = new ItemSpeedboat("speedboat");
		
		itemUpgrades = new ItemIPUpgrade("upgrades");
		
		itemOilCan = new ItemOilCan("oil_can");

	}
	
	public static void init()
	{
		blockFluidCrudeOil.setPotionEffects(new PotionEffect(IEPotions.flammable.id,100,1));
		blockFluidDiesel.setPotionEffects(new PotionEffect(IEPotions.flammable.id,100,1));
		blockFluidLubricant.setPotionEffects(new PotionEffect(IEPotions.slippery.id,100,1));
		ChemthrowerHandler.registerEffect("lubricant", new LubricantEffect());
		ChemthrowerHandler.registerEffect("plantoil", new LubricantEffect());
		ChemthrowerHandler.registerEffect("gasoline", new ChemthrowerEffect_Potion(null,0, IEPotions.flammable,60,1));
		ChemthrowerHandler.registerFlammable("gasoline");
		ChemthrowerHandler.registerEffect("lubricant", new ChemthrowerEffect_Potion(null,0, IEPotions.slippery,60,1));
		
		registerTile(TileEntityDistillationTower.class);
		registerTile(TileEntityDistillationTower.TileEntityDistillationTowerParent.class);
		registerTile(TileEntityPumpjack.class);
		registerTile(TileEntityPumpjack.TileEntityPumpjackParent.class);
		registerTile(TileEntityAutoLubricator.class);
		registerTile(TileEntityGasGenerator.class);
//
//		DistillationRecipe.addRecipe(
//				new FluidStack[] { 
//					new FluidStack(fluidLubricant, 3),
//					new FluidStack(fluidDiesel, 9),
//					new FluidStack(fluidGasoline, 13)
//				},
//				new ItemStack(itemMaterial, 1, 0), new FluidStack(fluidCrudeOil, 25), 2048, 1, .07F);

		MultiblockHandler.registerMultiblock(MultiblockDistillationTower.instance);
		MultiblockHandler.registerMultiblock(MultiblockPumpjack.instance);
		
		IERecipes.addOredictRecipe(new ItemStack(blockStoneDecoration, 8, BlockTypes_IPStoneDecoration.ASPHALT.getMeta()), "SCS", "GBG", "SCS", 'C', new ItemStack(itemMaterial, 1, 0), 'S', "sand", 'G', Blocks.gravel, 'B', new FluidStack(FluidRegistry.WATER,1000));
		IERecipes.addOredictRecipe(new ItemStack(blockStoneDecoration, 12, BlockTypes_IPStoneDecoration.ASPHALT.getMeta()), "SCS", "GBG", "SCS", 'C', new ItemStack(itemMaterial, 1, 0), 'S', "itemSlag", 'G', Blocks.gravel, 'B', new FluidStack(FluidRegistry.WATER,1000));
	
		if (!IPConfig.Tools.disable_projector)
		{
			IERecipes.addOredictRecipe(new ItemStack(itemProjector, 1, 0), "S  ", "IL ", " IW", 
					'W', "plankTreatedWood", 
					'L', new ItemStack(IEContent.blockMetalDecoration, 1, BlockMetalDecoration.META_lantern), 
					'S', "blockGlassColorless",
					'I', "ingotIron");

			GameRegistry.addRecipe(new SchematicCraftingHandler());
		}
		
		if (!IPConfig.Miscellaneous.disable_motorboat)
		{
			IERecipes.addOredictRecipe(new ItemStack(itemSpeedboat, 1, 0), "PME", "PPP", 
					'P', "plankTreatedWood", 
					'E', new ItemStack(IEContent.blockMetalDecoration, 1, BlockTypes_MetalDecoration0.LIGHT_ENGINEERING.getMeta()), 
					'M', new ItemStack(IEContent.itemMaterial, 1, 8));
			
			IERecipes.addOredictRecipe(new ItemStack(itemUpgrades, 1, 0), "P P", "PBP", 
					'P', "plateSteel",
					'B', "blockSteel");
			
			IERecipes.addOredictRecipe(new ItemStack(itemUpgrades, 1, 1), "I P", " IP", "PPB", 
					'P', "plateSteel",
					'B', "blockSteel",
					'I', "ingotSteel");
			
			IERecipes.addOredictRecipe(new ItemStack(itemUpgrades, 1, 2), " P ", "PTP", " P ",
					'P', "plateIron",
					'T', new ItemStack(IEContent.blockMetalDevice2, 1, BlockMetalDevices2.META_barrel));
			
			IERecipes.addOredictRecipe(new ItemStack(itemUpgrades, 1, 3), " RR", "PPP", "PPP",
					'P', "plateIron",
					'R', "stickIron");
			
			IERecipes.addOredictRecipe(new ItemStack(itemUpgrades, 1, 4), "S S", "S S", "P P",
					'P', "plankTreatedWood",
					'S', "stickTreatedWood");
		}
		
		if (!IPConfig.Miscellaneous.disable_lubricant)
		{
			IERecipes.addOredictRecipe(new ItemStack(blockMetalDevice, 1, 0), " G ", "G G", "WPW", 
					'W', "plankTreatedWood", 
					'P', new ItemStack(IEContent.blockMetalDevice2, 1, BlockMetalDevices2.META_fluidPipe),
					'G', "blockGlass");
			
			IERecipes.addOredictRecipe(new ItemStack(itemOilCan), " R ", "PBP", 
					'R', "dyeRed", 
					'P', "plateIron",
					'B', new ItemStack(Items.bucket));
		}
		
		if (!IPConfig.Generation.disable_portable_gen)
			IERecipes.addOredictRecipe(new ItemStack(blockMetalDevice, 1, 1), "ITI", "IGR", "III",  
					'G', new ItemStack(IEContent.blockMetalDecoration, 1, BlockTypes_MetalDecoration0.GENERATOR.getMeta()),
					'I', "plateIron",
					'R', new ItemStack(IEContent.blockMetalDevice, 1, BlockMetalDevices.META_capacitorLV),
					'T', new ItemStack(IEContent.blockMetalDevice2, 1, BlockMetalDevices2.META_barrel));
			
		Config.addConfigReservoirs(IPConfig.extraction.reservoirs);
		Config.addFuel(IPConfig.Generation.fuels);
		Config.addBoatFuel(IPConfig.Miscellaneous.boat_fuels);
		Config.addDistillationRecipe(IPConfig.Refining.towerRecipes, IPConfig.Refining.towerByproduct);

		LubricantHandler.registerLubricant(fluidLubricant, 3);
		LubricantHandler.registerLubricant(IEContent.fluidPlantoil, 12);

	}
	
	public static void registerTile(Class<? extends TileEntity> tile)
	{
		String s = tile.getSimpleName();
		s = s.substring(s.indexOf("TileEntity")+"TileEntity".length());
		GameRegistry.registerTileEntity(tile, ImmersivePetroleum.MODID+":"+ s);
	}
}
