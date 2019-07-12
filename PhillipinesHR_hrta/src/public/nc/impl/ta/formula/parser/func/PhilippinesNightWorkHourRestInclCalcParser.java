package nc.impl.ta.formula.parser.func;

import nc.impl.hr.formula.parser.AbstractParaFuncParser;
import nc.vo.pub.BusinessException;

public class PhilippinesNightWorkHourRestInclCalcParser extends AbstractParaFuncParser {
	
	/**
	 * 此函数为新加坡GB2BC专项补丁 如有其他需求再复用
	 */
	private static final long serialVersionUID = 6840868362898993876L;
	
	public PhilippinesNightWorkHourRestInclCalcParser() {
		this.setFuncName("getNightHourRestIncl");
	}

	@Override
	protected String translateFunc2SQL(String pk_org, String formula,
			String funcStr, String[] funcArgs, Object... params)
			throws BusinessException {
		// TODO Auto-generated method stub
		funcArgs = new String[] { "tbm_daystat.calendar" };
		String date = funcArgs[0];
		StringBuilder sb = new StringBuilder();
		sb.append(" (select sum((isnull(timedata.nightlength_cur, 0) + isnull(prev_timedata.nightlength_next, 0)) / 3600.0 ");
		sb.append(" + iif(timedata.tbmstatus like ('%Absent%') or timedata.tbmstatus like ('%Leave%') or timedata.tbmstatus like ('%Trip%'),0.0 ,curNightRegular.CurNightRegularHour) ");
		sb.append(" + iif(prev_timedata.tbmstatus like ('%Absent%') or prev_timedata.tbmstatus like ('%Leave%') or prev_timedata.tbmstatus like ('%Trip%'),0.0 ,prevNightRegular.PrevNightRegularHour)) ");
		sb.append(" actualWorkLen from tbm_psncalendar psncalendar ");
		sb.append(" left join tbm_psncalendar prev_psncalendar on prev_psncalendar.pk_psndoc = psncalendar.pk_psndoc and dateadd(day,1,prev_psncalendar.calendar) = psncalendar.calendar ");
		sb.append(" left join bd_shift workshift on psncalendar.pk_shift = workshift.pk_shift left join bd_shift prev_workshift on prev_psncalendar.pk_shift = prev_workshift.pk_shift ");
		sb.append(" left join (select sum(iif(curDayRT.beginday = 0 and curDayRT.begintime < '06:00:00' ");
		sb.append(" , datediff(SECOND,curDayRT.begintime, iif(curDayRT.endtime < '06:00:00', curDayRT.endtime, '06:00:00')) ");
		sb.append(" , iif(curDayRT.beginday = 0 and (curDayRT.endtime > '22:00:00' or curDayRT.endday = 1) ");
		sb.append(" , datediff(SECOND, iif(curDayRT.begintime > '22:00:00', concat('1900-01-01 ',curDayRT.begintime), '1900-01-01 22:00:00'), iif(curDayRT.endday = 1, '1900-01-02 00:00:00', concat('1900-01-01 ',curDayRT.endtime))),0))/3600.0) CurNightRegularHour, curDayShift.pk_shift ");
		sb.append(" from bd_shift curDayShift left join bd_rt curDayRT on curDayShift.pk_shift = curDayRT.pk_shift group by curDayShift.pk_shift ");
		sb.append(" ) curNightRegular on curNightRegular.pk_shift = workshift.pk_shift left join (select sum(iif(prevDayRT.endday = 1 and prevDayRT.beginday = 0 ");
		sb.append(" , datediff(SECOND, '00:00:00', iif(prevDayRT.endtime < '06:00:00', prevDayRT.endtime, '06:00:00')), iif(prevDayRT.endday = 1 and prevDayRT.begintime < '06:00:00' ");
		sb.append(" , datediff(SECOND, prevDayRT.begintime, iif(prevDayRT.endtime < '06:00:00', prevDayRT.endtime, '06:00:00')),0))/3600.0) PrevNightRegularHour, prevDayShift.pk_shift ");
		sb.append(" from bd_shift prevDayShift left join bd_rt prevDayRT on prevDayRT.pk_shift = prevDayShift.pk_shift group by prevDayShift.pk_shift ");
		sb.append(" ) prevNightRegular on prevNightRegular.pk_shift = prev_workshift.pk_shift ");
		sb.append(" left join tbm_timedata timedata on timedata.calendar = psncalendar.calendar and timedata.pk_psndoc = psncalendar.pk_psndoc ");
		sb.append(" left join tbm_timedata prev_timedata on prev_timedata.pk_psndoc = psncalendar.pk_psndoc and psncalendar.calendar = dateadd(day,1,prev_timedata.calendar) ");
		sb.append(" where psncalendar.calendar = " + date + " and psncalendar.pk_psndoc = tbm_daystat.pk_psndoc) ");
		return sb.toString();
	}
}
