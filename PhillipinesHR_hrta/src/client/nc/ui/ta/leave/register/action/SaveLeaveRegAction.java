package nc.ui.ta.leave.register.action;

import java.awt.event.ActionEvent;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Map;

import nc.bs.dbcache.intf.IDBCacheBS;
import nc.bs.framework.common.NCLocator;
import nc.bs.logging.Logger;
import nc.hr.utils.CommonUtils;
import nc.hr.utils.ResHelper;
import nc.itf.hr.frame.IPersistenceRetrieve;
import nc.itf.ta.ILeaveRegisterQueryMaintain;
import nc.itf.ta.IPeriodQueryService;
import nc.itf.ta.algorithm.ITimeScopeWithBillInfo;
import nc.jdbc.framework.processor.ArrayListProcessor;
import nc.ui.hr.uif2.action.SaveAction;
import nc.ui.ta.bill.BillInfoDlg;
import nc.ui.ta.leave.pf.view.SplitResultDialog;
import nc.ui.ta.leave.register.model.LeaveRegAppModel;
import nc.ui.uif2.IShowMsgConstant;
import nc.ui.uif2.ShowStatusBarMsgUtil;
import nc.ui.uif2.UIState;
import nc.ui.uif2.editor.IEditor;
import nc.ui.uif2.model.AbstractAppModel;
import nc.vo.bd.psn.PsndocVO;
import nc.vo.pub.BusinessException;
import nc.vo.pub.lang.UFBoolean;
import nc.vo.pub.lang.UFDate;
import nc.vo.pub.lang.UFDateTime;
import nc.vo.pub.lang.UFDouble;
import nc.vo.pub.lang.UFLiteralDate;
import nc.vo.ta.PublicLangRes;
import nc.vo.ta.bill.BillMutexException;
import nc.vo.ta.leave.LeaveCheckResult;
import nc.vo.ta.leave.LeaveRegVO;
import nc.vo.ta.leave.SplitBillResult;
import nc.vo.ta.period.PeriodVO;
import nc.vo.ta.pub.AllParams;
import nc.vo.ta.pub.TALoginContext;
import nc.vo.ta.timerule.TimeRuleVO;
import nc.vo.uif2.LoginContext;

