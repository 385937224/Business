<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!-- 标签库是要引入的。因为动态包含，是先翻译这个页面，再合并到 其他页面里的。。 -->
<!-- 标签库不引入的话。动态包含，就无法识别C标签库。。 -->
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">


	总共${sum}条记录，当前第 ${pageNumber} 页，共 ${pageTotal} 页
	<c:if test="${pageNumber>1}">
		<a href="javascript:goPage(${pageNumber}-1)">上一页</a>
	</c:if>
	<c:if test="${pageNumber < pageTotal}">
		<a href="javascript:goPage(${pageNumber}+1)">下一页</a>
	</c:if>
	<!--                不能加name属性。会有重叠覆盖。 -->
	到<input  type="text" style="width: 30px;"  min="1"
			max="${pageTotal}" value="${pageNumber}"  onchange="goPage(this.value)"/>
			
			
	<script type="text/javascript">
	function goPage(pagnumber) {
// 		alert(pagnumber);//因为这里传入 是要要int型，而js是个弱类型语言。怎么区分字符串和数值。用引号。所以传入的参数不能加'''。加引号会被当作字符串。
// 		document.forms[0].action="${basePatn}fw/user_listUI.action?pageNumber="+pagnumber;

// 因为是抽取成公共的goPage()方法，不能写死。各自的jsp页面定义个变量 goPageUri(写明各自分页请求路径)让这里调用。
		document.forms[0].action= goPageUri + pagnumber;
		document.forms[0].submit();
	}
	</script>		
