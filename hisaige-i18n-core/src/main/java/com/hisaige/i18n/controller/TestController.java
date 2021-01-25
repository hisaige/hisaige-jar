package com.hisaige.i18n.controller;

import com.hisaige.i18n.locale.LocaleMessage;
import com.hisaige.web.core.entity.domain.AjaxMessageRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author chenyj
 * 2019/11/20 - 13:29.
 **/
@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    private LocaleMessage localeMessage;

    @RequestMapping(value = "/test1", method = RequestMethod.GET)
    public AjaxMessageRes<String> test1(){
        System.out.println(LocaleContextHolder.getLocaleContext().getLocale().getLanguage());
        System.out.println(LocaleContextHolder.getLocale().getLanguage());
        String string = localeMessage.getMsgBundle().getString("0");
        return new AjaxMessageRes<>(string);
    }

    @RequestMapping(value = "/test2", method = RequestMethod.GET)
    public AjaxMessageRes<String> test2() {
        String string = localeMessage.getSystemLogBundle().getString("msg.httpReq.test2");
        return new AjaxMessageRes<>(string);
    }

}
