package tictactoe;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

public class GameBoard {
    private final static Scanner input = new Scanner(System.in);
    private boolean isGameOver = false;

    private Player player1;
    private Player player2;

    private Player currentPlayer;
    private String[][] gameBoard = new String[3][3];

    private String[][] testGameBoard = new String[3][3];
    private int[] validUserCoordinates = new int[2];
    private final String[] gamePieceChoices = {"X", "O"};
    private int gamePieceCount;
    private boolean opponentHasTwoPiecesInRow;
    private boolean currentPlayerHasTwoPiecesInRow;
    private int[] mediumCoordinates = new int[2];

    public GameBoard(Player player1, Player player2) {
        this.player1 = player1;
        this.player2 = player2;
        initializeGameBoard();
        printGameBoard();
    }

    // ***** GAME BOARD *****

    protected void initializeGameBoard() {

        for (int i = 0; i < getGameBoard().length; i++) {
            for (int j = 0; j < getGameBoard()[i].length; j++) {
                getTestGameBoard()[i][j] = " ";
            }
        }

        setGameBoard(getTestGameBoard());
    }

    protected void printGameBoard() {

        // print top row
        String topBottomBorder = "---------";
        System.out.println(topBottomBorder);

        // print game board
        for (int i = 0; i < getGameBoard().length; i++) {
            // print left border character
            String leftRightBorderCharacter = "|";
            System.out.print(leftRightBorderCharacter + " ");
            for (int j = 0; j < getGameBoard()[i].length; j++) {
                System.out.print(getGameBoard()[i][j] + " ");
            }
            // print right border character
            System.out.println(leftRightBorderCharacter);
        }

        // print bottom row
        System.out.println(topBottomBorder);

        // update number of game pieces on game board
        updateNumberOfPiecesOnBoard();

        // update current player
        chooseCurrentPlayer();

//        System.out.println("current player is " + getCurrentPlayer().getGamePiece());

    }

    private String[][] cloneGameBoard() {

        String[][] newGameBoard = new String[3][3];

        for (int i = 0; i < getGameBoard().length; i++) {
            for (int j = 0; j < getGameBoard()[i].length; j++) {
                newGameBoard[i][j] = getGameBoard()[i][j];
            }
        }

        return newGameBoard;
    }

    // ***** SWITCH GAME PIECE ON EVERY TURN *****

    private void chooseCurrentPlayer() {

        // if there are an even number of game pieces already on the board, the current player is player 1, otherwise it's player 2

        if (getGamePieceCount() % 2 == 0) {
            setCurrentPlayer(getPlayer1());
        } else {
            setCurrentPlayer(getPlayer2());
        }
    }

    // keeps track of the number of pieces currently on the game board
    private void updateNumberOfPiecesOnBoard() {
        int gamePieceCount = 0;

        for (int i = 0; i < getGameBoard().length; i++) {
            for (int j = 0; j < getGameBoard()[i].length; j++) {
                for (String gamePiece : getGamePieceChoices()) {
                    if (getGameBoard()[i][j].equals(gamePiece)) {
                        gamePieceCount++;
                    }
                }
            }
        }

        setGamePieceCount(gamePieceCount);
    }

    // ***** COMPUTER AI LOGIC *****

    protected void getComputerCoordinates() {

        String difficultyLevel = getCurrentPlayer().getType();

        System.out.println("Making move level " + "\"" + difficultyLevel + "\"");

        boolean computerCoordinatesValid = false;

        while(!computerCoordinatesValid) {

            Random random = new Random();

            int[] computerCoordinates = new int[2];

            if (difficultyLevel.equalsIgnoreCase("easy")) {
                computerCoordinates = getEasyCoordinates();
            } if (difficultyLevel.equalsIgnoreCase("medium")) {
                computerCoordinates = getMediumCoordinates();
            } if (difficultyLevel.equalsIgnoreCase("hard")) {
                computerCoordinates = getHardCoordinates();
            }

            // validate user coordinates

            computerCoordinatesValid = isValidInput(computerCoordinates[0], computerCoordinates[1]);

        }

    }

    // ***** HARD LEVEL LOGIC *****

