package sertyo.events.ui.gui.component.impl;



import com.mojang.blaze3d.matrix.MatrixStack;
import sertyo.events.Main;
import sertyo.events.module.api.setting.impl.ModeSetting;
import sertyo.events.ui.gui.component.Component;
import sertyo.events.util.math.MathUtility;
import sertyo.events.util.render.RenderUtility;
import sertyo.events.util.render.font.Fonts;

import java.awt.*;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public class ModeComponent extends Component {
   public ModuleComponent moduleComponent;
   public ModeSetting setting;

   public ModeComponent(ModuleComponent moduleComponent, ModeSetting setting) {
      super(0.0F, 0.0F, 0.0F, 18.0F);
      this.moduleComponent = moduleComponent;
      this.setting = setting;
   }

   public void render(MatrixStack ms, int mouseX, int mouseY) {
      super.render(ms, mouseX, mouseY);
      int color = Main.getInstance().getThemeManager().getCurrentStyleTheme().getColors()[0].getRGB();
      int color2 = Main.getInstance().getThemeManager().getCurrentStyleTheme().getColors()[1].getRGB();
      boolean isDark = true;
      Fonts.INTER_BOLD.get(14).draw(ms, this.setting.getName(), this.x + 5.0F, this.y + 3.0F, isDark ? Color.WHITE.getRGB() : (new Color(55, 55, 55)).getRGB());
      float availableWidth = this.width - 10.0F;
      float xOffset = 2.0F;
      float yOffset = 4.0F;
      float spacing = 3.0F;

      float enabledWidth;
      for(Iterator var10 = ((List)this.setting.getModes().stream().sorted(Comparator.comparingDouble(this::getEnabledWidth)).collect(Collectors.toList())).iterator(); var10.hasNext(); xOffset += enabledWidth + spacing) {
         String mode = (String)var10.next();
         enabledWidth = this.getEnabledWidth(mode);
         float enabledHeight = (float)(Fonts.INTER_BOLD.get(14).getHeight() + 4);
         if (xOffset + enabledWidth > availableWidth) {
            xOffset = 2.0F;
            yOffset += enabledHeight + spacing;
         }

         if (this.setting.get().equals(mode)) {
            RenderUtility.drawRoundedGradientRect(this.x + 3.0F + xOffset, this.y + (float)Fonts.INTER_BOLD.get(14).getHeight() + yOffset, enabledWidth, enabledHeight, 3.0F, 1.0F, color, color, color2, color2);
         } else {
            RenderUtility.drawRoundedRect(this.x + 3.0F + xOffset, this.y + (float)Fonts.INTER_BOLD.get(14).getHeight() + yOffset, enabledWidth, enabledHeight, 3.0F, isDark ? (new Color(52, 52, 52)).getRGB() : (new Color(160, 160, 160)).getRGB());
         }

         Fonts.INTER_BOLD.get(14).draw(ms, mode, this.x + 5.0F + xOffset, this.y + 3.0F + (float)Fonts.INTER_BOLD.get(14).getHeight() + yOffset, isDark ? Color.WHITE.getRGB() : (new Color(55, 55, 55)).getRGB());
      }

      this.height = 18.0F + yOffset;
   }

   public void mouseClicked(double mouseX, double mouseY, int mouseButton) {
      super.mouseClicked(mouseX, mouseY, mouseButton);
      float availableWidth = this.width - 10.0F;
      float xOffset = 2.0F;
      float yOffset = 4.0F;
      float spacing = 3.0F;

      float enabledWidth;
      for(Iterator var10 = ((List)this.setting.getModes().stream().sorted(Comparator.comparingDouble(this::getEnabledWidth)).collect(Collectors.toList())).iterator(); var10.hasNext(); xOffset += enabledWidth + spacing) {
         String mode = (String)var10.next();
         enabledWidth = this.getEnabledWidth(mode);
         float enabledHeight = (float)(Fonts.INTER_BOLD.get(14).getHeight() + 4);
         if (xOffset + enabledWidth > availableWidth) {
            xOffset = 2.0F;
            yOffset += enabledHeight + spacing;
         }

         if (MathUtility.isHovered(mouseX, mouseY, (double)(this.x + 3.0F + xOffset), (double)(this.y + (float)Fonts.INTER_BOLD.get(14).getHeight() + yOffset), (double)enabledWidth, (double)enabledHeight)) {
            this.setting.set(mode);
         }
      }

   }

   private float getEnabledWidth(String mode) {
      return (float)(Fonts.INTER_BOLD.get(14).getWidth(mode) + 4);
   }

   public boolean isVisible() {
      return (Boolean)this.setting.getVisible().get();
   }
}
