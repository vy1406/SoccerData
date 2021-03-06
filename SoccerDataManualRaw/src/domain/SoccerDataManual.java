package domain;

import java.util.ArrayList;
import java.util.Date;

import dao.CountryDao;
import dao.LeagueDao;
import dao.MatchDao;
import dao.TeamDao;
import model.Country;
import model.League;
import model.Match;
import model.Team;

public class SoccerDataManual {

	public static void main(String[] args) {
		CountryDao countryDao = new CountryDao();
		LeagueDao leagueDao = new LeagueDao();
		TeamDao teamDao = new TeamDao();
		MatchDao matchDao = new MatchDao();

		try {

			// System.out.println("Is league xml created ? : " +
			// leagueDao.createObjectXML());
			// System.out.println("Is country xml created ? : " +
			// countryDao.createObjectXML());
			// System.out.println("Is team xml created ? : " + teamDao.createObjectXML());
			// System.out.println("Is match xml created ? : " + matchDao.createObjectXML());

			// countryDao.showAll();
			// leagueDao.showAll();
			// teamDao.showAll();
			// matchDao.showAll();

			// System.out.println(countryDao.createDaoTable());
			// System.out.println(leagueDao.createDaoTable());
			// System.out.println(teamDao.createDaoTable());
			// System.out.println(matchDao.createDaoTable());

			// leagueDao.dropDaoTable();
			// countryDao.dropDaoTable();
			// teamDao.dropDaoTable();
		    // matchDao.dropDaoTable();

			// countryDao.showObjectsFromXML();
			// leagueDao.showObjectsFromXML();
			// teamDao.showObjectsFromXML();
			// matchDao.showObjectsFromXML();

			// countryDao.writeObjectsFromSQLiteToPostgreSQL();
			// leagueDao.writeObjectsFromSQLiteToPostgreSQL();
			// teamDao.writeObjectsFromSQLiteToPostgreSQL();
			// matchDao.writeObjectsFromSQLiteToPostgreSQL();

			// SQLite_util.closeConnection();
			// PostgreSQL_util.closeConnection();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
