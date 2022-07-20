
import java.awt.*;
import java.util.LinkedList;

/**
 * This class is responsible for holding the percolation grid representation.
 * Grid is represented as: 0 = blocked spot, 1 = open position, 2 = open and connected to top
 */
public class Percolation {
    private final int gridSize;
    private final int n;
    private final int bottomSite;
    private int numOfOpenSites;
    private final UnionFind uf;
    private int[][] grid;
    private LinkedList<Point> openSlots;

    /**
     * Creates an n-by-n grid with all sites blocked initially.
     * @param n - what size grid to have
     * @throws IllegalArgumentException - if size <= 0
     */
    public Percolation(int n) {
        if(n < 1)
            throw new IllegalArgumentException();
        openSlots = new LinkedList<>();
        uf = new UnionFind(n*n+2); // 0 = top virtual site, n+1 = bottom virtual site
        gridSize = n*n;
        bottomSite = n*n+1;
        this.n = n;
        numOfOpenSites = 0;
        grid = new int[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                grid[i][j] = 0;
            }
        }
    }

    /**
     * Opens a site at row column
     * @param row - what row to unlock
     * @param column - what column to unlock
     * @throws IllegalArgumentException - if the selected coordinate is not on the grid
     */
    public void open(int row, int column) {
        if (row < 1 || row > n || column < 1 || column > n)
            throw new IllegalArgumentException();
        if (grid[row-1][column-1] == 0) {
            numOfOpenSites++;
        }

        grid[row-1][column-1] = 1;
        openSlots.add(new Point(column-1,row-1));

        // if we open something in the first row, union it with the virtual top
        if (row-1 == 0) {
            uf.union(0, column);
            grid[row-1][column-1] = 2;
        }
        // if we open something in the bottom row, union it with the virtual bottom
        else if (row == n)
            uf.union((row-1)*n+column, bottomSite);

        // if the space right above the space is open, union them together
        if (row > 1 && grid[row-2][column-1] != 0) {
            uf.union((row - 1) * n + column, (row - 2) * n + column);
        }

        // if the space right below the selected space is open, union them together
        if (row < n && grid[row][column-1] != 0) {
            uf.union((row - 1) * n + column, (row) * n + column);
        }

        // if the space left of the selected space is open, union them together
        if (column > 1 && grid[row-1][column-2] != 0) {
            uf.union((row - 1) * n + column, (row - 1) * n + column - 1);
        }
        // if the space right of the selected space is open, union them together
        if (column < n && grid[row-1][column] != 0) {
            uf.union((row - 1) * n + column, (row - 1) * n + column + 1);
        }
        updateFull();
    }

    public void updateFull() {
        LinkedList<Point> toRemove = new LinkedList<>();
        for(Point i: openSlots) {
            int y = (int) i.getY();
            int x = (int) i.getX();
            if(uf.find((y*n) + x +1) == uf.find(0)) {
                grid[y][x] = 2;
                toRemove.add(i);
            }
        }
        for(Point i: toRemove)
            openSlots.remove(i);
    }



    /**
     * Is the selected row and column open or not
     * @param row - what row to check
     * @param column - what column to check
     * @throws IllegalArgumentException - if the selected coordinate is not on the grid
     * @return - whether or not a row,column is open (true if it is, false otherwise)
     */
    public boolean isOpen(int row, int column) {
        if (row < 1 || row > n || column < 1 || column > n)
            throw new IllegalArgumentException();
        if(grid[row-1][column-1] == 0)
            return false;
        return true;
    }

    /**
     * Checks to see if a coordinate is full
     * @param row - row to check
     * @param column - column to check
     * @return - returns true if the selected block is full, false otherwise
     * @throws IllegalArgumentException - if the selected coordinate is not on the grid
     */
    public boolean isFull(int row, int column) {
        if (row < 1 || row > n || column < 1 || column > n)
            throw new IllegalArgumentException();
        if (uf.find((row-1)*n+column) == uf.find(0))
            return true;
        return false;
    }

    /**
     * Checks to see how many cells are open on the grid
     * @return - how many cells are open
     */
    public int numberOfOpenSites() {
        return numOfOpenSites;
    }

    /**
     * Checks to see whether or not the system percolates
     * @return - True if the system does, false otherwise
     */
    public boolean percolates() {
        return (uf.find(0) == uf.find(gridSize+1));
    }




    @Override
    public String toString() {
        StringBuilder myString = new StringBuilder();
        for(int i=0;i<n;i++) {
            for(int j=0;j<n;j++) {
                myString.append(grid[i][j] + ",");
            }
            myString.append('\n');
        }
        return myString.toString();
    }

    /**
     * Return the current grid
     * @return - current grid array
     */
    public int[][] getGrid() {
        return grid;
    }
}