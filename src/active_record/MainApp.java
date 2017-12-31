package active_record;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class MainApp {
	static Scanner scanner = new Scanner(System.in);
	static String[] menulines = {"Wybierz jedną z opcji:", "add - dodawanie rozwiązania", 
			"view - przeglądanie swoich rozwiązań", "quit - zakończenie programu"};

	
	
	public static void main(String[] args) {
		String db = "warsztat2";
		String conCommand = "jdbc:mysql://localhost:3306/"+db+"?useSSL=false";
		String dbUser = "root";
		String password = "coderslab";
		
		int userId = 0;
		try {
			userId = Integer.parseInt(args[0]);
		} catch (ArrayIndexOutOfBoundsException e) {
			System.out.println("Brak wymaganego parametru (user id)");
			e.printStackTrace();
		} catch (NullPointerException e){
			System.out.println("Wymagany parametr typu int");
			e.printStackTrace();
		}
		try (Connection conn = DriverManager.getConnection(conCommand, dbUser, password)) {
			boolean isExit = false;
			while (!isExit) {
				String command = mainMenu(menulines);
				switch (command) {
					case "add"	:	addSolution(conn, userId);	
									break;
					
					case "view"	:	viewSolutions(conn, userId);
									break;
					
					case "quit" :	isExit = true;
									break;
				
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		scanner.close();
	}

	
	static public String mainMenu (String[] menuLines) {
		for (String string : menuLines) {
			System.out.println(string);
		}
		return commandReader();
	}

	
	static private String commandReader() {
		String command = scanner.nextLine();
		command = command.toLowerCase();
		switch (command) {
			case "add": break;
			case "view": break;
			case "quit": break;
			default: System.out.println("Błędna komenda. Wybierz add, view lub quit");
						command = commandReader();
		}
		return command;
	}	

	
	static public void addSolution(Connection conn, int userId) throws SQLException {
		Exercise[] exercises = Exercise.loadNotResolved(conn, userId);
		ExerciseDisplayUtils.displayAll(exercises);
		int exerciseId = Panel.getConsoleInt("Podaj id zadania: ");
		boolean isIn = false;
		for (Exercise exercise : exercises) {
			if (exercise.getId() == exerciseId) {
				isIn = true;
				break;
			}
		}
		if (!isIn) {
			System.out.println("Rozwiązanie do tego zadania już zapisane");
		} else {
			String description = Panel.getConsoleString("Podaj rozwiazanie zadania: ");
	        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.[S]");
			String created = LocalDateTime.now().format(formatter);
			Solution solution = new Solution(created, created, description, userId, exerciseId);
			solution.saveToDB(conn);
		}
	}

	
	static public void viewSolutions(Connection conn, int userId) throws SQLException {
		Solution[] solutions = Solution.loadAllByUserId(conn, userId);
		SolutionDisplayUtils.displayAll(solutions);
	}
}
