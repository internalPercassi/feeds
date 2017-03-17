package it.percassi.parsers.products.model;

/**
 *
 * @author Daniele Sperto
 */
public enum AvailabilityEnum {
	INSTOCK("in stock"),
	OUTOFSTOCK("out of stock");	

	private String fbCode;

	private AvailabilityEnum(String fbCode) {
		this.fbCode = fbCode;
	}

	public String getFbCode() {
		return fbCode;
	}
	
	public static AvailabilityEnum fromString(String fbCode) {
    for (AvailabilityEnum b : AvailabilityEnum.values()) {
      if (b.name().equalsIgnoreCase(fbCode)) {
        return b;
      }
    }
    return OUTOFSTOCK;
  }
}
