package com.meitianhui.productSpecialist.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.meitianhui.platform.cache.UserCache;
import com.meitianhui.platform.constant.CommonConstant;
import com.meitianhui.platform.constant.DataDictConstant;
import com.meitianhui.platform.entity.Area;
import com.meitianhui.platform.entity.Page;
import com.meitianhui.platform.entity.ResultVO;
import com.meitianhui.platform.entity.Team;
import com.meitianhui.platform.exception.PlatformApiException;
import com.meitianhui.platform.service.AreaService;
import com.meitianhui.platform.utils.ActionHelper;
import com.meitianhui.platform.utils.openapi.GoodsApiUtil;
import com.meitianhui.productSpecialist.entity.AppEnum;
import com.meitianhui.productSpecialist.entity.Goods;
import com.meitianhui.productSpecialist.entity.GoodsAttention;
import com.meitianhui.productSpecialist.entity.GoodsCategoryEnum;
import com.meitianhui.productSpecialist.entity.GoodsGrade;
import com.meitianhui.productSpecialist.entity.GoodsRecommend;
import com.meitianhui.productSpecialist.entity.GoodsSellExt;
import com.meitianhui.productSpecialist.entity.GoodsUserStatistics;
import com.meitianhui.productSpecialist.service.GoodsCommentGradeService;
import com.meitianhui.productSpecialist.service.GoodsRecommendService;
import com.meitianhui.productSpecialist.service.GoodsService;
import com.meitianhui.productSpecialist.service.GoodsUserStatisticsService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/***
 * 买手控制层
 * 
 * @author 丁硕
 * @date 2016年2月23日
 */
@Controller
@RequestMapping("/product")
public class ProductController {
	
	private Logger logger = Logger.getLogger(ProductController.class);
	
	@Autowired
	private GoodsService goodsService;
	
	@Autowired
	private GoodsUserStatisticsService goodsUserStatisticsService;
	
	@Autowired
	private GoodsCommentGradeService goodsCommentGradeService;
	
	@Autowired
	private GoodsRecommendService goodsRecommendService;
	
	@Autowired
	private AreaService areaService;
	
	//地区境外临时标识
	private String area_outside_id = "3af4130d-a749-4dd0-90e4-1afc0ccc4c34";
	private String recommend_id = "c242eb22-0ae7-11e6-8f64-fcaa1490ccaf";	//默认的推荐ID
	private String area_outside_code = "999999";	//境外code
	
	/***
	 * 首页，加载相关信息
	 * @param request
	 * @return
	 * @author 丁硕
	 * @date   2016年2月26日
	 */
	@RequestMapping("index")
	public String index(HttpServletRequest request){
		try{
			Area area = new Area();
			area.setType_key(DataDictConstant.AreaType.PROVINCE);
			List<Area> areaList = areaService.queryArea(area);
			Area outsideArea = new Area();
			outsideArea.setArea_id(area_outside_id);
			outsideArea.setArea_code(area_outside_code);
			outsideArea.setShort_name("境外");
			areaList.add(0, outsideArea);
			request.setAttribute("areaList", areaList);
			//查询展馆信息，并查出对应的推荐数量
			request.setAttribute("appEnumList", AppEnum.values());
			request.setAttribute("categoryList", GoodsCategoryEnum.values());
			
			//特殊处理，对搜索关键字进行处理，将数据设置到页面分页参数中
			String keyword = request.getParameter("keyword");
			if(StringUtils.isNotBlank(keyword)) {
				keyword = new String(keyword.getBytes("ISO-8859-1"),"UTF-8");
				request.setAttribute("keyword", keyword);
			}
		} catch(Exception e){
			logger.error("加载首页出错", e);
		}
		return "index";
	}
	
