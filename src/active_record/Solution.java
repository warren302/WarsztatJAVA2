package active_record;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;


public class Solution {
	private int id = 0;
	private LocalDateTime created;
	private LocalDateTime updated;
	private String description;
	private int usersId;
	private int exerciseId;
	
	
	public Solution() {}


	
	
	
	public Solution(String created, String updated, String description, int usersId, int exerciseId) {
		super();
		this.setCreated(created);
		this.setUpdated(updated);
		this.setDescription(description);
		this.setUsersId(usersId);
		this.setExerciseId(exerciseId);
	}


	public int getId() {
		return id;
	}


	private void setId(int id) {
		this.id = id;
	}


	private static LocalDateTime StringToLocalDateTime(String dateTimeStr) {
	       if (dateTimeStr == null) {
	           return null;
	       } else {
	           DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.[S]");
	           return LocalDateTime.parse(dateTimeStr, formatter);
	       }
	   }
	
	
	
	private static String LocalDateTimeToString(LocalDateTime dateTime) {
	       if (dateTime == null) {
	           return null;
	       } else {
	           DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	           return dateTime.format(formatter);
	       }
	   }
	
	
	
	public String getCreated() {
		return LocalDateTimeToString(created);
	}


	public void setCreated(String created) {
		this.created = StringToLocalDateTime(created);
	}


	public String getUpdated() {
		return LocalDateTimeToString(updated);
	}


	public void setUpdated(String updated) {
		this.updated = StringToLocalDateTime(updated);
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public int getUsersId() {
		return usersId;
	}


	public void setUsersId(int usersId) {
		this.usersId = usersId;
	}


	public int getExerciseId() {
		return exerciseId;
	}


	public void setExerciseId(int exerciseId) {
		this.exerciseId = exerciseId;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(this.getId()).append(", ");
		sb.append(this.getCreated()).append(", ");
		if (this.updated != null) {
			sb.append(this.getUpdated()).append(", ");
		} else {
			sb.append(" , ");
		}
		sb.append(this.getDescription()).append(", ");
		sb.append(this.getUsersId()).append(", ");
		sb.append(this.getExerciseId());
		return sb.toString();		
	}
	
	
	static public Solution loadById(Connection conn, int id) throws SQLException {
		String sql = "Select * from solution where id =?;";
		PreparedStatement pst = conn.prepareStatement(sql);
		pst.setInt(1, id);
		ResultSet res = pst.executeQuery();
		if (res.next()) {
			Solution tmpSol = new Solution();
			tmpSol.setId(res.getInt("id"));
			tmpSol.setCreated(res.getString("created"));
			tmpSol.setUpdated(res.getString("updated"));
			tmpSol.setDescription(res.getString("description"));
			tmpSol.setUsersId(res.getInt("users_id"));
			tmpSol.setExerciseId(res.getInt("exercise_id"));
			return tmpSol;
		}
		return null;
	}
	
	static public Solution[] loadAll(Connection conn) throws SQLException {
		
		ArrayList<Solution> solutions = new ArrayList<Solution>();
	
		Statement st = conn.createStatement();
		ResultSet res = st.executeQuery("Select * from solution;");
		while (res.next()) {
			Solution tmpSol = new Solution();
			tmpSol.setId(res.getInt("id"));
			tmpSol.setCreated(res.getString("created"));
			tmpSol.setUpdated(res.getString("updated"));
			tmpSol.setDescription(res.getString("description"));
			tmpSol.setUsersId(res.getInt("users_id"));
			tmpSol.setExerciseId(res.getInt("exercise_id"));
			solutions.add(tmpSol);
		}
		Solution[] solArr = new Solution[solutions.size()];
		solutions.toArray(solArr);
		return solArr;
	}

	public void delete(Connection conn) throws SQLException {
		if (this.getId() != 0) {
			String sql = "Delete from solution where id = ?;";
			PreparedStatement pst = conn.prepareStatement(sql);
			pst.setInt(1, this.getId());
			pst.executeUpdate();
			this.setId(0);
		}
		
	}
	
	
	/**
	 * Method writes one object as a record to the table (solution)
	 * if it is a new one or overwrites data if such exists  
	 * @param conn
	 * @return Solution
	 * @throws SQLException
	 */
	public Solution saveToDB(Connection conn) throws SQLException {
		if (this.getId() == 0) {
			String[] generatedColumns = { "id" };
			String sql = "Insert into solution (created, updated, description, users_id, exercise_id) Values (?, ?, ?, ?, ?);";
			PreparedStatement pst = conn.prepareStatement(sql, generatedColumns);
			pst.setString(1, this.getCreated());
			pst.setString(2, this.getUpdated());
			pst.setString(3, this.getDescription());
			pst.setInt(4, this.getUsersId());
			pst.setInt(5, this.getExerciseId());
			pst.executeUpdate();
			ResultSet res = pst.getGeneratedKeys();
			if (res.next())
				this.setId(res.getInt(1));
		} else {
			String sql = "Update solution SET created = ?, updated = ?, description = ?, users_id = ?, exercise_id = ? where id = ?;";
			PreparedStatement pst = conn.prepareStatement(sql);
			pst.setString(1, this.getCreated());
			pst.setString(2, this.getUpdated());
			pst.setString(3, this.getDescription());
			pst.setInt(4, this.getUsersId());
			pst.setInt(5, this.getExerciseId());
			pst.setInt(6, this.getId());
			pst.executeUpdate();
		}
		return this;
	}
	
	/**
	 * Method returns all solutions of a user 
	 * @param conn
	 * @param id
	 * @return Array of solution for given userId
	 * @throws SQLException
	 */
	static public Solution[] loadAllByUserId(Connection conn, int id) throws SQLException {
		String sql = "Select * from solution where users_id =?;";
		PreparedStatement pst = conn.prepareStatement(sql);
		pst.setInt(1, id);
		ResultSet res = pst.executeQuery();
		ArrayList<Solution> list = new ArrayList<Solution>();
		while (res.next()) {
			Solution tmpSol = new Solution();
			tmpSol.setId(res.getInt("id"));
			tmpSol.setCreated(res.getString("created"));
			tmpSol.setUpdated(res.getString("updated"));
			tmpSol.setDescription(res.getString("description"));
			tmpSol.setUsersId(res.getInt("users_id"));
			tmpSol.setExerciseId(res.getInt("exercise_id"));
			list.add(tmpSol);	
		}
		Solution[] tmpArr = new Solution[list.size()];
		list.toArray(tmpArr);
		return tmpArr;
	}
	
	static public Solution[] loadAllByExcerciseId(Connection conn, int id) throws SQLException {
		String sql = "Select * from solution where exercise_id =?;";
		PreparedStatement pst = conn.prepareStatement(sql);
		pst.setInt(1, id);
		ResultSet res = pst.executeQuery();
		ArrayList<Solution> list = new ArrayList<Solution>();
		while (res.next()) {
			Solution tmpSol = new Solution();
			tmpSol.setId(res.getInt("id"));
			tmpSol.setCreated(res.getString("created"));
			tmpSol.setUpdated(res.getString("updated"));
			tmpSol.setDescription(res.getString("description"));
			tmpSol.setUsersId(res.getInt("users_id"));
			tmpSol.setExerciseId(res.getInt("exercise_id"));
			list.add(tmpSol);	
		}
		
		list.sort(new Comparator<Solution>() {
		    @Override
		    public int compare(Solution o1, Solution o2) {
		        return o2.getCreated().compareTo(o1.getCreated());
		    }
		});
		
		Solution[] tmpArr = new Solution[list.size()];
		list.toArray(tmpArr);
		return tmpArr;
	}
}
