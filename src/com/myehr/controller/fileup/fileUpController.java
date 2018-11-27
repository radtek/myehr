package com.myehr.controller.fileup;

import groovy.util.ObservableList.ChangeType;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.aspectj.weaver.ast.Var;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;


import com.alibaba.fastjson.JSON;
import com.myehr.common.mybatis.MybatisUtil;
import com.myehr.common.utils.ActUtils;
import com.myehr.controller.form.parambean.CardformInitDataParams;
import com.myehr.controller.form.parambean.ExcRuleDataParams;
import com.myehr.controller.login.Login;
import com.myehr.mapper.act.checked.ModelNodeSeqMapper;
import com.myehr.mapper.act.checked.SysActCheckedPointMapper;
import com.myehr.mapper.act.checked.SysActCheckedpointResultMapper;
import com.myehr.mapper.act.checked.SysCheckedAndNodeRelationMapper;
import com.myehr.mapper.act.checked.SysCheckedAndPointRelationMapper;
import com.myehr.mapper.activiti.ActHiTaskinstMapper;
import com.myehr.mapper.activiti.ActReModelMapper;
import com.myehr.mapper.activiti.ActRuTaskMapper;
import com.myehr.mapper.activiti.expand.ActNodePropertiesExpandMapper;
import com.myehr.mapper.fileinput.A01Mapper;
import com.myehr.mapper.fileinput.ActShowHisMapper;
import com.myehr.mapper.fileinput.C11ExpandMapper;
import com.myehr.mapper.fileinput.C11Mapper;
import com.myehr.mapper.fileinput.SysFileCheckedMapper;
import com.myehr.mapper.fileinput.SysFileCheckedPointMapper;
import com.myehr.mapper.formmanage.form.SysFileButtonRoleMapper;
import com.myehr.mapper.formmanage.widget.SysFileuploadMapper;
import com.myehr.mapper.sysCvr.SysCvrExpandMapper;
import com.myehr.mapper.sysParam.SysSystemParamMapper;
import com.myehr.mapper.sysUserRole.SysUserRoleMapper;
import com.myehr.mapper.sysdict.SysDictEntryMapper;
import com.myehr.mapper.sysdict.SysDictTypeMapper;
import com.myehr.mapper.sysuser.SysUserMapper;
import com.myehr.pojo.act.checked.ActAndCheckAndFile;
import com.myehr.pojo.act.checked.ActAndChecked;
import com.myehr.pojo.act.checked.ModelNodeSeq;
import com.myehr.pojo.act.checked.ModelNodeSeqExample;
import com.myehr.pojo.act.checked.SysActCheckedPoint;
import com.myehr.pojo.act.checked.SysActCheckedPointExample;
import com.myehr.pojo.act.checked.SysActCheckedpointResult;
import com.myehr.pojo.act.checked.SysActCheckedpointResultExample;
import com.myehr.pojo.act.checked.SysCheckedAndNodeRelation;
import com.myehr.pojo.act.checked.SysCheckedAndNodeRelationExample;
import com.myehr.pojo.act.checked.SysCheckedAndPointRelation;
import com.myehr.pojo.act.checked.SysCheckedAndPointRelationExample;
import com.myehr.pojo.activiti.ActHiActinstExample;
import com.myehr.pojo.activiti.ActHiTaskinstExample;
import com.myehr.pojo.activiti.ActReModel;
import com.myehr.pojo.activiti.ActReModelExample;
import com.myehr.pojo.activiti.ActRuTask;
import com.myehr.pojo.activiti.expand.ActNodePropertiesExpand;
import com.myehr.pojo.activiti.expand.ActNodePropertiesExpandExample;
import com.myehr.pojo.dict.SysDictEntryExample;
import com.myehr.pojo.dict.SysDictType;
import com.myehr.pojo.dict.SysDictTypeExample;
import com.myehr.pojo.fileinput.A01Example;
import com.myehr.pojo.fileinput.ActShowHis;
import com.myehr.pojo.fileinput.ActShowHisExample;
import com.myehr.pojo.fileinput.C11;
import com.myehr.pojo.fileinput.C11Example;
import com.myehr.pojo.fileinput.FileManageParams;
import com.myehr.pojo.fileinput.SysCheckedPointAndFiles;
import com.myehr.pojo.fileinput.SysFileChecked;
import com.myehr.pojo.fileinput.SysFileCheckedExample;
import com.myehr.pojo.fileinput.SysFileCheckedPoint;
import com.myehr.pojo.fileinput.SysFileCheckedPointExample;
import com.myehr.pojo.fileinput.checkedAndPoints;
import com.myehr.pojo.fileinput.checkedPointAndFiles;
import com.myehr.pojo.formmanage.cache.CardAndCardFormBeansUtil;
import com.myehr.pojo.formmanage.form.SysFileButtonRole;
import com.myehr.pojo.formmanage.form.SysFileButtonRoleExample;
import com.myehr.pojo.formmanage.form.SysFormconfigWithBLOBs;
import com.myehr.pojo.formmanage.widget.SysFileupload;
import com.myehr.pojo.formmanage.widget.SysFileuploadExample;
import com.myehr.pojo.formmanage.widget.SysFormFileupload;
import com.myehr.pojo.sysParam.SysSystemParam;
import com.myehr.pojo.sysParam.SysSystemParamExample;
import com.myehr.pojo.sysUserRole.SysUserRole;
import com.myehr.pojo.sysUserRole.SysUserRoleExample;
import com.myehr.pojo.sysuser.SysUser;
import com.myehr.pojo.sysuser.SysUserExample;
import com.myehr.pojo.sysuser.SysUserExpand;
import com.myehr.service.formmanage.form.IFileUploadService;
import com.myehr.service.formmanage.form.ISysformconfigService;
import com.myehr.service.impl.formmanage.form.SysformconfigService;
import com.myehr.test.dao.TMapperExt;
import org.apache.commons.codec.binary.Base64;  

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

