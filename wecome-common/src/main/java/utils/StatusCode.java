package utils;

/**
 * @Author Ardien
 * @Date 10/5/2020 6:52 PM
 * @Email ardien@126.com
 * @Version 1.0
 **/
public final class StatusCode {

    public enum Action {
        ADD,DELETE
    }


    public enum MsgType{

        Myself(0, "自己"),
        Friend(1, "好友");

        private int value;
        private String key;

        MsgType(int value, String key) {
            this.value = value;
            this.key = key;
        }

        public int getValue() {
            return value;
        }

        public String getKey() {
            return key;
        }
    }

    public enum ChatType {
        PERSONAL(0, "个人"),GROUP(1,"群组");
        private int value ;
        private String key;

        ChatType(int value, String key) {
            this.value = value;
            this.key = key;
        }

        public int getValue() {
            return value;
        }

        public String getKey() {
            return key;
        }
    }
}
