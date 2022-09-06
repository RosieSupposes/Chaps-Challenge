package nz.ac.vuw.ecs.swen225.gp22.app;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

public class Base extends JFrame {
    Runnable closePhase = () -> {
    };
    int TILE_SIZE = 60;
    int NUM_GAME_TILE = 9;
    int SIDEBAR_WIDTH = 5 * TILE_SIZE;
    int WINDOW_HEIGHT = NUM_GAME_TILE * TILE_SIZE;
    int GAME_WINDOW_SIZE = WINDOW_HEIGHT;

    int WINDOW_WIDTH = NUM_GAME_TILE * TILE_SIZE + SIDEBAR_WIDTH;

    List<JComponent> components = new ArrayList<>();

    Base() {
        assert SwingUtilities.isEventDispatchThread();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        startPhase();
        setVisible(true);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                closePhase.run();
            }
        });
    }

    private void startPhase() {
        closePhase.run();
        closePhase = () -> {
            for (JComponent component : components) {
                remove(component);
            }
        };

        setupSideBar();
        setupMainPanel();
        setupMenuBar();

        //add(BorderLayout.CENTER, components);

        setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
        pack();
    }

    private void setupMenuBar() {
        int menuItemWidth = GAME_WINDOW_SIZE / 5;
        JMenuBar menuBar = new JMenuBar();
        components.add(menuBar);

        JMenuItem pause = new JMenuItem("Pause");
        pause.setMaximumSize(new Dimension(menuItemWidth,30));
        pause.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Pause!");
            }
        });
        menuBar.add(pause);
        components.add(pause);

        JMenu newLevel = new JMenu("New");
        newLevel.setMaximumSize(new Dimension(menuItemWidth,30));

        JMenuItem levelOne = new JMenuItem("Level One");
        newLevel.add(levelOne);
        components.add(levelOne);

        JMenuItem levelTwo = new JMenuItem("Level Two");
        newLevel.add(levelTwo);
        components.add(levelTwo);

        menuBar.add(newLevel);
        components.add(newLevel);

        JMenuItem save = new JMenuItem("Save");
        save.setMaximumSize(new Dimension(menuItemWidth,30));
        menuBar.add(save);
        components.add(save);

        JMenuItem load = new JMenuItem("Load");
        load.setMaximumSize(new Dimension(menuItemWidth,30));
        menuBar.add(load);
        components.add(load);

        JMenuItem exit = new JMenuItem("Exit");
        exit.setMaximumSize(new Dimension(menuItemWidth,30));
        menuBar.add(exit);
        components.add(exit);

        setJMenuBar(menuBar);
    }

    private void setupMainPanel() {
        //
    }

    private void setupSideBar() {
        JPanel sidePanel = new JPanel();
        sidePanel.setBackground(Color.BLUE);
        sidePanel.setPreferredSize(new Dimension(SIDEBAR_WIDTH, WINDOW_HEIGHT));
        add(BorderLayout.EAST, sidePanel);
        components.add(sidePanel);
    }

//    private void phaseOne() {
//        setPhase(Phase.level1(this::phaseTwo, this::phaseZero));
//    }

//    private void setPhase(Phase p) {
//        //set up the viewport and the timer
//        Viewport v = new Viewport(p.model());
//        v.addKeyListener(p.controller());
//        v.setFocusable(true);
//        Timer timer = new Timer(34, unused -> {
//            assert SwingUtilities.isEventDispatchThread();
//            p.model().ping();
//            v.repaint();
//        });
//        closePhase.run();//close phase before adding any element of the new phase
//        closePhase = () -> {
//            timer.stop();
//            remove(v);
//        };
//        add(BorderLayout.CENTER, v);//add the new phase viewport
//        setPreferredSize(getSize());//to keep the current size
//        pack();                     //after pack
//        v.requestFocus();//need to be after pack
//        timer.start();
//    }
}
