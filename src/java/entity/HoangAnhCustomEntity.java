/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

import entity.codebaseOld.Color;
import entity.codebaseOld.Size;
import entity.codebaseOld.Tag;
import entity.codebaseNew.Role;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * Hoang Anh Dep Trai
 */
public class HoangAnhCustomEntity {

    public static class ProductListDTO {

        private int productId;
        private String productName;
        private double productPrice;
        private String productThumb;
        private double star;
        private int countFeedBack;
        private int subCategoryId;
        private String subCategoryName;
        private List<Tag> tagList;
        private String status;
        private int soldItem;
        private String des;

        public ProductListDTO() {
        }

        public ProductListDTO(int productId, String productName, double productPrice, String productThumb, double star, int countFeedBack, int subCategoryId, String subCategoryName, List<Tag> tagList, String status, int soldItem) {
            this.productId = productId;
            this.productName = productName;
            this.productPrice = productPrice;
            this.productThumb = productThumb;
            this.star = star;
            this.countFeedBack = countFeedBack;
            this.subCategoryId = subCategoryId;
            this.subCategoryName = subCategoryName;
            this.tagList = tagList;
            this.status = status;
            this.soldItem = soldItem;
        }

        public ProductListDTO(int productId, String productName,
                double productPrice, String productThumb,
                String subCategoryName, double star, List<Tag> list,
                int countFeedBack) {
            this.productId = productId;
            this.productName = productName;
            this.productPrice = productPrice;
            this.productThumb = productThumb;
            this.subCategoryName = subCategoryName;
            this.star = star;
            this.tagList = list;
            this.countFeedBack = countFeedBack;
        }

        public ProductListDTO(int productId, String productName, double productPrice, String productThumb, double star, int countFeedBack, String subCategoryName, List<Tag> tagList, int subCategoryId) {
            this.productId = productId;
            this.productName = productName;
            this.productPrice = productPrice;
            this.productThumb = productThumb;
            this.star = star;
            this.countFeedBack = countFeedBack;
            this.subCategoryName = subCategoryName;
            this.tagList = tagList;
            this.subCategoryId = subCategoryId;
        }

        public String getDes() {
            return des;
        }

        public void setDes(String des) {
            this.des = des;
        }

        public int getSoldItem() {
            return soldItem;
        }

        public void setSoldItem(int soldItem) {
            this.soldItem = soldItem;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public int getSubCategoryId() {
            return subCategoryId;
        }

        public ProductListDTO setSubCategoryId(int subCategoryId) {
            this.subCategoryId = subCategoryId;
            return this;
        }

        public double getStar() {
            return star;
        }

        public ProductListDTO setStar(double star) {
            this.star = star;
            return this;
        }

        public int getProductId() {
            return productId;
        }

        public String getProductName() {
            return productName;
        }

        public double getProductPrice() {
            return productPrice;
        }

        public String getProductThumb() {
            return productThumb;
        }

        public String getSubCategoryName() {
            return subCategoryName;
        }

        public ProductListDTO setProductId(int productId) {
            this.productId = productId;
            return this;
        }

        public ProductListDTO setProductName(String productName) {
            this.productName = productName;
            return this;
        }

        public ProductListDTO setProductPrice(double productPrice) {
            this.productPrice = productPrice;
            return this;
        }

        public ProductListDTO setProductThumb(String productThumb) {
            this.productThumb = productThumb;
            return this;
        }

        public ProductListDTO setSubCategoryName(String subCategoryName) {
            this.subCategoryName = subCategoryName;
            return this;
        }

        public List<Tag> getTagList() {
            return tagList;
        }

        public ProductListDTO setTagList(List<Tag> tagList) {
            this.tagList = tagList;
            return this;
        }

        public int getCountFeedBack() {
            return countFeedBack;
        }

        public ProductListDTO setCountFeedBack(int countFeedBack) {
            this.countFeedBack = countFeedBack;
            return this;
        }

        @Override
        public String toString() {
            return "ProductListDTO{" + "productId=" + productId + ", productName=" + productName + ", productPrice=" + productPrice + ", productThumb=" + productThumb + ", star=" + star + ", countFeedBack=" + countFeedBack + ", subCategoryId=" + subCategoryId + ", subCategoryName=" + subCategoryName + ", tagList=" + tagList + ", status=" + status + ", soldItem=" + soldItem + '}';
        }

    }

