package org.gamelink.tournament;

import org.gamelink.tournament.Team;
import org.gamelink.tournament.Match;
import org.gamelink.game.Algo;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AssignableTypeFilter;
import org.springframework.beans.factory.config.*;
import java.lang.reflect.Method;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Set;

/**
* The Scheduler class is used to create a tournament structure for all matches to be played
* it ensures that every team plays every other team twice; once as player 1 and once as player 2
*/
public class Scheduler{
	/** An ArrayList containing all user made classes. These classes contain the algorithms to be used in the playing of the matches in the tournament */
	private ArrayList<Class<?>> algoList = new ArrayList<Class<?>>();

	/** An ArrayList containing all the matches to be played in the tournament */
	private ArrayList<Match> matchList = new ArrayList<Match>();

	/** An ArrayList containing all the teams participating in the tournament */
	private ArrayList<Team> teamList = new ArrayList<Team>();


	/** The scheduler constructor initializes the tournament by creating all matches, Teams, and Algorithms and adding them to the corresponding ArrayList */
	public Scheduler(){
		this.setAlgoList();
		this.setTeamList();
		this.setMatchList();
	}

	/**
	* Retrieves the ArrayList of Algorithms to be used by the gamePlayers
	* @return The ArrayList of Algorithms
	*/
	public ArrayList<Class<?>> getAlgoList(){
		return algoList;
	}

	/**
	* Retrieves the ArrayList of Matches to be used by the gamePlayers
	* @return The ArrayList of Matches
	*/
	public ArrayList<Match> getMatchList(){
		return matchList;
	}

	/**
	* Retrieves the ArrayList of Teams to be used by the gamePlayers
	* @return The ArrayList of Teams
	*/
	public ArrayList<Team> getTeamList(){
		return teamList;
	}

	/** Using the Spring Framework the setAlgoList initializes all user created Algorithm classes in the algorithms folder and adds them to the ArrayList algoList */
	private void setAlgoList(){
		ClassPathScanningCandidateComponentProvider provider = new ClassPathScanningCandidateComponentProvider(true);
		provider.addIncludeFilter(new AssignableTypeFilter(Algo.class));

		Set<BeanDefinition> components = provider.findCandidateComponents("algorithms");
		for (BeanDefinition component : components)
		{
			try{
		    Class<?> cls = Class.forName(component.getBeanClassName());
		    algoList.add(cls);
			}catch (ClassNotFoundException cnfe){
				System.out.println("Error: Class not found");
			}
		}
	}

	/** Creates a new team for every Algorithm class in the algoList ArrayList each Team is instantiated with the name retrieved from the corresponding user created Algorithm class */
	private void setTeamList(){
		for (int i = 0; i < algoList.size(); i ++){
			try{
				Method getTeamName = algoList.get(i).getMethod("getTeamName");
				teamList.add(new Team((String) getTeamName.invoke(new Object[] {null})));
			}catch(NoSuchMethodException me){
				System.out.println("Error: Method does not exist");
			}catch (IllegalAccessException iae){
				System.out.println("Error: Denied access to method");
			}catch (InvocationTargetException ite){
				System.out.println("Error: Invalid target method");
			}
		}
	}

	/** fills the matchList ArrayList with all matches needed to be played throughout the tournament the number of matches is determined by the number of teams as each team must play each other twice */
	private void setMatchList(){
		int numOfTeams = teamList.size();
		int teamsInArray = teamList.size();
		if (numOfTeams % 2 == 1) teamsInArray ++;
		int[][] matchMakingArray = getArray(teamsInArray);
		int matchCounter = 1;

		for (int i = teamsInArray - 1; i > 0; i --){
			for (int j = 0; j < (int)teamsInArray/2; j ++){
				if (matchMakingArray[0][j] <= numOfTeams && matchMakingArray[1][j] <= numOfTeams){
					matchList.add(new Match(matchCounter + "A", 
											teamList.get(matchMakingArray[0][j] - 1),
											teamList.get(matchMakingArray[1][j] - 1)));
					matchList.add(new Match(matchCounter + "B", 
											teamList.get(matchMakingArray[1][j] - 1),
											teamList.get(matchMakingArray[0][j] - 1)));
					matchCounter ++;
				}
			}
			matchMakingArray = rotateArray(matchMakingArray);
		}
	}

	/**
	* Creates an array that is used for matchmaking the teams in each match of the tournament
	* @param the number of teams participating in the tournament
	* @return the array that is manipulated to determine the matches of the tournament
	*/
	private static int[][] getArray(int teams){
		int[][] array = new int[2][(int)teams/2];
		int currTeam = 1;
		for (int i = 0; i < 2; i ++){
			for (int j = 0; j < (int) teams/2; j ++){
				array[i][j] = currTeam;
				currTeam ++;
			}
		}
		return array;
	}

	/**
	* Rotates the array clockwise so the next set of matches can be extracted
	* @param the current configuration of the array
	* @return the new configuration of the array
	*/
	private static int[][] rotateArray(int[][] array){
		int[][] copyArray = new int[2][array[0].length];
		for (int i = 0; i < 2; i ++){
			for (int j = 0; j < array[0].length; j ++){
				copyArray[i][j] = array[i][j];
			}
		}
		for (int i = 0; i < 2; i ++){
			for (int j = 0; j < array[0].length; j ++){
				array[i][j] = getPrev(copyArray, i, j);
			}
		}
		return array;
	}

	/**
	* retrieves the previous cell in the array (the one to its left) to be used when rotating the array
	* @param the current configuration of the array
	* @param the row that the cell lies on
	* @param the column that the cell lies on
	* @return the value of the previous cell
	*/
	private static int getPrev(int[][] array, int i, int j){
		if (i == 0 && j == 0) return array[0][0];
		else if (i == 0 && j == 1) return array[1][0];
		else if (i == 1 && j == (array[1].length - 1)) return array[0][array[0].length - 1];
		else if (i == 0) return array[i][j - 1];
		else return array[i][j + 1];
	}
}