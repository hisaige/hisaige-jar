package event.serevice;

import com.hisaige.web.core.util.CollectionUtils;
import event.factory.EventRegister;
import event.ievent.AbstractEvent;
import event.ievent.AbstractNotifyEvent;
import event.ievent.AbstractPersistenceEvent;
import event.launcher.CustomEventLauncher;
import event.launcher.NotifyEventLauncher;

import javax.annotation.Nullable;
import java.util.List;

/**
 * @author chenyj
 * 2020/3/19 - 14:54.
 **/
public class EventServiceImpl implements EventService {

    private List<CustomEventLauncher> customEventLaunchers;
    private List<NotifyEventLauncher> notifyEventLaunchers;
    private EventRegister eventRegister;

    public EventServiceImpl(@Nullable List<CustomEventLauncher> customEventLaunchers, @Nullable List<NotifyEventLauncher> notifyEventLaunchers, EventRegister eventRegister){
        this.customEventLaunchers = customEventLaunchers;
        this.notifyEventLaunchers = notifyEventLaunchers;
        this.eventRegister = eventRegister;
    }

    @Override
    public void pubEvent(AbstractEvent event) {
        //先处理内部事件监听
        eventRegister.process(event);

        if(event instanceof AbstractNotifyEvent){
            if(!CollectionUtils.isEmpty(notifyEventLaunchers)){
                notifyEventLaunchers.forEach(action -> action.pubEvent(event));
            }
        } else if(event instanceof AbstractPersistenceEvent){
            if(!CollectionUtils.isEmpty(customEventLaunchers)){
                customEventLaunchers.forEach(action -> action.pubEvent(event));
            }
        }
    }
}
