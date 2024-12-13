package com.example.demo.manager;

import java.io.*;
import java.util.*;

/**
 * Manages the leaderboard for storing and displaying player scores.
 * Handles reading and writing leaderboard data to a file.
 */
public class LeaderboardManager {

    /** Path to the file where leaderboard data is stored. */
    private static final String FILE_PATH = "C:\\Users\\Kei\\CW2024\\src\\main\\java\\com\\example\\demo\\data\\leaderboard.txt";

    /** Maximum number of entries allowed on the leaderboard. */
    private static final int MAX_ENTRIES = 5;

    /** List storing the leaderboard entries. */
    private final List<LeaderboardEntry> leaderboard = new ArrayList<>();

    /**
     * Constructor for the {@code LeaderboardManager}.
     * Ensures the directory for the file exists and loads the leaderboard data from the file.
     */
    public LeaderboardManager() {
        ensureDirectoryExists();
        loadLeaderboard();
    }

    /**
     * Saves the current leaderboard data to the file.
     * Ensures the file contains the most recent leaderboard entries.
     */
    protected void saveLeaderboard() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (LeaderboardEntry entry : leaderboard) {
                writer.write(entry.getPlayerName() + "," + entry.getScore());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Loads the leaderboard data from the file.
     * If the file does not exist, a new empty leaderboard is created.
     */
    protected void loadLeaderboard() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                leaderboard.add(new LeaderboardEntry(parts[0], Integer.parseInt(parts[1])));
            }
            leaderboard.sort(Comparator.comparingInt(LeaderboardEntry::getScore));
        } catch (IOException e) {
            System.out.println("No existing leaderboard found. Creating a new one.");
        }
    }

    /**
     * Adds a new entry to the leaderboard.
     * Automatically sorts entries by score in ascending order and ensures
     * the leaderboard does not exceed the maximum number of entries.
     *
     * @param playerName The name of the player.
     * @param score The player's score in milliseconds.
     */
    public void addEntry(String playerName, int score) {
        leaderboard.add(new LeaderboardEntry(playerName, score));
        leaderboard.sort(Comparator.comparingInt(LeaderboardEntry::getScore));
        if (leaderboard.size() > MAX_ENTRIES) {
            leaderboard.remove(leaderboard.size() - 1);
        }
        saveLeaderboard();
    }

    /**
     * Retrieves the top entries from the leaderboard.
     *
     * @return A list of the top leaderboard entries.
     */
    public List<LeaderboardEntry> getTopEntries() {
        return new ArrayList<>(leaderboard);
    }

    /**
     * Ensures the directory for the leaderboard file exists.
     * Creates the directory if it does not already exist.
     */
    private void ensureDirectoryExists() {
        File file = new File(FILE_PATH);
        File directory = file.getParentFile();
        if (!directory.exists()) {
            directory.mkdirs();
        }
    }

    /**
     * Represents an entry in the leaderboard.
     */
    public static class LeaderboardEntry {
        /** The name of the player. */
        private final String playerName;

        /** The player's score in milliseconds. */
        private final int score;

        /**
         * Constructor for a {@code LeaderboardEntry}.
         *
         * @param playerName The name of the player.
         * @param score The player's score in milliseconds.
         */
        public LeaderboardEntry(String playerName, int score) {
            this.playerName = playerName;
            this.score = score;
        }

        /**
         * Retrieves the player's name.
         *
         * @return The player's name.
         */
        public String getPlayerName() {
            return playerName;
        }

        /**
         * Retrieves the player's score.
         *
         * @return The player's score in milliseconds.
         */
        public int getScore() {
            return score;
        }
    }
}
