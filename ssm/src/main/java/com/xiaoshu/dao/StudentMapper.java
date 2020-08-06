package com.xiaoshu.dao;

import com.xiaoshu.base.dao.BaseMapper;
import com.xiaoshu.entity.Student;

import com.xiaoshu.entity.StudentVo;

import java.util.List;


public interface StudentMapper extends BaseMapper<Student> {

	public 	List<StudentVo> findList(StudentVo studentVo);
 
}