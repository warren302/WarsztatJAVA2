package active_record;

public class ExerciseDisplayUtils {
	static final int ID_LENGTH = 4;
	static final int TITLE_LENGTH = 40;
	static final int DESCRIPTION_LENGTH = 100;
	static final int ROW_LENGTH = ID_LENGTH + TITLE_LENGTH + DESCRIPTION_LENGTH;

	
	static public void displayTableHeader() {
		System.out.print(StrFormatUtil.fixedRightLengthString("ID", ID_LENGTH));
		System.out.print(StrFormatUtil.fixedRightLengthString("TITLE", TITLE_LENGTH));
		System.out.println(StrFormatUtil.fixedRightLengthString("DESCRIPTION", DESCRIPTION_LENGTH));
	}
	
	
	static public void displayTableRow(Exercise exercise) {
		String[] record = exercise.toString().split(",");
		System.out.print(StrFormatUtil.fixedRightLengthString(record[0].trim(), ID_LENGTH));
		System.out.print(StrFormatUtil.fixedRightLengthString(record[1].trim(), TITLE_LENGTH));
		System.out.println(StrFormatUtil.fixedRightLengthString(record[2].trim(), DESCRIPTION_LENGTH));
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
	
	
	static public void displayAll(Exercise[] exercises) {
		displayTableHeader();
		displaySingleLine();
		for (Exercise exercise : exercises) {
			displayTableRow(exercise);
		}
		displayDoubleLine();
	}

	
	static public void display(Exercise exercise) {
		displayTableHeader();
		displaySingleLine();
		displayTableRow(exercise);
		displayDoubleLine();
	}

}
