package nc.bs.hrss.ta.leave.lsnr;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import nc.bs.dbcache.intf.IDBCacheBS;
import nc.bs.framework.common.NCLocator;
import nc.bs.hrss.pub.HrssConsts;
import nc.bs.hrss.pub.Logger;
import nc.bs.hrss.pub.ServiceLocator;
import nc.bs.hrss.pub.cmd.CloseWindowCmd;
import nc.bs.hrss.pub.exception.HrssException;
import nc.bs.hrss.pub.tool.CommonUtil;
import nc.bs.hrss.ta.away.lsnr.AwaySaveProcessor;
import nc.bs.hrss.ta.common.TaApplyConsts;
import nc.bs.hrss.ta.common.pagemode.TaListBasePageMode;
import nc.bs.hrss.ta.leave.LeaveConsts;
import nc.bs.hrss.ta.leave.LeaveDefRuleCheckUtil;
import nc.bs.hrss.ta.utils.TaAppContextUtil;
import nc.bs.pub.filesystem.FSOption;
import nc.bs.pub.filesystem.IFileSystemService;
import nc.bs.uif2.validation.ValidationFailure;
import nc.hr.utils.ResHelper;
import nc.itf.hrss.pub.cmd.prcss.ISaveProcessor;
import nc.itf.ta.ILeaveApplyApproveManageMaintain;
import nc.itf.ta.ILeaveApplyQueryMaintain;
import nc.itf.ta.ITimeItemQueryService;
import nc.itf.ta.algorithm.ITimeScopeWithBillInfo;
import nc.jdbc.framework.processor.ArrayListProcessor;
import nc.md.model.MetaDataException;
import nc.md.persist.framework.IMDPersistenceQueryService;
import nc.md.persist.framework.MDPersistenceService;
import nc.uap.lfw.core.AppInteractionUtil;
import nc.uap.lfw.core.cmd.CmdInvoker;
import nc.uap.lfw.core.cmd.UifPlugoutCmd;
import nc.uap.lfw.core.comp.FormComp;
import nc.uap.lfw.core.ctx.AppLifeCycleContext;
import nc.uap.lfw.core.ctx.ApplicationContext;
import nc.uap.lfw.core.data.Dataset;
import nc.uap.lfw.core.exception.LfwRuntimeException;
import nc.uap.lfw.core.exception.LfwValidateException;
import nc.uap.lfw.core.page.LfwView;
import nc.vo.pub.AggregatedValueObject;
import nc.vo.pub.BusinessException;
import nc.vo.pub.VOStatus;
import nc.vo.pub.lang.UFBoolean;
import nc.vo.pub.lang.UFDouble;
import nc.vo.ta.bill.BillMutexException;
import nc.vo.ta.leave.AggLeaveVO;
import nc.vo.ta.leave.LeaveCheckResult;
import nc.vo.ta.leave.LeaveCommonVO;
import nc.vo.ta.leave.LeavebVO;
import nc.vo.ta.leave.LeavehVO;
import nc.vo.ta.leave.SplitBillResult;
import nc.vo.ta.leave.pf.validator.PFSaveLeaveValidator;
import nc.vo.ta.timeitem.TimeItemCopyVO;
import nc.vo.ta.timerule.TimeRuleVO;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

import uap.web.bd.pub.AppUtil;

/**
 * �ݼ�����/���������ı������
 * 
 * @author qiaoxp
 * 
 */
public class LeaveSaveProcessor implements ISaveProcessor {

	// �ݼ����뱣��ʱ��ȷ�϶Ի����Id
	private String DIALOG_SAVE = "dlg_Leave_save";

	// �𵥵���ʾ��Ϣ������
	public static final String APP_ATTR_SPLITBILL_DATAS = "app_attr_splitbill_datas";

	private LeaveCheckResult<AggLeaveVO> checkResult = null;

	/**
	 * ����ǰ����
	 * 
	 * @param aggVo
	 * @return
	 */
	@Override
	public void onBeforeVOSave(AggregatedValueObject aggVO) {

	}

