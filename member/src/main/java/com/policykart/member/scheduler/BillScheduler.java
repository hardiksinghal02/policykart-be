package com.policykart.member.scheduler;

import com.policykart.member.service.BillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class BillScheduler {

    @Autowired
    private BillService billService;

    @Scheduled(cron = "0 1 * * *") // Every day at 01:00 AM
    public void generateBills() {
        billService.generateMonthlyBills();
    }

    @Scheduled(cron = "0 4 * * *") // Every day at 04:00 AM
    public void checkPendingBills() {
        billService.checkPendingBills();
    }
}