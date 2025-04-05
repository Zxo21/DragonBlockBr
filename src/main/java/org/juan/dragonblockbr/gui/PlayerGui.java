package org.juan.dragonblockbr.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.juan.dragonblockbr.DragonBlock;
import org.juan.dragonblockbr.attributes.AttributesCapabilities;
import org.juan.dragonblockbr.attributes.PlayerAttributes;

@Mod.EventBusSubscriber(modid = DragonBlock.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class PlayerGui {

    private static final ResourceLocation HEALTH_BAR_BACK = new ResourceLocation("dragonblockbr", "textures/gui/img_background.png");
    private static final ResourceLocation HEALTH_BAR_FILL = new ResourceLocation("dragonblockbr", "textures/gui/img_fill.png");
    private static final ResourceLocation KI_BAR = new ResourceLocation("dragonblockbr", "textures/gui/ki_bar.png");
    private static final ResourceLocation STAMINA_BAR = new ResourceLocation("dragonblockbr", "textures/gui/food_bar.png");


    @SubscribeEvent
    public static void onRenderGameOverlay(RenderGameOverlayEvent event) {

        // Cancela o evento que carrega o GUI padrão do jogo
        if (event.getType() == RenderGameOverlayEvent.ElementType.HEALTH ||
                event.getType() == RenderGameOverlayEvent.ElementType.FOOD) {
            event.setCanceled(true);
        }

        // Renderiza o GUI personalizado
        if (event.getType() == RenderGameOverlayEvent.ElementType.ALL) {
            renderHealthBar(event.getMatrixStack());
            renderStaminaBar(event.getMatrixStack());
            renderKiBar(event.getMatrixStack());
        }
    }


    private static void renderHealthBar(MatrixStack matrixStack) {
        Minecraft minecraft = Minecraft.getInstance();
        PlayerEntity player = minecraft.player;

        if (player == null) return;

            float health = player.getHealth();
            float maxHealth = player.getMaxHealth();

            int screenWidth = minecraft.getWindow().getGuiScaledWidth();
            int screenHeight = minecraft.getWindow().getGuiScaledHeight();
            int healthBarWidth = 153;
            int healthBarHeight = 15;
            int x = (int) (screenWidth * 0.05);
            int y = (int) (screenHeight * 0.05);

            minecraft.getTextureManager().bind(HEALTH_BAR_BACK);
            minecraft.gui.blit(matrixStack, x, y, 0, 0, healthBarWidth, healthBarHeight, healthBarWidth, healthBarHeight);

            int filledWidth = (int) Math.min((health / maxHealth) * healthBarWidth, healthBarWidth);

            minecraft.getTextureManager().bind(HEALTH_BAR_FILL);
            minecraft.gui.blit(matrixStack, x, y, 0, 0, filledWidth, healthBarHeight, healthBarWidth, healthBarHeight);

    }

    private static void renderKiBar(MatrixStack matrixStack) {
        Minecraft minecraft = Minecraft.getInstance();
        PlayerEntity player = minecraft.player;
        if (player == null || minecraft.level == null) return;


        player.getCapability(AttributesCapabilities.PLAYER_ATTRIBUTES_CAP).ifPresent(attrs -> {
            float kiAtual = attrs.getKi();
            float kiMaximo = 1000;

            int screenWidth = minecraft.getWindow().getGuiScaledWidth();
            int screenHeight = minecraft.getWindow().getGuiScaledHeight();
            int kiBarWidth = 60, kiBarHeight = 5;

            int x = (int) (screenWidth * 0.05), y = (int) (screenHeight * 0.05) + 30; // 5% from the left

            int filledWidth = (int) ((kiAtual / kiMaximo) * kiBarWidth);

            minecraft.getTextureManager().bind(KI_BAR);
            minecraft.gui.blit(matrixStack, x, y, 0, 0, filledWidth, kiBarHeight, kiBarWidth, kiBarHeight);
        });
    }


    private static void renderStaminaBar(MatrixStack matrixStack) {
        Minecraft minecraft = Minecraft.getInstance();
        PlayerEntity player = minecraft.player;
        if (player == null) return;
        int stamina = player.getFoodData().getFoodLevel();

            int screenWidth = minecraft.getWindow().getGuiScaledWidth();
            int screenHeight = minecraft.getWindow().getGuiScaledHeight();
            int foodBarWidth = 60,foodBarHeight = 5;
            int x = (int) (screenWidth * 0.05), y = (int) (screenHeight * 0.05) + 20;


            int filledWidth = (int) ((stamina / 20.0f) * foodBarWidth);
            minecraft.getTextureManager().bind(STAMINA_BAR);
            minecraft.gui.blit(matrixStack, x, y, 0, 0, filledWidth, foodBarHeight, foodBarWidth, foodBarHeight);

    }

}
