package nz.ac.vuw.ecs.swen225.gp22.app;

import nz.ac.vuw.ecs.swen225.gp22.renderer.GameDimensions;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class GameDialog {

    private static final Dimension BUTTON_SIZE = new Dimension(150, 30);
    private final static int x = GameDimensions.WINDOW_WIDTH / 3;
    private final static int y = GameDimensions.WINDOW_HEIGHT / 3;

    public static void makeGameDialog(Base base, String type) {
        switch (type) {
            case "Pause" -> setUpPause(base);
            case "GameOver" -> setUpGameOver();
            case "GameCompleted" -> setUpGameCompleted();
            case "Save" -> setUpSave();
        }
    }

    private static void setUpSave() {

    }

    private static void setUpGameCompleted() {
    }

    private static void setUpGameOver() {
    }

    private static void setUpPause(Base base) {
        JDialog dialog = new JDialog();
        dialog.setBounds(x, y, 300, 250);
        dialog.setLayout(new GridBagLayout());
        dialog.setBackground(Main.LIGHT_YELLOW_COLOR);

        JLabel info = new JLabel("Game is Paused");
        Runnable action = () -> {
            base.unPause();
            dialog.setVisible(false);
        };
        info.addKeyListener(new Controller(base,true));
        GameButton playButton = makeButton("Play Game", dialog, action, KeyEvent.VK_ESCAPE, false);

        GridBagConstraints c = new GridBagConstraints();
        c.anchor = GridBagConstraints.CENTER;
        c.insets = new Insets(10, 10, 10, 10);

        c.gridy = 0;
        dialog.add(info, c);
        c.gridy = 1;
        dialog.add(playButton, c);

        dialog.setVisible(true);
    }

    private static GameButton makeButton(String name, JDialog dialog, Runnable action, int keyCode, boolean ctrlDown) {
        GameButton button = new GameButton(name, BUTTON_SIZE, e -> action.run());
        button.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if(ctrlDown){
                    if(e.isControlDown()){
                        if(e.getKeyCode() == keyCode){
                            action.run();
                        }
                    }
                }else{
                    if (e.getKeyCode() == keyCode) {
                        action.run();
                    }
                }

            }
        });
        dialog.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                action.run();
            }
        });
        return button;
    }
}
