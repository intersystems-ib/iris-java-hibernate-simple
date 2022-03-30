package com.intersystems.iberia.samples;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.*;
import java.util.List;
import java.util.ListIterator;
import java.util.logging.Level;

import javax.persistence.TypedQuery;

import com.intersystems.iberia.samples.entity.Person;
import com.intersystems.iberia.samples.entity.Trade2;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class hibernateSimple {
	protected SessionFactory sessionFactory;

	/**
	 * Main method
	 * @param args
	 */
	public static void main(String[] args) {
		
		java.util.logging.Logger.getLogger("org.hibernate").setLevel(Level.SEVERE);
		hibernateSimple driver = new hibernateSimple();
        driver.setup();
        System.out.println("Connected to InterSystems IRIS.");
        
		driver.create("GOOGL",Date.valueOf("2022-04-22"),BigDecimal.valueOf(125),88,"Meta4","Inc","+1888999111");

		driver.query("Meta");

		driver.displayLeaderboard();
		
        driver.exit();
	}
	
	/** 
	 * Setup Hibernate
	 */
    protected void setup() {
    	final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
    	        .configure() // configures settings from hibernate.cfg.xml
    	        .build();
    	try {
    	    sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
    	} 
    	catch (Exception ex) {
    		System.out.println(ex.toString());
    	    StandardServiceRegistryBuilder.destroy(registry);
    	}
    }

    /**
	 * Create new trade with new trader with full name
	 */
    protected void create(String stockName,Date tempDate,BigDecimal price,int shares,String traderFirstName,String traderLastName, String phone) {
    	try {
    		Trade2 trade = new Trade2(stockName, tempDate, price, shares);	
    		System.out.println("Trade created");
    		
    		Person trader = new Person(traderFirstName,traderLastName,phone);
    		System.out.println("person created");
    		
    		trader.getTrades().add(trade);
    		    		
    		System.out.println("Trader " + trader.getFirstname() + " set with references to trade: " + trade.getStockName());
    	   			
    	    Session session = sessionFactory.openSession();
    	    
    	    session.beginTransaction();
    	    System.out.println("transaction started");
    	    
    	    session.save(trade);
    	    System.out.println(trade.getStockName() + " saved with trade ID " + trade.getId());
    	    
    	    session.save(trader);
    	    System.out.println("Trader ID: " + trader.getId() + " saved.");
    	       	
    	    session.getTransaction().commit();
    		
    	    session.close();
        
        }
        catch (Exception e){
        	System.out.println("Error in creation: " + e.getMessage());
        }
    }
    
    /** 
	 * Create new trade with existing trader with ID
	 */
    protected void create(String stockName,Date tempDate,BigDecimal price,int shares, Long traderID) {
    	try {
    		Trade2 trade = new Trade2(stockName, tempDate, price, shares);	
    		System.out.println("Trade created");
    		
    		Session session = sessionFactory.openSession();
    		Person trader = session.get(Person.class, traderID);
    		System.out.println("person opened");
    		
    		trader.getTrades().add(trade);
    		trade.setTrader(trader);
    		 
    		System.out.println("Trader " + trader.getFirstname() + " set with references to trade: " + trade.getStockName());
    	   			    	    
    	    session.beginTransaction();
    	    System.out.println("transaction started");
    	    
    	    session.save(trade);
    	    System.out.println(trade.getStockName() + " saved with trade ID " + trade.getId());
    	    
    	    
    	    session.save(trader);
    	    System.out.println("Trader ID: " + trader.getId() + " saved.");
    	       	
    	    session.getTransaction().commit();
    		
    	    session.close();
        
        }
        catch (Exception e){
        	System.out.println("Error in creation: " + e.getMessage());
        }
    }

    /**
	 * Delete all traders and their trades
	 */
    protected void deleteAll() {
    	Session session = sessionFactory.openSession();
    	
    	session.beginTransaction();
	    System.out.println("transaction started");
	    
    	session.createQuery("delete from com.intersystems.iberia.samples.entity.Trade").executeUpdate();
    	session.createQuery("delete from com.intersystems.iberia.samples.entity.Person").executeUpdate();
    	
	    session.getTransaction().commit();
	    
    	session.close();
    	System.out.println("All trades and traders deleted from the database.");
    }

    /**
	 * Get trades by trader ID
	 */
    protected void getTraderTrades(long traderID ) {
    	Session session = sessionFactory.openSession();
   	 
        Person trader = session.get(Person.class, traderID);
        System.out.println("Trader Name: " + trader.getFirstname() + " " + trader.getLastname());
        List<Trade2> trades = trader.getTrades();
        System.out.println(" has " + trades.size() + " trades: ");
        for (int i = 0; i < trades.size(); i++)
        {
        	System.out.println("Name: " + trades.get(i).getStockName() + " with purchase price " + trades.get(i).getPurchasePrice());
        }
        session.close();
    }
    
    /**
	 * Get traders and their trades by traders' last name
	 */
    protected void query(String personName) {
    	Session session = sessionFactory.openSession();
    	String hql = "FROM com.intersystems.iberia.samples.entity.Person where lastname = :lastName";
    	
    	TypedQuery<Person> query = session.createQuery(hql, Person.class);
    	query.setParameter("lastName", personName);
    	
    	List<Person> results =  query.getResultList();
        for (ListIterator<Person> it = results.listIterator(results.size()); it.hasPrevious(); ) {
            Person p = it.previous();
            System.out.println("Query Person: " + p.getFirstname() + " " + p.getLastname());
            
            List<Trade2> trades = p.getTrades();
            System.out.println(" has " + trades.size() + " trades: ");
            for (int i = 0; i < trades.size(); i++)
            {
            	System.out.println("Name: " + trades.get(i).getStockName() + " with purchase price " + trades.get(i).getPurchasePrice());
            }
        }
        
        session.close();	
    }
    
    /**
	 * View the leaderboard of traders based on how much they earned
	 */
    protected void displayLeaderboard(){
    	Session session = sessionFactory.openSession();
    	
    	String hql = "select t.trader, sum((s.close-t.purchasePrice)*shares) as gain, " + 
    				"(sum((s.close-t.purchasePrice)*shares)/sum(t.purchasePrice) * 100) as percentIncrease " + 
    				"from com.intersystems.iberia.samples.entity.Trade2 as t left join com.intersystems.iberia.samples.entity.Stock as s on t.stockName = s.stockName " +
    				"where s.tDate = '2017-08-10' group by t.trader order by gain desc";
        
    	TypedQuery<Object[]> query = session.createQuery(hql,Object[].class);
        List<Object[]> listResult = query.getResultList();
        System.out.println("Trader\t\tGain\t\tPercent Increase");
        
        for (Object[] aRow : listResult) {
            Person trader = (Person) aRow[0];
            BigDecimal gain = (BigDecimal) aRow[1];
            BigDecimal percentIncrease = (BigDecimal) aRow[2];
            
            System.out.println(trader.getFirstname() + " " + trader.getLastname() + "\t$" + gain + "\t" + percentIncrease.setScale(2, RoundingMode.HALF_UP) + "%");
        }
        
        session.close();	

    }
    
	/**
	 * Exit
	 */
	protected void exit() {
			sessionFactory.close(); 
	}

}
