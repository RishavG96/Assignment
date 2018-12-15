import java.util.*;
import java.sql.*;
import java.lang.*;
class Multithreading{
	Items ob;
	ArrayList<Double> tax;
	Multithreading()
	{
		ob=new Items();
		ob.name=new ArrayList<String>();
		ob.type=new ArrayList<String>();
		ob.quantity=new ArrayList<Integer>();
		ob.price=new ArrayList<Integer>();
		tax=new ArrayList<Double>();
	}
	public Connection connect(){
		Connection conn=null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (Exception e) {
			System.out.println("Error 1="+e);
		}
		
		try {
			conn=DriverManager.getConnection("jdbc:mysql://localhost/feedback","root", "password");
			System.out.println("Database connected");
		} catch (Exception e) {
			System.out.println("Error 2="+e);
		}
		return conn;
	}
	public void input(Connection conn)
	{
		Statement statement=null;
		ResultSet result=null;
		try {
			statement=conn.createStatement();
			result=statement.executeQuery("Select * from item");
	
			while(result.next()) {
				ob.name.add(result.getString("name"));
				ob.type.add(result.getString("type"));
				ob.price.add(result.getInt("price"));
				ob.quantity.add(result.getInt("quantity"));
			}
			
			conn.close();
			statement.close();
			result.close();
		} catch (Exception e) {
			System.out.println("Error "+e);
		}
		
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
}