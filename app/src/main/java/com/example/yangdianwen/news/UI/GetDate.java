package com.example.yangdianwen.news.UI;

import java.util.Calendar;

/**
 * Created by yangdianwen on 2016/6/4.
 */
public class GetDate {
   public String getdate(){
      int dayOfYear = Calendar.DAY_OF_YEAR;
      int dayOfMonth = Calendar.DAY_OF_MONTH;
       int year = Calendar.YEAR;
       String date=year+"/"+dayOfMonth+"/"+dayOfYear;
       return date;
   }
}
