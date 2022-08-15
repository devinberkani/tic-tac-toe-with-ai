package tictactoe;

public class Player {

    private String type;
    private String gamePiece;

    public Player(String gamePiece) {
        this.gamePiece = gamePiece;
    }

    // ***** GETTERS AND SETTERS *****

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getGamePiece() {
        return gamePiece;
    }

    public void setGamePiece(String gamePiece) {
        this.gamePiece = gamePiece;
    }
}
