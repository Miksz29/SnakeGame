import java.awt.*;// the library to build GUI's and Paint graphics
import java.awt.event.*;// the library for event handling like providing classes for mouse and keyboard events and capturing activities like mouse clicks, key presses, and winodw movements.
import java.util.ArrayList;//the library for array lists
import java.util.Random; //the library for random number generation
import javax.swing.*; //the library for the GUI

public class SnakeGame extends JPanel implements ActionListener, KeyListener{ //the class that will be used to create the game
    private class Tile{ //the class that will be used to create the tiles of the game
        int x; //the x coordinate of the tile
        int y; //the y coordinate of the tile

        Tile(int x, int y){
            this.x = x; //the x coordinate of the tile is set to the value of the parameter x
            this.y = y;// the y coordinate of the tile is set to the value of the parameter y
        }
    }
    int boardWidth; //board width variable
    int boardHeight; //board height variable
    int tileSize= 25;

    //snake variables
    Tile snakeHead; //the tile that will be used to represent the snake's head
    ArrayList<Tile> snakeBody; //the array list that will be used to represent the snake's body
    //Food variables
    Tile food; //the tile that will be used to represent the food
    Random random; //the random number generator that will be used to generate random numbers for the food's position

    //gmae logic variables
    Timer gameLoop; //the timer that will be used to control the game loop
    int velocityX;
    int velocityY;
    boolean gameOver = false; //the boolean that will be used to determine if the game is over

    SnakeGame(int boardWidth, int boardHeight){
        this.boardHeight = boardHeight; // this.boardHeight is to distinguish the parameter boardHeight from the instance variable boardHeight. The this keyword is used to refer to the current object and its instance variables.
        this.boardWidth = boardWidth; // this.boardWidth is to distinguish the parameter boardWidth from the instance variable boardWidth. The this keyword is used to refer to the current object and its instance variables.
        setPreferredSize(new Dimension(this.boardWidth, this.boardHeight)); //set the preferred size of the panel to the specified width and height    }
        setBackground(Color.black); //set the background color of the panel to black
        addKeyListener(this); //add the key listener to the panel to listen for key events
        setFocusable(true); //set the panel to be focusable so that it can receive key events

        random = new Random();// create a new instance of the Random class to generate random numbers
        restartGame();
    }   

    public void restartGame(){
        snakeHead = new Tile(5, 5); //create a new tile at the specified coordinates to represent the snake's head
        snakeBody = new ArrayList<Tile>(); //create a new array list to represent the snake's body

        food = new Tile(10, 10); //create a new tile at the specified coordinates to represent the food
        placeFood(); //call the placeFood method to place the food at a random position on the board

        velocityX = 0; //set the initial velocity of the snake to 0
        velocityY = 0; //set the initial velocity of the snake to 0
        gameOver = false;

        if(gameLoop != null){
            gameLoop.stop();
        }

        gameLoop = new Timer(100, this);
        gameLoop.start(); //start the game loop
        requestFocusInWindow();
        repaint();
    }
    public void paintComponent(Graphics g){ //the funcion that will be use to draw the game and snake
        super.paintComponent(g);
        draw(g);
    }
    public void draw(Graphics g){ 
        //Grid
        // for(int i = 0; i < boardWidth / tileSize; i++){ //loop through the width of the board divided by the size of the tiles
        //   g.drawLine(i*tileSize, 0, i*tileSize, boardHeight); //draw a vertical line at the specified coordinates
        //   g.drawLine(0, i*tileSize, boardWidth, i*tileSize); //draw a horizontal line at the specified coordinates
        // }

        //Food
        g.setColor(Color.red); //set the color of the graphics object to red or the color of the pen/food
        //g.fillRect(food.x * tileSize, food.y * tileSize, tileSize, tileSize); //fill a rectangle at the specified coordinates with the specified width and height or drawing the rectangle/food.
        g.fill3DRect(food.x * tileSize, food.y * tileSize, tileSize, tileSize, true);

        //Snake head
        g.setColor(Color.green); //set the color of the graphics object to green or the color of the pen/snake
        //g.fillRect(snakeHead.x  * tileSize, snakeHead.y * tileSize, tileSize, tileSize); //fill a rectangle at the specified coordinates with the specified width and height or drawing the rectangle/snake.
        g.fill3DRect(snakeHead.x  * tileSize, snakeHead.y * tileSize, tileSize, tileSize, true);

        
        //Snake body
        for(int i = 0; i < snakeBody.size(); i++){ //loop through the size of the snake's body
            Tile snakePart = snakeBody.get(i);
            //g.fillRect(snakePart.x * tileSize, snakePart.y * tileSize, tileSize, tileSize);
            g.fill3DRect(snakePart.x * tileSize, snakePart.y * tileSize, tileSize, tileSize, true); //fill a 3D rectangle at the specified coordinates with the specified width and height or drawing the 3D rectangle/snake.
        }

        //Score
        g.setFont(new Font("Arial", Font.BOLD, 16));
        if(gameOver){
            g.setColor(Color.red);
            g.drawString("Game Over: " + String.valueOf(snakeBody.size()), tileSize - 16, tileSize);
        }
        else{
            g.drawString("Score: " + String.valueOf(snakeBody.size()), tileSize - 16, tileSize);
        }
    }

