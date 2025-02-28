package net.darkhax.darkutils;

import net.darkhax.bookshelf.registry.RegistryHelper;
import net.darkhax.bookshelf.registry.RegistryHelperClient;
import net.darkhax.darkutils.features.slimecrucible.RenderSlimeCrucible;
import net.darkhax.darkutils.features.slimecrucible.ScreenSlimeCrucible;
import net.darkhax.darkutils.features.slimecrucible.TileEntitySlimeCrucible;
import net.darkhax.darkutils.features.witherslime.EntitySlimeWither;
import net.darkhax.darkutils.features.witherslime.RenderSlimeWither;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.EventPriority;

public class ContentClient extends Content {
    
    public ContentClient(RegistryHelper registry) {
        
        super(registry);
        
        if (registry instanceof RegistryHelperClient) {
            
            final RegistryHelperClient clientRegistry = (RegistryHelperClient) registry;
            
            // Tile Entity Renders
            clientRegistry.setSpecialRenderer(TileEntitySlimeCrucible.class, new RenderSlimeCrucible());
            
            // Gui Screens
            clientRegistry.registerGuiScreen(this.containerSlimeCrucible, ScreenSlimeCrucible::new);
            
            // Entities
            clientRegistry.registerEntityRenderer(EntitySlimeWither.class, RenderSlimeWither::new);
        }
        
        MinecraftForge.EVENT_BUS.addListener(EventPriority.HIGHEST, this::addTooltips);
    }
    
    private void addTooltips (ItemTooltipEvent event) {
        
        final ResourceLocation id = event.getItemStack().getItem().getRegistryName();
        
        if ("darkutils".equals(id.getNamespace())) {
            
            event.getToolTip().add(new TranslationTextComponent("tooltip.darkutils." + id.getPath() + ".short").applyTextStyle(TextFormatting.GRAY));
        }
    }
}