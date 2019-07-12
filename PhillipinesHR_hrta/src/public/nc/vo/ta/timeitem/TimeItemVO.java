/***************************************************************\
 *     The skeleton of this class is generated by an automatic *
 * code generator for NC product. It is based on Velocity.     *
\***************************************************************/
package nc.vo.ta.timeitem;
	
import nc.hr.utils.MultiLangHelper;
import nc.vo.pub.SuperVO;
import nc.vo.pub.lang.UFBoolean;
import nc.vo.ta.basedoc.IBasedocDefVO;
import nc.vo.ta.annotation.IDColumn;
import nc.vo.ta.annotation.Table;

/**
 * <b> 在此处简要描述此类的功能 </b>
 * <p>
 *     在此处添加此类的描述信息
 * </p>
 * 创建日期:2010-09-14 20:33:54
 * @author 
 * @version NCPrj ??
 */
@SuppressWarnings("serial")

@Table(tableName="tbm_timeitem")
@IDColumn(idColumn="pk_timeitem")
public abstract class TimeItemVO extends SuperVO implements IBasedocDefVO{
	
	public static final int LEAVE_TYPE=0;
	public static final int OVERTIME_TYPE=1;
	public static final int AWAY_TYPE=2;
	public static final int SHUTDOWN_TYPE=3;
	
	protected java.lang.String pk_timeitem;
	private java.lang.String pk_group;
	private java.lang.String pk_org;
	private Integer enablestate;
	private java.lang.Integer itemtype;
	private java.lang.String timeitemcode;
	private java.lang.String timeitemname;
	private java.lang.String timeitemname2;
	private java.lang.String timeitemname3;
	private java.lang.String timeitemname4;
	private java.lang.String timeitemname5;
	private java.lang.String timeitemname6;
	private nc.vo.pub.lang.UFBoolean ispredef;
	private nc.vo.pub.lang.UFBoolean islactation;
	private java.lang.String creator;
	private nc.vo.pub.lang.UFDateTime creationtime;
	private java.lang.String modifier;
	private nc.vo.pub.lang.UFDateTime modifiedtime;
	private java.lang.Integer dr = 0;
	private nc.vo.pub.lang.UFDateTime ts;
	private UFBoolean isleaveapptimelimit;
	private Integer leaveapptimelimit;

	public static final String PK_TIMEITEM = "pk_timeitem";
	public static final String PK_GROUP = "pk_group";
	public static final String PK_ORG = "pk_org";
	public static final String ENABLESTATE = "enablestate";
	public static final String ITEMTYPE = "itemtype";
	public static final String TIMEITEMCODE = "timeitemcode";
	public static final String TIMEITEMNAME = "timeitemname";
	public static final String TIMEITEMNAME2 = "timeitemname2";
	public static final String TIMEITEMNAME3 = "timeitemname3";
	public static final String TIMEITEMNAME4 = "timeitemname4";
	public static final String TIMEITEMNAME5 = "timeitemname5";
	public static final String TIMEITEMNAME6 = "timeitemname6";
	public static final String ISPREDEF = "ispredef";
	public static final String CREATOR = "creator";
	public static final String CREATIONTIME = "creationtime";
	public static final String MODIFIER = "modifier";
	public static final String MODIFIEDTIME = "modifiedtime";
	public static final String ISLACTATION = "islactation";
	public static final String ISLEAVEAPPTIMELIMIT = "isleaveapptimelimit";
	public static final String LEAVEAPPTIMELIMIT = "leaveapptimelimit";
			
