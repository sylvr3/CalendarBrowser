package edu.asu.bsse.sbarnai.ser423calendarbrowser;


import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.Timer;


public class TimeZoneHandler {

    private static final String UTC_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private Timer mTimer;

    public static Date changeUTCToLOCAL(Date date) {
        Date d = null;


        SimpleDateFormat outputFmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        outputFmt.setTimeZone(TimeZone.getDefault());

        try {
            String dateStr = outputFmt.format(date.getTime());
            d = outputFmt.parse(dateStr);

        } catch (ParseException e) {

        }



        return d;
    }

    //finds the difference between the future timestamp and the current timestamp
    public static long diffBetweenLOCALTimes(String futureTimeStamp) {
        Date present;
        Date future;

        Calendar calendar = Calendar.getInstance();
        present = calendar.getTime();//getCurrentTimeUTC();

        SimpleDateFormat format = new SimpleDateFormat(UTC_FORMAT);
        format.setTimeZone(TimeZone.getDefault());
        try {
            future = format.parse(futureTimeStamp);
            //in milliseconds
            long difference = future.getTime() - present.getTime();

            return difference;

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return (long) 0.0;
    }

    // do this when I am adding events
    public static Date localToUTC(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date gmt = new Date(sdf.format(date));
        return gmt;
    }


/*
    public static Date dateToUTC(Date date){
        final DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        try {

            String dateStr = sdf.format(date);
            date = sdf.parse(dateStr);
        } catch(Exception s){
            String F = "";
        }
        return date;

    }
*/

    public static Date dateToUTC(Date date) {
        Date d = null;

        // Date localTime = new Date();
        //localTime.setT

        //creating DateFormat for converting time from local timezone to GMT
        DateFormat converter = new SimpleDateFormat("dd/MM/yyyy:HH:mm:ss");
        converter.setTimeZone(TimeZone.getTimeZone("UTC"));

        //getting GMT timezone, you can get any timezone e.g. UTC
        //converter.setTimeZone(TimeZone.getDefault());

        System.out.println("UTC: " + date);;
        System.out.println("time in MST : " + converter.format(date.getTime()));
        try {
            d = converter.parse(converter.format(date.getTime()));

        } catch (ParseException pe) {

        }


        return d;

    }


    public static String utcToLocaleDate(Date utcDate) {
        return utcToLocaleDate(utcDate.toString());
    }

    public static String utcToLocaleDate(String utcDate) {
        DateFormat dateFormatUtc = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateFormatUtc.setTimeZone(TimeZone.getDefault());

        try {
            Date dateInPDT = dateFormat.parse(utcDate);
            return dateFormatUtc.format(dateInPDT);
        } catch (Exception e) {
            String hit = "";
        }

        return "";
    }


    /*


    // do this when I am VIEWING events

    //Convert the UTC to LOCAL
    public static Date changeUTCToLOCAL(Date date) {

        SimpleDateFormat format = new SimpleDateFormat(UTC_FORMAT);
        format.setTimeZone(TimeZone.getTimeZone("UTC"));

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        TimeZone timeZone = calendar.getTimeZone();
        // converting UTC to LOCAL
        SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        format2.setTimeZone(timeZone);

        return date;
    }


    //finds the difference between the future timestamp and the current timestamp
    public static long diffBetweenLOCALTimes(String futureTimeStamp) {
        Date present;
        Date future;

        Calendar calendar = Calendar.getInstance();
        present = calendar.getTime();//getCurrentTimeUTC();

        SimpleDateFormat format = new SimpleDateFormat(UTC_FORMAT);
        format.setTimeZone(TimeZone.getDefault());
        try {
            future = format.parse(futureTimeStamp);
            //in milliseconds
            long difference = future.getTime() - present.getTime();

            return difference;

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return (long) 0.0;
    }

    // do this when I am adding events
    public static Date localToUTC(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date gmt = new Date(sdf.format(date));
        return gmt;
    }

    public static void main(String[] args) {
        // test w/ blu selfie
        // create a new date object called d

        Calendar cal = Calendar.getInstance();
        TimeZone tz2 = cal.getTimeZone();  // get current timezone

        //SimpleDateFormat dateFormatter2 = new SimpleDateFormat("MM-dd-yyyy HH:mm"); //this format changeable

        //String dateString2 = dateFormatter2.format(eventEndTime);

        //Date dtt = formatter.parse(dateString2);

        //Date convertdate = localtoutc(dtt); // date returned from utc to local method call

        // pass in this date to convert utc to local method


        //SimpleDateFormat dateFormatter2 = new SimpleDateFormat("MM-dd-yyyy HH:mm"); //this format changeable

        //dateFormatter2.setTimeZone(tz2);

        //String dateString2 = dateFormatter2.format(eventEndTime);

        //Date dtt = localtoutc(d); // date returned from utc to local method call

        //Date dtt = formatter.parse(dateString2);

        // Date ds = new Date(convertdate.getTime());
        //Calendar endCal = Calendar.getInstance();

        //endCal.setTimeInMillis(ds.getTime());

        // set date and time pickers to new date and time

        // endDate.updateDate(endCal.get(Calendar.YEAR), endCal.get(Calendar.MONTH), endCal.get(Calendar.DATE));

        //   endTime.setCurrentHour(endCal.get(Calendar.HOUR_OF_DAY));

        //   endTime.setCurrentMinute(endCal.get(Calendar.MINUTE));

        // todo: create a new Date object based on the values selected in date and time pickers

        Calendar cal = Calendar.getInstance();
cal.set(Calendar.YEAR, year);
cal.set(Calendar.MONTH, monthOfYear);
cal.set(Calendar.DATE, dayOfMonth);
cal.set(Calendar.HOUR_OF_DAY, hourOfDay);
cal.set(Calendar.MINUTE, minute);
cal.set(Calendar.SECOND, 0);
cal.set(Calendar.MILLISECOND, 0);
Date date = cal.getTime();
Date d = localToUTC(date); // do this for start time
// convert local to UTC

// repeat steps above for end time


Calendar beginCal = Calendar.getInstance();
        beginCal.setTime(d);
        Calendar endCal = Calendar.getInstance();
        endCal.setTime(d2);

        long beginTime = beginCal.getTimeInMillis();

        long endingTime = endCal.getTimeInMillis();

        event.put("dtstart", String.valueOf(beginTime)); // putting UTC times, which will be converted to local when viewed

        event.put("dtend", String.valueOf(endingTime));





        Date datet = new Date();
        System.out.println("UTC to local");
        Date test = new Date();
        test.setTime(datet.getTime());
        test = changeUTCToLOCAL(datet);
        System.out.println(test.toString());
        Date date = new Date();
        System.out.println("Local to UTC");
        System.out.println(localToUTC(date).toString());


    }
    */

}
