package be.yorian.budgetmonitor.repository;

import be.yorian.budgetmonitor.entity.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction,Long> {

    Transaction findByDateAndNumber(LocalDate date, String number);

    @Query(value = "select tx from Transaction tx " +
            "where SUBSTRING(tx.date, 6, 2) = ?1 " +
            "and SUBSTRING(tx.date, 1, 4) = ?2", nativeQuery = true)
    List<Transaction> findByMonth(String month, String year);

    @Query(value = "select tx from Transaction tx " +
            "where SUBSTRING(tx.date, 1, 7) = ?1 " +
            "and (tx.category.id = ?2 or tx.category.id = ?3)", nativeQuery = true)
    List<Transaction> findByDateAndCategory(String date, long loon, long kindergeld);

    Page<Transaction> findByCommentContaining(String comment, Pageable pageable);

    @Query("select t from Transaction t where month (t.date) = ?1 and year(t.date) = ?2")
    List<Transaction> findByDateContainingYearAndMont(int month, int year);

    @Query("select t from Transaction t where t.category.id = ?1 and year(t.date) = ?2")
    List<Transaction> findByCategoryIdAndDateContainingYear(Long categoryId, int year);

    List<Transaction> findByCategoryId(Long categoryId);
}
