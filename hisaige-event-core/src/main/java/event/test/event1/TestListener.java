package event.test.event1;

import com.alibaba.fastjson.JSON;
import event.listener.BaseEventHandler;
import event.test.TestEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author chenyj
 * 2020/1/15 - 16:36.
 **/
public class TestListener extends BaseEventHandler<TestEvent> {

    private static final Logger logger = LoggerFactory.getLogger(TestListener.class);
    @Override
    public void process(TestEvent event) {
        logger.info("receive event:{}", JSON.toJSONString(event));
    }
}
