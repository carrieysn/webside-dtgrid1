package com.meitianhui.productSpecialist.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.meitianhui.platform.cache.UserCache;
import com.meitianhui.platform.entity.Page;
import com.meitianhui.platform.entity.Pagetion;
import com.meitianhui.platform.entity.User;
import com.meitianhui.platform.utils.IDUtil;
import com.meitianhui.productSpecialist.dao.InvestmentMapper;
import com.meitianhui.productSpecialist.entity.IeComment;
import com.meitianhui.productSpecialist.entity.IeSubject;
import com.meitianhui.productSpecialist.service.InvestmentService;

@Service("investmentService")
public class InvestmentServiceImpl implements InvestmentService {

	@Autowired
	private InvestmentMapper investmentMapper;
	
	@Override
	public Page queryInvestmentListPage(int pageNum, int pageSize, Map<String, Object> params) {
		Pagetion.startPage(pageNum, pageSize);
		params.put("now", DateFormatUtils.format(new Date(), "yyyy-MM-dd"));
		investmentMapper.queryInvestmentList(params);
		return Pagetion.endPage();
	}
	
	@Override
	public Page queryMyInvestmentListPage(int pageNum, int pageSize, String token) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("publisher", UserCache.getUser(token).getUser_id());
		Pagetion.startPage(pageNum, pageSize);
		investmentMapper.queryInvestmentList(params);
		return Pagetion.endPage();
	}

	@Override
	public Map<String, Object> queryOneInvestment(String subject_id) {
		return investmentMapper.queryOneInvestmentDetail(subject_id);
	}
	
	@Override
	public IeSubject queryOneInvestment(Map<String, Object> params) {
		return investmentMapper.queryOneInvestment(params);
	}

	@Override
	public void saveInvestment(IeSubject subject, String token) {
		User user = UserCache.getUser(token);
		subject.setModified_date(DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
		if(StringUtils.isEmpty(subject.getSubject_id())){	//新增
			subject.setSubject_id(IDUtil.getUUID());
			subject.setPublisher(user.getUser_id());
			subject.setCreated_date(subject.getModified_date());
			investmentMapper.insertInvestment(subject);
		} else{	//修改
			investmentMapper.updateInvestment(subject);
		}
	}

	@Override
	public void deleteInvestment(String subject_id) {
		investmentMapper.deleteInvestment(subject_id);
	}
	
	@Override
	public int queryInvesetmentCount(String token) {
		User user = UserCache.getUser(token);
		return investmentMapper.queryInvesetmentCount(user.getUser_id());
	}

	@Override
	public Page queryCommentListPage(int pageNum, int pageSize, String subject_id) {
		Pagetion.startPage(pageNum, pageSize);
		investmentMapper.queryCommentList(subject_id);
		return Pagetion.endPage();
	}

	@Override
	public void saveSubjectComment(IeComment comment, String token) {
		comment.setComment_id(IDUtil.getUUID());
		comment.setCommentator(UserCache.getUser(token).getUser_id());
		comment.setCreated_date(DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
		investmentMapper.insertSubjectComment(comment);
	}

}
