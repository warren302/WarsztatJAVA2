package active_record;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class ExerciseAssignmentAdmMenu {
	static Scanner scanner = new Scanner(System.in);
	static String[] menulines = {"Wybierz jedną z opcji:", "add - przypisywanie zadań do użytkowników", 
			"view - przeglądanie rozwiązań danego użytkownika", "quit - zakończenie programu"};
			
	
	
	public static void main(String[] args) {
		String db = "warsztat2";
		String conCommand = "jdbc:mysql://localhost:3306/"+db+"?useSSL=false";
		String dbUser = "root";
		String password = "coderslab";
		
		try (Connection conn = DriverManager.getConnection(conCommand, dbUser, password)) {
			boolean isExit = false;
			while (!isExit) {
				String command = mainMenu(menulines);
				switch (command) {
					case "add"	:	AssignExerciseToUser(conn);	
									break;
					
					case "view"	:	ViewSolutionsOfUser(conn);
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
	
	
	static public void AssignExerciseToUser(Connection conn) throws SQLException {
		User[] users = User.loadAll(conn);
		UserDisplayUtils.displayAll(users);
		int userId = Panel.getConsoleInt("Podaj id użytkownika: ");
		Exercise[] exercises = Exercise.loadAll(conn);
		ExerciseDisplayUtils.displayAll(exercises);
		int exerciseId = Panel.getConsoleInt("Podaj id zadania: ");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.[S]");
		String created = LocalDateTime.now().format(formatter);
		Solution solution = new Solution(created, null, "", userId, exerciseId);
		solution.saveToDB(conn);
	}
	
	
	static public void ViewSolutionsOfUser(Connection conn) throws SQLException {
		User[] users = User.loadAll(conn);
		UserDisplayUtils.displayAll(users);
		int userId = Panel.getConsoleInt("Podaj id użytkownika: ");
		Solution[] solutions = Solution.loadAllByUserId(conn, userId);
		SolutionDisplayUtils.displayAll(solutions);
	}
	
}
