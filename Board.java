import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;

public class Board implements MouseListener {
    private int x1coord, y1coord;
    private final int x, y;
    private JFrame frame;
    private GridSquare[][] gridSquares;
    private boolean[][] enabledGrid;
    private JLabel statusLabel;
    private JTextArea gameLogArea;
    ArrayList<Point> availableGrids;
    private boolean isPlayerTurn = true;

    public Board(int x, int y) {
        this.x = x;
        this.y = y;
        gridSquares = new GridSquare[x][y];
        enabledGrid = new boolean[x][y];
    }

    public void createBoard() {
        frame = new JFrame("Yocky Choccy");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(700, 500);
        frame.setResizable(false);

        // Main panel with border layout
        JPanel mainPanel = new JPanel(new BorderLayout());
        frame.add(mainPanel);

        // Top label
        JLabel topLabel = new JLabel("Last to eat Soap loses", SwingConstants.CENTER);
        mainPanel.add(topLabel, BorderLayout.NORTH);

        // Center panel for game grid
        JPanel gridPanel = new JPanel(new GridLayout(x, y, 1, 1));
        
        // Sidebar panel
        JPanel sidebarPanel = new JPanel();
        sidebarPanel.setLayout(new BoxLayout(sidebarPanel, BoxLayout.Y_AXIS));
        sidebarPanel.setPreferredSize(new Dimension(250, 450));
        
        // Status label
        statusLabel = new JLabel("Game Status: Player's Turn", SwingConstants.CENTER);
        statusLabel.setFont(new Font("Arial", Font.BOLD, 14));
        
        // Game log area
        gameLogArea = new JTextArea(10, 20);
        gameLogArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(gameLogArea);
        
        // Add components to sidebar
        sidebarPanel.add(Box.createVerticalStrut(10));
        sidebarPanel.add(statusLabel);
        sidebarPanel.add(Box.createVerticalStrut(10));
        sidebarPanel.add(new JLabel("Game Log:"));
        sidebarPanel.add(scrollPane);

        // Create grid squares
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                gridSquares[i][j] = new GridSquare(i, j);
                gridSquares[i][j].setPreferredSize(new Dimension(50, 50));
                gridSquares[i][j].setColor(i, j);
                gridSquares[i][j].addMouseListener(this);
                enabledGrid[i][j] = true;
                gridPanel.add(gridSquares[i][j]);
            }
        }

        // Add panels to main panel
        mainPanel.add(gridPanel, BorderLayout.CENTER);
        mainPanel.add(sidebarPanel, BorderLayout.EAST);

        frame.setVisible(true);
        frame.validate();
        frame.repaint();

        // Initial log message
        gameLogArea.append("Game Started: Player's Turn\n");
    }

    private void updateStatus(String message) {
        statusLabel.setText("Game Status: " + message);
    }

    private void logMove(String player, int x, int y) {
        gameLogArea.append(player + " selected grid (" + x + "," + y + ")\n");
        gameLogArea.setCaretPosition(gameLogArea.getDocument().getLength());
    }

    private void handleGameOver(String message, boolean playerWon) {
        updateStatus("Game Over");
        int response = JOptionPane.showConfirmDialog(
            null, 
            message + "\n\nDo you want to play again?", 
            "Game Over", 
            JOptionPane.YES_NO_OPTION
        );

        if (response == JOptionPane.YES_OPTION) {
            // Close existing frame
            if (frame != null) {
                frame.dispose();
            }

            // Restart the game by opening a new board size selection
            Main.main(new String[]{});
        } else {
            System.exit(0);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        // Only allow click if it's player's turn
        if (!isPlayerTurn) return;

        Object selected = e.getSource();

        if (selected instanceof GridSquare clickedButton) {
            x1coord = clickedButton.getXcoord();
            y1coord = clickedButton.getYcoord();

            clickedButton.setBackground(Color.RED);
            
            // Check for soap square
            if (x1coord == 0 && y1coord == 0) {
                logMove("Player", x1coord, y1coord);
                handleGameOver("Computer Won!", false);
                return;
            }
            
            // Check if grid is disabled
            if (!enabledGrid[x1coord][y1coord]) {
                JOptionPane.showMessageDialog(null, "This grid is disabled! Please select another grid.");
                return;
            }
            
            // Disable grids
            disableGridSquares(x1coord, y1coord);
            
            // Log player move
            logMove("Player", x1coord, y1coord);
            
            // Switch turn
            isPlayerTurn = false;
            updateStatus("Computer's Turn");
        }

        // Computer's turn
        computerTurn();
    }

    public void computerTurn() {
        // Delay to simulate thinking
        Timer thinkTimer = new Timer(1000, e -> {
            availableGrids = getAvailableGrids();

            Random rand = new Random();
            int index = rand.nextInt(availableGrids.size());
            Point selectedPoint = availableGrids.get(index);
            int xcoord = selectedPoint.x;
            int ycoord = selectedPoint.y;
        
            gridSquares[xcoord][ycoord].setBackground(Color.red);
        
            // Disable grids
            disableGridSquares(xcoord, ycoord);

            // Log computer move
            logMove("Computer", xcoord, ycoord);

            // Check for soap square
            if(xcoord == 0 && ycoord == 0) {
                handleGameOver("You won!", true);
                return;
            }

            // Switch turn back to player
            isPlayerTurn = true;
            updateStatus("Player's Turn");
        });
        thinkTimer.setRepeats(false);
        thinkTimer.start();
    }

    // Rest of the methods remain the same as in previous implementation...

    public ArrayList<Point> getAvailableGrids() {
        availableGrids = new ArrayList<>();
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                if (enabledGrid[i][j]) {
                    availableGrids.add(new Point(i, j));
                }
            }
        }
        return availableGrids;
    }

    public void disableGridSquares(int xcoord, int ycoord) {
        for (int i = xcoord; i < x; i++) {
            for (int j = ycoord; j < y; j++) {
                gridSquares[i][j].setBackground(Color.WHITE);
                gridSquares[i][j].setForeground(Color.WHITE);
                gridSquares[i][j].setEnabled(false);
                enabledGrid[i][j] = false;
            }
        }
    }

    // Mouse listener methods
    @Override
    public void mousePressed(MouseEvent e) {}
    @Override
    public void mouseReleased(MouseEvent e) {}
    @Override
    public void mouseEntered(MouseEvent e) {}
    @Override
    public void mouseExited(MouseEvent e) {}
}