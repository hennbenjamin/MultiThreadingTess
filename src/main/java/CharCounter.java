public class CharCounter {


static int counter =0;
 static String w1 = "US00PP25245P3\n" +
         "a2) United States Plant Patent = co) Patent No.: US PP25,245 P3\n" +
         "Pierron-Darbonne (45) Date of Patent: Jan. 27, 2015\n" +
         "(54) RASPBERRY PLANT NAMED ‘ADELITA’ (52) U.S. CL\n" +
         "CPC viccccseeereeereeree AOLH 5/0887 (2013.01)\n" +
         "(50) LatinName: Rubus idaeus L. USPC veces Plt/204\n" +
         "Varietal Denomination: Adelita (58) Field of Classification Search\n" +
         ": CPC viccceccseeceertescnseteeeteereee AOLH 5/0887\n" +
         "(75) Inventor: olexandre Pierron-Darbonne, USPC voccccssssssesesssesseseeeeeeeeee Plt/204, 203\n" +
         "amplona (ES) or .\n" +
         "See application file for complete search history.\n" +
         "(73) Assignee: Plantas De Navarra, S.A., Navarra (ES) Primary Examiner — Susan McCormick Ewoldt\n" +
         "(*) Notice: Subject to any disclaimer, the term of this Assistant Examiner — Louanne Krawezewicz Myer\n" +
         "patent is extended or adjusted under 35 (74) Altorney, Agent, or Firm — Christie, Parker & Hale\n" +
         "U.S.C. 154(b) by 244 days. LLP\n" +
         "(21) Appl. No.: 13/507,055 (57) ABSTRACT\n" +
         ". The present invention relates to a new and distinct raspbe\n" +
         "(22) Filed: May 31, 2012 variety, The varietal denomination of the new variety is\n" +
         "(65) Prior Publication Data ‘Adelita’. Among the characteristics which appear to distin-\n" +
         "guish the new variety from other varieties are a combination\n" +
         "US 2012/0311748 P1 Dec. 6, 2012 of traits which include a sparse spines density and very abun-\n" +
         "dant production of medium red colored, conical shaped, and\n" +
         "(51) Int. Cl. very big fruit size.\n" +
         "AOI 5/00 (2006.01)\n" +
         "AOLH 5/08 (2006.01) 17 Drawing Sheets\n" +
         "1 2\n" +
         "\n" +
         "Botanical classification: Rubus idaeus L. COMPARISON NEW VARIETY TO\n" +
         "\n" +
         "Varietal denomination: The new plant has the varietal CO-PENDING CULTIVAR ‘LUPITA’\n" +
         "denomination ‘Adelita’.\n" +
         "\n" +
         "‘ADELITA’ shows a sparse spines density and a rigid\n" +
         "BACKGROUND OF THE INVENTION > spines texture; whereas ‘LUPITA’ shows a medium sparse\n" +
         "spines density and a smooth spines texture.\n" +
         "\n" +
         "The new variety of raspberry was created in a breeding ‘ADELITA’’s leaf shows a free arrangement of lateral leaf-\n" +
         "program by crossing two parents; in particular, by crossing as lets and the lateral leaftlets touching with the terminal leaflet;\n" +
         "seed parent an undistributed raspberry parent designated — whereas ‘LUPITA’’s leaf shows a touching arrangement of\n" +
         "07.09R.99 (unpatented) and as pollen parent an undistributed 1° Jateral leaflets and the lateral leaflets do not touch with the\n" +
         "raspberry parent designated 07.13R.46 (unpatented). Female terminal leaflet.\n" +
         "and male are selections from breeder’s program of Planasa. Fruit size of ‘ADELITA’ is bigger than fruit size of\n" +
         "Both parental varieties are property and have not been com- ‘LUPITA’.\n" +
         "mercialized. 15 ‘ADELITA’ shows a medium red fruit color, RHS red group";
 static String w2 = "Als Mahout 2009 vorgestellt wure, war es in erster Linie ein Java MapReduce Package";


    public static void main (String [] args){
        berechneCount();

    }

    public static void berechneCount(){



        char[] first  = w1.toLowerCase().toCharArray();
        char[] second = w2.toLowerCase().toCharArray();

        int minLength = Math.min(first.length, second.length);

        for (int i = 0; i < minLength; i++)

            if (first[i] != second[i])
            {
                counter++;

            }

System.out.println(counter);

    }

}


