package practice;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CRUD {

	public static void main(String[]args) 

	{

		try {
			//Class.forName("com.mysql.cj.jdbc.Driver");

			DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
			
			String url = "jdbc:mysql://localhost:3306/23wh1a05f9";
			String username = "root";
			String password = "1234";
			
			Connection connection=DriverManager.getConnection(url,username,password);
			System.out.println("Connection Established Successfully");
			
			Statement statement=connection.createStatement();
			
			ResultSet rs=statement.executeQuery("select *from Employee");
			while(rs.next()) {
				System.out.println("Name:"+rs.getString("name")+"\tSalary:"+rs.getFloat("salary"));
			}
			connection.close();
			
		} catch (Exception e) {
			e.printStackTrace();
			
		}
	}
}



import java.sql.*;

public class EmployeeDatabaseOperations {

    static final String DB_URL = "jdbc:mysql://localhost:3306/23wh1a05f9";
    static final String USER = "root";
    static final String PASS = "1234";

    public static Connection getConnection() {
        try {
            // Load MySQL JDBC Driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(DB_URL, USER, PASS);
        } catch (ClassNotFoundException e) {
            System.err.println("MySQL JDBC Driver not found! " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("Database connection failed: " + e.getMessage());
        }
        return null;
    }

    public static void insertEmployee(String name, float salary) {
        String insertQuery = "INSERT INTO Employee (name, salary) VALUES (?, ?)";
        try (Connection conn = getConnection(); 
             PreparedStatement stmt = conn.prepareStatement(insertQuery)) {
            if (conn == null) return;

            stmt.setString(1, name);
            stmt.setFloat(2, salary);
            int rowsAffected = stmt.executeUpdate();
            System.out.println(rowsAffected + " record(s) inserted.");
        } catch (SQLException e) {
            System.err.println("Error inserting employee: " + e.getMessage());
        }
    }

    public static void updateEmployee(String oldName, String newName, float newSalary) {
        String updateQuery = "UPDATE Employee SET name = ?, salary = ? WHERE name = ?";
        try (Connection conn = getConnection(); 
             PreparedStatement stmt = conn.prepareStatement(updateQuery)) {
            if (conn == null) return;

            stmt.setString(1, newName);
            stmt.setFloat(2, newSalary);
            stmt.setString(3, oldName);
            int rowsAffected = stmt.executeUpdate();
            System.out.println(rowsAffected + " record(s) updated.");
        } catch (SQLException e) {
            System.err.println("Error updating employee: " + e.getMessage());
        }
    }

    public static void deleteEmployee(float salary) {
        String deleteQuery = "DELETE FROM Employee WHERE salary = ?";
        try (Connection conn = getConnection(); 
             PreparedStatement stmt = conn.prepareStatement(deleteQuery)) {
            if (conn == null) return;

            stmt.setFloat(1, salary);
            int rowsAffected = stmt.executeUpdate();
            System.out.println(rowsAffected + " record(s) deleted.");
        } catch (SQLException e) {
            System.err.println("Error deleting employee: " + e.getMessage());
        }
    }

    public static void displayEmployees() {
        String selectQuery = "SELECT * FROM Employee";
        try (Connection conn = getConnection(); 
             PreparedStatement stmt = conn.prepareStatement(selectQuery);
             ResultSet rs = stmt.executeQuery()) {
            if (conn == null) return;

            System.out.println("\nEmployee Records:");
            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("id") + 
                                   ", Name: " + rs.getString("name") + 
                                   ", Salary: " + rs.getFloat("salary"));
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving employees: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        insertEmployee("Shivani", 60000);
        insertEmployee("Aishwarya", 50000);
        displayEmployees();

        updateEmployee("Shivani", "Nithya", 65000);
        displayEmployees();

        deleteEmployee(50000);
        displayEmployees();
    }
}