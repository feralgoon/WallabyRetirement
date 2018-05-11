package com.feralgoon;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Employee
{
    private String name;
    private int idNum;
    private BigDecimal contribution;
    private List<Fund> funds = new ArrayList<>();
    EmployerContribution employerContribution;

    public Employee(String name, int idNum, BigDecimal contribution)
    {
        this.name = name;
        this.idNum = idNum;
        this.contribution = contribution;
        if (contribution.compareTo(new BigDecimal("50.00")) >= 0)
        {
            employerContribution = new EmployerContribution(new BigDecimal("25.00"));
        }
        else
        {
            BigDecimal empContribution = contribution.multiply(new BigDecimal("0.50"));
            employerContribution = new EmployerContribution(empContribution);
        }
    }

    public void updateEmployerContribution(Fund f, int percent)
    {
        employerContribution.addContribution(f,percent + f.getContributionPercent(idNum));
    }

    public void addToFunds(Fund fund)
    {
        funds.add(fund);
        employerContribution.addContribution(fund,fund.getContributionPercent(idNum));
    }

    public void printEnrollment()
    {
        System.out.println("Employee Name:\t\t" + this.name);
        System.out.println("Employee Id:  \t\t" + this.idNum);
        System.out.println();
        System.out.println("Fund\t\t\t\t\t\tPercent\t\tAmount/Pay Period\t\tEmployer Contribution\t\tTotal");
        System.out.println("-----------------------------------------------------------------------------------------------");
        for (Fund f : funds)
        {
            System.out.println(String.format("%-20s",f.getName()) + "\t\t\t" + f.getContributionPercent(idNum) + "\t\t\t\t" + amountPerPeriod(f)
                                + "\t\t\t\t" + employerAmountPerPeriod(f) + "\t\t\t\t" + employerAmountPerPeriod(f).add(amountPerPeriod(f)));
        }
        System.out.println();
        System.out.println(String.format("%-45s","Total Contribution Per Pay Period: ") + contribution);
        System.out.println(String.format("%-45s","Total Employer Contribution Per Pay Period: ") + employerContribution.getAmount());
    }

    private BigDecimal amountPerPeriod(Fund fund)
    {
        BigDecimal percent = BigDecimal.valueOf(fund.getContributionPercent(idNum));

        return contribution.multiply(percent.movePointLeft(2)).setScale(2,BigDecimal.ROUND_HALF_DOWN);
    }

    private BigDecimal employerAmountPerPeriod(Fund fund)
    {
        BigDecimal percent = BigDecimal.valueOf(employerContribution.getContributionPercent(fund));

        return employerContribution.getAmount().multiply(percent.movePointLeft(2)).setScale(2,BigDecimal.ROUND_HALF_DOWN);
    }
}
