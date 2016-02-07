package org.gamelink.tournament;

import java.util.Comparator;

/** The TeamCOmparator class is used to compare different teams together to determine which team ranked higher in the playing of the tournament */
public class TeamComparator implements Comparator<Team> {

	/**
	 * Compares two teams to determin ewhich should rank higher in the leaderBoard
	 * @param  team1 The first team to be compared
	 * @param  team2 The second team to be compared
	 * @return       Negative if the first team ranks higher, 0 if they are equal and positive if the second team reanks heigher
	 */
	@Override
	public int compare(Team team1, Team team2) {
		int team1Points = (team1.getWins() * 2) + team1.getTies();
		int team2Points = (team2.getWins() * 2) + team2.getTies();
		if (team1Points > team2Points) return -1;
		else if (team1Points < team2Points) return 1;
		else {
			if (team1.getScore() > team2.getScore()) return -1;
			else if (team1.getScore() < team2.getScore()) return 1;
			else return 0;
		}
	}
}