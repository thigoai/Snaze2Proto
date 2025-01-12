package br.ufrn.imd.modelo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LevelLoader {
    public static int[][] loadLevel(String filename) {
        // Tenta diferentes caminhos para encontrar o arquivo
        String[] possiveisCaminhos = {
            filename,
            "resources/" + filename,
            "./resources/" + filename,
            "../resources/" + filename,
            "src/resources/" + filename,
            "./src/resources/" + filename
        };

        for (String caminho : possiveisCaminhos) {
            try {
                // Primeiro tenta como recurso do ClassLoader
                InputStream is = LevelLoader.class.getClassLoader().getResourceAsStream(caminho);
                if (is != null) {
                    return carregarArquivo(new BufferedReader(new InputStreamReader(is)));
                }

                // Se não encontrou como recurso, tenta como arquivo
                File file = new File(caminho);
                if (file.exists()) {
                    return carregarArquivo(new BufferedReader(new FileReader(file)));
                }
            } catch (IOException e) {
                System.out.println("Tentativa falhou para caminho: " + caminho);
            }
        }

        System.out.println("Não foi possível encontrar o arquivo em nenhum caminho tentado");
        return new int[23][24]; // Retorna matriz vazia como fallback
    }

    private static int[][] carregarArquivo(BufferedReader reader) throws IOException {
        List<String> lines = new ArrayList<>();
        String line;
        
        // Lê todas as linhas do arquivo
        while ((line = reader.readLine()) != null) {
            lines.add(line);
        }
        reader.close();
        
        if (lines.isEmpty()) {
            System.out.println("Arquivo vazio");
            return new int[23][24];
        }
        
        // Cria a matriz com as dimensões do arquivo
        int rows = lines.size();
        int cols = lines.get(0).length();
        int[][] layout = new int[rows][cols];
        
        // Converte cada caractere para número na matriz
        for (int i = 0; i < rows; i++) {
            line = lines.get(i);
            for (int j = 0; j < cols && j < line.length(); j++) {
                layout[i][j] = Character.getNumericValue(line.charAt(j));
            }
        }
        
        return layout;
    }
}