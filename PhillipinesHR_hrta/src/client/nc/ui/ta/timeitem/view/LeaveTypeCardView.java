package nc.ui.ta.timeitem.view;

import nc.hr.utils.CommonUtils;
import nc.vo.bd.pub.NODE_TYPE;
import nc.vo.pub.BusinessException;
import nc.vo.pub.lang.UFBoolean;
import nc.vo.pub.lang.UFDouble;
import nc.vo.ta.basedoc.RefDefVOWrapper;
import nc.vo.ta.timeitem.LeaveTypeCopyVO;
import nc.vo.ta.timeitem.TimeItemCopyVO;
import nc.vo.ta.timeitem.TimeItemVO;

import org.apache.commons.lang.StringUtils;

/**
 * 休假类别 cardView
 * @author yucheng
 *
 */
@SuppressWarnings("serial")
public class LeaveTypeCardView extends TimeItemCardView {

	private LeaveTypeCardPanel cardPanel;

	@Override
	public TimeItemCopyVO[] onRef(RefDefVOWrapper<TimeItemVO> objs) throws BusinessException {
		return getModel().refLeaveTypes(getModel().getContext(), objs);
	}

	@Override
	public void onSynchronize() throws BusinessException {
		getModel().synchronizeLeaveTypes(getModel().getContext());
	}
	
	@Override
	public RefDefVOWrapper<TimeItemVO> queryRefDefVOs() throws BusinessException {
		return getModel().queryLeaveRefDefVOs(getModel().getContext());
	}
	
	@Override
	public void checkBeforeEdit(Object obj){
		super.checkBeforeEdit(obj);
		if(!(obj instanceof LeaveTypeCopyVO))
			return;
		LeaveTypeCopyVO vo = (LeaveTypeCopyVO)obj;
		//判断是否哺乳假
		if(vo.getIslactation().booleanValue())
			getCardPanel().getTimeitemunit().setEnabled(false);
		processBeforeInit(vo);
	}
	
	@Override
	public void setDefaultValue() {
		LeaveTypeCopyVO vo = new LeaveTypeCopyVO();
		vo = (LeaveTypeCopyVO)getPubDefaultValue(vo);
		setValue(vo);
		//享有是否在自助中显示默认显示
		vo.setIshrssshow(UFBoolean.TRUE);
		processBeforeInit(vo);
	}
	
	/**
	 * 在新增和修改时对部分组件进行可编辑性初始化
	 * @param vo
	 */
	private void processBeforeInit(LeaveTypeCopyVO vo){
		//是否控制休假申请日期
		getCardPanel().getLeaveapptimelimit().setEnabled(vo.getIsleaveapptimelimit().booleanValue());
		//是否控制休假时长
		getCardPanel().getIsrestrictlimit().setEnabled(vo.getIsLeavelimit().booleanValue());
		// 不控制休假时长也可以设置规则 2011-03-23
//		getCardPanel().getFormulaset().setEnabled(vo.getIsLeavelimit().booleanValue());
		// 是否控制有效时限
		getCardPanel().getLeaveextendcount().setEnabled(vo.getIsleave().booleanValue());

		//组织级的结余是否转移不可编辑
		if(getModel().getContext().getNodeType().equals(NODE_TYPE.ORG_NODE))
			getCardPanel().getIsLeaveTransfer().setEnabled(false);
	}

