package com.salton123.util;

import org.greenrobot.eventbus.EventBus;

public class EventUtil {

    public static void register(Object context) {
        if (!EventBus.getDefault().isRegistered(context)) {
            EventBus.getDefault().register(context);
        }
    }

    public static void unregister(Object context) {
        EventBus.getDefault().unregister(context);
    }

    public static void sendEvent(Object object) {
        EventBus.getDefault().post(object);
    }
}
