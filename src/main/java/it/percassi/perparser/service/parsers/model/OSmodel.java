package it.percassi.perparser.service.parsers.model;

/**
 *
 * @author Daniele Sperto
 */
public class OSmodel extends BaseModel{

	private String productCode;
	private String modelCode;
	private String warehouse;
	private String physicalInventory;
	private String orderStatus;
	private String replenishmentLevel;
	public String getProductCode() {
		return productCode;
	}
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	public String getModelCode() {
		return modelCode;
	}
	public void setModelCode(String modelCode) {
		this.modelCode = modelCode;
	}
	public String getWarehouse() {
		return warehouse;
	}
	public void setWarehouse(String warehouse) {
		this.warehouse = warehouse;
	}
	public String getPhysicalInventory() {
		return physicalInventory;
	}
	public void setPhysicalInventory(String physicalInventory) {
		this.physicalInventory = physicalInventory;
	}
	public String getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}
	public String getReplenishmentLevel() {
		return replenishmentLevel;
	}
	public void setReplenishmentLevel(String replenishmentLevel) {
		this.replenishmentLevel = replenishmentLevel;
	}

	
}
