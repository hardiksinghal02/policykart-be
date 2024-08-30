package com.policykart.member.repository;

import com.policykart.member.entity.Bill;
import com.policykart.member.enums.PaymentStatus;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface BillRepository extends AbstractRepository<Bill, String> {

    Optional<List<Bill>> findBySubscriptionIdAndDeletedFalse(String subscription);

    @Query("SELECT b FROM bill b WHERE b.subscriptionId = :subscriptionId ORDER BY b.generatedOn DESC")
    Optional<Bill> findLatestBillBySubscriptionId(@Param("subscriptionId") String subscriptionId);

    @Query("SELECT b FROM bill b WHERE b.paymentStatus = :paymentStatus AND b.dueDate < :currentDate")
    Optional<List<Bill>> findBillsByStatusAndDueDateBefore(
            @Param("paymentStatus") PaymentStatus paymentStatus,
            @Param("currentDate") LocalDate currentDate
    );


}
