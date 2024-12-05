package kz.findmyname284.plugin.player;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class WindowsControl implements Control {
    private File tempFile;
    public WindowsControl() {
        try (InputStream tempFileStream = Main.class.getResourceAsStream("/player_control.exe")) {
            tempFile = File.createTempFile("player_control", ".exe");
            assert tempFileStream != null;
            Files.copy(tempFileStream, tempFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            System.out.println("Failed to " + e.getMessage());
        }
    }

    @Override
    public void playPause() {
        run(tempFile.getAbsolutePath() + " pp");
    }

    @Override
    public void nextTrack() {
        run(tempFile.getAbsolutePath() + " next");
    }

    @Override
    public void prevTrack() {
        run(tempFile.getAbsolutePath() + " prev");
    }

    @Override
    public void stop() {
        run(tempFile.getAbsolutePath() + " stop");
    }

    private void run(String command) {
        try {
            Runtime.getRuntime().exec(command).waitFor();
        } catch (InterruptedException | IOException e) {
            throw new RuntimeException(e);
        } finally {
            deleteOnExit();
        }
    }

    private void deleteOnExit() {
        tempFile.deleteOnExit();
    }
}
