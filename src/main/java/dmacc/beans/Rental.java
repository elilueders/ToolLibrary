/*************************************************
 * author: 	Ben Miner
 * course:	CIS 171 Java II
 * date:	11/15/2020
 * project:	ToolLibrary - group project
 * file:	Rental.java
 * summary:	Entity class for storing rental data
 ************************************************/

package dmacc.beans;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Data;

@Entity
@Data
public class Rental {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	long rentalId;
	Date checkedOut;
	Date checkedIN;
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="memberId", nullable=false)
	Member memberId;
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="toolId", nullable=false)
	Tool toolId;
}
