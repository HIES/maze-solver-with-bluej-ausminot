public class Maze
{
    private Cell[][] board;
    private final int DELAY = 200;

    public Maze(int rows, int cols, int[][] map){
        StdDraw.setXscale(0, cols);
        StdDraw.setYscale(0, rows);
        board = new Cell[rows][cols];
        
        int height = board.length - 1;
        for (int r = 0; r < rows; r++)
            for (int c = 0; c < cols; c++) {
                board[r][c] = map[r][c] == 1 ? new Cell(c , height - r, 0.5, false) : new Cell(c, height - r, 0.5, true);
            }
    }

    public void draw()
    {
        for (int r = 0; r < board.length; r++)
            for (int c = 0; c < board[r].length; c++){
                Cell cell = board[r][c];
                StdDraw.setPenColor(cell.getColor());
                StdDraw.filledSquare(cell.getX(), cell.getY(), cell.getRadius());
            }
            StdDraw.show();
    }

    private boolean isValid(int row, int col)
    {
        boolean valid = false;
        if (row >= 0 && row < board.length && col >= 0 && col < board[row].length)
        {
            if (!board[row][col].isWall() && !board[row][col].isVisited())
            {
                valid = true;
            }
        }
        return valid;
    }

    private boolean isExit(int row, int col)
    {
        if (board[row][col].equals(board[board.length-1][board[0].length-1]))
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public boolean findPath(int row, int col)
    {
        boolean isComp = false;
        
        if (isValid(row, col))
        {
            board[row][col].visitCell();
            this.draw();
            StdDraw.pause(DELAY);
            
            if (isExit(row, col))
            {
                isComp = true;
            }
            else
            {
                if (!isComp)
                {
                    isComp = findPath(row, col+1);
                }
                if (!isComp)
                {
                    isComp = findPath(row-1, col);
                }
                if (!isComp)
                {
                    isComp = findPath(row+1, col);
                }
                if (!isComp)
                {
                    isComp = findPath(row, col-1);
                }
            }
        }
        if (isComp)
        {
            board[row][col].becomePath();
            this.draw();
            StdDraw.pause(DELAY);
        }
        
        return isComp;
    }

    public static void main(String[] args) {
        StdDraw.enableDoubleBuffering();
        int[][] maze = {{1,1,0,0,0,0,0,1,0,0},
                        {0,1,1,1,1,0,1,1,1,0},
                        {0,1,1,1,1,0,1,1,0,0},
                        {0,1,0,1,1,1,1,1,1,0},
                        {0,0,0,0,0,1,0,1,1,0},
                        {0,1,1,1,1,1,0,1,1,0},
                        {0,1,1,0,0,1,0,0,0,0},
                        {0,0,1,0,1,1,0,0,1,0},
                        {0,0,0,0,1,1,0,0,1,0},
                        {0,0,0,0,0,0,0,0,1,1}};
        Maze geerid = new Maze(maze.length, maze[0].length, maze);
        geerid.draw();
        geerid.findPath(0, 0);
        geerid.draw();
    }
}
