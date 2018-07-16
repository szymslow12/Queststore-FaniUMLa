package com.codecool.faniUMLa.Queststore.model.store;

public class Level {
    private Integer id_level;
    private String level_name;
    private Integer threshold_level;

    public Level(Integer id_level, String level_name, Integer threshold_level) {
        this.id_level = id_level;
        this.level_name = level_name;
        this.threshold_level = threshold_level;
    }

    public Integer getId_level() {
        return id_level;
    }

    public String getLevel_name() {
        return level_name;
    }

    public Integer getThreshold_level() {
        return threshold_level;
    }

    public void setLevel_name(String level_name) {
        this.level_name = level_name;
    }

    public void setThreshold_level(Integer threshold_level) {
        this.threshold_level = threshold_level;
    }
}
