package sertyo.events.util;

import org.lwjgl.glfw.GLFW;

public class Mouse {
    public static boolean isButtonDown(int button) {
        int buttonn = 0;
        if (button == 1)
            buttonn = GLFW.GLFW_MOUSE_BUTTON_1;
        else if (button == 2)
            buttonn = GLFW.GLFW_MOUSE_BUTTON_2;
        return GLFW.glfwGetMouseButton(GLFW.glfwGetCurrentContext(), buttonn) == GLFW.GLFW_PRESS;
    }
}
