package com.loginmod.data;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.mindrot.jbcrypt.BCrypt;

import java.io.*;
import java.util.*;

public class PlayerData {
    public static final PlayerData INSTANCE = new PlayerData();
    
    private Map<UUID, String> playerPasswords = new HashMap<>();
    private Set<UUID> loggedInPlayers = new HashSet<>();
    
    public void savePassword(UUID player, String password) {
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
        playerPasswords.put(player, hashedPassword);
        saveToFile();
    }
    
    public boolean checkPassword(UUID player, String password) {
        String hashedPassword = playerPasswords.get(player);
        return hashedPassword != null && BCrypt.checkpw(password, hashedPassword);
    }
    
    public void setLoggedIn(UUID player) {
        loggedInPlayers.add(player);
    }
    
    public void setLoggedOut(UUID player) {
        loggedInPlayers.remove(player);
    }
    
    public boolean isLoggedIn(UUID player) {
        return loggedInPlayers.contains(player);
    }
    
    public boolean hasPassword(UUID player) {
        return playerPasswords.containsKey(player);
    }
    
    public void removePassword(UUID player) {
        playerPasswords.remove(player);
        loggedInPlayers.remove(player);
        saveToFile();
    }
    
    private void saveToFile() {
        try {
            File file = new File("player_passwords.dat");
            JsonObject json = new JsonObject();
            playerPasswords.forEach((uuid, hash) -> json.addProperty(uuid.toString(), hash));
            
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
                playerPasswords.put(UUID.fromString(entry.getKey()), entry.getValue().getAsString());
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
