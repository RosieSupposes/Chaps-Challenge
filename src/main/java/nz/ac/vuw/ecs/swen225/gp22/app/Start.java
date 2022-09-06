package nz.ac.vuw.ecs.swen225.gp22.app;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Map;

public class Start extends JFrame{
    Runnable closePhase = () -> {};
    int TILE_SIZE = 50;
    int GAME_TILE_WIDTH = 9;
    int SIDEBAR_WIDTH = 6*TILE_SIZE;
    int SIDEBAR_HEIGHT = GAME_TILE_WIDTH *TILE_SIZE;
    Start() {
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

    private void startPhase(){
        Map<String,JComponent> components = Map.of(
                "Start", new JLabel("Hello, World!"),
                "Sidebar", new JPanel(),
                "Game", new JPanel()
        );
        closePhase.run();
        closePhase = () -> {
            for(JComponent component: components.values()){
                remove(component);
            }
        };

        setupSideBar(components);
        setupMainPanel(components);
        add(BorderLayout.CENTER, components.get("Start"));

        setPreferredSize(new Dimension(SIDEBAR_WIDTH+GAME_TILE_WIDTH*TILE_SIZE,SIDEBAR_HEIGHT));
        pack();
    }

    private void setupMainPanel(Map<String, JComponent> components) {
        //
    }

    private void setupSideBar(Map<String, JComponent> components) {
        JPanel sidePanel = (JPanel) components.get("Sidebar");
        sidePanel.setForeground(Color.BLUE);
        add(BorderLayout.EAST, sidePanel);
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
