package com.siebentag.sw;

import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.HibernateTemplate;

public class BasicDAO
{
	private HibernateTemplate hibernate;
	
	public BasicDAO()
	{
	}
	
	public void save(Object obj)
	{
		hibernate.save(obj);
	}
	
	public HibernateTemplate getHibernate()
	{
		return hibernate;
	}
	
	public SessionFactory getSessionFactory()
	{
		return hibernate.getSessionFactory();
	}
	
	public void setSessionFactory(SessionFactory sf)
	{
		hibernate = new HibernateTemplate(sf);
	} 
}
