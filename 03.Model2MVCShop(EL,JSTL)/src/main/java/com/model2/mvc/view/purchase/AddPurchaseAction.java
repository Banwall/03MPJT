package com.model2.mvc.view.purchase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.domain.User;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;

public class AddPurchaseAction extends Action {

	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("\nAddPurchaseAction start.......");
		// User
		HttpSession session = request.getSession(false);
		User user = (User)session.getAttribute("user");
		
		// Product
		int prodNo = Integer.parseInt(request.getParameter("prodNo"));
		ProductService prod = new ProductServiceImpl();
		Product product = prod.getProduct(prodNo);
		
		// Purchase
		Purchase purchase = new Purchase();
		purchase.setBuyer(user);
		purchase.setPurchaseProd(product);
		purchase.setPaymentOption(request.getParameter("paymentOption"));
		purchase.setReceiverName(request.getParameter("receiverName"));
		purchase.setReceiverPhone(request.getParameter("receiverPhone"));
		purchase.setDivyAddr(request.getParameter("receiverAddr"));
		purchase.setDivyRequest(request.getParameter("receiverRequest"));
		purchase.setDivyDate(request.getParameter("receiverDate"));
		purchase.setTranCode("1");
		
		PurchaseService service = new PurchaseServiceImpl();
		service.addPurchase(purchase);
		request.setAttribute("purchaseVO", purchase);
		
		System.out.println("\nAddPurchaseAction End.......\n");
		return "forward:/purchase/addPurchase.jsp";
	}

}
