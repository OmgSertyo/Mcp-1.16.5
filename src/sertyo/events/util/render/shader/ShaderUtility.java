package sertyo.events.util.render.shader;

import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.shader.Framebuffer;
import org.lwjgl.opengl.GL11;
import sertyo.events.Main;
import sertyo.events.util.Util;

public class ShaderUtility implements Util {
   public static Framebuffer createFrameBuffer(Framebuffer framebuffer) {
      if (framebuffer != null && framebuffer.framebufferWidth == mc.getMainWindow().getWidth() && framebuffer.framebufferHeight == mc.getMainWindow().getHeight()) {
         return framebuffer;
      } else {
         if (framebuffer != null) {
            framebuffer.deleteFramebuffer();
         }
         boolean ismac;
         if (System.getProperty("os.name").toLowerCase().contains("mac"))
            ismac = true;
         else
            ismac = false;
         return new Framebuffer(mc.getMainWindow().getWidth(), mc.getMainWindow().getHeight(), true, ismac);
      }
   }

   public static void bindTexture(int texture) {
      GlStateManager.bindTexture(texture);
   }

   public static void drawQuads() {
      float width = (float) Main.getInstance().getScaleMath().calc(mc.getMainWindow().getScaledWidth());
      float height = (float) Main.getInstance().getScaleMath().calc(mc.getMainWindow().getScaledHeight());
      GL11.glBegin(7);
      GL11.glTexCoord2f(0.0F, 1.0F);
      GL11.glVertex2f(0.0F, 0.0F);
      GL11.glTexCoord2f(0.0F, 0.0F);
      GL11.glVertex2f(0.0F, height);
      GL11.glTexCoord2f(1.0F, 0.0F);
      GL11.glVertex2f(width, height);
      GL11.glTexCoord2f(1.0F, 1.0F);
      GL11.glVertex2f(width, 0.0F);
      GL11.glEnd();
   }
}