@Controller
@RequestMapping("/FileList")
public class fileUpController {
	
	
	private static Logger logger = LoggerFactory.getLogger(fileUpController.class);
	
@Autowired
C11ExpandMapper sMapper;
@Autowired
C11Mapper cMapper;
@Autowired
A01Mapper aMapper;
@Autowired
SysFileuploadMapper fMapper;
@Autowired
SysDictEntryMapper dEntryMapper;
@Autowired
SysDictTypeMapper dTypeMapper;
//@Autowired
@Resource(name = "TMapperExt")
private TMapperExt tMapperExt;
@Autowired 
private IFileUploadService iFileUploadService;
@Autowired
private SysSystemParamMapper sParamMapper;
@Autowired
private SysFileCheckedMapper sFileCheckedMapper;
@Autowired
private SysFileCheckedPointMapper sFileCheckedPointMapper;
@Autowired
private SysUserRoleMapper userRoleMapper;
@Autowired
private SysFileButtonRoleMapper sFileButtonRoleMapper;
@Autowired
private SysCheckedAndNodeRelationMapper checkedAndNodeRelationMapper;
@Autowired
private SysCheckedAndPointRelationMapper cPointRelationMapper;
@Autowired
private SysActCheckedPointMapper aCheckedPointMapper;
@Autowired
private SysActCheckedpointResultMapper actCheckedpointResultMapper;
@Autowired
private ActRuTaskMapper actRuTaskMapper;

@Autowired
private ActReModelMapper actReModelMapper;

@Autowired
private ActNodePropertiesExpandMapper actNodePropertiesExpandMapper;
@Autowired
private ModelNodeSeqMapper mSeqMapper;
@Autowired
private ActHiTaskinstMapper actHiTaskinstMapper;
@Autowired
private ISysformconfigService sysformconfigService;
@Autowired
private ActShowHisMapper actShowHisMapper;
@Autowired
private SysUserMapper userMapper;
	private String serverPath = "e:/";
	//查询所有数据
		@RequestMapping("/imgFileUp")
		 public @ResponseBody void imgFileUp(HttpServletRequest request) throws Exception {
			try { 
			      boolean isMultipart = ServletFileUpload.isMultipartContent(request); 
			      if (isMultipart) { 
			        FileItemFactory factory = new DiskFileItemFactory(); 
			        ServletFileUpload upload = new ServletFileUpload(factory); 
			        // 得到所有的表单域，它们目前都被当作FileItem 
			        List<FileItem> fileItems = upload.parseRequest(request); 
			        String id = ""; 
			        String fileName = ""; 
			        // 如果大于1说明是分片处理 
			        int chunks = 1; 
			        int chunk = 0; 
			        FileItem tempFileItem = null; 
			        for (FileItem fileItem : fileItems) { 
			          if (fileItem.getFieldName().equals("id")) { 
			            id = fileItem.getString(); 
			          } else if (fileItem.getFieldName().equals("name")) { 
			            fileName = new String(fileItem.getString().getBytes("ISO-8859-1"), "UTF-8"); 
			          } else if (fileItem.getFieldName().equals("chunks")) { 
			            chunks = NumberUtils.toInt(fileItem.getString()); 
			          } else if (fileItem.getFieldName().equals("chunk")) { 
			            chunk = NumberUtils.toInt(fileItem.getString()); 
			          } else if (fileItem.getFieldName().equals("multiFile")) { 
			            tempFileItem = fileItem; 
			          } 
			          String name = fileItem.getName();
	                  // 2. 获取文件的实际内容
			          InputStream is = fileItem.getInputStream();

			          // 3. 保存文件
			          FileUtils.copyInputStreamToFile(is, new File(serverPath + "/" + name));
			        } 
			        
			         
			      } 
			    } catch (Exception e) { 
			      //logger.error(e.getMessage(), e); 
			    } 
		}
		public static String subString(String str, String strStart, String strEnd) {

	        /* 找出指定的2个字符在 该字符串里面的 位置 */
	        int strStartIndex = str.indexOf(strStart);
	        int strEndIndex = str.indexOf(strEnd);

	        /* index 为负数 即表示该字符串中 没有该字符 */
	        if (strStartIndex < 0) {
	            return "字符串 :---->" + str + "<---- 中不存在 " + strStart + ", 无法截取目标字符串";
	        }
	        if (strEndIndex < 0) {
	            return "字符串 :---->" + str + "<---- 中不存在 " + strEnd + ", 无法截取目标字符串";
	        }
	        /* 开始截取 */
	        String result = str.substring(strStartIndex, strEndIndex).substring(strStart.length());
	        return result;
	    }
		@RequestMapping("/multiImportPhoto")
		public @ResponseBody Object multiImportPhoto(HttpServletRequest request) throws Exception {
			boolean isMultipart = ServletFileUpload.isMultipartContent(request);
//			SysSystemParamExample example = new SysSystemParamExample();
//			example.or().andSysParamCodeEqualTo("photoManage");
			SysSystemParam param = sysformconfigService.getSystemParam("photoManage");
			 if (isMultipart&&param!=null) { 
//				 	SysSystemParam param = sParamMapper.selectByExample(example).get(0);
			        FileItemFactory factory = new DiskFileItemFactory(); 
			        ServletFileUpload upload = new ServletFileUpload(factory); 
			        // 得到所有的表单域，它们目前都被当作FileItem 
			        List<FileItem> fileItems = upload.parseRequest(request); 
			        String id = ""; 
			        String fileName = ""; 
			        FileItem tempFileItem = null; 
			        String[] mark = new String[2];
			        int num = 0;
			        //for (FileItem fileItem : fileItems) { 
			          FileItem fileItem = fileItems.get(0);
			          if (fileItem.getFieldName().equals("file-zh[]")) { 
			            tempFileItem = fileItem; 
			          } 
			          String name = fileItem.getName();
			          //String path="E:\\myehr\\Myeclipse\\myeclipse\\apache-tomcat-6.0.45\\webapps\\myehr\\"+code+".jpg";
			          byte data[] = fileItem.get();
			          
			          Map<String,Object> maps  =new  HashMap<String,Object>();
			          //工号
			          String deptCode = "";
			          int A0188 = 0;
			          if (name!=null) {
			        	  try {
			        		  
			        		  //logger.info(name.split("_")[1]);
			        		  //deptCode = name.split("_")[1].split("\\.")[0];
			        		  fileUpController f=new fileUpController();
			        		  deptCode = f.namesub(name, "{", "}");
			        		  String cname1 = name.substring(0,name.indexOf("{"));
			        		  
			        		  //根据工号找ID
			        		  String sql = param.getSysParamRemark().replaceAll("\\["+param.getSysParamValue1()+"\\]", deptCode);
			        		  Map map = MybatisUtil.queryOne("runtime.selectSql", sql);
			        		  C11Example exampleC11 = new C11Example();
			        		  exampleC11.or().andA0188EqualTo(Integer.valueOf(map.get(param.getSysParamValue2())+""));
			        		  String CNAME = map.get(param.getSysParamEntry())+"";
			        		  A0188 = Integer.valueOf(map.get(param.getSysParamValue2())+"");
			        		  
			        		  if(cname1.equals(CNAME)){
					          try {
					        	  if (cMapper.selectByExample(exampleC11)!=null&&cMapper.selectByExample(exampleC11).size()>0){
					        		  String str = Base64.encodeBase64String(data);
					        		  data = str.getBytes();
						        	  maps.put("Picture", data);
							          maps.put("DeptCode", A0188);
							          sMapper.multiUpdate(maps);
							          mark[num] = "true";
							          mark[num+1] = name;
								  } else {
									  C11 c = new C11();
									  c.setA0188(A0188);
									  cMapper.insert(c);
									  String str = Base64.encodeBase64String(data);
					        		  data = str.getBytes();
									  maps.put("Picture", data);
							          maps.put("DeptCode", A0188);
							          sMapper.multiUpdate(maps);
							          mark[num] = "true";//图片保存成功
							          mark[num+1] = name;
								  }
					        	  sysformconfigService.setEmpPhotoInfo(A0188+"");
								} catch (Exception e) {
									// TODO: handle exception
									mark[num] = "false2";//图片保存出错
									mark[num+1] = name;
								}
			        		  }else{
			        			  mark[num] = "false3";//员工名称与工号不匹配
			        			  mark[num+1] = name;
			        		  }
						  } catch (Exception e) {
							  mark[num] = "false1";//图片命名出错
							  mark[num+1] = name;
						  }
			          }else{
			        	  return mark; 
			          }
				    //} 
			        //JSONArray jsonArry = JSON.stringify(mark);
			        return mark; 
			 	} 
			return null; 
		}
		public static String namesub(String str, String strStart, String strEnd) {

	        /* 找出指定的2个字符在 该字符串里面的 位置 */
	        int strStartIndex = str.indexOf(strStart);
	        int strEndIndex = str.indexOf(strEnd);

	        /* index 为负数 即表示该字符串中 没有该字符 */
	        if (strStartIndex < 0) {
	            return "字符串 :---->" + str + "<---- 中不存在 " + strStart + ", 无法截取目标字符串";
	        }
	        if (strEndIndex < 0) {
	            return "字符串 :---->" + str + "<---- 中不存在 " + strEnd + ", 无法截取目标字符串";
	        }
	        /* 开始截取 */
	        String result = str.substring(strStartIndex, strEndIndex).substring(strStart.length());
	        return result;
	    }
	//动态照片管理	
	@RequestMapping("/multiImportPhotox")
	public @ResponseBody Object multiImportPhotox(HttpServletRequest request) throws Exception {
		
		String[] mark = new String[3];
		try {
			request.setCharacterEncoding("utf-8");
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}  //设置编码
	    //获得磁盘文件条目工厂
	    DiskFileItemFactory factory = new DiskFileItemFactory();
	    
	    
	   /* SysSystemParamExample example1 = new SysSystemParamExample();
		example1.or().andSysParamCodeEqualTo("photoManage");
		SysSystemParam param = sParamMapper.selectByExample(example1).get(0);*/
	    SysSystemParam param = sysformconfigService.getSystemParam("photoManage");
	    //获取文件需要上传到的路径
	    /*SysSystemParamExample example = new SysSystemParamExample();
	    example.or().andSysParamCodeEqualTo("fileManage").andSysParamEntryEqualTo("filePath");
	    String path = sParamMapper.selectByExample(example).get(0).getSysParamValue1();
	    String ip = sParamMapper.selectByExample(example).get(0).getSysParamValue2();*/
	    SysSystemParam param1 = sysformconfigService.getSystemParam("fileManage");
	    String path = param1.getSysParamValue1();
	    String ip = param1.getSysParamValue2();
		String[] fileFolder = {"人员照片"}; 
		for (int i = 0; i < fileFolder.length; i++) {
			File file1=new File(path+"\\"+fileFolder[i]);
			if(!file1.exists()){
				file1.mkdirs();//mkdirs可生成多级路径,mkdir()生成一级
			}
		}
		File file=new File(path);
		if(!file.exists()){
			file.mkdirs();
		}
	    //如果没以下两行设置的话，上传大的 文件 会占用 很多内存，
	    //设置暂时存放的 存储室 , 这个存储室，可以和 最终存储文件 的目录不同
	    factory.setRepository(new File(path));
	    //设置 缓存的大小，当上传文件的容量超过该缓存时，直接放到 暂时存储室
	    factory.setSizeThreshold(1024*1024) ;
	    //高水平的API文件上传处理
	    ServletFileUpload upload = new ServletFileUpload(factory);
        //可以上传多个文件
        @SuppressWarnings("unchecked")
		List<FileItem> list = (List<FileItem>)upload.parseRequest(request);
        for(FileItem item : list){
            //获取表单的属性名字
            String name = item.getName() ;
            String fieldName = item.getFieldName();
	          //工号
	          String deptCode = "";
	          int A0188 = 0;
	          if (name!=null) {
	        	  try {
	        		  deptCode = name.split("_")[1].split("\\.")[0];
	        		  //根据工号找ID
	        		  String sql = param.getSysParamRemark().replaceAll("\\["+param.getSysParamValue1()+"\\]", deptCode);
	        		  Map map = MybatisUtil.queryOne("runtime.selectSql", sql);
	        		  A0188 = Integer.valueOf(map.get(param.getSysParamValue2())+"");
			          try {
			        	//如果获取的 表单信息是普通的 文本 信息
				            if(item.isFormField()){
				                //获取用户具体输入的字符串 ，名字起得挺好，因为表单提交过来的是 字符串类型的
				                String value = item.getString() ;
				                request.setAttribute(name, value);
				            }else{//对传入的非 简单的字符串进行处理 ，比如说二进制的 图片，电影这些
				                //获取路径名
				                //索引到最后一个反斜杠
				                int start = name.lastIndexOf("\\");
				                //截取 上传文件的 字符串名字，加1是 去掉反斜杠，
				                path = path+"\\人员照片";//所在物理位置
				                String filename = name.substring(start+1);
				                request.setAttribute(fieldName, filename);
				                //真正写到磁盘上
				                //它抛出的异常 用exception 捕捉
				                //item.write( new File(path,filename) );//第三方提供的
				                //手动写的
				                OutputStream out = new FileOutputStream(new File(path,filename));
				                InputStream in = item.getInputStream() ;
				                int length = 0 ;
				                byte [] buf = new byte[1024] ;
				                logger.info("获取上传文件的总共的容量："+item.getSize());
				                // in.read(buf) 每次读到的数据存放在   buf 数组中
				                while( (length = in.read(buf) ) != -1){
				                    //在   buf 数组中 取出数据 写到 （输出流）磁盘上
				                    out.write(buf, 0, length);
				                }
				                in.close();
				                out.close();
				                mark[0] = "true";//图片保存成功
						        mark[1] = name;

					            SysSystemParam param2 = sysformconfigService.getSystemParam("photoPath");
					            String photoPath = param2.getSysParamValue1()+"/人员照片/"+name;
					            sysformconfigService.setEmpPhotoByUserId(A0188+"",photoPath);
				                return mark;
				            }
				            
				            //sysformconfigService.setEmpPhotoInfo(A0188+"");
						} catch (Exception e) {
							// TODO: handle exception
							mark[0] = "false2";//图片保存出错
							mark[1] = name;
						}
				  } catch (Exception e) {
					  mark[0] = "false1";//图片命名出错
					  mark[1] = name;
				  }
	          }else{
	        	  return mark; 
	          }
	        return mark; 
        }
		return mark;
}
		
