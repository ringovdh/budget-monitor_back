package be.yorian.budgetmonitor.helper;

import be.yorian.budgetmonitor.entity.Transaction;
import org.apache.pdfbox.cos.COSDocument;
import org.apache.pdfbox.io.RandomAccessFile;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class PDFReader {

    private final MultipartFile originalFile;

    public PDFReader(MultipartFile file) {
        this.originalFile = file;
    }

    public List<Transaction> parsePDFToTransactions()  {
        String text = null;
        try {
            text = createTextFromPDF();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return filterTransactionsFromText(text);
    }

    private String createTextFromPDF() throws IOException {
        String text;
        File pdfFile = parseOriginalFile();
        PDFParser parser;
        parser = new PDFParser(new RandomAccessFile(pdfFile, "r"));
        parser.parse();
        COSDocument cosDoc = parser.getDocument();
        PDFTextStripper pdfStripper = new PDFTextStripper();
        PDDocument pdDoc = new PDDocument(cosDoc);
        try {
            pdDoc.getNumberOfPages();
            pdfStripper.setStartPage(1);
            pdfStripper.setEndPage(pdDoc.getNumberOfPages());
            text = pdfStripper.getText(pdDoc);
        }
        finally {
            pdDoc.close();
        }

        return text;
    }

    private File parseOriginalFile() {
        File pdfFile = new File(System.getProperty("java.io.tmpdir")+"/targetFile.tmp");
        try {
            this.originalFile.transferTo(pdfFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return pdfFile;
    }

    private List<Transaction> filterTransactionsFromText(String text) {
        int counter = 0;
        List<Transaction> transactions = new ArrayList<>();
        String year = null;
        String lines[] = text.split("[\\r\\n]+");
        for (String line : lines) {
            String words[] = line.split("\\s+");
            if (words.length >= 2) {
                if (words[0].equals("Premium")) {
                    year = words[4] ;
                }
                if (words[0].equals("") && words[1].matches("[0-9]{4}") && !words[2].matches("[0-9]{4}")) {
                    int length = words.length;
                    if(words[length -1].equals("+") || words[length -1].equals("-")){
                        transactions.add(mapShortTransaction(words, year));
                    } else {
                        mapLongTransaction(counter, lines, transactions, year);
                    }
                }
            }
            counter++;
        }

        return transactions;
    }

    private void mapLongTransaction(int positieEersteLijn, String lines[], List<Transaction> transactions, String year) {
        Transaction tx = null;
        Boolean isRightSign = false;
        int positieLaatsteLijn = positieEersteLijn;
        String line = lines[positieEersteLijn];
        String firstLine[] = line.split("\\s+");
        try {
            tx = new Transaction(firstLine[1]);
            String lastLine[] = firstLine;
            while (!isRightSign) {
                while (!hasSign(lastLine)) {
                    firstLine = concatenate(firstLine, lastLine);
                    positieLaatsteLijn++;
                    line = lines[positieLaatsteLijn];
                    lastLine = line.split("\\s+");

                }
                int length = lastLine.length;
                if (lastLine[length - 1].equals("-")) {
                    tx.setSign(lastLine[length - 1]);
                    tx.setAmount(convertAmount(lastLine[length - 2]));
                    tx.setDate(convertDate(lastLine[length - 3] + "-" + year));
                    transactions.add(tx);
                    isRightSign = true;
                } else {
                    tx.setSign("+");
                    tx.setAmount(convertAmount(lastLine[length - 1]));
                    tx.setDate(convertDate(lastLine[length - 2] + "-" + year));
                    transactions.add(tx);
                    isRightSign = true;
                }
            }

            tx.setOriginalComment(bepaalLangeOmschrijving(firstLine.length, firstLine));
        } catch (Exception ex) {
            System.out.println("fout in tx: " + tx.getNumber());
            tx.setOriginalComment(bepaalLangeOmschrijving(firstLine.length, firstLine));
            transactions.add(tx);
        }
    }

    private Transaction mapShortTransaction(String[] words, String year) {

        int length = words.length;

        Transaction tx = new Transaction(words[1]);
        tx.setOriginalComment(bepaalKorteOmschrijving(length, words));
        tx.setSign(words[length-1]);
        tx.setAmount(convertAmount(words[length-2]));
        tx.setDate(convertDate(words[length-3] + "-" + year));

        return tx;
    }

    private String bepaalKorteOmschrijving(int length, String[] words) {
        String comment = "";
        for(int i = 2; i < length-3 ;i++) {
            comment = comment + words[i] + " ";
        }

        return comment;
    }

    private String bepaalLangeOmschrijving(int length, String[] words) {
        String comment = "";
        for(int i = 2; i < length ;i++) {
            comment = comment + words[i] + " ";
        }

        return comment;
    }

    private <T> T[] concatenate(T[] a, T[] b) {
        int aLen = a.length;
        int bLen = b.length;

        @SuppressWarnings("unchecked")
        T[] c = (T[]) Array.newInstance(a.getClass().getComponentType(), aLen + bLen);
        System.arraycopy(a, 0, c, 0, aLen);
        System.arraycopy(b, 0, c, aLen, bLen);

        return c;
    }

    private boolean hasSign(String words[]){

        boolean hasSign = false;
        int length = words.length;
        if (words[length -1].endsWith("+") || words[length -1].equals("-")) {
            if (Pattern.compile("[0-9]").matcher(words[length -2]).find()) {
                hasSign = true;
            }
        }

        return hasSign;
    }

    private Double convertAmount(String amount_string) {
        if (amount_string.contains(".")) {
            amount_string = amount_string.replace(".",  "");
        }
        if (amount_string.contains("+")) {
            amount_string = amount_string.replace("+",  "");;
        }
        amount_string = amount_string.replace(',', '.');

        return Double.parseDouble(amount_string);
    }

    private Date convertDate(String date) {
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        java.util.Date parsed = null;
        try {
            parsed = format.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return new Date(parsed.getTime());
    }
}
