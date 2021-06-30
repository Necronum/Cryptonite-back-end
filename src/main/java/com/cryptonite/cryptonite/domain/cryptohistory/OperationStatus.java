package com.cryptonite.cryptonite.domain.cryptohistory;

import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

public enum OperationStatus {
    NEW ,PURCHASE, SALE;

    public static Optional<OperationStatus> parseString(String value){
        return Arrays.stream(values())
                .filter(status -> Objects.equals(status.name(), value))
                .findFirst();
    }
}
