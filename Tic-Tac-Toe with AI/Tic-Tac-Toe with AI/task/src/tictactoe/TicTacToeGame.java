package tictactoe;

public class TicTacToeGame {

    public TicTacToeGame() {

        Menu menu = new Menu();
        String userCommand = menu.getUserCommand();

        while (!userCommand.equalsIgnoreCase("exit")) {

            Player player1 = menu.getPlayer1();
            Player player2 = menu.getPlayer2();
            final GameBoard gameBoard = new GameBoard(player1, player2);

            boolean currentPlayerIsHuman = gameBoard.getCurrentPlayer().getType().equalsIgnoreCase("user");

            while(!gameBoard.isGameOver()) {

                if (currentPlayerIsHuman) {
                    gameBoard.getUserCoordinates();
                } else {
                    gameBoard.getComputerCoordinates(); // level will be passed in as argument here
                }

                gameBoard.setGameOver(gameBoard.checkForWinner()); // current player is switched

                currentPlayerIsHuman = gameBoard.getCurrentPlayer().getType().equalsIgnoreCase("user");

            }

            menu = new Menu();
            userCommand = menu.getUserCommand();

        }

    }

}
