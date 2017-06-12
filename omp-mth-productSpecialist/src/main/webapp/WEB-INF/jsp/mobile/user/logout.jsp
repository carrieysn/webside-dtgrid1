<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%request.setAttribute("title", "登出");%>
<%@ include file="/WEB-INF/jsp/mobile/common/common.jsp"%>
<body>
</body>
<script type="text/javascript">

// PS:用户登录过期或者手动点击登出时请跳转至该页面，该页面加载后会自动通知APP进行登出操作，然后跳转至APP的登录页面

// init for ios
function setupWebViewJavascriptBridge(callback) {
    if (window.WebViewJavascriptBridge) {
        return callback(WebViewJavascriptBridge);
    }
    if (window.WVJBCallbacks) {
        return window.WVJBCallbacks.push(callback);
    }
    window.WVJBCallbacks = [callback];
    var WVJBIframe = document.createElement('iframe');
    WVJBIframe.style.display = 'none';
    WVJBIframe.src = 'wvjbscheme://__BRIDGE_LOADED__';
    document.documentElement.appendChild(WVJBIframe);
    setTimeout(function() {
        document.documentElement.removeChild(WVJBIframe)
    }, 0)
}

$(function() {
    if(typeof window.jsObj == 'undefined') {    // IOS
        setupWebViewJavascriptBridge(function(bridge) {
            bridge.callHandler('logout', function(response) {});
        });
    } else {    // Android
        window.jsObj.logout();
    }
});
</script>
</html>