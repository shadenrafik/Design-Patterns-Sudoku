package controller.model;

public class Catalog {
    public boolean current;        // unfinished game exists
    public boolean allModesExist;   // easy + medium + hard exist

    public Catalog(boolean current, boolean allModesExist) {
        this.current = current;
        this.allModesExist = allModesExist;
    }
}
