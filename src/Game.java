import java.io.Serializable;
import java.util.Date;

class Game implements Serializable
{
	//BinaryNode containerNode;
	String label1="null";
	String label2="null";
	String date ="null";
	String time ="12:00:00";
	String location ="--";
	String bracket="null";
	String team1="null";
	String team2="null";
	String gameNumber="null";
	Date fulldate= new Date();
	int score1=0;
	int score2=0;
	int posx;
	int posy;

	Game()
	{
		gameNumber= "-";
	}
	Game(String g, String teamA, String teamB)
	{
		team1=teamA;
		team2=teamB;
		gameNumber=g;
	}
	int round = 0;
	String getTeam1() {return team1;}
	String getTeam2() {return team2;}
	String getGameNumber() {return gameNumber;}
	int  getRound(){return round;}
	String getLocation(){return  location;}
	String getTime(){return time;}
	String getDate(){return date;}
	String getBracket(){return bracket;}
	Date getFulllDate(){return fulldate;}
	String getScore1()
	{
		return ""+score1;
	}
	String getScore2()
	{
		return ""+score2;
	}
	int getPosx()
	{
		return posx;
	}
	int getPosy()
	{
		return posy;
	}

	void setTeams(String s, String s1) {team1=s; team2=s1;}
	void setRound(int r)
	{
		round=r;
	}
	void setBracket(String b) {bracket = b;}
	void setGameNumber(String g)
	{
		gameNumber=g;
	}
	void setTime(String s){time=s;}
	void setScore1(int x){score1=x;}
	void setScore2(int x){score2=x;}
	void setLocation(String l) {
		location= l;
	}
	void setDate( Date d){ fulldate=d;}
	void setTeam1(String t)
	{
		team1=t;
	}
	void setTeam2(String t) {team2=t;}
	void setPosx(int x)
	{
		posx=x;
	}
	void setPosy(int x)
	{
		posy=x;
	}

	public BinaryNode getNode(){
//		return containerNode;

		return null;
	}
	public void setNode(BinaryNode refNode){
		//containerNode=refNode;
	}


	void printGame()
	{
		Main.out("\nRound "+ round);
		Main.out("Game"+gameNumber+" "+team1+" "+ team2);
	}
	public static Game getDummyGame(){
		Game dummy = new Game();
		dummy.setGameNumber("123");
		dummy.setLocation("6030 N FM 493");
		dummy.setScore1(12);
		dummy.setScore2(22);
		dummy.setTeam1("team1");
		dummy.setTeam2("team2");
		Date date = new Date();
		dummy.setDate(date);
		dummy.setTime("12:00:00 am");
		return dummy;
	}
}