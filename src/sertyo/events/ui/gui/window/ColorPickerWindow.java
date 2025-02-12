package sertyo.events.ui.gui.window;


import sertyo.events.Main;
import sertyo.events.module.api.setting.impl.ColorSetting;
import sertyo.events.module.api.setting.impl.NumberSetting;
import sertyo.events.ui.gui.Gui;
import sertyo.events.util.Mouse;
import sertyo.events.util.animation.Animation;
import sertyo.events.util.animation.Direction;
import sertyo.events.util.animation.impl.EaseInOutQuad;
import sertyo.events.util.math.MathUtility;
import sertyo.events.util.render.ColorUtil;
import sertyo.events.util.render.RenderUtility;

import java.awt.*;

public class ColorPickerWindow extends Window {
   private final ColorSetting colorSetting;
   private Animation animation;
   private final NumberSetting hueSlider;
   private boolean isHueDragging;
   private int hueValue;

   public ColorPickerWindow(float x, float y, float width, float height, ColorSetting colorSetting) {
      super(x, y, width, height);
      this.animation = new EaseInOutQuad(250, 1.0F, Direction.FORWARDS);
      this.hueSlider = new NumberSetting("Hue", 1.0F, 1.0F, 360.0F, 1.0F);
      this.colorSetting = colorSetting;
      float[] color = ColorUtil.getRGBAf(colorSetting.get());
      float[] hueArray = Color.RGBtoHSB((int)(color[0] * 255.0F), (int)(color[1] * 255.0F), (int)(color[2] * 255.0F), color);
      this.hueValue = (int)(hueArray[0] * 360.0F);
   }

   public void init() {
      this.animation = new EaseInOutQuad(250, 1.0F, Direction.FORWARDS);
   }

   public void render(int mouseX, int mouseY) {
      if (this.animation.finished(Direction.BACKWARDS)) {
         Gui.colorPicker = null;
      } else {
         Color color = Main.getInstance().getThemeManager().getCurrentStyleTheme().getColors()[0];
         Color color2 = Main.getInstance().getThemeManager().getCurrentStyleTheme().getColors()[1];
         RenderUtility.scaleStart(this.x + this.width / 2.0F, this.y + this.height / 2.0F, this.animation.getOutput());
         RenderUtility.drawShadow(this.x, this.y, this.width, this.height, 7, color.getRGB(), color2.getRGB(), color.getRGB(), color2.getRGB());
         RenderUtility.drawRoundedRect(this.x, this.y, this.width, this.height, 5.0F, (new Color(30, 30, 30)).getRGB());
         int hueColor = ColorUtil.HUEtoRGB(this.hueValue);
         RenderUtility.drawRoundedGradientRect(this.x + 5.0F, this.y + 5.0F, this.width - 10.0F, this.height - 20.0F, 0.0F, 0.0F, Color.WHITE.getRGB(), Color.BLACK.getRGB(), hueColor, Color.BLACK.getRGB());
         int pixelColor = 0;
         float inc;
         float times;
         if (MathUtility.isHovered((float)mouseX, (float)mouseY, this.x + 5.0F, this.y + 5.0F, this.width - 10.0F, this.height - 20.0F) && !this.isHueDragging && Mouse.isButtonDown(0)) {
            inc = ((float)mouseX - (this.x + 5.0F)) / (this.width - 10.0F);
            times = 1.0F - ((float)mouseY - (this.y + 5.0F)) / (this.height - 20.0F);
            pixelColor = Color.HSBtoRGB((float)this.hueValue / 360.0F, inc, times);
         }

         if (pixelColor != 0) {
            this.colorSetting.setColor(pixelColor);
         }

         if (this.isHueDragging) {
            this.hueValue = this.getSliderValue(this.hueSlider, this.x + 5.0F, mouseX);
         }

         inc = 0.2F;
         times = 1.0F / inc;
         float xHuePos = this.x + 5.0F;
         float size = (this.width - 10.0F) / times;

         for(int i = 0; (float)i < times; ++i) {
            boolean last = (float)i == times - 1.0F;
            if (last) {
               --size;
            }

            RenderUtility.drawGradientRect(xHuePos, this.y + this.height - 10.0F, size, 5.0F, Color.HSBtoRGB(inc * (float)i, 1.0F, 1.0F), Color.HSBtoRGB(inc * (float)(i + 1), 1.0F, 1.0F), Color.HSBtoRGB(inc * (float)(i + 1), 1.0F, 1.0F), Color.HSBtoRGB(inc * (float)i, 1.0F, 1.0F));
            if (!last) {
               xHuePos += size;
            }
         }

         RenderUtility.drawRoundedRect(this.x + 4.0F + (float)this.getPos(this.hueSlider, this.hueValue), this.y + this.height - 11.0F, 2.0F, 7.0F, 1.0F, -1);
         RenderUtility.scaleEnd();
      }
   }

   public void mouseClicked(double mouseX, double mouseY, int mouseButton) {
      if (!MathUtility.isHovered(mouseX, mouseY, (double)this.x, (double)this.y, (double)this.width, (double)this.height) && this.animation.isDone()) {
         this.animation.setDirection(Direction.BACKWARDS);
         this.animation.setDuration(225);
      }

      if (MathUtility.isHovered(mouseX, mouseY, (double)(this.x + 5.0F), (double)(this.y + this.height - 10.0F), (double)(this.width - 10.0F), 5.0D) && mouseButton == 0) {
         this.isHueDragging = true;
      }

   }

   public void mouseReleased(double mouseX, double mouseY, int state) {
      this.isHueDragging = false;
   }

   public int getSliderValue(NumberSetting numberSetting, float posX, int mouseX) {
      int delta = (int)(numberSetting.getMaxValue() - numberSetting.getMinValue());
      float clickedX = (float)mouseX - posX;
      float value = clickedX / (this.width - 10.0F);
      float outValue = (float)(numberSetting.getMinValue() + (double)((float)delta * value));
      return (int) MathUtility.clamp((float)((int)MathUtility.round((double)outValue, numberSetting.getIncrement())), (float)numberSetting.getMinValue(), (float)numberSetting.getMaxValue());
   }

   public int getPos(NumberSetting numberSetting, int value) {
      int delta = (int)(numberSetting.getMaxValue() - numberSetting.getMinValue());
      return (int)((double)(this.width - 10.0F) * ((double)value - numberSetting.getMinValue()) / (double)delta);
   }

   public ColorSetting getColorSetting() {
      return this.colorSetting;
   }
}
