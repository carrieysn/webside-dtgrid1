package com.meitianhui.productSpecialist.controller.mobile;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.meitianhui.platform.cache.UserCache;
import com.meitianhui.platform.constant.CommonConstant;
import com.meitianhui.platform.entity.Page;
import com.meitianhui.platform.entity.ResultVO;
import com.meitianhui.platform.entity.User;
import com.meitianhui.platform.utils.ActionHelper;
import com.meitianhui.productSpecialist.entity.IeComment;
import com.meitianhui.productSpecialist.entity.IeSubject;
import com.meitianhui.productSpecialist.service.InvestmentService;

/***
 * 招商管理
 * 
 * @author 丁硕
 * @date 2016年11月29日
 */
@Controller
@RequestMapping("/mobile/investment")
public class InvestmentMobileController {
	
	private Log logger = LogFactory.getLog(InvestmentMobileController.class);
	
	@Autowired
	private InvestmentService investmentService;
	
	/***
	 * 招商管理首页
	 * @param request
	 * @return
	 * @author 丁硕
	 * @date   2016年11月29日
	 */
	@RequestMapping("index")
	public String index(HttpServletRequest request){
		return "mobile/investment/investmentList";
	}
	
	/***
	 * 查询招商列表分页信息
	 * @param request
	 * @param params
	 * @param pageNum
	 * @param pageSize
	 * @return
	 * @author 丁硕
	 * @date   2016年12月7日
	 */
	@RequestMapping("queryInvestmentListPage")
	public @ResponseBody ResultVO<Page> queryInvestmentListPage(HttpServletRequest request,@RequestParam Map<String, Object> params,
			@RequestParam(required=true) int pageNum, @RequestParam(required=true) int pageSize){
		ResultVO<Page> resultVo = new ResultVO<Page>();
		try{
			Page page = investmentService.queryInvestmentListPage(pageNum, pageSize, params);
			resultVo.setData(page);
			resultVo.setMsg("查询招商分页列表成功！");
			resultVo.setStatus(CommonConstant.RESULT_STATE_SUSS);
		} catch(Exception e){
			resultVo.setMsg("查询招商分页列表失败！");
			resultVo.setStatus(CommonConstant.RESULT_STATE_FAIL);
			logger.error("查询招商分页列表失败！", e);
		}
		return resultVo;
	}
	
	/***
	 * 查询我的招商分布列表
	 * @param request
	 * @param pageNum
	 * @param pageSize
	 * @return
	 * @author 丁硕
	 * @date   2016年12月7日
	 */
	@RequestMapping("queryMyInvestmentListPage")
	public @ResponseBody ResultVO<Page> queryMyInvestmentListPage(HttpServletRequest request, @RequestParam(required=true) int pageNum, @RequestParam(required=true) int pageSize){
		ResultVO<Page> resultVo = new ResultVO<Page>();
		try{
			String token = ActionHelper.getInstance().getToken(request);
			Page page = investmentService.queryMyInvestmentListPage(pageNum, pageSize, token);
			resultVo.setData(page);
			resultVo.setMsg("查询招商分页列表成功！");
			resultVo.setStatus(CommonConstant.RESULT_STATE_SUSS);
		} catch(Exception e){
			resultVo.setMsg("查询招商分页列表失败！");
			resultVo.setStatus(CommonConstant.RESULT_STATE_FAIL);
			logger.error("查询招商分页列表失败！", e);
		}
		return resultVo;
	}
	
	/***
	 * 招商详情页面
	 * @param request
	 * @return
	 * @author 丁硕
	 * @date   2016年11月29日
	 */
	@RequestMapping("detail")
	public String detail(HttpServletRequest request, @RequestParam(required=true) String subject_id){
		Map<String, Object> subjectDetail = investmentService.queryOneInvestment(subject_id);
		request.setAttribute("subject", subjectDetail);
		return "mobile/investment/investmentDetail";
	}
	
	/***
	 * 跳转进入招商新增页面
	 * @param request
	 * @return
	 * @author 丁硕
	 * @date   2016年12月7日
	 */
	@RequestMapping("create")
	public String create(HttpServletRequest request){
		String token = ActionHelper.getInstance().getToken(request);
		request.setAttribute("user_name", UserCache.getUser(token).getName());
		return "mobile/investment/investmentCreate";
	}
	
	/***
	 * 编辑招商信息
	 * @param request
	 * @param subject_id
	 * @return
	 * @author 丁硕
	 * @date   2017年3月17日
	 */
	@RequestMapping("edit")
	public String edit(HttpServletRequest request, @RequestParam(required=true) String subject_id){
		String token = ActionHelper.getInstance().getToken(request);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("subject_id", subject_id);
		params.put("publisher", UserCache.getUser(token).getUser_id());
		IeSubject subjbect = investmentService.queryOneInvestment(params);
		if(subjbect != null){
			request.setAttribute("subject", subjbect);
			request.setAttribute("user_name", UserCache.getUser(token).getName());
			return "mobile/investment/investmentCreate";
		}
		return "redirect:detail?token=" + token + "&subject_id=" + subject_id;
	}
	
