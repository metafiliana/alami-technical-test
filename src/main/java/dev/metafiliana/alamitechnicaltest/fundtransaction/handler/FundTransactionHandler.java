package dev.metafiliana.alamitechnicaltest.fundtransaction.handler;

import dev.metafiliana.alamitechnicaltest.fundtransaction.entities.FundTransaction;
import dev.metafiliana.alamitechnicaltest.fundtransaction.services.FundTransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api")
public class FundTransactionHandler {
    @Autowired
    private FundTransactionService fundTransactionSvc;

    @GetMapping("/fund-transactions/histories")
    public ResponseEntity<List<FundTransaction>> getFundTransactionHistories(
            @RequestParam(required = true) @DateTimeFormat(pattern="yyyy-MM-dd") Date startDate,
            @RequestParam(required = true) @DateTimeFormat(pattern="yyyy-MM-dd") Date endDate,
            @RequestParam(required = false, defaultValue = "") Long memberId) {
        List<FundTransaction> fundTransactions;
        if (memberId != null) {
            fundTransactions = fundTransactionSvc.getHistoryByDateAndMember(startDate, endDate, memberId);
        } else {
            fundTransactions = fundTransactionSvc.getHistoryByDate(startDate, endDate);
        }

        return new ResponseEntity<>(fundTransactions, HttpStatus.OK);
    }

    @PostMapping("/fund-transactions")
    public ResponseEntity<FundTransaction> createFundTransaction(
            @RequestBody FundTransaction fundTransaction) {
        FundTransaction newFundTransaction = fundTransactionSvc.createFundTransaction(fundTransaction);
        return new ResponseEntity<>(newFundTransaction, HttpStatus.CREATED);
    }
}
