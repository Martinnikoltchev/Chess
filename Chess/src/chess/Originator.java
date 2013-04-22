/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package chess;

/**
 *
 * @author Martin
 */
import java.util.List;
import java.util.ArrayList;
class Originator {
    private Player state;
    // The class could also contain additional data that is not part of the
    // state saved in the memento.
 
    public void set(Player state) {
        System.out.println("Originator: Setting state to " + state);
        this.state = state;
    }
 
    public Memento saveToMemento() {
        System.out.println("Originator: Saving to Memento.");
        return new Memento(state);
    }
 
    public void restoreFromMemento(Memento memento) {
        state = memento.getSavedState();
        System.out.println("Originator: State after restoring from Memento: " + state);
    }
 
    public static class Memento {
        private final Player state;
 
        public Memento(Player stateToSave) {
            state = stateToSave;
        }
 
        public Player getSavedState() {
            return state;
        }
    }
}
 
