package sertyo.events.ui.alt;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.IGuiEventListener;
import net.minecraft.client.gui.screen.MainMenuScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.gui.widget.button.AbstractButton;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;
import sertyo.events.util.NameGeneratorHelper;
import sertyo.events.util.Util;
import sertyo.events.util.animation.Animation;
import sertyo.events.util.animation.AnimationMath;
import sertyo.events.util.animation.Direction;
import sertyo.events.util.animation.impl.DecelerateAnimation;
import sertyo.events.util.math.MathUtility;
import sertyo.events.util.math.ScaleMath;
import sertyo.events.util.math.Vec2i;
import sertyo.events.util.render.ColorUtil;
import sertyo.events.util.render.RenderUtility;
import sertyo.events.util.render.SmartScissor;
import sertyo.events.util.render.font.Font;
import sertyo.events.util.render.font.Fonts;


import java.awt.*;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.List;

/**
 Alt manager SPIZHEN from hachclient
 **/
public class AltManager extends Screen implements Util {

    public AltManager() {
        super(new StringTextComponent(""));
        updateAccountClasses();
    }

    public static ArrayList<Account> accounts = new ArrayList<>();
    public static ArrayList<Account> accountsToDelete = new ArrayList<>();

    protected final List<IGuiEventListener> children = Lists.newArrayList();

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        return super.mouseReleased(mouseX, mouseY, button);
    }


    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {


        if(typing){
            if (keyCode == GLFW.GLFW_KEY_BACKSPACE) {
                if (!altName.isEmpty()) altName = altName.substring(0, altName.length() - 1);
            }

            if (keyCode == GLFW.GLFW_KEY_ENTER) {
                if (!altName.isEmpty())
                    accounts.add(new Account(altName));
                typing = false;
            }
            boolean isControlPressed = (modifiers & GLFW.GLFW_MOD_CONTROL) != 0;
            if ((keyCode == GLFW.GLFW_KEY_V || keyCode == GLFW.GLFW_KEY_M) && isControlPressed) {
                String buffer = mc.keyboardListener.getClipboardString();
                ArrayList<String> textToPaste = new ArrayList<>();
                char[] chars = buffer.toCharArray();
                for (char c : chars) {
                    if (c != ' ') { // Игнорируем пробелы
                        textToPaste.add(String.valueOf(c));
                    }
                }
                altName += String.join("", textToPaste);
            }
        }else if(typing_pass){
            if (keyCode == GLFW.GLFW_KEY_BACKSPACE) {
                if (!password.isEmpty()) password = password.substring(0, password.length() - 1);
            }

            if (keyCode == GLFW.GLFW_KEY_ENTER) {
//                if (!password.isEmpty()&&!altName.isEmpty())
//                    authAccount(altName,password);
                typing_pass = false;
            }
        }

        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    @Override
    public boolean charTyped(char codePoint, int modifiers) {
        if(typing)altName += Character.toString(codePoint);
        else if(typing_pass)password += Character.toString(codePoint);
        return super.charTyped(codePoint, modifiers);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        Vec2i fixed = main.getScaleMath().getMouse((int) mouseX, (int) mouseY, mc.getMainWindow());
        mouseX = fixed.getX();
        mouseY = fixed.getY();

        for (Account account : accounts) {
            account.onClick(mouseX,mouseY,button);
        }
        accounts.removeAll(accountsToDelete);
        accountsToDelete.clear();
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public void tick() {
        super.tick();
    }

    public float scroll;
    public float scrollAn;

    public boolean hoveredFirst;
    public boolean hoveredSecond;

    public float hoveredFirstAn;
    public float hoveredSecondAn;

    TextFieldWidget textUsername;

    private String altName = "",password="";
    private boolean typing,typing_pass;

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double delta) {
        scroll += (float) (delta * 30);
        return super.mouseScrolled(mouseX, mouseY, delta);
    }

    float anim_name,anim_pass;


    public void updateAccountClasses(){
//        Collections.reverse(accounts);
    }

    @Override
    protected void init() {
        updateAccountClasses();

        this.textUsername = new TextFieldWidget(this.font, (int) (mw.scaledWidth/2f-100), (int) (mw.scaledHeight/2f-160),200,20, new TranslationTextComponent("addServer.enterName"));
        this.textUsername.setFocused2(false);
        this.addButton(this.textUsername);

        this.addButton(new net.minecraft.client.gui.widget.button.Button((int) (mw.scaledWidth/2f-150), (int) (mw.scaledHeight/2f-190),100,20,new StringTextComponent("Рандом"),(p)->{
            accounts.add(new Account(NameGeneratorHelper.getMEGANIGGA()/*StringHelper.randomString(8)*/,System.currentTimeMillis()));
        }));
        this.addButton(new net.minecraft.client.gui.widget.button.Button((int)(mw.scaledWidth/2f-45),(int)(mw.scaledHeight/2f-190),100,20,new StringTextComponent("Добавить"),(p)->{
            accounts.add(new Account(textUsername.getText(),System.currentTimeMillis()));
            textUsername.setText("");
            textUsername.setFocused2(false);
        }));
        this.addButton(new net.minecraft.client.gui.widget.button.Button((int)(mw.scaledWidth/2f+60),(int)(mw.scaledHeight/2f-190),100,20,new StringTextComponent("Очистить"),(p)->{
            accounts.clear();
        }));

        super.init();
    }

    @Override
    public void init(Minecraft minecraft, int width, int height) {
        super.init(minecraft, width, height);
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {

        scrollAn = AnimationMath.lerp(scrollAn, scroll, 10);
//        mouseX = v.getX();
//        mouseY = v.getY();
        hoveredFirst = MathUtility.isHovered(mouseX, mouseY, 345 / 2f, 664 / 2f, 249 / 2f, 46 / 2f);
        hoveredSecond = MathUtility.isHovered(mouseX, mouseY, 345 / 2f, 723 / 2f, 249 / 2f, 46 / 2f);
        hoveredFirstAn = AnimationMath.fast(hoveredFirstAn, hoveredFirst ? 1 : 0, 5);
        hoveredSecondAn = AnimationMath.fast(hoveredSecondAn, hoveredSecond ? 1 : 0, 5);

        float mouseDelta = ((mc.getMainWindow().getScaledWidth()/2f-mouseX)/100);
        float mouseDelta2 = ((mc.getMainWindow().getScaledHeight()/2f-mouseY)/100);

        super.renderBackground(matrixStack);

        mc.gameRenderer.setupOverlayRendering(2);

        float width = 778 / 2.3f;
        float height = 539 / 2f;

        float x = (220 / 2f), y = (327 / 2f);

        Fonts.INTER_BOLD.get(15).drawCenter(matrixStack, "Вы вошли как " +  mc.getSession().getUsername(), mw.scaledWidth/2f, mw.scaledHeight/2f-200, ColorUtil.rgba(255,255,255, 255));


        float altX = mw.scaledWidth/2f-250,
                altY = mw.scaledHeight/2f-170;


        float iter = scrollAn;
        float size = 0;
        RenderUtility.drawRoundedGradientRect(mw.scaledWidth/2f-(width/2f), mw.scaledHeight/2f-(height/2f), 778 / 2.3f+24, height+24,5,
                new Color(10,10,10).getRGB(),
                new Color(10,10,10).getRGB(),
                new Color(10,10,10).getRGB(),
                new Color(30,30,30).getRGB()
        );
        SmartScissor.push();
        SmartScissor.setFromComponentCoordinates(mw.scaledWidth/2f-(width/2f), mw.scaledHeight/2f-(height/2f)+10, width, height);
        int count = 0;
        float yOffset = 0;
        float xOffset = 0;
        float allHeight = 0;

        if(accounts.isEmpty()){
            Fonts.INTER_BOLD.get(17).drawCenter(matrixStack,"Empty",mw.getScaledWidth()/2f,mw.getScaledHeight()/2f, ColorUtil.reAlphaInt(-1,200));
        }

        try {
            for (Account account : accounts) {
                float panWidth = 197 / 2f;

                account.width = 150;
                account.height = 70;
                if (count > 1) {
                    count = 0;
                    yOffset += account.height + 5;
                    xOffset = 0;
                }
                xOffset += account.width + 5;


                float acX = altX + 15 + (iter * (panWidth + 10));
                float acY = (442 / 2f) + yOffset;

                account.x = mw.scaledWidth / 2f + (160) - xOffset;
                account.y = mw.scaledHeight / 2f - (190 / 2f) + yOffset + (scrollAn);

                GL11.glPushMatrix();

                GL11.glTranslatef(account.x + account.width / 2f, account.y + account.height / 2f, 0);
                GL11.glScalef(0, 0, 0);
                GL11.glTranslatef(-(account.x + account.width / 2f), -(account.y + account.height / 2f), 0);

                account.render(matrixStack);

                //from end
                iter++;
                size++;
                count++;
                float headSize = 110 / 2f;
                GL11.glPopMatrix();
            }
        }catch (ConcurrentModificationException e){}

        scroll = MathHelper.clamp(scroll, -(yOffset), (0));

        SmartScissor.unset();
        SmartScissor.pop();
//        RenderUtil.Render2D.drawRoundedRect(mw.scaledWidth/2f-168,298 / 2f-MathHelper.clamp(scrollAn, -539 / 5.5f, 0),3,(539/2.6f)-(yOffset/size),2,new Color(100,100,100).getRGB());

        super.render(matrixStack,mouseX,mouseY,partialTicks);



        mc.gameRenderer.setupOverlayRendering();
    }

    public static class Button extends AbstractButton {

        public static final net.minecraft.client.gui.widget.button.Button.ITooltip field_238486_s_ = (button, matrixStack, mouseX, mouseY) ->
        {
        };
        protected final net.minecraft.client.gui.widget.button.Button.IPressable onPress;
        protected final net.minecraft.client.gui.widget.button.Button.ITooltip onTooltip;
        protected float animation_ = 0,hovered_time = 0;

        public Button(int x, int y, int width, int height, ITextComponent title, net.minecraft.client.gui.widget.button.Button.IPressable pressedAction) {
            this(x, y, width, height, title, pressedAction, field_238486_s_);
        }


        public Button(int x, int y, int width, int height, ITextComponent title, net.minecraft.client.gui.widget.button.Button.IPressable pressedAction, net.minecraft.client.gui.widget.button.Button.ITooltip onTooltip) {
            super(x, y, width, height, title);
            this.onPress = pressedAction;
            this.onTooltip = onTooltip;
        }



        public Animation animation = new DecelerateAnimation(300,1, Direction.BACKWARDS);

        public void renderButton(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
            mc.gameRenderer.setupOverlayRendering(2);
            GL11.glPushMatrix();
//            GL11.glTranslatef(x+width/2f,y+height/2f,0);
//            GL11.glScalef(animate.getOutput(),animate.getOutput(),0);
//            GL11.glTranslatef(-(x+width/2f),-(y+height/2f),0);
//            animation.setDirection(isHovered() ? Direction.FORWARDS : Direction.BACKWARDS);

            animation_ = AnimationMath.lerp(animation_, isHovered() ? 1 : 0, 5);

            hovered_time += isHovered() ? 4 : -4;
            hovered_time = MathHelper.clamp(hovered_time,0,361);

            int d = new Color(0,0,0,255).getRGB();
            int l = new Color(17,17,17,255).getRGB();

            int nurik = ColorUtil.reAlphaInt(new Color(ColorUtil.gradient(10,0,new Color(4,48,246).getRGB(),new Color(4,207,240).getRGB())).getRGB(), (int) (255*animation.getOutput()));
            int nurik2 = ColorUtil.reAlphaInt(new Color(ColorUtil.gradient(10, 90,new Color(4,48,246).getRGB(),new Color(4,207,240).getRGB())).getRGB(), (int) (255*animation.getOutput()));

            RenderUtility.drawShadow(x,y,width,height,16,
                    nurik,
                    nurik2,
                    nurik,
                    nurik2
            );
            RenderUtility.drawRoundedGradientRect(x,y,width,height,4,
                    nurik,
                    nurik2,
                    nurik,
                    nurik2
            );
            RenderUtility.drawRoundedGradientRect(x+0.9f,y+0.9f,width-1.8f,height-1.8f,8,d,d,l,d);

            int hovertext = ColorUtil.interpolateColor(new Color(155,155,155).getRGB(),Color.WHITE.getRGB(),animation.getOutput());

            Fonts.INTER_BOLD.get(23).drawCenter(matrixStack, this.getMessage().getString(), x + width / 2f, y + height / 2f - Fonts.INTER_BOLD.get(23).getHeight() / 2f + 2, hovertext);
            GL11.glPopMatrix();
            mc.gameRenderer.setupOverlayRendering();
        }


        public void onPress() {
            this.onPress.onPress(this);
        }


    }
}
