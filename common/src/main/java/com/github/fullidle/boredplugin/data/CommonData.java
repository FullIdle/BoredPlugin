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
        FIFIX("FIFix"),
        MC9YLOGIN("Mc9yLogin"),
        BIOPROMPT("BioPrompt"),
        BOREDPLUGIN("BoredPlugin"),
        CUSTOMPAPI("CustomPapi"),
        POKECLEAR("PokeClear"),
        STORAGEBAG("StorageBag");
        private final String name;
        private final Map<String,File> files = new HashMap<>();
        SubPlugin(String name){
            this.name = name;
        }
    }
}
