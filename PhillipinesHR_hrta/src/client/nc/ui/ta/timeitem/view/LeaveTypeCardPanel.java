package nc.ui.ta.timeitem.view;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.SwingWorker;

import nc.bs.framework.common.NCLocator;
import nc.bs.logging.Logger;
import nc.hr.utils.ResHelper;
import nc.itf.ta.ITimeItemQueryMaintain;
import nc.ui.hr.formula.FormulaAppModelContainer;
import nc.ui.hr.formula.HRFormulaEditorDialog;
import nc.ui.pub.beans.UIButton;
import nc.ui.pub.beans.UICheckBox;
import nc.ui.pub.beans.UIComboBox;
import nc.ui.pub.beans.UIDialog;
import nc.ui.pub.beans.UILabel;
import nc.ui.pub.beans.UIPanel;
import nc.ui.pub.beans.UITextAreaScrollPane;
import nc.ui.pub.beans.UITextField;
import nc.ui.pub.beans.border.UITitledBorder;
import nc.ui.pub.beans.constenum.DefaultConstEnum;
import nc.ui.pub.beans.textfield.UITextType;
import nc.ui.pub.beans.util.ColumnLayout;
import nc.ui.pub.bill.BillListData;
import nc.ui.pub.bill.BillListPanel;
import nc.ui.pub.bill.BillModel;
import nc.ui.pub.bill.IBillItem;
import nc.ui.pub.tools.BannerDialog;
import nc.ui.ta.pub.standardpsntemplet.PsnTempletUtils;
import nc.ui.uif2.UIState;
import nc.vo.pub.BusinessException;
import nc.vo.pub.bill.BillTempletBodyVO;
import nc.vo.pub.bill.BillTempletVO;
import nc.vo.pub.lang.MultiLangText;
import nc.vo.pub.lang.UFDouble;
import nc.vo.ta.PublicLangRes;
import nc.vo.ta.basedoc.RefDefVOWrapper;
import nc.vo.ta.timeitem.TimeItemCopyVO;
import nc.vo.ta.timeitem.TimeItemVO;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

import com.jgoodies.forms.builder.DefaultFormBuilder;
import com.jgoodies.forms.layout.FormLayout;

/**
 * 休假类别 cardPanel
 * @author yucheng
 *
 */
@SuppressWarnings("serial")
public class LeaveTypeCardPanel extends TimeItemCardPanel{

	//结算周期
	private UIComboBox leavesetperiod;

	//结算方式
	private UIComboBox leavesettlement;

	//计算方式
	private UIComboBox leavescale;
	
	//转下期上限
	private UITextField leavemax;
	
	private UILabel timeUnitLabel;

	//最小时间单位
	private UITextField timeunit;
	
	//公休日计算方式
	private UIComboBox gxcomtype;

	//是否控制休假时长
	private UICheckBox isleavelimit;

	//是否严格控制
	private UICheckBox isrestrictlimit;

	//是否控制休假申请日期
	private UICheckBox isleaveapptimelimit;

	//休假申请时限限制天数
	private UITextField leaveapptimelimit;

	//是否启用年假有效期
	private UICheckBox isleave;
	//有效期延长时限
	private UITextField leaveextendcount;

	//休假规则设置
	private UIButton formulaset;
	// 休假前置规则设置
	private UIButton dependset;

	//休假规则
	private UITextAreaScrollPane formulastr;
	private String formula;

	//折算规则
	private UIComboBox convertrule;
	// 公式对话框
	private HRFormulaEditorDialog editorDialog;
	// 前置规则设置对话框
	private EditDependDialog editDependDialog;

	//年假有效期
//	private UIPanel leavePanel;

	// 前置规则列表
	private BillListPanel dependPanel;

	// 规则和前置规则列表panel
	private UIPanel ruleUnionPanel;

	private ITimeItemQueryMaintain queryMaintain;
	
	//基本信息Panel共有部分
	private UIPanel basicPanel;
	
	//数据和计算控制部分
	private UIPanel controlPanel;
	
	//结余是否可以跨组织转移
	private UICheckBox isleaveTransfer;

	//是否在自助服务中显示享有时长
	private UICheckBox ishrssshow;
	
	//附件上传是否必需
	private UICheckBox isattachmust;
	
	private UITextField showorder;
	
