package nz.ac.vuw.ecs.swen225.gp22.domain;

@FunctionalInterface
public interface Observer<S extends Observable<S>>{
    public void update(S subject);
}
