package com.model2.mvc.service.purchase.impl;

import java.util.Map;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.product.dao.ProductDAO;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.dao.PurchaseDAO;
import com.model2.mvc.service.domain.Purchase;

public class PurchaseServiceImpl implements PurchaseService {
	
	private ProductDAO productDao;
	private PurchaseDAO purchaseDao;

	public PurchaseServiceImpl() {
		purchaseDao = new PurchaseDAO();
		productDao = new ProductDAO();
	}

	public void addPurchase(Purchase purchase) throws Exception {
		purchaseDao.insertPurchase(purchase);
	}

	public Purchase getPurchase(int tranNo) throws Exception {
		
		Purchase purchase = purchaseDao.findPurchase(tranNo);
		return purchase;
	}

	public Map<String, Object> getPurchaseList(Search search, String buyerId) throws Exception {
		
		Map<String, Object> map = purchaseDao.getPurchaseList(search, buyerId);
		
		return map;
	}

	public Map<String, Object> getSaleList(Search search) throws Exception {
		return null;
	}

	public void updatePurcahse(Purchase purchase) throws Exception {
		purchaseDao.updatePurchase(purchase);
	}

	public void updateTranCode(Purchase purchase) throws Exception {
		purchaseDao.updateTranCode(purchase);
	}

	public Purchase getPurchase2(int prodNo) throws Exception {
		
		Purchase purchase = purchaseDao.findPurchase2(prodNo);
		
		return purchase;
	}

}