    protected void printSimplifiedGameBoard() {
        for (int i = 0; i < getSimplifiedGameBoard().length; i++) {
            System.out.print(getSimplifiedGameBoard()[i] + " ");
        }
    }

    protected String[] getSimplifiedGameBoard() {

        String[] simplifiedGameBoard = new String[9];

        int index = 0;

        for (int i = 0; i < getGameBoard().length; i++) {
            for (int j = 0; j < getGameBoard()[i].length; j++) {
                if (getGameBoard()[i][j].equalsIgnoreCase(" ")) {
                    simplifiedGameBoard[index] = Integer.toString(index);
                } else {
                    simplifiedGameBoard[index] = getGameBoard()[i][j];
                }
                index++;
            }
        }
        return simplifiedGameBoard;
    }
    public ArrayList<Integer> getEmptyIndices(String[] gameBoard) {

        ArrayList<Integer> emptyIndices = new ArrayList<>();

        for (int i = 0; i < gameBoard.length; i++) {
            if (!gameBoard[i].equalsIgnoreCase("X") && !gameBoard[i].equalsIgnoreCase("O")) {
                emptyIndices.add(i);
            }
        }

        return emptyIndices;
    }

    protected boolean winning(String[] board, String player) {

        return (board[0].equalsIgnoreCase(player) && board[1].equalsIgnoreCase(player) && board[2].equalsIgnoreCase(player)) ||
                (board[3].equalsIgnoreCase(player) && board[4].equalsIgnoreCase(player) && board[5].equalsIgnoreCase(player)) ||
                (board[6].equalsIgnoreCase(player) && board[7].equalsIgnoreCase(player) && board[8].equalsIgnoreCase(player)) ||
                (board[0].equalsIgnoreCase(player) && board[3].equalsIgnoreCase(player) && board[6].equalsIgnoreCase(player)) ||
                (board[1].equalsIgnoreCase(player) && board[4].equalsIgnoreCase(player) && board[7].equalsIgnoreCase(player)) ||
                (board[2].equalsIgnoreCase(player) && board[5].equalsIgnoreCase(player) && board[8].equalsIgnoreCase(player)) ||
                (board[0].equalsIgnoreCase(player) && board[4].equalsIgnoreCase(player) && board[8].equalsIgnoreCase(player)) ||
                (board[2].equalsIgnoreCase(player) && board[4].equalsIgnoreCase(player) && board[6].equalsIgnoreCase(player));
    }

    protected Move minimax(String[] newBoard, String player) {

        //player is current player

        String aiPlayer = getCurrentPlayer().getGamePiece();
        String opponent;

        if (aiPlayer.equalsIgnoreCase("X")) {
            opponent = "O";
        } else {
            opponent = "X";
        }

//        System.out.println("ai player is " + aiPlayer);
//        System.out.println("opponent is " + opponent);

        ArrayList<Integer> availSpots = getEmptyIndices(newBoard);

        Move checkTestMove = new Move();

        if (winning(newBoard, opponent)) {
            checkTestMove.score = -10;
            return checkTestMove;
        } else if (winning(newBoard, aiPlayer)) {
            checkTestMove.score = 10;
            return checkTestMove;
        } else if (availSpots.size() == 0) {
            checkTestMove.score = 0;
            return checkTestMove;
        }

        // an array to collect all the objects
        ArrayList<Move> moves = new ArrayList<>();

        // loop through available spots
        for (int i = 0; i < availSpots.size(); i++) {
            //create an object for each and store the index of that spot
            Move testMove = new Move();
//            printSimplifiedGameBoard();
//            System.out.println();
//            System.out.println("i is currently " + i);
//            System.out.println(getEmptyIndices(newBoard));
//            System.out.println("1 current index in this game Board is" + newBoard[availSpots.get(i)]);
            testMove.index = Integer.parseInt(newBoard[availSpots.get(i)]);

            // set the empty spot to the current player
            newBoard[availSpots.get(i)] = player;
//            System.out.println("2 current index in this game Board is" + newBoard[availSpots.get(i)]);

            /*collect the score resulted from calling minimax
            on the opponent of the current player*/
            if (player.equalsIgnoreCase(aiPlayer)) {
                testMove.score = minimax(newBoard, opponent).score;
            } else {
                testMove.score = minimax(newBoard, aiPlayer).score;
            }

            // reset the spot to empty
            newBoard[availSpots.get(i)] = Integer.toString(testMove.index);
//            System.out.println("3 current index in this game Board is" + newBoard[availSpots.get(i)]);

            // push the object to the array
            moves.add(testMove);

        }

        // if it is the computer's turn loop over the moves and choose the move with the highest score
        int bestMove = 0; // index of the best move in the moves array (not a move object)
        if (player.equalsIgnoreCase(aiPlayer)) {
            int bestScore = -10000; // random low starting check number
            for (int i = 0; i < moves.size(); i++) {
                if (moves.get(i).score > bestScore) {
                    bestScore = moves.get(i).score;
                    bestMove = i;
                }
            }
            // else loop over the moves and choose the move with the lowest score
        } else {
            int bestScore = 10000;
            for (int i = 0; i < moves.size(); i++) {
                if (moves.get(i).score < bestScore) {
                    bestScore = moves.get(i).score;
                    bestMove = i;
                }
            }
        }

//        System.out.println("best move index is " + moves.get(bestMove).index);

        // return the chosen move (object) from the moves array
        return moves.get(bestMove);
    }

