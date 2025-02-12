package sertyo.events.util;

import net.minecraft.client.MainWindow;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import sertyo.events.Main;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public interface Util  {
    Tessellator TESSELLATOR = Tessellator.getInstance();
    BufferBuilder BUFFER = TESSELLATOR.getBuffer();
    Executor THREAD_POOL = Executors.newFixedThreadPool(1);
    Minecraft mc = Minecraft.getInstance();
    MainWindow mw = mc.getMainWindow();
    Main main = Main.getInstance();

}
