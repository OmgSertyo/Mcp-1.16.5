package sertyo.events.util.animation;

import net.minecraft.client.Minecraft;
import sertyo.events.util.math.MathUtility;

import static net.minecraft.util.math.MathHelper.clamp;

public class AnimationMath {
   public static double deltaTime() {
      return Minecraft.getDebugFPS() > 0 ? 1.0D / (double)Minecraft.getDebugFPS() : 1.0D;
   }

   public static float fast(float end, float start, float multiple) {
      return (1.0F - MathUtility.clamp((float)(deltaTime() * (double)multiple), 0.0F, 1.0F)) * end + MathUtility.clamp((float)(deltaTime() * (double)multiple), 0.0F, 1.0F) * start;
   }
   public static float lerp(float end, float start, float multiple) {
      return (float) (end + (start - end) * clamp(AnimationMath.deltaTime() * multiple, 0, 1));
   }
}
