import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class _2048 {

    //List of the Color constants
    public static final String ANSI_BLACK_BACKGROUND = "\u001B[40m";
    public static final String ANSI_RED_BACKGROUND = "\u001B[41m";
    public static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
    public static final String ANSI_YELLOW_BACKGROUND = "\u001B[43m";
    public static final String ANSI_BLUE_BACKGROUND = "\u001B[44m";
    public static final String ANSI_PURPLE_BACKGROUND = "\u001B[45m";
    public static final String ANSI_CYAN_BACKGROUND = "\u001B[46m";
    public static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";

    //create an empty 2048 board
    public static int[][] createBoard() {
        int board[][] = {{0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}};
        return board;
    }

    //getPossible takes one argument (a board to test)
    //checks the possible places to add a new tile and returns them
    public static ArrayList<ArrayList<Integer>> getPossible(int[][] board) {
        ArrayList<ArrayList<Integer>> possibleLoc = new ArrayList<ArrayList<Integer>>();
        //iterate through the board
        for (int x = 0; x < board.length; x++) {
            for(int y = 0; y < board[x].length; y++) {
                //if the tile has no number add location to possibleLoc array
                if (board[x][y] == 0) {
                    ArrayList<Integer> location = new ArrayList<Integer>();
                    location.add(x);
                    location.add(y);
                    possibleLoc.add(location);
                }
            }
        }
        return possibleLoc;
    }

    //getRandLoc takes one argument (An array of the possible locations)
    //returns a random X Y coordinate pair from the list of possible locations
    public static ArrayList<Integer> getRandLoc(ArrayList<ArrayList<Integer>> possibleLocs) {
        int randomLoc = (int) (Math.random() * possibleLocs.size());
        return possibleLocs.get(randomLoc);
    }

    //add Tile takes one argument (the board to add a tile to)
    //Generates a random number 2 or 4 weighted to produce more twos
    //adds the number to an open space on the board
    public static void addTile(int[][] board) {
        //generate a 4 at 10% odds and a 2 at 90%
        int value = (int) (Math.random() * 10);
        if (value !=4) {
            value = 2;
        }
        //get the possible locations of the board
        ArrayList<ArrayList<Integer>> possibleLocs = getPossible(board);
        //get a random location from the possible locations
        ArrayList<Integer> randomLoc = getRandLoc(possibleLocs);
        //add the value to the board
        board[randomLoc.get(0)][randomLoc.get(1)] = value;
    }

    //display takes one argument (a board to display)
    //prints out the board to the console
    public static void display(int[][] board) {
        //iterate through the board
        for (int row = 0; row < 4; row++) {
            for (int column = 0; column < 4; column++) {
                //color tiles acordingly
                if (board[row][column] == 0) {
                    System.out.print(ANSI_BLACK_BACKGROUND + ANSI_WHITE + board[row][column] + "\t");
                } else  if (board[row][column] < 16){
                    System.out.print(ANSI_GREEN_BACKGROUND + ANSI_BLACK + board[row][column] + "\t");
                } else if (board[row][column] < 256){
                    System.out.print(ANSI_YELLOW_BACKGROUND + ANSI_BLACK + board[row][column] + "\t");
                } else if (board[row][column] < 4096){
                    System.out.print(ANSI_RED_BACKGROUND + ANSI_BLACK + board[row][column] + "\t");
                } else {
                    System.out.print(ANSI_WHITE_BACKGROUND + ANSI_BLACK + board[row][column] + "\t");
                }
            }
            //add a new line space
            System.out.print("\n");
        }
    }

    //clearScreen clears the console
    public static void clearScreen() {
        //attempt to get the os name
        try
        {
            final String os = System.getProperty("os.name");

            //if the OS is Windows
            if (os.contains("Windows"))
            {
                Runtime.getRuntime().exec("cls");
            }
            else
            {
                System.out.println("\033\143");
            }
        }
        //if the os does not provide a name
        catch (final Exception e)
        {
            System.out.println("\033\143");
        }
    }

    //right takes one argument (the board to move right)
    public static void right(int[][] board) {
        //shift tiles over
        shiftRight(board);
        //iterate through and combine mathing tiles
        for (int x = 3; x >= 0; x--) {
            for (int y = 3; y >= 0; y--) {
                int currentPos[] = {x, y};
                //check if tiles are matching
                if (currentPos[1] - 1 >= 0 && board[x][y-1] == board[x][y]) {
                    board[x][y-1] *= 2;
                    board[x][y] = 0;
                }
            }
        }
        //shift board again to account for tiles that were matched
        shiftRight(board);
    }

    //shiftRight takes one argument (the board to shift)
    public static void shiftRight(int[][] board) {
        //iterate through the board 4 times
        for (int i =0; i < 4; i++) {
            for (int x = 0; x < 4; x++) {
                for (int y = 0; y < 4; y++) {
                    int currentPos[] = {x, y};
                    //check that other tile is empty and move tile over
                    if (currentPos[1] + 1 < 4 && board[x][y+1] == 0) {
                        board[x][y+1] = board[x][y];
                        board[x][y] = 0;
                    }
                }
            }
        }
    }

    //up takes one argument (the board to shift)
    public static void up(int[][] board) {
        //shift tiles over
        shiftUp(board);
        //iterate through and combine matching tiles
        for (int x = 0; x < 4; x++) {
            for (int y = 0; y < 4; y++) {
                int currentPos[] = {x, y};
                //check if tiles are matching
                if (currentPos[0] + 1 < 4 && board[x+1][y] == board[x][y]) {
                    board[x+1][y] *= 2;
                    board[x][y] = 0;
                }
            }
        }
        //shift tiles again to acount for tiles that were matched
        shiftUp(board);
    }

    //shiftUp takes one argument (the board to shift)
    public static void shiftUp(int[][] board) {
        //iterate through the board 4 times
        for (int i =0; i < 4; i++) {
            for (int x = 3; x >= 0; x--) {
                for (int y = 3; y >= 0; y--) {
                    int currentPos[] = {x, y};
                    //check that other tile is empty and move tile over
                    if (currentPos[0] - 1 >= 0 && board[x-1][y] == 0) {
                        board[x-1][y] = board[x][y];
                        board[x][y] = 0;
                    }
                }
            }
        }
    }

    //left takes one argument (the board to shift)
    public static void left(int[][] board) {
        //shift tiles over
        shiftLeft(board);
        //iterate through and combine matching tiles
        for (int x = 0; x < 4; x++) {
            for (int y = 0; y < 4; y++) {
                int currentPos[] = {x, y};
                if (currentPos[1] + 1 < 4 && board[x][y+1] == board[x][y]) {
                    board[x][y+1] *= 2;
                    board[x][y] = 0;
                }
            }
        }
        //shift tiles agian to acount for tiles that were matched
        shiftLeft(board);
    }

    //shiftLeft takes one argument (the board to shift)
    public static void shiftLeft(int[][] board) {
        //iterate throught the board 4 times
        for (int i =0; i < 4; i++) {
            for (int x = 3; x >= 0; x--) {
                for (int y = 3; y >= 0; y--) {
                    int currentPos[] = {x, y};
                    //check that other tile is empty and move tile over
                    if (currentPos[1] - 1 >= 0 && board[x][y-1] == 0) {
                        board[x][y-1] = board[x][y];
                        board[x][y] = 0;
                    }
                }
            }
        }
    }

    //down takes one argument (the board to shift)
    public static void down(int[][] board) {
        //shift tiles over
        shiftDown(board);
        //iterate throught and combine matching tiles
        for (int x = 3; x >= 0; x--) {
            for (int y = 3; y >= 0; y--) {
                int currentPos[] = {x, y};
                if (currentPos[0] - 1 >= 0 && board[x-1][y] == board[x][y]) {
                    board[x-1][y] *= 2;
                    board[x][y] = 0;
                }
            }
        }
        //shift tiles again to account for tiles that were matched
        shiftDown(board);
    }

    //shiftDown takes one argument (the board to shift)
    public static void shiftDown(int[][] board) {
        //iterate throught the board 4 times
        for (int i =0; i < 4; i++) {
            for (int x = 0; x < 4; x++) {
                for (int y = 0; y < 4; y++) {
                    int currentPos[] = {x, y};
                    //check that other tiles is empty and move tile over
                    if (currentPos[0] + 1 < 4 && board[x+1][y] == 0) {
                        board[x+1][y] = board[x][y];
                        board[x][y] = 0;
                    }
                }
            }
        }
    }

    //simulateMoves takes one argument (board to simulate)
    //tests every possible combination of moves on the board
    //returns True if no possible moves exist
    public static boolean simulateMoves(int[][] board) {
        //create a duplicate board
        int simulator[][] = new int[4][];
        for (int i = 0; i < 4; i++) {
            simulator[i] = Arrays.copyOf(board[i] ,board[i].length);
        }

        //test directions
        up(simulator);
        if (!Arrays.deepEquals(board, simulator)) {
            return  false;
        }
        down(simulator);
        if (!Arrays.deepEquals(board, simulator)) {
            return false;
        }
        left(simulator);
        if (!Arrays.deepEquals(board, simulator)) {
            return false;
        }
        right(simulator);
        if (!Arrays.deepEquals(board, simulator)) {
            return false;
        }
        //return if no directions are possible
        return true;
    }

    //runGame takes two arguments (a board to play on and a Scanner for input)
    //runs the main logic of the game and gets user input
    public static boolean runGame(int[][] board, Scanner scanner) {
        //add fist two game tiles
        addTile(board);
        addTile(board);
        //display board
        clearScreen();
        display(board);
        //game loop
        while (true) {
            System.out.print("::");
            String input = scanner.nextLine();
            String message = "";
            int oldArray[][] = new int[4][];
            for (int i = 0; i < 4; i++) {
                oldArray[i] = Arrays.copyOf(board[i] ,board[i].length);
            }
            boolean validPlay = true;
            //get user input direction
            if (input.equals("w")) {
                up(board);
            } else if (input.equals("a")) {
                left(board);
            } else if (input.equals("s")) {
                down(board);
            } else if (input.equals("d")) {
                right(board);
            } else {
                //bad value output
                message = "Invalid input! please only enter (W, A, S, D)";
                validPlay = false;
            }
            //checks if board changed after input
            if (Arrays.deepEquals(board, oldArray)) {
                validPlay = false;
            }
            //if board changed add a new tile
            if (validPlay) {
                addTile(board);
            }
            //update the screen
            clearScreen();
            display(board);
            //test if possible moves exist
            boolean gameOver = simulateMoves(board);
            if (gameOver) {
                break;
            }
            System.out.println(ANSI_BLACK_BACKGROUND + ANSI_WHITE + message);
        }
        //once the game is over and breaks out promt for another game
        System.out.println(ANSI_BLACK_BACKGROUND + ANSI_WHITE + "Game Over");
        System.out.print("Would you like to play again? (y/n): ");
        String answer = scanner.nextLine();
        //run another game
        if (answer.equals("y")) {
            return true;
        }
        //don't run another game
        return false;
    }

    //main function
    //contians the game loop
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        //initialized board
        int board[][] = createBoard();
        //game loop
        while(runGame(board, scanner)) {
            board = createBoard();
        }
        scanner.close();
    }

}
