package nc.bs.hrss.ta.leaveoff.prcss;

import nc.bs.hrss.ta.common.prcss.TaBaseAddProcessor;
import nc.bs.hrss.ta.leaveoff.ctrl.LeaveRegListView;
import nc.uap.lfw.core.ctx.AppLifeCycleContext;
import nc.uap.lfw.core.data.Dataset;
import nc.uap.lfw.core.data.Row;
import nc.vo.pub.lang.UFDateTime;
import nc.vo.pub.lang.UFDouble;
import nc.vo.pub.lang.UFLiteralDate;
import nc.vo.ta.leave.LeaveRegVO;
import nc.vo.ta.leaveoff.LeaveoffVO;
import uap.web.bd.pub.AppUtil;

/**
 * 销假的新增操作
 * @author qiaoxp
 *
 */
public class LeaveOffAddProcessor extends TaBaseAddProcessor {

	@Override
	public void onBeforeRowAdd(Dataset ds, Row row, String billTypeCode) {
		super.onBeforeRowAdd(ds, row, billTypeCode);
		
		String pk_leavereg = (String)AppUtil.getAppAttr(LeaveRegListView.APP_ID_PK_LEAVEREG);
		row.setString(ds.nameToIndex(LeaveoffVO.PK_LEAVEREG), pk_leavereg);
		
		String pk_leavetype = (String)AppUtil.getAppAttr(LeaveRegListView.APP_ID_PK_LEAVETYPE);
		row.setString(ds.nameToIndex(LeaveoffVO.PK_LEAVETYPE), pk_leavetype);
		
		String pk_leavetypecopy = (String)AppUtil.getAppAttr(LeaveRegListView.APP_ID_PK_LEAVETYPECOPY);
		row.setString(ds.nameToIndex(LeaveoffVO.PK_LEAVETYPECOPY), pk_leavetypecopy);
		
		UFDouble leavehour = (UFDouble) AppUtil.getAppAttr(LeaveRegVO.LEAVEHOUR);
		row.setValue(ds.nameToIndex(LeaveoffVO.REGLEAVEHOURCOPY), leavehour);
		
		UFLiteralDate leavebegindate = (UFLiteralDate) AppUtil.getAppAttr(LeaveRegVO.LEAVEBEGINDATE);
		UFLiteralDate leaveenddate = (UFLiteralDate) AppUtil.getAppAttr(LeaveRegVO.LEAVEENDDATE);
		UFDateTime  leavebegintime = (UFDateTime) AppUtil.getAppAttr(LeaveRegVO.LEAVEBEGINTIME);
		UFDateTime  leaveendtime = (UFDateTime) AppUtil.getAppAttr(LeaveRegVO.LEAVEENDTIME);
		
		// 设置销假默认数据
		if(leavebegindate == null && leavebegintime != null){
			leavebegindate = new UFLiteralDate(leavebegintime.getDate().toDate());
		}
		if(leaveenddate == null && leaveendtime != null){
			leaveenddate = new UFLiteralDate(leaveendtime.getDate().toDate());
		}
		if(leavebegindate != null && leavebegintime == null){
			leavebegintime = new UFDateTime(leavebegindate.toDate());
		}
		if(leaveenddate != null && leaveendtime == null){
			leaveendtime = new UFDateTime(leaveenddate.toDate());
		}
		
		row.setValue(ds.nameToIndex(LeaveoffVO.REGBEGINDATECOPY), leavebegindate);
		row.setValue(ds.nameToIndex(LeaveoffVO.REGENDDATECOPY), leaveenddate);
		row.setValue(ds.nameToIndex(LeaveoffVO.REGBEGINTIMECOPY), leavebegintime);
		row.setValue(ds.nameToIndex(LeaveoffVO.REGENDTIMECOPY), leaveendtime);
		
		row.setValue(ds.nameToIndex(LeaveoffVO.LEAVEBEGINDATE), leavebegindate);
		row.setValue(ds.nameToIndex(LeaveoffVO.LEAVEENDDATE), leavebegindate);
		row.setValue(ds.nameToIndex(LeaveoffVO.LEAVEBEGINTIME), leavebegintime);
		row.setValue(ds.nameToIndex(LeaveoffVO.LEAVEENDTIME), leavebegintime);
		// 真实休假长度改为0 差异值改为时长的负数
		row.setValue(ds.nameToIndex(LeaveoffVO.REALLYLEAVEHOUR), 0);
		row.setValue(ds.nameToIndex(LeaveoffVO.DIFFERENCEHOUR), new UFDouble(leavehour.multiply(-1)));
		
		AppLifeCycleContext.current().getApplicationContext().removeAppAttribute(LeaveRegVO.LEAVEBEGINDATE);
		AppLifeCycleContext.current().getApplicationContext().removeAppAttribute(LeaveRegVO.LEAVEENDDATE);
		AppLifeCycleContext.current().getApplicationContext().removeAppAttribute(LeaveRegVO.LEAVEBEGINTIME);
		AppLifeCycleContext.current().getApplicationContext().removeAppAttribute(LeaveRegVO.LEAVEENDTIME);
	}

	@Override
	public void onAfterRowAdd(Dataset ds, Row row) {		
	}

}
