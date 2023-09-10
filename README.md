# BoredPlugin
## 介绍
一些无聊的插件，不知道能写什么
## 构建
单独构建: ``` clean shadowJar ```
完整都将: ```clean isRoot shadowJar```
## 功能

- [x] BioPrompt —— 群系进入提示
    - [x] Minecraft原版群系 —— 可翻译文件内设置翻译名
    - [ ] 宝可梦群系支持
- [x] Mc9yLogin —— 九域登入
    - [x] 账号详细信息
    - [x] 资源插件信息 —— 可判断账号是否可下载指定插件
- [x] CustomPapi —— 自定义变量
- [ ] 更多功能

## 一些教程
需要在plugin.yml添加依赖
### Mc9yLogin —— 判断账号是否有插件
```java
public class Example extends JavaPlugin {
  @Override
  public void onLoad() {
    Mc9yPlugin mc9yPlugin = new Mc9yPlugin();
    /*<设置插件的id用于验证>*/
    mc9yPlugin.setId(693);
    /*<判断登入的这个账号是否能下载这个插件(如果买了这个插件就会显示成下载，免费的插件永远为true)>*/
    Object[] data = Main.myAccount.havePlugin(mc9yPlugin);
    /*<破解很容易的。。。>*/
    if ((boolean) data[0]) {
      /*<第一道验证>*/
      /*<第一道验证后的代码内容>*/
      /*<第二道验证>*/
      Page page = (Page)data[1];
      /*利用网页的信息进行判断，进行验证*/
    } else {
      /*<验证未通过>*/
      getLogger().info("你没有资格用这个插件");
    }
    /*这个是必要的!*/
    page.close();
    page.browser().close();
  }
}
```
### CustomPapi —— 如何自定义变量
```yaml
player_name: 'player.getName();' #这个在游戏中的变量就是 custompapi_player_name
player_level: 'player.getPlayer().getLevel();' #custompapi_player_level 由于传入的player是OfflinePlayer类型所以需要用一次getPlayer()
```
