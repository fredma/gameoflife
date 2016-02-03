import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.util.*;

public class GameOfLife extends JPanel {

    class Cell {
        public boolean exist;
        public byte nb_neighbours;
    }

    class CellPosition {
        public int x;
        public int y;

        public CellPosition(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    private ArrayList<CellPosition> deadCells;
    private ArrayList<CellPosition> bornCells;
    private BufferedImage canvas;
    private int width;
    private int height;
    private int zoom;
    private Cell[][] grid;

    public GameOfLife(int w, int h, int z) {
        canvas = new BufferedImage(w,h, BufferedImage.TYPE_INT_RGB);
        grid = new Cell[h][w];
        width = w;
        height = h;
        zoom = z;

        for (int j = 0; j < h; j++) {
            for (int i = 0; i < width; i++) {
                grid[j][i] = new Cell();
            }
        }
        deadCells = new ArrayList<CellPosition>();
        bornCells = new ArrayList<CellPosition>();
    }


    public void populate() {
        Random rand = new Random();
        for (int y = 0; y < height; y++) {
            for(int x = 0; x < width; x++) {
                if (rand.nextBoolean())
                    addCell(y,x);
            }
        }
    }

    public void paint(Graphics g) {
        paintCanvas();
        g.drawImage(canvas, 
                    0, 0, width * zoom, height * zoom,
                    0, 0, width, height,
                    this);
        step();
        repaint();
    }

    public void paintCanvas() {
        Random rand = new Random();
        for (int y = 0; y < height; y++) {
            for(int x = 0; x < width; x++) {
                int r = grid[y][x].exist ? rand.nextInt(255) + 1 : 0;
                int g = grid[y][x].exist ? rand.nextInt(255) + 1 : 0;
                int b = grid[y][x].exist ? rand.nextInt(255) + 1 : 0;
                canvas.setRGB(x,y, r << 16 | g << 8 | b);
            }
        }
    }

    public byte getCountNeighbours(int y, int x) {
        return grid[y][x].nb_neighbours;
    }

    public void updateNeighbours(int y, int x, boolean inc)
    {
        int row_start = y - 1;
        int row_end = y + 1;
        int col_start = x - 1;
        int col_end = x + 1;
        if (y == 0)
            row_start = 0;

        if (y == height - 1)
            row_end = y;

        if (x == 0)
            col_start = 0;

        if (x == width - 1)
            col_end = x;
        
        for (int row = row_start; row <= row_end; row++) {
            for (int col = col_start; col <= col_end; col++) {
                if (row == y && col == x)
                    continue;
                if (inc)
                    grid[row][col].nb_neighbours++;
                else {
                    grid[row][col].nb_neighbours--;
                    if (grid[row][col].nb_neighbours < 0)
                        grid[row][col].nb_neighbours = 0;
                }
            }
        }
    }

    public void addCell(int y, int x) {
        grid[y][x].exist = true;
        updateNeighbours(y, x, true);
    }

    public void removeCell(int y, int x) {
        grid[y][x].exist = false; 
        updateNeighbours(y, x, false);
    }
    
    public boolean isCell(int y, int x) {
        return grid[y][x].exist;
    }

    public void step() {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (grid[y][x].exist) {
                    if (grid[y][x].nb_neighbours <= 1 || grid[y][x].nb_neighbours >= 4)//death
                       deadCells.add(new CellPosition(x,y));
                }
                else {
                    if (grid[y][x].nb_neighbours == 3)//birth
                       bornCells.add(new CellPosition(x,y));
                }
            }
        }

        for (CellPosition pos : deadCells)
            removeCell(pos.y, pos.x); 

        for (CellPosition pos : bornCells)
            addCell(pos.y, pos.x);

        deadCells.clear();
        bornCells.clear();
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Game of life");
        if (args.length < 2) {
            System.out.println("Syntax: java GameOfLife width height <zoom>");
            System.out.println("Example (default zoom level 2): java GameOfLife 300 200");
            System.out.println("Example (zoom level 4): java GameOfLife 300 200 4");
            return;
        }
        int zoom = 2;
        if (args.length == 3) {
            zoom = Integer.parseInt(args[2]);
            if (!(zoom > 0 && zoom <= 5)) {
                System.out.println("Error: zoom must be > 0 and <= 5");
                return;
            }
        }
        int w = Integer.parseInt(args[0]);
        int h = Integer.parseInt(args[1]);
        GameOfLife game = new GameOfLife(w,h,zoom);
        game.populate();
        frame.getContentPane().add(game);
        frame.setSize(w*zoom,h*zoom);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
