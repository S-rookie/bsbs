package com.whpu.house;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("admin")
public class ControllerAdmin {
	// 主页
	@RequestMapping("index")
	public String index() {
		return "/admin/index";
	}
	@RequestMapping("login")
	public String login() {
		return "/admin/login";
	}

	@RequestMapping("/showContractReview")
	public String contractReview(){
		return "/admin/order/ContractReview";
	}
}

