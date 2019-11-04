package com.ldu.controller;

import com.ldu.pojo.*;
import com.ldu.service.*;
import com.ldu.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;


@Controller
@RequestMapping(value = "/goods")
public class GoodsController {
	@Autowired
	private GoodsService goodsService;
	@Autowired
	private ImageService imageService;
	@Autowired
	private CatelogService catelogService;
	@Resource
	private PurseService purseService;
	@Resource
	private UserService userService;
	/**
	 * 首页显示商品，每一类商品查询6件，根据最新上架排序 key的命名为catelogGoods1、catelogGoods2....
	 *
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/homeGoods")
	public ModelAndView homeGoods() throws Exception {
		ModelAndView modelAndView = new ModelAndView();
		// 商品种类数量
		int catelogSize = 7;
		// 每个种类显示商品数量
		int goodsSize = 6;

		List<Goods> goodsList = null;//获取商品
		List<GoodsExtend> goodsAndImage = null;//

		/* 获取最新发布列表 */
		goodsList = goodsService.getGoodsOrderByDate(goodsSize);
		goodsAndImage = new ArrayList<GoodsExtend>();
		for (int j = 0; j < goodsList.size(); j++) {
			// 将用户信息和image信息封装到GoodsExtend类中，传给前台
			GoodsExtend goodsExtend = new GoodsExtend();
			Goods goods = goodsList.get(j);
			List<Image> images = imageService.getImagesByGoodsPrimaryKey(goods.getId());
			goodsExtend.setGoods(goods);
			goodsExtend.setImages(images);
			goodsAndImage.add(j, goodsExtend);
		}
		String key0 = "catelog" + "Goods";
		modelAndView.addObject(key0, goodsAndImage);

		/* 获取其他列表物品信息 */
		for (int i = 1; i <= catelogSize; i++) {
			goodsList = goodsService.getGoodsByCatelogOrderByDate(i, goodsSize);
			goodsAndImage = new ArrayList<GoodsExtend>();
			for (int j = 0; j < goodsList.size(); j++) {
				// 将用户信息和image信息封装到GoodsExtend类中，传给前台
				GoodsExtend goodsExtend = new GoodsExtend();
				Goods goods = goodsList.get(j);
				List<Image> images = imageService.getImagesByGoodsPrimaryKey(goods.getId());
				goodsExtend.setGoods(goods);
				goodsExtend.setImages(images);
				goodsAndImage.add(j, goodsExtend);
			}
			String key = "catelog" + "Goods" + i;
			modelAndView.addObject(key, goodsAndImage);
		}
		modelAndView.setViewName("goods/homeGoods");
		return modelAndView;
	}
/**
 * 查询该类商品
 *最新发布
 * @param
 * @return
 * @throws Exception
 */



@RequestMapping(value = "/catelog")
	public ModelAndView homeGoods(HttpServletRequest request,@RequestParam(value = "str",required = false)String str){
	ModelAndView mv =new ModelAndView();

	//显示商品数量
	int goodsSize=12;
	List<Goods> goodsList =null;
	List<GoodsExtend> goodsAndImage=null;
	goodsList=goodsService.getGoodByStr(goodsSize,str,str);
	goodsAndImage =new ArrayList<GoodsExtend>();
	for (int j = 0; j <goodsList.size() ; j++) {
		// 将用户信息和image信息封装到GoodsExtend类中，传给前台
		GoodsExtend goodsExtend =new GoodsExtend();
		Goods goods =goodsList.get(j);
		List<Image> images =imageService.getImagesByGoodsPrimaryKey(goods.getId());//通过获取货物的id查找对用的图片
		goodsExtend.setGoods(goods);
		goodsExtend.setImages(images);
		goodsAndImage.add(j,goodsExtend);
	}
	mv.addObject("goodsExtendList", goodsAndImage);
	mv.addObject("search", str);
	mv.setViewName("/goods/catelogGoods");
	return mv;
}
	/**
	 * 查询该类商品
	 *
	 * @param id 要求该参数不为空
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/catelog/{id}")
	public ModelAndView catelogGoods(HttpServletRequest request, @PathVariable("id") Integer id,
									 @RequestParam(value = "str", required = false) String str) throws Exception {
		List<Goods> goodsList = goodsService.getGoodsByCatelog(id, str, str);
		Catelog catelog = catelogService.selectByPrimaryKey(id);
		List<GoodsExtend> goodsExtendList = new ArrayList<GoodsExtend>();
		for (int i = 0; i < goodsList.size(); i++) {
			GoodsExtend goodsExtend = new GoodsExtend();
			Goods goods = goodsList.get(i);
			List<Image> imageList = imageService.getImagesByGoodsPrimaryKey(goods.getId());
			goodsExtend.setGoods(goods);
			goodsExtend.setImages(imageList);
			goodsExtendList.add(i, goodsExtend);
		}
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("goodsExtendList", goodsExtendList);
		modelAndView.addObject("catelog", catelog);
		modelAndView.addObject("search", str);
		modelAndView.setViewName("/goods/catelogGoods");
		return modelAndView;
	}

/**
 * 发布商品
 *
 * @return
 * @throws Exception
 */
