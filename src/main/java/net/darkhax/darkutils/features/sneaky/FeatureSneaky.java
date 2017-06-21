package net.darkhax.darkutils.features.sneaky;

import static net.darkhax.bookshelf.util.OreDictUtils.OBSIDIAN;
import static net.darkhax.bookshelf.util.OreDictUtils.STONE;

import net.darkhax.bookshelf.util.TempCraftingUtils;
import net.darkhax.darkutils.DarkUtils;
import net.darkhax.darkutils.features.DUFeature;
import net.darkhax.darkutils.features.Feature;
import net.darkhax.darkutils.features.material.FeatureMaterial;
import net.darkhax.darkutils.handler.RecipeHandler;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@DUFeature(name = "Sneaky Blocks", description = "Blocks that can hide as other blocks")
public class FeatureSneaky extends Feature {

    /**
     * A state map instance for the sneaky block model.
     */
    @SideOnly(Side.CLIENT)
    private static StateMapperBase sneakyStateMap;

    public static Block blockSneakyBlock;

    public static Block blockSneakyLever;

    public static Block blockSneakyGhost;

    public static Block blockSneakyTorch;

    public static Block blockSneakyObsidian;

    public static Block blockSneakyPlate;

    public static Block blockSneakyBedrock;

    public static boolean craftSneakyBlock;

    public static boolean craftSneakyLever;

    public static boolean craftSneakyGhost;

    public static boolean craftSneakyTorch;

    public static boolean craftSneakyObsidian;

    public static boolean craftSneakyPlate;

    public static boolean opacity;

    @Override
    public void onRegistry () {

        blockSneakyBlock = new BlockSneaky();
        DarkUtils.REGISTRY.registerBlock(blockSneakyBlock, "sneaky");
        GameRegistry.registerTileEntity(TileEntitySneaky.class, "sneaky");

        blockSneakyLever = new BlockSneakyLever();
        DarkUtils.REGISTRY.registerBlock(blockSneakyLever, "sneaky_lever");

        blockSneakyGhost = new BlockSneakyGhost();
        DarkUtils.REGISTRY.registerBlock(blockSneakyGhost, "sneaky_ghost");

        blockSneakyTorch = new BlockSneakyTorch();
        DarkUtils.REGISTRY.registerBlock(blockSneakyTorch, "sneaky_torch");

        blockSneakyObsidian = new BlockSneaky().setHardness(20f).setResistance(2000f);
        DarkUtils.REGISTRY.registerBlock(blockSneakyObsidian, "sneaky_obsidian");

        blockSneakyPlate = new BlockSneakyPressurePlate();
        DarkUtils.REGISTRY.registerBlock(blockSneakyPlate, "sneaky_plate");

        blockSneakyBedrock = new BlockSneakyBedrock();
        DarkUtils.REGISTRY.registerBlock(blockSneakyBedrock, "sneaky_bedrock");
    }

    @Override
    public void setupConfiguration (Configuration config) {

        craftSneakyBlock = config.getBoolean("Craft Sneaky Block", this.configName, true, "Should the sneaky block be craftable?");
        craftSneakyLever = config.getBoolean("Craft Sneaky Lever", this.configName, true, "Should the sneaky lever be craftable?");
        craftSneakyGhost = config.getBoolean("Craft Sneaky False Block", this.configName, true, "Should the sneaky false block be craftable?");
        craftSneakyTorch = config.getBoolean("Craft Sneaky Torch", this.configName, true, "Should the sneaky torch be craftable?");
        craftSneakyObsidian = config.getBoolean("Craft Sneaky Obsidian", this.configName, true, "Should the sneaky obsidian be craftable?");
        craftSneakyPlate = config.getBoolean("Craft Sneaky Pressure Plate", this.configName, true, "Should the sneaky pressure plate be craftable?");
        opacity = config.getBoolean("Opacity", this.configName, true, "When true, all sneaky blocks will let no light through. When disabled, all light will be let through.");
    }

    @Override
    public void setupRecipes () {

        if (craftSneakyBlock) {

            RecipeHandler.addShapedOreRecipe(new ItemStack(blockSneakyBlock, 8), "rrr", "rsr", "rrr", 'r', STONE, 's', TempCraftingUtils.validateCrafting(new ItemStack(FeatureMaterial.itemMaterial, 1, 2)));

            if (craftSneakyLever) {
                RecipeHandler.addShapelessRecipe(new ItemStack(blockSneakyLever), blockSneakyBlock, Blocks.LEVER);
            }

            if (craftSneakyGhost) {
                RecipeHandler.addShapelessRecipe(new ItemStack(blockSneakyGhost), blockSneakyBlock, Blocks.WOOL);
            }

            if (craftSneakyTorch) {

                RecipeHandler.addShapelessRecipe(new ItemStack(blockSneakyTorch), blockSneakyBlock, Blocks.TORCH);
                RecipeHandler.addShapelessRecipe(new ItemStack(blockSneakyTorch), blockSneakyBlock, Blocks.REDSTONE_TORCH);
            }

            if (craftSneakyObsidian) {
                RecipeHandler.addShapelessOreRecipe(new ItemStack(blockSneakyObsidian), blockSneakyBlock, OBSIDIAN);
            }

            if (craftSneakyPlate) {
                RecipeHandler.addShapelessRecipe(new ItemStack(blockSneakyPlate), blockSneakyBlock, Blocks.WOODEN_PRESSURE_PLATE);
            }
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void onClientPreInit () {

        sneakyStateMap = new StateMapSneaky();
        this.registerSneakyModel(blockSneakyBlock, "sneaky_default", false);
        this.registerSneakyModel(blockSneakyLever, "sneaky_lever", false);
        this.registerSneakyModel(blockSneakyGhost, "sneaky_default", true);
        this.registerSneakyModel(blockSneakyTorch, "sneaky_torch", false);
        this.registerSneakyModel(blockSneakyObsidian, "sneaky_default", true);
        this.registerSneakyModel(blockSneakyPlate, "sneaky_plate", false);
        this.registerSneakyModel(blockSneakyBedrock, "sneaky_default", true);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void onClientInit () {

        Minecraft.getMinecraft().getBlockColors().registerBlockColorHandler(new BlockColorSneaky(), blockSneakyBlock, blockSneakyLever, blockSneakyGhost, blockSneakyTorch, blockSneakyObsidian, blockSneakyPlate, blockSneakyBedrock);
    }

    @Override
    public boolean usesEvents () {

        return true;
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void onModelBake (ModelBakeEvent event) {

        event.getModelRegistry().putObject(new ModelResourceLocation("darkutils:sneaky", "normal"), new ModelSneakyBlock());
    }

    /**
     * Registers a sneaky block model.
     *
     * @param block The block to register the model for.
     * @param name The name of the sneaky block model.
     * @param useDefault Whether or not the default model should be used.
     */
    @SideOnly(Side.CLIENT)
    public void registerSneakyModel (Block block, String name, boolean useDefault) {

        final Item item = Item.getItemFromBlock(block);
        ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation("darkutils:" + name, "inventory"));

        if (!useDefault) {
            ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation("darkutils:" + name, "normal"));
        }

        ModelLoader.setCustomStateMapper(block, sneakyStateMap);
    }
}
