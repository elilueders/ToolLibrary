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
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import dmacc.beans.Rental;
import dmacc.beans.Member;
import dmacc.beans.Tool;

public interface RentalRepo extends JpaRepository<Rental, Long>{
	
	List<Rental>findByMemberId(Member m);
	
	//Added by Chadwick for return tool function on viewMyTools
	@Query("select r from Rental r where r.memberId= :memberInput and r.tool= :toolInput")
	Rental findByuseridAndtoolid(@Param("memberInput")Member memberId, @Param("toolInput")Tool toolId);

}
