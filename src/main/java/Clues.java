import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.logging.Logger;

/**
 * Created by Hermes235 on 9.9.2016.
 */
public class Clues implements Iterable<Integer>, Iterator<Integer> {
    private static final Logger LOGGER = Logger.getLogger(Clues.class.getName());
    private int[][] clues;
    private int currCol = 0;
    private int currIndex = 0;
    private int maxIndex = 0;

    public Clues(int[][] clues) {
        this.clues = clues;
        calculateMaxIndex();
    }

    public Clues(int columnCount, int rowCount) {
        this.clues = new int[columnCount][rowCount];
        maxIndex = rowCount;
    }

    private void calculateMaxIndex() {
        maxIndex = 0;
        for (int[] clue : clues) {
            maxIndex = Math.max(clue.length, maxIndex);
        }
    }

    /**
     * Sets the number on specified position.
     *
     * @param index indexed from board outwards!
     */
    public void setClueReversed(int column, int index, int number) {
        if (!isColumnInRange(column)) {
            LOGGER.severe("Column out of range!");
            return;
        }
        if (!isIndexInRange(column, index)) {
            LOGGER.warning("Index was out of range! Resizing to fit it in!");
            if (index < 0) {
                resizeToAddStart(column, number);
            } else {
                resizeToAddEnd(column, number);
            }
            return;
        }

        clues[column][index] = number;
    }

    /**
     * Resizes the column to add specified clue to the start: Closest to board.
     */
    private void resizeToAddStart(int column, int toAdd) {
        if (!isColumnInRange(column)) {
            LOGGER.warning("Column out of range! Skipping.");
            return;
        }

        if (toAdd < 0) {
            LOGGER.warning("Trying to resize array to add negative number! Skipping.");
            return;
        }

        int length = clues[column].length;
        int[] newColumn = new int[length + 1];
        newColumn[0] = toAdd;
        System.arraycopy(clues[column], 0, newColumn, 1, length);
        clues[column] = newColumn;
        calculateMaxIndex();
    }

    /**
     * Resizes the column to add specified clue to the end: Closest to the edge (most left).
     */
    private void resizeToAddEnd(int column, int toAdd) {
        if (!isColumnInRange(column)) {
            LOGGER.warning("Column out of range! Skipping.");
            return;
        }

        if (toAdd < 0) {
            LOGGER.warning("Trying to resize array to add negative number! Skipping.");
            return;
        }

        int[] newColumn = new int[clues[column].length + 1];
        System.arraycopy(clues[column], 0, newColumn, 0, clues[column].length);
        newColumn[newColumn.length - 1] = toAdd;
        clues[column] = newColumn;
        calculateMaxIndex();
    }

    /**
     * Sets the number on specified position.
     *
     * @param index indexed from border inwards!
     */
    public void setClue(int column, int index, int number) {
        if (!isColumnInRange(column)) {
            LOGGER.severe("Column out of range!");
            return;
        }
        if (!isIndexInRange(column, index)) {
            LOGGER.warning("Index was out of range! Resizing to fit it in!");
            if (index < 0) {
                resizeToAddEnd(column, number);
            } else {
                resizeToAddStart(column, number);
            }
            return;
        }

        clues[column][clues[column].length - 1 - index] = number;
    }

    public void setClues(int column, int[] clues) {
        if (!isColumnInRange(column)) {
            LOGGER.severe("Column out of range!");
            return;
        }

        this.clues[column] = reverseArray(clues);
    }

    public void setCluesReversed(int column, int[] clues) {
        if (!isColumnInRange(column)) {
            LOGGER.severe("Column out of range!");
            return;
        }

        this.clues[column] = clues;
    }

    private int[] reverseArray(int[] array) {
        int length = array.length;
        int[] returning = new int[length];
        for (int i = 0; i < length; i++) {
            returning[i] = array[length - 1 - i];
        }
        return returning;
    }

    public void align() {
        for (int i = 0; i < clues.length; i++) {
            alignCol(i);
        }
        calculateRealMaxIndex();
    }

    public void calculateRealMaxIndex() {
        maxIndex = 0;
        for (int[] clue : clues) {
            for (int i = 0; i < clue.length; i++) {
                if (clue[i] == 0) {
                    maxIndex = Math.max(maxIndex, i - 1);
                    break;
                }
            }
        }
    }

    public int getMaxIndex() {
        return maxIndex;
    }

