package active_record;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class UserAdmMenu {
	static String[] menuLines = {"Wybierz jedną z opcji:", "* add - dodanie użytkownika", 
			"* edit - edycja użytkownika", "* delete - usunięcie użytkownika", "* quit - zakończenie programu"}; 

	
	public static void main(String[] args) {
		String db = "warsztat2";
		String conCommand = "jdbc:mysql://localhost:3306/"+db+"?useSSL=false";
		String dbUser = "root";
		String password = "coderslab";
		
		try (Connection conn = DriverManager.getConnection(conCommand, dbUser, password)){
			boolean isExit = false; 
			while (!isExit) {
				User[] users = User.loadAll(conn);
				UserDisplayUtils.displayAll(users);
				String command = Panel.mainMenu(menuLines);
				User user;
				switch (command) {
					case "add"   :	user = addUser();
									if (user != null) 
										user.saveToDB(conn);
									break;
					
					case "edit"  :	user = editUser(conn);
									if (user != null)
										user.saveToDB(conn);
									break;
									
					case "delete":	user = deleteUser(conn);
									if (user != null)
										user.delete(conn);
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


	
	static public User addUser() {
		String username = Panel.getConsoleString("Podaj imię i nazwisko użytkownika: ");
		String email = Panel.getConsoleEmail("Podaj e-mail użytkownika: ");
		if (email == null) {
			System.out.println("Wprowadzanie anulowane ");
			return null;
		}
		String password = Panel.getConsolePassword("Podaj hasło użytkownika: ");
		int groupId = Panel.getConsoleInt("Podaj nr grupy, do której użytkownik jest przypisany: ");
		User user = new User(username, email, password, groupId);
		return user;
	}
	
	
	static public User editUser(Connection conn) throws SQLException {
		int userId = Panel.getConsoleInt("Podaj id użytkownika, którego chcesz edytować: ");
		User user = User.loadById(conn, userId);
		if (user != null) {
			UserDisplayUtils.display(user);
			String username = Panel.getConsoleString("Podaj imię i nazwisko użytkownika: ");
			String email = Panel.getConsoleEmail("Podaj e-mail użytkownika: ");
			if (email == null) {
				System.out.println("Wprowadzanie anulowane ");
				return null;
			}
			String password = Panel.getConsolePassword("Podaj hasło użytkownika: ");
			int groupId = Panel.getConsoleInt("Podaj nr grupy, do której użytkownik jest przypisany: ");
			if (Panel.isConfirmed("Wprowadzono zmiany")) {
				user.setUsername(username);
				user.setEmail(email);
				user.setPassword(password);
				user.setGroupId(groupId);
				return user;
			} else {
				return null;
			}
		}
		return null;
	}
	
	
	static public User deleteUser(Connection conn) throws SQLException {
		int userId = Panel.getConsoleInt("Podaj id użytkownika, którego chcesz usunąć: ");
		User user = User.loadById(conn, userId);
		if (Panel.isConfirmed("Wybrano użytkownika do usunięcia")) {
			return user;
		} else {
			return null;
		}
	}
	
	
	
	
	
}