	public LeaveTypeCardPanel(){
		buildPanel();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		//点击是否控制休假申请日期
		if(e.getSource()==this.getIsleaveapptimelimit()){
			getLeaveapptimelimit().setEnabled(getIsleaveapptimelimit().isSelected());
			return;
		}
		//点击是否控制年假有效期
		if(e.getSource()==this.getIsleave()){
			getLeaveextendcount().setEnabled(getIsleave().isSelected());
			return;
		}
		//点击是否控制休假时长
		if(e.getSource()==this.getIsLeavelimit()){
			getIsrestrictlimit().setSelected(false);
			getIsrestrictlimit().setEnabled(getIsLeavelimit().isSelected());
			return;
		}
		//点击公式设置按钮
		if(e.getSource()==this.getFormulaset()){
			doFormulaSet();
			return;
		}
		// 点击前置规则设置按钮
		if(e.getSource()==this.getDependset()){
			doDependSet();
			return;
		}
	}

	@Override
	public void clearData() {
		super.clearData();
		setFormula(null);
		getFormulastr().setText(null);
		getDependPanel().getHeadBillModel().clearBodyData();
	}

	/**
	 * 打开公式设置dialog
	 */
	public void doFormulaSet(){
		final BannerDialog dialog = new BannerDialog(getModel().getContext().getEntranceUI());
		SwingWorker<Object, Object> worker = new SwingWorker<Object, Object>() {

			HRFormulaEditorDialog dlg = null;
			@Override
			protected void done() {
				dialog.end();
				dlg.showModal();
				if(dlg.getResult()==UIDialog.ID_OK){
					getFormulastr().setText(dlg.getBusinessLang());
					setFormula(dlg.getScriptLang());
				}
			}

			@Override
			protected Object doInBackground() throws Exception {
				try {
					dlg = getEditorDialog();
					dlg.setFormulaDesc(getFormulastr().getText());
				} catch (Exception e) {
					Logger.error(e.getMessage(),e);
					dialog.end();
					throw e;
				}
				return null;
			}

		};
		worker.execute();

		if (!worker.isDone()) {
			dialog.start();
		}
	}

	public HRFormulaEditorDialog getEditorDialog() {
		if(editorDialog==null){
			FormulaAppModelContainer.setModel(getModel());
			editorDialog = new HRFormulaEditorDialog(this,"nc/ui/ta/timeitem/leaverule_formula.xml");
		}
		return editorDialog;
	}

	private void doDependSet(){
		RefDefVOWrapper<TimeItemVO> wrapper = null;
		try {
			wrapper = getQueryMaintain().queryDependLeaveDefVOs(getModel().getContext(), getDependleavetypes(), getPk_timeitemcopy(), getLeavesetperiod().getSelectedIndex());
		} catch (BusinessException e) {
			Logger.error(e.getMessage());
		}
		getEditDependDialog().setLeftAndRightData(wrapper);
		getEditDependDialog().showModal();
	}

	public EditDependDialog getEditDependDialog() {
		if(editDependDialog==null){
			editDependDialog = new EditDependDialog(this);
			editDependDialog.setLeavePanel(this);
			editDependDialog.initUI();
		}
		return editDependDialog;
	}

	public ITimeItemQueryMaintain getQueryMaintain() {
		if(queryMaintain==null)
			queryMaintain = NCLocator.getInstance().lookup(ITimeItemQueryMaintain.class);
		return queryMaintain;
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		//修改计算单位
		if(e.getSource()==this.getTimeitemunit()){
			getTimeUnitLabel().setText(PublicLangRes.MINTIMEUNIT(getTimeitemunit().getSelectedIndex()));
			getTimeunit().setValue(UFDouble.ZERO_DBL);
			if(getTimeitemunit().getSelectedIndex()==TimeItemCopyVO.TIMEITEMUNIT_DAY){
				getTimeItemUnitLabel().setText(PublicLangRes.MINTIMEUNIT(getTimeitemunit().getSelectedIndex()));
				getTimeunit().setMaxValue(1);
//				getConvertRuleLabel().setVisible(true);
//				getConvertrule().setVisible(true);
				if(!(getModel().getUiState() == UIState.NOT_EDIT))
					getConvertrule().setEnabled(true);
				return;
			}
			getTimeItemUnitLabel().setText(PublicLangRes.MINTIMEUNIT(getTimeitemunit().getSelectedIndex()));
			getTimeunit().setMaxValue(1440);
//			getConvertRuleLabel().setVisible(false);
//			getConvertrule().setVisible(false);
			getConvertrule().setEnabled(false);
			getConvertrule().setSelectedItem(null);
			return;
		}
		//修改结算方式  控制假期计算方式是否显示
		if(e.getSource()==this.getLeavesetperiod()){
//			getScalePanel().setVisible(!(getLeavesetperiod().getSelectedIndex()==TimeItemCopyVO.LEAVESETPERIOD_MONTH));
//			getLeavePanel().setVisible(getLeavesetperiod().getSelectedIndex()==TimeItemCopyVO.LEAVESETPERIOD_YEAR);
			
			if(getLeavesetperiod().getSelectedIndex()==TimeItemCopyVO.LEAVESETPERIOD_MONTH){
				getLeavescale().setEnabled(false);
				getLeavescale().setSelectedItem(TimeItemCopyVO.LEAVESCALE_MONTH);
			}else{
				if(!(getModel().getUiState() == UIState.NOT_EDIT))
					getLeavescale().setEnabled(true);
			}
			return;
		}
	}

