/*
 Navicat Premium Data Transfer

 Source Server         : localhost_3306
 Source Server Type    : MySQL
 Source Server Version : 50515
 Source Host           : localhost:3306
 Source Schema         : community

 Target Server Type    : MySQL
 Target Server Version : 50515
 File Encoding         : 65001

 Date: 24/02/2020 18:06:51
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for comment
-- ----------------------------
DROP TABLE IF EXISTS `comment`;
CREATE TABLE `comment`  (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `PARENT_ID` bigint(20) NOT NULL,
  `TYPE` int(11) NOT NULL,
  `COMMENTATOR` bigint(20) NOT NULL,
  `GMT_CREATE` bigint(20) NOT NULL,
  `GMT_MODIFIED` bigint(20) NOT NULL,
  `LIKE_COUNT` bigint(20) DEFAULT 0,
  `CONTENT` varchar(1024) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `COMMENT_COUNT` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of comment
-- ----------------------------
INSERT INTO `comment` VALUES (1, 5, 1, 19, 1571277040799, 1571277040799, 0, '1111111', 0);
INSERT INTO `comment` VALUES (2, 6, 1, 19, 1571277208304, 1571277208304, 0, '111111', 0);
INSERT INTO `comment` VALUES (3, 16, 1, 19, 1580624768022, 1580624768021, 0, '11111', 0);
INSERT INTO `comment` VALUES (4, 18, 1, 19, 1580705604737, 1580705604737, 0, '2222', 0);
INSERT INTO `comment` VALUES (5, 18, 1, 19, 1580705659518, 1580705659518, 0, '333', 0);
INSERT INTO `comment` VALUES (6, 19, 1, 19, 1580712855801, 1580712855801, 0, '111', 1);
INSERT INTO `comment` VALUES (7, 6, 2, 19, 1580712927989, 1580712927989, 0, '111', NULL);
INSERT INTO `comment` VALUES (8, 18, 1, 23, 1581323736868, 1581323736868, 0, '1111', 0);

-- ----------------------------
-- Table structure for notification
-- ----------------------------
DROP TABLE IF EXISTS `notification`;
CREATE TABLE `notification`  (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `NOTIFIER` bigint(20) NOT NULL,
  `RECEIVER` bigint(20) NOT NULL,
  `OUTERID` bigint(20) NOT NULL,
  `TYPE` int(11) NOT NULL,
  `GMT_CREATE` bigint(20) NOT NULL,
  `STATUS` int(11) NOT NULL DEFAULT 0,
  `NOTIFIER_NAME` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `OUTER_TITLE` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of notification
-- ----------------------------
INSERT INTO `notification` VALUES (1, 19, 1, 5, 1, 1571277040854, 0, 'qwer461461', '111');
INSERT INTO `notification` VALUES (2, 19, 2, 6, 1, 1571277208342, 0, 'zuihou461', 'asdasd');
INSERT INTO `notification` VALUES (3, 19, 4, 18, 1, 1580705659522, 1, 'zuihou66', '啦啦啦');
INSERT INTO `notification` VALUES (4, 19, 4, 19, 1, 1580712856014, 0, '1348520123', '1111');
INSERT INTO `notification` VALUES (5, 23, 19, 18, 1, 1581323736886, 1, 'admin', '啦啦啦');

-- ----------------------------
-- Table structure for question
-- ----------------------------
DROP TABLE IF EXISTS `question`;
CREATE TABLE `question`  (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `TITLE` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `DESCRIPTION` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `GMT_CREATE` datetime DEFAULT NULL,
  `GMT_MODIFIED` datetime DEFAULT NULL,
  `CREATOR` bigint(20) DEFAULT NULL,
  `COMMENT_COUNT` int(11) DEFAULT 0,
  `VIEW_COUNT` int(11) DEFAULT 0,
  `LIKE_COUNT` int(11) DEFAULT 0,
  `TAG` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 25 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of question
-- ----------------------------
INSERT INTO `question` VALUES (6, 'asdasd ', '123asd a', '2019-10-17 00:00:00', NULL, 19, 1, 12, 0, 'css');
INSERT INTO `question` VALUES (7, 'img', '![](/uploadImg/6602-d9c62f0d-80bb-4745-9b75-0abb1f30441b.jpg)', '2019-10-17 00:00:00', NULL, 19, 0, 3, 0, 'html');
INSERT INTO `question` VALUES (8, 'spring', 'spring', '2019-10-27 00:00:00', NULL, 19, 0, 5, 0, 'javascript');
INSERT INTO `question` VALUES (9, 'spring boot', 'spring boot', '2019-10-27 00:00:00', NULL, 19, 0, 3, 0, 'javascript');
INSERT INTO `question` VALUES (10, 'spring', '123121321', '2019-10-27 00:00:00', NULL, 19, 0, 4, 0, 'javascript');
INSERT INTO `question` VALUES (12, 'index12', '123123', '2019-10-27 19:18:41', NULL, 19, 0, 5, 0, 'php');
INSERT INTO `question` VALUES (14, '啦啊啦', '![](/uploadImg/spring IOC 注释-ee7a41d6-01b4-4a4a-a580-9f901b20b2a6.png)', '2019-10-31 10:42:30', NULL, 19, 0, 2, 0, 'javascript,php');
INSERT INTO `question` VALUES (15, '123123', '![](/uploadImg/userimg-9916f38e-ca65-4f6f-afc4-44eaa19a66cf.png)', '2019-10-31 10:44:01', NULL, 19, 0, 1, 0, 'node.js');
INSERT INTO `question` VALUES (16, '啦啦啦', '![](/uploadImg/无标题-04dd0e76-61b5-4e90-8e99-280564c93f64.png)', '2019-10-31 10:53:28', NULL, 19, 1, 28, 0, 'python');
INSERT INTO `question` VALUES (17, '我的问题111', '![](/uploadImg/6602-a14f0ca6-17c7-4b85-83f9-cd7975e60dfd.jpg)', '2019-10-31 14:45:07', NULL, 19, 0, 41, 0, 'css,html');
INSERT INTO `question` VALUES (18, '啦啦啦', '22222![](/uploadImg/6602-3ea521ad-f87e-49ec-9ff4-669dbf4101f7.jpg)', '2020-02-03 12:53:13', NULL, 19, 3, 23, 0, 'javascript');
INSERT INTO `question` VALUES (20, '哈哈', 'aa', '2020-02-03 14:52:15', NULL, 19, 0, 25, 0, 'javascript');
INSERT INTO `question` VALUES (21, '测试删除', '1111111', '2020-02-08 14:30:16', NULL, 21, 0, 0, 0, 'php');
INSERT INTO `question` VALUES (24, 'zuihou7777', '啦啦啦啦啦啦啦', '2020-02-12 15:05:15', NULL, 19, 0, 5, 0, 'javascript');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `ID` bigint(8) NOT NULL AUTO_INCREMENT,
  `ACCOUNT_ID` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `TOKEN` char(36) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `GMT_CREATE` datetime DEFAULT NULL,
  `GMT_MODIFIED` datetime DEFAULT NULL,
  `TYPE` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `PASSWORD` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 30 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (19, 'zuihou777', '82e32966-33ea-429a-b61a-28f3498fe3d3', '2020-02-06 12:27:56', '2020-02-06 12:27:56', '1', '$2a$10$GC/AcyRsPOPh7asOKGyZDuTEJVG2Yrm6uo15Y4TuseOJ2vbsWFQxi');
INSERT INTO `user` VALUES (23, 'admin', '38727228-06a1-41ea-82bb-ac8569425393', '2020-02-09 20:01:56', '2020-02-09 20:01:56', '1', '$2a$10$wBeWa96ARETvbySAP37NiO8uuc687w/5SJygqUiiO0p.sbz0cFJSu');
INSERT INTO `user` VALUES (24, 'zuihou111', '24b95ead-fcc2-4266-bf72-312b0d424282', '2020-02-10 10:21:42', '2020-02-10 10:21:42', '1', '$2a$10$1E/kb47SgvwB5QceIRcfweWbN6jM9g/4j2Z5DlwvazbRvbui6yYYG');
INSERT INTO `user` VALUES (25, 'zuihou666', 'c6355656-c9e1-4e72-84b0-f9eec7bd5d91', '2020-02-10 10:41:58', '2020-02-10 10:41:58', '1', '$2a$10$odaQutcesKlVktZhTJXqle/RTXNckDzJdhbicTjmAYEh/tRjKlfcq');
INSERT INTO `user` VALUES (26, 'zuihou444', '7b1c201b-3e55-4e38-80f9-d13c44e85e0b', '2020-02-10 10:59:37', '2020-02-10 10:59:37', '1', '$2a$10$JtW8/CG4TLaTXQw4XH/wp.5XZujv0Yvqop4AMzIshitijeB9ncke.');
INSERT INTO `user` VALUES (27, 'zuihou444', '438b54cb-6b76-44a0-a921-9477a44b816b', '2020-02-10 11:01:32', '2020-02-10 11:01:32', '1', '$2a$10$t2gTpzKQrRix7yBXE2PB7.Jd3i5YqXNTBJKtv0KHxkX6miA2MBUyW');
INSERT INTO `user` VALUES (28, 'zuihou222', '0e97f212-2e8b-421b-8ea9-d145897bf692', '2020-02-10 11:22:31', '2020-02-10 11:22:31', '1', '$2a$10$X.3YUqH6sWzrt8JpVgICJ.dyddjBzNuRUMJ.iwHQSMPZoxEh9N19a');
INSERT INTO `user` VALUES (29, 'zuihou8989', 'fb0701cf-4e40-47e7-8508-7fef36b7a70b', '2020-02-10 11:24:55', '2020-02-10 11:24:55', '1', '$2a$10$klS8HnREN9QvNB9IRtBaKehlitWLl4rUfiMBzp2MuDZLBqEbiiaiS');

-- ----------------------------
-- Table structure for user_info
-- ----------------------------
DROP TABLE IF EXISTS `user_info`;
CREATE TABLE `user_info`  (
  `id` bigint(8) NOT NULL AUTO_INCREMENT,
  `bio` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `sex` tinyint(1) DEFAULT NULL,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `GMT_CREATE` datetime DEFAULT NULL,
  `GMT_MODIFIED` datetime DEFAULT NULL,
  `ACCOUNT_ID` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `avatar_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 12 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of user_info
-- ----------------------------
INSERT INTO `user_info` VALUES (1, 'spring security', 0, '张伟777', '2020-02-06 12:27:56', '2020-02-06 12:29:07', 'zuihou777', NULL, '4616461111114@qq.com');
INSERT INTO `user_info` VALUES (5, NULL, NULL, NULL, '2020-02-09 20:01:56', NULL, 'admin', NULL, NULL);
INSERT INTO `user_info` VALUES (6, NULL, NULL, NULL, '2020-02-10 10:21:42', NULL, 'zuihou111', NULL, NULL);
INSERT INTO `user_info` VALUES (7, NULL, NULL, NULL, '2020-02-10 10:40:29', NULL, 'zuihou666', NULL, NULL);
INSERT INTO `user_info` VALUES (8, NULL, NULL, NULL, '2020-02-10 10:59:32', NULL, 'zuihou444', NULL, NULL);
INSERT INTO `user_info` VALUES (9, NULL, NULL, NULL, '2020-02-10 11:01:14', NULL, 'zuihou444', NULL, NULL);
INSERT INTO `user_info` VALUES (10, NULL, NULL, NULL, '2020-02-10 11:22:24', NULL, 'zuihou222', NULL, NULL);
INSERT INTO `user_info` VALUES (11, NULL, NULL, NULL, '2020-02-10 11:24:52', NULL, 'zuihou8989', NULL, NULL);

SET FOREIGN_KEY_CHECKS = 1;
