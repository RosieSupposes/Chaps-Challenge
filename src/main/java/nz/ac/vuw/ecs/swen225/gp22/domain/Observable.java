package nz.ac.vuw.ecs.swen225.gp22.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * A class can extend this so an instance of it
 * can be observed by {@link Observer} objects. 
 *  
 * @author Abdulrahman Asfari 300475089
 */
public abstract class Observable<S extends Observable<S>>{
    /** Contains a list of the {@link Observer} objects that are monitoring this object. */
    private List<Observer<S>> observers = new ArrayList<>();

    /** 
     * Adds an {@link Observer} to the list of {@link #observers}.
     * 
     * @param observer {@link Observer} to be added.
     */
    @DevMarkers.NeedsPrecons
    public void addObserver(Observer<S> observer){
        if(observer == null || observers.contains(observer)) return; // BAD THINGS
        observers.add(observer);
    }

    /** 
     * Removes an {@link Observer} from the list of {@link #observers}.
     * 
     * @param observer {@link Observer} to be removed.
     */
    @DevMarkers.NeedsPrecons
    public void removeObserver(Observer<S> observer){
        if(!observers.contains(observer)) return; // BAD THINGS
        observers.remove(observer);
    }

    /** 
     * Calls the {@link Observer#update(Observable) update()} method for each observer 
     * attached to this object. This method suppresses the unchecked cast because type S
     * is always a subtype of {@link Observable} with the generic type also being S,
     * and the value of 'this' will also be a subtype of {@link Observable} with 
     * the generic type also being S, so the cast is safe.
     */
    @SuppressWarnings("unchecked") 
    public void updateObservers(){
        observers.forEach(o -> o.update((S) this));
    }
}
