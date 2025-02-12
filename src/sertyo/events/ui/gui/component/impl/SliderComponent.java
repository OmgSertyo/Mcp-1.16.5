package sertyo.events.ui.gui.component.impl;


import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.util.math.MathHelper;
import sertyo.events.Main;
import sertyo.events.module.api.setting.impl.NumberSetting;
import sertyo.events.ui.gui.component.Component;
import sertyo.events.util.Mouse;
import sertyo.events.util.animation.AnimationMath;
import sertyo.events.util.math.MathUtility;
import sertyo.events.util.render.RenderUtility;
import sertyo.events.util.render.font.Fonts;

import java.awt.*;

public class SliderComponent extends Component {
   public ModuleComponent moduleComponent;
   public NumberSetting setting;
   public float animation = 0.0F;
   public boolean isDragging;

   public SliderComponent(ModuleComponent moduleComponent, NumberSetting setting) {
      super(0.0F, 0.0F, 0.0F, 19.0F);
      this.moduleComponent = moduleComponent;
      this.setting = setting;
   }

   public void render(MatrixStack ms, int mouseX, int mouseY) {
      super.render(ms, mouseX, mouseY);
      int color = Main.getInstance().getThemeManager().getCurrentStyleTheme().getColors()[0].getRGB();
      int color2 = Main.getInstance().getThemeManager().getCurrentStyleTheme().getColors()[1].getRGB();
      boolean isDark = true;
      Fonts.INTER_BOLD.get(14).draw(ms, this.setting.getName(), this.x + 5.0F, this.y + 3.0F, isDark ? Color.WHITE.getRGB() : (new Color(55, 55, 55)).getRGB());
      RenderUtility.drawRect(this.x + 5.0F, this.y + 12.0F, this.width - 10.0F, 3.5F, isDark ? (new Color(25, 25, 25)).getRGB() : (new Color(160, 160, 160)).getRGB());
      float sliderWidth = (float)(((double)this.setting.get() - this.setting.getMinValue()) / (this.setting.getMaxValue() - this.setting.getMinValue()) * (double)(this.width - 10.0F));
      this.animation = AnimationMath.fast(this.animation, sliderWidth, 15.0F);
      RenderUtility.drawGradientRect(this.x + 5.0F, this.y + 12.0F, this.animation, 3.5F, color, color2, color2, color);
      RenderUtility.drawShadow(this.x + this.animation + 3.5F, this.y + 10.5F, 2.0F, 7.0F, 3, Color.BLACK.getRGB(), Color.BLACK.getRGB(), Color.BLACK.getRGB(), Color.BLACK.getRGB());
      RenderUtility.drawRect(this.x + this.animation + 3.0F, this.y + 10.0F, 2.5F, 7.5F, -1);
      if (this.isDragging) {
         if (!Mouse.isButtonDown(0)) {
            this.isDragging = false;
         }

         float sliderValue = (float)MathHelper.clamp(MathUtility.round((double)((float)((double)(((float)mouseX - this.x - 5.0F) / (this.width - 10.0F)) * (this.setting.getMaxValue() - this.setting.getMinValue()) + this.setting.getMinValue())), this.setting.getIncrement()), this.setting.getMinValue(), this.setting.getMaxValue());
         this.setting.set(sliderValue);
      }

      Fonts.INTER_BOLD.get(14).draw(ms, String.valueOf(this.setting.get()), this.x + this.width - 7.0F - (float)Fonts.INTER_BOLD.get(12).getWidth(String.valueOf(this.setting.get())), this.y + 3.0F, isDark ? Color.WHITE.getRGB() : (new Color(55, 55, 55)).getRGB());
   }

   public void mouseClicked(double mouseX, double mouseY, int mouseButton) {
      super.mouseClicked(mouseX, mouseY, mouseButton);
      boolean isHovered = MathUtility.isHovered(mouseX, mouseY, (double)this.x, (double)this.y, (double)this.width, (double)this.height);
      if (isHovered && mouseButton == 0) {
         this.isDragging = true;
      }

   }

   public boolean isVisible() {
      return (Boolean)this.setting.getVisible().get();
   }
}
