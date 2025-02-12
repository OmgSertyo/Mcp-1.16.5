package sertyo.events.ui.gui.component.impl;



import com.mojang.blaze3d.matrix.MatrixStack;
import sertyo.events.module.api.setting.impl.ColorSetting;
import sertyo.events.ui.gui.Gui;
import sertyo.events.ui.gui.component.Component;
import sertyo.events.ui.gui.window.ColorPickerWindow;
import sertyo.events.util.math.MathUtility;
import sertyo.events.util.render.RenderUtility;
import sertyo.events.util.render.font.Fonts;

import java.awt.*;

public class ColorComponent extends Component  {
   public ModuleComponent moduleComponent;
   public ColorSetting setting;

   public ColorComponent(ModuleComponent moduleComponent, ColorSetting setting) {
      super(0.0F, 0.0F, 0.0F, 14.0F);
      this.moduleComponent = moduleComponent;
      this.setting = setting;
   }

   public void render(MatrixStack ms, int mouseX, int mouseY) {
      super.render(ms, mouseX, mouseY);
      boolean isDark = true;
      Fonts.INTER_BOLD.get(14).draw(ms, this.setting.getName(), this.x + 5.0F, this.y + 5.0F, isDark ? Color.WHITE.getRGB() : (new Color(55, 55, 55)).getRGB());
      RenderUtility.drawRoundedRect(this.x + this.width - 16.0F, this.y + 1.5F, 11.0F, 11.0F, 10.0F, isDark ? (new Color(60, 60, 60)).getRGB() : (new Color(160, 160, 160)).getRGB());
      RenderUtility.drawRoundedRect(this.x + this.width - 15.0F, this.y + 2.5F, 9.0F, 9.0F, 8.0F, isDark ? (new Color(34, 34, 34)).getRGB() : (new Color(210, 210, 210)).getRGB());
      RenderUtility.drawRoundedRect(this.x + this.width - 14.0F, this.y + 3.5F, 7.0F, 7.0F, 6.0F, this.setting.get());
   }

   public void mouseClicked(double mouseX, double mouseY, int mouseButton) {
      super.mouseClicked(mouseX, mouseY, mouseButton);
      if (MathUtility.isHovered(mouseX, mouseY, (double)(this.x + this.width - 16.0F), (double)(this.y + 1.5F), 11.0D, 11.0D) && (Gui.colorPicker == null || !Gui.colorPicker.getColorSetting().equals(this.setting))) {
         Gui.colorPicker = new ColorPickerWindow((float)(mouseX + 5.0D), (float)(mouseY + 5.0D), 80.0F, 80.0F, this.setting);
      }

   }

   public boolean isVisible() {
      return (Boolean)this.setting.getVisible().get();
   }
}
