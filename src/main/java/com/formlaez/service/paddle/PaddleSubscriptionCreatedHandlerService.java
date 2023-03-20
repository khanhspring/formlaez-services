package com.formlaez.service.paddle;

import java.util.TreeMap;

public interface PaddleSubscriptionCreatedHandlerService {
    void handle(TreeMap<String, String> dataMap);
}
