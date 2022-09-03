package nz.ac.vuw.ecs.swen225.gp22.domain;

import java.util.ArrayList;
import java.util.List;

public abstract class Observable<S extends Observable<S>>{
    private List<Observer<S>> observers = new ArrayList<>();

    public void addObserver(Observer<S> observer){
        if(observer == null || observers.contains(observer)) return; // BAD THINGS
        observers.add(observer);
    }

    public void removeObserver(Observer<S> observer){
        if(!observers.contains(observer)) return; // BAD THINGS
        observers.remove(observer);
    }

    public void updateObservers(S subject){
        observers.forEach(o -> o.update(subject));
    }
}
