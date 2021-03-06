package com.ldu.controller;

import com.ldu.pojo.*;
import com.ldu.service.*;
import com.ldu.util.DateUtil;
import com.ldu.util.MD5;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping(value = "/user")
public class UserController {

	@Resource
	private UserService userService;
	@Resource
	private NoticeService noticeService;
	@Resource
	private PurseService purseService;
	@Resource
	private GoodsService goodsService;
	@Resource
	private ImageService imageService;
	@Resource
	private FocusService focusService;
	/**
	 * 验证登录
	 * @param request
	 * @param user
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "/login")
	public ModelAndView loginValidate(HttpServletRequest request, HttpServletResponse response, User user,
                                      ModelMap modelMap) {
		User cur_user = userService.getUserByPhone(user.getPhone());
		String url = request.getHeader("Referer");//获取url
		if (cur_user != null) {
			String pwd = MD5.md5(user.getPassword());
			if (pwd.equals(cur_user.getPassword())) {
				if(cur_user.getStatus()==1) {
				request.getSession().setAttribute("cur_user", cur_user);//获取session对象,然后把要绑定对象/值 帮定到session对象上
				return new ModelAndView("redirect:" + url);
				}
			}
		}
		return new ModelAndView("redirect:" + url);
	}

	/**
	 * 用户注册
	 *
	 * @param user1
	 * @return
	 */
	@RequestMapping(value = "/addUser")
	public String addUser(HttpServletRequest request,@ModelAttribute("user")User user1){
		String url = request.getHeader("Referer");
		User user=userService.getUserByPhone(user1.getPhone());//查看是否注册
		if (user ==null){
			String t =DateUtil.getNowDate();//获取注册时间
			String str = MD5.md5(user1.getPassword());//MD5加密
			user1.setCreateAt(t);
			user1.setPassword(str);
			user1.setGoodsNum(0);
			user1.setStatus((byte)1);//正常状态
			user1.setPower(100);//普通
			userService.addPUser(user1);
			purseService.addPurse(user1.getId());// 注册的时候同时生成钱包
		}
		return "redirect:" +url;
	}
/**
 *验证手机号是否注册
 */
@RequestMapping(value = "/register")
@ResponseBody
	public String register(HttpServletRequest request){
		String phone =request.getParameter("phone");
		User user =userService.getUserByPhone(phone);
		if (user==null){
			return "{\"success\":true,\"flag\":false}";//用户存在，注册失败
		}else {
			return "{\"success\":true,\"flag\":true}";//用户不存在，可以注册
		}
		}

	/**
	 * 更改用户名
	 *
	 * @param request
	 * @param user
	 * @param// modelMap
	 * @return
	 */
	@RequestMapping(value = "/changeName")
	public ModelAndView changeName(HttpServletRequest request ,User user){
		String url = request.getHeader("Referer");
		// 从session中获取出当前用户
		User cur_user = (User) request.getSession().getAttribute("cur_user");
		cur_user.setUsername(user.getUsername());// 更改当前用户的用户名
		userService.updateUserName(cur_user);// 执行修改操作
		request.getSession().setAttribute("cur_user", cur_user);// 修改session值
		return new ModelAndView("redirect:"+url);
	}
	/**
	 * 查看求购消息
	 *
	 * @return
	 */
	@RequestMapping(value = "/home")
	public ModelAndView home(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();
		User cur_user = (User) request.getSession().getAttribute("cur_user");
		Integer userId = cur_user.getId();
		int size=5;

	//	Purse myPurse = purseService.getPurseByUserId(userId);
		//List<User> users=userService.getUserOrderByDate(size);
		List<Notice> notice=noticeService.getNoticeList();
		mv.addObject("notice", notice);
		//mv.addObject("myPurse", myPurse);
		//mv.addObject("users", users);
		mv.setViewName("/user/home");
		return mv;
	}
	/**
	 * 发表求购消息
	 *
	 *
	 */
	@RequestMapping(value = "/insertSelective",method = RequestMethod.POST)
	@ResponseBody//转化成jeson数据
	public  String insertnotice(HttpServletRequest request){
		String context=request.getParameter("context");//获取页面输入的信息
		User cur_user =(User) request.getSession().getAttribute("cur_user");
		Notice notice =new Notice();
		notice.setContext(context);
		Date dt =new Date();
		SimpleDateFormat sdf =new SimpleDateFormat("\"yyyy-MM-dd HH:mm:ss\"");//转化时间格式
		notice.setCreateAt(sdf.format(dt));
		notice.setStatus((byte)0);
		notice.setUser(cur_user);
		if (context==null||context==""){
			return "{\"success\":false,\"msg\":\"发布失败，请输入内容!\"}";
		}
		noticeService.insertSelective(notice);
		return "{\"success\":true,\"msg\":\"发布成功!\"}";
	}
/**
 * 我的闲置 查询出所有的用户商品以及商品对应的图片
 *
 * @return 返回的model为 goodsAndImage对象,该对象中包含goods 和 images，参考相应的类
 */
@RequestMapping(value = "/allGoods")
	public ModelAndView goods(HttpServletRequest request){
	User cur_user =(User) request.getSession().getAttribute("cur_user");
	Integer userId=cur_user.getId();
	List<Goods> goodsList =goodsService.getGoodsByUserId(userId);
	List<GoodsExtend> goodsAndImage =new ArrayList<GoodsExtend>();
	for (int i = 0; i <goodsList.size() ; i++) {
		// 将用户信息和image信息封装到GoodsExtend类中，传给前台
		GoodsExtend goodsExtend=new GoodsExtend();
		Goods goods =goodsList.get(i);
		List<Image> images =imageService.getImagesByGoodsPrimaryKey(goods.getId());
		goodsExtend.setGoods(goods);
		goodsExtend.setImages(images);
		goodsAndImage.add(i,goodsExtend);
	}
	Purse myPurse =purseService.getPurseByUserId(userId);
	ModelAndView mv =new ModelAndView();
	mv.addObject("goodsAndImage",goodsAndImage);
	mv.setViewName("/user/goods");
	mv.addObject("myPurse",myPurse);
	return  mv;
}
/**
 * 用户退出
 *
 * @param request
 * @return
 */
@RequestMapping(value = "/logout")
	public String logout(HttpServletRequest request){
	request.getSession().setAttribute("cur_user",null);
	return "redirect:/goods/homeGoods";
}

/**
 * 修改个人信息
 *
 *
 *
 */
 @RequestMapping(value = "/updateInfo")
	public ModelAndView updateInfo(HttpServletRequest request,User user){
 	//从session中获取当前用户
	 User cur_user =(User)request.getSession().getAttribute("cur_user");
	 cur_user.setUsername(user.getUsername());
	 cur_user.setQq(user.getQq());
	 userService.updateUserName(cur_user);
	 request.getSession().setAttribute("cur_user",cur_user);
	 return new ModelAndView("redirect:/user/basic");
 }
 @RequestMapping(value = "/basic")
	public ModelAndView basic(HttpServletRequest request){
 	User cur_user =(User)request.getSession().getAttribute("cur_user");
 	Integer userId =cur_user.getId();
 	Purse myPurse = purseService.getPurseByUserId(userId);
 	ModelAndView mv =new ModelAndView();
 	mv.addObject("myPurse",myPurse);
 	mv.setViewName("/user/basic");
 	return mv;
 }

