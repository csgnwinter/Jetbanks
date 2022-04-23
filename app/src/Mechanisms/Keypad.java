package Mechanisms;

public interface Keypad {
    public String getInput(String text, int maxOption, boolean canCancel);

    public String getKeyboardInput(String text);
}
