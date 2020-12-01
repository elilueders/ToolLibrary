/*************************************************
 * author: 	
 * course:	CIS 171 Java II
 * date:	
 * project:	ToolLibrary - group project
 * file:	RentalRepo.java
 * summary:	
 ************************************************/

package dmacc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import dmacc.beans.Rental;
import dmacc.beans.Member;

public interface RentalRepo extends JpaRepository<Rental, Long>{
	
	List<Rental>findByMemberId(Member m);

}
