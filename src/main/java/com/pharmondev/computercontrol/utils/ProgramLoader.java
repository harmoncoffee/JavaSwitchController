package com.pharmondev.computercontrol.utils;

import com.pharmondev.computercontrol.models.Game;
import com.pharmondev.computercontrol.models.Games;
import java.io.InputStream;
import java.util.List;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

public class ProgramLoader {
    private static ProgramLoader instance;
    // private List<Game> games;
    private Games games;

    private ProgramLoader() {
        loadData();
    }

    public static ProgramLoader getInstance() {
        if (instance == null) {
            synchronized (ProgramLoader.class) {
                if (instance == null) {
                    instance = new ProgramLoader();
                }
            }
        }
        return instance;
    }

    private void loadData() {
        Yaml yaml = new Yaml(new Constructor(Games.class));
        try (InputStream inputStream = ProgramLoader.class.getResourceAsStream("/programs.yaml")) {
            // Map<String, List<Map<String, Object>>> data = yaml.load(inputStream);
            Games data = yaml.load(inputStream);
            // List<Map<String, Object>> gameResults = data.get("games");
            games = data;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Game> getGames() {
        return games.getGames();
    }
}
