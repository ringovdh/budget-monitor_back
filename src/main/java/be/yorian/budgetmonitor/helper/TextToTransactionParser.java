package be.yorian.budgetmonitor.helper;

import be.yorian.budgetmonitor.entity.Transaction;
import be.yorian.budgetmonitor.exception.TextParserException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicInteger;

public class TextToTransactionParser {

    private String year;
    List<String> lines;

    public TextToTransactionParser(String text) {
        this.lines = splitText(text);
    }

    public List<Transaction> filterTransactionsFromText() throws TextParserException {
        retrieveYear();
        Map<String, Integer> startlines = findTransactionsStarts();
        Map<String, String> txtexts = convertToTransactions(startlines);
        return createTransactions(txtexts);
    }

    private List<Transaction> createTransactions(Map<String, String> txtexts) {
        List<Transaction> transactions = new ArrayList<>();
        txtexts.forEach((k, v) -> {
            Transaction tx = new Transaction(k);
            tx.setTransactionAsText(v);
            tx.setYear(year);
            tx.convertTransaction();
            transactions.add(tx);
        });
        return transactions;
    }

    private Map<String, String> convertToTransactions(Map<String, Integer> startlines) {
        Map<String, String> transactions = new TreeMap<>();
        startlines.forEach((k, v) -> {
            String text = "";
            text = text.concat(lines.get(v));
            ++v;
            while (v < lines.size() && !isEndOfTransaction(lines.get(v))) {
                text = text.concat(" ").concat(lines.get(v));
                ++v;
            }
            text = text.concat(" ").concat(lines.get(v));
            ++v;
            transactions.put(k, text);
        });

        return transactions;
    }

    private Map<String, Integer> findTransactionsStarts() {
        Map<String, Integer> startlines = new TreeMap<>();
        AtomicInteger counter = new AtomicInteger(0);
        lines.forEach(l -> {
            if (isStartOfTransaction(l)) {
                String key = l.substring(1,5);
                startlines.put(key, counter.intValue());
            }
            counter.getAndIncrement();
        });

        return startlines;
    }

    private boolean isStartOfTransaction(String l) {
        String[] words = l.split("\\s+");
        if (words.length > 1) {
            return words[0].isEmpty() && words[1].matches("[0-9]{4}")
                    && !words[2].matches("[0-9]{4}");
        } else {
            return false;
        }
    }

    private boolean isEndOfTransaction(String l) {
        String[] words = l.split("\\s+");
        int length = words.length;
        return length > 1 && (words[length - 1].equals("-")
                || words[length - 1].endsWith("+"));
    }

    private void retrieveYear() throws TextParserException {
        year = lines.stream()
                .filter(l -> l.contains("Easy Guide Pack")
                        || l.contains("Premium Pack"))
                .findFirst()
                .map(l -> {
                    String[] words = l.split("\\s+");
                    return words[(words.length - 3)];
                }).orElseThrow(() -> new TextParserException("Year could not be found in file"));
    }

    private static List<String> splitText(String text) {
        return List.of(text.split("[\\r\\n]+"));
    }
}
