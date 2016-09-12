package com.vsp.contactlist.controller;

import static org.springframework.web.bind.annotation.RequestMethod.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vsp.contactlist.db.Contact;
import com.vsp.contactlist.db.ContactRepository;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(value="/contact")
public class ContactListService {

	@Autowired
	ContactRepository crepo;
	
	public ContactListService() {
		// TODO Auto-generated constructor stub
	}
	
	@RequestMapping(value="/{id}", method=GET)
	public Contact findById( @PathVariable Long id )
	{
		return crepo.getContactById(id);
	}
	
	@RequestMapping(value="/", method=POST)
	public Contact createContact( @RequestBody Contact contact )
	{
		crepo.addContact(contact); 
		return contact;
	}
	
	@RequestMapping(value="/{id}", method=PUT)
	public Contact updateContact( @RequestBody Contact contact )
	{
		crepo.updateContact(contact);
		return contact;
	}
	
	@RequestMapping(value="/{id}", method=DELETE)
	public void deleteContact( @PathVariable Long id )
	{
		Contact c = crepo.getContactById(id);
		crepo.deleteContact(crepo.getReference(id));
	}
}