    private int[] getHardCoordinates() {

        Move bestMove = minimax(getSimplifiedGameBoard(), getCurrentPlayer().getGamePiece());
        int bestMoveIndex = bestMove.index;

        return translateHardCoordinates(bestMoveIndex);

    }

    private int[] translateHardCoordinates(int index) {

        int rows = 0;
        int columns = 0;

        if (index < 3) {
//            row = 0;
            columns = index;
        } else if (index < 6) {
            rows = 1;
            columns = index - 3;
        } else if (index < 9) {
            rows = 2;
            columns = index - 6;
        }

        return new int[]{rows, columns};

    }

    private int[] getEasyCoordinates() {

        Random random = new Random();

        int computerCoordinate1 = random.nextInt(3);
        int computerCoordinate2 = random.nextInt(3);

        return new int[]{computerCoordinate1, computerCoordinate2};
    }

    private int[] getMediumCoordinates() {

//        System.out.println("current player is " + getCurrentPlayer().getGamePiece());

        for (String gamePieceCheck : getGamePieceChoices()) {
            for (int i = 0; i < getGameBoard().length; i++) {

                String gamePiece1 = "";
                String gamePiece2 = "";
                String gamePiece3 = "";

                int mediumCoordinate1 = 0;
                int mediumCoordinate2 = 0;

                for (int j = 0; j < getGameBoard()[i].length; j++) {

                    // check for left to right diagonal winner
                    if (i == 0 && j == 0) {
                        gamePiece1 = getGameBoard()[i][j];
                        gamePiece2 = getGameBoard()[i + 1][j + 1];
                        gamePiece3 = getGameBoard()[i + 2][j + 2];

                        if (isTwoGamePiecesInRow(gamePieceCheck, gamePiece1, gamePiece2, gamePiece3)) {
                            if (isCurrentPlayerHasTwoPiecesInRow()) {
                                if (gamePiece1.equalsIgnoreCase(" ")) {
                                    mediumCoordinate1 = i;
                                    mediumCoordinate2 = j;
                                } else if (gamePiece2.equalsIgnoreCase(" ")) {
                                    mediumCoordinate1 = i + 1;
                                    mediumCoordinate2 = j + 1;
                                } else if (gamePiece3.equalsIgnoreCase(" ")) {
                                    mediumCoordinate1 = i + 2;
                                    mediumCoordinate2 = j + 2;
                                }
                                setMediumCoordinates(new int[]{mediumCoordinate1, mediumCoordinate2});
                                break;
                            } else if (isOpponentHasTwoPiecesInRow()) {
                                if (gamePiece1.equalsIgnoreCase(" ")) {
                                    mediumCoordinate1 = i;
                                    mediumCoordinate2 = j;
                                } else if (gamePiece2.equalsIgnoreCase(" ")) {
                                    mediumCoordinate1 = i + 1;
                                    mediumCoordinate2 = j + 1;
                                } else if (gamePiece3.equalsIgnoreCase(" ")) {
                                    mediumCoordinate1 = i + 2;
                                    mediumCoordinate2 = j + 2;
                                }
                                setMediumCoordinates(new int[]{mediumCoordinate1, mediumCoordinate2});
                            }
                        }
                    }

                    // check for right to left diagonal winner
                    if (i == 0 && j == 2) {
                        gamePiece1 = getGameBoard()[i][j];
                        gamePiece2 = getGameBoard()[i + 1][j - 1];
                        gamePiece3 = getGameBoard()[i + 2][j - 2];

                        if (isTwoGamePiecesInRow(gamePieceCheck, gamePiece1, gamePiece2, gamePiece3)) {
                            if (isCurrentPlayerHasTwoPiecesInRow()) {
                                if (gamePiece1.equalsIgnoreCase(" ")) {
                                    mediumCoordinate1 = i;
                                    mediumCoordinate2 = j;
                                } else if (gamePiece2.equalsIgnoreCase(" ")) {
                                    mediumCoordinate1 = i + 1;
                                    mediumCoordinate2 = j - 1;
                                } else if (gamePiece3.equalsIgnoreCase(" ")) {
                                    mediumCoordinate1 = i + 2;
                                    mediumCoordinate2 = j - 2;
                                }
                                setMediumCoordinates(new int[]{mediumCoordinate1, mediumCoordinate2});
                                break;
                            } else if (isOpponentHasTwoPiecesInRow()) {
                                if (gamePiece1.equalsIgnoreCase(" ")) {
                                    mediumCoordinate1 = i;
                                    mediumCoordinate2 = j;
                                } else if (gamePiece2.equalsIgnoreCase(" ")) {
                                    mediumCoordinate1 = i + 1;
                                    mediumCoordinate2 = j - 1;
                                } else if (gamePiece3.equalsIgnoreCase(" ")) {
                                    mediumCoordinate1 = i + 2;
                                    mediumCoordinate2 = j - 2;
                                }
                                setMediumCoordinates(new int[]{mediumCoordinate1, mediumCoordinate2});
                            }
                        }
                    }

                    // check for horizontal winner
                    if (j == 0) {
                        gamePiece1 = getGameBoard()[i][j];
                        gamePiece2 = getGameBoard()[i][j + 1];
                        gamePiece3 = getGameBoard()[i][j + 2];

                        if (isTwoGamePiecesInRow(gamePieceCheck, gamePiece1, gamePiece2, gamePiece3)) {
                            if (isCurrentPlayerHasTwoPiecesInRow()) {
                                if (gamePiece1.equalsIgnoreCase(" ")) {
                                    mediumCoordinate1 = i;
                                    mediumCoordinate2 = j;
                                } else if (gamePiece2.equalsIgnoreCase(" ")) {
                                    mediumCoordinate1 = i;
                                    mediumCoordinate2 = j + 1;
                                } else if (gamePiece3.equalsIgnoreCase(" ")) {
                                    mediumCoordinate1 = i;
                                    mediumCoordinate2 = j + 2;
                                }
                                setMediumCoordinates(new int[]{mediumCoordinate1, mediumCoordinate2});
                                break;
                            } else if (isOpponentHasTwoPiecesInRow()) {
                                if (gamePiece1.equalsIgnoreCase(" ")) {
                                    mediumCoordinate1 = i;
                                    mediumCoordinate2 = j;
                                } else if (gamePiece2.equalsIgnoreCase(" ")) {
                                    mediumCoordinate1 = i;
                                    mediumCoordinate2 = j + 1;
                                } else if (gamePiece3.equalsIgnoreCase(" ")) {
                                    mediumCoordinate1 = i;
                                    mediumCoordinate2 = j + 2;
                                }
                                setMediumCoordinates(new int[]{mediumCoordinate1, mediumCoordinate2});
                            }
                        }
                    }

                    // check for vertical winner
                    if (i == 0) {
                        gamePiece1 = getGameBoard()[i][j];
                        gamePiece2 = getGameBoard()[i + 1][j];
                        gamePiece3 = getGameBoard()[i + 2][j];

                        if (isTwoGamePiecesInRow(gamePieceCheck, gamePiece1, gamePiece2, gamePiece3)) {
                            if (isCurrentPlayerHasTwoPiecesInRow()) {
                                if (gamePiece1.equalsIgnoreCase(" ")) {
                                    mediumCoordinate1 = i;
                                    mediumCoordinate2 = j;
                                } else if (gamePiece2.equalsIgnoreCase(" ")) {
                                    mediumCoordinate1 = i + 1;
                                    mediumCoordinate2 = j;
                                } else if (gamePiece3.equalsIgnoreCase(" ")) {
                                    mediumCoordinate1 = i + 2;
                                    mediumCoordinate2 = j;
                                }
                                setMediumCoordinates(new int[]{mediumCoordinate1, mediumCoordinate2});
                                break;
                            } else if (isOpponentHasTwoPiecesInRow()) {
                                if (gamePiece1.equalsIgnoreCase(" ")) {
                                    mediumCoordinate1 = i;
                                    mediumCoordinate2 = j;
                                } else if (gamePiece2.equalsIgnoreCase(" ")) {
                                    mediumCoordinate1 = i + 1;
                                    mediumCoordinate2 = j;
                                } else if (gamePiece3.equalsIgnoreCase(" ")) {
                                    mediumCoordinate1 = i + 2;
                                    mediumCoordinate2 = j;
                                }
                                setMediumCoordinates(new int[]{mediumCoordinate1, mediumCoordinate2});
                            }
                        }
                    }
                }
            }
        }

//        System.out.println("current player has two pieces in a row? " + isCurrentPlayerHasTwoPiecesInRow());
//        System.out.println("opponent has two pieces in a row? " + isOpponentHasTwoPiecesInRow());

        // if computer is at risk of losing or has ability to win, use medium ai logic, else use easy logic to produce random coordinates

        if (isOpponentHasTwoPiecesInRow() || isCurrentPlayerHasTwoPiecesInRow()) {
            return mediumCoordinates;
        } else {
            return getEasyCoordinates();
        }
    }

