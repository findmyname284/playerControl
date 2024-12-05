package kz.findmyname284.plugin.player;

public class Main {
    public static void main(String[] args) {
        if (args.length == 0) {
            return;
        }
        String command = args[0].trim().toUpperCase();

        Control control = isWindows() ? new WindowsControl() : isLinux() ? new LinuxControl() : null;
        if (control == null) {
            System.out.println("Unsupported OS");
            return;
        }

        try {
            Code code = Code.valueOf(command);
            switch (code) {
                case PP -> control.playPause();
                case NEXT -> control.nextTrack();
                case PREV -> control.prevTrack();
                case STOP -> control.stop();
                default -> throw new IllegalArgumentException();
            }
        } catch (IllegalArgumentException e) {
            // ignore
        }
    }

    private static boolean isWindows() {
        return System.getProperty("os.name").toLowerCase().contains("win");
    }

    private static boolean isLinux() {
        String osName = System.getProperty("os.name").toLowerCase();
        return osName.contains("nix") || osName.contains("nux");
    }
}