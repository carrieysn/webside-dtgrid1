package com.meitianhui.productSpecialist.controller.mobile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
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
import com.meitianhui.productSpecialist.entity.GoodsDisplayAreaEnum;
import com.meitianhui.productSpecialist.entity.GoodsGrade;
import com.meitianhui.productSpecialist.entity.GoodsRecommend;
import com.meitianhui.productSpecialist.entity.GoodsSellExt;
import com.meitianhui.productSpecialist.service.GoodsCommentGradeService;
import com.meitianhui.productSpecialist.service.GoodsRecommendService;
import com.meitianhui.productSpecialist.service.GoodsService;

import net.sf.json.JSONArray;

/***
 * 移动端商品操作接口
 * 
 * @author 丁硕
 * @date 2016年5月11日
 */
@Controller
@RequestMapping("/mobile/goods")
public class GoodsMobileController {

	private Logger logger = Logger.getLogger(GoodsMobileController.class);
	
	@Autowired
	private GoodsService goodsService;
	
	@Autowired
	private GoodsCommentGradeService goodsCommentGradeService;
	
	@Autowired
	private GoodsRecommendService goodsRecommendService;
	
	@Autowired
	private AreaService areaService;
	
	private String recommend_id = "c242eb22-0ae7-11e6-8f64-fcaa1490ccaf";	//默认的推荐ID
	
	private final String company_category = "bt";	//合作商类型,主要靠页面传入的参数
	
	/***
	 * APP商品首页
	 * @param request
	 * @return
	 * @author 丁硕
	 * @date   2016年5月11日
	 */
	@RequestMapping("index")
	public String index(HttpServletRequest request, @RequestParam Map<String, Object> params){
		//添加token信息，加载页面时，直接将token加入到session中
		logger.info("APP首页获取到的参数信息: " + params);
		request.getSession().setAttribute("token", ActionHelper.getInstance().getToken(request));
		String bt = request.getParameter(company_category);
		AppEnum app = AppEnum.getApp(bt);
		//设置当前app_key类型
		if(app != null){
			request.getSession().setAttribute(company_category, bt);
		}
		//对惠如意的APP进行首页过滤
		if(AppEnum.HRY.getApp_key().equals(request.getSession().getAttribute(company_category))){
			return "redirect:/mobile/hry/goods/index";
		}
		return "mobile/index";
	}
	
	/***
	 * 找好货页面
	 * @param request
	 * @return
	 * @author 丁硕
	 * @date   2016年5月11日
	 */
	@RequestMapping("find")
	public String findGoods(HttpServletRequest request){
		try{
			//查询库中的所有省份信息进行展示
			Area area = new Area();
			area.setType_key(DataDictConstant.AreaType.PROVINCE);
			List<Area> areaList = areaService.queryArea(area);
			request.setAttribute("areaList", areaList);
			setDisplayAreaList(request);
			//特殊处理，对搜索关键字进行处理，将数据设置到页面分页参数中
			String keyword = request.getParameter("keyword");
			if(StringUtils.isNotBlank(keyword)) {
				keyword = new String(keyword.getBytes("ISO-8859-1"),"UTF-8");
				request.setAttribute("keyword", keyword);
			}
		} catch(Exception e){
			logger.error("找好货页面加载失败！", e);
		}
		return "mobile/find";
	}
	
