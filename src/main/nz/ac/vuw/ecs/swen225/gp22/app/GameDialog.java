package nz.ac.vuw.ecs.swen225.gp22.app;

import nz.ac.vuw.ecs.swen225.gp22.renderer.GameDimensions;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
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
            case "GameOver" -> setUpGameOver(base);
            case "GameCompleted" -> setUpGameCompleted(base);
            case "Save" -> setUpSave();
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

    private void setUpSave() {

    }

    private void setUpGameCompleted(Base base) {

    }

    private void setUpGameOver(Base base) {

    }

    private List<JComponent> setUpPause(Base base) {
        this.setBounds(x, 50, 200, 400);
        this.addKeyListener(new Controller(base, this));

        JLabel info = new JLabel("Game is Paused");
        info.setFont(new Font("Arial", Font.BOLD, 18));

        return List.of(info, playButton(), loadButton(), newOneButton(), newTwoButton(), saveButton(), exitButton());
    }

    public GameButton playButton() {
        return makeButton("Play Game", () -> {
            base.unPause();
            this.setVisible(false);
        });
    }

    public GameButton loadButton() {
        return makeButton("Load New Game", () -> {
            base.loadGame();
            this.setVisible(false);
        });
    }

    public GameButton newOneButton() {
        return makeButton("Load Level One", () -> {
            base.newGame(1);
            this.setVisible(false);
        });
    }

    public GameButton newTwoButton() {
        return makeButton("Load Level Two", () -> {
            base.newGame(2);
            this.setVisible(false);
        });
    }

    public GameButton saveButton() {
        return makeButton("Save Game", () -> {
            base.saveGame();
            this.setVisible(false);
        });
    }

    public GameButton exitButton() {
        return makeButton("Exit Game", () -> {
            base.newGame(2);
            this.setVisible(false);
        });
    }

    private GameButton makeButton(String name, Runnable action) {
        GameButton button = new GameButton(name, BUTTON_SIZE, e -> action.run());
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                action.run();
            }
        });
        return button;
    }
}
