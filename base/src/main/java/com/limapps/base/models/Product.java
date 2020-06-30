package com.limapps.base.models;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import com.google.gson.annotations.SerializedName;
import com.limapps.base.helpers.ProductExtensionKt;
import com.limapps.base.others.SaleTypeConstants;
import com.limapps.base.others.calculators.GeneralCalculator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Product implements BaseProduct, Cloneable, Parcelable {

    private static final String ID_SEPARATOR = "_";

    public static String NO_IMAGE = "NO-IMAGE";

    public static final String DUMMY_ID = "dummyId";
    public static final List<Product> dummyProducts = Arrays.asList(new Product(DUMMY_ID),
            new Product(DUMMY_ID), new Product(DUMMY_ID));
    public static final List<Product> dummiesMarketProducts = Arrays.asList(new Product(DUMMY_ID),
            new Product(DUMMY_ID), new Product(DUMMY_ID), new Product(DUMMY_ID), new Product(DUMMY_ID),
            new Product(DUMMY_ID), new Product(DUMMY_ID), new Product(DUMMY_ID));

    @SerializedName(value = "cm_h", alternate = {"cm_height", "h"})
    private float h;

    @SerializedName(value = "cm_w", alternate = {"cm_width", "w"})
    private float w;

    @SerializedName("hook_l")
    private float hookL = 0;

    @SerializedName("hook_t")
    private float hookT = 0;

    @SerializedName("price")
    public double price;

    @SerializedName("real_price")
    public double realPrice;

    @SerializedName("have_discount")
    private boolean discount;

    @SerializedName(value = "balancePrice", alternate = "balance_price")
    public double balancePrice;

    public String saleTypeBasket = "";
    @SerializedName(value = "store_id", alternate = "storeId")
    public String storeId;
    public String priceLabel;
    public String label;
    public String displayType;
    public String category;

    public Offer offer;

    public boolean hasRecommendations = false;

    @SerializedName(value = "age_restriction", alternate = "requiresAgeVerification")
    public boolean requiresAgeVerification = false;

    @SerializedName("is_open_forever")
    private boolean isOpenForever;

    private List<ScheduleDatesProduct> schedules;

    public String displayQuantity;

    @SerializedName("quantity_basket")
    public int quantity;

    @SerializedName("quantity")
    public String quantityPresentation;

    @SerializedName("min_quantity_in_grams")
    public int minQuantityGrams;
    @SerializedName("max_quantity_in_grams")
    public int maxQuantityGrams;
    @SerializedName("step_quantity_in_grams")
    public int stepQuantity;
    public int initialQuantity;
    public int incrementer;

    @SerializedName("id")
    public String id;
    public float x;
    public float y;
    public float priceY;

    @SerializedName(value = "imageHigh", alternate = {"image"})
    public String image;

    @SerializedName("imageLow")
    public String imageLow;

    public String name;
    public String description;
    public String priceText;
    @SerializedName(value = "available", alternate = "is_available")
    public boolean available = true;
    public int units;
    public boolean hasRecommendation = false;

    @SerializedName("has_toppings")
    public boolean hasToppings = false;

    @SerializedName("need_topping")
    public boolean needToppings = false;

    public boolean hasAntismoking = false;

    @SerializedName("identification_required")
    public boolean identificationRequired;

    @SerializedName("sale_type")
    public String saleType;

    @SerializedName("need_image")
    public boolean needImage;

    @SerializedName("corridor_id")
    public String corridorId;

    public String corridorName;

    @SerializedName("subcorridor_id")
    public String subcorridorId;

    public String subcorridorName;

    @SerializedName("linear_id")
    public int linearId;

    public int zone = 1;

    @SerializedName("area_count")
    public int areaCount = 0;

    @SerializedName("total_price")
    public double totalPrice;

    @SerializedName("unit_price")
    public double unitPrice;

    @SerializedName("unit_type")
    public String unitType;

    public String presentation;

    @SerializedName("total_quantity")
    public float totalQuantity;

    public boolean removed = false;

    public String localImage;
    public String remoteImage;


    @SerializedName("comment_balance")
    public String comment;

    @SerializedName("topping_products")
    public List<Topping> toppingList = new ArrayList<>();

    @SerializedName("is_liked")
    public boolean isLiked;

    public ProductPivot pivot;

    public String rawImage;

    public String gridBackground;

    public String tablecloth;

    public boolean isRecommendedProduct;
    public boolean isCheckoutProduct;
    public int categoryId;
    public String categoryName;

    private boolean isFromCoupon;
    private transient boolean edit;
    private int previousHash = -1;
    private boolean isWhim;
    private String source;
    private int index;

    public Product() {
        setInitialQuantity(1);
        setIncrementer(1);
        saleType = SaleTypeConstants.UNIT;
    }

    public Product(String id) {
        this();
        this.id = id;
    }

    public Product(String id, String name, double price) {
        this();
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public Product(int id, String name, double price) {
        this();
        this.id = String.valueOf(id);
        this.name = name;
        this.price = price;
    }

    public Product(Product product, int quantity) {
        this();
        id = product.id;
        name = product.name;
        unitPrice = product.unitPrice;
        price = product.price;
        image = product.image;
        hasRecommendation = product.hasRecommendation;
        description = product.description;
        priceLabel = product.priceLabel;
        requiresAgeVerification = product.requiresAgeVerification;
        label = product.label;
        this.quantity = quantity;
        saleType = product.saleType;
        minQuantityGrams = product.minQuantityGrams;
        maxQuantityGrams = product.maxQuantityGrams;
        stepQuantity = product.stepQuantity;
        balancePrice = product.balancePrice;
        incrementer = product.incrementer;
        w = product.w;
        h = product.h;
        previousHash = product.previousHash;
        isWhim = product.isWhim;
        quantityPresentation = product.quantityPresentation;
        isLiked = product.isLiked;
        index = product.index;
    }

    public Product(Cursor cursor) {

        final int columnId = 0;
        final int columnName = 1;
        final int columnPrice = 2;
        final int columnImage = 3;
        final int columnHasRecommendations = 4;
        final int columnDescription = 5;
        final int columnPriceLabel = 6;
        final int columnAgeVerification = 7;
        final int columnLabel = 8;
        final int columnSaleType = 9;
        final int columnMinQuantityGrams = 10;
        final int columnMaxQuantityGrams = 11;
        final int columnStepQuantityGrams = 12;
        final int columnBalancePrice = 13;

        id = cursor.getString(columnId);
        name = cursor.getString(columnName);
        unitPrice = cursor.getFloat(columnPrice);
        price = cursor.getFloat(columnPrice);
        image = cursor.getString(columnImage);
        hasRecommendation = cursor.getInt(columnHasRecommendations) == 1;
        description = cursor.getString(columnDescription);
        priceLabel = cursor.getString(columnPriceLabel);
        requiresAgeVerification = cursor.getInt(columnAgeVerification) == 1;
        label = cursor.getString(columnLabel);
        quantity = 0;
        saleType = cursor.getString(columnSaleType);
        minQuantityGrams = cursor.getInt(columnMinQuantityGrams);
        maxQuantityGrams = cursor.getInt(columnMaxQuantityGrams);
        stepQuantity = cursor.getInt(columnStepQuantityGrams);
        balancePrice = cursor.getDouble(columnBalancePrice);
        incrementer = 1;
        initialQuantity = 1;
    }

    public Product(String id, int quantity, String category, String image, float w, float h, boolean available) {
        this.h = h;
        this.w = w;
        this.category = category;
        this.quantity = quantity;
        this.id = id;
        this.image = image;
        this.available = available;
    }

    protected Product(Parcel in) {
        h = in.readFloat();
        w = in.readFloat();
        hookL = in.readFloat();
        hookT = in.readFloat();
        price = in.readDouble();
        realPrice = in.readDouble();
        balancePrice = in.readDouble();
        saleTypeBasket = in.readString();
        storeId = in.readString();
        priceLabel = in.readString();
        label = in.readString();
        displayType = in.readString();
        category = in.readString();
        offer = in.readParcelable(Offer.class.getClassLoader());
        hasRecommendations = in.readByte() != 0;
        requiresAgeVerification = in.readByte() != 0;
        displayQuantity = in.readString();
        quantity = in.readInt();
        minQuantityGrams = in.readInt();
        maxQuantityGrams = in.readInt();
        stepQuantity = in.readInt();
        initialQuantity = in.readInt();
        incrementer = in.readInt();
        id = in.readString();
        x = in.readFloat();
        y = in.readFloat();
        priceY = in.readFloat();
        image = in.readString();
        imageLow = in.readString();
        name = in.readString();
        description = in.readString();
        priceText = in.readString();
        available = in.readByte() != 0;
        units = in.readInt();
        hasRecommendation = in.readByte() != 0;
        hasToppings = in.readByte() != 0;
        needToppings = in.readByte() != 0;
        hasAntismoking = in.readByte() != 0;
        saleType = in.readString();
        needImage = in.readByte() != 0;
        corridorId = in.readString();
        subcorridorId = in.readString();
        linearId = in.readInt();
        zone = in.readInt();
        areaCount = in.readInt();
        totalPrice = in.readDouble();
        unitPrice = in.readDouble();
        unitType = in.readString();
        presentation = in.readString();
        totalQuantity = in.readFloat();
        removed = in.readByte() != 0;
        localImage = in.readString();
        remoteImage = in.readString();
        comment = in.readString();
        rawImage = in.readString();
        isFromCoupon = in.readByte() != 0;
        previousHash = in.readInt();
        isWhim = in.readByte() != 0;
        source = in.readString();
        quantityPresentation = in.readString();
        isLiked = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeFloat(h);
        dest.writeFloat(w);
        dest.writeFloat(hookL);
        dest.writeFloat(hookT);
        dest.writeDouble(price);
        dest.writeDouble(realPrice);
        dest.writeDouble(balancePrice);
        dest.writeString(saleTypeBasket);
        dest.writeString(storeId);
        dest.writeString(priceLabel);
        dest.writeString(label);
        dest.writeString(displayType);
        dest.writeString(category);
        dest.writeParcelable(offer, flags);
        dest.writeByte((byte) (hasRecommendations ? 1 : 0));
        dest.writeByte((byte) (requiresAgeVerification ? 1 : 0));
        dest.writeString(displayQuantity);
        dest.writeInt(quantity);
        dest.writeInt(minQuantityGrams);
        dest.writeInt(maxQuantityGrams);
        dest.writeInt(stepQuantity);
        dest.writeInt(initialQuantity);
        dest.writeInt(incrementer);
        dest.writeString(id);
        dest.writeFloat(x);
        dest.writeFloat(y);
        dest.writeFloat(priceY);
        dest.writeString(image);
        dest.writeString(imageLow);
        dest.writeString(name);
        dest.writeString(description);
        dest.writeString(priceText);
        dest.writeByte((byte) (available ? 1 : 0));
        dest.writeInt(units);
        dest.writeByte((byte) (hasRecommendation ? 1 : 0));
        dest.writeByte((byte) (hasToppings ? 1 : 0));
        dest.writeByte((byte) (needToppings ? 1 : 0));
        dest.writeByte((byte) (hasAntismoking ? 1 : 0));
        dest.writeString(saleType);
        dest.writeByte((byte) (needImage ? 1 : 0));
        dest.writeString(corridorId);
        dest.writeString(subcorridorId);
        dest.writeInt(linearId);
        dest.writeInt(zone);
        dest.writeInt(areaCount);
        dest.writeDouble(totalPrice);
        dest.writeDouble(unitPrice);
        dest.writeString(unitType);
        dest.writeString(presentation);
        dest.writeFloat(totalQuantity);
        dest.writeByte((byte) (removed ? 1 : 0));
        dest.writeString(localImage);
        dest.writeString(remoteImage);
        dest.writeString(comment);
        dest.writeString(rawImage);
        dest.writeByte((byte) (isFromCoupon ? 1 : 0));
        dest.writeInt(previousHash);
        dest.writeByte((byte) (isWhim ? 1 : 0));
        dest.writeString(source);
        dest.writeString(quantityPresentation);
        dest.writeByte((byte) (isLiked ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Product> CREATOR = new Creator<Product>() {
        @Override
        public Product createFromParcel(Parcel in) {
            return new Product(in);
        }

        @Override
        public Product[] newArray(int size) {
            return new Product[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getW() {
        return w;
    }

    public void setW(float w) {
        this.w = w;
    }

    public float getH() {
        return h;
    }

    public void setH(float h) {
        this.h = h;
    }

    public float getPriceY() {
        return priceY;
    }

    public void setPriceY(float priceY) {
        this.priceY = priceY;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPriceText() {
        return priceText;
    }

    public void setPrice(String priceText) {
        this.priceText = priceText;
    }

    public double getRealPrice() {
        return realPrice;
    }

    public void setRealPrice(double realPrice) {
        this.realPrice = realPrice;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public boolean increment() {
        return increment(quantity);
    }

    public boolean increment(int quantity) {
        boolean wasIncremented = false;
        if (ProductExtensionKt.canBeIncremented(this)) {
            int actualQuantity = getQuantity();
            setQuantity(actualQuantity + quantity);
            wasIncremented = true;
        }
        return wasIncremented;
    }

    public int getUnits() {
        return units;
    }

    public void setUnits(int units) {
        this.units = units;
    }

    public String getSaleType() {
        return saleType;
    }

    @Override
    public Product clone() {
        try {
            return (Product) super.clone();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean remove() {
        return false;
    }


    public double getPriceValue() {
        return price;
    }


    public int getQuantity() {
        return quantity;
    }

    public boolean isDecrementAvailable() {
        return (quantity - incrementer) >= initialQuantity;
    }

    public String getCategory(String defaultCategory) {
        return category != null ? category : defaultCategory;
    }

    public boolean decrement() {
        boolean decrement = false;
        if (getQuantity() > 1) {
            setQuantity(getQuantity() - getIncrementer());
            decrement = true;
        }
        return decrement;
    }

    public void setSaleType(String saleType) {
        List<String> validSaleType = SaleTypeConstants.INSTANCE.getSaleTypes();
        if (validSaleType.contains(saleType)) {
            this.saleType = saleType;
        } else {
            this.saleType = validSaleType.get(0);
        }
    }

    //This method is used to avoid overwrite the actual saleType
    public void setSaleTypeBasket(String saleTypeBasket) {
        this.saleTypeBasket = saleTypeBasket;
    }

    public String getSaleTypeBasket() {
        if (TextUtils.isEmpty(saleTypeBasket)) {
            saleTypeBasket = saleType;
        }
        return saleTypeBasket;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getStoreId() {
        if (TextUtils.isEmpty(storeId) && id != null) {
            String storeIdArray[] = id.split("_");
            if (storeIdArray.length == 2) {
                return storeIdArray[0];
            }
        } else {
            return storeId;
        }

        return "";
    }

    public boolean isValid() {
        return id != null;
    }

    public String getPriceLabel() {
        return priceLabel;
    }

    public void setPriceLabel(String priceLabel) {
        this.priceLabel = priceLabel;
    }

    public float getHookL() {
        return hookL;
    }

    public void setHookL(float hookL) {
        this.hookL = hookL;
    }

    public float getHookT() {
        return hookT;
    }

    public void setHookT(float hookT) {
        this.hookT = hookT;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getDisplayQuantity() {
        return displayQuantity;
    }

    public void setDisplayQuantity(String displayQuantity) {
        this.displayQuantity = displayQuantity;
    }

    public String getDisplayType() {
        return displayType;
    }

    public void setDisplayType(String displayType) {
        this.displayType = displayType;
    }

    public boolean isRequiresAgeVerification() {
        return requiresAgeVerification;
    }

    public void setRequiresAgeVerification(boolean requiresAgeVerification) {
        this.requiresAgeVerification = requiresAgeVerification;
    }

    public boolean isHasRecommendations() {
        return hasRecommendations;
    }

    public void setHasRecommendations(boolean hasRecommendations) {
        this.hasRecommendations = hasRecommendations;
    }

    public int getStepQuantity() {
        return stepQuantity;
    }

    public void setStepQuantity(int stepQuantity) {
        this.stepQuantity = stepQuantity;
    }

    public int getMaxQuantityGrams() {
        return maxQuantityGrams;
    }

    public void setMaxQuantityGrams(int maxQuantityGrams) {
        this.maxQuantityGrams = maxQuantityGrams;
    }

    public int getMinQuantityGrams() {
        return minQuantityGrams;
    }

    public void setMinQuantityGrams(int minQuantityGrams) {
        this.minQuantityGrams = minQuantityGrams;
    }

    public boolean isWeightProduct() {
        return saleType != null && (saleType.equalsIgnoreCase(SaleTypeConstants.WEIGHT_BOTH) ||
                saleType.equalsIgnoreCase(SaleTypeConstants.WEIGHT_WEIGHT));
    }

    public boolean shouldShowBascule() {
        return saleType != null && (saleType.equalsIgnoreCase(SaleTypeConstants.WEIGHT_WEIGHT) ||
                saleType.equalsIgnoreCase(SaleTypeConstants.WEIGHT_UNIT) ||
                saleType.equalsIgnoreCase(SaleTypeConstants.WEIGHT_BOTH) ||
                saleType.equalsIgnoreCase(SaleTypeConstants.TRAY));
    }

    public Offer getOffer() {
        return offer;
    }

    public void setOffer(Offer offer) {
        this.offer = offer;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public double getRawPrice() {
        return price;
    }

    public void setUnitPrice(double price) {
        this.price = price;
    }

    public double getBalancePrice() {
        return balancePrice;
    }

    public void setBalancePrice(double balancePrice) {
        this.balancePrice = balancePrice;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getInitialQuantity() {
        return initialQuantity;
    }

    public void setInitialQuantity(int initialQuantity) {
        this.initialQuantity = initialQuantity;
    }

    public int getIncrementer() {
        return incrementer;
    }

    public void setIncrementer(int incrementer) {
        this.incrementer = incrementer;
    }

    public String getCorridorId() {
        return corridorId;
    }

    public void setCorridorId(String corridorId) {
        this.corridorId = corridorId;
    }


    public void setHasToppings(boolean hasToppings) {
        this.hasToppings = hasToppings;
    }

    public boolean hasToppings() {
        return hasToppings;
    }

    public boolean isNeedImage() {
        return needImage;
    }

    public void setNeedImage(boolean needImage) {
        this.needImage = needImage;
    }

    public boolean needsToppings() {
        return needToppings;
    }

    public void setNeedToppings(boolean needToppings) {
        this.needToppings = needToppings;
    }

    public boolean isHasAntismoking() {
        return hasAntismoking;
    }

    public void setHasAntismoking(boolean hasAntismoking) {
        this.hasAntismoking = hasAntismoking;
    }

    public String getImageLow() {
        return imageLow;
    }

    public String getOrderSaleType() {
        switch (saleTypeBasket) {
            case "":
                return saleType;
            default:
                return saleTypeBasket;
        }
    }

    public ProductPivot getPivot() {
        return pivot;
    }

    public void setPivot(ProductPivot pivot) {
        this.pivot = pivot;
    }

    public List<Topping> getToppingsList() {
        if (toppingList == null) {
            toppingList = Collections.emptyList();
        }
        return toppingList;
    }

    public void setToppingList(List<Topping> toppingList) {
        this.toppingList = toppingList;
    }

    public boolean isFromCoupon() {
        return isFromCoupon;
    }

    public void setFromCoupon(boolean fromCoupon) {
        isFromCoupon = fromCoupon;
    }

    public boolean isEdit() {
        return edit;
    }

    public void setEdit(boolean edit) {
        this.edit = edit;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public int getPreviousHash() {
        return previousHash;
    }

    public void setPreviousHash(int previousHash) {
        this.previousHash = previousHash;
    }

    @Override
    public boolean equals(Object obj) {
        boolean equal = false;
        if (obj instanceof Product) {
            Product product = (Product) obj;
            equal = getCorrectProductId(product.id).equals(getCorrectProductId(this.id))
                    && product.hasToppings() == this.hasToppings()
                    && product.needsToppings() == this.needsToppings();
            if (product.getComment() != null) {
                equal = equal && product.getComment().equals(this.comment);
            }
            if (product.getToppingsList() != null) {
                equal = equal && product.getToppingsList().equals(this.toppingList);
            }
            if (product.getSaleTypeBasket() != null) {
                equal = equal && product.getSaleTypeBasket().equals(getSaleTypeBasket());
            }
        }
        return equal;
    }

    @Override
    public int hashCode() {
        int productHash = getCorrectProductId(id).hashCode();
        int productIntermediateHash = toppingList != null && !toppingList.isEmpty() ?
                productHash + toppingList.hashCode() :
                productHash;

        return comment != null && !TextUtils.isEmpty(comment) ? productIntermediateHash +
                comment.hashCode() : productIntermediateHash;
    }

    public static String getCorrectProductId(String productId) {
        if (productId != null) {
            int index = productId.indexOf(ID_SEPARATOR);
            if (index >= 0) {
                productId = productId.substring(index + 1);
            }
        }
        return productId;
    }

    public static String getCorrectStoreId(String productId) {
        if (productId != null) {
            int index = productId.indexOf(ID_SEPARATOR);
            if (index >= 0) {
                productId = productId.substring(0, index);
            }
        }
        return productId;
    }

    public boolean hasImage() {
        return image != null && !image.equals(NO_IMAGE);
    }

    public boolean isOpenForever() {
        return isOpenForever;
    }

    public List<ScheduleDatesProduct> getSchedules() {
        return schedules == null ? Collections.<ScheduleDatesProduct>emptyList() : schedules;
    }

    public boolean hasDiscount() {
        return price < realPrice;
    }

    public double getDiscount() {
        return GeneralCalculator.Companion.getDiscountProduct(quantity, offer, price);
    }

    public boolean isWhim() {
        return isWhim;
    }

    public void setWhim(boolean whim) {
        isWhim = whim;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }


    public boolean isSaleTypeWeightBoth() {
        return saleType.equals(SaleTypeConstants.WEIGHT_BOTH);
    }

    public boolean isDiscount() {
        return discount;
    }

    public boolean isIdentificationRequired() {
        return identificationRequired;
    }

    public void setIdentificationRequired(boolean identificationRequired) {
        this.identificationRequired = identificationRequired;
    }

    @Override
    public double getPrice() {
        return price;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
