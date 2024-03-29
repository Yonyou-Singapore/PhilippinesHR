package nc.ui.ta.leave.pf.action;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import nc.bs.dbcache.intf.IDBCacheBS;
import nc.bs.framework.common.NCLocator;
import nc.hr.utils.CommonUtils;
import nc.hr.utils.ResHelper;
import nc.itf.ta.ILeaveApplyQueryMaintain;
import nc.itf.ta.algorithm.ITimeScopeWithBillInfo;
import nc.itf.uap.IUAPQueryBS;
import nc.jdbc.framework.processor.ArrayListProcessor;
import nc.jdbc.framework.processor.BeanProcessor;
import nc.ui.hr.pf.action.PFSaveAction;
import nc.ui.hr.pf.model.PFAppModel;
import nc.ui.pub.bill.BillCardPanel;
import nc.ui.pub.bill.BillData;
import nc.ui.ta.bill.BillInfoDlg;
import nc.ui.ta.leave.pf.model.LeaveAppModel;
import nc.ui.ta.leave.pf.view.LeaveCardForm;
import nc.ui.ta.leave.pf.view.SplitResultDialog;
import nc.ui.ta.wf.pub.TBMPubBillCardForm;
import nc.ui.uif2.IShowMsgConstant;
import nc.ui.uif2.ShowStatusBarMsgUtil;
import nc.ui.uif2.UIState;
import nc.ui.uif2.editor.BillForm;
import nc.vo.pub.BusinessException;
import nc.vo.pub.ValidationException;
import nc.vo.pub.lang.UFBoolean;
import nc.vo.pub.lang.UFDate;
import nc.vo.pub.lang.UFDouble;
import nc.vo.pub.lang.UFLiteralDate;
import nc.vo.ta.PublicLangRes;
import nc.vo.ta.bill.BillMutexException;
import nc.vo.ta.leave.AggLeaveVO;
import nc.vo.ta.leave.LeaveCheckResult;
import nc.vo.ta.leave.LeavebVO;
import nc.vo.ta.leave.LeavehVO;
import nc.vo.ta.leave.SplitBillResult;
import nc.vo.ta.pub.AllParams;
import nc.vo.ta.pub.TALoginContext;
import nc.vo.ta.timeitem.TimeItemVO;
import nc.vo.ta.timerule.TimeRuleVO;
import nc.vo.uif2.LoginContext;

public class PFSaveLeaveAction extends PFSaveAction {
	private static final long serialVersionUID = 1L;

	protected void validate(Object objValue) {
		super.validate(objValue);
	}

	private boolean islactation() {
		boolean islactation = false;
		if (getModel().getUiState() == UIState.ADD) {
			islactation = ((LeaveAppModel) getModel()).isIslactation() == null ? false
					: ((LeaveAppModel) getModel()).isIslactation()
							.booleanValue();
		} else {
			AggLeaveVO aggVO = (AggLeaveVO) getModel().getSelectedData();
			if (aggVO != null) {
				islactation = aggVO.getLeavehVO().getIslactation() == null ? false
						: aggVO.getLeavehVO().getIslactation().booleanValue();
			}
		}

		return islactation;
	}

