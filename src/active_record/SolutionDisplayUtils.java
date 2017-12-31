package active_record;

public class SolutionDisplayUtils {
	static final int ID_LENGTH = 4;
	static final int CREATED_LENGTH = 20;
	static final int UPDATED_LENGTH = 20;
	static final int DESCRIPTION_LENGTH = 60;
	static final int USERSID_LENGTH = 8;
	static final int EXERCISEID_LENGTH = 10;
	static final int ROW_LENGTH = ID_LENGTH + CREATED_LENGTH + UPDATED_LENGTH + DESCRIPTION_LENGTH + USERSID_LENGTH 
								+ EXERCISEID_LENGTH;

	
	static public void displayTableHeader() {
		System.out.print(StrFormatUtil.fixedRightLengthString("ID", ID_LENGTH));
		System.out.print(StrFormatUtil.fixedRightLengthString("CREATED", CREATED_LENGTH));
		System.out.print(StrFormatUtil.fixedRightLengthString("UPDATED", UPDATED_LENGTH));
		System.out.print(StrFormatUtil.fixedRightLengthString("DESCRIPTION", DESCRIPTION_LENGTH));
		System.out.print(StrFormatUtil.fixedRightLengthString("USERSID", USERSID_LENGTH));
		System.out.println(StrFormatUtil.fixedRightLengthString("EXERCISEID", EXERCISEID_LENGTH));
	}
	
	
	static public void displayTableRow(Solution solution) {
		String[] record = solution.toString().split(",");
		System.out.print(StrFormatUtil.fixedRightLengthString(record[0].trim(), ID_LENGTH));
		System.out.print(StrFormatUtil.fixedRightLengthString(record[1].trim(), CREATED_LENGTH));
		System.out.print(StrFormatUtil.fixedRightLengthString(record[2].trim(), UPDATED_LENGTH));
		System.out.print(StrFormatUtil.fixedRightLengthString(record[3].trim(), DESCRIPTION_LENGTH));
		System.out.print(StrFormatUtil.fixedRightLengthString(record[4].trim(), USERSID_LENGTH));
		System.out.println(StrFormatUtil.fixedRightLengthString(record[5].trim(), EXERCISEID_LENGTH));
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
	
	
	static public void displayAll(Solution[] solutions) {
		displayTableHeader();
		displaySingleLine();
		for (Solution solution : solutions) {
			displayTableRow(solution);
		}
		displayDoubleLine();
	}

	
	static public void display(Solution solution) {
		displayTableHeader();
		displaySingleLine();
		displayTableRow(solution);
		displayDoubleLine();
	}
}
