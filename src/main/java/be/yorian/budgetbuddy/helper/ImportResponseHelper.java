package be.yorian.budgetbuddy.helper;

import be.yorian.budgetbuddy.dto.TransactionsPerCategory;
import be.yorian.budgetbuddy.entity.Comment;
import be.yorian.budgetbuddy.dto.ImportTransactionsResponse;
import be.yorian.budgetbuddy.entity.Transaction;
import be.yorian.budgetbuddy.repository.CommentRepository;
import be.yorian.budgetbuddy.repository.TransactionRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static java.util.stream.Collectors.groupingBy;

public class ImportResponseHelper {

    private final TransactionRepository transactionRepository;
    private final CommentRepository commentRepository;
    private List<Comment> comments = new ArrayList<>();
    private final List<Transaction> transactions;
    private final ImportTransactionsResponse response;

    public ImportResponseHelper(TransactionRepository transactionRepository,
                                CommentRepository commentRepository,
                                List<Transaction> transactions)
    {
        this.transactionRepository = transactionRepository;
        this.commentRepository = commentRepository;
        this.transactions = transactions;
        this.response = new ImportTransactionsResponse();
    }


    public ImportTransactionsResponse createImportResponse() {
        loadAllComments();
        filterNewTransactions();

        return this.response;
    }

    private void loadAllComments() {
        comments = commentRepository.findAll();
    }

    private void filterNewTransactions() {

        List<Transaction> newTransactions = new ArrayList<>();
        List<Transaction> existingTransactions = new ArrayList<>();

        for (Transaction tx : transactions) {
            Transaction existingTransaction = checkTransaction(tx.getDate(), tx.getNumber());
            if (null == existingTransaction) {
                prepareTransaction(tx);
                newTransactions.add(tx);
            } else {
                existingTransactions.add(existingTransaction);
            }
        }
        newTransactions.sort(Comparator.comparing(Transaction::getDate));

        this.response.setExistingTransactions(createBudgetOverview(existingTransactions));
        this.response.setNewTransactions(newTransactions);
    }

    private List<TransactionsPerCategory> createBudgetOverview(List<Transaction> existingTransactions) {
        List<TransactionsPerCategory> budgetOverview = new ArrayList<>();
        existingTransactions.stream()
                .collect(groupingBy(Transaction::getCategory))
                .forEach((category, transactions) -> {
                    TransactionsPerCategory dto = new TransactionsPerCategory();
                    dto.setCategory(category);
                    dto.setTransactions(transactions);
                    dto.calculateAndSetTotal(transactions);
                    budgetOverview.add(dto);
                });

        return budgetOverview;
    }

    private Transaction checkTransaction(LocalDate date, String number) {
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