	/**
	 * 取休假类别panel
	 */
	@Override
	public UIPanel getUpPanel(){
		UIPanel upPanel = new UIPanel();
		upPanel.setLayout(new ColumnLayout());
		FormLayout layout = new FormLayout(
				"left:pref",
				"");
		DefaultFormBuilder builder = new DefaultFormBuilder(layout,upPanel);

		builder.append(getBasicPanel());
		builder.nextLine();
		builder.append(getControlPanel());
		builder.nextLine();
		builder.append(getNotePanel());
		builder.nextLine();
//		builder.append(getSettlementPanel());
//		builder.nextLine();
//		builder.append(getScalePanel());
//		builder.nextLine();
//		builder.append(getDownPanel());
//		builder.nextLine();
//		builder.append(getLeavePanel());
//		builder.nextLine();
		builder.append(getRuleUnionPanel());
		getShoworder();//只初始化一下
		return upPanel;
	}

	/**
	 * 顶部panel
	 * @return
	 */
	public UIPanel getBasicPanel(){
		if(basicPanel == null){
			basicPanel = new UIPanel();
			
			FormLayout layout = new FormLayout(colwidth,"");
			DefaultFormBuilder builder = new DefaultFormBuilder(layout,basicPanel);
			
			builder.append(PublicLangRes.CODE());
			builder.append(getTimeitemcode());
			
			builder.append(PublicLangRes.NAME());
			builder.append(getTimeitemname());
			
			builder.append(ResHelper.getString("common","UC001-0000118")/*@res "启用状态"*/);
			builder.append(getEnablestate());
			//计量单位
			builder.append(PublicLangRes.COMPUNIT());
			builder.append(getTimeitemunit());
			
			//转下期上限;
			builder.append(ResHelper.getString("6017basedoc","06017basedoc1938")/*@res "转下期上限"*/);
			builder.append(getLeavemax());//转下期时长上限
		}
		return basicPanel;
	}
	
	/**
	 * 数据和逻辑控制区域
	 * @return
	 */
	public UIPanel getControlPanel(){
		if(controlPanel == null){
			controlPanel = new UIPanel();
			FormLayout layout = new FormLayout(colwidth,"");
			DefaultFormBuilder builder = new DefaultFormBuilder(layout,controlPanel);
			
			//折算规则
			builder.append(PublicLangRes.CONVERTRULE());
			builder.append(getConvertrule());
			
			//结算周期
			builder.append(ResHelper.getString("6017basedoc","06017basedoc1882")/*@res "结算周期"*/);
			builder.append(getLeavesetperiod());
			//结算方式
//			UILabel settlementLabel = new UILabel(ResHelper.getString("common","UC000-0003249")/*@res "结算方式"*/);
			UILabel settlementLabel = new UILabel(ResHelper.getString("6017basedoc","06017basedoc1928")/*@res "结算方式"*/);
			builder.append(settlementLabel);
			builder.append(getLeavesettlement());
			
			//假期计算方式
			UILabel leaveCalLabek = new UILabel(ResHelper.getString("6017basedoc","06017basedoc1517")/*@res "假期计算方式"*/);
			builder.append(leaveCalLabek);
			builder.append(getLeavescale());
			//最小时间单位
			builder.append(getTimeUnitLabel());
			builder.append(getTimeunit());
			//舍位方式
			builder.append(PublicLangRes.ROUNDMODE());
			builder.append(getRoundmode());
			//公休日计算方式,使用uilable 的好处是 鼠标移上去可以显示tooltip
			UILabel gxLabel = new UILabel(PublicLangRes.GXCOMPTYPE());
			builder.append(gxLabel);
			builder.append(getGxcomtype());
			//休假申请日期不得晚于休假开始日期
			builder.append("");
			builder.append(getIsleaveapptimelimit(), 3);
			builder.append(getLeaveapptimelimit());
			builder.nextLine();
			//控制休假时长
			builder.append("");
			builder.append(getIsLeavelimit(),3);
			builder.append(getIsrestrictlimit());
			//结余跨组织转移
			builder.append("");
			builder.append(getIsLeaveTransfer());
			builder.nextLine();
			//有效期限延长
			builder.append("");
			builder.append(getIsleave(),3);
			builder.append(getLeaveextendcount());
			builder.nextLine();
			//享有是否在自助中显示
			builder.append("");
			builder.append(getIshrssshow(),3);
			builder.append(getIsattachmust());
			builder.nextLine();
			
		}
		return controlPanel;
	}
	
