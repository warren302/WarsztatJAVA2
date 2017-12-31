package active_record;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Exercise {
	private int id = 0;
	private String title;
	private String description;
	
	/**
	 * Constructor for downloading records from the table
	 */
	public Exercise() {};
	
	/**
	 * Constructor for creating a new one 
	 * @param name
	 */	
	public Exercise(String title, String description) {
		super();
		this.setTitle(title);
		this.setDescription(description);
	}


	public int getId() {
		return id;
	}


	private Exercise setId(int id) {
		this.id = id;
		return this;
	}


	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(this.id).append(", ");
		sb.append(this.title).append(", ");
		sb.append(this.description);
		return sb.toString();
	}
	
	
	
	/**
	 * Method downloads all records from table exercise 
	 * @param conn
	 * @return Exercise[]
	 * @throws SQLException
	 */
	static public Exercise[] loadAll(Connection conn) throws SQLException {
		
		ArrayList<Exercise> exercises = new ArrayList<Exercise>();
		
		Statement st = conn.createStatement();
		ResultSet res = st.executeQuery("Select * from exercise;"); 
		while (res.next()) {
			Exercise tmpEx = new Exercise();
			tmpEx.setId(res.getInt("id"));
			tmpEx.setTitle(res.getString("title"));
			tmpEx.setDescription(res.getString("description"));
			exercises.add(tmpEx);
		}
		Exercise[] exArr = new Exercise[exercises.size()];
		exercises.toArray(exArr);
		return exArr;
	}
	
	/**
	 * Method downloads one record which id is equal with given parameter
	 * @param conn
	 * @param id
	 * @return Exercise
	 * @throws SQLException
	 */
	static public Exercise loadById(Connection conn, int id) throws SQLException {
		String sql = "Select * from exercise where id = ?;";
		PreparedStatement pst = conn.prepareStatement(sql);
		pst.setInt(1, id);
		ResultSet res = pst.executeQuery();
		if (res.next()) {
			Exercise ex = new Exercise();
			ex.setId(res.getInt("id"));
			ex.setTitle(res.getString("title"));
			ex.setDescription(res.getString("description"));
			return ex;
		}
		return null;
	}
	
	
	
	/**
	 * Method deletes one record from the table (exercise)
	 * @param conn
	 * @throws SQLException
	 */
	public void delete(Connection conn) throws SQLException {
		if (this.id != 0) {
			String sql = "Delete from exercise where id = ?;";
			PreparedStatement pst = conn.prepareStatement(sql);
			pst.setInt(1, this.id);
			pst.executeUpdate();
			this.id = 0;
		}
	}
	
	
	/**
	 * Method writes one object as a record to the table (exercise)
	 * if it is a new one or overwrites data if such exists  
	 * @param conn
	 * @return Exercise
	 * @throws SQLException
	 */
	public Exercise saveToDB(Connection conn) throws SQLException {
		if (this.getId() == 0) {
			String[] generatedColumns = {"id"};
			String sql = "Insert into exercise (title, description) values( ?, ?);";
			PreparedStatement pst = conn.prepareStatement(sql, generatedColumns);
			pst.setString(1, this.getTitle());
			pst.setString(2, this.getDescription());
			pst.executeUpdate();
			ResultSet res = pst.getGeneratedKeys();
			if (res.next())
				this.setId(res.getInt(1));
		} else {
			String sql = "Update exercise SET  title = ?, description = ? where id = ?;";
			PreparedStatement pst = conn.prepareStatement(sql);
			pst.setString(1, this.getTitle());
			pst.setString(2, this.getDescription());
			pst.setInt(3, this.getId());
			pst.executeUpdate();
		}
		return this;
	}
	
	
	static public Exercise[] loadNotResolved(Connection conn, int userId) throws SQLException {
		String sql = "select * from exercise where id not in(select exercise_id from solution where users_id=?);";
		PreparedStatement pst = conn.prepareStatement(sql);
		pst.setInt(1, userId);
		ResultSet res = pst.executeQuery();
		ArrayList<Exercise> exercises = new ArrayList<Exercise>();
		while (res.next()) {
			Exercise tmpEx = new Exercise();
			tmpEx.setId(res.getInt("id"));
			tmpEx.setTitle(res.getString("title"));
			tmpEx.setDescription(res.getString("description"));
			exercises.add(tmpEx);
		}
		Exercise[] exArr = new Exercise[exercises.size()];
		exercises.toArray(exArr);
		return exArr;
	}
	
	
}
