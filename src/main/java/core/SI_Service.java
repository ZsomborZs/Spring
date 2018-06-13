package core;

import java.util.Collection;

import org.springframework.integration.annotation.Payload;

import struct.Person;

/*
 * Spring integration service
 */
public interface SI_Service {

	@Payload("new java.util.Date()")
	Collection<Person> listAllPeople();

	void addPerson(@Payload Person person);

	Person getPerson(@Payload String name);

	void deletePerson(@Payload String name);

}