	/***
	 * 加载各个区域的产品数量
	 * @param request
	 * @return
	 * @author 丁硕
	 * @date   2016年2月29日
	 */
	@RequestMapping("queryCountByArea")
	public @ResponseBody ResultVO<List<Map<String, Object>>> queryCountByArea(HttpServletRequest request){
		ResultVO<List<Map<String, Object>>> resultVo = new ResultVO<List<Map<String, Object>>>();
		try{
			List<Map<String, Object>> areaCount = goodsService.queryCountByArea();
			resultVo.setData(areaCount);
			resultVo.setMsg("查询出区域产品数成功！");
			resultVo.setStatus(CommonConstant.RESULT_STATE_SUSS);
		} catch(Exception e) {
			resultVo.setMsg("查询出区域产品数失败！");
			resultVo.setStatus(CommonConstant.RESULT_STATE_FAIL);
			logger.error("加载各个区域的产品数量出错", e);
		}
		return resultVo;
	}
	
	/***
	 * 根据条件查询产品列表
	 * @param request
	 * @param params
	 * @param pageNum
	 * @param pageSize
	 * @return
	 * @author 丁硕
	 * @date   2016年3月30日
	 */
	@RequestMapping("queryProductPage")
	public @ResponseBody ResultVO<Page> queryProductPage(HttpServletRequest request,  @RequestParam Map<String,Object> params, 
			@RequestParam(required=true) int pageNum, @RequestParam(required=true) int pageSize) {
		ResultVO<Page> resultVo = new ResultVO<Page>();
		try{
			//搜索关键字特殊处理,进行字符转码
			String keyword = request.getParameter("keyword");
			if(StringUtils.isNotBlank(keyword)){
				params.put("keyword", new String(keyword.getBytes("ISO-8859-1"),"UTF-8"));
			}
			Page page = goodsService.queryProductPage(pageNum, pageSize, params);
			resultVo.setData(page);
			resultVo.setMsg("根据条件查询产品列表成功！");
			resultVo.setStatus(CommonConstant.RESULT_STATE_SUSS);
		} catch(Exception e) {
			resultVo.setMsg("根据条件查询产品列表失败！");
			resultVo.setStatus(CommonConstant.RESULT_STATE_FAIL);
			logger.error("根据条件查询产品列表失败！", e);
		}
		return resultVo;
	}
	
	/**
	 * 查询用户产品统计信息
	 * @param request
	 * @return
	 * @author 丁硕
	 * @date   2016年2月25日
	 */
	@RequestMapping("queryUserStatistics")
	public @ResponseBody ResultVO<GoodsUserStatistics> queryUserStatistics(HttpServletRequest request){
		ResultVO<GoodsUserStatistics> resultVo = new ResultVO<GoodsUserStatistics>();
		try{
			String user_id = ActionHelper.getInstance().getSessionUser(request).getUser_id();
			GoodsUserStatistics statistics = goodsUserStatisticsService.queryUserStatistics(user_id);
			if(statistics == null){
				statistics = new GoodsUserStatistics();
			}
			resultVo.setData(statistics);
			resultVo.setMsg("查询用户产品统计信息成功！");
			resultVo.setStatus(CommonConstant.RESULT_STATE_SUSS);
		} catch(Exception e){
			resultVo.setMsg("查询用户产品统计信息失败！");
			resultVo.setStatus(CommonConstant.RESULT_STATE_FAIL);
			logger.error("查询用户产品统计信息失败！", e);
		}
		return resultVo;
	}
	
	/***
	 * 用户产品列表页面
	 * @param request
	 * @return
	 * @author 丁硕
	 * @date   2016年2月26日
	 */
	@RequestMapping("userProductList")
	public String userProductList(HttpServletRequest request){
		String statistics_type = request.getParameter("statistics_type");
		request.setAttribute("statistics_type", statistics_type);
		if("sell".equals(statistics_type)){	//我的卖单
			return "order/index";
		} else{
			return "userProductList";
		}
	}
	