	public void doAction(ActionEvent evt)
     throws Exception
   {
     ((LeaveCardForm)getEditor()).getBillCardPanel().stopEditing();
     AggLeaveVO aggVO = (AggLeaveVO)((TBMPubBillCardForm)getEditor()).getValue(1);
     
     LeavehVO headVO = aggVO.getLeavehVO();
     headVO.setIslactation(UFBoolean.valueOf(islactation()));
     super.validate(aggVO);
      
     // 休假申请 休假总时长限制  xm:2018.11.22/start
     IDBCacheBS dbcf = (IDBCacheBS) NCLocator.getInstance().lookup(IDBCacheBS.class.getName());
     // 查询: 需要判断类型视图
     String selectSQL =  "select pk_timeitem from timeitem_vacationduration where pk_timeitem ='" + headVO.getPk_leavetype() + "';";
     ArrayList selectList = (ArrayList) dbcf.runSQLQuery(selectSQL, new ArrayListProcessor());
     
     if ( selectList.size() > 0 ) {
    	 for (int i = 0; (aggVO.getLeavebVOs() != null ) && (i < aggVO.getLeavebVOs().length ) ; i++) {
    		 LeavebVO  leavebVO = aggVO.getLeavebVOs()[i];
    		 UFLiteralDate leaveBeginDate = leavebVO.getLeavebegindate();
    		 UFLiteralDate leaveEndDate = leavebVO.getLeaveenddate();
    		 
    		 long rowDifference =  ((leaveEndDate.getMillis() - leaveBeginDate.getMillis())  / 1000 / 60 / 60 / 24) ; 
    		 
    		 // 判断：是否同一个月
    		// 2019-07-10 客户变更：同一个月校验去掉  Ethan Wu start
//    		 if ( leaveBeginDate.getMonth() != leaveEndDate.getMonth() ) {
//    			 throw new BusinessException("休假期间必须在同一个月");
//    		 }
    		// 2019-07-10 客户变更：同一个月校验去掉  Ethan Wu start
    		 // 判断单行：结束日期-开始日期  = 6 
        	 if ( rowDifference != 6 ) {
        		 throw new BusinessException("第" + (i+1) + "行明细必须连续休假7天");
        	 } 
        	 
    		 // 判断多行( 第一次填写，未保存到数据库 )： 开始日期必须晚于上次休假结束日期的7天自然日 
        	 if ( i > 0 && leavebVO.getPk_leaveb() == null) {
        		 // 多行并且未保存
        		 // 不同月份判断间隔
        		 LeavebVO  lastLeavebVO = aggVO.getLeavebVOs()[(i-1)];
        		 UFLiteralDate lastBeginDate = lastLeavebVO.getLeavebegindate();
        		 UFLiteralDate lastEndDate = lastLeavebVO.getLeaveenddate();
        		 
        		 long rowsDifference =  ((leaveBeginDate.getMillis() - lastEndDate.getMillis())  / 1000 / 60 / 60 / 24) ; 
        		 long rowsDifference2 = ((lastBeginDate.getMillis() - leaveEndDate.getMillis())  / 1000 / 60 / 60 / 24);
        		// 判断：本月 是否已有提交, 判断间隔, 月份是否大于本月  // 中间间隔大于7天 (开始日期 - 上一次结束日期 ) > 8 
//        		 if ( leaveBeginDate.getMonth() == lastBeginDate.getMonth() ) {
//        			 throw new BusinessException("第" + (i+1) + "行明细本月已申请过休假，不能再申请");
//        		 } else 
        		 if ( leaveBeginDate.after(lastBeginDate) && rowsDifference < 8 ) {
        			 // 否 > 本月开始时间和查询月结束时间做判断
        			 throw new BusinessException("第" + (i+1) + "行明细开始日期必须与上一次休假结束日期间隔7个自然日");
        		 } else if ( leaveBeginDate.before(lastBeginDate) && rowsDifference2 < 8 ) {
        			 // 是 > 本月结束时间和查询月开始时间做判断
        			 throw new BusinessException("第" + (i+1) + "行明细结束日期必须与上一次休假开始日期间隔7个自然日");
        		 }           	 
            	 
        	 } 
            	 // 查询：已经填写（已经保存到数据库）的单据(最后一条)的开始和结束日期
        		 String leavebPK =  leavebVO.getPk_leaveb() == null ? "" : (" and b.pk_leaveb !='"+ leavebVO.getPk_leaveb() + "'");
    			 
        		 selectSQL = "select  b.leavebegindate,b.leaveenddate from tbm_leaveb b inner join tbm_leaveh h on h.pk_leaveh  = b.pk_leaveh"
    					 + " where  h.pk_psndoc ='" + headVO.getPk_psndoc() + "' and h.pk_leavetype ='" + headVO.getPk_leavetype() + "'"
    					 + leavebPK
    					 + " order by b.leaveendtime desc";
            	 
            	 selectList = (ArrayList) dbcf.runSQLQuery(selectSQL, new ArrayListProcessor());
            	 
            	 if (selectList.size() > 0 ) {
            		 Object[] values = (Object[]) selectList.get(0);
            		 UFLiteralDate lastBeginDate = new UFLiteralDate(values[0].toString());
            		 UFLiteralDate lastEndDate = new UFLiteralDate(values[1].toString());
            		 long rowsDifference = ((leaveBeginDate.getMillis() - lastEndDate.getMillis())  / 1000 / 60 / 60 / 24);
            		 long rowsDifference2 = ((lastBeginDate.getMillis() - leaveEndDate.getMillis())  / 1000 / 60 / 60 / 24);
            		 // 判断：本月 是否已有提交, 判断间隔, 月份是否大于本月
//            		 if ( leaveBeginDate.getMonth() == lastBeginDate.getMonth() ) {
//            			 throw new BusinessException("第" + (i+1) + "行明细本月已申请过休假，不能再申请");
//            		 } else 
            		if ( leaveBeginDate.after(lastBeginDate) && rowsDifference < 8 ) {
            			 // 否 > 本月开始时间和查询月结束时间做判断
            			 throw new BusinessException("第" + (i+1) + "行明细开始日期必须晚于上一次休假结束日期7个自然日");
            		 } else if ( leaveBeginDate.before(lastBeginDate) && rowsDifference2 < 8 ) {
            			 // 是 > 本月结束时间和查询月开始时间做判断
            			 throw new BusinessException("第" + (i+1) + "行明细开始日期必须早于上一次休假申请结束日期7个自然日");
            		 }
            	 }        		 
        	 
    	 }
    	 
    	  //ResHelper.getString("6017leave", "06017leave0249")
     }
  // 休假申请 休假总时长限制  xm:2018.11.22/end 
     
     
     for (int i = 0; (aggVO.getLeavebVOs() != null) && (i < aggVO.getLeavebVOs().length); i++)
     {
       aggVO.getLeavebVOs()[i].setIslactation(UFBoolean.valueOf(islactation()));
     }
     
     ILeaveApplyQueryMaintain maintain = (ILeaveApplyQueryMaintain)NCLocator.getInstance().lookup(ILeaveApplyQueryMaintain.class);
     try {
       if (!islactation())
       {
 
    	 
         LeavebVO[] leavebvos = aggVO.getLeavebVOs();
         List<LeavebVO> subvos = new ArrayList();
         for (int i = 0; i < leavebvos.length; i++) {
           if (3 != leavebvos[i].getStatus())
           {
             subvos.add(leavebvos[i]); }
         }
         LeavebVO[] leaveVOs = (LeavebVO[])subvos.toArray(new LeavebVO[0]);
         for (int i = 0; i < leaveVOs.length; i++) {
           if (leaveVOs[i].getLeavehour().doubleValue() <= 0.0D) {
             throw new ValidationException(ResHelper.getString("6017leave", "06017leave0249", new String[] { i + 1 + "" }));
           }
           
         }
       }
       
       LeaveCheckResult<AggLeaveVO> checkResult = maintain.checkWhenSave(aggVO);
       
 
 
       Map<String, Map<Integer, ITimeScopeWithBillInfo[]>> checkMutextResult = checkResult.getMutexCheckResult();
       if (checkMutextResult != null) {
         int result = BillInfoDlg.showOkCancelDialog(getContext().getEntranceUI(), null, CommonUtils.transferMap(checkMutextResult));
         if (result != 1) {
           putValue("message_after_action", PublicLangRes.CANCELED());
           return;
         }
       }
       SplitBillResult<AggLeaveVO> splitResult = checkResult.getSplitResult();
       if (splitResult.needQueryUser()) {
         TimeRuleVO timeRuleVO = ((TALoginContext)getModel().getContext()).getAllParams().getTimeRuleVO();
         int result = SplitResultDialog.showOkCancelDialog(getContext().getEntranceUI(), null, splitResult, timeRuleVO);
         if ((result != 1) && (result != 4)) {
           putValue("message_after_action", PublicLangRes.CANCELED());
           return;
         }
       }
       
 
 
 
 
       boolean validateResult = ((BillForm)getEditor()).getBillCardPanel().getBillData().execValidateFormulas();
       
       if (!validateResult)
         return;
       if (getModel().getUiState() == UIState.ADD) {
         getModel().add(splitResult);
         getModel().clearSelectedOperaRows();
         int size = getModel().getRowCount();
         int length = ((AggLeaveVO[])splitResult.getSplitResult()).length;
         if (length > 1) {
           int[] selectedrows = new int[length];
           for (int i = 0; i < length; i++) {
             selectedrows[i] = (size - i - 1);
           }
           getModel().setSelectedOperaRowsWithoutEvent(selectedrows);
         }
       }
       else if (getModel().getUiState() == UIState.EDIT) {
         getModel().update(splitResult); }
       getModel().setUiState(UIState.NOT_EDIT);
       ShowStatusBarMsgUtil.showStatusBarMsg(IShowMsgConstant.getSaveSuccessInfo(), getContext());
       ((LeaveAppModel)getModel()).setIslactation(UFBoolean.valueOf(islactation()));
 
     }
     catch (BillMutexException bme)
     {
       Map<String, Map<Integer, ITimeScopeWithBillInfo[]>> result = bme.getMutexBillsMap();
       if (result != null) {
         BillInfoDlg.showErrorDialog(getContext().getEntranceUI(), null, CommonUtils.transferMap(result));
         throw new BusinessException(ResHelper.getString("6017leave", "06017leave0195"));
       }
     }
   }
}
