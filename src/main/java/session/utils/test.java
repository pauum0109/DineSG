package session.utils;

/**
 * enum account. Client input theo number, 
 */
public class test {
    public static void main(String[] args) {
        //Tạo database theo thứ tự trong AccountType
        //Client request theo number role
        //1:ADMIN,2:USER,3:GUEST
        int req_index = 0;
        AccountType req = AccountType.values()[req_index];
        //
        //Take out account index from server then store as AccountType index
        int role_index = 0;
        AccountType type = AccountType.values()[role_index];
        //Lưu dưới number store vào database
        System.out.println(type.ordinal());
    }
}
 enum AccountType {
    ADMIN, USER, GUEST;
}