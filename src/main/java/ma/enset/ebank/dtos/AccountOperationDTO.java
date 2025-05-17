package ma.enset.ebank.dtos;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.enset.ebank.entities.BankAccount;
import ma.enset.ebank.enums.OperationType;

import java.util.Date;
@Data
public class AccountOperationDTO {
    private Long id;
    private Date operationDate;
    private double amount;
    private String description;
    private OperationType type;
    private BankAccount bankAccount;
}
