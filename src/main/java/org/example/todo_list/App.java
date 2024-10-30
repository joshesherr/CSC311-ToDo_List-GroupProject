package org.example.todo_list;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.MouseListener;
import java.io.IOException;


public class App extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        stage.getIcons().add(new Image(getClass().getResourceAsStream("images/icon_32.png")));
        stage.setTitle("Just Do");
        stage.setResizable(false);//Todo to keep things simple windows wont be resizable for now.

        SceneManager sceneManager = SceneManager.getInstance();
        sceneManager.setPrimaryStage(stage);

        sceneManager.loadScene("RegisterScene"); //Load register view early for more seamless transitions

        sceneManager.showScene("LoginScene"); //Show log in window
    }

    /**
     * The starting point for our cool app.
     */
    public static void main(String[] args) throws IOException, AWTException {
        startBackgroundProcess();
        launch();
    }

    //ToDo move to new BackgroundApp class. Will be used to send notifications while app is open.
    /**
     * Starts the background process of the application. This allows the app to keep running in the background to send notifications.
     * A different method is used depending on the operating system.
     * @throws IOException
     * @throws AWTException
     */
    public static void startBackgroundProcess() throws IOException, AWTException {
        String title = "JustDo has started!";
        String message = "We'll be running in the background and notify you when a deadline is coming up!";
        java.awt.Image image = ImageIO.read(App.class.getResourceAsStream("images/icon_16.png"));

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
            Platform.setImplicitExit(false);// The app won't completely close when hitting the X button.
            SystemTray tray = SystemTray.getSystemTray();

            PopupMenu popUp = new PopupMenu();
            MenuItem exit = new MenuItem("Exit JustDo");
            exit.addActionListener(e->{System.exit(0);});// Close the app using system tray menu item. (Right-click icon)
            popUp.add(exit);

            TrayIcon trayIcon = new TrayIcon(image, "JustDo", popUp);
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

            trayIcon.displayMessage(title, message, TrayIcon.MessageType.NONE);
        }
    }

}