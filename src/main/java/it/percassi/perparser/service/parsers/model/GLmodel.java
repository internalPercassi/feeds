package it.percassi.perparser.service.parsers.model;

/**
 *
 * @author Daniele Sperto
 */
public class GLmodel extends BaseModel{

	private String pertinencySite;
	private String pertinencySiteDesc;
	private String uniqueProductCode;
	private String depositor;
	private Integer stockedQty;
	private Integer bookedQty;
	private String accountingState;

	public void setPertinencySite(String pertinencySite) {
		this.pertinencySite = pertinencySite;
	}

	public void setPertinencySiteDesc(String pertinencySiteDesc) {
		this.pertinencySiteDesc = pertinencySiteDesc;
	}

	public void setUniqueProductCode(String uniqueProductCode) {
		this.uniqueProductCode = uniqueProductCode;
	}

	public void setDepositor(String depositor) {
		this.depositor = depositor;
	}

	public void setStockedQty(Integer stockedQty) {
		this.stockedQty = stockedQty;
	}

	public void setBookedQty(Integer bookedQty) {
		this.bookedQty = bookedQty;
	}

	public void setAccountingState(String accountingState) {
		this.accountingState = accountingState;
	}

	public String getPertinencySite() {
		return pertinencySite;
	}

	public String getPertinencySiteDesc() {
		return pertinencySiteDesc;
	}

	public String getUniqueProductCode() {
		return uniqueProductCode;
	}

	public String getDepositor() {
		return depositor;
	}

	public Integer getStockedQty() {
		return stockedQty;
	}

	public Integer getBookedQty() {
		return bookedQty;
	}

	public String getAccountingState() {
		return accountingState;
	}

	
}
