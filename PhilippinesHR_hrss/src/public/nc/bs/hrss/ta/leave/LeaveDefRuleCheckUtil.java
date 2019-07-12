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
 * �ݼ��Զ�������鹤����
 * 
 * @author xm
 */
public class LeaveDefRuleCheckUtil {
	
	/**
	 * �ݼٵǼ� - �����ݼٿ��ƣ�AWA��
	 * @throws BusinessException
	 */
	public static void checkAWAForLeaveReg(LeaveRegVO vo) throws BusinessException {
		
		UFLiteralDate leaveBeginDate = vo.getLeavebegindate();
		UFLiteralDate leaveEndDate = vo.getLeaveenddate();			
		
		long rowDifference = (( leaveEndDate.getMillis() - leaveBeginDate.getMillis()) / 1000 / 60 / 60 / 24);
		
		// 2019-07-10 �ͻ������ͬһ����У��ȥ��  Ethan Wu start
		// �жϣ��Ƿ�ͬһ����
//		if ( leaveBeginDate.getMonth() != leaveEndDate.getMonth() ) {
//			// �ݼ��ڼ������ͬһ����
//			throw new BusinessException("Vacation must be in the same month.");
//		}
		// 2019-07-10 �ͻ������ͬһ����У��ȥ��  Ethan Wu end
		
		// �жϵ��У��ݼٽ������ڱ�����ڿ�ʼ���ڼ�7����Ȼ��
		if (rowDifference != 6) {
			// �ݼٽ������ڱ�����ڿ�ʼ���ڼ�7����Ȼ��
			throw new BusinessException("Must be taken continuously for 7 days.");
		}
		// ��ѯ���Ѿ���д���Ѿ����浽���ݿ⣩�ĵ���(���һ��)�Ŀ�ʼ�ͽ�������
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
       		 // �жϣ����� �Ƿ������ύ, �жϼ��, �·��Ƿ���ڱ���
//       		 if ( leaveBeginDate.getYear() == lastBeginDate.getYear() && leaveBeginDate.getMonth() == lastBeginDate.getMonth() ) {
//       			 // �����ѵǼǹ��������ٵǼ�
//       			 throw new BusinessException("You have applied for leave in this month, cannot apply again.");
//       		 } else 
       			 
       		 if (  leaveBeginDate.getYear() == lastBeginDate.getYear() && leaveBeginDate.getMonth() > lastBeginDate.getMonth() && rowsDifference < 8 ) {
       			 // �� > ���¿�ʼʱ��Ͳ�ѯ�½���ʱ�����ж�
       			throw new BusinessException("The minimum interval is 7 days between End Date of previous leave and Start Date of new leave.");
       		 } else if (  leaveBeginDate.getYear() == lastBeginDate.getYear() && leaveBeginDate.getMonth() < lastBeginDate.getMonth() && rowsDifference2 < 8 ) {
       			 // �� > ���½���ʱ��Ͳ�ѯ�¿�ʼʱ�����ж�
       			throw new BusinessException("The minimum interval is 7 days between End Date of previous leave and Start Date of new leave.");
       		 } else if ( leaveBeginDate.getYear() != lastBeginDate.getYear() && rowsDifference < 8 ) {
       			 // ����
       			throw new BusinessException("The minimum interval is 7 days between End Date of previous leave and Start Date of new leave.");
       		 }
       	 } 		
	}
	
	/**
	 * �ݼ����� - �����ݼٿ���(AWA)
	 * @throws BusinessException 
	 */
	public static void checkAWAForLeave(AggLeaveVO aggVO) throws BusinessException {
		// ��ѯ
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
			// �жϣ��Ƿ�ͬһ����
			// 2019-07-10 �ͻ������ͬһ����У��ȥ��  Ethan Wu start
//			if (leaveBeginDate.getMonth() != leaveEndDate.getMonth()) {
//				throw new BusinessException(
//						"Vacation must be in the same month."); // �ݼ��ڼ������ͬһ����
//
//			}
			// 2019-07-10 �ͻ������ͬһ����У��ȥ��  Ethan Wu end
			// �жϵ��У���������-��ʼ���� = 6
			if (rowDifference != 6) {
				// ��x����ϸ���������ݼ�7��
				throw new BusinessException("Line " + (i + 1)
						+ " details must be taken continuously for 7 days.");
			}

			// �ж϶���( ��һ����д��δ���浽���ݿ� )�� ��ʼ���ڱ��������ϴ��ݼٽ������ڵ�7����Ȼ��
			if (i > 0 && leavebVO.getPk_leaveb() == null) {
				// ���в���δ����
				// ��ͬ�·��жϼ��
				LeavebVO lastLeavebVO = aggVO.getLeavebVOs()[(i - 1)];
				UFLiteralDate lastBeginDate = lastLeavebVO.getLeavebegindate();
				UFLiteralDate lastEndDate = lastLeavebVO.getLeaveenddate();

				long rowsDifference = ((leaveBeginDate.getMillis() - lastEndDate
						.getMillis()) / 1000 / 60 / 60 / 24);
				long rowsDifference2 = ((lastBeginDate.getMillis() - leaveEndDate
						.getMillis()) / 1000 / 60 / 60 / 24);
				// �жϣ����� �Ƿ������ύ, �жϼ��, �·��Ƿ���ڱ��� // �м�������7�� (��ʼ���� - ��һ�ν������� ) >
				// 8
//				if (leaveBeginDate.getMonth() == lastBeginDate.getMonth()) {
//					// "������ϸ������������ݼ٣�����������"
//					throw new BusinessException(
//							"The "
//									+ (i + 1)
//									+ " line: You have applied for leave in this month, cannot apply again.");
//				} else 
					
				if (leaveBeginDate.after(lastBeginDate)
						&& rowsDifference < 8) {
					// �� > ���¿�ʼʱ��Ͳ�ѯ�½���ʱ�����ж�
					// ��(i+1)����ϸ��ʼ���ڱ�������һ���ݼٽ������ڼ��7����Ȼ��
					throw new BusinessException(
							"The "
									+ (i + 1)
									+ " line: The minimum interval is 7 days between End Date of previous leave and Start Date of new leave.");
				} else if (leaveBeginDate.before(lastBeginDate)
						&& rowsDifference2 < 8) {
					// �� > ���½���ʱ��Ͳ�ѯ�¿�ʼʱ�����ж�
					// ��(i+1)����ϸ�������ڱ�������һ���ݼٿ�ʼ���ڼ��7����Ȼ��
					throw new BusinessException(
							"The "
									+ (i + 1)
									+ " line: The minimum interval is 7 days between Start Date of previous leave and End Date of new leave.");
				}

			}
			// ��ѯ���Ѿ���д���Ѿ����浽���ݿ⣩�ĵ���(���һ��)�Ŀ�ʼ�ͽ�������
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

				// �жϣ����� �Ƿ������ύ, �жϼ��, �·��Ƿ���ڱ���
//				if (leaveBeginDate.getYear() == lastBeginDate.getYear()
//						&& leaveBeginDate.getMonth() == lastBeginDate
//								.getMonth()) {
//					// "��" + (i+1) + "����ϸ������������ݼ٣�����������"
//					throw new BusinessException(
//							"The "
//									+ (i + 1)
//									+ " line: You have applied for leave in this month, cannot apply again.");
//				} else 
					
				if (leaveBeginDate.after(lastBeginDate)
						&& rowsDifference < 8) {
					// �� > ���¿�ʼʱ��Ͳ�ѯ�½���ʱ�����ж�
					// "��" + (i+1) + "����ϸ��ʼ���ڱ���������һ���ݼٽ�������7����Ȼ��"
					throw new BusinessException(
							"The "
									+ (i + 1)
									+ " line: The minimum interval is 7 days between End Date of previous leave and Start Date of new leave.");
				} else if (leaveBeginDate.before(lastBeginDate)
						&& rowsDifference2 < 8) {
					// �� > ���½���ʱ��Ͳ�ѯ�¿�ʼʱ�����ж�
					// "��" + (i+1) + "����ϸ��ʼ���ڱ���������һ���ݼ������������7����Ȼ��"
					throw new BusinessException(
							"The "
									+ (i + 1)
									+ " line: The minimum interval is 7 days between End Date of previous leave and Start Date of new leave.");
				} 

			}

		}
	}

}