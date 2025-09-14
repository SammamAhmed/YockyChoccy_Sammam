import javax.swing.JOptionPane;

public class Main{
    public Main(){}

    public static void main(String[] args){

        String[] options = {"5x5", "6x6", "7x7"}; 
        int choice = JOptionPane.showOptionDialog(null, "Select the board size:", "Board Size Selection", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);


        int rows = 0, cols = 0;
        switch (choice) {
            case 0 -> {
                rows = 5;
                cols = 5;
            }
            case 1 -> {
                rows = 6;
                cols = 6;
            }
            case 2 -> {
                rows = 7;
                cols = 7;
            }
            default -> {
                JOptionPane.showMessageDialog(null, "No valid selection made. Exiting...");
                System.exit(0);
            }
        }
        Board Board = new Board(rows, cols);
        Board.createBoard();

    }
}