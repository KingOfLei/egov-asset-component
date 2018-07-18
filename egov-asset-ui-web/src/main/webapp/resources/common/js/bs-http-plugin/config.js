/**
 * Created by qiu.yong on 2017/5/16.
 */
define(["app/core/app-jquery","app/core/app-core"], function (AppCore,$A) {
    var cfgAssistantExe = typeof(_assistantExe) == 'undefined' ? "bosssoft-assistant-v1.5.0.exe" : _assistantExe;
    var Config = {
        version:'1.5.0',
        url:'http://127.0.0.1:13526/',
        guardUrl:'http://127.0.0.1:13528/controlMainApp',
        heartbeat:'heart',
        update:'update',
        startUrl:'BosssoftAssistant://',
        downloadUrl:$A.getHostUrl()+'download/' + cfgAssistantExe,
        cookies: window._cookies||'no-cookies',
        timeout:2000,
        sliceSize:1024
    }
    return Config;
});