package Controller;

import Objects.Inserat;
import Objects.User;

import at.favre.lib.bytes.Bytes;
import at.favre.lib.crypto.bcrypt.BCrypt;


import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.nio.charset.StandardCharsets;
import java.sql.*;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Random;


public class Controller
{
    static Connection _connection;

    static
    {
        try
        {
        	Class.forName("org.postgresql.Driver");
            _connection = DriverManager.getConnection("jdbc:postgresql://207.154.234.136:5432/1920-Automarkt",
                    "1920-Automarkt", "caccfc046d179b6f792f841568dbb013");
        } catch (SQLException throwables)
        {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
    }

    public Controller() throws SQLException
    {
    }

    public static User LoginUser(String loginEmailAddress, String password) throws SQLException
    {
        if (!UserExists(loginEmailAddress))
        {
            return null;
        }

        String query = GetAuthenticationQuery(loginEmailAddress);

        Statement userPasswordHash = _connection.createStatement();
        ResultSet userData = userPasswordHash.executeQuery(query);
        userData.next();

        if (VerifyPassword(password, userData.getString("passwordhash")))
        {
            return new User(userData);
        } else
        {
            return null;
        }
    }

    	
    //Liefert Inserate die den gegebenen Kriterien entsprechen
    public static ArrayList<Inserat> SearchInserate(String beschreibung, String marke, int PS, float verbrauch, int groesse, int kilometerstand,
            String verbrauchsstoff, String bezeichnung) throws SQLException
    {
    	String query = GetSearchInserateQuery(beschreibung, marke, PS, verbrauch, groesse, kilometerstand, verbrauchsstoff, bezeichnung);
    	
    	Statement search = _connection.createStatement();
    	ResultSet result = search.executeQuery(query);
    	
    	
    	ArrayList<Inserat> returnList = new ArrayList<>();
        while(result.next())
        {
            returnList.add(new Inserat(result));
        }

        return returnList;
    	
    }
    
    //Liefert alle Inserate
    public static ArrayList<Inserat> ReadAllInserate() throws SQLException
    {
    	String query = "select * from inserate;";
    	Statement s = _connection.createStatement();
    	ResultSet result = s.executeQuery(query);
    	
    	ArrayList<Inserat> returnList = new ArrayList<>();
        while(result.next())
        {
            returnList.add(new Inserat(result));
        }

        return returnList;
    }
    
    //Liefert die Query zum selektieren von Inseraten nach bestimmten Kriterien
    private static String GetSearchInserateQuery(String beschreibung, String marke, int PS, float verbrauch, int groesse, int kilometerstand,
            String verbrauchsstoff, String bezeichnung)
    {
    	return String.format("select * from inserate where beschreibung like '{1}' and marke like '{4}' and PS >= %s and verbrauch <= %s and groesse >= %s and kilometerstand <= %s and verbrauchsstoff like '{5}' and bezeichnung like '{2}';",
    			PS, verbrauch, groesse, kilometerstand)
		.replace("{1}", beschreibung)
		.replace("{2}", bezeichnung)
		.replace("{4}", marke)
		.replace("{5}", verbrauchsstoff);
    			
    	
    }
    
    //Liefert ein Userobjekt mit leerem Passwort, anhand der User Email adresse ( diese ist unique)
    public static User GetUser(String loginEmailAddress) throws SQLException
    {
        String query = GetAuthenticationQuery(loginEmailAddress);
        Statement userSelect = _connection.createStatement();

        ResultSet userData = userSelect.executeQuery(query);
        userData.next();
        User user = new User(userData);
        user.passwordHash = "";
        return user;
    }

    //Liefert ein Userobjekt mit leerem Passwort, anhand der User ID
    public static User GetUser(int UserId) throws SQLException
    {
        String query = GetAuthenticationQuery(UserId);
        Statement userSelect = _connection.createStatement();

        ResultSet userData = userSelect.executeQuery(query);
        userData.next();
        return new User(userData);
    }
    
    //Schreibt die �bergebenen neuen Daten zum User in die Datenbank
    public static boolean UpdateUser(int userId, String UserName, String FirstName, String LastName) throws SQLException
    {
        String query = GetUpdateUserQuery(userId, UserName, FirstName, LastName);

        Statement UserUpdate = _connection.createStatement();

        //executeUpdate returned die Anzahl der geupdateten datensaetze
        //Sollte also immer 1 sein wenn ein user geupdated wird
        //Wenn es 0 ist existiert der user nicht
        return UserUpdate.executeUpdate(query) == 1;

    }
    
    //�ndert das Passwort des Users auf das neue gew�hlte Passwort
    public static boolean ChangePassword(int userId, String currentPassword, String newPassword) throws SQLException
    {
    	User currentUser = GetUser(userId);
    	
    	if(Controller.LoginUser(currentUser.userName, currentPassword) != null)
    	{
    		String newPasswordHash = GetPasswordHash(newPassword);
    		Statement updatePasswordStatement = _connection.createStatement();
    		
    		return updatePasswordStatement.executeUpdate(GetChangePasswordQuery(currentUser.id, newPasswordHash)) == 1;
    	
    	}
    	else
    	{
    		return false;
    		
    	}
    	
    	
    }
    
    //Gibt die Query zum setzen eines neuen Userpassworts zur�ck
    private static String GetChangePasswordQuery(int userId, String newPasswordHash)
    {
    	return String.format("update users set passwordhash = \'%s\' where id = \'%s\';", newPasswordHash, userId);
    	
    }

    
    //Passwort vergessen funktion
    //Setzt dsa Passwort auf ein neues, zuf�lliges
    public static boolean ResetUserPassword(User user) throws SQLException, MessagingException
    {
        return resetUserPassword(user.id, user.userName);
        
    }

    //Passwort vergessen funktion
    //Setzt dsa Passwort auf ein neues, zuf�lliges
    public static boolean ResetUserPassword(int userId, String userName) throws SQLException, MessagingException
    {
        return resetUserPassword(userId, userName);

    }
    
    //Passwort vergessen funktion
    //Setzt dsa Passwort auf ein neues, zuf�lliges
    private static boolean resetUserPassword(int UserId, String userName) throws SQLException, MessagingException
    {
    	 String newPassword = getRandomPassword(8);
         String passwordHash = GetPasswordHash(newPassword);

         String query = GetUpdateUserPasswordQuery(UserId, passwordHash);

         Statement UpdateUserPasword = _connection.createStatement();

         //Email mit "Ihr passwort wurde auf xxx ge�ndert" senden
         return UpdateUserPasword.executeUpdate(query) == 1;
         
    }
    
    //Gibt die Query zum Updaten des Passworts eines Users zur�ck
    private static String GetUpdateUserPasswordQuery(int UserId, String passwordHash)
    {
        return String.format("update users set passwordhash = \'%s\' where id = \'%s\';", UserId, passwordHash);

    }

    //Generiert ein zuf�lliges Passwort der �bergebenen l�nge
    private static String getRandomPassword(int length)
    {
        String result = "";
        Random r = new Random();
        for(int i =0; i < length; i++)
        {
            result = result + r.nextInt(10);

        }

        return result;
    }
    
    
    //Gibt eine Query zum updaten des gegebenen Users mit den entsprechenden Werten zur�ck
    private static String GetUpdateUserQuery(int userId, String UserName, String firstName, String LastName)
    {
        return String.format("update users set loginemailaddress = \'%s\', firstname = \'%s\', lastname = \'%s\' where " +
                "id = \'%s\';", UserName, firstName, LastName, userId);
    }
    
    //Pr�ft ob das gegebene Passwort eines Users mti dem in der Db �bereinstimmt
    private static boolean VerifyPassword(String inputPassword, String realPasswordHash)
    {
        return BCrypt.verifyer().verify(inputPassword.getBytes(StandardCharsets.UTF_8),
                realPasswordHash.getBytes(StandardCharsets.UTF_8)).verified;
    }

    //Gibt eine Query zum lesen des Passworthashes eines Users zur�ck
    private static String GetAuthenticationQuery(String loginEmailAddress)
    {
        return String.format("select * from users where loginEmailAddress = \'%s\';", loginEmailAddress);
    }

    //Gibt eine Query zur�ck die zum Authentifizieren verwendet wird
    private static String GetAuthenticationQuery(int userID)
    {
        return String.format("select * from users where id = \'%s\';", userID);
    }

    
    //Pr�ft ob zur gegebenen Email / Username ein user existiert
    private static boolean UserExists(String loginEmailAddress) throws SQLException
    {
        Statement userExists = _connection.createStatement();
       
        userExists.execute(String.format("select id from users where loginEmailAddress = \'%s\';", loginEmailAddress));
        
        
        return userExists.getResultSet().next();
    }

    
    //Legt einen neuen user mit den gegebenen Daten an
    //Die UserID wird von der DB automatisch vergeben
    public static User InsertUser(String firstName, String lastName, String loginEmailAddress, String password) throws SQLException
    {
        if (UserExists(loginEmailAddress))
        {
            return null;
        }

        String passwordHash = GetPasswordHash(password);
        String query = GetInsertUserQuery(firstName, lastName, loginEmailAddress, passwordHash);

        //Logger.DebugLog("Query: " + query);

        Statement insertUser = _connection.createStatement();
        ResultSet insertResult = insertUser.executeQuery(query);
        insertResult.next();
        return new User(insertResult.getInt(1), firstName, lastName, loginEmailAddress, passwordHash);
    }
    
    
    //L�scht das �bergebene Inserat und die Zuordnung
    public static boolean DeleteInserat(int inseratId) throws SQLException
    {
    	
    	Statement deleteInserat = _connection.createStatement();
    	deleteInserat.executeUpdate(GetDeleteInseratQuery(inseratId));
    	
    	Statement deleteAssignment = _connection.createStatement();
    	deleteAssignment.executeUpdate(GetDeleteInseratAssignmentQuery(inseratId));
    	
    	return true;
    }
    
    //Gibt eine Query zum l�schen eines inserats zur�ck
    private static String GetDeleteInseratQuery(int inseratId)
    {
    	return String.format("delete from inserate where id = %s", inseratId);
    	
    }
    
    //Gibt eine Query zum L�schen der Inseratszuordnung zur�ck
    private static String GetDeleteInseratAssignmentQuery(int inseratId)
    {
    	return String.format("delete from user_inserate where inseratid = %s", inseratId);
    	
    }
   
    //Liest das gegebene Inserat aus
    public static Inserat ReadInserat( int inseratId) throws SQLException
    {
    	Statement readInserat = _connection.createStatement();
    	String query = GetReadInseratQuery(inseratId);
    	
    	ResultSet InseratData = readInserat.executeQuery(query);
    	InseratData.next();
    	
    	return new Inserat(InseratData);
    	
    }
    
    //Generiert die Query zum lesen eines inserats
    public static String GetReadInseratQuery(int inseratId)
    {
    	return String.format("select * from inserate where id = %s", inseratId);
    	
    }
    
    
    
    //Setzt beim �bergebenen Inserat die neuen Werte
    public static boolean UpdateInserat(int id, String beschreibung, String marke, int PS, float verbrauch, int groesse, int kilometerstand, String verbrauchsstoff, String bezeichnung, String Ausstattung ) throws SQLException
    {
    	
    	String query = GetUpdateInseratQuery(id, beschreibung, marke, PS, verbrauch, groesse, kilometerstand, verbrauchsstoff, bezeichnung, Ausstattung);
    	Statement updateInserat = _connection.createStatement();
    	
    	return updateInserat.executeUpdate(query) == 1;
    }
    
    //Gibt eine Query zum Anpassen eines Inserats zur�ck
    private static String GetUpdateInseratQuery(int id, String beschreibung, String marke, int PS, float verbrauch, int groesse, int kilometerstand, String verbrauchsstoff, String bezeichnung, String Ausstattung)
    {
    	return String.format("update inserate set beschreibung = \'%s\', marke = \'%s\', PS = \'%s\', verbrauch = \'%s\', groesse = \'%s\', kilometerstand = \'%s\', verbrauchsstoff = \'%s\', "
    			+ "bezeichnung = \'%s\', Ausstattung = \'%s\' where id = %s ",beschreibung, marke, PS, verbrauch, groesse, kilometerstand, verbrauchsstoff, bezeichnung, Ausstattung, id);
    	
    }
    	
    
    //Gibt die Query zum lesen aller Inserate eines Users zur�ck
    public static ArrayList<Inserat> ReadInserateForUser(User user) throws SQLException
    {
        return ReadInserateForUser(user.id);

    }

    //Liest alle Inserate f�r den gegebenen user und gibt sie als ArrayList zur�ck
    public static ArrayList<Inserat> ReadInserateForUser(int UserId) throws SQLException
    {
        String query = GetReadInserateForUserQuery(UserId);

        Statement ReadInseratForUser = _connection.createStatement();
        ResultSet inserate = ReadInseratForUser.executeQuery(query);

        ArrayList<Inserat> returnList = new ArrayList<>();
        while(inserate.next())
        {
            returnList.add(new Inserat(inserate));
        }

        return returnList;
    }

    //F�gt ein neues Inserat in die Datenbank ein (Tabelle inserate) und ordnet es dem user zu (Tabelle user_inserate)
    public static Inserat InsertInserat(String beschreibung, String marke, int PS, float verbrauch, int groesse, int kilometerstand,
                                        String verbrauchsstoff, String bezeichnung, String ausstattung, int userId) throws SQLException
    {
    	//Inserat in tabelle einf�gen
        String query = GetInsertInseratQuery(beschreibung, marke, PS, verbrauch, groesse, kilometerstand, verbrauchsstoff,
                bezeichnung, ausstattung);

        Statement insertInserat = _connection.createStatement();
        ResultSet insertResult = insertInserat.executeQuery(query);
        insertResult.next();
        
        int newId = insertResult.getInt(1);
        
        Inserat newInserat = new Inserat(newId, beschreibung, marke, PS, verbrauch, groesse, kilometerstand, verbrauchsstoff, bezeichnung, ausstattung);
        
        //Inserat user zuordnen
        String zuordnenQuery = GetAssignInseratToUserQuery(newInserat.id, userId);
        

        Statement insertZuordnung = _connection.createStatement();
        ResultSet zuordnungResult = insertZuordnung.executeQuery(zuordnenQuery);
        
        return newInserat;

    }
    
    //Gibt die Daten eines Inserats anhand seiner ID zuruck
    private static ResultSet GetInseratData(int inseratId) throws SQLException
    {
    	Statement s = _connection.createStatement();
    	return s.executeQuery(GetInseratDataQuery(inseratId));
    	
    }
    
    //Generiert die Query zum lesen der Daten eines internats
    private static String GetInseratDataQuery(int inseratId)
    {
    	return String.format("select * from inserate where id = \'%s\' returning id;", inseratId);
    	
    }
    
    //Generiert die query zum zuordnen des inserats zum user
    private static String GetAssignInseratToUserQuery(int inseratId, int userId)
    {
    	//insert into user_inserate( userid, inseratid ) values (1, 3);
    	return String.format("insert into user_inserate( userid, inseratid ) values (%s, %s) returning userid;", userId, inseratId);
    }

    //Passwort hash aus einem Klartext passwort generieren (Wird gesalted)
    //Bcrypt wei� sp�ter selbst wie es den Salt wieder rausfischt
    private static String GetPasswordHash(String password)
    {
        return new String(BCrypt.withDefaults().hash(10, Bytes.random(16).array(), password.getBytes(StandardCharsets.UTF_8)),
                StandardCharsets.UTF_8);
    }

    
    //Gibt eine Query zum Anlegen eines Users auf der DB zur�ck
    private static String GetInsertUserQuery(String firstName, String lastName, String loginEmailAddress, String passwordHash)
    {
        return String.format("insert into users (firstName, lastName, loginEmailAddress, " +
                        "passwordHash) values (\'%s\', \'%s\', \'%s\', \'%s\') returning " +
                        "id;",
                firstName, lastName, loginEmailAddress, passwordHash);
    }

    //Gibt eine Query zum Anlegen eines Inserats auf der DB zur�ck
    private static String GetInsertInseratQuery(String beschreibung, String marke, int PS, float verbrauch, int groesse,
                                                int kilometerstand, String Verbrauchsstoff, String Bezeichnung, String Ausstattung)
    {
        return String.format("insert into inserate (Beschreibung, marke, PS, verbrauch, groesse, kilometerstand," +
                " Verbrauchsstoff, Bezeichnung, Ausstattung) values (\'%s\', \'%s\', \'%s\', \'%s\', \'%s\', \'%s\', \'%s\', \'%s\'" +
                        ", \'%s\') returning id;",
                beschreibung, marke, PS, verbrauch,groesse, kilometerstand, Verbrauchsstoff, Bezeichnung, Ausstattung);
    }
    
    
    //Gibt eine Query zum lesen aller Inserate zu einem user zur�ck
    private static String GetReadInserateForUserQuery(int UserId)
    {
        return String.format("select inserate.* from user_inserate join inserate on inserate.id = user_inserate.inseratid " +
                "join users on users.id = user_inserate.userid where user_inserate.userid = %s;", UserId);
    }

}


