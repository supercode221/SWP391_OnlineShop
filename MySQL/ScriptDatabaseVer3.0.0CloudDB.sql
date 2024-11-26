-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema freezedb
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema freezedb
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `freezedb` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci ;
-- -----------------------------------------------------
-- Schema freezedb
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema freezedb
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `freezedb` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci ;
USE `freezedb` ;

-- -----------------------------------------------------
-- Table `freezedb`.`changelog`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `freezedb`.`changelog` ;

CREATE TABLE IF NOT EXISTS `freezedb`.`changelog` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `CreateAt` TIMESTAMP NULL DEFAULT NULL,
  `UpdateAt` TIMESTAMP NULL DEFAULT NULL,
  `DeleteAt` TIMESTAMP NULL DEFAULT NULL,
  PRIMARY KEY (`ID`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `freezedb`.`role`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `freezedb`.`role` ;

CREATE TABLE IF NOT EXISTS `freezedb`.`role` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `Name` VARCHAR(50) CHARACTER SET 'utf8mb3' NULL DEFAULT NULL,
  `Weight` INT NULL DEFAULT NULL,
  PRIMARY KEY (`ID`))
ENGINE = InnoDB
AUTO_INCREMENT = 7
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `freezedb`.`user`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `freezedb`.`user` ;

CREATE TABLE IF NOT EXISTS `freezedb`.`user` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `ChangeLogID` INT NULL DEFAULT NULL,
  `RoleID` INT NULL DEFAULT '1',
  `Status` VARCHAR(50) CHARACTER SET 'utf8mb3' NULL DEFAULT 'active',
  `FirstName` VARCHAR(50) CHARACTER SET 'utf8mb3' NULL DEFAULT NULL,
  `EncodedPassword` VARCHAR(255) NULL DEFAULT NULL,
  `Email` VARCHAR(100) NULL DEFAULT NULL,
  `PhoneNumber` VARCHAR(20) NULL DEFAULT NULL,
  `LastName` VARCHAR(100) NULL DEFAULT NULL,
  `Description` TEXT NULL DEFAULT NULL,
  `AvatarImage` VARCHAR(255) NULL DEFAULT 'https://i.postimg.cc/SRhRPJdX/defaultavatar.jpg',
  PRIMARY KEY (`ID`),
  INDEX `ChangeLogID` (`ChangeLogID` ASC) VISIBLE,
  INDEX `RoleID` (`RoleID` ASC) VISIBLE,
  CONSTRAINT `user_ibfk_1`
    FOREIGN KEY (`ChangeLogID`)
    REFERENCES `freezedb`.`changelog` (`ID`),
  CONSTRAINT `user_ibfk_2`
    FOREIGN KEY (`RoleID`)
    REFERENCES `freezedb`.`role` (`ID`))
ENGINE = InnoDB
AUTO_INCREMENT = 2
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `freezedb`.`address`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `freezedb`.`address` ;

CREATE TABLE IF NOT EXISTS `freezedb`.`address` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `UserID` INT NULL DEFAULT NULL,
  `Country` VARCHAR(50) NULL DEFAULT NULL,
  `TinhThanhPho` VARCHAR(50) NULL DEFAULT NULL,
  `QuanHuyen` VARCHAR(50) NULL DEFAULT NULL,
  `PhuongXa` VARCHAR(50) NULL DEFAULT NULL,
  `Details` TEXT NULL DEFAULT NULL,
  `Note` TEXT NULL DEFAULT NULL,
  PRIMARY KEY (`ID`),
  INDEX `UserID` (`UserID` ASC) VISIBLE,
  CONSTRAINT `address_ibfk_1`
    FOREIGN KEY (`UserID`)
    REFERENCES `freezedb`.`user` (`ID`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `freezedb`.`blog`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `freezedb`.`blog` ;

CREATE TABLE IF NOT EXISTS `freezedb`.`blog` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `UserID` INT NULL DEFAULT NULL,
  `Title` VARCHAR(255) CHARACTER SET 'utf8mb3' NULL DEFAULT NULL,
  `Content` TEXT NULL DEFAULT NULL,
  PRIMARY KEY (`ID`),
  INDEX `UserID` (`UserID` ASC) VISIBLE,
  CONSTRAINT `blog_ibfk_1`
    FOREIGN KEY (`UserID`)
    REFERENCES `freezedb`.`user` (`ID`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `freezedb`.`blogmedia`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `freezedb`.`blogmedia` ;

CREATE TABLE IF NOT EXISTS `freezedb`.`blogmedia` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `BlogID` INT NULL DEFAULT NULL,
  `Link` VARCHAR(255) NULL DEFAULT NULL,
  PRIMARY KEY (`ID`),
  INDEX `BlogID` (`BlogID` ASC) VISIBLE,
  CONSTRAINT `blogmedia_ibfk_1`
    FOREIGN KEY (`BlogID`)
    REFERENCES `freezedb`.`blog` (`ID`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `freezedb`.`cart`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `freezedb`.`cart` ;

CREATE TABLE IF NOT EXISTS `freezedb`.`cart` (
  `UserID` INT NULL DEFAULT NULL,
  `ID` INT NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`ID`),
  INDEX `UserID` (`UserID` ASC) VISIBLE,
  CONSTRAINT `cart_ibfk_1`
    FOREIGN KEY (`UserID`)
    REFERENCES `freezedb`.`user` (`ID`))
ENGINE = InnoDB
AUTO_INCREMENT = 2
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `freezedb`.`subcategory`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `freezedb`.`subcategory` ;

CREATE TABLE IF NOT EXISTS `freezedb`.`subcategory` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `Name` VARCHAR(50) CHARACTER SET 'utf8mb3' NULL DEFAULT NULL,
  `OriginalType` VARCHAR(50) CHARACTER SET 'utf8mb3' NULL DEFAULT NULL,
  PRIMARY KEY (`ID`))
ENGINE = InnoDB
AUTO_INCREMENT = 10
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `freezedb`.`product`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `freezedb`.`product` ;

CREATE TABLE IF NOT EXISTS `freezedb`.`product` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `SubCategoryID` INT NULL DEFAULT NULL,
  `Price` DOUBLE NULL DEFAULT NULL,
  `Star` FLOAT NULL DEFAULT NULL,
  `Name` VARCHAR(255) CHARACTER SET 'utf8mb3' NULL DEFAULT NULL,
  `Description` TEXT NULL DEFAULT NULL,
  `ThumbnailImage` VARCHAR(255) NULL DEFAULT NULL,
  `status` VARCHAR(50) NULL DEFAULT NULL,
  PRIMARY KEY (`ID`),
  INDEX `SubCategoryID` (`SubCategoryID` ASC) VISIBLE,
  CONSTRAINT `product_ibfk_1`
    FOREIGN KEY (`SubCategoryID`)
    REFERENCES `freezedb`.`subcategory` (`ID`))
ENGINE = InnoDB
AUTO_INCREMENT = 49
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `freezedb`.`cartproduct`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `freezedb`.`cartproduct` ;

