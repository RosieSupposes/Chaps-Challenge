package nz.ac.vuw.ecs.swen225.gp22.app;

import nz.ac.vuw.ecs.swen225.gp22.renderer.GameDimensions;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class GameDialog extends JDialog {


    private static final Dimension BUTTON_SIZE = new Dimension(150, 30);
    private final static int x = GameDimensions.WINDOW_WIDTH / 3;
    private final static int y = GameDimensions.WINDOW_HEIGHT / 3;
    private Base base;

    public GameDialog(Base base, String type) {
        this.base = base;
        this.setLayout(new GridBagLayout());
        this.setBackground(Main.LIGHT_YELLOW_COLOR);
        List<JComponent> components = List.of();
        switch (type) {
            case "Pause" -> components = setUpPause(base);
            case "GameOver" -> components = setUpGameOver(base);
            case "GameCompleted" -> components = setUpGameCompleted(base);
            case "Save" -> components = setUpSave();
        }
        GridBagConstraints c = new GridBagConstraints();
        c.anchor = GridBagConstraints.CENTER;
        c.insets = new Insets(5, 5, 5, 5);
        c.gridy = 0;
        for (JComponent component : components) {
            this.add(component, c);
            c.gridy++;
        }
        this.setVisible(true);
        this.requestFocus();
    }

    private int timeMS = 0;

    private List<JComponent> setUpSave() {
        this.setBounds(50, 50, 100, 70);
        this.addKeyListener(new Controller(base, this));

        JLabel info = new JLabel("Game Saved");
        info.setFont(new Font("Arial", Font.BOLD, 14));

        timeMS = 0;
        Timer timer = new Timer(250, unused -> {
            assert SwingUtilities.isEventDispatchThread();
            timeMS += 1;
            if (timeMS >= 3) {
                this.setVisible(false);
            }
        });
        timer.start();
        return List.of(info);
    }

    private List<JComponent> setUpGameCompleted(Base base) {
        this.setBounds(x, 50, 200, 400);
        this.addKeyListener(new Controller(base, this));

        JLabel info = new JLabel("You Win!");
        info.setFont(new Font("Arial", Font.BOLD, 18));

        return List.of(info, loadButton(), newOneButton(), newTwoButton(), exitButton());
    }

    private List<JComponent> setUpGameOver(Base base) {
        this.setBounds(x, 50, 200, 300);
        this.addKeyListener(new Controller(base, this));

        JLabel info = new JLabel("Game is Over");
        info.setFont(new Font("Arial", Font.BOLD, 18));

        return List.of(info, loadButton(), newOneButton(), newTwoButton(), exitButton());
    }

    private List<JComponent> setUpPause(Base base) {
        this.setBounds(x, 50, 200, 400);
        this.addKeyListener(new Controller(base, this));

        JLabel info = new JLabel("Game is Paused");
        info.setFont(new Font("Arial", Font.BOLD, 18));

        return List.of(info, playButton(), loadButton(), newOneButton(), newTwoButton(), saveButton(), exitButton());
    }

    public GameButton playButton() {
        Runnable action = () -> {
            base.unPause();
            this.setVisible(false);
        };
        GameButton button = new GameButton("Play Game", BUTTON_SIZE, e -> action.run());
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                action.run();
            }
        });
        return button;
    }

    public GameButton loadButton() {
        return new GameButton("Load New Game", BUTTON_SIZE, e -> {
            base.loadGame();
            this.setVisible(false);
        });
    }

    public GameButton newOneButton() {
        return new GameButton("Load Level One", BUTTON_SIZE, e -> {
            base.newGame(1);
            this.setVisible(false);
        });
    }

    public GameButton newTwoButton() {
        return new GameButton("Load Level Two", BUTTON_SIZE, e -> {
            base.newGame(2);
            this.setVisible(false);
        });
    }

    public GameButton saveButton() {
        return new GameButton("Save Game", BUTTON_SIZE, e -> {
            base.saveGame();
        });
    }

    public GameButton exitButton() {
        return new GameButton("Exit Game", BUTTON_SIZE, e -> {
            base.exitGame();
            this.setVisible(false);
        });
    }

//    private GameButton makeButton(String name, Runnable action) {
//        GameButton button = new GameButton(name, BUTTON_SIZE, e -> action.run());
//        this.addWindowListener(new WindowAdapter() {
//            @Override
//            public void windowClosed(WindowEvent e) {
//                action.run();
//            }
//        });
//        return button;
//    }
}
