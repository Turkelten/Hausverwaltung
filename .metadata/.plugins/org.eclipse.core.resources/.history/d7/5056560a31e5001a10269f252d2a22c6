package Objects;

import java.sql.ResultSet;
import java.sql.SQLException;

public class User
{
    public User(int id, String firstName, String lastName, String loginEmailAddress, String passwordHash)
    {
        this.id = id;
        this.firstName = firstName;
        this.lastName =lastName;
        this.userName = loginEmailAddress;
        this.passwordHash = passwordHash;
    }

    public User(ResultSet result) throws SQLException
    {
        id = result.getInt("id");
        firstName = result.getString("firstName");
        lastName =result.getString("lastName");
        userName = result.getString("loginEmailAddress");
        passwordHash = result.getString("passwordHash");

    }

    public int id;
    public String firstName;
    public String lastName;
    public String userName;
    public String passwordHash;
    
    
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPasswordHash() {
		return passwordHash;
	}

	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
    
    
}
