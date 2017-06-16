package cn.e3mall.freemarker;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import freemarker.template.Configuration;
import freemarker.template.Template;

public class FreemarkerTest {
	@Test
	public void testFreemarker() throws Exception{
		// 1、向工程中添加freemarker的依赖
		// 2、创建一个Configuration对象
		Configuration configuration=new Configuration(Configuration.getVersion());
		// 3、设置Configuration对象的属性，模板所在的路径。
		configuration.setDirectoryForTemplateLoading(new File("D:/mysoftinstall/workspace/git-49/e3-item-web/src/main/webapp/WEB-INF/ftl"));
		// 4、需要设置模板的编码格式，一般就是utf-8
		configuration.setDefaultEncoding("utf-8");
		// 5、加载模板对象，需要制定一个模板名称。
		Template template = configuration.getTemplate("student.ftl");
		// 6、创建一个数据集对象，可以是pojo也可以是map，一般使用Map。
		Map data=new HashMap<>();
		data.put("hello","hello freemarker");
		//向map中添加一个pojo
		Student student=new Student(1, "张三", 18, "北京");
		data.put("student", student);
		//创建一个list
		List<Student> stuList=new ArrayList<>();
		stuList.add(new Student(2, "李四", 18, "北京"));
		stuList.add(new Student(3, "李四1", 18, "北京"));
		stuList.add(new Student(4, "李四2", 18, "北京"));
		stuList.add(new Student(5, "李四3", 18, "北京"));
		data.put("studentList", stuList);
		// 7、创建一个Writer对象，指定文件输出的目录及文件名。
		Writer out=new FileWriter(new File("D:/temp/freemarker/out/student.html"));
		//生成文件
		template.process(data, out);
		// 8、关闭流
		out.close();
	}
}