    private boolean isTwoGamePiecesInRow(String gamePieceCheck, String gamePiece1, String gamePiece2, String gamePiece3) {

        ArrayList<String> gamePiecesChecked = new ArrayList<>();

        // logic for medium level ai

        gamePiecesChecked.add(gamePiece1);
        gamePiecesChecked.add(gamePiece2);
        gamePiecesChecked.add(gamePiece3);

        if (getCurrentPlayer().getGamePiece().equalsIgnoreCase(gamePieceCheck)) {
            // if the game piece matches the game piece being checked && it IS the current player's piece, remove it from the array
            gamePiecesChecked.removeIf(gamePiece -> gamePiece.equalsIgnoreCase(gamePieceCheck) && gamePiece.equalsIgnoreCase(getCurrentPlayer().getGamePiece()));
            // if there is only one left and it is blank, this is where the computer should try to go,
            if (gamePiecesChecked.size() == 1) {
                // make sure the space is blank
                setCurrentPlayerHasTwoPiecesInRow(gamePiecesChecked.get(0).equalsIgnoreCase(" "));
                return gamePiecesChecked.get(0).equalsIgnoreCase(" ");
            }
        } else {
            // if the game piece matches the game piece being checked && it is NOT the current player's piece, remove it from the array
            gamePiecesChecked.removeIf(gamePiece -> gamePiece.equalsIgnoreCase(gamePieceCheck) && !gamePiece.equalsIgnoreCase(getCurrentPlayer().getGamePiece()));
            // if there is only one left and it is blank, this is where the computer should try to go,
            if (gamePiecesChecked.size() == 1) {
                // make sure the space is blank
                if (!isOpponentHasTwoPiecesInRow()) {
                    setOpponentHasTwoPiecesInRow(gamePiecesChecked.get(0).equalsIgnoreCase(" "));
                }
                return gamePiecesChecked.get(0).equalsIgnoreCase(" ");
            }
        }
        return false;
    }

