package nz.ac.vuw.ecs.swen225.gp22.app;

class Controller extends Keys {
    //    Controller(Camera c, Sword s) {
//        setAction(Compact.getKeyCodes("Up"), c.set(Direction::up), c.set(Direction::unUp));
//        setAction(Compact.getKeyCodes("Down"), c.set(Direction::down), c.set(Direction::unDown));
//        setAction(Compact.getKeyCodes("Left"), c.set(Direction::left), c.set(Direction::unLeft));
//        setAction(Compact.getKeyCodes("Right"), c.set(Direction::right), c.set(Direction::unRight));
//        setAction(Compact.getKeyCodes("Attack Left"), s.set(Direction::left), s.set(Direction::unLeft));
//        setAction(Compact.getKeyCodes("Attack Right"), s.set(Direction::right), s.set(Direction::unRight));
//    }
    Controller() {
        setAction(65, () -> System.out.println("a"), () -> System.out.println("no a")); //example
    }
}