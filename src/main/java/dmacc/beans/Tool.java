/*************************************************
 * author: 	Eli Lueders
 * course:	CIS 171 Java II
 * date:	17 Nov 2020
 * project:	ToolLibrary - group project
 * file:	Tool.java
 * summary:	
 ************************************************/

package dmacc.beans;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class Tool {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long toolId;
	private String name;
	private String type;
	private String serial;
	private boolean isAvailable;

}
