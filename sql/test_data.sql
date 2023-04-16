-- CHARACTER IMAGE
INSERT INTO `OctoDream`.`CHARACTER_IMAGE_TB` (`CHARACTER_IMAGE_PK`, `CHARACTER_IMAGE_NM`, `CHARACTER_IMAGE_URL`) VALUES ('1', '문어1', '/Users/jeongchan-yeong/Documents/GitHub/Octo-Backend/sql/test_image/문어1.png');
INSERT INTO `OctoDream`.`CHARACTER_IMAGE_TB` (`CHARACTER_IMAGE_PK`, `CHARACTER_IMAGE_NM`, `CHARACTER_IMAGE_URL`) VALUES ('2', '문어2', '/Users/jeongchan-yeong/Documents/GitHub/Octo-Backend/sql/test_image/문어2.png');
INSERT INTO `OctoDream`.`CHARACTER_IMAGE_TB` (`CHARACTER_IMAGE_PK`, `CHARACTER_IMAGE_NM`, `CHARACTER_IMAGE_URL`) VALUES ('3', '문어3', '/Users/jeongchan-yeong/Documents/GitHub/Octo-Backend/sql/test_image/문어3.png');
INSERT INTO `OctoDream`.`CHARACTER_IMAGE_TB` (`CHARACTER_IMAGE_PK`, `CHARACTER_IMAGE_NM`, `CHARACTER_IMAGE_URL`) VALUES ('4', '문어4', '/Users/jeongchan-yeong/Documents/GitHub/Octo-Backend/sql/test_image/문어4.png');
INSERT INTO `OctoDream`.`CHARACTER_IMAGE_TB` (`CHARACTER_IMAGE_PK`, `CHARACTER_IMAGE_NM`, `CHARACTER_IMAGE_URL`) VALUES ('5', '문어5', '/Users/jeongchan-yeong/Documents/GitHub/Octo-Backend/sql/test_image/문어5.png');
-- USER 
INSERT INTO `OctoDream`.`USER_TB` (`USER_PK`, `USER_EMAIL`, `CHARACTER_NM`, `EXPERIENCE_VALUE`, `CHARACTER_IMAGE_URL`) VALUES ('1', 'asd123@naver.com', '나의 문어', '0', '/Users/jeongchan-yeong/Documents/GitHub/Octo-Backend/sql/test_image/문어1.png');
INSERT INTO `OctoDream`.`USER_TB` (`USER_PK`, `USER_EMAIL`, `CHARACTER_NM`, `EXPERIENCE_VALUE`, `CHARACTER_IMAGE_URL`) VALUES ('2', 'zxc123@gmail.com', '문돌이', '0', '/Users/jeongchan-yeong/Documents/GitHub/Octo-Backend/sql/test_image/문어5.png');
INSERT INTO `OctoDream`.`USER_TB` (`USER_PK`, `USER_EMAIL`, `CHARACTER_NM`, `EXPERIENCE_VALUE`, `CHARACTER_IMAGE_URL`) VALUES ('3', 'qwe123@naver.com', '홍길동', '0', '/Users/jeongchan-yeong/Documents/GitHub/Octo-Backend/sql/test_image/문어3.png');
-- ITEM
INSERT INTO `OctoDream`.`ITEM_TB` (`ITEM_PK`, `NAME`, `AMOUNT`) VALUES ('1', '아이템1', '1000');
INSERT INTO `OctoDream`.`ITEM_TB` (`ITEM_PK`, `NAME`, `AMOUNT`) VALUES ('2', '아이템2', '3000');
INSERT INTO `OctoDream`.`ITEM_TB` (`ITEM_PK`, `NAME`, `AMOUNT`) VALUES ('3', '아이템3', '5000');
-- COLLECTION
INSERT INTO `OctoDream`.`COLLECTION_TB` (`COLLECTION_PK`, `USER_FK`, `CHARACTER_IMAGE_FK`) VALUES ('1', '1', '1');
INSERT INTO `OctoDream`.`COLLECTION_TB` (`COLLECTION_PK`, `USER_FK`, `CHARACTER_IMAGE_FK`) VALUES ('2', '2', '5');
INSERT INTO `OctoDream`.`COLLECTION_TB` (`COLLECTION_PK`, `USER_FK`, `CHARACTER_IMAGE_FK`) VALUES ('3', '3', '3');
-- DIARY
INSERT INTO `OctoDream`.`DIARY_TB` (`DIARY_PK`, `USER_FK`, `CONTENT`, `CREATED_TIME`) VALUES ('1', '1', '오늘은 아주 재미있는 하루였다.', '2023-04-10 17:22:21');
INSERT INTO `OctoDream`.`DIARY_TB` (`DIARY_PK`, `USER_FK`, `CONTENT`, `CREATED_TIME`) VALUES ('2', '1', '오늘은 비가와서 조용했다.', '2023-04-11 17:22:21');
INSERT INTO `OctoDream`.`DIARY_TB` (`DIARY_PK`, `USER_FK`, `CONTENT`, `CREATED_TIME`) VALUES ('3', '2', '날씨가 화창해서 좋았다', '2023-04-11 18:42:21');
INSERT INTO `OctoDream`.`DIARY_TB` (`DIARY_PK`, `USER_FK`, `CONTENT`, `CREATED_TIME`) VALUES ('4', '3', '놀러갔다 왔다', '2023-04-15 22:12:21');
-- GUESTBOOK
INSERT INTO `OctoDream`.`GUESTBOOK_TB` (`GUESTBOOK_PK`, `USER_FK`, `GUEST_FK`, `CONTENT`, `READ_FL`, `CREATED_TIME`) VALUES ('1', '1', '3', '안녕하세요', 'false', '2023-04-10 12:22:21');
INSERT INTO `OctoDream`.`GUESTBOOK_TB` (`GUESTBOOK_PK`, `USER_FK`, `GUEST_FK`, `CONTENT`, `READ_FL`, `CREATED_TIME`) VALUES ('2', '2', '1', '뭐해', 'false', '2023-04-11 09:22:21');
INSERT INTO `OctoDream`.`GUESTBOOK_TB` (`GUESTBOOK_PK`, `USER_FK`, `GUEST_FK`, `CONTENT`, `READ_FL`, `CREATED_TIME`) VALUES ('3', '3', '2', 'ㅎㅇ', 'false', '2023-04-10 14:38:21');
-- 