	//saveToImgByStr
	//查询所有数据
	@RequestMapping("/saveToImgByStr")
	public @ResponseBody  ModelAndView saveToImgByStr(HttpServletRequest request,HttpServletResponse resp) throws Exception {
		String[] Codes =request.getParameter("Code").split(",");
		String Code = "";
		for (int i = 0; i < Codes.length; i++) {
			if (i==0) {
				Code += "'"+Codes[i]+"'";
			} else {
				Code += ",'"+Codes[i]+"'";
			}
		}
//		C11Example example = new C11Example();
//		example.or().andA0188EqualTo(4845);
//		List<C11> cList = cMapper.selectByExample(example);
		String sql0 = "SELECT a01.a0101 as name,a01.a0190 as code,C11.C1101 as C1101 from c11 join a01 on C11.a0188=a01.A0188 WHERE a01.a0190 in ("+Code+")";//
		List<Map> cList = MybatisUtil.queryList("runtime.selectSql", sql0);
		String imgPath = "E:/myehr";
		String imgName = "";
		String nameString = "";
		String codeString = "";
		String[] filePathArray = new String[cList.size()];  
		int i=0;
		for (Map c11 : cList) {
//			byte data[] = Base64.decode((data[]) c11.get("C1101"));
//			String picture = Base64.encodeBase64String((byte[]) c11.get("C1101"));
			nameString = (String) c11.get("name");
			codeString = (String) c11.get("code");
			//imgName = nameString+"_"+codeString+".jpg";
			imgName = nameString+"{"+codeString+"}.jpg";
			if(c11.get("C1101")!=null){
				String picture = new String((byte[]) c11.get("C1101"));
//				logger.info(picture);
				BASE64Decoder decoder = new BASE64Decoder();
				CardAndCardFormBeansUtil.saveToImgByStr(decoder.decodeBuffer(picture), imgPath, imgName);
				filePathArray[i]=imgName;
			}
			
			i++;
		}
		String zipFileName = "test.zip";
		  
	    //这些文件都是存在的，我是写死了的，可以从页面传名称过来 
//		String path = getServletContext().getRealPath("/") + "\\image";
	  
	    resp.setContentType("application/x-msdownload" ); // 通知客户文件的MIME类型：
	    resp.setHeader("Content-disposition","attachment;filename=" + zipFileName);
	  
	    ZipOutputStream zos = new ZipOutputStream(resp.getOutputStream());
	    byte[] buffer = new byte[1024];  
	    for (String filePath : filePathArray) {
	    	File file = new File(imgPath +"\\" + filePath);
//	    	doZip(file, zos);
	    	try {
	    		FileInputStream fis = new FileInputStream(imgPath +"\\" + filePath);  
		    	zos.putNextEntry(new ZipEntry(file.getName())); 
		    	//设置压缩文件内的字符编码，不然会变成乱码  
//		    	((Object) zos).setEncoding("GBK");  
		    	int len;  
		    	// 读入需要下载的文件的内容，打包到zip文件  
		    	while ((len = fis.read(buffer)) > 0) {  
		    		zos.write(buffer, 0, len);  
		    	}  
		        zos.closeEntry();  
	            fis.close();
			} catch (Exception e) {
				// TODO: handle exception
				logger.info(file.getName());
			} 
	    }   
	    zos.close();
	    return null;
	}
	
//查询所有数据
@RequestMapping("/newFileUp")
public @ResponseBody Object newFileUp(HttpServletRequest request) throws Exception {
	String userId = (String)(request.getSession().getAttribute("userid")+"");
	String[] mark = new String[3];
	String objType =request.getParameter("objType");
	String objId =request.getParameter("objId");
	String tag =request.getParameter("tag");
	logger.info(request.getParameter("tag"));
	logger.info(tag);
	SysFileupload fileupload = new SysFileupload();
	request.setCharacterEncoding("utf-8");  //设置编码
    //获得磁盘文件条目工厂
    DiskFileItemFactory factory = new DiskFileItemFactory();
    //获取文件需要上传到的路径
    SysSystemParamExample example = new SysSystemParamExample();
    example.or().andSysParamCodeEqualTo("fileManage").andSysParamEntryEqualTo("filePath");
    String path = sParamMapper.selectByExample(example).get(0).getSysParamValue1();
    String ip = sParamMapper.selectByExample(example).get(0).getSysParamValue2();
	//String path2 = request.getSession().getServletContext().getRealPath("/upload1");
	String[] fileFolder = {"文档","图片","视频","音频","其他"}; 
	for (int i = 0; i < fileFolder.length; i++) {
		File file1=new File(path+"\\"+fileFolder[i]);
		if(!file1.exists()){
			file1.mkdirs();//mkdirs可生成多级路径,mkdir()生成一级
		}
	}
	File file=new File(path);
	if(!file.exists()){
		file.mkdirs();
	}
   // String path1 = "c:/upload1";
    //如果没以下两行设置的话，上传大的 文件 会占用 很多内存，
    //设置暂时存放的 存储室 , 这个存储室，可以和 最终存储文件 的目录不同
    /**
     * 原理 它是先存到 暂时存储室，然后在真正写到 对应目录的硬盘上， 
     * 按理来说 当上传一个文件时，其实是上传了两份，第一个是以 .tem 格式的 
     * 然后再将其真正写到 对应目录的硬盘上
     */
    factory.setRepository(new File(path));
    //设置 缓存的大小，当上传文件的容量超过该缓存时，直接放到 暂时存储室
    factory.setSizeThreshold(1024*1024) ;
    //高水平的API文件上传处理
    ServletFileUpload upload = new ServletFileUpload(factory);
    try {
        //可以上传多个文件
        @SuppressWarnings("unchecked")
		List<FileItem> list = (List<FileItem>)upload.parseRequest(request);
        for(FileItem item : list){
            //获取表单的属性名字
            String name = item.getFieldName();
            //String xxxString= item.getString("tag");
            //如果获取的 表单信息是普通的 文本 信息
            if(item.isFormField()){
                //获取用户具体输入的字符串 ，名字起得挺好，因为表单提交过来的是 字符串类型的
                String value = item.getString() ;
                request.setAttribute(name, value);
            }else{//对传入的非 简单的字符串进行处理 ，比如说二进制的 图片，电影这些
                /**
                 * 以下三步，主要获取 上传文件的名字
                 *  文档文件:txt,doc,wps,rtf,html,pdf

					压缩文件:rar,zip
					
					图形文件:bmp,gif,jpg,pic,png,tif
					
					音频文件:wav,aif,mp3
					
					视频文件:avi,mp4,swf
					
					其他文件:......
                 */
                //获取路径名
                String value = item.getName() ;
                //索引到最后一个反斜杠
                int start = value.lastIndexOf("\\");
                //截取 上传文件的 字符串名字，加1是 去掉反斜杠，
                path = ChangePath(value,path);//所在物理位置
                String filename = value.substring(start+1);
                request.setAttribute(name, filename);
                fileupload.setFilename(item.getName());//
                fileupload.setFileFolder(ChangeType(value));//所在文件夹
                fileupload.setName(item.getFieldName());
                //fileupload.setFilepath(path+"\\"+item.getName());
                fileupload.setFilepath("http://"+ip+"/"+ChangeType(value)+"/"+item.getName());//服务器访问地址
                fileupload.setClientfilename(ChangeType2(value));
                fileupload.setClientpath(path+"\\"+item.getName());//物理路径
                fileupload.setContenttype(item.getContentType());
                fileupload.setUploadtime(new Date());
                fileupload.setUploaduser(userId);
                fileupload.setFileSize(new BigDecimal(item.getSize()));
                fileupload.setSavetype(Long.valueOf(1));
                if(objId!=null&&!objId.equals("")){
                    fileupload.setObjId(objId);
                }else {
                    fileupload.setObjId("0");
				}
                fileupload.setObjType(objType);
                fileupload.setFileTag(tag);
                fMapper.insert(fileupload);
                //真正写到磁盘上
                //它抛出的异常 用exception 捕捉
                //item.write( new File(path,filename) );//第三方提供的
                //手动写的
                OutputStream out = new FileOutputStream(new File(path,filename));
                InputStream in = item.getInputStream() ;
                int length = 0 ;
                byte [] buf = new byte[1024] ;
                logger.info("获取上传文件的总共的容量："+item.getSize());
                // in.read(buf) 每次读到的数据存放在   buf 数组中
                while( (length = in.read(buf) ) != -1){
                    //在   buf 数组中 取出数据 写到 （输出流）磁盘上
                    out.write(buf, 0, length);
                }
                in.close();
                out.close();
                mark[0] = "true";//图片保存成功
		        mark[1] = value;
                mark[2] = fileupload.getFileid()+"";//图片命名出错
                return mark;
            }
        }
    }catch (FileUploadException e) {
    	
        e.printStackTrace();logger.error(e.getMessage(),e);
    }catch (Exception e) {
    	
        e.printStackTrace();logger.error(e.getMessage(),e);
    }
    return null;
}
	

//查询所有数据
@RequestMapping("/checkedPoint_newFileUp")
public @ResponseBody Object checkedPoint_newFileUp(HttpServletRequest request) throws Exception {
	String userId = (String)(request.getSession().getAttribute("userid")+"");
	String objType =request.getParameter("objType");
	String objId =request.getParameter("objId");
	String tag =request.getParameter("tag");
	String checkedId =request.getParameter("checkedId");
	String overMark =request.getParameter("overMark");
	String changeMark =request.getParameter("changeMark");
	//后台上传权限控制
	SysUserRoleExample example = new SysUserRoleExample();
	example.or().andUserIdEqualTo(Integer.valueOf(userId));
	List<SysUserRole> userRoles = userRoleMapper.selectByExample(example);
	boolean shangchuan = true;
	if (userRoles.size()>0) {
		List<BigDecimal> roleIds = new ArrayList<BigDecimal>();
		for (SysUserRole role : userRoles) {
			roleIds.add(new BigDecimal(role.getRoleId()));
		}
		SysFileButtonRoleExample example1 = new SysFileButtonRoleExample();
		if(userId.equals("1")||shangchuan){
			shangchuan = true;
		}else {
			example1.or().andFormIdEqualTo(new BigDecimal(objId)).andRoleIdIn(roleIds).andButtonCodeEqualTo("文件上传");
			List<SysFileButtonRole> roles = sFileButtonRoleMapper.selectByExample(example1);
			for (SysFileButtonRole sysFileButtonRole : roles) {
				if (!sysFileButtonRole.getButtonRoleRemark().equals("3")) {
					shangchuan = true;
				}
			}
		}
	}
	if (shangchuan) {
		String[] result = saveFile(userId,objType,objId,tag,checkedId,request,Boolean.parseBoolean(overMark),Boolean.parseBoolean(changeMark));
		return result;
	}else {
		return null;
	}
	
	
	
}
//checkedPoint_deleteFile
//删除关联元素
@RequestMapping("/checkedPoint_deleteFile")
public @ResponseBody Object checkedPoint_deleteFile(HttpServletRequest request) throws Exception {
	String userId = (String)(request.getSession().getAttribute("userid")+"");
	String checkedId = request.getParameter("checkedId");
	String fileId = request.getParameter("fileId");
	String formId = request.getParameter("formId");
	String[] recode = new String[2];
	recode[0]="1";
	recode[1]="操作成功";
//	SysFileChecked checked = sFileCheckedMapper.selectByPrimaryKey(new BigDecimal(checkedId));
//	checked.setRelatedFileId(deleteElement(checked.getRelatedFileId(),fileId));
	//后台删除权限控制
//	SysUserRoleExample example = new SysUserRoleExample();
//	example.or().andUserIdEqualTo(Integer.valueOf(userId));
//	List<SysUserRole> userRoles = userRoleMapper.selectByExample(example);
	boolean deleteAction = true;
	/*if (userRoles.size()>0) {
		List<BigDecimal> roleIds = new ArrayList<BigDecimal>();
		for (SysUserRole role : userRoles) {
			roleIds.add(new BigDecimal(role.getRoleId()));
		}
		SysFileButtonRoleExample example1 = new SysFileButtonRoleExample();
		example1.or().andFormIdEqualTo(new BigDecimal(formId)).andRoleIdIn(roleIds).andButtonCodeEqualTo("文件删除");
		List<SysFileButtonRole> roles = sFileButtonRoleMapper.selectByExample(example1);
		for (SysFileButtonRole sysFileButtonRole : roles) {
			if (!sysFileButtonRole.getButtonRoleRemark().equals("3")) {
				deleteAction = true;
			}
		}
	}*/
	if (deleteAction) {
//		sFileCheckedMapper.updateByPrimaryKey(checked);
		fMapper.deleteByPrimaryKey(new BigDecimal(fileId));
		recode[0]="0";
		return recode;
	}else {
		return recode;
	}
}
//删除关联元素
@RequestMapping("/form_deleteFile")
public @ResponseBody Object form_deleteFile(HttpServletRequest request) throws Exception {
	String userId = (String)(request.getSession().getAttribute("userid")+"");
	String fileId = request.getParameter("fileId");
	String formId = request.getParameter("formId");
	String[] recode = new String[2];
	recode[0]="1";
	recode[1]="操作成功";
	//后台删除权限控制
	/*SysUserRoleExample example = new SysUserRoleExample();
	example.or().andUserIdEqualTo(Integer.valueOf(userId));
	List<SysUserRole> userRoles = userRoleMapper.selectByExample(example);
	boolean deleteAction = false;
	if (userRoles.size()>0) {
		List<BigDecimal> roleIds = new ArrayList<BigDecimal>();
		for (SysUserRole role : userRoles) {
			roleIds.add(new BigDecimal(role.getRoleId()));
		}
		SysFileButtonRoleExample example1 = new SysFileButtonRoleExample();
		example1.or().andFormIdEqualTo(new BigDecimal(formId)).andRoleIdIn(roleIds).andButtonCodeEqualTo("文件删除");
		List<SysFileButtonRole> roles = sFileButtonRoleMapper.selectByExample(example1);
		for (SysFileButtonRole sysFileButtonRole : roles) {
			if (!sysFileButtonRole.getButtonRoleRemark().equals("3")) {
				deleteAction = true;
			}
		}
	}
	if (deleteAction) {*/
	try {
		fMapper.deleteByPrimaryKey(new BigDecimal(fileId));
		recode[0]="0";
		return recode;
	} catch (Exception e) {
		return recode;
	}
		
	/*}else {
		return null;
	}*/
}


public String[] saveFile(String userId,String objType,String objId,String tag,String checkedId,HttpServletRequest request,boolean overMark,boolean changeMark) {
	SysFileupload fileupload = new SysFileupload();
	String[] mark = new String[3];
	try {
		request.setCharacterEncoding("utf-8");
	} catch (UnsupportedEncodingException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}  //设置编码
    //获得磁盘文件条目工厂
    DiskFileItemFactory factory = new DiskFileItemFactory();
    //获取文件需要上传到的路径
    SysSystemParamExample example = new SysSystemParamExample();
    example.or().andSysParamCodeEqualTo("fileManage").andSysParamEntryEqualTo("filePath");
    String path = sParamMapper.selectByExample(example).get(0).getSysParamValue1();
    String ip = sParamMapper.selectByExample(example).get(0).getSysParamValue2();
	//String path2 = request.getSession().getServletContext().getRealPath("/upload1");
	String[] fileFolder = {"文档","图片","视频","音频","其他"}; 
	for (int i = 0; i < fileFolder.length; i++) {
		File file1=new File(path+"\\"+fileFolder[i]);
		if(!file1.exists()){
			file1.mkdirs();//mkdirs可生成多级路径,mkdir()生成一级
		}
	}
	File file=new File(path);
	if(!file.exists()){
		file.mkdirs();
	}
    //如果没以下两行设置的话，上传大的 文件 会占用 很多内存，
    //设置暂时存放的 存储室 , 这个存储室，可以和 最终存储文件 的目录不同
    /**
     * 原理 它是先存到 暂时存储室，然后在真正写到 对应目录的硬盘上， 
     * 按理来说 当上传一个文件时，其实是上传了两份，第一个是以 .tem 格式的 
     * 然后再将其真正写到 对应目录的硬盘上
     */
    factory.setRepository(new File(path));
    //设置 缓存的大小，当上传文件的容量超过该缓存时，直接放到 暂时存储室
    factory.setSizeThreshold(1024*1024) ;
    //高水平的API文件上传处理
    ServletFileUpload upload = new ServletFileUpload(factory);
    try {
        //可以上传多个文件
        @SuppressWarnings("unchecked")
		List<FileItem> list = (List<FileItem>)upload.parseRequest(request);
        for(FileItem item : list){
            //获取表单的属性名字
            String name = item.getFieldName();
            //String xxxString= item.getString("tag");
            //如果获取的 表单信息是普通的 文本 信息
            if(item.isFormField()){
                //获取用户具体输入的字符串 ，名字起得挺好，因为表单提交过来的是 字符串类型的
                String value = item.getString() ;
                request.setAttribute(name, value);
            }else{//对传入的非 简单的字符串进行处理 ，比如说二进制的 图片，电影这些
                /**
                 * 以下三步，主要获取 上传文件的名字
                 *  文档文件:txt,doc,wps,rtf,html,pdf

					压缩文件:rar,zip
					
					图形文件:bmp,gif,jpg,pic,png,tif
					
					音频文件:wav,aif,mp3
					
					视频文件:avi,mp4,swf
					
					其他文件:......
                 */
                //获取路径名
                String value = item.getName() ;
                //索引到最后一个反斜杠
                int start = value.lastIndexOf("\\");
                //截取 上传文件的 字符串名字，加1是 去掉反斜杠，
                path = ChangePath(value,path);//所在物理位置
                String filename = value.substring(start+1);
                request.setAttribute(name, filename);
                
                fileupload.setFilename(item.getName());//
                fileupload.setFileFolder(ChangeType(value));//所在文件夹
                fileupload.setName(item.getFieldName());
                //fileupload.setFilepath(path+"\\"+item.getName());
                
                fileupload.setFilepath("http://"+ip+"/"+ChangeType(value)+"/"+item.getName());//服务器访问地址
                fileupload.setClientfilename(ChangeType2(value));
                
                fileupload.setClientpath(path+"\\"+item.getName());//物理路径
                fileupload.setContenttype(item.getContentType());
                fileupload.setUploadtime(new Date());
                fileupload.setUploaduser(userId);
                fileupload.setFileSize(new BigDecimal(item.getSize()));
                fileupload.setSavetype(Long.valueOf(1));
                if(objId!=null&&!objId.equals("")){
                    fileupload.setObjId(objId);
                }else {
                    fileupload.setObjId("0");
				}
                fileupload.setObjType(objType);
                fileupload.setFileTag(tag);
                if (checkedId!=null&&objType.equals("checkedAndForm")) {//检查点附件上传
                	SysFileChecked checked = sFileCheckedMapper.selectByPrimaryKey(new BigDecimal(checkedId));
                	if (checked.getRelatedFileId()!=null&&!checked.getRelatedFileId().equals("")) {
                    	String[] fileIds = checked.getRelatedFileId().split(",");
                    	String[] fileNames = checked.getRelatedFileName().split(",");
            			String flag = checkSameName(fileIds,item.getName());
            			if (flag.equals("true")) {
            				fMapper.insert(fileupload);
            				checked.setRelatedFileId(checked.getRelatedFileId()+","+fileupload.getFileid());
            				checked.setRelatedFileName(checked.getRelatedFileName()+","+fileupload.getFilename());
            				sFileCheckedMapper.updateByPrimaryKey(checked);
						}
					}else {
						fMapper.insert(fileupload);
        				checked.setRelatedFileId(fileupload.getFileid()+"");
        				checked.setRelatedFileName(fileupload.getFilename());
						sFileCheckedMapper.updateByPrimaryKey(checked);
					}
                	
				}else {
					fMapper.insert(fileupload);
				} 
                
                //真正写到磁盘上
                //它抛出的异常 用exception 捕捉
                //item.write( new File(path,filename) );//第三方提供的
                //手动写的
                OutputStream out = new FileOutputStream(new File(path,filename));
                InputStream in = item.getInputStream() ;
                int length = 0 ;
                byte [] buf = new byte[1024] ;
                logger.info("获取上传文件的总共的容量："+item.getSize());
                // in.read(buf) 每次读到的数据存放在   buf 数组中
                while( (length = in.read(buf) ) != -1){
                    //在   buf 数组中 取出数据 写到 （输出流）磁盘上
                    out.write(buf, 0, length);
                }
                in.close();
                out.close();
                mark[0] = "true";//图片保存成功
		        mark[1] = value;
                mark[2] = fileupload.getFileid()+"";//图片命名出错
                return mark;
                
            }
        }
    }catch (FileUploadException e) {
    	
        e.printStackTrace();logger.error(e.getMessage(),e);
    }catch (Exception e) {
    	
        e.printStackTrace();logger.error(e.getMessage(),e);
    }
	return mark;
}

private String checkSameName(String[] files, String name) {
	boolean flag = true;
	String fileIds = "";
	for (int i = 0; i < files.length; i++) {
		if (!files[i].equals("")) {
			SysFileupload fileupload = fMapper.selectByPrimaryKey(new BigDecimal(files[i]));
			if (!fileupload.getFilename().equals(name)) {
				fileIds += fileupload.getFileid()+"";
			}
		}
	}
	if (flag) {
		return "true";
	} else {
		return fileIds;
	}
}	
private String deleteElement(String all, String obj) {
	String fileIds = "";
	String[] objs = all.split(",");
	for (int i = 0; i < objs.length; i++) {
		if (!objs[i].equals(obj)) {
			if (i==0) {
				fileIds += objs[i];
			}else {
				fileIds += ","+objs[i];
			}
		}
	}
	return fileIds;
}	

