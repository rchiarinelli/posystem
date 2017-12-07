package br.valinorti.posystem.service.view.helper;


/**
 * 
 * @author Rafael Chiarinelli
 *
 */
public class ViewBeanHelper {

	private static PFCustomerViewBeanHelper pfCustomerHelper;
	
	private static RequestHelper requestHelper;
	
	private static RequestFilterHelper requestFilterHelper;
	
	private static JSONViewHelper jsonViewHelper;
	
	private static POViewHelper poViewHelper;
	
	private static CustomerAddressViewHelper customerAddressViewHelper;
	
	private static CustomerContactViewHelper customerContactViewHelper;
	
	private static PJCustomerViewBeanHelper pjCustomerViewBeanHelper;
	
	private static CustomerViewBeanHelper customerViewBeanHelper;
	
	private static ProcessBillingValuesViewBeanHelper proceValuesViewBeanHelper;
	
	private static BillingViewBeanHelper billingViewBeanHelper;
	
	private ViewBeanHelper(){
		
	}
	
	public static PFCustomerViewBeanHelper getPFCustomerHelper() {
		if (pfCustomerHelper==null) {
			pfCustomerHelper = new PFCustomerViewBeanHelper();
		}
		return pfCustomerHelper;
	}
	
	
	public static RequestHelper getRequestHelper(){
		if (requestHelper==null) {
			requestHelper = new RequestHelper();
		}
		return requestHelper;
	}
	
	public static RequestFilterHelper getRequestFilterHelper(){
		if (requestFilterHelper==null){
			requestFilterHelper = new RequestFilterHelper();
		}
		return requestFilterHelper;
	}
	/**
	 * 
	 * @return
	 */
	public static JSONViewHelper getJSONViewHelper() {
		if (jsonViewHelper==null) {
			jsonViewHelper = new JSONViewHelper();
		}
		return jsonViewHelper;
	}
	/**
	 * 
	 * @return
	 */
	public static POViewHelper getPOViewHelper() {
		if (poViewHelper==null) {
			poViewHelper = new POViewHelper();
		}
		return poViewHelper;
	}
	
	public static CustomerAddressViewHelper getCustomerAddressViewHelper(){
		if (customerAddressViewHelper==null) {
			customerAddressViewHelper = new CustomerAddressViewHelper();
		}
		return customerAddressViewHelper;
	}
	
	public static CustomerContactViewHelper getCustomerContactViewHelper(){
		if (customerContactViewHelper==null) {
			customerContactViewHelper = new CustomerContactViewHelper();
		}
		return customerContactViewHelper;
	}
	
	public static PJCustomerViewBeanHelper getPJCustomerViewBeanHelper() {
		if (pjCustomerViewBeanHelper==null) {
			pjCustomerViewBeanHelper = new PJCustomerViewBeanHelper();
		}
		return pjCustomerViewBeanHelper;
	}
	
	public static CustomerViewBeanHelper getCustomerViewBeanHelper() {
		if (customerViewBeanHelper==null) {
			customerViewBeanHelper = new CustomerViewBeanHelper();
		}
		return customerViewBeanHelper;
	}
	
	public static ProcessBillingValuesViewBeanHelper getProcessBillingValuesViewBeanHelper() {
		if (proceValuesViewBeanHelper==null) {
			proceValuesViewBeanHelper = new ProcessBillingValuesViewBeanHelper();
		}
		return proceValuesViewBeanHelper;
	}
	
	public static BillingViewBeanHelper getBillingViewBeanHelper() {
		if (billingViewBeanHelper==null) {
			billingViewBeanHelper = new BillingViewBeanHelper();
		}
		return billingViewBeanHelper;
	}
	
}