	/***
	 * 首页加载top10的推荐商品信息
	 * @param request
	 * @return
	 * @author 丁硕
	 * @date   2016年8月11日
	 */
	@RequestMapping("loadRecommendGoodsList")
	public @ResponseBody ResultVO<Object> loadRecommendGoodsList(HttpServletRequest request){
		ResultVO<Object> resultVo = new ResultVO<Object>();
		try{
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("orderby", "recommend");
			Page page = goodsService.queryProductPage(1, 8, params);
			//TODO 到时候些断代码删除掉，临时从开放平台获取数据
			if(page != null && page.getResult().size() > 0){
				List<Object> result = page.getResult();
				for(Object o : result){
					GoodsSellExt goods = (GoodsSellExt) o;
					goodsService.loadGoodsSellExtInfo(goods);
				}
			}
			resultVo.setData(page.getResult());
			resultVo.setStatus(CommonConstant.RESULT_STATE_SUSS);
		} catch(Exception e) {
			resultVo.setMsg(e instanceof PlatformApiException ? e.getMessage() : "查询商品分页列表失败！");
			resultVo.setStatus(CommonConstant.RESULT_STATE_FAIL);
			logger.error("根据条件查询商品分页列表失败！", e);
		}
		return resultVo;
	}
	
	/***
	 * 加载热卖商品列表
	 * @param request
	 * @param params
	 * @param pageNum
	 * @param pageSize
	 * @return
	 * @author 丁硕
	 * @date   2016年8月17日
	 */
	@RequestMapping("loadSalesGoodsList")
	public @ResponseBody ResultVO<Page> loadSalesGoodsList(HttpServletRequest request, @RequestParam Map<String,Object> params,
			@RequestParam(required=true) int pageNum, @RequestParam(required=true) int pageSize){
		ResultVO<Page> resultVo = new ResultVO<Page>();
		try{
			Page page = GoodsApiUtil.querySellPsGoodsListPage(pageNum, pageSize, new HashMap<String, Object>());
			resultVo.setData(page);
			resultVo.setStatus(CommonConstant.RESULT_STATE_SUSS);
		} catch(Exception e) {
			resultVo.setMsg(e instanceof PlatformApiException ? e.getMessage() : "加载热卖商品列表失败！");
			resultVo.setStatus(CommonConstant.RESULT_STATE_FAIL);
			logger.error("加载热卖商品列表失败！", e);
		}
		return resultVo;
	}
	
	/***
	 * 加载最新的商品列表
	 * @param request
	 * @param params
	 * @param pageNum
	 * @param pageSize
	 * @return
	 * @author 丁硕
	 * @date   2016年9月18日
	 */
	@RequestMapping("loadNewestGoodsList")
	public @ResponseBody ResultVO<Page> loadNewestGoodsList(HttpServletRequest request, @RequestParam Map<String,Object> params,
			@RequestParam(required=true) int pageNum, @RequestParam(required=true) int pageSize){
		ResultVO<Page> resultVo = new ResultVO<Page>();
		try{
			Date now = new Date();
			params.put("created_date_start", DateFormatUtils.format(DateUtils.addWeeks(now, -1), "yyyy-MM-dd"));
			params.put("created_date_end", DateFormatUtils.format(now, "yyyy-MM-dd"));
			Page page = GoodsApiUtil.queryPsGoodsNewsetListPage(pageNum, pageSize, params);
			resultVo.setData(page);
			resultVo.setStatus(CommonConstant.RESULT_STATE_SUSS);
		} catch(Exception e) {
			resultVo.setMsg(e instanceof PlatformApiException ? e.getMessage() : "加载最新商品列表失败！");
			resultVo.setStatus(CommonConstant.RESULT_STATE_FAIL);
			logger.error("加载最新商品列表失败！", e);
		}
		return resultVo;
	}
	