    public static class ProductDetailDTO extends ProductListDTO {

        private List productImage;
        private String productDescription;

        public ProductDetailDTO() {
        }

        public ProductDetailDTO(List productImage, String productDescription) {
            this.productImage = productImage;
            this.productDescription = productDescription;
        }

        public ProductDetailDTO(List productImage, int productId, String productName, double productPrice, String productThumb, String subCategoryName, double star, List<Tag> list, int countFeedBack, String productDescription) {
            super(productId, productName, productPrice, productThumb, subCategoryName, star, list, countFeedBack);
            this.productImage = productImage;
            this.productDescription = productDescription;
        }

        public String getProductDescription() {
            return productDescription;
        }

        public void setProductDescription(String productDescription) {
            this.productDescription = productDescription;
        }

        public List getProductImage() {
            return productImage;
        }

        public void setProductImage(List productImage) {
            this.productImage = productImage;
        }

        @Override
        public String toString() {
            return "ProductDetailDTO{" + super.toString() + "productImage=" + productImage + '}';
        }

    }

    public static class QuantityTracker {

        private int colorId;
        private int sizeId;
        private int quantity;

        public QuantityTracker() {
        }

        public QuantityTracker(int colorId, int sizeId, int quantity) {
            this.colorId = colorId;
            this.sizeId = sizeId;
            this.quantity = quantity;
        }

        public int getColorId() {
            return colorId;
        }

        public void setColorId(int colorId) {
            this.colorId = colorId;
        }

        public int getSizeId() {
            return sizeId;
        }

        public void setSizeId(int sizeId) {
            this.sizeId = sizeId;
        }

        @Override
        public String toString() {
            return "QuantityTracker{" + "colorId=" + colorId + ", sizeId=" + sizeId + ", quantity=" + quantity + '}';
        }

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }

    }

    public static class ProductDetailAddOnAttribute {

        private List<Color> productColor;
        private List<Size> productSize;
        private List<QuantityTracker> quantity;

        public ProductDetailAddOnAttribute() {
            this.productColor = new ArrayList<>();
            this.productSize = new ArrayList<>();
            this.quantity = new ArrayList<>();
        }

//        @Override
//        public boolean equals(Object otherProductDetailAddOnAttribute) {
//            if (!(otherProductDetailAddOnAttribute instanceof ProductDetailAddOnAttribute)) {
//                return false;
//            }
//            ProductDetailAddOnAttribute o = (ProductDetailAddOnAttribute) otherProductDetailAddOnAttribute;
//            
//            for(Color c : this.productColor){
//                
//            }
//            
//            return this.id == o.id && this.size.equalsIgnoreCase(o.size) && this.colorName.equalsIgnoreCase(o.colorName);
//        }
        public ProductDetailAddOnAttribute(List<Color> productColor, List<Size> productSize, List<QuantityTracker> quantity) {
            this.productColor = productColor;
            this.productSize = productSize;
            this.quantity = quantity;
        }

        public List<Color> getProductColor() {
            return productColor;
        }

        public void setProductColor(List<Color> productColor) {
            this.productColor = productColor;
        }

        public List<Size> getProductSize() {
            return productSize;
        }

        public void setProductSize(List<Size> productSize) {
            this.productSize = productSize;
        }

        public List<QuantityTracker> getQuantity() {
            return quantity;
        }

        public void setQuantity(List<QuantityTracker> quantity) {
            this.quantity = quantity;
        }

