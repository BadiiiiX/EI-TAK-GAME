package fr.esiea.mali.ui.icons;

import fr.esiea.mali.core.model.piece.PieceKind;
import fr.esiea.mali.core.model.team.TeamColor;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Manager class responsible for loading and caching game piece sprites.
 * Uses a singleton pattern with a private constructor and static methods.
 * 
 * @author Mehdi A.
 */
public final class SpriteManager {
    private static final Map<String, Image> CACHE = new ConcurrentHashMap<>();

    /**
     * Private constructor to prevent instantiation.
     * This class is designed to be used statically.
     */
    private SpriteManager() { }

    /**
     * Gets the sprite image for a game piece based on its kind and color.
     * Images are loaded from resources and cached for performance.
     * 
     * @param kind the kind of piece (flat, standing, or capstone)
     * @param color the color of the piece
     * @return the image for the specified piece
     * @throws UncheckedIOException if the sprite cannot be loaded or is not found
     */
    public static Image getSprite(PieceKind kind, TeamColor color) {
        String key = kind.name() + "_" + color.name();
        return CACHE.computeIfAbsent(key, k -> {
            String path = String.format(
                    "/icons/%s_%s.png",
                    kind.name().toLowerCase(),
                    color.name().toLowerCase()
            );
            try (var is = SpriteManager.class.getResourceAsStream(path)) {
                if (is == null) {
                    throw new UncheckedIOException(
                            new IOException("Resource not found: " + path)
                    );
                }
                return ImageIO.read(is);
            } catch (IOException e) {
                throw new UncheckedIOException("Cannot load sprite: " + path, e);
            }
        });
    }
}
