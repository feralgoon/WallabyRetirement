package com.feralgoon;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class EmployerContribution
{
    private Map<Fund,Integer> contributions;
    private BigDecimal amount;

    public EmployerContribution(BigDecimal amount)
    {
        this.amount = amount;
        contributions = new HashMap<>();
    }

    public void addContribution(Fund f, int percent)
    {
        contributions.put(f,percent);
    }

    public int getContributionPercent(Fund f)
    {
        return contributions.get(f);
    }

    public BigDecimal getAmount()
    {
        return amount;
    }
}
