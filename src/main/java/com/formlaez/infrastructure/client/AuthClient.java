package com.formlaez.infrastructure.client;

import com.formlaez.infrastructure.client.model.AuthToken;

public interface AuthClient {
    AuthToken getTokenByCode(String code);
}
