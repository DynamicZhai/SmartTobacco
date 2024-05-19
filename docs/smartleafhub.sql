/*
 Navicat Premium Data Transfer

 Source Server         : 后台
 Source Server Type    : MySQL
 Source Server Version : 80024
 Source Host           : 139.155.150.150:3307
 Source Schema         : smartleafhub

 Target Server Type    : MySQL
 Target Server Version : 80024
 File Encoding         : 65001

 Date: 16/04/2024 10:27:20
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for alert_data
-- ----------------------------
DROP TABLE IF EXISTS `alert_data`;
CREATE TABLE `alert_data`  (
  `alert_type` int NOT NULL,
  `alert_message` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `alert_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `alert_data` decimal(10, 2) NULL DEFAULT NULL,
  `id` int NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for automation_status_table
-- ----------------------------
DROP TABLE IF EXISTS `automation_status_table`;
CREATE TABLE `automation_status_table`  (
  `light_status` tinyint(1) NOT NULL DEFAULT 0,
  `pump_status` tinyint(1) NOT NULL DEFAULT 0,
  `fen_status` tinyint(1) NOT NULL DEFAULT 0,
  `timestamp` timestamp NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for device_state
-- ----------------------------
DROP TABLE IF EXISTS `device_state`;
CREATE TABLE `device_state`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `pump_ctrl_state` int NULL DEFAULT NULL,
  `pump_power_state` int NULL DEFAULT NULL,
  `fan_mode` int NULL DEFAULT NULL,
  `fan_level` int NULL DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 50 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for light_data
-- ----------------------------
DROP TABLE IF EXISTS `light_data`;
CREATE TABLE `light_data`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `light_intensity` decimal(10, 2) NULL DEFAULT NULL,
  `reading_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1024 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for light_instructions
-- ----------------------------
DROP TABLE IF EXISTS `light_instructions`;
CREATE TABLE `light_instructions`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `light_mode` int NULL DEFAULT NULL,
  `light_level` int NULL DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 79 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for opinions
-- ----------------------------
DROP TABLE IF EXISTS `opinions`;
CREATE TABLE `opinions`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `phone_number` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `description` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 12 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for soil_data
-- ----------------------------
DROP TABLE IF EXISTS `soil_data`;
CREATE TABLE `soil_data`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `soil_humidity` decimal(10, 2) NULL DEFAULT NULL,
  `reading_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 187 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for temperature_data
-- ----------------------------
DROP TABLE IF EXISTS `temperature_data`;
CREATE TABLE `temperature_data`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `air_temperature` decimal(10, 2) NULL DEFAULT NULL,
  `reading_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 175 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for userinfo
-- ----------------------------
DROP TABLE IF EXISTS `userinfo`;
CREATE TABLE `userinfo`  (
  `id` int NOT NULL,
  `nickname` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `email` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  CONSTRAINT `userinfo_ibfk_1` FOREIGN KEY (`id`) REFERENCES `users` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for users
-- ----------------------------
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `password` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `phone_number` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 15 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