    private void alignCol(int col) {
        if (!isColumnInRange(col)) {
            LOGGER.warning("Column not in range! Skipping.");
            return;
        }
        int[] currentCol = clues[col];

        int i = 0, current = 0;
        while (i < currentCol.length && current < currentCol.length) {
            if (current < i) current = i;
            if (currentCol[i] == 0) {
                current++;
                while (current < currentCol.length && currentCol[current] == 0) {
                    current++;
                }
                if (current >= currentCol.length) return;
                currentCol[i] = currentCol[current];
                currentCol[current] = 0;
            }
            i++;
        }
    }

    public int getColumns() {
        return clues.length;
    }

    public int getIndexes() {
        return clues[0].length;
    }

    public int getIndexes(int columnIndex) {
        if (!isColumnInRange(columnIndex)) {
            LOGGER.severe("Column index is out of range!");
            return 0;
        }
        return clues[columnIndex].length;
    }

    private boolean isColumnInRange(int columnIndex) {
        return columnIndex >= 0 && columnIndex < clues.length;
    }

    private boolean isIndexInRange(int index) {
        return index >= 0 && index < clues[currCol].length;
    }

    private boolean isIndexInRange(int columnIndex, int index) {
        return index >= 0 && index < clues[columnIndex].length;
    }

    public void clearClues() {
        LOGGER.info("Clearing all Clues from " + this);
        for (int[] clue : clues) {
            for (int i = 0; i < clue.length; i++) {
                clue[i] = 0;
            }
        }
    }

    /**
     * Returns the whole array of clues from that column.
     *
     * @return numbered from the board to the edge
     */
    public int[] getClues(int column) {
        if (!isColumnInRange(column)) {
            LOGGER.severe("Column index is out of range!");
            return null;
        }
        return reverseArray(clues[column]);
    }

    /**
     * Returns the clue on specified position
     *
     * @param clueIndex numbered from the OUTSIDE towards the board! Not counting empty spaces
     */
    public int getClue(int column, int clueIndex) {

        if (!isColumnInRange(column)) {
            LOGGER.severe("Column index is out of range!");
            return 0;
        }

        if (!isIndexInRange(column, clueIndex)) {
            LOGGER.severe("clueIndex is out of range!");
            return 0;
        }

        int index = clueIndex;
        int i = clues[column].length - 1;
        while (i >= 0) {
            if (clues[column][i] == 0) {
                i--;
            } else if (index == 0) {
                return clues[column][i];
            } else {
                i--;
                index--;
            }
        }
        LOGGER.fine("No such clue!");
        return 0;
    }

    /**
     * Returns the clue on specified position
     *
     * @param clueIndex numbered from the INSIDE towards the edge! There should be no empty spaces
     */
    public int getClueReversed(int column, int clueIndex) {
        if (!isColumnInRange(column)) {
            LOGGER.severe("Column index is out of range!");
            return 0;
        }

        if (!isIndexInRange(column, clueIndex)) {
            LOGGER.severe("clueIndex is out of range!");
            return 0;
        }

        return clues[column][clueIndex];
    }


    /**
     * Sets currently browsed column. Used for iterating! Also sets currIndex to max possible, so you can iterate.
     */
    public void setCurrCol(int currCol) {
        if (!isColumnInRange(currCol)) {
            LOGGER.warning("trying to set currCol out of range!");
            currCol = 0;
        }
        currIndex = clues[currCol].length - 1;
    }

    public Iterator<Integer> iterator() {
        return this;
    }

    public boolean hasNext() {
        if (!isColumnInRange(currCol) || !isIndexInRange(currIndex)) return false;
        while (clues[currCol][currIndex] == 0 && currIndex > 0) {
            currIndex--;
        }
        return currIndex != 0 || clues[currCol][currIndex] != 0;
    }

    public Integer next() {
        if (currIndex < 0) {
            LOGGER.severe("Iterator called after the end!");
            throw new NoSuchElementException();
        }
        int toReturn = clues[currCol][currIndex];
        if (toReturn == 0) {
            LOGGER.warning("toReturn was 0! This loop is necessary!");
            while (currIndex >= 0 && clues[currCol][currIndex] == 0) {
                currIndex--;
            }
            if (currIndex < 0) {
                LOGGER.severe("Iterator called after the end!");
                throw new NoSuchElementException();
            } else {
                currIndex--;
                return clues[currCol][currIndex + 1];
            }
        }
        currIndex--;
        return toReturn;
    }

    public void remove() {

    }

    public int[] getCluesReversed(int column) {
        if (!isColumnInRange(column)) {
            LOGGER.severe("Column out of range!");
            return null;
        }
        return clues[column];
    }
}