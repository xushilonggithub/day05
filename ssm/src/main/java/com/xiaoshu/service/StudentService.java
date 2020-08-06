package com.xiaoshu.service;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.util.StringUtil;
import com.xiaoshu.dao.MajorMapper;
import com.xiaoshu.dao.StudentMapper;
import com.xiaoshu.dao.UserMapper;
import com.xiaoshu.entity.Major;
import com.xiaoshu.entity.Student;
import com.xiaoshu.entity.StudentVo;
import com.xiaoshu.entity.User;
import com.xiaoshu.entity.UserExample;
import com.xiaoshu.entity.UserExample.Criteria;

@Service
public class StudentService {

	@Autowired
	StudentMapper studentMapper;
	
	@Autowired
	MajorMapper majorMapper;
	
	
	
	//导出
	public List<StudentVo>findList(StudentVo studentVo){
		return studentMapper.findList(studentVo);
	}
	
	
	
	
	public List<Major>findMajor(){
		return majorMapper.selectAll();
	}

	public PageInfo<StudentVo> findPage(StudentVo studentVo,Integer pageNum,Integer pageSize){
		PageHelper.startPage(pageNum, pageSize);
		List<StudentVo>list = 	studentMapper.findList(studentVo);
		return new PageInfo<>(list);
	}

	public Student findName(String sName){
		
		Student param = new Student();
		param.setsName(sName);
	return	studentMapper.selectOne(param);
	}
	
	public void addStudent(Student student){
		student.setsBirth(new Date());
		studentMapper.insert(student);
	}
	
	
	public void updateStudent(Student student){
		studentMapper.updateByPrimaryKeySelective(student);
	}
	public void deleteStudent(Integer id){
		studentMapper.deleteByPrimaryKey(id);
	}
	public void importStudent(MultipartFile studentFile) throws InvalidFormatException, IOException{
		//导入业务
		//获取工作表
		Workbook workbook = WorkbookFactory.create(studentFile.getInputStream());
		
		//工作对象
		Sheet sheet = workbook.getSheetAt(0);
		
		int rowNum = sheet.getLastRowNum();//获取总行数
		
		for (int i = 0; i < rowNum; i++) {
			//第一行是表头，过滤
			Row row = sheet.getRow(i+1);
			String sName = row.getCell(0).toString();
			String sSex = row.getCell(1).toString();
			String sHobby = row.getCell(2).toString();
			Date sBirth = row.getCell(3).getDateCellValue();
			String mname = row.getCell(4).toString();
			
			if (sSex.equals("男")&& "大数据".equals(mname)) {
				//把数据封装实体类
				Student s = new Student();
				s.setsName(sName);
				s.setsSex(sSex);
				s.setsHobby(sHobby);
				s.setsBirth(new Date());
				
				
				//公司id
				Major param = new Major();
				param.setmName(mname);
				Major major = majorMapper.selectOne(param);
				s.setmId(major.getmId());
				
				studentMapper.insert(s);
			}
			
			
	}
}
	}
