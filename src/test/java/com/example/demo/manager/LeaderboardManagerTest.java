package com.example.demo.manager;

import com.example.demo.testutils.JavaFXTestUtils;
import org.junit.jupiter.api.*;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class LeaderboardManagerTest {

    private LeaderboardManager leaderboardManager;
    private static final String TEST_FILE_PATH = "C:\\Users\\Kei\\CW2024\\src\\main\\java\\com\\example\\demo\\data\\test_leaderboard.txt";

    @BeforeAll
    public static void setUp() {
        JavaFXTestUtils.initializeJavaFX(); // Ensures JavaFX is initialized
    }

    @BeforeEach
    void init() {
        leaderboardManager = new LeaderboardManager() {
            @Override
            protected void saveLeaderboard() {
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(TEST_FILE_PATH))) {
                    for (LeaderboardEntry entry : getTopEntries()) {
                        writer.write(entry.getPlayerName() + "," + entry.getScore());
                        writer.newLine();
                    }
                } catch (IOException e) {
                    fail("Failed to save leaderboard during test setup.");
                }
            }

            @Override
            protected void loadLeaderboard() {
                try (BufferedReader reader = new BufferedReader(new FileReader(TEST_FILE_PATH))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        String[] parts = line.split(",");
                        addEntry(parts[0], Integer.parseInt(parts[1]));
                    }
                } catch (IOException e) {
                    System.out.println("No existing leaderboard found. Creating a new one.");
                }
            }
        };
    }

    @AfterEach
    void cleanup() {
        File testFile = new File(TEST_FILE_PATH);
        if (testFile.exists()) {
            testFile.delete();
        }
    }

    @Test
    void testAddEntryDefaultName() {
        leaderboardManager.addEntry("Player", 60000); // 1 minute
        leaderboardManager.addEntry("Player", 120000); // 2 minutes
        leaderboardManager.addEntry("Player", 30000); // 30 seconds

        List<LeaderboardManager.LeaderboardEntry> entries = leaderboardManager.getTopEntries();
        assertEquals(3, entries.size(), "The leaderboard should have 3 entries.");
        assertEquals("Player", entries.get(0).getPlayerName(), "Player should be the default name.");
        assertEquals(30000, entries.get(0).getScore(), "The top entry should have the lowest score.");
    }

    @Test
    void testMaxEntriesWithDefaultName() {
        leaderboardManager.addEntry("Player", 120000);
        leaderboardManager.addEntry("Player", 90000);
        leaderboardManager.addEntry("Player", 60000);
        leaderboardManager.addEntry("Player", 30000);
        leaderboardManager.addEntry("Player", 150000);
        leaderboardManager.addEntry("Player", 180000); // Exceeds max entries

        List<LeaderboardManager.LeaderboardEntry> entries = leaderboardManager.getTopEntries();
        assertEquals(5, entries.size(), "The leaderboard should only keep 5 entries.");
        assertEquals(30000, entries.get(0).getScore(), "The top entry should have the lowest score.");
    }

    @Test
    void testLoadAndSaveLeaderboardWithDefaultName() {
        leaderboardManager.addEntry("Player", 120000);
        leaderboardManager.addEntry("Player", 60000);
        leaderboardManager.saveLeaderboard();

        LeaderboardManager loadedManager = new LeaderboardManager() {
            @Override
            protected void loadLeaderboard() {
                try (BufferedReader reader = new BufferedReader(new FileReader(TEST_FILE_PATH))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        String[] parts = line.split(",");
                        addEntry(parts[0], Integer.parseInt(parts[1]));
                    }
                } catch (IOException e) {
                    fail("Failed to load leaderboard during test.");
                }
            }
        };

        List<LeaderboardManager.LeaderboardEntry> entries = loadedManager.getTopEntries();
        assertEquals(2, entries.size(), "The loaded leaderboard should have 2 entries.");
        assertEquals("Player", entries.get(0).getPlayerName(), "Player should be the default name.");
    }
}
