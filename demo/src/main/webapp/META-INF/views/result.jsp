<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<html>
<head>
	<title>result</title>
</head>
<body>

after
<img src= ${imgpath} width="400" height="400">

before
<img src= ${ogimgpath} width="400" height="400">

data : ${data}
score : ${score}

</body>
</html>
