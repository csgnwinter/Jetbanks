package Mechanisms;

public interface Screen {
    public final String COLOR_RESET = "\u001B[0m";
    public final String COLOR_RED = "\u001B[31m";
    public final String COLOR_GREEN = "\u001B[32m";
    public final String COLOR_YELLOW = "\u001B[33m";
    public final String COLOR_BLUE = "\u001B[34m";
    public final String COLOR_PURPLE = "\u001B[35m";
    public final String COLOR_CYAN = "\u001B[36m";

    public void printMessage(String message);
}
