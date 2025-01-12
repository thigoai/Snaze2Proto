package br.ufrn.imd.dao;

import java.io.*;

public class PlayerData {

    private String playerName;
    private int playerScore;

    // Método para salvar os dados do jogador
    public void savePlayerData(String fileName) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName))) {
            oos.writeObject(this);  // Serializa o objeto PlayerData
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Método para carregar os dados do jogador, agora recebendo o nome do arquivo
    public void loadPlayerData(String fileName) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName))) {
            PlayerData loadedPlayer = (PlayerData) ois.readObject();  // Desserializa o objeto PlayerData
            this.playerName = loadedPlayer.playerName;
            this.playerScore = loadedPlayer.playerScore;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
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

    // Construtor
    public PlayerData(String playerName, int playerScore) {
        this.playerName = playerName;
        this.playerScore = playerScore;
    }

    // Construtor sem parâmetros (caso necessário para inicialização sem dados)
    public PlayerData() {
    }
}
