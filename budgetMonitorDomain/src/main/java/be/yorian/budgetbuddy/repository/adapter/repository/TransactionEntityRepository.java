package be.yorian.budgetbuddy.repository.adapter.repository;

import be.yorian.budgetbuddy.repository.adapter.entity.TransactionEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TransactionEntityRepository extends JpaRepository<TransactionEntity,Long>  {

    TransactionEntity findByDateAndNumber(LocalDate date, String number);

    @Query(value = "select tx from TransactionEntity tx " +
            "where SUBSTRING(tx.date, 6, 2) = ?1 " +
            "and SUBSTRING(tx.date, 1, 4) = ?2", nativeQuery = true)
    List<TransactionEntity> findByMonth(String month, String year);

    Page<TransactionEntity> findByCommentContaining(String comment, Pageable pageable);

    @Query("""
            select t from TransactionEntity t
            where month (t.date) = ?1
            and year(t.date) = ?2""")
    List<TransactionEntity> findByDateContainingYearAndMonth(int month, int year);

    @Query("""
            select t from TransactionEntity t
            where t.category.id = ?1
            and year(t.date) = ?2""")
    List<TransactionEntity> findByCategoryIdAndDateContainingYear(Long categoryId, int year);

    List<TransactionEntity> findByCategoryId(Long categoryId);

    @Query("""
            select t from TransactionEntity t
            where year(t.date) = ?1""")
    List<TransactionEntity> findByDateContainingYear(int year);

    List<TransactionEntity> findByProjectProjectname(String projectname);

    @Query("""
            select t from TransactionEntity t
            where year(t.date) = ?1
            and t.project is not null""")
    List<TransactionEntity> findByDateContainingYearAndProjectNotNull(int year);

    @Query("""
            select t from TransactionEntity t 
            where month (t.date) = ?1 
            and year(t.date) = ?2 
            and t.project is not null""")
    List<TransactionEntity> findByDateContainingYearAndMonthAndProjectNotNull(int month, int year);
}
