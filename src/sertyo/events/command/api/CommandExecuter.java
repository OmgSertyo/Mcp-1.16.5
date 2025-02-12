package sertyo.events.command.api;


import sertyo.events.util.player.ChatUtility;

public abstract class CommandExecuter {
    public String name = ((CommandAnnotation)this.getClass().getAnnotation(CommandAnnotation.class)).name();
    public String description = ((CommandAnnotation)this.getClass().getAnnotation(CommandAnnotation.class)).description();

    public abstract void error();

    public abstract void execute(String[] var1) throws Exception;

    public void sendMessage(String message) {
        ChatUtility.addChatMessage(message);
    }
}
