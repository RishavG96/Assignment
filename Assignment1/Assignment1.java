import java.util.*;  
public class Assignment1{
	Items ob;
	ArrayList<Double> tax;
	Assignment1()
	{
		ob=new Items();
		ob.name=new ArrayList<String>();
		ob.type=new ArrayList<String>();
		ob.quantity=new ArrayList<Integer>();
		ob.price=new ArrayList<Integer>();
		tax=new ArrayList<Double>();
	}
	public void input()
	{
		Scanner sc=new Scanner(System.in);
		while(true){
			System.out.println("Enter Item Name: ");
			ob.name.add(sc.nextLine());
			System.out.println("Enter Item Price: ");
			try{
				ob.price.add(sc.nextInt());
				sc.nextLine();
			}catch(Exception e){
				System.out.println("Please enter only integers for Price");
				ob.name.remove(ob.name.size()-1);
				sc.nextLine();
				continue;
			}
			System.out.println("Enter Item Quantity: ");
			try{
				ob.quantity.add(sc.nextInt());
				sc.nextLine();
			}catch(Exception e){
				System.out.println("Please enter only integers for Quantity");
				ob.name.remove(ob.name.size()-1);
				ob.price.remove(ob.price.size()-1);
				sc.nextLine();
				continue;
			}
			System.out.println("Enter Item type:(raw/manufactured/imported) ");
			String s=sc.nextLine();
			if(s.equals("raw") || s.equals("manufactured") || s.equals("imported"))
				ob.type.add(s);
			else{
				System.out.println("Incorrect type");
				ob.name.remove(ob.name.size()-1);
				ob.price.remove(ob.price.size()-1);
				ob.quantity.remove(ob.quantity.size()-1);				
				sc.nextLine();
				continue;
			}
			System.out.println("Do you want to enter details of any other item (y/n): ");
			char ch=sc.next().charAt(0);
			sc.nextLine();
			if(ch=='n' || ch=='N')
				break;
		}
		return;
	}
	public void print()
	{
		Iterator i=ob.name.iterator();
		Iterator i1=ob.type.iterator();
		Iterator i2=tax.iterator();
		Iterator i3=ob.price.iterator();
		System.out.println("Name Type Tax TotalPrice");
		while(i.hasNext() && i1.hasNext() && i2.hasNext() && i3.hasNext())
		{
			double t=Double.parseDouble(i2.next()+"");
			double total=t+Double.parseDouble(i3.next()+"");
			System.out.println(i.next()+" "+i1.next()+" "+t+" "+total);
		}	
	}
	public void calculate()
	{
		Iterator i1=ob.type.iterator();
		Iterator i2=ob.price.iterator();
		Iterator i3=ob.quantity.iterator();
		while(i1.hasNext() && i2.hasNext() && i3.hasNext())
		{
			String t=i1.next() + "";
			int p=Integer.parseInt(i2.next()+"");
			int q=Integer.parseInt(i3.next()+"");
			if(t.equals("raw"))
			{
				tax.add(0.125*p*q);
			}
			else if(t.equals("manufactured"))
			{
				double temp=p*q+0.125*p*q;
				tax.add(0.125*p*q + 0.02*temp);
			}
			else
			{
				double temp,t1;
				t1=0.1*p*q;
				if(t1<=100)
					temp=5;
				else if(t1>100 && t1<=200)
					temp=10;
				else
					temp=0.05*t1;
				tax.add(t1+ temp);
			}
		}
	}
	public static void main(String args[])
	{
		Assignment1 ob1=new Assignment1();
		ob1.input();
		ob1.calculate();
		ob1.print();
	}
}