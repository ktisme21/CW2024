package com.example.demo.manager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class LeaderboardManagerTest {

    private LeaderboardManager leaderboardManager;
    private static final String TEST_FILE_PATH = "C:\\Users\\Kei\\CW2024\\src\\main\\java\\com\\example\\demo\\data\\test_leaderboard.txt";

    @BeforeEach
    void setUp() {
        // Ensure a fresh instance for each test
        leaderboardManager = new LeaderboardManager() {
            @Override
            protected String getFilePath() {
                return TEST_FILE_PATH; // Override the file path for testing
            }
        };

        // Clear the test file before running tests
        File file = new File(TEST_FILE_PATH);
        if (file.exists()) {
            file.delete();
        }
    }

    @Test
    void testAddEntry() {
        leaderboardManager.addEntry("Player1", 10000); // 10 seconds
        leaderboardManager.addEntry("Player2", 8000);  // 8 seconds
        leaderboardManager.addEntry("Player3", 12000); // 12 seconds

        List<LeaderboardManager.LeaderboardEntry> entries = leaderboardManager.getTopEntries();
        assertEquals(3, entries.size(), "Leaderboard should contain 3 entries.");
        assertEquals("Player2", entries.get(0).getPlayerName(), "Player2 should be first (lowest score).");
        assertEquals(8000, entries.get(0).getScore(), "Player2's score should be 8000.");
    }

    @Test
    void testMaxEntriesLimit() {
        // Add more entries than the limit
        leaderboardManager.addEntry("Player1", 10000);
        leaderboardManager.addEntry("Player2", 9000);
        leaderboardManager.addEntry("Player3", 12000);
        leaderboardManager.addEntry("Player4", 7000);
        leaderboardManager.addEntry("Player5", 11000);
        leaderboardManager.addEntry("Player6", 6000); // Should push out the last entry

        List<LeaderboardManager.LeaderboardEntry> entries = leaderboardManager.getTopEntries();
        assertEquals(5, entries.size(), "Leaderboard should contain 5 entries (limit).");
        assertEquals("Player6", entries.get(0).getPlayerName(), "Player6 should be first (lowest score).");
        assertEquals("Player5", entries.get(4).getPlayerName(), "Player5 should be last in the leaderboard.");
    }

    @Test
    void testSaveAndLoadLeaderboard() {
        leaderboardManager.addEntry("Player1", 10000);
        leaderboardManager.addEntry("Player2", 9000);
        leaderboardManager.addEntry("Player3", 12000);

        // Reload leaderboard from the file
        leaderboardManager = new LeaderboardManager() {
            @Override
            protected String getFilePath() {
                return TEST_FILE_PATH;
            }
        };

        List<LeaderboardManager.LeaderboardEntry> entries = leaderboardManager.getTopEntries();
        assertEquals(3, entries.size(), "Leaderboard should contain 3 entries after reload.");
        assertEquals("Player2", entries.get(0).getPlayerName(), "Player2 should still be first (lowest score).");
    }

    @Test
    void testEmptyLeaderboard() {
        List<LeaderboardManager.LeaderboardEntry> entries = leaderboardManager.getTopEntries();
        assertTrue(entries.isEmpty(), "Leaderboard should be empty initially.");
    }

    @Test
    void testPrintAllEntries() {
        leaderboardManager.addEntry("Player1", 10000);
        leaderboardManager.addEntry("Player2", 9000);

        leaderboardManager.printAllEntries();
    }
}
