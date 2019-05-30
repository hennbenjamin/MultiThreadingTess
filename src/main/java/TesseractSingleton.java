import net.sourceforge.tess4j.Tesseract;

public class TesseractSingleton extends Tesseract {
    private static volatile TesseractSingleton instance = new TesseractSingleton();

    private TesseractSingleton(){}


    public static TesseractSingleton getTheInstance(){
        return instance;
    }
}
