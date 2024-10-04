package simulation.render;

import simulation.Coordinates;
import simulation.Map;
import simulation.entity.Entity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

/**
 * Рендерер карты
 */
public class MapRenderer {

    private static Scanner scanner = new Scanner(System.in);
    private static final String ICON_FILE_RESOURCE_PATH = "/render/entity-icon-map.txt";
    private static final String NO_ENTITY_ICON = "\uD83D\uDFEB";

    /**
     * Рендерит карту
     * @param map карта
     */
    public static void render(Map map) {

        for (int y = map.getSizeY(); y >= 0; y--) {
            for (int x = 0; x <= map.getSizeX(); x++) {
                Entity entity = map.getEntity(new Coordinates(x, y));
                renderEntity(entity);
            }
            System.out.print("\n");
        }

    }

    private static void renderEntity(Entity entity) {
        String entityIcon = getEntityIcon(entity);
        System.out.print(entityIcon);
    }

    /**
     * Возвращает иконку для сущности
     * @param entity сущность
     * @return если передано null, то возвращает пробельную строку, иначе символ для сущности из файла
     * @throws RuntimeException если иконка для файла не найдена или задан неверный формат для иконок в файле
     */
    private static String getEntityIcon(Entity entity) {

        if (entity == null) {
            return NO_ENTITY_ICON;
        } else {
            List<String> lines = readFile();
            List<String> entityIcons = new ArrayList<>();

            for (String line : lines) {
                int colonIndex = line.indexOf(':');
                if (colonIndex == -1) {
                    throw new RuntimeException("Invalid entity icon line format: " + line);
                }
                String classNamePart = line.substring(0, colonIndex);
                if (entity.getClass().getSimpleName().equals(classNamePart)) {
                    entityIcons.add(line.substring(colonIndex + 1));
                }
            }

            if (entityIcons.isEmpty()) {
                throw new RuntimeException("Icon for entity " + entity.getClass().getSimpleName() + " not found");
            } else {
                Random iconRandom = new Random();
                String entityIconWithSpaces = entityIcons.get(iconRandom.nextInt(entityIcons.size()));
                return entityIconWithSpaces.substring(0, entityIconWithSpaces.indexOf(" ") != -1 ? entityIconWithSpaces.indexOf(" ") : entityIconWithSpaces.length());
            }
        }

    }

    private static List<String> readFile() {

        List<String> lines = new ArrayList<>();
        InputStream iconFileStream = MapRenderer.class.getResourceAsStream(ICON_FILE_RESOURCE_PATH);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(iconFileStream));
        String line;
        try {
            while ((line = bufferedReader.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return lines;
    }

}
