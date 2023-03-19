package org.ashone.rconcore.setting;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Setting {

    public final static Properties setting = new Properties();

    static {


//        File file=new File();

        try (InputStream inputStream = Setting.class.getClassLoader().getResourceAsStream("setting.properties");) {

            if (inputStream != null) {

                setting.load(inputStream);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    ;

    public static String getProp(String key) {
        return setting.getProperty(key);

    }

}
