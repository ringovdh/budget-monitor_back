package be.yorian.budgetmonitor.response;

import be.yorian.budgetmonitor.dto.BudgetOverviewPerCategory;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_DEFAULT;

@JsonInclude(NON_DEFAULT)
public class BudgetOverviewResponse {

    List<BudgetOverviewPerCategory> outgoingBudget;
    List<BudgetOverviewPerCategory> incommingBudget;
    List<BudgetOverviewPerCategory> fixedcostBudget;
}
