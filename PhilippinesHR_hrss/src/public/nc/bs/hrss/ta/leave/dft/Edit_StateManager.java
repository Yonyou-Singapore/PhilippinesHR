package nc.bs.hrss.ta.leave.dft;

import nc.uap.lfw.core.bm.IStateManager;
import nc.uap.lfw.core.bm.dft.AbstractStateManager;
import nc.uap.lfw.core.comp.WebComponent;
import nc.uap.lfw.core.data.Dataset;
import nc.uap.lfw.core.data.Row;
import nc.uap.lfw.core.page.LfwView;

public class Edit_StateManager extends AbstractStateManager {

	@Override
	public State getState(WebComponent target, LfwView view) {
		Dataset ds = getCtrlDataset(view);
		Row row = ds.getSelectedRow();
		String key = ds.getCurrentKey();
		if (target.getId().equals("attachment_upload") ) {
			if (key != null && key.length() == 20) {
				return IStateManager.State.HIDDEN;				
			}
		}
		return IStateManager.State.ENABLED;
	}

}
