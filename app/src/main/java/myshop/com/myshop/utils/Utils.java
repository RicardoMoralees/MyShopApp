package myshop.com.myshop.utils;

import android.content.Context;

import java.text.NumberFormat;
import java.util.Locale;

import myshop.com.myshop.R;

public class Utils {

    public static String getFormatPrize(Context context, double price){
        NumberFormat numberFormat = NumberFormat.getCurrencyInstance(Locale.US);
        numberFormat.setMaximumFractionDigits(2);
        numberFormat.setMinimumFractionDigits(2);
        return numberFormat.format(String.format(context.getResources().getString(R.string.price),price));
    }
}
