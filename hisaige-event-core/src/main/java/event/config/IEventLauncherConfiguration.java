package event.config;

import event.launcher.NotifyEventLauncher;
import event.supper.notifyEvent.NotifyEventLauncherImpl;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;

/**
 * @author chenyj
 * 2020/4/5 - 15:03.
 **/
public class IEventLauncherConfiguration {

    //添加默认的通知事件发射器
    @Bean
    @ConditionalOnMissingBean(NotifyEventLauncher.class)
    public NotifyEventLauncher notifyEventLauncher(){
        return new NotifyEventLauncherImpl();
    }
}
