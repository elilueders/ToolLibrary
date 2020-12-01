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
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import dmacc.beans.Member;

public interface MemberRepo extends JpaRepository<Member,Long>{
	
	@Query("SELECT CASE WHEN COUNT(m) > 0 THEN true ELSE false END FROM Member m WHERE m.username=:usernameInput and m.password=:passwordInput")
	boolean memberExist(@Param("usernameInput")String username, @Param("passwordInput")String password);
	
	@Query("select m from Member m where m.username= :usernameInput and m.password= :passwordInput")
	Member findByusernameAndPassword(@Param("usernameInput")String username, @Param("passwordInput")String password);
}
