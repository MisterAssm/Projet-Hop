package fr.hop.utilities;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ImageUtilities {

    public static BufferedImage resizeImage(BufferedImage originalImage, int width, int height) {
        Image scaledImage = originalImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = bufferedImage.createGraphics();
        g2d.drawImage(scaledImage, 0, 0, null);
        g2d.dispose();
        return bufferedImage;
    }

    // chatgpt
    public static BufferedImage miroirImage(BufferedImage originalImage) {
        int width = originalImage.getWidth();
        int height = originalImage.getHeight();

        // Créer une nouvelle image qui aura la même taille que l'image originale
        BufferedImage mirroredImage = new BufferedImage(width, height, originalImage.getType());

        // Appliquer la symétrie sur l'image par rapport à l'axe des ordonnées
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                // Récupérer le pixel de l'image originale
                int pixel = originalImage.getRGB(x, y);

                // Appliquer la symétrie horizontale : inversion par rapport à l'axe des ordonnées
                int mirroredX = width - 1 - x;

                // Définir le pixel symétrique dans l'image résultante
                mirroredImage.setRGB(mirroredX, y, pixel);
            }
        }

        return mirroredImage;  // Retourner l'image symétrique
    }

}
