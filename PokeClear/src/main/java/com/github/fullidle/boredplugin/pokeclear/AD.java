package com.github.fullidle.boredplugin.pokeclear;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
public class AD {
    public AD() {
        try {
            URL url = new URL("http://api.7mc7.com/ad/AD.txt");
            InputStream inputS = url.openStream();
            InputStreamReader isr = new InputStreamReader(inputS);
            BufferedReader br = new BufferedReader(isr);
            for (Object str : br.lines().toArray()) {
                System.out.println(str.toString());
            }
        } catch (Exception ignored) {
        }
    }
}
