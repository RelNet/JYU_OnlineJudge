<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="zh-CN">
	<head>
		<meta charset="utf-8"/>
		<title>Home</title>
<%--		<link href="https://cdn.bootcss.com/twitter-bootstrap/4.3.1/css/bootstrap.min.css" rel="stylesheet">--%>
		<link href="https://cdn.bootcss.com/twitter-bootstrap/4.3.1/css/bootstrap-reboot.min.css" rel="stylesheet">
		<link href="https://cdn.bootcss.com/twitter-bootstrap/4.3.1/css/bootstrap-grid.min.css" rel="stylesheet">
		<script src="https://cdn.bootcss.com/twitter-bootstrap/4.3.1/js/bootstrap.bundle.min.js"></script>
		<script src="https://cdn.bootcss.com/twitter-bootstrap/4.3.1/js/bootstrap.min.js"></script>
		
		<meta name="viewport" content="width=device-width, initial-scale=1"/>
	</head>
	<body>
		<head class="navbar navbar-static-top bs-docs-nav" id="top">
			::before
			<div class="container">
				::before
				<nav id="bs-navbar" class="collapse navbar-collapse">
					::before
					<ul class="nav navbar-nav">
						::before
						<li class="active">
							<a href="index.jsp">主页</a>
						</li>
						<li>
							<a href="problemset">题集</a>
						</li>
						<li>
							<a href="status">状态</a>
						</li>
						<li>
							<a href="contests">比赛和考试</a>
						</li>
						::after
					</ul>
					::after
				</nav>
				::after
			</div>
			::after
		</head>
	<article>
		<form action="userpage" method="post">
			用户名: <input type="text" name="username"/><br/>
			密码: <input type="text" name="password"/><br/>
			<input type="submit" value="登陆" />
		</form>
	</article>
	</body>
</html>