	/**
	 * 查询上传文件基本信息
	 * @param fileId
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/getFilenameById")
	public @ResponseBody Object getFilenameById(HttpServletRequest request) throws Throwable {
		String fileId = request.getParameter("fileId");
		return fMapper.selectByPrimaryKey(new BigDecimal(fileId));
	}
	
	@RequestMapping("/deleteFile")
	public void deleteFile(HttpServletRequest request) throws Throwable {
		String fileId = request.getParameter("fileId");
		fMapper.deleteByPrimaryKey(new BigDecimal(fileId));
	}
	
	
	/**
	 * 查询字典基本信息根据字典编码
	 * @param fileId
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/getDictData")
	public @ResponseBody Object getDictData(HttpServletRequest request) throws Throwable {
		String DictName = request.getParameter("DictName");
		SysDictTypeExample example1 = new SysDictTypeExample();
		SysDictEntryExample example2 = new SysDictEntryExample();
		example1.or().andDictTypeCodeEqualTo(DictName);
		SysDictType type = dTypeMapper.selectByExample(example1).get(0);
		example2.or().andDictTypeIdEqualTo(type.getDictTypeId());
		return dEntryMapper.selectByExample(example2);
	}
	
	/**
	 * 查询字典基本信息根据字典编码
	 * @param fileIdupdataTags
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/updataTags")
	public @ResponseBody void updataTags(HttpServletRequest request) throws Throwable {
		String tag = request.getParameter("tag");
		String fileId = request.getParameter("fileId");
		SysFileupload fileupload = fMapper.selectByPrimaryKey(new BigDecimal(fileId));
		fileupload.setFileTag(tag);
		fMapper.updateByPrimaryKey(fileupload);
	}
	
	public String ChangeType(String fileName) {
		String[] files = fileName.split("\\.");
		String type = "";
		String fileTypeZ = files[files.length-1].toLowerCase();
		if (fileTypeZ.equals("zip")||fileTypeZ.equals("rar")) {
					type += "压缩";
				} else if (fileTypeZ.equals("ogg")||fileTypeZ.equals("wav")||fileTypeZ.equals("aif")||fileTypeZ.equals("mp3")) {
					type += "音频";
				} else if (fileTypeZ.equals("avi")||fileTypeZ.equals("mp4")||fileTypeZ.equals("swf")||fileTypeZ.equals("wmv")) {
					type += "视频";
				} else if (fileTypeZ.equals("txt")||fileTypeZ.equals("doc")||fileTypeZ.equals("wps")||fileTypeZ.equals("rtf")||fileTypeZ.equals("html")||fileTypeZ.equals("pdf")||fileTypeZ.equals("xls")||fileTypeZ.equals("xlsx")) {
					type += "文档";
				} else if (fileTypeZ.equals("gif")||fileTypeZ.equals("bmp")||fileTypeZ.equals("jpg")||fileTypeZ.equals("jpeg")||fileTypeZ.equals("pic")||fileTypeZ.equals("png")) {
					type += "图片";
				} else{
					type += "其他";
				}
		return type;
	}
	
	//预览类型
	public String ChangeType2(String fileName) {
		String[] files = fileName.split("\\.");
		String type = "";//|||||
		String fileTypeZ = files[files.length-1].toLowerCase();
		if (fileTypeZ.equals("gif")||fileTypeZ.equals("png")||fileTypeZ.equals("jpeg")||fileTypeZ.equals("jpg")) {
					type += "image";
				} else if (fileTypeZ.equals("htm")||fileTypeZ.equals("html")) {
					type += "html";
				} else if (fileTypeZ.equals("rtf")||fileTypeZ.equals("docx")||fileTypeZ.equals("xlsx")||fileTypeZ.equals("pptx")||fileTypeZ.equals("pps")||fileTypeZ.equals("potx")||fileTypeZ.equals("ods")) {
					type += "office";
				} else if (fileTypeZ.equals("txt")||fileTypeZ.equals("md")||fileTypeZ.equals("csv")||fileTypeZ.equals("nfo")||fileTypeZ.equals("ini")||fileTypeZ.equals("json")||fileTypeZ.equals("php")||fileTypeZ.equals("js")||fileTypeZ.equals("css")) {
					type += "text";
				} else if (fileTypeZ.equals("mp4")||fileTypeZ.equals("mov")||fileTypeZ.equals("webm")||fileTypeZ.equals("avi")||fileTypeZ.equals("wmv")) {
					type += "video";
				} else if (fileTypeZ.equals("ogg")||fileTypeZ.equals("mp3")||fileTypeZ.equals("wav")) {
					type += "audio";
				} else if (fileTypeZ.equals("swf")) {
					type += "flash";
				} else if (fileTypeZ.equals("pdf")) {
					type += "pdf";
				} else{
					type += "other";
				}
		return type;
	}
	
	public String ChangePath(String fileName,String type) {
		String[] files = fileName.split("\\.");
		String fileTypeZ = files[files.length-1].toLowerCase();
		if (fileTypeZ.equals("zip")||fileTypeZ.equals("rar")) {
					type += "\\压缩";
				} else if (fileTypeZ.equals("ogg")||fileTypeZ.equals("wav")||fileTypeZ.equals("aif")||fileTypeZ.equals("mp3")) {
					type += "\\音频";
				} else if (fileTypeZ.equals("avi")||fileTypeZ.equals("mp4")||fileTypeZ.equals("swf")||fileTypeZ.equals("wmv")) {
					type += "\\视频";
				} else if (fileTypeZ.equals("txt")||fileTypeZ.equals("doc")||fileTypeZ.equals("wps")||fileTypeZ.equals("rtf")||fileTypeZ.equals("html")||fileTypeZ.equals("pdf")||fileTypeZ.equals("xls")||fileTypeZ.equals("xlsx")) {
					type += "\\文档";
				} else if (fileTypeZ.equals("gif")||fileTypeZ.equals("bmp")||fileTypeZ.equals("jpg")||fileTypeZ.equals("jpeg")||fileTypeZ.equals("pic")||fileTypeZ.equals("png")) {
					type += "\\图片";
				} else{
					type += "\\其他";
				}
		return type;
	}
	/**
	 * 查询文件信息
	 * @param fileId
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/cardformInitData")
	public @ResponseBody Object cardformInitData(HttpServletRequest request,@RequestBody FileManageParams queryParams) throws Throwable {
		String ShowSql = ""; 
		String FolderSql = "";
		String TagSql = "";
		String objId = "";
		if (queryParams.getShowType().equals("all")) {
			
		}else {
			//ShowSql = "and ";
		}
		if (queryParams.getFolder().equals("all")) {
			
		}else {
			FolderSql = "and file_folder = '"+queryParams.getFolder()+"'";
		}
		if (queryParams.getTag().equals("")) {
			
		}else {
			queryParams.getTag().subSequence(0, queryParams.getTag().length()-1);
			String[] tags = queryParams.getTag().split(",");
			for (int i = 0; i < tags.length; i++) {
				/*if (i==0) {
					TagSql += "file_tag like "+tags[i]+" ";
				} else {*/
					TagSql += " and file_tag like '%"+tags[i]+"%' ";
				//}
			}
		}
		if (queryParams.getFormId()!=null&&!queryParams.getFormId().equals("")) {
			TagSql += " and OBJ_ID = "+queryParams.getFormId()+" ";
		} 
		String sql = "select * from sys_fileupload where name = 'file-zh[]' "+ShowSql+FolderSql+TagSql;
		List<Map> ids = tMapperExt.queryByFormDefSql(sql);
		return ids;
	}
	
	@RequestMapping("/downLoadfile")
	public @ResponseBody ModelAndView downLoadfile(HttpServletRequest request) throws Exception{
		ModelAndView mv = null;
		String FILEID =request.getParameter("FILEID");
		String formId =request.getParameter("formId");
		SysFileupload file = iFileUploadService.selectByPrimaryKey(FILEID);
		String userId = (String)(request.getSession().getAttribute("userid")+"");
		//后台删除权限控制
		SysUserRoleExample example = new SysUserRoleExample();
		example.or().andUserIdEqualTo(Integer.valueOf(userId));
		List<SysUserRole> userRoles = userRoleMapper.selectByExample(example);
		boolean uploadAction = true;
		if (userRoles.size()>0) {
			List<BigDecimal> roleIds = new ArrayList<BigDecimal>();
			for (SysUserRole role : userRoles) {
				roleIds.add(new BigDecimal(role.getRoleId()));
			}
			SysFileButtonRoleExample example1 = new SysFileButtonRoleExample();
			example1.or().andFormIdEqualTo(new BigDecimal(formId)).andRoleIdIn(roleIds).andButtonCodeEqualTo("文件下载");
			List<SysFileButtonRole> roles = sFileButtonRoleMapper.selectByExample(example1);
			for (SysFileButtonRole sysFileButtonRole : roles) {
				if (!sysFileButtonRole.getButtonRoleRemark().equals("3")) {
					uploadAction = true;
				}
			}
		}
		if (uploadAction) {
			String baseExclePath = "";
			baseExclePath = file.getClientpath();
			String filename = file.getFilename();
			String contentType= file.getContenttype();
	        mv = new ModelAndView("forward:/jsp/form/button/download.jsp?filename="+filename+"&contentType="+contentType+"&filePath="+baseExclePath);
	        return mv;
		}else {
			return null;
		}
		
	}
	
	@RequestMapping("/downLoadfile_ACT")
	public @ResponseBody ModelAndView downLoadfile_ACT(HttpServletRequest request) throws Exception{
		ModelAndView mv = null;
		String FILEID =request.getParameter("FILEID");
		String formId =request.getParameter("formId");
		SysFileupload file = iFileUploadService.selectByPrimaryKey(FILEID);
		String userId = (String)(request.getSession().getAttribute("userid")+"");
		//后台删除权限控制
//		SysUserRoleExample example = new SysUserRoleExample();
//		example.or().andUserIdEqualTo(Integer.valueOf(userId));
//		List<SysUserRole> userRoles = userRoleMapper.selectByExample(example);
		boolean uploadAction = true;
//		if (userRoles.size()>0) {
//			List<BigDecimal> roleIds = new ArrayList<BigDecimal>();
//			for (SysUserRole role : userRoles) {
//				roleIds.add(new BigDecimal(role.getRoleId()));
//			}
//			SysFileButtonRoleExample example1 = new SysFileButtonRoleExample();
//			example1.or().andFormIdEqualTo(new BigDecimal(formId)).andRoleIdIn(roleIds).andButtonCodeEqualTo("文件下载");
//			List<SysFileButtonRole> roles = sFileButtonRoleMapper.selectByExample(example1);
//			for (SysFileButtonRole sysFileButtonRole : roles) {
//				if (!sysFileButtonRole.getButtonRoleRemark().equals("3")) {
//					uploadAction = true;
//				}
//			}
//		}
		if (uploadAction) {
			String baseExclePath = "";
			baseExclePath = file.getClientpath();
			String filename = file.getFilename();
			String contentType= file.getContenttype();
	        mv = new ModelAndView("forward:/jsp/form/button/download.jsp?filename="+filename+"&contentType="+contentType+"&filePath="+baseExclePath);
	        return mv;
		}else {
			return null;
		}
		
	}
	
	@RequestMapping("/showPhoto")
	public @ResponseBody Object showPhoto(HttpServletRequest request) throws Exception{
		String A0188 =request.getParameter("A0188");
		C11 c11 = cMapper.selectByPrimaryKey(Integer.valueOf(A0188));
		BASE64Encoder encoder = new BASE64Encoder();
        // 返回Base64编码过的字节数组字符串
        String xxString = encoder.encode(c11.getC1101());
        BASE64Decoder decoder = new sun.misc.BASE64Decoder();
        byte[] yy = decoder.decodeBuffer(xxString);
		return yy;
	}
	
	@RequestMapping("/saveHtmlPicture")
	public void saveHtmlPicture(HttpServletRequest request,@RequestBody ActShowHis showHis) {
		try {
			actShowHisMapper.insert(showHis);
			sysformconfigService.setHtmlPicByMBId(showHis.getMark(),showHis.getImg());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@RequestMapping("/createPicture")
	public @ResponseBody Object createPicture(HttpServletRequest request,@RequestBody ActShowHis showHis) {
		try {
			String imgStr = showHis.getImg().split(",")[1];
			if (imgStr == null){ //图像数据为空  
			}else {
				BASE64Decoder decoder = new BASE64Decoder();  
		        try   
		        {  
		            //Base64解码  
		            byte[] b = decoder.decodeBuffer(imgStr);  
		            for(int i=0;i<b.length;++i)  
		            {  
		                if(b[i]<0)  
		                {//调整异常数据  
		                    b[i]+=256;  
		                }  
		            }  
		            //生成jpeg图片  
		            String imgFilePath = "E:\\photo\\new.png";//新生成的图片  
		            OutputStream out = new FileOutputStream(imgFilePath);      
		            out.write(b);  
		            out.flush();  
		            out.close();   
		        }   
		        catch (Exception e){   
		        	e.printStackTrace();
		        }  
			}
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
		return true;
	}
	
	@RequestMapping("/getHtmlPicture")
	public @ResponseBody Object getHtmlPicture(HttpServletRequest request) throws Exception{
		String procInsId = request.getParameter("procInsId");
		return sysformconfigService.getHtmlPicByMBId(procInsId);
	}
	
	@RequestMapping("/getEmpPhotoSYS")
	public @ResponseBody Object getEmpPhotoSYS(HttpServletRequest request) throws Exception{
		String A0188 = request.getParameter("EMPID");
		if(A0188==null||A0188.equals("")){
			A0188 = request.getSession().getAttribute("empid")+"";
		}
		return ActUtils.getUserPhotoById(A0188,sysformconfigService);
	}
	
	@RequestMapping("/getEmpPhotoByUserId")
	public @ResponseBody Object getEmpPhotoByUserId(HttpServletRequest request) throws Exception{
		String userId = request.getParameter("userId");
		String[] recode = new String[2];
		SysUser user = userMapper.selectByPrimaryKey(Integer.valueOf(userId));
		String A0188 = user.getEmpId()+"";
		SysSystemParam photoPath = (SysSystemParam) sysformconfigService.getSysParam().get("photoPath");
		SysSystemParam empParam = (SysSystemParam) sysformconfigService.getSysParam().get("EMP_ENTITY");
		String sqlx = "select "+empParam.getSysParamRemark()+" from "+empParam.getSysParamValue1()+" where "+empParam.getSysParamValue2()+" = '"+A0188+"'";
		Map map = MybatisUtil.queryOne("runtime.selectSql", sqlx);
		if(map!=null){
			String empName = (String) map.get(empParam.getSysParamEntry().split("_")[0]);
			String empCode = (String) map.get(empParam.getSysParamEntry().split("_")[1]);
			recode[0]=photoPath.getSysParamValue1()+"/人员照片/"+empName+"_"+empCode+".jpg";
		}
		return recode;
	}
	
	@RequestMapping("/getCheckedByFormId")
	public @ResponseBody Object getCheckedByFormId(HttpServletRequest request) throws Exception{
		String formId =request.getParameter("formId");
		SysFileCheckedExample example = new SysFileCheckedExample();
		example.or().andFormDefIdEqualTo(new BigDecimal(formId));
		List<SysFileChecked> checkeds = sFileCheckedMapper.selectByExample(example);
		List<SysCheckedPointAndFiles> cAndFiles = new ArrayList<SysCheckedPointAndFiles>();
		for (SysFileChecked sysFileChecked : checkeds) {
			SysCheckedPointAndFiles cAndFile = new SysCheckedPointAndFiles();
			cAndFile.setPoint(sysFileChecked);
			if (sysFileChecked.getRelatedFileId()!=null) {
				String[] fileId = sysFileChecked.getRelatedFileId().split(",");
				List<SysFileupload> files = new ArrayList<SysFileupload>();
				for (int i = 0; i < fileId.length; i++) {
					if (!fileId[i].equals("")) {
						SysFileupload fileupload = fMapper.selectByPrimaryKey(new BigDecimal(fileId[i]));
						files.add(fileupload);
					}
				}
				cAndFile.setFiles(files);
			}
			cAndFiles.add(cAndFile);
		}
		return cAndFiles;
	}
	@RequestMapping("/getCheckedByNodeId")
	public @ResponseBody Object getCheckedByNodeId(HttpServletRequest request) throws Exception{
		String taskId =request.getParameter("taskId");
		String nodeId =request.getParameter("nodeId");
		String procId =request.getParameter("procId");
		SysCheckedAndNodeRelationExample example = new SysCheckedAndNodeRelationExample();
		example.or().andActNodeIdEqualTo(nodeId);
		List<SysCheckedAndNodeRelation> checkeds = checkedAndNodeRelationMapper.selectByExample(example);
		List<checkedAndPoints> objs = new ArrayList<checkedAndPoints>();
		for (SysCheckedAndNodeRelation checked : checkeds) {
			checkedAndPoints checkedAndPoints = new checkedAndPoints();
			checkedAndPoints.setChecked(checked);//检查点
			SysCheckedAndPointRelationExample example2 = new SysCheckedAndPointRelationExample();
			example2.or().andCheckedIdEqualTo(checked.getId());
			List<SysCheckedAndPointRelation> points = cPointRelationMapper.selectByExample(example2);
			List<checkedPointAndFiles> sPoints = new ArrayList<checkedPointAndFiles>();
			for (SysCheckedAndPointRelation sysCheckedAndPointRelation : points) {
				checkedPointAndFiles pointAndFiles = new checkedPointAndFiles();
				SysActCheckedPoint point = aCheckedPointMapper.selectByPrimaryKey(sysCheckedAndPointRelation.getPointId());
				SysActCheckedpointResultExample exampleResult = new SysActCheckedpointResultExample();
				exampleResult.or().andCheckedpointIdEqualTo(sysCheckedAndPointRelation.getPointId()).andTaskIdEqualTo(taskId);
				if (actCheckedpointResultMapper.selectByExample(exampleResult).size()>0) {
					SysActCheckedpointResult result = actCheckedpointResultMapper.selectByExample(exampleResult).get(0);
					pointAndFiles.setPointResult(result);
				} 
				pointAndFiles.setPoint(point);
				if (point.getIsFile()!=null&&point.getIsFile().equals("Y")) {
					SysFileuploadExample example3 = new SysFileuploadExample();
					String nodeIdAndPointId = nodeId+"_"+procId+","+ sysCheckedAndPointRelation.getPointId();
					example3.or().andObjTypeEqualTo("nodeId,pointId").andObjIdEqualTo(nodeIdAndPointId);
					List<SysFileupload> files = fMapper.selectByExample(example3);
					pointAndFiles.setFiles(files);
				}
				sPoints.add(pointAndFiles);
			}
			checkedAndPoints.setpFiles(sPoints);
			objs.add(checkedAndPoints);
		}
		return objs;
	}
	@RequestMapping("/getCheckedHisByNodeId")
	public @ResponseBody Object getCheckedHisByNodeId(HttpServletRequest request) throws Exception{
		String taskId1 =request.getParameter("taskId");
		String procId =request.getParameter("procId");
		if (taskId1.equals("null")) {
			return null;
		} else {
			List<ActAndChecked> objx = getCheckHisByNodeId(taskId1);
			List<ActAndCheckAndFile> objy = new ArrayList<ActAndCheckAndFile>();
			for (ActAndChecked actAndChecked : objx) {
				ActHiTaskinstExample example = new ActHiTaskinstExample();
				example.or().andProcInstIdEqualTo(procId).andTaskDefKeyEqualTo(actAndChecked.getNodeKey());
				String taskId = actHiTaskinstMapper.selectByExample(example).get(0).getId()+"";
				List<SysCheckedAndNodeRelation> checkeds = actAndChecked.getObjs();
				List<checkedAndPoints> objs = new ArrayList<checkedAndPoints>();
				ActAndCheckAndFile actAndCheckAndFile = new ActAndCheckAndFile();
				for (SysCheckedAndNodeRelation checked : checkeds) {
					checkedAndPoints checkedAndPoints = new checkedAndPoints();
					checkedAndPoints.setChecked(checked);//检查点
					SysCheckedAndPointRelationExample example2 = new SysCheckedAndPointRelationExample();
					example2.or().andCheckedIdEqualTo(checked.getId());
					List<SysCheckedAndPointRelation> points = cPointRelationMapper.selectByExample(example2);
					List<checkedPointAndFiles> sPoints = new ArrayList<checkedPointAndFiles>();
					for (SysCheckedAndPointRelation sysCheckedAndPointRelation : points) {
						checkedPointAndFiles pointAndFiles = new checkedPointAndFiles();
						SysActCheckedPoint point = aCheckedPointMapper.selectByPrimaryKey(sysCheckedAndPointRelation.getPointId());
						SysActCheckedpointResultExample exampleResult = new SysActCheckedpointResultExample();
						exampleResult.or().andCheckedpointIdEqualTo(sysCheckedAndPointRelation.getPointId()).andTaskIdEqualTo(taskId);
						if (actCheckedpointResultMapper.selectByExample(exampleResult).size()>0) {
							SysActCheckedpointResult result = actCheckedpointResultMapper.selectByExample(exampleResult).get(0);
							pointAndFiles.setPointResult(result);
						} 
						pointAndFiles.setPoint(point);
						if (point.getIsFile()!=null&&point.getIsFile().equals("Y")) {
							SysFileuploadExample example3 = new SysFileuploadExample();
							String nodeIdAndPointId = checked.getActNodeId()+"_"+procId+","+ sysCheckedAndPointRelation.getPointId();
							example3.or().andObjTypeEqualTo("nodeId,pointId").andObjIdEqualTo(nodeIdAndPointId);
							List<SysFileupload> files = fMapper.selectByExample(example3);
							pointAndFiles.setFiles(files);
						}
						sPoints.add(pointAndFiles);
					}
					checkedAndPoints.setpFiles(sPoints);
					objs.add(checkedAndPoints);
				}
				actAndCheckAndFile.setObjs(objs);
				actAndCheckAndFile.setNodeName(actAndChecked.getNodeName());
				objy.add(actAndCheckAndFile);
			}
			return objy;
		}
		
	}
	private List<ActAndChecked> getCheckHisByNodeId(String taskId) {
		// TODO Auto-generated method stub
		if (taskId!=null&&!taskId.equals("null")) {
			ActRuTask task = actRuTaskMapper.selectByPrimaryKey(taskId);
			ActReModelExample example = new ActReModelExample();
			example.or().andKeyEqualTo((task.getProcDefId()+"").split(":")[0]);
			ActReModel model = actReModelMapper.selectByExample(example).get(0);
			ActNodePropertiesExpandExample example2 = new ActNodePropertiesExpandExample();
			example2.or().andActNodeKeyEqualTo(task.getTaskDefKey()+"").andActModelIdEqualTo(model.getId()+"");
			ActNodePropertiesExpand node = actNodePropertiesExpandMapper.selectByExample(example2).get(0);
			List<ActAndChecked> checkeds = getCheckedList(model.getId()+"",node.getActNodeKey(),0);
			return checkeds;
		} else {
			return null;
		}
		
	}

	private List<ActAndChecked> getCheckedList(String modelId,String actNodeKey,int num) {
		List<SysCheckedAndNodeRelation> checkeds = new ArrayList<SysCheckedAndNodeRelation>();//ActAndChecked
		List<ActAndChecked> Acheckeds = new ArrayList<ActAndChecked>();
		ModelNodeSeqExample example = new ModelNodeSeqExample();
		example.or().andModelProDefEqualTo(modelId).andModelNodeEqualTo(actNodeKey);
		ActAndChecked aChecked = new ActAndChecked();
		if (actNodeKey.equals("")) {
			return Acheckeds;
		} else {
			SysCheckedAndNodeRelationExample example2 = new SysCheckedAndNodeRelationExample();
			example2.or().andActNodeIdEqualTo(modelId+"_"+actNodeKey);
			ModelNodeSeq modelSeq = mSeqMapper.selectByExample(example).get(0);
			if (num!=0&&checkedAndNodeRelationMapper.selectByExample(example2).size()>0) {
				aChecked.setObjs(checkedAndNodeRelationMapper.selectByExample(example2));
				aChecked.setNodeName(modelSeq.getModelName());
				aChecked.setNodeKey(modelSeq.getModelNode());
				Acheckeds.add(aChecked);
			}
			num++;
			Acheckeds.addAll(getCheckedList(modelId,modelSeq.getModelPreNode(),num));
		}
		return Acheckeds;
	}

	/**
	 * 修改检查点审核结果
	 * @param fileId
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/updateCheckPointResult")
	public @ResponseBody Object updateCheckPointResult(HttpServletRequest request,@RequestBody SysActCheckedpointResult result) throws Throwable {
		String[] recode = new String[2];
		recode[0]="0";
		recode[1]="操作成功";
		String userId = (String)(request.getSession().getAttribute("userid")+"");
		try {
			if (result.getId()!=null&&!result.getId().equals("")) {
				SysActCheckedpointResult result2 = actCheckedpointResultMapper.selectByPrimaryKey(result.getId());
				result2.setOperatorTime(new Date());
				result2.setOperatorName(userId);
				result2.setCheckResult(result.getCheckResult());
				actCheckedpointResultMapper.updateByPrimaryKey(result);
			} else {
				result.setOperatorTime(new Date());
				result.setOperatorName(userId);
				actCheckedpointResultMapper.insert(result);
			}
			return recode;
		} catch (Exception e) {
			logger.info(e+"");
			recode[0]="1";
			recode[1]="操作异常";
			return recode;
		}
	}
	
	@RequestMapping("/getCheckedPointById")
	public @ResponseBody Object getCheckedPointById(HttpServletRequest request) throws Exception{
		String userId = request.getSession().getAttribute("userid")+"";
		String checkedId =request.getParameter("checkedId");
		SysFileCheckedPointExample example = new SysFileCheckedPointExample();
		example.or().andCheckedIdEqualTo(new BigDecimal(checkedId));
		List<SysFileCheckedPoint> points = sFileCheckedPointMapper.selectByExample(example);
		return points;
	}
	//本人上传文件
	@RequestMapping("/getFileByObjId")
	public @ResponseBody Object getFileByObjId(HttpServletRequest request) throws Exception{
		String userId = request.getSession().getAttribute("userid")+"";
		String objType =request.getParameter("objType");
		SysFileuploadExample example = new SysFileuploadExample();
		example.or().andObjIdEqualTo(userId).andObjTypeEqualTo(objType);
		List<SysFileupload> files = fMapper.selectByExample(example);
		return files;
	}
	/*//非历史上传文件
	@RequestMapping("/getFileByObjId")
	public @ResponseBody Object getFileByObjId(HttpServletRequest request) throws Exception{
		String objType =request.getParameter("objType");
		String objId =request.getParameter("objId");
		SysFileuploadExample example = new SysFileuploadExample();
		example.or().andObjIdEqualTo(objId).andObjTypeEqualTo(objType).andFileTagNotLike("HISTORY");
		List<SysFileupload> files = fMapper.selectByExample(example);
		return files;
	}*/
	//非本人上传文件
	@RequestMapping("/getFileByObjIdHis")
	public @ResponseBody Object getFileByObjIdHis(HttpServletRequest request) throws Exception{
		HttpSession session = request.getSession();
		String userId = request.getSession().getAttribute("userId")+"";
		
		logger.info(userId+"_"+session.getAttribute("userid"));
		String objType =request.getParameter("objType");
		SysFileuploadExample example = new SysFileuploadExample();
		example.or().andObjIdNotEqualTo(userId).andObjTypeEqualTo(objType);
		List<SysFileupload> files = fMapper.selectByExample(example);
		return files;
	}
	/*//历史上传文件
	@RequestMapping("/getFileByObjIdHis")
	public @ResponseBody Object getFileByObjIdHis(HttpServletRequest request) throws Exception{
		String objType =request.getParameter("objType");
		String objId =request.getParameter("objId");
		SysFileuploadExample example = new SysFileuploadExample();
		example.or().andObjIdEqualTo(objId).andObjTypeEqualTo(objType).andFileTagLike("HISTORY");
		List<SysFileupload> files = fMapper.selectByExample(example);
		return files;
	}*/
	
	/*@RequestMapping("/setFileByObjIdHis")
	public void setFileByObjIdHis(HttpServletRequest request) throws Exception{
		String userId = request.getSession().getAttribute("userid")+"";
		String objType =request.getParameter("objType");
		String objId =request.getParameter("objId");
		SysFileuploadExample example = new SysFileuploadExample();
		example.or().andObjIdEqualTo(objId).andObjTypeEqualTo(objType).andFileTagNotLike("HISTORY");
		List<SysFileupload> files = fMapper.selectByExample(example);
		for (SysFileupload sysFileupload : files) {
			if (sysFileupload.getFileTag()!=null&&!sysFileupload.getFileTag().equals("")) {
				sysFileupload.setFileTag(sysFileupload.getFileTag()+",HISTORY");
			} else {
				sysFileupload.setFileTag("HISTORY");
			}
			fMapper.updateByPrimaryKey(sysFileupload);
		}
	}*/
	
	/**
	 * 修改检查点审核状态
	 * @param fileId
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/updateIsAchieve")
	public @ResponseBody Object updateIsAchieve(HttpServletRequest request,@RequestBody SysFileCheckedPoint point) throws Throwable {
		String[] recode = new String[2];
		recode[0]="0";
		recode[1]="操作成功";
		SysFileCheckedPoint point2 = sFileCheckedPointMapper.selectByPrimaryKey(point.getId());
		String userId = (String)(request.getSession().getAttribute("userid")+"");
		point2.setOperatorTime(new Date());
		point2.setOperatorName(userId);
		point2.setIsAchieve(point.getIsAchieve());
		try {
			sFileCheckedPointMapper.updateByPrimaryKey(point2);
			return recode;
		} catch (Exception e) {
			// TODO: handle exception
			recode[0]="1";
			recode[1]="操作异常";
			return recode;
		}
	}
}