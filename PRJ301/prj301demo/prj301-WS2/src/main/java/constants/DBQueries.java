package constants;

public class DBQueries {
    // User
    public static final String USER_LOGIN = "SELECT * FROM users WHERE username=? AND password=? ";
    public static final String USER_SEARCH = "SELECT username, name FROM users WHERE name like ?";
    public static final String USER_DELETE = "DELETE users WHERE username=?";
    public static final String USER_UPDATE = "UPDATE users SET name=? WHERE username=?";
    public static final String USER_CHECK_DUPLICATE = "SELECT userID FROM users WHERE userID=?  ";
    public static final String USER_INSERT = "INSERT INTO users(username, name, password) " + "VALUES(?,?,?)";
    
    // Workout
    public static final String SELECT_ALL_WORKOUT = "SELECT * FROM Workouts";
}
