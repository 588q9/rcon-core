package org.ashone.rconcore.setting;

public enum SettingPropertiesName {
    ip,port,password,server;

    public static String propertiesKey(String ...name){

        return String.join(".",name);
    }

public  String key(){

    return this.toString();

}
}
