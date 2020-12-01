/*************************************************
 * author: 	Brogan
 * course:	CIS 171 Java II
 * date:	11/30/2020
 * project:	ToolLibrary - group project
 * file:	UserSignInLog.java
 * summary:	Entity class for storing the times a user logs in
 ************************************************/

package dmacc.beans;

import java.util.Date;
import java.sql.Timestamp;


import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Column;


import lombok.Data;

@Entity
@Data
public class UserSignInLog {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	long logId;
	
	@Column(columnDefinition = "TIMESTAMP default CURRENT_TIMESTAMP")
	Timestamp currentTimeStamp;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="memberId", nullable=false)
	Member memberId;
}
