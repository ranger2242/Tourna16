import java.util.*;

/**
 * Created by range_000 on 6/6/2015.
 */
public class Bracket2 {

    static ArrayList<String> instructions = new ArrayList<>();
    static ArrayList<List<String>> listOfRounds = new ArrayList<List<String>>();
    private static ArrayList<Game> gamesList = new ArrayList<>();
    Game tempGame = new Game();

    public ArrayList<Game> getGamesList()
    {
        return gamesList;
    }

    public void printGameList() {
        for(int i=0; i<gamesList.size();i++) {
            gamesList.get(i).printGame();
        }
    }
    public void makeBracketGames() {
        printInstructions();
        for(int i=0; i<instructions.size(); i++) {
            tempGame = new Game();
            String fullInit = instructions.get(i);
            String roundBracket = fullInit.substring(0,2);
            String gameNumber = fullInit.substring(3,6);
            String team1 = fullInit.substring(6,10);
            String team2 = fullInit.substring(10,13);

            roundCheck(roundBracket);
            gameNumberCheck(gameNumber);
            teamCheck(team1, team2);
            gamesList.add(tempGame);
        }
    }

    public void teamCheck(String s1, String s2) {
        String team1="";
        String team2="";

        if (s1.charAt(0) == 'w') {
            team1 = "Winner" + s1.substring(1, 3);
        } else if (s1.charAt(0) == 'l') {
            team1 = "Loser" + s1.substring(1, 3);
        } else if (s1.charAt(0) == 't') {
            team1 = "Team" + s1.substring(1, 3);
        }
        if (s2.charAt(0) == 'w') {
            team2 = "Winner" + s2.substring(1, 3);
        } else if (s2.charAt(0) == 'l') {
            team2 = "Loser" + s2.substring(1, 3);
        } else if (s2.charAt(0) == 't') {
            team2 = "Team" + s2.substring(1, 3);
        }
        tempGame.setTeams(team1, team2);
    }

    public void gameNumberCheck(String s) {
        System.out.println(s);
        tempGame.setGameNumber(s);
    }

    public void roundCheck(String s) {
        String bracket= "";
        String round="";
        if(isNumeric(s.substring(0,1))) {
            round+=s.substring(0,1);
            if(s.charAt(1)=='W') {
                bracket+="Winners";
            }
            else if(s.charAt(1)=='L') {
                bracket+="Losers";
            }
        }
        else if(s.charAt(0)=='F') {
            bracket+= "Finals";
            round+="F"+s.charAt(1);
        }
        tempGame.setRound(round);
        tempGame.setBracket(bracket);
    }

    public static boolean isNumeric(String str) {
        try {double d = Double.parseDouble(str);}
        catch(NumberFormatException nfe) {return false;}
        return true;
    }

    public void setInstructions(ArrayList arrayList) {
        instructions.addAll(arrayList);
    }

    public void printInstructions() {
        for(int i=0; i<instructions.size();i++) {
            System.out.println(instructions.get(i));
        }
    }
}
