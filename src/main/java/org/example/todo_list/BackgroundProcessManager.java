package org.example.todo_list;

import javafx.application.Platform;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;

public class BackgroundProcessManager {

    private static final BackgroundProcessManager INSTANCE = new BackgroundProcessManager();
    public static BackgroundProcessManager getInstance() {
        return INSTANCE;
    }
    //Tray Icon for supported Devices
    private TrayIcon trayIcon;

    public void sendNotification(String title, String message) throws IOException, AWTException {
        String os = System.getProperty("os.name");
        if (os.contains("Linux")) {
            ProcessBuilder builder = new ProcessBuilder(
                    "zenity",
                    "--notification",
                    "--text=" + title + "\\n" + message);
            builder.inheritIO().start();
        } else if (os.contains("Mac")) {
            ProcessBuilder builder = new ProcessBuilder(
                    "osascript", "-e",
                    "display notification \"" + message + "\""
                            + " with title \"" + title + "\"");
            builder.inheritIO().start();
        } else if (SystemTray.isSupported()) {
            trayIcon.displayMessage(title, message, TrayIcon.MessageType.NONE);
        }
    }

    /**
     * Starts the background process of the application. This allows the app to keep running in the background to send notifications.
     * A different method is used depending on the operating system.
     * @throws IOException
     * @throws AWTException
     */
    public void startBackgroundProcess() throws IOException, AWTException {
        String title = "JustDo has started!";
        String message = "We'll be running in the background and notify you when a deadline is coming up!";
        java.awt.Image image = ImageIO.read(App.class.getResourceAsStream("images/logo/icon_16px.png"));

        //Todo setup BG process for Mac and Linux
        if (SystemTray.isSupported()) {
            Platform.setImplicitExit(false);// The app won't completely close when hitting the X button.
            SystemTray tray = SystemTray.getSystemTray();

            PopupMenu popUp = new PopupMenu();
            MenuItem exit = new MenuItem("Exit JustDo");
            exit.addActionListener(e->{System.exit(0);});// Close the app using system tray menu item. (Right-click icon)
            popUp.add(exit);

            trayIcon = new TrayIcon(image, "JustDo", popUp);
            trayIcon.setImageAutoSize(true);
            trayIcon.addActionListener(e->{
                Platform.runLater(// Use runLater to avoid running on the incorrect thread.
                        new Runnable() {
                            @Override
                            public void run() {
                                SceneManager.getInstance().open();
                            }
                        }
                );
            });
            tray.add(trayIcon);
        }
        sendNotification(title, message);
    }
}
