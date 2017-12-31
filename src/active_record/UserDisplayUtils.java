package active_record;

public class UserDisplayUtils {
	static final int ID_LENGTH = 4;
	static final int USERNAME_LENGTH = 20;
	static final int EMAIL_LENGTH = 20;
	static final int PASSWORD_LENGTH = 60;
	static final int GROUPID_LENGTH = 8;
	static final int ROW_LENGTH = ID_LENGTH + USERNAME_LENGTH + EMAIL_LENGTH + PASSWORD_LENGTH + GROUPID_LENGTH;

	
	static public void displayTableHeader() {
		System.out.print(StrFormatUtil.fixedRightLengthString("ID", ID_LENGTH));
		System.out.print(StrFormatUtil.fixedRightLengthString("USERNAME", USERNAME_LENGTH));
		System.out.print(StrFormatUtil.fixedRightLengthString("E-MAIL", EMAIL_LENGTH));
		System.out.print(StrFormatUtil.fixedRightLengthString("PASSWORD", PASSWORD_LENGTH));
		System.out.println(StrFormatUtil.fixedRightLengthString("GROUP_ID", GROUPID_LENGTH));
	}
	
	
	static public void displayTableRow(User user) {
		String[] record = user.toString().split(",");
		System.out.print(StrFormatUtil.fixedRightLengthString(record[0].trim(), ID_LENGTH));
		System.out.print(StrFormatUtil.fixedRightLengthString(record[1].trim(), USERNAME_LENGTH));
		System.out.print(StrFormatUtil.fixedRightLengthString(record[2].trim(), EMAIL_LENGTH));
		System.out.print(StrFormatUtil.fixedRightLengthString(record[3].trim(), PASSWORD_LENGTH));
		System.out.println(StrFormatUtil.fixedRightLengthString(record[4].trim(), GROUPID_LENGTH));
	}
	

	
	static public void displaySingleLine() {
		for (int i = 0; i < ROW_LENGTH; i++) {
			System.out.print("-");
		}
		System.out.println();
	}

	
	static public void displayDoubleLine() {
		for (int i = 0; i < ROW_LENGTH; i++) {
			System.out.print("=");
		}
		System.out.println();
	}
	
	
	static public void displayAll(User[] users) {
		displayTableHeader();
		displaySingleLine();
		for (User user : users) {
			displayTableRow(user);
		}
		displayDoubleLine();
	}

	
	static public void display(User user) {
		displayTableHeader();
		displaySingleLine();
		displayTableRow(user);
		displayDoubleLine();
	}
}
