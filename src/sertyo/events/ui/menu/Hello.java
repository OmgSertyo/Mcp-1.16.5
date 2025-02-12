package sertyo.events.ui.menu;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.gui.screen.MainMenuScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.multiplayer.ServerList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import sertyo.events.util.Util;
import sertyo.events.util.animation.AnimationMath;
import sertyo.events.util.math.MathUtility;
import sertyo.events.util.render.ColorUtil;
import sertyo.events.util.render.RenderUtility;
import sertyo.events.util.render.font.Fonts;

import java.awt.*;
import java.util.ArrayList;



public class Hello extends Screen implements Util {
    public Hello(ITextComponent titleIn) {
        super(titleIn);
    }

    float anim1 = 0,anim2 = 0, panelAnim = 0, stage2 = 0, animFT = 0,animRW = 0, endAnim = 0;
    int ticks = 0, stage = 0, selected_cfg = -1;

    ArrayList<String> selectedServers = new ArrayList<>();
    float animFT2 = 0, animRW2 = 0, animFS2 = 0, animHW2 = 0;

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        ticks ++;
        super.render(matrixStack, mouseX, mouseY, partialTicks);
        RenderUtility.drawRect(0,0,mw.getScaledWidth(),mw.getScaledHeight(),new Color(10,10,10).getRGB());
//        anim1 = 0;
        if(ticks>50)
            anim1 = AnimationMath.fast(anim1, 1,5);
        if(ticks>200) {
            anim2 = AnimationMath.fast(anim2,stage>=2?0:1,5);
            endAnim = AnimationMath.fast(endAnim,stage>=2?1:0,5);
            panelAnim = AnimationMath.fast(panelAnim,(stage==0?1:0),5);
            stage2 = AnimationMath.fast(stage2,stage==1?1:0,5);
        }


            Fonts.INTER_BOLD.get(40).drawGradient(matrixStack, "Привет!", (mw.getScaledWidth()/2f)-Fonts.INTER_BOLD.get(40).getWidth("Привет!")/2f,mw.getScaledHeight()/2f-10*anim1-90*anim2, new Color(4, 180, 255).getRGB(), new Color(16, 31, 255).getRGB());

        Fonts.INTER_BOLD.get(20).drawCenter(matrixStack,"Давай настроим клиент под тебя?",mw.getScaledWidth()/2f,mw.getScaledHeight()/2f+10*anim1-80*anim2, ColorUtil.getColor(255,255,255, (int) (255*anim1)));

        drawFirstStage(matrixStack);

      //  drawSecondStage(matrixStack);

        RenderUtility.drawRoundedRect(mw.getScaledWidth()/2f,mw.getScaledHeight()/2f+25*endAnim,60,20,4,ColorUtil.getColor(40, 150, 190, (int) (255*endAnim)));
      //  Fonts.INTER_BOLD.get(17).drawCenter(matrixStack,"Закончим?",mw.getScaledWidth()/2f+30,mw.getScaledHeight()/2f+33*endAnim,ColorUtil.getColor(255, 255, 255, (int) (255*endAnim)));

