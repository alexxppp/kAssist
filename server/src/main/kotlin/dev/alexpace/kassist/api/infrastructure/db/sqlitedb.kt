package dev.alexpace.kassist.api.infrastructure.db

import java.sql.DriverManager
import java.sql.SQLException

class sqlitedb {

    val dbPath = "jdbc:sqlite:sample.db"

    fun AccessDb() {
        try {
            DriverManager.getConnection(dbPath).use { connection ->
                println("Connected to SQLite database")

                // Create a table
                val statement = connection.createStatement()
                statement.executeUpdate(
                    """
                        CREATE TABLE IF NOT EXISTS users (
                            id INTEGER PRIMARY KEY AUTOINCREMENT,
                            name TEXT NOT NULL,
                            age INTEGER
                        )
                    """.trimIndent()
                )

                // Insert data
                statement.executeUpdate("INSERT INTO users (name, age) VALUES ('Alice', 30)")
                statement.executeUpdate("INSERT INTO users (name, age) VALUES ('Bob', 25)")

                // Query data
                val resultSet = statement.executeQuery("SELECT * FROM users")
                while (resultSet.next()) {
                    val id = resultSet.getInt("id")
                    val name = resultSet.getString("name")
                    val age = resultSet.getInt("age")
                    println("User: ID=$id, Name=$name, Age=$age")
                }
            }
        } catch (e: SQLException) {
            println("Database error: ${e.message}")
        }
    }
}