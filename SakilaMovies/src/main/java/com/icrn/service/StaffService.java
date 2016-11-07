package com.icrn.service;

import java.security.Principal;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.icrn.dao.StaffDao;
import com.icrn.model.Staff;

@Service
public class StaffService {
	
	@Autowired
	StaffDao staffDao;
	
	final static Logger logger = Logger.getLogger(StaffService.class);

	public boolean updateStaffMember(Staff staff){
		logger.info("updateStaffMember called by: "+SecurityContextHolder.getContext().getAuthentication().getName());
		return staffDao.updateStaffMember(staff);
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN') || hasRole('ROLE_STAFF) && #staff.username == principal.name")
	public boolean updateStaffMemberPassword(String password, Staff staff){
		logger.info("updateStaffMemberPassword called by: "+SecurityContextHolder.getContext().getAuthentication().getName());
		return staffDao.updateStaffMemberPassword(password, staff.getStaffId());
	}
//	@PreAuthorize("hasRole('ROLE_ADMIN') || hasRole('ROLE_STAFF) && #staff.username == principal.name")
//	public boolean updateStaffMemberPassword(String password, int staffId){
//		logger.info("updateStaffMemberPassword called");
//		return staffDao.updateStaffMemberPassword(password, staffId);
//	}
	@PreAuthorize("hasRole('ROLE_ADMIN') || hasRole('ROLE_STAFF) && #username == principal.name")
	public boolean updateStaffMemberPassword(String password, String username){
		logger.info("updateStaffMemberPassword called by: "+SecurityContextHolder.getContext().getAuthentication().getName());
		return staffDao.updateStaffMemberPassword(password, staffDao.getStaffByUsername(username).getStaffId());
	}

	public List<Staff> getStaff() {
		logger.info("getStaff called by: "+SecurityContextHolder.getContext().getAuthentication().getName());
		return staffDao.getStaff();
	}
	
}
