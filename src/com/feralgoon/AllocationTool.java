package com.feralgoon;

import java.math.BigDecimal;
import java.util.*;

public class AllocationTool
{
    private Map<Integer, Employee> employees = new HashMap<>();
    private List<Fund> funds = new ArrayList<>();

    public void run()
    {
        Scanner scan = new Scanner(System.in);
        String choice;
        boolean add = true;

        funds.add(new Fund("End of World 2012"));
        funds.add(new Fund("End of TIme 2038"));
        funds.add(new Fund("Y2K Survivors"));
        funds.add(new Fund("Super Risky Optimists"));

        printHeader();

        System.out.print("Would you like to enter additional retirement plans? [Y/N]  ");
        choice = scan.nextLine();

        if (choice.equalsIgnoreCase("y"))
        {
            System.out.println();
            System.out.println("Type 'exit' at any time to quit entering new plans.");
            do
            {
                add = true;
                System.out.println("Enter the name of the new plan: ");
                choice = scan.nextLine();
                if (!choice.equalsIgnoreCase("exit"))
                {
                    for(Fund f : funds)
                    {
                        if (f.getName().equalsIgnoreCase(choice))
                        {
                            System.out.println("Fund already exists.");
                            add = false;
                        }
                    }
                }
                else
                {
                    add = false;
                }
                if (add)
                {
                    funds.add(new Fund(choice));
                }

            }while (!choice.equalsIgnoreCase("exit"));
        }

        do
        {
            setUpEmployee();
        } while (true);

    }

    private void setUpEmployee()
    {
        Scanner scan = new Scanner(System.in);
        String name;
        String idNumString;
        int idNum;
        BigDecimal contributionAmount;
        String choice;
        String contributionAmountString;

        System.out.println();
        System.out.println("Setting up new Employee Retirement Account.");
        System.out.println("Enter 'abandon' at anytime to quit.");
        System.out.println();
        System.out.print("Enter Name: ----> ");
        name = scan.nextLine();
        if (name.equalsIgnoreCase("abandon"))
        {
            return;
        }
        System.out.print("Enter employee ID Number: ---->  ");
        idNumString = scan.nextLine();
        if (idNumString.equalsIgnoreCase("abandon"))
        {
            return;
        }
        else
        {
            idNum = Integer.parseInt(idNumString);
        }

        do
        {
            System.out.print("Enter your contribution amount: ----> ");
            contributionAmountString = scan.nextLine();
            if (contributionAmountString.equalsIgnoreCase("abandon"))
            {
                return;
            }
            else
            {
                contributionAmount = new BigDecimal(contributionAmountString);
            }

            if (contributionAmount.intValue() > 200)
            {
                System.out.println("Amount must be between 10 and 200.");
            }

        }while ((contributionAmount.intValue() > 200) || (contributionAmount.intValue() < 10));

        Employee employee = new Employee(name,idNum,contributionAmount);

        employees.put(idNum,employee);

        int contributionTotal = 0;
        int contributionRemaining = 100;



        do
        {
            System.out.println();
            printFunds();
            System.out.println();
            System.out.print("Which fund would you like to contribute to?  ");
            choice = scan.nextLine();
            int selection;
            if (choice.equalsIgnoreCase("Abandon"))
            {
                return;
            }
            else
            {
                selection = Integer.parseInt(choice);
            }
            System.out.println();
            System.out.println();
            System.out.println("You have " + contributionRemaining + " percent left to allocate.");
            System.out.print("What percent would you like to contribute to " + funds.get(selection-1).getName() + "?  ");
            String contributionInput = scan.nextLine();
            int contributionPercent;
            if (contributionInput.equalsIgnoreCase("Abandon"))
            {
                return;
            }
            else
            {
                contributionPercent = Integer.parseInt(contributionInput);
            }
            System.out.println();

            if (contributionPercent > contributionRemaining)
            {
                System.out.println("Invalid contribution amount.");
                System.out.println("Amount must be " + contributionRemaining + " or less.");
            }
            else
            {
                if (funds.get(selection-1).hasEmployee(idNum))
                {
                    funds.get(selection-1).addContributionToEmployee(idNum,contributionPercent);
                    employee.updateEmployerContribution(funds.get(selection-1),contributionPercent);
                }
                else
                {
                    funds.get(selection-1).addEmployeeContribution(idNum,contributionPercent);
                    employees.get(idNum).addToFunds(funds.get(selection-1));
                }
                contributionTotal += contributionPercent;
                contributionRemaining -= contributionPercent;
            }

        }while (contributionTotal != 100);

        System.out.print("Would you like to see a printout of your retirement allocations?  ");
        choice = scan.nextLine();
        System.out.println();
        if (choice.equalsIgnoreCase("Y") || choice.equalsIgnoreCase("Yes"))
        {
            System.out.println();
            employee.printEnrollment();
            System.out.println();
        }
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
