
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.math.linear.OpenMapRealVector;
import org.apache.commons.math.linear.RealVectorFormat;
import org.apache.commons.math.linear.SparseRealVector;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.Field.TermVector;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.index.TermEnum;
import org.apache.lucene.index.TermFreqVector;

import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.LockObtainFailedException;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.util.Version;


//Klasse für den Vergleich von zwei Textdokumenten. Der Vergleich findet auf Worthäufigkeitsbasis statt
public class Lucene3 {

    public static final String ORDNER = "C:\\Users\\Benni\\Desktop\\files";
    public static final String PATH = "path";
    public static final String CONTENTS = "contents";

    private static Directory ramDirectory = new RAMDirectory();

    public static void main(String[] args) throws Exception {

        createIndex();
        searchIndex("lucene");
        testSimilarityUsingCosine();



    }

    //Erstellt eine Art Datenbank zum Vergleich der Dateien, in unserem Fall sind es die Dateien in dem angegebenen Ordner, also in der Regel 2 Dateien
        // Den Ordnerpfad muss man anpassen in dem Klassenattribut oben "Ordner"
    public static void createIndex() throws CorruptIndexException, LockObtainFailedException, IOException {
        Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_36);
        IndexWriter indexWriter = new IndexWriter(ramDirectory, new IndexWriterConfig(Version.LUCENE_36, analyzer));
        File dir = new File(ORDNER);
        File[] files = dir.listFiles();
        for (File file : files) {
            Document document = new Document();

            String path = file.getCanonicalPath();
            document.add(new Field(PATH, path, Field.Store.YES, Field.Index.ANALYZED,TermVector.YES));

            Reader reader = new FileReader(file);
            document.add(new Field(CONTENTS, reader,TermVector.YES));

            indexWriter.addDocument(document);
        }
        indexWriter.commit();
        indexWriter.close();
    }


    //definiert den Suchindex (die zwei Dokumente)
    public static void searchIndex(String searchString) throws IOException, ParseException {
        IndexReader indexReader = IndexReader.open(ramDirectory);
        IndexSearcher indexSearcher = new IndexSearcher(indexReader);

        Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_36);
        QueryParser queryParser = new QueryParser(Version.LUCENE_36,CONTENTS, analyzer);
        Query query = queryParser.parse(searchString);
        TopDocs topDocs = indexSearcher.search(query, 100);


        ScoreDoc[] hits = topDocs.scoreDocs;
        for (int i = 0; i < hits.length; i++) {
            int docId = hits[i].doc;
            Document d = indexSearcher.doc(docId);
            TermFreqVector termFreqVector=indexReader.getTermFreqVector(docId, CONTENTS);
        }


    }


    private static double getCosineSimilarity(DocVector d1, DocVector d2) {
        return (d1.vector.dotProduct(d2.vector)) /
                (d1.vector.getNorm() * d2.vector.getNorm());
    }

    private static class DocVector {
        public Map<String,Integer> terms;
        public SparseRealVector vector;

        public DocVector(Map<String,Integer> terms) {
            this.terms = terms;
            this.vector = new OpenMapRealVector(terms.size());
        }

        public void setEntry(String term, int freq) {
            if (terms.containsKey(term)) {
                int pos = terms.get(term);
                vector.setEntry(pos, (double) freq);
            }
        }

        public void normalize() {
            double sum = vector.getL1Norm();
            vector = (SparseRealVector) vector.mapDivide(sum);
        }

        public String toString() {
            RealVectorFormat formatter = new RealVectorFormat();
            return formatter.format(vector);
        }
    }
    //gibt die Kosinus-Ähnlichkeit zwischen zwei Texten bzw. Dokumenten als Wert zwischen 0 und 1 zurück

    public static void testSimilarityUsingCosine() throws Exception {
        IndexReader reader = IndexReader.open(
                ramDirectory);
        Map<String,Integer> terms = new HashMap<String,Integer>();
        TermEnum termEnum = reader.terms(new Term("contents"));
        int pos = 0;
        while (termEnum.next()) {
            Term term = termEnum.term();
            if (! "contents".equals(term.field()))
                break;
            terms.put(term.text(), pos++);
        }
        int[] docIds = new int[] {0,1};
        DocVector[] docs = new DocVector[docIds.length];
        int i = 0;
        for (int docId : docIds) {
            TermFreqVector[] tfvs = reader.getTermFreqVectors(docId);
            docs[i] = new DocVector(terms);
            for (TermFreqVector tfv : tfvs) {
                String[] termTexts = tfv.getTerms();
                int[] termFreqs = tfv.getTermFrequencies();
                for (int j = 0; j < termTexts.length; j++) {
                    docs[i].setEntry(termTexts[j], termFreqs[j]);
                }
            }
            docs[i].normalize();
            i++;
        }
        System.out.println("Anzahl Dokumente: "+ i);

        //Ausgabe der Kosinus-Ähnlichkeit zwischen den beiden Dokumenten
        double cosim01 = getCosineSimilarity(docs[0], docs[1]);
        System.out.println("Die Kosinus-Ähnlichkeit zwischen dem Referenzdokument und dem Vergleichsobjekt beträgt " + cosim01);

        reader.close();
    }
}