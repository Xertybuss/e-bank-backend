package ma.enset.ebank;

import ma.enset.ebank.dtos.BankAccountDTO;
import ma.enset.ebank.dtos.CurrentBankAccountDTO;
import ma.enset.ebank.dtos.CustomerDTO;
import ma.enset.ebank.dtos.SavingBankAccountDTO;
import ma.enset.ebank.entities.*;
import ma.enset.ebank.enums.AccountStatus;
import ma.enset.ebank.enums.OperationType;
import ma.enset.ebank.exceptions.BalanceNotSufficientException;
import ma.enset.ebank.exceptions.BankAccountNotFoundException;
import ma.enset.ebank.exceptions.CustomerNotFoundException;
import ma.enset.ebank.repositories.AccountOperationRepository;
import ma.enset.ebank.repositories.BankAccountRepository;
import ma.enset.ebank.repositories.CustomerRepository;
import ma.enset.ebank.services.BankAccountService;
import ma.enset.ebank.services.BankService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

@SpringBootApplication
public class EbankApplication {

    public static void main(String[] args) {
        SpringApplication.run(EbankApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(BankAccountService bankAccountService) {
        return args -> {
            Stream.of("Hassan", "Imane", "Mohammed").forEach(name -> {
                CustomerDTO customer = new CustomerDTO();
                customer.setName(name);
                customer.setEmail(name + "@email.com");
                bankAccountService.saveCustomer(customer);
            });
            bankAccountService.listCustomers().forEach(customer -> {
                try {
                    bankAccountService.saveCurrentBankAccount(Math.random() * 90000, 9000, customer.getId());
                    bankAccountService.saveSavingBankAccount(Math.random() * 120000, 5.5, customer.getId());
                    List<BankAccountDTO> bankAccounts = bankAccountService.bankAccountList();
                    for(BankAccountDTO bankAccount : bankAccounts) {
                        for(int i = 0; i < 10; i++){
                            String accountId;
                            if(bankAccount instanceof SavingBankAccountDTO){
                                 accountId=((SavingBankAccountDTO) bankAccount).getId();
                            }else {
                                accountId = ((CurrentBankAccountDTO) bankAccount).getId();
                            }
                            bankAccountService.credit(accountId, 10000+Math.random()*120000, "Credit");
                            bankAccountService.debit(accountId, 1000+Math.random()*9000, "Debit");
                        }
                    }
                } catch (CustomerNotFoundException e) {
                    e.printStackTrace();
                } catch (BankAccountNotFoundException | BalanceNotSufficientException e) {
                    e.printStackTrace();
                }
            });
        };
    }
    //@Bean
    CommandLineRunner start(CustomerRepository customerRepository,
                            BankAccountRepository bankAccountRepository,
                            AccountOperationRepository accountOperationRepository) {
        return args -> {
            /*Stream.of("Hassan","Yassine","Aicha").forEach(name->{
                Customer customer = new Customer();
                customer.setName(name);
                customer.setEmail(name+"@gmail.com");
                customerRepository.save(customer);
            });
            customerRepository.findAll().forEach(customer -> {
                CurrentAccount currentAccount = new CurrentAccount();
                currentAccount.setId(UUID.randomUUID().toString());
                currentAccount.setBalance(Math.random()*90000);
                currentAccount.setCreatedAt(new Date());
                currentAccount.setStatus(AccountStatus.CREATED);
                currentAccount.setCustomer(customer);
                currentAccount.setOverdraft(9000);
                bankAccountRepository.save(currentAccount);

                SavingAccount savingAccount = new SavingAccount();
                savingAccount.setId(UUID.randomUUID().toString());
                savingAccount.setBalance(Math.random()*90000);
                savingAccount.setCreatedAt(new Date());
                savingAccount.setStatus(AccountStatus.CREATED);
                savingAccount.setCustomer(customer);
                savingAccount.setInterestRate(5.5);
                bankAccountRepository.save(savingAccount);
            });
            bankAccountRepository.findAll().forEach(bankAccount -> {
                for(int i = 0; i < 5; i++){
                    AccountOperation accountOperation = new AccountOperation();
                    accountOperation.setOperationDate(new Date());
                    accountOperation.setAmount(Math.random()*12000);
                    accountOperation.setType(Math.random() > 0.5 ? OperationType.DEBIT : OperationType.CREDIT);
                    accountOperation.setBankAccount(bankAccount);
                    accountOperationRepository.save(accountOperation);

                }
            });*/
        };
    }
}