	/**
	 * 设置界面初始值
	 * 对特殊类别需进行界面部分组件可见性的设置
	 */
	@Override
	public void setValue(Object object) {
		super.setValue(object);
		if(!(object instanceof LeaveTypeCopyVO))
			return;
		LeaveTypeCopyVO vo = (LeaveTypeCopyVO)object;
		LeaveTypeCardPanel cardPanel = getCardPanel();
		cardPanel.getLeavesetperiod().setSelectedItem(vo.getLeavesetperiod());
		cardPanel.getLeavesettlement().setSelectedItem(vo.getLeavesettlement());
		cardPanel.getLeavescale().setSelectedItem(vo.getLeavescale());
		cardPanel.getTimeunit().setValue(vo.getTimeunit());
		cardPanel.getLeavemax().setValue(vo.getLeavemax());//转下期上限
		cardPanel.getGxcomtype().setSelectedItem(vo.getGxcomtype());
		cardPanel.getIsLeavelimit().setSelected(vo.getIsLeavelimit().booleanValue());
		cardPanel.getIsrestrictlimit().setSelected(vo.getIsRestrictlimit().booleanValue());
		cardPanel.getIsleaveapptimelimit().setSelected(vo.getIsleaveapptimelimit().booleanValue());
		cardPanel.getLeaveapptimelimit().setText(vo.getLeaveapptimelimit().toString());
		
		cardPanel.getShoworder().setText(vo.getShoworder()==null?null:vo.getShoworder().toString());//隐藏字段的赋值
		
//		cardPanel.getFormulastr().setText(CommonUtils.toStringObject(vo.getFormulastr()));//不能按当前语种翻译公式改为下面的方法
		cardPanel.setFormulaDesc(CommonUtils.toStringObject(vo.getFormulastr()));
		
		cardPanel.setFormula(CommonUtils.toStringObject(vo.getFormula()));
		cardPanel.getConvertrule().setSelectedItem(vo.getConvertrule());
		cardPanel.getIsleave().setSelected(vo.getIsleave().booleanValue());
		cardPanel.getLeaveextendcount().setText(vo.getLeaveextendcount()==null?null:vo.getLeaveextendcount().toString());
		cardPanel.setDependleavetypes(vo.getDependVOs());
		cardPanel.getIsLeaveTransfer().setSelected(vo.getIsleavetransfer()==null?false:vo.getIsleavetransfer().booleanValue());
		cardPanel.getIshrssshow().setSelected(vo.getIshrssshow() == null?false:vo.getIshrssshow().booleanValue());
		
		// 必须上传附件对话框
		cardPanel.getIsattachmust().setSelected(vo.getIsattachmust() == null?false:vo.getIsattachmust().booleanValue());
		
		//根据结算方式判断是否显示假期结算方式和是否控制年假有效期
//		cardPanel.getLeavePanel().setVisible(!(TimeItemCopyVO.LEAVESETPERIOD_MONTH==vo.getLeavesetperiod().intValue()));
		if(TimeItemCopyVO.LEAVESETPERIOD_MONTH==vo.getLeavesetperiod().intValue()){
			cardPanel.getLeavescale().setEnabled(false);
			cardPanel.getLeavescale().setSelectedItem(TimeItemCopyVO.LEAVESCALE_MONTH);
		}
		
		boolean isLactation = vo.getIslactation()!=null&&vo.getIslactation().booleanValue();
		boolean isOvertorest = vo.getTimeitemcode() != null && vo.getTimeitemcode().equals(TimeItemCopyVO.OVERTIMETOLEAVETYPE);
		//哺乳假界面设置,结算周期，计算方式，最小时间单位，设为方式，公休日计休假，有效期延长时限，前置假，休假规则都不显示
		if(isLactation){
//			cardPanel.getSettlementPanel().setVisible(false);
//			cardPanel.getScalePanel().setVisible(false);
//			cardPanel.getDownPanel().setVisible(false);
//			cardPanel.getLeavePanel().setVisible(false);
			cardPanel.getDependSetPanel().setVisible(false);
			cardPanel.getFormulaSetPanel().setVisible(false);
			cardPanel.getControlPanel().setVisible(false);
//			cardPanel.getTimeitemunit().setSelectedItem(TimeItemCopyVO.TIMEITEMUNIT_HOUR);
		}
		else if(isOvertorest){//加班转调休界面设置，休假规则不显示（因为加班转调休的时长是从加班转来）
//			cardPanel.getSettlementPanel().setVisible(true);
//			cardPanel.getScalePanel().setVisible(vo.getLeavesetperiod().intValue()!=LeaveTypeCopyVO.LEAVESETPERIOD_MONTH);
//			cardPanel.getDownPanel().setVisible(true);
//			cardPanel.getLeavePanel().setVisible(true);
			cardPanel.getDependSetPanel().setVisible(true);
			cardPanel.getFormulaSetPanel().setVisible(false);
			cardPanel.getControlPanel().setVisible(true);
		}
		else{//正常的类别，都显示
//			cardPanel.getSettlementPanel().setVisible(true);
//			cardPanel.getScalePanel().setVisible(vo.getLeavesetperiod().intValue()!=LeaveTypeCopyVO.LEAVESETPERIOD_MONTH);
//			cardPanel.getDownPanel().setVisible(true);
//			cardPanel.getLeavePanel().setVisible(true);
			cardPanel.getDependSetPanel().setVisible(true);
			cardPanel.getFormulaSetPanel().setVisible(true);
			cardPanel.getControlPanel().setVisible(true);
		}
		
	}
	