	/***
	 * 根据条件查询商品分页列表
	 * @param request
	 * @param params
	 * @return
	 * @author 丁硕
	 * @date   2016年5月16日
	 */
	@RequestMapping("queryGoodsListPage")
	public @ResponseBody ResultVO<Page> queryGoodsListPage(HttpServletRequest request,  @RequestParam Map<String,Object> params,
			@RequestParam(required=true) int pageNum, @RequestParam(required=true) int pageSize) {
		ResultVO<Page> resultVo = new ResultVO<Page>();
		try{
			//搜索关键字特殊处理,进行字符转码
			String keyword = request.getParameter("keyword");
			if(StringUtils.isNotBlank(keyword)){
				params.put("keyword", new String(keyword.getBytes("ISO-8859-1"),"UTF-8"));
			}
			//2016.07.25 modify by dingshuo 添加app限制
			AppEnum app = AppEnum.getApp(request.getSession().getAttribute(company_category) + "");
			if(app != null){
				params.put("display_area_list",  Arrays.asList(app.getApp_display_area().split(",")));
			}
			Page page = goodsService.queryProductPage(pageNum, pageSize, params);
			//TODO 到时候些断代码删除掉，临时从开放平台获取数据
			if(page != null && page.getResult().size() > 0){
				List<Object> result = page.getResult();
				for(Object o : result){
					GoodsSellExt goods = (GoodsSellExt) o;
					goodsService.loadGoodsSellExtInfo(goods);
				}
			}
			resultVo.setData(page);
			resultVo.setMsg("根据条件查询商品分页列表成功！");
			resultVo.setStatus(CommonConstant.RESULT_STATE_SUSS);
		} catch(Exception e) {
			resultVo.setMsg("根据条件查询商品分页列表失败！");
			resultVo.setStatus(CommonConstant.RESULT_STATE_FAIL);
			logger.error(e);
		}
		return resultVo;
	}
	