        @Override
        public String toString() {
            return "ProductDetailAddOnAttribute{" + "productColor=" + productColor + ", productSize=" + productSize + ", quantity=" + quantity + '}';
        }

    }

    public static class FeedbackDetails {

        private int id;
        private String username;
        private int star;
        private String comment;
        private String createAt;
        private String status;

        public FeedbackDetails() {
        }

        public FeedbackDetails(int id, String username, int star, String comment, String createAt, String status) {
            this.id = id;
            this.username = username;
            this.star = star;
            this.comment = comment;
            this.createAt = createAt;
            this.status = status;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public int getStar() {
            return star;
        }

        public void setStar(int star) {
            this.star = star;
        }

        public String getComment() {
            return comment;
        }

        public void setComment(String comment) {
            this.comment = comment;
        }

        public String getCreateAt() {
            return createAt;
        }

        public void setCreateAt(String createAt) {
            this.createAt = createAt;
        }

        @Override
        public String toString() {
            return "FeedbackDetails{" + "id=" + id + ", username=" + username + ", star=" + star + ", comment=" + comment + ", createAt=" + createAt + '}';
        }
    }

    public static class FeedbackMedia {

        private int id;
        private int feedbackId;
        private String Link;

        public FeedbackMedia() {
        }

        public FeedbackMedia(int id, int feedbackId, String Link) {
            this.id = id;
            this.feedbackId = feedbackId;
            this.Link = Link;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getFeedbackId() {
            return feedbackId;
        }

        public void setFeedbackId(int feedbackId) {
            this.feedbackId = feedbackId;
        }

        public String getLink() {
            return Link;
        }

        public void setLink(String Link) {
            this.Link = Link;
        }

        @Override
        public String toString() {
            return "FeedbackMedia{" + "id=" + id + ", feedbackId=" + feedbackId + ", Link=" + Link + '}';
        }
    }

    public static class PaymentMethod {

        private int id;
        private String method;

        public PaymentMethod() {
        }

        public PaymentMethod(int id, String method) {
            this.id = id;
            this.method = method;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getMethod() {
            return method;
        }

        public void setMethod(String method) {
            this.method = method;
        }

        @Override
        public String toString() {
            return "PaymentMethod{" + "id=" + id + ", method=" + method + '}';
        }
    }

    public static class ShipMethod {

        private int id;
        private String method;

        public ShipMethod() {
        }

        public ShipMethod(int id, String method) {
            this.id = id;
            this.method = method;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getMethod() {
            return method;
        }

        public void setMethod(String method) {
            this.method = method;
        }

        @Override
        public String toString() {
            return "ShipMethod{" + "id=" + id + ", method=" + method + '}';
        }

    }

    public static class Order {

        private int id;
        private int productId;
        private String productName;
        private String thumbnailImage;
        private int quantity;
        private double totalPrice;
        private Color color;
        private Size size;
        private boolean isFeedbacked;

        public Order() {
        }

        public Order(int id, int productId, String productName, String thumbnailImage, int quantity, double totalPrice, Color color, Size size, boolean isFeedbacked) {
            this.id = id;
            this.productId = productId;
            this.productName = productName;
            this.thumbnailImage = thumbnailImage;
            this.quantity = quantity;
            this.totalPrice = totalPrice;
            this.color = color;
            this.size = size;
            this.isFeedbacked = isFeedbacked;
        }

        public boolean isIsFeedbacked() {
            return isFeedbacked;
        }

        public void setIsFeedbacked(boolean isFeedbacked) {
            this.isFeedbacked = isFeedbacked;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getProductId() {
            return productId;
        }

        public void setProductId(int productId) {
            this.productId = productId;
        }

        public String getProductName() {
            return productName;
        }

        public void setProductName(String productName) {
            this.productName = productName;
        }

        public String getThumbnailImage() {
            return thumbnailImage;
        }

        public void setThumbnailImage(String thumbnailImage) {
            this.thumbnailImage = thumbnailImage;
        }

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }

