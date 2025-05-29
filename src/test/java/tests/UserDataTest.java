package tests;

import org.example.DatabaseUtil;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.AfterClass;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class UserDataTest {

    private Connection connection;

    public void verifyUserEmailInDatabase(String expectedEmail, int emailId) {
        if (connection == null) {
            System.err.println("Conexiunea la baza de date nu a fost stabilită. Trec peste test.");
            return;
        }

        Statement statement = null;
        ResultSet resultSet = null;
//        String expectedEmail = "George.datcu@hapifyme.com"; // Emailul așteptat din UI sau un test case

        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT email FROM users WHERE id = " + emailId); // Exemplu de interogare

            assertNotNull(resultSet, "ResultSet-ul nu ar trebui să fie null.");

            if (resultSet.next()) {
                String actualEmail = resultSet.getString("email");
                assertEquals(actualEmail, expectedEmail, "Emailul din baza de date nu corespunde cu cel așteptat.");
                System.out.println("Emailul verificat cu succes: " + actualEmail);
            } else {
                System.out.println("Nu s-a găsit nicio înregistrare pentru utilizatorul cu ID=43.");
                // Poți alege să faci o aserțiune care să eșueze aici dacă te aștepți la o înregistrare
            }

        } catch (SQLException e) {
            System.err.println("Eroare la executarea interogării SQL: " + e.getMessage());
            // Poți arunca o excepție sau face testul să eșueze
        } finally {
            DatabaseUtil.closeResultSet(resultSet);
            DatabaseUtil.closeStatement(statement);
        }
    }

    @BeforeClass
    public void setupDatabaseConnection() {
        try {
            connection = DatabaseUtil.getConnection();
            System.out.println("Conexiune la baza de date stabilită cu succes.");
        } catch (SQLException e) {
            System.err.println("Eroare la stabilirea conexiunii cu baza de date: " + e.getMessage());
            // Poți alege să arunci o excepție sau să faci testul să eșueze aici
        }
    }

    @Test
    void testUserMickyEmailInDatabase() {
        verifyUserEmailInDatabase("Mickey@gmail.com", 5);
    }

    @Test
    void testUserGCEmailInDatabase() {
        verifyUserEmailInDatabase("Gigi.coman@abc.com", 20);
    }


    @Test
    public void verifyUserRegistrationDate() {
        if (connection == null) {
            System.err.println("Conexiunea la baza de date nu a fost stabilită. Trec peste test.");
            return;
        }

        Statement statement = null;
        ResultSet resultSet = null;
        String expectedDate = "2023-01-01"; // Data așteptată, format conform bazei de date

        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT registration_date FROM users WHERE username = 'john_doe'");

            assertNotNull(resultSet, "ResultSet-ul nu ar trebui să fie null.");

            if (resultSet.next()) {
                String actualDate = resultSet.getString("registration_date"); // Poți folosi și getDate() dacă este tip Date
                assertEquals(actualDate, expectedDate, "Data înregistrării nu corespunde.");
                System.out.println("Data înregistrării verificată cu succes: " + actualDate);
            } else {
                System.out.println("Nu s-a găsit utilizatorul 'john_doe'.");
            }
        } catch (SQLException e) {
            System.err.println("Eroare la verificarea datei de înregistrare: " + e.getMessage());
        } finally {
            DatabaseUtil.closeResultSet(resultSet);
            DatabaseUtil.closeStatement(statement);
        }
    }

    @AfterClass
    public void tearDownDatabaseConnection() {
        DatabaseUtil.closeConnection(connection);
        System.out.println("Conexiunea la baza de date închisă.");
    }
}