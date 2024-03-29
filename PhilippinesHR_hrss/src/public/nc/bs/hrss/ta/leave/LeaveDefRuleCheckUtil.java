package nc.bs.hrss.ta.leave;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import nc.bs.dbcache.intf.IDBCacheBS;
import nc.bs.framework.common.NCLocator;
import nc.jdbc.framework.processor.ArrayListProcessor;
import nc.uap.oba.docx4j.model.datastorage.XPathEnhancerParser.main_return;
import nc.vo.pub.BusinessException;
import nc.vo.pub.lang.UFBoolean;
import nc.vo.pub.lang.UFDate;
import nc.vo.pub.lang.UFDouble;
import nc.vo.pub.lang.UFLiteralDate;
import nc.vo.ta.leave.AggLeaveVO;
import nc.vo.ta.leave.LeaveRegVO;
import nc.vo.ta.leave.LeavebVO;
import nc.vo.ta.leave.LeavehVO;

/**
 * 休假自定义规则检查工具类
 * 
 * @author xm
 */
public class LeaveDefRuleCheckUtil {
	
	/**
	 * 休假登记 - 连续休假控制（AWA）
	 * @throws BusinessException
	 */
	public static void checkAWAForLeaveReg(LeaveRegVO vo) throws BusinessException {
		
		UFLiteralDate leaveBeginDate = vo.getLeavebegindate();
		UFLiteralDate leaveEndDate = vo.getLeaveenddate();			
		
		long rowDifference = (( leaveEndDate.getMillis() - leaveBeginDate.getMillis()) / 1000 / 60 / 60 / 24);
		
		// 2019-07-10 客户变更：同一个月校验去掉  Ethan Wu start
		// 判断：是否同一个月
//		if ( leaveBeginDate.getMonth() != leaveEndDate.getMonth() ) {
//			// 休假期间必须在同一个月
//			throw new BusinessException("Vacation must be in the same month.");
//		}
		// 2019-07-10 客户变更：同一个月校验去掉  Ethan Wu end
		
		// 判断单行：休假结束日期必须等于开始日期加7个自然日
		if (rowDifference != 6) {
			// 休假结束日期必须等于开始日期加7个自然日
			throw new BusinessException("Must be taken continuously for 7 days.");
		}
		// 查询：已经填写（已经保存到数据库）的单据(最后一条)的开始和结束日期
   		 String leaveregPK =  vo.getPk_leavereg() == null ? "" : (" and pk_leavereg !='"+ vo.getPk_leavereg() + "'");
   		 IDBCacheBS dbcf = (IDBCacheBS) NCLocator.getInstance().lookup(IDBCacheBS.class.getName());	 
   		 
   		 String selectSQL = "select leavebegindate,leaveenddate from tbm_leavereg where pk_psndoc='" + vo.getPk_psndoc() + "'"
					 + leaveregPK
					 + " order by leaveendtime desc";
   		 
   		 ArrayList selectList = (ArrayList) dbcf.runSQLQuery(selectSQL, new ArrayListProcessor());
       	 
       	 if (selectList.size() > 0 ) {
       		 Object[] values = (Object[]) selectList.get(0);
       		 UFLiteralDate lastBeginDate = new UFLiteralDate(values[0].toString());
       		 UFLiteralDate lastEndDate = new UFLiteralDate(values[1].toString());
       		 
       		 long rowsDifference = ((leaveBeginDate.getMillis() - lastEndDate.getMillis())  / 1000 / 60 / 60 / 24);
       		 // 
       		 long rowsDifference2 = ((lastBeginDate.getMillis() - leaveEndDate.getMillis())  / 1000 / 60 / 60 / 24);
       		 // 判断：本月 是否已有提交, 判断间隔, 月份是否大于本月
//       		 if ( leaveBeginDate.getYear() == lastBeginDate.getYear() && leaveBeginDate.getMonth() == lastBeginDate.getMonth() ) {
//       			 // 本月已登记过，不能再登记
//       			 throw new BusinessException("You have applied for leave in this month, cannot apply again.");
//       		 } else 
       			 
       		 if (  leaveBeginDate.getYear() == lastBeginDate.getYear() && leaveBeginDate.getMonth() > lastBeginDate.getMonth() && rowsDifference < 8 ) {
       			 // 否 > 本月开始时间和查询月结束时间做判断
       			throw new BusinessException("The minimum interval is 7 days between End Date of previous leave and Start Date of new leave.");
       		 } else if (  leaveBeginDate.getYear() == lastBeginDate.getYear() && leaveBeginDate.getMonth() < lastBeginDate.getMonth() && rowsDifference2 < 8 ) {
       			 // 是 > 本月结束时间和查询月开始时间做判断
       			throw new BusinessException("The minimum interval is 7 days between End Date of previous leave and Start Date of new leave.");
       		 } else if ( leaveBeginDate.getYear() != lastBeginDate.getYear() && rowsDifference < 8 ) {
       			 // 跨年
       			throw new BusinessException("The minimum interval is 7 days between End Date of previous leave and Start Date of new leave.");
       		 }
       	 } 		
	}
	
