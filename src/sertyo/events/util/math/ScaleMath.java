package sertyo.events.util.math;

import net.minecraft.client.MainWindow;
import net.minecraft.client.Minecraft;

public class ScaleMath {
   private int scale;
   private MainWindow mw = Minecraft.getInstance().getMainWindow();
   public void pushScale() {
      Minecraft.getInstance().gameRenderer.setupOverlayRendering(scale);
   }

   public void popScale() {
      Minecraft.getInstance().gameRenderer.setupOverlayRendering();
   }

   public int calc(int value) {
      return (int) (value * mw.getGuiScaleFactor() / this.scale);
   }


   public Vec2i getMouse(int mouseX, int mouseY, MainWindow mw) {
      return new Vec2i((int) (mouseX * mw.getGuiScaleFactor() / this.scale), (int) (mouseY * mw.getGuiScaleFactor() / this.scale));
   }

   public ScaleMath(int scale) {
      this.scale = scale;
   }

   public int getScale() {
      return this.scale;
   }

   public void setScale(int scale) {
      this.scale = scale;
   }
}
