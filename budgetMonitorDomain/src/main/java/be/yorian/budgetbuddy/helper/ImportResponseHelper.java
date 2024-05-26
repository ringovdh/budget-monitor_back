package be.yorian.budgetbuddy.helper;

import be.yorian.budgetbuddy.adapter.database.entity.TransactionEntity;
import be.yorian.budgetbuddy.adapter.database.repository.TransactionEntityRepository;
import be.yorian.budgetbuddy.dto.TransactionsPerCategory;
import be.yorian.budgetbuddy.model.Comment;
import be.yorian.budgetbuddy.model.ImportTransactionsResponse;
import be.yorian.budgetbuddy.repository.CommentRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static java.util.stream.Collectors.groupingBy;

public class ImportResponseHelper {

    private final TransactionEntityRepository transactionRepository;
    private final CommentRepository commentRepository;
    private List<Comment> comments = new ArrayList<>();
    private final List<TransactionEntity> transactionEntities;
    private final ImportTransactionsResponse response;

    public ImportResponseHelper(TransactionEntityRepository transactionRepository,
                                CommentRepository commentRepository,
                                List<TransactionEntity> transactionEntities)
    {
        this.transactionRepository = transactionRepository;
        this.commentRepository = commentRepository;
        this.transactionEntities = transactionEntities;
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

        List<TransactionEntity> newTransactionEntities = new ArrayList<>();
        List<TransactionEntity> existingTransactionEntities = new ArrayList<>();

        for (TransactionEntity tx : transactionEntities) {
            TransactionEntity existingTransaction = checkTransaction(tx.getDate(), tx.getNumber());
            if (null == existingTransaction) {
                prepareTransaction(tx);
                newTransactionEntities.add(tx);
            } else {
                existingTransactionEntities.add(existingTransaction);
            }
        }
        newTransactionEntities.sort(Comparator.comparing(TransactionEntity::getDate));

        this.response.setExistingTransactions(createBudgetOverview(existingTransactionEntities));
        this.response.setNewTransactions(newTransactionEntities);
    }

    private List<TransactionsPerCategory> createBudgetOverview(List<TransactionEntity> existingTransactionEntities) {
        List<TransactionsPerCategory> budgetOverview = new ArrayList<>();
        existingTransactionEntities.stream()
                .collect(groupingBy(TransactionEntity::getCategory))
                .forEach((category, transactions) -> {
                    TransactionsPerCategory dto = new TransactionsPerCategory();
                    dto.setCategory(category);
                    dto.setTransactions(transactions);
                    dto.calculateAndSetTotal(transactions);
                    budgetOverview.add(dto);
                });

        return budgetOverview;
    }

    private TransactionEntity checkTransaction(LocalDate date, String number) {
        return transactionRepository.findByDateAndNumber(date, number);
    }

    private void prepareTransaction(TransactionEntity tx) {

        String originalComment_lower = tx.originalComment.toLowerCase();

        for (Comment cmt : comments) {
            if (originalComment_lower.contains(cmt.getSearchterm())) {
                tx.setComment(cmt.getReplacement());
                tx.setCategory(cmt.getCategory());
            }
        }
    }

}