        //Fonts.INTER_BOLD.get(17).drawCenter(matrixStack, "Назад", mw.getScaledWidth() / 2f - (50*(endAnim)), mw.getScaledHeight() / 2f + 33 * endAnim, ColorUtil.getColor(155, 155, 155, (int) (255 * ((1*endAnim)))));
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {

        if(stage==0){
            if(MathUtility.isHovered(mouseX,mouseY,mw.getScaledWidth() / 2f - 100, mw.getScaledHeight() / 2f - 25, 100, 25)){
                if(selected_cfg==0){
                    selected_cfg = -1;
                    return false;
                }
                selected_cfg = 0;
                return false;
            }
            if(MathUtility.isHovered(mouseX,mouseY,mw.getScaledWidth() / 2f, mw.getScaledHeight() / 2f - 25, 100, 25)){
                if(selected_cfg==1){
                    selected_cfg = -1;
                    return false;
                }
                selected_cfg = 1;
                return false;
            }
        }
        if(stage==1){
            if(MathUtility.isHovered(mouseX,mouseY,mw.getScaledWidth() / 2f - 100, mw.getScaledHeight() / 2f - 60, 100, 25)){
                if(selectedServers.contains("funtime")){
                    selectedServers.remove("funtime");
                    return false;
                }
                selectedServers.add("funtime");
                return false;
            }
            if(MathUtility.isHovered(mouseX,mouseY,mw.getScaledWidth() / 2f, mw.getScaledHeight() / 2f - 60, 100, 25)){
                if(selectedServers.contains("reallyworld")){
                    selectedServers.remove("reallyworld");
                    return false;
                }
                selectedServers.add("reallyworld");
                return false;
            }
            if(MathUtility.isHovered(mouseX,mouseY,mw.getScaledWidth() / 2f, mw.getScaledHeight() / 2f - 35, 100, 25)){
                if(selectedServers.contains("funsky")){
                    selectedServers.remove("funsky");
                    return false;
                }
                selectedServers.add("funsky");
                return false;
            }
            if(MathUtility.isHovered(mouseX,mouseY,mw.getScaledWidth() / 2f - 100 * stage2, mw.getScaledHeight() / 2f - 35, 100, 25)){
                if(selectedServers.contains("holyworld")){
                    selectedServers.remove("holyworld");
                    return false;
                }
                selectedServers.add("holyworld");
                return false;
            }
        }


        if(MathUtility.isHovered(mouseX,mouseY,mw.getScaledWidth() / 2f + (stage==0?0:50*(1-panelAnim))-Fonts.INTER_BOLD.get(17).getWidth("Пропустить")/2f, mw.getScaledHeight() / 2f + 33,Fonts.INTER_BOLD.get(17).getWidth("Пропустить"),Fonts.INTER_BOLD.get(17).getHeight())){
            if(stage<2)
                stage++;
            return false;
        }
        if(MathUtility.isHovered(mouseX,mouseY,mw.getScaledWidth() / 2f - (stage==0?0:50*(1-panelAnim))-Fonts.INTER_BOLD.get(17).getWidth("Назад")/2f, mw.getScaledHeight() / 2f + 33,Fonts.INTER_BOLD.get(17).getWidth("Назад"),Fonts.INTER_BOLD.get(17).getHeight())){
            if(stage>0)
                stage--;
            return false;
        }
        if(MathUtility.isHovered(mouseX,mouseY,mw.getScaledWidth()/2f,mw.getScaledHeight()/2f+25,60,20)){
            if(stage>=2){

                ServerList s = new ServerList(mc);

                if(selectedServers.contains("funtime"))
                    s.addServerData(new ServerData("FunTime","funtime.su",false));
                if(selectedServers.contains("funsky"))
                    s.addServerData(new ServerData("FunSky","mc.funsky.su",false));
                if(selectedServers.contains("reallyworld"))
                    s.addServerData(new ServerData("ReallyWorld","mc.reallyworld.ru",false));
                if(selectedServers.contains("holyworld"))
                    s.addServerData(new ServerData("HolyWorld","mc.holyworld.ru",false));
                s.saveServerList();


                if(selected_cfg==0){ //ft
                    try {
                       // String url = "\uE068\uE074\uE074\uE070:\uE02F\uE02F\uE035\uE02E\uE033\uE035\uE02E\uE031\uE031\uE034\uE02E\uE031\uE035\uE031\uE02F\uE068\uE061\uE063\uE068\uE02F\uE061\uE070\uE069\uE02F\uE076\uE031\uE02F\uE064\uE06F\uE077\uE06E\uE06C\uE06F\uE061\uE064\uE02F\uE066\uE074\uE02E\uE063\uE066\uE067";

                        //MainUtil.downloadFile(StringHelper.decrypt(url), "C:/hachrecode/configs/ft.cfg");

                       // Manager.configs.loadConfiguration("ft.cfg",false);
                       // Manager.notifications.add("Загрузил конфиг ft","",3, Type.INFO);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
                if(selected_cfg==1){
                    try {
                        /*String url = "\uE068\uE074\uE074\uE070:\uE02F\uE02F\uE035\uE02E\uE033\uE035\uE02E\uE031\uE031\uE034\uE02E\uE031\uE035\uE031\uE02F\uE068\uE061\uE063\uE068\uE02F\uE061\uE070\uE069\uE02F\uE076\uE031\uE02F\uE064\uE06F\uE077\uE06E\uE06C\uE06F\uE061\uE064\uE02F\uE072\uE077\uE02E\uE063\uE066\uE067";
                        MainUtil.downloadFile(StringHelper.decrypt(url), "C:/hachrecode/configs/rw.cfg");

                        Manager.configs.loadConfiguration("rw.cfg",false);
                        Manager.notifications.add("Загрузил конфиг rw","",3, Type.INFO);*/
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }

                mc.displayGuiScreen(new MainMenuScreen(true));
            }
            return false;
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }
    public void drawFirstStage(MatrixStack matrixStack){
        animFT = AnimationMath.fast(animFT, selected_cfg==0?1:0,5);
        animRW = AnimationMath.fast(animRW, selected_cfg==1?1:0, 5);

        Fonts.INTER_BOLD.get(17).drawCenter(matrixStack, "Начнем с выбора конфига?", mw.getScaledWidth() / 2f, mw.getScaledHeight() / 2f + 10 * anim1 - 50 * panelAnim, ColorUtil.getColor(255, 255, 255, (int) (255 * panelAnim)));

        Fonts.INTER_BOLD.get(17).drawCenter(matrixStack, "Пропустить", mw.getScaledWidth() / 2f + (50*(1-panelAnim)), mw.getScaledHeight() / 2f + 10 * anim1 + 25 * anim2, ColorUtil.getColor(155, 155, 155, (int) (255 * anim2)));
        Fonts.INTER_BOLD.get(17).drawCenter(matrixStack, "Назад", mw.getScaledWidth() / 2f - (50*(1-panelAnim)), mw.getScaledHeight() / 2f + 10 * anim1 + 25 * anim2, ColorUtil.getColor(155, 155, 155, (int) (255 * ((1*anim2)-panelAnim))));

        int c1 = ColorUtil.gradient(5,0,new Color(4, 180, 255).getRGB(), new Color(16, 31, 255).getRGB());
        int c2 = ColorUtil.gradient(5,90,new Color(4, 180, 255).getRGB(), new Color(16, 31, 255).getRGB());
        int c3 = ColorUtil.gradient(5,0,new Color(4, 180, 255).getRGB(), new Color(16, 31, 255).getRGB());
        int c4 = ColorUtil.gradient(5,90,new Color(4, 180, 255).getRGB(), new Color(16, 31, 255).getRGB());

        RenderUtility.drawRoundedRect(mw.getScaledWidth() / 2f - 100 * panelAnim, mw.getScaledHeight() / 2f - 25, 100, 25, 8, ColorUtil.getColor(25, (25), 25, (int) (255 * panelAnim)));
        RenderUtility.drawRoundedGradientRect(mw.getScaledWidth() / 2f - 100 * panelAnim + 1, mw.getScaledHeight() / 2f - 25 + 1, 100 - 2, 25 - 2, 8,
                ColorUtil.reAlphaInt( c1, (int) (155*animFT*panelAnim)),
                ColorUtil.reAlphaInt(c2, (int) (155*animFT*panelAnim)),
                ColorUtil.reAlphaInt(c3, (int) (155*animFT*panelAnim)),
                ColorUtil.reAlphaInt(c4, (int) (155*animFT*panelAnim))
        );
        Fonts.INTER_BOLD.get(20).drawCenter(matrixStack, "Конфиг " + TextFormatting.RED + "F" + TextFormatting.WHITE + "T", mw.getScaledWidth() / 2f - 50 * panelAnim, mw.getScaledHeight() / 2f - 16, ColorUtil.getColor(255, 255, 255, (int) (255 * panelAnim)));

        RenderUtility.drawRoundedRect(mw.getScaledWidth() / 2f, mw.getScaledHeight() / 2f - 25, 100, 25, 8, ColorUtil.getColor(25, (25), 25, (int) (255 * panelAnim)));
        RenderUtility.drawRoundedGradientRect(mw.getScaledWidth() / 2f+1, mw.getScaledHeight() / 2f - 25+1, 100-2, 25-2, 8,
                ColorUtil.reAlphaInt(c1, (int) (155*animRW*panelAnim)),
                ColorUtil.reAlphaInt(c2, (int) (155*animRW*panelAnim)),
                ColorUtil.reAlphaInt(c3, (int) (155*animRW*panelAnim)),
                ColorUtil.reAlphaInt(c4, (int) (155*animRW*panelAnim))
        );
        Fonts.INTER_BOLD.get(20).drawCenter(matrixStack, "Конфиг R" + TextFormatting.GOLD + "W", mw.getScaledWidth() / 2f + 50 * panelAnim, mw.getScaledHeight() / 2f - 16, ColorUtil.getColor(255, 255, 255, (int) (255 * panelAnim)));

    }
    public void drawSecondStage(MatrixStack matrixStack){
        animFT2 = AnimationMath.fast(animFT2, selectedServers.contains("funtime")?1:0,5);
        animFS2 = AnimationMath.fast(animFS2, selectedServers.contains("funsky")?1:0,5);
        animRW2 = AnimationMath.fast(animRW2, selectedServers.contains("reallyworld")?1:0,5);
        animHW2 = AnimationMath.fast(animHW2, selectedServers.contains("holyworld")?1:0,5);

        Fonts.INTER_BOLD.get(17).drawCenter(matrixStack, "Какие сервера добавить в список?", mw.getScaledWidth() / 2f, mw.getScaledHeight() / 2f + 10 * anim1 - 50 * panelAnim, ColorUtil.getColor(255, 255, 255, (int) (255 * stage2)));

        Fonts.INTER_BOLD.get(17).drawCenter(matrixStack, "Пропустить", mw.getScaledWidth() / 2f + (50*(1-panelAnim)), mw.getScaledHeight() / 2f + 10 * anim1 + 25 * anim2, ColorUtil.getColor(155, 155, 155, (int) (255 * anim2)));
        Fonts.INTER_BOLD.get(17).drawCenter(matrixStack, "Назад", mw.getScaledWidth() / 2f - (50*(1-panelAnim)), mw.getScaledHeight() / 2f + 10 * anim1 + 25 * anim2, ColorUtil.getColor(155, 155, 155, (int) (255 * ((1*anim2)-panelAnim))));

        int c1 = ColorUtil.gradient(5,0,new Color(4, 180, 255).getRGB(), new Color(16, 31, 255).getRGB());
        int c2 = ColorUtil.gradient(5,90,new Color(4, 180, 255).getRGB(), new Color(16, 31, 255).getRGB());
        int c3 = ColorUtil.gradient(5,0,new Color(4, 180, 255).getRGB(), new Color(16, 31, 255).getRGB());
        int c4 = ColorUtil.gradient(5,90,new Color(4, 180, 255).getRGB(), new Color(16, 31, 255).getRGB());

        RenderUtility.drawRoundedRect(mw.getScaledWidth() / 2f - 100 * stage2, mw.getScaledHeight() / 2f - 60, 100, 25, 8, ColorUtil.getColor(25, (25), 25, (int) (255 * stage2)));
        RenderUtility.drawRoundedGradientRect(mw.getScaledWidth() / 2f - 100 * stage2 + 1, mw.getScaledHeight() / 2f - 60 + 1, 100 - 2, 25 - 2, 8,
                ColorUtil.reAlphaInt( c1, (int) (155*animFT2*stage2)),
                ColorUtil.reAlphaInt(c2, (int) (155*animFT2*stage2)),
                ColorUtil.reAlphaInt(c3, (int) (155*animFT2*stage2)),
                ColorUtil.reAlphaInt(c4, (int) (155*animFT2*stage2))
        );
        Fonts.INTER_BOLD.get(20).drawCenter(matrixStack, TextFormatting.RED + "Fun" + TextFormatting.WHITE + "Time", mw.getScaledWidth() / 2f - 50 * stage2, mw.getScaledHeight() / 2f - 50, ColorUtil.getColor(255, 255, 255, (int) (255 * stage2)));

        RenderUtility.drawRoundedRect(mw.getScaledWidth() / 2f, mw.getScaledHeight() / 2f - 60, 100, 25, 8, ColorUtil.getColor(25, (25), 25, (int) (255 * stage2)));
        RenderUtility.drawRoundedGradientRect(mw.getScaledWidth() / 2f+1, mw.getScaledHeight() / 2f - 60+1, 100-2, 25-2, 8,
                ColorUtil.reAlphaInt(c1, (int) (155*animRW2*stage2)),
                ColorUtil.reAlphaInt(c2, (int) (155*animRW2*stage2)),
                ColorUtil.reAlphaInt(c3, (int) (155*animRW2*stage2)),
                ColorUtil.reAlphaInt(c4, (int) (155*animRW2*stage2))
        );
        Fonts.INTER_BOLD.get(20).drawCenter(matrixStack, "Really" + TextFormatting.GOLD + "World", mw.getScaledWidth() / 2f + 50 * stage2, mw.getScaledHeight() / 2f - 50, ColorUtil.getColor(255, 255, 255, (int) (255 * stage2)));

        RenderUtility.drawRoundedRect(mw.getScaledWidth() / 2f, mw.getScaledHeight() / 2f - 35, 100, 25, 8, ColorUtil.getColor(25, (25), 25, (int) (255 * stage2)));
        RenderUtility.drawRoundedGradientRect(mw.getScaledWidth() / 2f+1, mw.getScaledHeight() / 2f - 35+1, 100-2, 25-2, 8,
                ColorUtil.reAlphaInt(c1, (int) (155*animFS2*stage2)),
                ColorUtil.reAlphaInt(c2, (int) (155*animFS2*stage2)),
                ColorUtil.reAlphaInt(c3, (int) (155*animFS2*stage2)),
                ColorUtil.reAlphaInt(c4, (int) (155*animFS2*stage2))
        );
        Fonts.INTER_BOLD.get(20).drawCenter(matrixStack, "Fun" + TextFormatting.DARK_PURPLE + "Sky", mw.getScaledWidth() / 2f + 50 * stage2, mw.getScaledHeight() / 2f - 25, ColorUtil.getColor(255, 255, 255, (int) (255 * stage2)));

        RenderUtility.drawRoundedRect(mw.getScaledWidth() / 2f - 100 * stage2, mw.getScaledHeight() / 2f - 35, 100, 25, 8, ColorUtil.getColor(25, (25), 25, (int) (255 * stage2)));
        RenderUtility.drawRoundedGradientRect(mw.getScaledWidth() / 2f - 100 * stage2 + 1, mw.getScaledHeight() / 2f - 35 + 1, 100 - 2, 25 - 2, 8,
                ColorUtil.reAlphaInt( c1, (int) (155*animHW2*stage2)),
                ColorUtil.reAlphaInt(c2, (int) (155*animHW2*stage2)),
                ColorUtil.reAlphaInt(c3, (int) (155*animHW2*stage2)),
                ColorUtil.reAlphaInt(c4, (int) (155*animHW2*stage2))
        );
        Fonts.INTER_BOLD.get(20).drawCenter(matrixStack, TextFormatting.BLUE + "HolyWorld", mw.getScaledWidth() / 2f - 50 * stage2, mw.getScaledHeight() / 2f - 25, ColorUtil.getColor(255, 255, 255, (int) (255 * stage2)));

    }
}
