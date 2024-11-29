package com.example.demo.manager;

import java.io.*;
import java.util.*;

public class LeaderboardManager {

    // Updated file path to save in the specified directory
    private static final String FILE_PATH = "C:\\Users\\Kei\\CW2024\\src\\main\\java\\com\\example\\demo\\data\\leaderboard.txt";
    private static final int MAX_ENTRIES = 5;
    private final List<LeaderboardEntry> leaderboard = new ArrayList<>();

    public LeaderboardManager() {
        ensureDirectoryExists(); // Ensure the directory exists before loading
        loadLeaderboard();
    }

    // Add a new score and update the leaderboard
    public void addEntry(String playerName, int score) {
        leaderboard.add(new LeaderboardEntry(playerName, score));
        leaderboard.sort(Comparator.comparingInt(LeaderboardEntry::getScore)); // Ascending for time
        if (leaderboard.size() > MAX_ENTRIES) {
            leaderboard.remove(leaderboard.size() - 1); // Keep only top 5
        }
        saveLeaderboard();
    }

    // Get the top 5 entries
    public List<LeaderboardEntry> getTopEntries() {
        return new ArrayList<>(leaderboard);
    }

    // Print all leaderboard entries
    public void printAllEntries() {
        System.out.println("Leaderboard Scores:");
        for (LeaderboardEntry entry : leaderboard) {
            System.out.println("Player: " + entry.getPlayerName() + ", Time: " + formatTime(entry.getScore()));
        }
    }

    private String formatTime(int score) {
        int minutes = score / 60000;
        int seconds = (score / 1000) % 60;
        int millis = score % 1000;
        return String.format("%02d:%02d:%03d", minutes, seconds, millis);
    }

    // Ensure the directory exists
    private void ensureDirectoryExists() {
        File file = new File(FILE_PATH);
        File directory = file.getParentFile();
        if (!directory.exists()) {
            directory.mkdirs(); // Create the directory if it doesn't exist
        }
    }

    // Load leaderboard from file
    private void loadLeaderboard() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                leaderboard.add(new LeaderboardEntry(parts[0], Integer.parseInt(parts[1])));
            }
            leaderboard.sort(Comparator.comparingInt(LeaderboardEntry::getScore)); // Sort after loading
        } catch (IOException e) {
            System.out.println("No existing leaderboard found. Creating a new one.");
        }
    }

    // Save leaderboard to file
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

    // Leaderboard entry class
    public static class LeaderboardEntry {
        private final String playerName;
        private final int score;

        public LeaderboardEntry(String playerName, int score) {
            this.playerName = playerName;
            this.score = score;
        }

        public String getPlayerName() {
            return playerName;
        }

        public int getScore() {
            return score;
        }
    }
}