package com.github.fullidle.boredplugin.data;

import com.github.fullidle.boredplugin.FiPlugin;
import lombok.Getter;
import lombok.Setter;

import java.io.File;
import java.util.HashMap;
import java.util.Map;


public class CommonData {
    @Getter @Setter private static FiPlugin mainPlugin;
    @Getter
    public enum SubPlugin {
        MC9YLOGIN("Mc9yLogin"),
        BIOPROMPT("BioPrompt"),
        BOREDPLUGIN("BoredPlugin");
        private final String name;
        private final Map<String,File> files = new HashMap<>();
        SubPlugin(String name){
            this.name = name;
        }
    }
}