	public UILabel getTimeUnitLabel(){
		if(timeUnitLabel == null){
			timeUnitLabel = new UILabel(PublicLangRes.MINTIMEUNIT());
		}
		return timeUnitLabel;
	}

	/**
	 * 假期计算方式panel
	 */
//	private UIPanel scalePanel;
//	public UIPanel getScalePanel(){
//		if(scalePanel==null){
//			scalePanel = new UIPanel();
//			FormLayout layout = new FormLayout(
//					"left:140px, 1px, fill:120px, 15px,left:100px, 10px",
//					"");
//			DefaultFormBuilder builder = new DefaultFormBuilder(layout,scalePanel);

//			builder.append(getLeavescaleLabel());
//			builder.append(getLeavescale());
//			builder.nextLine();
//		}
//		return scalePanel;
//	}

	/**
	 * 底部panel
	 */
//	private UIPanel downPanel;
//	public UIPanel getDownPanel(){
//		if (downPanel == null) {
//			downPanel = new UIPanel();
//			FormLayout layout = new FormLayout(
//					//"left:140px, 1px, fill:120px, 15px,left:120px, 10px", 中文
//
//					"left:150px, 1px, fill:120px, 15px,left:300px, 10px",
//					"20px,5px,20px,5px,20px,5px,25px,5px,25px,5px");
//			DefaultFormBuilder builder = new DefaultFormBuilder(layout,
//					downPanel);

//			builder.append(PublicLangRes.MINTIMEUNIT());
//			builder.append(getTimeunit());
//			builder.append(getTimeItemUnitLabel());
//			builder.nextLine();
//			builder.nextLine();

//			builder.append(PublicLangRes.ROUNDMODE());
//			builder.append(getRoundmode());
//			builder.nextLine();
//			builder.nextLine();

//			builder.append(PublicLangRes.GXCOMPTYPE());
//			builder.append(getGxcomtype());
//			builder.nextLine();
//			builder.nextLine();

//			builder.append(getIsleaveapptimelimit(), 3);
//			UIPanel leaveLimitPanel = new UIPanel();
//			leaveLimitPanel.setLayout(new FlowLayout(3,3,3));
////			leaveLimitPanel.add(getLeaveapptimelimit());
//			//天
//			leaveLimitPanel.add(new UILabel(StringHelper.getPartOfString(getLeaveAppDateStr(), 1)));
//			builder.append(leaveLimitPanel);
//			builder.nextLine();
//			builder.nextLine();
			
//			builder.append(getIsLeavelimit());//控制休假时长
			/*UIPanel a1 = new UIPanel();
			a1.setLayout(new FlowLayout(3,3,3));
			a1.add(getIsLeavelimit());
			//严格控制
			a1.add(getIsrestrictlimit());
			builder.append(a1);
			*/
			//builder.append(getIsLeavelimit());
//			builder.append(getIsrestrictlimit());
//			builder.nextLine();
//		}
//		return downPanel;
//	}

	/**
	 * 折算规则label
	 */
	private UILabel convertRuleLabel;
	public UILabel getConvertRuleLabel(){
		if(convertRuleLabel==null)
			convertRuleLabel = new UILabel(PublicLangRes.CONVERTRULE());
		return convertRuleLabel;
	}

	/**
	 * 假期计算方式label
	 */
	private UILabel leavescaleLabel;
	public UILabel getLeavescaleLabel(){
		if(leavescaleLabel==null)
			leavescaleLabel = new UILabel(ResHelper.getString("6017basedoc","06017basedoc1517")
/*@res "假期计算方式"*/);
//		leavescaleLabel.setToolTipText(ResHelper.getString("6017basedoc","06017basedoc1517")
///*@res "假期计算方式"*/);
		return leavescaleLabel;
	}

	/**
	 * 计算方式panel
	 * @return
	 */
//	public UIPanel getConvertRulePanel(){
//		UIPanel convertPanel = new UIPanel();
//		FormLayout layout = new FormLayout(
//				"left:60px, 5px, right:50px, 5px,fill:110px, 1px",
//				"");
//
//		DefaultFormBuilder builder = new DefaultFormBuilder(layout,convertPanel);
//
//		builder.append(getTimeitemunit());
//		builder.append(getConvertRuleLabel());
////		builder.append(getConvertrule());
//		builder.nextLine();

//		return convertPanel;
//	}

