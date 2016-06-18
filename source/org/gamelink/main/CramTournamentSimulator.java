package org.gamelink.main;

import org.gamelink.tournament.Match;
import org.gamelink.tournament.Scheduler;
import org.gamelink.tournament.TeamComparator;
import org.gamelink.tournament.Team;
import org.gamelink.game.Cram;
import org.gamelink.game.CramDisplay;
import java.util.Date;
import java.util.Collections;
import java.util.Scanner;
import java.lang.reflect.Method;
import java.lang.reflect.InvocationTargetException;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.net.InetAddress;
import org.gamelink.connect.Link;



/**
* The CramRunner classes starts both CramAutoPlayer classes so that they mey simulate
* the playing of player 1 and player 2 for all games in the tournament
*/
public class CramTournamentSimulator{
	public static void main(String[] args){
		if (args.length == 0) {
			(new Player1Simulator()).start();
			(new Player2Simulator()).start();
		} else {
			if (args[0].equals("1")) {
				(new Player1Simulator(true)).start();
			} else if (args[0].equals("2")) {
				(new Player2Simulator(true)).start();
			} else {
				System.out.println("Error: Invalid Command Line Argument");
				System.out.println("Please enter 1 or 2 for the player you wish to simulate");
			}
		}
	}
}

/**
* The Player1Simulator class acts as player 1 in the tournament by using the algorithm
* of the team it is simulating. after the completion of the tournament it writes the results
* of the tournament to the results file.
*/
class Player1Simulator extends Thread{
	private static final String RESULTS_FILE_PATH = "Results" + ".txt";
	private static ArrayList<Class<?>> algoList;
	private static ArrayList<Team> teamList;
	private static ArrayList<Match> matchList;

	private final int PLAYER1 = 1;
	private final int PLAYER2 = 2;
	private int maxNameLength = 13;
	private boolean distributedMode = false;
	CramDisplay display = new CramDisplay(PLAYER1);

	public Player1Simulator(){}

	public Player1Simulator(boolean distributedMode) {
		InetAddress ip = Link.getNonLoopbackAddress();
        System.out.println("Address: " + ip.toString().substring(1));
	}

	/**Simulates all games of the tournament from the perspective of player 1 while writting the results to the results file */
	@Override
	public void run(){
		Scheduler tournament = new Scheduler();
		algoList = tournament.getAlgoList();
		teamList = tournament.getTeamList();
		matchList = tournament.getMatchList();
		System.out.println("********************************************************************************");
		System.out.println("NEW CRAM TOURNAMENT: TEAMS: " + teamList.size() + ", MATCHES: " + (matchList.size() / 2));
		Iterator<Match> matchIterator = matchList.iterator();
		try {
			FileWriter fw = new FileWriter(RESULTS_FILE_PATH, false);
			PrintWriter pw = new PrintWriter(fw);

			pw.println("-------------------------------------------------------------------------");
			pw.println("| MATCH | TEAM1         | SCORE | TEAM2         | SCORE | WINNER        |");
			pw.println("-------------------------------------------------------------------------");
			while(matchIterator.hasNext()) {
				Match currMatch = matchIterator.next();
				System.out.print("Simulating Match " + currMatch.getName() + ": " 
													 + currMatch.getTeam1Name() + " VS " 
													 + currMatch.getTeam2Name());
				Class<?> currAlgo = algorithmRetriever.getAlgorithm(currMatch, algoList, PLAYER1);
				display.getJFrame().setTitle(currMatch.getTeam1Name() + " (Player 1)");
				Cram game = new Cram(PLAYER1, true, display);
				game.startGame(currAlgo);
				String winner;
				if (game.getWinner() == PLAYER1) winner = currMatch.getTeam1Name();
				else if (game.getWinner() == PLAYER2) winner = currMatch.getTeam2Name();
				else winner = "TIE";
				System.out.print(", Results: " + currMatch.getTeam1Name() + ": " 
											   + game.getPlayer1Score() + ", " 
											   + currMatch.getTeam2Name() + ": " 
											   + game.getPlayer2Score() + " Winner: " 
											   + winner + "\n");
				pw.println("| " + formatMatchName(currMatch.getName()) + "   | " 
								+ formatName(currMatch.getTeam1Name()) + " | "
								+ formatNum(game.getPlayer1Score()) + "    | "
								+ formatName(currMatch.getTeam2Name()) + " | "
								+ formatNum(game.getPlayer2Score()) + "    | "
								+ formatName(winner) + " | ");
				pw.println("-------------------------------------------------------------------------");

				switch(game.getWinner()){
					case PLAYER1: 	currMatch.getTeam1().addWin();
								  	currMatch.getTeam2().addLoss();
								  	break;
					case PLAYER2: 	currMatch.getTeam2().addWin();
								  	currMatch.getTeam1().addLoss();
								  	break;
					default: 	  	currMatch.getTeam2().addTie();
								  	currMatch.getTeam1().addTie();
				}
				currMatch.getTeam1().addScore(game.getPlayer1Score());
                currMatch.getTeam2().addScore(game.getPlayer2Score());
                System.gc();
            } 
			pw.print("\n");
			System.out.print("\n");
			printLeaderBoard(matchList, teamList, pw);
			pw.close();
		} catch (IOException fileWriterError){
			System.out.println("Error unable to write results to file");
		}
		System.exit(0);
	}

