package nc.impl.ta.formula.parser.func;

import nc.impl.hr.formula.parser.AbstractParaFuncParser;
import nc.vo.pub.BusinessException;

public class PhilippinesNormalWorkHourCalcParser extends AbstractParaFuncParser {

	/**
	 * 
	 */
	private static final long serialVersionUID = -589029543918273236L;
	
	public PhilippinesNormalWorkHourCalcParser() {
		this.setFuncName("getDaytimeWorkShiftHour");
	}

	@Override
	protected String translateFunc2SQL(String pk_org, String formula,
			String funcStr, String[] funcArgs, Object... params)
			throws BusinessException {
		// TODO Auto-generated method stub
		funcArgs = new String[] { "tbm_daystat.calendar" };
		String date = funcArgs[0];
		StringBuilder sb = new StringBuilder();
		sb.append(" (select sum((isnull(timedata.worklength_cur,0) - timedata.nightlength_cur + isnull(prev_timedata.worklength_next,0)   ");
		sb.append(" - isnull(prev_timedata.nightlength_next,0)) / 3600.0 ) actualWorkLen ");
		sb.append(" from tbm_psncalendar psncalendar ");
		sb.append(" left join bd_shift workshift on psncalendar.pk_shift = workshift.pk_shift ");
		sb.append(" inner join tbm_timedata timedata on timedata.calendar = psncalendar.calendar and timedata.pk_psndoc = psncalendar.pk_psndoc ");
		sb.append(" left join tbm_timedata prev_timedata on prev_timedata.pk_psndoc = timedata.pk_psndoc and timedata.calendar = dateadd(day,1,prev_timedata.calendar) ");
		sb.append(" where timedata.calendar = " + date + " and timedata.pk_psndoc = tbm_daystat.pk_psndoc) ");
		return sb.toString();
	}
}
