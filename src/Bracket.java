public class Bracket {
    BinaryTree winners;
    BinaryTree losers;
    public Bracket(int nTeams){
        winners =BinaryTree.createWinnerBracket(nTeams - 2);
        losers = BinaryTree.createLoserBracket(nTeams - 2);
        winners.labelWinnerBracket(winners.getRoot());
        losers.labelWinnerBracket(losers.getRoot());
        losers.getListsByLevel();
    }
}
