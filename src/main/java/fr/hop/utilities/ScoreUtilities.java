package fr.hop.utilities;

import java.io.*;
import java.util.Optional;

public class ScoreUtilities {

    private static final String SCORE_FILE_PATH = "hop_best_score.dat";
    private static Integer cachedBestScore = null; // Cache pour éviter des lectures répétées

    // Enregistre le meilleur score dans un fichier. Si le score passé est inférieur
    // au meilleur score actuel, il n'est pas enregistré.
    public static void saveBestScore(int score) {
        int currentBest = getBestScore();

        if (score > currentBest) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(SCORE_FILE_PATH))) {
                writer.write(String.valueOf(score));
                cachedBestScore = score; // Mettre à jour le cache
            } catch (IOException e) {
                System.err.println("ERREUR: Impossible d'enregistrer le meilleur score.");
                e.printStackTrace();
            }
        }
    }

    // Récupère le meilleur score à partir du fichier. Utilise un cache pour optimiser les performances.
    public static int getBestScore() {
        if (cachedBestScore != null) {
            return cachedBestScore;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(SCORE_FILE_PATH))) {
            String scoreString = reader.readLine();
            cachedBestScore = Optional.ofNullable(scoreString).map(Integer::parseInt).orElse(0);
        } catch (FileNotFoundException e) {
            cachedBestScore = 0;
        } catch (IOException | NumberFormatException e) {
            System.err.println("ERREUR: Impossible de lire le meilleur score.");
            e.printStackTrace();
            cachedBestScore = 0;
        }

        return cachedBestScore;
    }
}

