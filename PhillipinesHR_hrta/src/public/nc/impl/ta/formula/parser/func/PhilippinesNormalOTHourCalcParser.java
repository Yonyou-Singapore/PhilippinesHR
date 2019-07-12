package nc.impl.ta.formula.parser.func;

import nc.impl.hr.formula.parser.AbstractParaFuncParser;
import nc.vo.pub.BusinessException;

public class PhilippinesNormalOTHourCalcParser extends AbstractParaFuncParser {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4852132320417412666L;
	
	public PhilippinesNormalOTHourCalcParser() {
		this.setFuncName("getDaytimeOTHour");
	}

	@Override
	protected String translateFunc2SQL(String pk_org, String formula,
			String funcStr, String[] funcArgs, Object... params)
			throws BusinessException {
		// TODO Auto-generated method stub
		funcArgs = new String[] { "tbm_daystat.calendar" };
		String date = funcArgs[0];
		StringBuilder sb = new StringBuilder();
		sb.append(" (select sum((datediff(SECOND, iif(timedata.overtimebegintime > concat(psncalendar.calendar,' ','06:00:00') ");
		sb.append(" , iif(timedata.overtimebegintime < concat(psncalendar.calendar,' ','22:00:00'), timedata.overtimebegintime, concat(psncalendar.calendar,' ','22:00:00')) ");
		sb.append(" , iif (timedata.overtimebegintime is null, concat(psncalendar.calendar,' ','22:00:00'), concat(psncalendar.calendar,' ','06:00:00'))) ");
		sb.append(" , iif(timedata.overtimeendtime < concat(psncalendar.calendar, ' ','22:00:00') ");
		sb.append(" , iif(timedata.overtimeendtime > concat(psncalendar.calendar, ' ','06:00:00'), timedata.overtimeendtime, concat(psncalendar.calendar, ' ','06:00:00'))  ");
		sb.append(" , concat(psncalendar.calendar, ' ','22:00:00'))) / 3600.0  ");
		sb.append(" + datediff(SECOND, concat(psncalendar.calendar, ' ','06:00:00'), iif(prev_timedata.overtimeendtime > concat(psncalendar.calendar, ' ','22:00:00') ");
		sb.append(" , concat(psncalendar.calendar, ' ','22:00:00'), iif(prev_timedata.overtimeendtime < concat(psncalendar.calendar, ' ','06:00:00') ");
		sb.append(" , concat(psncalendar.calendar, ' ','06:00:00'), isnull(prev_timedata.overtimeendtime,concat(psncalendar.calendar, ' ','06:00:00'))))) / 3600.0)) ");
		sb.append(" from tbm_psncalendar psncalendar ");
		sb.append(" left join tbm_overtimereg timedata on timedata.overtimebegindate = psncalendar.calendar and timedata.pk_psndoc = psncalendar.pk_psndoc ");
		// 傻逼GB，要除去加班时长小于一小时的 start
		sb.append(" and timedata.acthour >= 1.00 ");
		// 傻逼GB，要除去加班时长小于一小时的 end
		sb.append(" left join tbm_overtimereg prev_timedata on prev_timedata.pk_psndoc = psncalendar.pk_psndoc and psncalendar.calendar = dateadd(day,1,prev_timedata.overtimebegindate) ");
		// 傻逼GB，要除去加班时长小于一小时的 start
		sb.append(" and prev_timedata.acthour >= 1.00 ");
		// 傻逼GB，要除去加班时长小于一小时的 end
		
		sb.append(" where psncalendar.calendar = " + date + " and psncalendar.pk_psndoc = tbm_daystat.pk_psndoc) ");
		return sb.toString();
	}

}
