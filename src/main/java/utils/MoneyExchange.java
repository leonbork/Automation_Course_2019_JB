package utils;

/**
 * Implementation for moneyExchange Api.
 */

import javax.money.convert.ExchangeRate;
import javax.money.convert.ExchangeRateProvider;
import javax.money.convert.MonetaryConversions;

public class MoneyExchange {

    public static ExchangeRate getExchangeRate(String baseCurrency, String targetCurrency) {

        ExchangeRateProvider exchangeRateProvider = MonetaryConversions.getExchangeRateProvider();

        ExchangeRate rate = exchangeRateProvider.getExchangeRate(baseCurrency, targetCurrency);
        LogTest.log("Rate " + rate.getFactor());
        return rate;
    }
}
