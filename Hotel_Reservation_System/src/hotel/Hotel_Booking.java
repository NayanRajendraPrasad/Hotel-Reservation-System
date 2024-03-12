package hotel;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Hotel_Booking {
	
	private static String url = "jdbc:mysql://localhost:3306/reservation";
	private static String user = "root";
	private static String pass = "Nayan@1102";

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
		Connection con = DriverManager.getConnection(url,user,pass);
		Scanner sc = new Scanner(System.in);
		while(true) {
			System.out.println("Hotel reservation management ");
			System.out.println("1. Reserve a room");
			System.out.println("2. View reservations");
			System.out.println("3. Get room number");
			System.out.println("4. Update details");
			System.out.println("5. Delete Reservation");
			System.out.println("0. Exit");
			System.out.println("Enter your option");
			int option = sc.nextInt();
			switch(option){
			case 1:
				System.out.println("-----------for adding details--------------");
				reserveRoom(con,sc);
				System.out.println();
				break;
			case 2:
				System.out.println("-----------for getting details-------------");
				viewReservation(con,sc);
				System.out.println();
				break;
			case 3:
				System.out.println("-----------for getting room number ---------");
				getRoomNum(con,sc);
				System.out.println();
				break;
			case 4:
				System.out.println("1.Update mobile number");
				System.out.println("2.Name correction");
				System.out.println("Enter required above options ");
				int option1=sc.nextInt();
				switch(option1) {
				case 1:
					System.out.println("--------for updating mobile number---------");
					update(con,sc);
					System.out.println();
					break;
				case 2:
					System.out.println("------------Name correction----------------");
					name(con,sc);
					System.out.println();
				}
				
				break;
			case 5:
				System.out.println("-----------for deleting details-------------");
				delete(con,sc);
				System.out.println();
				break;
			case 0:
				sc.close();
				con.close();
				exit();
				break;
			default:
				System.out.println("Please enter above mentioned options");	
				System.out.println();
			}
		}
		
		}catch(SQLException e) {
			System.out.println(e.getMessage());
		}
	}
	public static void reserveRoom(Connection con,Scanner sc) {
		System.out.println("Enter the details for reservation");
		System.out.println("Enter guest name ");
		String name = sc.next();
		sc.nextLine();
		System.out.println("Enter room number");
		int room = sc.nextInt();
		System.out.println("Enter mobile number");
		String mobile = sc.next();
		System.out.println("Enter number of people(each reservation can have only 4 people or less than that)");
		int people = sc.nextInt();
		
		String query ="insert into hotel(guest_name,room_no,mobile_no,no_of_people) values(?,?,?,?)";
		try {
			PreparedStatement pst = con.prepareStatement(query);
			pst.setString(1, name);
			pst.setInt(2, room);
			pst.setString(3, mobile);
			pst.setInt(4, people);
			
			int n=pst.executeUpdate();
			
			if(n>0) {
				System.out.println("Reservation done");
				System.out.println();
			}
			else {
				System.out.println("You may not entered data properly");
				System.out.println();
			}
			pst.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void viewReservation(Connection con,Scanner sc) {
		String query = "select * from hotel";
		
		try {
			PreparedStatement pst = con.prepareStatement(query);
			ResultSet rs = pst.executeQuery(query);
			System.out.println("guest_id  Name       Room_no  mobile_no      Date                People");
			while(rs.next()) {
				int id = rs.getInt("reservation_id");
				String name = rs.getString("guest_name");
				int room = rs.getInt("room_no");
				String mobile = rs.getString("mobile_no");
				String date = rs.getString("reservation_date");
				int people = rs.getInt("no_of_people");
				System.out.printf("%-9s %-10s %-8s %-14s %-22s %s %n",id,name,room,mobile,date,people);
				
			}
			pst.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void getRoomNum(Connection con,Scanner sc) {
		System.out.print("Enter reservation id: ");
		int id = sc.nextInt();
		System.out.println();
		System.out.print("Enter guest name: ");
		String name = sc.next();
		sc.nextLine();
		
		String query = "select room_no from hotel where reservation_id=? and guest_name=?";
		try {
			PreparedStatement pst = con.prepareStatement(query);
			pst.setInt(1, id);
			pst.setString(2, name);
			
			ResultSet rs = pst.executeQuery();
			
			int num =0;
			while(rs.next()) {
				num=rs.getInt("room_no");
			}
			if(num!=0) {
				System.out.println("Room number for id "+id+"and for name "+name+" is "+num);
				System.out.println();
			}
			else {
				System.out.println("oops!!!! ");
				System.out.println("There is no data for this details");
				System.out.println();
			}
			pst.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void update(Connection con,Scanner sc) {
		System.out.print("Enter reservation id for update: ");
		int id = sc.nextInt();
		System.out.println();
		System.out.print("Enter guest name for update: ");
		String name = sc.next();
		sc.nextLine();
		System.out.print("Enter new mobile number to update: ");
		String update_no = sc.next();
		sc.nextLine();
		
		String query = "update hotel set mobile_no=? where reservation_id=? and guest_name=?";
		
		int rs=0;
		try {
			PreparedStatement pst = con.prepareStatement(query);
			pst.setString(1, update_no);
			pst.setInt(2,id);
			pst.setString(3, name);
			rs = pst.executeUpdate();
			if(rs==0) {
				System.out.println("No data is updated");
				System.out.println();
			}
			else {
				System.out.println(rs+" row(s) updated");
				System.out.println();
			}
			pst.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void delete(Connection con,Scanner sc) {
		System.out.print("Enter reservation id for delete: ");
		int id = sc.nextInt();
		System.out.println();
		String query = "delete from hotel where reservation_id=?";
		int n=0;
		try {
			PreparedStatement pst = con.prepareStatement(query);
			pst.setInt(1, id);
			n = pst.executeUpdate();
			if(n==0) {
				System.out.println("No row(s) deleted");
				System.out.println();
			}
			else {
				System.out.println(n+" row(s) deleted");
				System.out.println();
			}
			pst.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void name(Connection con,Scanner sc) {
		int n=0;
		System.out.println("Enter correct name:");
		String correctName=sc.next();
		sc.nextLine();
		System.out.println("Enter id to change:");
		int id=sc.nextInt();
		sc.nextLine();
		String query = "update hotel set guest_name=? where reservation_id=?";
		try {
			PreparedStatement pt = con.prepareStatement(query);
			pt.setString(1, correctName);
			pt.setInt(2, id);
			n=pt.executeUpdate();
			if(n==0) {
				System.out.println("No row(s) affected");
				System.out.println();
			}
			else {
				System.out.println(n+"row(s) affected");
				System.out.println();
			}
			pt.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void exit() { 
		System.out.print("Exiting System");
		int i=5;
		while(i>0) {
			System.out.print(".");
			i--;
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.out.println();
		System.out.println("Thank you for using hotel reservation system!!!!");
		System.exit(0);
	}
}