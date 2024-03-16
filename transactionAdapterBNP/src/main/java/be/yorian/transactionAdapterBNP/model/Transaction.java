package be.yorian.transactionAdapterBNP.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Transaction {

    private final String number;
    private Double amount;
    private String sign;
    private LocalDate date;
    private String comment = "";
    private String[] words;
    private int length;
    private String year;


    public Transaction(String number) {
        this.number = number;
    }

    public String getNumber() {
        return number;
    }

    public Double getAmount() {
        return amount;
    }

    public String getSign() {
        return sign;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getComment() {
        return comment;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public void setTransactionAsText(String text) {
        this.words = text.split("\\s+");
        this.length = words.length;
    }

    public void convertTransaction() {
        convertSign();
        convertDate();
        convertAmount();
        convertComment();
    }

    private void convertComment() {
        if (isNegative()) {
            for(int i = 0; i < length-3 ;i++) {
                this.comment = comment.concat(words[i] + " ");
            }
        } else {
            for(int i = 0; i < length-2 ;i++) {
                this.comment = comment.concat(words[i] + " ");
            }
        }
    }

    private void convertAmount() {
        String amount_string;
        if (isNegative()) {
            amount_string = words[length - 2];
        } else {
            amount_string = words[length - 1];
        }
        if (amount_string.contains(".")) {
            amount_string = amount_string.replace(".",  "");
        }
        if (amount_string.contains("+")) {
            amount_string = amount_string.replace("+",  "");;
        }
        amount_string = amount_string.replace(',', '.');

        this.amount = Double.parseDouble(amount_string);
    }

    private void convertDate() {
        String date;
        if (isNegative()) {
            date = words[length - 3];
        } else {
            date = words[length - 2];
        }
        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        this.date = LocalDate.parse(date.concat("-" + year), format);
    }

    private boolean isNegative() {
        return sign.equals("-");
    }
    private void convertSign() {
        if (words[length - 1].equals("-")) {
            this.sign = "-";
        } else {
            this.sign = "+";
        }
    }
}
