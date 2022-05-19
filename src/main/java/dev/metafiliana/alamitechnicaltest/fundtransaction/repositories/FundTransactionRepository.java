package dev.metafiliana.alamitechnicaltest.fundtransaction.repositories;

import dev.metafiliana.alamitechnicaltest.fundtransaction.entities.FundTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

public interface FundTransactionRepository extends JpaRepository<FundTransaction, Long> {
    @Query(
            value = "SELECT * FROM fund_transaction WHERE created_at BETWEEN :startDate AND :endDate ORDER BY created_at DESC",
            nativeQuery = true
    )
    List<FundTransaction> findByCreatedAt(Date startDate, Date endDate);

    @Query(
            value = "SELECT * FROM fund_transaction WHERE (created_at BETWEEN :startDate AND :endDate) AND member_id = :memberId ORDER BY created_at DESC",
            nativeQuery = true
    )
    List<FundTransaction> findByCreatedAtAndMemberId(Date startDate, Date endDate, Long memberId);

    @Query("SELECT SUM(ft.value) as totalFund FROM FundTransaction ft")
    BigInteger getTotalFund();
}
