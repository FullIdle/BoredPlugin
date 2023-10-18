package com.github.fullidle.boredplugin.custompapi;

import com.github.fullidle.boredplugin.data.CommonData;
import com.github.fullidle.boredplugin.util.FileUtil;
import lombok.SneakyThrows;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.script.Bindings;
import javax.script.ScriptContext;
import javax.script.ScriptEngine;

public class MyPapi extends PlaceholderExpansion {
    @Override
    public @NotNull String getIdentifier() {
        return "custompapi";
    }

    @Override
    public @NotNull String getAuthor() {
        return "FullIdle";
    }

    @Override
    public @NotNull String getVersion() {
        return "1.0";
    }

    @SneakyThrows
    @Override
    public @Nullable String onRequest(OfflinePlayer player, @NotNull String params) {
        FileUtil config = CommonData.getMainPlugin().getConfig(CommonData.SubPlugin.CUSTOMPAPI, "config.yml");
        String code = config.getConfiguration().getString(params);
        if (code != null){
            ScriptEngine js = Main.scriptEngineManager.getEngineByName("nashorn");
            Bindings bindings = js.getContext().getBindings(ScriptContext.ENGINE_SCOPE);

            /*<==传入的数据==>*/
            bindings.put("player",player);
            bindings.put("params",params);
            /*<==-==-==-==>*/
            String result = String.valueOf(js.eval(code));
            bindings.clear();
            return result;
        }
        return null;
    }
}
