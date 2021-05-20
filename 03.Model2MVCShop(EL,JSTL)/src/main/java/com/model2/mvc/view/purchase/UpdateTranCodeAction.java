package com.model2.mvc.view.purchase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;
import com.model2.mvc.service.domain.Purchase;

public class UpdateTranCodeAction extends Action {

	public UpdateTranCodeAction() {
	}

	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		int tranNo = Integer.parseInt(request.getParameter("tranNo"));
		Purchase purchase = new Purchase();
		purchase.setTranNo(tranNo);
		purchase.setTranCode(request.getParameter("tranCode"));
		
		PurchaseService service = new PurchaseServiceImpl();
		service.updateTranCode(purchase);
		System.out.println("UpdatePurchaseVO : "+purchase);
		
		System.out.println("UpdateTranCodeAction End.........");
		return "forward:/listPurchase.do";
	}

}
