import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.logging.Logger;

/**
 * Clues class, which is defining one set of clues in the Katana.
 * Can be iterated over, stores values in 2-d array. First index is "Column" (defines the row of Katana), second index is "Index" (defines the individual numbers in specified Column)
 * Clues are stored in OUTWARDS direction by default.
 * <p>
 * Example:    <br>
 * 3x3 Katana grid with 2 clues on each row:
 * <p>
 * __|___    <br>
 * __|111   <br>
 * ______   <br>
 * 11|■□■   <br>
 * __|□□□   <br>
 * _1|□■□   <br>
 * <p>
 * For the rows Clues it is indexed as follows:
 * <p>
 * [0,1] [0,0] |   <br>
 * [1,1] [1,0] |   <br>
 * [2,1] [2,0] |   <br>
 */
public class Clues implements Iterable<Integer>, Iterator<Integer> {
    private static final Logger LOGGER = Logger.getLogger(Clues.class.getName());
    //Default OUTWARDS direction!
    private int[][] clues;
    private int currCol = 0;
    private int currIndex = 0;
    private int maxIndex = 0;
    private IODirection iterDirection = IODirection.INWARDS;

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
     * Check whether column is in range and if number is not negative
     * Meant to be used with:
     * {@linkplain #appendOutwards(int, int)}
     * {@linkplain #appendInwards(int, int)}
     */
    private boolean obeysBoundariesForAppend(int column, int number) {
        if (!isColumnInRange(column)) {
            LOGGER.warning("Column out of range!");
            return false;
        }

        if (number < 0) {
            LOGGER.warning("Trying to resize array to add negative number!");
            return false;
        }
        return true;
    }

    /**
     * Returns the clue at specified position, index in the INWARDS direction
     */
    public int getClue(int column, int index){
        return getClue(IODirection.INWARDS, column, index);
    }


    /**
     * Adds specified value to the end in the specified direction
     */
    public void append(IODirection dir, int column, int number){
        if (dir == IODirection.INWARDS) {
            appendInwards(column, number);
        } else {
            appendOutwards(column, number);
        }
    }

    /**
     * Adds specified value to the end in INWARDS direction
     */
    public void append(int column, int number){
        appendInwards(column, number);
    }

    /**
     * Meant to be used with {@linkplain #append(IODirection, int, int)}
     */
    private void appendInwards(int column, int number){
        if (!obeysBoundariesForAppend(column, number)) {
            LOGGER.warning("Skipping cause of invalid arguments!");
            return;
        }
        //TODO: COMPLETE
    }

    /**
     * Meant to be used with {@linkplain #append(IODirection, int, int)}
     */
    private void appendOutwards(int column, int number){
        if (!obeysBoundariesForAppend(column, number)) {
            LOGGER.warning("Skipping cause of invalid arguments!");
            return;
        }
        //TODO: COMPLETE

    }

    public void setClue(IODirection dir, int column, int index, int number) {
        if (dir == IODirection.INWARDS) {
            setClueInwards(column, index, number);
        } else {
            setClueOutwards(column, index, number);
        }
    }

    /**
     * Sets the number on specified position.
     *
     * @param index indexed from board outwards!
     */
    private void setClueOutwards(int column, int index, int number) {
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
    private void setClueInwards(int column, int index, int number) {
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

    private void setCluesOutwards(int column, int[] clues) {
        if (!isColumnInRange(column)) {
            LOGGER.severe("Column out of range!");
            return;
        }
        this.clues[column] = new int[clues.length];
        System.arraycopy(clues, 0, this.clues[column], 0, clues.length);
        maxIndex = Math.max(clues.length, maxIndex);
    }

    private void setCluesInwards(int column, int[] clues) {
        if (!isColumnInRange(column)) {
            LOGGER.severe("Column out of range!");
            return;
        }
        this.clues[column] = reverseArray(clues);
        maxIndex = Math.max(clues.length, maxIndex);
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

    /**
     * Should be used with align() or AFTER calling align();
     */
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

    /**
     * Returns the IndexCount of the first column
     */
    public int getIndexes() {
        return clues[0].length;
    }

    /**
     * Returns the number of indexes in specified column
     */
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
    private int[] getCluesOutwards(int column) {
        if (!isColumnInRange(column)) {
            LOGGER.severe("Column index is out of range!");
            return null;
        }
        return clues[column];
    }

    /**
     * Returns the clue on specified position, INCLUDING empty spaces!
     */
    private int getTrueClueInwards(int column, int clueIndex) {
        if (!isColumnInRange(column)) {
            LOGGER.severe("Column index is out of range!");
            return 0;
        }

        if (!isIndexInRange(column, clueIndex)) {
            LOGGER.severe("clueIndex is out of range!");
            return 0;
        }


        return clues[column][clues[column].length - 1 - clueIndex];
    }

    /**
     * Returns the clue on specified position, INCLUDING empty spaces!
     */
    private int getTrueClueOutwards(int column, int clueIndex) {
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
     * Returns the clue on specified position in specified direction. INCLUDING empty spaces!
     */
    public int getTrueClue(IODirection dir, int column, int clueIndex) {
        return dir == IODirection.INWARDS ? getTrueClueInwards(column, clueIndex) : getTrueClueOutwards(column, clueIndex);
    }


    /**
     * Returns the clue on specified position, SKIPPING empty spaces
     *
     * @param clueIndex numbered from the OUTSIDE towards the board! Not counting empty spaces
     */
    private int getClueInwards(int column, int clueIndex) {

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
     * Returns the clue at specified position. Skipping empty spaces!
     */
    public int getClue(IODirection dir, int column, int clueIndex) {
        return dir == IODirection.INWARDS ? getClueInwards(column, clueIndex) : getClueOutwards(column, clueIndex);
    }

    /**
     * Returns the clue on specified position
     * Skipping empty spaces
     *
     * @param clueIndex numbered from the INSIDE towards the edge! Skipping empty spaces
     */
    private int getClueOutwards(int column, int clueIndex) {
        if (!isColumnInRange(column)) {
            LOGGER.severe("Column index is out of range!");
            return 0;
        }

        if (!isIndexInRange(column, clueIndex)) {
            LOGGER.severe("clueIndex is out of range!");
            return 0;
        }

        int index = 0;
        while (clueIndex > 0 || clues[column][index] == 0) {
            if (clues[column][index] != 0) {
                clueIndex--;
            }
            index++;
            if (!isIndexInRange(column, index)) {
                return 0;
            }
        }

        return clues[column][index];
    }


    /**
     * Sets currently browsed column. Used for iterating! Automatically sets the direction to INWARDS.
     */
    public void setCurrCol(int currCol) {
        if (!isColumnInRange(currCol)) {
            LOGGER.warning("trying to set currCol out of range!");
            this.currCol = 0;
            setIterDirection(IODirection.INWARDS);
        }
        this.currCol = currCol;
        setIterDirection(IODirection.INWARDS);
    }

    /**
     * Sets the iteration direction to specified value, also sets the currIndex to the according value
     */
    public void setIterDirection(IODirection dir) {
        iterDirection = dir;
        if (dir == IODirection.OUTWARDS) currIndex = 0;
        else currIndex = clues[currCol].length - 1;
    }

    /**
     * Iterates through the column in set direction
     */
    public Iterator<Integer> iterator() {
        return this;
    }

    public boolean hasNext() {
        if (!isColumnInRange(currCol) || !isIndexInRange(currIndex)) return false;
        if (iterDirection == IODirection.INWARDS) {
            while (clues[currCol][currIndex] == 0 && currIndex > 0) {
                currIndex--;
            }
            return clues[currCol][currIndex] != 0;
        } else {
            while (clues[currCol][currIndex] == 0 && currIndex < clues[currCol].length - 1) {
                currIndex++;
            }
            return clues[currCol][currIndex] != 0;
        }
    }

    public Integer next() {
        if (!hasNext()) return 0;
        if (currIndex > clues[currCol].length - 1 || currIndex < 0) {
            LOGGER.severe("Iterator called after the end!");
            throw new NoSuchElementException();
        }
        int toReturn = clues[currCol][currIndex];
        if (toReturn == 0) {
            LOGGER.severe("hasNext passed while toReturn was 0! Returning 0");
            return 0;
        }
        if (iterDirection == IODirection.OUTWARDS) currIndex++;
        else currIndex--;
        return toReturn;
    }

    public void remove() {

    }

    private int[] getCluesInwards(int column) {
        if (!isColumnInRange(column)) {
            LOGGER.severe("Column out of range!");
            return null;
        }
        return reverseArray(clues[column]);
    }

    public int getCurrCol() {
        return currCol;
    }

    /**
     * Sets the array of Clues in specified col, direction
     */
    public void setClues(IODirection dir, int column, int[] array) {
        if (dir == IODirection.INWARDS) {
            setCluesInwards(column, array);
        } else {
            setCluesOutwards(column, array);
        }
    }

    /**
     * Returns the array of Clues in specified col, direction
     */
    public int[] getClues(IODirection dir, int column) {
        return dir == IODirection.INWARDS ? getCluesInwards(column) : getCluesOutwards(column);
    }
}