	/**
	 * 属性pk_timeitem的Getter方法.
	 * 创建日期:2010-09-14 20:33:54
	 * @return java.lang.String
	 */
	public java.lang.String getPk_timeitem () {
		return pk_timeitem;
	}   
	/**
	 * 属性pk_timeitem的Setter方法.
	 * 创建日期:2010-09-14 20:33:54
	 * @param newPk_timeitem java.lang.String
	 */
	public void setPk_timeitem (java.lang.String newPk_timeitem ) {
	 	this.pk_timeitem = newPk_timeitem;
	} 	  
	/**
	 * 属性pk_group的Getter方法.
	 * 创建日期:2010-09-14 20:33:54
	 * @return java.lang.String
	 */
	public java.lang.String getPk_group () {
		return pk_group;
	}   
	/**
	 * 属性pk_group的Setter方法.
	 * 创建日期:2010-09-14 20:33:54
	 * @param newPk_group java.lang.String
	 */
	public void setPk_group (java.lang.String newPk_group ) {
	 	this.pk_group = newPk_group;
	} 	  
	/**
	 * 属性pk_org的Getter方法.
	 * 创建日期:2010-09-14 20:33:54
	 * @return java.lang.String
	 */
	public java.lang.String getPk_org () {
		return pk_org;
	}   
	/**
	 * 属性pk_org的Setter方法.
	 * 创建日期:2010-09-14 20:33:54
	 * @param newPk_org java.lang.String
	 */
	public void setPk_org (java.lang.String newPk_org ) {
	 	this.pk_org = newPk_org;
	} 	  
	/**
	 * 属性itemtype的Getter方法.
	 * 创建日期:2010-09-14 20:33:54
	 * @return java.lang.Integer
	 */
	public java.lang.Integer getItemtype () {
		return itemtype;
	}   
	/**
	 * 属性itemtype的Setter方法.
	 * 创建日期:2010-09-14 20:33:54
	 * @param newItemtype java.lang.Integer
	 */
	public void setItemtype (java.lang.Integer newItemtype ) {
	 	this.itemtype = newItemtype;
	} 	  
	/**
	 * 属性timeitemcode的Getter方法.
	 * 创建日期:2010-09-14 20:33:54
	 * @return java.lang.String
	 */
	public java.lang.String getTimeitemcode () {
		return timeitemcode;
	}   
	/**
	 * 属性timeitemcode的Setter方法.
	 * 创建日期:2010-09-14 20:33:54
	 * @param newTimeitemcode java.lang.String
	 */
	public void setTimeitemcode (java.lang.String newTimeitemcode ) {
	 	this.timeitemcode = newTimeitemcode;
	} 	  
	/**
	 * 属性timeitemname的Getter方法.
	 * 创建日期:2010-09-14 20:33:54
	 * @return java.lang.String
	 */
	public java.lang.String getTimeitemname () {
		return timeitemname;
	}   
	/**
	 * 属性timeitemname的Setter方法.
	 * 创建日期:2010-09-14 20:33:54
	 * @param newTimeitemname java.lang.String
	 */
	public void setTimeitemname (java.lang.String newTimeitemname ) {
	 	this.timeitemname = newTimeitemname;
	} 	  
	/**
	 * 属性timeitemname2的Getter方法.
	 * 创建日期:2010-09-14 20:33:54
	 * @return java.lang.String
	 */
	public java.lang.String getTimeitemname2 () {
		return timeitemname2;
	}   
	/**
	 * 属性timeitemname2的Setter方法.
	 * 创建日期:2010-09-14 20:33:54
	 * @param newTimeitemname2 java.lang.String
	 */
	public void setTimeitemname2 (java.lang.String newTimeitemname2 ) {
	 	this.timeitemname2 = newTimeitemname2;
	} 	  
	/**
	 * 属性timeitemname3的Getter方法.
	 * 创建日期:2010-09-14 20:33:54
	 * @return java.lang.String
	 */
	public java.lang.String getTimeitemname3 () {
		return timeitemname3;
	}   
	/**
	 * 属性timeitemname3的Setter方法.
	 * 创建日期:2010-09-14 20:33:54
	 * @param newTimeitemname3 java.lang.String
	 */
	public void setTimeitemname3 (java.lang.String newTimeitemname3 ) {
	 	this.timeitemname3 = newTimeitemname3;
	} 	  
	/**
	 * 属性ispredef的Getter方法.
	 * 创建日期:2010-09-14 20:33:54
	 * @return nc.vo.pub.lang.UFBoolean
	 */
	public nc.vo.pub.lang.UFBoolean getIspredef () {
		return ispredef;
	}   
	/**
	 * 属性ispredef的Setter方法.
	 * 创建日期:2010-09-14 20:33:54
	 * @param newIspredef nc.vo.pub.lang.UFBoolean
	 */
	public void setIspredef (nc.vo.pub.lang.UFBoolean newIspredef ) {
	 	this.ispredef = newIspredef;
	} 	  
	/**
	 * 属性creator的Getter方法.
	 * 创建日期:2010-09-14 20:33:54
	 * @return java.lang.String
	 */
	public java.lang.String getCreator () {
		return creator;
	}   
	/**
	 * 属性creator的Setter方法.
	 * 创建日期:2010-09-14 20:33:54
	 * @param newCreator java.lang.String
	 */
	public void setCreator (java.lang.String newCreator ) {
	 	this.creator = newCreator;
	} 	  
	/**
	 * 属性creationtime的Getter方法.
	 * 创建日期:2010-09-14 20:33:54
	 * @return nc.vo.pub.lang.UFDateTime
	 */
	public nc.vo.pub.lang.UFDateTime getCreationtime () {
		return creationtime;
	}   
	/**
	 * 属性creationtime的Setter方法.
	 * 创建日期:2010-09-14 20:33:54
	 * @param newCreationtime nc.vo.pub.lang.UFDateTime
	 */
	public void setCreationtime (nc.vo.pub.lang.UFDateTime newCreationtime ) {
	 	this.creationtime = newCreationtime;
	} 	  
	/**
	 * 属性modifier的Getter方法.
	 * 创建日期:2010-09-14 20:33:54
	 * @return java.lang.String
	 */
	public java.lang.String getModifier () {
		return modifier;
	}   
	/**
	 * 属性modifier的Setter方法.
	 * 创建日期:2010-09-14 20:33:54
	 * @param newModifier java.lang.String
	 */
	public void setModifier (java.lang.String newModifier ) {
	 	this.modifier = newModifier;
	} 	  
	/**
	 * 属性modifiedtime的Getter方法.
	 * 创建日期:2010-09-14 20:33:54
	 * @return nc.vo.pub.lang.UFDateTime
	 */
	public nc.vo.pub.lang.UFDateTime getModifiedtime () {
		return modifiedtime;
	}   
	/**
	 * 属性modifiedtime的Setter方法.
	 * 创建日期:2010-09-14 20:33:54
	 * @param newModifiedtime nc.vo.pub.lang.UFDateTime
	 */
	public void setModifiedtime (nc.vo.pub.lang.UFDateTime newModifiedtime ) {
	 	this.modifiedtime = newModifiedtime;
	} 	  
	/**
	 * 属性islactation的Getter方法.
	 * 创建日期:2010-09-14 20:33:54
	 * @return nc.vo.pub.lang.UFBoolean
	 */
	public nc.vo.pub.lang.UFBoolean getIslactation () {
		return islactation;
	}   
	/**
	 * 属性islactation的Setter方法.
	 * 创建日期:2010-09-14 20:33:54
	 * @param newIslactation nc.vo.pub.lang.UFBoolean
	 */
	public void setIslactation (nc.vo.pub.lang.UFBoolean newIslactation ) {
	 	this.islactation = newIslactation;
	} 	  
	/**
	 * 属性dr的Getter方法.
	 * 创建日期:2010-09-14 20:33:54
	 * @return java.lang.Integer
	 */
	public java.lang.Integer getDr () {
		return dr;
	}   
	/**
	 * 属性dr的Setter方法.
	 * 创建日期:2010-09-14 20:33:54
	 * @param newDr java.lang.Integer
	 */
	public void setDr (java.lang.Integer newDr ) {
	 	this.dr = newDr;
	} 	  
	/**
	 * 属性ts的Getter方法.
	 * 创建日期:2010-09-14 20:33:54
	 * @return nc.vo.pub.lang.UFDateTime
	 */
	public nc.vo.pub.lang.UFDateTime getTs () {
		return ts;
	}   
	/**
	 * 属性ts的Setter方法.
	 * 创建日期:2010-09-14 20:33:54
	 * @param newTs nc.vo.pub.lang.UFDateTime
	 */
	public void setTs (nc.vo.pub.lang.UFDateTime newTs ) {
	 	this.ts = newTs;
	} 	  
 
