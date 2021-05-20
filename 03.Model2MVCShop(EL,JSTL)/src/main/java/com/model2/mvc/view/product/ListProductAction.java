package com.model2.mvc.view.product;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.model2.mvc.common.Page;
import com.model2.mvc.common.Search;
import com.model2.mvc.framework.Action;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;

public class ListProductAction extends Action{


	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		Search search = new Search();
		
		int currentPage= 1;
		String menu = request.getParameter("menu");
		System.out.println("currentPage 값 :: "+request.getParameter("currentPage"));
		
		if(request.getParameter("currentPage") != null) {
			currentPage=Integer.parseInt(request.getParameter("currentPage"));
		}
		
		search.setCurrentPage(currentPage);
		search.setSearchCondition(request.getParameter("searchCondition"));
		search.setSearchKeyword(request.getParameter("searchKeyword"));
		
		int pageSize = Integer.parseInt( getServletContext().getInitParameter("pageSize"));
		int pageUnit  =  Integer.parseInt(getServletContext().getInitParameter("pageUnit"));
		search.setPageSize(pageSize);
		System.out.println("입력한 검색어 : "+search);
		
		System.out.println("Menu는 : "+menu);
		
		ProductService service=new ProductServiceImpl();
		Map<String,Object> map=service.getProductList(search);
		
		Page resultPage	= 
				new Page( currentPage, ((Integer)map.get("totalCount")).intValue(), pageUnit, pageSize);
		System.out.println("ListProductAction ::"+resultPage);
		
		request.setAttribute("resultPage", resultPage);
		request.setAttribute("menu", menu);
		request.setAttribute("list", map.get("list"));
		request.setAttribute("search", search);
		
		return "forward:/product/listProduct.jsp";
	}
	
	

}
