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




    public class PerformanceTest_ignorieren {

        private static final int RUNS = 1;


        public static void pdfToImage() {


            File pdfFile = new File("C:\\Users\\Benni\\Desktop\\pdf\\s.pdf");

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


            System.setProperty("jna.library.path", "32".equals(System.getProperty("sun.arch.data.model")) ? "lib/win32-x86" : "lib/win32-x86-64");

            File[] files = new File("C:\\Users\\Benni\\Desktop\\eva_output").listFiles();
            System.out.println("Verfügbare Prozessoren: " +  Runtime.getRuntime().availableProcessors());
            System.out.println("Es existieren so viele pngs für OCR in parallel: " +files.length);

            final LongAdder totalErrors = new LongAdder();

            for (int runIndex = 1; runIndex <= RUNS; runIndex++) {
                final LongAdder errorsInCurrentRun = new LongAdder();
                Arrays.stream(files).parallel().forEach((file) -> {
                            Tesseract tesseract = TesseractSingleton.getTheInstance();
                            tesseract.setDatapath("C:\\Users\\Benni\\Desktop\\tessdata_diff\\tessdata_v3\\tessdata");
                            try {
                             String result  =tesseract.doOCR(file); // Ignore Result
                                System.out.println(result);

                            } catch (TesseractException e) {
                                e.printStackTrace();
                            } catch (Error e) {
                                errorsInCurrentRun.increment();
                            }

                        }



                );

                System.out.println(format("\tRun %d -> Errors: %d/%d",
                        runIndex,
                        errorsInCurrentRun.intValue(),
                        files.length));

                totalErrors.add(errorsInCurrentRun.intValue());
            }
            int totalNumberOfErrors = totalErrors.intValue();


            long timeEnd = System.currentTimeMillis();
            System.out.println("Verlaufszeit der Schleife: " + (timeEnd - timeStart) + " Millisek.");
        }

    }

