package com.exchange.app;

import org.joda.time.DateTime;

import java.util.Currency;

public class RatesProvider {
    private ForeignExchangeRatesApiClient apiClient;

    public RatesProvider(ForeignExchangeRatesApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public Double getExchangeRateInEUR(Currency requested) {
        try {
            return apiClient.getLatestRates().get(requested.getCurrencyCode());
        } catch (IllegalArgumentException e) {
            throw new CurrencyNotSupportedException("Currency is not supported: " + requested.getCurrencyCode());
        }
    }

    public Double getExchangeRate(Currency requested, Currency exchanged) {
        return apiClient.getLatestRates(exchanged.getCurrencyCode()).get(requested.getCurrencyCode());
    }

    public Double getExchangeRateForDate(Currency currency, DateTime dateTime) {
        return apiClient.getHistoricalRates(dateTime).get(currency.getCurrencyCode());
    }
}
