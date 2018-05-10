package com.feralgoon;

import java.util.HashMap;
import java.util.Map;

public class Fund
{
    private String name;
    private Map<Integer, Integer> employeesContributing;

    public Fund(String name)
    {
        this.name = name;
        employeesContributing = new HashMap<>();
    }

    public void addEmployeeContribution(int empId,int amount)
    {
        employeesContributing.put(empId,amount);
    }

    public String getName()
    {
        return name;
    }

    public int getContributionPercent(int idNum)
    {
        return employeesContributing.get(idNum);
    }

    public void addContributionToEmployee(int idNum, int amount)
    {
        employeesContributing.put(idNum,employeesContributing.get(idNum) + amount);
    }

    public boolean hasEmployee(int idNum)
    {
        boolean result = false;

        if (employeesContributing.containsKey(idNum))
        {
            result = true;
        }
        return result;
    }
}
