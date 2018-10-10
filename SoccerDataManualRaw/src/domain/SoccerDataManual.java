package domain;

import java.awt.Label;
import java.sql.*;
import java.util.ArrayList;

import dao.CountryDao;
import dao.LeagueDao;
import model.Country;
import model.League;
import util.DBConnetions.PostgreSQL_util;
import util.DBConnetions.SQLite_util;

public class SoccerDataManual {

	public static void main(String[] args) {
		CountryDao countryDao = new CountryDao();
		LeagueDao leagueDao = new LeagueDao();

		ArrayList<Country> arrCountries = new ArrayList<Country>();
		ArrayList<League> arrLeagues = new ArrayList<>();

		try {

			System.out.println("Is league xml created ? : " + leagueDao.createObjectXML());
			System.out.println("Is country xml created ? : " + countryDao.createObjectXML());
			
			//countryDao.showAll();
			//leagueDao.showAll();

			//System.out.println(countryDao.createDaoTable());
			//System.out.println(leagueDao.createDaoTable());
			
			//leagueDao.dropDaoTable();
			//countryDao.dropDaoTable();
			
			//countryDao.showObjectsFromXML();
			//leagueDao.showObjectsFromXML();
			
			countryDao.writeObjectsFromSQLiteToPostgreSQL();
			leagueDao.writeObjectsFromSQLiteToPostgreSQL();
						
			SQLite_util.closeConnection();
			PostgreSQL_util.closeConnection();
		
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}

}
