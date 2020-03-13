package main;
import java.sql.*;
import java.util.ArrayList;

/***
 * DatabaseHandler is responsible for adding flashcards
 * and their sets as well as deleting them
 * @author ninjagodivan
 *
 */
public class DatabaseHandler {
	
	/**
	 * Connects to MySQL server. You will
	 * need to enter your username and
	 * password.
	 * @return
	 */
	public static Connection getConnection() {
			
			try {
				String driver = "com.mysql.cj.jdbc.Driver";
				String url = "jdbc:mysql://localhost:3306/Flashcards";
				String username = "root", password = "root";
				Class.forName(driver);
				Connection connection = DriverManager.getConnection(url, username, password);
				System.out.println("Database Connected!");
				return connection; 
			} catch(Exception e) {
				System.out.println(e);
			}
			
			return null;
		}
	
	/**
	 * Creates a flashcard table
	 * @param newSet
	 */
	public static void addFlashCardSet(String newSet) {
		
		try {
			
			Connection connect = getConnection();
			PreparedStatement add = connect.prepareStatement("CREATE TABLE " + newSet + "(flashcard_id INT AUTO_INCREMENT PRIMARY KEY, question TEXT, answer TEXT);");
			add.executeUpdate();
			System.out.println("Flashcard Set Added!!!");
		}catch(Exception e) {
			System.out.println(e);
		}
		
	}
	
	/**
	 * Adds a flashcard to a specified flashcard set
	 * @param question
	 * @param answer
	 * @param flashcardSet
	 */
	public static void addFlashCard(String question, String answer, String flashcardSet) {
		
		try {
			Connection connect = getConnection();
			PreparedStatement add = connect.prepareStatement("INSERT INTO " + flashcardSet + "VALUES(" + question + "," + answer + ");");
			add.executeUpdate();
		}catch(Exception e) {
			System.out.println(e);
		}
		
	}
	
	/**
	 * Returns all flashcard sets from the flashcard database
	 * @return 
	 */
	public static void getAllFlashCardSets(){
		
		ArrayList<String> flashcardSets = new ArrayList<>();
		
		try {
			Connection connect = getConnection();
			PreparedStatement getAll = connect.prepareStatement("SELECT TABLE_NAME FROM information_schema.tables WHERE table_schema = 'Flashcards';");
			ResultSet res = getAll.executeQuery();
			
			while(res.next()) {
				System.out.println(res.getString("TABLE_NAME"));
			}
			
		}catch(Exception e) {
			System.out.println(e);
		}
		
		//return flashcardSets;
	}
	
	/**
	 * Checks if the flashcard set exists
	 * @param set
	 * @return
	 */
	public static boolean flashcardSetExists(String set) {
		
		try {
			
			Connection connect = getConnection();
			PreparedStatement get = connect.prepareStatement("SELECT * FROM flashcard_sets WHERE flashcard_setName = '" + set + "'");
			ResultSet res = get.executeQuery();
			
			if(res.next())
				return true;
		}catch(Exception e) {
			System.out.print(e);
		}
		
		return false;
	}
}
