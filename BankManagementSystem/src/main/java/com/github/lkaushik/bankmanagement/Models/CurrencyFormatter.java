package com.github.lkaushik.bankmanagement.Models;

import java.text.NumberFormat;
import java.util.Currency;
import java.util.Locale;

public class CurrencyFormatter {

    public static String formattedCurrency(double currency, String languageCode, String countryCode) {
        Locale locale = Locale.of(languageCode, countryCode);
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(locale);
        String currencySymbol = Currency.getInstance(locale).getSymbol(locale);

        return currencyFormat.format(currency).replace(currencySymbol, currencySymbol + " ");
    }

    public static String formattedCurrency(double currency) {
        Locale locale = Locale.getDefault();
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(locale);
        String currencySymbol = Currency.getInstance(locale).getSymbol(locale);

        return currencyFormat.format(currency).replace(currencySymbol, currencySymbol + " ");
    }
}
