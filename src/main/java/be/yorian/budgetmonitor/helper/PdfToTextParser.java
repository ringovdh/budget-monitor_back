package be.yorian.budgetmonitor.helper;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.File;
import java.io.IOException;

public class PdfToTextParser {

    private File pdfFile;

    public PdfToTextParser(File pdfFile) {
        this.pdfFile = pdfFile;
    }

    public String createTextFromPDF() throws IOException {
        PDDocument document = PDDocument.load(pdfFile);
        PDFTextStripper pdfStripper = new PDFTextStripper();
        return pdfStripper.getText(document);
    }
}
