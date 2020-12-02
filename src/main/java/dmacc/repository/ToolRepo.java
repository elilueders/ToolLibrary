/*************************************************
 * author: 	
 * course:	CIS 171 Java II
 * date:	
 * project:	ToolLibrary - group project
 * file:	ToolRepo.java
 * summary:	
 ************************************************/

package dmacc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import dmacc.beans.Tool;

public interface ToolRepo extends JpaRepository<Tool, Long>{

	@Query("SELECT t from Tool t WHERE t.name LIKE %:keyword% OR t.type LIKE %:keyword%")
	List<Tool> keywordSearch(@Param("keyword")String keyword);

}