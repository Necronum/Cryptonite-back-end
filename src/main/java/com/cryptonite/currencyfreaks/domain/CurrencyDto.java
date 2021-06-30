package com.cryptonite.currencyfreaks.domain;

import com.cryptonite.currencyfreaks.domain.deserializer.CurrencyDtoDeserializer;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.*;

import java.util.List;

@Data
@Getter
@AllArgsConstructor
@NoArgsConstructor
@JsonDeserialize(using = CurrencyDtoDeserializer.class)
public class CurrencyDto {
    @JsonProperty("base")
    private String base;
    @JsonProperty("rates")
    private List<RatesDto> rates;
}
