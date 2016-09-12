package com.vsp.contactlist.db;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class ContactRepository 
{
	// If using only one PU then 'unitName' is optional
	@PersistenceContext(unitName="contactlist")
	private EntityManager entityManager;

	public void addContact( Contact c )
	{
		entityManager.persist(c); 
	}
	
	public void updateContact( Contact c )
	{
		entityManager.merge(c);
	}
	
	public void deleteContact( Contact c )
	{
		entityManager.remove(c);  
	}
	
	public Contact getContactById( Long id )
	{
		return entityManager.find(Contact.class, id); 
	}
	
	// To avoid detached instances
	public Contact getReference( Long id )
	{
		return entityManager.getReference(Contact.class, id);
	}
}