	protected static String generateUniqueFileName(){
        String filename="";
        long millis=System.currentTimeMillis();
        String datetime=new Date().toString();
        datetime=datetime.replace(" ", ":");
        filename=datetime+"_"+millis;
        return "Cram-Results:" + filename.substring(0,19);
	}

	/**
	 * Prints the leader board from the tournament to the results file
	 * @param teamList   The list of teams that participated in the tournament
	 * @param pw         The printWritter used to write to the results file
	 */
	private void printLeaderBoard(ArrayList<Match> matchList, ArrayList<Team> teamList, PrintWriter pw){
		TeamComparator comparator = new TeamComparator();
		Collections.sort(teamList, comparator);
		Iterator<Team> teamIterator = teamList.iterator();
		maxNameLength = 20;
		pw.println("---------------------------------------------------------------");
		pw.println("| PLACE | TEAMNAME             | WINS | LOSSES | TIES | SCORE |");
		pw.println("---------------------------------------------------------------");
		System.out.println("---------------------------------------------------------------");
		System.out.println("| PLACE | TEAMNAME             | WINS | LOSSES | TIES | SCORE |");
		System.out.println("---------------------------------------------------------------");
		int place = 1;
		while (teamIterator.hasNext()){
			Team currTeam = teamIterator.next();
			pw.println("| " + formatPlace(place) + "  | "
							+ formatName(currTeam.getName()) + " | "
							+ formatNum(currTeam.getWins()) + "   | "
							+ formatNum(currTeam.getLosses()) + "     | " 
							+ formatNum(currTeam.getTies()) + "   | "
							+ formatLargeNum(currTeam.getScore()) + "   |");

			System.out.println("| " + formatPlace(place) + "  | "
									+ formatName(currTeam.getName()) + " | "
									+ formatNum(currTeam.getWins()) + "   | "
									+ formatNum(currTeam.getLosses()) + "     | " 
									+ formatNum(currTeam.getTies()) + "   | "
									+ formatLargeNum(currTeam.getScore()) + "   |");
			pw.println("---------------------------------------------------------------");
			System.out.println("---------------------------------------------------------------");
			place ++;
		}
		pw.close();
	}

	/**
	 * formats the name of a team so that it may neatly fit into the results file
	 * @param  name    The name of the team
	 * @return         The team name formated to fit nicely in the results file
	 */
	private String formatName(String name) {
		StringBuilder teamName = new StringBuilder(name);
		int errorLength = name.length() - maxNameLength;
		if (errorLength == 0) return teamName.toString();
		else if (errorLength < 0) {
			for (int i = 0; i < Math.abs(errorLength); i ++) {
				teamName.append(" ");
			}
		} else {
			teamName.delete(teamName.length() - (errorLength + 2), teamName.length());
			teamName.append("..");
		}
		return teamName.toString();
	}

	/**
	 * Formats the name of the match so it may fit nicely into the results file
	 * @param  match    The name of the match to be formated
	 * @return          The formated name of the match
	 */
	private String formatMatchName(String match) {
		if (match.length() == 2) return match + " ";
		else return match;
	}

	/**
	 * Formats a number so it may fit neatly into the results file
	 * @param  num    The number to be formated
	 * @return        The formatted number
	 */
	private String formatNum(int num) {
		if (num < 10) return num + " ";
		else return num + "";
	}

