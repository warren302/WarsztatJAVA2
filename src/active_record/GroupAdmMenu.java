package active_record;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class GroupAdmMenu {
	static String[] menuLines = {"Wybierz jedną z opcji:", "* add - dodanie grupy", 
			"* edit - edycja grupy", "* delete - usunięcie grupy", "* quit - zakończenie programu"}; 

	
	public static void main(String[] args) {
		String db = "warsztat2";
		String conCommand = "jdbc:mysql://localhost:3306/"+db+"?useSSL=false";
		String dbUser ="root";
		String password ="coderslab";
		
		try (Connection conn = DriverManager.getConnection(conCommand, dbUser, password)) {
			boolean isExit = false; 
			while (!isExit) {
				Group[] groups = Group.loadAll(conn);
				GroupDisplayUtils.displayAll(groups);
				String command = Panel.mainMenu(menuLines);
				Group group;
				switch (command) {
					case "add"   :	group = addGroup();
									if (group != null) 
										group.saveToDB(conn);
									break;
					
					case "edit"  :	group = editGroup(conn);
									if (group != null)
										group.saveToDB(conn);
									break;
									
					case "delete":	group = deleteGroup(conn);
									if (group != null)
										group.delete(conn);
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

	
	static public Group addGroup() {
		String name = Panel.getConsoleString("Podaj nazwę grupy: ");
		Group group = new Group(name);
		return group;
	}
	
	
	static public Group editGroup(Connection conn) throws SQLException {
		int groupId = Panel.getConsoleInt("Podaj id grupy, którą chcesz edytować: ");
		Group group = Group.loadById(conn, groupId);
		if (group != null) {
			GroupDisplayUtils.display(group);
			String name = Panel.getConsoleString("Podaj nazwę grupy: ");
			if (Panel.isConfirmed("Wprowadzono zmiany")) {
				group.setName(name);
				return group;
			} else {
				return null;
			}
		}
		return null;
	}
	
	
	static public Group deleteGroup(Connection conn) throws SQLException {
		int groupId = Panel.getConsoleInt("Podaj id grupy, którą chcesz usunąć: ");
		Group group = Group.loadById(conn, groupId);
		if (Panel.isConfirmed("Wybrano grupę do usunięcia")) {
			return group;
		} else {
			return null;
		}
	}
}
