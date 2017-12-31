package active_record;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Panel {
	static public Scanner scanner = new Scanner(System.in);
	
	
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
			case "edit": break;
			case "delete": break;
			case "quit": break;
			default: System.out.println("Błędna komenda. Wybierz add, edit, delete lub quit");
						command = commandReader();
		}
		return command;
	}
	
	
	static public String getConsoleString(String title) {
		System.out.print(title);
		return scanner.nextLine();
	}
	
	
	static public String getConsoleEmail(String title) {
		final String COMPATIBILE = "Wprowadzone dane zgodne ze wzorcem adresu mailowego";
		final String INCOMPATIBLE = "Wprowadzone dane niezgodne ze wzorcem adresu mailowego";
		
		String email = Panel.getConsoleString(title);
		Pattern compiledPattern = Pattern.compile("^[_a-zA-Z0-9-]+(\\.[_a-zA-Z0-9-]+)*@[a-zA-Z0-9-]+"
												+ "(\\.[a-zA-Z0-9-]{1,})*\\.([a-zA-Z]{2,}){1}$");
		Matcher ifEmailAddress = compiledPattern.matcher(email);
		if (ifEmailAddress.matches()) {
			System.out.println(COMPATIBILE);
			return email;
		} else if (retrying(INCOMPATIBLE)) {
			email = Panel.getConsoleString(title);
			ifEmailAddress = compiledPattern.matcher(email);
			if (ifEmailAddress.matches()) {
				System.out.println(COMPATIBILE);
				return email;
			} else {
				System.out.println(INCOMPATIBLE);
				return null;
			}
		} else {
			System.out.println(INCOMPATIBLE);
			return null;
		}
	}
	
	
	static public boolean isAccepted() {
		while (true) {
			String line = scanner.next();
			scanner.nextLine();
			if (line.substring(0, 1).toLowerCase().equals("t")) {
				return true;
			} else if (line.substring(0,1).toLowerCase().equals("n")) {
				return false;
			}
		}		
	}
	
	
	static public boolean retrying(String title) {
		System.out.println(title);
		System.out.print("chcesz spróbować jeszcze raz? T(t)/N(n): ");
		return isAccepted();
	}
	
	
	static public boolean isConfirmed(String title) {
		System.out.println(title);
		System.out.print("potwierdzasz? T(t)/N(n): ");
		return isAccepted();
	}	
	
	
	static public int getConsoleInt(String title) {
		System.out.print(title);
		while (!scanner.hasNextInt()) {
			scanner.next();
			System.out.print(title);
		}
		int tmpInt = scanner.nextInt();
		scanner.nextLine();
		return tmpInt;
	}
	
	
	static public String getConsolePassword(String title) {
		return Panel.getConsoleString(title);
	}
	
	
	static public void close() {
		scanner.close();
	}
	
}
