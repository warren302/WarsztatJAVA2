package active_record;

public class GroupDisplayUtils {
	static final int ID_LENGTH = 4;
	static final int NAME_LENGTH = 20;
	static final int ROW_LENGTH = ID_LENGTH + NAME_LENGTH;

	
	static public void displayTableHeader() {
		System.out.print(StrFormatUtil.fixedRightLengthString("ID", ID_LENGTH));
		System.out.println(StrFormatUtil.fixedRightLengthString("NAME", NAME_LENGTH));
	}
	
	
	static public void displayTableRow(Group group) {
		String[] record = group.toString().split(",");
		System.out.print(StrFormatUtil.fixedRightLengthString(record[0].trim(), ID_LENGTH));
		System.out.println(StrFormatUtil.fixedRightLengthString(record[1].trim(), NAME_LENGTH));
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
	
	
	static public void displayAll(Group[] groups) {
		displayTableHeader();
		displaySingleLine();
		for (Group group : groups) {
			displayTableRow(group);
		}
		displayDoubleLine();
	}

	
	static public void display(Group group) {
		displayTableHeader();
		displaySingleLine();
		displayTableRow(group);
		displayDoubleLine();
	}

}
