/*************************************************
 * author: 	
 * course:	CIS 171 Java II
 * date:	
 * project:	ToolLibrary - group project
 * file:	MemberRepo.java
 * summary:	
 ************************************************/

package dmacc.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import dmacc.beans.Member;

public interface MemberRepo extends JpaRepository<Member,Long>{

}
