package com.ssafy.chuck.log.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.ssafy.chuck.log.dao.LogDao;
import com.ssafy.chuck.log.dto.LogDto;

@Service
public class LogServiceImpl implements LogService {

	@Autowired
	LogDao dao;

	@Override
	public void create(LogDto log) {
		try {
			dao.create(log);
		} catch (DataAccessException e) {
			throw e;
		}
	}

	@Override
	public List<LogDto> readAll(int id) {
		return dao.readAll(id);
	}
}