	/***
	 * 根据条件查询用户产品分页信息
	 * @param request
	 * @param type
	 * @param pageNum
	 * @param pageSize
	 * @return
	 * @author 丁硕
	 * @date   2016年2月26日
	 */
	@RequestMapping("queryUserProductPage")
	public @ResponseBody ResultVO<Page> queryUserProductPage(HttpServletRequest request, @RequestParam(required=true) String statistics_type,
			@RequestParam(required=true) int pageNum, @RequestParam(required=true) int pageSize){
		ResultVO<Page> resultVo = new ResultVO<>();
		try{
			Map<String,  Object> params = new HashMap<String, Object>();
			params.put("user_id", ActionHelper.getInstance().getSessionUser(request).getUser_id());
			params.put("statistics_type", statistics_type);
			Page page = goodsService.queryUserProductList(pageNum, pageSize, params);
			resultVo.setData(page);
			resultVo.setMsg("根据条件查询产品列表成功！");
			resultVo.setStatus(CommonConstant.RESULT_STATE_SUSS);
		} catch(Exception e) {
			logger.error("根据条件查询产品列表失败！", e);
			resultVo.setMsg("根据条件查询产品列表失败！");
			resultVo.setStatus(CommonConstant.RESULT_STATE_FAIL);
		}
		return resultVo;
	}
	
	/***
	 * 跳转到新增产品页面
	 * @param request
	 * @return
	 * @author 丁硕
	 * @date   2016年2月23日
	 */
	@RequestMapping("create")
	public String createProduct(HttpServletRequest request){
		try{
			//类目列表信息
			request.setAttribute("appEnumList", AppEnum.values());
			//类型列表信息
			request.setAttribute("categoryList", GoodsCategoryEnum.values());
			request.setAttribute("goods", new GoodsSellExt());
		} catch(Exception e){
			logger.error(e);
		}
		return "create";
	}
	
	/***
	 * 编辑产品
	 * @param request
	 * @param goods_id
	 * @return
	 * @author 丁硕
	 * @date   2016年2月29日
	 */
	@RequestMapping("edit")
	public String editProduct(HttpServletRequest request, @RequestParam(required=true) String goods_id){
		try{
			if(StringUtils.isNotBlank(goods_id)) {
				String user_id = ActionHelper.getInstance().getSessionUser(request).getUser_id();
				GoodsSellExt goods = goodsService.queryOneGoods(goods_id);
				if(goods != null && user_id.equals(goods.getOwner_id())) {  //只能产品的发布者才能进行编辑
					//类型列表信息
					request.setAttribute("categoryList", GoodsCategoryEnum.values());
					//类目列表信息
					request.setAttribute("appEnumList", AppEnum.values());
					request.setAttribute("goods", goods);
					request.setAttribute("edit", true);
					//对选择区域进行特殊处理
					if(StringUtils.isNotBlank(goods.getDelivery_area())){
						List<Area> deliveryareaList = areaService.findAreaByCode(goods.getDelivery_area().split(","));
						if(deliveryareaList != null && deliveryareaList.size() > 0){
							request.setAttribute("deliveryArea", deliveryareaList.get(0));
						}
					}
					return "create";
				} else{
					logger.warn("用户：" + user_id + " 非法对产品：" + goods_id + " 进行编辑操作,程序拦截成功！！！");
				}
			}
		} catch(Exception e){
			logger.error("进入编辑页面失败！", e);
		}
		return "redirect:index";
	}
	
	/***
	 * 保存产品信息
	 * @param request
	 * @return
	 * @author 丁硕
	 * @date   2016年2月23日
	 */
	@RequestMapping("save")
	public @ResponseBody ResultVO<String> saveProduct(HttpServletRequest request, GoodsSellExt goods) {
		ResultVO<String> resultVo = new ResultVO<String>();
		try{
			String token = ActionHelper.getInstance().getToken(request);
			//设置产品所在地区，默认为发布都所在地
			Team team = UserCache.getUser(token).getTeam();
			Area area = areaService.findArea(team.getProviceAreaId());
			if(area != null){
				goods.setArea_id(area.getArea_code());
			}
			Goods updateGoods = goodsService.saveOrUpdateGoods(goods, token);
			resultVo.setData(updateGoods.getGoods_id());
			resultVo.setMsg("保存产品成功");
			resultVo.setStatus(CommonConstant.RESULT_STATE_SUSS);
		} catch(Exception e){
			logger.error("保存产品失败", e);
			resultVo.setMsg(e instanceof PlatformApiException ? e.getMessage() : "保存产品失败");
			resultVo.setStatus(CommonConstant.RESULT_STATE_FAIL);
		}
		return resultVo;
	}
	
