/*************************************************
 * author: 	Brogan
 * course:	CIS 171 Java II
 * date:	11/30/2020
 * project:	ToolLibrary - group project
 * file:	UserSignInLogRepo.java
 * summary:	
 ************************************************/

package dmacc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import dmacc.beans.UserSignInLog;

public interface UserSignInLogRepo extends JpaRepository<UserSignInLog, Long>{
	UserSignInLog findFirstByOrderByCurrentTimeStampDesc();
	
	
}