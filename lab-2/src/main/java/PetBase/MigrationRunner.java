package PetBase;

import org.flywaydb.core.Flyway;

public class MigrationRunner {
    public static void main(String[] args) {
        Flyway flyway = Flyway.configure()
                .dataSource("jdbc:postgresql://localhost:5432/lab2_db", "postgres", "stacia")
                .load();

        flyway.migrate();

        System.out.println("Миграция выполнена");
    }
}
