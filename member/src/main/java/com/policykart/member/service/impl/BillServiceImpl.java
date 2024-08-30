package com.policykart.member.service.impl;

import com.policykart.member.entity.Bill;
import com.policykart.member.entity.Subscription;
import com.policykart.member.enums.PaymentStatus;
import com.policykart.member.repository.BillRepository;
import com.policykart.member.repository.SubscriptionRepository;
import com.policykart.member.service.BillService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class BillServiceImpl implements BillService {

    private final Logger logger = LoggerFactory.getLogger(BillServiceImpl.class);

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    @Autowired
    private BillRepository billRepository;


    @Override
    public void generateMonthlyBills() {
        Optional<List<Subscription>> optionalSubscriptionList = subscriptionRepository.findByActiveTrue();

        if (optionalSubscriptionList.isEmpty() || CollectionUtils.isEmpty(optionalSubscriptionList.get())) {
            logger.error("No active subscriptions found");
            return;
        }

        List<Subscription> activeSubscriptions = optionalSubscriptionList.get();

        LocalDate date30DaysAgo = LocalDate.now().minusDays(30);

        for (Subscription subscription : activeSubscriptions) {
            Optional<Bill> lastBillOptional = billRepository.findLatestBillBySubscriptionId(subscription.getId());

            if (lastBillOptional.isPresent() && lastBillOptional.get().getGeneratedOn().isBefore(date30DaysAgo)) {
                generateBill(lastBillOptional.get());
            }
        }
    }

    @Override
    public void checkPendingBills() {

        Optional<List<Bill>> overdueBills =
                billRepository.findBillsByStatusAndDueDateBefore(PaymentStatus.PENDING, LocalDate.now());

        if (overdueBills.isEmpty()) {
            return;
        }

        for (Bill b : overdueBills.get()) {
            b.setPaymentStatus(PaymentStatus.OVERDUE);
            b.setLateFee((b.getAmount() + b.getPreviousOutstandingAmount()) * 0.1);
            billRepository.save(b);
        }
    }

    private void generateBill(Bill lastBill) {
        Bill newBill = Bill.builder()
                .subscriptionId(lastBill.getSubscriptionId())
                .memberId(lastBill.getMemberId())
                .amount(lastBill.getAmount())
                .previousOutstandingAmount(
                        PaymentStatus.PAID.equals(lastBill.getPaymentStatus()) ? 0 :
                                lastBill.getAmount() + lastBill.getPreviousOutstandingAmount() + lastBill.getLateFee()
                )
                .lateFee((double) 0)
                .generatedOn(LocalDate.now())
                .dueDate(LocalDate.now().plusDays(5))
                .build();

        billRepository.save(newBill);
    }
}
