package sertyo.events.ui.gui.component.impl;


import com.mojang.blaze3d.matrix.MatrixStack;
import sertyo.events.Main;
import sertyo.events.module.api.setting.Setting;
import sertyo.events.module.api.setting.impl.*;
import sertyo.events.ui.gui.Gui;
import sertyo.events.ui.gui.component.Component;
import sertyo.events.module.api.Module;
import sertyo.events.util.animation.AnimationMath;
import sertyo.events.util.math.MathUtility;
import sertyo.events.util.render.ColorUtil;
import sertyo.events.util.render.RenderUtility;
import sertyo.events.util.render.font.Fonts;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ModuleComponent extends Component {
   private final Module module;
   public boolean binding;
   public List<Component> elements = new ArrayList();
   public float enableAnimation = 0.0F;

   public ModuleComponent(Module module, float width, float height) {
      super(0.0F, 0.0F, width, height);
      this.module = module;
      Iterator var4 = module.getSettings().iterator();

      while(var4.hasNext()) {
         Setting setting = (Setting)var4.next();
         if (setting instanceof BooleanSetting) {
            this.elements.add(new BooleanComponent(this, (BooleanSetting)setting));
         } else if (setting instanceof NumberSetting) {
            this.elements.add(new SliderComponent(this, (NumberSetting)setting));
         } else if (setting instanceof ModeSetting) {
            this.elements.add(new ModeComponent(this, (ModeSetting)setting));
         } else if (setting instanceof MultiBooleanSetting) {
            this.elements.add(new MultiBoolComponent(this, (MultiBooleanSetting)setting));
         } else if (setting instanceof ColorSetting) {
            this.elements.add(new ColorComponent(this, (ColorSetting)setting));
         } else if (setting instanceof TextSetting) {
            this.elements.add(new InputObject(this, (TextSetting) setting));
         }
      }

   }

   public void render(MatrixStack ms,int mouseX, int mouseY) {
      int offset = 0;
      Iterator var4 = this.elements.iterator();

      while(var4.hasNext()) {
         Component element = (Component)var4.next();
         if (element.isVisible()) {
            offset = (int)((float)offset + element.height);
         }
      }

      float normalHeight = this.height + (float)offset;
      Color color = Main.getInstance().getThemeManager().getCurrentStyleTheme().getColors()[0];
      Color color2 = Main.getInstance().getThemeManager().getCurrentStyleTheme().getColors()[1];
      boolean isDark = true;
      Color moduleColor = isDark ? new Color(34, 34, 34) : new Color(210, 210, 210);
      RenderUtility.drawShadow(this.x + 1.0F, this.y + normalHeight - 1.0F, (float)((int)this.width - 2), 2.0F, 4, color.getRGB(), color2.getRGB(), color.getRGB(), color2.getRGB());
      RenderUtility.drawRoundedGradientRect(this.x + 0.5F, this.y + normalHeight - 8.0F, this.width - 1.0F, 9.0F, 5.0F, 1.0F, color.getRGB(), color.getRGB(), color2.getRGB(), color2.getRGB());
      RenderUtility.drawRoundedRect(this.x, this.y, this.width, normalHeight, 5.0F, moduleColor.getRGB());
      if (!this.module.enabled) {
         Fonts.INTER_BOLD.get(16).draw(ms, this.binding ? "Press a key... " : this.module.getName(), this.x + 5.0F, this.y + 5.0F, isDark ? Color.WHITE.getRGB() : (new Color(40, 40, 40)).getRGB());
      }   if (this.module.enabled) {
         Fonts.INTER_BOLD.get(16).drawGradient(ms, this.binding ? "Press a key... " : this.module.getName(), this.x + 5.0F, this.y + 5.0F, color.getRGB(), color2.getRGB());
      }


      RenderUtility.drawRect(this.x + 5.0F, this.y + 5.0F + (float)Fonts.INTER_BOLD.get(14).getHeight() + 3.0F, this.width - 10.0F, 1.0F, isDark ? (new Color(54, 54, 54)).getRGB() : (new Color(170, 170, 170)).getRGB());
      Fonts.INTER_BOLD.get(14).draw(ms, "Enabled", this.x + 5.0F, this.y + 5.0F + (float)Fonts.INTER_BOLD.get(14).getHeight() + 9.0F, isDark ? Color.WHITE.getRGB() : (new Color(55, 55, 55)).getRGB());
      this.enableAnimation = AnimationMath.fast(this.enableAnimation, this.module.enabled ? -1.0F : 0.0F, 15.0F);
      RenderUtility.drawRoundedRect(this.x + this.width - 25.0F, this.y + 5.0F + (float)Fonts.INTER_BOLD.get(14).getHeight() + 6.0F, 20.0F, 10.0F, 6.0F, isDark ? (new Color(25, 25, 25)).getRGB() : (new Color(160, 160, 160)).getRGB());
      Color c = ColorUtil.interpolateColorC(isDark ? (new Color(34, 34, 34)).getRGB() : Color.WHITE.getRGB(), isDark ? Color.WHITE.getRGB() : (new Color(100, 100, 100)).getRGB(), Math.abs(this.enableAnimation));
      RenderUtility.drawRoundedRect(this.x + this.width - 23.5F - this.enableAnimation * 10.0F, this.y + 6.5F + (float)Fonts.INTER_BOLD.get(14).getHeight() + 6.0F, 7.0F, 7.0F, 6.0F, c.getRGB());
      offset = 0;
      Iterator var10 = this.elements.iterator();

      while(var10.hasNext()) {
         Component element = (Component)var10.next();
         if (element.isVisible()) {
            element.x = this.x;
            element.y = this.y + 29.0F + (float)offset;
            element.width = this.width;
            element.render(ms, mouseX, mouseY);
            offset = (int)((float)offset + element.height);
         }
      }

   }

   public void mouseClicked(double mouseX, double mouseY, int mouseButton) {
      super.mouseClicked(mouseX, mouseY, mouseButton);
      if (this.binding && mouseButton > 2) {
         this.module.bind = mouseButton - 100;
         this.binding = false;
      }

      boolean enableButtonHovered = MathUtility.isHovered(mouseX, mouseY, (double)(this.x + this.width - 25.0F), (double)(this.y + 5.0F + (float)Fonts.INTER_BOLD.get(14).getHeight() + 6.0F), 20.0D, 10.0D);
      boolean isTitleHovered = MathUtility.isHovered(mouseX, mouseY, (double)this.x, (double)this.y, (double)this.width, (double)(Fonts.INTER_BOLD.get(14).getHeight() + 8));
      if (enableButtonHovered && mouseButton == 0) {
         this.module.toggle();
      } else if (isTitleHovered && mouseButton == 2) {
         this.binding = !this.binding;
      }

      Iterator var8 = this.elements.iterator();

      while(var8.hasNext()) {
         Component element = (Component)var8.next();
         element.mouseClicked(mouseX, mouseY, mouseButton);
      }

   }

   public void keyTyped(int keyCode) {
      super.keyTyped(keyCode);
      if (this.binding) {
         if (keyCode == 1) {
            Gui.escapeInUse = true;
            this.binding = false;
            return;
         }

         if (keyCode == 211) {
            this.module.bind = 0;
         } else {
            this.module.bind = keyCode;
         }

         this.binding = false;
      }

   }

   public Module getModule() {
      return this.module;
   }
}
