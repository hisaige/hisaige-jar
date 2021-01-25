package event.processor;

import event.factory.EventRegister;
import event.listener.IEventListener;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

import javax.validation.constraints.NotNull;

/**
 * @author chenyj
 * 2020/1/15 - 17:21.
 **/
public class IEventListenerPostProcessor implements BeanPostProcessor {

    private EventRegister eventRegister;

    public IEventListenerPostProcessor(EventRegister eventRegister){
        this.eventRegister = eventRegister;
    }

    @Override
    public Object postProcessAfterInitialization(@NotNull Object bean, String beanName) throws BeansException {

        IEventListener listener;
        if(bean instanceof IEventListener){
            listener = (IEventListener)bean;
            eventRegister.register(listener);
        }
        return bean;
    }
}