	/**
	 * �ݼ�����/���������ı���ǰУ�����
	 * 
	 * @param aggVo
	 * @return
	 */
	@Override
	public boolean checkBeforeVOSave(AggregatedValueObject aggVO)
			throws Exception {
		// ����ֵ
		Boolean result = AppInteractionUtil.getConfirmDialogResult(DIALOG_SAVE);
		if (result != null && Boolean.FALSE.equals(result)) {
			return false;
		}
		AggLeaveVO aggLeaveVO = (AggLeaveVO) aggVO;
		String primaryKey = aggLeaveVO.getLeavehVO().getPrimaryKey();
		LeavehVO leavehVO = (LeavehVO) aggLeaveVO.getParentVO();
		
		ITimeItemQueryService itemService = NCLocator.getInstance().lookup(ITimeItemQueryService.class);

		// HR���ػ����󣺱���ǰ��鸽���Ƿ��Ѿ��ϴ� by Ethan Wu start
		TimeItemCopyVO leavetype = itemService.queryCopyTypesByDefPK(leavehVO.getPk_org(), leavehVO.getPk_leavetype(), TimeItemCopyVO.LEAVE_TYPE);
		if (leavetype.getIsattachmust() != null && leavetype.getIsattachmust().booleanValue()) {
			IFileSystemService service = NCLocator.getInstance().lookup(IFileSystemService.class);
			if (leavehVO.getPrimaryKey() == null && !service.isExist("hrtaleave" + leavehVO.getPk_psndoc())) {
				throw new BusinessException("Please upload attachment!");
			} else if (leavehVO.getPrimaryKey() != null && !service.isExist(leavehVO.getPrimaryKey())) {
				throw new BusinessException("Please upload attachment!");
			}
		}
		// HR���ػ����󣺱���ǰ��鸽���Ƿ��Ѿ��ϴ� by Ethan Wu start
		
		// �Ƿ���ұ�ʶ
		UFBoolean islactation = leavehVO.getIslactation();

		String billCode = leavehVO.getBill_code();// ���ݱ���

		LeavebVO[] leavebVOs = aggLeaveVO.getLeavebVOs();
		for (int i = 0; leavebVOs != null && i < leavebVOs.length; i++) {
			leavebVOs[i].setIslactation(islactation);
			leavebVOs[i].setPk_psndoc(leavehVO.getPk_psndoc());
			leavebVOs[i].setPk_psnjob(leavehVO.getPk_psnjob());
			leavebVOs[i].setPk_group(leavehVO.getPk_group());
			leavebVOs[i].setPk_org(leavehVO.getPk_org());
		}
		
		// �ݼ����� �ݼ���ʱ������  xm:2018.11.22/start
		IDBCacheBS dbcf = (IDBCacheBS) NCLocator.getInstance().lookup(IDBCacheBS.class.getName());
		// ��ѯ: ��Ҫ�ж�������ͼ
		String selectSQL =  "select pk_timeitem from timeitem_vacationduration where pk_timeitem ='" + leavehVO.getPk_leavetype() + "';";
		ArrayList selectList = (ArrayList) dbcf.runSQLQuery(selectSQL, new ArrayListProcessor());
		
		if ( selectList != null && selectList.size() > 0 ) {
			 // check
			 LeaveDefRuleCheckUtil.checkAWAForLeave(aggLeaveVO);
			 //ResHelper.getString("6017leave", "06017leave0249")
		}
		  // �ݼ����� �ݼ���ʱ������  xm:2018.11.22/end

		// ����Ƿ������־
		String confirmFlag = getLifeCycleContext().getParameter("isContinue");
		if (StringUtils.isEmpty(confirmFlag)) {
			if (!Boolean.TRUE.equals(result)) {
				LfwView viewMain = AppLifeCycleContext.current()
						.getViewContext().getView();
				FormComp formComp = (FormComp) viewMain.getViewComponents()
						.getComponent(LeaveConsts.PAGE_FORM_LEAVEINFO);
				// У��
				ValidationFailure failur = new PFSaveLeaveValidator()
						.validate(aggVO);
				if (failur != null) {
					CommonUtil.showErrorDialog(
							ResHelper.getString("c_pub-res", "0c_pub-res0169"),
							failur.getMessage());
				}
				// �ݼ�������VO
				LeavehVO headVO = ((AggLeaveVO) aggVO).getLeavehVO();
				if (!islactation.booleanValue()) {
					// �ݼ���ʱ��
					UFDouble sumHour = headVO.getSumhour() == null ? UFDouble.ZERO_DBL
							: headVO.getSumhour();
					if (sumHour.compareTo(UFDouble.ZERO_DBL) <= 0) {
						if (formComp != null) {
							String errorMsg = nc.vo.ml.NCLangRes4VoTransl
									.getNCLangRes().getStrByID("c_ta-res",
											"0c_ta-res0072")/*
															 * @ res
															 * "�ݼ���ʱ��Ϊ0�����ܽ��б��������"
															 */;
							LfwValidateException exception = new LfwValidateException(
									errorMsg);
							exception.setViewId(viewMain.getId());
							exception.addComponentId(formComp.getId());
							throw exception;
						}
					}
				} else {
					if (formComp != null) {
						// ���ղ���ʱ��
						UFDouble lactationhour = headVO.getLactationhour() == null ? UFDouble.ZERO_DBL
								: headVO.getLactationhour();
						if (lactationhour.compareTo(UFDouble.ZERO_DBL) <= 0) {
							String errorMsg = nc.vo.ml.NCLangRes4VoTransl
									.getNCLangRes().getStrByID("c_ta-res",
											"0c_ta-res0141")/*
															 * @ res
															 * "���ղ���ʱ��С�ڵ���0�����ܽ��б��������"
															 */;
							LfwValidateException exception = new LfwValidateException(
									errorMsg);
							exception.setViewId(viewMain.getId());
							exception.addComponentId(formComp.getId());
							throw exception;
						}
					}

				}
			}
			// ����ǰ��У��һ�Σ�����е��ݳ�ͻ�������Ǳ������ģ�����ʾ��Щ��ͻ���ݣ���ѯ���û��Ƿ񱣴�
			Map<String, Map<Integer, ITimeScopeWithBillInfo[]>> checkMutextResult = null;
			try {
				// ����ǰ��У��һ�Σ�����е��ݳ�ͻ�������Ǳ������ģ�����ʾ��Щ��ͻ���ݣ���ѯ���û��Ƿ񱣴�
				checkMutextResult = getCheckResult(aggVO).getMutexCheckResult();
				if (checkMutextResult != null) {
					AwaySaveProcessor
							.showConflictInfoList(
									new BillMutexException(null,
											checkMutextResult),
									nc.vo.ml.NCLangRes4VoTransl.getNCLangRes()
											.getStrByID("c_ta-res",
													"0c_ta-res0008")/*
																	 * @ res
																	 * "�����е�����ʱ���ͻ���Ƿ񱣴�?"
																	 */,
									TaApplyConsts.DIALOG_CONFIRM);
					return false;
				}
			} catch (BillMutexException ex) {
				AwaySaveProcessor.showConflictInfoList(
						((BillMutexException) ex),
						nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID(
								"c_ta-res", "0c_ta-res0007")/*
															 * @ res
															 * "�����е�����ʱ���ͻ���������ܼ���"
															 */,
						TaApplyConsts.DIALOG_ALERT);
				return false;
			}
		}
		// ���Ƿ������־
		SplitBillResult<AggLeaveVO> splitResult = null;
		try {
			splitResult = getCheckResult(aggVO).getSplitResult();
		} catch (BusinessException ex) {
			new HrssException(ex).deal();
		}
		boolean flag = true;
		String isSplitBillContinue = getLifeCycleContext().getParameter(
				"isSplitBillContinue");
		if (StringUtils.isEmpty(isSplitBillContinue)
				&& !UFBoolean.TRUE.equals(islactation)) {
			// �ݼٲ�
			if (splitResult != null && splitResult.needQueryUser()) {
				AppUtil.addAppAttr("isContinue", confirmFlag);// ͬʱ�������ݳ�ͻ�Ͳ�ʱʹ��
				flag = showSplitInfoList(primaryKey, splitResult);
			}
		}
		if(!flag){
			return false;
		}
		AppUtil.addAppAttr("isContinue", null);
		AggLeaveVO[] newAggVOs = null;
		try {
			ILeaveApplyApproveManageMaintain service = ServiceLocator
					.lookup(ILeaveApplyApproveManageMaintain.class);
			LeavehVO newleaveVO = splitResult.getOriginalBill().getLeavehVO();
			LeavebVO[] newleaveVOs = splitResult.getOriginalBill().getLeavebVOs();
			//����ǲ���٣�������ʱ��add by lihld 2015-07-08
			if(newleaveVO.getIslactation().booleanValue()){
				splitResult.getOriginalBill().getLeavehVO().setSumhour(new UFDouble(0));
				for( int i=0;i<newleaveVOs.length;i++){
					splitResult.getOriginalBill().getLeavebVOs()[i].setLeavehour(new UFDouble(0));
				}
			}
			if (StringUtils.isEmpty(primaryKey)) {
				newAggVOs = service.insertData(splitResult);
			} else {
				newAggVOs = service.updateData(splitResult);
			}
		} catch (HrssException e) {
			new HrssException(e).alert();
		} catch (BusinessException e) {
			new HrssException(e).deal();
		}
		String temp_primaryKey = null;
		String temp_billCode = null;
		LeavehVO temp_leavehVO = null;
		AppUtil.addAppAttr("splitResult", splitResult.getSplitResult());
		if (!ArrayUtils.isEmpty(newAggVOs)) {
			for (AggLeaveVO newAggVO : newAggVOs) {
				temp_leavehVO = newAggVO.getLeavehVO();
				temp_billCode = temp_leavehVO.getBill_code();
				if (billCode.equals(temp_billCode)) {
					temp_primaryKey = temp_leavehVO.getPrimaryKey();
					break;
				}
			}
		}
		if (!StringUtils.isEmpty(temp_primaryKey)) {
			AppUtil.addAppAttr("App_newAggVO_PrimaryKey", temp_primaryKey);
		}

		return true;
	}