        public double getTotalPrice() {
            return totalPrice;
        }

        public void setTotalPrice(double totalPrice) {
            this.totalPrice = totalPrice;
        }

        public Color getColor() {
            return color;
        }

        public void setColor(Color color) {
            this.color = color;
        }

        public Size getSize() {
            return size;
        }

        public void setSize(Size size) {
            this.size = size;
        }

        @Override
        public String toString() {
            return "Order{" + "id=" + id + ", productId=" + productId + ", productName=" + productName + ", thumbnailImage=" + thumbnailImage + ", quantity=" + quantity + ", totalPrice=" + totalPrice + ", color=" + color + ", size=" + size + ", isFeedbacked=" + isFeedbacked + '}';
        }

    }

    public static class Bill {

        private int id;
        private List<Order> order;
        private double totalPrice;
        private String publishDate;
        private String status;
        private int customerId;
        private int salerId;
        private int shipperId;
        private Address address;
        private ShipMethod shipMethod;
        private PaymentMethod payment;
        private boolean isFeedbacked;

        public Bill() {
        }

        public Bill(int id, List<Order> order, double totalPrice, String publishDate, String status, int customerId, int salerId, int shipperId, Address address, ShipMethod shipMethod, PaymentMethod payment, boolean isFeedbacked) {
            this.id = id;
            this.order = order;
            this.totalPrice = totalPrice;
            this.publishDate = publishDate;
            this.status = status;
            this.customerId = customerId;
            this.salerId = salerId;
            this.shipperId = shipperId;
            this.address = address;
            this.shipMethod = shipMethod;
            this.payment = payment;
            this.isFeedbacked = isFeedbacked;
        }

        public boolean isIsFeedbacked() {
            return isFeedbacked;
        }

