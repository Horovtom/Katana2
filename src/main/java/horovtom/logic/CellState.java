package horovtom.logic;

import java.util.HashMap;

/**
 * Created by Hermes235 on 9.9.2016.
 */
public enum CellState {
    WHITE(0), BLACK(1), CROSS(2), DOT(3);

    private final int index;
    private static HashMap<Integer, CellState> states;

    CellState(int i) {
        index = i;
    }

    public static CellState getState(int i) {
        if (states == null) {
            initializeMapping();
        }
        if (states.containsKey(i)) {
            return states.get(i);
        }
        return null;
    }

    public int getIndex(){
        return index;
    }

    private static void initializeMapping() {
        states = new HashMap<Integer, CellState>();
        for (CellState s : CellState.values()) {
            states.put(s.index, s);
        }
    }

    public CellState getNextState(){
        if (states == null) initializeMapping();
        if (index + 1 == states.size()) return states.get(0);
        else return states.get(index +1);
    }
}