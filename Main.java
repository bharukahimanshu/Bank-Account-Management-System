import java.util.*;
import java.lang.*;
import java.io.*;
import java.time.LocalDate;

class Loan{
    public static int LoanNo=0;
    public float Amount;
    final double r=0.01;
    public int installment;
    public Customer c=null;
    public double EMI;
    Loan(float Amount, int installment, Customer c){
        LoanNo+=1;
        this.Amount=Amount;
        this.installment=installment;
        this.c=c;
        System.out.println(Amount);
        System.out.println(installment);
        EMI=getEMI(r,Amount,installment);
        System.out.println("Your Loan Details");
        System.out.println("Loan no-"+LoanNo);
        System.out.printf("EMI:- %.2f",EMI);
        System.out.println();
    }
    public double getEMI(double r,float p,int n){
        double emi = p*r*(Math.pow((1+r),n)/(Math.pow((1+r),n)+1));
        return emi;
    }
    public void payment(){
        installment--;
    }
}

class Customer{
    private String name,address,phone;
    public static int CustNo=100;
    public Savings_Account saving_ac=null;
    public Current_Account current_ac=null;
    List<Loan>allLoans=new ArrayList<Loan>();
    Customer(String name, String address, String phone){
        CustNo=CustNo+1;
        this.name=name;
        this.address=address;
        this.phone=phone;
        System.out.println("Your Customer no-"+CustNo);
    }
    public void addLoan(Loan loan){
        allLoans.add(loan);
    }
    public void removeLoan(Loan loan){
        for (Loan i:allLoans) {
            if(i==loan){
                allLoans.remove(i);
                break;
            }
        }
    }
    public void getDetails(){
        System.out.println("Your Personal Details");
        System.out.println("Name:- "+name);
        System.out.println("Address:- "+address);
        System.out.println("Phone no:- "+phone);
        System.out.println("Your Account Details");
        if(saving_ac!=null){
            System.out.println("Savings Account");
            System.out.println("Account number= "+saving_ac.AccNo+" Balance= "+saving_ac.Balance);
        }
        if(current_ac!=null){
            System.out.println("Current Account");
            System.out.println("Account number= "+current_ac.AccNo+" Balance= "+current_ac.Balance);
        }
        System.out.println("Your Loan Details");
        System.out.println("Loan no  installment left EMI");
        for (Loan i:allLoans) {
            System.out.print(i.LoanNo+" "+i.installment+" ");
            System.out.printf("%.2f",i.EMI);
            System.out.println();
        }
    }
}

class Savings_Account extends Account{
    private LocalDate date_of_opening = LocalDate.now();
    Savings_Account(Customer AccHolder){
        super(AccHolder,2000);
    }
}

class Current_Account extends Account{
    
    private LocalDate date_of_opening = LocalDate.now();
    Current_Account(Customer AccHolder){
        super(AccHolder,5000);
    }
}

class Account{
    public int Balance;
    public static int AccNo=1000;
    Customer AccHolder;
    Account(Customer AccHolder, int Balance){
        AccNo+=1;
        this.Balance=Balance;
        this.AccHolder=AccHolder;
        System.out.println("Your Account no:- "+AccNo);
    }
    public void debitAmount(int amount){
        if(Balance>=amount){
            Balance-=amount;
        }
    }
    public void creditAmount(int amount){
        Balance+=amount;
    }
    public int getBalance(){
        return Balance;
    }
}

class Branch {
    public String BranchCode, city;
    List<Savings_Account>allSavingsAccounts=new ArrayList<Savings_Account>();
    List<Current_Account>allCurrentAccounts=new ArrayList<Current_Account>();
    List<Customer>allCustomers=new ArrayList<Customer>();
    List<Loan>allLoans=new ArrayList<Loan>();
    
    Branch(String BranchCode, String city){
        this.city=city;
        this.BranchCode=BranchCode;
    }
    
    public Customer getCustomer(int custno){
        for (Customer i:allCustomers) {
            if(i.CustNo==custno){
                return i;
            }
        }
        return null;
    }
    
