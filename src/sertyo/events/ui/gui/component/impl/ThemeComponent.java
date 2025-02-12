package sertyo.events.ui.gui.component.impl;


import com.mojang.blaze3d.matrix.MatrixStack;
import lombok.Getter;
import sertyo.events.Main;
import sertyo.events.manager.theme.Theme;
import sertyo.events.ui.gui.component.Component;
import sertyo.events.util.math.MathUtility;
import sertyo.events.util.render.RenderUtility;
import sertyo.events.util.render.font.Fonts;

import java.awt.*;

@Getter
public class ThemeComponent extends Component {
   private final Theme theme;

   public ThemeComponent(Theme theme, float width, float height) {
      super(0.0F, 0.0F, width, height);
      this.theme = theme;
   }

   public void render(MatrixStack ms, int mouseX, int mouseY) {
       if (Main.getInstance().getThemeManager().getCurrentStyleTheme().equals(this.theme) || Main.getInstance().getThemeManager().getCurrentGuiTheme().equals(this.theme)) {
         RenderUtility.drawRoundedRect(this.x - 1.0F, this.y - 1.0F, this.width + 2.0F, this.height + 2.0F, 8.0F, Color.WHITE.getRGB());
      }

      RenderUtility.drawRoundedRect(this.x, this.y, this.width, this.height, 7.0F, new Color(34, 34, 34).getRGB());
      if (this.theme.getType().equals(Theme.ThemeType.GUI)) {
         RenderUtility.drawRoundedGradientRect(this.x, this.y, this.width, this.height - 12.0F, 7.0F, 0.0F, 7.0F, 0.0F, 1.0F, this.theme.getColors()[0].getRGB(), this.theme.getColors()[0].getRGB(), this.theme.getColors()[0].getRGB(), this.theme.getColors()[0].getRGB());
      } else {
         RenderUtility.drawRoundedGradientRect(this.x, this.y, this.width, this.height - 12.0F, 7.0F, 0.0F, 7.0F, 0.0F, 1.0F, this.theme.getColors()[0].getRGB(), this.theme.getColors()[0].getRGB(), this.theme.getColors()[1].getRGB(), this.theme.getColors()[1].getRGB());
      }

      Fonts.INTER_BOLD.get(14).drawCenter(ms, this.theme.getName(), this.x + this.width / 2.0F, this.y + 22.0F, Color.WHITE.getRGB());
   }

   public void mouseClicked(double mouseX, double mouseY, int mouseButton) {
      if (MathUtility.isHovered(mouseX, mouseY, this.x, this.y, this.width, this.height)) {
         if (this.theme.getType().equals(Theme.ThemeType.GUI)) {
            Main.getInstance().getThemeManager().setCurrentGuiTheme(this.theme);
         } else {
            Main.getInstance().getThemeManager().setCurrentStyleTheme(this.theme);
         }
      }

   }

}
