package kz.kase.next.checker.view.comparator;

import kz.kase.next.checker.model.domain.QuoteHolder;

import java.util.Comparator;


public class QuoteComparators {


    public static class BidComparator implements Comparator<QuoteHolder> {

           @Override
           public int compare(QuoteHolder q1, QuoteHolder q2) {

               if (q1.getPrice() > q2.getPrice()) {
                   return -1;
               } else if (q1.getPrice() < q2.getPrice()) {
                   return 1;
               } else {
                   return 0;
               }
           }
       }


       public static class AskComparator implements Comparator<QuoteHolder> {
           @Override
           public int compare(QuoteHolder q1, QuoteHolder q2) {
               if (q1.getPrice() < q2.getPrice()) {
                   return -1;
               } else if (q1.getPrice() > q2.getPrice()) {
                   return 1;
               } else {
                   return 0;
               }

           }
       }

       public static class BidSwapComparator implements Comparator<QuoteHolder> {

           @Override
           public int compare(QuoteHolder q1, QuoteHolder q2) {

               if (q1.getPrice() < q2.getPrice()) {
                   return -1;
               } else if (q1.getPrice() > q2.getPrice()) {
                   return 1;
               } else {
                   return 0;
               }
           }
       }


       public static class AskSwapComparator implements Comparator<QuoteHolder> {
           @Override
           public int compare(QuoteHolder q1, QuoteHolder q2) {
               if (q1.getPrice() > q2.getPrice()) {
                   return -1;
               } else if (q1.getPrice() < q2.getPrice()) {
                   return 1;
               } else {
                   return 0;
               }

           }
       }



}