	/**
	  * <p>取得父VO主键字段.
	  * <p>
	  * 创建日期:2010-09-14 20:33:54
	  * @return java.lang.String
	  */
	public java.lang.String getParentPKFieldName() {
	    return null;
	}   
    
	/**
	  * <p>取得表主键.
	  * <p>
	  * 创建日期:2010-09-14 20:33:54
	  * @return java.lang.String
	  */
	public java.lang.String getPKFieldName() {
	  return "pk_timeitem";
	}
    
	/**
	 * <p>返回表名称.
	 * <p>
	 * 创建日期:2010-09-14 20:33:54
	 * @return java.lang.String
	 */
	public java.lang.String getTableName() {
		return "tbm_timeitem";
	}    
	
	/**
	 * <p>返回表名称.
	 * <p>
	 * 创建日期:2010-09-14 20:33:54
	 * @return java.lang.String
	 */
	public static java.lang.String getDefaultTableName() {
		return "tbm_timeitem";
	}    
    
    /**
	  * 按照默认方式创建构造子.
	  *
	  * 创建日期:2010-09-14 20:33:54
	  */
     public TimeItemVO() {
		super();	
	}
	public UFBoolean getIsleaveapptimelimit() {
		return isleaveapptimelimit;
	}
	public void setIsleaveapptimelimit(UFBoolean isleaveapptimelimit) {
		this.isleaveapptimelimit = isleaveapptimelimit;
	}
	public Integer getLeaveapptimelimit() {
		return leaveapptimelimit;
	}
	public void setLeaveapptimelimit(Integer leaveapptimelimit) {
		this.leaveapptimelimit = leaveapptimelimit;
	}
	public Integer getEnablestate() {
		return enablestate;
	}
	public void setEnablestate(Integer enablestate) {
		this.enablestate = enablestate;
	}
	
	@Override
	public String toString() {
		return getMultilangName();
	}
	public java.lang.String getTimeitemname4() {
		return timeitemname4;
	}
	public void setTimeitemname4(java.lang.String timeitemname4) {
		this.timeitemname4 = timeitemname4;
	}
	public java.lang.String getTimeitemname5() {
		return timeitemname5;
	}
	public void setTimeitemname5(java.lang.String timeitemname5) {
		this.timeitemname5 = timeitemname5;
	}
	public java.lang.String getTimeitemname6() {
		return timeitemname6;
	}
	public void setTimeitemname6(java.lang.String timeitemname6) {
		this.timeitemname6 = timeitemname6;
	}

	public String getMultilangName(){
		return MultiLangHelper.getName(this);
	}
} 


