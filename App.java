import java.awt.BorderLayout;
import javax.swing.*; //the library for the GUI


public class App {
    public static void main(String[] args) throws Exception { //the main method that runs the program
            int boardWidth = 600; //the width of the board
            int boardHeight = boardWidth; // the height of the board
            

            JFrame frame = new JFrame("Snake"); //the frame that will be displayed
            frame.setLocationRelativeTo(null); //center the frame on the screen
            frame.setResizable(false); //prevent the frame from being resized
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //exit the program when the frame is closed

            SnakeGame snakeGame = new SnakeGame(boardWidth, boardHeight); //create a new instance of the SnakeGame class
            JPanel container = new JPanel(new BorderLayout());
            container.add(snakeGame, BorderLayout.CENTER);

            JButton restartButton = new JButton("Restart");
            restartButton.addActionListener(e -> {
                snakeGame.restartGame();
            });
            container.add(restartButton, BorderLayout.SOUTH);

            frame.add(container); //add the snake game to the frame
            frame.pack(); //it will place the frame in its full dimensions
            frame.setVisible(true); //make the frame visible
            snakeGame.requestFocusInWindow();
    }
}
