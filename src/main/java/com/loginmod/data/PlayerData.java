package com.loginmod.data;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.mindrot.jbcrypt.BCrypt;

import java.io.*;
import java.util.*;

public class PlayerData {
    public static final PlayerData INSTANCE = new PlayerData();
    
    private Map<String, String> playerPasswords = new HashMap<>();
    private Set<String> loggedInPlayers = new HashSet<>();
    
    public void savePassword(String playerName, String password) {
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
        playerPasswords.put(playerName.toLowerCase(), hashedPassword);
        saveToFile();
    }
    
    public boolean checkPassword(String playerName, String password) {
        String hashedPassword = playerPasswords.get(playerName.toLowerCase());
        return hashedPassword != null && BCrypt.checkpw(password, hashedPassword);
    }
    
    public void setLoggedIn(String playerName) {
        loggedInPlayers.add(playerName.toLowerCase());
    }
    
    public void setLoggedOut(String playerName) {
        loggedInPlayers.remove(playerName.toLowerCase());
    }
    
    public boolean isLoggedIn(String playerName) {
        return loggedInPlayers.contains(playerName.toLowerCase());
    }
    
    public boolean hasPassword(String playerName) {
        return playerPasswords.containsKey(playerName.toLowerCase());
    }
    
    public void removePassword(String playerName) {
        playerPasswords.remove(playerName.toLowerCase());
        loggedInPlayers.remove(playerName.toLowerCase());
        saveToFile();
    }
    
    private void saveToFile() {
        try {
            File file = new File("player_passwords.dat");
            JsonObject json = new JsonObject();
            playerPasswords.forEach((name, hash) -> json.addProperty(name, hash));
            
            FileWriter writer = new FileWriter(file);
            new Gson().toJson(json, writer);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void loadFromFile() {
        try {
            File file = new File("player_passwords.dat");
            if (!file.exists()) return;
            
            JsonObject json = new Gson().fromJson(new FileReader(file), JsonObject.class);
            json.entrySet().forEach(entry -> {
                playerPasswords.put(entry.getKey(), entry.getValue().getAsString());
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