@RequestMapping(value = "publishGoods")
	public ModelAndView publishGoods(HttpServletRequest request){
	//校验用户是否登录
	User cur_user =(User)request.getSession().getAttribute("cur_user");
	Integer userid =cur_user.getId();
	Purse myPurse =purseService.getPurseByUserId(userid);
	ModelAndView mv =new ModelAndView();
	mv.addObject("myPurse",myPurse);
	mv.setViewName("/goods/pubGoods");
	return  mv;
}
	/**
	 * 提交发布的商品信息
	 *
	 * @return
	 * @throws Exception
	 */
@RequestMapping(value = "/publishGoodsSubmit")
	public String publishGoodsSubmit(HttpServletRequest request,Image ima,Goods goods,MultipartFile image){
	//查询当前用户的cur_user对象，便于使用id
	User cur_user =(User) request.getSession().getAttribute("cur_user");
	goods.setUserId(cur_user.getId());
	goodsService.addGoods(goods,10);//在goods标中插入数据
	//返回插入物品的id
	int goodsId =goods.getId();
	ima.setGoodsId(goodsId);
	imageService.insert(ima);//在image中插入添加商品的图片
	// 发布商品后，catlog的number+1，user表的goods_num+1，更新session的值
	int number =cur_user.getGoodsNum();
	Integer catelog_id =goods.getCatelogId();
	Catelog catelog =catelogService.selectByPrimaryKey(catelog_id);
	catelogService.updateCatelogNum(catelog_id,catelog.getNumber()+1);
	userService.updateGoodsNum(cur_user.getId(),number+1);
	cur_user.setGoodsNum(number+1);
	request.getSession().setAttribute("cur_user", cur_user);// 修改session值
	return "redirect:/user/allGoods";
}
/**
 * 上传物品
 *
 * @param session
 * @param myfile
 * @return
 * @throws IllegalStateException
 * @throws IOException
 */
    @ResponseBody
	@RequestMapping(value = "/uploadFile")
	public Map<String,Object>uploadFile(HttpSession session,MultipartFile myfile) throws IOException {
    	//原始名称
		String oldFileName =myfile.getOriginalFilename();//获取上传文件名称
		//存储图片物理路径
		String file_path=session.getServletContext().getRealPath("upload");
		//上传图片
		if (myfile!=null&&oldFileName!=null&&oldFileName.length()>0){
			//新的图片名称
			String newFileName =UUID.randomUUID()+oldFileName.substring(oldFileName.lastIndexOf("."));
			//新图片
			File newFile = new File(file_path+"/"+newFileName);
			//将内存中的数据写入磁盘
			myfile.transferTo(newFile);
			//将新图片的名称返回前端
			Map<String,Object> map =new HashMap<String, Object>();
			map.put("success","成功啦");
			map.put("imgUrl",newFileName);
			return map;
		}else {
			Map<String,Object> map =new HashMap<String, Object>();
			map.put("error","图片不合法");
			return map;
		}
	}
/**
 * 用户删除商品
 *
 * @return
 * @throws Exception
 */
