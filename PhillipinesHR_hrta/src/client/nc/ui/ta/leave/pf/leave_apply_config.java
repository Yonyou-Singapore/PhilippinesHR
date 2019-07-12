package nc.ui.ta.leave.pf;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nc.ui.uif2.factory.AbstractJavaBeanDefinition;

public class leave_apply_config extends AbstractJavaBeanDefinition{
	private Map<String, Object> context = new HashMap();
public nc.ui.uif2.tangramlayout.CardLayoutToolbarPanel getListToolBarPanel(){
 if(context.get("listToolBarPanel")!=null)
 return (nc.ui.uif2.tangramlayout.CardLayoutToolbarPanel)context.get("listToolBarPanel");
  nc.ui.uif2.tangramlayout.CardLayoutToolbarPanel bean = new nc.ui.uif2.tangramlayout.CardLayoutToolbarPanel();
  context.put("listToolBarPanel",bean);
  bean.setModel(getAppModel());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.pub.beans.ActionsBar.ActionsBarSeparator getBarSeparator(){
 if(context.get("barSeparator")!=null)
 return (nc.ui.pub.beans.ActionsBar.ActionsBarSeparator)context.get("barSeparator");
  nc.ui.pub.beans.ActionsBar.ActionsBarSeparator bean = new nc.ui.pub.beans.ActionsBar.ActionsBarSeparator();
  context.put("barSeparator",bean);
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.hr.pf.HRPFCardLayoutToolbarPanel getEditorToolBarPanel(){
 if(context.get("editorToolBarPanel")!=null)
 return (nc.ui.hr.pf.HRPFCardLayoutToolbarPanel)context.get("editorToolBarPanel");
  nc.ui.hr.pf.HRPFCardLayoutToolbarPanel bean = new nc.ui.hr.pf.HRPFCardLayoutToolbarPanel();
  context.put("editorToolBarPanel",bean);
  bean.setModel(getAppModel());
  bean.setTitleAction(getEditorReturnAction());
  bean.setActions(getManagedList0());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private List getManagedList0(){  List list = new ArrayList();  list.add(getFileAction());  list.add(getBarSeparator());  list.add(getFirstLineAction());  list.add(getPreLineAction());  list.add(getNextLineAction());  list.add(getLastLineAction());  return list;}

public nc.ui.uif2.actions.ShowMeUpAction getEditorReturnAction(){
 if(context.get("editorReturnAction")!=null)
 return (nc.ui.uif2.actions.ShowMeUpAction)context.get("editorReturnAction");
  nc.ui.uif2.actions.ShowMeUpAction bean = new nc.ui.uif2.actions.ShowMeUpAction();
  context.put("editorReturnAction",bean);
  bean.setGoComponent(getListView());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.uif2.actions.ActionContributors getToftpanelActionContributors(){
 if(context.get("toftpanelActionContributors")!=null)
 return (nc.ui.uif2.actions.ActionContributors)context.get("toftpanelActionContributors");
  nc.ui.uif2.actions.ActionContributors bean = new nc.ui.uif2.actions.ActionContributors();
  context.put("toftpanelActionContributors",bean);
  bean.setContributors(getManagedList1());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private List getManagedList1(){  List list = new ArrayList();  list.add(getListViewActions());  list.add(getCardViewActions());  return list;}

public nc.ui.ta.leave.pf.DynamicActionContainer getListViewActions(){
 if(context.get("listViewActions")!=null)
 return (nc.ui.ta.leave.pf.DynamicActionContainer)context.get("listViewActions");
  nc.ui.ta.leave.pf.DynamicActionContainer bean = new nc.ui.ta.leave.pf.DynamicActionContainer(getListView());  context.put("listViewActions",bean);
  bean.setModel(getAppModel());
  bean.setActions(getManagedList2());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private List getManagedList2(){  List list = new ArrayList();  list.add(getAddActionGroup());  list.add(getEditAction());  list.add(getDeleteAction());  list.add(getCopyAction());  list.add(getSeparatorAction());  list.add(getQueryAction());  list.add(getRefreshAction());  list.add(getSeparatorAction());  list.add(getCommitActionGroup());  list.add(getAssistActionGroup());  list.add(getSeparatorAction());  list.add(getListPrintActionGroup());  return list;}

public nc.ui.ta.leave.pf.DynamicActionContainer getCardViewActions(){
 if(context.get("cardViewActions")!=null)
 return (nc.ui.ta.leave.pf.DynamicActionContainer)context.get("cardViewActions");
  nc.ui.ta.leave.pf.DynamicActionContainer bean = new nc.ui.ta.leave.pf.DynamicActionContainer(getCardView());  context.put("cardViewActions",bean);
  bean.setModel(getAppModel());
  bean.setActions(getManagedList3());
  bean.setEditActions(getManagedList4());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private List getManagedList3(){  List list = new ArrayList();  list.add(getAddActionGroup());  list.add(getEditAction());  list.add(getDeleteAction());  list.add(getCopyAction());  list.add(getSeparatorAction());  list.add(getRefreshAction());  list.add(getSeparatorAction());  list.add(getCommitActionGroup());  list.add(getAssistActionGroup());  list.add(getSeparatorAction());  list.add(getCardPrintGroup());  return list;}

private List getManagedList4(){  List list = new ArrayList();  list.add(getSaveAction());  list.add(getSaveAddAction());  list.add(getSaveSubmitAction());  list.add(getSeparatorAction());  list.add(getCancelAction());  return list;}

public nc.ui.hr.uif2.validator.SelectionValidator getSelectionValidator(){
 if(context.get("selectionValidator")!=null)
 return (nc.ui.hr.uif2.validator.SelectionValidator)context.get("selectionValidator");
  nc.ui.hr.uif2.validator.SelectionValidator bean = new nc.ui.hr.uif2.validator.SelectionValidator();
  context.put("selectionValidator",bean);
  bean.setModel(getAppModel());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.hr.uif2.validator.OrgNotNullValidator getOrgNotNullValidator(){
 if(context.get("orgNotNullValidator")!=null)
 return (nc.ui.hr.uif2.validator.OrgNotNullValidator)context.get("orgNotNullValidator");
  nc.ui.hr.uif2.validator.OrgNotNullValidator bean = new nc.ui.hr.uif2.validator.OrgNotNullValidator();
  context.put("orgNotNullValidator",bean);
  bean.setModel(getAppModel());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.hr.pf.validator.PFBatchCallBackValidator getCallBackValidator(){
 if(context.get("callBackValidator")!=null)
 return (nc.ui.hr.pf.validator.PFBatchCallBackValidator)context.get("callBackValidator");
  nc.ui.hr.pf.validator.PFBatchCallBackValidator bean = new nc.ui.hr.pf.validator.PFBatchCallBackValidator();
  context.put("callBackValidator",bean);
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.ta.leave.pf.validator.PFCopyLeaveValidator getCopyValidator(){
 if(context.get("copyValidator")!=null)
 return (nc.ui.ta.leave.pf.validator.PFCopyLeaveValidator)context.get("copyValidator");
  nc.ui.ta.leave.pf.validator.PFCopyLeaveValidator bean = new nc.ui.ta.leave.pf.validator.PFCopyLeaveValidator();
  context.put("copyValidator",bean);
  bean.setModel(getAppModel());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.hr.pf.validator.PFEditValidator getEditValidator(){
 if(context.get("editValidator")!=null)
 return (nc.ui.hr.pf.validator.PFEditValidator)context.get("editValidator");
  nc.ui.hr.pf.validator.PFEditValidator bean = new nc.ui.hr.pf.validator.PFEditValidator();
  context.put("editValidator",bean);
  bean.setModel(getAppModel());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.hr.pf.validator.PFBatchDeleteValidator getDeleteValidator(){
 if(context.get("deleteValidator")!=null)
 return (nc.ui.hr.pf.validator.PFBatchDeleteValidator)context.get("deleteValidator");
  nc.ui.hr.pf.validator.PFBatchDeleteValidator bean = new nc.ui.hr.pf.validator.PFBatchDeleteValidator();
  context.put("deleteValidator",bean);
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.hr.pf.validator.PFBatchSubmitValidator getSubmitValidator(){
 if(context.get("submitValidator")!=null)
 return (nc.ui.hr.pf.validator.PFBatchSubmitValidator)context.get("submitValidator");
  nc.ui.hr.pf.validator.PFBatchSubmitValidator bean = new nc.ui.hr.pf.validator.PFBatchSubmitValidator();
  context.put("submitValidator",bean);
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.hr.pf.validator.PFBatchApproveValidator getApproveValidator(){
 if(context.get("approveValidator")!=null)
 return (nc.ui.hr.pf.validator.PFBatchApproveValidator)context.get("approveValidator");
  nc.ui.hr.pf.validator.PFBatchApproveValidator bean = new nc.ui.hr.pf.validator.PFBatchApproveValidator();
  context.put("approveValidator",bean);
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.hr.pf.validator.PFBatchUnApproveValidator getUnApproveValidator(){
 if(context.get("unApproveValidator")!=null)
 return (nc.ui.hr.pf.validator.PFBatchUnApproveValidator)context.get("unApproveValidator");
  nc.ui.hr.pf.validator.PFBatchUnApproveValidator bean = new nc.ui.hr.pf.validator.PFBatchUnApproveValidator();
  context.put("unApproveValidator",bean);
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.hr.uif2.action.HrActionInterceptor getActionInterceptor(){
 if(context.get("actionInterceptor")!=null)
 return (nc.ui.hr.uif2.action.HrActionInterceptor)context.get("actionInterceptor");
  nc.ui.hr.uif2.action.HrActionInterceptor bean = new nc.ui.hr.uif2.action.HrActionInterceptor();
  context.put("actionInterceptor",bean);
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.funcnode.ui.action.SeparatorAction getSeparatorAction(){
 if(context.get("separatorAction")!=null)
 return (nc.funcnode.ui.action.SeparatorAction)context.get("separatorAction");
  nc.funcnode.ui.action.SeparatorAction bean = new nc.funcnode.ui.action.SeparatorAction();
  context.put("separatorAction",bean);
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.funcnode.ui.action.GroupAction getApproveGroupAction(){
 if(context.get("approveGroupAction")!=null)
 return (nc.funcnode.ui.action.GroupAction)context.get("approveGroupAction");
  nc.funcnode.ui.action.GroupAction bean = new nc.funcnode.ui.action.GroupAction();
  context.put("approveGroupAction",bean);
  bean.setCode("ApproveGroupAction");
  bean.setName(getI18nFB_13d9300());
  bean.setActions(getManagedList5());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private java.lang.String getI18nFB_13d9300(){
 if(context.get("nc.ui.uif2.I18nFB#13d9300")!=null)
 return (java.lang.String)context.get("nc.ui.uif2.I18nFB#13d9300");
  nc.ui.uif2.I18nFB bean = new nc.ui.uif2.I18nFB();
    context.put("&nc.ui.uif2.I18nFB#13d9300",bean);  bean.setResDir("common");
  bean.setDefaultValue("审核");
  bean.setResId("UC001-0000027");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
 try {
     Object product = bean.getObject();
    context.put("nc.ui.uif2.I18nFB#13d9300",product);
     return (java.lang.String)product;
}
catch(Exception e) { throw new RuntimeException(e);}}

private List getManagedList5(){  List list = new ArrayList();  list.add(getApproveAction());  list.add(getUnApproveAction());  list.add(getApproveNoteAction());  return list;}

public nc.ui.ta.leave.pf.action.PFAddLeaveAction getAddAction(){
 if(context.get("addAction")!=null)
 return (nc.ui.ta.leave.pf.action.PFAddLeaveAction)context.get("addAction");
  nc.ui.ta.leave.pf.action.PFAddLeaveAction bean = new nc.ui.ta.leave.pf.action.PFAddLeaveAction();
  context.put("addAction",bean);
  bean.setModel(getAppModel());
  bean.setNcActionStatusJudge(getEnableJudge());
  bean.setValidator(getManagedList6());
  bean.setDefaultValueProvider(getDefaultValueProvider());
  bean.setFormEditor(getCardView());
  bean.setAddLineAction(getAddLineAction());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private List getManagedList6(){  List list = new ArrayList();  list.add(getOrgNotNullValidator());  return list;}

public nc.ui.ta.leave.pf.model.LeaveDefaultValueProvider getDefaultValueProvider(){
 if(context.get("DefaultValueProvider")!=null)
 return (nc.ui.ta.leave.pf.model.LeaveDefaultValueProvider)context.get("DefaultValueProvider");
  nc.ui.ta.leave.pf.model.LeaveDefaultValueProvider bean = new nc.ui.ta.leave.pf.model.LeaveDefaultValueProvider();
  context.put("DefaultValueProvider",bean);
  bean.setModel(getAppModel());
  bean.setLastDefaultValueProvider(getPfdefaultValueProvider());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.ta.leave.pf.action.PFCallBackLeaveAction getCallBackAction(){
 if(context.get("callBackAction")!=null)
 return (nc.ui.ta.leave.pf.action.PFCallBackLeaveAction)context.get("callBackAction");
  nc.ui.ta.leave.pf.action.PFCallBackLeaveAction bean = new nc.ui.ta.leave.pf.action.PFCallBackLeaveAction();
  context.put("callBackAction",bean);
  bean.setModel(getAppModel());
  bean.setNcActionStatusJudge(getEnableJudge());
  bean.setBillform(getCardView());
  bean.setValidator(getManagedList7());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private List getManagedList7(){  List list = new ArrayList();  list.add(getSelectionValidator());  list.add(getCallBackValidator());  return list;}

public nc.ui.ta.leave.pf.action.PFCancelLeaveAction getCancelAction(){
 if(context.get("cancelAction")!=null)
 return (nc.ui.ta.leave.pf.action.PFCancelLeaveAction)context.get("cancelAction");
  nc.ui.ta.leave.pf.action.PFCancelLeaveAction bean = new nc.ui.ta.leave.pf.action.PFCancelLeaveAction();
  context.put("cancelAction",bean);
  bean.setModel(getAppModel());
  bean.setBillform(getCardView());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.ta.leave.pf.action.PFEditLeaveAction getEditAction(){
 if(context.get("editAction")!=null)
 return (nc.ui.ta.leave.pf.action.PFEditLeaveAction)context.get("editAction");
  nc.ui.ta.leave.pf.action.PFEditLeaveAction bean = new nc.ui.ta.leave.pf.action.PFEditLeaveAction();
  context.put("editAction",bean);
  bean.setModel(getAppModel());
  bean.setValidator(getManagedList8());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private List getManagedList8(){  List list = new ArrayList();  list.add(getSelectionValidator());  list.add(getEditValidator());  return list;}

public nc.ui.ta.leave.pf.action.PFDeleteLeaveAction getDeleteAction(){
 if(context.get("deleteAction")!=null)
 return (nc.ui.ta.leave.pf.action.PFDeleteLeaveAction)context.get("deleteAction");
  nc.ui.ta.leave.pf.action.PFDeleteLeaveAction bean = new nc.ui.ta.leave.pf.action.PFDeleteLeaveAction();
  context.put("deleteAction",bean);
  bean.setModel(getAppModel());
  bean.setNcActionStatusJudge(getEnableJudge());
  bean.setBillform(getCardView());
  bean.setValidator(getManagedList9());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private List getManagedList9(){  List list = new ArrayList();  list.add(getSelectionValidator());  list.add(getDeleteValidator());  return list;}

public nc.ui.ta.leave.pf.action.PFQueryLeaveAction getQueryAction(){
 if(context.get("queryAction")!=null)
 return (nc.ui.ta.leave.pf.action.PFQueryLeaveAction)context.get("queryAction");
  nc.ui.ta.leave.pf.action.PFQueryLeaveAction bean = new nc.ui.ta.leave.pf.action.PFQueryLeaveAction();
  context.put("queryAction",bean);
  bean.setModel(getAppModel());
  bean.setNcActionStatusJudge(getEnableJudge());
  bean.setDataManager(getModelDataManager());
  bean.setQueryDelegator(getQueryDelegator());
  bean.setValidator(getManagedList10());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private List getManagedList10(){  List list = new ArrayList();  list.add(getOrgNotNullValidator());  return list;}

public nc.ui.ta.leave.pf.model.LeaveQueryDelegator getQueryDelegator(){
 if(context.get("queryDelegator")!=null)
 return (nc.ui.ta.leave.pf.model.LeaveQueryDelegator)context.get("queryDelegator");
  nc.ui.ta.leave.pf.model.LeaveQueryDelegator bean = new nc.ui.ta.leave.pf.model.LeaveQueryDelegator();
  context.put("queryDelegator",bean);
  bean.setContext(getContext());
  bean.setModel(getAppModel());
  bean.setQueryEditorListener(getQueryEditorListener());
  bean.setQueryAreaShell(getQueryAreaShell());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.ta.leave.pf.action.PFRefreshLeaveAction getRefreshAction(){
 if(context.get("refreshAction")!=null)
 return (nc.ui.ta.leave.pf.action.PFRefreshLeaveAction)context.get("refreshAction");
  nc.ui.ta.leave.pf.action.PFRefreshLeaveAction bean = new nc.ui.ta.leave.pf.action.PFRefreshLeaveAction();
  context.put("refreshAction",bean);
  bean.setModel(getAppModel());
  bean.setNcActionStatusJudge(getEnableJudge());
  bean.setDataManager(getModelDataManager());
  bean.setFormEditor(getCardView());
  bean.setValidator(getManagedList11());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private List getManagedList11(){  List list = new ArrayList();  list.add(getOrgNotNullValidator());  return list;}

public nc.ui.ta.leave.pf.action.PFSaveLeaveAction getSaveAction(){
 if(context.get("saveAction")!=null)
 return (nc.ui.ta.leave.pf.action.PFSaveLeaveAction)context.get("saveAction");
  nc.ui.ta.leave.pf.action.PFSaveLeaveAction bean = new nc.ui.ta.leave.pf.action.PFSaveLeaveAction();
  context.put("saveAction",bean);
  bean.setModel(getAppModel());
  bean.setEditor(getCardView());
  bean.setValidator(getManagedList12());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private List getManagedList12(){  List list = new ArrayList();  list.add(getBillFormNotNullValidator());  list.add(getPFValidateSave());  return list;}

public nc.ui.ta.leave.pf.action.PFSaveAddLeaveAction getSaveAddAction(){
 if(context.get("saveAddAction")!=null)
 return (nc.ui.ta.leave.pf.action.PFSaveAddLeaveAction)context.get("saveAddAction");
  nc.ui.ta.leave.pf.action.PFSaveAddLeaveAction bean = new nc.ui.ta.leave.pf.action.PFSaveAddLeaveAction();
  context.put("saveAddAction",bean);
  bean.setModel(getAppModel());
  bean.setSaveAction(getSaveAction());
  bean.setAddAction(getAddAction());
  bean.setAddSuckleAction(getPFAddSuckleAction());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.ta.pub.action.TAPFSaveSubmitAction getSaveSubmitAction(){
 if(context.get("saveSubmitAction")!=null)
 return (nc.ui.ta.pub.action.TAPFSaveSubmitAction)context.get("saveSubmitAction");
  nc.ui.ta.pub.action.TAPFSaveSubmitAction bean = new nc.ui.ta.pub.action.TAPFSaveSubmitAction();
  context.put("saveSubmitAction",bean);
  bean.setModel(getAppModel());
  bean.setSaveAction(getSaveAction());
  bean.setSubmitAction(getSubmitAction());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.ta.leave.pf.action.PFCopyLeaveAction getCopyAction(){
 if(context.get("copyAction")!=null)
 return (nc.ui.ta.leave.pf.action.PFCopyLeaveAction)context.get("copyAction");
  nc.ui.ta.leave.pf.action.PFCopyLeaveAction bean = new nc.ui.ta.leave.pf.action.PFCopyLeaveAction();
  context.put("copyAction",bean);
  bean.setModel(getAppModel());
  bean.setFormEditor(getCardView());
  bean.setValidator(getManagedList13());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private List getManagedList13(){  List list = new ArrayList();  list.add(getSelectionValidator());  list.add(getCopyValidator());  return list;}

public nc.ui.hr.pf.action.PFFileManageAction getFileManageAction(){
 if(context.get("fileManageAction")!=null)
 return (nc.ui.hr.pf.action.PFFileManageAction)context.get("fileManageAction");
  nc.ui.hr.pf.action.PFFileManageAction bean = new nc.ui.hr.pf.action.PFFileManageAction();
  context.put("fileManageAction",bean);
  bean.setModel(getAppModel());
  bean.setValidator(getManagedList14());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private List getManagedList14(){  List list = new ArrayList();  list.add(getSelectionValidator());  return list;}

public nc.ui.hr.pf.action.PFFileManageAction getFileAction(){
 if(context.get("fileAction")!=null)
 return (nc.ui.hr.pf.action.PFFileManageAction)context.get("fileAction");
  nc.ui.hr.pf.action.PFFileManageAction bean = new nc.ui.hr.pf.action.PFFileManageAction();
  context.put("fileAction",bean);
  bean.setModel(getAppModel());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.ta.leave.pf.action.PFSubmitLeaveAction getSubmitAction(){
 if(context.get("submitAction")!=null)
 return (nc.ui.ta.leave.pf.action.PFSubmitLeaveAction)context.get("submitAction");
  nc.ui.ta.leave.pf.action.PFSubmitLeaveAction bean = new nc.ui.ta.leave.pf.action.PFSubmitLeaveAction();
  context.put("submitAction",bean);
  bean.setModel(getAppModel());
  bean.setNcActionStatusJudge(getEnableJudge());
  bean.setBillform(getCardView());
  bean.setValidator(getManagedList15());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private List getManagedList15(){  List list = new ArrayList();  list.add(getSelectionValidator());  list.add(getSubmitValidator());  return list;}

public nc.funcnode.ui.action.GroupAction getSubmitGroupAction(){
 if(context.get("submitGroupAction")!=null)
 return (nc.funcnode.ui.action.GroupAction)context.get("submitGroupAction");
  nc.funcnode.ui.action.GroupAction bean = new nc.funcnode.ui.action.GroupAction();
  context.put("submitGroupAction",bean);
  bean.setActions(getManagedList16());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private List getManagedList16(){  List list = new ArrayList();  list.add(getSubmitAction());  list.add(getCallBackAction());  list.add(getSeparatorAction());  list.add(getApproveNoteAction());  return list;}

public nc.ui.ta.leave.pf.action.PFApproveLeaveAction getApproveAction(){
 if(context.get("approveAction")!=null)
 return (nc.ui.ta.leave.pf.action.PFApproveLeaveAction)context.get("approveAction");
  nc.ui.ta.leave.pf.action.PFApproveLeaveAction bean = new nc.ui.ta.leave.pf.action.PFApproveLeaveAction();
  context.put("approveAction",bean);
  bean.setModel(getAppModel());
  bean.setNcActionStatusJudge(getEnableJudge());
  bean.setValidator(getManagedList17());
  bean.setBillform(getCardView());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private List getManagedList17(){  List list = new ArrayList();  list.add(getApproveValidator());  return list;}

public nc.ui.ta.leave.pf.action.PFApproveNoteLeaveAction getApproveNoteAction(){
 if(context.get("approveNoteAction")!=null)
 return (nc.ui.ta.leave.pf.action.PFApproveNoteLeaveAction)context.get("approveNoteAction");
  nc.ui.ta.leave.pf.action.PFApproveNoteLeaveAction bean = new nc.ui.ta.leave.pf.action.PFApproveNoteLeaveAction();
  context.put("approveNoteAction",bean);
  bean.setModel(getAppModel());
  bean.setNcActionStatusJudge(getEnableJudge());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.ta.leave.pf.action.PFUnApproveLeaveAction getUnApproveAction(){
 if(context.get("unApproveAction")!=null)
 return (nc.ui.ta.leave.pf.action.PFUnApproveLeaveAction)context.get("unApproveAction");
  nc.ui.ta.leave.pf.action.PFUnApproveLeaveAction bean = new nc.ui.ta.leave.pf.action.PFUnApproveLeaveAction();
  context.put("unApproveAction",bean);
  bean.setModel(getAppModel());
  bean.setNcActionStatusJudge(getEnableJudge());
  bean.setValidator(getManagedList18());
  bean.setBillform(getCardView());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private List getManagedList18(){  List list = new ArrayList();  list.add(getUnApproveValidator());  return list;}

public nc.ui.uif2.actions.FirstLineAction getFirstLineAction(){
 if(context.get("firstLineAction")!=null)
 return (nc.ui.uif2.actions.FirstLineAction)context.get("firstLineAction");
  nc.ui.uif2.actions.FirstLineAction bean = new nc.ui.uif2.actions.FirstLineAction();
  context.put("firstLineAction",bean);
  bean.setModel(getAppModel());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.uif2.actions.NextLineAction getNextLineAction(){
 if(context.get("nextLineAction")!=null)
 return (nc.ui.uif2.actions.NextLineAction)context.get("nextLineAction");
  nc.ui.uif2.actions.NextLineAction bean = new nc.ui.uif2.actions.NextLineAction();
  context.put("nextLineAction",bean);
  bean.setModel(getAppModel());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.uif2.actions.PreLineAction getPreLineAction(){
 if(context.get("preLineAction")!=null)
 return (nc.ui.uif2.actions.PreLineAction)context.get("preLineAction");
  nc.ui.uif2.actions.PreLineAction bean = new nc.ui.uif2.actions.PreLineAction();
  context.put("preLineAction",bean);
  bean.setModel(getAppModel());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.uif2.actions.LastLineAction getLastLineAction(){
 if(context.get("lastLineAction")!=null)
 return (nc.ui.uif2.actions.LastLineAction)context.get("lastLineAction");
  nc.ui.uif2.actions.LastLineAction bean = new nc.ui.uif2.actions.LastLineAction();
  context.put("lastLineAction",bean);
  bean.setModel(getAppModel());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.funcnode.ui.action.GroupAction getListPrintActionGroup(){
 if(context.get("listPrintActionGroup")!=null)
 return (nc.funcnode.ui.action.GroupAction)context.get("listPrintActionGroup");
  nc.funcnode.ui.action.GroupAction bean = new nc.funcnode.ui.action.GroupAction();
  context.put("listPrintActionGroup",bean);
  bean.setActions(getManagedList19());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private List getManagedList19(){  List list = new ArrayList();  list.add(getDirectPrintAction());  list.add(getDirectPreviewAction());  list.add(getExportListAction());  list.add(getSeparatorAction());  list.add(getTemplatePrintAction());  list.add(getTemplatePreviewAction());  return list;}

public nc.ui.ta.leave.pf.action.LeaveTemplatePrintAction getTemplatePrintAction(){
 if(context.get("templatePrintAction")!=null)
 return (nc.ui.ta.leave.pf.action.LeaveTemplatePrintAction)context.get("templatePrintAction");
  nc.ui.ta.leave.pf.action.LeaveTemplatePrintAction bean = new nc.ui.ta.leave.pf.action.LeaveTemplatePrintAction();
  context.put("templatePrintAction",bean);
  bean.setModel(getAppModel());
  bean.setNcActionStatusJudge(getEnableJudge());
  bean.setPrintDlgParentConatiner(getCardView());
  bean.setDatasource(getDatasource());
  bean.setNodeKey(getPrintNodekey());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.ta.leave.pf.action.LeaveTemplatePreviewAction getTemplatePreviewAction(){
 if(context.get("templatePreviewAction")!=null)
 return (nc.ui.ta.leave.pf.action.LeaveTemplatePreviewAction)context.get("templatePreviewAction");
  nc.ui.ta.leave.pf.action.LeaveTemplatePreviewAction bean = new nc.ui.ta.leave.pf.action.LeaveTemplatePreviewAction();
  context.put("templatePreviewAction",bean);
  bean.setModel(getAppModel());
  bean.setNcActionStatusJudge(getEnableJudge());
  bean.setPrintDlgParentConatiner(getCardView());
  bean.setDatasource(getDatasource());
  bean.setNodeKey(getPrintNodekey());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.ta.leave.pf.action.LeaveDirectPrintAction getDirectPrintAction(){
 if(context.get("directPrintAction")!=null)
 return (nc.ui.ta.leave.pf.action.LeaveDirectPrintAction)context.get("directPrintAction");
  nc.ui.ta.leave.pf.action.LeaveDirectPrintAction bean = new nc.ui.ta.leave.pf.action.LeaveDirectPrintAction();
  context.put("directPrintAction",bean);
  bean.setModel(getAppModel());
  bean.setListView(getListView());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.ta.leave.pf.action.LeaveDirectPreviewAction getDirectPreviewAction(){
 if(context.get("directPreviewAction")!=null)
 return (nc.ui.ta.leave.pf.action.LeaveDirectPreviewAction)context.get("directPreviewAction");
  nc.ui.ta.leave.pf.action.LeaveDirectPreviewAction bean = new nc.ui.ta.leave.pf.action.LeaveDirectPreviewAction();
  context.put("directPreviewAction",bean);
  bean.setModel(getAppModel());
  bean.setListView(getListView());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.uif2.actions.OutputAction getExportCardAction(){
 if(context.get("exportCardAction")!=null)
 return (nc.ui.uif2.actions.OutputAction)context.get("exportCardAction");
  nc.ui.uif2.actions.OutputAction bean = new nc.ui.uif2.actions.OutputAction();
  context.put("exportCardAction",bean);
  bean.setModel(getAppModel());
  bean.setDatasource(getPrintDataSource());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.ta.leave.pf.action.LeaveOutPutAction getExportListAction(){
 if(context.get("exportListAction")!=null)
 return (nc.ui.ta.leave.pf.action.LeaveOutPutAction)context.get("exportListAction");
  nc.ui.ta.leave.pf.action.LeaveOutPutAction bean = new nc.ui.ta.leave.pf.action.LeaveOutPutAction();
  context.put("exportListAction",bean);
  bean.setModel(getAppModel());
  bean.setListView(getListView());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.hr.uif2.model.HRMetaDataDataSource getPrintDataSource(){
 if(context.get("printDataSource")!=null)
 return (nc.ui.hr.uif2.model.HRMetaDataDataSource)context.get("printDataSource");
  nc.ui.hr.uif2.model.HRMetaDataDataSource bean = new nc.ui.hr.uif2.model.HRMetaDataDataSource();
  context.put("printDataSource",bean);
  bean.setModel(getAppModel());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.vo.ta.pub.TALoginContext getContext(){
 if(context.get("context")!=null)
 return (nc.vo.ta.pub.TALoginContext)context.get("context");
  nc.vo.ta.pub.TALoginContext bean = new nc.vo.ta.pub.TALoginContext();
  context.put("context",bean);
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.ta.leave.pf.model.LeaveModelService getManageModelService(){
 if(context.get("manageModelService")!=null)
 return (nc.ui.ta.leave.pf.model.LeaveModelService)context.get("manageModelService");
  nc.ui.ta.leave.pf.model.LeaveModelService bean = new nc.ui.ta.leave.pf.model.LeaveModelService();
  context.put("manageModelService",bean);
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.vo.bd.meta.BDObjectAdpaterFactory getBoadatorfactory(){
 if(context.get("boadatorfactory")!=null)
 return (nc.vo.bd.meta.BDObjectAdpaterFactory)context.get("boadatorfactory");
  nc.vo.bd.meta.BDObjectAdpaterFactory bean = new nc.vo.bd.meta.BDObjectAdpaterFactory();
  context.put("boadatorfactory",bean);
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.ta.leave.pf.model.LeaveAppModel getAppModel(){
 if(context.get("appModel")!=null)
 return (nc.ui.ta.leave.pf.model.LeaveAppModel)context.get("appModel");
  nc.ui.ta.leave.pf.model.LeaveAppModel bean = new nc.ui.ta.leave.pf.model.LeaveAppModel();
  context.put("appModel",bean);
  bean.setBillType("6404");
  bean.setApproveSite(false);
  bean.setAutoGenerateBillCode(true);
  bean.setService(getManageModelService());
  bean.setBusinessObjectAdapterFactory(getBoadatorfactory());
  bean.setContext(getContext());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.ta.leave.pf.model.LeaveModelDataManager getModelDataManager(){
 if(context.get("modelDataManager")!=null)
 return (nc.ui.ta.leave.pf.model.LeaveModelDataManager)context.get("modelDataManager");
  nc.ui.ta.leave.pf.model.LeaveModelDataManager bean = new nc.ui.ta.leave.pf.model.LeaveModelDataManager();
  context.put("modelDataManager",bean);
  bean.setService(getManageModelService());
  bean.setContext(getContext());
  bean.setModel(getAppModel());
  bean.setOrgPanel(getOrgPanel());
  bean.setQueryAreaShell(getQueryAreaShell());
  bean.setFormEditor(getCardView());
  bean.setPaginationModel(getPaginationModel());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.hr.uif2.mediator.HyperLinkClickMediator getMouseClickShowPanelMediator(){
 if(context.get("mouseClickShowPanelMediator")!=null)
 return (nc.ui.hr.uif2.mediator.HyperLinkClickMediator)context.get("mouseClickShowPanelMediator");
  nc.ui.hr.uif2.mediator.HyperLinkClickMediator bean = new nc.ui.hr.uif2.mediator.HyperLinkClickMediator();
  context.put("mouseClickShowPanelMediator",bean);
  bean.setModel(getAppModel());
  bean.setShowUpComponent(getCardView());
  bean.setHyperLinkColumn("bill_code");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public java.lang.String getBillCode(){
 if(context.get("billCode")!=null)
 return (java.lang.String)context.get("billCode");
  java.lang.String bean = new java.lang.String("bill_code");  context.put("billCode",bean);
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.uif2.actions.QueryAreaShell getQueryAreaShell(){
 if(context.get("queryAreaShell")!=null)
 return (nc.ui.uif2.actions.QueryAreaShell)context.get("queryAreaShell");
  nc.ui.uif2.actions.QueryAreaShell bean = new nc.ui.uif2.actions.QueryAreaShell();
  context.put("queryAreaShell",bean);
  bean.setQueryArea(getQueryAction_created_1ad6351());
  bean.initUI();
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private nc.ui.queryarea.QueryArea getQueryAction_created_1ad6351(){
 if(context.get("queryAction.created#1ad6351")!=null)
 return (nc.ui.queryarea.QueryArea)context.get("queryAction.created#1ad6351");
  nc.ui.queryarea.QueryArea bean = getQueryAction().createQueryArea();
  context.put("queryAction.created#1ad6351",bean);
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.uif2.editor.TemplateContainer getTemplateContainer(){
 if(context.get("templateContainer")!=null)
 return (nc.ui.uif2.editor.TemplateContainer)context.get("templateContainer");
  nc.ui.uif2.editor.TemplateContainer bean = new nc.ui.uif2.editor.TemplateContainer();
  context.put("templateContainer",bean);
  bean.setContext(getContext());
  bean.setNodeKeies(getManagedList20());
  bean.load();
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private List getManagedList20(){  List list = new ArrayList();  list.add(getNodekey());  return list;}

public nc.ui.uif2.TangramContainer getContainer(){
 if(context.get("container")!=null)
 return (nc.ui.uif2.TangramContainer)context.get("container");
  nc.ui.uif2.TangramContainer bean = new nc.ui.uif2.TangramContainer();
  context.put("container",bean);
  bean.setTangramLayoutRoot(getTBNode_14e4cb2());
  bean.initUI();
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private nc.ui.uif2.tangramlayout.node.TBNode getTBNode_14e4cb2(){
 if(context.get("nc.ui.uif2.tangramlayout.node.TBNode#14e4cb2")!=null)
 return (nc.ui.uif2.tangramlayout.node.TBNode)context.get("nc.ui.uif2.tangramlayout.node.TBNode#14e4cb2");
  nc.ui.uif2.tangramlayout.node.TBNode bean = new nc.ui.uif2.tangramlayout.node.TBNode();
  context.put("nc.ui.uif2.tangramlayout.node.TBNode#14e4cb2",bean);
  bean.setShowMode("CardLayout");
  bean.setTabs(getManagedList21());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private List getManagedList21(){  List list = new ArrayList();  list.add(getVSNode_7d878d());  list.add(getCardPanel());  return list;}

private nc.ui.uif2.tangramlayout.node.VSNode getVSNode_7d878d(){
 if(context.get("nc.ui.uif2.tangramlayout.node.VSNode#7d878d")!=null)
 return (nc.ui.uif2.tangramlayout.node.VSNode)context.get("nc.ui.uif2.tangramlayout.node.VSNode#7d878d");
  nc.ui.uif2.tangramlayout.node.VSNode bean = new nc.ui.uif2.tangramlayout.node.VSNode();
  context.put("nc.ui.uif2.tangramlayout.node.VSNode#7d878d",bean);
  bean.setShowMode("NoDivider");
  bean.setUp(getCNode_536c1c());
  bean.setDown(getHSNode_d14aac());
  bean.setDividerLocation(30f);
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private nc.ui.uif2.tangramlayout.node.CNode getCNode_536c1c(){
 if(context.get("nc.ui.uif2.tangramlayout.node.CNode#536c1c")!=null)
 return (nc.ui.uif2.tangramlayout.node.CNode)context.get("nc.ui.uif2.tangramlayout.node.CNode#536c1c");
  nc.ui.uif2.tangramlayout.node.CNode bean = new nc.ui.uif2.tangramlayout.node.CNode();
  context.put("nc.ui.uif2.tangramlayout.node.CNode#536c1c",bean);
  bean.setComponent(getOrgPanel());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private nc.ui.uif2.tangramlayout.node.HSNode getHSNode_d14aac(){
 if(context.get("nc.ui.uif2.tangramlayout.node.HSNode#d14aac")!=null)
 return (nc.ui.uif2.tangramlayout.node.HSNode)context.get("nc.ui.uif2.tangramlayout.node.HSNode#d14aac");
  nc.ui.uif2.tangramlayout.node.HSNode bean = new nc.ui.uif2.tangramlayout.node.HSNode();
  context.put("nc.ui.uif2.tangramlayout.node.HSNode#d14aac",bean);
  bean.setLeft(getCNode_dee30f());
  bean.setRight(getCNode_10cf984());
  bean.setDividerLocation(0.2f);
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private nc.ui.uif2.tangramlayout.node.CNode getCNode_dee30f(){
 if(context.get("nc.ui.uif2.tangramlayout.node.CNode#dee30f")!=null)
 return (nc.ui.uif2.tangramlayout.node.CNode)context.get("nc.ui.uif2.tangramlayout.node.CNode#dee30f");
  nc.ui.uif2.tangramlayout.node.CNode bean = new nc.ui.uif2.tangramlayout.node.CNode();
  context.put("nc.ui.uif2.tangramlayout.node.CNode#dee30f",bean);
  bean.setComponent(getQueryAreaShell());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private nc.ui.uif2.tangramlayout.node.CNode getCNode_10cf984(){
 if(context.get("nc.ui.uif2.tangramlayout.node.CNode#10cf984")!=null)
 return (nc.ui.uif2.tangramlayout.node.CNode)context.get("nc.ui.uif2.tangramlayout.node.CNode#10cf984");
  nc.ui.uif2.tangramlayout.node.CNode bean = new nc.ui.uif2.tangramlayout.node.CNode();
  context.put("nc.ui.uif2.tangramlayout.node.CNode#10cf984",bean);
  bean.setComponent(getListView());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.ta.leave.pf.view.LeavePrimaryOrgPanel getOrgPanel(){
 if(context.get("orgPanel")!=null)
 return (nc.ui.ta.leave.pf.view.LeavePrimaryOrgPanel)context.get("orgPanel");
  nc.ui.ta.leave.pf.view.LeavePrimaryOrgPanel bean = new nc.ui.ta.leave.pf.view.LeavePrimaryOrgPanel();
  context.put("orgPanel",bean);
  bean.setModel(getAppModel());
  bean.setDataManager(getModelDataManager());
  bean.setPk_orgtype("HRORGTYPE00000000000");
  bean.initUI();
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.ta.leave.pf.view.LeaveListView getListView(){
 if(context.get("listView")!=null)
 return (nc.ui.ta.leave.pf.view.LeaveListView)context.get("listView");
  nc.ui.ta.leave.pf.view.LeaveListView bean = new nc.ui.ta.leave.pf.view.LeaveListView();
  context.put("listView",bean);
  bean.setModel(getAppModel());
  bean.setMultiSelectionEnable(true);
  bean.setMultiSelectionMode(1);
  bean.setTemplateContainer(getTemplateContainer());
  bean.setNodekey(getNodekey());
  bean.setDealHyperlink(true);
  bean.setNorth(getListToolBarPanel());
  bean.setPaginationBar(getPaginationBar());
  bean.initUI();
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.ta.leave.pf.view.LeaveCardForm getCardView(){
 if(context.get("cardView")!=null)
 return (nc.ui.ta.leave.pf.view.LeaveCardForm)context.get("cardView");
  nc.ui.ta.leave.pf.view.LeaveCardForm bean = new nc.ui.ta.leave.pf.view.LeaveCardForm();
  context.put("cardView",bean);
  bean.setModel(getAppModel());
  bean.setTemplateContainer(getTemplateContainer());
  bean.setNodekey(getNodekey());
  bean.setActions(getManagedList22());
  bean.setTabActions(getManagedList23());
  bean.initUI();
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private List getManagedList22(){  List list = new ArrayList();  list.add(getFirstLineAction());  list.add(getPreLineAction());  list.add(getNextLineAction());  list.add(getLastLineAction());  return list;}

private List getManagedList23(){  List list = new ArrayList();  list.add(getAddLineAction());  list.add(getInsertLineAction());  list.add(getDelLineAction());  list.add(getCopyLineAction());  list.add(getPasteLineAction());  return list;}

public nc.ui.uif2.tangramlayout.node.VSNode getCardPanel(){
 if(context.get("cardPanel")!=null)
 return (nc.ui.uif2.tangramlayout.node.VSNode)context.get("cardPanel");
  nc.ui.uif2.tangramlayout.node.VSNode bean = new nc.ui.uif2.tangramlayout.node.VSNode();
  context.put("cardPanel",bean);
  bean.setShowMode("NoDivider");
  bean.setUp(getCNode_18d515a());
  bean.setDown(getCNode_d824ed());
  bean.setDividerLocation(26f);
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private nc.ui.uif2.tangramlayout.node.CNode getCNode_18d515a(){
 if(context.get("nc.ui.uif2.tangramlayout.node.CNode#18d515a")!=null)
 return (nc.ui.uif2.tangramlayout.node.CNode)context.get("nc.ui.uif2.tangramlayout.node.CNode#18d515a");
  nc.ui.uif2.tangramlayout.node.CNode bean = new nc.ui.uif2.tangramlayout.node.CNode();
  context.put("nc.ui.uif2.tangramlayout.node.CNode#18d515a",bean);
  bean.setComponent(getEditorToolBarPanel());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private nc.ui.uif2.tangramlayout.node.CNode getCNode_d824ed(){
 if(context.get("nc.ui.uif2.tangramlayout.node.CNode#d824ed")!=null)
 return (nc.ui.uif2.tangramlayout.node.CNode)context.get("nc.ui.uif2.tangramlayout.node.CNode#d824ed");
  nc.ui.uif2.tangramlayout.node.CNode bean = new nc.ui.uif2.tangramlayout.node.CNode();
  context.put("nc.ui.uif2.tangramlayout.node.CNode#d824ed",bean);
  bean.setComponent(getCardView());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.uif2.FunNodeClosingHandler getClosingListener(){
 if(context.get("ClosingListener")!=null)
 return (nc.ui.uif2.FunNodeClosingHandler)context.get("ClosingListener");
  nc.ui.uif2.FunNodeClosingHandler bean = new nc.ui.uif2.FunNodeClosingHandler();
  context.put("ClosingListener",bean);
  bean.setModel(getAppModel());
  bean.setCancelaction(getCancelAction());
  bean.setSaveaction(getSaveAction());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public java.lang.String getBilltype(){
 if(context.get("billtype")!=null)
 return (java.lang.String)context.get("billtype");
  java.lang.String bean = new java.lang.String("6404");  context.put("billtype",bean);
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public java.lang.String getNodekey(){
 if(context.get("nodekey")!=null)
 return (java.lang.String)context.get("nodekey");
  java.lang.String bean = new java.lang.String("6017leaveapply_b");  context.put("nodekey",bean);
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public java.lang.String getQueryNodekey(){
 if(context.get("queryNodekey")!=null)
 return (java.lang.String)context.get("queryNodekey");
  java.lang.String bean = new java.lang.String("6017leaveapply_q");  context.put("queryNodekey",bean);
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public java.lang.String getPrintNodekey(){
 if(context.get("printNodekey")!=null)
 return (java.lang.String)context.get("printNodekey");
  java.lang.String bean = new java.lang.String("6017leaveapply_p");  context.put("printNodekey",bean);
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.ta.pub.action.EnableJudge getEnableJudge(){
 if(context.get("enableJudge")!=null)
 return (nc.ui.ta.pub.action.EnableJudge)context.get("enableJudge");
  nc.ui.ta.pub.action.EnableJudge bean = new nc.ui.ta.pub.action.EnableJudge();
  context.put("enableJudge",bean);
  bean.setModel(getAppModel());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.ta.leave.balance.view.LeaveQueryEditorListener getQueryEditorListener(){
 if(context.get("queryEditorListener")!=null)
 return (nc.ui.ta.leave.balance.view.LeaveQueryEditorListener)context.get("queryEditorListener");
  nc.ui.ta.leave.balance.view.LeaveQueryEditorListener bean = new nc.ui.ta.leave.balance.view.LeaveQueryEditorListener();
  context.put("queryEditorListener",bean);
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public java.lang.Integer getFunc_type(){
 if(context.get("func_type")!=null)
 return (java.lang.Integer)context.get("func_type");
  java.lang.Integer bean = new java.lang.Integer("4");  context.put("func_type",bean);
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.hr.pf.model.PFDefaultValueProvider getPfdefaultValueProvider(){
 if(context.get("pfdefaultValueProvider")!=null)
 return (nc.ui.hr.pf.model.PFDefaultValueProvider)context.get("pfdefaultValueProvider");
  nc.ui.hr.pf.model.PFDefaultValueProvider bean = new nc.ui.hr.pf.model.PFDefaultValueProvider();
  context.put("pfdefaultValueProvider",bean);
  bean.setModel(getAppModel());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.ta.leave.pf.PFAddLineDefaultValueProvider getPFAddLineDefaultValueProvider(){
 if(context.get("PFAddLineDefaultValueProvider")!=null)
 return (nc.ui.ta.leave.pf.PFAddLineDefaultValueProvider)context.get("PFAddLineDefaultValueProvider");
  nc.ui.ta.leave.pf.PFAddLineDefaultValueProvider bean = new nc.ui.ta.leave.pf.PFAddLineDefaultValueProvider();
  context.put("PFAddLineDefaultValueProvider",bean);
  bean.setModel(getAppModel());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.ta.leave.pf.action.PFAddSuckleLeaveAction getPFAddSuckleAction(){
 if(context.get("PFAddSuckleAction")!=null)
 return (nc.ui.ta.leave.pf.action.PFAddSuckleLeaveAction)context.get("PFAddSuckleAction");
  nc.ui.ta.leave.pf.action.PFAddSuckleLeaveAction bean = new nc.ui.ta.leave.pf.action.PFAddSuckleLeaveAction();
  context.put("PFAddSuckleAction",bean);
  bean.setDefaultValueProvider(getDefaultValueProvider());
  bean.setNcActionStatusJudge(getEnableJudge());
  bean.setFormEditor(getCardView());
  bean.setModel(getAppModel());
  bean.setValidator(getManagedList24());
  bean.setAddLineAction(getAddLineAction());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private List getManagedList24(){  List list = new ArrayList();  list.add(getOrgNotNullValidator());  return list;}

public nc.ui.ta.wf.batch.BatchAddAction getPFBatchAddAction(){
 if(context.get("PFBatchAddAction")!=null)
 return (nc.ui.ta.wf.batch.BatchAddAction)context.get("PFBatchAddAction");
  nc.ui.ta.wf.batch.BatchAddAction bean = new nc.ui.ta.wf.batch.BatchAddAction();
  context.put("PFBatchAddAction",bean);
  bean.setModel(getAppModel());
  bean.setNcActionStatusJudge(getEnableJudge());
  bean.setCardForm(getMultiCardView());
  bean.setListView(getListView());
  bean.setBillType(getBilltype());
  bean.setFunc_type(getFunc_type());
  bean.setFromApp(true);
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.ta.leave.pf.view.LeaveBatchBillInfoPanel getBillInfoPanel(){
 if(context.get("billInfoPanel")!=null)
 return (nc.ui.ta.leave.pf.view.LeaveBatchBillInfoPanel)context.get("billInfoPanel");
  nc.ui.ta.leave.pf.view.LeaveBatchBillInfoPanel bean = new nc.ui.ta.leave.pf.view.LeaveBatchBillInfoPanel();
  context.put("billInfoPanel",bean);
  bean.setRefBillForm(getCardView());
  bean.initUI();
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.ta.leave.pf.view.LeaveBatchBillGenPanel getBillGenPanel(){
 if(context.get("billGenPanel")!=null)
 return (nc.ui.ta.leave.pf.view.LeaveBatchBillGenPanel)context.get("billGenPanel");
  nc.ui.ta.leave.pf.view.LeaveBatchBillGenPanel bean = new nc.ui.ta.leave.pf.view.LeaveBatchBillGenPanel();
  context.put("billGenPanel",bean);
  bean.initUI();
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.ta.leave.pf.view.LeaveCardForm getMultiCardView(){
 if(context.get("multiCardView")!=null)
 return (nc.ui.ta.leave.pf.view.LeaveCardForm)context.get("multiCardView");
  nc.ui.ta.leave.pf.view.LeaveCardForm bean = new nc.ui.ta.leave.pf.view.LeaveCardForm();
  context.put("multiCardView",bean);
  bean.setModel(getMultiAddModel());
  bean.setTemplateContainer(getTemplateContainer());
  bean.setNodekey(getNodekey());
  bean.initUI();
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.ta.leave.pf.model.LeaveAppModel getMultiAddModel(){
 if(context.get("multiAddModel")!=null)
 return (nc.ui.ta.leave.pf.model.LeaveAppModel)context.get("multiAddModel");
  nc.ui.ta.leave.pf.model.LeaveAppModel bean = new nc.ui.ta.leave.pf.model.LeaveAppModel();
  context.put("multiAddModel",bean);
  bean.setApproveSite(false);
  bean.setBillType(getBilltype());
  bean.setContext(getContext());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.ta.pub.action.TAPFQueryExcecutor getQueryExcecutor(){
 if(context.get("queryExcecutor")!=null)
 return (nc.ui.ta.pub.action.TAPFQueryExcecutor)context.get("queryExcecutor");
  nc.ui.ta.pub.action.TAPFQueryExcecutor bean = new nc.ui.ta.pub.action.TAPFQueryExcecutor();
  context.put("queryExcecutor",bean);
  bean.setDataManager(getModelDataManager());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.vo.ta.leave.pf.validator.PFSaveLeaveValidator getPFValidateSave(){
 if(context.get("PFValidateSave")!=null)
 return (nc.vo.ta.leave.pf.validator.PFSaveLeaveValidator)context.get("PFValidateSave");
  nc.vo.ta.leave.pf.validator.PFSaveLeaveValidator bean = new nc.vo.ta.leave.pf.validator.PFSaveLeaveValidator();
  context.put("PFValidateSave",bean);
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.ta.leave.pf.action.PFAddLineLeaveAction getAddLineAction(){
 if(context.get("addLineAction")!=null)
 return (nc.ui.ta.leave.pf.action.PFAddLineLeaveAction)context.get("addLineAction");
  nc.ui.ta.leave.pf.action.PFAddLineLeaveAction bean = new nc.ui.ta.leave.pf.action.PFAddLineLeaveAction();
  context.put("addLineAction",bean);
  bean.setModel(getAppModel());
  bean.setCardPanel(getCardView());
  bean.setDefaultValueProvider(getPFAddLineDefaultValueProvider());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.ta.leave.pf.action.PFDelLineLeaveAction getDelLineAction(){
 if(context.get("DelLineAction")!=null)
 return (nc.ui.ta.leave.pf.action.PFDelLineLeaveAction)context.get("DelLineAction");
  nc.ui.ta.leave.pf.action.PFDelLineLeaveAction bean = new nc.ui.ta.leave.pf.action.PFDelLineLeaveAction();
  context.put("DelLineAction",bean);
  bean.setModel(getAppModel());
  bean.setCardPanel(getCardView());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.ta.leave.pf.action.PFInsertLineLeaveAction getInsertLineAction(){
 if(context.get("InsertLineAction")!=null)
 return (nc.ui.ta.leave.pf.action.PFInsertLineLeaveAction)context.get("InsertLineAction");
  nc.ui.ta.leave.pf.action.PFInsertLineLeaveAction bean = new nc.ui.ta.leave.pf.action.PFInsertLineLeaveAction();
  context.put("InsertLineAction",bean);
  bean.setModel(getAppModel());
  bean.setCardPanel(getCardView());
  bean.setDefaultValueProvider(getPFAddLineDefaultValueProvider());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.ta.leave.pf.action.PFCopyLineLeaveAction getCopyLineAction(){
 if(context.get("copyLineAction")!=null)
 return (nc.ui.ta.leave.pf.action.PFCopyLineLeaveAction)context.get("copyLineAction");
  nc.ui.ta.leave.pf.action.PFCopyLineLeaveAction bean = new nc.ui.ta.leave.pf.action.PFCopyLineLeaveAction();
  context.put("copyLineAction",bean);
  bean.setModel(getAppModel());
  bean.setCardPanel(getCardView());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.ta.leave.pf.action.PFPasteLineLeaveAction getPasteLineAction(){
 if(context.get("PasteLineAction")!=null)
 return (nc.ui.ta.leave.pf.action.PFPasteLineLeaveAction)context.get("PasteLineAction");
  nc.ui.ta.leave.pf.action.PFPasteLineLeaveAction bean = new nc.ui.ta.leave.pf.action.PFPasteLineLeaveAction();
  context.put("PasteLineAction",bean);
  bean.setModel(getAppModel());
  bean.setCardPanel(getCardView());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.ta.leave.pf.model.TALeavePFMetaDataDataSource getDatasource(){
 if(context.get("datasource")!=null)
 return (nc.ui.ta.leave.pf.model.TALeavePFMetaDataDataSource)context.get("datasource");
  nc.ui.ta.leave.pf.model.TALeavePFMetaDataDataSource bean = new nc.ui.ta.leave.pf.model.TALeavePFMetaDataDataSource();
  context.put("datasource",bean);
  bean.setModel(getAppModel());
  bean.setSingleData(true);
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.hr.uif2.action.print.ExportCardAction getCardOutputAction(){
 if(context.get("cardOutputAction")!=null)
 return (nc.ui.hr.uif2.action.print.ExportCardAction)context.get("cardOutputAction");
  nc.ui.hr.uif2.action.print.ExportCardAction bean = new nc.ui.hr.uif2.action.print.ExportCardAction();
  context.put("cardOutputAction",bean);
  bean.setModel(getAppModel());
  bean.setNcActionStatusJudge(getEnableJudge());
  bean.setPrintDlgParentConatiner(getCardView());
  bean.setDatasource(getDatasource());
  bean.setNodeKey(getPrintNodekey());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.funcnode.ui.action.GroupAction getAddActionGroup(){
 if(context.get("addActionGroup")!=null)
 return (nc.funcnode.ui.action.GroupAction)context.get("addActionGroup");
  nc.funcnode.ui.action.GroupAction bean = new nc.funcnode.ui.action.GroupAction();
  context.put("addActionGroup",bean);
  bean.setCode("add");
  bean.setName(getI18nFB_1bb227a());
  bean.setActions(getManagedList25());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private java.lang.String getI18nFB_1bb227a(){
 if(context.get("nc.ui.uif2.I18nFB#1bb227a")!=null)
 return (java.lang.String)context.get("nc.ui.uif2.I18nFB#1bb227a");
  nc.ui.uif2.I18nFB bean = new nc.ui.uif2.I18nFB();
    context.put("&nc.ui.uif2.I18nFB#1bb227a",bean);  bean.setResDir("common");
  bean.setDefaultValue("新增");
  bean.setResId("UC001-0000108");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
 try {
     Object product = bean.getObject();
    context.put("nc.ui.uif2.I18nFB#1bb227a",product);
     return (java.lang.String)product;
}
catch(Exception e) { throw new RuntimeException(e);}}

private List getManagedList25(){  List list = new ArrayList();  list.add(getAddAction());  list.add(getPFAddSuckleAction());  list.add(getPFBatchAddAction());  return list;}

public nc.funcnode.ui.action.GroupAction getCommitActionGroup(){
 if(context.get("commitActionGroup")!=null)
 return (nc.funcnode.ui.action.GroupAction)context.get("commitActionGroup");
  nc.funcnode.ui.action.GroupAction bean = new nc.funcnode.ui.action.GroupAction();
  context.put("commitActionGroup",bean);
  bean.setCode("commit");
  bean.setName(getI18nFB_d4b70c());
  bean.setActions(getManagedList26());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private java.lang.String getI18nFB_d4b70c(){
 if(context.get("nc.ui.uif2.I18nFB#d4b70c")!=null)
 return (java.lang.String)context.get("nc.ui.uif2.I18nFB#d4b70c");
  nc.ui.uif2.I18nFB bean = new nc.ui.uif2.I18nFB();
    context.put("&nc.ui.uif2.I18nFB#d4b70c",bean);  bean.setResDir("common");
  bean.setDefaultValue("提交");
  bean.setResId("UC001-0000029");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
 try {
     Object product = bean.getObject();
    context.put("nc.ui.uif2.I18nFB#d4b70c",product);
     return (java.lang.String)product;
}
catch(Exception e) { throw new RuntimeException(e);}}

private List getManagedList26(){  List list = new ArrayList();  list.add(getSubmitAction());  list.add(getCallBackAction());  list.add(getApproveNoteAction());  return list;}

public nc.funcnode.ui.action.MenuAction getAssistActionGroup(){
 if(context.get("assistActionGroup")!=null)
 return (nc.funcnode.ui.action.MenuAction)context.get("assistActionGroup");
  nc.funcnode.ui.action.MenuAction bean = new nc.funcnode.ui.action.MenuAction();
  context.put("assistActionGroup",bean);
  bean.setCode("assist");
  bean.setName(getI18nFB_17e17e6());
  bean.setActions(getManagedList27());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private java.lang.String getI18nFB_17e17e6(){
 if(context.get("nc.ui.uif2.I18nFB#17e17e6")!=null)
 return (java.lang.String)context.get("nc.ui.uif2.I18nFB#17e17e6");
  nc.ui.uif2.I18nFB bean = new nc.ui.uif2.I18nFB();
    context.put("&nc.ui.uif2.I18nFB#17e17e6",bean);  bean.setResDir("common");
  bean.setDefaultValue("辅助功能");
  bean.setResId("UC001-0000137");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
 try {
     Object product = bean.getObject();
    context.put("nc.ui.uif2.I18nFB#17e17e6",product);
     return (java.lang.String)product;
}
catch(Exception e) { throw new RuntimeException(e);}}

private List getManagedList27(){  List list = new ArrayList();  list.add(getFileManageAction());  return list;}

public nc.funcnode.ui.action.GroupAction getCardPrintGroup(){
 if(context.get("cardPrintGroup")!=null)
 return (nc.funcnode.ui.action.GroupAction)context.get("cardPrintGroup");
  nc.funcnode.ui.action.GroupAction bean = new nc.funcnode.ui.action.GroupAction();
  context.put("cardPrintGroup",bean);
  bean.setCode("cardprint");
  bean.setName(getI18nFB_1532e04());
  bean.setActions(getManagedList28());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

private java.lang.String getI18nFB_1532e04(){
 if(context.get("nc.ui.uif2.I18nFB#1532e04")!=null)
 return (java.lang.String)context.get("nc.ui.uif2.I18nFB#1532e04");
  nc.ui.uif2.I18nFB bean = new nc.ui.uif2.I18nFB();
    context.put("&nc.ui.uif2.I18nFB#1532e04",bean);  bean.setResDir("common");
  bean.setDefaultValue("打印");
  bean.setResId("UC001-0000007");
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
 try {
     Object product = bean.getObject();
    context.put("nc.ui.uif2.I18nFB#1532e04",product);
     return (java.lang.String)product;
}
catch(Exception e) { throw new RuntimeException(e);}}

private List getManagedList28(){  List list = new ArrayList();  list.add(getTemplatePrintAction());  list.add(getTemplatePreviewAction());  list.add(getCardOutputAction());  return list;}

public nc.ui.hr.uif2.validator.BillFormNotNullValidator getBillFormNotNullValidator(){
 if(context.get("billFormNotNullValidator")!=null)
 return (nc.ui.hr.uif2.validator.BillFormNotNullValidator)context.get("billFormNotNullValidator");
  nc.ui.hr.uif2.validator.BillFormNotNullValidator bean = new nc.ui.hr.uif2.validator.BillFormNotNullValidator();
  context.put("billFormNotNullValidator",bean);
  bean.setBillForm(getCardView());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.hr.pf.validator.PFSaveValidator getSaveValidator(){
 if(context.get("saveValidator")!=null)
 return (nc.ui.hr.pf.validator.PFSaveValidator)context.get("saveValidator");
  nc.ui.hr.pf.validator.PFSaveValidator bean = new nc.ui.hr.pf.validator.PFSaveValidator();
  context.put("saveValidator",bean);
  bean.setModel(getAppModel());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.ta.pub.model.TAInitDataListener getInitDataListener(){
 if(context.get("InitDataListener")!=null)
 return (nc.ui.ta.pub.model.TAInitDataListener)context.get("InitDataListener");
  nc.ui.ta.pub.model.TAInitDataListener bean = new nc.ui.ta.pub.model.TAInitDataListener();
  context.put("InitDataListener",bean);
  bean.setModel(getAppModel());
  bean.setDataManager(getModelDataManager());
  bean.setOrgPanel(getOrgPanel());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.uif2.components.pagination.PaginationBar getPaginationBar(){
 if(context.get("paginationBar")!=null)
 return (nc.ui.uif2.components.pagination.PaginationBar)context.get("paginationBar");
  nc.ui.uif2.components.pagination.PaginationBar bean = new nc.ui.uif2.components.pagination.PaginationBar();
  context.put("paginationBar",bean);
  bean.setPaginationModel(getPaginationModel());
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

public nc.ui.uif2.components.pagination.PaginationModel getPaginationModel(){
 if(context.get("paginationModel")!=null)
 return (nc.ui.uif2.components.pagination.PaginationModel)context.get("paginationModel");
  nc.ui.uif2.components.pagination.PaginationModel bean = new nc.ui.uif2.components.pagination.PaginationModel();
  context.put("paginationModel",bean);
  bean.setPaginationQueryService(getManageModelService());
  bean.init();
setBeanFacotryIfBeanFacatoryAware(bean);
invokeInitializingBean(bean);
return bean;
}

}
