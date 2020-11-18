/*************************************************
 * author: 	
 * course:	CIS 171 Java II
 * date:	
 * project:	ToolLibrary - group project
 * file:	ToolRepo.java
 * summary:	
 ************************************************/

package dmacc.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import dmacc.beans.Tool;

public interface ToolRepo extends JpaRepository<Tool, Long>{

}