	/**
	 * 结算方式panel
	 */
//	private UIPanel settPanel;
//	public UIPanel getSettlementPanel(){
//		if(settPanel==null){
//			settPanel = new UIPanel();
//			FormLayout layout = new FormLayout(
//					"left:140px, 1px,left:90px, 1px, left:80px, 5px",
//					"");
//			DefaultFormBuilder builder = new DefaultFormBuilder(layout,settPanel);

//			builder.append(ResHelper.getString("common","UC000-0003249")/*@res "结算方式"*/);
//			builder.append(getLeavesetperiod());
//			builder.append(getLeavesettlement());
//			builder.nextLine();
//		}
//		return settPanel;
//	}
	
	

//	public UIPanel getLeavePanel() {
//		if(leavePanel==null){
//			leavePanel = new UIPanel();
//			FormLayout layout = new FormLayout(
//					//"left:140px, 1px,left:50px, 2px, left:15px, 2px",   //中文
//					"left:240px, 1px,left:150px, 2px, left:150px, 2px",
//					"");
//			DefaultFormBuilder builder = new DefaultFormBuilder(layout,leavePanel);
////			builder.append(getIsleave(),1);
//			//builder.append(getLeaveextendcount());
//			//builder.append(PublicLangRes.DAY());
//			
//			UIPanel dayPanel = new UIPanel();
//			dayPanel.setLayout(new FlowLayout(1));
////			dayPanel.add(getLeaveextendcount());
//			//天
//			dayPanel.add(new UILabel(PublicLangRes.DAY()));
//			builder.append(dayPanel);
//			builder.nextLine();
//		}
//		return leavePanel;
//	}

	public UIPanel getRuleUnionPanel() {
		if(ruleUnionPanel==null){
			ruleUnionPanel = new UIPanel();
			FormLayout layout = new FormLayout(
					"left:pref","");
			DefaultFormBuilder builder = new DefaultFormBuilder(layout,ruleUnionPanel);

			builder.append(getFormulaSetPanel());
			builder.nextLine();
			builder.append(getDependSetPanel());
			builder.nextLine();
		}
		return ruleUnionPanel;
	}

	private UIPanel formulaSetPanel;
	public UIPanel getFormulaSetPanel(){
		if(formulaSetPanel==null){
			formulaSetPanel = new UIPanel();
			formulaSetPanel.setBorder(new UITitledBorder(ResHelper.getString("6017basedoc","06017basedoc1518")
/*@res "休假规则"*/));
			//列宽定义
			String colwidth = "right:105px,5px,fill:185px,5px,right:130px,5px,fill:185px,5px,right:130px,5px,fill:185px,5px";
			FormLayout layout = new FormLayout(colwidth,"");
			DefaultFormBuilder builder = new DefaultFormBuilder(layout,formulaSetPanel);

			builder.append(getFormulaset());
			builder.append(getFormulastr(),9);
		}
		return formulaSetPanel;
	}

	private UIPanel dependSetPanel;
	public UIPanel getDependSetPanel(){
		if(dependSetPanel==null){
			dependSetPanel = new UIPanel();
			dependSetPanel.setBorder(new UITitledBorder(ResHelper.getString("6017basedoc","06017basedoc1519")
/*@res "前置休假类别"*/));
			//列宽定义
			String colwidth = "right:105px,5px,fill:185px,5px,right:130px,5px,fill:185px,5px,right:130px,5px,fill:185px,5px";
			FormLayout layout = new FormLayout(colwidth,"");
			DefaultFormBuilder builder = new DefaultFormBuilder(layout,dependSetPanel);

			builder.append(getDependset());
			builder.append(getDependPanel(),9);
			builder.nextLine();
		}
		return dependSetPanel;
	}

