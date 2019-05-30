import java.util.Arrays;

public class EditDistanceRecursive {

     String x = "Apache Mahout ist eine quelloffene Machine-Learning Bibliothek der Apache Software Foundation. Mahout wurde als Teilprojekt von Apache Lucene im April 2009 vorgestellt [vgl. 1]. Während Apache Lucene den Fokus auf die Durchsuchung von (textuellen) Inhalten und Informationsbeschaffung legt, konzentriert sich Apache Mahout auf die Bereitstellung und Anwendung von Algorithmen aus dem Machine Learning-Umfeld in¬¬¬ nicht-verteilten und verteilten Systemen. Die Algorithmen werden dabei in drei Teilgebieten kategorisiert:";
     String y = "Apache Mahou ist eine quelloffene Machine-Learning Bibliothek der Apache Software Foundatian. Mahout wurde als Teilprojekt von Apache Lucene im April 2009 vorgestellt [vgl. 1]. Während Apache Lucene den Fokus auf die Durchsuchung von (textuellen) Inhalten und Informationsbeschaffung legt, konzentriert sich Apache Mahout auf die Bereitstellung und Anwendung von Algorithmen aus dem Machine Learning-Umfeld in¬¬¬ nicht-verteilten und verteilten Systemen. Die Algorithmen werden dabei in drei Teilgebieten kategorisiert:";

     int calculate(String x, String y) {

        this.x = x;
        this.y = y;


        if (x.isEmpty()) {
            return y.length();
        }

        if (y.isEmpty()) {
            return x.length();
        }

        int substitution = calculate(x.substring(1), y.substring(1))
                + costOfSubstitution(x.charAt(0), y.charAt(0));
        int insertion = calculate(x, y.substring(1)) + 1;
        int deletion = calculate(x.substring(1), y) + 1;

        return min(substitution, insertion, deletion);
    }

    public static int costOfSubstitution(char a, char b) {
        return a == b ? 0 : 1;
    }

    public static int min(int... numbers) {
         System.out.println(Arrays.stream(numbers).min().orElse(Integer.MAX_VALUE));
        return Arrays.stream(numbers).min().orElse(Integer.MAX_VALUE);
    }

    public static void main (String [] args){
        min();

    }
}