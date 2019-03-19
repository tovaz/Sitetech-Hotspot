/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Util;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Comparator;
import java.util.Currency;
import java.util.Locale;
import java.util.SortedMap;
import java.util.TreeMap;
import sitetech.Helpers.dbHelper;
import sitetech.hotspot.Modelos.Configuracion;
import sitetech.hotspot.Modelos.ConfiguracionManager2;

/**
 *
 * @author megan
 */
public class Moneda {
    public static String Formatear(BigDecimal bd) {
        Configuracion conf = ConfiguracionManager2.getConfiguracion(new dbHelper());
        Currency currency = conf.getMoneda();
        DecimalFormat df = new DecimalFormat(getSimbolo(currency.getCurrencyCode()) + " ###,###.00", new DecimalFormatSymbols(conf.getRegionLocal().getLocale()));

        return df.format(bd);
    }
    
    public static String Formatear(BigDecimal bd, Locale locale) {
        Currency currency = Currency.getInstance(locale);
        DecimalFormat df = new DecimalFormat(getSimbolo(currency.getCurrencyCode()) + " ###,###.00", new DecimalFormatSymbols(locale));
        
        return df.format(bd);
    }
    
    public static SortedMap<Currency, Locale> currencyLocaleMap;
      static {
          currencyLocaleMap = new TreeMap<Currency, Locale>(new Comparator<Currency>() {
            public int compare(Currency c1, Currency c2){
                return c1.getCurrencyCode().compareTo(c2.getCurrencyCode());
            }
        });
        for (Locale locale : Locale.getAvailableLocales()) {
             try {
                 Currency currency = Currency.getInstance(locale);
             currencyLocaleMap.put(currency, locale);
             }catch (Exception e){
         }
        }
      }
      
    public static String getSimbolo(String currencyCode) {
        Currency currency = Currency.getInstance(currencyCode);
        System.out.println( currencyCode+ ":-" + currency.getSymbol(currencyLocaleMap.get(currency)));
        return currency.getSymbol(currencyLocaleMap.get(currency));
    }
}