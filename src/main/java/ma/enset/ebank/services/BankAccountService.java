package ma.enset.ebank.services;

import ma.enset.ebank.dtos.CustomerDTO;
import ma.enset.ebank.entities.BankAccount;
import ma.enset.ebank.entities.CurrentAccount;
import ma.enset.ebank.entities.Customer;
import ma.enset.ebank.entities.SavingAccount;
import ma.enset.ebank.exceptions.BalanceNotSufficientException;
import ma.enset.ebank.exceptions.BankAccountNotFoundException;
import ma.enset.ebank.exceptions.CustomerNotFoundException;

import java.util.List;

public interface BankAccountService {
    Customer saveCustomer(Customer customer);
    CurrentAccount saveCurrentBankAccount(double initialBalance, double overDraft, Long customerId) throws CustomerNotFoundException;
    SavingAccount saveSavingBankAccount(double initialBalance, double interestRate, Long customerId) throws CustomerNotFoundException;
    List<CustomerDTO> listCustomers();
    BankAccount getBankAccount(String accountId) throws BankAccountNotFoundException;
    void debit(String accountId, double amount, String description) throws BankAccountNotFoundException, BalanceNotSufficientException;
    void credit(String accountId, double amount, String description) throws BankAccountNotFoundException;
    void transfer(String accountIdSource, String accountIdDest, double amount, String description) throws BankAccountNotFoundException, BalanceNotSufficientException;

    List<BankAccount> bankAccountList();
}
