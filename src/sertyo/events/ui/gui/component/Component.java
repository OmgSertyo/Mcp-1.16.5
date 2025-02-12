package sertyo.events.ui.gui.component;

import com.mojang.blaze3d.matrix.MatrixStack;
import lombok.Setter;

@Setter
public class Component {
   public float x;
   public float y;
   public float width;
   public float height;

   public Component(float x, float y, float width, float height) {
      this.x = x;
      this.y = y;
      this.width = width;
      this.height = height;
   }

   public void render(MatrixStack ms,int mouseX, int mouseY) {
   }

   public void mouseClicked(double mouseX, double mouseY, int mouseButton) {
   }

   public void mouseReleased(double mouseX, double mouseY, int state) {
   }

   public void keyTyped(int keyCode) {
   }

   public boolean isVisible() {
      return true;
   }

}
