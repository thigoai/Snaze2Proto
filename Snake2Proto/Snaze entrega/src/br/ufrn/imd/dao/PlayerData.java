package br.ufrn.imd.dao;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import br.ufrn.imd.exception.PlayerDataException;

public class PlayerData {
    private String playerName;
    private int playerScore;
    
    public PlayerData() {
        this.playerName = "";
        this.playerScore = 0;
    }
    
    public PlayerData(String playerName, int playerScore) {
        this.playerName = playerName;
        this.playerScore = playerScore;
    }
    
    public List<PlayerData> loadAllPlayers(String filePath) throws PlayerDataException {
        List<PlayerData> players = new ArrayList<>();
        
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length != 2) {
                    throw new PlayerDataException("Formato inválido de dados: " + line);
                }
                try {
                    String name = data[0];
                    int score = Integer.parseInt(data[1]);
                    if (name.trim().isEmpty()) {
                        throw new PlayerDataException("Nome do jogador não pode ser vazio");
                    }
                    players.add(new PlayerData(name, score));
                } catch (NumberFormatException e) {
                    throw new PlayerDataException("Pontuação inválida: " + data[1]);
                }
            }
        } catch (IOException e) {
            throw new PlayerDataException("Erro ao ler arquivo: " + e.getMessage());
        }
        
        return players;
    }
    
    public void savePlayerData(String filePath) throws PlayerDataException {
        if (playerName == null || playerName.trim().isEmpty()) {
            throw new PlayerDataException("Nome do jogador não pode ser vazio");
        }
        
        try {
            List<PlayerData> players = loadAllPlayers(filePath);
            boolean playerExists = false;
            
            for (int i = 0; i < players.size(); i++) {
                if (players.get(i).getPlayerName().equals(this.playerName)) {
                    if (this.playerScore > players.get(i).getPlayerScore()) {
                        players.set(i, this);
                    }
                    playerExists = true;
                    break;
                }
            }
            
            if (!playerExists) {
                players.add(this);
            }
            
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
                for (PlayerData player : players) {
                    writer.write(player.getPlayerName() + "," + player.getPlayerScore());
                    writer.newLine();
                }
            }
            
        } catch (IOException e) {
            throw new PlayerDataException("Erro ao salvar arquivo: " + e.getMessage());
        }
    }
    
    public void updateScore(int newScore, String filePath) throws PlayerDataException {
        if (newScore > this.playerScore) {
            this.playerScore = newScore;
            savePlayerData(filePath);
        }
    }
    
    
    public String getPlayerName() {
        return playerName;
    }
    
    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }
    
    public int getPlayerScore() {
        return playerScore;
    }
    
    public void setPlayerScore(int playerScore) {
        this.playerScore = playerScore;
    }
}
