package active_record;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ExerciseAdmMenu {
	static String[] menuLines = {"Wybierz jedną z opcji:", "* add - dodanie zadania", 
			"* edit - edycja zadania", "* delete - usunięcie zadania", "* quit - zakończenie programu"}; 
	
	
	
	public static void main(String[] args) {
		String db = "warsztat2";
		String conCommand = "jdbc:mysql://localhost:3306/"+db+"?useSSL=false";
		String dbUser = "root";
		String password = "coderslab";

		try (Connection conn = DriverManager.getConnection(conCommand, dbUser, password)) {
			boolean isExit = false; 
			while (!isExit) {
				Exercise[] exercises = Exercise.loadAll(conn);
				ExerciseDisplayUtils.displayAll(exercises);
				String command = Panel.mainMenu(menuLines);
				Exercise exercise;
				switch (command) {
					case "add"   :	exercise = addExercise();
									if (exercise != null) 
										exercise.saveToDB(conn);
									break;
					
					case "edit"  :	exercise = editExercise(conn);
									if (exercise != null)
										exercise.saveToDB(conn);
									break;
									
					case "delete":	exercise = deleteExercise(conn);
									if (exercise != null)
										exercise.delete(conn);
									break;
	
					case "quit"  :	isExit = true;
									break;
						
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		Panel.close();
	}


	static public Exercise addExercise() {
		String title = Panel.getConsoleString("Podaj tytuł zadania: ");
		String description = Panel.getConsoleString("Podaj opis zadania: ");
		Exercise exercise = new Exercise(title, description);
		return exercise;
	}
	
	
	static public Exercise editExercise(Connection conn) throws SQLException {
		int exerciseId = Panel.getConsoleInt("Podaj id zadania, które chcesz edytować: ");
		Exercise exercise = Exercise.loadById(conn, exerciseId);
		if (exercise != null) {
			ExerciseDisplayUtils.display(exercise);
			String title = Panel.getConsoleString("Podaj tytuł zadania: ");
			String description = Panel.getConsoleString("Podaj opis zadania: ");
			if (Panel.isConfirmed("Wprowadzono zmiany")) {
				exercise.setTitle(title);
				exercise.setDescription(description);
				return exercise;
			} else {
				return null;
			}
		}
		return null;
	}
	
	
	static public Exercise deleteExercise(Connection conn) throws SQLException {
		int exerciseId = Panel.getConsoleInt("Podaj id zadania, które chcesz usunąć: ");
		Exercise exercise = Exercise.loadById(conn, exerciseId);
		if (Panel.isConfirmed("Wybrano zadanie do usunięcia")) {
			return exercise;
		} else {
			return null;
		}
	}
	
	
	
}
