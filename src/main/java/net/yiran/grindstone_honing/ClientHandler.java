package net.yiran.grindstone_honing;

import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.client.event.RegisterItemDecorationsEvent;
import net.minecraftforge.common.ToolAction;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import se.mickelus.tetra.client.ToolActionIconStore;
import se.mickelus.tetra.module.data.GlyphData;

public class ClientHandler {
    @SubscribeEvent
    public static void onRegisterItemDecorations(RegisterItemDecorationsEvent event) {
        event.register(Items.DEBUG_STICK,(GuiGraphics guiGraphics, Font font, ItemStack stack, int xOffset, int yOffset) -> {
            if(stack.hasTag()){
                if(stack.getTag().contains("tool")){
                    GlyphData glyphData = ToolActionIconStore.instance.getIcon(ToolAction.get(stack.getTag().getString("tool")));
                    guiGraphics.blit(
                            glyphData.textureLocation,xOffset,yOffset,200,glyphData.textureX,glyphData.textureY,16,16,256,256
                    );
                }
            }
            return true;
        });
    }

}
