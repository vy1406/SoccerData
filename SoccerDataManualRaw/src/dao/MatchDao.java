package dao;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;


import model.Match;

import util.DBConnetions.PostgreSQL_util;
import util.DBConnetions.SQLite_util;
import util.interfaces.PostgresReaderWriter;
import util.interfaces.SqliteReader;
import util.interfaces.XML_ReaderWriter;

public class MatchDao extends Dao implements PostgresReaderWriter, SqliteReader, XML_ReaderWriter {

	@Override
	public ArrayList<Match> getObjectsFromXML() {
		ArrayList<Match> list = new ArrayList<Match>();
		String fileName = "matches";
		File xmlFile = new File(path + fileName);
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder;
		try {
			dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(xmlFile);
			doc.getDocumentElement().normalize();
			NodeList nodeList = doc.getElementsByTagName("Match");
			for (int i = 0; i < nodeList.getLength(); i++) {
				list.add((Match) getObject(nodeList.item(i)));
			}

		} catch (SAXException | ParserConfigurationException | IOException e1) {
			e1.printStackTrace();
		}

		return list;
	}

	@Override
	public Object getObject(Node node) {
		Match match = new Match();
		String s = "";
		if (node.getNodeType() == Node.ELEMENT_NODE) {
			Element element = (Element) node;
			match.setId(getTagValue("id", element));
			match.setCountry_id(getTagValue("country_id", element));
			match.setLeague_id(getTagValue("league_id", element));
			match.setSeason(getTagValue("season", element));
			
			s = getTagValue("date", element);
			Date date = new Date();
			try {
				
				date = convertToDate(s);
			
			} catch (ParseException e) {

				e.printStackTrace();
			}
			match.setDate(date);
		}

		return match;
	}

	@Override
	public void showObjectsFromXML() {
		String fileName = "matches";
		File xmlFile = new File(path + fileName);
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder;
		try {
			dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(xmlFile);
			doc.getDocumentElement().normalize();
			System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
			NodeList nodeList = doc.getElementsByTagName("Match");
			ArrayList<Match> list = new ArrayList<Match>();
			for (int i = 0; i < nodeList.getLength(); i++) {
				list.add((Match) getObject(nodeList.item(i)));
			}
			for (Match current : list) {
				System.out.println(current.toString());
			}
		} catch (SAXException | ParserConfigurationException | IOException e1) {
			e1.printStackTrace();
		}
	}

	@Override
	public boolean createObjectXML() {

		ArrayList<Match> arrMatches = new ArrayList<>();
		String fileName = "matches";
		try {
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

			// root elements
			Document doc = docBuilder.newDocument();
			Element rootElement = doc.createElement("Matches");
			doc.appendChild(rootElement);

			arrMatches = getAll();
			for (Match curMatch : arrMatches) {
				Element countryElem = doc.createElement("Match");
				rootElement.appendChild(countryElem);

				Element id = doc.createElement("id");
				id.appendChild(doc.createTextNode(curMatch.getId()));
				countryElem.appendChild(id);

				Element country_id = doc.createElement("country_id");
				country_id.appendChild(doc.createTextNode(curMatch.getCountry_id()));
				countryElem.appendChild(country_id);

				Element league_id = doc.createElement("league_id");
				league_id.appendChild(doc.createTextNode(curMatch.getLeague_id()));
				countryElem.appendChild(league_id);

				Element season = doc.createElement("season");
				season.appendChild(doc.createTextNode(curMatch.getSeason()));
				countryElem.appendChild(season);
				
				Element date = doc.createElement("date");
				date.appendChild(doc.createTextNode(curMatch.getDate().toString()));
				countryElem.appendChild(date);

			}

			// ----------------------------------
			// Save the file
			// ----------------------------------
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult streamResult = new StreamResult(new File(path + fileName));
			transformer.transform(source, streamResult);
			return true;

		} catch (Exception e) {
			System.out.println("Exception in createXML:");
			e.printStackTrace();
			return false;
		}

	}

	@Override
	public String getTagValue(String argTag, Element element) {
		NodeList nodeList = element.getElementsByTagName(argTag).item(0).getChildNodes();
		Node node = (Node) nodeList.item(0);
		return node.getNodeValue();
	}

	@Override
	public ArrayList<Match> getAll() {
		connection = SQLite_util.getConnection();
		ArrayList<Match> matches = new ArrayList<>();

		try {
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery("select * from Match");
			while (rs.next()) {
				Match curMatch = new Match();
				curMatch.setId(rs.getString("id"));
				curMatch.setCountry_id(rs.getString("country_id"));
				curMatch.setLeague_id(rs.getString("league_id"));
				curMatch.setSeason(rs.getString("season"));

				Date formattedDate = convertToDate(rs.getString("date"));
				curMatch.setDate(formattedDate);

				matches.add(curMatch);
			}

		} catch (ParseException | SQLException e) {
			e.printStackTrace();
		}
		return matches;
	}

	private Date convertToDate(String receivedDate) throws ParseException {
		System.out.println("here");
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Date date = formatter.parse(receivedDate);
		return date;
	}

	@Override
	public void showAll() {
		ArrayList<Match> matches = this.getAll();
		for (int i = 0; i < matches.size(); i++) {
			Match curMatch = matches.get(i);
			System.out.println(curMatch);
		}
	}

	@Override
	public void closeConnection() {
		try {
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public void writeObjectsFromSQLiteToPostgreSQL() {
		ArrayList<Match> arrList = getObjectsFromXML();
		try {
			createTableIfNotExist("match");
			for (Match curMatch : arrList) {
				writeSingleObjectToPostgres(curMatch, "match");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public String dropDaoTable() {
		return dropTable("match");
	}

	@Override
	public String createDaoTable() {

		Connection con = PostgreSQL_util.getConnection();
		String message = "";
		try {
			String sql2 = " CREATE TABLE match ( id SERIAL PRIMARY KEY  , country_id text , league_id text , season text , date text ) ";
			PreparedStatement stmt = con.prepareStatement(sql2);
			stmt.executeUpdate();
			message += "match table was created ";
			return message;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "match wasnt created, see stacktrace";
	}

	@Override
	public void writeSingleObjectToPostgres(Object argObject, String argTable) throws SQLException {

		Match match = (Match) argObject;
		Connection conn = PostgreSQL_util.getConnection();
		String sql = "INSERT INTO " + argTable + " (id, country_id, league_id, season, date) VALUES (?, ?, ? , ? ,?) ";
		PreparedStatement st = conn.prepareStatement(sql);
		st.setInt(1, Integer.parseInt(match.getId()));
		st.setString(2, match.getCountry_id());
		st.setString(3, match.getLeague_id());
		st.setString(4, match.getSeason());
		st.setString(5, match.getDate().toString());
		st.executeUpdate();
		st.close();
	}

}