	/**
	 * ��ȡ����У��Ľ��
	 * 
	 * @param aggVO
	 * @return
	 * @throws HrssException
	 * @throws BusinessException
	 */
	private LeaveCheckResult<AggLeaveVO> getCheckResult(
			AggregatedValueObject aggVO) throws BusinessException {
		if (checkResult != null) {
			return checkResult;
		}
		try {
			ILeaveApplyQueryMaintain service = ServiceLocator
					.lookup(ILeaveApplyQueryMaintain.class);
			checkResult = service.checkWhenSave((AggLeaveVO) aggVO);
		} catch (HrssException e) {
			e.alert();
		}
		return checkResult;
	}

	/**
	 * ��ʾ�͵�ǰ�������ͻ�ĵ����б�
	 */
	public boolean showSplitInfoList(String primaryKey,
			SplitBillResult<AggLeaveVO> splitResult) {
		// �Ƿ�ִ���˲�
		boolean isSplit = splitResult.isSplit();
		// �Ƿ�ʱ������
		boolean isExceedLimit = splitResult.isExceedLimit();
		if (!isSplit && isExceedLimit) {// ���û�в�,��ʱ��������,��ֱ���ü򵥶Ի�����ʾ
			AggLeaveVO aggVO = ((AggLeaveVO[]) splitResult.getSplitResult())[0];
			UFDouble sumHour = aggVO.getHeadVO().getSumhour();
			UFDouble usefulHour = aggVO.getHeadVO().getUsefuldayorhour();
			usefulHour = usefulHour.setScale(getPointNum(),
					UFDouble.ROUND_HALF_UP);
			// ����ݼ����CopyVO
			TimeItemCopyVO leaveTypeCopyVO = getTimeItemCopyVO((AggLeaveVO) aggVO);

			if (leaveTypeCopyVO.getIsLeavelimit() != null
					&& leaveTypeCopyVO.getIsLeavelimit().booleanValue()) {// �����ݼ�ʱ��ʱУ��
				// �ݼ���ʱ�����ڿ���ʱ��
				if (sumHour.compareTo(usefulHour) > 0) {
					String errMsg = nc.vo.ml.NCLangRes4VoTransl.getNCLangRes()
							.getStrByID("c_ta-res", "0c_ta-res0073")/*
																	 * @ res
																	 * "�ݼ���ʱ��{0}���ڿ���ʱ��{1}�����ܽ��б��������"
																	 */;
					// �ϸ���� �ݼ�ʱ��
					if (leaveTypeCopyVO.getIsRestrictlimit() != null
							&& leaveTypeCopyVO.getIsRestrictlimit()
									.booleanValue()) {
						CommonUtil.showErrorDialog(ResHelper.getString(
								"c_pub-res", "0c_pub-res0169"), MessageFormat
								.format(errMsg, sumHour.toString(),
										usefulHour.toString()));
					}
					errMsg = MessageFormat
							.format(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes()
									.getStrByID("c_ta-res", "0c_ta-res0074")/*
																			 * @
																			 * res
																			 * "�ݼ���ʱ��{0}���ڿ���ʱ��{1}���Ƿ����������"
																			 */,
									sumHour.toString(), usefulHour.toString());
					// �� �ϸ���� �ݼ�ʱ��
					AppInteractionUtil
							.showConfirmDialog(
									DIALOG_SAVE,
									nc.vo.ml.NCLangRes4VoTransl.getNCLangRes()
											.getStrByID("c_ta-res",
													"0c_ta-res0075")/*
																	 * @ res
																	 * "ѯ��"
																	 */,
									errMsg, null);

				}
			} else {// �������ݼ�ʱ��ʱ�� ����ҪУ��
				try {
					ILeaveApplyApproveManageMaintain service = ServiceLocator
							.lookup(ILeaveApplyApproveManageMaintain.class);
					if (StringUtils.isEmpty(primaryKey)) {
						service.insertData(splitResult);
					} else {
						service.updateData(splitResult);
					}
				} catch (HrssException e) {
					new HrssException(e).alert();
				} catch (BusinessException e) {
					new HrssException(e).deal();
				}
			}

			return true;

		} else { // ����
			String title = null;
			if (isSplit && !isExceedLimit) {
				title = nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID(
						"c_ta-res", "0c_ta-res0076")/*
													 * @ res
													 * "����д���ݼ����뵥����ֳ����е���,�Ƿ񱣴�?"
													 */;
			} else {
				title = nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID(
						"c_ta-res", "0c_ta-res0077")/*
													 * @ res
													 * "����д���ݼ����뵥����ֳ����е���,�Ҵ��ڳ�������ʱ���ļ�¼(���û�ɫ���),�Ƿ񱣴�?"
													 */;
			}
			LeaveCommonVO[] splitVOs = null;
			AggLeaveVO[] aggVOs = (AggLeaveVO[]) splitResult.getSplitResult();
			List<LeavebVO> list = new ArrayList<LeavebVO>();
			for (AggLeaveVO aggVO : aggVOs) {
				LeavebVO[] bvos = aggVO.getBodyVOs();
				if (!ArrayUtils.isEmpty(bvos))
					for (LeavebVO bvo : bvos) {
						if (bvo.getStatus() != VOStatus.DELETED)
							list.add(bvo);
					}
			}
			splitVOs = list.toArray(new LeavebVO[0]);
			ApplicationContext appCxt = AppLifeCycleContext.current()
					.getApplicationContext();
			appCxt.addAppAttribute(APP_ATTR_SPLITBILL_DATAS, splitVOs);
			// ����ģ̬�����URL
			String windowId = "SplitResultInfo";
			// ���ù���ģ�鴰����ʾ����
			CommonUtil.showWindowDialog(windowId, title, "900", "400", null,
					ApplicationContext.TYPE_DIALOG);
			return false;
		}
	}

