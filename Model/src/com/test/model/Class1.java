package com.test.model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.Date;

import oracle.jbo.domain.Number;

public class Class1 {
      public static void main (String[] args) throws java.lang.Exception
      {
          // get the current time and date
          DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
          DateFormat timeFormat = new SimpleDateFormat("HHmm");

          Date date = new Date();
          Number dateAdded = new Number(Integer.parseInt(dateFormat.format(date)));
          Number timeAdded = new Number(Integer.parseInt(timeFormat.format(date)));
          dateFormat = new SimpleDateFormat("HHmm");
          System.out.println(dateAdded);
          System.out.println(timeAdded);
      }
}
