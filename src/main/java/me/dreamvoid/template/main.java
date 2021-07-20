package me.dreamvoid.template;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import org.bstats.bukkit.Metrics;

import java.io.File;

public class main extends JavaPlugin {

    // 定义变量
    public static YamlConfiguration config;

    @Override
    public void onLoad() { } // 加载插件

    @Override
    public void onEnable() { LoadConfig(); } // 启用插件

    @Override
    public void onDisable() { } // 禁用插件

    private void LoadConfig() {
        // 加载配置文件
        File configure = new File(getDataFolder(), "config.yml");
        if(!(configure.exists())){ saveDefaultConfig(); }
        config = YamlConfiguration.loadConfiguration(configure);

        // bStats统计
        if(config.getBoolean("bStats", true)) {
            int pluginId = 1234;
            Metrics metrics = new Metrics(this, pluginId);
        }
    }

}