	/**
	 * ��ÿ���λ��
	 * 
	 * @return
	 */
	private int getPointNum() {
		TimeRuleVO timeRuleVO = TaAppContextUtil.getTimeRuleVO();
		if (timeRuleVO == null) {
			// û�п��ڹ�������������Ĭ��ֵ
			return TaListBasePageMode.DEFAULT_PRECISION;
		}
		int pointNum = Math.abs(timeRuleVO.getTimedecimal());
		return pointNum;
	}

	/**
	 * �������
	 * 
	 * @param aggVo
	 * @return
	 */
	@Override
	public AggregatedValueObject onVOSave(AggregatedValueObject aggVO) {
		String billPk = (String) AppUtil.getAppAttr("App_newAggVO_PrimaryKey");
		AggregatedValueObject newAggVO = null;
		if (!StringUtils.isEmpty(billPk)) {
			newAggVO = getBillVOByPk(billPk);
			AppUtil.addAppAttr("App_newAggVO_PrimaryKey", null);
		}
		
		// HR���ػ������ݼ����뱣���Ҫ������Ū��ȥ by Ethan Wu start
		if (aggVO.getParentVO() instanceof LeavehVO) {
			try {
				
				IFileSystemService service = NCLocator.getInstance().lookup(IFileSystemService.class);
				String newLeavePk = aggVO.getParentVO().getPrimaryKey();
				service.copyTo("hrtaleave" + ((LeavehVO)aggVO.getParentVO()).getPk_psndoc(), newLeavePk, FSOption.WHEN_DEST_EXIST_OVERWRITE, false);
				service.deleteNCFileNode("hrtaleave" + ((LeavehVO)aggVO.getParentVO()).getPk_psndoc());
			} catch (Exception e) {
				new HrssException(e).deal();
			}
		}
		// HR���ػ������ݼ����뱣���Ҫ������Ū��ȥ by Ethan Wu end
		return newAggVO;
	}

