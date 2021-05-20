<%@ page contentType="text/html; charset=euc-kr" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
	
	<!-- UserList -->
	<c:if test="${ resultPage.currentPage > resultPage.pageUnit }">
			<a href="javascript:fncGetList('${ resultPage.beginUnitPage - 1}')">이전</a>
	</c:if>
	
	<c:forEach var="i"  begin="${resultPage.beginUnitPage}" end="${resultPage.endUnitPage}" step="1">
		<a href="javascript:fncGetList('${ i }');">${ i }</a>
	</c:forEach>
	<c:if test="${ resultPage.endUnitPage < resultPage.maxPage }">
			<a href="javascript:fncGetList('${resultPage.endUnitPage+1}')">이후</a>
	</c:if>
	
	
	<!-- ProductList -->
	<!--<c:if test="${ menu == 'search' || menu == 'manage' }">
		<c:if test="${ resultPage.currentPage > resultPage.pageUnit }">
				<a href="javascript:fncGetProductList('${ resultPage.beginUnitPage-1}')">이전</a>
		</c:if>
		<c:forEach var="i"  begin="${resultPage.beginUnitPage}" end="${resultPage.endUnitPage}" step="1">
			<a href="javascript:fncGetProductList('${ i }');">${ i }</a>
		</c:forEach>
		<c:if test="${ resultPage.endUnitPage < resultPage.maxPage }">
				<a href="javascript:fncGetProductList('${resultPage.endUnitPage+1}')">이후</a>
		</c:if>
	</c:if>-->
	
	