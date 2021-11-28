package configuration;

import java.util.Arrays;

public enum ConfigNode {
    // valori di default
    MAZE_WIDTH("Labirinto.larghezza",30),
    MAZE_HEIGHT("Labirinto.profondità",30),
    MAZE_CORNER_LOCATION_WORLD("Labirinto.world","world"),
    MAZE_CORNER_LOCATION_X("Labirinto.posizione_angolo_sinistro_inferiore.x",1000.0),
    MAZE_CORNER_LOCATION_Y("Labirinto.posizione_angolo_sinistro_inferiore.y",80.0),
    MAZE_CORNER_LOCATION_Z("Labirinto.posizione_angolo_sinistro_inferiore.z",-1000.0),

    GATE_LOCATION_X("Labirinto.posizione_gate_ingresso.x",1000.0),
    GATE_LOCATION_Y("Labirinto.posizione_gate_ingresso.y",80.0),
    GATE_LOCATION_Z("Labirinto.posizione_gate_ingresso.z",-1000.0),

    PRIZEBLOCK_LOCATION_X("Labirinto.chest_premio.x",1000.0),
    PRIZEBLOCK_LOCATION_Y("Labirinto.chest_premio.y",80.0),
    PRIZEBLOCK_LOCATION_Z("Labirinto.chest_premio.z",-1000.0),

    FIRST_PRIZEBLOCK_PRIZE("Labirinto.primo_premio","RANK_WINNER"),
    SECOND_PRIZEBLOCK_PRIZE("Labirinto.secondo_premio","KEY"),
    THIRD_PRIZEBLOCK_PRIZE("Labirinto.terzo_premio","ELYTRA"),
    GENERIC_PRIZEBLOCK_PRIZE("Labirinto.terzo_premio","EMERALDS"),

    EVENT_HOUR("Ora_evento_giornaliero","HH:mm");


    private final String path;
    private final Object value;

    ConfigNode(String path, Object defaultValue) {
        this.path = path;
        value = defaultValue;
    }

    public String getPath() {
        return path;
    }

    public Object getDefaultValue() {
        return value;
    }
}
