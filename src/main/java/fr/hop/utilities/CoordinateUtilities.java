package fr.hop.utilities;

import java.util.AbstractMap;
import java.util.ArrayDeque;

public class CoordinateUtilities {

    public static ArrayDeque<AbstractMap.SimpleEntry<Integer, Integer>> generateCoordinates(int n, int x0, int y0, int xf, int yf) {
        ArrayDeque<AbstractMap.SimpleEntry<Integer, Integer>> coordinates = new ArrayDeque<>();

        // Calcul des différences entre les coordonnées de départ et d'arrivée
        int dx = xf - x0;
        int dy = yf - y0;

        // Calcul de l'incrément pour chaque frame
        double stepX = dx / (double) n;
        double stepY = dy / (double) n;

        // Générer les coordonnées pour chaque frame
        for (int i = 0; i <= n; i++) {
            // Calcul des coordonnées à la frame i
            int x = (int)(x0 + stepX * i);
            int y = (int)(y0 + stepY * i);

            // Ajouter la paire de coordonnées dans l'ArrayDeque
            coordinates.add(new AbstractMap.SimpleEntry<>(x, y));
        }
        
        return coordinates;
    }

}
