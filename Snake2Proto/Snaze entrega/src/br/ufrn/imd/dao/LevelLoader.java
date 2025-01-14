package br.ufrn.imd.dao;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import br.ufrn.imd.exception.LevelLoadException;

public class LevelLoader {
    public static int[][] loadLevel(String filePath) throws LevelLoadException {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            List<String> lines = new ArrayList<>();
            String line;
            
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    throw new LevelLoadException("Linha vazia encontrada no arquivo");
                }
                lines.add(line);
            }
            
            if (lines.isEmpty()) {
                throw new LevelLoadException("Arquivo de nível vazio");
            }
            
            int height = lines.size();
            int width = lines.get(0).length();
            int[][] layout = new int[height][width];
            
            for (int i = 0; i < height; i++) {
                if (lines.get(i).length() != width) {
                    throw new LevelLoadException("Largura inconsistente na linha " + (i + 1));
                }
                
                for (int j = 0; j < width; j++) {
                    char c = lines.get(i).charAt(j);
                    if (c != '0' && c != '1') {
                        throw new LevelLoadException("Caractere inválido encontrado: " + c);
                    }
                    layout[i][j] = c - '0';
                }
            }
            
            return layout;
        } catch (IOException e) {
            throw new LevelLoadException("Erro ao ler arquivo: " + e.getMessage());
        }
    }
}