	/***
	 * 保存招商信息
	 * @param request
	 * @param subject
	 * @return
	 * @author 丁硕
	 * @date   2016年12月7日
	 */
	@RequestMapping("save")
	public @ResponseBody ResultVO<String> save(HttpServletRequest request, IeSubject subject){
		ResultVO<String> resultVo = new ResultVO<String>();
		try{
			String token = ActionHelper.getInstance().getToken(request);
			investmentService.saveInvestment(subject, token);
			resultVo.setData(subject.getSubject_id());
			resultVo.setStatus(CommonConstant.RESULT_STATE_SUSS);
		} catch(Exception e){
			resultVo.setMsg("保存招商信息失败！");
			resultVo.setStatus(CommonConstant.RESULT_STATE_FAIL);
			logger.error("保存招商信息失败！", e);
		}
		return resultVo;
	}
	
	/***
	 * 查询用户发布招商信息总数
	 * @param request
	 * @param subject
	 * @return
	 * @author 丁硕
	 * @date   2016年12月7日
	 */
	@RequestMapping("queryInvesetmentCount")
	public @ResponseBody ResultVO<Map<String, Object>> queryInvesetmentCount(HttpServletRequest request, IeSubject subject){
		ResultVO<Map<String, Object>> resultVo = new ResultVO<Map<String, Object>>();
		try{
			String token = ActionHelper.getInstance().getToken(request);
			int count = investmentService.queryInvesetmentCount(token);
			Map<String, Object> resultMap = new HashMap<String, Object>();
			resultMap.put("invesetment_qty", count);
			resultVo.setData(resultMap);
			resultVo.setStatus(CommonConstant.RESULT_STATE_SUSS);
		} catch(Exception e){
			resultVo.setMsg("查询招商总数失败！");
			resultVo.setStatus(CommonConstant.RESULT_STATE_FAIL);
			logger.error("查询招商总数失败！", e);
		}
		return resultVo;
	}
	
	//=================================评论===============================
	
	/***
	 * 对招商信息发布评论
	 * @param request
	 * @param subject_id
	 * @param cotent
	 * @return
	 * @author 丁硕
	 * @date   2016年12月7日
	 */
	@RequestMapping("publishComment")
	public @ResponseBody ResultVO<Map<String, Object>> publishComment(HttpServletRequest request, @RequestParam(required=true) String subject_id,
			@RequestParam(required=true)String content){
		ResultVO<Map<String, Object>> resultVo = new ResultVO<Map<String, Object>>();
		try{
			String token = ActionHelper.getInstance().getToken(request);
			IeComment comment = new IeComment();
			comment.setSubject_id(subject_id);
			comment.setContent(content);
			investmentService.saveSubjectComment(comment, token);
			
			User user = UserCache.getUser(token);
			Map<String, Object> resultMap = new HashMap<String, Object>();
			resultMap.put("content", content);
			resultMap.put("team_name", user.getTeam_name());
			resultMap.put("user_name", user.getName());
			resultMap.put("created_date", DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
			resultVo.setData(resultMap);
			resultVo.setStatus(CommonConstant.RESULT_STATE_SUSS);
		} catch(Exception e){
			resultVo.setMsg("评论发布失败！");
			resultVo.setStatus(CommonConstant.RESULT_STATE_FAIL);
			logger.error("评论发布失败！", e);
		}
		return resultVo;
	}
	
	/***
	 * 查询评论列表
	 * @param request
	 * @param subject_id
	 * @param pageNum
	 * @param pageSize
	 * @return
	 * @author 丁硕
	 * @date   2016年12月7日
	 */
	@RequestMapping("queryCommentListPage")
	public @ResponseBody ResultVO<Page> queryCommentListPage(HttpServletRequest request, @RequestParam(required=true) String subject_id,
			@RequestParam(required=true) int pageNum, @RequestParam(required=true) int pageSize){
		ResultVO<Page> resultVo = new ResultVO<Page>();
		try{
			Page page = investmentService.queryCommentListPage(pageNum, pageSize, subject_id);
			resultVo.setData(page);
			resultVo.setMsg("查询评论列表成功！");
			resultVo.setStatus(CommonConstant.RESULT_STATE_SUSS);
		} catch(Exception e){
			resultVo.setMsg("查询评论列表失败！");
			resultVo.setStatus(CommonConstant.RESULT_STATE_FAIL);
			logger.error("查询评论列表失败！", e);
		}
		return resultVo;
	}
	
	
}
