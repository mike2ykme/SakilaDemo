package com.icrn.controllers;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.icrn.service.StaffService;

@Controller
@RequestMapping("/staffFunctions")
public class StaffController {

	final static Logger logger = Logger.getLogger(StaffController.class);

	@Autowired
	private StaffService staffService;
	
	@RequestMapping(value="/updateStaffMember")
	public String selectCustomer(Model model){
		logger.info("/updateStaffMember mapping called");
		model.addAttribute("staffMembers",staffService.getStaff());
		return "updateStaff";
	}
	
	@RequestMapping(value="/updatePassword",method=RequestMethod.POST)
	public String updateStaffMemberPassword(Model model, @RequestParam(value="newPassword") String newPassword,
														@RequestParam(value="username") String username){
		logger.info("/updatePassword mapping called");
		staffService.updateStaffMemberPassword(newPassword, username);
		
		return "redirect:updateSuccess";
	}
	@RequestMapping(value="/updateSuccess")
	public String updateSuccess(){
		
		return "updateSuccess";
	}
}