	/**
	 * 查看我的关注
	 */
	@RequestMapping(value = "/allFocus")
	public ModelAndView focus(HttpServletRequest request){
		User cur_user =(User) request.getSession().getAttribute("cur_user");
		Integer userId = cur_user.getId();
		List<Focus> focusList =focusService.getFocusByUserId(userId);
		List<GoodsExtend> goodsAndImage = new ArrayList<GoodsExtend>();
		for (int i = 0; i <focusList.size() ; i++) {
			// 将用户信息和image信息封装到GoodsExtend类中，传给前台
			GoodsExtend goodsExtend = new GoodsExtend();
			Focus focus =focusList.get(i);
			Goods goods =goodsService.getGoodsByPrimaryKey(focus.getGoodsId());
			List<Image> images =imageService.getImagesByGoodsPrimaryKey(focus.getGoodsId());
			goodsExtend.setGoods(goods);
			goodsExtend.setImages(images);
			goodsAndImage.add(i,goodsExtend);
		}
		Purse myPurse =purseService.getPurseByUserId(userId);
		ModelAndView mv =new ModelAndView();
		mv.addObject("goodsAndImage",goodsAndImage);
		mv.addObject("myPurse",myPurse);
		mv.setViewName("/user/focus");
		return mv;
	}
	/**
	 * 添加关注
	 * @return
	 */
	@RequestMapping(value = "/addFocus/{id}")
	public String addFocus(HttpServletRequest request, @PathVariable("id") Integer goods_id) {
		User cur_user = (User) request.getSession().getAttribute("cur_user");
		Integer user_id = cur_user.getId();
		List<Focus> focus=focusService.getFocusByUserId(user_id);
		if(focus.isEmpty()) {
			focusService.addFocusByUserIdAndId(goods_id, user_id);
		}
		for (Focus myfocus : focus) {
			int goodsId=myfocus.getGoodsId();
			if(goodsId!=goods_id) {
				focusService.addFocusByUserIdAndId(goods_id, user_id);
			}
		}
		return "redirect:/user/allFocus";

	}

	/**
	 * 删除关注
	 * @param request
	 * @param goods_id
	 * @return
	 */
	@RequestMapping(value = "/deleteFocus/{id}")
	public  String deleteFocus(HttpServletRequest request,@PathVariable("id")Integer goods_id){
		User cur_user =(User) request.getSession().getAttribute("cur_user");
		Integer user_id =cur_user.getId();
		focusService.deleteFocusByUserIdAndGoodsId(goods_id,user_id);
		return "redirect:/user/allFocus";
	}

	/**
	 * 查看我的钱包
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/myPurse")
	public ModelAndView getMoney(HttpServletRequest request){
		User cur_user =(User) request.getSession().getAttribute("cur_user");
		Integer user_id =cur_user.getId();
		Purse purse = purseService.getPurseByUserId(user_id);
		ModelAndView mv =new ModelAndView();
		mv.addObject("myPurse",purse);
		mv.setViewName("/user/purse");
		return mv;
}

@RequestMapping(value = "/updatePurse")
	public String updatePurse(HttpServletRequest request,Purse purse){
		User cur_user =(User) request.getSession().getAttribute("cur_user");
		Integer user_id =cur_user.getId();
		purse.setUserId(user_id);
		purse.setState(0);
		if (purse.getRecharge()!=null){
			purseService.updatePurse(purse);
		}
		if (purse.getWithdrawals()!=null){
			purseService.updatePurse(purse);
		}
	return "redirect:/user/myPurse";
}
//	/* 用户查看审核结果 */
//	@RequestMapping(value = "/updatePurse")
//	public String updatePurseState(HttpServletRequest request, Purse purse) {
//		User cur_user =(User) request.getSession().getAttribute("cur_user");
//		Integer user_id =cur_user.getId();
//		purse.setUserId(user_id);
//		//purse.setState(0);
//		purse.setState(null);
//		if (purse.getState()==1||purse.getState()==2)
//		purseService.updatePurse(purse);//修改state为null
//		return "redirect:/user/myPurse";
//	}
}