CREATE TABLE IF NOT EXISTS `freezedb`.`cartproduct` (
  `CartID` INT NOT NULL,
  `ProductID` INT NOT NULL,
  PRIMARY KEY (`CartID`, `ProductID`),
  INDEX `ProductID` (`ProductID` ASC) VISIBLE,
  CONSTRAINT `cartproduct_ibfk_1`
    FOREIGN KEY (`CartID`)
    REFERENCES `freezedb`.`cart` (`ID`),
  CONSTRAINT `cartproduct_ibfk_2`
    FOREIGN KEY (`ProductID`)
    REFERENCES `freezedb`.`product` (`ID`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `freezedb`.`color`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `freezedb`.`color` ;

CREATE TABLE IF NOT EXISTS `freezedb`.`color` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `Name` VARCHAR(50) NULL DEFAULT NULL,
  `ColorCode` VARCHAR(7) NULL DEFAULT NULL,
  PRIMARY KEY (`ID`))
ENGINE = InnoDB
AUTO_INCREMENT = 9
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `freezedb`.`colorproduct`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `freezedb`.`colorproduct` ;

CREATE TABLE IF NOT EXISTS `freezedb`.`colorproduct` (
  `ColorID` INT NOT NULL,
  `ProductID` INT NOT NULL,
  PRIMARY KEY (`ColorID`, `ProductID`),
  INDEX `ProductID` (`ProductID` ASC) VISIBLE,
  CONSTRAINT `colorproduct_ibfk_1`
    FOREIGN KEY (`ColorID`)
    REFERENCES `freezedb`.`color` (`ID`),
  CONSTRAINT `colorproduct_ibfk_2`
    FOREIGN KEY (`ProductID`)
    REFERENCES `freezedb`.`product` (`ID`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `freezedb`.`ShipMethod`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `freezedb`.`ShipMethod` ;

CREATE TABLE IF NOT EXISTS `freezedb`.`ShipMethod` (
  `ID` INT NOT NULL,
  `Name` VARCHAR(45) NULL,
  PRIMARY KEY (`ID`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `freezedb`.`PaymentMethod`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `freezedb`.`PaymentMethod` ;

CREATE TABLE IF NOT EXISTS `freezedb`.`PaymentMethod` (
  `ID` INT NOT NULL,
  `Name` VARCHAR(45) NULL,
  PRIMARY KEY (`ID`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `freezedb`.`bills`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `freezedb`.`bills` ;

CREATE TABLE IF NOT EXISTS `freezedb`.`bills` (
  `ID` INT NOT NULL,
  `TotalPrice` DOUBLE NULL,
  `PublishDate` TIMESTAMP NULL,
  `Status` VARCHAR(20) NULL,
  `CustomerID` INT NOT NULL,
  `SalerID` INT NOT NULL,
  `ShipperID` INT NOT NULL,
  `AddressID` INT NOT NULL,
  `ShipMethodID` INT NOT NULL,
  `PaymentMethodID` INT NOT NULL,
  PRIMARY KEY (`ID`),
  INDEX `fk_bills_user1_idx` (`CustomerID` ASC) VISIBLE,
  INDEX `fk_bills_user2_idx` (`SalerID` ASC) VISIBLE,
  INDEX `fk_bills_user3_idx` (`ShipperID` ASC) VISIBLE,
  INDEX `fk_bills_address1_idx` (`AddressID` ASC) VISIBLE,
  INDEX `fk_bills_ShipMethod1_idx` (`ShipMethodID` ASC) VISIBLE,
  INDEX `fk_bills_PaymentMethod1_idx` (`PaymentMethodID` ASC) VISIBLE,
  CONSTRAINT `fk_bills_user1`
    FOREIGN KEY (`CustomerID`)
    REFERENCES `freezedb`.`user` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_bills_user2`
    FOREIGN KEY (`SalerID`)
    REFERENCES `freezedb`.`user` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_bills_user3`
    FOREIGN KEY (`ShipperID`)
    REFERENCES `freezedb`.`user` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_bills_address1`
    FOREIGN KEY (`AddressID`)
    REFERENCES `freezedb`.`address` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_bills_ShipMethod1`
    FOREIGN KEY (`ShipMethodID`)
    REFERENCES `freezedb`.`ShipMethod` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_bills_PaymentMethod1`
    FOREIGN KEY (`PaymentMethodID`)
    REFERENCES `freezedb`.`PaymentMethod` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `freezedb`.`order`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `freezedb`.`order` ;

CREATE TABLE IF NOT EXISTS `freezedb`.`order` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `ProductID` INT NULL DEFAULT NULL,
  `Quantity` INT NULL DEFAULT NULL,
  `TotalPrice` DOUBLE NULL DEFAULT NULL,
  `ColorID` INT NULL,
  `SizeID` INT NULL,
  `BillID` INT NULL,
  PRIMARY KEY (`ID`),
  INDEX `ProductID` (`ProductID` ASC) VISIBLE,
  INDEX `fk_order_bills1_idx` (`BillID` ASC) VISIBLE,
  CONSTRAINT `order_ibfk_3`
    FOREIGN KEY (`ProductID`)
    REFERENCES `freezedb`.`product` (`ID`),
  CONSTRAINT `fk_order_bills1`
    FOREIGN KEY (`BillID`)
    REFERENCES `freezedb`.`bills` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `freezedb`.`feedback`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `freezedb`.`feedback` ;

CREATE TABLE IF NOT EXISTS `freezedb`.`feedback` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `UserID` INT NULL DEFAULT NULL,
  `OrderID` INT NULL DEFAULT NULL,
  `ProductID` INT NULL DEFAULT NULL,
  `Star` INT NULL DEFAULT NULL,
  `Comment` TEXT NULL DEFAULT NULL,
  PRIMARY KEY (`ID`),
  INDEX `UserID` (`UserID` ASC) VISIBLE,
  INDEX `OrderID` (`OrderID` ASC) VISIBLE,
  INDEX `ProductID` (`ProductID` ASC) VISIBLE,
  CONSTRAINT `feedback_ibfk_1`
    FOREIGN KEY (`UserID`)
    REFERENCES `freezedb`.`user` (`ID`),
  CONSTRAINT `feedback_ibfk_2`
    FOREIGN KEY (`OrderID`)
    REFERENCES `freezedb`.`order` (`ID`),
  CONSTRAINT `feedback_ibfk_3`
    FOREIGN KEY (`ProductID`)
    REFERENCES `freezedb`.`product` (`ID`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `freezedb`.`feedbackmedia`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `freezedb`.`feedbackmedia` ;

CREATE TABLE IF NOT EXISTS `freezedb`.`feedbackmedia` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `FeedbackID` INT NULL DEFAULT NULL,
  `Link` VARCHAR(255) NULL DEFAULT NULL,
  PRIMARY KEY (`ID`),
  INDEX `FeedbackID` (`FeedbackID` ASC) VISIBLE,
  CONSTRAINT `feedbackmedia_ibfk_1`
    FOREIGN KEY (`FeedbackID`)
    REFERENCES `freezedb`.`feedback` (`ID`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `freezedb`.`permission`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `freezedb`.`permission` ;

CREATE TABLE IF NOT EXISTS `freezedb`.`permission` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `Name` VARCHAR(50) NULL DEFAULT NULL,
  `Code` VARCHAR(50) NULL DEFAULT NULL,
  `Description` TEXT NULL DEFAULT NULL,
  PRIMARY KEY (`ID`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `freezedb`.`size`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `freezedb`.`size` ;

CREATE TABLE IF NOT EXISTS `freezedb`.`size` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `Size` VARCHAR(50) NULL DEFAULT NULL,
  PRIMARY KEY (`ID`))
ENGINE = InnoDB
AUTO_INCREMENT = 5
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `freezedb`.`product_color_size`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `freezedb`.`product_color_size` ;

CREATE TABLE IF NOT EXISTS `freezedb`.`product_color_size` (
  `ProductID` INT NOT NULL,
  `ColorID` INT NOT NULL,
  `SizeID` INT NOT NULL,
  `Quantity` INT NULL DEFAULT NULL,
  PRIMARY KEY (`ProductID`, `ColorID`, `SizeID`),
  INDEX `ColorID` (`ColorID` ASC) VISIBLE,
  INDEX `SizeID` (`SizeID` ASC) VISIBLE,
  CONSTRAINT `product_color_size_ibfk_1`
    FOREIGN KEY (`ProductID`)
    REFERENCES `freezedb`.`product` (`ID`),
  CONSTRAINT `product_color_size_ibfk_2`
    FOREIGN KEY (`ColorID`)
    REFERENCES `freezedb`.`color` (`ID`),
  CONSTRAINT `product_color_size_ibfk_3`
    FOREIGN KEY (`SizeID`)
    REFERENCES `freezedb`.`size` (`ID`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `freezedb`.`productimage`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `freezedb`.`productimage` ;

CREATE TABLE IF NOT EXISTS `freezedb`.`productimage` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `ProductID` INT NULL DEFAULT NULL,
  `Link` VARCHAR(255) NULL DEFAULT NULL,
  PRIMARY KEY (`ID`),
  INDEX `ProductID` (`ProductID` ASC) VISIBLE,
  CONSTRAINT `productimage_ibfk_1`
    FOREIGN KEY (`ProductID`)
    REFERENCES `freezedb`.`product` (`ID`))
ENGINE = InnoDB
AUTO_INCREMENT = 401
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `freezedb`.`rolepermission`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `freezedb`.`rolepermission` ;

CREATE TABLE IF NOT EXISTS `freezedb`.`rolepermission` (
  `RoleID` INT NOT NULL,
  `PermissionID` INT NOT NULL,
  PRIMARY KEY (`RoleID`, `PermissionID`),
  INDEX `PermissionID` (`PermissionID` ASC) VISIBLE,
  CONSTRAINT `rolepermission_ibfk_1`
    FOREIGN KEY (`RoleID`)
    REFERENCES `freezedb`.`role` (`ID`),
  CONSTRAINT `rolepermission_ibfk_2`
    FOREIGN KEY (`PermissionID`)
    REFERENCES `freezedb`.`permission` (`ID`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `freezedb`.`ShipBrand`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `freezedb`.`ShipBrand` ;

CREATE TABLE IF NOT EXISTS `freezedb`.`ShipBrand` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `Name` VARCHAR(100) CHARACTER SET 'utf8mb3' NULL DEFAULT NULL,
  PRIMARY KEY (`ID`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `freezedb`.`slider`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `freezedb`.`slider` ;

CREATE TABLE IF NOT EXISTS `freezedb`.`slider` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `UserID` INT NULL DEFAULT NULL,
  `Content` TEXT NULL DEFAULT NULL,
  `Link` VARCHAR(250) NULL DEFAULT NULL,
  `BackLink` VARCHAR(250) NULL DEFAULT NULL,
  PRIMARY KEY (`ID`),
  INDEX `UserID` (`UserID` ASC) VISIBLE,
  CONSTRAINT `slider_ibfk_1`
    FOREIGN KEY (`UserID`)
    REFERENCES `freezedb`.`user` (`ID`))
ENGINE = InnoDB
AUTO_INCREMENT = 5
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `freezedb`.`tag`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `freezedb`.`tag` ;

CREATE TABLE IF NOT EXISTS `freezedb`.`tag` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `Name` VARCHAR(50) CHARACTER SET 'utf8mb3' NULL DEFAULT NULL,
  `Color` VARCHAR(7) NULL DEFAULT NULL,
  PRIMARY KEY (`ID`))
ENGINE = InnoDB
AUTO_INCREMENT = 4
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `freezedb`.`tagproduct`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `freezedb`.`tagproduct` ;

CREATE TABLE IF NOT EXISTS `freezedb`.`tagproduct` (
  `TagID` INT NOT NULL,
  `ProductID` INT NOT NULL,
  PRIMARY KEY (`TagID`, `ProductID`),
  INDEX `ProductID` (`ProductID` ASC) VISIBLE,
  CONSTRAINT `tagproduct_ibfk_1`
    FOREIGN KEY (`TagID`)
    REFERENCES `freezedb`.`tag` (`ID`),
  CONSTRAINT `tagproduct_ibfk_2`
    FOREIGN KEY (`ProductID`)
    REFERENCES `freezedb`.`product` (`ID`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `freezedb`.`voucher`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `freezedb`.`voucher` ;

CREATE TABLE IF NOT EXISTS `freezedb`.`voucher` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `Code` VARCHAR(50) NULL DEFAULT NULL,
  `Percent` INT NULL DEFAULT NULL,
  `UserID` INT NULL DEFAULT NULL,
  PRIMARY KEY (`ID`),
  INDEX `UserID` (`UserID` ASC) VISIBLE,
  CONSTRAINT `voucher_ibfk_1`
    FOREIGN KEY (`UserID`)
    REFERENCES `freezedb`.`user` (`ID`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `freezedb`.`wishlistuserproduct`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `freezedb`.`wishlistuserproduct` ;

CREATE TABLE IF NOT EXISTS `freezedb`.`wishlistuserproduct` (
  `UserID` INT NOT NULL,
  `ProductID` INT NOT NULL,
  PRIMARY KEY (`UserID`, `ProductID`),
  INDEX `ProductID` (`ProductID` ASC) VISIBLE,
  CONSTRAINT `wishlistuserproduct_ibfk_1`
    FOREIGN KEY (`UserID`)
    REFERENCES `freezedb`.`user` (`ID`),
  CONSTRAINT `wishlistuserproduct_ibfk_2`
    FOREIGN KEY (`ProductID`)
    REFERENCES `freezedb`.`product` (`ID`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `freezedb`.`Ship_Brand_Method`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `freezedb`.`Ship_Brand_Method` ;

CREATE TABLE IF NOT EXISTS `freezedb`.`Ship_Brand_Method` (
  `ShipBrandID` INT NOT NULL,
  `ShipMethodID` INT NOT NULL,
  PRIMARY KEY (`ShipBrandID`, `ShipMethodID`),
  INDEX `fk_Ship_Brand_Method_ShipBrand1_idx` (`ShipBrandID` ASC) VISIBLE,
  INDEX `fk_Ship_Brand_Method_ShipMethod1_idx` (`ShipMethodID` ASC) VISIBLE,
  CONSTRAINT `fk_Ship_Brand_Method_ShipBrand1`
    FOREIGN KEY (`ShipBrandID`)
    REFERENCES `freezedb`.`ShipBrand` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Ship_Brand_Method_ShipMethod1`
    FOREIGN KEY (`ShipMethodID`)
    REFERENCES `freezedb`.`ShipMethod` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

USE `freezedb` ;

-- -----------------------------------------------------
-- Table `freezedb`.`changelog`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `freezedb`.`changelog` ;

CREATE TABLE IF NOT EXISTS `freezedb`.`changelog` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `CreateAt` TIMESTAMP NULL DEFAULT NULL,
  `UpdateAt` TIMESTAMP NULL DEFAULT NULL,
  `DeleteAt` TIMESTAMP NULL DEFAULT NULL,
  PRIMARY KEY (`ID`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `freezedb`.`role`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `freezedb`.`role` ;

CREATE TABLE IF NOT EXISTS `freezedb`.`role` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `Name` VARCHAR(50) NOT NULL,
  `Weight` INT NULL DEFAULT NULL,
  PRIMARY KEY (`ID`))
ENGINE = InnoDB
AUTO_INCREMENT = 7
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `freezedb`.`user`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `freezedb`.`user` ;

CREATE TABLE IF NOT EXISTS `freezedb`.`user` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `ChangeLogID` INT NULL DEFAULT NULL,
  `RoleID` INT NOT NULL DEFAULT '1',
  `Status` VARCHAR(50) CHARACTER SET 'utf8mb3' NOT NULL DEFAULT 'active',
  `FirstName` VARCHAR(50) CHARACTER SET 'utf8mb3' NOT NULL,
  `EncodedPassword` VARCHAR(255) NOT NULL,
  `Email` VARCHAR(100) NOT NULL,
  `PhoneNumber` VARCHAR(20) NULL DEFAULT NULL,
  `LastName` VARCHAR(100) NOT NULL,
  `Description` TEXT NULL DEFAULT NULL,
  `AvatarImage` VARCHAR(255) NOT NULL DEFAULT 'https://i.postimg.cc/SRhRPJdX/defaultavatar.jpg',
  `Gender` VARCHAR(6) CHARACTER SET 'utf8mb3' NULL DEFAULT NULL,
  PRIMARY KEY (`ID`),
  INDEX `ChangeLogID` (`ChangeLogID` ASC) VISIBLE,
  INDEX `User_ibfk_2` (`RoleID` ASC) VISIBLE,
  CONSTRAINT `User_ibfk_1`
    FOREIGN KEY (`ChangeLogID`)
    REFERENCES `freezedb`.`changelog` (`ID`),
  CONSTRAINT `User_ibfk_2`
    FOREIGN KEY (`RoleID`)
    REFERENCES `freezedb`.`role` (`ID`))
ENGINE = InnoDB
AUTO_INCREMENT = 100
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `freezedb`.`address`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `freezedb`.`address` ;

CREATE TABLE IF NOT EXISTS `freezedb`.`address` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `UserID` INT NOT NULL,
  `Country` VARCHAR(50) NOT NULL,
  `TinhThanhPho` VARCHAR(50) NOT NULL,
  `QuanHuyen` VARCHAR(50) NOT NULL,
  `PhuongXa` VARCHAR(50) NOT NULL,
  `Details` TEXT NULL DEFAULT NULL,
  `Note` TEXT NULL DEFAULT NULL,
  PRIMARY KEY (`ID`),
  INDEX `Address_ibfk_1` (`UserID` ASC) VISIBLE,
  CONSTRAINT `Address_ibfk_1`
    FOREIGN KEY (`UserID`)
    REFERENCES `freezedb`.`user` (`ID`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `freezedb`.`blog`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `freezedb`.`blog` ;

CREATE TABLE IF NOT EXISTS `freezedb`.`blog` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `UserID` INT NOT NULL,
  `Title` VARCHAR(255) CHARACTER SET 'utf8mb3' NOT NULL,
  `Content` TEXT NOT NULL,
  PRIMARY KEY (`ID`),
  INDEX `Blog_ibfk_1` (`UserID` ASC) VISIBLE,
  CONSTRAINT `Blog_ibfk_1`
    FOREIGN KEY (`UserID`)
    REFERENCES `freezedb`.`user` (`ID`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `freezedb`.`blogmedia`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `freezedb`.`blogmedia` ;

CREATE TABLE IF NOT EXISTS `freezedb`.`blogmedia` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `BlogID` INT NOT NULL,
  `Link` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`ID`),
  INDEX `BlogMedia_ibfk_1` (`BlogID` ASC) VISIBLE,
  CONSTRAINT `BlogMedia_ibfk_1`
    FOREIGN KEY (`BlogID`)
    REFERENCES `freezedb`.`blog` (`ID`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `freezedb`.`cart`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `freezedb`.`cart` ;

CREATE TABLE IF NOT EXISTS `freezedb`.`cart` (
  `UserID` INT NULL DEFAULT NULL,
  `ID` INT NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`ID`),
  INDEX `UserID` (`UserID` ASC) VISIBLE,
  CONSTRAINT `Cart_ibfk_1`
    FOREIGN KEY (`UserID`)
    REFERENCES `freezedb`.`user` (`ID`))
ENGINE = InnoDB
AUTO_INCREMENT = 17
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `freezedb`.`subcategory`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `freezedb`.`subcategory` ;

CREATE TABLE IF NOT EXISTS `freezedb`.`subcategory` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `Name` VARCHAR(50) NOT NULL,
  `OriginalType` VARCHAR(50) CHARACTER SET 'utf8mb3' NOT NULL,
  PRIMARY KEY (`ID`))
ENGINE = InnoDB
AUTO_INCREMENT = 10
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `freezedb`.`product`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `freezedb`.`product` ;

CREATE TABLE IF NOT EXISTS `freezedb`.`product` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `SubCategoryID` INT NOT NULL,
  `Price` DOUBLE NOT NULL,
  `Star` FLOAT NULL DEFAULT '0',
  `Name` VARCHAR(255) CHARACTER SET 'utf8mb3' NOT NULL,
  `Description` TEXT NOT NULL,
  `ThumbnailImage` VARCHAR(255) NOT NULL,
  `status` VARCHAR(50) NOT NULL DEFAULT 'active',
  PRIMARY KEY (`ID`),
  INDEX `Product_ibfk_1` (`SubCategoryID` ASC) VISIBLE,
  CONSTRAINT `Product_ibfk_1`
    FOREIGN KEY (`SubCategoryID`)
    REFERENCES `freezedb`.`subcategory` (`ID`))
ENGINE = InnoDB
AUTO_INCREMENT = 49
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `freezedb`.`color`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `freezedb`.`color` ;

CREATE TABLE IF NOT EXISTS `freezedb`.`color` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `Name` VARCHAR(50) NOT NULL,
  `ColorCode` VARCHAR(7) NOT NULL,
  PRIMARY KEY (`ID`))
ENGINE = InnoDB
AUTO_INCREMENT = 8
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `freezedb`.`size`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `freezedb`.`size` ;

CREATE TABLE IF NOT EXISTS `freezedb`.`size` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `Size` VARCHAR(50) NOT NULL,
  PRIMARY KEY (`ID`))
ENGINE = InnoDB
AUTO_INCREMENT = 5
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `freezedb`.`cartproduct`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `freezedb`.`cartproduct` ;

CREATE TABLE IF NOT EXISTS `freezedb`.`cartproduct` (
  `CartID` INT NOT NULL,
  `ProductID` INT NOT NULL,
  `Quantity` INT NOT NULL DEFAULT '1',
  `SizeID` INT NOT NULL,
  `ColorID` INT NOT NULL,
  `Checked` INT NOT NULL DEFAULT '0',
  PRIMARY KEY (`CartID`, `ProductID`, `SizeID`, `ColorID`),
  INDEX `ProductID` (`ProductID` ASC) VISIBLE,
  INDEX `fk_size` (`SizeID` ASC) VISIBLE,
  INDEX `fk_color` (`ColorID` ASC) VISIBLE,
  CONSTRAINT `CartProduct_ibfk_1`
    FOREIGN KEY (`CartID`)
    REFERENCES `freezedb`.`cart` (`ID`),
  CONSTRAINT `CartProduct_ibfk_2`
    FOREIGN KEY (`ProductID`)
    REFERENCES `freezedb`.`product` (`ID`),
  CONSTRAINT `fk_color`
    FOREIGN KEY (`ColorID`)
    REFERENCES `freezedb`.`color` (`ID`),
  CONSTRAINT `fk_size`
    FOREIGN KEY (`SizeID`)
    REFERENCES `freezedb`.`size` (`ID`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `freezedb`.`ship`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `freezedb`.`ship` ;

CREATE TABLE IF NOT EXISTS `freezedb`.`ship` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `Name` VARCHAR(100) NOT NULL,
  PRIMARY KEY (`ID`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `freezedb`.`order`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `freezedb`.`order` ;

CREATE TABLE IF NOT EXISTS `freezedb`.`order` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `CustomerID` INT NULL DEFAULT NULL,
  `SaleID` INT NULL DEFAULT NULL,
  `ProductID` INT NULL DEFAULT NULL,
  `ShipID` INT NULL DEFAULT NULL,
  `OrderDate` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
  `Quantity` INT NULL DEFAULT NULL,
  `Price` DOUBLE NULL DEFAULT NULL,
  `Status` VARCHAR(50) NULL DEFAULT NULL,
  `AddressID` INT NULL DEFAULT NULL,
  `PaymentMethod` VARCHAR(50) NULL DEFAULT NULL,
  PRIMARY KEY (`ID`),
  INDEX `CustomerID` (`CustomerID` ASC) VISIBLE,
  INDEX `SaleID` (`SaleID` ASC) VISIBLE,
  INDEX `ProductID` (`ProductID` ASC) VISIBLE,
  INDEX `ShipID` (`ShipID` ASC) VISIBLE,
  CONSTRAINT `Order_ibfk_1`
    FOREIGN KEY (`CustomerID`)
    REFERENCES `freezedb`.`user` (`ID`),
  CONSTRAINT `Order_ibfk_2`
    FOREIGN KEY (`SaleID`)
    REFERENCES `freezedb`.`user` (`ID`),
  CONSTRAINT `Order_ibfk_3`
    FOREIGN KEY (`ProductID`)
    REFERENCES `freezedb`.`product` (`ID`),
  CONSTRAINT `Order_ibfk_4`
    FOREIGN KEY (`ShipID`)
    REFERENCES `freezedb`.`ship` (`ID`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `freezedb`.`feedback`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `freezedb`.`feedback` ;

CREATE TABLE IF NOT EXISTS `freezedb`.`feedback` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `UserID` INT NOT NULL,
  `OrderID` INT NOT NULL,
  `ProductID` INT NOT NULL,
  `Star` INT NOT NULL DEFAULT '0',
  `Comment` TEXT NULL DEFAULT NULL,
  PRIMARY KEY (`ID`),
  INDEX `Feedback_ibfk_1` (`UserID` ASC) VISIBLE,
  INDEX `Feedback_ibfk_2` (`OrderID` ASC) VISIBLE,
  INDEX `Feedback_ibfk_3` (`ProductID` ASC) VISIBLE,
  CONSTRAINT `Feedback_ibfk_1`
    FOREIGN KEY (`UserID`)
    REFERENCES `freezedb`.`user` (`ID`),
  CONSTRAINT `Feedback_ibfk_2`
    FOREIGN KEY (`OrderID`)
    REFERENCES `freezedb`.`order` (`ID`),
  CONSTRAINT `Feedback_ibfk_3`
    FOREIGN KEY (`ProductID`)
    REFERENCES `freezedb`.`product` (`ID`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `freezedb`.`feedbackmedia`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `freezedb`.`feedbackmedia` ;

CREATE TABLE IF NOT EXISTS `freezedb`.`feedbackmedia` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `FeedbackID` INT NOT NULL,
  `Link` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`ID`),
  INDEX `FeedbackMedia_ibfk_1` (`FeedbackID` ASC) VISIBLE,
  CONSTRAINT `FeedbackMedia_ibfk_1`
    FOREIGN KEY (`FeedbackID`)
    REFERENCES `freezedb`.`feedback` (`ID`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `freezedb`.`permission`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `freezedb`.`permission` ;

CREATE TABLE IF NOT EXISTS `freezedb`.`permission` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `Name` VARCHAR(50) NULL DEFAULT NULL,
  `Code` VARCHAR(50) NULL DEFAULT NULL,
  `Description` TEXT NULL DEFAULT NULL,
  PRIMARY KEY (`ID`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `freezedb`.`product_color_size`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `freezedb`.`product_color_size` ;

CREATE TABLE IF NOT EXISTS `freezedb`.`product_color_size` (
  `ProductID` INT NOT NULL,
  `ColorID` INT NOT NULL,
  `SizeID` INT NOT NULL,
  `Quantity` INT NULL DEFAULT NULL,
  PRIMARY KEY (`ProductID`, `ColorID`, `SizeID`),
  INDEX `ColorID` (`ColorID` ASC) VISIBLE,
  INDEX `SizeID` (`SizeID` ASC) VISIBLE,
  CONSTRAINT `Product_Color_Size_ibfk_1`
    FOREIGN KEY (`ProductID`)
    REFERENCES `freezedb`.`product` (`ID`),
  CONSTRAINT `Product_Color_Size_ibfk_2`
    FOREIGN KEY (`ColorID`)
    REFERENCES `freezedb`.`color` (`ID`),
  CONSTRAINT `Product_Color_Size_ibfk_3`
    FOREIGN KEY (`SizeID`)
    REFERENCES `freezedb`.`size` (`ID`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `freezedb`.`productimage`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `freezedb`.`productimage` ;

CREATE TABLE IF NOT EXISTS `freezedb`.`productimage` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `ProductID` INT NOT NULL,
  `Link` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`ID`),
  INDEX `ProductImage_ibfk_1` (`ProductID` ASC) VISIBLE,
  CONSTRAINT `ProductImage_ibfk_1`
    FOREIGN KEY (`ProductID`)
    REFERENCES `freezedb`.`product` (`ID`))
ENGINE = InnoDB
AUTO_INCREMENT = 401
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `freezedb`.`rolepermission`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `freezedb`.`rolepermission` ;

CREATE TABLE IF NOT EXISTS `freezedb`.`rolepermission` (
  `RoleID` INT NOT NULL,
  `PermissionID` INT NOT NULL,
  PRIMARY KEY (`RoleID`, `PermissionID`),
  INDEX `PermissionID` (`PermissionID` ASC) VISIBLE,
  CONSTRAINT `RolePermission_ibfk_1`
    FOREIGN KEY (`RoleID`)
    REFERENCES `freezedb`.`role` (`ID`),
  CONSTRAINT `RolePermission_ibfk_2`
    FOREIGN KEY (`PermissionID`)
    REFERENCES `freezedb`.`permission` (`ID`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `freezedb`.`slider`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `freezedb`.`slider` ;

CREATE TABLE IF NOT EXISTS `freezedb`.`slider` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `UserID` INT NULL DEFAULT NULL,
  `Content` TEXT NOT NULL,
  `Link` VARCHAR(250) NOT NULL,
  `Status` VARCHAR(10) NOT NULL,
  `BackLink` TEXT NOT NULL,
  PRIMARY KEY (`ID`),
  INDEX `UserID` (`UserID` ASC) VISIBLE,
  CONSTRAINT `Slider_ibfk_1`
    FOREIGN KEY (`UserID`)
    REFERENCES `freezedb`.`user` (`ID`))
ENGINE = InnoDB
AUTO_INCREMENT = 3
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `freezedb`.`tag`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `freezedb`.`tag` ;

CREATE TABLE IF NOT EXISTS `freezedb`.`tag` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `Name` VARCHAR(50) NOT NULL,
  `Color` VARCHAR(7) NOT NULL,
  PRIMARY KEY (`ID`))
ENGINE = InnoDB
AUTO_INCREMENT = 4
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `freezedb`.`tagproduct`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `freezedb`.`tagproduct` ;

CREATE TABLE IF NOT EXISTS `freezedb`.`tagproduct` (
  `TagID` INT NOT NULL,
  `ProductID` INT NOT NULL,
  PRIMARY KEY (`TagID`, `ProductID`),
  INDEX `ProductID` (`ProductID` ASC) VISIBLE,
  CONSTRAINT `TagProduct_ibfk_1`
    FOREIGN KEY (`TagID`)
    REFERENCES `freezedb`.`tag` (`ID`),
  CONSTRAINT `TagProduct_ibfk_2`
    FOREIGN KEY (`ProductID`)
    REFERENCES `freezedb`.`product` (`ID`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `freezedb`.`voucher`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `freezedb`.`voucher` ;

CREATE TABLE IF NOT EXISTS `freezedb`.`voucher` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `Code` VARCHAR(50) NULL DEFAULT NULL,
  `Percent` INT NULL DEFAULT NULL,
  `UserID` INT NULL DEFAULT NULL,
  PRIMARY KEY (`ID`),
  INDEX `UserID` (`UserID` ASC) VISIBLE,
  CONSTRAINT `Voucher_ibfk_1`
    FOREIGN KEY (`UserID`)
    REFERENCES `freezedb`.`user` (`ID`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `freezedb`.`wishlistuserproduct`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `freezedb`.`wishlistuserproduct` ;

CREATE TABLE IF NOT EXISTS `freezedb`.`wishlistuserproduct` (
  `UserID` INT NOT NULL,
  `ProductID` INT NOT NULL,
  PRIMARY KEY (`UserID`, `ProductID`),
  INDEX `ProductID` (`ProductID` ASC) VISIBLE,
  CONSTRAINT `WishListUserProduct_ibfk_1`
    FOREIGN KEY (`UserID`)
    REFERENCES `freezedb`.`user` (`ID`),
  CONSTRAINT `WishListUserProduct_ibfk_2`
    FOREIGN KEY (`ProductID`)
    REFERENCES `freezedb`.`product` (`ID`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Data for table `freezedb`.`ShipMethod`
-- -----------------------------------------------------
START TRANSACTION;
USE `freezedb`;
INSERT INTO `freezedb`.`ShipMethod` (`ID`, `Name`) VALUES (1, 'Fast Delivery');
INSERT INTO `freezedb`.`ShipMethod` (`ID`, `Name`) VALUES (2, 'Normal Delivery');
INSERT INTO `freezedb`.`ShipMethod` (`ID`, `Name`) VALUES (3, 'Economical delivery');

COMMIT;


-- -----------------------------------------------------
-- Data for table `freezedb`.`PaymentMethod`
-- -----------------------------------------------------
START TRANSACTION;
USE `freezedb`;
INSERT INTO `freezedb`.`PaymentMethod` (`ID`, `Name`) VALUES (1, 'VNPay');
INSERT INTO `freezedb`.`PaymentMethod` (`ID`, `Name`) VALUES (2, 'COD');
INSERT INTO `freezedb`.`PaymentMethod` (`ID`, `Name`) VALUES (3, 'MoMo');
INSERT INTO `freezedb`.`PaymentMethod` (`ID`, `Name`) VALUES (4, 'ZaloPay');
INSERT INTO `freezedb`.`PaymentMethod` (`ID`, `Name`) VALUES (5, 'Banking');

COMMIT;


-- -----------------------------------------------------
-- Data for table `freezedb`.`ShipBrand`
-- -----------------------------------------------------
START TRANSACTION;
USE `freezedb`;
INSERT INTO `freezedb`.`ShipBrand` (`ID`, `Name`) VALUES (1, 'Giao Hàng Tiết Kiệm');
INSERT INTO `freezedb`.`ShipBrand` (`ID`, `Name`) VALUES (2, 'ShopeeExpress');
INSERT INTO `freezedb`.`ShipBrand` (`ID`, `Name`) VALUES (3, 'Giao Hàng Nhanh');
INSERT INTO `freezedb`.`ShipBrand` (`ID`, `Name`) VALUES (4, 'Viettel Post');
INSERT INTO `freezedb`.`ShipBrand` (`ID`, `Name`) VALUES (5, 'Ninja Van');

COMMIT;


-- -----------------------------------------------------
-- Data for table `freezedb`.`Ship_Brand_Method`
-- -----------------------------------------------------
START TRANSACTION;
USE `freezedb`;
INSERT INTO `freezedb`.`Ship_Brand_Method` (`ShipBrandID`, `ShipMethodID`) VALUES (1, 1);
INSERT INTO `freezedb`.`Ship_Brand_Method` (`ShipBrandID`, `ShipMethodID`) VALUES (1, 2);
INSERT INTO `freezedb`.`Ship_Brand_Method` (`ShipBrandID`, `ShipMethodID`) VALUES (1, 3);
INSERT INTO `freezedb`.`Ship_Brand_Method` (`ShipBrandID`, `ShipMethodID`) VALUES (1, 4);
INSERT INTO `freezedb`.`Ship_Brand_Method` (`ShipBrandID`, `ShipMethodID`) VALUES (2, 1);
INSERT INTO `freezedb`.`Ship_Brand_Method` (`ShipBrandID`, `ShipMethodID`) VALUES (2, 2);
INSERT INTO `freezedb`.`Ship_Brand_Method` (`ShipBrandID`, `ShipMethodID`) VALUES (2, 3);
INSERT INTO `freezedb`.`Ship_Brand_Method` (`ShipBrandID`, `ShipMethodID`) VALUES (2, 4);
INSERT INTO `freezedb`.`Ship_Brand_Method` (`ShipBrandID`, `ShipMethodID`) VALUES (3, 1);
INSERT INTO `freezedb`.`Ship_Brand_Method` (`ShipBrandID`, `ShipMethodID`) VALUES (3, 2);
INSERT INTO `freezedb`.`Ship_Brand_Method` (`ShipBrandID`, `ShipMethodID`) VALUES (3, 3);
INSERT INTO `freezedb`.`Ship_Brand_Method` (`ShipBrandID`, `ShipMethodID`) VALUES (3, 4);
INSERT INTO `freezedb`.`Ship_Brand_Method` (`ShipBrandID`, `ShipMethodID`) VALUES (4, 1);
INSERT INTO `freezedb`.`Ship_Brand_Method` (`ShipBrandID`, `ShipMethodID`) VALUES (4, 2);
INSERT INTO `freezedb`.`Ship_Brand_Method` (`ShipBrandID`, `ShipMethodID`) VALUES (4, 3);
INSERT INTO `freezedb`.`Ship_Brand_Method` (`ShipBrandID`, `ShipMethodID`) VALUES (4, 4);
INSERT INTO `freezedb`.`Ship_Brand_Method` (`ShipBrandID`, `ShipMethodID`) VALUES (5, 1);
INSERT INTO `freezedb`.`Ship_Brand_Method` (`ShipBrandID`, `ShipMethodID`) VALUES (5, 2);
INSERT INTO `freezedb`.`Ship_Brand_Method` (`ShipBrandID`, `ShipMethodID`) VALUES (5, 3);
INSERT INTO `freezedb`.`Ship_Brand_Method` (`ShipBrandID`, `ShipMethodID`) VALUES (5, 4);

COMMIT;

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

-- Insert data for user role
INSERT INTO role (Name, Weight) 
VALUES 
('user', 0),
('admin', 0),
('marketer', 0),
('saler', 0),
('marketingmanager', 0),
('salemanager', 0);

SELECT * FROM role;

ALTER TABLE User 
ALTER RoleID SET DEFAULT 1; -- set default value for RoleID when User register

ALTER TABLE User 
ALTER Status SET DEFAULT 'active'; -- Set default value for user account status

INSERT INTO Product (SubCategoryID, Price, Name, Description, ThumbnailImage, status)  -- Insert data for Product table cause we have not add admin function yet
VALUES 
(1, 490000, 'ABYSS | SS24 INSECT AFFECTION TEE', 'This is a great tee from hades the local brand. No words can describe the amazing of this tee!', 'https://i.postimg.cc/7ZyxJbxM/hadest60086-165dffeeb0874e8d957abd58768db8b8-master.jpg', 'available'),
(1, 490000, 'ABYSS | SS24 MYSTIC WING WAX TEE', 'This is a great tee from hades the local brand. No words can describe the amazing of this tee!', 'https://i.postimg.cc/Y949fVT1/hades0024-2fd0c21809ce4a1e93adcfc797c84748-master.jpg', 'available'),
(1, 420000, 'DC | DBZ Dragon Team T-Shirt - Cream 420', 'This is a great tee from hades the local brand. No words can describe the amazing of this tee!', 'https://i.postimg.cc/7ZYqnd2f/1-7648edaf-d90e-46e8-a8f8-f316b44c3ee7-1.jpg', 'available'),
(1, 450000, 'DC | DBZ Frieza T-Shirt - Black 450', 'This is a great tee from hades the local brand. No words can describe the amazing of this tee!', 'https://i.postimg.cc/Dy7h7H5B/1-48d3526c-3277-44cc-a512-cb212e28ac2b-1.jpg', 'available'),
(1, 450000, 'DC | DBZ Gohan Super Saiyan T-Shirt - White 450', 'This is a great tee from hades the local brand. No words can describe the amazing of this tee!', 'https://i.postimg.cc/4NfDDtN0/1-eb2f12fa-a926-42a7-948b-b240ecb04710-1.jpg', 'available'),
(1, 499000, 'DC | DBZ Goku & Shenron T-Shirt - Cream 499', 'This is a great tee from hades the local brand. No words can describe the amazing of this tee!', 'https://i.postimg.cc/0yLHyJPP/1-49e3507e-d5da-400f-984b-0e843724b412-1.jpg', 'available'),
(1, 490000, 'DC | DBZ Goku Football Jersey - CreamBlue 490', 'This is a great tee from hades the local brand. No words can describe the amazing of this tee!', 'https://i.postimg.cc/sDxnPJ23/2-f690b5ab-65e5-4ca6-a7ac-0fc296dcb84d_1.jpg', 'available'),
(1, 490000, 'DC | DBZ Goku Football Jersey - CreamOrange 490', 'This is a great tee from hades the local brand. No words can describe the amazing of this tee!', 'https://i.postimg.cc/Wbx7GLK7/1-92894086-c213-407f-99ea-2a4c78e0b46d_1.jpg', 'available'),
(1, 450000, 'DC | DBZ Perfect Cell T-Shirt - Black 450', 'This is a great tee from hades the local brand. No words can describe the amazing of this tee!', 'https://i.postimg.cc/k5s8hkjz/1-d3162669-bc80-4da0-b65e-e82e8f1b7dd1_1.jpg', 'available'),
(1, 450000, 'DC | DBZ Vegeta T-Shirt - White 450', 'This is a great tee from hades the local brand. No words can describe the amazing of this tee!', 'https://i.postimg.cc/C1dZrdnn/1-79a62dd9-2f23-4f39-91de-4a3f6d239fb6_1.jpg', 'available'),
(3, 550000, 'Wreath Leaf Sweatshirt', 'This is a great tee from hades the local brand. No words can describe the amazing of this tee!', 'https://i.postimg.cc/CM373nHg/117-1.jpg', 'available'),
(3, 900000, 'Letters Monogram Knit Sweater - Black', 'This is a great tee from hades the local brand. No words can describe the amazing of this tee!', 'https://i.postimg.cc/wjcwcxNd/111-1.jpg', 'available'),
(2, 590000, 'DC | DBZ Goku & Shenron Shirt - Black', 'This is a great tee from hades the local brand. No words can describe the amazing of this tee!', 'https://i.postimg.cc/QCG7y1LY/1-fc2065c8-a3bc-4501-8629-4174841cf53f-1.jpg', 'available'),
(2, 499000, 'Dragon All Print Shirt - Cream', 'This is a great tee from hades the local brand. No words can describe the amazing of this tee!', 'https://i.postimg.cc/6qX70KRD/90-1.jpg', 'available'),
(2, 520000, 'HADES LOVELESS STRIPED SHIRT', 'This is a great tee from hades the local brand. No words can describe the amazing of this tee!', 'https://i.postimg.cc/pLTmkqhX/hades4381-f3a4cdd6c052415cb389231ff250298e-master.jpg', 'available'),
(2, 480000, 'HADES STRIPED SOLID SHIRT', 'This is a great tee from hades the local brand. No words can describe the amazing of this tee!', 'https://i.postimg.cc/6qt3Z0xK/hades0040-6e3e09600e0c430d980a561acd8361aa-master.jpg', 'available'),
(2, 690000, 'Letters Monogram Denim Jersey Shirt - Black', 'This is a great tee from hades the local brand. No words can describe the amazing of this tee!', 'https://i.postimg.cc/mZjZq7Gc/99-1.jpg', 'available'),
(2, 450000, 'University Bandana Shirt - Black', 'This is a great tee from hades the local brand. No words can describe the amazing of this tee!', 'https://i.postimg.cc/yYWKb5P5/1-dfe02552-fe5e-4400-b4c6-3db579fbb28c_1.jpg', 'available'),
(2, 450000, 'University Bandana Shirt - Green', 'This is a great tee from hades the local brand. No words can describe the amazing of this tee!', 'https://i.postimg.cc/V6YQBHvK/1-055c44c7-a572-450a-9d97-a1a4d948d1be_1.jpg', 'available'),
(2, 450000, 'Y Logo Relaxed Shirt - White', 'This is a great tee from hades the local brand. No words can describe the amazing of this tee!', 'https://i.postimg.cc/xTBwFdqJ/77_1.jpg', 'available'),
(2, 590000, 'Baseball Jersey Striped', 'This is a great tee from hades the local brand. No words can describe the amazing of this tee!', 'https://i.postimg.cc/pVFYJXN1/17-1.jpg', 'available'),
(4, 899000, 'DirtyCoins Checkerboard Knit Cardigan', 'This is a great tee from hades the local brand. No words can describe the amazing of this tee!', 'https://i.postimg.cc/Xqmzh2NS/191-1.jpg', 'available'),
(4, 590000, 'DirtyCoins Print Cardigan - IvoryBrown', 'This is a great tee from hades the local brand. No words can describe the amazing of this tee!', 'https://i.postimg.cc/9Xw5PTQ5/115-1.jpg', 'available'),
(4, 590000, 'DirtyCoins Print Cardigan - IvoryPink', 'This is a great tee from hades the local brand. No words can describe the amazing of this tee!', 'https://i.postimg.cc/MHVFC7nn/134-1.jpg', 'available'),
(4, 590000, 'DirtyCoins Print Cardigan', 'This is a great tee from hades the local brand. No words can describe the amazing of this tee!', 'https://i.postimg.cc/8CxwTFQM/194-2-1.jpg', 'available'),
(6, 720000, 'DC  DBZ Goku SS Chibi Hoodie - Cream', 'This is a great tee from hades the local brand. No words can describe the amazing of this tee!', 'https://i.postimg.cc/hvDmngjV/1-be6945f5-6e7a-4ed4-9fbf-11b871706db5-1.jpg', 'available'),
(6, 750000, 'DC  DBZ Shenron Hoodie - Black', 'This is a great tee from hades the local brand. No words can describe the amazing of this tee!', 'https://i.postimg.cc/851cJ4SS/1-cc0a0db8-5463-4f6f-bd53-a3e3c8a69988-1.jpg', 'available'),
(6, 790000, 'HADES CELLULE WAY HOODIE ZIP', 'This is a great tee from hades the local brand. No words can describe the amazing of this tee!', 'https://i.postimg.cc/L5GmTBrh/hades8172-a8535cd49bc543e5a79b8815b50d1ac6-master.jpg', 'available'),
(6, 669000, 'More Money More Problems Hoodie - Black 669', 'This is a great tee from hades the local brand. No words can describe the amazing of this tee!', 'https://i.postimg.cc/jjRtZjk6/97-1-1.jpg', 'available'),
(6, 669000, 'Y Patch Hoodie - Brown', 'This is a great tee from hades the local brand. No words can describe the amazing of this tee!', 'https://i.postimg.cc/tRsZhr6q/96-1-1.jpg', 'available'),
(5, 750000, 'DC  DBZ Goku Battle Half-Zip Jacket - CreamBlue', 'This is a great tee from hades the local brand. No words can describe the amazing of this tee!', 'https://i.postimg.cc/tTwgSj6X/1-8186fbfa-9222-418e-acff-9346bc4950e2-1.jpg', 'available'),
(5, 750000, 'DC  DBZ Goku Battle Half-Zip Jacket - CreamGrey', 'This is a great tee from hades the local brand. No words can describe the amazing of this tee!', 'https://i.postimg.cc/d3GJ5TvB/1-65727f80-e161-423d-b767-56decbf307f1-1.jpg', 'available'),
(5, 1290000, 'DC  DBZ Logo Varsity Jacket - Black', 'This is a great tee from hades the local brand. No words can describe the amazing of this tee!', 'https://i.postimg.cc/MH0872b3/1-7fe412a0-1bfc-410b-9a72-eea702efe6bb-1.jpg', 'available'),
(5, 3000000, 'DC x LA LUNE Phoenix Reversible Puffer Jacket', 'This is a great tee from hades the local brand. No words can describe the amazing of this tee!', 'https://i.postimg.cc/J73wd8ZP/71-1.jpg', 'available'),
(7, 690000, 'ABYSS SS24 ECHINIDERM PANTS', 'This is a great tee from hades the local brand. No words can describe the amazing of this tee!', 'https://i.postimg.cc/J4dppY6p/untitled-capture1135-5220199b654246fbb286ef5b29453804.jpg', 'available'),
(7, 690000, 'Cargo Pants Patched Logo - Cream', 'This is a great tee from hades the local brand. No words can describe the amazing of this tee!', 'https://i.postimg.cc/wB5cYw28/1-0671f405-2382-4104-9613-7d93d65a6a56-1.jpg', 'available'),
(7, 690000, 'Cargo Pants Patched Logo - Green', 'This is a great tee from hades the local brand. No words can describe the amazing of this tee!', 'https://i.postimg.cc/SNz9pZQT/1-0ad80e24-b62f-4480-a172-b5305c117ccf-1.jpg', 'available'),
(7, 790000, 'DirtyCoins Comfy Essential Jeans - Black Wash', 'This is a great tee from hades the local brand. No words can describe the amazing of this tee!', 'https://i.postimg.cc/dVjmzW4M/2-0fba7023-d6c5-4857-8c90-7467f41b8c37-1.jpg', 'available'),
(7, 690000, 'HADES PLAID CASUAL PANTS', 'This is a great tee from hades the local brand. No words can describe the amazing of this tee!', 'https://i.postimg.cc/3rGGsSxL/20240820-131304_8832c9aa27cb40fdb49007e5a9b8d978.jpg', 'available'),
(7, 490000, 'Track Pants Relaxed Taped Logo - White', 'This is a great tee from hades the local brand. No words can describe the amazing of this tee!', 'https://i.postimg.cc/bNzvm3DS/1-85567b56-b0b8-4733-ad56-a1a97ae9ece9_1.jpg', 'available'),
(8, 590000, 'Denim Shorts Frayed Logo - Blue Wash', 'This is a great tee from hades the local brand. No words can describe the amazing of this tee!', 'https://i.postimg.cc/BbZGB7Yq/1-83c6bd82-49b7-47e2-8fe5-ec4e48824183.jpg', 'available'),
(8, 690000, 'Embroidery Logo Baggy Denim Shorts - Black Wash', 'This is a great tee from hades the local brand. No words can describe the amazing of this tee!', 'https://i.postimg.cc/2jHpfw9Z/28-4905f683-7d22-4b7f-9110-a292d48b47e4-1.jpg', 'available'),
(8, 690000, 'Embroidery Logo Baggy Denim Shorts - Light Blue', 'This is a great tee from hades the local brand. No words can describe the amazing of this tee!', 'https://i.postimg.cc/cJWk3g5W/1-61e699dd-5311-4a93-bf89-6977dae6d0bd-1.jpg', 'available'),
(8, 480000, 'HADES FRAYED EDGE SHORTS', 'This is a great tee from hades the local brand. No words can describe the amazing of this tee!', 'https://i.postimg.cc/pr5J4R1k/hades-0071-1c433f61dd0b4afdbec407fccd9a527d-master.jpg', 'available'),
(8, 380000, 'HADES STANDARD STRIPLE SHORTS', 'This is a great tee from hades the local brand. No words can describe the amazing of this tee!', 'https://i.postimg.cc/XN98f7d7/hades4134_e21501a09a2a4de28997a799ef5a35b2_master.jpg', 'available'),
(8, 490000, 'Script Logo Nylon Shorts', 'This is a great tee from hades the local brand. No words can describe the amazing of this tee!', 'https://i.postimg.cc/Qx0cmBts/1-4908b4ac-ae6b-4693-bd7e-249eb752f8b3_1.jpg', 'available'),
(9, 460000, 'HADES NEUTRAL CARGO PARACHUTE SKIRT', 'This is a great tee from hades the local brand. No words can describe the amazing of this tee!', 'https://i.postimg.cc/HxvC34fX/z4908503993219-2a3ba233739e6c352441ded60c16ae49-3e0378a895c9427595ff337f4cb745f8-master.jpg', 'available'),
(9, 450000, 'Logo Plaid Tennis Skirt', 'This is a great tee from hades the local brand. No words can describe the amazing of this tee!', 'https://i.postimg.cc/x8WVVjjd/173.jpg', 'available');
-- end of insert product

SELECT * FROM SubCategory;

-- Insert data for Size table
INSERT INTO Size (Size)
VALUES
('S'),
('M'),
('L'),
('XL');

-- Insert data for extends table of Product, color and size
INSERT INTO Product_Color_Size (ProductID, ColorID, SizeID, Quantity) VALUES
(1, 1, 1, 10),
(1, 2, 2, 10),
(1, 3, 3, 10),
(1, 4, 4, 10),

(2, 1, 1, 10),
(2, 2, 2, 10),
(2, 3, 3, 10),
(2, 4, 4, 10),

(3, 1, 1, 10),
(3, 2, 2, 10),
(3, 3, 3, 10),
(3, 4, 4, 10),

(4, 1, 1, 10),
(4, 2, 2, 10),
(4, 3, 3, 10),
(4, 4, 4, 10),

(5, 1, 1, 10),
(5, 2, 2, 10),
(5, 3, 3, 10),
(5, 4, 4, 10),

(6, 1, 1, 10),
(6, 2, 2, 10),
(6, 3, 3, 10),
(6, 4, 4, 10),

(7, 1, 1, 10),
(7, 2, 2, 10),
(7, 3, 3, 10),
(7, 4, 4, 10),

(8, 1, 1, 10),
(8, 2, 2, 10),
(8, 3, 3, 10),
(8, 4, 4, 10),

(9, 1, 1, 10),
(9, 2, 2, 10),
(9, 3, 3, 10),
(9, 4, 4, 10),

(10, 1, 1, 10),
(10, 2, 2, 10),
(10, 3, 3, 10),
(10, 4, 4, 10),

(11, 1, 1, 10),
(11, 2, 2, 10),
(11, 3, 3, 10),
(11, 4, 4, 10),

(12, 1, 1, 10),
(12, 2, 2, 10),
(12, 3, 3, 10),
(12, 4, 4, 10),

(13, 1, 1, 10),
(13, 2, 2, 10),
(13, 3, 3, 10),
(13, 4, 4, 10),

(14, 1, 1, 10),
(14, 2, 2, 10),
(14, 3, 3, 10),
(14, 4, 4, 10),

(15, 1, 1, 10),
(15, 2, 2, 10),
(15, 3, 3, 10),
(15, 4, 4, 10),

(16, 1, 1, 10),
(16, 2, 2, 10),
(16, 3, 3, 10),
(16, 4, 4, 10),

(17, 1, 1, 10),
(17, 2, 2, 10),
(17, 3, 3, 10),
(17, 4, 4, 10),

(18, 1, 1, 10),
(18, 2, 2, 10),
(18, 3, 3, 10),
(18, 4, 4, 10),

(19, 1, 1, 10),
(19, 2, 2, 10),
(19, 3, 3, 10),
(19, 4, 4, 10),

(20, 1, 1, 10),
(20, 2, 2, 10),
(20, 3, 3, 10),
(20, 4, 4, 10),

(21, 1, 1, 10),
(21, 2, 2, 10),
(21, 3, 3, 10),
(21, 4, 4, 10),

(22, 1, 1, 10),
(22, 2, 2, 10),
(22, 3, 3, 10),
(22, 4, 4, 10),

(23, 1, 1, 10),
(23, 2, 2, 10),
(23, 3, 3, 10),
(23, 4, 4, 10),

(24, 1, 1, 10),
(24, 2, 2, 10),
(24, 3, 3, 10),
(24, 4, 4, 10),

(25, 1, 1, 10),
(25, 2, 2, 10),
(25, 3, 3, 10),
(25, 4, 4, 10),

(26, 1, 1, 10),
(26, 2, 2, 10),
(26, 3, 3, 10),
(26, 4, 4, 10),

(27, 1, 1, 10),
(27, 2, 2, 10),
(27, 3, 3, 10),
(27, 4, 4, 10),

(28, 1, 1, 10),
(28, 2, 2, 10),
(28, 3, 3, 10),
(28, 4, 4, 10),

(29, 1, 1, 10),
(29, 2, 2, 10),
(29, 3, 3, 10),
(29, 4, 4, 10),

(30, 1, 1, 10),
(30, 2, 2, 10),
(30, 3, 3, 10),
(30, 4, 4, 10),

(31, 1, 1, 10),
(31, 2, 2, 10),
(31, 3, 3, 10),
(31, 4, 4, 10),

(32, 1, 1, 10),
(32, 2, 2, 10),
(32, 3, 3, 10),
(32, 4, 4, 10),

(33, 1, 1, 10),
(33, 2, 2, 10),
(33, 3, 3, 10),
(33, 4, 4, 10),

(34, 1, 1, 10),
(34, 2, 2, 10),
(34, 3, 3, 10),
(34, 4, 4, 10),

(35, 1, 1, 10),
(35, 2, 2, 10),
(35, 3, 3, 10),
(35, 4, 4, 10),

(36, 1, 1, 10),
(36, 2, 2, 10),
(36, 3, 3, 10),
(36, 4, 4, 10),

(37, 1, 1, 10),
(37, 2, 2, 10),
(37, 3, 3, 10),
(37, 4, 4, 10),

(38, 1, 1, 10),
(38, 2, 2, 10),
(38, 3, 3, 10),
(38, 4, 4, 10),

(39, 1, 1, 10),
(39, 2, 2, 10),
(39, 3, 3, 10),
(39, 4, 4, 10),

(40, 1, 1, 10),
(40, 2, 2, 10),
(40, 3, 3, 10),
(40, 4, 4, 10),

(41, 1, 1, 10),
(41, 2, 2, 10),
(41, 3, 3, 10),
(41, 4, 4, 10),

(42, 1, 1, 10),
(42, 2, 2, 10),
(42, 3, 3, 10),
(42, 4, 4, 10),

(43, 1, 1, 10),
(43, 2, 2, 10),
(43, 3, 3, 10),
(43, 4, 4, 10),

(44, 1, 1, 10),
(44, 2, 2, 10),
(44, 3, 3, 10),
(44, 4, 4, 10),

(45, 1, 1, 10),
(45, 2, 2, 10),
(45, 3, 3, 10),
(45, 4, 4, 10),

(46, 1, 1, 10),
(46, 2, 2, 10),
(46, 3, 3, 10),
(46, 4, 4, 10),

(47, 1, 1, 10),
(47, 2, 2, 10),
(47, 3, 3, 10),
(47, 4, 4, 10),

(48, 1, 1, 10),
(48, 2, 2, 10),
(48, 3, 3, 10),
(48, 4, 4, 10);
-- End of insertion

-- Insert tag for product
INSERT INTO TagProduct (ProductID, TagID)
VALUES 
(1, 1),
(2, 1),
(3, 2),
(4, 2),
(5, 2),
(6, 2),
(7, 2),
(8, 2),
(9, 2),
(10, 2),
(11, 2),
(12, 2),
(13, 2),
(14, 2),
(15, 1),
(16, 1),
(17, 1),
(18, 1),
(19, 2),
(20, 2),
(21, 2),
(22, 2),
(23, 1),
(24, 1),
(25, 1),
(26, 1),
(27, 2),
(28, 1),
(29, 2),
(30, 2),
(31, 2),
(32, 1),
(33, 1),
(34, 2),
(35, 2),
(36, 2),
(37, 1),
(38, 1),
(39, 2),
(40, 2),
(41, 2),
(42, 1),
(43, 2),
(44, 2),
(45, 2),
(46, 2),
(47, 1),
(48, 2);
-- End of insertion

-- Insert picture link for product
INSERT INTO ProductImage (ProductID, Link) VALUES
(1, 'https://i.postimg.cc/7ZyxJbxM/hadest60086-165dffeeb0874e8d957abd58768db8b8-master.jpg'),
(1, 'https://i.postimg.cc/bvm85T13/hadest60088-cb66bfbe62464a34b5c898f7d9af653e-master.jpg'),
(1, 'https://i.postimg.cc/ZR0ZzdcZ/hadest60091-0f1078c579e745329cc3a21f93cdd723-master.jpg'),
(1, 'https://i.postimg.cc/K8rmPFjX/hadest60092-3ab6883e27e74c58902398ed31dc4063-master.jpg'),
(1, 'https://i.postimg.cc/yN1sn53D/hadest60094-4d48371597cb43ec90ffc26d0688bf8b-master.jpg'),
(2, 'https://i.postimg.cc/Y949fVT1/hades0024-2fd0c21809ce4a1e93adcfc797c84748-master.jpg'),
(2, 'https://i.postimg.cc/7P2h5F34/hades0025-2f0a677013e54c45abd5b286e3aede39-master.jpg'),
(2, 'https://i.postimg.cc/5yL2Dz4y/hades0028-afa7ca3f7b7b4fa9af0ee649e896845c-master.jpg'),
(2, 'https://i.postimg.cc/k4k5jsdL/hades0029-368f36061c044d4ab413e7371c520074-master.jpg'),
(2, 'https://i.postimg.cc/vHwBf0hV/hades0032-016409bb4a6c482cbeaab7a829ad4590-master.jpg'),
(2, 'https://i.postimg.cc/7ZxZQbpP/hades0034-2f3b70dc463d4a128f81f774e1796dd8-master.jpg'),
(2, 'https://i.postimg.cc/QxgMbcYn/hd-t6-0638-b27cc998e9604f89a38eb35eba7f39a6-master.jpg'),
(2, 'https://i.postimg.cc/HkBkGdgw/hd-t6-0646-6223426238a3465fb9918d5c83eaa2cd-master.jpg'),
(2, 'https://i.postimg.cc/zfQDB733/hd-t6-0659-db6bed4d94144d9a8bbedfd2e3fde7d5-master.jpg'),
(2, 'https://i.postimg.cc/JnBrBXBx/hd-t6-0693-88d5f441a6274c37ad604edcc05d701b-master.jpg'),
(2, 'https://i.postimg.cc/x8D0Gdv2/hd-t6-0726-21344bc987cd4417b40336f590342a94-master.jpg'),
(3, 'https://i.postimg.cc/7ZYqnd2f/1-7648edaf-d90e-46e8-a8f8-f316b44c3ee7-1.jpg'),
(3, 'https://i.postimg.cc/bNPyssY0/1-8b88ca32-7697-465e-a4e4-92f10f7d06e8-1.jpg'),
(3, 'https://i.postimg.cc/sDJVSs1k/2-3bff33ed-dd68-4d19-928d-908b8e424763-1.jpg'),
(3, 'https://i.postimg.cc/dQrQs8Ft/2-3c08d8b3-31cf-4e0a-a281-a33d8dae8973-1.jpg'),
(3, 'https://i.postimg.cc/BvRssvwK/3-46aaebc8-e9c4-4e1e-a237-7b24edea27d9-1.jpg'),
(3, 'https://i.postimg.cc/13CsCBGM/artboard-1-copy-2-9c9bb1ba-7463-4ab7-944c-23e05a61b4f0-1.jpg'),
(3, 'https://i.postimg.cc/hGXgHmMn/artboard-1-copy-3-2c707733-2b32-4c4a-bd1c-d68ddcfbafdc-1.jpg'),
(3, 'https://i.postimg.cc/JnJ8hW9d/artboard-1-copy-bf58deaa-7e88-4c6c-b1f0-3fd747f35f12-1.jpg'),
(3, 'https://i.postimg.cc/brhhDBPY/artboard-1-e9e7d764-75e6-4c9a-87f5-c74ee0b56212-1.jpg'),
(3, 'https://i.postimg.cc/Dy7h7H5B/1-48d3526c-3277-44cc-a512-cb212e28ac2b-1.jpg'),
(3, 'https://i.postimg.cc/PfWhsTbC/2-5fc3a46e-60cf-406a-a845-3acf8e78251e-1.jpg'),
(3, 'https://i.postimg.cc/SxspM9B9/4-3f3cd219-42e0-4037-bfa7-b8da666408fb-1.jpg'),
(3, 'https://i.postimg.cc/RVGBhdD9/5-68642665-9c30-4a2f-b39a-8bac1796cf49-1.jpg'),
(3, 'https://i.postimg.cc/CKnTvgLL/6-5f1e35c8-05b3-422b-9e43-02119a02da90-1.jpg'),
(3, 'https://i.postimg.cc/QdyZktDy/artboard-1-copy-2-9b3e9b85-bacd-4936-bdb2-6f6a305d2a55-1.jpg'),
(3, 'https://i.postimg.cc/26ppMKVd/artboard-1-copy-3-c36bd486-817f-43a8-bd99-99a6dc36427c-1.jpg'),
(3, 'https://i.postimg.cc/B6xW0BKr/artboard-1-copy-6a88b75f-575b-4d59-911d-57325bcefc9a-1.jpg'),
(3, 'https://i.postimg.cc/2yms8LdV/artboard-1-d9b6aad6-ac01-4a7a-a21e-a83cfc53cc4e-1.jpg'),
(4, 'https://i.postimg.cc/Dy7h7H5B/1-48d3526c-3277-44cc-a512-cb212e28ac2b-1.jpg'),
(4, 'https://i.postimg.cc/PfWhsTbC/2-5fc3a46e-60cf-406a-a845-3acf8e78251e-1.jpg'),
(4, 'https://i.postimg.cc/SxspM9B9/4-3f3cd219-42e0-4037-bfa7-b8da666408fb-1.jpg'),
(4, 'https://i.postimg.cc/RVGBhdD9/5-68642665-9c30-4a2f-b39a-8bac1796cf49-1.jpg'),
(4, 'https://i.postimg.cc/CKnTvgLL/6-5f1e35c8-05b3-422b-9e43-02119a02da90-1.jpg'),
(4, 'https://i.postimg.cc/QdyZktDy/artboard-1-copy-2-9b3e9b85-bacd-4936-bdb2-6f6a305d2a55-1.jpg'),
(4, 'https://i.postimg.cc/26ppMKVd/artboard-1-copy-3-c36bd486-817f-43a8-bd99-99a6dc36427c-1.jpg'),
(4, 'https://i.postimg.cc/B6xW0BKr/artboard-1-copy-6a88b75f-575b-4d59-911d-57325bcefc9a-1.jpg'),
(4, 'https://i.postimg.cc/2yms8LdV/artboard-1-d9b6aad6-ac01-4a7a-a21e-a83cfc53cc4e-1.jpg'),
(5, 'https://i.postimg.cc/4NfDDtN0/1-eb2f12fa-a926-42a7-948b-b240ecb04710-1.jpg'),
(5, 'https://i.postimg.cc/8C69Rmt7/10-717ff19a-0e74-43fd-ad3f-ce40e3fbbc1e-1.jpg'),
(5, 'https://i.postimg.cc/L6xc2XJ8/2-c73dad71-8431-474a-aec1-a555cc849f13-1.jpg'),
(5, 'https://i.postimg.cc/BnyRPVcW/7-01b7c529-e6ea-4afe-9cce-22e96bf814f6-1.jpg'),
(5, 'https://i.postimg.cc/W1tQ3vZ6/8-1.jpg'),
(5, 'https://i.postimg.cc/V6PTD0VD/artboard-1-b944bdd2-a1c8-4ad0-9f76-f6e20457384b-1.jpg'),
(5, 'https://i.postimg.cc/761RcYvD/artboard-1-copy-2-4d7b00c2-f7a7-4de3-9f97-5a2e575d6793-1.jpg'),
(5, 'https://i.postimg.cc/Fz96Q5mS/artboard-1-copy-3-b45b4a48-c02e-4df8-a61f-a4b56350ef29-1.jpg'),
(5, 'https://i.postimg.cc/QC0y84xy/artboard-1-copy-33-1.jpg'),
(6, 'https://i.postimg.cc/0yLHyJPP/1-49e3507e-d5da-400f-984b-0e843724b412-1.jpg'),
(6, 'https://i.postimg.cc/3wmf4Jxp/2-dfb5a840-8668-43b7-9ae6-b0d72246f104-1.jpg'),
(6, 'https://i.postimg.cc/85by4KWz/42-6ac82852-e877-429c-a2c1-2f9a26c0f6a6-1.jpg'),
(6, 'https://i.postimg.cc/XJWHwSrP/artboard-1-2ed00563-f5bf-4a84-9378-62b9a3d21460-1.jpg'),
(6, 'https://i.postimg.cc/sxDTNY99/artboard-1-copy-3-7d2dc57f-8659-4f99-aa56-d444037d19b5.jpg'),
(6, 'https://i.postimg.cc/6qjMJ8Ms/artboard-1-copy-feb550f4-1f00-4557-a61e-16e6a01a8c19.jpg'),
(6, 'https://i.postimg.cc/HWBZ7NdS/ban-sao-cua-7.jpg'),
(7, 'https://i.postimg.cc/sDxnPJ23/2-f690b5ab-65e5-4ca6-a7ac-0fc296dcb84d_1.jpg'),
(7, 'https://i.postimg.cc/MpjdNKR4/20-453483eb-3533-4acc-a53e-c4d3aaeb3e4c_1.jpg'),
(7, 'https://i.postimg.cc/8PYZZrFM/21-e97fb0e4-8e87-443d-bb10-dcf1190ca4d1_1.jpg'),
(7, 'https://i.postimg.cc/wB80p8QB/22-0ed8380f-bad7-4ee4-9571-41727959afbc_1.jpg'),
(7, 'https://i.postimg.cc/cJRT19FB/artboard-1-12070bd4-c67f-4fa8-8db1-81b944033a74_1.jpg'),
(7, 'https://i.postimg.cc/qRpwNsYV/artboard-1-copy-1f2b25b3-ec0b-47c6-a3d5-d6c75f11223c_1.jpg'),
(7, 'https://i.postimg.cc/Wz37n2T5/artboard-1-copy-2-f6d0b5c3-d26e-4967-a8c6-368b85de4241_1.jpg'),
(7, 'https://i.postimg.cc/85wHMFQF/artboard-1-copy-3-eaf1d376-76d4-4d3e-8970-19a5e98566c6_1.jpg'),
(7, 'https://i.postimg.cc/ZY7Hqyx2/artboard-1-copy-4-47281ff4-da31-44eb-a586-93b252bd231b_1.jpg'),
(7, 'https://i.postimg.cc/brB9VKvp/artboard-1-copy-5-64f42b51-6a94-416d-bdf7-923c949976e3_1.jpg'),
(7, 'https://i.postimg.cc/9FDpwFxM/artboard-1-copy-6-936d483d-3d6e-453b-838c-73ec6f55b148_1.jpg'),
(8, 'https://i.postimg.cc/Wbx7GLK7/1-92894086-c213-407f-99ea-2a4c78e0b46d_1.jpg'),
(8, 'https://i.postimg.cc/t4PNDvGG/2-4bbf5ab5-e334-43cc-8c91-01b72eb7cc08_1.jpg'),
(8, 'https://i.postimg.cc/R0BQY1v6/23-b1d33579-49d4-44e6-91cc-2e44320acebd_1.jpg'),
(8, 'https://i.postimg.cc/VNWBPSN7/24-d4c53651-5e63-4b36-883f-aac4ad35ac5d_1.jpg'),
(8, 'https://i.postimg.cc/gjnvhNH5/25-bc8be9c5-a7f8-4981-b848-dea56723cf2b_1.jpg'),
(8, 'https://i.postimg.cc/636VW92B/artboard-1-copy-2-2607dca1-5efc-4ef6-b3da-9cf8d3a09862_1.jpg'),
(8, 'https://i.postimg.cc/85yRsqMM/artboard-1-copy-3-959b419d-3e4e-40fd-9437-772e758cbe00_1.jpg'),
(8, 'https://i.postimg.cc/Jzwby3D6/artboard-1-copy-3513a675-2113-45fc-87fc-8dc8e661c2c6_1.jpg'),
(8, 'https://i.postimg.cc/59R5JWxM/artboard-1-copy-5-e8e50858-4245-4d3b-80bc-26fc8e33b9e6_1.jpg'),
(8, 'https://i.postimg.cc/VL0qLZ60/artboard-1-copy-6-39d5ddd9-9936-4821-959f-d2c5127a5812_1.jpg'),
(8, 'https://i.postimg.cc/wjhDDtD6/artboard-1-f4de26c7-6e37-451b-aaee-abac27ae0bdb_1.jpg'),
(9, 'https://i.postimg.cc/k5s8hkjz/1-d3162669-bc80-4da0-b65e-e82e8f1b7dd1_1.jpg'),
(9, 'https://i.postimg.cc/JhMZx1Qj/2-e1229039-eeac-426c-bcdf-b2787652dc0a_1.jpg'),
(9, 'https://i.postimg.cc/Sst9mcbG/32-0de41630-4c38-4b16-93e1-0336193e2c71_1.jpg'),
(9, 'https://i.postimg.cc/hjGxFp6j/33-c8889045-fd0c-45ac-9ba9-124cf26134dd_1.jpg'),
(9, 'https://i.postimg.cc/RhknH9F6/34-6145a558-7797-48bd-a51e-4b52e8c62b9f_1.jpg'),
(9, 'https://i.postimg.cc/yxgZxWVC/artboard-1-313389b6-fc34-4cbc-acdb-87f16104df1e_1.jpg'),
(9, 'https://i.postimg.cc/ncmmtsRv/artboard-1-copy-2-25fde1a1-3d73-4c65-9aaa-91a4ff2889d0_1.jpg'),
(9, 'https://i.postimg.cc/x1Szj0hB/artboard-1-copy-3-e5da02d3-4358-4cc2-ad22-b14da3f915d9_1.jpg'),
(9, 'https://i.postimg.cc/QM8K5fXb/artboard-1-copy-c96ca61e-9850-42be-b3d6-2fd8cad95b9f_1.jpg'),
(10, 'https://i.postimg.cc/C1dZrdnn/1-79a62dd9-2f23-4f39-91de-4a3f6d239fb6_1.jpg'),
(10, 'https://i.postimg.cc/85372DLK/2-abc288ed-d638-44b6-aac2-49c840cd3bf1_1.jpg'),
(10, 'https://i.postimg.cc/hvdXgwrS/38-f881dd36-d960-476e-be85-f41a851ff356_1.jpg'),
(10, 'https://i.postimg.cc/23NV5wcn/39-9e992b6c-7a28-4b0f-967a-503a9d26ff26_1.jpg'),
(10, 'https://i.postimg.cc/RCNb5zkc/40-383910df-448d-471b-a066-093b194c0624_1.jpg'),
(10, 'https://i.postimg.cc/3rVf3chz/artboard-1-copy-0ad7a984-e017-4927-9679-36a2bafc064c_1.jpg'),
(10, 'https://i.postimg.cc/KvTqjDvj/artboard-1-copy-2-97ade966-3dc5-4575-b3f1-2b4ce7166f99_1.jpg'),
(11, 'https://i.postimg.cc/CM373nHg/117-1.jpg'),
(11, 'https://i.postimg.cc/6pw03smb/117-1-1.jpg'),
(11, 'https://i.postimg.cc/qM2w4BjC/118-1.jpg'),
(11, 'https://i.postimg.cc/XY8gBHMj/118-1-1.jpg'),
(11, 'https://i.postimg.cc/CMBJrs87/5-ec3292cd-bf98-4aa1-a22f-77fc057bd3df-1.jpg'),
(11, 'https://i.postimg.cc/5t2SBB1w/artboard-1-cea613bc-8f21-4829-8a2d-0cdae2d3dd84-1.jpg'),
(11, 'https://i.postimg.cc/wB4c01Tn/artboard-1-copy-b5a86485-13f6-47ee-b446-be3087bddd9e-1.jpg'),
(12, 'https://i.postimg.cc/wjcwcxNd/111-1.jpg'),
(12, 'https://i.postimg.cc/nhtdD9Ck/artboard-1-07ea4d8a-3934-405b-b3e2-9ece5451cced-1.jpg'),
(12, 'https://i.postimg.cc/Hny2JTmR/artboard-1-copy-1449f4ff-ef8b-4540-8088-669cf46dc45e-1.jpg'),
(12, 'https://i.postimg.cc/HL63RS3Z/artboard-1-copy-2-22ac6a3a-6dac-421c-9e59-294f523423dd-1.jpg'),
(12, 'https://i.postimg.cc/yN1vfqh3/artboard-1-copy-3-e1f49100-0a76-483a-95ff-1059d1639d3c-1.jpg'),
(12, 'https://i.postimg.cc/vBjhHcJ5/artboard-10-compressed-1.jpg'),
(12, 'https://i.postimg.cc/SsjVhWvy/artboard-9-compressed-1.jpg'),
(13, 'https://i.postimg.cc/QCG7y1LY/1-fc2065c8-a3bc-4501-8629-4174841cf53f-1.jpg'),
(13, 'https://i.postimg.cc/KjPTSjxG/2-a0a8bbdc-072e-49c9-ae53-4682e3bd6b7f-1.jpg'),
(13, 'https://i.postimg.cc/J4QXpspK/artboard-1-30d2c2ef-9609-4073-ac94-ee7275a0d480-1.jpg'),
(13, 'https://i.postimg.cc/fR29h6n9/artboard-1-copy-3-b8bcb0cc-8914-49d7-96b6-5e9a8e96d2da-1.jpg'),
(13, 'https://i.postimg.cc/3xJvV36h/artboard-1-copy-2-57c1d061-75ad-486e-be72-cc79a9a09768-1.jpg'),
(13, 'https://i.postimg.cc/59LLF7vh/11-1.jpg'),
(13, 'https://i.postimg.cc/8PGWdRmj/12-6c02fe86-0905-443f-9020-e207e425dc12-1.jpg'),
(14, 'https://i.postimg.cc/6qX70KRD/90-1.jpg'),
(14, 'https://i.postimg.cc/tJNsvcN7/artboard-1-b97edbed-ffbc-46d2-8e3e-44627f98f6d1-1.jpg'),
(14, 'https://i.postimg.cc/PfWvqLnh/artboard-1-copy-2-a8ae785c-004c-4165-aed1-a84cfd42eab3-1.jpg'),
(14, 'https://i.postimg.cc/yY7g3V7R/artboard-1-copy-21b4526d-6e1c-44bb-82f0-29a63fdb3df9-1.jpg'),
(14, 'https://i.postimg.cc/DZsJL0Wr/artboard-1-copy-3-f5f66d76-4cee-44f9-8318-856dfaf7b2fd-1.jpg'),
(14, 'https://i.postimg.cc/Yq841JSs/dsc07125-min-compressed-1.jpg'),
(15, 'https://i.postimg.cc/pLTmkqhX/hades4381-f3a4cdd6c052415cb389231ff250298e-master.jpg'),
(15, 'https://i.postimg.cc/kgpBHKKN/hades2577-89a1168eee344c9da5415269f9802565-master.jpg'),
(15, 'https://i.postimg.cc/Dz4830P9/hades4383-53cd9562b2c14fb5897d234c27b5f109-master.jpg'),
(15, 'https://i.postimg.cc/MTkX6wsv/hades4384-e5fa6256882840cda9864e196af86c3d-master.jpg'),
(15, 'https://i.postimg.cc/vBWTDKjX/hades4386-04976c30f6a54998978591421d944993-master.jpg'),
(16, 'https://i.postimg.cc/6qt3Z0xK/hades0040-6e3e09600e0c430d980a561acd8361aa-master.jpg'),
(16, 'https://i.postimg.cc/kGs5vb7b/hades0051-a746c3ff505b41baaf1ffc0e20834165-master.jpg'),
(16, 'https://i.postimg.cc/wxQ3pSDj/hades0046-c3384a7927c74a63ab38cd3e62c62e22-master.jpg'),
(16, 'https://i.postimg.cc/CLM5Lm4H/hades0049-1f524d23893c4014945c7b460f309636-master.jpg'),
(16, 'https://i.postimg.cc/BnXv2bkj/hades0052-20e22fb7b50447d389ab518795f78d37-master.jpg'),
(16, 'https://i.postimg.cc/TYkwyg78/hades0061-2-f4fdfa3c86cb46ac9d94b7de2eb50ae5-master.jpg'),
(16, 'https://i.postimg.cc/cJ345jsZ/hades0063-439be748537d4646b032b60d09fd70e8-master.jpg'),
(16, 'https://i.postimg.cc/zG6G75Y0/hades0066-80084806c5cf463b88090388be02b7d8-master.jpg'),
(16, 'https://i.postimg.cc/mDNZTHz5/hades0067-96bcdb05bbeb4b6abfee9bc4d514c8c9-master.jpg'),
(16, 'https://i.postimg.cc/pTtXNZTj/hades0072-638e374330534a9ba1d4bf33d8b6599f-master.jpg'),
(17, 'https://i.postimg.cc/mZjZq7Gc/99-1.jpg'),
(17, 'https://i.postimg.cc/VvKYCPF9/99-1-1.jpg'),
(17, 'https://i.postimg.cc/TYP2XhRt/artboard-1-53926154-d655-491f-9ec1-fa95ad3395c9-1.jpg'),
(17, 'https://i.postimg.cc/hvDDvtVP/artboard-1-copy-2-c03f1f70-1a65-4f35-a3ec-b686c77cd77c-1.jpg'),
(17, 'https://i.postimg.cc/m26LZRkB/artboard-1-copy-3-ffc21139-683a-480e-921d-f77b8b3b75a5-1.jpg'),
(17, 'https://i.postimg.cc/VkrYdT0M/artboard-1-copy-4-e06689da-180a-4628-9af2-72ba673b66bc-1.jpg'),
(17, 'https://i.postimg.cc/L8cmfTjW/artboard-1-copy-72ec2961-7843-4a35-97ae-9c0522899ddb-1.jpg'),
(17, 'https://i.postimg.cc/1zDsKqH3/fe1652c185adf842fe1652c185adf842b-e1-ba-a3nsaoc-e1-bb-a7aimg-2207-min-6ad04d71-e003-4448-8462-de3e80.jpg'),
(18, 'https://i.postimg.cc/yYWKb5P5/1-dfe02552-fe5e-4400-b4c6-3db579fbb28c_1.jpg'),
(18, 'https://i.postimg.cc/2ySYmXf1/2-c815a32b-1207-48e5-be9c-116898df1ad7_1.jpg'),
(18, 'https://i.postimg.cc/Xq1bvcVQ/artboard-1-copy-2-b7c72bb0-8d39-47e6-9291-57f18edad13b_1.jpg'),
(18, 'https://i.postimg.cc/wxwHFHhL/artboard-1-copy-3-e5663156-cdb4-41c9-bf5a-8263dfbb6566_1.jpg'),
(18, 'https://i.postimg.cc/3J6h5Bfw/artboard-1-copy-a79068b5-97eb-448a-88aa-76d1fb7d9d9f_1.jpg'),
(18, 'https://i.postimg.cc/CxqgFq9s/artboard-1-e4645411-5739-4dd6-be37-2528c06cf4f4.jpg'),
(19, 'https://i.postimg.cc/V6YQBHvK/1-055c44c7-a572-450a-9d97-a1a4d948d1be_1.jpg'),
(19, 'https://i.postimg.cc/85t8yBrN/2-e7b58d54-f357-41fd-8528-497220b62c81_1.jpg'),
(19, 'https://i.postimg.cc/j2FpTxX1/artboard-1-copy-078debb1-7c43-4478-8c7f-35779f324835_1.jpg'),
(19, 'https://i.postimg.cc/SKLb9pSX/artboard-1-copy-2-2bfe0d70-015a-4b84-8306-1de6d7dbc81f.jpg'),
(19, 'https://i.postimg.cc/D0Z9RJqs/artboard-1-copy-3-edb5b7c2-516d-496c-97f9-48715972ce5e_1.jpg'),
(19, 'https://i.postimg.cc/05XRDnsC/artboard-1-d5a23f17-467d-4bc9-92df-263790381b16_1.jpg'),
(20, 'https://i.postimg.cc/xTBwFdqJ/77_1.jpg'),
(20, 'https://i.postimg.cc/X7ZM85N6/77-1_1.jpg'),
(20, 'https://i.postimg.cc/DzTtnYr7/artboard-1-20d76c19-3d01-4da3-8659-24dbbb402e04_1.jpg'),
(20, 'https://i.postimg.cc/BnDrWG1h/artboard-1-copy-2-4fba5551-6d71-4aa2-9d23-d0cc87d45b21_1.jpg'),
(20, 'https://i.postimg.cc/TPJBwq0g/artboard-1-copy-3-7e150d83-5385-4981-a2aa-f42d46a83210_1.jpg'),
(20, 'https://i.postimg.cc/tTRwVZvf/artboard-1-copy-8653b392-8aae-4553-b8f9-467c547119bf_1.jpg'),
(21, 'https://i.postimg.cc/pVFYJXN1/17-1.jpg'),
(21, 'https://i.postimg.cc/brkxyLHj/17-1-1.jpg'),
(21, 'https://i.postimg.cc/50Z5WDz3/18-d88ebfe8-43d3-4d63-9ef5-cae528fd4c74-1.jpg'),
(21, 'https://i.postimg.cc/RF318vWh/18-1-1.jpg'),
(21, 'https://i.postimg.cc/ZKP8X758/22-1-1.jpg'),
(21, 'https://i.postimg.cc/vHw7qFb8/22-1.jpg'),
(21, 'https://i.postimg.cc/QdXQDQFR/artboard-1-5fb66b69-00d7-42c1-af1c-ad5b5aa1c1b9-1.jpg'),
(21, 'https://i.postimg.cc/pLqDLm8y/artboard-1-copy-2-cba45690-1c68-4da5-9c02-e9c02f6afd7f-1.jpg'),
(21, 'https://i.postimg.cc/Zqn6BJFH/artboard-1-copy-3-ef4e02e2-1fe3-44f5-92cf-4eecec078550-1.jpg'),
(21, 'https://i.postimg.cc/MGLV1g9S/artboard-1-copy-4-d97ef48e-1be8-4bf7-9b6d-64ef162f5bfd-1.jpg'),
(21, 'https://i.postimg.cc/TwWm0QsW/artboard-1-copy-50759f05-f517-4956-8115-626ae9f73ee2-1.jpg'),
(22, 'https://i.postimg.cc/Xqmzh2NS/191-1.jpg'),
(22, 'https://i.postimg.cc/rsKHf6kN/191-1-1.jpg'),
(22, 'https://i.postimg.cc/PfR9PqW3/191-2-1.jpg'),
(22, 'https://i.postimg.cc/ZKvg3hjT/191-3-1.jpg'),
(22, 'https://i.postimg.cc/52crSkk8/191-4-1.jpg'),
(22, 'https://i.postimg.cc/BnxVYcdk/191-5-1.jpg'),
(22, 'https://i.postimg.cc/N0BPW2tM/191-6-1.jpg'),
(22, 'https://i.postimg.cc/fLbqb4JQ/191-7-1.jpg'),
(22, 'https://i.postimg.cc/YSRyk22x/dscf0463-1.jpg'),
(22, 'https://i.postimg.cc/qRZbhm3h/dscf0517-1.jpg'),
(22, 'https://i.postimg.cc/0jTVZbZ9/lsjg7434-1.jpg'),
(22, 'https://i.postimg.cc/HncSQQwS/mg-9670-1.jpg'),
(22, 'https://i.postimg.cc/fWVZHp5P/mg-9673-1.jpg'),
(22, 'https://i.postimg.cc/KcCbCfH8/mg-9674-1.jpg'),
(22, 'https://i.postimg.cc/Qx5DD72k/mg-9678-1.jpg'),
(22, 'https://i.postimg.cc/W1vv13jR/mg-9680-1.jpg'),
(22, 'https://i.postimg.cc/Z5xhBSKW/mg-9684-1.jpg'),
(22, 'https://i.postimg.cc/TPkM0JbG/mg-9688-1.jpg'),
(23, 'https://i.postimg.cc/9Xw5PTQ5/115-1.jpg'),
(23, 'https://i.postimg.cc/d3ybqMxt/115-1-1.jpg'),
(23, 'https://i.postimg.cc/tC00BTs1/artboard-1-ae14a45f-f36f-42de-aa92-7e5377c94f6e-1.jpg'),
(23, 'https://i.postimg.cc/DZWDgkFd/artboard-1-copy-048d7526-789a-4c73-980f-73232045aa7d-1.jpg'),
(23, 'https://i.postimg.cc/L6jcwpPx/artboard-1-copy-2-0b544f5c-8dad-407d-8488-283e0f3537be-1.jpg'),
(23, 'https://i.postimg.cc/mgRvG5NB/artboard-1-copy-3-a03bf0d1-6681-4258-8b2e-088b6641a55a-1.jpg'),
(23, 'https://i.postimg.cc/VkR3sWDM/artboard-1-copy-4-b7cad095-01fa-4a40-8a8d-52d9da79d2d9-1.jpg'),
(23, 'https://i.postimg.cc/hGtNXQ5Y/artboard-1-copy-5-b109934a-2576-4e13-8db1-c2fbb23a43aa-1.jpg'),
(23, 'https://i.postimg.cc/wvZK8xFG/3-0384ed77-d7a7-4015-af3f-040143f66128-1.jpg'),
(23, 'https://i.postimg.cc/9Mvkg8Q3/carnau1-1.jpg'),
(24, 'https://i.postimg.cc/MHVFC7nn/134-1.jpg'),
(24, 'https://i.postimg.cc/WpkCqv5s/134-1-1.jpg'),
(24, 'https://i.postimg.cc/FsXqjB63/dsc5897-1.jpg'),
(24, 'https://i.postimg.cc/ncKWT185/hihih-1.jpg'),
(24, 'https://i.postimg.cc/bwzB52pv/nta125-4rtoa9ba-1-1jna-hinh-chi-tiet-goc-do-khac-1-1.jpg'),
(24, 'https://i.postimg.cc/2S2KN8S7/nta125-4rtoa9ba-1-1jna-hinh-chi-tiet-goc-do-khac-2-1.jpg'),
(25, 'https://i.postimg.cc/8CxwTFQM/194-2-1.jpg'),
(25, 'https://i.postimg.cc/8cmbdtFm/194-3-1.jpg'),
(25, 'https://i.postimg.cc/GtFX7bfY/3-b4a71933-d07e-4324-9d42-e82117000129-1.jpg'),
(25, 'https://i.postimg.cc/RFnst7Qg/4-2e65d4de-9170-4285-8edc-d2cc160821c1-1.jpg'),
(25, 'https://i.postimg.cc/qMGm2MyH/5-f6e2f240-a4ad-46d9-bffc-e3b732cbe016-1.jpg'),
(25, 'https://i.postimg.cc/prb0JmsR/6-c45bfce0-4a4d-4e0e-9c4f-132329847637-1.jpg'),
(25, 'https://i.postimg.cc/CxPm6Pcv/7-e698a90b-228d-4abf-bb51-fa3db48aedbf-1.jpg'),
(25, 'https://i.postimg.cc/XNfx072B/dscf0098-green-1.jpg'),
(25, 'https://i.postimg.cc/4NXP4kML/dscf0170-green-1.jpg'),
(26, 'https://i.postimg.cc/hvDmngjV/1-be6945f5-6e7a-4ed4-9fbf-11b871706db5-1.jpg'),
(26, 'https://i.postimg.cc/x86zT7Jh/2-c0b19baa-fbb7-49a7-94d5-7e054c3df9a7.jpg'),
(26, 'https://i.postimg.cc/rFp4Y87k/26-9f0021c7-facb-4942-8c00-c6ae9ee6cdf2-1.jpg'),
(26, 'https://i.postimg.cc/J7xJqy6x/27-184a9dd9-db59-4ed2-a9d6-49c3ba4c533a-1.jpg'),
(26, 'https://i.postimg.cc/tCmxKKHV/28-2e547d62-db8c-4df3-9bfc-a9132c96c833-1.jpg'),
(26, 'https://i.postimg.cc/DwJXnx2p/artboard-1-30bc2295-326b-4cea-8eeb-047adb0b4a93.jpg'),
(26, 'https://i.postimg.cc/YC5m9Q0r/artboard-1-copy-2-a2ab26bb-763f-42a9-a10b-35d3f4042b8f-1.jpg'),
(26, 'https://i.postimg.cc/3w20DW0g/artboard-1-copy-3-2f862338-0301-4f4f-badd-015b2df32f1f-1.jpg'),
(26, 'https://i.postimg.cc/26mbdmng/artboard-1-copy-4-5deb0203-5ad5-41e0-9cce-931854983bef-1.jpg'),
(26, 'https://i.postimg.cc/1tHgJTHX/artboard-1-copy-b788e460-c7d6-4349-88ce-89ea36a10ef3-1.jpg'),
(27, 'https://i.postimg.cc/851cJ4SS/1-cc0a0db8-5463-4f6f-bd53-a3e3c8a69988-1.jpg'),
(27, 'https://i.postimg.cc/SKLjx5Hd/2-5857f50e-0930-448b-967c-45d968fa4d62-1.jpg'),
(27, 'https://i.postimg.cc/50n0wz4F/35-1a3e2694-150b-4081-b494-0d8d7f9fc916-1.jpg'),
(27, 'https://i.postimg.cc/1XXzMzX7/36-97ee14ac-036a-4863-8535-70329291ba35-1.jpg'),
(27, 'https://i.postimg.cc/NfyMHLW5/37-c4a1c4e7-f194-41b3-8ec2-277331a8924c-1.jpg'),
(27, 'https://i.postimg.cc/nrWhWtQF/artboard-1-copy-2-5709d964-57d3-46f4-87e7-cf1d26abd798-1.jpg'),
(27, 'https://i.postimg.cc/FFCzz0CM/artboard-1-copy-3-7d825492-3c06-4311-bc64-daf142a8d39a-1.jpg'),
(27, 'https://i.postimg.cc/zX9fS8rp/artboard-1-copy-4-c5411667-0e12-49c2-88a9-afb2170f68f8-1.jpg'),
(28, 'https://i.postimg.cc/L5GmTBrh/hades8172-a8535cd49bc543e5a79b8815b50d1ac6-master.jpg'),
(28, 'https://i.postimg.cc/7LNYkzTQ/hades5348-95eb633672ff437b94d9d38bdf9b1cee-master.jpg'),
(28, 'https://i.postimg.cc/W3bpb4Gp/hades5336-d289c1d9172941b798abb655d0b2c2fd-master.jpg'),
(28, 'https://i.postimg.cc/SRnyCt1w/287fb5ac6b26cc789537-c5c307c557d748c7afddd6034259fbb1-master.jpg'),
(28, 'https://i.postimg.cc/B6YS8GpT/hades5351-c0000d14a0e14e7ea2de570e331a48f5-master.jpg'),
(29, 'https://i.postimg.cc/kgQ7QSwX/97-1.jpg'),
(29, 'https://i.postimg.cc/jjRtZjk6/97-1-1.jpg'),
(29, 'https://i.postimg.cc/766DrW4f/artboard-1-111491e6-ba26-4a46-974f-30730f6e3da6-1.jpg'),
(29, 'https://i.postimg.cc/TPQGSXZz/artboard-1-copy-2-424c1b86-00f5-487f-9d4d-5068f184a8af-1.jpg'),
(29, 'https://i.postimg.cc/63mKWf6z/artboard-1-copy-3-d87faa0e-b4c4-421b-81a3-1503ab54f953-1.jpg'),
(29, 'https://i.postimg.cc/7hJkfJf4/artboard-1-copy-8a6d8f20-3727-4259-b24a-96261a0c4922-1.jpg'),
(29, 'https://i.postimg.cc/rz6LWyrr/dsc06479-min-compressed-1.jpg'),
(30, 'https://i.postimg.cc/66F7zdVd/96-1.jpg'),
(30, 'https://i.postimg.cc/tRsZhr6q/96-1-1.jpg'),
(30, 'https://i.postimg.cc/XYbr64zq/artboard-1-063cd4c2-c22c-4b03-81ae-41e752d2622b-1.jpg'),
(30, 'https://i.postimg.cc/PrZC7PK0/artboard-1-copy-2-c3e6b062-e16a-4539-ba6d-97af62f5a45a-1.jpg'),
(30, 'https://i.postimg.cc/MpmvWNCD/artboard-1-copy-3-698e6431-b65f-4418-b16a-51b4a67db792-1.jpg'),
(30, 'https://i.postimg.cc/6Q6TTzwN/artboard-1-copy-4-cb64f71c-ad9d-4f1e-97fd-0634fa60c88e-1.jpg'),
(30, 'https://i.postimg.cc/tgrJ6PCn/artboard-1-copy-766c0212-c31c-4386-b7e8-8ec0b29a1054-1.jpg'),
(31, 'https://i.postimg.cc/tTwgSj6X/1-8186fbfa-9222-418e-acff-9346bc4950e2-1.jpg'),
(31, 'https://i.postimg.cc/nzCzGzCR/2-90baa268-c4d8-4ccc-9a8e-0eeb5d9779b0-1.jpg'),
(31, 'https://i.postimg.cc/TY8wnTG0/artboard-1-a4e58024-972c-488a-9160-afe3941caf81-1.jpg'),
(31, 'https://i.postimg.cc/YCMCy8vm/artboard-1-copy-2-0def4527-ec7a-46b0-8434-7582ea5542d2-1.jpg'),
(31, 'https://i.postimg.cc/pLCLN8jc/artboard-1-copy-3-1331f8fb-9880-4d59-9f92-971c5ad7d499-1.jpg'),
(31, 'https://i.postimg.cc/Xq5YtzK9/14-a7b41ebb-8497-4890-9c72-0920f27e54e2-1.jpg'),
(31, 'https://i.postimg.cc/FsjKZwH4/16-33991b0c-875b-404c-ba12-884e9570fb91-1.jpg'),
(31, 'https://i.postimg.cc/N00fQM7s/artboard-1-copy-4-1c242581-6c8e-4808-a032-041fe05a0cfc-1.jpg'),
(31, 'https://i.postimg.cc/nLwVrJ0v/artboard-1-copy-5-9ce20a1d-b6bd-4ff9-8134-8d03eb2536b8-1.jpg'),
(31, 'https://i.postimg.cc/fLtz0rr1/artboard-1-copy-6-fc4b17ef-7da3-4c39-8386-2ae1912c995f-1.jpg'),
(32, 'https://i.postimg.cc/d3GJ5TvB/1-65727f80-e161-423d-b767-56decbf307f1-1.jpg'),
(32, 'https://i.postimg.cc/tR6Rq8Px/2-cb821083-1899-4f29-8d76-714024a0dad4-1.jpg'),
(32, 'https://i.postimg.cc/rpzcvHMz/artboard-1-copy-22-1.jpg'),
(32, 'https://i.postimg.cc/QMPssqdm/artboard-1-copy-33-8b7d9256-24b5-466a-8fdd-d2e2a7b3ea1e-1.jpg'),
(32, 'https://i.postimg.cc/g2gmtp9f/artboard-1-copy1-1.jpg'),
(32, 'https://i.postimg.cc/PxwHq8Lj/artboard-11-1.jpg'),
(32, 'https://i.postimg.cc/Gh1LHzJS/17-977b0cc7-8041-47bb-a192-bbd16ea8d025-1.jpg'),
(32, 'https://i.postimg.cc/wTc6JJwx/18-ee6488e0-a03c-488a-9ba7-1302b9245356-1.jpg'),
(32, 'https://i.postimg.cc/8zpTb5YR/19-6704fe16-f7c5-4983-9393-ecc8ed18dacb-1.jpg'),
(33, 'https://i.postimg.cc/MH0872b3/1-7fe412a0-1bfc-410b-9a72-eea702efe6bb-1.jpg'),
(33, 'https://i.postimg.cc/Kjh23vLg/2-203616d6-732c-4af6-97c6-0a6fbddc85bf-1.jpg'),
(33, 'https://i.postimg.cc/c1fZywy7/artboard-1-34978cbb-5ef6-4679-9cb5-728b2d7e1867-1.jpg'),
(33, 'https://i.postimg.cc/J4qLt8hG/artboard-1-copy-2-4ae6804c-26c1-47a6-8409-1ea219d951c3-1.jpg'),
(33, 'https://i.postimg.cc/g22PZ7w4/artboard-1-copy-3-f4270a73-94a4-4a89-a7fe-0daf85b01bc6-1.jpg'),
(33, 'https://i.postimg.cc/MGc2sjmQ/artboard-1-copy-4-9457d919-f1ee-4762-8947-30f71271e0f1-1.jpg'),
(33, 'https://i.postimg.cc/XY16cgdW/artboard-1-copy-5-3debfb0c-804b-402c-98a4-bf972f9a364d-1.jpg'),
(33, 'https://i.postimg.cc/HLfGFs6P/artboard-1-copy-6-ecebe3b9-c82a-4aa5-8222-a9b7e09e3d66-1.jpg'),
(33, 'https://i.postimg.cc/gJ89pF5c/artboard-1-copy-7-796954de-e683-4f9f-ab20-46655f0ec44c-1.jpg'),
(33, 'https://i.postimg.cc/MHSCK327/artboard-1-copy-83beb62e-e8f8-4fe8-9b22-ca08094bfeb9-1.jpg'),
(33, 'https://i.postimg.cc/ZKZmTn4G/45-d48d1bc2-15cb-479a-9e10-4b9b87578552-1.jpg'),
(34, 'https://i.postimg.cc/J73wd8ZP/71-1.jpg'),
(34, 'https://i.postimg.cc/bJXM3Vv7/71-1-1.jpg'),
(34, 'https://i.postimg.cc/7LJd1rq1/71-3-1.jpg'),
(34, 'https://i.postimg.cc/zGB9BRMc/71-2-1.jpg'),
(34, 'https://i.postimg.cc/wM2Gx6kz/artboard-1-copy-2-96bdd581-cafd-42a1-8ea9-53483e9515c8-1.jpg'),
(34, 'https://i.postimg.cc/2yrtfqhT/artboard-1-copy-3-6b13ddb5-9bb6-4fe2-881b-498ade819362-1.jpg'),
(34, 'https://i.postimg.cc/1RrTrH1d/artboard-1-copy-4-53223a35-7681-43d4-9f8a-9e006e1ec55b-1.jpg'),
(34, 'https://i.postimg.cc/7YPRs2xZ/artboard-1-copy-5-caf3d2db-5c44-4b36-a3ad-467f0008d8e6-1.jpg'),
(34, 'https://i.postimg.cc/GmH5rnT0/artboard-1-copy-a6bee867-5c46-4915-8ee4-9f8859d9981d-1.jpg'),
(34, 'https://i.postimg.cc/4yQrKvsr/1-21d00bba-f4ef-414b-bd78-1e512bff4d09-1.jpg'),
(34, 'https://i.postimg.cc/029TT4Cx/2-b2c331cf-7815-41d2-9f51-3b9dbce2f1ae-1.jpg'),
(34, 'https://i.postimg.cc/vHXJbvPn/3-44b8ffec-2509-4ca4-92b6-3750ed43105d-1.jpg'),
(35, 'https://i.postimg.cc/J4dppY6p/untitled-capture1135-5220199b654246fbb286ef5b29453804.jpg'),
(35, 'https://i.postimg.cc/6p2YgVFt/untitled-capture1136-5d4bddbc21a8440d8bdb7f021895cec5.jpg'),
(35, 'https://i.postimg.cc/j5KcDp2S/untitled-capture1138-c78a683e2d04414aaba5478c6f6aa284.jpg'),
(35, 'https://i.postimg.cc/Hx22X684/untitled-capture1142-9cccabd9ac3440b0b6151f21208d3a7e.jpg'),
(35, 'https://i.postimg.cc/SR1ftjrn/untitled-capture1174-6329434284b6482ca767c17ab2b3211f.jpg'),
(35, 'https://i.postimg.cc/RV8T76h8/untitled-capture1177-956f668b268b4766873a92c81d85dc06-master.jpg'),
(36, 'https://i.postimg.cc/wB5cYw28/1-0671f405-2382-4104-9613-7d93d65a6a56-1.jpg'),
(36, 'https://i.postimg.cc/SKb7PBWf/2-183f0ac3-3c74-4fde-b9c9-5d76caccbb9c-1.jpg'),
(36, 'https://i.postimg.cc/hvYV16Dx/artboard-1-3f49e7b8-1252-4ac1-b5f3-02eab68d936f-1.jpg'),
(36, 'https://i.postimg.cc/gkwV2zsK/artboard-1-copy-2-aee1379d-01cf-4d47-8858-9b7d17a64598-1.jpg'),
(36, 'https://i.postimg.cc/XYd99sTG/artboard-1-copy-3-e2119969-5483-4749-b8dd-5c18729587e9-1.jpg'),
(36, 'https://i.postimg.cc/bvRbBsYq/artboard-1-copy-4-ff0f7a01-c180-433f-bc6c-065e1f09143e-1.jpg'),
(36, 'https://i.postimg.cc/NMJXQSrV/artboard-1-copy-b0832908-8400-40ed-8c45-5d2604afc995-1.jpg'),
(37, 'https://i.postimg.cc/SNz9pZQT/1-0ad80e24-b62f-4480-a172-b5305c117ccf-1.jpg'),
(37, 'https://i.postimg.cc/sgYZMvh5/2-22c02a1d-37eb-4227-8aef-10d86c929247.jpg'),
(37, 'https://i.postimg.cc/ZqjBL2jR/artboard-1-415d8bd0-6d31-428e-928e-a8aef4958d96.jpg'),
(37, 'https://i.postimg.cc/50RXhzhW/artboard-1-copy-2-3977c9c3-788c-47e8-92df-babb583464f9.jpg'),
(37, 'https://i.postimg.cc/C5nRS10B/artboard-1-copy-3-fa31fca8-b140-4286-acad-edac74f2381e.jpg'),
(37, 'https://i.postimg.cc/v84hctH9/artboard-1-copy-4-687eeee7-83c9-4250-aa1f-a060e06e30d3-1.jpg'),
(37, 'https://i.postimg.cc/pLHJcNQY/artboard-1-copy-8c1333aa-9043-4218-9212-f55416bf8788-1.jpg'),
(38, 'https://i.postimg.cc/VNsB1jVJ/1-474fb718-70b4-42a1-8b38-14fa23187829-1.jpg'),
(38, 'https://i.postimg.cc/dVjmzW4M/2-0fba7023-d6c5-4857-8c90-7467f41b8c37-1.jpg'),
(38, 'https://i.postimg.cc/W3hGqsjj/artboard-1-994c0a09-e6d8-4a93-ac24-f942cfc040e2.jpg'),
(38, 'https://i.postimg.cc/yxR0nnkb/artboard-1-copy-2-14d28246-6e86-445e-a46f-b8b6d8e1097f-1.jpg'),
(38, 'https://i.postimg.cc/c4QQn9qK/artboard-1-copy-3-cd11c438-253e-4f06-a055-9a072ac0e75a-1.jpg'),
(38, 'https://i.postimg.cc/Cx7kvRxS/artboard-1-copy-4-ca706c19-5952-4d9a-bdee-d60a95f7596a-1.jpg'),
(38, 'https://i.postimg.cc/SKt8hH9P/artboard-1-copy-e4f99f2b-039b-48fd-aeb2-cebce32dc1ed-1.jpg'),
(39, 'https://i.postimg.cc/3rGGsSxL/20240820-131304-8832c9aa27cb40fdb49007e5a9b8d978.jpg'),
(39, 'https://i.postimg.cc/3xt0Xztw/20240820-131317-e1a5e74c639e43fcb79fc0c34dc5eabb-master.jpg'),
(39, 'https://i.postimg.cc/SRbnQ4xg/20240820-131321-b23b928a810047dd820611c47cd1a05d-master.jpg'),
(39, 'https://i.postimg.cc/Zq9nrn9f/untitled-capture1115-8ca6b1da49894cf6bc8225c709ce4632-master.jpg'),
(39, 'https://i.postimg.cc/JzYtDZhD/untitled-capture1110-82b081c12676489484ab516558460333-master.jpg'),
(39, 'https://i.postimg.cc/ZnmqtLwg/untitled-capture1131-41fe63e3665f40e7a2416d8d9da7ab6f-master.jpg'),
(39, 'https://i.postimg.cc/d1y7K3w7/20240820-131325-54caebc26b984fb58bb88779bc6ccff7-master.jpg'),
(39, 'https://i.postimg.cc/2jnqc10M/20240820-131330-8c93073ce500458a9a96ef3376c539cd-master.jpg'),
(39, 'https://i.postimg.cc/HsHrfTKx/20240820-131333-547a15ed5bcd467abc0e57f6a82567d2-master.jpg'),
(40, 'https://i.postimg.cc/bNzvm3DS/1-85567b56-b0b8-4733-ad56-a1a97ae9ece9-1.jpg'),
(40, 'https://i.postimg.cc/NG8M8WMw/2-1f69d91e-4ed6-4b06-a9ac-845f13aad4e1-1.jpg'),
(40, 'https://i.postimg.cc/rwHFFTh5/artboard-1-6d8f51e4-5b29-4476-aa48-2d658fbe94b1-1.jpg'),
(40, 'https://i.postimg.cc/wv8qLVYv/artboard-1-copy-2-98f3d29a-a222-420b-b5ad-cf682f0955c7-1.jpg'),
(40, 'https://i.postimg.cc/GtbbSm2n/artboard-1-copy-3-e347bd60-d501-4400-9993-9f4007a2d1d8-1.jpg'),
(40, 'https://i.postimg.cc/NGxs2mJs/artboard-1-copy-4-aa46322b-ba1c-4e45-8c3e-342d08e633a9-1.jpg'),
(40, 'https://i.postimg.cc/WbWsXjcx/artboard-1-copy-552d87e0-94c4-4929-bca7-da6538b2b94e-1.jpg'),
(41, 'https://i.postimg.cc/BbZGB7Yq/1-83c6bd82-49b7-47e2-8fe5-ec4e48824183.jpg'),
(41, 'https://i.postimg.cc/mDysMWSX/2-d2053e9d-07ff-48f1-86e4-b4fb38e32d6d.jpg'),
(41, 'https://i.postimg.cc/L4vS8R5h/artboard-1-1eaf198d-4290-44f1-b156-1a75afe0f818-1.jpg'),
(41, 'https://i.postimg.cc/Yq9H4VLR/artboard-1-copy-2-d111f058-f3bf-479b-8c81-4a5094d3f296.jpg'),
(41, 'https://i.postimg.cc/BQVG2gx0/artboard-1-copy-3-c76e8f5f-4fed-4aa4-93cd-b6a5a90f77ac.jpg'),
(41, 'https://i.postimg.cc/R0r5DZsp/artboard-1-copy-4-cbcc9efe-1277-40ba-b89c-4c0c08d5f7bd-1.jpg'),
(41, 'https://i.postimg.cc/vBbRGSdW/artboard-1-copy-76dd788b-e902-43f2-b419-4d32438b18e4-1.jpg'),
(42, 'https://i.postimg.cc/2jHpfw9Z/28-4905f683-7d22-4b7f-9110-a292d48b47e4-1.jpg'),
(42, 'https://i.postimg.cc/gj2fZXjj/28-1-1.jpg'),
(42, 'https://i.postimg.cc/9FSvDfhX/artboard-1-29016b9a-29c8-4894-a438-35ce6404a27f-1.jpg'),
(42, 'https://i.postimg.cc/T3DBgXdV/artboard-1-copy-2-a4682ef3-c55b-401b-9c60-bd68de187bf8-1.jpg'),
(42, 'https://i.postimg.cc/43vM2bhn/artboard-1-copy-3-6512851b-406a-434f-b7de-cea4054aaf09.jpg'),
(42, 'https://i.postimg.cc/ydvGs1jH/artboard-1-copy-4-b345396d-b271-4745-aa6f-cd43162194b1-1.jpg'),
(42, 'https://i.postimg.cc/D0NY4xRp/artboard-1-copy-5-0b8720c3-a88a-4dc1-92ca-c7bd646cab96-1.jpg'),
(42, 'https://i.postimg.cc/gkw7bP6C/artboard-1-copy-81664e0d-98ca-40ee-a76e-6cd3d2ac0808-1.jpg'),
(43, 'https://i.postimg.cc/cJWk3g5W/1-61e699dd-5311-4a93-bf89-6977dae6d0bd-1.jpg'),
(43, 'https://i.postimg.cc/pXg621fp/2-8f6f89c0-6d6c-40f0-89f9-ed2e7546bdd9.jpg'),
(43, 'https://i.postimg.cc/XJHst2NN/artboard-1-c9a051e6-b42d-4ccf-b6fb-a97f5c52133e-1.jpg'),
(43, 'https://i.postimg.cc/SRHDxJzw/artboard-1-copy-2-6725a28e-7e5e-4046-b77e-c44d1f578dfe-1.jpg'),
(43, 'https://i.postimg.cc/CLfc6H3Y/artboard-1-copy-3-56463321-8633-4f5d-8b82-21d4b52759c0-1.jpg'),
(43, 'https://i.postimg.cc/sgYw3ffN/artboard-1-copy-4-e48e1eb3-6b22-454d-ba96-90f2eea8a69e-1.jpg'),
(43, 'https://i.postimg.cc/hGMs7Cd8/artboard-1-copy-5-99108ac9-0120-4521-b560-62a07ab44128-1.jpg'),
(43, 'https://i.postimg.cc/LXbVxnvn/artboard-1-copy-c2a750ea-ca8c-451a-a3c4-ab84f245b3a6-1.jpg'),
(44, 'https://i.postimg.cc/pr5J4R1k/hades-0071-1c433f61dd0b4afdbec407fccd9a527d-master.jpg'),
(44, 'https://i.postimg.cc/mZcxB9vp/hades-0072-900438157f7a424488d33c7654d901b5-master.jpg'),
(44, 'https://i.postimg.cc/KvLdg2vn/hades-0073-e1057ecbc31f48608adc6768fb04bb20.jpg'),
(44, 'https://i.postimg.cc/Gp3NNTPQ/hades-0074-8c6f03d811c24149898a2c22623739b4-master.jpg'),
(44, 'https://i.postimg.cc/DZ8NfVM4/hades-0076-74e3c5b9105549d99e80b15e393e41e2-master.jpg'),
(45, 'https://i.postimg.cc/XN98f7d7/hades4134-e21501a09a2a4de28997a799ef5a35b2-master.jpg'),
(45, 'https://i.postimg.cc/5yJ87LL9/hades8236-b54a0c90bdd3432b9e7584f18f38a75e-master.jpg'),
(45, 'https://i.postimg.cc/P55QnWcP/hades4137-8951f1ef5ebf41f8a961153a61adea30-master.jpg'),
(45, 'https://i.postimg.cc/Yq3zPnd5/hades4136-628a329441be42d3bd28c48556797274-master.jpg'),
(45, 'https://i.postimg.cc/26s0z1Wg/hades1556-68307da61e6d4e22b93818495698d5f6-master.jpg'),
(45, 'https://i.postimg.cc/zGz0XZLD/hades1569-eaefc19fe53a4363a7a59f8e9aceea5f.jpg'),
(45, 'https://i.postimg.cc/RhwXqvQh/hades1558-15e6c580cd4a4b1db759e6cf0a36727e-master.jpg'),
(45, 'https://i.postimg.cc/Nf7dcfjP/hades1563-ad09ebc43cb14167a7ea28a2fe31de3c-master.jpg'),
(45, 'https://i.postimg.cc/mrhjCYN0/hades2333-903f9b6ca16840aeaf457dcf2da690aa-master.jpg'),
(45, 'https://i.postimg.cc/DfQrWp3S/hades8475-a8576466e64440c09e8ad05a544398ba-master.jpg'),
(45, 'https://i.postimg.cc/PqBWDBqw/hades6427-2ca7a63f257544d0aa2211ce20f8489b-master.jpg'),
(45, 'https://i.postimg.cc/0NHtBWyk/hades0528-3af4790189474e478fa42a0e4ed39781-master.jpg'),
(46, 'https://i.postimg.cc/Qx0cmBts/1-4908b4ac-ae6b-4693-bd7e-249eb752f8b3-1.jpg'),
(46, 'https://i.postimg.cc/4NJzpvXB/1-5168acc1-4195-4279-9219-d17a54a47ac2-1.jpg'),
(46, 'https://i.postimg.cc/mDf9kCd3/1-be1f80ec-4a64-45eb-b882-6eaa729f571e-1.jpg'),
(46, 'https://i.postimg.cc/76322hcF/1-c2acd013-99ba-4163-9b15-807fdbf95d08.jpg'),
(47, 'https://i.postimg.cc/nzhJvwwn/z4908503993219-2a3ba233739e6c352441ded60c16ae49-3e0378a895c9427595ff337f4cb745f8.jpg'),
(47, 'https://i.postimg.cc/7LV4Jy9B/z4908503983927-fc7610a26ee49b0eb9b5da1b83606ccf-46196b4224334cb4b6336607555fe762-master.jpg'),
(47, 'https://i.postimg.cc/15fzx4By/dsc03601-1aa15e176bcb4275a1488fc13b7ed931.jpg'),
(47, 'https://i.postimg.cc/4yFN7TfC/dsc03603-1427e5867a05411e87f4a1e1545c1f0e.jpg'),
(47, 'https://i.postimg.cc/wvq6wB38/hades-001076-696683b1655d4491a5abca82c849b972.jpg'),
(47, 'https://i.postimg.cc/90zcjNGL/hades-001099-3e9af71ac2744a9c8a38f121fa6b61b9.jpg'),
(47, 'https://i.postimg.cc/9XNWBChK/hades-001147-36478207d30b4ad1865cf3d68e4c53a8.jpg'),
(47, 'https://i.postimg.cc/02TPLnrC/hades-001263-dd095cbcbfd84077946289ba3d864a2a.jpg'),
(48, 'https://i.postimg.cc/x8WVVjjd/173.jpg'),
(48, 'https://i.postimg.cc/vT1wKxsj/173-1.jpg'),
(48, 'https://i.postimg.cc/Bn59RGrq/173-2-1.jpg'),
(48, 'https://i.postimg.cc/nL08cGVg/173-3.jpg'),
(48, 'https://i.postimg.cc/9MnvyX7B/artboard-1-copy-2-249174f7-b6fa-45c0-9068-346e1e3e3b96.jpg'),
(48, 'https://i.postimg.cc/rsR7j6G2/artboard-1-copy-3-66821acd-fe5a-406f-9072-88056b227946.jpg'),
(48, 'https://i.postimg.cc/VsSNpdDm/artboard-1-copy-4-3433240a-2057-483c-940b-c08cd9b848e7.jpg');
-- Ends of insertion

-- Insert some color
INSERT INTO Color (Name, ColorCode) VALUES
('Black', '#000000'),
('Red', '#FF0000'),
('White', '#FFFFFF'),
('Grey', '#808080'),
('Brown', '#A52A2A'),
('Cream', '#FFFDD0'),
('Green', '#008000'),
('Green', '#008000');

-- Insert subcategory
INSERT INTO SubCategory (Name, OriginalType) VALUES
('Skirt', 'Bottom'),
('T-Shirt', 'Top'),
('Shirt', 'Top'),
('Sweat Shirt', 'Top'),
('Cardigan', 'Top'),
('Jacket', 'Outerwear'),
('Hoodie', 'Outerwear'),
('Pants', 'Bottom'),
('Shorts', 'Bottom');

-- Insert Tag
INSERT INTO Tag (Name, Color) VALUES
('Hades', '#008000'),  -- Green
('DirtyCoin', '#0000FF'),    -- Blue
('Sale', '#FF0000');  -- Red

INSERT INTO Slider(Content, Link, BackLink) VALUES
('Dirty Coin Collection', 'https://bizweb.dktcdn.net/100/369/010/themes/914385/assets/slide-img1.jpg?1727316743604', 'newcollection?sliderId=1&content=Dirty%20Coin%20Collection&tag=DirtyCoin'),
('Hades Collection', 'https://theme.hstatic.net/1000306633/1001194548/14/slideshow_2.jpg?v=235', 'newcollection?sliderId=2&content=Hades%20Collection&tag=Hades');

-- ------------------------------------------------------------------- END OF INSERT SECTION -------------------------------------------------------------------------

SELECT * FROM Product;
SELECT * FROM Color;
SELECT * FROM SubCategory;

SELECT DISTINCT
	t.ID as TagID,
	t.Name as TagName,
    t.Color AS TagColor
FROM
	Product p
    JOIN TagProduct tp ON p.ID = tp.ProductID
    JOIN Tag t ON tp.TagID = t.ID;
    
SELECT DISTINCT
    s.ID,
    s.Name,
    s.OriginalType
FROM
	Product p
    JOIN SubCategory s ON s.ID = p. SubCategoryID
WHERE p.ID = 1;
    
SELECT CONCAT('ALTER TABLE ', TABLE_NAME, ' AUTO_INCREMENT = 1;')
FROM information_schema.TABLES
WHERE TABLE_SCHEMA = 'freezedb' AND AUTO_INCREMENT IS NOT NULL;

SELECT * FROM Tag;

SELECT DISTINCT
    t.ID as TagID,
    t.Name as TagName,
    t.Color as TagColorCode
FROM
    Product p
    JOIN TagProduct pt ON pt.ProductID = p.ID
    JOIN Tag t ON t.ID = pt.TagID
WHERE
    p.ID = 1  -- Filter by ProductID for each product
ORDER BY t.ID;

DELIMITER $$

CREATE TRIGGER update_product_star 
AFTER INSERT ON feedback
FOR EACH ROW
BEGIN
    DECLARE avg_star FLOAT;

    -- Calculate the average star rating for the product from the feedback table
    SELECT AVG(Star) INTO avg_star
    FROM feedback
    WHERE ProductID = NEW.ProductID;

    -- Update the product table with the new average star rating
    UPDATE product
    SET Star = avg_star
    WHERE ID = NEW.ProductID;
END $$

DELIMITER ;

USE freezedb;

INSERT INTO `freezedb`.`order`(ProductID, Quantity, TotalPrice, ColorID, SizeID) VALUES (1, 4, 300000.0, 1, 2);
INSERT INTO `freezedb`.`order`(ProductID, Quantity, TotalPrice, ColorID, SizeID) VALUES (1, 4, 300000.0, 1, 2);
INSERT INTO feedback(UserID, OrderID, ProductID, Star, Comment) VALUES (1, 2, 1, 5, 'nice');

SELECT * FROM `freezedb`.`order`;
SELECT * FROM feedback;
SELECT * FROM Product;

SELECT 
	p.ID as ProductID,
    s.ID,
	p.Name as ProductName,
	p.ThumbnailImage as ProductThumbnailImage,
	p.Price as ProductPrice,
	t.Name as ProductTagName,
    t.Color as ProductTagColorCode
FROM Product p
	JOIN SubCategory s ON s.ID = p.SubCategoryID
	JOIN TagProduct tp ON p.ID = tp.ProductID 
	JOIN Tag t ON tp.TagID = t.ID
WHERE t.ID IN ('1') 
    AND s.ID IN ('2') 
    AND p.Price BETWEEN 100000.0 AND 500000.0
ORDER BY 
	p.ID;
	
    
SELECT 
    p.ID AS productID,
    p.Name AS ProductName,
    p.Price AS ProductPrice,
    p.ThumbnailImage AS ProductImage,
    s.Name AS subCategory,
    t.Name AS Tag,
    t.Color AS TagColor,
    p.Star AS ProductStar,
    COUNT(f.ID)
FROM
    Product p
        JOIN
    SubCategory s ON s.ID = p.SubCategoryID
        JOIN
    TagProduct tp ON tp.ProductID = p.ID
        JOIN
    Tag t ON t.ID = tp.TagID
		JOIN
	feedback f ON f.ProductID = p.ID
ORDER BY p.ID;

SELECT
	COUNT(*)
FROM feedback
WHERE
	ProductID = 1;
    
SELECT 
    p.ID AS productID,
    p.Name AS ProductName,
    p.Price AS ProductPrice,
    p.ThumbnailImage AS ProductImage,
    s.Name AS subCategory,
    t.Name AS Tag,
    t.Color AS TagColor,
    p.Star AS ProductStar,
    COUNT(f.ID) AS FeedbackCount
FROM
    Product p
        JOIN
    SubCategory s ON s.ID = p.SubCategoryID
        JOIN
    TagProduct tp ON tp.ProductID = p.ID
        JOIN
    Tag t ON t.ID = tp.TagID
        LEFT JOIN
    feedback f ON f.ProductID = p.ID
GROUP BY 
    p.ID, p.Name, p.Price, p.ThumbnailImage, s.Name, t.Name, t.Color, p.Star
ORDER BY p.ID;


