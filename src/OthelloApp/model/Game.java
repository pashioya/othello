package OthelloApp.model;

public class Game {

    final static String GAME_SETUP_RULES ="""
                The game is played on a 8 x 8 board of squares. The game begins with four stones (two white and two black) in four squares in the center of the board.
                """;

    final static String GAMEPLAY_RULES = """
                Othello is a two-player game. One player plays black stones and the other player plays white stones. The player that plays black stones makes the first move.
                
                Players make moves by placing stones of their respective colors on the board. A move is valid only if the placed stone outflanks an opposite-colored stone (or row of opposite-colored stones).
                A stone or row of stones is outflanked when it is bordered by opposite-colored stones at each end. Each player must outflank opposite-colored stones and flip them so they have the player's color.
                A player can flip stones horizontally, vertically, and diagonally.
       
                If a player is not able to flip any stones, the player forfeits his/her turn and the other player plays again. Players may not voluntarily forfeit a turn if a move is available.
                """;

    final static String GAME_END_RULES = """
                The game is over when neither player can make a move. The player with the most stones of his/her color on the board wins the game. 
                The game may also end if a board is not full and the board only contains stones of one player's color.
                """;

    public String getGameSetupRules(){
        return GAME_SETUP_RULES;
    }

    public String getGamePlayRules(){
        return GAMEPLAY_RULES;
    }

    public String getGameEndRules(){
        return GAME_END_RULES;
    }

    public GameSession createNewGameSession(boolean userGoesFirst, String userName, String difficultyMode){
        return new GameSession(userGoesFirst, userName, difficultyMode);
    }
}
