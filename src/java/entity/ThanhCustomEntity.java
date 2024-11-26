/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

import entity.HoangAnhCustomEntity;
import java.util.List;

/**
 *
 * @author Lenovo
 */
public class ThanhCustomEntity {

    public static class BlogSubMedia {
        private int id;
        private int blogId;
        private String link;
        private String type;
        private int subTitleId;

        public BlogSubMedia() {
        }

        public BlogSubMedia(int id, int blogId, String link, String type, int subTitleId) {
            this.id = id;
            this.blogId = blogId;
            this.link = link;
            this.type = type;
            this.subTitleId = subTitleId;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getBlogId() {
            return blogId;
        }

        public void setBlogId(int blogId) {
            this.blogId = blogId;
        }

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public int getSubTitleId() {
            return subTitleId;
        }

        public void setSubTitleId(int subTitleId) {
            this.subTitleId = subTitleId;
        }
    }
    
    public static class BlogAttribute {
        private int id;
        private String subTitle;
        private String subContent;
        private int blogId;
        private String type;

        public BlogAttribute() {
        }

        public BlogAttribute(int id, String subTitle, String subContent, int blogId, String type) {
            this.id = id;
            this.subTitle = subTitle;
            this.subContent = subContent;
            this.blogId = blogId;
            this.type = type;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getSubTitle() {
            return subTitle;
        }

        public void setSubTitle(String subTitle) {
            this.subTitle = subTitle;
        }

        public String getSubContent() {
            return subContent;
        }

        public void setSubContent(String subContent) {
            this.subContent = subContent;
        }

        public int getBlogId() {
            return blogId;
        }

        public void setBlogId(int blogId) {
            this.blogId = blogId;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }
    
    public static class Bill {

        private int id;
        private List<HoangAnhCustomEntity.Order> order;
        private double totalPrice;
        private String publishDate;
        private String status;
        private int customerId;
        private int salerId;
        private int shipperId;
        private HoangAnhCustomEntity.Address address;
        private HoangAnhCustomEntity.ShipMethod shipMethod;
        private HoangAnhCustomEntity.PaymentMethod payment;
        private boolean isFeedbacked;
        private boolean isCanceled;

        public Bill() {
        }

        public Bill(int id, List<HoangAnhCustomEntity.Order> order, double totalPrice, String publishDate, String status, int customerId, int salerId, int shipperId, HoangAnhCustomEntity.Address address, HoangAnhCustomEntity.ShipMethod shipMethod, HoangAnhCustomEntity.PaymentMethod payment, boolean isFeedbacked, boolean isCanceled) {
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
            this.isCanceled = isCanceled;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public List<HoangAnhCustomEntity.Order> getOrder() {
            return order;
        }

        public void setOrder(List<HoangAnhCustomEntity.Order> order) {
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

        public HoangAnhCustomEntity.Address getAddress() {
            return address;
        }

        public void setAddress(HoangAnhCustomEntity.Address address) {
            this.address = address;
        }

        public HoangAnhCustomEntity.ShipMethod getShipMethod() {
            return shipMethod;
        }

        public void setShipMethod(HoangAnhCustomEntity.ShipMethod shipMethod) {
            this.shipMethod = shipMethod;
        }

        public HoangAnhCustomEntity.PaymentMethod getPayment() {
            return payment;
        }

        public void setPayment(HoangAnhCustomEntity.PaymentMethod payment) {
            this.payment = payment;
        }

        public boolean isIsFeedbacked() {
            return isFeedbacked;
        }

        public void setIsFeedbacked(boolean isFeedbacked) {
            this.isFeedbacked = isFeedbacked;
        }

        public boolean isIsCanceled() {
            return isCanceled;
        }

        public void setIsCanceled(boolean isCanceled) {
            this.isCanceled = isCanceled;
        }

        @Override
        public String toString() {
            return "Bill{" + "id=" + id + ", order=" + order + ", totalPrice=" + totalPrice + ", publishDate=" + publishDate + ", status=" + status + ", customerId=" + customerId + ", salerId=" + salerId + ", shipperId=" + shipperId + ", address=" + address + ", shipMethod=" + shipMethod + ", payment=" + payment + ", isFeedbacked=" + isFeedbacked + ", isCanceled=" + isCanceled + '}';
        }

    }

    public static class LoginUserDTO {

        private int userID;
        private String userEmail;
        private String userPass;
        private int userRollID;
        private String userStatus;

        public LoginUserDTO() {
        }

        public LoginUserDTO(int userID, String userEmail, String userPass, int userRollID, String userStatus) {
            this.userID = userID;
            this.userEmail = userEmail;
            this.userPass = userPass;
            this.userRollID = userRollID;
            this.userStatus = userStatus;
        }

        public int getUserID() {
            return userID;
        }

        public void setUserID(int userID) {
            this.userID = userID;
        }

        public String getUserEmail() {
            return userEmail;
        }

        public void setUserEmail(String userEmail) {
            this.userEmail = userEmail;
        }

        public String getUserPass() {
            return userPass;
        }

        public void setUserPass(String userPass) {
            this.userPass = userPass;
        }

        public int getUserRollID() {
            return userRollID;
        }

        public void setUserRollID(int userRollID) {
            this.userRollID = userRollID;
        }

        public String getUserStatus() {
            return userStatus;
        }

        public void setUserStatus(String userStatus) {
            this.userStatus = userStatus;
        }

    }

    public static class blogMedia {

        private int id;
        private int blogId;
        private String Link;
        private String type;

        public blogMedia() {
        }

        public blogMedia(int id, int blogId, String Link, String type) {
            this.id = id;
            this.blogId = blogId;
            this.Link = Link;
            this.type = type;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getBlogId() {
            return blogId;
        }

        public void setBlogId(int blogId) {
            this.blogId = blogId;
        }

        public String getLink() {
            return Link;
        }

        public void setLink(String Link) {
            this.Link = Link;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        @Override
        public String toString() {
            return "blogMedia{" + "id=" + id + ", blogId=" + blogId + ", Link=" + Link + ", type=" + type + '}';
        }

    }

    public static class blogAuthor {

        private int id;
        private String authorName;

        public blogAuthor() {
        }

        public blogAuthor(int id, String authorName) {
            this.id = id;
            this.authorName = authorName;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getAuthorName() {
            return authorName;
        }

        public void setAuthorName(String authorName) {
            this.authorName = authorName;
        }

    }

    public static class blog {

        private int id;
        private blogAuthor author;
        private String title;
        private BlogCategory cate;
        private String content;
        private String miniDescription;
        private String createAt;
        private List<blogMedia> blogImageList;
        private String status;

        public blog() {
        }

        public blog(int id, blogAuthor author, String title, BlogCategory cate, String content, String miniDescription, String createAt, List<blogMedia> blogImageList, String status) {
            this.id = id;
            this.author = author;
            this.title = title;
            this.cate = cate;
            this.content = content;
            this.miniDescription = miniDescription;
            this.createAt = createAt;
            this.blogImageList = blogImageList;
            this.status = status;
        }

        public int getId() {
            return id;
        }

        public BlogCategory getCate() {
            return cate;
        }

        public void setCate(BlogCategory cate) {
            this.cate = cate;
        }

        public void setId(int id) {
            this.id = id;
        }

        public blogAuthor getAuthor() {
            return author;
        }

        public void setAuthor(blogAuthor author) {
            this.author = author;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getMiniDescription() {
            return miniDescription;
        }

        public void setMiniDescription(String miniDescription) {
            this.miniDescription = miniDescription;
        }

        public String getCreateAt() {
            return createAt;
        }

        public void setCreateAt(String createAt) {
            this.createAt = createAt;
        }

        public List<blogMedia> getBlogImageList() {
            return blogImageList;
        }

        public void setBlogImageList(List<blogMedia> blogImageList) {
            this.blogImageList = blogImageList;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        @Override
        public String toString() {
            return "blog{" + "id=" + id + ", author=" + author + ", title=" + title + ", content=" + content + ", miniDescription=" + miniDescription + ", createAt=" + createAt + ", blogImageList=" + blogImageList + ", blogStatus" + status + '}';
        }

    }

    public static class Product {

        private int id;
        private Subcategory subCate;
        private double price;
        private double star;
        private String name;
        private String des;
        private String status;
        private String thumbnail;
        private List<Tag> tagList;
        private List<QuantityTracker> quantityTracker;
        private List<ProductImage> imageList;

        public Product() {
        }

        public Product(int id, Subcategory subCate, double price, double star, String name, String des, String status, String thumbnail, List<Tag> tagList, List<QuantityTracker> quantityTracker, List<ProductImage> imageList) {
            this.id = id;
            this.subCate = subCate;
            this.price = price;
            this.star = star;
            this.name = name;
            this.des = des;
            this.thumbnail = thumbnail;
            this.tagList = tagList;
            this.quantityTracker = quantityTracker;
            this.imageList = imageList;
            this.status = status;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public Subcategory getSubCate() {
            return subCate;
        }

        public void setSubCate(Subcategory subCate) {
            this.subCate = subCate;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        public double getStar() {
            return star;
        }

        public void setStar(double star) {
            this.star = star;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDes() {
            return des;
        }

        public void setDes(String des) {
            this.des = des;
        }

        public String getThumbnail() {
            return thumbnail;
        }

        public void setThumbnail(String thumbnail) {
            this.thumbnail = thumbnail;
        }

        public List<Tag> getTagList() {
            return tagList;
        }

        public void setTagList(List<Tag> tagList) {
            this.tagList = tagList;
        }

        public List<QuantityTracker> getQuantityTracker() {
            return quantityTracker;
        }

        public void setQuantityTracker(List<QuantityTracker> quantityTracker) {
            this.quantityTracker = quantityTracker;
        }

        public List<ProductImage> getImageList() {
            return imageList;
        }

        public void setImageList(List<ProductImage> imageList) {
            this.imageList = imageList;
        }

        @Override
        public String toString() {
            return "Product{" + "id=" + id + ", subCate=" + subCate + ", price=" + price + ", star=" + star + ", name=" + name + ", des=" + des + ", status=" + status + ", thumbnail=" + thumbnail + ", tagList=" + tagList + ", quantityTracker=" + quantityTracker + ", imageList=" + imageList + '}';
        }

    }

    public static class ProductImage {

        private int id;
        private int productId;
        private String link;

        public ProductImage() {
        }

        public ProductImage(int id, int productId, String link) {
            this.id = id;
            this.productId = productId;
            this.link = link;
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

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }

        @Override
        public String toString() {
            return "ProductImage{" + "id=" + id + ", productId=" + productId + ", link=" + link + '}';
        }

    }

    public static class QuantityTracker {

        private Color color;
        private Size size;
        private int quantity;

        public QuantityTracker() {
        }

        public QuantityTracker(Color color, Size size, int quantity) {
            this.color = color;
            this.size = size;
            this.quantity = quantity;
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

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }

        @Override
        public String toString() {
            return "QuantityTracker{" + "color=" + color + ", size=" + size + ", quantity=" + quantity + '}';
        }

    }

    public static class Size {

        private int id;
        private String size;

        public Size() {
        }

        public Size(int id, String size) {
            this.id = id;
            this.size = size;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getSize() {
            return size;
        }

        public void setSize(String size) {
            this.size = size;
        }

        @Override
        public String toString() {
            return "Size{" + "id=" + id + ", size=" + size + '}';
        }

    }

    public static class Color {

        private int id;
        private String name;
        private String colorCode;

        public Color() {
        }

        public Color(int id, String name, String colorCode) {
            this.id = id;
            this.name = name;
            this.colorCode = colorCode;
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

        public String getColorCode() {
            return colorCode;
        }

        public void setColorCode(String colorCode) {
            this.colorCode = colorCode;
        }

        @Override
        public String toString() {
            return "Color{" + "id=" + id + ", name=" + name + ", colorCode=" + colorCode + '}';
        }

    }

    public static class Tag {

        private int id;
        private String name;
        private String colorCode;

        public Tag() {
        }

        public Tag(int id, String name, String colorCode) {
            this.id = id;
            this.name = name;
            this.colorCode = colorCode;
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

        public String getColorCode() {
            return colorCode;
        }

        public void setColorCode(String colorCode) {
            this.colorCode = colorCode;
        }

        @Override
        public String toString() {
            return "Tag{" + "id=" + id + ", name=" + name + ", colorCode=" + colorCode + '}';
        }

    }

    public static class Subcategory {

        private int id;
        private String name;
        private String originalType;

        public Subcategory() {
        }

        public Subcategory(int id, String name, String originalType) {
            this.id = id;
            this.name = name;
            this.originalType = originalType;
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

        public String getOriginalType() {
            return originalType;
        }

        public void setOriginalType(String originalType) {
            this.originalType = originalType;
        }

        @Override
        public String toString() {
            return "Subcategory{" + "id=" + id + ", name=" + name + ", originalType=" + originalType + '}';
        }

    }

    public static class BlogCategory {

        private int id;
        private String name;

        public BlogCategory() {
        }

        public BlogCategory(int id, String name) {
            this.id = id;
            this.name = name;
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

    }
}
