package ru.luminous_team.luminous_team_magic.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.FurnaceScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import ru.luminous_team.luminous_team_magic.LuminousTeamMagic;
import ru.luminous_team.luminous_team_magic.blocks.blocks_entity.TableBlockEntity;

public class TableScreen extends AbstractContainerScreen<TableContainer> {
    private static final ResourceLocation GUI_TEXTURE = new ResourceLocation("luminous_team_magic", "textures/screens/table_gui.png");
    private final int imageWidth = 176; // Ширина интерфейса
    private final int imageHeight = 166; // Высота интерфейса
    private final int imageArrowWidth = 24;
    private final int imageArrowHeight = 16;

    public TableScreen(TableContainer container, Inventory inventory, Component title) {
        super(container, inventory, title);
    }

    @Override
    protected void renderBg(PoseStack poseStack, float partialTicks, int mouseX, int mouseY) {
        RenderSystem.setShaderTexture(0, GUI_TEXTURE);
        int x = (this.width - this.imageWidth) / 2;
        int y = (this.height - this.imageHeight) / 2;
        this.blit(poseStack, x, y, 0, 0, this.imageWidth, this.imageHeight);
        TableBlockEntity blockEntity = (TableBlockEntity) this.menu.getBlockEntity();
        RenderSystem.setShaderTexture(0, new ResourceLocation("luminous_team_magic", "textures/screens/arrow" + Math.round(blockEntity.progress / 20) + ".png"));
        int arrowX = x + (this.imageWidth - this.imageArrowWidth) / 2;
        int arrowY = y + (this.imageHeight - this.imageArrowHeight) / 4;
        blit(poseStack, arrowX + 2, arrowY - 4, 0, 0, imageArrowWidth, imageArrowHeight, imageArrowWidth, imageArrowHeight);
    }

    @Override
    public void render(PoseStack poseStack, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(poseStack);
        super.render(poseStack, mouseX, mouseY, partialTicks);
        this.renderTooltip(poseStack, mouseX, mouseY);
    }
}