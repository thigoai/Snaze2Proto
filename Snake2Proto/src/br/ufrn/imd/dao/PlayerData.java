package br.ufrn.imd.dao;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

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
    
    public List<PlayerData> loadAllPlayers(String filePath) {
        List<PlayerData> players = new ArrayList<>();
        
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length == 2) {
                    String name = data[0];
                    int score = Integer.parseInt(data[1]);
                    players.add(new PlayerData(name, score));
                }
            }
        } catch (IOException e) {
            System.out.println("Erro ao carregar jogadores: " + e.getMessage());
        }
        
        return players;
    }
    
    public void savePlayerData(String filePath) {
        try {
            // Primeiro carrega todos os jogadores existentes
            List<PlayerData> players = loadAllPlayers(filePath);
            
            // Verifica se o jogador já existe
            boolean playerExists = false;
            for (int i = 0; i < players.size(); i++) {
                if (players.get(i).getPlayerName().equals(this.playerName)) {
                    // Atualiza a pontuação se for maior
                    if (this.playerScore > players.get(i).getPlayerScore()) {
                        players.set(i, this);
                    }
                    playerExists = true;
                    break;
                }
            }
            
            // Se o jogador não existe, adiciona à lista
            if (!playerExists) {
                players.add(this);
            }
            
            // Salva todos os jogadores de volta no arquivo
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
                for (PlayerData player : players) {
                    writer.write(player.getPlayerName() + "," + player.getPlayerScore());
                    writer.newLine();
                }
            }
            
        } catch (IOException e) {
            System.out.println("Erro ao salvar dados do jogador: " + e.getMessage());
        }
    }
    
    public void updateScore(int newScore, String filePath) {
        if (newScore > this.playerScore) {
            this.playerScore = newScore;
            savePlayerData(filePath);
        }
    }
    
    // Getters e Setters
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
