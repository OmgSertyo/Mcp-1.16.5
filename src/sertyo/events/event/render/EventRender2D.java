package sertyo.events.event.render;

import com.darkmagician6.eventapi.events.Event;
import com.mojang.blaze3d.matrix.MatrixStack;
import lombok.Getter;
import net.minecraft.client.MainWindow;

public class EventRender2D implements Event {
   private MainWindow resolution;
   private float partialTicks;
   @Getter
   private MatrixStack matrixStack;

   public MainWindow getResolution() {
      return this.resolution;
   }

   public float getPartialTicks() {
      return this.partialTicks;
   }

   public EventRender2D(MatrixStack ms, MainWindow resolution, float partialTicks) {
      this.matrixStack = ms;
      this.resolution = resolution;
      this.partialTicks = partialTicks;
   }
}
