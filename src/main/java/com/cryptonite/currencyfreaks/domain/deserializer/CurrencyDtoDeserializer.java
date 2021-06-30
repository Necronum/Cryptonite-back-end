package com.cryptonite.currencyfreaks.domain.deserializer;

import com.cryptonite.currencyfreaks.domain.CurrencyDto;
import com.cryptonite.currencyfreaks.domain.RatesDto;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class CurrencyDtoDeserializer extends StdDeserializer<CurrencyDto> {
    public CurrencyDtoDeserializer(){
        super((Class<?>) null);
    }

    @Override
    public CurrencyDto deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
            throws IOException {

        JsonNode node = jsonParser.getCodec().readTree(jsonParser);

        String base = getBase(node);
        List<RatesDto> rates = getRates(node);
        return new CurrencyDto(base, rates);
    }

    private String getBase(JsonNode node) {
        return node.get("base").textValue();
    }

    private List<RatesDto> getRates(JsonNode node) {
        List<RatesDto> rates = new LinkedList<>();

        JsonNode ratesNode = node.get("rates");

        for (Iterator<Map.Entry<String, JsonNode>> iterator = ratesNode.fields(); iterator.hasNext(); ) {
            Map.Entry<String, JsonNode> next = iterator.next();
            rates.add(
                    new RatesDto(next.getKey(), new BigDecimal(next.getValue().textValue()))
            );
        }
        return rates;
    }
}
