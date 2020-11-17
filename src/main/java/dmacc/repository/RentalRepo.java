/*************************************************
 * author: 	
 * course:	CIS 171 Java II
 * date:	
 * project:	ToolLibrary - group project
 * file:	RentalRepo.java
 * summary:	
 ************************************************/

package dmacc.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import dmacc.beans.Rental;

public interface RentalRepo extends JpaRepository<Rental, Long>{

}
