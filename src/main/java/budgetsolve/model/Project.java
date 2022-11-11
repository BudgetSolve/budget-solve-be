package budgetsolve.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Currency;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    private String email;

    @ManyToOne
    private User createdBy;

    private LocalDateTime created;

    private LocalDateTime lastEdited;

    private BigDecimal budget;

    private BigDecimal profit;

    private BigDecimal value;

    private Currency currency;
}
