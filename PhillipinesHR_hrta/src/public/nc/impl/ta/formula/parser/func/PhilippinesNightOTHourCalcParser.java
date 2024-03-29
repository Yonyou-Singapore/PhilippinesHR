package nc.impl.ta.formula.parser.func;

import nc.impl.hr.formula.parser.AbstractParaFuncParser;
import nc.vo.pub.BusinessException;

public class PhilippinesNightOTHourCalcParser extends AbstractParaFuncParser {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1603752077236394106L;
	
	public PhilippinesNightOTHourCalcParser() {
		this.setFuncName("getNighttimeOTHour");
	}

	@Override
	protected String translateFunc2SQL(String pk_org, String formula,
			String funcStr, String[] funcArgs, Object... params)
			throws BusinessException {
		// TODO Auto-generated method stub
		funcArgs = new String[] { "tbm_daystat.calendar" };
		String date = funcArgs[0];
		StringBuilder sb = new StringBuilder();
		sb.append(" (select sum(a) from (select datediff(SECOND, iif(timedata.overtimebegintime < concat(psncalendar.calendar,' ','06:00:00'), timedata.overtimebegintime ");
		sb.append(" , concat(psncalendar.calendar,' ','06:00:00')), iif(timedata.overtimeendtime < concat(psncalendar.calendar, ' ','06:00:00'), timedata.overtimeendtime ");
		sb.append(" , concat(psncalendar.calendar, ' ','06:00:00'))) / 3600.0 a, psncalendar.calendar, psncalendar.pk_psndoc from tbm_psncalendar psncalendar ");
		sb.append(" left join tbm_overtimereg timedata on timedata.overtimebegindate = psncalendar.calendar and timedata.pk_psndoc = psncalendar.pk_psndoc ");
		// 傻逼GB，要除去加班时长小于一小时的 start
		sb.append(" and timedata.acthour >= 1.00 ");
		// 傻逼GB，要除去加班时长小于一小时的 end
		sb.append(" left join tbm_overtimereg prev_timedata on prev_timedata.pk_psndoc = psncalendar.pk_psndoc and psncalendar.calendar = dateadd(day,1,prev_timedata.overtimebegindate) ");
		// 傻逼GB，要除去加班时长小于一小时的 start
		sb.append(" and prev_timedata.acthour >= 1.00 ");
		// 傻逼GB，要除去加班时长小于一小时的 end
		sb.append(" union all ");
		sb.append(" select datediff(SECOND, iif(timedata.overtimebegintime > concat(psncalendar.calendar,' ', '22:00:00'), timedata.overtimebegintime ");
		sb.append(" , concat(psncalendar.calendar, ' ', '22:00:00')), iif (timedata.overtimeendtime > concat(psncalendar.calendar,' ', '22:00:00') ");
		sb.append(" , iif (timedata.overtimeendtime < dateadd(day, 1, timedata.overtimebegindate), timedata.overtimeendtime, dateadd(day, 1, timedata.overtimebegindate)) ");
		sb.append(" , concat(psncalendar.calendar, ' ', '22:00:00'))) / 3600.0 a, psncalendar.calendar, psncalendar.pk_psndoc from tbm_psncalendar psncalendar ");
		sb.append(" left join tbm_overtimereg timedata on timedata.overtimebegindate = psncalendar.calendar and timedata.pk_psndoc = psncalendar.pk_psndoc ");
		// 傻逼GB，要除去加班时长小于一小时的 start
		sb.append(" and timedata.acthour >= 1.00 ");
		// 傻逼GB，要除去加班时长小于一小时的 end
		sb.append(" left join tbm_overtimereg prev_timedata on prev_timedata.pk_psndoc = psncalendar.pk_psndoc and psncalendar.calendar = dateadd(day,1,prev_timedata.overtimebegindate) ");
		// 傻逼GB，要除去加班时长小于一小时的 start
		sb.append(" and prev_timedata.acthour >= 1.00 ");
		// 傻逼GB，要除去加班时长小于一小时的 end
		sb.append(" union all  ");
		sb.append(" select datediff(SECOND, concat(psncalendar.calendar,' ','00:00:00'), iif(prev_timedata.overtimeendtime > concat(psncalendar.calendar, ' ','06:00:00') ");
		sb.append(" , concat(psncalendar.calendar, ' ','06:00:00'), iif (prev_timedata.overtimeendtime > concat(psncalendar.calendar, ' ', '00:00:00'), prev_timedata.overtimeendtime ");
		sb.append(" , concat(psncalendar.calendar,' ','00:00:00')))) / 3600.0 a, psncalendar.calendar, psncalendar.pk_psndoc from tbm_psncalendar psncalendar ");
		sb.append(" left join tbm_overtimereg prev_timedata on prev_timedata.pk_psndoc = psncalendar.pk_psndoc and psncalendar.calendar = dateadd(day,1,prev_timedata.overtimebegindate) ");
		// 傻逼GB，要除去加班时长小于一小时的 start
		sb.append(" and prev_timedata.acthour >= 1.00 ");
		// 傻逼GB，要除去加班时长小于一小时的 end
		sb.append(" union all ");
		sb.append(" select datediff(SECOND, concat(psncalendar.calendar,' ','22:00:00'), iif(prev_timedata.overtimeendtime > concat(psncalendar.calendar, ' ','22:00:00') ");
		sb.append(" , iif (prev_timedata.overtimeendtime < dateadd(day, 1, timedata.overtimebegindate), prev_timedata.overtimeendtime, dateadd(day, 1, timedata.overtimebegindate)) ");
		sb.append(" , concat(psncalendar.calendar,' ','22:00:00'))) / 3600.0 a, psncalendar.calendar, psncalendar.pk_psndoc from tbm_psncalendar psncalendar ");
		sb.append(" left join tbm_overtimereg timedata on timedata.overtimebegindate = psncalendar.calendar and timedata.pk_psndoc = psncalendar.pk_psndoc ");
		// 傻逼GB，要除去加班时长小于一小时的 start
		sb.append(" and timedata.acthour >= 1.00 ");
		// 傻逼GB，要除去加班时长小于一小时的 end
		sb.append(" left join tbm_overtimereg prev_timedata on prev_timedata.pk_psndoc = psncalendar.pk_psndoc and psncalendar.calendar = dateadd(day,1,prev_timedata.overtimebegindate)");
		// 傻逼GB，要除去加班时长小于一小时的 start
		sb.append(" and prev_timedata.acthour >= 1.00 ");
		// 傻逼GB，要除去加班时长小于一小时的 end
		sb.append(" ) b ");
		sb.append(" where calendar = " + date + " and pk_psndoc = tbm_daystat.pk_psndoc) ");
		return sb.toString();
	}

}