	/**
	 * ��������,��ѯVO
	 * 
	 * @param primaryKey
	 * @return
	 */
	protected AggLeaveVO getBillVOByPk(String primaryKey) {
		if (StringUtils.isEmpty(primaryKey)) {
			return null;
		}
		IMDPersistenceQueryService service = MDPersistenceService
				.lookupPersistenceQueryService();
		// ��ѯ����VO
		AggLeaveVO billVO = null;
		try {
			billVO = (AggLeaveVO) service.queryBillOfVOByPK(AggLeaveVO.class,
					primaryKey, false);
		} catch (MetaDataException e) {
			new HrssException(e).deal();
		}
		return billVO;
	}

	/**
	 * ��������
	 */
	@Override
	public AggregatedValueObject onAfterVOSave(Dataset ds, Dataset[] dsDetails,
			AggregatedValueObject aggVO) {
		// �رյ���ҳ��
		CmdInvoker.invoke(new CloseWindowCmd());
		ApplicationContext applicationContext = getLifeCycleContext()
				.getApplicationContext();
		applicationContext.closeWinDialog("LeaveApply");
		// ִ������ݲ�ѯ
		CmdInvoker.invoke(new UifPlugoutCmd(HrssConsts.PAGE_MAIN_WIDGET,
				"closewindow"));
		return null;
	}

	/**
	 * ����ݼ����Copy��VO
	 * 
	 * @param aggVO
	 * @return
	 */
	private TimeItemCopyVO getTimeItemCopyVO(AggLeaveVO aggVO) {
		ITimeItemQueryService itemService = NCLocator.getInstance().lookup(
				ITimeItemQueryService.class);
		TimeItemCopyVO typeVO = null;
		try {
			typeVO = itemService.queryCopyTypesByDefPK(aggVO.getHeadVO()
					.getPk_org(), aggVO.getHeadVO().getPk_leavetype(),
					TimeItemCopyVO.LEAVE_TYPE);
		} catch (BusinessException e) {
			Logger.error(e.getMessage(), e);
			throw new LfwRuntimeException(e.getMessage(), e);
		}
		return typeVO;
	}

	private AppLifeCycleContext getLifeCycleContext() {
		return AppLifeCycleContext.current();
	}

}