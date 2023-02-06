package me.dreamvoid.explimit;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerExpChangeEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class main extends JavaPlugin implements Listener {
    // 定义变量
    private File logDir;

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

        logDir = new File(getDataFolder(),"logs");
        if(!logDir.exists()){ logDir.mkdir(); }

        Bukkit.getPluginManager().registerEvents(this,this);
    }

    @EventHandler
    public void onPlayerExp(PlayerExpChangeEvent e){
        if(e.getAmount()>0 && getConfig().getInt("limit")>0){
            Date date = new Date(System.currentTimeMillis());
            SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd");
            File dateFile = new File(logDir,formatter.format(date)+".yml");
            if(!dateFile.exists()){
                try {
                    dateFile.createNewFile();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
            YamlConfiguration log = YamlConfiguration.loadConfiguration(dateFile);
            if(log.getInt(e.getPlayer().getUniqueId().toString(),0) >= getConfig().getInt("limit")){
                e.setAmount(0);
            } else {
                log.set(e.getPlayer().getUniqueId().toString(),log.getInt(e.getPlayer().getUniqueId().toString(),0)+e.getAmount());
                try {
                    log.save(dateFile);
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        }
    }
}
