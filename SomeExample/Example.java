package com.github.fullidle.boredplugin.mc9ylogin;

import com.ruiyun.jvppeteer.core.page.Page;
import lombok.SneakyThrows;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class Example extends JavaPlugin implements Listener {
    @Override
    public void onLoad() {
        super.onLoad();
    }

    @Override
    public void onEnable() {
        super.onEnable();
        if (this.getConfig().getBoolean("是否评价过")) {
            getServer().getPluginCommand("pingjia").setExecutor(this);
            getServer().getPluginManager().registerEvents(this,this);
        }
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }

    @SneakyThrows
    @EventHandler
    public void onSubmitARating(PlayerJoinEvent e){
        Player player = e.getPlayer();
        if (!player.isOp()) {
            return;
        }
        ComponentBuilder text = new ComponentBuilder("§c给个评价把\n");
/*★☆☆☆☆*/
        for (int i = 0; i < 5; i++) {
            StringBuilder builder = new StringBuilder();
            for (int j = 0; j < i; j++) {
                builder.append("★");
            }
            for (int j = 0; j < 5 - j; j++) {
                builder.append("☆");
            }
            builder.append("\n");
            ComponentBuilder xx = new ComponentBuilder(builder.toString());
            ComponentBuilder hover = xx.event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("评价" + (i+1) + "星").create()));
            ComponentBuilder click = xx.event(new ClickEvent(ClickEvent.Action.RUN_COMMAND,"/pj "+(i+1)));
            xx.append(hover.create());
            xx.append(click.create());
            text.append(xx.create());
        }
        BaseComponent[] base = text.create();
        player.spigot().sendMessage(base);
    }

    @SneakyThrows
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length > 0){
            int x;
            try {
                x = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                sender.sendMessage("非数字参数!");
                return false;
            }
            try {
                Page page = Main.getMyAccount().initializeBrowserPage();
                page.goTo("插件地址");
                page.$("html>body>div>div>div>div>div>div>div>div>div>div>div>a:nth-of-type(1)").click();
                page.$("html>body>div>div>div>form>div>div>dl>dd>div>div>a:nth-of-type("+x+")").click();
                page.$("html>body>div>div>div>form>div>div>dl>dd>textarea:nth-of-type(1)").type("九域有你更精彩");
                page.$("html>body>div>div>div>form>div>dl>dd>div>div>button:nth-of-type(1)").click();
                sender.sendMessage("感谢你的评价");
            }catch (Exception e){
                e.printStackTrace();
                sender.sendMessage("评价失败!不过你的心意作者心领了");
            }
        }
        sender.sendMessage("参数不足");
        return false;
    }
}
