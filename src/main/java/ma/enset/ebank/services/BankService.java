package ma.enset.ebank.services;

import jakarta.transaction.Transactional;
import ma.enset.ebank.entities.BankAccount;
import ma.enset.ebank.entities.CurrentAccount;
import ma.enset.ebank.entities.SavingAccount;
import ma.enset.ebank.repositories.BankAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class BankService {
    @Autowired
    BankAccountRepository bankAccountRepository;
    public void consulter(){
        BankAccount bankAccount = bankAccountRepository.findById("316c3f06-7cc7-4a4e-8620-38512d4c3655").orElse(null);
        if(bankAccount!=null){
            System.out.println(bankAccount.getId());
            System.out.println(bankAccount.getBalance());
            System.out.println(bankAccount.getCreatedAt());
            System.out.println(bankAccount.getStatus());
            System.out.println(bankAccount.getCustomer().getName());
            if(bankAccount instanceof CurrentAccount){
                System.out.println("Over draft=>"+((CurrentAccount)bankAccount).getOverdraft());
            }else if(bankAccount instanceof SavingAccount){
                System.out.println("Rate=>"+((SavingAccount)bankAccount).getInterestRate());
            }
            bankAccount.getAccountOperations().forEach(op->{
                System.out.println(op.getType()+"\t"+op.getOperationDate()+"\t"+op.getAmount());
            });
        }
    }
}
