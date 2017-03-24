package it.percassi.perparser.service.parsers.model;

/**
 *
 * @author Daniele Sperto
 */
public class GLmodel extends BaseModel{

	private String GL;
	private String INS;
	private String pertinencySite;
	private String pertinencySiteDesc;
	private String uniqueProductCode;
	private String depositor;
	private String stockedQty;
	private String bookedQty;
	private String accountingState;

	public void setGL(String GL) {
		this.GL = GL;
	}

	public void setINS(String INS) {
		this.INS = INS;
	}

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

	public void setStockedQty(String stockedQty) {
		this.stockedQty = stockedQty;
	}

	public void setBookedQty(String bookedQty) {
		this.bookedQty = bookedQty;
	}

	public void setAccountingState(String accountingState) {
		this.accountingState = accountingState;
	}

	public String getGL() {
		return GL;
	}

	public String getINS() {
		return INS;
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

	public String getStockedQty() {
		return stockedQty;
	}

	public String getBookedQty() {
		return bookedQty;
	}

	public String getAccountingState() {
		return accountingState;
	}

	
}