    public void addLoan(Customer c, float Amount, int installment){
        
        if(c==null){
            Scanner sc = new Scanner(System.in);
            String Name,Address,phone;
            System.out.println("Enter your name");
            Name=sc.nextLine();
            System.out.println("Enter your Address");
            Address =sc.nextLine();
            System.out.println("Enter your phone no");
            phone=sc.nextLine();
            c= new Customer(Name,Address,phone);
            allCustomers.add(c);
        }
        Loan loan = new Loan(Amount,installment,c);
        c.addLoan(loan);
        allLoans.add(loan);
    }
    
    public Loan getLoan(int LoanNo){
        for (Loan i:allLoans) {
            if(i.LoanNo==LoanNo){
                return i;
            }
        }
        return null;
    }
    public void removeLoan(Customer c, Loan loan){
        for (Loan i:allLoans) {
            if(i==loan){
                allLoans.remove(i);
                c.removeLoan(loan);
                break;
            }
        }
    }
    public void addAccount(Customer c, int type){
        Scanner sc = new Scanner(System.in);
        if(c==null){
            String Name,Address,phone;
            System.out.println("Enter your name");
            Name=sc.nextLine();
            System.out.println("Enter your Address");
            Address =sc.nextLine();
            System.out.println("Enter your phone no");
            phone=sc.nextLine();
            c= new Customer(Name,Address,phone);
            allCustomers.add(c);
        }
        if(type==1){
            Savings_Account ac = new Savings_Account(c);
            allSavingsAccounts.add(ac);
            c.saving_ac=ac;
        }
        else{
            Current_Account ac = new Current_Account(c);
            allCurrentAccounts.add(ac);
            c.current_ac=ac;
        }
    }
    
    public Account getAccount(int type, int AccNo){
        if(type==1){
            for (Savings_Account i:allSavingsAccounts) {
                if(i.AccNo==AccNo){
                    System.out.println("Success");
                    return i;
                }
            }
        }
        else{
            for (Current_Account i:allCurrentAccounts) {
                if(i.AccNo==AccNo){
                    return i;
                }
            }
        }
        return null;
    }
    public void removeAccount(int type, int AccNo) {
        if(type==1){
            for (Account i:allSavingsAccounts) {
                if(i.AccNo==AccNo) {
                    allSavingsAccounts.remove(i);
                    break;
                }
            }
        }
        else{
            for (Account i:allCurrentAccounts) {
                if(i.AccNo==AccNo) {
                    allSavingsAccounts.remove(i);
                    break;
                }
            }
        }
    }
    public void getDetails() {
        System.out.println("Branch Code:- "+BranchCode+" City:- "+city);
    }
}

class Bank{
    private String name;
    List<Branch>allBranches=new ArrayList<Branch>(); 
    Bank(String name){
        this.name=name;
    }
    public void addBranch(String BranchCode, String city){
        Branch b =new Branch(BranchCode,city);
        allBranches.add(b);  
    }
    public List<Branch>getallBranches(){
        return allBranches;
    }
    public Branch getBranch(String BranchCode){
        for (Branch i:allBranches) {
            if(i.BranchCode.equals(BranchCode)) {
                return i;
            }
        }
        return null;
    }
    public void removeBranch(String BranchCode){
        for (Branch i:allBranches) {
            System.out.println(i.BranchCode);
            if(i.BranchCode.equals(BranchCode)) {
                allBranches.remove(i);
                break;
            }
        }
    }
    
}