	public BillListPanel getDependPanel() {
		if(dependPanel==null){
			dependPanel = new BillListPanel();
			List<BillTempletBodyVO> retList = new ArrayList<BillTempletBodyVO>();
			int order = 1000;
			//开始时间
			BillTempletBodyVO bodyVO = PsnTempletUtils.createDefaultBillTempletBodyVO(IBillItem.HEAD, order++);
			retList.add(bodyVO);
			bodyVO.setDatatype(IBillItem.STRING);
			bodyVO.setNullflag(false);
//			bodyVO.setShowflag(false);
			bodyVO.setDefaultshowname(ResHelper.getString("6017item","2awaytype-00014")
					/*@res "考勤项目主键"*/);
			bodyVO.setItemkey(TimeItemVO.PK_TIMEITEM);
			bodyVO.setMetadataproperty(null);
			bodyVO.setMetadatapath(null);
			bodyVO.setListshowflag(false);
//			bodyVO.setWidth(0);

			//结束时间
			bodyVO = PsnTempletUtils.createDefaultBillTempletBodyVO(IBillItem.HEAD, order++);
			retList.add(bodyVO);
			bodyVO.setDatatype(IBillItem.STRING);
			bodyVO.setDefaultshowname(ResHelper.getString("6017basedoc","06017basedoc1520")
/*@res "休假类别编码"*/);
			bodyVO.setNullflag(false);
			bodyVO.setItemkey(TimeItemVO.TIMEITEMCODE);
			bodyVO.setMetadataproperty(null);
			bodyVO.setMetadatapath(null);

			//休息时长
			bodyVO = PsnTempletUtils.createDefaultBillTempletBodyVO(IBillItem.HEAD, order++);
			retList.add(bodyVO);
			bodyVO.setDatatype(IBillItem.MULTILANGTEXT);
			bodyVO.setDefaultshowname(ResHelper.getString("6017basedoc","06017basedoc1521")
/*@res "休假类别名称"*/);
			bodyVO.setNullflag(false);
			bodyVO.setItemkey(TimeItemVO.TIMEITEMNAME);
			bodyVO.setMetadataproperty(null);
			bodyVO.setMetadatapath(null);

			BillTempletVO btv = new BillTempletVO();
			btv.setChildrenVO(retList.toArray(new BillTempletBodyVO[0]));

			dependPanel.setListData(new BillListData(btv));
			dependPanel.getChildListPanel().setBBodyMenuShow(false);
			dependPanel.getHeadTable().setColumnWidth(new int[]{110,110});
			dependPanel.setPreferredSize(new Dimension(270,110));
			dependPanel.getHeadTable().setSortEnabled(false);
		}
		return dependPanel;
	}

	/**
	 * 用于切换显示 分钟 或 小时  的uiLabel
	 */
	private UILabel timeItemUnitLabel;
	public UILabel getTimeItemUnitLabel(){
		if(timeItemUnitLabel==null)
			timeItemUnitLabel = new UILabel(PublicLangRes.MINTIMEUNIT());
		return timeItemUnitLabel;
	}

	@SuppressWarnings("unchecked")
	public UIComboBox getLeavesetperiod() {
		if(leavesetperiod==null){
			leavesetperiod = new UIComboBox();
//			String s = ResHelper.getString("6017basedoc","06017basedoc1522")
/*@res "按{0}结算"*/;
			leavesetperiod.addItem(new DefaultConstEnum(TimeItemCopyVO.LEAVESETPERIOD_MONTH,ResHelper.getString("6017basedoc","06017basedoc1929")
					/*@res "按期间结算"*/));
			leavesetperiod.addItem(new DefaultConstEnum(TimeItemCopyVO.LEAVESETPERIOD_YEAR, ResHelper.getString("6017basedoc","06017basedoc1930")
					/*@res "按年结算"*/));
			leavesetperiod.addItem(new DefaultConstEnum(TimeItemCopyVO.LEAVESETPERIOD_DATE, ResHelper.getString("6017basedoc","06017basedoc1931")
					/*@res "按入职日期结算"*/));
			getComponentList().add(leavesetperiod);
			leavesetperiod.addItemListener(this);
		}
		return leavesetperiod;
	}
	@SuppressWarnings("unchecked")
	public UIComboBox getLeavesettlement() {
		if(leavesettlement==null){
			leavesettlement = new UIComboBox();
			leavesettlement.addItem(new DefaultConstEnum(TimeItemCopyVO.LEAVESETTLEMENT_DROP,ResHelper.getString("6017basedoc","06017basedoc1523")
/*@res "过期作废"*/));
			leavesettlement.addItem(new DefaultConstEnum(TimeItemCopyVO.LEAVESETTLEMENT_NEXT, ResHelper.getString("6017basedoc","06017basedoc1524")
/*@res "转下期"*/));
			leavesettlement.addItem(new DefaultConstEnum(TimeItemCopyVO.LEAVESETTLEMENT_MONEY,ResHelper.getString("6017basedoc","06017basedoc1525")
/*@res "转工资"*/));
			getComponentList().add(leavesettlement);
		}
		return leavesettlement;
	}
	@SuppressWarnings("unchecked")
	public UIComboBox getLeavescale() {
		if(leavescale==null){
			leavescale = new UIComboBox();
			leavescale.addItem(new DefaultConstEnum(TimeItemCopyVO.LEAVESCALE_YEAR,ResHelper.getString("6017basedoc","06017basedoc1526")
/*@res "按年计算"*/));
			leavescale.addItem(new DefaultConstEnum(TimeItemCopyVO.LEAVESCALE_MONTH, ResHelper.getString("6017basedoc","06017basedoc1527")
/*@res "按月计算"*/));
			getComponentList().add(leavescale);
		}
		return leavescale;
	}
	@SuppressWarnings("unchecked")
	public UIComboBox getGxcomtype() {
		if(gxcomtype==null){
			gxcomtype = new UIComboBox();
			gxcomtype.addItem(new DefaultConstEnum(TimeItemCopyVO.GXCOMTYPE_NOTLEAVE,ResHelper.getString("6017basedoc","06017basedoc1528")
/*@res "不计休假"*/));
			gxcomtype.addItem(new DefaultConstEnum(TimeItemCopyVO.GXCOMTYPE_TOLEAVE, ResHelper.getString("6017basedoc","06017basedoc1529")
/*@res "计为休假"*/));
			getComponentList().add(gxcomtype);
		}
		return gxcomtype;
	}
	public UICheckBox getIsLeavelimit() {
		if(isleavelimit==null){
			isleavelimit = new UICheckBox(ResHelper.getString("6017basedoc","06017basedoc1530")
/*@res "控制休假时长"*/);
			isleavelimit.addActionListener(this);
			getComponentList().add(isleavelimit);
		}
		return isleavelimit;
	}
	public UICheckBox getIsrestrictlimit() {
		if(isrestrictlimit==null){
			isrestrictlimit = new UICheckBox(ResHelper.getString("6017basedoc","06017basedoc1531")
/*@res "严格控制"*/);
			getComponentList().add(isrestrictlimit);
		}
		return isrestrictlimit;
	}
	