@RequestMapping(value = "/deleteGoods/{id}")
	public String deleteGoods(HttpServletRequest request,@PathVariable("id")Integer id){
	Goods goods =goodsService.getGoodsByPrimaryKey(id);
	// 删除商品后，catlog的number-1，user表的goods_num-1，image删除,更新session的值
	User cur_user =(User)request.getSession().getAttribute("cur_user");
	goods.setUserId(cur_user.getId());
	int number =cur_user.getGoodsNum();
	Integer catelog_id =goods.getCatelogId();
	Catelog catelog = catelogService.selectByPrimaryKey(catelog_id);
	catelogService.updateCatelogNum(catelog_id, catelog.getNumber() - 1);
	userService.updateGoodsNum(cur_user.getId(), number - 1);
	cur_user.setGoodsNum(number - 1);
	request.getSession().setAttribute("cur_user", cur_user);// 修改session值
	//imageService.deleteImagesByGoodsPrimaryKey(id);
	goodsService.deleteGoodsByPrimaryKey(id);
	return "redirect:/user/allGoods";
}
	/**
	 * 修改商品信息
	 *获取要修改商品的信息
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/editGoods/{id}")
	public ModelAndView editGoods(HttpServletRequest request,@PathVariable("id")Integer id){
		User cur_user =(User)request.getSession().getAttribute("cur_user");
		Goods goods =goodsService.getGoodsByPrimaryKey(id);
		List<Image> imageList =imageService.getImagesByGoodsPrimaryKey(id);
		GoodsExtend goodsExtend =new GoodsExtend();
		goodsExtend.setGoods(goods);
		goodsExtend.setImages(imageList);
		ModelAndView mv =new ModelAndView();
		Integer userId=cur_user.getId();
		Purse myPurse =purseService.getPurseByUserId(userId);
		mv.addObject("myPuser",myPurse);
		mv.addObject("goodsExtend",goodsExtend);
		mv.setViewName("/goods/editGoods");
		return mv;
	}
	/**
	 * 提交商品更改信息
	 *
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/editGoodsSubmit")
	public String editGoodsSubmit(HttpServletRequest request,Goods goods){
		User cur_user =(User)request.getSession().getAttribute("cur_user");
		goods.setUserId(cur_user.getId());
		String polish_time =DateUtil.getNowDay();
		goods.setPolishTime(polish_time);
		goods.setStatus(1);
		goodsService.updateGoodsByPrimaryKeyWithBLOBs(goods.getId(),goods);
	    return "redirect:/user/allGoods";
	}


	/**
	 * 根据商品id查询该商品详细信息
	 *
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/goodsId/{id}")
	public ModelAndView getGoodsById(HttpServletRequest request, @PathVariable("id") Integer id
									) throws Exception {
		Goods goods = goodsService.getGoodsByPrimaryKey(id);
		User users = userService.selectByPrimaryKey(goods.getUserId());
	//	Catelog catelog = catelogService.selectByPrimaryKey(goods.getCatelogId());
		GoodsExtend goodsExtend = new GoodsExtend();
		List<Image> imageList = imageService.getImagesByGoodsPrimaryKey(id);
		CommentExtend CommentExtend = goodsService.selectCommentsByGoodsId(id);
		goodsExtend.setGoods(goods);
		goodsExtend.setImages(imageList);
		ModelAndView mv= new ModelAndView();
		mv.addObject("CommentExtend", CommentExtend);
		mv.addObject("goodsExtend", goodsExtend);
		mv.addObject("seller", users);
		//mv.addObject("search", str);
	//	mv.addObject("catelog", catelog);
		mv.setViewName("/goods/detailGoods");
		return mv;

	}
	/**
	 * 发布评论
	 *
	 * @return
	 */
	@RequestMapping(value = "/addComments", method = RequestMethod.POST)
	public void addComment(HttpServletRequest request, Comments comments) {
		User cur_user = (User) request.getSession().getAttribute("cur_user");
		comments.setUser(cur_user);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		Date createAt = new Date();
		comments.setCreateAt(sdf.format(createAt));
		goodsService.addComments(comments);

	}

	/**
	 * 搜索商品
	 * @RequestParam 获取参数
	 */
	@RequestMapping(value = "search")
	public ModelAndView searchGoods(@RequestParam(value = "str" ,required = false) String str ){
		List<Goods> goodsList =goodsService.searchGoods(str,str);
		List<GoodsExtend> goodsExtendList =new ArrayList<GoodsExtend>();
		for (int i = 0; i <goodsList.size() ; i++) {
			GoodsExtend goodsExtend =new GoodsExtend();
			Goods goods =goodsList.get(i);
			List<Image> imageList =imageService.getImagesByGoodsPrimaryKey(goods.getId());
			goodsExtend.setImages(imageList);
			goodsExtend.setGoods(goods);
			goodsExtendList.add(i,goodsExtend);
		}
		ModelAndView mv =new ModelAndView();
		mv.addObject("goodsExtendList",goodsExtendList);
		mv.addObject("search",str);
		mv.setViewName("/goods/searchGoods");
		return mv;
	}

	/**
	 * 确认订单时显示商品详情
	 * @param request
	 * @param id
	 * @return
	 */

	@RequestMapping(value = "/buyId/{id}")
	public  ModelAndView getGoodsdetailById(HttpServletRequest request,@PathVariable("id") Integer id){
		Goods goods =goodsService.getGoodsByPrimaryKey(id);
		GoodsExtend goodsExtend =new GoodsExtend();
		List<Image> imageList =imageService.getImagesByGoodsPrimaryKey(id);
		goodsExtend.setGoods(goods);
		goodsExtend.setImages(imageList);
		User cur_user =(User) request.getSession().getAttribute("cur_user");
		Integer userId =cur_user.getId();
		Purse myPurse = purseService.getPurseByUserId(userId);
		ModelAndView mv = new ModelAndView();
		mv.addObject("goodsExtend",goodsExtend);
		mv.addObject("myPurse",myPurse);
		mv.setViewName("/user/pay");
		return  mv;
	}
}