public class Main
{
	public static void main (String[] args) {
	    Scanner sc = new Scanner(System.in);
	    Bank b = new Bank("Axis");
	    b.addBranch("234dss","Mulund");
	    b.addBranch("234des","Nahur");
	    List <Branch> branches=b.getallBranches();
		for (Branch i:branches) {
            i.getDetails();
        }
        System.out.println("Choose one of the Branch and type its code");
        String branchcode=sc.next();
        Branch B=b.getBranch(branchcode);
        int choice=0;
        while(choice!=4)
        {
            System.out.println("Choose one of the below options");
            System.out.println("1. Get Customer Details");
            System.out.println("2. Accounts");
            System.out.println("3. Loans");
            System.out.println("4. Exit");
            choice=sc.nextInt();
            if(choice==1){
                System.out.println("Enter your customer number");
                int custno=sc.nextInt();
                Customer c = B.getCustomer(custno);
                c.getDetails();
            }
            else if(choice==2){
                int choice1=0;
                while(choice1!=5)
                {
                    System.out.println("Choose one of the below options");
                    System.out.println("1. Open a new account");
                    System.out.println("2. Deposit");
                    System.out.println("3. Withdraw");
                    System.out.println("4. Get Balance");
                    System.out.println("5. Exit");
                    choice1=sc.nextInt();
                    if(choice1==1){
                        Customer c = null;
                        System.out.println("Are you already a customer of the bank");
                        System.out.println("1. Yes   2. No");
                        int flag = sc.nextInt();
                        if(flag==1){
                            System.out.println("Enter your customer number");
                            int custno=sc.nextInt();
                            c = B.getCustomer(custno);
                        }
                        System.out.println("Enter the type of account");
                        System.out.println("1. Savings   2. Current");
                        int type = sc.nextInt();
                        B.addAccount(c,type);
                    }
                    else if(choice1==2){
                        System.out.println("Enter the type of account");
                        System.out.println("1. Savings   2. Current");
                        int type = sc.nextInt();
                        System.out.println("Enter your Account number");
                        int accno= sc.nextInt();
                        System.out.println("Enter the amount you want to deposit.");
                        int deposit=sc.nextInt();
                        Account ac =B.getAccount(type,accno);
                        if(ac==null){
                            System.out.println("Error");
                        }
                        ac.creditAmount(deposit);
                    }
                    else if(choice1==3){
                        System.out.println("Enter the type of account");
                        System.out.println("1. Savings   2. Current");
                        int type = sc.nextInt();
                        System.out.println("Enter your Account number");
                        int accno= sc.nextInt();
                        System.out.println("Enter the amount you want to withdraw.");
                        int withdraw=sc.nextInt();
                        Account ac =B.getAccount(type,accno);
                        ac.debitAmount(withdraw);
                    }
                    else if(choice1 ==4){
                        System.out.println("Enter the type of account");
                        System.out.println("1. Savings   2. Current");
                        int type = sc.nextInt();
                        System.out.println("Enter your Account number");
                        int accno= sc.nextInt();
                        Account ac =B.getAccount(type,accno);
                        System.out.println("Your Account balance ="+ac.Balance);
                        
                    }
                    
                }
            }
            else if(choice==3){
                int choice1=0;
                while(choice1!=3)
                {
                    System.out.println("Choose one of the below options");
                    System.out.println("1. Take a new loan");
                    System.out.println("2. Pay a installment");
                    System.out.println("3. Exit");
                    choice1=sc.nextInt();
                    if(choice1==1){
                        Customer c = null;
                        System.out.println("Are you already a customer of the bank");
                        System.out.println("1. Yes   2. No");
                        int flag = sc.nextInt();
                        if(flag==1){
                            System.out.println("Enter your customer number");
                            int custno=sc.nextInt();
                            c= B.getCustomer(custno);
                        }
                        System.out.println("Enter the loan amount.");
                        float amt= sc.nextFloat();
                        System.out.println("Enter the number of installment in which you can repay the loan");
                        int installment=sc.nextInt();
                        B.addLoan(c,amt,installment);
                    }
                    else if(choice1==2){
                        System.out.println("Enter your loan number");
                        int loanno=sc.nextInt();
                        Loan l = B.getLoan(loanno);
                        l.payment();
                        if(l.installment==0){
                            System.out.println("You have repayed the loan.");
                            B.removeLoan(l.c,l);
                        }
                    }
                }
            }
	    }
	}
}