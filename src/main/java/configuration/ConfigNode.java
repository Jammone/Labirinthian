package configuration;

import java.util.Arrays;

public enum ConfigNode {
    // valori di default
    SPACE_BETWEEN_LINES("space-between-lines", 0.02),
    QUICK_EDIT_COMMANDS("quick-edit-commands", true),
    IMAGES_SYMBOL("images.symbol", "[x]"),
    TRANSPARENCY_SPACE("images.transparency.space", " [|] "),
    TRANSPARENCY_COLOR("images.transparency.color", "&7"),
    UPDATE_NOTIFICATION("update-notification", true),
    BUNGEE_REFRESH_SECONDS("bungee.refresh-seconds", 3),
    BUNGEE_USE_REDIS_BUNGEE("bungee.use-RedisBungee", false),
    BUNGEE_USE_FULL_PINGER("bungee.pinger.enable", false),
    BUNGEE_PINGER_TIMEOUT("bungee.pinger.timeout", 500),
    BUNGEE_PINGER_OFFLINE_MOTD("bungee.pinger.offline-motd", "&cOffline, couldn't get the MOTD."),
    BUNGEE_PINGER_ONLINE_FORMAT("bungee.pinger.status.online", "&aOnline"),
    BUNGEE_PINGER_OFFLINE_FORMAT("bungee.pinger.status.offline", "&cOffline"),
    BUNGEE_PINGER_TRIM_MOTD("bungee.pinger.motd-remove-leading-trailing-spaces", true),
    BUNGEE_PINGER_SERVERS("bungee.pinger.servers",Arrays.asList("hub: 127.0.0.1:25565", "survival: 127.0.0.1:25566", "minigames: 127.0.0.1:25567")),
    TIME_FORMAT("time.format", "H:mm"),
    TIME_ZONE("time.zone", "GMT+1"),
    DEBUG("debug", false),
    LOCATION_CASPER_WORLD("locations.spawn_casper.world","world"),
    LOCATION_CASPER_X("locations.spawn_casper.x",1.0),
    LOCATION_CASPER_Y("locations.spawn_casper.y",1.0),
    LOCATION_CASPER_Z("locations.spawn_casper.z",1.0),

    LOCATION_1_WORLD("locations.spawn_1.world","world"),
    LOCATION_1_X("locations.spawn_1.x",1.0),
    LOCATION_1_Y("locations.spawn_1.y",1.0),
    LOCATION_1_Z("locations.spawn_1.z",1.0),

    LOCATION_2_WORLD("locations.spawn_2.world","world"),
    LOCATION_2_X("locations.spawn_2.x",1.0),
    LOCATION_2_Y("locations.spawn_2.y",1.0),
    LOCATION_2_Z("locations.spawn_2.z",1.0),

    LOCATION_3_WORLD("locations.spawn_3.world","world"),
    LOCATION_3_X("locations.spawn_3.x",1.0),
    LOCATION_3_Y("locations.spawn_3.y",1.0),
    LOCATION_3_Z("locations.spawn_3.z",1.0),

    LOCATION_CORE_1_WORLD("locations.core_1.world","world"),
    LOCATION_CORE_1_X("locations.core_1.x",1.0),
    LOCATION_CORE_1_Y("locations.core_1.y",1.0),
    LOCATION_CORE_1_Z("locations.core_1.z",1.0),

    LOCATION_CORE_2_WORLD("locations.core_2.world","world"),
    LOCATION_CORE_2_X("locations.core_2.x",1.0),
    LOCATION_CORE_2_Y("locations.core_2.y",1.0),
    LOCATION_CORE_2_Z("locations.core_2.z",1.0),

    LOCATION_CORE_3_WORLD("locations.core_3.world","world"),
    LOCATION_CORE_3_X("locations.core_3.x",1.0),
    LOCATION_CORE_3_Y("locations.core_3.y",1.0),
    LOCATION_CORE_3_Z("locations.core_3.z",1.0),

    LOCATION_ARENA_WORLD("locations.arena.world","world"),
    LOCATION_ARENA_X_MIN("locations.arena.x",1.0),
    LOCATION_ARENA_Y("locations.arena.y",1.0),
    LOCATION_ARENA_Z("locations.arena.z",1.0),
    LOCATION_ARENA_RAGGIO("locations.arena.diametro",1.0);



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
