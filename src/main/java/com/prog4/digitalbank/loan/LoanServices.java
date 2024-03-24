package com.prog4.digitalbank.loan;

import com.prog4.digitalbank.CrudOperations.FindAll;
import com.prog4.digitalbank.CrudOperations.FindById;
import com.prog4.digitalbank.CrudOperations.Save;
import com.prog4.digitalbank.account.Account;
import com.prog4.digitalbank.account.AccountServices;
import com.prog4.digitalbank.insertGeneralisation.InsertServices;
import com.prog4.digitalbank.methods.Conversion;
import com.prog4.digitalbank.methods.IdGenerators;
import com.prog4.digitalbank.methods.InterestCalcul;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class LoanServices {
    private Save<LoanEvolution> loanEvolutionSave;
    private Save<BankLoan> bankLoanSave;
    private AccountServices accountServices;
    private InsertServices insertServices;
    private  LoanRepository loanRepository;
    private FindById<BankLoan> bankLoanFindById;
    private FindAll<BankLoan> bankLoanFindAll;
    private BankLoan bankLoanSave (BankLoan bankLoan) throws SQLException {
        return bankLoanSave.insert(bankLoan);
    }



    private LoanEvolution loanEvolutionSave (LoanEvolution loanEvolution ) throws SQLException {

        return loanEvolutionSave.insert(loanEvolution);
    }

    private boolean checkEligility (BankLoan bankLoan , Class<Account> accountClass){
        Account account = accountServices.findById(accountClass, bankLoan.getAccountId());
        Boolean auth = account.getLoanAuthorization();
        Double amount = bankLoan.getAmount();
        Double salary = account.getMonthlyPay();
        if (!auth){
            return false;
        }
        if(salary < amount / 3){
            return false;

        }
        if (findByAccountId(bankLoan.getAccountId()).size() > 0){
            return false;
        }
        return true;
    }


    public BankLoan loanOperation(BankLoan bankLoan) throws SQLException {
        if (checkEligility(bankLoan,Account.class)){
            String id = IdGenerators.generateId(12);
            Double amount = bankLoan.getAmount();
            Double interest1 = bankLoan.getInterestSevenDay();
            String accountId = bankLoan.getAccountId();
            Double interest2 = bankLoan.getInterestAboveSevenDay();
            Date date = bankLoan.getLoanDate();
            Timestamp timestamp = Conversion.DateToTimestamp(date);
            BankLoan bankLoan1 = new BankLoan(id,amount,date,interest1,accountId,interest2,"unpaid");
            bankLoanSave.insert(bankLoan1);
            String idEvolution = IdGenerators.generateId(12);
            LoanEvolution loanEvolution = new LoanEvolution(idEvolution,timestamp,0.0,amount,id);
            loanEvolutionSave.insert(loanEvolution);
            insertServices.insertTransaction(accountId,
                    amount,
                    date,
                    "credit",
                    id,
                    "loan",
                    29);
            return bankLoan1;
        }
        BankLoan error = new BankLoan("your are not allowed for this operation (check your loan authorization or your monthly pay is not enough)");
        return error;
    }

    public List<BankLoan> findByAccountId(String accountId){
        return loanRepository.findByAccountId(accountId);
    }
    public List<BankLoan> findBankLoanByAccountId(String accountId){
        return bankLoanFindById.findByAccountId(BankLoan.class,accountId,"order by loan_date desc limit 1","loan_date <= current_date");
    }

    public List<BankLoan> findAll() throws SQLException {
        return bankLoanFindAll.findAll(BankLoan.class , "where status = 'unpaid' ");

    }
    @Scheduled(fixedDelay = Long.MAX_VALUE)
    public void addInterest() throws SQLException {
       List<BankLoan> bankLoans = findAll();
       for (BankLoan bankLoan : bankLoans){
           LoanEvolution loanEvolution = loanRepository.getLastState(bankLoan.getId());
           Double rest = loanEvolution.getRest();
           Date date = new Date(loanEvolution.getDateTime().getTime());
           if (rest != 0 && date.before(Date.valueOf(LocalDate.now()))){
               String id = IdGenerators.generateId(12);
               Timestamp dateTime = Timestamp.valueOf(LocalDateTime.now());
               Timestamp startDate = Conversion.DateToTimestamp(bankLoan.getLoanDate());
               Double totalInterest = InterestCalcul.interest(startDate,
                       dateTime,bankLoan.getInterestSevenDay(),
                       bankLoan.getInterestAboveSevenDay(),
                       bankLoan.getAmount());
               Double actualInterest = totalInterest - loanEvolution.getTotalInterest();
               Double newRest = rest + actualInterest;
               LoanEvolution save = new LoanEvolution(id , dateTime , totalInterest , newRest , bankLoan.getId());
               loanEvolutionSave(save);
           }
       }


    }

    @Scheduled(fixedRate = 5000)
    public void updateStatusBankLoan(){
        loanRepository.updateBankLoanStatus();
    }

}