    // ***** GET USER COORDINATES *****

    protected void getUserCoordinates() {

        boolean userCoordinatesValid = false;

        while(!userCoordinatesValid) {

            System.out.print("Enter the coordinates: ");

            // empty coordinates in order to use validate input method
            int userCoordinate1 = 0;
            int userCoordinate2 = 0;

            // validate user coordinates

            userCoordinatesValid = isValidInput(userCoordinate1, userCoordinate2);

        }

    }

    // ***** VALIDATE COORDINATES AND ADD THEM TO GAME BOARD *****
    private boolean isValidInput(int coordinate1, int coordinate2) {

        boolean playerIsHuman = getCurrentPlayer().getType().equalsIgnoreCase("user");

        try {

            if (playerIsHuman) {
                coordinate1 = input.nextInt() - 1;
                coordinate2 = input.nextInt() - 1;
            }

            int[] testCoordinates = {coordinate1, coordinate2};

            setValidUserCoordinates(testCoordinates);

            setTestGameBoard(checkForGamePieceInCoordinateLocation(getValidUserCoordinates()));

            String coordinate = getGameBoard()[getValidUserCoordinates()[0]][getValidUserCoordinates()[1]];

            if (coordinate.equals(getGamePieceChoices()[0]) || coordinate.equals(getGamePieceChoices()[1])) {
                throw new CellOccupiedException("This cell is occupied! Choose another one!");
            } else {
                setGameBoard(getTestGameBoard());
                return true;
            }

        } catch (CellOccupiedException cellOccupiedException) {
            if (playerIsHuman) {
                System.out.println(cellOccupiedException.getMessage());
            }
        // check that coordinates are numbers
        } catch (InputMismatchException inputMismatchException) {
            if (playerIsHuman) {
                System.out.println("You should enter numbers!");
                input.nextLine(); // consume input
            }
        // check that coordinates are within correct range
        } catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {
            if (playerIsHuman) {
                System.out.println("Coordinates should be from 1 to 3!");
            }
        }
        return false;
    }

