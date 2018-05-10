package com.feralgoon;

import java.math.BigDecimal;
import java.util.*;

public class AllocationTool
{
    private Map<Integer, Employee> employees = new HashMap<>();
    private List<Fund> funds = new ArrayList<>();

    public void run()
    {
        funds.add(new Fund("End of World 2012"));
        funds.add(new Fund("End of TIme 2038"));
        funds.add(new Fund("Y2K Survivors"));
        funds.add(new Fund("Super Risky Optimists"));

        printHeader();

        Scanner scan = new Scanner(System.in);
        String choice;

        do
        {
            Employee employee = setUpEmployee();
            System.out.print("Would you like to see a printout of your retirement allocations?  ");
            choice = scan.nextLine();
            System.out.println();
            if (choice.equalsIgnoreCase("Y") || choice.equalsIgnoreCase("Yes"))
            {
                System.out.println();
                employee.printEnrollment();
                System.out.println();
            }
        } while (true);

    }

    private Employee setUpEmployee()
    {
        Scanner scan = new Scanner(System.in);
        String name;
        int idNum;
        BigDecimal contributionAmount;
        int choice;

        System.out.println();
        System.out.println("Setting up new Employee Retirement Account.");
        System.out.println();
        System.out.print("Enter Name: ----> ");
        name = scan.nextLine();
        System.out.print("Enter employee ID Number: ---->  ");
        idNum = scan.nextInt();
        scan.nextLine();
        do
        {
            System.out.print("Enter your contribution amount: ----> ");
            contributionAmount = scan.nextBigDecimal();
            if (contributionAmount.intValue() > 200)
            {
                System.out.println("Amount must be less than 200.");
            }

        }while (!(contributionAmount.intValue() <= 200));

        Employee employee = employees.put(idNum,new Employee(name,idNum,contributionAmount));

        int contributionTotal = 0;
        int contributionRemaining = 100;

        do
        {
            System.out.println();
            printFunds();
            System.out.println();
            System.out.print("Which fund would you like to contribute to?  ");
            choice = scan.nextInt();
            System.out.println();
            System.out.println();
            System.out.println("You have " + contributionRemaining + " percent left to allocate.");
            System.out.print("What percent would you like to contribute to " + funds.get(choice-1).getName() + "?  ");
            int contributionPercent = scan.nextInt();
            System.out.println();

            if (contributionPercent > contributionRemaining)
            {
                System.out.println("Invalid contribution amount.");
                System.out.println("Amount must be " + contributionRemaining + " or less.");
            }
            else
            {
                if (funds.get(choice-1).hasEmployee(idNum))
                {
                    funds.get(choice-1).addContributionToEmployee(idNum,contributionPercent);
                }
                else
                {
                    employees.get(idNum).addToFunds(funds.get(choice-1));
                    funds.get(choice-1).addEmployeeContribution(idNum,contributionPercent);
                }
                contributionTotal += contributionPercent;
                contributionRemaining -= contributionPercent;
            }

        }while (contributionTotal != 100);

        return employees.get(idNum);
    }

    private void printFunds()
    {
        System.out.println("Funds available to choose from:");
        System.out.println("-------------------------------");
        int index = 1;
        for(Fund f : funds)
        {
            System.out.println(index + ". " + f.getName());
            index++;
        }
    }

    private void printEnrollmentReport()
    {

    }

    private void printHeader()
    {
        System.out.println("=====================================");
        System.out.println("|| WallabyTech Retirement Services ||");
        System.out.println("=====================================");
        System.out.println();
    }

    public static void main(String[] args)
    {
        AllocationTool allocationTool = new AllocationTool();
        allocationTool.run();
    }
}
