package budgetsolve.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Currency;

@Getter
@Setter
@AllArgsConstructor
public class ProjectDetails {
    private Long id;

    private String name;

    private User createdBy;

    private LocalDateTime created;

    private LocalDateTime lastEdited;

    private BigDecimal budget;

    private BigDecimal profit;

    private BigDecimal value;

    private Currency currency;
}
