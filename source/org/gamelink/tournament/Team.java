package org.gamelink.tournament;

import java.util.ArrayList;

/** The Team Class is used to represent a team in the tournament, it stores all stats associated with that teams position in that particular tournament, including wins, losses, ties, total score */
public class Team{

	/** The name of the team retrived from the class created by the user*/
	private String teamName;

	/** Defines the number of wins the team has earned during the playing of the tournament */
	private int wins = 0;

	/** Defines the number of losses the team has earned during the playing of the tournament */
	private int losses = 0;

	/** Defines the number of ties the team has earned during the playing of the tournament */
	private int ties = 0;

	/** The collective score earned by the team in every game of the tournament thus far */
	private int score = 0;

	/**
	* constructs an instance of Team with its name, there should be a Team for each user made algorithm class.
	* @param the name of the Team
	*/
	public Team(String name){
		this.teamName = name;
	}

	public int getWins(){ return wins; }

	public int getLosses(){ return losses; }

	public int getTies(){ return ties; }

	public int getScore(){ return score; }

	public void addWin(){ wins ++; }

	public void addLoss(){ losses ++; }

	public void addTie(){ ties ++; }

	public void addScore(int s){ score += s; }

	public String getName(){ return teamName; }
}

