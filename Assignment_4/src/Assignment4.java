import java.sql.*;
public class Assignment4{
	public static void main(String args[])
	{
		Multithreading ob=new Multithreading();
		Connection conn=ob.connect();
		Thread t1=new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					ob.input(conn);
				} catch (Exception e) {
					System.out.println("Error in thread 1");
				}
			}
		});
		Thread t2=new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					ob.calculate();
				} catch (Exception e) {
					System.out.println("Error in thread 2");
				}
			}
		});
		try{
		t1.start(); 
		t1.join(); 
	    t2.start();
		
	    
	    t2.join(); 
		}catch(Exception e){}
		
		ob.print();
	}
}