	@Override
	public Object getValue() {
		LeaveTypeCopyVO vo = new LeaveTypeCopyVO();
		vo = (LeaveTypeCopyVO)getPubValue(vo);
		LeaveTypeCardPanel cardPanel = getCardPanel();
		cardPanel.stopEditing();
		vo.setLeavesetperiod((Integer)cardPanel.getLeavesetperiod().getSelectdItemValue());
		vo.setLeavesettlement((Integer)cardPanel.getLeavesettlement().getSelectdItemValue());
		vo.setLeavescale((Integer)cardPanel.getLeavescale().getSelectdItemValue());
		//数据格式--非小数显示格式的取出的字符串无法转换为double类型
		vo.setTimeunit(cardPanel.getTimeunit().getValue()==null ? UFDouble.ZERO_DBL:(UFDouble) cardPanel.getTimeunit().getValue());
		vo.setLeavemax(cardPanel.getLeavemax()==null ? null:(UFDouble) cardPanel.getLeavemax().getValue());
		vo.setGxcomtype((Integer)cardPanel.getGxcomtype().getSelectdItemValue());
		vo.setIsLeavelimit(UFBoolean.valueOf(cardPanel.getIsLeavelimit().isSelected()));
		vo.setIsRestrictlimit(UFBoolean.valueOf(cardPanel.getIsrestrictlimit().isSelected()));
		vo.setIsleaveapptimelimit(UFBoolean.valueOf(cardPanel.getIsleaveapptimelimit().isSelected()));
		String leaveApptimeLimit = cardPanel.getLeaveapptimelimit().getText();
		vo.setLeaveapptimelimit(StringUtils.isBlank(leaveApptimeLimit)?0:Integer.parseInt(leaveApptimeLimit));
		vo.setFormulastr(cardPanel.getFormulastr().getText());
		vo.setFormula(cardPanel.getFormula());
		vo.setConvertrule((Integer)cardPanel.getConvertrule().getSelectdItemValue());
		vo.setIsleave(UFBoolean.valueOf(cardPanel.getIsleave().isSelected()));
		vo.setPk_dependleavetypes(cardPanel.getDependleavetypes());
		String leaveExtendCount = cardPanel.getLeaveextendcount().getText();
		vo.setLeaveextendcount(StringUtils.isBlank(leaveExtendCount)?0:Integer.parseInt(leaveExtendCount));
		vo.setItemtype(TimeItemCopyVO.LEAVE_TYPE);
		vo.setIsleavetransfer(UFBoolean.valueOf(cardPanel.getIsLeaveTransfer().isSelected()));
		vo.setIshrssshow(UFBoolean.valueOf(cardPanel.getIshrssshow().isSelected()));
		
		// 添加对是否必须上传附件的VO控制
		vo.setIsattachmust(UFBoolean.valueOf(cardPanel.getIsattachmust().isSelected()));
		
		String order = cardPanel.getShoworder().getText();
		vo.setShoworder(StringUtils.isBlank(order)?null:Integer.parseInt(order));
		
		return vo;
	}

	public LeaveTypeCardPanel getCardPanel() {
		if(cardPanel==null)
			cardPanel = new LeaveTypeCardPanel();
		return cardPanel;
	}
	
	

	public void setCardPanel(LeaveTypeCardPanel cardPanel) {
		this.cardPanel = cardPanel;
	}
	
	@Override
	protected void processCompEnableStatusWhenUsed(){
		super.processCompEnableStatusWhenUsed();
		((LeaveTypeCardPanel)getCardPanel()).getLeavesetperiod().setEnabled(false);
	}
}
