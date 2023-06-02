package com.pharmondev.computercontrol.models;

import java.util.List;

public class Games {
    private List<Game> games;

    public Games() {}

    public Games(List<Game> games) {
        this.games = games;
    }

    public List<Game> getGames() {
        return games;
    }

    public void setGames(List<Game> games) {
        this.games = games;
    }
}