	public UICheckBox getIsleaveapptimelimit() {
		if(isleaveapptimelimit==null){
			//休假申请日期不得晚于休假开始日期
			isleaveapptimelimit = new UICheckBox(ResHelper.getString("6017basedoc","06017basedoc1823")
					/*@res "休假申请日期不得晚于开始日期(天)"*/);
			getComponentList().add(isleaveapptimelimit);
			isleaveapptimelimit.addActionListener(this);
		}
		return isleaveapptimelimit;
	}
	public UITextField getLeaveapptimelimit() {
		if(leaveapptimelimit==null){
			leaveapptimelimit = new UITextField();
			leaveapptimelimit.setTextType(UITextType.TextInt);
			leaveapptimelimit.setMinValue(0);
			getComponentList().add(leaveapptimelimit);
		}
		return leaveapptimelimit;
	}
	public UICheckBox getIsleave() {
		if(isleave==null){
			isleave = new UICheckBox(ResHelper.getString("6017basedoc","06017basedoc1533")
/*@res "有效期延长时限(天)"*/);
			getComponentList().add(isleave);
			isleave.addActionListener(this);
		}
		return isleave;
	}

	@SuppressWarnings("unchecked")
	public UIComboBox getConvertrule() {
		if(convertrule==null){
			convertrule = new UIComboBox();
			convertrule.addItem(new DefaultConstEnum(TimeItemCopyVO.CONVERTRULE_DAY,ResHelper.getString("6017basedoc","06017basedoc1534")
/*@res "按工作日折算"*/));
			convertrule.addItem(new DefaultConstEnum(TimeItemCopyVO.CONVERTRULE_TIME, ResHelper.getString("6017basedoc","06017basedoc1535")
/*@res "按班次时长折算"*/));
			getComponentList().add(convertrule);
		}
		return convertrule;
	}
	public UITextField getTimeunit() {
		if(timeunit==null){
			timeunit = new UITextField();
			timeunit.setTextType(UITextType.TextDbl);
			timeunit.setNumPoint(2);
			timeunit.setMinValue(0);
			getComponentList().add(timeunit);
		}
		return timeunit;
	}
	public UITextField getLeaveextendcount() {
		if(leaveextendcount==null){
			leaveextendcount = new UITextField();
			leaveextendcount.setTextType(UITextType.TextInt);
			leaveextendcount.setMinValue(0);
			leaveextendcount.setMaxValue(1000);
			getComponentList().add(leaveextendcount);
		}
		return leaveextendcount;
	}

