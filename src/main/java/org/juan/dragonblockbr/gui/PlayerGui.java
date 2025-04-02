package org.juan.dragonblockbr.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.juan.dragonblockbr.attributes.AttributesCapabilities;
import org.juan.dragonblockbr.attributes.PlayerAttributes;

public class PlayerGui {

    private static final ResourceLocation HEALTH_BAR_BACK = new ResourceLocation("examplemod", "textures/gui/img_background.png");
    private static final ResourceLocation HEALTH_BAR_FILL = new ResourceLocation("examplemod", "textures/gui/img_fill.png");
    private static final ResourceLocation KI_BAR = new ResourceLocation("examplemod", "textures/gui/ki_bar.png");
    private static final ResourceLocation FOOD_BAR = new ResourceLocation("examplemod", "textures/gui/food_bar.png");

    @SubscribeEvent
    public static void onRenderGameOverlay(RenderGameOverlayEvent event) {
        // Cancela as barras padrão
        if (event.getType() == RenderGameOverlayEvent.ElementType.HEALTH ||
                event.getType() == RenderGameOverlayEvent.ElementType.FOOD) {
            event.setCanceled(true);
        }

        // Renderiza as barras personalizadas
        if (event.getType() == RenderGameOverlayEvent.ElementType.ALL) {
            renderCustomHealthBar(event.getMatrixStack());
            renderCustomFoodBar(event.getMatrixStack());
            renderKiBar(event.getMatrixStack());
        }
    }


    private static void renderCustomHealthBar(MatrixStack matrixStack) {
        Minecraft minecraft = Minecraft.getInstance();
        PlayerEntity player = minecraft.player;

        if (player == null) return;

        // Obtém a capacidade do jogador (se existir)
        LazyOptional<PlayerAttributes> capability = player.getCapability(AttributesCapabilities.PLAYER_ATTRIBUTES_CAP);
        if (!capability.isPresent()) return; // Se não houver capability, sai

        capability.ifPresent(attrs -> {
            float health = player.getHealth();
            float maxHealth = attrs.getMaxHealth(); // Usa o maxHealth da capability

            // Configurações da barra
            int screenWidth = minecraft.getWindow().getGuiScaledWidth();
            int screenHeight = minecraft.getWindow().getGuiScaledHeight();
            int healthBarWidth = 153;
            int healthBarHeight = 15;
            int x = (int) (screenWidth * 0.05);
            int y = (int) (screenHeight * 0.05);

            // Renderiza o fundo da barra
            minecraft.getTextureManager().bind(HEALTH_BAR_BACK);
            minecraft.gui.blit(matrixStack, x, y, 0, 0, healthBarWidth, healthBarHeight, healthBarWidth, healthBarHeight);

            // Calcula a largura preenchida (garante que não ultrapasse 100%)
            int filledWidth = (int) Math.min((health / maxHealth) * healthBarWidth, healthBarWidth);

            // Renderiza a parte preenchida da barra
            minecraft.getTextureManager().bind(HEALTH_BAR_FILL);
            minecraft.gui.blit(matrixStack, x, y, 0, 0, filledWidth, healthBarHeight, healthBarWidth, healthBarHeight);
        });
    }

    private static void renderKiBar(MatrixStack matrixStack) {
        Minecraft minecraft = Minecraft.getInstance();
        PlayerEntity player = minecraft.player;

        // Ensure the player exists and the game is in a valid state
        if (player == null || minecraft.level == null) return;

        // Get the Ki capability
        player.getCapability(AttributesCapabilities.PLAYER_ATTRIBUTES_CAP).ifPresent(attrs -> {
            float kiAtual = attrs.getKi();
            float kiMaximo = 1000; // Maximum Ki value

            // Get screen dimensions
            int screenWidth = minecraft.getWindow().getGuiScaledWidth();
            int screenHeight = minecraft.getWindow().getGuiScaledHeight();

            // Ki bar dimensions
            int kiBarWidth = 60;
            int kiBarHeight = 5;
            int x = (int) (screenWidth * 0.05); // 5% from the left
            int y = (int) (screenHeight * 0.05) + 30; // 5% from the top, plus 30 pixels


            // Calculate the width of the filled Ki bar
            int filledWidth = (int) ((kiAtual / kiMaximo) * kiBarWidth);

            // Bind and render the filled portion of the Ki bar
            minecraft.getTextureManager().bind(KI_BAR);
            minecraft.gui.blit(matrixStack, x, y, 0, 0, filledWidth, kiBarHeight, kiBarWidth, kiBarHeight);
        });
    }


    private static void renderCustomFoodBar(MatrixStack matrixStack) {
        Minecraft minecraft = Minecraft.getInstance();
        PlayerEntity player = minecraft.player;

        if (player == null) return;

        player.getCapability(AttributesCapabilities.PLAYER_ATTRIBUTES_CAP).ifPresent(attrs -> {
            int stamina = attrs.getStamina();

            int screenWidth = minecraft.getWindow().getGuiScaledWidth();
            int screenHeight = minecraft.getWindow().getGuiScaledHeight();
            int foodBarWidth = 60;
            int foodBarHeight = 5;
            int x = (int) (screenWidth * 0.05);
            int y = (int) (screenHeight * 0.05) + 20; // 15 pixels abaixo da barra de vida


            int filledWidth = (int) ((stamina / 20.0f) * foodBarWidth);
            minecraft.getTextureManager().bind(FOOD_BAR);
            minecraft.gui.blit(matrixStack, x, y, 0, 0, filledWidth, foodBarHeight, foodBarWidth, foodBarHeight);
        });
    }

}