public class SaveLeaveRegAction extends SaveAction {
	public void doAction(ActionEvent evt) throws Exception {
		Object objValue = super.getEditor().getValue();
		LeaveRegVO vo = (LeaveRegVO) objValue;

		super.validate(vo);

		String hint = "";

		if (islactation()) {
			if ((vo.getLeavebegindate() == null)
					|| (vo.getLeaveenddate() == null)
					|| (vo.getLeavebegindate().after(vo.getLeaveenddate()))) {

				hint = ResHelper.getString("6017leave", "06017leave0203");

				hint = MessageFormat.format(
						hint,
						new Object[] { vo.getLeavebegindate(),
								vo.getLeaveenddate() });
			}

		} else {
			PeriodVO period1 = null;
			PeriodVO period2 = null;
			try {
				period1 = ((IPeriodQueryService) NCLocator.getInstance()
						.lookup(IPeriodQueryService.class)).queryByDate(
						vo.getPk_org(), vo.getBegindate());
				period2 = ((IPeriodQueryService) NCLocator.getInstance()
						.lookup(IPeriodQueryService.class)).queryByDate(
						vo.getPk_org(), vo.getEnddate());
				TimeRuleVO timeRule = ((TALoginContext) getModel().getContext())
						.getAllParams().getTimeRuleVO();
				UFBoolean canOverYear = timeRule.getIscansaveoveryear();
				if ((!period1.getYear().equals(period2.getYear()))
						&& ((!canOverYear.booleanValue()) || (canOverYear == null))) {
					hint = ResHelper.getString("6017leave", "06017leave0265");
				}
			} catch (BusinessException e) {
				Logger.error(e.getMessage(), e);
			}

			if ((vo.getLeavebegintime() == null)
					|| (vo.getLeaveendtime() == null)
					|| (vo.getLeavebegintime().after(vo.getLeaveendtime()))) {

				hint = ResHelper.getString("6017leave", "06017leave0204");

				hint = MessageFormat.format(
						hint,
						new Object[] { vo.getLeavebegintime(),
								vo.getLeaveendtime() });
			}
		}

		if (!hint.equals("")) {
			throw new BusinessException(hint);
		}
		
		// xm 休假总时长限制,当休假类别为指定类别时 /start
		IDBCacheBS dbcf = (IDBCacheBS) NCLocator.getInstance().lookup(IDBCacheBS.class.getName());
		// 查询: 需要判断类型视图
		String selectSQL =  "select pk_timeitem from timeitem_vacationduration where pk_timeitem ='" + vo.getPk_leavetype() + "';";
		ArrayList selectList = (ArrayList) dbcf.runSQLQuery(selectSQL, new ArrayListProcessor());
		 		
		if ( selectList.size() > 0 ) {
   		 	UFLiteralDate leaveBeginDate = vo.getLeavebegindate();
   		 	UFLiteralDate leaveEndDate = vo.getLeaveenddate();
			
			long rowDifference = (( leaveEndDate.getMillis() - leaveBeginDate.getMillis()) / 1000 / 60 / 60 / 24);
			// 判断：是否同一个月
			// 2019-07-10 客户变更：同一个月校验去掉  Ethan Wu start
//			if ( leaveBeginDate.getMonth() != leaveEndDate.getMonth() ) {
//				throw new BusinessException("休假期间必须在同一个月");
//			}
			// 2019-07-10 客户变更：同一个月校验去掉  Ethan Wu start
			// 判断单行：休假结束日期必须等于开始日期加7个自然日
			if (rowDifference != 6) {
				throw new BusinessException("休假结束日期必须等于开始日期加7个自然日");
			}
			// 查询：已经填写（已经保存到数据库）的单据(最后一条)的开始和结束日期
	   		 String leaveregPK =  vo.getPk_leavereg() == null ? "" : (" and pk_leavereg !='"+ vo.getPk_leavereg() + "'");
				 
	   		 selectSQL = "select leavebegindate,leaveenddate from tbm_leavereg where pk_psndoc='" + vo.getPk_psndoc() + "'"
						 + leaveregPK
						 + " order by leaveendtime desc";
	   		 
	       	 selectList = (ArrayList) dbcf.runSQLQuery(selectSQL, new ArrayListProcessor());
	       	 
	       	 if (selectList.size() > 0 ) {
	       		 Object[] values = (Object[]) selectList.get(0);
	       		 UFLiteralDate lastBeginDate = new UFLiteralDate(values[0].toString());
	       		 UFLiteralDate lastEndDate = new UFLiteralDate(values[1].toString());
	       		 
	       		 long rowsDifference = ((leaveBeginDate.getMillis() - lastEndDate.getMillis())  / 1000 / 60 / 60 / 24);
	       		 // 
	       		 long rowsDifference2 = ((lastBeginDate.getMillis() - leaveEndDate.getMillis())  / 1000 / 60 / 60 / 24);
	       		 // 判断：本月 是否已有提交, 判断间隔, 月份是否大于本月
//	       		 if ( leaveBeginDate.getMonth() == lastBeginDate.getMonth() ) {
//	       			 throw new BusinessException("本月已登记过休假，不能再登记");
//	       		 } else 
	       		 if ( leaveBeginDate.after(lastBeginDate) && rowsDifference < 8 ) {
	       			 // 否 > 本月开始时间和查询月结束时间做判断
	       			 throw new BusinessException("开始日期必须与上次休假结束日期间隔7个自然日");
	       		 } else if ( leaveBeginDate.before(lastBeginDate) && rowsDifference2 < 8 ) {
	       			 // 是 > 本月结束时间和查询月开始时间做判断
	       			 throw new BusinessException("结束日期必须与上次休假开始日期间隔7个自然日");
	       		 }
	       	 }        			
		}
		// ResHelper.getString("6017leave", "06017leave0249")
		// xm 休假总时长限制 /end

		ILeaveRegisterQueryMaintain maintain = (ILeaveRegisterQueryMaintain) NCLocator
				.getInstance().lookup(ILeaveRegisterQueryMaintain.class);
		try {
			if (!islactation()) {
				if ((vo.getLeavehour() == null)
						|| (vo.getLeavehour().doubleValue() <= 0.0D)) {
					IPersistenceRetrieve ipersistenceRetrieve = (IPersistenceRetrieve) NCLocator
							.getInstance().lookup(IPersistenceRetrieve.class);
					PsndocVO psndocvo = (PsndocVO) ipersistenceRetrieve
							.retrieveByPk(null, PsndocVO.class,
									vo.getPk_psndoc());
					String name = psndocvo.getName();
					throw new BusinessException(ResHelper.getString(
							"6017leave", "06017leave0250",
							new String[] { name }));
				}
			}

			LeaveCheckResult<LeaveRegVO> checkResult = maintain
					.checkWhenSave(vo);

			Map<String, Map<Integer, ITimeScopeWithBillInfo[]>> checkMutexResult = checkResult
					.getMutexCheckResult();
			if (checkMutexResult != null) {
				int result = BillInfoDlg.showOkCancelDialog(getContext()
						.getEntranceUI(), null, CommonUtils
						.transferMap(checkMutexResult));
				if (result != 1) {
					putValue("message_after_action", PublicLangRes.CANCELED());
					return;
				}
			}
			SplitBillResult<LeaveRegVO> splitResult = checkResult
					.getSplitResult();
			if (splitResult.needQueryUser()) {
				TimeRuleVO timeRuleVO = ((TALoginContext) getModel()
						.getContext()).getAllParams().getTimeRuleVO();
				int result = SplitResultDialog.showOkCancelDialog(
						getEntranceUI(), null, splitResult, timeRuleVO);
				if ((result != 1) && (result != 4)) {
					putValue("message_after_action", PublicLangRes.CANCELED());
					return;
				}
			}

			if (getModel().getUiState() == UIState.ADD) {
				getModel().add(splitResult);
			} else if (getModel().getUiState() == UIState.EDIT)
				getModel().update(splitResult);
			getModel().setUiState(UIState.NOT_EDIT);
			ShowStatusBarMsgUtil.showStatusBarMsg(
					IShowMsgConstant.getSaveSuccessInfo(), getContext());
		} catch (BillMutexException bme) {
			Map<String, Map<Integer, ITimeScopeWithBillInfo[]>> result = bme
					.getMutexBillsMap();
			if (result != null) {
				BillInfoDlg.showErrorDialog(getContext().getEntranceUI(), null,
						CommonUtils.transferMap(result));
				throw new BusinessException(ResHelper.getString("6017leave",
						"06017leave0195"));
			}
		}

		super.doAction(evt);
		((LeaveRegAppModel) getModel()).setIslactation(UFBoolean
				.valueOf(islactation()));
	}

	private boolean islactation() {
		boolean islactation = false;
		if (getModel().getUiState() == UIState.ADD) {
			islactation = ((LeaveRegAppModel) getModel()).Islactation() == null ? false
					: ((LeaveRegAppModel) getModel()).Islactation()
							.booleanValue();
		} else {
			LeaveRegVO regVO = (LeaveRegVO) getModel().getSelectedData();
			if (regVO != null) {
				islactation = regVO.getIslactation() == null ? false : regVO
						.getIslactation().booleanValue();
			}
		}

		return islactation;
	}
}
