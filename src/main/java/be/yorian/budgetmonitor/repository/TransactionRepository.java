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

    Page<Transaction> findByCommentContaining(String comment, Pageable pageable);

    @Query("""
            select t from Transaction t
            where month (t.date) = ?1
            and year(t.date) = ?2""")
    List<Transaction> findByDateContainingYearAndMonth(int month, int year);

    @Query("""
            select t from Transaction t
            where t.category.id = ?1
            and year(t.date) = ?2""")
    List<Transaction> findByCategoryIdAndDateContainingYear(Long categoryId, int year);

    List<Transaction> findByCategoryId(Long categoryId);

    @Query("""
            select t from Transaction t
            where year(t.date) = ?1""")
    List<Transaction> findByDateContainingYear(int year);

    List<Transaction> findByProjectProjectname(String projectname);

    @Query("""
            select t from Transaction t
            where year(t.date) = ?1
            and t.project is not null""")
    List<Transaction> findByDateContainingYearAndProjectNotNull(int year);

    @Query("""
            select t from Transaction t 
            where month (t.date) = ?1 
            and year(t.date) = ?2 
            and t.project is not null""")
    List<Transaction> findByDateContainingYearAndMonthAndProjectNotNull(int month, int year);
}