	/***
	 * 详情页面
	 * @param request
	 * @param goods_id
	 * @return
	 * @author 丁硕
	 * @date   2016年5月11日
	 */
	@RequestMapping("detail")
	public String detail(HttpServletRequest request, @RequestParam(required= true) String goods_id){
		try{
			GoodsSellExt goods = goodsService.queryOneGoods(goods_id);
			if(goods != null){
				request.setAttribute("goods", goods);
				//格式化标签
				if(StringUtils.isNotEmpty(goods.getLabel())){
					request.setAttribute("labels", goods.getLabel().split(","));
				}
				String pic_info = goods.getPic_info();
				if(StringUtils.isNotEmpty(pic_info)){
					//格式化产品图片数据，将JSON数据转换成页面可显示数据
					JSONArray array = JSONArray.fromObject(pic_info);
					request.setAttribute("picInfoList", array);
				}
				if(StringUtils.isNotEmpty(goods.getPic_detail_info())){
					//格式化产品图片数据，将JSON数据转换成页面可显示数据
					JSONArray array = JSONArray.fromObject(goods.getPic_detail_info());
					request.setAttribute("picDetailInfoList", array);
				}
				if(StringUtils.isNotEmpty(goods.getDelivery_area())){ //查询area信息
					try{
						if("100000".equals(goods.getDelivery_area())){  //全国
							goods.setDelivery_area_path("全国");
						} else{
							List<Area> areaList = areaService.findAreaByCode(goods.getDelivery_area().split(","));
							if(areaList != null && areaList.size() > 0){
								StringBuilder path = new StringBuilder();
								for(Area area: areaList){
									path.append(area.getPath() + "&nbsp;");
								}
								goods.setDelivery_area_path(path.toString());
							}
						}
					} catch(Exception e){
						logger.error("查询配送地区失败！", e);
					}
				}
				String user_id = ActionHelper.getInstance().getSessionUser(request).getUser_id();
				//查询是否关注过
				request.setAttribute("isAttentioned", goodsRecommendService.isAttentioned(goods_id, user_id));
				//查询是否推荐过
				request.setAttribute("isRecommend", goodsRecommendService.isRecommended(goods_id, recommend_id, user_id));	//默认为其它
				request.setAttribute("display_area_id", recommend_id);
				return "mobile/detail";
			}
		} catch(Exception e){
			logger.error("加载商品详情失败！", e);
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
	 * 跳转进入创建页面
	 * @param request
	 * @return
	 * @author 丁硕
	 * @date   2016年5月11日
	 */
	@RequestMapping("create")
	public String create(HttpServletRequest request){
		try{
			//request.setAttribute("displayAreaList", GoodsDisplayAreaEnum.values());
			setDisplayAreaList(request);
			//查询可销售的category信息
			request.setAttribute("categoryList", GoodsCategoryEnum.values());
			request.setAttribute("goods", new GoodsSellExt());
		} catch(Exception e){
			logger.error(e);
		}
		return "mobile/publish";
	}
	
	/***
	 * 编辑页面
	 * @param request
	 * @param goods_id
	 * @return
	 * @author 丁硕
	 * @date   2016年5月12日
	 */
	@RequestMapping("edit")
	public String edit(HttpServletRequest request, @RequestParam(required=true) String goods_id){
		try{
			if(StringUtils.isNotBlank(goods_id)) {
				String user_id = ActionHelper.getInstance().getSessionUser(request).getUser_id();
				GoodsSellExt goods = goodsService.queryOneGoods(goods_id);
				if(goods != null && user_id.equals(goods.getOwner_id())) {  //只能产品的发布者才能进行编辑
					//查询可销售的category信息
					request.setAttribute("categoryList", GoodsCategoryEnum.values());
					setDisplayAreaList(request);
					//request.setAttribute("displayAreaList", GoodsDisplayAreaEnum.values());
					
					request.setAttribute("goods", goods);
					request.setAttribute("edit", true);
					//对选择区域进行特殊处理
					if(StringUtils.isNotBlank(goods.getDelivery_area())){
						List<Area> deliveryareaList = areaService.findAreaByCode(goods.getDelivery_area().split(","));
						if(deliveryareaList != null && deliveryareaList.size() > 0){
							request.setAttribute("deliveryArea", deliveryareaList.get(0));
						}
					}
					return "mobile/publish";
				} else{
					logger.warn("用户：" + user_id + " 非法对产品：" + goods_id + " 进行编辑操作,程序拦截成功！！！");
				}
			}
		} catch(Exception e){
			logger.error(e);
		}
		return "redirect:index";
	}
	
	/***
	 * 保存商品信息
	 * @param request
	 * @param goods
	 * @return
	 * @author 丁硕
	 * @date   2016年5月12日
	 */
	@RequestMapping("save")
	public @ResponseBody ResultVO<String> save(HttpServletRequest request, GoodsSellExt goods) {
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
			resultVo.setMsg(e instanceof PlatformApiException ? e.getMessage() : "保存产品失败！");
			resultVo.setStatus(CommonConstant.RESULT_STATE_FAIL);
			logger.error("保存产品失败", e);
		}
		return resultVo;
	}
	
	/***
	 * 删除商品信息
	 * @param request
	 * @param goods_id
	 * @return
	 * @author 丁硕
	 * @date   2016年5月12日
	 */
	@RequestMapping("delete")
	public @ResponseBody ResultVO<String> delete(HttpServletRequest request, @RequestParam(required= true) String goods_id) {
		ResultVO<String> resultVo = new ResultVO<String>();
		try{
			String token = ActionHelper.getInstance().getToken(request);
			goodsService.deleteGoods(goods_id, token);
			resultVo.setMsg("删除产品成功！");
			resultVo.setStatus(CommonConstant.RESULT_STATE_SUSS);
		} catch(Exception e){
			resultVo.setMsg(e instanceof PlatformApiException ? e.getMessage() :  "删除产品失败！");
			resultVo.setStatus(CommonConstant.RESULT_STATE_FAIL);
			logger.error("删除产品失败！goods_id：" + goods_id, e);
		}
		return resultVo;
	} 
	
	/***
	 * 对商品进行评分操作
	 * @param request
	 * @param goods_id
	 * @param comment_content
	 * @param grade
	 * @return
	 * @author 丁硕
	 * @date   2016年5月12日
	 */
	@RequestMapping("gradeGoods")
	public @ResponseBody ResultVO<String> gradeGoods(HttpServletRequest request, @RequestParam(required=true) String goods_id, GoodsGrade grade){
		ResultVO<String> resultVo = new ResultVO<String>();
		try{
			String token = ActionHelper.getInstance().getToken(request);
			if(grade.getTotal_grade() > 0){	//只评分，不评论
				goodsCommentGradeService.addGoodsGrade(goods_id, grade, token);
				resultVo.setMsg("发布评分成功！");
			}
			resultVo.setStatus(CommonConstant.RESULT_STATE_SUSS);
		} catch(Exception e){
			resultVo.setMsg("发布评分失败！");
			resultVo.setStatus(CommonConstant.RESULT_STATE_FAIL);
			logger.error("发布评分失败", e);
		}
		return resultVo;
	}
	
	/***
	 * 对商品进行评论操作
	 * @param request
	 * @param goods_id
	 * @param comment_content
	 * @param grade
	 * @return
	 * @author 丁硕
	 * @date   2016年5月12日
	 */
	@RequestMapping("commentGoods")
	public @ResponseBody ResultVO<String> commentGoods(HttpServletRequest request, @RequestParam(required=true) String goods_id, @RequestParam(required=true) String comment_content){
		ResultVO<String> resultVo = new ResultVO<String>();
		try{
			String token = ActionHelper.getInstance().getToken(request);
			if(StringUtils.isNotEmpty(comment_content)){  //评论
				goodsCommentGradeService.addGoodsCommment(goods_id, comment_content, token);
				resultVo.setMsg("发布评论成功！");
			}
			resultVo.setStatus(CommonConstant.RESULT_STATE_SUSS);
		} catch(Exception e){
			resultVo.setMsg("发布评论失败！");
			resultVo.setStatus(CommonConstant.RESULT_STATE_FAIL);
			logger.error("发布评论失败",e);
		}
		return resultVo;
	}
	
	/***
	 * 查询商品评分列表
	 * @param request
	 * @param goods_id
	 * @param pageNum
	 * @param pageSize
	 * @return
	 * @author 丁硕
	 * @date   2016年2月26日
	 */
	@RequestMapping("queryGoodsCommentList")
	public @ResponseBody ResultVO<List<Object>> queryGoodsCommentList(HttpServletRequest request, @RequestParam(required=true) String goods_id){
		ResultVO<List<Object>> resultVo = new ResultVO<List<Object>>();
		try{
			Page page = goodsCommentGradeService.queryGoodsCommentPage(1, 100, goods_id);	//只显示前100条评论
			resultVo.setData(page.getResult());
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
	 * 关注商品
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
			logger.error(e);
		}
		return resultVo;
	}
	
	/***
	 * 推荐荐产品
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
		}  catch(Exception e){
			resultVo.setMsg(e instanceof PlatformApiException ? e.getMessage() : "推荐失败！");
			resultVo.setStatus(CommonConstant.RESULT_STATE_FAIL);
			logger.error(e);
		}
		return resultVo;
	}
	
	/***
	 * 设置类目列表信息
	 * @param request
	 * @author 丁硕
	 * @date   2016年7月25日
	 */
	public void setDisplayAreaList(HttpServletRequest request){
		//获取所有的类目信息展示到页面
		//modify by dingshuo 添加app限制
		AppEnum app = AppEnum.getApp(request.getSession().getAttribute(company_category) + "");
		if(app == null){
			request.setAttribute("displayAreaList", GoodsDisplayAreaEnum.values());
		} else{
			List<GoodsDisplayAreaEnum> displayAreaList = new ArrayList<GoodsDisplayAreaEnum>();
			for(GoodsDisplayAreaEnum areaEnum : GoodsDisplayAreaEnum.values()){
				if(app.getApp_display_area().contains(areaEnum.getDisplay_area())){
					displayAreaList.add(areaEnum);
				}
			}
			request.setAttribute("displayAreaList", displayAreaList);
		}
	}
	
	
}