    public void placeFood(){ //the function that will be used to place the food at a random X and Y position on the board
        food.x = random.nextInt(boardWidth / tileSize);//used to generate a random integer between 0 (inclusive) and the specified value (exclusive) for the x coordinate of the food
        food.y = random.nextInt(boardHeight/ tileSize);//used to generate a random integer between 0 (inclusive) and the specified value (exclusive) for the y coordinate of the food

    }

    public boolean collision(Tile tile1, Tile tile2){ //the function that will be used to check if the snake's head has collided with the food or the snake's body}
        return tile1.x == tile2.x && tile1.y == tile2.y; //return true if the x and y coordinates of the two tiles are equal, otherwise return false
    }

    public void move(){ 
        //eat food
        if(collision((snakeHead), food)){ //check if the snake's head has collided with the food
            snakeBody.add(new Tile(food.x, food.y)); //add a new tile to the snake's body at the position of the food}
            placeFood(); //call the placeFood method to place the food at a random position on the board
        }

        //snake body movment
        for(int i = snakeBody.size() - 1; i >= 0; i--){ //loop through the size of the snake's body in reverse order
            Tile snakePart = snakeBody.get(i); // get the tile at the specified index of the snake's body           
             if(i == 0){
                snakePart.x = snakeHead.x; //set the x coordinate of the tile to the x coordinate of the snake's head
                snakePart.y = snakeHead.y; //set the y coordinate of the tile to the y coordinate of the snake's head
            }
            else{
                Tile prevSnakePart = snakeBody.get(i-1); //get the tile at the specified index of the snake's body
                snakePart.x = prevSnakePart.x; //set the x coordinate of the tile to the x coordinate of the previous tile in the snake's body
                snakePart.y = prevSnakePart.y; //set the y coordinate of the tile to the y coordinate of the previous tile in the snake's body
            }
        }



        //snake Head Movement
        snakeHead.x += velocityX; //update the x coordinate of the snake's head by adding the current velocity in the x direction
        snakeHead.y += velocityY; //update the y coordinate of the snake's head by adding the current velocity in the y direction


        //game over conditions
        for(int i = 0 ; i <snakeBody.size(); i++){//loop through the size of the snake's body
            Tile snakePart = snakeBody.get(i); // get the tile at the specified index of the snake's body
            //collide with the snake head
            if(collision(snakeHead, snakePart)){ //check if the snake's head has collided with the snake's body
                gameOver = true; //set the gameOver variable to true if the snake's head has collided with the snake's body
            }
        }

        if(snakeHead.x *tileSize < 0 || snakeHead.x * tileSize > boardWidth ||
            snakeHead.y *tileSize < 0 || snakeHead.y * tileSize > boardHeight){ //check if the snake's head has collided with the wall
                gameOver = true; //set the gameOver variable to true if the snake's head has collided with the wall
            }
    }   

    @Override
    public void actionPerformed(ActionEvent e) {
        move(); //call the move method to move the snake
        repaint(); //repaint the panel to update the graphics
        if (gameOver){ // check if the game is over
            gameLoop.stop(); //stop the game loop if the game is over
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
       if (e.getKeyCode() == KeyEvent.VK_UP && velocityY != 1){
            velocityX = 0;
            velocityY = -1;
       }
       else if (e.getKeyCode() == KeyEvent.VK_DOWN && velocityY != -1){
            velocityX = 0;
            velocityY = 1;
       }
       else if (e.getKeyCode() == KeyEvent.VK_LEFT && velocityX != 1){
            velocityX = -1;
            velocityY = 0;
       }
       else if (e.getKeyCode() == KeyEvent.VK_RIGHT && velocityX != -1){
            velocityX = 1;
            velocityY = 0;
       }
    }
    




    //do not need to implement these methods but they are required by the KeyListener interface
    @Override
    public void keyTyped(KeyEvent e) {}
    @Override
    public void keyReleased(KeyEvent e) {}
}