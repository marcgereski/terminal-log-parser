package kz.kase.next.checker.view.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class FormatUtil {

    //    public static final String NO_DATE_SET = "";
    private static double PRECISION_SET[] = {1, 0.1, 0.01, 0.001, 0.0001, 0.00001, 0.000001};

    public static final String NO_DATE_SET = "";
    public static final int DEFAULT_PRECISION = 6;

    private static NumberFormat defaultFormat =
            NumberFormat.getNumberInstance(Locale.getDefault());

    private static final String DECIMAL_SEPARATOR = String.valueOf((char)160);

    public static final NumberFormat DOUBLE_FORMAT = new DecimalFormat("###,###,##0.00");


    public static final NumberFormat SIMPLE_NUMBER_FORMAT = new DecimalFormat("###,###,##0");
    private static final NumberFormat quoteFormat1 = new DecimalFormat("###,###,##0.0");
    private static final NumberFormat quoteFormat2 = new DecimalFormat("###,###,##0.00");
    private static final NumberFormat quoteFormat3 = new DecimalFormat("###,###,##0.000");
    private static final NumberFormat quoteFormat4 = new DecimalFormat("###,###,##0.0000");
    private static final NumberFormat quoteFormat5 = new DecimalFormat("###,###,##0.00000");
    private static final NumberFormat quoteFormat6 = new DecimalFormat("###,###,##0.000000");

    private static final NumberFormat TF_FORMAT0 = new DecimalFormat("#########");
    private static final NumberFormat TF_FORMAT1 = new DecimalFormat("########0.0");
    private static final NumberFormat TF_FORMAT2 = new DecimalFormat("########0.00");
    private static final NumberFormat TF_FORMAT3 = new DecimalFormat("########0.000");
    private static final NumberFormat TF_FORMAT4 = new DecimalFormat("########0.0000");
    private static final NumberFormat TF_FORMAT5 = new DecimalFormat("########0.00000");
    private static final NumberFormat TF_FORMAT6 = new DecimalFormat("########0.000000");

    private static final NumberFormat quoteFormatDec = new DecimalFormat("###,###,##0.");

    private static final DateFormat dateFormatter = new SimpleDateFormat("dd MMM yyyy", new Locale("RU"));
    public static final DateFormat YEAR_FIRST_WITH_MINUS = new SimpleDateFormat("yyyy-MMM-dd", new Locale("RU"));
    private static final DateFormat timeFormatter = new SimpleDateFormat("HH:mm:ss", new Locale("RU"));
    private static final DateFormat timeFormatter2 = new SimpleDateFormat("HH:mm", new Locale("RU"));


    private static final DecimalFormat decimalFormat2 = new DecimalFormat("#.00");
    private static final DecimalFormat decimalFormat4 = new DecimalFormat("###,###,##0.0000");

    static {
        decimalFormat4.setRoundingMode(RoundingMode.HALF_UP);
    }

//    public static Double formatWithPrecisionDouble(Number num, int precision) {
//        NumberFormat numb = NumberFormat.getNumberInstance(Locale.getDefault());
//        numb.setMaximumFractionDigits(precision);
//        return Double.parseDouble(numb.format(num));
//    }


    public static NumberFormat getTextFieldFormat(int precision) {
        switch (precision) {
            case 0:
                return TF_FORMAT0;
            case 1:
                return TF_FORMAT1;
            case 2:
                return TF_FORMAT2;
            case 3:
                return TF_FORMAT3;
            case 4:
                return TF_FORMAT4;
            case 5:
                return TF_FORMAT5;
            case 6:
                return TF_FORMAT6;

            default:
                return TF_FORMAT2;
        }
    }

    public static NumberFormat getFormat(int precision) {
        switch (precision) {
            case -1:
                return quoteFormatDec;
            case 0:
                return SIMPLE_NUMBER_FORMAT;
            case 1:
                return quoteFormat1;
            case 2:
                return quoteFormat2;
            case 3:
                return quoteFormat3;
            case 4:
                return quoteFormat4;
            case 5:
                return quoteFormat5;
            case 6:
                return quoteFormat6;

            default:
                return quoteFormat2;
        }
    }

    public static String format(Number num, int precision) {
        if (num == null) return "";
        switch (precision) {
            case -1:
                return quoteFormatDec.format(num);
            case 0:
                return SIMPLE_NUMBER_FORMAT.format(num);
            case 1:
                return TF_FORMAT1.format(num);
            case 2:
                return quoteFormat2.format(num);
            case 3:
                return quoteFormat3.format(num);
            case 4:
                return quoteFormat4.format(num);
            case 5:
                return quoteFormat5.format(num);
            case 6:
                return quoteFormat6.format(num);
            default:
                return quoteFormat2.format(num);
        }
    }

    public static double parseFormattedDouble(String value) {
        String v = value.replace("," , ".");
        v = v.replaceAll(DECIMAL_SEPARATOR, "");
        return Double.parseDouble(v);
    }

    public static String format(Number num) {
        if (num == null) return "";
        if (num instanceof Float) {
            return DOUBLE_FORMAT.format(round((Float) num, DEFAULT_PRECISION));
//            return DOUBLE_FORMAT.format(num);
        } else if (num instanceof Double) {
            return DOUBLE_FORMAT.format(round((Double) num, DEFAULT_PRECISION));
//            return DOUBLE_FORMAT.format(num);
        } else if (num instanceof SignedDouble) {
            return format((SignedDouble) num);
        } else {
            return defaultFormat.format(num);
        }
    }

    public static String format4(double value) {
        return decimalFormat4.format(value);
    }

    public static String format(Date date) {
        if (date == null) return NO_DATE_SET;
        return dateFormatter.format(date);
    }

    public static String format(LocalDate date) {
        if (date == null) return NO_DATE_SET;
        return date.format(DateTimeFormatter.ofPattern("dd MMM yyyy"));
    }

    public static Date parseDate(String date) {
        try {
            return dateFormatter.parse(date);
        } catch (ParseException e) {
            System.out.println(e.toString());
            return null;
        }
    }

    public static String timeFormat(Date date) {
        if (date == null) return NO_DATE_SET;
        return timeFormatter.format(date);
    }

    public static String timeFormat2(Date date) {
        if (date == null) return NO_DATE_SET;
        return timeFormatter2.format(date);
    }

//    public static String format(DateTS dateTS) {
//        if (dateTS == null) return NO_DATE_SET;
//        return format(dateTS.getDate());
//    }
//
//    public static String format(TimeTS timeTS) {
//        if (timeTS == null) return NO_DATE_SET;
//        return timeFormatter.format(timeTS.getDate());
//    }

    public static String format(SignedDouble signedDouble) {
        double val = signedDouble.doubleValue();
        String formatted = format(val);
        return val > 0 ? "+" + formatted : formatted;
    }

    public static String format2(double value) {
        return decimalFormat2.format(value);
    }


    public static Number parse(String val, Number defValue) {
        if (val == null) return defValue;
        val = val.trim();
        try {
            return defaultFormat.parse(val);
        } catch (ParseException e) {
            return defValue;
        }
    }

    public static Integer parseInt(String val, Integer defValue) {
        return parse(val, defValue).intValue();
    }

    public static Double parseDouble(String val, Double defValue) {
        return parse(val, defValue).doubleValue();
    }

//    public static Double pareDouble(double number,int pricision){
//          if(pricision == 2){
//              DOUBLE_FORMAT.parseAcc
//          }
//    }

    public static Long parseLong(String val, Long defValue) {
        return parse(val, defValue).longValue();
    }

    public static Integer parseInt(String val) {
        return parseInt(val, 0);
    }

    public static Double parseDouble(String val) {
        return parseDouble(val, 0.0);
    }

    public static Long parseLong(String val) {
        return parseLong(val, 0L);
    }

    public static BigDecimal parseBigDecimal(String value) throws ParseException {
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setGroupingSeparator(' ');
        symbols.setDecimalSeparator('.');
        String pattern = "# ###.0#";
//        DecimalFormat decimalFormat = new DecimalFormat(pattern, symbols);
        DecimalFormat decimalFormat = new DecimalFormat();
        decimalFormat.setParseBigDecimal(true);

        return (BigDecimal) decimalFormat.parse(value);
    }

//    public static String getDirectionTitle(Markets.OrderDir dir) {
//        int a = 0;
//        switch (dir) {
//            case BUY:
//                return Lang.getStr("a_buy");
//            case SELL:
//                return Lang.getStr("a_sell");
//        }
//        return null;
//    }

    public static double round(double val, int zeros) {
        int order = (int) Math.pow(10, zeros);
//        double ceil = Math.round(val);
//        return ceil + (double) Math.round((val - ceil) * Order) / Order;
        return (double) Math.round(val * order) / order;
    }

    public static final double[] PRECISIONS = {0, 0.1, 0.01, 0.001, 0.0001, 0.00001, 0.000001, 0.0000001};

    public static boolean compare(Double d1, Double d2, int prec) {
        double d = Math.abs(d1 - d2);
        return !(prec < 0 || prec >= PRECISIONS.length) && Math.abs(d1 - d2) < PRECISIONS[prec + 1];
    }

    public static Date getDateWithoutTime(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.HOUR, 0);

        return calendar.getTime();
    }

    public static double roundTo(double val, int precision) {
        double div = 1;
        for (int i = 0; i < precision; i++) div *= 10;
        return Math.round(val * div) / div;
    }


    public static class SignedDouble extends Number {

        private double doubleVal = 0;

        public SignedDouble(double doubleVal) {
            this.doubleVal = doubleVal;
        }

        @Override
        public int intValue() {
            return (int) doubleVal;
        }

        @Override
        public long longValue() {
            return (long) doubleVal;
        }

        @Override
        public float floatValue() {
            return (float) doubleVal;
        }

        @Override
        public double doubleValue() {
            return doubleVal;
        }
    }

    public static double convertPrecision(int precision) {
        return PRECISION_SET[precision];
    }

    public static void main(String[] args) {
        System.out.println(roundTo(0.987654321, 7));
    }

}
