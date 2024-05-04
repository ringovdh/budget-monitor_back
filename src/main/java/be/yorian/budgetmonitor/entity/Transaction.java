package be.yorian.budgetmonitor.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Transient;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Entity
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long tx_id;

    public String number;

    public Double amount;

    public String sign;

    public LocalDate date;

    public String comment;

    @Transient
    public String originalComment = "";

    @OneToOne
    @JoinColumn(name = "category", referencedColumnName = "id")
    public Category category;

    @OneToOne
    @JoinColumn(name= "project_id", referencedColumnName = "id")
    public Project project;

    @Transient
    private String[] words;
    @Transient
    private int length;
    @Transient
    private String year;

    public Transaction() {}

    public Transaction(String number, Double amount, String sign, LocalDate date, String comment, Category category, Project project) {
        this.number = number;
        this.amount = amount;
        this.sign = sign;
        this.date = date;
        this.comment = comment;
        this.category = category;
        this.project = project;
    }

    public Transaction(String number) {
        this.number = number;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment){
        this.comment = comment;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }


    public double getAmountWithSign() {
        if (sign.equals("+")) {
            return amount;
        } else {
        return -amount;
        }
    }

    public void setTransactionAsText(String text) {
        this.words = text.split("\\s+");
        this.length = words.length;
    }

    public void setYear(String year) {
        this.year = year;
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
                this.originalComment = originalComment.concat(words[i] + " ");
            }
        } else {
            for(int i = 0; i < length-2 ;i++) {
                this.originalComment = originalComment.concat(words[i] + " ");
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

    @Override
    public String toString() {
        return "Transaction{" +
                "tx_id=" + tx_id +
                ", number='" + number + '\'' +
                ", amount=" + amount +
                ", sign='" + sign + '\'' +
                ", date=" + date +
                ", comment='" + comment + '\'' +
                ", category=" + category + '\'' +
                ", project=" + project +
                '}';
    }

}
