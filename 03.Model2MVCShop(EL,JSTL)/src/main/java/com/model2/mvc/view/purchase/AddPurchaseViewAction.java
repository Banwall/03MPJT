package com.model2.mvc.view.purchase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;

public class AddPurchaseViewAction extends Action {


	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		int prodNo = Integer.parseInt(request.getParameter("prodNo"));
		ProductService prod = new ProductServiceImpl();
		Product vo = prod.getProduct(prodNo);
		
		//PurchaseVO purchaseVO = new PurchaseVO();
		//purchaseVO.setPurchaseProd(vo);
		
		//PurchaseService service = new PurchaseServiceImpl();
		//service.addPurchase(purchaseVO);
		
		request.setAttribute("productVO", vo);
		System.out.println("AddPurchaseViewAction Start........");
		
		return "forward:/purchase/addPurchaseView.jsp";
	}

}
