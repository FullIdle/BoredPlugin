# BoredPlugin
## 介绍
一些无聊的插件，不知道能写什么
## 获得它
前去Actions中运行任务构建最新版本
## 功能
- [x] BioPrompt —— 群系进入提示
    - [x] Minecraft原版群系 —— 可翻译文件内设置翻译名
    - [x] 宝可梦群系支持 —— (支持forge1.12.2)
- [x] Mc9yLogin —— 九域登入
  - [x] 账号详细信息
  - [x] 资源插件信息 —— 可判断账号是否可下载指定插件
- [x] CustomPapi —— 自定义变量
- [x] PokeClear —— 清理神奇宝贝
- [x] FIFix —— 一个修复插件
  - [x] 一只精灵多人单独对战bug(后果可以多次捕捉)
  - [x] 无技能对战
  - [x] 无技能巢穴对战(与上面有所不同,处理方式不同)
- [x] CommandAll —— 为所有在线玩家执行命令(支持占位符)
- [x] OfflinePokeCtrl —— 离线玩家宝可梦控制器(控制离线宝可梦与玩家/宝可梦对战)
- [x] StorageBag —— 收纳袋(自定义物品,自定义空间,可以套娃)
- [ ] 更多功能
## 一些无聊的示例
<https://github.com/FullIdle/BoredPlugin/tree/master/SomeExample>
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
### BioPrompt —— 群系进入提示
配置未被翻译的群系
```yaml
{群系未翻译名(就是你屏幕弹出的名字)}: {翻译名}
#{}-》》花括号是不需要的!
```
### PokeClear —— 清理神奇宝贝
插件原地址: <https://bbs.mc9y.net/resources/178>
完全就是复刻过来的，所以教程直接去看原地址就好了
### StorageBag —— 收纳袋
教程看只能就能会,用记得重载就好.
```yaml
message:
  help:
    - 'help的提示待自行配置,我懒得搞!'
    - 'help 顾名思义'
    - 'give [player] [id] 给与玩家指定的物品'
    - 'rename [name] 修改手上收纳袋的名字'
    - 'upload [id] [row(有多少行)] 上传手上的物品做收纳袋的外观(添加了记得重载reload重载!)'
    - 'check 显示所有上传了的物品id(检擦是否加载用,没有就reload)'
    - 'reload 重载配置'
```
## 构建
#单独构建: ``` clean :<SubProject>:shadowJar ```
#完整构建: ```clean isRoot shadowJar```
