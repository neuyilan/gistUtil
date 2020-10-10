package io.reactor;

import java.nio.channels.SelectionKey;

/**
 * @author HouliangQi (neuyilan@163.com)
 * @description
 * @since 2020-07-31 17:01
 */
public interface EventHandler {
    public void handleEvent(SelectionKey handle) throws Exception;
}
