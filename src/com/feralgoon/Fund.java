package com.feralgoon;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Fund
{
    private String name;
    private Map<Integer, Integer> employeeContributionPercent;
    private Map<Integer, Employee> employeesInFund;


    public Fund(String name)
    {
        this.name = name;
        employeeContributionPercent = new HashMap<>();
        employeesInFund = new HashMap<>();
    }

    public void addEmployeeContribution(int empId,int amount,Employee employee)
    {
        employeeContributionPercent.put(empId,amount);
        employeesInFund.put(empId, employee);
    }

    public String getName()
    {
        return name;
    }

    public int getContributionPercent(int idNum)
    {
        return employeeContributionPercent.get(idNum);
    }

    public void addContributionToEmployee(int idNum, int amount)
    {
        employeeContributionPercent.put(idNum, employeeContributionPercent.get(idNum) + amount);
    }

    public boolean hasEmployee(int idNum)
    {
        boolean result = false;

        if (employeeContributionPercent.containsKey(idNum))
        {
            result = true;
        }
        return result;
    }

    public BigDecimal getTotalInvested()
    {
        BigDecimal result = new BigDecimal("0.00");

        for(int idNum : employeeContributionPercent.keySet())
        {
            result = result.add(employeesInFund.get(idNum).amountPerPeriod(this));
        }

        return result;
    }

    public int getNumberEnrolled()
    {
        return employeeContributionPercent.size();
    }
}