    private String[][] checkForGamePieceInCoordinateLocation(int[] validUserCoordinates) {

        setTestGameBoard(cloneGameBoard());

        getTestGameBoard()[validUserCoordinates[0]][validUserCoordinates[1]] = getCurrentPlayer().getGamePiece();

        return getTestGameBoard();

    }

    // ***** GET AND TEST USER CELLS AND ADD THEM TO GAME BOARD *****

    protected void getUserCells() {

        System.out.print("Enter the cells: ");

        // get all cells and split them into an array
        String[] userCells = input.nextLine().split("");

        // add to the game board
        setGameBoard(translateUserInput(userCells));

        // print game board
        printGameBoard();
    }

    // translate the user input String into the test game board array
    private String[][] translateUserInput(String[] userInput) {

        // add user input to test game board

        int index = 0;
        for (int i = 0; i < getTestGameBoard().length; i++) {
            for (int j = 0; j < getTestGameBoard()[i].length; j++) {
                // if one of the cells is "_" it should be printed as an empty space
                if (userInput[index].equals("_")) {
                    getTestGameBoard()[i][j] = " ";
                } else {
                    getTestGameBoard()[i][j] = userInput[index];
                }
                index++;
            }
        }

        return getTestGameBoard();
    }

    // ***** DEFINE WINS AND CHECKS GAME BOARD FOR WINNER *****
    protected boolean checkForWinner() {

        // reset medium ai opponent logic
        setCurrentPlayerHasTwoPiecesInRow(false);
        setOpponentHasTwoPiecesInRow(false);
        printGameBoard(); // keeps game board in correct order for tests

        boolean gameWon = false;
        String winner;

        for (String gamePieceCheck : getGamePieceChoices()) {
            for (int i = 0; i < getGameBoard().length; i++) {

                String gamePiece1 = "";
                String gamePiece2 = "";
                String gamePiece3 = "";

                int mediumCoordinate1 = 0;
                int mediumCoordinate2 = 0;

                for (int j = 0; j < getGameBoard()[i].length; j++) {

                    // check for left to right diagonal winner
                    if (i == 0 && j == 0) {
                        gamePiece1 = getGameBoard()[i][j];
                        gamePiece2 = getGameBoard()[i + 1][j + 1];
                        gamePiece3 = getGameBoard()[i + 2][j + 2];

                        if (gamePieceCheck.equals(gamePiece1) && gamePieceCheck.equals(gamePiece2) && gamePieceCheck.equals(gamePiece3)) {
                            break;
                        }
                    }

                    // check for right to left diagonal winner
                    if (i == 0 && j == 2) {
                        gamePiece1 = getGameBoard()[i][j];
                        gamePiece2 = getGameBoard()[i + 1][j - 1];
                        gamePiece3 = getGameBoard()[i + 2][j - 2];

                        if (gamePieceCheck.equals(gamePiece1) && gamePieceCheck.equals(gamePiece2) && gamePieceCheck.equals(gamePiece3)) {
                            break;
                        }
                    }

                    // check for horizontal winner
                    if (j == 0) {
                        gamePiece1 = getGameBoard()[i][j];
                        gamePiece2 = getGameBoard()[i][j + 1];
                        gamePiece3 = getGameBoard()[i][j + 2];

                        if (gamePieceCheck.equals(gamePiece1) && gamePieceCheck.equals(gamePiece2) && gamePieceCheck.equals(gamePiece3)) {
                            break;
                        }
                    }

                    // check for vertical winner
                    if (i == 0) {
                        gamePiece1 = getGameBoard()[i][j];
                        gamePiece2 = getGameBoard()[i + 1][j];
                        gamePiece3 = getGameBoard()[i + 2][j];

                        if (gamePieceCheck.equals(gamePiece1) && gamePieceCheck.equals(gamePiece2) && gamePieceCheck.equals(gamePiece3)) {
                            break;
                        }
                    }
                }

                // break loop if game won
                gameWon = gamePieceCheck.equals(gamePiece1) && gamePieceCheck.equals(gamePiece2) && gamePieceCheck.equals(gamePiece3);
                if (gameWon) {
                    winner = gamePieceCheck;
                    System.out.println(winner + " wins");
                    return true;
                }
            }
        }

        if (getGamePieceCount() == 9) {
            System.out.println("Draw");
            return true;
        } else {
//            System.out.println("Game not finished");
            return false;
        }
    }

