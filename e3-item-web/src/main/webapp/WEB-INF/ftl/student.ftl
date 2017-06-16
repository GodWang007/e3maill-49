<html>
<head>
	<meta charset="utf-8" />
	<title>测试freemarkeer</title>
</head>
<body>
	学生信息:<br>
	学号:${student.id}&nbsp&nbsp;姓名:${student.name}&nbsp&nbsp;年龄:${student.age}&nbsp&nbsp;地址:${student.address};
	学生列表:<br>
	<table border="1">
		<tr>
			<th>序号</th>
			<th>学号</th>
			<th>姓名</th>
			<th>年龄</th>
			<th>地址</th>
		</tr>
	<#list studentList as student>
		<#if student_index % 2 == 0>
		<tr bgcolor="blue">
		<#else>
		<tr bgcolor="red">
		</#if>

			<th>${student_index}</th>
			<th>${student.id}</th>
			<th>${student.name}</th>
			<th>${student.age}</th>
			<th>${student.address}</th>
		</tr>
	</#list>
	</table>
	
	
</body>
</html>