package nc.impl.ta.formula.parser.func;

import nc.impl.hr.formula.parser.AbstractParaFuncParser;
import nc.vo.pub.BusinessException;

public class PhilippinesDayOTOver8 extends AbstractParaFuncParser {

	/**
	 * 
	 */
	public PhilippinesDayOTOver8() {
		this.setFuncName("getDaytimeexd8");
	}
	
	private static final long serialVersionUID = 2376843159206230971L;

	@Override
	protected String translateFunc2SQL(String pk_org, String formula,
			String funcStr, String[] funcArgs, Object... params)
			throws BusinessException {
		funcArgs = new String[] { "tbm_daystat.calendar" };
		String date = funcArgs[0];
		StringBuilder sb = new StringBuilder();
		
		sb.append(" (select sum(b.a) from (select (iif (timedata.overtimehour > 8, (datediff(SECOND ");
		sb.append(" , iif(dateadd(HOUR,8,timedata.overtimebegintime) > concat(psncalendar.calendar,' ','06:00:00') ");
		sb.append(" , iif(dateadd(HOUR,8,timedata.overtimebegintime) < concat(psncalendar.calendar,' ','22:00:00') ");
		sb.append(" , dateadd(HOUR,8,timedata.overtimebegintime), concat(psncalendar.calendar,' ','22:00:00')), concat(psncalendar.calendar,' ','06:00:00')) ");
		sb.append(" , iif(timedata.overtimeendtime < concat(psncalendar.calendar, ' ','22:00:00'), iif(timedata.overtimeendtime > concat(psncalendar.calendar, ' ','06:00:00') ");
		sb.append(" , timedata.overtimeendtime,concat(psncalendar.calendar, ' ','06:00:00')), concat(psncalendar.calendar, ' ','22:00:00'))) / 3600.0 ), 0)) a, psncalendar.calendar, psncalendar.pk_psndoc ");
		sb.append(" from tbm_psncalendar psncalendar left join bd_shift workshift on psncalendar.pk_shift = workshift.pk_shift ");
		sb.append(" left join tbm_overtimereg timedata on timedata.overtimebegindate = psncalendar.calendar and timedata.pk_psndoc = psncalendar.pk_psndoc ");
		// 傻逼GB，要除去加班时长小于一小时的 start
		sb.append(" and timedata.acthour >= 1.00 ");
		// 傻逼GB，要除去加班时长小于一小时的 end
		sb.append(" union all select (iif(prev_timedata.overtimehour > 8, datediff(SECOND ");
		sb.append(" , iif(dateadd(HOUR,8,prev_timedata.overtimebegintime) > concat(psncalendar.calendar,' ','06:00:00') ");
		sb.append(" , iif(dateadd(HOUR,8,prev_timedata.overtimebegintime) < concat(psncalendar.calendar,' ','22:00:00') ");
		sb.append(" , dateadd(HOUR,8,prev_timedata.overtimebegintime), concat(psncalendar.calendar,' ','22:00:00')), concat(psncalendar.calendar,' ','06:00:00')) ");
		sb.append(" , iif(prev_timedata.overtimeendtime > concat(psncalendar.calendar, ' ','22:00:00'), concat(psncalendar.calendar, ' ','22:00:00') ");
		sb.append(" , iif(prev_timedata.overtimeendtime < concat(psncalendar.calendar, ' ','06:00:00'), concat(psncalendar.calendar, ' ','06:00:00') ");
		sb.append(" , isnull(prev_timedata.overtimeendtime, concat(psncalendar.calendar, ' ','06:00:00'))))) / 3600.0 ,0)) a, psncalendar.calendar, psncalendar.pk_psndoc ");
		sb.append(" from tbm_psncalendar psncalendar left join bd_shift workshift on psncalendar.pk_shift = workshift.pk_shift ");
		sb.append(" left join tbm_overtimereg prev_timedata on prev_timedata.pk_psndoc = psncalendar.pk_psndoc and psncalendar.calendar = dateadd(day,1,prev_timedata.overtimebegindate) ");
		// 傻逼GB，要除去加班时长小于一小时的 start
		sb.append(" and prev_timedata.acthour >= 1.00 ");
		// 傻逼GB，要除去加班时长小于一小时的 end
		sb.append(" ) b ");
		sb.append(" where calendar = " + date + " and pk_psndoc = tbm_daystat.pk_psndoc) ");
		return sb.toString();
	}

}
