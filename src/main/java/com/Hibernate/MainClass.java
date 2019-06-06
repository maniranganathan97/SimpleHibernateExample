package com.Hibernate;

import java.util.List;
import java.util.Scanner;

import javax.persistence.EntityTransaction;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.resource.transaction.spi.TransactionStatus;

public class MainClass {

	public static void main(String[] args) {

		try {
			StandardServiceRegistry service = new StandardServiceRegistryBuilder().configure("hibernate.cfg.xml")
					.build();

			Metadata metadata = new MetadataSources(service).getMetadataBuilder().build();

			SessionFactory sessionFactory = metadata.getSessionFactoryBuilder().build();

			Session session = sessionFactory.openSession();
			Scanner sc = new Scanner(System.in);
			int decision=0;
			do {
			System.out.println("What you want? Please enter 4 to quit this operations!");
			System.out.println("1.Add");
			System.out.println("2.Delete");
			System.out.println("3.List all");
			System.out.println("4.Exit");
			
			 decision = sc.nextInt();
			
				switch (decision) {
				case 1:
					System.out.println("Enter id and name in order to store the data");
					int id = sc.nextInt();
					String name = sc.next();
					addMethod(session, id, name);
					break;
				case 2:
					System.out.println("Enter an id to delete the object");
					int deleteId = sc.nextInt();
					deleteMethod(session, deleteId);
					break;
				case 3:
					System.out.println("List of all the students::");
					List<Student> listOfStudents = list(session);
					printList(listOfStudents);
					break;

				default:
					System.out.println("Thanking You.....You are exit now!");
					break;
				}
				
				}while (decision != 4);

			
			session.close();
			sessionFactory.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private static void printList(List<Student> listOfStudents) {
		System.out.println("There are "+listOfStudents.size()+" Students present");
		for (Student sb : listOfStudents) {

			System.out.println("Id: " + sb.getId());
			System.out.println("Name: " + sb.getName());
		}

	}

	private static List<Student> list(Session session) {

		return session.createCriteria(Student.class).list();
	}

	private static void deleteMethod(Session session, int deleteId) {

		Student check = null;
		check = session.get(Student.class, deleteId);
		if (check != null) {
			session.delete(check);
			Transaction transaction = session.beginTransaction();
			;
			// if(transaction.getStatus().equals(TransactionStatus.ACTIVE))
			transaction.commit();
			// transaction.commit();
			System.out.println("****************************************");
			System.out.println("Object deleted successfully!");
			System.out.println("****************************************");
		} else {
			System.out.println("****************************************");
			System.out.println("Sorry Object is not present");
			System.out.println("****************************************");
		}
	}

	private static void addMethod(Session session, int id, String name) {
		Student bean = new Student();
		bean.setName(name);
		bean.setId(id);
		Student check=null;
		 check = session.get(Student.class, bean.getId());
		if (check == null) {
			session.save(bean);
			Transaction transaction = session.beginTransaction();
//			if(transaction.getStatus().equals(TransactionStatus.ACTIVE))
				transaction.commit();
//			transaction.commit();
			System.out.println("****************************************");
			System.out.println(" Object saved successfully!");
			System.out.println("****************************************");
		} else {
			System.out.println("****************************************");
			System.out.println("Sorry Object already present!");
			System.out.println("****************************************");
		}
	}

	/**
	 * 
	 * With error.. Please fix the Below statements
	 */

	/*
	 * public static void main(String[] args) {
	 * 
	 * try { Configuration conf = new Configuration();
	 * conf.configure("hibernate.cfg.xml"); // conf.addCla?ss(Student.class); //
	 * conf.addResource("src/main/resources/Student.hbm.xml");
	 * StandardServiceRegistryBuilder serviceRegistry = new
	 * StandardServiceRegistryBuilder().applySettings(conf.getProperties());
	 * 
	 * SessionFactory sessionFactory =
	 * conf.buildSessionFactory(serviceRegistry.build()); Session session =
	 * sessionFactory.openSession();
	 * 
	 * Transaction tx = session.beginTransaction();
	 * 
	 * Student s = new Student(); s.setName("aaa"); s.setId(1212);
	 * 
	 * session.save(s);
	 * 
	 * tx.commit();
	 * 
	 * session.close();
	 * 
	 * sessionFactory.close(); } catch (Exception e) { // TODO Auto-generated
	 * catch block e.printStackTrace(); }
	 * 
	 * 
	 * }
	 */
}
