package org.viswa.util;

import java.util.Scanner;

public class ScannerProvider {         //Singleton Class for one scanner object
    
      private static final Scanner sc=new Scanner(System.in);
      
      private ScannerProvider(){}

      public static Scanner getScanner(){ return sc;}

}