	public UIButton getDependset() {
		if(dependset==null){
			dependset = new UIButton();
			dependset.setText(PublicLangRes.SET());
//			dependset.setText("设置前置假别");
//			dependset.setPreferredSize(new Dimension(100,20));
			getComponentList().add(dependset);
			dependset.addActionListener(this);
		}
		return dependset;
	}

	public UIButton getFormulaset() {
		if(formulaset==null){
			formulaset = new UIButton();
//			formulaset.setText("设置休假规则");
			formulaset.setText(PublicLangRes.SET());
//			formulaset.setPreferredSize(new Dimension(100,20));
			getComponentList().add(formulaset);
			formulaset.addActionListener(this);
		}
		return formulaset;
	}
	public UITextAreaScrollPane getFormulastr() {
		if(formulastr==null){
			formulastr = new UITextAreaScrollPane();
			formulastr.setPreferredSize(new Dimension(500,100));
			formulastr.setEnabled(false);
		}
		return formulastr;
	}
	
	public void setFormulaDesc(String formula){
		if(StringUtils.isBlank(formula))
			return;
		String formulaDesc = getEditorDialog().getBusinessLang(formula);
		getFormulastr().setText(formulaDesc);
	}
	
	public String getFormula() {
		return formula;
	}
	public void setFormula(String formula) {
		this.formula = formula;
	}
	/**
	 * 取列表中休假类别的主键
	 * @return
	 */
	public String getDependleavetypes() {
		BillModel billModel = getDependPanel().getHeadBillModel();
		int rowCount = billModel.getRowCount();
		if(rowCount<=0)
			return null;
		StringBuffer dependBuffer = new StringBuffer();
		for(int i=0; i<rowCount; i++){
			String pk_timeitem = (String) billModel.getValueAt(i, TimeItemVO.PK_TIMEITEM);
			dependBuffer.append(pk_timeitem).append(",");
		}
		return dependBuffer.substring(0, dependBuffer.length()-1).toString();
	}
	/**
	 * 设置前置规则列表
	 * @param dependleavetypes
	 */
	public void setDependleavetypes(TimeItemVO[] dependleavetypes) {
		BillModel billModel = getDependPanel().getHeadBillModel();
		billModel.clearBodyData();
		if(ArrayUtils.isEmpty(dependleavetypes))
			return;
		billModel.addLine(dependleavetypes.length);
		for(int i = 0;i < dependleavetypes.length;i++){
			billModel.setValueAt(dependleavetypes[i].getPk_timeitem(), i, TimeItemVO.PK_TIMEITEM);
			billModel.setValueAt(dependleavetypes[i].getTimeitemcode(), i, TimeItemVO.TIMEITEMCODE);
			MultiLangText text = new MultiLangText();
			text.setText(dependleavetypes[i].getTimeitemname());
			text.setText2(dependleavetypes[i].getTimeitemname2());
			text.setText3(dependleavetypes[i].getTimeitemname3());
			text.setText4(dependleavetypes[i].getTimeitemname4());
			text.setText5(dependleavetypes[i].getTimeitemname5());
			text.setText6(dependleavetypes[i].getTimeitemname6());
			billModel.setValueAt(text, i, TimeItemVO.TIMEITEMNAME);
		}
	}
	
	public UICheckBox getIsLeaveTransfer(){
		if(isleaveTransfer == null){
			isleaveTransfer = new UICheckBox(ResHelper.getString("6017basedoc","06017basedoc1883")/*@res "结余跨组织转移"*/);
			getComponentList().add(isleaveTransfer);
		}
		return isleaveTransfer;
	}
	
	public UICheckBox getIshrssshow(){
		if(ishrssshow == null){
			ishrssshow = new UICheckBox(ResHelper.getString("6017basedoc","06017basedoc1926")/*@res "在自助服务中显示享有时长"*/);
			getComponentList().add(ishrssshow);
		}
		return ishrssshow;
	}
	
	public UICheckBox getIsattachmust() {
		if(isattachmust == null) {
			isattachmust = new UICheckBox("Attachment Upload Required");
			getComponentList().add(isattachmust);
		}
		return isattachmust;
	}
	
	public UITextField getShoworder() {
		if(showorder==null){
			showorder = new UITextField();
			showorder.setTextType(UITextType.TextInt);
			showorder.setMinValue(0);
			showorder.setMaxValue(1000);
			getComponentList().add(showorder);
		}
		return showorder;
	}
	
	public UITextField getLeavemax() {
		if(leavemax==null){
			leavemax = new UITextField();
			leavemax.setTextType(UITextType.TextDbl);
			leavemax.setNumPoint(2);
			leavemax.setMinValue(0);
			getComponentList().add(leavemax);
		}
		return leavemax;
	}
}