	/***
	 * 产品详情页面
	 * @param request
	 * @param goods_id
	 * @return
	 * @author 丁硕
	 * @date   2016年2月25日
	 */
	@RequestMapping("detail")
	public String detailProduct(HttpServletRequest request, @RequestParam(required= true) String goods_id) {
		try{
			GoodsSellExt goods = goodsService.queryOneGoods(goods_id);
			if(goods != null){
				request.setAttribute("goods", goods);
				//查询展馆信息，并查出对应的推荐数量
				//List<GoodsDisplayArea> displayAreaList = goodsDisplayAreaService.queryDisplayAreaList();
				//request.setAttribute("displayAreaList", displayAreaList);
				
				//格式化标签
				if(StringUtils.isNotEmpty(goods.getLabel())){
					String str =  ",|，|\\s+";
					request.setAttribute("labels", goods.getLabel().split(str));
				}
				String pic_info = goods.getPic_info();
				if(StringUtils.isNotEmpty(pic_info)){
					//格式化产品图片数据，将JSON数据转换成页面可显示数据
					List<Map<String, String>> picInfoList = new ArrayList<Map<String, String>>();
					JSONArray array = JSONArray.fromObject(pic_info);
					for(int i = 0;i < array.size();i++){
						JSONObject json = JSONObject.fromObject(array.get(i));
						Map<String, String> map = new HashMap<String, String>();
						map.put("path_id", json.getString("path_id"));
						map.put("title", json.getString("title"));
						if(i == 0){  //页面进行大图展示，进行特殊处理
							request.setAttribute("bigPicInfo", map);
						} else{
							picInfoList.add(map);
						}
					}
					request.setAttribute("picInfoList", picInfoList);
				}
				//商品详情图片
				if(StringUtils.isNotEmpty(goods.getPic_detail_info())){
					//格式化产品图片数据，将JSON数据转换成页面可显示数据
					List<Map<String, String>> picInfoList = new ArrayList<Map<String, String>>();
					JSONArray array = JSONArray.fromObject(goods.getPic_detail_info());
					for(int i = 0;i < array.size();i++){
						JSONObject json = JSONObject.fromObject(array.get(i));
						Map<String, String> map = new HashMap<String, String>();
						map.put("path_id", json.getString("path_id"));
						map.put("title", json.getString("title"));
						picInfoList.add(map);
					}
					request.setAttribute("picDetailInfoList", picInfoList);
				}
				
				if(StringUtils.isNotEmpty(goods.getDelivery_area())){ //查询area信息
					try{
						if("100000".equals(goods.getDelivery_area())){  //全国
							goods.setDelivery_area_path("<p>全国</p>");
						} else{
							List<Area> areaList = areaService.findAreaByCode(goods.getDelivery_area().split(","));
							if(areaList != null && areaList.size() > 0){
								StringBuilder path = new StringBuilder();
								for(Area area: areaList){
									path.append("<p>" + area.getPath() + "</p>");
								}
								goods.setDelivery_area_path(path.toString());
							}
						}
					} catch(Exception e){
						logger.error("查询配送地区失败！", e);
						e.printStackTrace();
					}
				}
				String user_id = ActionHelper.getInstance().getSessionUser(request).getUser_id();
				//查询是否关注过
				request.setAttribute("isAttentioned", goodsRecommendService.isAttentioned(goods_id, user_id));
				//查询是否推荐过
				request.setAttribute("isRecommend", goodsRecommendService.isRecommended(goods_id, recommend_id, user_id));	//默认为其它
				request.setAttribute("display_area_id", recommend_id);
				return "detail";
			}
		} catch(Exception e){
			logger.error("加载用户详情信息失败！", e);
		}
		return "redirect:index";
	}
	