        public void setIsFeedbacked(boolean isFeedbacked) {
            this.isFeedbacked = isFeedbacked;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public List<Order> getOrder() {
            return order;
        }

        public void setOrder(List<Order> order) {
            this.order = order;
        }

        public double getTotalPrice() {
            return totalPrice;
        }

        public void setTotalPrice(double totalPrice) {
            this.totalPrice = totalPrice;
        }

        public String getPublishDate() {
            return publishDate;
        }

        public void setPublishDate(String publishDate) {
            this.publishDate = publishDate;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public int getCustomerId() {
            return customerId;
        }

        public void setCustomerId(int customerId) {
            this.customerId = customerId;
        }

        public int getSalerId() {
            return salerId;
        }

        public void setSalerId(int salerId) {
            this.salerId = salerId;
        }

        public int getShipperId() {
            return shipperId;
        }

        public void setShipperId(int shipperId) {
            this.shipperId = shipperId;
        }

        public Address getAddress() {
            return address;
        }

        public void setAddress(Address address) {
            this.address = address;
        }

        public ShipMethod getShipMethod() {
            return shipMethod;
        }

        public void setShipMethod(ShipMethod shipMethod) {
            this.shipMethod = shipMethod;
        }

        public PaymentMethod getPayment() {
            return payment;
        }

        public void setPayment(PaymentMethod payment) {
            this.payment = payment;
        }

//        @Override
//        public String toString() {
//            return "Bill{" + "id=" + id + ", order=" + order + ", totalPrice=" + totalPrice + ", publishDate=" + publishDate + ", status=" + status + ", customerId=" + customerId + ", salerId=" + salerId + ", shipperId=" + shipperId + ", address=" + address + ", shipMethod=" + shipMethod + ", payment=" + payment + ", isFeedbacked=" + isFeedbacked + '}';
//        }
    }

    public static class Staff {

        private int id;
        private String name;
        private String phoneNumber;

        public Staff() {
        }

        public Staff(int id, String name, String phoneNumber) {
            this.id = id;
            this.name = name;
            this.phoneNumber = phoneNumber;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPhoneNumber() {
            return phoneNumber;
        }

        public void setPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
        }

        @Override
        public String toString() {
            return "Staff{" + "id=" + id + ", name=" + name + ", phoneNumber=" + phoneNumber + '}';
        }

    }

    public static class CustomerInformationForOrderManager {

        private int id;
        private String fullName;
        private String mobile;
        private String gender;
        private String email;

        public CustomerInformationForOrderManager() {
        }

        public CustomerInformationForOrderManager(int id, String fullName, String mobile, String gender, String email) {
            this.id = id;
            this.fullName = fullName;
            this.mobile = mobile;
            this.gender = gender;
            this.email = email;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getFullName() {
            return fullName;
        }

        public void setFullName(String fullName) {
            this.fullName = fullName;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        @Override
        public String toString() {
            return "CustomerInformationForOrderManager{" + "id=" + id + ", fullName=" + fullName + ", mobile=" + mobile + ", gender=" + gender + ", email=" + email + '}';
        }

    }

    public static class BillForOrderManager {

        private int id;
        private List<Order> order;
        private double totalPrice;
        private String publishDate;
        private String status;
        private CustomerInformationForOrderManager customer;
        private Staff saler;
        private Staff shipper;
        private Address address;
        private ShipMethod shipMethod;
        private PaymentMethod payment;
        private boolean isFeedbacked;

        public BillForOrderManager() {
        }

        public BillForOrderManager(int id, List<Order> order, double totalPrice, String publishDate, String status, CustomerInformationForOrderManager customer, Staff saler, Staff shipper, Address address, ShipMethod shipMethod, PaymentMethod payment, boolean isFeedbacked) {
            this.id = id;
            this.order = order;
            this.totalPrice = totalPrice;
            this.publishDate = publishDate;
            this.status = status;
            this.customer = customer;
            this.saler = saler;
            this.shipper = shipper;
            this.address = address;
            this.shipMethod = shipMethod;
            this.payment = payment;
            this.isFeedbacked = isFeedbacked;
        }

        public boolean isIsFeedbacked() {
            return isFeedbacked;
        }

        public void setIsFeedbacked(boolean isFeedbacked) {
            this.isFeedbacked = isFeedbacked;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public List<Order> getOrder() {
            return order;
        }

        public void setOrder(List<Order> order) {
            this.order = order;
        }

        public double getTotalPrice() {
            return totalPrice;
        }

        public void setTotalPrice(double totalPrice) {
            this.totalPrice = totalPrice;
        }

        public String getPublishDate() {
            return publishDate;
        }

        public void setPublishDate(String publishDate) {
            this.publishDate = publishDate;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public CustomerInformationForOrderManager getCustomer() {
            return customer;
        }

        public void setCustomer(CustomerInformationForOrderManager customer) {
            this.customer = customer;
        }

        public Staff getSaler() {
            return saler;
        }

        public void setSaler(Staff saler) {
            this.saler = saler;
        }

        public Staff getShipper() {
            return shipper;
        }

        public void setShipper(Staff shipper) {
            this.shipper = shipper;
        }

        public Address getAddress() {
            return address;
        }

        public void setAddress(Address address) {
            this.address = address;
        }

        public ShipMethod getShipMethod() {
            return shipMethod;
        }

        public void setShipMethod(ShipMethod shipMethod) {
            this.shipMethod = shipMethod;
        }

        public PaymentMethod getPayment() {
            return payment;
        }

        public void setPayment(PaymentMethod payment) {
            this.payment = payment;
        }

        @Override
        public String toString() {
            return "Bill{" + "id=" + id + ", order=" + order + ", totalPrice=" + totalPrice + ", publishDate=" + publishDate + ", status=" + status + ", customer=" + customer + ", saler=" + saler + ", shipper=" + shipper + ", address=" + address + ", shipMethod=" + shipMethod + ", payment=" + payment + ", isFeedbacked=" + isFeedbacked + '}';
        }
    }

    public static class Address {

        private int id;
        private int userId;
        private String country;
        private String tinhThanhPho;
        private String quanHuyen;
        private String PhuongXa;
        private String details;
        private String note;

        public Address() {
        }

        public Address(int id, int userId, String country, String tinhThanhPho, String quanHuyen, String PhuongXa, String details, String note) {
            this.id = id;
            this.userId = userId;
            this.country = country;
            this.tinhThanhPho = tinhThanhPho;
            this.quanHuyen = quanHuyen;
            this.PhuongXa = PhuongXa;
            this.details = details;
            this.note = note;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public String getTinhThanhPho() {
            return tinhThanhPho;
        }

        public void setTinhThanhPho(String tinhThanhPho) {
            this.tinhThanhPho = tinhThanhPho;
        }

        public String getQuanHuyen() {
            return quanHuyen;
        }

        public void setQuanHuyen(String quanHuyen) {
            this.quanHuyen = quanHuyen;
        }

        public String getPhuongXa() {
            return PhuongXa;
        }

        public void setPhuongXa(String PhuongXa) {
            this.PhuongXa = PhuongXa;
        }

        public String getDetails() {
            return details;
        }

        public void setDetails(String details) {
            this.details = details;
        }

        public String getNote() {
            return note;
        }

        public void setNote(String note) {
            this.note = note;
        }

        @Override
        public String toString() {
            return "Address{" + "id=" + id + ", userId=" + userId + ", country=" + country + ", tinhThanhPho=" + tinhThanhPho + ", quanHuyen=" + quanHuyen + ", PhuongXa=" + PhuongXa + ", details=" + details + ", note=" + note + '}';
        }
    }

    public static class StaffWithRole {

        private int id;
        private String name;
        private String phoneNumber;
        private String email;
        private Role role;

        public StaffWithRole() {
        }

        public StaffWithRole(int id, String name, String phoneNumber, Role role, String email) {
            this.id = id;
            this.name = name;
            this.phoneNumber = phoneNumber;
            this.role = role;
            this.email = email;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPhoneNumber() {
            return phoneNumber;
        }

        public void setPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
        }

        public Role getRole() {
            return role;
        }

        public void setRole(Role role) {
            this.role = role;
        }

        @Override
        public String toString() {
            return "StaffWithRole{" + "id=" + id + ", name=" + name + ", phoneNumber=" + phoneNumber + ", role=" + role + '}';
        }
    }

    public static class DashBoardContent {

        private double totalRevenue;
        private double pendingPayment;
        private double refunds;
        private double avgPerOrder;
        private int totalBills;
        private int canceledBills;
        private int successBills;
        private int onDeliveryOrPendingBills;
        private int totalCustomer;
        private int newlyReg;
        private int newlyBought;
        private int bannedCustomer;
        private int totalProductFeedback;
        private double avgProductStar;
        private int totalShopFeedback;
        private double avgShopStar;

        public DashBoardContent() {
        }

        public double getTotalRevenue() {
            return totalRevenue;
        }

        public void setTotalRevenue(double totalRevenue) {
            this.totalRevenue = totalRevenue;
        }

        public double getPendingPayment() {
            return pendingPayment;
        }

        public void setPendingPayment(double pendingPayment) {
            this.pendingPayment = pendingPayment;
        }

        public double getRefunds() {
            return refunds;
        }

        public void setRefunds(double refunds) {
            this.refunds = refunds;
        }

        public double getAvgPerOrder() {
            return avgPerOrder;
        }

        public void setAvgPerOrder(double avgPerOrder) {
            this.avgPerOrder = avgPerOrder;
        }

        public int getTotalBills() {
            return totalBills;
        }

        public void setTotalBills(int totalBills) {
            this.totalBills = totalBills;
        }

        public int getCanceledBills() {
            return canceledBills;
        }

        public void setCanceledBills(int canceledBills) {
            this.canceledBills = canceledBills;
        }

        public int getSuccessBills() {
            return successBills;
        }

        public void setSuccessBills(int successBills) {
            this.successBills = successBills;
        }

        public int getOnDeliveryOrPendingBills() {
            return onDeliveryOrPendingBills;
        }

        public void setOnDeliveryOrPendingBills(int onDeliveryOrPendingBills) {
            this.onDeliveryOrPendingBills = onDeliveryOrPendingBills;
        }

        public int getTotalCustomer() {
            return totalCustomer;
        }

        public void setTotalCustomer(int totalCustomer) {
            this.totalCustomer = totalCustomer;
        }

        public int getNewlyReg() {
            return newlyReg;
        }

        public void setNewlyReg(int newlyReg) {
            this.newlyReg = newlyReg;
        }

        public int getNewlyBought() {
            return newlyBought;
        }

        public void setNewlyBought(int newlyBought) {
            this.newlyBought = newlyBought;
        }

        public int getBannedCustomer() {
            return bannedCustomer;
        }

        public void setBannedCustomer(int bannedCustomer) {
            this.bannedCustomer = bannedCustomer;
        }

        public int getTotalProductFeedback() {
            return totalProductFeedback;
        }

        public void setTotalProductFeedback(int totalProductFeedback) {
            this.totalProductFeedback = totalProductFeedback;
        }

        public double getAvgProductStar() {
            return avgProductStar;
        }

        public void setAvgProductStar(double avgProductStar) {
            this.avgProductStar = avgProductStar;
        }

        public int getTotalShopFeedback() {
            return totalShopFeedback;
        }

        public void setTotalShopFeedback(int totalShopFeedback) {
            this.totalShopFeedback = totalShopFeedback;
        }

        public double getAvgShopStar() {
            return avgShopStar;
        }

        public void setAvgShopStar(double avgShopStar) {
            this.avgShopStar = avgShopStar;
        }

        @Override
        public String toString() {
            return "DashBoardContent{" + "totalRevenue=" + totalRevenue + ", pendingPayment=" + pendingPayment + ", refunds=" + refunds + ", avgPerOrder=" + avgPerOrder + ", totalBills=" + totalBills + ", canceledBills=" + canceledBills + ", successBills=" + successBills + ", onDeliveryOrPendingBills=" + onDeliveryOrPendingBills + ", totalCustomer=" + totalCustomer + ", newlyReg=" + newlyReg + ", newlyBought=" + newlyBought + ", bannedCustomer=" + bannedCustomer + ", totalProductFeedback=" + totalProductFeedback + ", avgProductStar=" + avgProductStar + ", totalShopFeedback=" + totalShopFeedback + ", avgShopStar=" + avgShopStar + '}';
        }
    }

    public static class YearRevenue {

        private int month;
        private int revenue;

        public YearRevenue() {
        }

        public YearRevenue(int month, int revenue) {
            this.month = month;
            this.revenue = revenue;
        }

        public int getMonth() {
            return month;
        }

        public void setMonth(int month) {
            this.month = month;
        }

        public int getRevenue() {
            return revenue;
        }

        public void setRevenue(int revenue) {
            this.revenue = revenue;
        }

        @Override
        public String toString() {
            return "YearRevenue{" + "month=" + month + ", revenue=" + revenue + '}';
        }

    }

    public static class RevenueChart {

        private int year;
        private List<YearRevenue> revenueByMonth;

        public RevenueChart() {
        }

        public RevenueChart(int year, List<YearRevenue> revenueByMonth) {
            this.year = year;
            this.revenueByMonth = revenueByMonth;
        }

        public int getYear() {
            return year;
        }

        public void setYear(int year) {
            this.year = year;
        }

        public List<YearRevenue> getRevenueByMonth() {
            return revenueByMonth;
        }

        public void setRevenueByMonth(List<YearRevenue> revenueByMonth) {
            this.revenueByMonth = revenueByMonth;
        }

        @Override
        public String toString() {
            return "RevenueChart{" + "year=" + year + ", revenueByMonth=" + revenueByMonth + '}';
        }

    }
}
