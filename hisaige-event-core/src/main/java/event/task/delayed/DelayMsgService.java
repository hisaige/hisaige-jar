package event.task.delayed;

/**
 * @author chenyj
 * 2020/10/16 - 18:48.
 **/
public class DelayMsgService {

    public boolean pubDelayMsg(DelayedMsg delayedMsg){
        return DelayMsgDispatcher.offerTask(delayedMsg);
    }
}
