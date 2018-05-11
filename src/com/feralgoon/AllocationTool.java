package com.feralgoon;

import java.math.BigDecimal;
import java.util.*;

public class AllocationTool
{
    private Map<Integer, Employee> employees = new HashMap<>();
    private List<Fund> funds = new ArrayList<>();

    public void run()
    {
        printHeader();
        addDefaultFunds();

        boolean keepGoing;

        do
        {
            Scanner scan = new Scanner(System.in);
            String choice;

            printOptions();
            System.out.print("Enter your selection ---->  ");
            choice = scan.nextLine();
            keepGoing = handleOptionChoice(choice);

        } while (keepGoing);

        System.out.println("Exiting Program...");
        System.out.println("Goodbye!");
    }

    private boolean handleOptionChoice(String choice)
    {
        boolean falseIfExit = true;
        int selection;

        try
        {
            selection = Integer.parseInt(choice);
        }catch (Exception e)
        {
            System.out.println("Invalid Selection.");
            return falseIfExit;
        }

        switch (selection)
        {
            case 1:
                listEnrolledEmployees();
                break;
            case 2:
                printRetirementStatement();
                break;
            case 3:
                printFundSummary();
                break;
            case 4:
                setUpEmployee();
                break;
            case 5:
                printTimeSummary();
                break;
            case 6:
                addRetirementFunds();
                break;
            case 7:
                falseIfExit = false;
                break;
            default:
                System.out.println("Invalid selection.");
        }

        return falseIfExit;
    }

    private void listEnrolledEmployees()
    {
        System.out.println("Currently Enrolled Employees");
        System.out.println();
        System.out.println(String.format("%-25s","Name") + "\tID Number");
        System.out.println("------------------------------------");
        for(int id : employees.keySet())
        {
            System.out.println(String.format("%-25s",employees.get(id)) + "\t" + id);
        }
    }

    private void printRetirementStatement()
    {
        Scanner scan = new Scanner(System.in);
        System.out.print("Enter the employee ID number to see their retirement summary: ---->  ");
        int iDNum = scan.nextInt();

        if (employees.containsKey(iDNum))
        {
            System.out.println();
            System.out.println();
            employees.get(iDNum).printEnrollment();
        }
        else
        {
            System.out.println();
            System.out.println("ID Number " + iDNum + " is not currently enrolled.");
        }
    }

    private void printFundSummary()
    {
        System.out.println();
        System.out.println(String.format("%-20s","Fund name") + "\tEmployees Enrolled\t\tTotal Amount Invested");
        System.out.println("---------------------------------------------------------------------");
        for(Fund f : funds)
        {
            System.out.println(String.format("%-20s",f.getName()) + "\t\t\t" + f.getNumberEnrolled() + "\t\t\t\t\t" + f.getTotalInvested());
        }
        System.out.println();
    }

    private void printTimeSummary()
    {
        long totalTimeTakenToEnroll = 0;
        Employee longestTime = null;
        Employee shortestTime = null;

        for (int id : employees.keySet())
        {
            totalTimeTakenToEnroll += employees.get(id).getTimeTakenToEnroll();
            longestTime = employees.get(id);
            shortestTime = employees.get(id);
        }

        long averageTimeTakenToEnroll = totalTimeTakenToEnroll/employees.size();

        for(Employee employee : employees.values())
        {
            if (employee.getTimeTakenToEnroll() > longestTime.getTimeTakenToEnroll())
            {
                longestTime = employee;
            }
            if (employee.getTimeTakenToEnroll() < shortestTime.getTimeTakenToEnroll())
            {
                shortestTime = employee;
            }
        }

        System.out.println();
        System.out.println("Enrollment Time Summary");
        System.out.println();
        System.out.println("Total time spent on enrollment :  " + totalTimeTakenToEnroll + "ms");
        System.out.println("Average time taken to enroll   :  " + averageTimeTakenToEnroll + "ms");
        System.out.println("Fastest employee to enroll     :  " + shortestTime);
        System.out.println("Slowest employee to enroll     :  " + longestTime);
        System.out.println();
    }
    private void addDefaultFunds()
    {
        funds.add(new Fund("End of World 2012"));
        funds.add(new Fund("End of TIme 2038"));
        funds.add(new Fund("Y2K Survivors"));
        funds.add(new Fund("Super Risky Optimists"));
    }

    private void addRetirementFunds()
    {
        Scanner scan = new Scanner(System.in);
        String choice;
        boolean add;

        System.out.println();
        System.out.println("Type 'exit' at any time to quit entering new funds.");
        do
        {
            add = true;
            System.out.println("Enter the name of the new fund: ");
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
                System.out.println("Added " + choice + " to list of available Funds.");
            }

        }while (!choice.equalsIgnoreCase("exit"));
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
        long startEnrollTime = System.currentTimeMillis();

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
            if (employees.containsKey(idNum))
            {
                System.out.println("Invalid employee ID. Employee already in system.");
                return;
            }
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
                    funds.get(selection-1).addEmployeeContribution(idNum,contributionPercent,employee);
                    employee.addToFunds(funds.get(selection-1));
                }
                contributionTotal += contributionPercent;
                contributionRemaining -= contributionPercent;
            }

        }while (contributionTotal != 100);

        long endEnrollTime = System.currentTimeMillis();

        employee.setTimeTakenToEnroll(endEnrollTime - startEnrollTime);

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

    private void printOptions()
    {
        System.out.println("\nChoose one of the following options: \n");
        System.out.println("1. List Enrolled Employees");
        System.out.println("2. Print Employee Retirement Statement");
        System.out.println("3. Show Fund Details");
        System.out.println("4. Enroll New Employee");
        System.out.println("5. Show Time Summary");
        System.out.println("6. Add New Retirement Funds");
        System.out.println("7. Exit Program");
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
