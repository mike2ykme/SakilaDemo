package com.icrn.dao;

import java.util.List;

import com.icrn.model.Staff;


public interface StaffDao {
	boolean updateStaffMember(Staff staff);
	boolean updateStaffMemberPassword(String password, int staffId);
	public Staff getStaffByUsername(String userName);
	List<Staff> getStaff();
}
