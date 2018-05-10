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

    public Employee(String name, int idNum, BigDecimal contribution)
    {
        this.name = name;
        this.idNum = idNum;
        this.contribution = contribution;
    }

    public void addToFunds(Fund fund)
    {
        funds.add(fund);
    }

    public void printEnrollment()
    {
        System.out.println("Employee Name:\t\t" + this.name);
        System.out.println("Employee Id:  \t\t" + this.idNum);
        System.out.println();
        System.out.println("Fund\t\t\t\t\t\tPercent\t\tAmount/Pay Period");
        System.out.println("---------------------------------------------------------");
        for (Fund f : funds)
        {
            System.out.println(String.format("%-20s",f.getName()) + "\t\t\t" + f.getContributionPercent(idNum) + "\t\t\t" + amountPerPeriod(f));
        }
        System.out.println();
        System.out.println("Total Contribution Per Pay Period: " + contribution);
    }

    private BigDecimal amountPerPeriod(Fund fund)
    {
        BigDecimal percent = BigDecimal.valueOf(fund.getContributionPercent(idNum));

        return contribution.multiply(percent.movePointLeft(2)).setScale(2,BigDecimal.ROUND_HALF_DOWN);
    }

}
