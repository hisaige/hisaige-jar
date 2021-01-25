package com.hisaige.i18n.locale;

import com.hisaige.web.core.util.StringUtils;
import org.apache.commons.lang3.LocaleUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * @author chenyj
 * 2019/11/20 - 10:27.
 **/
public class LocaleMessage {

    private static final Logger logger = LoggerFactory.getLogger(LocaleMessage.class);

    public Locale getLocale() {
        Locale locale;
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if(null == requestAttributes){
            logger.debug("requestAttributes is null...");
            return LocaleContextHolder.getLocale();
        }
        HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
        // = (String) Objects.requireNonNull(RequestContextHolder.getRequestAttributes()).getAttribute("lang", RequestAttributes.SCOPE_REQUEST);
        String lang = request.getParameter("lang");
        if (!StringUtils.isEmpty(lang)) {
            //locale = new Locale(lang); //这样处理有问题
            locale = LocaleUtils.toLocale(lang);
        } else {
            locale = LocaleContextHolder.getLocale();
        }
        return locale;
    }

    public ResourceBundle getMsgBundle() {

        return getBundle("i18n/msg");
    }

    public ResourceBundle getSystemLogBundle() {

        return getBundle("i18n/systemLog");
    }

    private ResourceBundle getBundle(String bundleKey) {
        Locale locale = getLocale();
        return ResourceBundle.getBundle(bundleKey, locale);
    }
}
