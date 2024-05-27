package be.yorian.budgetbuddy.model;

import java.time.LocalDate;

public class Transaction {

    public int id;

    public String number;

    public Double amount;

    public String sign;

    public LocalDate date;

    public String comment;

    public String originalComment;

    public Category category;

    public Project project;

    public Transaction() {}

    public Transaction(int id, String number, Double amount, String sign, LocalDate date, String comment, Category category, Project project) {
        this.id = id;
        this.number = number;
        this.amount = amount;
        this.sign = sign;
        this.date = date;
        this.comment = comment;
        this.category = category;
        this.project = project;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public void setOriginalComment(String originalComment) {
        this.originalComment = originalComment;
    }

    public double getAmountWithSign() {
        if (sign.equals("+")) {
            return amount;
        } else {
        return -amount;
        }
    }

    public static class TransactionBuilder {
        private String number;
        private Double amount;
        private String sign;
        private LocalDate date;
        private String comment;
        private String originalComment;
        private Category category;
        private Project project;

        public TransactionBuilder(String number) {
            this.number = number;
        }

        public TransactionBuilder amount(Double amount) {
            this.amount = amount;
            return this;
        }

        public Transaction build() {
            return new Transaction();
        }
    }

}