    // ***** GETTERS AND SETTERS *****


    public Player getPlayer1() {
        return player1;
    }

    public void setPlayer1(Player player1) {
        this.player1 = player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public void setPlayer2(Player player2) {
        this.player2 = player2;
    }

    public String[][] getGameBoard() {
        return gameBoard;
    }

    public void setGameBoard(String[][] gameBoard) {
        this.gameBoard = gameBoard;
    }

    public String[][] getTestGameBoard() {
        return testGameBoard;
    }

    public void setTestGameBoard(String[][] testGameBoard) {
        this.testGameBoard = testGameBoard;
    }

    public int[] getValidUserCoordinates() {
        return validUserCoordinates;
    }

    public void setValidUserCoordinates(int[] validUserCoordinates) {
        this.validUserCoordinates = validUserCoordinates;
    }

    public boolean isGameOver() {
        return isGameOver;
    }

    public void setGameOver(boolean gameOver) {
        isGameOver = gameOver;
    }

    public String[] getGamePieceChoices() {
        return gamePieceChoices;
    }

    public int getGamePieceCount() {
        return gamePieceCount;
    }

    public void setGamePieceCount(int gamePieceCount) {
        this.gamePieceCount = gamePieceCount;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public void setMediumCoordinates(int[] mediumCoordinates) {
        this.mediumCoordinates = mediumCoordinates;
    }

    public boolean isOpponentHasTwoPiecesInRow() {
        return opponentHasTwoPiecesInRow;
    }

    public void setOpponentHasTwoPiecesInRow(boolean opponentHasTwoPiecesInRow) {
        this.opponentHasTwoPiecesInRow = opponentHasTwoPiecesInRow;
    }

    public boolean isCurrentPlayerHasTwoPiecesInRow() {
        return currentPlayerHasTwoPiecesInRow;
    }

    public void setCurrentPlayerHasTwoPiecesInRow(boolean currentPlayerHasTwoPiecesInRow) {
        this.currentPlayerHasTwoPiecesInRow = currentPlayerHasTwoPiecesInRow;
    }
}