	/**
	 * 休假申请 - 连续休假控制(AWA)
	 * @throws BusinessException 
	 */
	public static void checkAWAForLeave(AggLeaveVO aggVO) throws BusinessException {
		// 查询
		IDBCacheBS dbcf = (IDBCacheBS) NCLocator.getInstance().lookup(IDBCacheBS.class.getName());
		//String selectSQL = "select * from v_tbm_timeitem_forfile where pk_timeitem='"+ leaveTypePK + "';";
		//ArrayList selectList = (ArrayList) dbcf.runSQLQuery(selectSQL,new ArrayListProcessor());
		LeavehVO headVO = aggVO.getLeavehVO();
		
		for (int i = 0; (aggVO.getLeavebVOs() != null)
				&& (i < aggVO.getLeavebVOs().length); i++) {
			LeavebVO leavebVO = aggVO.getLeavebVOs()[i];
			UFLiteralDate leaveBeginDate = leavebVO.getLeavebegindate();
			UFLiteralDate leaveEndDate = leavebVO.getLeaveenddate();

			long rowDifference = ((leaveEndDate.getMillis() - leaveBeginDate
					.getMillis()) / 1000 / 60 / 60 / 24);
			// 判断：是否同一个月
			// 2019-07-10 客户变更：同一个月校验去掉  Ethan Wu start
//			if (leaveBeginDate.getMonth() != leaveEndDate.getMonth()) {
//				throw new BusinessException(
//						"Vacation must be in the same month."); // 休假期间必须在同一个月
//
//			}
			// 2019-07-10 客户变更：同一个月校验去掉  Ethan Wu end
			// 判断单行：结束日期-开始日期 = 6
			if (rowDifference != 6) {
				// 第x行明细必须连续休假7天
				throw new BusinessException("Line " + (i + 1)
						+ " details must be taken continuously for 7 days.");
			}

			// 判断多行( 第一次填写，未保存到数据库 )： 开始日期必须晚于上次休假结束日期的7天自然日
			if (i > 0 && leavebVO.getPk_leaveb() == null) {
				// 多行并且未保存
				// 不同月份判断间隔
				LeavebVO lastLeavebVO = aggVO.getLeavebVOs()[(i - 1)];
				UFLiteralDate lastBeginDate = lastLeavebVO.getLeavebegindate();
				UFLiteralDate lastEndDate = lastLeavebVO.getLeaveenddate();

				long rowsDifference = ((leaveBeginDate.getMillis() - lastEndDate
						.getMillis()) / 1000 / 60 / 60 / 24);
				long rowsDifference2 = ((lastBeginDate.getMillis() - leaveEndDate
						.getMillis()) / 1000 / 60 / 60 / 24);
				// 判断：本月 是否已有提交, 判断间隔, 月份是否大于本月 // 中间间隔大于7天 (开始日期 - 上一次结束日期 ) >
				// 8
//				if (leaveBeginDate.getMonth() == lastBeginDate.getMonth()) {
//					// "第行明细本月已申请过休假，不能再申请"
//					throw new BusinessException(
//							"The "
//									+ (i + 1)
//									+ " line: You have applied for leave in this month, cannot apply again.");
//				} else 
					
				if (leaveBeginDate.after(lastBeginDate)
						&& rowsDifference < 8) {
					// 否 > 本月开始时间和查询月结束时间做判断
					// 第(i+1)行明细开始日期必须与上一次休假结束日期间隔7个自然日
					throw new BusinessException(
							"The "
									+ (i + 1)
									+ " line: The minimum interval is 7 days between End Date of previous leave and Start Date of new leave.");
				} else if (leaveBeginDate.before(lastBeginDate)
						&& rowsDifference2 < 8) {
					// 是 > 本月结束时间和查询月开始时间做判断
					// 第(i+1)行明细结束日期必须与上一次休假开始日期间隔7个自然日
					throw new BusinessException(
							"The "
									+ (i + 1)
									+ " line: The minimum interval is 7 days between Start Date of previous leave and End Date of new leave.");
				}

			}
			// 查询：已经填写（已经保存到数据库）的单据(最后一条)的开始和结束日期
			String leavebPK = leavebVO.getPk_leaveb() == null ? ""
					: (" and b.pk_leaveb !='" + leavebVO.getPk_leaveb() + "'");

			String selectSQL = "select  b.leavebegindate,b.leaveenddate from tbm_leaveb b inner join tbm_leaveh h on h.pk_leaveh  = b.pk_leaveh"
					+ " where  h.pk_psndoc ='"
					+ headVO.getPk_psndoc()
					+ "' and h.pk_leavetype ='"
					+ headVO.getPk_leavetype()
					+ "'" + leavebPK + " order by b.leaveendtime desc";

			ArrayList selectList = (ArrayList) dbcf.runSQLQuery(selectSQL,
					new ArrayListProcessor());

			if (selectList.size() > 0) {
				Object[] values = (Object[]) selectList.get(0);
				UFLiteralDate lastBeginDate = new UFLiteralDate(
						values[0].toString());
				UFLiteralDate lastEndDate = new UFLiteralDate(
						values[1].toString());
				long rowsDifference = ((leaveBeginDate.getMillis() - lastEndDate
						.getMillis()) / 1000 / 60 / 60 / 24);
				long rowsDifference2 = ((lastBeginDate.getMillis() - leaveEndDate
						.getMillis()) / 1000 / 60 / 60 / 24);

				// 判断：本月 是否已有提交, 判断间隔, 月份是否大于本月
//				if (leaveBeginDate.getYear() == lastBeginDate.getYear()
//						&& leaveBeginDate.getMonth() == lastBeginDate
//								.getMonth()) {
//					// "第" + (i+1) + "行明细本月已申请过休假，不能再申请"
//					throw new BusinessException(
//							"The "
//									+ (i + 1)
//									+ " line: You have applied for leave in this month, cannot apply again.");
//				} else 
					
				if (leaveBeginDate.after(lastBeginDate)
						&& rowsDifference < 8) {
					// 否 > 本月开始时间和查询月结束时间做判断
					// "第" + (i+1) + "行明细开始日期必须晚于上一次休假结束日期7个自然日"
					throw new BusinessException(
							"The "
									+ (i + 1)
									+ " line: The minimum interval is 7 days between End Date of previous leave and Start Date of new leave.");
				} else if (leaveBeginDate.before(lastBeginDate)
						&& rowsDifference2 < 8) {
					// 是 > 本月结束时间和查询月开始时间做判断
					// "第" + (i+1) + "行明细开始日期必须早于上一次休假申请结束日期7个自然日"
					throw new BusinessException(
							"The "
									+ (i + 1)
									+ " line: The minimum interval is 7 days between End Date of previous leave and Start Date of new leave.");
				} 

			}

		}
	}

}