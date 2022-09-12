package nz.ac.vuw.ecs.swen225.gp22.app;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

public class Base extends JFrame {
    public final int TILE_SIZE = 60;
    public final int NUM_GAME_TILE = 9;
    public final int SIDEBAR_WIDTH = 5 * TILE_SIZE;
    public final int WINDOW_HEIGHT = NUM_GAME_TILE * TILE_SIZE;
    public final int GAME_WINDOW_SIZE = WINDOW_HEIGHT;
    public final int WINDOW_WIDTH = NUM_GAME_TILE * TILE_SIZE + SIDEBAR_WIDTH;

    private List<JComponent> components = new ArrayList<>();

    private final Runnable closePhase = () -> {
        for (JComponent component : this.components) {
            remove(component);
        }
    };

    Base() {
        assert SwingUtilities.isEventDispatchThread();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        startPhase();
        setVisible(true);
        setResizable(false);
        setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
        pack();
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                closePhase.run();
            }
        });
    }

    public void changePhase(Phase p){
        closePhase.run();
        add(BorderLayout.CENTER,p);
        components.add(p);
    }

//    public Runnable getClosePhase(){
//        return closePhase;
//    }

    public void runClosePhase(){
        closePhase.run();
        components.clear();
    }

//    public List<JComponent> components(){
//        return components;
//    }

    public void addComponent(JComponent jc){
        components.add(jc);
    }

    private void startPhase() {
        Phase menu = new Phase(this);
        add(menu);
        components.add(menu);

    }

    public void levelOnePhase() {
        closePhase.run();
        components.clear();

        Phase levelOne = new Phase(this,0);
        components.add(levelOne);
        pack();
    }

    private void levelTwoPhase() {

    }

    private void pausePhase() {

    }

    private void repeatPhase() {

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
