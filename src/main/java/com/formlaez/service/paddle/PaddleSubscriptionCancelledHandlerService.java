package com.formlaez.service.paddle;

import java.util.TreeMap;

public interface PaddleSubscriptionCancelledHandlerService {
    void handle(TreeMap<String, String> dataMap);
}
