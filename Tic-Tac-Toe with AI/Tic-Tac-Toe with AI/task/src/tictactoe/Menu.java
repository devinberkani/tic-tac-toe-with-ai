package tictactoe;

import java.util.Scanner;

public class Menu {

    private static final Scanner input = new Scanner(System.in);
    private String[] userInputArray;
    private final String[] userCommandChoices = {"start", "exit"};
    private final String[] userPlayerChoices = {"easy", "medium", "hard", "user"};
    private String userCommand;
    private final Player player1 = new Player("X"); // game piece is always X
    private final Player player2 = new Player("O"); // game piece is always O

    public Menu() {
        getUserInput();
    }

    private void getUserInput() {

        System.out.print("Input command: ");
        setUserInputArray(input.nextLine().split(" "));

        boolean isExiting = getUserInputArray()[0].equalsIgnoreCase(getUserCommandChoices()[1]);

        while (!isValidInputArray() && !isExiting) {

            System.out.println("Bad parameters!");
            setUserInputArray(input.nextLine().split(" "));

            isExiting = getUserInputArray()[0].equalsIgnoreCase(getUserCommandChoices()[1]);

        }

        setUserCommand(getUserInputArray()[0]);

        if (!isExiting) {
            getPlayer1().setType(getUserInputArray()[1]);
            getPlayer2().setType(getUserInputArray()[2]);
        }

    }

    private boolean isValidInputArray() {

        // make sure length of input array is 3
        if (getUserInputArray().length != 3) {
            return false;
        }

        int validInputCount = 0;

        // make sure user command is correct
        for (String userCommand : getUserCommandChoices()) {
            if (getUserInputArray()[0].equalsIgnoreCase(userCommand)) {
                validInputCount++;
            }
        }

        // make sure user player choice 1 is correct
        for (String userPlayerChoice: getUserPlayerChoices()) {
            if (getUserInputArray()[1].equals(userPlayerChoice)) {
                validInputCount++;
            }
            if (getUserInputArray()[2].equals(userPlayerChoice)) {
                validInputCount++;
            }
        }

        return validInputCount == 3;
    }

    // ***** GETTERS AND SETTERS *****


    public String[] getUserCommandChoices() {
        return userCommandChoices;
    }

    public String[] getUserPlayerChoices() {
        return userPlayerChoices;
    }

    public String[] getUserInputArray() {
        return userInputArray;
    }

    public void setUserInputArray(String[] userInputArray) {
        this.userInputArray = userInputArray;
    }

    public String getUserCommand() {
        return userCommand;
    }

    public void setUserCommand(String userCommand) {
        this.userCommand = userCommand;
    }

    public Player getPlayer1() {
        return player1;
    }

    public Player getPlayer2() {
        return player2;
    }
}

