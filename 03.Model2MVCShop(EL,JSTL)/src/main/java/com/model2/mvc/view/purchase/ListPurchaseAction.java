package com.model2.mvc.view.purchase;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.model2.mvc.common.Page;
import com.model2.mvc.common.Search;
import com.model2.mvc.framework.Action;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.domain.User;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;

public class ListPurchaseAction extends Action {

	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		
		System.out.println("session :: User ::"+user);
		System.out.println("currentPage °ª :: "+request.getParameter("currentPage"));
		
		Search search=new Search();
		
		int currentPage=1;
		
		if(request.getParameter("currentPage") != null) {
			currentPage=Integer.parseInt(request.getParameter("currentPage"));
		}
		
		search.setCurrentPage(currentPage);
		
		int pageSize = Integer.parseInt(getServletContext().getInitParameter("pageSize"));
		int pageUnit = Integer.parseInt(getServletContext().getInitParameter("pageSize"));
		search.setPageSize(pageSize);
		
		PurchaseService service=new PurchaseServiceImpl();
		Map<String, Object> map = service.getPurchaseList(search, user.getUserId());
		
		Page resultPage	= 
				new Page( currentPage, ((Integer)map.get("totalCount")).intValue(), pageUnit, pageSize);
		System.out.println("ListPurchaseAction ::"+resultPage);
		
		request.setAttribute("resultPage", resultPage);
		request.setAttribute("list", map.get("list"));
		request.setAttribute("search", search);
		
		return "forward:/purchase/listPurchase.jsp";
	}
}