	/**
	 * Formats a large number so it may fit neatly in the results file 		
	 * @param  num    The number to be formated
	 * @return        The formatted number
	 */
	private String formatLargeNum(int num) {
		if (num < 10) return num + "  ";
		else if (num < 100) return num + " ";
		else return num + "";
	}

	/**
	 * Formats the place of the player to fit neatly in the results file
	 * @param  place    The place String to be formatted
	 * @return          The formatted String
	 */
	private String formatPlace(int place) {
		String[] sufixes = new String[] { "th", "st", "nd", "rd", "th", "th", "th", "th", "th", "th" };
	    switch (place % 100) {
	    case 11:
	    case 12:
	    case 13:
	        return place + "th";
	    default:
	    	StringBuilder formattedPlace = new StringBuilder(place + sufixes[place % 10]);
	    	if (place < 10) formattedPlace.append(" ");
	    	return formattedPlace.toString();
	    }
	}
}

/**
* The CramAutoPlayer2 class acts as player 2 in the tournament by using the algorithm
* of the team it is simulating. after the completion of the tournament it writes the results
* of the tournament to the results file.
*/
class Player2Simulator extends Thread{
	private static ArrayList<Class<?>> algoList;
	private static ArrayList<Team> teamList;
	private static ArrayList<Match> matchList;
	private final int PLAYER2 = 2;
	private boolean distributedMode = false;
	CramDisplay display = new CramDisplay(PLAYER2);
	String ipAddressOfP1Simulator;

	public Player2Simulator(){}

	public Player2Simulator(boolean distributedMode) {
		this.distributedMode = distributedMode;
		ipAddressOfP1Simulator = this.getHostIpAddressFromUser();
	}

	/**
	* The run method uses the scheduler to create the tournament and proceeds to simulate eveygame
	* acting as player 2 for each. at the end of the tournament it calls the writeResults method
	* to post the results of the tournament to the results file
	*/
	public void run(){
		Scheduler tournament = new Scheduler();
		algoList = tournament.getAlgoList();
		teamList = tournament.getTeamList();
		matchList = tournament.getMatchList();

		Iterator<Match> matchIterator = matchList.iterator();

		while(matchIterator.hasNext()){
			Match currMatch = matchIterator.next();
			Class<?> currAlgo = algorithmRetriever.getAlgorithm(currMatch, algoList, PLAYER2);
			display.getJFrame().setTitle(currMatch.getTeam2Name() + " (Player 2)");
			Cram game;
			if (!distributedMode) {game = new Cram(PLAYER2, true, display);}
			else game = new Cram(ipAddressOfP1Simulator, display);
			game.startGame(currAlgo);

			try {
            Thread.sleep(5000);
            } catch(InterruptedException ex) {
            Thread.currentThread().interrupt();
            }
            System.gc();
		}
	}

   /**
     * Prompts the user to enter the IP address of the host so 
     * @return The Ip address of the host as entered by the user
     */
    public String getHostIpAddressFromUser(){
        String serverHostname = "";
        while (serverHostname.length() == 0){
            Scanner hostNameInputScanner = new Scanner(System.in);
            System.out.println("Please enter the address of your opponent. Example: 10.20.12.169");
            serverHostname = hostNameInputScanner.nextLine();
        }
        return serverHostname;
    }
}

/** Resposible for calling the algorithm class created by the user. */
class algorithmRetriever {

	/** calls the  algorithm method created by the user */
	public static Class<?> getAlgorithm(Match currMatch, ArrayList<Class<?>> algoList, int player) {
		for (Class<?> algo : algoList){
			try{
				Method getTeamName = algo.getMethod("getTeamName");
				if (player == 1) {
					if (getTeamName.invoke(new Object[] {null}).equals(currMatch.getTeam1Name())){
						return algo;
					}	
				} else {
					if (getTeamName.invoke(new Object[] {null}).equals(currMatch.getTeam2Name())){
						return algo;
					}
				}
			} catch (NoSuchMethodException me){
				System.out.println("Error: Method does not exist");
			} catch (IllegalAccessException iae){
				System.out.println("Error: Denied access to method");
			} catch (InvocationTargetException ite){
				System.out.println("Error: Invalid target method");
			}
		}
		System.out.println("Error: could not find Algo Class in algoList");
		return null;
	}
}