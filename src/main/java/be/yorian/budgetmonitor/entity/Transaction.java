package be.yorian.budgetmonitor.entity;

import jakarta.persistence.*;

import java.sql.Date;

@Entity
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long tx_id;
    public String number;
    public Double amount;
    public String sign;
    public Date date;
    public String comment;
    @Transient
    public String originalComment;
    @OneToOne
    @JoinColumn(name = "category", referencedColumnName = "id")
    public Category category;

    public Transaction() {}

    public Transaction(String number, Double amount, String sign, Date date, String comment, Category category) {
        this.number = number;
        this.amount = amount;
        this.sign = sign;
        this.date = date;
        this.comment = comment;
        this.category = category;
    }

    public Transaction(String number) {
        this.number = number;
    }

    public long getTx_id() {
        return tx_id;
    }

    public void setTx_id(long tx_id) {
        this.tx_id = tx_id;
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
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

    public void setOriginalComment(String originalComment) {
        this.originalComment = originalComment;
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
                ", category=" + category +
                '}';
    }

}
