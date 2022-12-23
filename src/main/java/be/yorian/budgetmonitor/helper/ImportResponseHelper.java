package be.yorian.budgetmonitor.helper;

import be.yorian.budgetmonitor.entity.Comment;
import be.yorian.budgetmonitor.entity.ImportResponse;
import be.yorian.budgetmonitor.entity.Transaction;
import be.yorian.budgetmonitor.repository.CommentRepository;
import be.yorian.budgetmonitor.repository.TransactionRepository;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ImportResponseHelper {

    private final TransactionRepository transactionRepository;
    private final CommentRepository commentRepository;
    private List<Comment> comments = new ArrayList<>();
    private final List<Transaction> transactions;
    private final ImportResponse response;


    public ImportResponseHelper(TransactionRepository transactionRepository, CommentRepository commentRepository, List<Transaction> transactions) {

        this.transactionRepository = transactionRepository;
        this.commentRepository = commentRepository;
        this.transactions = transactions;
        this.response = new ImportResponse();
    }


    public ImportResponse createImportResponse() {

        loadAllComments();
        filterNewTransactions();
        //retrieveBudget();

        return this.response;
    }

    private void retrieveBudget() {
        String date = retrieveCorrectDate();
        double budget = 0.0;
        List<Transaction> transactions = this.transactionRepository.findByDateAndCategory(date, 5, 13);
        if (transactions.size() > 0){
            for(Transaction tx : transactions) {
                budget = budget + tx.amount;
            }
        }
        this.response.setAvailableBudget(budget);
    }

    private String retrieveCorrectDate() {

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Transaction tx = this.transactions.get(0);
        Date date = tx.getDate();

        Calendar c = Calendar.getInstance();
        c.setTime(date);

        c.add(Calendar.MONTH, -1);

        Date changedDate = c.getTime();
        String dateAsString = dateFormat.format(changedDate);

        return dateAsString.substring(0,7);
    }

    private void loadAllComments() {

        comments = commentRepository.findAll();
    }

    private void filterNewTransactions() {

        List<Transaction> filteredTransactions = new ArrayList<>();
        List<Transaction> existingTransactions = new ArrayList<>();

        for (Transaction tx : transactions) {
            Transaction existingTransaction = checkTransaction(tx.getDate(), tx.getNumber());
            if (null == existingTransaction) {
                prepareTransaction(tx);
                filteredTransactions.add(tx);
            } else {
                existingTransactions.add(existingTransaction);
            }
        }

        this.response.setExistingTransactions(existingTransactions);
        this.response.setFilteredTransactions(filteredTransactions);
    }

    private Transaction checkTransaction(Date date, String number) {

        return transactionRepository.findByDateAndNumber(date, number);
    }

    private void prepareTransaction(Transaction tx) {

        String originalComment_lower = tx.originalComment.toLowerCase();

        for (Comment cmt : comments) {
            if (originalComment_lower.contains(cmt.getSearchterm())) {
                tx.setComment(cmt.getReplacement());
                tx.setCategory(cmt.getCategory());
            }
        }
    }

}