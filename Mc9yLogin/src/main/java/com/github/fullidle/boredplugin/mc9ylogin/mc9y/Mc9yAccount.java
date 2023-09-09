package com.github.fullidle.boredplugin.mc9ylogin.mc9y;

import com.github.fullidle.boredplugin.mc9ylogin.mc9y.util.SelectorData;
import com.google.gson.Gson;
import com.ruiyun.jvppeteer.core.Puppeteer;
import com.ruiyun.jvppeteer.core.browser.Browser;
import com.ruiyun.jvppeteer.core.page.ElementHandle;
import com.ruiyun.jvppeteer.core.page.Page;
import com.ruiyun.jvppeteer.core.page.Response;
import com.ruiyun.jvppeteer.options.LaunchOptions;
import com.ruiyun.jvppeteer.options.LaunchOptionsBuilder;
import com.ruiyun.jvppeteer.protocol.network.Cookie;
import com.ruiyun.jvppeteer.protocol.network.CookieParam;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
public class Mc9yAccount {
    private final String account;
    private final String password;
    @Setter private String name;
    @Setter private int id;
    @Setter private String designation;
    @Setter private String level;
    @Setter private boolean isLogin;
    @Setter private Mc9yPlugin[] resource;
    @Setter private Cookie[] cookies;

    public Mc9yAccount(String account,String password){
        this.account = account;
        this.password = password;
    }

    /**
     * @return 返回登入状态
     */
    @SneakyThrows
    public boolean login() {
        Page page = initializeBrowserPage();
        Browser browser = page.browser();

        int status = page.goTo("https://bbs.mc9y.net/account/").status();
        if (status != 200&&status != 304) {
            cleanData();
            page.$(SelectorData.accountInputBox).type(this.account);
            page.$(SelectorData.passwordInputBox).type(this.password);
            page.$(SelectorData.loginButton).click();
        }
        Response response = page.waitForNavigation();
        if (response == null) {
            /*<如果账号密码错误!>*/
            return false;
        }
        /*<正常登入>*/
        page.$(SelectorData.personalSpaceButton).click();
        page.waitForNavigation();
        this.id = Integer.parseInt(page.mainFrame().url().replace("https://bbs.mc9y.net/members/", "").replace("/", ""));
        this.name = textContent(page.$(SelectorData.personalSpaceName));
        this.designation = textContent(page.$(SelectorData.personalSpaceDesignation));
        this.level = textContent(page.$(SelectorData.personalSpaceLevel));
        this.isLogin = true;
        this.cookies = page.cookies().toArray(new Cookie[0]);
        loadResourcePlugin(page);
        /*关闭浏览器*/
        page.close();
        browser.close();
        return true;
    }
    private void cleanData(){
        this.isLogin = false;
        this.cookies = null;
        this.designation = null;
        this.id = 0;
        this.name = null;
        this.resource = null;
        this.level = null;
    }
    public static String textContent(ElementHandle element){
        return element.evaluate("el => el.textContent",new ArrayList<>()).toString();
    }

    @SneakyThrows
    private void loadResourcePlugin(Page page){
        String url = "https://bbs.mc9y.net/resources/authors/" + this.id;
        page.goTo(url);
        ArrayList<Mc9yPlugin> list = new ArrayList<>();
        for (ElementHandle handle : page.$$(SelectorData.resourcePluginChunking)) {
            Mc9yPlugin mc9yPlugin = new Mc9yPlugin();
            ElementHandle elName = handle.$(SelectorData.pluginBlockName);
            mc9yPlugin.setLatestVersion(textContent(handle.$(SelectorData.pluginChunkedVersion)));
            mc9yPlugin.setPrefix(textContent(handle.$(SelectorData.pluginBlockTag)));
            mc9yPlugin.setSynopsis(textContent(handle.$(SelectorData.plugInChunkingSummary)));
            mc9yPlugin.setAuthor(textContent(handle.$(SelectorData.pluginChunkingAuthor)));
            mc9yPlugin.setName(textContent(elName));
            mc9yPlugin.setId(Integer.parseInt(elName.evaluate("el => el.getAttribute('href')",new ArrayList<>()).toString().replace("/resources/","").replace("/","")));
            list.add(mc9yPlugin);
        }
        this.resource = list.toArray(new Mc9yPlugin[0]);
    }

    public static List<String> myArgs = Arrays.asList("--no-sandbox", "--disable-setuid-sandbox");
    @SneakyThrows
    public Object[] havePlugin(Mc9yPlugin plugin){
        Page page = initializeBrowserPage();
        Browser browser = page.browser();
        page.goTo("https://bbs.mc9y.net/resources/"+plugin.getId());
        ElementHandle button = page.$("html>body>div>div>div>div>div>div>div>div>div>div>a:nth-of-type(1)");
        boolean b = Mc9yAccount.textContent(button).contains("下载");
        page.close();
        browser.close();
        return new Object[]{b,page};
    }

    @SneakyThrows
    public Page initializeBrowserPage(){
        Gson gson = new Gson();
        /*<数据>*/
        LaunchOptions options = new LaunchOptionsBuilder().withArgs(myArgs).withHeadless(true).build();
        /*<浏览器创建>*/
        Browser browser = Puppeteer.launch(options);
        Page page = browser.newPage();
        /*<添加cookie>*/
        if (cookies != null){
            List<CookieParam> list = new ArrayList<>();
            for (Cookie cookie : cookies) {
                String json = gson.toJson(cookie);
                list.add(gson.fromJson(json, CookieParam.class));
            }
            page.setCookie(list);
        }
        page.setJavaScriptEnabled(false);
        return page;
    }
}
