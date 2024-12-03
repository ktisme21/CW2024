package com.example.demo.manager;

import java.io.*;
import java.util.*;

/**
 * Manages the leaderboard for storing and displaying player scores.
 * Handles reading and writing leaderboard data to a file.
 */
public class LeaderboardManager {

    private static final String FILE_PATH = "C:\\Users\\Kei\\CW2024\\src\\main\\java\\com\\example\\demo\\data\\leaderboard.txt";
    private static final int MAX_ENTRIES = 5;
    private final List<LeaderboardEntry> leaderboard = new ArrayList<>();

    /**
     * Initializes the LeaderboardManager, loading data from the file if it exists.
     * Creates the directory for the file if it doesn't exist.
     */
    public LeaderboardManager() {
        ensureDirectoryExists();
        loadLeaderboard();
    }

    /**
     * Adds a new score to the leaderboard and updates the file.
     *
     * @param playerName the name of the player.
     * @param score      the player's score (in milliseconds).
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
     * @return a list of the top leaderboard entries.
     */
    public List<LeaderboardEntry> getTopEntries() {
        return new ArrayList<>(leaderboard);
    }

    /**
     * Prints all entries in the leaderboard to the console.
     */
    public void printAllEntries() {
        System.out.println("Leaderboard Scores:");
        for (LeaderboardEntry entry : leaderboard) {
            System.out.println("Player: " + entry.getPlayerName() + ", Time: " + formatTime(entry.getScore()));
        }
    }

    /**
     * Formats the score from milliseconds to a string in MM:SS:MS format.
     *
     * @param score the score in milliseconds.
     * @return a formatted string representing the score.
     */
    private String formatTime(int score) {
        int minutes = score / 60000;
        int seconds = (score / 1000) % 60;
        int millis = score % 1000;
        return String.format("%02d:%02d:%03d", minutes, seconds, millis);
    }

    /**
     * Ensures the directory for the leaderboard file exists.
     */
    private void ensureDirectoryExists() {
        File file = new File(FILE_PATH);
        File directory = file.getParentFile();
        if (!directory.exists()) {
            directory.mkdirs();
        }
    }

    /**
     * Loads the leaderboard data from the file.
     */
    private void loadLeaderboard() {
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
     * Saves the current leaderboard data to the file.
     */
    private void saveLeaderboard() {
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
     * Represents an entry in the leaderboard.
     */
    public static class LeaderboardEntry {
        private final String playerName;
        private final int score;

        /**
         * Initializes a LeaderboardEntry.
         *
         * @param playerName the name of the player.
         * @param score      the player's score.
         */
        public LeaderboardEntry(String playerName, int score) {
            this.playerName = playerName;
            this.score = score;
        }

        /**
         * Gets the player's name.
         *
         * @return the player's name.
         */
        public String getPlayerName() {
            return playerName;
        }

        /**
         * Gets the player's score.
         *
         * @return the player's score.
         */
        public int getScore() {
            return score;
        }
    }
}
