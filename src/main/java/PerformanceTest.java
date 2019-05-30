import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.Tesseract1;
import net.sourceforge.tess4j.TesseractException;
import org.apache.pdfbox.cos.COSName;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageTree;
import org.apache.pdfbox.pdmodel.PDResources;
import org.apache.pdfbox.pdmodel.encryption.InvalidPasswordException;
import org.apache.pdfbox.pdmodel.graphics.PDXObject;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.LongAdder;

import static java.lang.String.format;




public class PerformanceTest {

    private static final int RUNS = 1;
    static String result=null;



    public static void pdfToImage() {


        File pdfFile = new File("C:\\Users\\Benni\\Desktop\\pdf\\tesspatent.pdf");

        try {
            final PDDocument document;

            document = PDDocument.load(pdfFile);

            PDPageTree list = document.getPages();
            int i = 1;
            for (PDPage page : list) {
                PDResources pdResources = page.getResources();
                for (COSName name : pdResources.getXObjectNames()) {
                    PDXObject o = null;
                    try {
                        o = pdResources.getXObject(name);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if (o instanceof PDImageXObject) {
                        PDImageXObject image = (PDImageXObject) o;
                        String outputPath = "C:\\Users\\Benni\\Desktop\\eva_output\\";
                        String filename = outputPath + "s" + i + ".png";
                        try {
                            ImageIO.write(image.getImage(), "png", new File(filename));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        i++;

                    }
                }
            }

        } catch (InvalidPasswordException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        long timeStart = System.currentTimeMillis();
        pdfToImage();



        File[] files = new File("C:\\Users\\Benni\\Desktop\\eva_output").listFiles();

        Tesseract tesseract = new Tesseract();
        tesseract.setDatapath("C:\\Users\\Benni\\Desktop\\tessdata_diff\\tessdata_default\\tessdata");

        for (int i = 1; i <= files.length-1; i++) {


                        try {

                            result=tesseract.doOCR(files[i]);
                            System.out.println(result);

                        } catch (TesseractException e) {
                            e.printStackTrace();
                        }

                    }


        long timeEnd = System.currentTimeMillis();
        System.out.println("Verlaufszeit der Schleife: " + (timeEnd - timeStart) + " Millisek.");
    }

}

