package com.myehr.controller.form;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.myehr.common.mybatis.MybatisUtil;
import com.myehr.mapper.formmanage.form.SysEvaluationMapper;
import com.myehr.mapper.formmanage.form.SysNewsMapper;
import com.myehr.mapper.formmanage.form.SysReplyMapper;
import com.myehr.pojo.formmanage.form.SysEvaluation;
import com.myehr.pojo.formmanage.form.SysNews;
import com.myehr.pojo.formmanage.form.SysNewsExample;
import com.myehr.pojo.formmanage.form.SysReply;
import com.myehr.pojo.formmanage.form.SysReplyCache;
import com.myehr.pojo.sysParam.SysSystemParam;
import com.myehr.service.formmanage.form.ISysEvaluationService;
import com.myehr.service.impl.formmanage.form.SysformconfigService;


@Controller
@RequestMapping("/evaluation")
public class EvaluationController {
	private static Logger logger = LoggerFactory.getLogger(EvaluationController.class);
	@Autowired
	private SysEvaluationMapper sysEvaluationMapper;
	@Autowired
	private SysReplyMapper sysReplyMapper;
	@Autowired
	private SysNewsMapper sysNewsMapper;
	@Autowired
	private ISysEvaluationService iSysEvaluationService;
	@Autowired
	private SysformconfigService sysformconfigService;
	
	/**
	 *评论 
	 */
	@RequestMapping("/findEvaluationList")
	public @ResponseBody List<SysEvaluation> findSysAssessByFormId(HttpServletRequest request) throws Exception{
		String formId = request.getParameter("formId");
		List<SysEvaluation> lists=iSysEvaluationService.findSysEvaluationByFormId(Integer.parseInt(formId));
		return lists;
	}
	@RequestMapping("/saveEvaluationList")
	public @ResponseBody  Object saveAssessList(HttpServletRequest request,@RequestBody SysEvaluation assess){
			String userName = (String)request.getSession().getAttribute("userName").toString();
			String empId = request.getSession().getAttribute("empid").toString()+"";
			String formId = request.getParameter("formId");
			assess.setEvaluationName(userName);
			assess.setEvaluationDatetime(new Date());
			assess.setEvaluationKeyid(new BigDecimal(formId));
			assess.setEvaluationReplyip(empId);
			try {
				int reCode = sysEvaluationMapper.insert(assess);
				if (reCode==1) {
					return reCode;
				} else {
					return 0;
				}
			} catch (Exception e) {
				e.printStackTrace();logger.error(e.getMessage(),e);
				return "false";
			}
		}
	/**
	 *回复
	 */
	@RequestMapping("/findReplyList")
	public @ResponseBody List<SysReplyCache> findSysReplyByEvaluationId(HttpServletRequest request) throws Exception{
		String EvaluationId = request.getParameter("evaluationId");
		List<SysReply> lists=iSysEvaluationService.findSysReplyByEvaluationId(Integer.parseInt(EvaluationId));
		List<SysReplyCache> list = new ArrayList<SysReplyCache>();
		for (SysReply sysReply : lists) {
			SysReplyCache rCache = new SysReplyCache();
			rCache.setReply(sysReply);
			String[] recode = new String[2];
			SysSystemParam photoPath = (SysSystemParam) sysformconfigService.getSysParam().get("photoPath");
			SysSystemParam empParam = (SysSystemParam) sysformconfigService.getSysParam().get("EMP_ENTITY");
			String sqlx = "select "+empParam.getSysParamRemark()+" from "+empParam.getSysParamValue1()+" where "+empParam.getSysParamValue2()+" = '"+sysReply.getReplyName()+"'";
			Map map = new HashMap();
			try {
				map = MybatisUtil.queryOne("runtime.selectSql", sqlx);
				String empName = (String) map.get(empParam.getSysParamEntry().split("_")[0]);
				String empCode = (String) map.get(empParam.getSysParamEntry().split("_")[1]);
				rCache.setReplyPhoto(photoPath.getSysParamValue1()+"/人员照片/"+empName+"_"+empCode+".jpg");
				rCache.setReplyName(empName);
			} catch (Exception e) {
				// TODO: handle exception
				rCache.setReplyPhoto(photoPath.getSysParamValue1()+"/人员照片/W01.jpg");
				rCache.setReplyName("系统管理员");
			}
						list.add(rCache);
		}
		return list;
	}
	
	@RequestMapping("/SaveReplyList")
	public @ResponseBody  Object saveReplyList(HttpServletRequest request,@RequestBody SysReply reply){
		String userName = request.getSession().getAttribute("empid")+"";
		reply.setReplyName(userName);
		reply.setReplyTime(new Date());
		try {
			int reCode = sysReplyMapper.insert(reply);
			if (reCode==1) {
				return reCode;
			} else {
				return 0;
			}
		} catch (Exception e) {
			e.printStackTrace();logger.error(e.getMessage(),e);
			return "false";
		}
		
	}
	
	/**
	 * 新闻时间轴
	 */
	
	@RequestMapping("/findNewsList")
	public @ResponseBody List<SysNews> findSysNewsByFormId(HttpServletRequest request) throws Exception{	
		String formId = "1";
		List<SysNews> lists=iSysEvaluationService.findSysNewsByFormId(Integer.parseInt(formId));
		return lists;
	}
	
	@RequestMapping("/findSysNewsByNewId")
	public @ResponseBody List<SysNews> findSysNewsByNewId(HttpServletRequest request) throws Exception{	
		String id = request.getParameter("id");
		SysNewsExample example = new SysNewsExample();
		example.or().andIdEqualTo(Integer.valueOf(id));
		List<SysNews> lists=iSysEvaluationService.findSysNewsByNewId(Integer.parseInt(id));
		return lists;
	}

}