	/**
	 * 加载商品销量信息
	 * @param request
	 * @param goods_id
	 * @return
	 * @author 丁硕
	 * @date   2016年8月19日
	 */
	@RequestMapping("queryGoodsSellInfo")
	public @ResponseBody ResultVO<Object> queryGoodsSellInfo(HttpServletRequest request, @RequestParam(required= true) String goods_id){
		ResultVO<Object> resultVo = new ResultVO<Object>();
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("goods_id", goods_id);
			Page page = GoodsApiUtil.querySellPsGoodsListPage(1, 1, params);
			if(page != null && page.getResult().size() > 0){
				resultVo.setData(page.getResult().get(0));
			}
			resultVo.setStatus(CommonConstant.RESULT_STATE_SUSS);
		} catch (PlatformApiException e) {
			logger.error("加载商品销量信息失败！" ,e);
			resultVo.setMsg("加载商品销量信息失败！");
			resultVo.setStatus(CommonConstant.RESULT_STATE_SUSS);
		}
		return resultVo;
	}
	
	/***
	 * 删除产品，只能是自己创建的产品
	 * @param request
	 * @param goods_id
	 * @author 丁硕
	 * @date   2016年3月8日
	 */
	@RequestMapping("delete")
	public @ResponseBody ResultVO<String> delete(HttpServletRequest request, @RequestParam(required= true) String goods_id) {
		ResultVO<String> resultVo = new ResultVO<String>();
		try{
			String token = ActionHelper.getInstance().getToken(request);
			goodsService.deleteGoods(goods_id, token);
			resultVo.setMsg("删除产品成功！");
			resultVo.setStatus(CommonConstant.RESULT_STATE_SUSS);
		}  catch(Exception e){
			resultVo.setMsg(e instanceof PlatformApiException ? e.getMessage() : "删除产品失败！");
			resultVo.setStatus(CommonConstant.RESULT_STATE_FAIL);
			logger.error("删除产品失败！goods_id：" + goods_id, e);
		}
		return resultVo;
	}
	
	/***
	 * 查询用户推荐产品到哪些展馆列表，每个用户只能推荐到不同展馆一次
	 * @param request
	 * @param goods_id
	 * @return
	 * @author 丁硕
	 * @date   2016年2月29日
	 */
	@RequestMapping("queryUserRecommendListByGoodsId")
	public @ResponseBody ResultVO<List<GoodsRecommend>> queryUserRecommendListByGoodsId(HttpServletRequest request, @RequestParam(required= true) String goods_id){
		ResultVO<List<GoodsRecommend>> resultVo = new ResultVO<List<GoodsRecommend>>();
		try{
			//查询用户推荐产品到展馆记录
			List<GoodsRecommend> recommendList = goodsRecommendService.queryUserRecommendListByGoodsId(goods_id, ActionHelper.getInstance().getSessionUser(request).getUser_id());
			resultVo.setData(recommendList);
			resultVo.setMsg("查询用户推荐产品展馆成功");
			resultVo.setStatus(CommonConstant.RESULT_STATE_SUSS);
		} catch(Exception e){
			resultVo.setMsg("查询用户推荐产品展馆失败");
			resultVo.setStatus(CommonConstant.RESULT_STATE_FAIL);
			logger.error("查询用户推荐产品展馆失败", e);
		}
		return resultVo;
	}
	
	/***
	 * 新增一条用户产品评论记录
	 * @param request
	 * @param goods_id
	 * @param grade
	 * @return
	 * @author 丁硕
	 * @date   2016年2月26日
	 */
	@RequestMapping("commentGoods")
	public @ResponseBody ResultVO<String> commentGoods(HttpServletRequest request, @RequestParam(required=true) String goods_id, @RequestParam(required=true) String comment_content, GoodsGrade grade){
		ResultVO<String> resultVo = new ResultVO<String>();
		try{
			String token = ActionHelper.getInstance().getToken(request);
			if(StringUtils.isNotEmpty(comment_content) && grade.getTotal_grade() > 0){  //同时发布评论与评分
				goodsCommentGradeService.addGoodsCommentGrade(goods_id, grade, comment_content, token);
				resultVo.setMsg("发布评论内容成功！");
			} else if(StringUtils.isNotEmpty(comment_content)){  //只评论，不评分
				goodsCommentGradeService.addGoodsCommment(goods_id, comment_content, token);
				resultVo.setMsg("发布评论成功！");
			} else if(grade.getTotal_grade() > 0){	//只评分，不评论
				goodsCommentGradeService.addGoodsGrade(goods_id, grade, token);
				resultVo.setMsg("发布评分成功！");
			}
			resultVo.setStatus(CommonConstant.RESULT_STATE_SUSS);
		} catch(Exception e){
			resultVo.setMsg("发布评论失败！");
			resultVo.setStatus(CommonConstant.RESULT_STATE_FAIL);
			logger.error("发布评论失败！", e);
		}
		return resultVo;
	}
	
	/***
	 * 查询产品评分页信息
	 * @param request
	 * @param goods_id
	 * @param pageNum
	 * @param pageSize
	 * @return
	 * @author 丁硕
	 * @date   2016年2月26日
	 */
	@RequestMapping("queryGoodsCommentPage")
	public @ResponseBody ResultVO<Page> queryGoodsCommentPage(HttpServletRequest request, @RequestParam(required=true) String goods_id,
			@RequestParam(required=true) int pageNum, @RequestParam(required=true) int pageSize){
		ResultVO<Page> resultVo = new ResultVO<Page>();
		try{
			Page page = goodsCommentGradeService.queryGoodsCommentPage(pageNum, pageSize, goods_id);
			resultVo.setData(page);
			resultVo.setMsg("查询评论分布列表成功！");
			resultVo.setStatus(CommonConstant.RESULT_STATE_SUSS);
		} catch(Exception e){
			resultVo.setMsg("查询评论分布列表失败");
			resultVo.setStatus(CommonConstant.RESULT_STATE_FAIL);
			logger.error(e);
		}
		return resultVo;
	}
	
	/***
	 * 用户推荐产品
	 * @param request
	 * @param goods_id  产品编号
	 * @param display_area_id 展馆
	 * @return
	 * @author 丁硕
	 * @date   2016年2月26日
	 */
	@RequestMapping("recommendGoods")
	public @ResponseBody ResultVO<String> recommendGoods(HttpServletRequest request, @RequestParam(required=true) String goods_id, @RequestParam(required=true) String display_area_id){
		ResultVO<String> resultVo = new ResultVO<String>();
		try{
			String token = ActionHelper.getInstance().getToken(request);
			GoodsRecommend recommend = goodsRecommendService.recommendGoods(goods_id, display_area_id, token);
			if(recommend != null){
				resultVo.setMsg("推荐成功！");
				resultVo.setStatus(CommonConstant.RESULT_STATE_SUSS);
			} else{
				resultVo.setMsg("推荐失败！");
				resultVo.setStatus(CommonConstant.RESULT_STATE_FAIL);
			}
		} catch(Exception e){
			resultVo.setMsg(e instanceof PlatformApiException ? e.getMessage() : "推荐失败！");
			resultVo.setStatus(CommonConstant.RESULT_STATE_FAIL);
			logger.error("推荐失败", e);
		}
		return resultVo;
	}
	
	/***
	 * 用户关注产品
	 * @param request
	 * @param goods_id
	 * @return
	 * @author 丁硕
	 * @date   2016年2月26日
	 */
	@RequestMapping("attentionGoods")
	public @ResponseBody ResultVO<String> attentionGoods(HttpServletRequest request, @RequestParam(required=true) String goods_id){
		ResultVO<String> resultVo = new ResultVO<String>();
		try{
			String token = ActionHelper.getInstance().getToken(request);
			GoodsAttention attention = goodsRecommendService.attentionGoods(goods_id, token);
			if(attention != null) {
				resultVo.setMsg("关注成功！");
				resultVo.setStatus(CommonConstant.RESULT_STATE_SUSS);
			} else{
				resultVo.setMsg("关注失败！");
				resultVo.setStatus(CommonConstant.RESULT_STATE_FAIL);
			}
		} catch(Exception e){
			resultVo.setMsg(e instanceof PlatformApiException ? e.getMessage() : "关注失败！");
			resultVo.setStatus(CommonConstant.RESULT_STATE_FAIL);
			logger.error("关注失败", e);
		}
		return resultVo;
	} 
	
}
