package org.gamelink.tournament;

import org.gamelink.tournament.Team;

/** The Match class is responsible for holding all imformation regarding a match in the tournament,  */
public class Match{

	/** The team acting as player 1 in this match */
	private Team team1;

	/** The team acting as player 2 in this match */
	private Team team2;

	/** The player number of the team that won the match */
	private int winner;

	/** The name of the match */
	private String matchName;

	/**
	 * The constuctor responsible for initializing the teams and name of the match
	 * @param  mn    The name of the match 
	 * @param  t1    The team acting as player 1 in this match
	 * @param  t2    The team acting as player 2 in this match
	 */
	public Match(String mn, Team t1, Team t2){
		matchName = mn;
		team1 = t1;
		team2 = t2;

	}

	/**
	 * Retrieves the team acting as player 1 for this match
	 * @return The team acting as player 1 for this match
	 */
	public Team getTeam1() {
		return team1;
	}

	/**
	 * Retrieves the team acting as player 2 for this match
	 * @return The team acting as player 2 for this match
	 */
	public Team getTeam2() {
		return team2;
	}

	/**
	 * Retrieves the name of the team acting as player 1 for this match
	 * @return The team acting as player 1 for this match
	 */
	public String getTeam1Name() {
		return team1.getName();
	}

	/**
	 * Retrieves the name of the team acting as player 2 for this match
	 * @return The team acting as player 2 for this match
	 */
	public String getTeam2Name() {
		return team2.getName();
	}

	/**
	 * Retrieves the name of the match
	 * @return The name of the match
	 */
	public String getName() {
		return matchName;
	}
}