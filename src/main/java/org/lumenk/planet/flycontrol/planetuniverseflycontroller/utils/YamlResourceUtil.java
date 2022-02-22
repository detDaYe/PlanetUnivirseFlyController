package org.lumenk.planet.flycontrol.planetuniverseflycontroller.utils;


import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class YamlResourceUtil {
    private static final String PLUGIN_DIRETORY_PATH = "plugins/FlyController";
    private final File pluginFOLDER;
    private final File yamlFile;
    private final String fileName;

    public final YamlConfiguration yamlConfiguration = new YamlConfiguration();


    /**
     * 굳이 하위 폴더 안 만들거면 이걸 쓰면 됩니다
     * @param fileName 파일 이름을 정하세요.
     */
    public YamlResourceUtil(String fileName){
        this(fileName, "/");
    }

    /**
     * 생성자
     * @param fileName 파일의 이름을 정하세요. OOO로 전달하면 OOO.yml로 작성됩니다
     * @param subdir '/'로 시작하고 '/'로 끝나야 합니다
     */
    public YamlResourceUtil(String fileName, String subdir){
        pluginFOLDER = new File(PLUGIN_DIRETORY_PATH + subdir);
        this.fileName = fileName + ".yml";
        yamlFile = new File(PLUGIN_DIRETORY_PATH + subdir + this.fileName);
    }

    public void load(){
        try {
            yamlConfiguration.load(yamlFile);
        } catch (IOException e) {
            e.printStackTrace();
            Bukkit.getLogger().info("걱정 마세요! 아마도 잘 해결 되었을 거에요. (" + init() + ")");
        } catch (InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    public void save(){
        try {
            yamlConfiguration.save(yamlFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean init(){
        boolean dir_result = true;
        boolean file_result = true;

        if(!pluginFOLDER.exists()){
            Bukkit.getLogger().warning("플러그인 폴더가 없습니다!");
            dir_result = pluginFOLDER.mkdirs();
            if(dir_result) Bukkit.getLogger().info("플러그인 폴더를 만들었습니다.");
            else Bukkit.getLogger().warning("플러그인 폴더를 만들지 못했습니다!!");
        }

        if(!yamlFile.exists()){
            Bukkit.getLogger().warning(fileName + "이 존재하지 않습니다.");
            try {
                file_result = yamlFile.createNewFile();
                if(file_result)Bukkit.getLogger().info("파일을 생성하였습니다");
                else Bukkit.getLogger().warning("파일을 생성할 수 없습니다!!");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return dir_result & file_result;
    }
}
