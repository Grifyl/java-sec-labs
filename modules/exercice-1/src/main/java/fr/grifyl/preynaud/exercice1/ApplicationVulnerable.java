package fr.grifyl.preynaud.exercice1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class ApplicationVulnerable {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            // Initialisation de la base de données H2 avec le script schema.sql
            Connection initConn = DriverManager.getConnection("jdbc:h2:mem:yourdb;DB_CLOSE_DELAY=-1", "sa", "");
            Statement initStmt = initConn.createStatement();
            initStmt.execute("RUNSCRIPT FROM 'classpath:schema.sql'");
            initStmt.close();
            initConn.close();

            System.out.println("Entrez votre nom d'utilisateur :");
            String username = scanner.nextLine();

            System.out.println("Entrez votre mot de passe :");
            String password = scanner.nextLine();

            // Requête vulnérable à l'injection SQL
            String query = "SELECT * FROM users WHERE username = '" + username + "' AND password = '" + password + "'";

            Connection conn = DriverManager.getConnection("jdbc:h2:mem:yourdb;DB_CLOSE_DELAY=-1", "sa", "");
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            if (rs.next()) {
                System.out.println("Authentification réussie. Bienvenue, " + rs.getString("username") + " !");
            } else {
                System.out.println("Échec de l'authentification.");
            }

            rs.close();
            stmt.close();
            conn.close();
        } catch (E:wxception e) {
            e.printStackTrace();
        }
    }
}

