package dev.metafiliana.alamitechnicaltest.fundtransaction.services;

import dev.metafiliana.alamitechnicaltest.exception.GeneralBadRequestException;
import dev.metafiliana.alamitechnicaltest.exception.GeneralDBException;
import dev.metafiliana.alamitechnicaltest.exception.GeneralNotFoundException;
import dev.metafiliana.alamitechnicaltest.fundtransaction.entities.FundTransaction;
import dev.metafiliana.alamitechnicaltest.fundtransaction.repositories.FundTransactionRepository;
import dev.metafiliana.alamitechnicaltest.member.entities.Member;
import dev.metafiliana.alamitechnicaltest.member.services.MemberService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.*;

@Service
public class FundTransactionService {
    private static final Logger logger = LoggerFactory.getLogger(FundTransactionService.class);
    @Autowired
    private FundTransactionRepository fundTransactionRepository;
    @Autowired
    private MemberService memberSvc;

    public List<FundTransaction> getHistoryByDate(Date startDate, Date endDate) {
        List<FundTransaction> fundTransactions;
        try {
            fundTransactions = fundTransactionRepository.findByCreatedAt(startDate, endDate);
            if (fundTransactions.isEmpty()) {
                throw new GeneralNotFoundException();
            }

            // get member detail
            HashSet<Long> memberIds = new HashSet<Long>();
            fundTransactions.forEach((n) -> memberIds.add(n.getMemberId()));
            HashMap<Long, Member> mapMembers = memberSvc.getMemberByIds(memberIds);

            // map member detail
            for (int i = 0; i < fundTransactions.size(); i++)
                if (mapMembers.containsKey(fundTransactions.get(i).getMemberId())) {
                    fundTransactions.get(i).setMemberName(mapMembers.get(fundTransactions.get(i).getMemberId()).getName());
                }

            return fundTransactions;
        } catch (Exception e) {
            throw e;
        }
    }

    public List<FundTransaction> getHistoryByDateAndMember(Date startDate, Date endDate, Long memberId) {
        List<FundTransaction> fundTransactions;
        try {
            fundTransactions = fundTransactionRepository.findByCreatedAtAndMemberId(startDate, endDate, memberId);
            if (fundTransactions.isEmpty()) {
                throw new GeneralNotFoundException();
            }

            // get member detail & assign value
            Member member = memberSvc.getMemberById(memberId);

            // map member detail
            for (int i = 0; i < fundTransactions.size(); i++)
                fundTransactions.get(i).setMemberName(member.getName());

            return fundTransactions;
        } catch (Exception e) {
            throw e;
        }
    }

    @Transactional
    public FundTransaction createFundTransaction(FundTransaction fundTransaction) {
        try {
            BigInteger transactionValue = fundTransaction.getValue();

            // validate the value, must positive
            if (transactionValue.compareTo(BigInteger.valueOf(0)) == -1) {
                logger.error("createFundTransaction error, given transaction value <= 0");
                throw new GeneralBadRequestException();
            }

            // validate the state, must within enum
            if (fundTransaction.getState() != FundTransaction.FundTransactionState_Saving
                    && fundTransaction.getState() != FundTransaction.FundTransactionState_Lending) {
                logger.error("createFundTransaction error, state are not saving or lending enum");
                throw new GeneralBadRequestException();
            }

            // get current total fund available
            BigInteger totalFund = fundTransactionRepository.getTotalFund();

            // validate current total fund to desired lending fund
            if (fundTransaction.getState() == FundTransaction.FundTransactionState_Lending) {
                if (totalFund.compareTo(transactionValue) == -1) {
                    logger.error("createFundTransaction error, total fund less than transaction value");
                    throw new GeneralBadRequestException();
                }
                // negate the value because of the transaction type is lending
                transactionValue = transactionValue.negate();
            }

            FundTransaction newFundTransaction = new FundTransaction();
            newFundTransaction.setMemberId(fundTransaction.getMemberId());
            newFundTransaction.setState(fundTransaction.getState());
            newFundTransaction.setValue(transactionValue);

            return fundTransactionRepository.save(newFundTransaction);
        } catch (Exception e) {
            throw e;
        }
    }
}
