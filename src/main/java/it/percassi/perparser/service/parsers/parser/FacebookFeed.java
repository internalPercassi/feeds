package it.percassi.perparser.service.parsers.parser;

/**
 *
 * @author Daniele Sperto
 */
public class FacebookFeed extends BaseModel{
	
	public  static final String FIELD_SEPARATOR = "|";
	
	private String id;
	private String availability;
	private String condition = "new";
	private String description;
	private String imageLink;
	private String link;
	private String title;
	private String price;
	private String brand = "KIK";
	private String additionalImageLink;
	private String ageGroup;
	private String color;
	private String expirationDate;
	private String gender;
	private String itemGroupId;
	private String googleProductCategory="Health & Beauty > Personal Care > Cosmetics > Makeup > Eye Makeup > Mascara";
	private String material;
	private String pattern;
	private String productType;
	private String salePrice;
	private String shipping;
	private String shippingWeight;	
	private String customLabel0;
	private String customLabel1;
	private String customLabel2;
	private String customLabel3;
	private String customLabel4;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAvailability() {
		return availability;
	}

	public void setAvailability(String availability) {
		this.availability = availability;
	}

	public String getCondition() {
		return condition;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getImageLink() {
		return imageLink;
	}

	public void setImageLink(String imageLink) {
		this.imageLink = imageLink;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getAdditionalImageLink() {
		return additionalImageLink;
	}

	public void setAdditionalImageLink(String additionalImageLink) {
		this.additionalImageLink = additionalImageLink;
	}

	public String getAgeGroup() {
		return ageGroup;
	}

	public void setAgeGroup(String ageGroup) {
		this.ageGroup = ageGroup;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getExpirationDate() {
		return expirationDate;
	}

	public void setExpirationDate(String expirationDate) {
		this.expirationDate = expirationDate;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getItemGroupId() {
		return itemGroupId;
	}

	public void setItemGroupId(String itemGroupId) {
		this.itemGroupId = itemGroupId;
	}

	public String getGoogleProductCategory() {
		return googleProductCategory;
	}

	public void setGoogleProductCategory(String googleProductCategory) {
		this.googleProductCategory = googleProductCategory;
	}

	public String getMaterial() {
		return material;
	}

	public void setMaterial(String material) {
		this.material = material;
	}

	public String getPattern() {
		return pattern;
	}

	public void setPattern(String pattern) {
		this.pattern = pattern;
	}

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public String getSalePrice() {
		return salePrice;
	}

	public void setSalePrice(String salePrice) {
		this.salePrice = salePrice;
	}

	public String getShipping() {
		return shipping;
	}

	public void setShipping(String shipping) {
		this.shipping = shipping;
	}

	public String getShippingWeight() {
		return shippingWeight;
	}

	public void setShippingWeight(String shippingWeight) {
		this.shippingWeight = shippingWeight;
	}

	public String getCustomLabel0() {
		return customLabel0;
	}

	public void setCustomLabel0(String customLabel0) {
		this.customLabel0 = customLabel0;
	}

	public String getCustomLabel1() {
		return customLabel1;
	}

	public void setCustomLabel1(String customLabel1) {
		this.customLabel1 = customLabel1;
	}

	public String getCustomLabel2() {
		return customLabel2;
	}

	public void setCustomLabel2(String customLabel2) {
		this.customLabel2 = customLabel2;
	}

	public String getCustomLabel3() {
		return customLabel3;
	}

	public void setCustomLabel3(String customLabel3) {
		this.customLabel3 = customLabel3;
	}

	public String getCustomLabel4() {
		return customLabel4;
	}

	public void setCustomLabel4(String customLabel4) {
		this.customLabel4 = customLabel4;
	}		
}
