package com.github.fullidle.boredplugin.mc9ylogin.mc9y;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Mc9yPlugin {
    private String prefix;
    private String name;
    /**
     * 如果只是用来判断账户是否可以下载(也就是购买了)这个插件的话就设置好插件id就可以用来判断了
     */
    private int id;
    private String latestVersion;
    private String author;
    private String synopsis;
}
