package com.hesc.urp.exclusiveGateWay;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class EventConstants {
	//通用状态
	public static final String EVENTSTATE_DISCARD = "DISCARD"; // 废弃
	public static final String EVENTSTATE_CITY_YJA = "END"; // 结案,归档
	public static final String EVENTSTATE_CITY_YCL = "YCL"; // 预处理
	public static final String EVENTSTATE_CITY_RECORD = "RECORD"; // 预处理
	//新流程状态
	public static final String EVENTSTATE_QZXLA = "QZX2"; // 区中心立案
	public static final String EVENTSTATE_QZXJB = "QZX6"; // 区中心交办
	public static final String EVENTSTATE_QZXBJ = "QZX8"; // 区中心办结
	public static final String EVENTSTATE_QZXPQ = "QZX10"; // 区中心派遣
	public static final String EVENTSTATE_QZXZF = "QZX12"; // 区中心作废
	
	public static final String EVENTSTATE_JDLA = "JD2"; // 街道立案
	public static final String EVENTSTATE_JDSB = "JD4"; // 街道上报
	public static final String EVENTSTATE_JDJB = "JD6"; // 街道交办
	public static final String EVENTSTATE_JDFK = "JD8"; // 街道反馈
	public static final String EVENTSTATE_JDBJ = "JD10";// 街道办结
	public static final String EVENTSTATE_JDPQ = "JD12";// 街道派遣
	public static final String EVENTSTATE_JDZF = "JD14";// 街道作废
	
	public static final String EVENTSTATE_SHLA = "SH2"; // 社区立案	
	public static final String EVENTSTATE_SHSB = "SH4"; // 社区上报	
	public static final String EVENTSTATE_SHJB = "SH6"; // 社区交办	
	public static final String EVENTSTATE_SHFK = "SH8"; // 社区反馈	
	public static final String EVENTSTATE_SHBJ = "SH10";// 社区办结	
	public static final String EVENTSTATE_SHZF = "SH12";// 社区作废
	
	public static final String EVENTSTATE_WGLA = "WG2"; // 网格立案
	public static final String EVENTSTATE_WGSB = "WG4"; // 网格上报
	public static final String EVENTSTATE_WGFK = "WG6"; // 网格反馈
	public static final String EVENTSTATE_WGBJ = "WG8"; // 网格办结
	public static final String EVENTSTATE_WGZF = "WG10"; // 网格作废
	
	//区专业部门
	public static final String EVENTSTATE_QZYBMLA = "QZYBM2"; // 区专业部门立案
	public static final String EVENTSTATE_QZYBMSB = "QZYBM4"; // 区专业部门上报
	public static final String EVENTSTATE_QZYBMPQ = "QZYBM6"; // 区专业部门派遣
	public static final String EVENTSTATE_QZYBMFK = "QZYBM8"; // 区专业部门反馈
	
	public static final String EVENTSTATE_QZYBMZF = "QZYBM10"; //区二级专业部门作废
	public static final String EVENTSTATE_QEJZYBMFK = "QEJZYBM8"; // 区二级专业部门反馈
	
	//街道专业部门
	public static final String EVENTSTATE_JDZYBMLA = "JDZYBM2"; // 街道专业部门立案
	public static final String EVENTSTATE_JDZYBMSB = "JDZYBM4"; // 街道专业部门上报
	public static final String EVENTSTATE_JDZYBMPQ = "JDZYBM6"; // 街道专业部门派遣
	public static final String EVENTSTATE_JDZYBMFK = "JDZYBM8"; // 街道专业部门反馈
	public static final String EVENTSTATE_JDEJZYBMFK = "JDEJZYBM8"; // 街道二级专业部门反馈
	
	//上报人确认
	public static final String EVENTSTATE_SBRQRK = "SBRQR8"; // 上报人确认
	public static final String EVENTSTATE_HTQZX ="SBRQR2"; //   回退到区中心
	public static final String EVENTSTATE_HTJD = "SBRQR4"; //  回退到街道
	public static final String EVENTSTATE_HTSH = "SBRQR6";//   回退到社区
	
	
	//状态对应的key-value
	public static final TreeMap<String, String> EVENTSTATEMAP = new TreeMap<String, String>();
	static{
	    EVENTSTATEMAP.put(EVENTSTATE_DISCARD, "作废");
	    EVENTSTATEMAP.put(EVENTSTATE_CITY_YJA, "已结案");
	    EVENTSTATEMAP.put(EVENTSTATE_CITY_YCL, "预处理");
	    EVENTSTATEMAP.put(EVENTSTATE_CITY_RECORD, "归档");
	    
	    EVENTSTATEMAP.put(EVENTSTATE_QZXLA, "中心立案");
	    EVENTSTATEMAP.put(EVENTSTATE_QZXJB, "中心交办");
	    EVENTSTATEMAP.put(EVENTSTATE_QZXBJ, "中心办结");
	    EVENTSTATEMAP.put(EVENTSTATE_QZXPQ, "中心交办");
	    EVENTSTATEMAP.put(EVENTSTATE_QZXZF, "中心作废");
	    
	    EVENTSTATEMAP.put(EVENTSTATE_JDLA, "街道立案");
	    EVENTSTATEMAP.put(EVENTSTATE_JDSB, "街道上报");
	    EVENTSTATEMAP.put(EVENTSTATE_JDJB, "街道交办");
	    EVENTSTATEMAP.put(EVENTSTATE_JDFK, "街道反馈");
	    EVENTSTATEMAP.put(EVENTSTATE_JDBJ, "街道办结");
	    EVENTSTATEMAP.put(EVENTSTATE_JDPQ, "街道派遣");
	    EVENTSTATEMAP.put(EVENTSTATE_JDZF, "街道作废");
	    
	    
	    EVENTSTATEMAP.put(EVENTSTATE_SHLA, "社区立案");
	    EVENTSTATEMAP.put(EVENTSTATE_SHSB, "社区上报");
	    EVENTSTATEMAP.put(EVENTSTATE_SHJB, "社区交办");
	    EVENTSTATEMAP.put(EVENTSTATE_SHFK, "社区反馈");
	    EVENTSTATEMAP.put(EVENTSTATE_SHBJ, "社区办结");
	    EVENTSTATEMAP.put(EVENTSTATE_SHZF, "社区作废");
	    
	    
	    EVENTSTATEMAP.put(EVENTSTATE_WGLA, "网格立案");
	    EVENTSTATEMAP.put(EVENTSTATE_WGSB, "网格上报");
	    EVENTSTATEMAP.put(EVENTSTATE_WGFK, "网格反馈");
	    EVENTSTATEMAP.put(EVENTSTATE_WGBJ, "网格办结");	
	    EVENTSTATEMAP.put(EVENTSTATE_WGZF, "网格作废");	
	    
	    EVENTSTATEMAP.put(EVENTSTATE_QZYBMLA, "专业部门立案");
	    EVENTSTATEMAP.put(EVENTSTATE_QZYBMSB, "专业部门上报");
	    EVENTSTATEMAP.put(EVENTSTATE_QZYBMFK, "专业部门反馈");   
	    EVENTSTATEMAP.put(EVENTSTATE_QZYBMPQ, "专业部门交办");
	    EVENTSTATEMAP.put(EVENTSTATE_QZYBMZF, "专业部门作废");
	    
	    EVENTSTATEMAP.put(EVENTSTATE_QEJZYBMFK, "二级专业部门反馈");
	    
	    EVENTSTATEMAP.put(EVENTSTATE_JDZYBMLA, "街道专业部门立案");
	    EVENTSTATEMAP.put(EVENTSTATE_JDZYBMSB, "街道专业部门上报");
	    EVENTSTATEMAP.put(EVENTSTATE_JDZYBMPQ, "街道专业部门交办");
	    EVENTSTATEMAP.put(EVENTSTATE_JDZYBMFK, "街道专业部门反馈");
	    
	    EVENTSTATEMAP.put(EVENTSTATE_JDEJZYBMFK, "街道二级专业部门反馈");
	    
	    EVENTSTATEMAP.put(EVENTSTATE_HTQZX, "回退到中心");
	    EVENTSTATEMAP.put(EVENTSTATE_HTJD,  "回退到街道");
	    EVENTSTATEMAP.put(EVENTSTATE_HTSH,  "回退到社区");
	    EVENTSTATEMAP.put(EVENTSTATE_SBRQRK,  "上报人确认");
	}
	/*----------------------菜单查询状态值-------------------------------*/
	public static final String Query_EVENT_YCL = "YCL";//待派遣
	// 待派遣-》查询立案数据
	public static final String Query_EVENT_DPQ = "DPQ";//待派遣
	public static final List<String>  dpqList= new ArrayList<String>(); 
	static{
		dpqList.add(EVENTSTATE_QZXLA);
		dpqList.add(EVENTSTATE_JDLA);
		dpqList.add(EVENTSTATE_SHLA);
		dpqList.add(EVENTSTATE_WGLA);
		dpqList.add(EVENTSTATE_QZYBMLA);
		dpqList.add(EVENTSTATE_JDZYBMLA);
	}
	// 待处理-》查询不是所有立案数据
	public static final String Query_EVENT_DCL = "DCL";//待处理
	
	
   //问题来源  key-value
	public static final TreeMap<String, String> EVENTSRCMAP = new TreeMap<String, String>();  
	public static final String EVENTSRC_TYPE_RXJB = "01";//热线举报
	public static final String EVENTSRC_TYPE_JDYSB = "02";//监督员上报
	public static final String EVENTSRC_TYPE_LDJB = "03";//领导交办
	public static final String EVENTSRC_TYPE_SPJK= "04"; //视频监控
	public static final String EVENTSRC_TYPE_SMT = "06";//文明创建随手拍
	public static final String EVENTSRC_TYPE_TXWX = "07";//微信
	public static final String EVENTSRC_TYPE_ZHYY = "08";//智慧应用
	public static final String EVENTSRC_TYPE_HYJC = "09";//物联监测
	public static final String EVENTSRC_TYPE_ZXFWRX = "10";//咨询服务热线
	public static final String EVENTSRC_TYPE_ZHCG = "11";//智慧城管
	public static final String EVENTSRC_TYPE_ZHZF = "12";//智慧执法
	public static final String EVENTSRC_TYPE_OTHERS = "99";//其他
	public static final String EVENTSRC_TYPE_BTZNFX = "13";//贝特智能分析
	public static final String EVENTSRC_TYPE_DH = "14";//大华视频
	
	static{ 
	    EVENTSRCMAP.put(EVENTSRC_TYPE_DH, "大华视频监控");
	    EVENTSRCMAP.put(EVENTSRC_TYPE_BTZNFX, "贝特智能分析");
	    EVENTSRCMAP.put(EVENTSRC_TYPE_RXJB, "热线举报");
	    EVENTSRCMAP.put(EVENTSRC_TYPE_JDYSB, "网格员上报");
	    EVENTSRCMAP.put(EVENTSRC_TYPE_LDJB, "领导交办");
	    EVENTSRCMAP.put(EVENTSRC_TYPE_SPJK, "视频监控");
	    EVENTSRCMAP.put(EVENTSRC_TYPE_SMT, "文明创建随手拍");
	    EVENTSRCMAP.put(EVENTSRC_TYPE_TXWX, "微信");
	    EVENTSRCMAP.put(EVENTSRC_TYPE_ZHYY, "智慧应用");
/*	    EVENTSRCMAP.put(EVENTSRC_TYPE_HYJC, "物联监测");*/
	    EVENTSRCMAP.put(EVENTSRC_TYPE_ZXFWRX, "咨询服务热线");	 
	    EVENTSRCMAP.put(EVENTSRC_TYPE_ZHCG, "智慧城管");	
	    EVENTSRCMAP.put(EVENTSRC_TYPE_ZHZF, "智慧执法");	
	    EVENTSRCMAP.put(EVENTSRC_TYPE_OTHERS, "其他");
	}
	
	//问题类型 key-value
	public static final Map<String, String> WTTYPEMAP = new  TreeMap<String, String>();
	public static final String WTTYPE_TYPE_TS = "01";//投诉
	public static final String WTTYPE_TYPE_ZX = "02";//咨询
	public static final String WTTYPE_TYPE_JY = "03";//建议
	public static final String WTTYPE_TYPE_QZ = "04";//求助
	public static final String WTTYPE_TYPE_CJ = "05";//采集
	public static final String WTTYPE_TYPE_JB = "06";//交办
	public static final String WTTYPE_TYPE_QT = "07";//其他
	
	static{ 
	    WTTYPEMAP.put(WTTYPE_TYPE_TS, "投诉");
	    WTTYPEMAP.put(WTTYPE_TYPE_ZX, "咨询");
	    WTTYPEMAP.put(WTTYPE_TYPE_JY, "建议");
	    WTTYPEMAP.put(WTTYPE_TYPE_QZ, "求助");
	    WTTYPEMAP.put(WTTYPE_TYPE_CJ, "采集");
	    WTTYPEMAP.put(WTTYPE_TYPE_JB, "交办");
	    WTTYPEMAP.put(WTTYPE_TYPE_QT, "其他");
	}
	
	//附件类型：image、video、voice
	public final static String TYPE_IMAGE = "image";
	public final static String TYPE_VIDEO = "video";
	public final static String TYPE_VOICE = "voice";
	
	//日志阶段
	public final static String SECTIONLOG_ACCEPTED  = "1";//受理
	public final static String SECTIONLOG_DISPATCH  = "2";//派遣
	public final static String SECTIONLOG_DEPT  = "3";//专业部门
	public final static String SECTIONLOG_REVIEW  = "4";//审核
	public final static String SECTIONLOG_GRID  = "5";//网格
	public final static String SECTIONLOG_COMMUNITY  = "6";//社区
	public final static String SECTIONLOG_STREET  = "7";//街道
	public final static String SECTIONLOG_FUNCTIONAL  = "8";//街道职能部门
	public final static String SECTIONLOG_COMMANDER  = "9";//区指挥长授权
	
	public static TreeMap<String, String> SECTIONLOGMAP = new TreeMap<String,String>();
	static{
		SECTIONLOGMAP.put(SECTIONLOG_ACCEPTED, "区中心");
		SECTIONLOGMAP.put(SECTIONLOG_DISPATCH, "派遣");
		SECTIONLOGMAP.put(SECTIONLOG_DEPT, "区职能");
		SECTIONLOGMAP.put(SECTIONLOG_REVIEW, "审核");
		SECTIONLOGMAP.put(SECTIONLOG_GRID, "网格员");
		SECTIONLOGMAP.put(SECTIONLOG_COMMUNITY, "社区");
		SECTIONLOGMAP.put(SECTIONLOG_STREET, "街道中心");
		SECTIONLOGMAP.put(SECTIONLOG_FUNCTIONAL, "街道科室");
		SECTIONLOGMAP.put(SECTIONLOG_COMMANDER, "区指挥长");
	}
}
