import java.util.Date;

class Game
{
	String label1="null";
	String label2="null";
	String date ="null";
	String time ="12:00:00";
	String location ="--";
	String round="null";
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
	int depth = 0;
	String getTeam1() {return team1;}
	String getTeam2() {return team2;}
	String getGameNumber() {return gameNumber;}
	String getRound(){return round;}
	String getLocation(){return  location;}
	String getTime(){return time;}
	String getDate(){return date;}
	String getBracket(){return bracket;}
	Date getFulllDate(){return fulldate;}
	int getScore1()
	{
		return score1;
	}
	int getScore2()
	{
		return score2;
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
	void setRound(String r)
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
	void setDate(String s, Date d){date=s; fulldate=d;}
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

	void printGame()
	{
		System.out.println("D:\n"+depth + " Round "+ round);
		System.out.println("Game"+gameNumber+" "+team1+" "+ team2);
	}
}